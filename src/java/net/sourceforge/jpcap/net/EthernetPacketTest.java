// $Id: EthernetPacketTest.java,v 1.1 2002/07/10 23:12:09 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;

import java.util.Random;

import junit.framework.*;

import net.sourceforge.jpcap.net.EthernetPacket;
import net.sourceforge.jpcap.net.EthernetProtocols;
import net.sourceforge.jpcap.net.LinkLayer;
import net.sourceforge.jpcap.net.LinkLayers;
import net.sourceforge.jpcap.simulator.PacketGenerator;

public class EthernetPacketTest extends TestCase {

    private EthernetPacket _goodie;
    private EthernetPacket _baddie;

    private static final int IP_PROTOCOL = 0x0800;
    
    public EthernetPacketTest (String testName) {
        super(testName);
    }        
    
    public static void main(java.lang.String[] args) {
	//junit.swingui.TestRunner.main (new String[] {EthernetPacketTest.class.getName ()});
        junit.textui.TestRunner.run (suite ());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite (EthernetPacketTest.class);
        return suite;
    }

    private static byte[] GOOD_PACKET = {
	0x00, 0x04, 0x76, (byte)0xba, (byte)0x86, 0x19, 0x01, 0x02, 0x03,
	0x04, 0x05, 0x06, 0x08, 0x00, 0x45, 0x00, 0x00, 0x2c, 0x04, 0x45,
	0x20, 0x00, 0x40, 0x06, (byte)0xc2, 0x56, 0x0a, 0x32, 0x01, 0x52,
	(byte)0xc0, (byte)0xa8, (byte)0xc8, 0x04, 0x04, 0x4b, 0x00, 0x19,
	(byte)0x83, (byte)0xbd, 0x76, 0x5c, 0x7a, (byte)0xc0, 0x7f,
	(byte)0xbd, (byte)0x80, 0x11, 0x19, 0x20, (byte)0xd6, (byte)0xde,
	0x00, 0x00, 0x01, 0x01, 0x08, 0x0a, 0x01, 0x17, 0x75, (byte)0x84,
	0x01, (byte)0xb9, (byte)0x81, 0x3c };

    public void setUp () throws Exception {
	int linkLayerLen = LinkLayer.getLinkLayerLength (LinkLayers.EN10MB);
	_goodie = new EthernetPacket (linkLayerLen,GOOD_PACKET);
	byte[] badBytes = new byte[GOOD_PACKET.length];
	(new Random ()).nextBytes (badBytes);
	_baddie = new EthernetPacket (linkLayerLen,badBytes);
    }

    public void tearDown () throws Exception {
    }

    public void testGoodPacketHeaderLengths () {
	assertEquals (14,_goodie.getEthernetHeaderLength ());
 	assertEquals (14,_goodie.getEthernetHeader ().length);
	assertEquals (14,_goodie.getHeaderLength ());
 	assertEquals (14,_goodie.getHeader ().length);
    }

    public void testGoodPacketDataLengths () {
	assertEquals (52,_goodie.getEthernetData ().length);
	assertEquals (52,_goodie.getData ().length);
    }

    public void testGoodPacketAddresses () {
	assertEquals ("01:02:03:04:05:06",_goodie.getSourceHwAddress ());
	assertEquals ("00:04:76:ba:86:19",_goodie.getDestinationHwAddress ());
    }

    public void testGoodPacketProtocol () {
	assertEquals (EthernetProtocols.IP,_goodie.getEthernetProtocol ());
	assertEquals (EthernetProtocols.IP,_goodie.getProtocol ());
    }

    public void testBadPacketHeaderLengths () {
	assertEquals (14,_baddie.getEthernetHeaderLength ());
 	assertEquals (14,_baddie.getEthernetHeader ().length);
    }

    public void testBadPacketDataLengths () {
	assertEquals (52,_baddie.getEthernetData ().length);
	assertEquals (52,_baddie.getData ().length);
    }

}
