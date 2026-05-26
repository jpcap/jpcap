/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.net;

import java.util.HashMap;

/**
 * IGMP message utility class.
 *
 */
public class IGMPMessage implements IGMPMessages
{
  /**
   * Fetch an IGMP message.
   * @param code the code associated with the message.
   * @return a message describing the significance of the IGMP code.
   */
  public static String getDescription(int code) {
    Integer c = new Integer(code);
    if(messages.containsKey(c)) 
      return (String)messages.get(c);
    else 
      return "unknown";
  }

  /**
   * 'Human-readable' IGMP messages.
   */
  //jdk1.5: private static HashMap <Integer, String> messages = new HashMap<Integer, String>();
  private static HashMap messages = new HashMap();
  static {
    messages.put(new Integer(LEAVE), "leave group");
    messages.put(new Integer(V1_REPORT), "v1 membership report");
    messages.put(new Integer(V2_REPORT), "v2 membership report");
    messages.put(new Integer(QUERY), "membership query");
  }
}

