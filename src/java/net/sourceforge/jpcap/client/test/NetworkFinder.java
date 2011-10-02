// $Id: NetworkFinder.java,v 1.1 2002/03/29 04:31:10 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and William Johnsen                 *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package net.sourceforge.jpcap.client;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;
import java.util.HashSet;


/**
 * Network Finder.
 *
 * @author William Johnsen and Patrick Charles
 * @version $Revision: 1.1 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2002/03/29 04:31:10 $
 *
 * Capture IP packets and extract source and destination addresses.
 * <p>
 * This class is intended to be run on a 'foreign' wired or wireless 
 * network where the user has no idea what IP subnets are in use and is 
 * unable to obtain an IP address via DHCP or BOOTP.
 * <p>
 * Run this program to interrogate the network and find out what subnet 
 * is in use.
 * <p>
 * Before running this class, configure your network adapter
 * using any IP address (e.g. 1.1.1.1) and a wide netmask like 0.0.0.0.
 * Even though the IP address may not be valid on the subnet connected to, 
 * it will pick up broadcast packets.
 * <p>
 * This program will extract from the received packets information about 
 * active IPs from which the user can then infer the subnet and valid 
 * free IP addresses for his own use.
 */
public class NetworkFinder
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = INFINITE; 

  public NetworkFinder() throws Exception {
    // Initialize jpcap
    PacketCapture pcap = new PacketCapture();
    String device = pcap.findDevice();
    pcap.open(device, true);
    pcap.setFilter("", true);
    pcap.addPacketListener(new PacketHandler());

    System.out.println("Waiting for IP traffic. ");
    System.out.println("Extracting addresses... ");

    // Start capturing packets...
    pcap.capture(PACKET_COUNT);
  }

  public static void main(String[] args) {
    try {
      NetworkFinder finder = new NetworkFinder();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}

class PacketHandler implements PacketListener 
{
  private boolean flag = false;

  private void checkAddress(String address) {
    if(!addresses.contains(address)) {
      if(flag == true)
	System.out.println();

      System.out.println(address);
      addresses.add(address);
      flag = false;
    }
  }

  public void packetArrived(Packet packet) {
    try {
      if(packet instanceof IPPacket) {
	IPPacket ipPacket = (IPPacket)packet;

	String srcHost = ipPacket.getSourceAddress();
	String dstHost = ipPacket.getDestinationAddress();

	checkAddress(srcHost);
	checkAddress(dstHost);
      }
      else {
	System.err.print(".");
	flag = true;
      }
    }
    catch( Exception e ) {
      e.printStackTrace();
    }
  }

  private HashSet addresses = new HashSet();
}
