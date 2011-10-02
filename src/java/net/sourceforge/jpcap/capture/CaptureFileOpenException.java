//  $Id: CaptureFileOpenException.java,v 1.1 2001/12/30 23:07:00 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Christopher Balcerek                                *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package net.sourceforge.jpcap.capture;

/**
 * This exception occurs when the savefile cannot be opened.
 *
 * @author Christopher Balcerek
 * @version $Revision: 1.1 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/12/30 23:07:00 $
 */
public class CaptureFileOpenException extends Exception
{
  /**
   * Create a new invalid capture file exception.
   */
  public CaptureFileOpenException(String message) { 
    super(message);
  }


  private static String _rcsId = 
    "$Id: CaptureFileOpenException.java,v 1.1 2001/12/30 23:07:00 pcharles Exp $";
}
