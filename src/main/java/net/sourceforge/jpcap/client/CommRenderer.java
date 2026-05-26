/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.HashMap;

import net.sourceforge.jpcap.net.ICMPPacket;
import net.sourceforge.jpcap.net.IGMPPacket;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;

/**
 * A rendering of communication between two hosts.
 */
public class CommRenderer {

    private static final int OFFSET = 10;

    private final Component canvas;
    private final Packet packet;
    private final HostRenderer hostA;
    private final HostRenderer hostB;
    private final String description;
    private final int index;
    private int packetCount;
    private long byteCount;

    public CommRenderer(Component canvas,
                        Packet packet,
                        HostRenderer hostA, HostRenderer hostB,
                        String description,
                        int index) {
        this.canvas = canvas;
        this.packet = packet;
        this.hostA = hostA;
        this.hostB = hostB;
        this.description = description;
        this.index = index;
    }

    void paint(Graphics g) {
        int ax = hostA.getRenderedX();
        int ay = hostA.getRenderedY();
        int bx = hostB.getRenderedX();
        int by = hostB.getRenderedY();

        int offsetX = 0;
        int offsetY = 0;
        Color line;
        Color label;
        if (packet instanceof UDPPacket) {
            line = Settings.COLOR_P_UDP; label = Settings.COLOR_P_UDP_H;
            offsetX = 1; offsetY = 1;
        } else if (packet instanceof TCPPacket) {
            line = Settings.COLOR_P_TCP; label = Settings.COLOR_P_TCP_H;
            offsetX = -1; offsetY = -1;
        } else if (packet instanceof ICMPPacket) {
            line = Settings.COLOR_P_ICMP; label = Settings.COLOR_P_ICMP_H;
            offsetX = -2; offsetY = -2;
        } else if (packet instanceof IGMPPacket) {
            line = Settings.COLOR_P_IGMP; label = Settings.COLOR_P_IGMP_H;
            offsetX = 2; offsetY = 2;
        } else {
            line = Settings.COLOR_P_UNKNOWN; label = Settings.COLOR_P_UNKNOWN_H;
        }

        g.setColor(line);
        g.drawLine(ax + offsetX, ay + offsetY, bx + offsetX, by + offsetY);

        int midX = (ax + bx) / 2 + OFFSET * (index - 1);
        int midY = (ay + by) / 2 + OFFSET * (index - 1);

        g.setColor(label);
        String text = description
            + (Settings.SHOW_COUNTS
                ? " (" + packetCount + ":" + byteCount + ")"
                : "");
        g.drawString(text, midX, midY);
    }

    public String getDescription() {
        return description;
    }

    public void incrementPacketCount() {
        packetCount++;
    }

    public void incrementByteCount(long newBytes) {
        byteCount += newBytes;
    }

    static int keyPort(HashMap commMap,
                       String srcAddress, int srcPort,
                       String dstAddress, int dstPort) {
        int key = Math.min(srcPort, dstPort);
        if (key < 1024) {
            return key;
        }
        if (commMap.keySet().contains(commKey(srcAddress, dstAddress, dstPort))) {
            return dstPort;
        }
        if (commMap.keySet().contains(commKey(srcAddress, dstAddress, srcPort))) {
            return srcPort;
        }
        return dstPort;
    }

    static String commKey(String srcAddr, String dstAddr, int port) {
        return srcAddr.hashCode() < dstAddr.hashCode()
            ? srcAddr + dstAddr + port
            : dstAddr + srcAddr + port;
    }

    static String hostPairKey(String srcAddr, String dstAddr) {
        return srcAddr.hashCode() < dstAddr.hashCode()
            ? srcAddr + dstAddr
            : dstAddr + srcAddr;
    }
}
