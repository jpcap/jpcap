// $Id: CaptureConfigurationException.java,v 1.1 2001/05/17 20:13:51 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.capture;


/**
 * This exception occurs when the capture client tries to 
 * specify a capture device that does not exist or if the capture
 * device specified is illegal.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.1 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/05/17 20:13:51 $
 */
public class CaptureConfigurationException extends Exception
{
  /**
   * Create a new invalid capture device exception.
   */
  public CaptureConfigurationException(String message) { 
    super(message);
  }


  private static String _rcsId = 
    "$Id: CaptureConfigurationException.java,v 1.1 2001/05/17 20:13:51 pcharles Exp $";
}
