// $Id: ICMPTest.java,v 1.1 2003/07/02 18:52:34 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package net.sourceforge.jpcap.tutorial.misc;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;
import net.sourceforge.jpcap.util.HexHelper;


/**
 * jpcap Tutorial - icmp test
 *
 * @author Patrick Charles
 * @version $Revision: 1.1 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2003/07/02 18:52:34 $
 *
 */
public class ICMPTest
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = 1;

  // BPF filter for only capturing ICMP packets
  private static final String FILTER = "proto ICMP";

  private PacketCapture m_pcap;
  private String m_device;

  public ICMPTest() throws Exception {
    // instantiate capture engine
    m_pcap = new PacketCapture();

    // open device for capturing (requires root)
    m_pcap.open("lo", true);

    // add a BPF Filter (see tcpdump documentation)
    m_pcap.setFilter(FILTER, true);

    // register a listener
    m_pcap.addPacketListener(new PacketHandler());

    // capture data
    m_pcap.capture(PACKET_COUNT);
  }

  public static void main(String[] args) {
    try {
      System.err.println("waiting for an icmp packet.. ");
      ICMPTest icmpTest = new ICMPTest();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}


class PacketHandler implements PacketListener 
{
  public void packetArrived(Packet packet) {
    try {
      ICMPPacket icmpPacket = (ICMPPacket) packet;

      String srcHost = icmpPacket.getSourceAddress();
      String dstHost = icmpPacket.getDestinationAddress();
	    
      System.out.println(icmpPacket.toColoredString(true));
      System.out.println("message code: " + icmpPacket.getMessageCode());
      System.out.println("message major: " + icmpPacket.getMessageMajorCode());
      System.out.println("message minor: " + icmpPacket.getMessageMinorCode());
      System.out.println("checksum: " + icmpPacket.getChecksum());

      System.out.println("");
      System.out.println("ethernet header: " + 
                         HexHelper.toString(icmpPacket.getEthernetHeader()));
      System.out.println("ethernet data: " + 
                         HexHelper.toString(icmpPacket.getEthernetData()));
      System.out.println("");

      System.out.println("header: " + 
                         HexHelper.toString(icmpPacket.getHeader()));

      System.err.println("data: " + HexHelper.toString(icmpPacket.getData()));
    } catch( Exception e ) {
      e.printStackTrace();
    }
  }
}
