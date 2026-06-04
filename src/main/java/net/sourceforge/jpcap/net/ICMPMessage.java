/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.net;

import java.util.HashMap;

/**
 * ICMP message utility class.
 *
 */
public class ICMPMessage implements ICMPMessages
{
  /**
   * Fetch an ICMP message.
   * @param code the code associated with the message.
   * @return a message describing the significance of the ICMP code.
   */
  public static String getDescription(int code) {
    Integer c = Integer.valueOf(code);
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
    messages.put(Integer.valueOf(ECHO_REPLY), "echo reply");
    messages.put(Integer.valueOf(ECHO), "echo request");
    messages.put(Integer.valueOf(UNREACH_NET), "net unreachable");
    messages.put(Integer.valueOf(UNREACH_HOST), "host unreachable");
    messages.put(Integer.valueOf(UNREACH_PROTOCOL), "bad protocol");
    messages.put(Integer.valueOf(UNREACH_PORT), "port unreachable");
    messages.put(Integer.valueOf(UNREACH_NEEDFRAG), "ip_df drop");
    messages.put(Integer.valueOf(UNREACH_SRCFAIL), "source route failed");
    messages.put(Integer.valueOf(UNREACH_NET_UNKNOWN), "unknown network");
    messages.put(Integer.valueOf(UNREACH_HOST_UNKNOWN), "unknown host");
    messages.put(Integer.valueOf(UNREACH_ISOLATED), "source host isolated");
    messages.put(Integer.valueOf(UNREACH_NET_PROHIB), "net access prohibited");
    messages.put(Integer.valueOf(UNREACH_HOST_PROHIB), "host access prohibited");
    messages.put(Integer.valueOf(UNREACH_TOSNET), "tos for net invalid");
    messages.put(Integer.valueOf(UNREACH_TOSHOST), "tos for host invalid");
    messages.put(Integer.valueOf(SOURCE_QUENCH), "packet lost");
    messages.put(Integer.valueOf(REDIRECT_NET), "redirect to network");
    messages.put(Integer.valueOf(REDIRECT_HOST), "redirect to host");
    messages.put(Integer.valueOf(REDIRECT_TOSNET), "tos redirect to network");
    messages.put(Integer.valueOf(REDIRECT_TOSHOST), "tos redirect to host");
    messages.put(Integer.valueOf(ROUTER_ADVERT), "router advert");
    messages.put(Integer.valueOf(ROUTER_SOLICIT), "router solicit");
    messages.put(Integer.valueOf(TIME_EXCEED_INTRANS), "transit time exceeded");
    messages.put(Integer.valueOf(TIME_EXCEED_REASS), "reass time exceeded");
    messages.put(Integer.valueOf(PARAM_PROB), "bad ip header");
    messages.put(Integer.valueOf(TSTAMP), "timestamp request");
    messages.put(Integer.valueOf(TSTAMP_REPLY), "timestamp reply");
    messages.put(Integer.valueOf(IREQ), "information request");
    messages.put(Integer.valueOf(IREQ_REPLY), "information reply");
    messages.put(Integer.valueOf(MASK_REQ), "address mask request");
    messages.put(Integer.valueOf(MASK_REPLY), "address mask reply");
  }
}

