// $Id: IPPacketTest.java,v 1.1 2002/07/10 23:12:09 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;

import java.util.Random;
import junit.framework.*;
import net.sourceforge.jpcap.net.IPPacket;
import net.sourceforge.jpcap.net.IPProtocols;
import net.sourceforge.jpcap.net.IPVersions;
import net.sourceforge.jpcap.net.LinkLayer;
import net.sourceforge.jpcap.net.LinkLayers;

public class IPPacketTest extends TestCase {

    // yes, I realize that as IP packets, these are not SYN-ACK and 
    // PSH-ACKs, but I use the same shell for testing TCPPacket
    private IPPacket _synAck;
    private IPPacket _pshAck;
    // and bad is always bad
    private IPPacket _baddie;

    public IPPacketTest (String testName) {
        super(testName);
    }        
    
    public static void main(java.lang.String[] args) {
	//junit.swingui.TestRunner.main (new String[] {IPPacketTest.class.getName ()});
        junit.textui.TestRunner.run (suite ());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite (IPPacketTest.class);
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
	_synAck = new IPPacket (linkLayerLen,SYN_ACK_PACKET);
	// create psh-ack packet
	_pshAck = new IPPacket (linkLayerLen,PSH_ACK_PACKET);
	// create packet with random garbage
	byte[] badBytes = new byte[SYN_ACK_PACKET.length];
	(new Random ()).nextBytes (badBytes);
	_baddie = new IPPacket (linkLayerLen,badBytes);
    }

    public void tearDown () throws Exception {
    }

    public void testSynAckPacketHeaderLengths () {
	assertEquals (20,_synAck.getIPHeaderLength ());
 	assertEquals (20,_synAck.getIPHeader ().length);
	assertEquals (20,_synAck.getHeaderLength ());
 	assertEquals (20,_synAck.getHeader ().length);
    }

    public void testPshAckPacketHeaderLengths () {
	assertEquals (20,_pshAck.getIPHeaderLength ());
 	assertEquals (20,_pshAck.getIPHeader ().length);
	assertEquals (20,_pshAck.getHeaderLength ());
 	assertEquals (20,_pshAck.getHeader ().length);
    }

    public void testSynAckPacketDataLengths () {
	assertEquals (24,_synAck.getIPData ().length);
	assertEquals (24,_synAck.getData ().length);
    }

    public void testPshAckPacketDataLengths () {
	assertEquals (42,_pshAck.getIPData ().length);
	assertEquals (42,_pshAck.getData ().length);
    }

    public void testSynAckPacketAddresses () {
	assertEquals ("172.16.112.50",_synAck.getSourceAddress ());
	assertEquals ("135.13.216.191",_synAck.getDestinationAddress ());
	assertEquals (2886758450L,_synAck.getSourceAddressAsLong ());
	assertEquals (2265831615L,_synAck.getDestinationAddressAsLong ());
	byte[] srcAdd = _synAck.getSourceAddressBytes ();
	assertTrue ("Source address as byte array does not match, bytes are: "+
		    noNeg (srcAdd[0])+"."+noNeg (srcAdd[1])+"."+
		    noNeg (srcAdd[2])+"."+noNeg (srcAdd[3]),
		    ((srcAdd[0]==(byte)172)&&(srcAdd[1]==(byte)16)&&
		     (srcAdd[2]==(byte)112)&&(srcAdd[3]==(byte)50)));
	byte[] dstAdd = _synAck.getDestinationAddressBytes ();
	assertTrue ("Dest address as byte array does not match, bytes are: "+
		    noNeg (dstAdd[0])+"."+noNeg (dstAdd[1])+"."+
		    noNeg (dstAdd[2])+"."+noNeg (dstAdd[3]),
		    ((dstAdd[0]==(byte)135)&&(dstAdd[1]==(byte)13)&&
		     (dstAdd[2]==(byte)216)&&(dstAdd[3]==(byte)191)));
    }

    public void testPshAckPacketAddresses () {
	assertEquals ("135.13.216.191",_pshAck.getSourceAddress ());
	assertEquals ("172.16.112.50",_pshAck.getDestinationAddress ());
    }

    private int noNeg (byte b) {
	return 0 | (b & 0xff);
    }


    public void testSynAckPacketHeaderValues () {
 	assertEquals (IPProtocols.TCP,_synAck.getIPProtocol ());
 	assertEquals (IPProtocols.TCP,_synAck.getProtocol ());
 	assertEquals ("IP Checksum mismatch, should be 0x6c38, but is "+
                      Integer.toHexString(_synAck.getIPChecksum ()),
                      0x6c38,_synAck.getIPChecksum ());
 	assertEquals ("(IP) Checksum mismatch, should be 0x6c38, but is "+
                      Integer.toHexString(_synAck.getChecksum ()),
                      0x6c38,_synAck.getChecksum ());
        IPPacket.TestProbe probe = _synAck.new TestProbe();
   	assertTrue   ("Computed IP checksum mismatch, should be "+
                      Integer.toHexString(_synAck.getIPChecksum())+", but is "+
                      Integer.toHexString(probe.getComputedSenderIPChecksum())+
                      ", ("+
                      Integer.toHexString(probe.getComputedReceiverIPChecksum())+
                      ")", _synAck.isValidChecksum ());
 	assertEquals ("Version mismatch, should be "+IPVersions.IPV4+
                      ", but is "+_synAck.getVersion (),
                      IPVersions.IPV4,_synAck.getVersion ());
 	assertEquals ("TOS incorrect, should be 0, but is "+
                      _synAck.getTypeOfService (),
                      0,_synAck.getTypeOfService ());
 	assertEquals ("Length incorrect, should be 44, but is "+
                      _synAck.getLength(),44,_synAck.getLength ());
 	assertEquals ("ID incorrect, should be 0x9383, but is "+
                      _synAck.getId(),0x9383,_synAck.getId ());
 	assertEquals ("Fragment flags incorrect, should be 0, but are "+
                      _synAck.getFragmentFlags(),
                      2,_synAck.getFragmentFlags());
 	assertEquals ("Fragment offset incorrect, should be 0, but is "+
                      _synAck.getFragmentOffset(),
                      0,_synAck.getFragmentOffset());
 	assertEquals ("Time-to-live incorrect, should be 255, but is "+
                      _synAck.getTimeToLive(),
                      255,_synAck.getTimeToLive());
    }

    public void testPshAckPacketHeaderValues() {
 	assertEquals (IPProtocols.TCP,_pshAck.getIPProtocol ());
 	assertEquals (IPProtocols.TCP,_pshAck.getProtocol ());
 	assertEquals ("IP Checksum mismatch, should be 0x38a2, but is "+
                      Integer.toHexString(_pshAck.getIPChecksum ()),
                      0x38a2,_pshAck.getIPChecksum ());
 	assertEquals ("(IP) Checksum mismatch, should be 0x38a2, but is "+
                      Integer.toHexString(_pshAck.getChecksum ()),
                      0x38a2,_pshAck.getChecksum ());
        IPPacket.TestProbe probe = _pshAck.new TestProbe();
   	assertTrue   ("Computed IP checksum mismatch, should be "+
                      Integer.toHexString(_pshAck.getIPChecksum())+", but is "+
                      Integer.toHexString(probe.getComputedSenderIPChecksum())+
                      ", ("+
                      Integer.toHexString(probe.getComputedReceiverIPChecksum())+
                      ")", _pshAck.isValidChecksum ());
 	assertEquals ("Version mismatch, should be "+IPVersions.IPV4+
                      ", but is "+_pshAck.getVersion (),
                      IPVersions.IPV4,_pshAck.getVersion ());
 	assertEquals ("TOS incorrect, should be 0, but is "+
                      _pshAck.getTypeOfService (),
                      0,_pshAck.getTypeOfService ());
 	assertEquals ("Length incorrect, should be 62, but is "+
                      _pshAck.getLength(),62,_pshAck.getLength ());
 	assertEquals ("ID incorrect, should be 0x8708, but is "+
                      _pshAck.getId(),0x8708,_pshAck.getId ());
 	assertEquals ("Fragment flags incorrect, should be 0, but are "+
                      _pshAck.getFragmentFlags(),
                      2,_pshAck.getFragmentFlags());
 	assertEquals ("Fragment offset incorrect, should be 0, but is "+
                      _pshAck.getFragmentOffset(),
                      0,_pshAck.getFragmentOffset());
 	assertEquals ("Time-to-live incorrect, should be 63, but is "+
                      _pshAck.getTimeToLive(),
                      63,_pshAck.getTimeToLive());
    }

    public void testBadPacketHeaderLengths () {
	// really just make sure this doesn't crash the thing
 	assertTrue ("Bad read of IP header for random data",
		    (_baddie.getIPHeader ().length>=0));
 	assertTrue ("Bad read of IP header for random data",
		    (_baddie.getHeader ().length>=0));
    }

    public void testBadPacketDataLengths () {
	// really just make sure this doesn't crash the thing
 	assertTrue ("Bad read of IP data (payload) for random data",
		    (_baddie.getIPData ().length>=0));
 	assertTrue ("Bad read of IP data (payload) for random data",
		    (_baddie.getData ().length>=0));
    }

}
