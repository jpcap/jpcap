// $Id: EthernetProtocols.java,v 1.7 2003/07/09 03:04:24 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;


/**
 * Code constants for well-defined ethernet protocols.
 * <p>
 * Taken from linux/if_ether.h and tcpdump/ethertype.h
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.7 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2003/07/09 03:04:24 $
 */
public interface EthernetProtocols
{
  /**
   * IP protocol.
   */
  int IP = 0x0800;

  /**
   * Address resolution protocol.
   */
  int ARP = 0x0806;

  /**
   * Reverse address resolution protocol.
   */
  int RARP = 0x8035;


  /**
   * Ethernet Loopback packet 
   */
  int LOOP = 0x0060;

  /**
   * Ethernet Echo packet		
   */
  int ECHO = 0x0200;

  /**
   * Xerox PUP packet
   */
  int PUP = 0x0400;

  /**
   * CCITT X.25			
   */
  int X25 = 0x0805;

  /**
   * G8BPQ AX.25 Ethernet Packet	[ NOT AN OFFICIALLY REGISTERED ID ] 
   */
  int BPQ = 0x08FF;

  /**
   * DEC Assigned proto
   */
  int DEC = 0x6000;

  /**
   * DEC DNA Dump/Load
   */
  int DNA_DL = 0x6001;
  
  /**
   * DEC DNA Remote Console
   */
  int DNA_RC = 0x6002;

  /**
   * DEC DNA Routing
   */
  int DNA_RT = 0x6003;

  /**
   * DEC LAT
   */
  int LAT = 0x6004;

  /**
   * DEC Diagnostics
   */
  int DIAG = 0x6005;

  /**
   * DEC Customer use
   */
  int CUST = 0x6006;

  /**
   * DEC Systems Comms Arch
   */
  int SCA = 0x6007;

  /**
   * Appletalk DDP 
   */
  int ATALK = 0x809B;

  /**
   * Appletalk AARP
   */
  int AARP = 0x80F3;

  /**
   * IPX over DIX
   */
  int IPX = 0x8137;

  /**
   * IPv6 over bluebook
   */
  int IPV6 = 0x86DD;


  // Non DIX types. Won't clash for 1500 types.
 
  /**
   * Dummy type for 802.3 frames  
   */
  int N802_3 = 0x0001;

  /**
   * Dummy protocol id for AX.25  
   */
  int AX25 = 0x0002;

  /**
   * Every packet.
   */
  int ALL = 0x0003;

  /**
   * 802.2 frames
   */
  int N802_2 = 0x0004;

  /**
   * Internal only
   */
  int SNAP = 0x0005;

  /**
   * DEC DDCMP: Internal only
   */
  int DDCMP = 0x0006;

  /**
   * Dummy type for WAN PPP frames
   */
  int WAN_PPP = 0x0007;

  /**
   * Dummy type for PPP MP frames 
   */
  int PPP_MP = 0x0008;

  /**
   * Localtalk pseudo type 
   */
  int LOCALTALK = 0x0009;

  /**
   * Dummy type for Atalk over PPP
   */
  int PPPTALK = 0x0010;

  /**
   * 802.2 frames
   */
  int TR_802_2 = 0x0011;

  /**
   * Mobitex (kaz@cafe.net)
   */
  int MOBITEX = 0x0015;

  /**
   * Card specific control frames
   */
  int CONTROL = 0x0016;

  /**
   * Linux/IR
   */
  int IRDA = 0x0017;


  // others not yet documented..

  int NS = 0x0600;

  int SPRITE = 0x0500;

  int TRAIL = 0x1000;

  int LANBRIDGE = 0x8038;

  int DECDNS = 0x803c;

  int DECDTS = 0x803e;

  int VEXP = 0x805b;

  int VPROD = 0x805c;

  int N8021Q = 0x8100;

  int PPP = 0x880b;

  int PPPOED = 0x8863;

  int PPPOES = 0x8864;

  int LOOPBACK = 0x9000;


  // these aren't valid ETHERNET codes, but show up in the type field.

  // spanning tree bridge protocol
  int STBPDU = 0x0026;

  // intel adapter fault tolerance heartbeats
  int INFTH = 0x886d;


  /**
   * Ethernet protocol mask.
   */
  int MASK = 0xffff;
}
