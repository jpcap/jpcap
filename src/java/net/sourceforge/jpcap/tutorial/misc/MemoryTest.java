// $Id: MemoryTest.java,v 1.3 2003/11/19 17:44:55 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package net.sourceforge.jpcap.tutorial.misc;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;


/**
 * Memory Test.
 *
 * @author Jonas Lehmann and Patrick Charles
 * @version $Revision: 1.3 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2003/11/19 17:44:55 $
 */
public class MemoryTest
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = 10; 
  private static int m_counter = 0;

  
  // BPF filter for capturing any packet
  private static final String FILTER = "";

  private PacketCapture m_pcap;
  private String m_device;

  public MemoryTest() throws Exception {
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

    // Step 6:  Capture Data
    m_pcap.capture(INFINITE);
  }

  public static void main(String[] args) {
    try {
      MemoryTest memoryTest = new MemoryTest();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }


class RawPacketHandler implements RawPacketListener 
{
  String string;
  public void rawPacketArrived(RawPacket data) {
    System.err.println("vm total memory: " + Runtime.getRuntime().totalMemory());
    m_counter++;
    System.out.println("Received packet (" + m_counter + ")");
    //    System.err.println(string);
  }
}
}
