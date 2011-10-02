// $Id: PacketDispatcher.java,v 1.3 2004/10/02 01:23:19 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.capture;

import java.util.Iterator;
import java.util.HashSet;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.RawPacket;


/**
 * This class encapsulates a mechanism for dispatching network data and 
 * packets to a listener who has subscribed to such events.
 * <p>
 * Since this class doesn't contain an implementation for handling 
 * real network data, it is abstract.
 * <p>
 * This class is extended by packet capture systems which, although 
 * they may utilize different sources of packet data, share a common
 * mechanism for dispatching the packets.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.3 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/10/02 01:23:19 $
 */
public abstract class PacketDispatcher implements PacketDispatchCapable
{
  /**
   * Register a raw packet listener with this capture system.
   * @param rawListener the raw packet listener to add to the notification 
   * list.
   */
  public void addRawPacketListener(RawPacketListener rawListener) {
    rawListeners.add(rawListener);
  }

  /**
   * Deregister a raw packet listener from this capture system.
   * @param rawListener the raw packet listener to remove from the 
   * notification list.
   */
  public void removeRawPacketListener(RawPacketListener rawListener) {
    rawListeners.remove(rawListener);
  }

  /**
   * Register a packet object listener with this capture system.
   * @param objListener the packet listener to add to the notification list.
   */
  public void addPacketListener(PacketListener objListener) {
    objListeners.add(objListener);
  }

  /**
   * Deregister a packet object listener from this capture system.
   * @param objListener the packet listener to remove from the 
   * notification list.
   */
  public void removePacketListener(PacketListener objListener) {
    objListeners.remove(objListener);
  }

  /**
   * Dispatch a raw packet to all registered listeners.
   */
  public void dispatchRawPacket(RawPacket rawPacket) {
    Iterator i = rawListeners.iterator();
    while(i.hasNext()) {
      RawPacketListener pl = (RawPacketListener)i.next();
      // System.err.println(NAME + ": calling registered listener " + pl);
      pl.rawPacketArrived(rawPacket);
    }
  }

  /**
   * Dispatch a packet to all registered listeners.
   */
  public void dispatchPacket(Packet packet) {
    Iterator i = objListeners.iterator();
    while(i.hasNext()) {
      PacketListener pl = (PacketListener)i.next();
      // System.err.println(NAME + ": calling registered listener " + pl);
      pl.packetArrived(packet);
    }
  }


  /**
   * Registered clients listening for raw packets captured by this object.
   */
  //jdk1.5: HashSet <RawPacketListener> rawListeners = new HashSet<RawPacketListener>();
  HashSet rawListeners = new HashSet();

  /**
   * Registered clients listening for packet objects captured by this object.
   */
  //jdk1.5: HashSet <PacketListener> objListeners = new HashSet<PacketListener>();
  HashSet objListeners = new HashSet();

  private String _rcsid = 
    "$Id: PacketDispatcher.java,v 1.3 2004/10/02 01:23:19 pcharles Exp $";
}
