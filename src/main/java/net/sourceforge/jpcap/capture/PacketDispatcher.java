/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.capture;

import java.util.concurrent.CopyOnWriteArraySet;

import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.RawPacket;

/**
 * Mechanism for dispatching captured data and parsed packets to listeners.
 * <p>
 * Concrete subclasses provide the source of packets; this class only handles
 * registration and fan-out. Listener collections are thread-safe so the
 * native capture thread can dispatch while the application thread mutates
 * the subscriber set.
 */
public abstract class PacketDispatcher implements PacketDispatchCapable {

    private final CopyOnWriteArraySet<RawPacketListener> rawListeners =
        new CopyOnWriteArraySet<>();
    private final CopyOnWriteArraySet<PacketListener> objListeners =
        new CopyOnWriteArraySet<>();

    @Override
    public void addRawPacketListener(RawPacketListener rawListener) {
        rawListeners.add(rawListener);
    }

    @Override
    public void removeRawPacketListener(RawPacketListener rawListener) {
        rawListeners.remove(rawListener);
    }

    @Override
    public void addPacketListener(PacketListener objListener) {
        objListeners.add(objListener);
    }

    @Override
    public void removePacketListener(PacketListener objListener) {
        objListeners.remove(objListener);
    }

    public void dispatchRawPacket(RawPacket rawPacket) {
        for (RawPacketListener listener : rawListeners) {
            listener.rawPacketArrived(rawPacket);
        }
    }

    public void dispatchPacket(Packet packet) {
        for (PacketListener listener : objListeners) {
            listener.packetArrived(packet);
        }
    }
}
