// $Id: PacketEncoding.java,v 1.4 2002/07/10 23:18:20 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;


/**
 * Packet encoding.
 * <p>
 * Contains utility methods for decoding generic packets.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.4 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2002/07/10 23:18:20 $
 */
public class PacketEncoding
{
    
  // create an empty array to return whenever we need to return an, er,
  // empty array. this should be okay, because how can you mutate a 
  // 0-length array?
  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    
  /**
   * Extract a header from a packet.
   *
   * @param offset the offset in bytes to the start of the embedded header.
   * @param headerLen the length of the header embedded in the packet.
   * @param bytes the packet data, including the embedded header and data.
   * @return the extracted header data.
   */
  public static byte [] extractHeader(int offset, int headerLen, 
                                      byte [] bytes) {
    // null in = null out ?
    if( bytes == null ) return null;
    // negative in, empty array out
    if( ( offset < 0 ) || ( headerLen < 0 ) ) return EMPTY_BYTE_ARRAY;
    
    // verify that requested length is in the bounds of the array
    int useLen = ( headerLen <= (bytes.length-offset) ? 
		   headerLen : (bytes.length-offset) );
    // verify that requested offset is also in the bounds
    if( useLen <= 0 ) return EMPTY_BYTE_ARRAY;
                   
    byte [] header = new byte[useLen];
    System.arraycopy(bytes, offset, header, 0, useLen);
    return header;
  }

  /**
   * Extract data from a packet.
   *
   * @param offset the offset in bytes to the start of the embedded header.
   * @param headerLen the length of the header embedded in the packet.
   * @param bytes the packet data, including the embedded header and data.
   * @return the extracted packet data.
   */
  public static byte [] extractData(int offset, int headerLen, 
                                    byte [] bytes) {
    // null in = null out ?
    if( bytes == null ) return null;
    // negative in, empty array out
    if( ( offset < 0 ) || ( headerLen < 0 ) ) return EMPTY_BYTE_ARRAY;

    int dataLength = bytes.length - headerLen - offset;

    // check that requested datalength is valid.
    // (it may not be if packet values are invalid.)
    if(dataLength <= 0) return EMPTY_BYTE_ARRAY;

    // valid length, go for it dude
    byte [] data = new byte[dataLength];
    System.arraycopy(bytes, offset + headerLen, data, 0, dataLength);
    return data;
  }

  /**
   * Extract data from a packet.
   *
   * @param offset the offset in bytes to the start of the embedded header.
   * @param headerLen the length of the header embedded in the packet.
   * @param bytes the packet data, including the embedded header and data.
   * @return the extracted packet data.
   */
  public static byte [] extractData(int offset, int headerLen, 
                                    byte [] bytes,int dataLength) {
    // null in = null out ?
    if( bytes == null ) return null;
    // negative in, empty array out. request for no-data in, empty array out
    if( ( offset < 0 ) || ( headerLen < 0 ) ||
	( dataLength <= 0 ) || ( (offset+headerLen) > bytes.length ) )
      return EMPTY_BYTE_ARRAY;

    //-- make sure dataLength + offset + headerLen <= bytes.length
    if((dataLength + offset + headerLen) > bytes.length) {
      //-- adjust dataLength
      dataLength = bytes.length - headerLen - offset;
    }

    //-- valid length, go for it dude
    byte [] data = new byte[dataLength];
    System.arraycopy(bytes, offset + headerLen, data, 0, dataLength);
    return data;
  }

  private String _rcsid = 
    "$Id: PacketEncoding.java,v 1.4 2002/07/10 23:18:20 pcharles Exp $";
}
