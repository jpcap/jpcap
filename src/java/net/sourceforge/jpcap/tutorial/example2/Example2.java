// $Id: Example2.java,v 1.3 2002/02/18 15:33:00 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package net.sourceforge.jpcap.tutorial.example2;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;


/**
 * jpcap Tutorial - Example 2
 *
 * @author Jonas Lehmann and Patrick Charles
 * @version $Revision: 1.3 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2002/02/18 15:33:00 $
 */
public class Example2 
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = 10; 

  // BPF filter for capturing any packet
  private static final String FILTER = "";

  private PacketCapture m_pcap;
  private String m_device;

  public Example2() throws Exception {
    // Step 1:  Instantiate Capturing Engine
    m_pcap = new PacketCapture();

    // Step 2:  Check for devices 
    m_device = m_pcap.findDevice();

    // Step 3:  Open Device for Capturing (requires root)
    m_pcap.open(m_device, true);

    // Step 4:  Add a BPF Filter (see tcpdump documentation)
    m_pcap.setFilter(FILTER, true);

    // Step 5:  Register a Listener for Raw Packets
    m_pcap.addRawPacketListener(new RawPacketHandler());

    // Step 6:  Capture Data (max. PACKET_COUNT packets)
    m_pcap.capture(PACKET_COUNT);
  }

  public static void main(String[] args) {
    try {
      Example2 example = new Example2();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}


class RawPacketHandler implements RawPacketListener 
{
  private static int m_counter = 0;

  public void rawPacketArrived(RawPacket data) {
    m_counter++;
    System.out.println("Packet " + m_counter + "\n" + data + "\n");
  }
}
