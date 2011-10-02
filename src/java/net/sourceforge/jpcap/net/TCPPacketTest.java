// $Id: TCPPacketTest.java,v 1.1 2002/07/10 23:12:09 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;

import java.util.Random;
import junit.framework.*;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.LinkLayer;
import net.sourceforge.jpcap.net.LinkLayers;

public class TCPPacketTest extends TestCase {

    // yes, I realize that as TCP packets, these are not SYN-ACK and 
    // PSH-ACKs, but I use the same shell for testing TCPPacket
    private TCPPacket _synAck;
    private TCPPacket _pshAck;
    // and bad is always bad
    private TCPPacket _baddie;
    
    public TCPPacketTest (String testName) {
        super(testName);
    }        
    
    public static void main(java.lang.String[] args) {
	//junit.swingui.TestRunner.main (new String[] {TCPPacketTest.class.getName ()});
        junit.textui.TestRunner.run (suite ());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite (TCPPacketTest.class);
        return suite;
    }

    private static byte[] SYN_ACK_PACKET = {
	0x00, 0x10, 0x7b, 0x38, 0x46, 0x33, 0x08, 0x00, 0x20, (byte)0x89,
	(byte)0xa5, (byte)0x9f, 0x08, 0x00, 0x45, 0x00, 0x00, 0x2c,
	(byte)0x93, (byte)0x83, 0x40, 0x00, (byte)0xff, 0x06, 0x6c,
	0x38, (byte)0xac, 0x10, 0x70, 0x32, (byte)0x87, 0x0d, (byte)0xd8,
	(byte)0xbf, 0x00, 0x19, 0x50, 0x49, 0x78, (byte)0xbe, (byte)0xe0,
	(byte)0xa7, (byte)0x9f, 0x3a, (byte)0xb4, 0x03, 0x60, 0x12, 0x22,
	0x38, (byte)0xfc, (byte)0xc7, 0x00, 0x00, 0x02, 0x04, 0x05,
	(byte)0xb4, 0x70, 0x6c };
    private static byte[] PSH_ACK_PACKET = {
	0x08, 0x00, 0x20, (byte)0x89, (byte)0xa5, (byte)0x9f, 0x00, 0x10,
	0x7b, 0x38, 0x46, 0x33, 0x08, 0x00, 0x45, 0x00, 0x00, 0x3e,
	(byte)0x87, 0x08, 0x40, 0x00, 0x3f, 0x06, 0x38, (byte)0xa2,
	(byte)0x87, 0x0d, (byte)0xd8, (byte)0xbf, (byte)0xac, 0x10, 0x70,
	0x32, 0x50, 0x49, 0x00, 0x19, (byte)0x9f, 0x3a, (byte)0xb4, 0x03,
	0x78, (byte)0xbe, (byte)0xe0, (byte)0xf8, 0x50, 0x18, 0x7d, 0x78,
	(byte)0x86, (byte)0xf0, 0x00, 0x00, 0x45, 0x48, 0x4c, 0x4f, 0x20,
	0x61, 0x6c, 0x70, 0x68, 0x61, 0x2e, 0x61, 0x70, 0x70, 0x6c, 0x65,
	0x2e, 0x65, 0x64, 0x75, 0x0d, 0x0a };

    public void setUp () throws Exception {
	// get link layer length
	int linkLayerLen = LinkLayer.getLinkLayerLength (LinkLayers.EN10MB);
	// create syn-ack packet
	_synAck = new TCPPacket (linkLayerLen,SYN_ACK_PACKET);
	// create psh-ack packet
	_pshAck = new TCPPacket (linkLayerLen,PSH_ACK_PACKET);
	// create packet with random garbage
	byte[] badBytes = new byte[SYN_ACK_PACKET.length];
	(new Random ()).nextBytes (badBytes);
	_baddie = new TCPPacket (linkLayerLen,badBytes);
    }

    public void tearDown () throws Exception {
    }

    public void testSynAckPacketHeaderLengths () {
	assertEquals (24,_synAck.getTCPHeaderLength ());
 	assertEquals (24,_synAck.getTCPHeader ().length);
	assertEquals (24,_synAck.getHeaderLength ());
 	assertEquals (24,_synAck.getHeader ().length);
    }

    public void testPshAckPacketHeaderLengths () {
	assertEquals (20,_pshAck.getTCPHeaderLength ());
 	assertEquals (20,_pshAck.getTCPHeader ().length);
	assertEquals (20,_pshAck.getHeaderLength ());
 	assertEquals (20,_pshAck.getHeader ().length);
    }

    public void testSynAckPacketDataLengths () {
	assertEquals (0,_synAck.getTCPData ().length);
	assertEquals (0,_synAck.getData ().length);
    }

    public void testPshAckPacketDataLengths () {
	assertEquals (22,_pshAck.getTCPData ().length);
	assertEquals (22,_pshAck.getData ().length);
    }

    public void testSynAckPacketPorts () {
	assertEquals (25,_synAck.getSourcePort ());
	assertEquals (20553,_synAck.getDestinationPort ());
    }

    public void testPshAckPacketAddresses () {
	assertEquals (20553,_pshAck.getSourcePort ());
	assertEquals (25,_pshAck.getDestinationPort ());
    }

    public void testSynAckPacketHeaderValues () {
 	assertEquals (2025775271L,_synAck.getSequenceNumber ());
 	assertEquals (2671424515L,_synAck.getAcknowledgmentNumber ());
 	assertEquals (8760,_synAck.getWindowSize ());
 	assertEquals (0xfcc7,_synAck.getTCPChecksum ());
 	assertEquals (0xfcc7,_synAck.getChecksum ());
//   	assertTrue   ("Packet should checksum",_synAck.isValidChecksum ());
 	assertEquals (0,_synAck.getUrgentPointer ());
	assertTrue   (!_synAck.isUrg ());
	assertTrue   ( _synAck.isAck ());
	assertTrue   (!_synAck.isPsh ());
	assertTrue   (!_synAck.isRst ());
	assertTrue   ( _synAck.isSyn ());
	assertTrue   (!_synAck.isFin ());
    }

    public void testPshAckPacketHeaderValues () {
 	assertEquals (2671424515L,_pshAck.getSequenceNumber ());
 	assertEquals (2025775352L,_pshAck.getAcknowledgmentNumber ());
 	assertEquals (32120,_pshAck.getWindowSize ());
 	assertEquals (0x86f0,_pshAck.getTCPChecksum ());
 	assertEquals (0x86f0,_pshAck.getChecksum ());
//   	assertTrue   ("Packet should checksum",_pshAck.isValidChecksum ());
 	assertEquals (0,_pshAck.getUrgentPointer ());
	assertTrue   (!_pshAck.isUrg ());
	assertTrue   ( _pshAck.isAck ());
	assertTrue   ( _pshAck.isPsh ());
	assertTrue   (!_pshAck.isRst ());
	assertTrue   (!_pshAck.isSyn ());
	assertTrue   (!_pshAck.isFin ());
    }

    public void testBadPacketHeaderLengths () {
	// really just make sure this doesn't crash the thing
 	assertTrue ("Bad read of TCP header for random data",
		    (_baddie.getTCPHeader ().length>=0));
 	assertTrue ("Bad read of TCP header for random data",
		    (_baddie.getHeader ().length>=0));
    }

    public void testBadPacketDataLengths () {
	// really just make sure this doesn't crash the thing
 	assertTrue ("Bad read of TCP data (payload) for random data",
		    (_baddie.getTCPData ().length>=0));
 	assertTrue ("Bad read of TCP data (payload) for random data",
		    (_baddie.getData ().length>=0));
    }

}
