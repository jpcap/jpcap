// $Id: ICMPMessages.java,v 1.5 2004/02/24 19:21:30 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;


/**
 * Code constants for ICMP message types.
 *
 * Taken originally from tcpdump/print-icmp.c
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.5 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/02/24 19:21:30 $
 */
public interface ICMPMessages
{
  /**
   * Echo reply.
   */
  int ECHO_REPLY = 0x0000;

  /**
   * Destination network unreachable.
   */
  int UNREACH_NET = 0x0300;

  /**
   * Destination host unreachable.
   */
  int UNREACH_HOST = 0x0301;

  /**
   * Bad protocol.
   */
  int UNREACH_PROTOCOL = 0x0302;

  /**
   * Bad port.
   */
  int UNREACH_PORT = 0x0303;

  /**
   * IP_DF caused drop.
   */
  int UNREACH_NEEDFRAG = 0x0304;

  /**
   * Src route failed.
   */
  int UNREACH_SRCFAIL = 0x0305;

  /**
   * Unknown network.
   */
  int UNREACH_NET_UNKNOWN = 0x0306;

  /**
   * Unknown host.
   */
  int UNREACH_HOST_UNKNOWN = 0x0307;

  /**
   * Src host isolated.
   */
  int UNREACH_ISOLATED = 0x0308;

  /**
   * Network access prohibited.
   */
  int UNREACH_NET_PROHIB = 0x0309;

  /**
   * Host access prohibited.
   */
  int UNREACH_HOST_PROHIB = 0x030a;

  /**
   * Bad TOS for net.
   */
  int UNREACH_TOSNET = 0x030b;

  /**
   * Bad TOS for host.
   */
  int UNREACH_TOSHOST = 0x030c;

  /**
   * Packet lost, slow down.
   */
  int SOURCE_QUENCH = 0x0400;

  /**
   * Shorter route to network.
   */
  int REDIRECT_NET = 0x0500;

  /**
   * Shorter route to host.
   */
  int REDIRECT_HOST = 0x0501;

  /**
   * Shorter route for TOS and network.
   */
  int REDIRECT_TOSNET = 0x0502;

  /**
   * Shorter route for TOS and host.
   */
  int REDIRECT_TOSHOST = 0x0503;

  /**
   * Echo request.
   */
  int ECHO = 0x0800;

  /**
   * router advertisement
   */
  int ROUTER_ADVERT = 0x0900;

  /**
   * router solicitation
   */
  int ROUTER_SOLICIT = 0x0a00;

  /**
   * time exceeded in transit.
   */  
  int TIME_EXCEED_INTRANS = 0x0b00;

  /**
   * time exceeded in reass.
   */  
  int TIME_EXCEED_REASS = 0x0b01;

  /**
   * ip header bad; option absent.
   */
  int PARAM_PROB = 0x0c01;

  /**
   * timestamp request 
   */
  int TSTAMP = 0x0d00;

  /**
   * timestamp reply 
   */
  int TSTAMP_REPLY = 0x0e00;

  /**
   * information request 
   */
  int IREQ = 0x0f00;

  /**
   * information reply 
   */
  int IREQ_REPLY = 0x1000;

  /**
   * address mask request 
   */
  int MASK_REQ = 0x1100;

  /**
   * address mask reply 
   */
  int MASK_REPLY = 0x1200;


  // marker indicating index of largest ICMP message type code
  int LAST_MAJOR_CODE = 0x12;
}
