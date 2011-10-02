// $Id: Example3b.java,v 1.5 2004/02/22 22:29:06 pcharles Exp $

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
 * Same as Example 3, but includes multiple listeners.
 *
 * This class works by default on Linux. For other platforms, change 
 * the device names 'lo' and 'eth0' below to the devices you'd like to test.
 *
 * For a multiple, simultaneous capture example that works on any platform, 
 * see Example3c.
 *
 * @author Jonas Lehmann and Patrick Charles
 * @version $Revision: 1.5 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/02/22 22:29:06 $
 */
public class Example3b
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = 10; // INFINITE

  // BPF filter for capturing any packet
  private static final String FILTER = "";

  private PacketCapture pcapLo;
  private PacketCapture pcapE0;

  PacketHandler phLo;
  PacketHandler phE0;

  public Example3b() throws Exception {
    // instantiate capture engine
    pcapLo = new PacketCapture();
    pcapE0 = new PacketCapture();

    // open devices for capturing (requires root)
    pcapLo.open("lo", true);
    pcapE0.open("eth0", true);

    // add a BPF filter (see tcpdump documentation)
    pcapLo.setFilter(FILTER, true);
    pcapE0.setFilter(FILTER, true);

    // create multiple handlers, each with a separate listener
    phLo = new PacketHandler("lo");
    pcapLo.addPacketListener(phLo);

    phE0 = new PacketHandler("eth0");
    pcapE0.addPacketListener(phE0);

    // create a separate thread for each capture instance
    CaptureThread ctLo = new CaptureThread(pcapLo, PACKET_COUNT);
    CaptureThread ctE0 = new CaptureThread(pcapE0, PACKET_COUNT);

    // capture packets
    ctLo.start();
    ctE0.start();
  }

  public static void main(String[] args) {
    try {
      Example3b example = new Example3b();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

class CaptureThread extends Thread
{
  public CaptureThread(PacketCapture pc, int count) {
    this.pc = pc;
    this.count = count;
  }

  public void run() {
    try {
      pc.capture(count);
    }
    catch(CapturePacketException cpe) {
      cpe.printStackTrace();
    }
  }

  PacketCapture pc;
  int count;
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
    System.err.println(packet);
  }

  String name;
}
}
