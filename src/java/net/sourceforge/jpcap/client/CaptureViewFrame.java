// $Id: CaptureViewFrame.java,v 1.14 2004/10/02 01:23:19 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.client;

import java.util.Iterator;
import java.util.HashMap;
import javax.swing.JFrame;
import net.sourceforge.jpcap.net.IPPort;
import net.sourceforge.jpcap.net.IPProtocol;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.IPPacket;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;
import net.sourceforge.jpcap.net.ICMPPacket;
import net.sourceforge.jpcap.net.ICMPMessage;
import net.sourceforge.jpcap.net.IGMPPacket;
import net.sourceforge.jpcap.net.IGMPMessage;


/**
 * A graphical view of captured packets.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.14 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/10/02 01:23:19 $
 */
public class CaptureViewFrame extends JFrame 
{
  /**
   * Create a new capture view frame.
   */
  public CaptureViewFrame() {
    super("Network Packet Capture Frame");

    pvc = new PacketVisualizationCanvas(hostMap, commMap);
    this.setSize(Settings.DEFAULT_CANVAS_X, Settings.DEFAULT_CANVAS_Y);
    this.getContentPane().add(pvc);

    // to keep the view frame simple, delegate mouse event handling..
    CaptureViewMouseListener cvml = new CaptureViewMouseListener(pvc);
    pvc.addMouseListener(cvml);
    pvc.addMouseMotionListener(cvml);
  }

  /**
   * If a renderer doesn't exist for a host, create a new one and add 
   * it to the renderer map. Otherwise, simply fetch the existing renderer.
   * @param ipp the IP protocol packet
   * @param srcFlag whether the desired host is the source or destination
   * of the specified packet.
   * @return a host renderer
   */
  private HostRenderer addHostRenderer(IPPacket ipp, boolean srcFlag) {
    String a = srcFlag ? ipp.getSourceAddress() : ipp.getDestinationAddress();
    HostRenderer hr;
    if(hostMap.keySet().contains(a))
      hr = (HostRenderer)hostMap.get(a);
    else {
      hr = new HostRenderer(pvc, a);
      hostMap.put(a, hr);
    }

    return hr;
  }

  /**
   * Update a single packet in the view.
   * <p>
   * When a packet arrives, three renderers are created (or, if they 
   * already exist, are pulled from cache.) There are two renderers 
   * to represent the source and destination hosts and a communication 
   * renderer for the protocol via which the hosts are communicating.
   */
  public void update(Packet packet) {
    if(packet instanceof IPPacket) {
      int srcPort = IPPort.NONE;
      int dstPort = IPPort.NONE;
      int byteCount = 0;
      if(packet instanceof UDPPacket) {
        UDPPacket up = (UDPPacket)packet;
        srcPort = up.getSourcePort();
        dstPort = up.getDestinationPort();
        byteCount += up.getData().length;
      }
      else if(packet instanceof TCPPacket) {
        TCPPacket tp = (TCPPacket)packet;
        srcPort = tp.getSourcePort();
        dstPort = tp.getDestinationPort();
        byteCount += tp.getData().length;

        /*
        if(! tp.isSyn())
          break;
        */
      }
      else if(packet instanceof ICMPPacket) {
        // icmp doesn't have ports, use message type instead..
        srcPort = dstPort = ((ICMPPacket)packet).getMessageCode();
        byteCount += ((ICMPPacket)packet).getData().length;
      }
      else if(packet instanceof IGMPPacket) {
        // igmp doesn't have ports, use message type instead..
        srcPort = dstPort = ((IGMPPacket)packet).getMessageType();
        byteCount += ((IGMPPacket)packet).getData().length;
      }
      else {
        // must be unrecognized IPPacket..
        srcPort = dstPort = ((IPPacket)packet).getProtocol();
        byteCount += ((IPPacket)packet).getData().length;
      }

      HostRenderer src = addHostRenderer((IPPacket)packet, true);
      HostRenderer dst = addHostRenderer((IPPacket)packet, false);

      String description = "unknown";
      CommRenderer commRenderer;

      // from the source and destination ports, figure out which is the 
      // server (aka. 'key' port) for the communication vector's label
      int keyPort = CommRenderer.keyPort(commMap,
                                         src.getIpAddress(), srcPort, 
                                         dst.getIpAddress(), dstPort);

      // a unique key under which the communication is indexed
      String commKey = CommRenderer.commKey(src.getIpAddress(), 
                                            dst.getIpAddress(), keyPort);

      if(commMap.keySet().contains(commKey)) {
        if(Settings.SHOW_COUNTS) {
          // if the renderer is already in the collection, just update stats..
          commRenderer = (CommRenderer)commMap.get(commKey);
          commRenderer.erase();

          commRenderer.incrementPacketCount();
          commRenderer.incrementByteCount(byteCount);
          commRenderer.paint();
        }
        else {
          commRenderer = (CommRenderer)commMap.get(commKey);
          // if(Settings.ACTIVE_HIGHLIGHT)
          // commRenderer.highlight();
        }
      }
      else {
        if(packet instanceof TCPPacket || packet instanceof UDPPacket)
          // tcp or udp? label with service name (or port # if unrecognized)
          description = IPPort.getName(keyPort);
        else if(packet instanceof ICMPPacket)
          // icmp? label with icmp message type
          description = ICMPMessage.getDescription(srcPort);
        else if(packet instanceof IGMPPacket)
          // igmp? label with igmp message type
          description = IGMPMessage.getDescription(srcPort);
        else 
          // unidentified ip? label with IP protocol name
          description = IPProtocol.getDescription(srcPort);

        String hostPairKey = CommRenderer.hostPairKey(src.getIpAddress(), 
                                                      dst.getIpAddress());
        Integer linkCount = (Integer)commLinks.get(hostPairKey);
        int lc = (linkCount == null ? 0 : linkCount.intValue()) + 1;
        commLinks.put(hostPairKey, new Integer(lc));
          
        commRenderer = 
          new CommRenderer(pvc, packet, src, dst, description, lc);
        commRenderer.incrementPacketCount();
        commRenderer.incrementByteCount(byteCount);
        commRenderer.paint();
        commMap.put(commKey, commRenderer);
      }
    }
  }

  /**
   * Reset the view frame. Removes all existing renderers, and regenerates
   * the network view from scratch.
   */
  public void reset() {
    hostMap.clear();
    commMap.clear();
    commLinks.clear();
    pvc.repaint(); // force a repaint of the now blank canvas
  }


  /**
   * Host renderer cache.
   */
  //jdk1.5: HashMap <String, HostRenderer> hostMap = new HashMap<String, HostRenderer>();
  HashMap hostMap = new HashMap();

  /**
   * Communication renderer cache.
   */
  //jdk1.5: HashMap <String, CommRenderer> commMap = new HashMap<String, CommRenderer>();
  HashMap commMap = new HashMap();

  /**
   * Communication channel counts.
   */
  //jdk1.5: HashMap <String, Integer> commLinks = new HashMap<String, Integer>();
  HashMap commLinks = new HashMap();

  /**
   * Canvas where network packets are being rendered.
   */
  PacketVisualizationCanvas pvc;

  private String _rcsid = 
    "$Id: CaptureViewFrame.java,v 1.14 2004/10/02 01:23:19 pcharles Exp $";
}
