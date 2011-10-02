// $Id: OfflineTest.java,v 1.4 2003/11/19 17:44:55 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2003, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package net.sourceforge.jpcap.tutorial.misc;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;
import net.sourceforge.jpcap.util.HexHelper;


/**
 * jpcap Tutorial - openOffline() test
 *
 * @author Patrick Charles
 * @version $Revision: 1.4 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2003/11/19 17:44:55 $
 */
public class OfflineTest
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = INFINITE;

  // BPF filter for capturing any packet
  private static final String FILTER = "";

  private PacketCapture pcap;
  PacketHandler ph;
  RawPacketHandler rph;

  public OfflineTest(String fileName) throws Exception {
    // instantiate capture engine
    pcap = new PacketCapture();

    // open devices for capturing (requires root)
    pcap.openOffline(fileName);

    // add a BPF filter (see tcpdump documentation)
    pcap.setFilter(FILTER, true);

    // create a handler

    // packet
    ph = new PacketHandler("offline");
    pcap.addPacketListener(ph);

    // raw
    //rph = new RawPacketHandler("offline");
    //pcap.addRawPacketListener(rph);

    // capture packets
    pcap.capture(PACKET_COUNT);
  }

  public static void main(String[] args) throws Exception {
    if(args.length < 1) {
      System.err.println("  usage: OfflineTest filename");
      System.exit(2);
    }
    OfflineTest offlineTest = new OfflineTest(args[0]);
  }

class PacketHandler implements PacketListener 
{
  private int counter = 0;

  public PacketHandler(String name) {
    this.name = name;
  }

  public void packetArrived(Packet packet) {
    counter++;
    String type = packet.getClass().getName();
    System.out.println(name + ": Packet(" + counter + 
                       ") is of type " + type + ".");
    System.err.println(HexHelper.toString(packet.getData()));
  }

  String name;
}

class RawPacketHandler implements RawPacketListener 
{
  private int counter = 0;

  public RawPacketHandler(String name) {
    this.name = name;
  }

  public void rawPacketArrived(RawPacket rawPacket) {
    counter++;
    System.err.println(rawPacket);
  }

  String name;
}
}
