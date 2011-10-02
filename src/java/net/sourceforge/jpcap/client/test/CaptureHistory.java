// $Id: CaptureHistory.java,v 1.5 2004/02/24 19:21:06 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.client;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import net.sourceforge.jpcap.net.Packet;


/**
 * Captured packet history.
 * <p>
 * If a size is specified, the history acts as a FIFO.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.5 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/02/24 19:21:06 $
 */
public class CaptureHistory
{
  /**
   * Create a new bounded capture history buffer.
   * The history will not grow beyond the number of packets specified.
   *
   * @param maxSize the maximum number of packets to remember.
   * Oldest packets are discarded first.
   */
  public CaptureHistory(int maxSize) {
    this.maxSize = maxSize;
  }

  /**
   * Create a new unbounded capture history buffer.
   * The history buffer will grow indefinitely (read until the memory of 
   * the host is exhausted.)
   *
   * Oldest packets are discarded first.
   */
  public CaptureHistory() {
    this(UNBOUNDED);
  }

  /**
   * Set the size of the history.
   * If the history buffer is already larger than the specified size, 
   * old packets will be discarded until it shrinks to the specified size.
   */
  public void setMaxSize(int maxSize) {
    this.maxSize = maxSize;

    // ignore invalid size requests..
    if(maxSize < 0) 
      return;

    trim(false);
  }

  public int getMaxSize() {
    return maxSize;
  }

  /**
   * Add the most recent packet to the history collection.
   */
  public boolean add(Packet packet) {
    trim(true);

    return packets.add(packet);
  }

  /**
   * Fetch a packet from a specific location in the history.
   */
  public Packet get(int index) {
    return (Packet)packets.get(index);
  }

  /**
   * Fetch the full history as a packet collection.
   */
  public Collection getCollection() {
    return packets;
  }

  /**
   * Clear all the packets in the history collection.
   */
  public void clear() {
    packets.clear();
  }

  /**
   * Fetch the number of packets in the history collection.
   */
  public int size() {
    return packets.size();
  }

  /**
   * Dump the history buffer contents to stdout.
   */
  public int dump(boolean colored) {
    Iterator i = packets.iterator();
    int count = 0;
    while(i.hasNext())
      System.err.println((count ++) + " - " + 
                         ((Packet)i.next()).toColoredString(colored));

    return count;
  }

  /**
   * Trim the capture history by removing the oldest packets, until 
   * the size is equal to the maximum allowed history buffer size.
   *
   * @param eventPendingFlag indicates whether or not to trim the buffer
   * one smaller than the maximum allowed size.
   */
  private void trim(boolean eventPendingFlag) {
    if(maxSize != UNBOUNDED) {
      while(packets.size() > maxSize)
        packets.remove(0); // remove the oldest packet

      // trim one smaller than the allowed size to prime for pending insert..
      if(eventPendingFlag && packets.size() == maxSize)
        packets.remove(0);
    }
  }    


  static int UNBOUNDED = -1;
  int maxSize = UNBOUNDED;
  ArrayList packets = new ArrayList();

  private String _rcsid = 
    "$Id: CaptureHistory.java,v 1.5 2004/02/24 19:21:06 pcharles Exp $";
}
