/*
 * Copyright (C) 2026, jpcap modernised fork
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.tutorial.sniff;

import net.sourceforge.jpcap.capture.PacketCapture;
import net.sourceforge.jpcap.net.Packet;

/**
 * Tiny live-capture demo:
 *   sudo java -cp <classpath> net.sourceforge.jpcap.tutorial.sniff.Sniff [device] [count] [filter]
 * Defaults: device=en0, count=10, filter="".
 */
public class Sniff {

    public static void main(String[] args) throws Exception {
        String device = args.length > 0 ? args[0] : "en0";
        int count = args.length > 1 ? Integer.parseInt(args[1]) : 10;
        String filter = args.length > 2 ? args[2] : "";

        PacketCapture pcap = new PacketCapture();
        pcap.open(device, true);
        if (!filter.isEmpty()) {
            pcap.setFilter(filter, true);
        }
        int[] n = {0};
        pcap.addPacketListener((Packet p) ->
            System.out.println(++n[0] + ": " + p));
        System.err.println("Capturing " + count + " packet(s) on " + device
            + (filter.isEmpty() ? "" : " with filter '" + filter + "'") + " ...");
        pcap.capture(count);
        pcap.close();
        System.err.println("Stats: " + pcap.getStatistics());
    }
}
