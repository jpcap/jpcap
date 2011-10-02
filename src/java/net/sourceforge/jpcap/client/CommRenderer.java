// $Id: CommRenderer.java,v 1.8 2001/07/30 00:03:24 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.client;

import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.Graphics;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;
import net.sourceforge.jpcap.net.ICMPPacket;
import net.sourceforge.jpcap.net.IGMPPacket;


/**
 * A rendering of communication between two hosts.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.8 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/07/30 00:03:24 $
 */
public class CommRenderer
{
  /**
   * Create a new communication renderer.
   * @param canvas the canvas where this communication is being drawn.
   */
  public CommRenderer(Canvas canvas, 
                      Packet packet, 
                      HostRenderer hostA, HostRenderer hostB, 
                      String description,
                      int index) {
    this.canvas = canvas;
    this.packet = packet;
    this.hostA = hostA;
    this.hostB = hostB;
    this.description = description;
    this.index = index;
  }

  void paint() {
    draw(false);
  }

  void erase() {
    draw(true);
  }

  /**
   * Plot a communication vector between the specified points.
   * @param erase whether this is an erase operation
   */
  void drawCommLink(boolean erase, int ax, int ay, int bx, int by) {
    int offsetx = 0;
    int offsety = 0;

    if(erase)
      gc.setColor(Settings.COLOR_BG);
    else 
      if(packet instanceof UDPPacket) {
        gc.setColor(Settings.COLOR_P_UDP);
        offsetx = 1; offsety = 1;
      }
      else if(packet instanceof TCPPacket) {
        gc.setColor(Settings.COLOR_P_TCP);
        offsetx = -1; offsety = -1;
      }
      else if(packet instanceof ICMPPacket) {
        gc.setColor(Settings.COLOR_P_ICMP);
        offsetx = -2; offsety = -2;
      }
      else if(packet instanceof IGMPPacket) {
        gc.setColor(Settings.COLOR_P_IGMP);
        offsetx = 2; offsety = 2;
      }
      else gc.setColor(Settings.COLOR_P_UNKNOWN);
    
    // draw the vector connecting the hosts at the specified points
    gc.drawLine(ax + offsetx, ay + offsety, bx + offsetx, by + offsety);
  }

  /**
   * Paint this communication on the canvas.
   * @param erase whether this is an erase or draw operation.
   */
  void draw(boolean erase) {
    if(gc == null)
      gc = canvas.getGraphics();

    int ax = hostA.getRenderedX(); int ay = hostA.getRenderedY();
    int bx = hostB.getRenderedX(); int by = hostB.getRenderedY();

    drawCommLink(erase, ax, ay, bx, by);

    // protocol label starts at the midpoint between two hosts communicating
    int midX = (ax + bx) / 2; int midY = (ay + by) / 2;


    // if communication is occurring between two hosts on multiple ports
    // offset the mid-point
    midX += OFFSET * (index - 1);
    midY += OFFSET * (index - 1);

    // select the label color
    if(erase)
      gc.setColor(Settings.COLOR_BG);
    else 
      if(packet instanceof UDPPacket) 
        gc.setColor(Settings.COLOR_P_UDP_H);
      else if(packet instanceof TCPPacket) 
        gc.setColor(Settings.COLOR_P_TCP_H);
      else if(packet instanceof ICMPPacket) 
        gc.setColor(Settings.COLOR_P_ICMP_H);
      else if(packet instanceof IGMPPacket) 
        gc.setColor(Settings.COLOR_P_IGMP_H);
      else gc.setColor(Settings.COLOR_P_UNKNOWN_H);

    // label the communication vector
    gc.drawString(description + 
                  (Settings.SHOW_COUNTS ? 
                  " (" + packetCount + ":" + byteCount + ")" : ""), 
                  midX, midY);
  }

  /**
   * Fetch a description (read protocol name) of the communication 
   * associated with this renderer.
   */
  public String getDescription() {
    return description;
  }

  public void incrementPacketCount() {
    packetCount ++;
  }

  public void incrementByteCount(long newBytes) {
    byteCount += newBytes;
  }

  /**
   * Generate a port 'key'.
   * <p>
   * Usually, the lower-numbered port is the service port while the 
   * higher-numbered port is the client's outgoing port.
   * Limited session tracking is performed (by checking the cache)
   * to figure out which port is the server port.
   *
   * <p>
   * NOTE:
   * If this renderer picks up in the middle of a session already in progress,
   * and the first packet that it observes is one in transit from the server
   * to the client's dynamic port, then this logic won't work right; it 
   * will incorrectly identify the client's private port as the key or 
   * service port.
   * The only way, that I know of, to avoid this possibility is to truly 
   * delve into the details of every protocol to do sophisticated session
   * tracking.
   */
  static int keyPort(HashMap commMap, 
                     String srcAddress, int srcPort, 
                     String dstAddress, int dstPort) {
    int key = srcPort < dstPort ? srcPort : dstPort;
    // Assume that the well-known port (under 1024) is the service port.
    if(key < 1024)
      return key;

    // If the communication (either direction) is already in the 
    // cache, use the existing service port. Otherwise, 
    // assume that the destination port is the service port.
    if(commMap.keySet().contains(commKey(srcAddress, dstAddress, dstPort)))
      return dstPort;
    if(commMap.keySet().contains(commKey(srcAddress, dstAddress, srcPort)))
      return srcPort;

    return dstPort;
  }

  /**
   * Generate a communication 'key'.
   * <p>
   * Sorts the ip addresses and appends the port number to produce a 
   * hash key unique to the communication.
   */
  static String commKey(String srcAddr, String dstAddr, int port) {
    return (srcAddr.hashCode() < dstAddr.hashCode()) ? 
      (srcAddr + dstAddr + port) : (dstAddr + srcAddr + port);
  }

  /**
   * Generate a host pair key.
   * <p>
   * Sorts the ip addresses to make a unique host pair key.
   */
  static String hostPairKey(String srcAddr, String dstAddr) {
    return 
      (srcAddr.hashCode() < dstAddr.hashCode()) ? 
      (srcAddr + dstAddr) : (dstAddr + srcAddr);
  }


  Packet packet;
  HostRenderer hostA;
  HostRenderer hostB;
  String description;
  int index;
  int packetCount;
  long byteCount;
  private static int OFFSET = 10;

  Graphics gc;
  private Canvas canvas;

  private String _rcsid = 
    "$Id: CommRenderer.java,v 1.8 2001/07/30 00:03:24 pcharles Exp $";
}
