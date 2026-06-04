/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.capture;

/**
 * An interface for classes capable of dispatching captured network 
 * packets.
 *
 */
public interface PacketDispatchCapable
{
  /**
   * Register a raw packet listener with this capture system.
   * @param rawListener the raw packet listener to add to the notification 
   * list.
   */
  void addRawPacketListener(RawPacketListener rawListener);

  /**
   * Deregister a raw packet listener from this capture system.
   * @param rawListener the raw packet listener to remove from the 
   * notification list.
   */
  void removeRawPacketListener(RawPacketListener rawListener);

  /**
   * Register a packet object listener with this capture system.
   * @param objListener the packet listener to add to the notification list.
   */
  void addPacketListener(PacketListener objListener);

  /**
   * Deregister a packet object listener from this capture system.
   * @param objListener the packet listener to remove from the 
   * notification list.
   */
  void removePacketListener(PacketListener objListener);
}
