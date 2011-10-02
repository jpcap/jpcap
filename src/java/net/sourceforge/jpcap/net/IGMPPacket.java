// $Id: IGMPPacket.java,v 1.7 2004/05/05 23:14:45 pcharles Exp $

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
 * An IGMP packet.
 * <p>
 * Extends an IP packet, adding an IGMP header and IGMP data payload.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.7 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/05/05 23:14:45 $
 */
public class IGMPPacket extends IPPacket implements IGMPFields, Serializable
{
  public IGMPPacket(int lLen, byte [] bytes) {
    super(lLen, bytes);
  }

  public IGMPPacket(int lLen, byte [] bytes, Timeval tv) {
    this(lLen, bytes);
    this._timeval = tv;
  }

  private byte[] _igmpHeaderBytes = null;
  /**
   * Fetch the IGMP header a byte array.
   */
  public byte[] getIGMPHeader() {
    if(_igmpHeaderBytes == null) {
      _igmpHeaderBytes =
	PacketEncoding.extractHeader(_ethOffset,IGMP_HEADER_LEN, _bytes);
    }
    return _igmpHeaderBytes;
  }

  /**
   * Fetch the IGMP header as a byte array.
   */
  public byte[] getHeader() {
    return getIGMPHeader();
  }

  private byte[] _igmpDataBytes = null;
  /**
   * Fetch the IGMP data as a byte array.
   */
  public byte[] getIGMPData() {
    if(_igmpDataBytes == null) {
      // set data length based on info in headers (note: tcpdump
      //  can return extra junk bytes which bubble up to here
      int dataLen = _bytes.length - _ethOffset - IGMP_HEADER_LEN;

      _igmpDataBytes =
	PacketEncoding.extractData(_ethOffset, IGMP_HEADER_LEN,
				   _bytes, dataLen);
    }
    return _igmpDataBytes;
  }

  /**
   * Fetch the IGMP data as a byte array.
   */
  public byte[] getData() {
    return getIGMPData();
  }

  /**
   * Fetch the IGMP message type, including subcode. Return value can be 
   * used with IGMPMessage.getDescription().
   * @return a 2-byte value containing the message type in the high byte
   * and the message type subcode in the low byte.
   */
  public int getMessageType() {
    return ArrayHelper.extractInteger(_bytes, _ipOffset + IGMP_CODE_POS,
				      IGMP_CODE_LEN);
  }

  /**
   * Fetch the IGMP max response time.
   */
  public int getMaxResponseTime() {
    return ArrayHelper.extractInteger(_bytes, _ipOffset + IGMP_MRSP_POS,
				      IGMP_MRSP_LEN);
  }

  /**
   * Fetch the IGMP header checksum.
   */
  public int getIGMPChecksum() {
    return ArrayHelper.extractInteger(_bytes, _ipOffset + IGMP_CSUM_POS,
				      IGMP_CSUM_LEN);
  }

  /**
   * Fetch the IGMP header checksum.
   */
  public int getChecksum() {
    return getIGMPChecksum();
  }

  /**
   * Fetch the IGMP group address.
   */
  public String getGroupAddress() {
    return IPAddress.extract(_ipOffset + IGMP_GADDR_POS, _bytes);
  }


  /**
   * Convert this IGMP packet to a readable string.
   */
  public String toString() {
    return toColoredString(false);
  }

  /**
   * Generate string with contents describing this IGMP packet.
   * @param colored whether or not the string should contain ansi
   * color escape sequences.
   */
  public String toColoredString(boolean colored) {
    StringBuffer buffer = new StringBuffer();
    buffer.append('[');
    if(colored) buffer.append(getColor());
    buffer.append("IGMPPacket");
    if(colored) buffer.append(AnsiEscapeSequences.RESET);
    buffer.append(": ");
    buffer.append(IGMPMessage.getDescription(getMessageType()));
    buffer.append(", ");
    buffer.append(getGroupAddress() + ": ");
    buffer.append(getSourceAddress() + " -> " + 
                  getDestinationAddress());
    buffer.append(" l=" + IGMP_HEADER_LEN + "," + 
		  (_bytes.length-_ipOffset-IGMP_HEADER_LEN));
    buffer.append(']');

    return buffer.toString();
  }

  /**
   * Fetch ascii escape sequence of the color associated with this packet type.
   */
  public String getColor() {
    return AnsiEscapeSequences.BROWN;
  }


  private String _rcsid = 
    "$Id: IGMPPacket.java,v 1.7 2004/05/05 23:14:45 pcharles Exp $";
}
