// $Id: ICMPPacket.java,v 1.19 2004/05/05 23:14:45 pcharles Exp $

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
 * An ICMP packet.
 * <p>
 * Extends an IP packet, adding an ICMP header and ICMP data payload.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.19 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/05/05 23:14:45 $
 */
public class ICMPPacket extends IPPacket implements ICMPFields, Serializable
{
  public ICMPPacket(int lLen, byte [] bytes) {
    super(lLen, bytes);
  }

  public ICMPPacket(int lLen, byte [] bytes, Timeval tv) {
    this(lLen, bytes);
    this._timeval = tv;
  }

  private byte[] _icmpHeaderBytes = null;
  /**
   * Fetch the ICMP header a byte array.
   */
  public byte[] getICMPHeader() {
    if(_icmpHeaderBytes == null) {
      _icmpHeaderBytes =
	PacketEncoding.extractHeader(_ipOffset, ICMP_HEADER_LEN, _bytes);
    }
    return _icmpHeaderBytes;
  }

  /**
   * Fetch the ICMP header as a byte array.
   */
  public byte[] getHeader() {
    return getICMPHeader();
  }

  private byte[] _icmpDataBytes = null;
  /** 
   * Fetch the ICMP data as a byte array.
   */
  public byte[] getICMPData() {
    if(_icmpDataBytes == null) {
      // set data length based on info in headers (note: tcpdump
      //  can return extra junk bytes which bubble up to here
      int dataLen = _bytes.length - _ipOffset - ICMP_HEADER_LEN;

      _icmpDataBytes =
	PacketEncoding.extractData(_ipOffset, ICMP_HEADER_LEN, 
                                   _bytes, dataLen);
    }
    return _icmpDataBytes;
  }

  /**
   * Fetch the ICMP data as a byte array.
   */
  public byte[] getData() {
    return getICMPData();
  }

  /**
   * Fetch the ICMP message type, including subcode. Return value can be 
   * used with ICMPMessage.getDescription().
   * @return a 2-byte value containing the message type in the high byte
   * and the message type subcode in the low byte.
   */
  public int getMessageCode() {
    return ArrayHelper.extractInteger(_bytes, _ipOffset + ICMP_CODE_POS,
				      ICMP_CODE_LEN * 2);
  }

  /**
   * Fetch the ICMP message type code. Formerly .getMessageType().
   */
  public int getMessageMajorCode() {
    return ArrayHelper.extractInteger(_bytes, _ipOffset + ICMP_CODE_POS,
				      ICMP_CODE_LEN);
  }

  /**
   * For backward compatibility. @deprecated use getMessageMajorCode().
   */
  public int getMessageType() {
    return getMessageMajorCode();
  }

  /**
   * Fetch the ICMP message subcode.
   */
  public int getMessageMinorCode() {
    return ArrayHelper.extractInteger(_bytes, _ipOffset + ICMP_CODE_POS + 1, 
                                      ICMP_CODE_LEN);
  }

  /**
   * Fetch the ICMP header checksum.
   */
  public int getChecksum() {
    return getICMPChecksum();
  }

  /**
   * Fetch the ICMP header checksum.
   */
  public int getICMPChecksum() {
    return ArrayHelper.extractInteger(_bytes, _ipOffset + ICMP_CSUM_POS,
				      ICMP_CSUM_LEN);
  }

  /**
   * Convert this ICMP packet to a readable string.
   */
  public String toString() {
    return toColoredString(false);
  }

  /**
   * Generate string with contents describing this ICMP packet.
   * @param colored whether or not the string should contain ansi
   * color escape sequences.
   */
  public String toColoredString(boolean colored) {
    StringBuffer buffer = new StringBuffer();
    buffer.append('[');
    if(colored) buffer.append(getColor());
    buffer.append("ICMPPacket");
    if(colored) buffer.append(AnsiEscapeSequences.RESET);
    buffer.append(": ");
    buffer.append(ICMPMessage.getDescription(getMessageCode()));
    buffer.append(", ");
    buffer.append(getSourceAddress() + " -> " + 
                  getDestinationAddress());
    buffer.append(" l=" + ICMP_HEADER_LEN + "," + 
		  (_bytes.length - _ipOffset - ICMP_HEADER_LEN));
    buffer.append(']');

    return buffer.toString();
  }

  /**
   * Fetch ascii escape sequence of the color associated with this packet type.
   */
  public String getColor() {
    return AnsiEscapeSequences.LIGHT_BLUE;
  }

  private String _rcsid = 
    "$Id: ICMPPacket.java,v 1.19 2004/05/05 23:14:45 pcharles Exp $";
}
