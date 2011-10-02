// $Id: Example7.java,v 1.2 2001/07/02 16:44:21 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package net.sourceforge.jpcap.tutorial.example7;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;


/**
 * jpcap Tutorial - Example 7
 *
 * @author Jonas Lehmann and Patrick Charles
 * @version $Revision: 1.2 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/07/02 16:44:21 $
 *
 * First modify the HOST constant to an IP address of a host to sniff.
 * The host HOST must support telnet logins for example to work properly.
 *
 * Run example and telnet to the host HOST.
 * All TCP connections to/from the host HOST and the data contents of each 
 * packet will be displayed.
 */
public class Example7 
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = INFINITE; 
  private static final String HOST = "172.16.32.32";
  private static final String FILTER = 
    "host " + HOST + " and proto TCP and port 23";

  public Example7() throws Exception {
    // Initialize jpcap
    PacketCapture pcap = new PacketCapture();
    String device = pcap.findDevice();
    pcap.open(device, true);
    pcap.setFilter(FILTER, true);
    pcap.addPacketListener(new PacketHandler());

    // Start capturing packets...
    pcap.capture(PACKET_COUNT);
  }

  public static void main(String[] args) {
    try {
      Example7 example = new Example7();
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
      TCPPacket tcpPacket = (TCPPacket)packet;

      byte[] data = tcpPacket.getData();
	    
      String srcHost = tcpPacket.getSourceAddress();
      String dstHost = tcpPacket.getDestinationAddress();
      String isoData = new String(data, "ISO-8859-1");

      System.out.println(srcHost+" -> " + dstHost + ": " + isoData);
    }
    catch( Exception e ) {
      e.printStackTrace();
    }
  }
}
