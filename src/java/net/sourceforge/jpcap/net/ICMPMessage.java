// $Id: ICMPMessage.java,v 1.5 2004/10/02 01:23:19 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;

import java.util.HashMap;


/**
 * ICMP message utility class.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.5 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/10/02 01:23:19 $
 */
public class ICMPMessage implements ICMPMessages
{
  /**
   * Fetch an ICMP message.
   * @param code the code associated with the message.
   * @return a message describing the significance of the ICMP code.
   */
  public static String getDescription(int code) {
    Integer c = new Integer(code);
    if(messages.containsKey(c)) 
      return (String)messages.get(c);
    else 
      return "unknown";
  }

  /**
   * 'Human-readable' ICMP messages.
   */
  //jdk1.5: private static HashMap <Integer, String> messages = new HashMap<Integer, String>();
  private static HashMap messages = new HashMap();
  static {
    messages.put(new Integer(ECHO_REPLY), "echo reply");
    messages.put(new Integer(ECHO), "echo request");
    messages.put(new Integer(UNREACH_NET), "net unreachable");
    messages.put(new Integer(UNREACH_HOST), "host unreachable");
    messages.put(new Integer(UNREACH_PROTOCOL), "bad protocol");
    messages.put(new Integer(UNREACH_PORT), "port unreachable");
    messages.put(new Integer(UNREACH_NEEDFRAG), "ip_df drop");
    messages.put(new Integer(UNREACH_SRCFAIL), "source route failed");
    messages.put(new Integer(UNREACH_NET_UNKNOWN), "unknown network");
    messages.put(new Integer(UNREACH_HOST_UNKNOWN), "unknown host");
    messages.put(new Integer(UNREACH_ISOLATED), "source host isolated");
    messages.put(new Integer(UNREACH_NET_PROHIB), "net access prohibited");
    messages.put(new Integer(UNREACH_HOST_PROHIB), "host access prohibited");
    messages.put(new Integer(UNREACH_TOSNET), "tos for net invalid");
    messages.put(new Integer(UNREACH_TOSHOST), "tos for host invalid");
    messages.put(new Integer(SOURCE_QUENCH), "packet lost");
    messages.put(new Integer(REDIRECT_NET), "redirect to network");
    messages.put(new Integer(REDIRECT_HOST), "redirect to host");
    messages.put(new Integer(REDIRECT_TOSNET), "tos redirect to network");
    messages.put(new Integer(REDIRECT_TOSHOST), "tos redirect to host");
    messages.put(new Integer(ROUTER_ADVERT), "router advert");
    messages.put(new Integer(ROUTER_SOLICIT), "router solicit");
    messages.put(new Integer(TIME_EXCEED_INTRANS), "transit time exceeded");
    messages.put(new Integer(TIME_EXCEED_REASS), "reass time exceeded");
    messages.put(new Integer(PARAM_PROB), "bad ip header");
    messages.put(new Integer(TSTAMP), "timestamp request");
    messages.put(new Integer(TSTAMP_REPLY), "timestamp reply");
    messages.put(new Integer(IREQ), "information request");
    messages.put(new Integer(IREQ_REPLY), "information reply");
    messages.put(new Integer(MASK_REQ), "address mask request");
    messages.put(new Integer(MASK_REPLY), "address mask reply");
  }


  private String _rcsid = 
    "$Id: ICMPMessage.java,v 1.5 2004/10/02 01:23:19 pcharles Exp $";
}

