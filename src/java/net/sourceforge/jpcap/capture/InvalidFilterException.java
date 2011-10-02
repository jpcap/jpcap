// $Id: InvalidFilterException.java,v 1.2 2001/08/21 23:30:12 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.capture;


/**
 * This exception occurs when an error occurs while capturing data.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.2 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/08/21 23:30:12 $
 */
public class InvalidFilterException extends Exception
{
  /**
   * Create a new invalid filter exception.
   */
  public InvalidFilterException(String message) { 
    super(message);
  }


  private static String _rcsId = 
    "$Id: InvalidFilterException.java,v 1.2 2001/08/21 23:30:12 pcharles Exp $";
}
