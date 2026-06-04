/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.net;

import java.util.HashMap;

/**
 * IPProtocol utility class.
 *
 */
public class IPProtocol implements IPProtocols
{
  /**
   * Fetch a protocol description.
   * @param code the code associated with the message.
   * @return a message describing the significance of the IP protocol.
   */
  public static String getDescription(int code) {
    Integer c = Integer.valueOf(code);
    if(messages.containsKey(c)) 
      return (String)messages.get(c);
    else 
      return "unknown";
  }

  /**
   * 'Human-readable' IP protocol descriptions.
   */
  //jdk1.5: private static HashMap <Integer, String> messages = new HashMap<Integer, String>();
  private static HashMap messages = new HashMap();
  static {
    messages.put(Integer.valueOf(IP), "Dummy protocol for TCP"); 
    messages.put(Integer.valueOf(HOPOPTS), "IPv6 Hop-by-Hop options"); 
    messages.put(Integer.valueOf(ICMP), "Internet Control Message Protocol"); 
    messages.put(Integer.valueOf(IGMP), "Internet Group Management Protocol");
    messages.put(Integer.valueOf(IPIP), "IPIP tunnels"); 
    messages.put(Integer.valueOf(TCP), "Transmission Control Protocol"); 
    messages.put(Integer.valueOf(EGP), "Exterior Gateway Protocol"); 
    messages.put(Integer.valueOf(PUP), "PUP protocol"); 
    messages.put(Integer.valueOf(UDP), "User Datagram Protocol"); 
    messages.put(Integer.valueOf(IDP), "XNS IDP protocol"); 
    messages.put(Integer.valueOf(TP), "SO Transport Protocol Class 4"); 
    messages.put(Integer.valueOf(IPV6), "IPv6 header"); 
    messages.put(Integer.valueOf(ROUTING), "IPv6 routing header"); 
    messages.put(Integer.valueOf(FRAGMENT), "IPv6 fragmentation header"); 
    messages.put(Integer.valueOf(RSVP), "Reservation Protocol"); 
    messages.put(Integer.valueOf(GRE), "General Routing Encapsulation"); 
    messages.put(Integer.valueOf(ESP), "encapsulating security payload"); 
    messages.put(Integer.valueOf(AH), "authentication header"); 
    messages.put(Integer.valueOf(ICMPV6), "ICMPv6"); 
    messages.put(Integer.valueOf(NONE), "IPv6 no next header"); 
    messages.put(Integer.valueOf(DSTOPTS), "IPv6 destination options"); 
    messages.put(Integer.valueOf(MTP), "Multicast Transport Protocol"); 
    messages.put(Integer.valueOf(ENCAP), "Encapsulation Header"); 
    messages.put(Integer.valueOf(PIM), "Protocol Independent Multicast"); 
    messages.put(Integer.valueOf(COMP), "Compression Header Protocol"); 
    messages.put(Integer.valueOf(RAW), "Raw IP Packet"); 
    messages.put(Integer.valueOf(INVALID), "INVALID IP"); 
  }

  /**
   * Extract the protocol code from packet data. The packet data 
   * must contain an IP datagram.
   * The protocol code specifies what kind of information is contained in the 
   * data block of the ip datagram.
   *
   * @param lLen the length of the link-level header.
   * @param packetBytes packet bytes, including the link-layer header.
   * @return the IP protocol code. i.e. 0x06 signifies TCP protocol.
   */
  public static int extractProtocol(int lLen, byte [] packetBytes) {
    return packetBytes[lLen + IPFields.IP_CODE_POS];
  }
}
