// $Id: IPProtocol.java,v 1.6 2004/10/02 01:23:19 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;

import java.util.HashMap;


/**
 * IPProtocol utility class.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.6 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/10/02 01:23:19 $
 */
public class IPProtocol implements IPProtocols
{
  /**
   * Fetch a protocol description.
   * @param code the code associated with the message.
   * @return a message describing the significance of the IP protocol.
   */
  public static String getDescription(int code) {
    Integer c = new Integer(code);
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
    messages.put(new Integer(IP), "Dummy protocol for TCP"); 
    messages.put(new Integer(HOPOPTS), "IPv6 Hop-by-Hop options"); 
    messages.put(new Integer(ICMP), "Internet Control Message Protocol"); 
    messages.put(new Integer(IGMP), "Internet Group Management Protocol");
    messages.put(new Integer(IPIP), "IPIP tunnels"); 
    messages.put(new Integer(TCP), "Transmission Control Protocol"); 
    messages.put(new Integer(EGP), "Exterior Gateway Protocol"); 
    messages.put(new Integer(PUP), "PUP protocol"); 
    messages.put(new Integer(UDP), "User Datagram Protocol"); 
    messages.put(new Integer(IDP), "XNS IDP protocol"); 
    messages.put(new Integer(TP), "SO Transport Protocol Class 4"); 
    messages.put(new Integer(IPV6), "IPv6 header"); 
    messages.put(new Integer(ROUTING), "IPv6 routing header"); 
    messages.put(new Integer(FRAGMENT), "IPv6 fragmentation header"); 
    messages.put(new Integer(RSVP), "Reservation Protocol"); 
    messages.put(new Integer(GRE), "General Routing Encapsulation"); 
    messages.put(new Integer(ESP), "encapsulating security payload"); 
    messages.put(new Integer(AH), "authentication header"); 
    messages.put(new Integer(ICMPV6), "ICMPv6"); 
    messages.put(new Integer(NONE), "IPv6 no next header"); 
    messages.put(new Integer(DSTOPTS), "IPv6 destination options"); 
    messages.put(new Integer(MTP), "Multicast Transport Protocol"); 
    messages.put(new Integer(ENCAP), "Encapsulation Header"); 
    messages.put(new Integer(PIM), "Protocol Independent Multicast"); 
    messages.put(new Integer(COMP), "Compression Header Protocol"); 
    messages.put(new Integer(RAW), "Raw IP Packet"); 
    messages.put(new Integer(INVALID), "INVALID IP"); 
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


  private String _rcsid = 
    "$Id: IPProtocol.java,v 1.6 2004/10/02 01:23:19 pcharles Exp $";
}
