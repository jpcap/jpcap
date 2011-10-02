// $Id: IPPacket.java,v 1.24 2004/05/05 23:14:45 pcharles Exp $

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
 * An IP protocol packet.
 * <p>
 * Extends an ethernet packet, adding IP header information and an IP 
 * data payload. 
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.24 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/05/05 23:14:45 $
 */
public class IPPacket extends EthernetPacket implements IPFields, Serializable
{
  // offset from beginning of byte array where IP header ends (i.e.,
  //  size of ethernet frame header and IP header
  protected int _ipOffset;

  /** 
   * Create a new IP packet. 
   */
  public IPPacket(int lLen, byte [] bytes) {
    super(lLen, bytes);
    // fetch the actual header length from the incoming bytes
    _ipHeaderLength = 
      (ArrayHelper.extractInteger(_bytes,_ethOffset+IP_VER_POS,
				   IP_VER_LEN) & 0xf) * 4;
    // set offset into _bytes of previous layers
    _ipOffset = _ethOffset + _ipHeaderLength;
  }

  /**
   * Create a new IP packet.
   */
  public IPPacket(int lLen, byte [] bytes, Timeval tv) {
    this(lLen, bytes);
    this._timeval = tv;
  }

  private int _version;
  private boolean _versionSet = false;  // have to use a boolean, int!=Object
  /** 
   * Get the IP version code.
   */
  public int getVersion() {
    if(! _versionSet) {
      _version =
	(ArrayHelper.extractInteger(_bytes, _ethOffset + IP_VER_POS,
				    IP_VER_LEN) >> 4) & 0xf;
      _versionSet = true;
    }
    return _version;
  }

  // set in constructor
  private int _ipHeaderLength;
  /** 
   * Fetch the IP header length in bytes. 
   */
  public int getIPHeaderLength() {
    return _ipHeaderLength;
  }

  /** 
   * Fetch the IP header length in bytes.
   */
  public int getIpHeaderLength() {
    // this is the old method call, but everything else uses all caps for
    // TCP, so in the interest of consistency...
    return getIPHeaderLength();
  }

  /** 
   * Fetch the packet IP header length.
   */
  public int getHeaderLength() {
    return getIPHeaderLength();
  }

  private int _typeOfService;
  private boolean _typeOfServiceSet = false;
  /** 
   * Fetch the type of service. 
   * For more information refer to the TypesOfService interface.
   */
  public int getTypeOfService() {
    if(! _typeOfServiceSet) {
      _typeOfService = 
	ArrayHelper.extractInteger(_bytes, _ethOffset + IP_TOS_POS,
				   IP_TOS_LEN);
      _typeOfServiceSet = true;
    }
    return _typeOfService;
  }

  private int _length;
  private boolean _lengthSet = false;
  /** 
   * Fetch the IP length in bytes.
   */
  public int getLength() {
    if(! _lengthSet) {
      _length = 
	ArrayHelper.extractInteger(_bytes, _ethOffset + IP_LEN_POS,
                                   IP_LEN_LEN);
      _lengthSet = true;
    }
    return _length;
  }

  private int _id;
  private boolean _idSet = false;
  /**
   * Fetch the unique ID of this IP datagram. The ID normally 
   * increments by one each time a datagram is sent by a host.
   */
  public int getId() {
    if(! _idSet) {
      _id = 
	ArrayHelper.extractInteger(_bytes, _ethOffset + IP_ID_POS, IP_ID_LEN);
      _idSet = true;
    }
    return _id;
  }

  private int _fragmentFlags;
  private boolean _fragmentFlagsSet = false;
  /** 
   * Fetch fragmentation flags.
   */
  public int getFragmentFlags() {
    if(! _fragmentFlagsSet) {
      // fragment flags are the high 3 bits
      int huh = ArrayHelper.extractInteger(_bytes,
					   _ethOffset + IP_FRAG_POS,
					   IP_FRAG_LEN);
      _fragmentFlags = 
	(ArrayHelper.extractInteger(_bytes, _ethOffset + IP_FRAG_POS,
				    IP_FRAG_LEN) >> 13) & 0x7;
      _fragmentFlagsSet = true;
    }
    return _fragmentFlags;
  }

  private int _fragmentOffset;
  private boolean _fragmentOffsetSet = false;
  /** 
   * Fetch fragmentation offset.
   */
  public int getFragmentOffset() {
    if(! _fragmentOffsetSet) {
      // offset is the low 13 bits
      _fragmentOffset =
	ArrayHelper.extractInteger(_bytes, _ethOffset + IP_FRAG_POS,
				   IP_FRAG_LEN) & 0x1fff;
      _fragmentOffsetSet = true;
    }
    return _fragmentOffset;
  }

  private int _timeToLive;
  private boolean _timeToLiveSet = false;
  /**
   * Fetch the time to live. TTL sets the upper limit on the number of 
   * routers through which this IP datagram is allowed to pass.
   */
  public int getTimeToLive() {
    if(! _timeToLiveSet) {
      _timeToLive = 
	ArrayHelper.extractInteger(_bytes, _ethOffset + IP_TTL_POS,
				   IP_TTL_LEN);
      _timeToLiveSet = true;
    }
    return _timeToLive;
  }

  private int _ipProtocol;
  private boolean _ipProtocolSet = false;
  /**
   * Fetch the code indicating the type of protocol embedded in the IP
   * datagram. @see IPProtocols.
   */
  public int getIPProtocol() {
    if(! _ipProtocolSet) {
      _ipProtocol = 
	ArrayHelper.extractInteger(_bytes, _ethOffset + IP_CODE_POS,
				   IP_CODE_LEN);
      _ipProtocolSet = true;
    }
    return _ipProtocol;
  }
  /**
   * Fetch the code indicating the type of protocol embedded in the IP
   * datagram. @see IPProtocols.
   */
  public int getProtocol() {
    return getIPProtocol();
  }

  private int _ipChecksum;
  private boolean _ipChecksumSet = false;
  /** 
   * Fetch the header checksum.
   */
  public int getIPChecksum() {
    if(! _ipChecksumSet) {
      _ipChecksum = 
	ArrayHelper.extractInteger(_bytes, _ethOffset + IP_CSUM_POS,
				   IP_CSUM_LEN);
      _ipChecksumSet = true;
    }
    return _ipChecksum;
  }

  /** 
   * Fetch the header checksum.
   */
  public int getChecksum() {
    return getIPChecksum();
  }

  private String _sourceAddress = null;
  /** 
   * Fetch the IP address of the host where the packet originated from.
   */
  public String getSourceAddress() {
    if(_sourceAddress == null) {
      _sourceAddress = IPAddress.extract(_ethOffset + IP_SRC_POS, _bytes);
    }
    return _sourceAddress;
  }

  private byte[] _sourceAddressBytes = null;
  /** 
   * Fetch the source address as a byte array.
   */
  public byte[] getSourceAddressBytes() {
    if(_sourceAddressBytes == null) {
      _sourceAddressBytes = new byte[4];
      System.arraycopy(_bytes, _ethOffset + IP_SRC_POS,
		       _sourceAddressBytes, 0, 4);
    }
    return _sourceAddressBytes;
  }

  private long _sourceAddressAsLong;
  private boolean _sourceAddressAsLongSet = false;
  /** 
   * Fetch the source address as a long.
   */
  public long getSourceAddressAsLong() {
    if(! _sourceAddressAsLongSet) {
      _sourceAddressAsLong = 
	ArrayHelper.extractLong(_bytes, _ethOffset + IP_SRC_POS, 4);
      _sourceAddressAsLongSet = true;
    }
    return _sourceAddressAsLong;
  }

  private String _destinationAddress = null;
  /** 
   * Fetch the IP address of the host where the packet is destined.
   */
  public String getDestinationAddress() {
    if(_destinationAddress == null) {
      _destinationAddress = 
	IPAddress.extract(_ethOffset + IP_DST_POS, _bytes);
    }
    return _destinationAddress;
  }

  private byte[] _destinationAddressBytes = null;
  /** 
   * Fetch the destination address as a byte array.
   */
  public byte[] getDestinationAddressBytes() {
    if(_destinationAddressBytes == null) {
      _destinationAddressBytes = new byte[4];
      System.arraycopy(_bytes,_ethOffset+IP_DST_POS,
		       _destinationAddressBytes,0,4);
    }
    return _destinationAddressBytes;
  }

  private long _destinationAddressAsLong;
  private boolean _destinationAddressAsLongSet = false;
  /** 
   * Fetch the destination address as a long.
   */
  public long getDestinationAddressAsLong() {
    if(! _destinationAddressAsLongSet) {
      _destinationAddressAsLong = 
	ArrayHelper.extractLong(_bytes, _ethOffset + IP_DST_POS, 4);
      _destinationAddressAsLongSet = true;
    }
    return _destinationAddressAsLong;
  }

  private byte[] _ipHeaderBytes = null;
  /** 
   * Fetch the IP header a byte array.
   */
  public byte[] getIPHeader() {
    if(_ipHeaderBytes == null) {
      _ipHeaderBytes =
	PacketEncoding.extractHeader(_ethOffset, getIPHeaderLength(),
				     _bytes);
    }
    return _ipHeaderBytes;
  }

  /** 
   * Fetch the IP header as a byte array.
   */
  public byte[] getHeader() {
    return getIPHeader();
  }

  private byte[] _ipDataBytes = null;
  /** 
   * Fetch the IP data as a byte array.
   */
  public byte[] getIPData() {
    if(_ipDataBytes == null) {
      // set data length based on info in headers (note: tcpdump
      //  can return extra junk bytes which bubble up to here
      int tmpLen = getLength() - getIPHeaderLength();
      _ipDataBytes =
	PacketEncoding.extractData(_ethOffset, getIPHeaderLength(),
				   _bytes, tmpLen);
    }
    return _ipDataBytes;
  }

  /** 
   * Fetch the IP data as a byte array.
   */
  public byte[] getData() {
    return getIPData();
  }

  private boolean _isValidChecksum;
  private boolean _isValidChecksumSet = false;
  /** 
   * Check if the IP packet is valid, checksum-wise.
   */
  public boolean isValidChecksum() {
    if(! _isValidChecksumSet) {
      // first validate other information about the packet. if this stuff
      // is not true, the packet (and therefore the checksum) is invalid
      // - ip_hl >= 5 (ip_hl is the length in 4-byte words)
      if( getIPHeaderLength() < IP_HEADER_LEN ) {
	_isValidChecksum = false;
      } else {
	_isValidChecksum = (computeReceiverIPChecksum() == 0xffff);
      }
      _isValidChecksumSet = true;
    }
    return _isValidChecksum;
  }

  /**
   * Check if the IP packet is valid, checksum-wise.
   */
  public boolean isValidIPChecksum() {
    return isValidChecksum();
  }
  protected int computeReceiverIPChecksum() {
    return computeReceiverChecksum(_ethOffset, getIPHeaderLength());
  }
  protected int computeReceiverChecksum(int start, int len) {
    // checksum should come out to -1 if checksum is correct
    return onesCompSum(_bytes,start,len);
  }
  protected int computeSenderIPChecksum() {
    return computeSenderChecksum(_ethOffset, getIPHeaderLength(), 10);
  }
  protected int computeSenderChecksum(int start, int len, int csumPos) {
    // quick bad-data check
    if( csumPos >= len) return 0; // bad data, header too short
    // copy bytes, zero out checksum
    byte[] sbytes = new byte[len];
    System.arraycopy(_bytes, start, sbytes, 0, len);
    // zero out any current checksum
    sbytes[csumPos] = sbytes[csumPos+1] = 0;
    // checksum should come out to -1 if checksum is correct
    return onesCompSum(sbytes,0,len);
  }
  protected int onesCompSum(byte[] bytes, int start, int len) {
    int sum = 0;
    // basically, IP checksums are done by taking the 16 bit ones-
    // complement sum of the IP header. This means summing two bytes
    // at a time. no error checking is done (e.g. bounds checking)
    int i;
    for(i = 0; i < len; i+=2) {
      // put bytes in ints so we can forget about sign-extension
      int i1 = bytes[start + i] & 0xff;
      // zero-pad, maybe
      int i2 = (start + i + 1 < len ? bytes[start + i + 1] & 0xff : 0); 
      sum += ((i1 << 8) + i2);
      while( (sum & 0xffff) != sum ) {
	sum &= 0xffff;
	sum += 1;
      }
    }
    return sum;
  }

  /**
   * Convert this IP packet to a readable string.
   */
  public String toString() {
    return toColoredString(false);
  }

  /**
   * Generate string with contents describing this IP packet.
   * @param colored whether or not the string should contain ansi
   * color escape sequences.
   */
  public String toColoredString(boolean colored) {
    StringBuffer buffer = new StringBuffer();
    buffer.append('[');
    if(colored) buffer.append(getColor());
    buffer.append("IPPacket");
    if(colored) buffer.append(AnsiEscapeSequences.RESET);
    buffer.append(": ");
    buffer.append(getSourceAddress() + " -> " + 
                  getDestinationAddress());
    buffer.append(" proto=" + getProtocol());
    buffer.append(" l=" + getIPHeaderLength() + "," + getLength());
    buffer.append(']');

    return buffer.toString();
  }

  /**
   * Convert this IP packet to a more verbose string.
   */
  public String toColoredVerboseString(boolean colored) {
    StringBuffer buffer = new StringBuffer();
    buffer.append('[');
    if(colored) buffer.append(getColor());
    buffer.append("IPPacket");
    if(colored) buffer.append(AnsiEscapeSequences.RESET);
    buffer.append(": ");
    buffer.append("version=" + getVersion() + ", ");
    buffer.append("hlen=" + getHeaderLength() + ", ");
    buffer.append("tos=" + getTypeOfService() + ", ");
    buffer.append("length=" + getLength() + ", ");
    buffer.append("id=" + getId() + ", ");
    buffer.append("flags=0x" + Integer.toHexString(getFragmentFlags()) + ", ");
    buffer.append("offset=" + getFragmentOffset() + ", ");
    buffer.append("ttl=" + getTimeToLive() + ", ");
    buffer.append("proto=" + getProtocol() + ", ");
    buffer.append("sum=0x" + Integer.toHexString(getChecksum()) + ", ");
    buffer.append("src=" + getSourceAddress() + ", ");
    buffer.append("dest=" + getDestinationAddress());
    buffer.append(']');

    return buffer.toString();
  }

  /**
   * Fetch ascii escape sequence of the color associated with this packet type.
   */
  public String getColor() {
    return AnsiEscapeSequences.WHITE;
  }
 
  /** 
   * This inner class provides access to private methods for unit testing.
   */
  public class TestProbe {
    public int getComputedReceiverIPChecksum() {
      return IPPacket.this.computeReceiverIPChecksum();
    }
    public int getComputedSenderIPChecksum() {
      return IPPacket.this.computeSenderIPChecksum();
    }
  }


  private String _rcsid = 
    "$Id: IPPacket.java,v 1.24 2004/05/05 23:14:45 pcharles Exp $";
}
