/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.net;

import net.sourceforge.jpcap.util.HexHelper;
import net.sourceforge.jpcap.util.Timeval;
import java.io.Serializable;

/**
 * A captured packet containing raw data.
 * <p>
 * Encapsulation for data captured on a network device by PacketCapture's
 * raw capture interface.
 *
 */
public class RawPacket implements Serializable
{
  /**
   * Create a new raw packet.
   *
   * @param timeval the time the packet arrived on the device where it was 
   * captured.
   * @param bytes the raw packet data, including headers.
   * @param droplen the number of bytes dropped (if any) when the packet 
   * was captured.
   */
  public RawPacket(Timeval timeval, byte [] bytes, int droplen) {
    this.timeval = timeval;
    this.bytes = bytes;
    this.droplen = droplen;
  }

  /**
   * Fetch the timeval containing the time the packet arrived on the 
   * device where it was captured.
   */
  public Timeval getTimeval() {
    return timeval;
  }

  /**
   * Fetch the raw packet data.
   */
  public byte [] getData() {
    return bytes;
  }

  /**
   * Fetch the number of bytes dropped (if any) when the packet 
   * was captured.
   * <p>
   * Bytes are dropped when the snapshot length (a ceiling on the number of 
   * bytes per packet to capture) is smaller than the actual number of bytes
   * in the packet on the wire. In other words, when caplen exceeds snaplen,
   * bytes are dropped and droplen will be nonzero. Otherwise, all the 
   * packet bytes were captured and droplen is zero.
   */
  public int getDroplen() {
    return droplen;
  }

  /**
   * Convert this packet to a readable string.
   */
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    int length = bytes.length;
    buffer.append("[RawPacket: ");
    buffer.append("l = " + length + " of " + (length + droplen) + ", ");
    buffer.append("t = " + timeval + ", ");
    buffer.append("d = ");
    buffer.append(HexHelper.toString(bytes));
    buffer.append(']');

    return buffer.toString();
  }

  private Timeval timeval;
  private byte [] bytes;
  private int droplen;
}
