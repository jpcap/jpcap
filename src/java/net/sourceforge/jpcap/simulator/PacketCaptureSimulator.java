// $Id: PacketCaptureSimulator.java,v 1.8 2002/02/18 21:52:31 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.simulator;

import net.sourceforge.jpcap.util.ArrayHelper;
import net.sourceforge.jpcap.net.LinkLayers;
import net.sourceforge.jpcap.capture.InvalidFilterException;
import net.sourceforge.jpcap.capture.PacketCaptureBase;
import net.sourceforge.jpcap.capture.PacketCaptureCapable;
import net.sourceforge.jpcap.capture.CaptureStatistics;
import net.sourceforge.jpcap.capture.CapturePacketException;
import net.sourceforge.jpcap.capture.CaptureConfigurationException;
import net.sourceforge.jpcap.capture.CaptureDeviceLookupException;
import net.sourceforge.jpcap.capture.CaptureDeviceOpenException;
import net.sourceforge.jpcap.capture.CaptureDeviceNotFoundException;


/**
 * This class has the same external interface as PacketCapture.
 * <p>
 * Instead of capturing and dispatching packets from a physical network
 * device, however, this class generates fake packets.
 * The type and frequency of the packets is set by policies configured in
 * the simulator property file.
 * <p>
 * Instances of this class are used mostly by developers working on
 * applications that utilize jpcap in environments where a real 
 * network device isn't available or when the type of packets arriving 
 * needs to be carefully controlled.
 * <p>
 * For more documentation on this class's methods, see PacketCaptureCapable;
 * Javadoc is 'inherited' from this interface.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.8 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2002/02/18 21:52:31 $
 */
public class PacketCaptureSimulator extends PacketCaptureBase
  implements PacketCaptureCapable
{
  public PacketCaptureSimulator() {
    // the simulator currently only simulates ethernet with link-level headers
    linkType = LinkLayers.EN10MB;
  }


  // methods for controlling a (simulated) packet capture session

  public void open(String device, boolean promiscuous)
   throws CaptureDeviceOpenException {
    open(device, DEFAULT_SNAPLEN, promiscuous, DEFAULT_TIMEOUT);
  }

  public void open(String device, int snaplen, 
                   boolean promiscuous, int timeout) {
    // noop
  }

  public void openOffline(String fileName) {
    // noop
  }

  public void setFilter(String filterExpression, boolean optimize)
    throws InvalidFilterException {
    // noop. the simulator could potentially parse the filter and 
    // modify the packet generator's engine accordingly. 
    // instead, though, prefer to control the generator's behavior
    // via the simulator properties. the complexities of bpf are 
    // currently beyond the scope of the simulator. :/
  }

  /**
   * The simulator's implementation of capture causes packets to be 
   * randomly generated. The packet frequency and type are configurable.
   */
  public void capture(int count)
    throws CapturePacketException {
    for(int i=0; i<count; i++) {
      byte [] bytes = PacketGenerator.generate();

      long millis = System.currentTimeMillis();
      int seconds = (int)(millis / 1000);
      int useconds = (int)(millis * 1000 - seconds * 1000 * 1000);

      handlePacket(bytes.length, bytes.length, seconds, useconds, bytes);
      receivedCount ++;
    }
  }

  public CaptureStatistics getStatistics() {
    return new CaptureStatistics(receivedCount, droppedCount);
  }

  public void close() {
    // noop
  }


  // static native methods to fetch capture device and network information

  public String findDevice()
    throws CaptureDeviceNotFoundException {
    return "jpcapsim0";
  }

  public String[] lookupDevices()
    throws CaptureDeviceLookupException {
    return new String [] {"jpcapsim0"};
  }

  public int getNetwork(String device)
    throws CaptureConfigurationException {
    return 0x00000000;
  }

  public int getNetmask(String device)
    throws CaptureConfigurationException {
    return 0x00000000;
  }

  public int getLinkLayerType()
    throws CaptureConfigurationException {
    return linkType;
  }

  public int getSnapshotLength() {
    return DEFAULT_SNAPLEN;
  }


  private String _rcsid = 
    "$Id: PacketCaptureSimulator.java,v 1.8 2002/02/18 21:52:31 pcharles Exp $";
}
