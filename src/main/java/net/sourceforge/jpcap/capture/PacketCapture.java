/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 *
 * Modernised 2026: native libpcap JNI binding replaced with Pcap4J.
 */
package net.sourceforge.jpcap.capture;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.PcapStat;
import org.pcap4j.core.Pcaps;

/**
 * Core packet capture entry point.
 * <p>
 * Implements {@link PacketCaptureCapable} by delegating to the Pcap4J library,
 * which itself wraps libpcap/WinPcap/Npcap through JNA. The original jpcap
 * JNI shared library is no longer required.
 * <p>
 * Implement {@link PacketListener} or {@link RawPacketListener} and register
 * with an instance of this class. {@link #capture(int)} blocks until the
 * requested number of packets has been delivered; call {@link #endCapture()}
 * from another thread to break out early.
 */
public class PacketCapture extends PacketCaptureBase
    implements PacketCaptureCapable {

    private final AtomicReference<PcapHandle> handle = new AtomicReference<>();
    private volatile int snapLen = DEFAULT_SNAPLEN;

    public PacketCapture() {
    }

    // ---------------------------------------------------------------------
    // open / setFilter / capture / close
    // ---------------------------------------------------------------------

    @Override
    public void open(String device, boolean promiscuous)
        throws CaptureDeviceOpenException {
        open(device, DEFAULT_SNAPLEN, promiscuous, DEFAULT_TIMEOUT);
    }

    @Override
    public void open(String device, int snaplen,
                     boolean promiscuous, int timeout)
        throws CaptureDeviceOpenException {
        try {
            PcapNetworkInterface nif = Pcaps.getDevByName(device);
            if (nif == null) {
                throw new CaptureDeviceOpenException(
                    "No such device: " + device);
            }
            PromiscuousMode mode = promiscuous
                ? PromiscuousMode.PROMISCUOUS
                : PromiscuousMode.NONPROMISCUOUS;
            PcapHandle h = nif.openLive(snaplen, mode, timeout);
            installHandle(h, snaplen);
        } catch (PcapNativeException e) {
            throw new CaptureDeviceOpenException(e.getMessage());
        }
    }

    @Override
    public void openOffline(String fileName) throws CaptureFileOpenException {
        try {
            PcapHandle h = Pcaps.openOffline(fileName);
            installHandle(h, snapshotLengthOf(h));
        } catch (PcapNativeException e) {
            throw new CaptureFileOpenException(e.getMessage());
        }
    }

    private static int snapshotLengthOf(PcapHandle h) {
        try {
            return h.getSnapshot();
        } catch (NotOpenException e) {
            return DEFAULT_SNAPLEN;
        }
    }

    private void installHandle(PcapHandle h, int snaplen) {
        PcapHandle previous = handle.getAndSet(h);
        if (previous != null && previous.isOpen()) {
            previous.close();
        }
        this.snapLen = snaplen;
        this.linkType = h.getDlt().value();
    }

    @Override
    public void setFilter(String filterExpression, boolean optimize)
        throws InvalidFilterException {
        PcapHandle h = requireHandle();
        try {
            h.setFilter(filterExpression,
                optimize ? BpfCompileMode.OPTIMIZE : BpfCompileMode.NONOPTIMIZE);
        } catch (PcapNativeException | NotOpenException e) {
            throw new InvalidFilterException(e.getMessage());
        }
    }

    @Override
    public void capture(int count) throws CapturePacketException {
        PcapHandle h = requireHandle();
        org.pcap4j.core.RawPacketListener delegate = bytes -> {
            java.sql.Timestamp ts = h.getTimestamp();
            long millis = ts == null ? System.currentTimeMillis() : ts.getTime();
            int micros = ts == null ? 0 : ts.getNanos() / 1000;
            handlePacket(bytes.length, bytes.length,
                (int) (millis / 1000L), micros, bytes);
        };
        try {
            h.loop(count, delegate);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CapturePacketException("Interrupted: " + e.getMessage());
        } catch (PcapNativeException | NotOpenException e) {
            throw new CapturePacketException(e.getMessage());
        }
    }

    @Override
    public CaptureStatistics getStatistics() {
        PcapHandle h = handle.get();
        if (h == null) {
            return new CaptureStatistics(receivedCount, droppedCount);
        }
        try {
            PcapStat stat = h.getStats();
            receivedCount = (int) stat.getNumPacketsReceived();
            droppedCount = (int) stat.getNumPacketsDropped();
        } catch (PcapNativeException | NotOpenException ignored) {
            // savefiles and closed handles fall through with last known values
        }
        return new CaptureStatistics(receivedCount, droppedCount);
    }

    @Override
    public void close() {
        PcapHandle h = handle.getAndSet(null);
        if (h != null && h.isOpen()) {
            h.close();
        }
    }

    public void endCapture() {
        PcapHandle h = handle.get();
        if (h != null && h.isOpen()) {
            try {
                h.breakLoop();
            } catch (NotOpenException ignored) {
                // already closed -- nothing to do
            }
        }
    }

    // ---------------------------------------------------------------------
    // device / network info
    // ---------------------------------------------------------------------

    @Override
    public String findDevice() throws CaptureDeviceNotFoundException {
        try {
            List<PcapNetworkInterface> devs = Pcaps.findAllDevs();
            if (devs == null || devs.isEmpty()) {
                throw new CaptureDeviceNotFoundException(
                    "No capture devices found");
            }
            for (PcapNetworkInterface nif : devs) {
                if (!nif.isLoopBack() && nif.isUp()
                    && !nif.getAddresses().isEmpty()) {
                    return nif.getName();
                }
            }
            return devs.get(0).getName();
        } catch (PcapNativeException e) {
            throw new CaptureDeviceNotFoundException(e.getMessage());
        }
    }

    public static String[] lookupDevices() throws CaptureDeviceLookupException {
        try {
            List<PcapNetworkInterface> devs = Pcaps.findAllDevs();
            if (devs == null) {
                return new String[0];
            }
            return devs.stream()
                .map(PcapNetworkInterface::getName)
                .toArray(String[]::new);
        } catch (PcapNativeException e) {
            throw new CaptureDeviceLookupException(e.getMessage());
        }
    }

    @Override
    public int getNetwork(String device) throws CaptureConfigurationException {
        return inet4ToInt(findFirstAddress(device).getAddress());
    }

    @Override
    public int getNetmask(String device) throws CaptureConfigurationException {
        return inet4ToInt(findFirstAddress(device).getNetmask());
    }

    private PcapAddress findFirstAddress(String device)
        throws CaptureConfigurationException {
        try {
            PcapNetworkInterface nif = Pcaps.getDevByName(device);
            if (nif == null) {
                throw new CaptureConfigurationException(
                    "No such device: " + device);
            }
            for (PcapAddress addr : nif.getAddresses()) {
                if (addr.getAddress() instanceof Inet4Address) {
                    return addr;
                }
            }
            throw new CaptureConfigurationException(
                "No IPv4 address bound to " + device);
        } catch (PcapNativeException e) {
            throw new CaptureConfigurationException(e.getMessage());
        }
    }

    private static int inet4ToInt(InetAddress address) {
        if (address == null) {
            return 0;
        }
        byte[] octets = address.getAddress();
        return ByteBuffer.wrap(octets).order(ByteOrder.BIG_ENDIAN).getInt();
    }

    @Override
    public int getLinkLayerType() throws CaptureConfigurationException {
        PcapHandle h = handle.get();
        if (h == null) {
            throw new CaptureConfigurationException("Capture not open");
        }
        return h.getDlt().value();
    }

    @Override
    public int getSnapshotLength() {
        PcapHandle h = handle.get();
        return h == null ? snapLen : snapshotLengthOf(h);
    }

    private PcapHandle requireHandle() {
        PcapHandle h = handle.get();
        if (h == null) {
            throw new IllegalStateException(
                "open() must be called before this operation");
        }
        return h;
    }
}
