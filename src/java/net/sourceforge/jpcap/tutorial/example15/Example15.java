/***************************************************************************
 *          Christian Tzolov (tzolov@cs.utwente.nl)                        *
 *          Nikolay Diakov (diakov@cs.utwnte.nl)                           *
 ***************************************************************************/

package net.sourceforge.jpcap.tutorial.example15;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;

/*
 *  This example utilizes the endCapture() feature.
 */
public class Example15 {
    private static final int INFINITE = -1;
    private static final int PACKET_COUNT = INFINITE;
    
    // BPF filter for capturing any packet
    private static final String FILTER = "";
    
    private PacketCapture m_pcap;
    private String m_device;
    
    
    public Example15() throws Exception {
      // Step 1:  Instantiate Capturing Engine
      m_pcap = new PacketCapture();

      // Step 2:  Check for devices 
      m_device = m_pcap.findDevice();

      // Step 3:  Open Device for Capturing (requires root)
      m_pcap.open(m_device, true);

      // Step 4:  Add a BPF Filter (see tcpdump documentation)
      m_pcap.setFilter(FILTER, true);

      // Step 5:  Register a Listener for Raw Packets        
      m_pcap.addRawPacketListener(new RawPacketHandler(m_pcap));

      // Step 6:  Capture Data (max. PACKET_COUNT packets)
      m_pcap.capture(PACKET_COUNT);
    }

    public static void main(String[] args) {
      try {
        Example15 example = new Example15();
      } catch(Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
    }    
}


class RawPacketHandler implements RawPacketListener 
{
  private static int m_counter = 0;
  private PacketCapture m_pcap = null;
    
  public RawPacketHandler(PacketCapture pcap) {
    m_counter = 0;
    m_pcap = pcap;
  }

  public synchronized void rawPacketArrived(RawPacket data) {
    m_counter++;
    System.out.println("Packet " + m_counter + "\n" + data + "\n");

    if(condition())
      m_pcap.endCapture();
  }

  private boolean condition() {
    return (m_counter == 5) ? true : false;
  }
}
