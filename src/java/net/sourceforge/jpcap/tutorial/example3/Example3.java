// $Id: Example3.java,v 1.6 2004/05/05 23:14:45 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package net.sourceforge.jpcap.tutorial.example3;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;


/**
 * jpcap Tutorial - Example 3
 *
 * @author Jonas Lehmann and Patrick Charles
 * @version $Revision: 1.6 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/05/05 23:14:45 $
 */
public class Example3 
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = INFINITE; 
  private static int m_counter = 0;
  
  // BPF filter for capturing any packet
  private static final String FILTER = "";

  private PacketCapture m_pcap;
  private String m_device;

  public Example3() throws Exception {
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
      Example3 example = new Example3();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

class PacketHandler implements PacketListener 
{

  public void packetArrived(Packet packet) {
    m_counter++;
    String type = packet.getClass().getName();
    System.out.println("Packet(" + m_counter + ") is of type " + type + ".");
    System.out.println("Packet(" + m_counter + ") time = " + packet.getTimeval());
  }
}
}
