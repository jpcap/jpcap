// $Id: Example5.java,v 1.2 2001/07/02 16:44:21 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package net.sourceforge.jpcap.tutorial.example5;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;


/**
 * jpcap Tutorial - Example 5
 *
 * @author Jonas Lehmann and Patrick Charles
 * @version $Revision: 1.2 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/07/02 16:44:21 $
 *
 * Run example and then initiate a ping on the network.
 * The example will produce output only if ICMP (ping)
 * packets are captured.
 */
public class Example5 
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = INFINITE; 

  // BPF filter for only capturing ICMP packets
  private static final String FILTER = "proto ICMP";

  private PacketCapture m_pcap;
  private String m_device;
  
  public Example5() throws Exception {
    // Step 1:  Instantiate Capturing Engine
    m_pcap = new PacketCapture();

    // Step 2:  Check for devices 
    m_device = m_pcap.findDevice();

    // Step 3:  Open Device for Capturing (requires root)
    m_pcap.open(m_device, true);

    // Step 4:  Add a BPF Filter (see tcpdump documentation)
    m_pcap.setFilter(FILTER, true);

    // Step 5:  Register a Listener for jpcap Packets
    m_pcap.addPacketListener(new PacketHandler());

    // Step 6:  Capture Data (max. PACKET_COUNT packets)
    m_pcap.capture(PACKET_COUNT);
  }

  public static void main(String[] args) {
    try {
      Example5 example = new Example5();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}


class PacketHandler implements PacketListener 
{
  private static int m_counter = 0;
  private static int m_icmpCounter = 0;

  public void packetArrived(Packet packet) {
    m_counter++;

    if(packet instanceof ICMPPacket)
      m_icmpCounter++;
    else
      System.err.println("Packet slipped thru filter: " + 
                         packet.getClass().getName());

    System.out.println("Total Packets: " + m_counter + 
                       "   ICMP Packets: " + m_icmpCounter);
  }
}
