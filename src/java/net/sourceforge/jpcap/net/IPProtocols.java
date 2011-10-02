// $Id: IPProtocols.java,v 1.4 2001/06/27 02:15:49 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;


/**
 * Code constants for well-defined IP protocols.
 * <p>
 * Taken from netinet/in.h
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.4 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/06/27 02:15:49 $
 */
public interface IPProtocols
{
  /**
   * Dummy protocol for TCP. 
   */
  int IP = 0;

  /**
   * IPv6 Hop-by-Hop options. 
   */
  int HOPOPTS = 0;

  /**
   * Internet Control Message Protocol. 
   */
  int ICMP = 1;

  /**
   * Internet Group Management Protocol.
   */
  int IGMP = 2;

  /**
   * IPIP tunnels (older KA9Q tunnels use 94). 
   */
  int IPIP = 4;

  /**
   * Transmission Control Protocol. 
   */
  int TCP = 6;

  /**
   * Exterior Gateway Protocol. 
   */
  int EGP = 8;

  /**
   * PUP protocol. 
   */
  int PUP = 12;

  /**
   * User Datagram Protocol. 
   */
  int UDP = 17;

  /**
   * XNS IDP protocol. 
   */
  int IDP = 22;

  /**
   * SO Transport Protocol Class 4. 
   */
  int TP = 29;

  /**
   * IPv6 header. 
   */
  int IPV6 = 41;

  /**
   * IPv6 routing header. 
   */
  int ROUTING = 43;

  /**
   * IPv6 fragmentation header. 
   */
  int FRAGMENT = 44;

  /**
   * Reservation Protocol. 
   */
  int RSVP = 46;

  /**
   * General Routing Encapsulation. 
   */
  int GRE = 47;

  /**
   * encapsulating security payload. 
   */
  int ESP = 50;

  /**
   * authentication header. 
   */
  int AH = 51;

  /**
   * ICMPv6. 
   */
  int ICMPV6 = 58;

  /**
   * IPv6 no next header. 
   */
  int NONE = 59;

  /**
   * IPv6 destination options. 
   */
  int DSTOPTS = 60;

  /**
   * Multicast Transport Protocol. 
   */
  int MTP = 92;

  /**
   * Encapsulation Header. 
   */
  int ENCAP = 98;

  /**
   * Protocol Independent Multicast. 
   */
  int PIM = 103;

  /**
   * Compression Header Protocol. 
   */
  int COMP = 108;

  /**
   * Raw IP packets. 
   */
  int RAW = 255;


  /**
   * Unrecognized IP protocol.
   * WARNING: this only works because the int storage for the protocol
   * code has more bits than the field in the IP header where it is stored.
   */
  int INVALID = -1;

  /**
   * IP protocol mask.
   */
  int MASK = 0xff;
}
