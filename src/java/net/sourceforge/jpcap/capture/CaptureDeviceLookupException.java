// $Id: CaptureDeviceLookupException.java,v 1.1 2002/02/18 21:51:54 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Rex Tsai						   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.capture;


/**
 * This exception occurs when no capture devices are detected.
 *
 * @author Rex Tsai &gt;chihchun@kalug.linux.org.tw&lt;
 * @version $Revision: 1.1 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2002/02/18 21:51:54 $
 */
public class CaptureDeviceLookupException extends Exception
{
  /**
   * Create a new capture device not found exception.
   */
  public CaptureDeviceLookupException(String message) { 
    super(message);
  }


  private static String _rcsId = 
    "$Id: CaptureDeviceLookupException.java,v 1.1 2002/02/18 21:51:54 pcharles Exp $";
}
