// $Id: SerializationTest.java,v 1.3 2003/11/19 17:44:55 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package net.sourceforge.jpcap.tutorial.misc;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;
import java.io.*;


/**
 * jpcap Tutorial - serialization test
 *
 * @author Patrick Charles
 * @version $Revision: 1.3 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2003/11/19 17:44:55 $
 */
public class SerializationTest
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = 100;

  // BPF filter for capturing any packet
  private static final String FILTER = "";

  private PacketCapture pcap;
  PacketHandler ph;
  RawPacketHandler rph;

  public SerializationTest() throws Exception {
    // instantiate capture engine
    pcap = new PacketCapture();

    // open devices for capturing (requires root)
    pcap.open("eth0", true);

    // add a BPF filter (see tcpdump documentation)
    pcap.setFilter(FILTER, true);

    // create a handler

    // packet
    ph = new PacketHandler("packet");
    pcap.addPacketListener(ph);

    // raw
    //rph = new RawPacketHandler("raw");
    //pcap.addRawPacketListener(rph);

    // capture packets
    pcap.capture(PACKET_COUNT);
  }

  public static void main(String[] args) throws Exception {
    SerializationTest sTest = new SerializationTest();
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

    try {
      System.err.print("serializing the packet.. ");
      ByteArrayOutputStream bostr = new ByteArrayOutputStream();
      ObjectOutputStream oostr = new ObjectOutputStream(bostr);
      oostr.writeObject(packet);
      oostr.close(); 
      bostr.close();
      System.err.println("ok");
    }
    catch(IOException e) {
      e.printStackTrace();
    }
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

    try {
      System.err.print("serializing the packet.. ");
      ByteArrayOutputStream bostr = new ByteArrayOutputStream();
      ObjectOutputStream oostr = new ObjectOutputStream(bostr);
      oostr.writeObject(rawPacket);
      oostr.close(); 
      bostr.close();
      System.err.println("ok");
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

  String name;
}
}
