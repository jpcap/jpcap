// $Id: ArrayHelper.java,v 1.9 2004/09/28 17:31:37 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.util;


/**
 * Utility functions for populating and manipulating arrays.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.9 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/09/28 17:31:37 $
 */
public class ArrayHelper
{
  /**
   * Join two arrays.
   */
  public static byte [] join(byte [] a, byte [] b) {
    byte [] bytes = new byte[a.length + b.length];

    System.arraycopy(a, 0, bytes, 0, a.length);
    System.arraycopy(b, 0, bytes, a.length, b.length);

    return bytes;
  }

  /**
   * Extract a long from a byte array.
   *
   * @param bytes an array.
   * @param pos the starting position where the integer is stored.
   * @param cnt the number of bytes which contain the integer.
   * @return the long, or <b>0</b> if the index/length to use 
   *         would cause an ArrayOutOfBoundsException
   */
  public static long extractLong(byte[] bytes, int pos, int cnt) {
    // commented out because it seems like it might mask a fundamental 
    // problem, if the caller is referencing positions out of bounds and 
    // silently getting back '0'.
    //   if((pos + cnt) > bytes.length) return 0;
    long value = 0;
    for(int i=0; i<cnt; i++)
      value |= ((bytes[pos + cnt - i - 1] & 0xffL) << 8 * i);

    return value;
  }

  /**
   * Extract an integer from a byte array.
   *
   * @param bytes an array.
   * @param pos the starting position where the integer is stored.
   * @param cnt the number of bytes which contain the integer.
   * @return the integer, or <b>0</b> if the index/length to use 
   *         would cause an ArrayOutOfBoundsException
   */
  public static int extractInteger(byte[] bytes, int pos, int cnt) {
    // commented out because it seems like it might mask a fundamental 
    // problem, if the caller is referencing positions out of bounds and 
    // silently getting back '0'.
    // if((pos + cnt) > bytes.length) return 0;
    int value = 0;
    for(int i=0; i<cnt; i++)
      value |= ((bytes[pos + cnt - i - 1] & 0xff) << 8 * i);

    return value;
  }

  /**
   * Insert data contained in a long integer into an array.
   *
   * @param bytes an array.
   * @param value the long to insert into the array.
   * @param pos the starting position into which the long is inserted.
   * @param cnt the number of bytes to insert.
   */
  public static void insertLong(byte[] bytes, long value, int pos, int cnt) {
    for(int i=0; i<cnt; i++) {
      bytes[pos + cnt - i - 1] = (byte)(value & 0xff);
      value >>= 8;
    }
  }

  /**
   * Convert a long integer into an array of bytes.
   * 
   * @param value the long to convert.
   * @param cnt the number of bytes to convert.
   */
  public static byte [] toBytes(long value, int cnt) {
    byte [] bytes = new byte[cnt];
    for(int i=0; i<cnt; i++) {
      bytes[cnt - i - 1] = (byte)(value & 0xff);
      value >>= 8;
    }

    return bytes;
  }

  /**
   * Convert a long integer into an array of bytes, little endian format. 
   * (i.e. this does the same thing as toBytes() but returns an array 
   * in reverse order from the array returned in toBytes().
   * @param value the long to convert.
   * @param cnt the number of bytes to convert.
   */
  public static byte [] toBytesLittleEndian(long value, int cnt) {
    byte [] bytes = new byte[cnt];
    for(int i=0; i<cnt; i++) {
      bytes[i] = (byte)(value & 0xff);
      value >>= 8;
    }

    return bytes;
  }

  public static void fillBytes(byte[] byteArray, long value, 
                               int cnt, int index) {

    for(int i=0; i<cnt; i++) {
      byteArray[cnt - i - 1 + index] = (byte)(value & 0xff);
      value >>= 8;
    }
  }

  public static void fillBytesLittleEndian(byte[] byteArray, long value, 
                                           int cnt, int index) {
    for(int i=0; i<cnt; i++) {
      byteArray[index+i] = (byte)(value & 0xff);
      value >>= 8;
    }
  }

  static final String _rcsid = 
    "$Id: ArrayHelper.java,v 1.9 2004/09/28 17:31:37 pcharles Exp $";
}
