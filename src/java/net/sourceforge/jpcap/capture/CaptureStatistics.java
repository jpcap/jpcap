// $Id: CaptureStatistics.java,v 1.1 2001/05/17 20:13:51 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.capture;


/**
 * Packet capture statistics.
 * <p>
 * Encapsulation for statistics collected by PacketCapture.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.1 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/05/17 20:13:51 $
 */
public class CaptureStatistics
{
  /**
   * Create a new and empty statistics container.
   */
  public CaptureStatistics(int receivedCount, int droppedCount) {
    this.receivedCount = receivedCount;
    this.droppedCount = droppedCount;
  }

  public int getReceivedCount() {
    return receivedCount;
  }

  public int getDroppedCount() {
    return droppedCount;
  }


  /**
   * Convert this packet to a readable string.
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[PacketStatistics: ");
    buffer.append("received = " + receivedCount);
    buffer.append(", dropped = " + droppedCount);
    buffer.append(']');

    return buffer.toString();
  }


  private int receivedCount;
  private int droppedCount;

  private String _rcsid = 
    "$Id: CaptureStatistics.java,v 1.1 2001/05/17 20:13:51 pcharles Exp $";
}
