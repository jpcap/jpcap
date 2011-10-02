// $Id: TCPPacket.java,v 1.22 2004/05/05 23:14:45 pcharles Exp $

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
 * A TCP packet.
 * <p>
 * Extends an IP packet, adding a TCP header and TCP data payload.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.22 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/05/05 23:14:45 $
 */
public class TCPPacket extends IPPacket implements TCPFields, Serializable
{
  /** 
   * Create a new TCP packet.
   */
  public TCPPacket(int lLen, byte [] bytes) {
    super(lLen,bytes);
    // set TCP header length
    _tcpHeaderLength =
      ((ArrayHelper.extractInteger(_bytes, _ipOffset + TCP_FLAG_POS,
				   TCP_FLAG_LEN) >> 12) & 0xf) * 4;
    // set data (payload) length based on info in headers (note: tcpdump
    //  can return extra junk bytes which bubble up to here
    int tmpLen =
      getLength() - getIpHeaderLength() - _tcpHeaderLength;
    _payloadDataLength = (tmpLen < 0) ? 0 : tmpLen;
  }

  /**
   * Create a new TCP packet.
   */
  public TCPPacket(int lLen, byte [] bytes, Timeval tv) {
    this(lLen, bytes);
    this._timeval = tv;
  }

  private int _sourcePort;
  private boolean _sourcePortSet = false;
  /** 
   * Fetch the port number on the source host.
   */
  public int getSourcePort() {
    if(! _sourcePortSet) {
      _sourcePort =
	ArrayHelper.extractInteger(_bytes, _ipOffset + TCP_SP_POS,
				   TCP_PORT_LEN);
      _sourcePortSet = true;
    }
    return _sourcePort;
  }

  private int _destinationPort;
  private boolean _destinationPortSet = false;
  /** 
   * Fetches the port number on the destination host.
   */
  public int getDestinationPort() {
    if(! _destinationPortSet) {
      _destinationPort = 
	ArrayHelper.extractInteger(_bytes, _ipOffset + TCP_DP_POS,
				   TCP_PORT_LEN);
      _destinationPortSet = true;
    }
    return _destinationPort;
  }

  private long _sequenceNumber;
  private boolean _sequenceNumberSet = false;
  /** 
   * Fetch the packet sequence number.
   */
  public long getSequenceNumber() {
    if(! _sequenceNumberSet) {
      _sequenceNumber =
	ArrayHelper.extractLong(_bytes, _ipOffset + TCP_SEQ_POS,
				TCP_SEQ_LEN);
      _sequenceNumberSet = true;
    }
    return _sequenceNumber;
  }

  private long _acknowledgmentNumber;
  private boolean _acknowledgmentNumberSet = false;
  /** 
   *    Fetch the packet acknowledgment number.
   */
  public long getAcknowledgmentNumber() {
    if(! _acknowledgmentNumberSet) {
      _acknowledgmentNumber =
	ArrayHelper.extractLong(_bytes, _ipOffset + TCP_ACK_POS,
				TCP_ACK_LEN);
      _acknowledgmentNumberSet = true;
    }
    return _acknowledgmentNumber;
  }

  /** 
   * Fetch the packet acknowledgment number. 
   */
  public long getAcknowledgementNumber() {
    return getAcknowledgmentNumber();
  }

  // this gets set by the constructor
  private int _tcpHeaderLength;

  /** 
   * Fetch the TCP header length in bytes.
   */
  public int getTCPHeaderLength() {
    return _tcpHeaderLength;
  }

  /** 
   * Fetch the TCP header length in bytes.
   */
  public int getTcpHeaderLength() {
    // this is the old method call, but everything else uses all caps for
    // TCP, so in the interest of consistency...
    return getTCPHeaderLength();
  }

  /**
   * Fetches the packet TCP header length.
   */
  public int getHeaderLength() {
    return getTCPHeaderLength();
  }

  // this gets set by the constructor
  private int _payloadDataLength;
  /**
   * Fetches the length of the payload data.
   */
  public int getPayloadDataLength() {
    return _payloadDataLength;
  }

  private int _windowSize;
  private boolean _windowSizeSet = false;
  /**
   * Fetch the window size.
   */
  public int getWindowSize() {
    if(! _windowSizeSet) {
      _windowSize =
	ArrayHelper.extractInteger(_bytes, _ipOffset + TCP_WIN_POS,
				   TCP_WIN_LEN);
      _windowSizeSet = true;
    }
    return _windowSize;
  }

  private int _tcpChecksum;
  private boolean _tcpChecksumSet = false;
  /** 
   * Fetch the header checksum.
   */
  public int getTCPChecksum() {
    if(! _tcpChecksumSet) {
      _tcpChecksum =
	ArrayHelper.extractInteger(_bytes, _ipOffset + TCP_CSUM_POS,
				   TCP_CSUM_LEN);
      _tcpChecksumSet = true;
    }
    return _tcpChecksum;
  }

  /**
   * Fetch the header checksum.
   */
  public int getChecksum() {
    return getTCPChecksum();
  }

  private int _urgentPointer;
  private boolean _urgentPointerSet = false;
  /** 
   * Fetch the urgent pointer.
   */
  public int getUrgentPointer() {
    if(! _urgentPointerSet) {
      _urgentPointer =
	ArrayHelper.extractInteger(_bytes, _ipOffset + TCP_URG_POS,
				   TCP_URG_LEN);
      _urgentPointerSet = true;
    }
    return _urgentPointer;
  }

  // next value holds all the flags
  private int _allFlags;
  private boolean _allFlagsSet = false;
  private int getAllFlags() {
    if(! _allFlagsSet) {
      _allFlags =
	ArrayHelper.extractInteger(_bytes, _ipOffset + TCP_FLAG_POS,
				   TCP_FLAG_LEN);
    }
    return _allFlags;
  }

  private boolean _isUrg;
  private boolean _isUrgSet = false;
  /** 
   * Check the URG flag, flag indicates if the urgent pointer is valid.
   */
  public boolean isUrg() {
    if(! _isUrgSet) {
      _isUrg = (getAllFlags() & TCP_URG_MASK) != 0;
      _isUrgSet = true;
    }
    return _isUrg;
  }

  private boolean _isAck;
  private boolean _isAckSet = false;
  /** 
   * Check the ACK flag, flag indicates if the ack number is valid.
   */
  public boolean isAck() {
    if(! _isAckSet) {
      _isAck = (getAllFlags () & TCP_ACK_MASK) != 0;
      _isAckSet = true;
    }
    return _isAck;
  }

  private boolean _isPsh;
  private boolean _isPshSet = false;
  /** 
   * Check the PSH flag, flag indicates the receiver should pass the
   * data to the application as soon as possible.
   */
  public boolean isPsh() {
    if(! _isPshSet) {
      _isPsh = (getAllFlags() & TCP_PSH_MASK) != 0;
      _isPshSet = true;
    }
    return _isPsh;
  }

  private boolean _isRst;
  private boolean _isRstSet = false;
  /** 
   * Check the RST flag, flag indicates the session should be reset between
   * the sender and the receiver.
   */
  public boolean isRst() {
    if(! _isRstSet) {
      _isRst = (getAllFlags() & TCP_RST_MASK) != 0;
      _isRstSet = true;
    }
    return _isRst;
  }

  private boolean _isSyn;
  private boolean _isSynSet = false;
  /** 
   * Check the SYN flag, flag indicates the sequence numbers should
   * be synchronized between the sender and receiver to initiate
   * a connection.
   */
  public boolean isSyn() {
    if(! _isSynSet) {
      _isSyn = (getAllFlags() & TCP_SYN_MASK) != 0;
      _isSynSet = true;
    }
    return _isSyn;
  }

  private boolean _isFin;
  private boolean _isFinSet = false;
  /** 
   * Check the FIN flag, flag indicates the sender is finished sending.
   */
  public boolean isFin() {
    if(! _isFinSet) {
      _isFin = (getAllFlags () & TCP_FIN_MASK) != 0;
      _isFinSet = true;
    }
    return _isFin;
  }

  private byte[] _tcpHeaderBytes = null;
  /**
   * Fetch the TCP header a byte array.
   */
  public byte[] getTCPHeader() {
    if(_tcpHeaderBytes == null) {
      _tcpHeaderBytes =
	PacketEncoding.extractHeader(_ipOffset, getTcpHeaderLength(),
				     _bytes);
    }
    return _tcpHeaderBytes;
  }

  /** 
   * Fetch the TCP header as a byte array.
   */
  public byte[] getHeader() {
    return getTCPHeader();
  }

  private byte[] _tcpDataBytes = null;
  /** 
   * Fetch the TCP data as a byte array.
   */
  public byte[] getTCPData() {
    if(_tcpDataBytes == null) {
      // set data length based on info in headers (note: tcpdump
      //  can return extra junk bytes which bubble up to here
      _tcpDataBytes =
	PacketEncoding.extractData(_ipOffset, getTcpHeaderLength(),
				   _bytes, getPayloadDataLength());
    }
    return _tcpDataBytes;
  }

  /**
   * Fetch the TCP data as a byte array.
   */
  public byte[] getData() {
    return getTCPData();
  }


  /**
   * Convert this TCP packet to a readable string.
   */
  public String toString() {
    return toColoredString(false);
  }

  /**
   * Generate string with contents describing this TCP packet.
   * @param colored whether or not the string should contain ansi
   * color escape sequences.
   */
  public String toColoredString(boolean colored) {
    StringBuffer buffer = new StringBuffer();
    buffer.append('[');
    if(colored) buffer.append(getColor());
    buffer.append("TCPPacket");
    if(colored) buffer.append(AnsiEscapeSequences.RESET);
    buffer.append(": ");
    buffer.append(getSourceAddress());
    buffer.append('.');
    buffer.append(IPPort.getName(getSourcePort()));
    buffer.append(" -> ");
    buffer.append(getDestinationAddress());
    buffer.append('.');
    buffer.append(IPPort.getName(getDestinationPort()));
    if(isUrg()) 
      buffer.append(" urg[0x" + Integer.toHexString(getUrgentPointer()) + "]");
    if(isAck()) 
      buffer.append(" ack[0x" + 
                    Long.toHexString(getAcknowledgmentNumber()) + "]");
    if(isPsh()) buffer.append(" psh");
    if(isRst()) buffer.append(" rst");
    if(isSyn()) 
      buffer.append(" syn[0x" + 
                    Long.toHexString(getSequenceNumber()) + "]");
    if(isFin()) buffer.append(" fin");
    buffer.append(" l=" + getTCPHeaderLength() + "," +
                  getPayloadDataLength());
    buffer.append(']');

    return buffer.toString();
  }

  /**
   * Convert this TCP packet to a verbose.
   */
  public String toColoredVerboseString(boolean colored) {
    StringBuffer buffer = new StringBuffer();
    buffer.append('[');
    if(colored) buffer.append(getColor());
    buffer.append("TCPPacket");
    if(colored) buffer.append(AnsiEscapeSequences.RESET);
    buffer.append(": ");
    buffer.append("sport=" + getSourcePort() + ", ");
    buffer.append("dport=" + getDestinationPort() + ", ");
    buffer.append("seqn=0x" + Long.toHexString(getSequenceNumber()) + ", ");
    buffer.append("ackn=0x" + 
                  Long.toHexString(getAcknowledgmentNumber()) + ", ");
    buffer.append("hlen=" + getHeaderLength() + ", ");
    buffer.append("urg=" + isUrg() + ", ");
    buffer.append("ack=" + isAck() + ", ");
    buffer.append("psh=" + isPsh() + ", ");
    buffer.append("rst=" + isRst() + ", ");
    buffer.append("syn=" + isSyn() + ", ");
    buffer.append("fin=" + isFin() + ", ");
    buffer.append("wsize=" + getWindowSize() + ", ");
    buffer.append("sum=0x" + Integer.toHexString(getChecksum()) + ", ");
    buffer.append("uptr=0x" + Integer.toHexString(getUrgentPointer()));
    buffer.append(']');

    return buffer.toString();
  }

  /**
   * Fetch ascii escape sequence of the color associated with this packet type.
   */
  public String getColor() {
    return AnsiEscapeSequences.YELLOW;
  }


  private String _rcsid = 
    "$Id: TCPPacket.java,v 1.22 2004/05/05 23:14:45 pcharles Exp $";
}
