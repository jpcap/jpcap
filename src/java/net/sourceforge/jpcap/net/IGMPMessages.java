// $Id: IGMPMessages.java,v 1.1 2001/07/30 00:00:02 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;


/**
 * Code constants for IGMP message types.
 *
 * From RFC #2236.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.1 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/07/30 00:00:02 $
 */
public interface IGMPMessages
{
  /**
   * membership query.
   */
  int QUERY = 0x11;

  /**
   * v1 membership report.
   */
  int V1_REPORT = 0x12;

  /**
   * v2 membership report.
   */
  int V2_REPORT = 0x16;

  /**
   * Leave group.
   */
  int LEAVE = 0x17;
}
