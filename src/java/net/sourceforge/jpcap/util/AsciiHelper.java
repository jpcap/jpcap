// $Id: AsciiHelper.java,v 1.1 2001/06/14 21:19:58 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.util;

import java.io.StringWriter;


/**
 * Functions for formatting and printing binary data as ascii.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.1 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/06/14 21:19:58 $
 */
public class AsciiHelper
{
  /**
   * Returns a text representation of a byte array.
   * Bytes in the array which don't convert to text in the range a..Z
   * are dropped.
   *
   * @param bytes a byte array
   * @return a string containing the text equivalent of the bytes.
   */
  public static String toText(byte [] bytes) {
    StringWriter sw = new StringWriter();

    int length = bytes.length;
    if(length > 0) {
      for(int i = 0; i < length; i++) {
        byte b = bytes [i];
        if(b > 64 && b < 91 || b > 96 && b < 123)
          sw.write((char)b);
      }
    }
    return(sw.toString());
  }

  /**
   * Returns a text representation of a byte array.
   * Bytes in the array which don't convert to printable ascii characters
   * are dropped.
   *
   * @param bytes a byte array
   * @return a string containing the ascii equivalent of the bytes.
   */
  public static String toString(byte [] bytes) {
    StringWriter sw = new StringWriter();

    int length = bytes.length;
    if(length > 0) {
      for(int i = 0; i < length; i++) {
        byte b = bytes [i];
        if(b > 32 && b < 127)
          sw.write((char)b);
      }
    }
    return(sw.toString());
  }


  static final String _rcsid = 
    "$Id: AsciiHelper.java,v 1.1 2001/06/14 21:19:58 pcharles Exp $";
}
