/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.capture;

import net.sourceforge.jpcap.net.RawPacket;

/**
 * Raw packet data listener.
 * <p>
 * Applications interested in listening for raw packet data must register
 * with PacketCapture and implement RawPacketDataListener.
 *
 */
public interface RawPacketListener
{
  void rawPacketArrived(RawPacket rawPacket);
}
