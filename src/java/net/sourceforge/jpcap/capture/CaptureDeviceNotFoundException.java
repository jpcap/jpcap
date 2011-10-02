// $Id: CaptureDeviceNotFoundException.java,v 1.1 2001/05/17 20:13:51 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.capture;


/**
 * This exception occurs when no capture devices are detected.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.1 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/05/17 20:13:51 $
 */
public class CaptureDeviceNotFoundException extends Exception
{
  /**
   * Create a new capture device not found exception.
   */
  public CaptureDeviceNotFoundException(String message) { 
    super(message);
  }


  private static String _rcsId = 
    "$Id: CaptureDeviceNotFoundException.java,v 1.1 2001/05/17 20:13:51 pcharles Exp $";
}
