/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.capture;

import net.sourceforge.jpcap.net.Packet;

/**
 * Packet data listener.
 * <p>
 * Applications interested in listening for packets must register
 * with PacketCapture and implement PacketDataListener.
 *
 */
public interface PacketListener
{
  void packetArrived(Packet packet);
}
