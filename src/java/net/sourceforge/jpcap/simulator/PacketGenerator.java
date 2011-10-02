// $Id: PacketGenerator.java,v 1.5 2001/06/27 02:17:33 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.simulator;

import net.sourceforge.jpcap.util.HexHelper;
import net.sourceforge.jpcap.util.ArrayHelper;
import net.sourceforge.jpcap.net.EthernetProtocols;
import net.sourceforge.jpcap.net.EthernetFields;
import net.sourceforge.jpcap.net.IPFields;
import net.sourceforge.jpcap.net.IPProtocols;


/**
 * This class produces data fabricated to look like it originated from a 
 * network device.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.5 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/06/27 02:17:33 $
 */
public class PacketGenerator implements EthernetFields, IPFields
{
  /**
   * Generate a pseudo-random network packet.
   
   * @return an array of bytes containing a randomly generated 
   * ethernet network packet. Packet can encapsulate any known protocol.
   */
  public static byte [] generate() {
    // create ethernet header
    byte [] packet = HeaderGenerator.generateRandomEthernetHeader();
    int eProto = 
      ArrayHelper.extractInteger(packet, ETH_CODE_POS, ETH_CODE_LEN);

    // figure out what type of packet should be encapsulated after the 
    // newly generated ethernet header.
    switch(eProto) {
    case EthernetProtocols.IP: // encapsulate IP
      byte [] ipHeader = HeaderGenerator.generateRandomIPHeader();
      packet = ArrayHelper.join(packet, ipHeader);

      // figure out what type of protocol should be encapsulated after the 
      // newly generated IP header.
      int ipProto = 
        ArrayHelper.extractInteger(ipHeader, IP_CODE_POS, IP_CODE_LEN);
      switch(ipProto) {
      case IPProtocols.UDP: // encapsulate UDP inside IP
        byte [] udpHeader = HeaderGenerator.generateRandomUDPHeader();
        packet = ArrayHelper.join(packet, udpHeader);
        break;
      case IPProtocols.ICMP: // encapsulate ICMP inside IP
        byte [] icmpHeader = HeaderGenerator.generateRandomICMPHeader();
        packet = ArrayHelper.join(packet, icmpHeader);
        break;
      case IPProtocols.TCP: // encapsulate TCP inside IP
        byte [] tcpHeader = HeaderGenerator.generateRandomTCPHeader();
        packet = ArrayHelper.join(packet, tcpHeader);
        break;
      default: 
        // if the IP protocol isn't known, leave the data payload empty
        break;
      }
      break;
    case EthernetProtocols.ARP:
      byte [] arpHeader = HeaderGenerator.generateRandomARPHeader();
      packet = ArrayHelper.join(packet, arpHeader);
      break;
    default:
      // if the ethernet protocol isn't known, leave the data payload empty
      break;
    }

    return packet;
  }


  /**
   * Unit test.
   */
  public static void main(String [] args) {
    byte [] bytes = HeaderGenerator.generateRandomEthernetHeader();
    System.err.println(HexHelper.toString(bytes));

    bytes = HeaderGenerator.generateRandomIPHeader();
    System.err.println(HexHelper.toString(bytes));

    bytes = HeaderGenerator.generateRandomARPHeader();
    System.err.println(HexHelper.toString(bytes));
  }


  private String _rcsid = 
    "$Id: PacketGenerator.java,v 1.5 2001/06/27 02:17:33 pcharles Exp $";
}
