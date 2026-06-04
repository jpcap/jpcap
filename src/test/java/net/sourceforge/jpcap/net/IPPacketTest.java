/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IPPacketTest {

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

    private IPPacket _synAck;
    private IPPacket _pshAck;
    private IPPacket _baddie;

    @BeforeEach
    void setUp() {
        int linkLayerLen = LinkLayer.getLinkLayerLength(LinkLayers.EN10MB);
        _synAck = new IPPacket(linkLayerLen, SYN_ACK_PACKET);
        _pshAck = new IPPacket(linkLayerLen, PSH_ACK_PACKET);
        byte[] badBytes = new byte[SYN_ACK_PACKET.length];
        new Random().nextBytes(badBytes);
        _baddie = new IPPacket(linkLayerLen, badBytes);
    }

    @Test
    void synAckPacketHeaderLengths() {
        assertEquals(20, _synAck.getIPHeaderLength());
        assertEquals(20, _synAck.getIPHeader().length);
        assertEquals(20, _synAck.getHeaderLength());
        assertEquals(20, _synAck.getHeader().length);
    }

    @Test
    void pshAckPacketHeaderLengths() {
        assertEquals(20, _pshAck.getIPHeaderLength());
        assertEquals(20, _pshAck.getIPHeader().length);
        assertEquals(20, _pshAck.getHeaderLength());
        assertEquals(20, _pshAck.getHeader().length);
    }

    @Test
    void synAckPacketDataLengths() {
        assertEquals(24, _synAck.getIPData().length);
        assertEquals(24, _synAck.getData().length);
    }

    @Test
    void pshAckPacketDataLengths() {
        assertEquals(42, _pshAck.getIPData().length);
        assertEquals(42, _pshAck.getData().length);
    }

    @Test
    void synAckPacketAddresses() {
        assertEquals("172.16.112.50", _synAck.getSourceAddress());
        assertEquals("135.13.216.191", _synAck.getDestinationAddress());
        assertEquals(2886758450L, _synAck.getSourceAddressAsLong());
        assertEquals(2265831615L, _synAck.getDestinationAddressAsLong());

        byte[] src = _synAck.getSourceAddressBytes();
        assertTrue(src[0] == (byte) 172 && src[1] == (byte) 16
                && src[2] == (byte) 112 && src[3] == (byte) 50,
            () -> "Source bytes are: " + asDotted(src));

        byte[] dst = _synAck.getDestinationAddressBytes();
        assertTrue(dst[0] == (byte) 135 && dst[1] == (byte) 13
                && dst[2] == (byte) 216 && dst[3] == (byte) 191,
            () -> "Dest bytes are: " + asDotted(dst));
    }

    @Test
    void pshAckPacketAddresses() {
        assertEquals("135.13.216.191", _pshAck.getSourceAddress());
        assertEquals("172.16.112.50", _pshAck.getDestinationAddress());
    }

    @Test
    void synAckPacketHeaderValues() {
        assertEquals(IPProtocols.TCP, _synAck.getIPProtocol());
        assertEquals(IPProtocols.TCP, _synAck.getProtocol());
        assertEquals(0x6c38, _synAck.getIPChecksum());
        assertEquals(0x6c38, _synAck.getChecksum());
        IPPacket.TestProbe probe = _synAck.new TestProbe();
        assertTrue(_synAck.isValidChecksum(),
            () -> "Computed checksum mismatch: expected "
                + Integer.toHexString(_synAck.getIPChecksum())
                + " got " + Integer.toHexString(probe.getComputedSenderIPChecksum()));
        assertEquals(IPVersions.IPV4, _synAck.getVersion());
        assertEquals(0, _synAck.getTypeOfService());
        assertEquals(44, _synAck.getLength());
        assertEquals(0x9383, _synAck.getId());
        assertEquals(2, _synAck.getFragmentFlags());
        assertEquals(0, _synAck.getFragmentOffset());
        assertEquals(255, _synAck.getTimeToLive());
    }

    @Test
    void pshAckPacketHeaderValues() {
        assertEquals(IPProtocols.TCP, _pshAck.getIPProtocol());
        assertEquals(IPProtocols.TCP, _pshAck.getProtocol());
        assertEquals(0x38a2, _pshAck.getIPChecksum());
        assertEquals(0x38a2, _pshAck.getChecksum());
        IPPacket.TestProbe probe = _pshAck.new TestProbe();
        assertTrue(_pshAck.isValidChecksum(),
            () -> "Computed checksum mismatch: expected "
                + Integer.toHexString(_pshAck.getIPChecksum())
                + " got " + Integer.toHexString(probe.getComputedSenderIPChecksum()));
        assertEquals(IPVersions.IPV4, _pshAck.getVersion());
        assertEquals(0, _pshAck.getTypeOfService());
        assertEquals(62, _pshAck.getLength());
        assertEquals(0x8708, _pshAck.getId());
        assertEquals(2, _pshAck.getFragmentFlags());
        assertEquals(0, _pshAck.getFragmentOffset());
        assertEquals(63, _pshAck.getTimeToLive());
    }

    @Test
    void badPacketHeaderLengths() {
        assertTrue(_baddie.getIPHeader().length >= 0);
        assertTrue(_baddie.getHeader().length >= 0);
    }

    @Test
    void badPacketDataLengths() {
        assertTrue(_baddie.getIPData().length >= 0);
        assertTrue(_baddie.getData().length >= 0);
    }

    private static String asDotted(byte[] addr) {
        return (addr[0] & 0xff) + "." + (addr[1] & 0xff) + "."
            + (addr[2] & 0xff) + "." + (addr[3] & 0xff);
    }
}
