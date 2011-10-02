// $Id: HeaderGenerator.java,v 1.2 2001/06/27 02:17:33 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.simulator;

import net.sourceforge.jpcap.util.ArrayHelper;
import net.sourceforge.jpcap.net.MACAddress;
import net.sourceforge.jpcap.net.IPAddress;
import net.sourceforge.jpcap.net.EthernetProtocols;
import net.sourceforge.jpcap.net.EthernetFields;
import net.sourceforge.jpcap.net.ARPFields;
import net.sourceforge.jpcap.net.IPProtocols;
import net.sourceforge.jpcap.net.IPFields;
import net.sourceforge.jpcap.net.IPPorts;
import net.sourceforge.jpcap.net.UDPFields;
import net.sourceforge.jpcap.net.TCPFields;
import net.sourceforge.jpcap.net.ICMPFields;
import net.sourceforge.jpcap.net.ICMPMessages;


/**
 * This class generates random protocol headers.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.2 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/06/27 02:17:33 $
 */
public class HeaderGenerator
  implements EthernetFields, 
             ARPFields, IPFields, TCPFields, UDPFields, ICMPFields
{
  /**
   * Roll the dice.
   * @return whether or not a synthesized probabilistic event occurred.
   */
  private static boolean test(float probability) {
    double r = Math.random();
    return r < probability;
  }

  /**
   * Generate a pseudo-random ethernet protocol code.
   */
  public static int randomEthernetProtocol() {
    if(test(Settings.PROB_ETH_IP))
      return EthernetProtocols.IP;
    if(test(Settings.PROB_ETH_ARP))
      return EthernetProtocols.ARP;
    if(test(Settings.PROB_ETH_RARP))
      return EthernetProtocols.RARP;

    // other..
    return (int)(Math.random() * EthernetProtocols.MASK);
  }

  /**
   * Generate a pseudo-random IP protocol code.
   */
  public static int randomIPProtocol() {
    if(test(Settings.PROB_IP_TCP))
      return IPProtocols.TCP;
    if(test(Settings.PROB_IP_UDP))
      return IPProtocols.UDP;
    if(test(Settings.PROB_IP_ICMP))
      return IPProtocols.ICMP;

    // other..
    return (int)(Math.random() * IPProtocols.MASK);
  }

  /**
   * Generate a pseudo-random IP port.
   */
  public static int randomPort() {
    return (int)(Math.random() * IPPorts.MASK);
  }

  /**
   * Generate a pseudo-random well-known IP port.
   */
  public static int randomPrivilegedPort() {
    return (int)(Math.random() * IPPorts.LIMIT_PRIVILEGED);
  }

  /**
   * Generate a pseudo-random ICMP protocol code (message type).
   */
  public static int randomICMPType() {
    return (int)(Math.random() * ICMPMessages.LAST_MAJOR_CODE);
  }

  /**
   * Generate a pseudo-random ethernet header.
   */
  public static byte [] generateRandomEthernetHeader() {
    byte [] bytes = new byte[ETH_HEADER_LEN];

    long dst = MACAddress.random();
    ArrayHelper.insertLong(bytes, dst, ETH_DST_POS, MACAddress.WIDTH);

    long src = MACAddress.random();
    ArrayHelper.insertLong(bytes, src, ETH_SRC_POS, MACAddress.WIDTH);

    int type = randomEthernetProtocol();
    ArrayHelper.insertLong(bytes, type, ETH_CODE_POS, ETH_CODE_LEN);

    return bytes;
  }

  /**
   * Generate a pseudo-random ARP header.
   */
  public static byte [] generateRandomARPHeader() {
    byte [] bytes = new byte[ARP_HEADER_LEN];

    ArrayHelper.insertLong
      (bytes, ARP_ETH_ADDR_CODE, ARP_HW_TYPE_POS, ARP_ADDR_TYPE_LEN);
    ArrayHelper.insertLong
      (bytes, ARP_IP_ADDR_CODE, ARP_PR_TYPE_POS, ARP_ADDR_TYPE_LEN);
    ArrayHelper.insertLong
      (bytes, IPAddress.WIDTH, ARP_HW_LEN_POS, ARP_ADDR_SIZE_LEN);
    ArrayHelper.insertLong
      (bytes, MACAddress.WIDTH, ARP_PR_LEN_POS, ARP_ADDR_SIZE_LEN);
    ArrayHelper.insertLong
      (bytes, test(Settings.PROB_ARP_REQUEST) ? 
       ARP_OP_REQ_CODE : ARP_OP_REP_CODE, ARP_OP_POS, ARP_OP_LEN);
    ArrayHelper.insertLong
      (bytes, MACAddress.random(), ARP_S_HW_ADDR_POS, MACAddress.WIDTH);
    int srcAddress = IPAddress.random(Settings.SIM_NETWORK, 
                                      Settings.SIM_NETMASK);
    ArrayHelper.insertLong
      (bytes, srcAddress, ARP_S_PR_ADDR_POS, IPAddress.WIDTH);
    ArrayHelper.insertLong
      (bytes, MACAddress.random(), ARP_T_HW_ADDR_POS, MACAddress.WIDTH);

    int dstAddress = srcAddress;
    int count = 0;
    // cheezy way to make sure that the source and dest address aren't the same
    while(dstAddress == srcAddress && count++ < randomRetryCount) {
      dstAddress = IPAddress.random(Settings.SIM_NETWORK, 
                                    Settings.SIM_NETMASK);
    }
    ArrayHelper.insertLong
      (bytes, dstAddress, ARP_T_PR_ADDR_POS, IPAddress.WIDTH);

    return bytes;
  }

  private static int fakeId = 0;
  /**
   * Generate a pseudo-random IP header.
   */
  public static byte [] generateRandomIPHeader() {
    byte [] bytes = new byte[IP_HEADER_LEN];

    // ipv4. WARNING: only IPv4 is currently supported
    ArrayHelper.insertLong(bytes, 0x45, IP_VER_POS, IP_VER_LEN);

    // specify no special handling flag in type of service code
    ArrayHelper.insertLong(bytes, 0x2, IP_TOS_POS, IP_TOS_LEN);

    // random packet contains only a header, no payload
    ArrayHelper.insertLong(bytes, IP_HEADER_LEN, IP_LEN_POS, IP_LEN_LEN);

    // increment id each time a packet is generated
    ArrayHelper.insertLong(bytes, fakeId++, IP_ID_POS, IP_ID_LEN);

    // header length and flags (none specified)..
    ArrayHelper.insertLong(bytes, 0x4000, IP_FRAG_POS, IP_FRAG_LEN);

    // time-to-live
    ArrayHelper.insertLong(bytes, 0xff, IP_TTL_POS, IP_TTL_LEN);

    // protocol
    ArrayHelper.insertLong
      (bytes, randomIPProtocol(), IP_CODE_POS, IP_CODE_LEN);

    // checksum. todo: generate real checksum
    ArrayHelper.insertLong(bytes, 0xcccc, IP_CSUM_POS, IP_CSUM_LEN);

    int srcAddress = IPAddress.random(Settings.SIM_NETWORK, 
                                      Settings.SIM_NETMASK);
    // source ip
    ArrayHelper.insertLong(bytes, srcAddress, IP_SRC_POS, IPAddress.WIDTH);

    int dstAddress = srcAddress;
    int count = 0;
    // cheezy way to make sure that the source and dest address aren't the same
    while(dstAddress == srcAddress && count++ < randomRetryCount) {
      dstAddress = IPAddress.random(Settings.SIM_NETWORK, 
                                    Settings.SIM_NETMASK);
    }
    // dest ip
    ArrayHelper.insertLong(bytes, dstAddress, IP_DST_POS, IPAddress.WIDTH);

    return bytes;
  }

  /**
   * Generate a pseudo-random TCP header.
   */
  public static byte [] generateRandomUDPHeader() {
    byte [] bytes = new byte[UDP_HEADER_LEN];

    // source port
    ArrayHelper.insertLong(bytes, randomPort(), UDP_SP_POS, UDP_PORT_LEN);

    // destination port
    ArrayHelper.insertLong(bytes, randomPort(), UDP_DP_POS, UDP_PORT_LEN);

    // length
    ArrayHelper.insertLong(bytes, UDP_HEADER_LEN, UDP_LEN_POS, UDP_PORT_LEN);

    // checksum. todo: generate real checksum
    ArrayHelper.insertLong(bytes, 0xcccc, UDP_CSUM_POS, UDP_CSUM_LEN);

    return bytes;
  }

  /**
   * Generate a pseudo-random TCP header.
   */
  public static byte [] generateRandomTCPHeader() {
    byte [] bytes = new byte[TCP_HEADER_LEN];

    // source port
    ArrayHelper.insertLong(bytes, randomPort(), TCP_SP_POS, TCP_PORT_LEN);

    // destination port
    ArrayHelper.insertLong
      (bytes, randomPrivilegedPort(), TCP_DP_POS, UDP_PORT_LEN);

    // sequence number
    ArrayHelper.insertLong(bytes, 0x00000000, TCP_SEQ_POS, TCP_SEQ_LEN);

    // acknowledgment number
    ArrayHelper.insertLong(bytes, 0x00000000, TCP_ACK_POS, TCP_ACK_LEN);

    // header length and flags (0x2 is syn).
    ArrayHelper.insertLong(bytes, 0xa002, TCP_FLAG_POS, TCP_FLAG_LEN);

    // window size
    ArrayHelper.insertLong(bytes, 0x0000, TCP_WIN_POS, TCP_WIN_LEN);

    // checksum. todo: generate real checksum
    ArrayHelper.insertLong(bytes, 0xcccc, TCP_CSUM_POS, TCP_CSUM_LEN);

    // urgent pointer
    ArrayHelper.insertLong(bytes, 0x0000, TCP_URG_POS, TCP_URG_LEN);

    return bytes;
  }

  /**
   * Generate a pseudo-random ICMP header.
   */
  public static byte [] generateRandomICMPHeader() {
    byte [] bytes = new byte[ICMP_HEADER_LEN];

    // code (message type)
    ArrayHelper.insertLong(bytes, randomICMPType(), 
                           ICMP_CODE_POS, ICMP_CODE_LEN);

    // subcode
    ArrayHelper.insertLong(bytes, 0x0, ICMP_SUBC_POS, ICMP_SUBC_LEN);

    // checksum. todo: generate real checksum
    ArrayHelper.insertLong(bytes, 0xcccc, ICMP_CSUM_POS, ICMP_CSUM_LEN);

    return bytes;
  }


  private static int randomRetryCount = 10;

  private String _rcsid = 
    "$Id: HeaderGenerator.java,v 1.2 2001/06/27 02:17:33 pcharles Exp $";
}
