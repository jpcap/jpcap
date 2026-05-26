/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.capture;

import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.PacketFactory;
import net.sourceforge.jpcap.net.RawPacket;
import net.sourceforge.jpcap.util.Timeval;

/**
 * Common functionality shared by the live capture system and the simulator.
 * <p>
 * Subclasses feed bytes into {@link #handlePacket} and the bytes are turned
 * into {@link RawPacket}/{@link Packet} objects and dispatched to listeners.
 */
public abstract class PacketCaptureBase
    extends PacketDispatcher implements PacketHandler {

    /** Link-type for the currently open device (libpcap DLT_* code). */
    public int linkType;

    /** Running counters; populated by {@link PacketCapture#getStatistics()}. */
    public int receivedCount = 0;
    public int droppedCount = 0;

    @Override
    public void handlePacket(int length, int caplen,
                             int seconds, int useconds, byte[] data) {
        Timeval tv = new Timeval(seconds, useconds);
        int truncated = length > caplen ? length - caplen : 0;
        RawPacket raw = new RawPacket(tv, data, truncated);
        Packet packet = PacketFactory.dataToPacket(linkType, data, tv);

        dispatchRawPacket(raw);
        dispatchPacket(packet);
    }
}
