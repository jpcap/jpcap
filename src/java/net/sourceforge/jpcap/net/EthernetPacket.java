// $Id: EthernetPacket.java,v 1.19 2004/05/05 23:14:45 pcharles Exp $

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
 * An ethernet packet.
 * <p>
 * Contains link-level header and data payload encapsulated by an ethernet
 * packet.
 * <p>
 * There are currently two subclasses. IP and ARP protocols are supported.
 * IPPacket extends with ip header and data information.
 * ARPPacket extends with hardware and protocol addresses.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.19 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/05/05 23:14:45 $
 */
public class EthernetPacket extends Packet 
  implements EthernetFields, Serializable
{
  // store the data here, all subclasses can offset into this
  protected byte[] _bytes;

  // offset from beginning of byte array where the data payload 
  // (i.e. IP packet) starts. The size of the ethernet frame header.
  protected int _ethOffset;

  // time that the packet was captured off the wire
  protected Timeval _timeval;


  /**
   * Construct a new ethernet packet.
   * <p>
   * For the purpose of jpcap, when the type of ethernet packet is 
   * recognized as a protocol for which a class exists network library, 
   * then a more specific class like IPPacket or ARPPacket is instantiated.
   * The subclass can always be cast into a more generic form.
   */
  public EthernetPacket(int lLen, byte [] bytes) {
    _bytes = bytes;
    _ethernetHeaderLength = lLen;
    _ethOffset = lLen;
  }

  /**
   * Construct a new ethernet packet, including the capture time.
   */
  public EthernetPacket(int lLen, byte [] bytes, Timeval tv) {
    this(lLen, bytes);
    this._timeval = tv;
  }

  // set in constructor
  private int _ethernetHeaderLength;
  /** 
   * Fetch the ethernet header length in bytes.
   */
  public int getEthernetHeaderLength() {
    return _ethernetHeaderLength;
  }

  /** 
   * Fetch the packet ethernet header length.
   */
  public int getHeaderLength() {
    return getEthernetHeaderLength();
  }

  private byte[] _ethernetHeaderBytes = null;
  /**
   * Fetch the ethernet header as a byte array.
   */
  public byte[] getEthernetHeader() {
    if(_ethernetHeaderBytes == null) {
      _ethernetHeaderBytes =
	PacketEncoding.extractHeader(0, getEthernetHeaderLength(),
				     _bytes);
    }
    return _ethernetHeaderBytes;
  }

  /**
   * Fetch the ethernet header as a byte array.
   */
  public byte[] getHeader() {
    return getEthernetHeader();
  }

  private byte[] _ethernetDataBytes = null;
  /**
   * Fetch the ethernet data as a byte array.
   */
  public byte[] getEthernetData() {
    if(_ethernetDataBytes == null) {
      _ethernetDataBytes =
	PacketEncoding.extractData(0, getEthernetHeaderLength(),
				   _bytes);
    }
    return _ethernetDataBytes;
  }

  /**
   * Fetch the ethernet data as a byte array.
   */
  public byte[] getData() {
    return getEthernetData();
  }

  private String _sourceHwAddress = null;
  /**
   * Fetch the IP address of the host where the packet originated from.
   */
  public String getSourceHwAddress() {
    if(_sourceHwAddress == null) {
      _sourceHwAddress = MACAddress.extract(ETH_SRC_POS, _bytes);
    }
    return _sourceHwAddress;
  }

  private String _destinationHwAddress = null;
  /**
   * Fetch the IP address of the host where the packet originated from.
   */
  public String getDestinationHwAddress() {
    if(_destinationHwAddress == null) {
      _destinationHwAddress = MACAddress.extract(ETH_DST_POS, _bytes);
    }
    return _destinationHwAddress;
  }

  private int _etherProtocol;
  private boolean _etherProtocolSet = false;
  /**
   * Fetch the ethernet protocol.
   */
  public int getEthernetProtocol() {
    if(! _etherProtocolSet) {
      _etherProtocol =
	ArrayHelper.extractInteger(_bytes, ETH_CODE_POS, ETH_CODE_LEN);
      _etherProtocolSet = true;
    }
    return _etherProtocol;
  }

  /**
   * Fetch the ethernet protocol.
   */
  public int getProtocol() {
    return getEthernetProtocol();
  }

  /**
   * Fetch the timeval containing the time the packet arrived on the 
   * device where it was captured.
   */
  public Timeval getTimeval() {
    return _timeval;
  }
  
  /**
   * Convert this ethernet packet to a readable string.
   */
  public String toString() {
    return toColoredString(false);
  }

  /**
   * Generate string with contents describing this ethernet packet.
   * @param colored whether or not the string should contain ansi
   * color escape sequences.
   */
  public String toColoredString(boolean colored) {
    StringBuffer buffer = new StringBuffer();
    buffer.append('[');
    if(colored) buffer.append(getColor());
    buffer.append("EthernetPacket");
    if(colored) buffer.append(AnsiEscapeSequences.RESET);
    buffer.append(": ");
    buffer.append(getSourceHwAddress() + " -> " + 
                  getDestinationHwAddress());
    buffer.append(" proto=0x" + Integer.toHexString(getProtocol()));
    buffer.append(" l=" + getEthernetHeaderLength()); // + "," + data.length);
    buffer.append(']');

    return buffer.toString();
  }

  /**
   * Fetch ascii escape sequence of the color associated with this packet type.
   */
  public String getColor() {
    return AnsiEscapeSequences.DARK_GRAY;
  }


  private String _rcsid = 
    "$Id: EthernetPacket.java,v 1.19 2004/05/05 23:14:45 pcharles Exp $";
}
