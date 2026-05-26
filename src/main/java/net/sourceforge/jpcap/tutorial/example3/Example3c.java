/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.tutorial.example3;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;

/**
 * Capture packets simultaneously on all detected ethernet devices.
 *
 * Modifications to Example3 submitted by Anatol Novchenko.
 */
public class Example3c
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = -1; // INFINITE

  // BPF filter for capturing any packet
  private static final String FILTER = "";

  private PacketCapture pcap;
  private PacketHandler ph;
  private CaptureThread ct;

  public Example3c() throws Exception {
    // instantiate capture engine
    String[] devices = PacketCapture.lookupDevices();

    // capture packets on all detected devices
    for(int i=0;i<devices.length;i++) {
      System.err.println("adding device: " + devices[i]);

      pcap = new PacketCapture();
      pcap.open(devices[i], true);
      pcap.setFilter(FILTER, true);

      ph = new PacketHandler(devices[i]);
      pcap.addPacketListener(ph);

      ct = new CaptureThread(pcap, PACKET_COUNT, i);
      ct.start();
    }       
  }

  public static void main(String[] args) {
    try {
      Example3c example = new Example3c();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}

class CaptureThread extends Thread
{
  public CaptureThread(PacketCapture pc, int count, int instance) {
    this.pc = pc;
    this.count = count;
    this.instance = instance;
  }

  public void run() {
    try {
      pc.capture(count);
      System.out.println("Stop on device " + instance);
    }
    catch(CapturePacketException cpe) {
      cpe.printStackTrace();
    }
  }

  PacketCapture pc;
  int count;
  int instance;
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
