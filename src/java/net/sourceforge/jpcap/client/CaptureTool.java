// $Id: CaptureTool.java,v 1.20 2002/02/18 21:52:31 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.client;

import com.fooware.util.CommandLine;
import net.ultrametrics.console.TestConsole;
import net.sourceforge.jpcap.net.RawPacket;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.util.AsciiHelper;
import net.sourceforge.jpcap.util.AnsiEscapeSequences;
import net.sourceforge.jpcap.capture.RawPacketListener;
import net.sourceforge.jpcap.capture.PacketListener;
import net.sourceforge.jpcap.capture.CaptureStatistics;
import net.sourceforge.jpcap.capture.CapturePacketException;
import net.sourceforge.jpcap.capture.InvalidFilterException;
import net.sourceforge.jpcap.capture.CaptureDeviceOpenException;
import net.sourceforge.jpcap.capture.CaptureDeviceLookupException;
import net.sourceforge.jpcap.capture.CaptureConfigurationException;
import net.sourceforge.jpcap.capture.CaptureDeviceNotFoundException;
import net.sourceforge.jpcap.capture.PacketCaptureCapable;
import net.sourceforge.jpcap.capture.PacketCapture;
import net.sourceforge.jpcap.simulator.PacketCaptureSimulator;


/**
 * Tool with text console for controlling packet capture.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.20 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2002/02/18 21:52:31 $
 */
public class CaptureTool implements RawPacketListener, PacketListener
{
  /**
   * Create a new capture tool.
   */
  public CaptureTool() {
    initializeSystem();
    initializeConsole();
    autoDetect();

    // check the ui property
    if(! Settings.ENABLE_UI_DEFAULT) {
      // if the user has disabled the property, don't say anything
    }
    if(Settings.ENABLE_UI_DEFAULT)
      // attempt to enable the ui. suppressed if user has no graphics device.
      if(activateUi() == false)
        System.err.println("Graphical traffic view frame is suppressed.");
  }


  // implementation of RawPacketListener interface

  public void rawPacketArrived(RawPacket rawPacket) {
    if(mode.equals(MODE_RAW) || mode.equals(MODE_VERBOSE)) 
      System.err.println(rawPacket + " captured");
  }


  // implementation of PacketListener interface

  public void packetArrived(Packet packet) {
    if(mode.equals(MODE_OBJECT) || mode.equals(MODE_VERBOSE))
      // print the packet object
      System.err.println
        (packet.toColoredString(Settings.ENABLE_COLOR_CONSOLE));
    else if(mode.equals(MODE_TERSE)) { 
      // print a '.' for each packet received
      if(Settings.ENABLE_COLOR_CONSOLE) 
        System.err.print(packet.getColor());
      System.err.print(".");
      if(Settings.ENABLE_COLOR_CONSOLE)
        System.err.print(AnsiEscapeSequences.RESET);
    }
    else if(mode.equals(MODE_ASCII)) 
      // print the ascii contents of the packet
      packetToAscii(packet);
    else if(mode.equals(MODE_TEXT)) 
      // print the text contents of the packet
      packetToText(packet);

    // add the packet to the history buffer
    history.add(packet);

    // update the ui
    if(uiVisible)
      viewFrame.update(packet);
  }

  private static void packetToAscii(Packet packet) {
    String ascii = AsciiHelper.toString(packet.getData());
    if(ascii.length() > 0) {
      if(Settings.ENABLE_COLOR_CONSOLE)
        System.err.print(packet.getColor());
      System.err.print(ascii + "|");
      if(Settings.ENABLE_COLOR_CONSOLE)
        System.err.print(AnsiEscapeSequences.RESET);
    }
  }

  private static void packetToText(Packet packet) {
    String text = AsciiHelper.toText(packet.getData());
    if(text.length() > 0) {
      if(Settings.ENABLE_COLOR_CONSOLE)
        System.err.print(packet.getColor());
      System.err.print(text + "|");
      if(Settings.ENABLE_COLOR_CONSOLE)
        System.err.print(AnsiEscapeSequences.RESET);
    }
  }


  // implementation of tool logic

  /**
   * Initialize the packet capture system.
   */
  protected void initializeSystem() {
    if(Settings.USE_SIMULATOR == true) 
      packetCapture = new PacketCaptureSimulator();
    else 
      packetCapture = new PacketCapture();

    packetCapture.addRawPacketListener(this);
    packetCapture.addPacketListener(this);
  }

  /**
   * Attempt to detect and open a capture device.
   */
  protected boolean autoDetect() {
    try {
      System.err.println("attempting to find network device.. ");
      System.err.print("devices detected: ");
      String[] devs = PacketCapture.lookupDevices();
      for(int i = 0; i < devs.length ; i++)
	System.err.print(devs[i] + " ");
      System.err.println();

      device = packetCapture.findDevice();
      System.err.println("\nusing device '" + device + "'.");
    }
    catch(CaptureDeviceLookupException e) {
      System.err.println("Capture devices couldn't be detected!");
      System.err.println("You can't run this tool without a network device.");
      System.err.println("Please specify one with console commmand 'device'.");
      return false;
    }
    catch(CaptureDeviceNotFoundException e) {
      System.err.println("No suitable capture device could be found!");
      System.err.println("You can't run this tool without a network device.");
      System.err.println("Please specify one with console commmand 'device'.");
      return false;
    }
    try {
      setDevice(device);
    }
    catch(CaptureDeviceOpenException e) {
      System.err.println
        ("'" + device + "' was found but couldn't be properly opened.");
      System.err.println("Error: " + e.getMessage());
      System.err.println
        ("You may need to run this tool as root in order to capture packets.");
      // set the device name back to null, since it couldn't be opened
      device = null;
      return false;
    }

    return true;
  }

  /**
   * Initialize the tool's console.
   */
  protected void initializeConsole() {
    console = new CaptureConsole(this);
    // create the console's command-line processor
    CommandLine cl = new CommandLine(console); 
    cl.setPromptString("CaptureTool> ");
    cl.start();
  }

  /**
   * Reset the capture view frame to clear its contents.
   * @return whether or not the capture view frame was actually cleared.
   * The only time it should ever not be possible to clear it is when it 
   * does not exist (usu. because client has no graphics capability).
   */
  public boolean resetViewFrame() {
    if(viewFrame != null) {
      viewFrame.reset();
      return true;
    }
    return false;
  }

  /**
   * Clear the capture history.
   */
  public void clearHistory() {
    history.clear();
  }

  /**
   * Set the maximum size of the history FIFO.
   */
  public void setMaxHistorySize(int size) {
    history.setMaxSize(size);
  }

  /**
   * Dump the capture history buffer contents.
   */
  public int dumpHistory() {
    return history.dump(Settings.ENABLE_COLOR_CONSOLE);
  }

  private static void showUiFailure(String message) {
    System.err.println
      ("Your terminal doesn't seem to have graphics capabilities.");
    if(message != null)
      System.err.println(message);
  }

  /**
   * Active the graphical packet view.
   */
  public boolean activateUi() {
    if(uiVisible == false && viewFrame == null) {
      try {
        new java.awt.Frame();
      }
      catch(InternalError e) {
        // an internal error occurs if the display can't be opened
        showUiFailure(e.getMessage());
        return uiVisible;
      }
      catch(NoClassDefFoundError e) {
        // on platforms with no AWT, the Frame class isn't defined
        showUiFailure(e.getMessage());
        return uiVisible;
      }
      viewFrame = new CaptureViewFrame();
    }

    uiVisible = !uiVisible;
    viewFrame.setVisible(uiVisible);

    return uiVisible;
  }


  // PacketCapture wrappers

  /**
   * Fetch the network number for the current device.
   */
  public int getNetwork() throws CaptureConfigurationException {
    if(device == null)
      return 0;

    return packetCapture.getNetwork(device);
  }

  /**
   * Fetch the netmask for the current device.
   */
  public int getNetmask() throws CaptureConfigurationException {
    if(device == null)
      return 0;

    return packetCapture.getNetmask(device);
  }

  /**
   * Fetch the link-layer type.
   */
  public int getLinkLayerType() 
    throws CaptureConfigurationException, CaptureDeviceOpenException {
    // device must be open in order to fetch its link layer type
    packetCapture.open(device, snaplen, promiscuous, timeout);
    return packetCapture.getLinkLayerType();
  }

  /**
   * Set and open the capture device.
   */
  public void setDevice(String device) throws CaptureDeviceOpenException {
    // open the device as soon as it is specified to validate
    packetCapture.open(device, snaplen, promiscuous, timeout);
    this.device = device;
  }

  /**
   * Capture packets.
   */
  public CaptureStatistics capture(int count) 
    throws CaptureDeviceOpenException, 
           InvalidFilterException, 
           CapturePacketException {

    // open the device again, in case any settings have chang
    System.err.println("opening device '" + device + "'.. ");
    packetCapture.open(device, snaplen, promiscuous, timeout);

    // filter must be set every time the device is (re)opened
    System.err.println("setting filter '" + filter + "'.. ");
    packetCapture.setFilter(filter, true);

    System.err.println("waiting for " + count + " packet(s).. ");
    packetCapture.capture(count);

    return statistics = packetCapture.getStatistics();
  }


  // tool settings

  /**
   * Fetch the capture device name.
   */
  public String getDevice() {
    return device;
  }

  /**
   * Set the packet filter expression.
   */
  public void setFilter(String filter) throws InvalidFilterException {
    packetCapture.setFilter(filter, true);
    this.filter = filter;
  }

  /**
   * Fetch the filter expression.
   */
  public String getFilter() {
    return filter;
  }

  /**
   * Set the capture mode.
   */
  public void setMode(String mode) {
    this.mode = mode;
  }

  /**
   * Fetch the capture mode.
   */
  public String getMode() {
    return mode;
  }

  /**
   * Check if a proposed mode is valid.
   */
  public static boolean isModeValid(String pmode) {
    return 
      pmode.equals(MODE_RAW) || pmode.equals(MODE_OBJECT) ||
      pmode.equals(MODE_TERSE) || pmode.equals(MODE_VERBOSE) ||
      pmode.equals(MODE_ASCII) || pmode.equals(MODE_TEXT);
  }

  /**
   * Set the capture timeout value.
   */
  public void setTimeout(int timeout) throws CaptureDeviceOpenException {
    // validate the proposed timeout by opening the device with the new setting
    packetCapture.open(device, snaplen, promiscuous, timeout);
    this.timeout = timeout;
  }

  /**
   * Fetch the capture timeout value.
   */
  public int getTimeout() {
    return timeout;
  }

  /**
   * Set the snapshot length.
   */
  public void setSnaplen(int snaplen) throws CaptureDeviceOpenException {
    // validate the proposed snaplen by opening the device with the new setting
    packetCapture.open(device, snaplen, promiscuous, timeout);
    this.snaplen = snaplen;
  }

  /**
   * Fetch the snapshot length.
   */
  public int getSnaplen() {
    return snaplen;
  }

  /**
   * Fetch a reference to the history buffer.
   */
  public CaptureHistory getHistory() {
    return history;
  }


  /**
   * Packet capture.
   */
  public static void main(String [] args) {
    CaptureTool captureTool = new CaptureTool();
  }


  /**
   * The console handling commands for this capture tool.
   */
  CaptureConsole console;

  /**
   * Packet capture system.
   */
  PacketCaptureCapable packetCapture;

  /**
   * Captured packet history.
   */
  CaptureHistory history = new CaptureHistory();

  /**
   * Capture statistics.
   */
  CaptureStatistics statistics;

  /**
   * Graphical view of captured packets.
   */
  CaptureViewFrame viewFrame;

  /**
   * Whether or not the ui is visible.
   */
  boolean uiVisible = false;

  // capture modes..
  protected static final String MODE_RAW = "raw";
  protected static final String MODE_OBJECT = "object";
  protected static final String MODE_VERBOSE = "verbose";
  protected static final String MODE_TERSE = "terse";
  protected static final String MODE_ASCII = "ascii";
  protected static final String MODE_TEXT = "text";

  /**
   * Capture mode.
   */
  private String mode = MODE_OBJECT;

  /**
   * Packet capture device name.
   */
  private String device = null;

  /**
   * packet filter expression.
   */
  private String filter = "";

  /**
   * snapshot length in bytes.
   */
  private int snaplen = PacketCapture.DEFAULT_SNAPLEN;

  /**
   * capture timeout in ms.
   */
  private int timeout = PacketCapture.DEFAULT_TIMEOUT;

  /**
   * whether or not to capture in promiscuous mode.
   */
  private boolean promiscuous = true;

  private String _rcsid = 
    "$Id: CaptureTool.java,v 1.20 2002/02/18 21:52:31 pcharles Exp $";
}
