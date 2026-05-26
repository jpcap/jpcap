/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TCPPacketTest {

    private static final byte[] SYN_ACK_PACKET = {
        0x00, 0x10, 0x7b, 0x38, 0x46, 0x33, 0x08, 0x00, 0x20, (byte) 0x89,
        (byte) 0xa5, (byte) 0x9f, 0x08, 0x00, 0x45, 0x00, 0x00, 0x2c,
        (byte) 0x93, (byte) 0x83, 0x40, 0x00, (byte) 0xff, 0x06, 0x6c,
        0x38, (byte) 0xac, 0x10, 0x70, 0x32, (byte) 0x87, 0x0d, (byte) 0xd8,
        (byte) 0xbf, 0x00, 0x19, 0x50, 0x49, 0x78, (byte) 0xbe, (byte) 0xe0,
        (byte) 0xa7, (byte) 0x9f, 0x3a, (byte) 0xb4, 0x03, 0x60, 0x12, 0x22,
        0x38, (byte) 0xfc, (byte) 0xc7, 0x00, 0x00, 0x02, 0x04, 0x05,
        (byte) 0xb4, 0x70, 0x6c };

    private static final byte[] PSH_ACK_PACKET = {
        0x08, 0x00, 0x20, (byte) 0x89, (byte) 0xa5, (byte) 0x9f, 0x00, 0x10,
        0x7b, 0x38, 0x46, 0x33, 0x08, 0x00, 0x45, 0x00, 0x00, 0x3e,
        (byte) 0x87, 0x08, 0x40, 0x00, 0x3f, 0x06, 0x38, (byte) 0xa2,
        (byte) 0x87, 0x0d, (byte) 0xd8, (byte) 0xbf, (byte) 0xac, 0x10, 0x70,
        0x32, 0x50, 0x49, 0x00, 0x19, (byte) 0x9f, 0x3a, (byte) 0xb4, 0x03,
        0x78, (byte) 0xbe, (byte) 0xe0, (byte) 0xf8, 0x50, 0x18, 0x7d, 0x78,
        (byte) 0x86, (byte) 0xf0, 0x00, 0x00, 0x45, 0x48, 0x4c, 0x4f, 0x20,
        0x61, 0x6c, 0x70, 0x68, 0x61, 0x2e, 0x61, 0x70, 0x70, 0x6c, 0x65,
        0x2e, 0x65, 0x64, 0x75, 0x0d, 0x0a };

    private TCPPacket _synAck;
    private TCPPacket _pshAck;
    private TCPPacket _baddie;

    @BeforeEach
    void setUp() {
        int linkLayerLen = LinkLayer.getLinkLayerLength(LinkLayers.EN10MB);
        _synAck = new TCPPacket(linkLayerLen, SYN_ACK_PACKET);
        _pshAck = new TCPPacket(linkLayerLen, PSH_ACK_PACKET);
        byte[] badBytes = new byte[SYN_ACK_PACKET.length];
        new Random().nextBytes(badBytes);
        _baddie = new TCPPacket(linkLayerLen, badBytes);
    }

    @Test
    void synAckPacketHeaderLengths() {
        assertEquals(24, _synAck.getTCPHeaderLength());
        assertEquals(24, _synAck.getTCPHeader().length);
        assertEquals(24, _synAck.getHeaderLength());
        assertEquals(24, _synAck.getHeader().length);
    }

    @Test
    void pshAckPacketHeaderLengths() {
        assertEquals(20, _pshAck.getTCPHeaderLength());
        assertEquals(20, _pshAck.getTCPHeader().length);
        assertEquals(20, _pshAck.getHeaderLength());
        assertEquals(20, _pshAck.getHeader().length);
    }

    @Test
    void synAckPacketDataLengths() {
        assertEquals(0, _synAck.getTCPData().length);
        assertEquals(0, _synAck.getData().length);
    }

    @Test
    void pshAckPacketDataLengths() {
        assertEquals(22, _pshAck.getTCPData().length);
        assertEquals(22, _pshAck.getData().length);
    }

    @Test
    void synAckPacketPorts() {
        assertEquals(25, _synAck.getSourcePort());
        assertEquals(20553, _synAck.getDestinationPort());
    }

    @Test
    void pshAckPacketAddresses() {
        assertEquals(20553, _pshAck.getSourcePort());
        assertEquals(25, _pshAck.getDestinationPort());
    }

    @Test
    void synAckPacketHeaderValues() {
        assertEquals(2025775271L, _synAck.getSequenceNumber());
        assertEquals(2671424515L, _synAck.getAcknowledgmentNumber());
        assertEquals(8760, _synAck.getWindowSize());
        assertEquals(0xfcc7, _synAck.getTCPChecksum());
        assertEquals(0xfcc7, _synAck.getChecksum());
        assertEquals(0, _synAck.getUrgentPointer());
        assertFalse(_synAck.isUrg());
        assertTrue(_synAck.isAck());
        assertFalse(_synAck.isPsh());
        assertFalse(_synAck.isRst());
        assertTrue(_synAck.isSyn());
        assertFalse(_synAck.isFin());
    }

    @Test
    void pshAckPacketHeaderValues() {
        assertEquals(2671424515L, _pshAck.getSequenceNumber());
        assertEquals(2025775352L, _pshAck.getAcknowledgmentNumber());
        assertEquals(32120, _pshAck.getWindowSize());
        assertEquals(0x86f0, _pshAck.getTCPChecksum());
        assertEquals(0x86f0, _pshAck.getChecksum());
        assertEquals(0, _pshAck.getUrgentPointer());
        assertFalse(_pshAck.isUrg());
        assertTrue(_pshAck.isAck());
        assertTrue(_pshAck.isPsh());
        assertFalse(_pshAck.isRst());
        assertFalse(_pshAck.isSyn());
        assertFalse(_pshAck.isFin());
    }

    @Test
    void badPacketHeaderLengths() {
        assertTrue(_baddie.getTCPHeader().length >= 0);
        assertTrue(_baddie.getHeader().length >= 0);
    }

    @Test
    void badPacketDataLengths() {
        assertTrue(_baddie.getTCPData().length >= 0);
        assertTrue(_baddie.getData().length >= 0);
    }
}
