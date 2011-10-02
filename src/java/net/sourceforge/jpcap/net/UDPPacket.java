// $Id: UDPPacket.java,v 1.18 2004/05/05 23:14:45 pcharles Exp $

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
 * A UDP packet.
 * <p>
 * Extends an IP packet, adding a UDP header and UDP data payload.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.18 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/05/05 23:14:45 $
 */
public class UDPPacket extends IPPacket implements UDPFields, Serializable
{
  /**
   * Create a new UDP packet.
   */
  public UDPPacket(int lLen, byte [] bytes) {
    super(lLen, bytes);
  }

  /**
   * Create a new UDP packet.
   */
  public UDPPacket(int lLen, byte [] bytes, Timeval tv) {
    this(lLen, bytes);
    this._timeval = tv;
  }

  /**
   * Fetch the port number on the source host.
   */
  public int getSourcePort() {
    return ArrayHelper.extractInteger(_bytes, _ipOffset + UDP_SP_POS,
				      UDP_PORT_LEN);
  }

  /**
   * Fetch the port number on the target host.
   */
  public int getDestinationPort() {
    return ArrayHelper.extractInteger(_bytes, _ipOffset + UDP_DP_POS,
				      UDP_PORT_LEN);
  }

  /**
   * Fetch the total length of the UDP packet, including header and
   * data payload, in bytes.
   */
  public int getLength() {
    // should produce the same value as header.length + data.length
    return ArrayHelper.extractInteger(_bytes, _ipOffset + UDP_LEN_POS,
				      UDP_LEN_LEN);
  }

  /**
   * Fetch the header checksum.
   */
  public int getUDPChecksum() {
    return ArrayHelper.extractInteger(_bytes, _ipOffset + UDP_CSUM_POS,
				      UDP_CSUM_LEN);
  }

  /**
   * Fetch the header checksum.
   */
  public int getChecksum() {
    return getUDPChecksum();
  }

  private byte[] _udpHeaderBytes = null;
  /** 
   * Fetch the UDP header a byte array.
   */
  public byte[] getUDPHeader() {
    if(_udpHeaderBytes == null) {
      _udpHeaderBytes =
	PacketEncoding.extractHeader(_ipOffset, UDP_HEADER_LEN, _bytes);
    }
    return _udpHeaderBytes;
  }

  /**
   * Fetch the UDP header as a byte array.
   */
  public byte[] getHeader() {
    return getUDPHeader();
  }

  private byte[] _udpDataBytes = null;
  /**
   * Fetch the UDP data as a byte array.
   */
  public byte[] getUDPData() {
    if(_udpDataBytes == null) {
      // set data length based on info in headers (note: tcpdump
      //  can return extra junk bytes which bubble up to here
      int tmpLen = _bytes.length - _ipOffset - UDP_HEADER_LEN;
      _udpDataBytes =
	PacketEncoding.extractData(_ipOffset, UDP_HEADER_LEN,
				   _bytes, tmpLen);
    }
    return _udpDataBytes;
  }

  /**
   * Fetch the UDP data as a byte array.
   */
  public byte[] getData() {
    return getUDPData();
  }

  /**
   * Convert this UDP packet to a readable string.
   */
  public String toString() {
    return toColoredString(false);
  }

  /**
   * Generate string with contents describing this UDP packet.
   * @param colored whether or not the string should contain ansi
   * color escape sequences.
   */
  public String toColoredString(boolean colored) {
    StringBuffer buffer = new StringBuffer();
    buffer.append('[');
    if(colored) buffer.append(getColor());
    buffer.append("UDPPacket");
    if(colored) buffer.append(AnsiEscapeSequences.RESET);
    buffer.append(": ");
    buffer.append(getSourceAddress());
    buffer.append('.');
    buffer.append(IPPort.getName(getSourcePort()));
    buffer.append(" -> ");
    buffer.append(getDestinationAddress());
    buffer.append('.');
    buffer.append(IPPort.getName(getDestinationPort()));
    buffer.append(" l=" + UDP_HEADER_LEN + "," + (getLength()-UDP_HEADER_LEN));
    buffer.append(']');

    return buffer.toString();
  }

  /**
   * Fetch ascii escape sequence of the color associated with this packet type.
   */
  public String getColor() {
    return AnsiEscapeSequences.LIGHT_GREEN;
  }


  private String _rcsid = 
    "$Id: UDPPacket.java,v 1.18 2004/05/05 23:14:45 pcharles Exp $";
}
