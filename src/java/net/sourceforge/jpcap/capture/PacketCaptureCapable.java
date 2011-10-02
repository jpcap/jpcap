// $Id: PacketCaptureCapable.java,v 1.4 2003/06/24 20:30:18 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.capture;


/**
 * This is the packet capture interface. It is implemented by both the 
 * packet capture system (PacketCapture) and the simulator
 * (PacketCaptureSimulator).
 * <p>
 * The interface has two major components: methods that a client uses 
 * to register for packet events and methods that a client 
 * calls in order to setup and initiate packet capture.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.4 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2003/06/24 20:30:18 $
 */
public interface PacketCaptureCapable
{
  /**
   * Snapshot length. Maximum number of bytes per packet to capture.
   * For IPv4, 96 bytes guarantees that at least the headers of most 
   * packet types and protocols will get captured.
   * For IPv6, 68 is a better value?
   */
  int DEFAULT_SNAPLEN = 96;

  /**
   * Default capture timeout in milliseconds.
   */
  int DEFAULT_TIMEOUT = 1000;


  // capture component of interface

  /**
   * Open a network device for data capture. Throws an exception if 
   * the device name specified is invalid. Uses default values for 
   * the capture timeout and snaplen.
   *
   * @param device the name of the network device. 
   * Examples of valid network devices on linux are 'eth0' and 'ppp0'.
   * @param promiscuous whether or not the device should be opened in 
   * promiscuous mode.
   */
  void open(String device, boolean promiscuous)
    throws CaptureDeviceOpenException;

  /**
   * Open a network device for data capture.
   * 
   * @param device the name of the network device. 
   * Examples of valid network devices on linux are 'eth0' and 'ppp0'.
   * @param snaplen the 'snapshot' length. Defines the maximum number of 
   * bytes to save from each captured packet.
   * @param promiscuous whether or not the device should be opened in 
   * promiscuous mode.
   * @param timeout the packet capture timeout in milliseconds.
   */
  void open(String device, int snaplen, boolean promiscuous, int timeout) 
    throws CaptureDeviceOpenException;

 /**
   * Open a tcpdump-formatted savefile.
   * 
   * @param fileName the name of the savefile. 
   */
  void openOffline(String fileName) 
    throws CaptureFileOpenException;

  /**
   * Create, compile and activate a filter from a filter expression.
   *
   * @param filterExpression the filter expression. For example, 
   * the expression "host techno" would filter only packets sent or 
   * arriving at the host named techno.
   * @param optimize whether or not the resulting bpf code is optimized 
   * internally by libpcap.
   */
  void setFilter(String filterExpression, boolean optimize)
    throws InvalidFilterException;

  /**
   * Capture packets.
   *
   * @param count the number of packets to capture.
   * If count is negative, capture will block forever, unless an exception 
   * is thrown.
   */
  void capture(int count)
    throws CapturePacketException;

  /**
   * Fetch statistics on captured packets.
   * This method should not be called unless capture() was previously called.
   *
   * @return packet capture statistics.
   */
  CaptureStatistics getStatistics();

  /**
   * Close the capture device.
   */
  void close();


  // static native methods to fetch capture device and network information

  /**
   * Detect a network device suitable for packet capture.
   *
   * @return a string describing the network device. if no device can be 
   * found, null is returned.
   */
  String findDevice() 
    throws CaptureDeviceNotFoundException;

  /**
   * Fetch the network number for the specified device.
   *
   * @param device the name of the network device. 
   * @return the network address
   */
  int getNetwork(String device)
    throws CaptureConfigurationException;

  /**
   * Fetch the network mask for the specified device.
   *
   * @param device the name of the network device. 
   * @return the netmask address
   */
  int getNetmask(String device)
    throws CaptureConfigurationException;

  /**
   * Fetch the link layer type for the specified device.
   *
   * @return the link layer type code.
   */
  int getLinkLayerType()
    throws CaptureConfigurationException;

  /**
   * Get the snapshot length given that network device is open.
   *
   * @return the packet snapshot length.
   */
  int getSnapshotLength();

  // listener registration component of interface

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
