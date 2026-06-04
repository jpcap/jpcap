/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.capture;

/**
 * Packet capture statistics.
 * <p>
 * Encapsulation for statistics collected by PacketCapture.
 *
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
    StringBuilder buffer = new StringBuilder();
    buffer.append("[PacketStatistics: ");
    buffer.append("received = " + receivedCount);
    buffer.append(", dropped = " + droppedCount);
    buffer.append(']');

    return buffer.toString();
  }

  private int receivedCount;
  private int droppedCount;
}
