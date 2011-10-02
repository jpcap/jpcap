// $Id: ARPPacket.java,v 1.14 2004/05/05 23:14:45 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;

import net.sourceforge.jpcap.util.AnsiEscapeSequences;
import net.sourceforge.jpcap.util.ArrayHelper;
import net.sourceforge.jpcap.util.Timeval;
import java.io.Serializable;


/**
 * An ARP protocol packet.
 * <p>
 * Extends an ethernet packet, adding ARP header information and an ARP 
 * data payload. 
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.14 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/05/05 23:14:45 $
 */
public class ARPPacket extends EthernetPacket 
  implements ARPFields, Serializable
{
  /**
   * Create a new ARP packet.
   */
  public ARPPacket(int lLen, byte [] bytes) {
    super(lLen, bytes);

    this.header = PacketEncoding.extractHeader(lLen, ARP_HEADER_LEN, bytes);
    this.data = PacketEncoding.extractData(lLen, ARP_HEADER_LEN, bytes);
  }

  /**
   * Create a new ARP packet.
   */
  public ARPPacket(int lLen, byte [] bytes, Timeval tv) {
    this(lLen, bytes);
    this._timeval = tv;
  }

  /**
   * Fetch the hardware source address.
   */
  public String getSourceHwAddress() {
    return MACAddress.extract(ARP_S_HW_ADDR_POS, header);
  }

  /**
   * Fetch the hardware destination address.
   */
  public String getDestinationHwAddress() {
    return MACAddress.extract(ARP_T_HW_ADDR_POS, header);
  }

  /**
   * Fetch the proto sender address.
   */
  public String getSourceProtoAddress() {
    return IPAddress.extract(ARP_S_PR_ADDR_POS, header);
  }

  /**
   * Fetch the proto sender address.
   */
  public String getDestinationProtoAddress() {
    return IPAddress.extract(ARP_T_PR_ADDR_POS, header);
  }

  /**
   * Fetch the operation code.
   * Usually one of ARPFields.{ARP_OP_REQ_CODE, ARP_OP_REP_CODE}.
   */
  public int getOperation() {
    return ArrayHelper.extractInteger(header, ARP_OP_POS, ARP_OP_LEN);
  }

  /**
   * Fetch the arp header, excluding arp data payload.
   */
  public byte [] getARPHeader() {
    return header;
  }

  /**
   * Fetch data portion of the arp header.
   */
  public byte [] getARPData() {
    return data;
  }

  /**
   * Fetch the arp header, excluding arp data payload.
   */
  public byte [] getHeader() {
    return getARPHeader();
  }

  /**
   * Fetch data portion of the arp header.
   */
  public byte [] getData() {
    return getARPData();
  }

  /**
   * Convert this ARP packet to a readable string.
   */
  public String toString() {
    return toColoredString(false);
  }

  /**
   * Generate string with contents describing this ARP packet.
   * @param colored whether or not the string should contain ansi
   * color escape sequences.
   */
  public String toColoredString(boolean colored) {
    StringBuffer buffer = new StringBuffer();
    buffer.append('[');
    if(colored) buffer.append(getColor());
    buffer.append("ARPPacket");
    if(colored) buffer.append(AnsiEscapeSequences.RESET);
    buffer.append(": ");
    buffer.append(getOperation() == ARP_OP_REQ_CODE ? "request" : "reply");
    buffer.append(' ');
    buffer.append(getSourceHwAddress() + " -> " + 
                  getDestinationHwAddress());
    buffer.append(", ");
    buffer.append(getSourceProtoAddress() + " -> " + 
                  getDestinationProtoAddress());
    buffer.append(" l=" + header.length + "," + data.length);
    buffer.append(']');

    return buffer.toString();
  }

  /**
   * Fetch ascii escape sequence of the color associated with this packet type.
   */
  public String getColor() {
    return AnsiEscapeSequences.PURPLE;
  }

  /**
   * ARP header.
   */
  byte [] header;

  /**
   * ARP data.
   */
  byte [] data;


  private String _rcsid = 
    "$Id: ARPPacket.java,v 1.14 2004/05/05 23:14:45 pcharles Exp $";
}
