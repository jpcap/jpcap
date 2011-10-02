// $Id: CaptureConsole.java,v 1.17 2004/05/06 20:08:29 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.client;

import java.util.Collection;
import java.util.HashSet;
import net.ultrametrics.console.TestConsole;
import net.sourceforge.jpcap.util.HexHelper;
import net.sourceforge.jpcap.net.LinkLayer;
import net.sourceforge.jpcap.capture.CaptureStatistics;
import net.sourceforge.jpcap.capture.PacketCapture;
import net.sourceforge.jpcap.capture.CapturePacketException;
import net.sourceforge.jpcap.capture.InvalidFilterException;
import net.sourceforge.jpcap.capture.CaptureDeviceOpenException;
import net.sourceforge.jpcap.capture.CaptureDeviceLookupException;
import net.sourceforge.jpcap.capture.CaptureConfigurationException;


/**
 * Text console for controlling packet capture.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.17 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/05/06 20:08:29 $
 */
public class CaptureConsole extends TestConsole
{
  /**
   * Construct a new capture console.
   * @param captureTool the capture tool service handle.
   */
  public CaptureConsole(CaptureTool captureTool) {
    this.captureTool = captureTool;
  }

  /**
   * Command processor. Handles commands specific to packet capture.
   * Invokes parent to handle commands inherited from other consoles.
   * @param command the command to execute.
   * @param args optional and required arguments to the provided command.
   */
  public String doCommand(String command, String [] args) {
    String result = "";
    if(command.toLowerCase().startsWith("capture"))
      return command_capture(args);
    if(command.toLowerCase().startsWith("clear"))
      return command_clear(args);
    if(command.toLowerCase().startsWith("device"))
      return command_device(args);
    if(command.toLowerCase().startsWith("filter"))
      return command_filter(args);
    if(command.toLowerCase().startsWith("hclear"))
      return command_hclear(args);
    if(command.toLowerCase().startsWith("history"))
      return command_history(args);
    if(command.toLowerCase().startsWith("hsize"))
      return command_hsize(args);
    if(command.toLowerCase().startsWith("mode"))
      return command_mode(args);
    if(command.toLowerCase().startsWith("snaplen"))
      return command_snaplen(args);
    if(command.toLowerCase().startsWith("status"))
      return command_status();
    if(command.toLowerCase().startsWith("timeout"))
      return command_timeout(args);
    if(command.toLowerCase().startsWith("ui"))
      return command_ui(args);

    result = super.doCommand(command, args);

    return result;
  }


  /**
   * Capture packets.
   */
  public String command_capture(String [] args) {
    if(args.length < 1) 
      return "usage: capture packet_count";

    if(captureTool.getDevice() == null)
      return "Error. No device is specified. Try 'device' first.";

    int count = -1;
    try {
      count = Integer.parseInt(args[0]);
    }
    catch(NumberFormatException e) {
      return "Error. The capture command accepts only a numeric argument.";
    }

    try {
      CaptureStatistics cs = captureTool.capture(count);
      System.err.println(cs);
    }
    catch(CaptureDeviceOpenException cdoe) {
      System.err.println(cdoe);
      return "An error occurred. Insufficient privileges or your " + 
        "capture device isn't valid?";
    }
    catch(InvalidFilterException ife) {
      System.err.println(ife);
      return "An error occurred. Your filter is invalid?";
    }
    catch(CapturePacketException cpe) {
      System.err.println(cpe);
      return "An error occurred";
    }

    return "Capture complete.";
  }

  /**
   * Clear the packet view frame.
   */
  public String command_clear(String [] args) {
    if(captureTool.resetViewFrame())
      return "packet view frame cleared";
    else 
      return "packet view frame can't be cleared because one doesn't exist";
  }

  /**
   * Set the device name.
   */
  public String command_device(String [] args) {
    if(args.length < 1) {
      try {
	StringBuffer sb = new StringBuffer();
	sb.append("usage: device device_name\n\n");
	sb.append("[devices detected: ");
	String[] devs = PacketCapture.lookupDevices();
	for(int i = 0; i < devs.length ; i++)
	  sb.append(devs[i] + " ");
	sb.append("]");

	return sb.toString();
      }
      catch(CaptureDeviceLookupException e) {
	return "could query available devices: :" + e.getMessage();
      }
    }

    try {
      captureTool.setDevice(args[0]);
    }
    catch(CaptureDeviceOpenException e) {
      return "could not set device to '" + args[0] + "'. " + e.getMessage();
    }

    return "device set to '" + args[0] + "'";
  }

  /**
   * Set the filter expression.
   */
  public String command_filter(String [] args) {
    if(args.length < 1) 
      return "usage: filter filter_expression";

    String filter = args[0];
    for(int i=1; i<args.length; i++)
      filter = filter + " " + args[i];

    try {
      captureTool.setFilter(filter);
    }
    catch(InvalidFilterException e) {
      return "Error. Filter rejected: " + e.getMessage();
    }

    return "filter set to '" + filter + "'";
  }

  /**
   * Clear the history buffer.
   */
  public String command_hclear(String [] args) {
    captureTool.clearHistory();

    return "history cleared";
  }

  /**
   * Show the history buffer contents.
   */
  public String command_history(String [] args) {
    int count = captureTool.dumpHistory();

    return "displayed " + count + " packets from history buffer";
  }

  /**
   * Resize the history buffer.
   */
  public String command_hsize(String [] args) {
    if(args.length < 1) 
      return "usage: hsize packet_count";

    int size = -1;
    try {
      size = Integer.parseInt(args[0]);
      if(size > 0) 
        captureTool.setMaxHistorySize(size);
      else 
        return "Error. The history size must be greater than 0.";
    }
    catch(NumberFormatException e) {
      return "Error. The history size must be a numeric argument.";
    }

    return "history buffer FIFO set to " + size + " packet" + 
      ((size == 1) ? "" : "s");
  }

  /**
   * Set the capture mode.
   */
  public String command_mode(String [] args) {
    if(args.length < 1) 
      return "usage: mode_name\n  where mode_name is one of: \n" + 
        "\traw - display raw packet data during capture\n" + 
        "\tobject - display packet objects during capture\n" + 
        "\tverbose - display both raw packet data and network objects\n" +
        "\tterse - don't display any packet data during capture\n" +
        "\tascii - display only printable ascii text from captured packets\n" +
        "\ttext - display only text (range a..Z) from captured packets\n";

    String mode = args[0];
    if(!CaptureTool.isModeValid(mode))
      return "unrecognized mode '" + mode + "'";

    captureTool.setMode(mode);
    return "packet capture display mode toggled to '" + mode + "'";
  }

  /**
   * Set the capture data length.
   */
  public String command_snaplen(String [] args) {
    if(args.length < 1) 
      return "usage: snaplen length";

    int snaplen;
    try {
      snaplen = Integer.parseInt(args[0]);
    }
    catch(NumberFormatException e) {
      return "Error. The snapshot length must be numeric.";
    }
    try {
      captureTool.setSnaplen(snaplen);
    }
    catch(CaptureDeviceOpenException e) {
      return "could not set snaplen to " + snaplen + ". " + e.getMessage();
    }

    return "snaplen set to " + snaplen + " byte" +
      ((snaplen == 1) ? "" : "s");
  }

  /**
   * show the status.
   */
  public String command_status() {
    StringBuffer buffer = new StringBuffer();

    buffer.append("\tcapture device = '" + captureTool.getDevice() + "'\n");
    buffer.append("\tnetwork = ");
    try {
      buffer.append(HexHelper.toQuadString(captureTool.getNetwork()));
    }
    catch(CaptureConfigurationException e) {
      buffer.append("error! " + e.getMessage());
    }

    buffer.append("\n\tnetmask = ");
    try {
      buffer.append(HexHelper.toQuadString(captureTool.getNetmask()));
    }
    catch(CaptureConfigurationException e) {
      buffer.append("error! " + e.getMessage());
    }

    buffer.append("\n\tlinktype = ");
    try {
      int llType = captureTool.getLinkLayerType();
      buffer.append(LinkLayer.getDescription(llType) + " [" + llType + "]");
    }
    catch(CaptureConfigurationException e) {
      buffer.append("error! " + e.getMessage());
    }
    catch(CaptureDeviceOpenException e) {
      buffer.append("error! " + e.getMessage());
    }

    buffer.append("\n\tsnapshot length = " + 
                  captureTool.getSnaplen() + " bytes\n");
    buffer.append("\tcapture timeout = " + captureTool.getTimeout() + "ms\n");
    String filter = captureTool.getFilter();
    buffer.append("\tpacket filter = " + 
                  (filter.equals("") ? "[none defined]" : 
                   "'" + filter + "'") + "\n");
    buffer.append("\tconsole mode = '" + captureTool.getMode() + "'\n");
    int maxSize = captureTool.getHistory().getMaxSize();
    buffer.append("\thistory buffer FIFO size = " + 
                  (maxSize == CaptureHistory.UNBOUNDED ? "[no limit]\n" : 
                  "" + maxSize + " packets\n"));
    buffer.append("\tcurrent history buffer contents = " + 
                  captureTool.getHistory().size() + " packets\n");

    return buffer.toString();
  }

  /**
   * Set the capture timeout in ms.
   */
  public String command_timeout(String [] args) {
    if(args.length < 1) 
      return "usage: timeout length";

    int timeout;
    try {
      timeout = Integer.parseInt(args[0]);
    }
    catch(NumberFormatException e) {
      return "Error. The timeout value must be numeric.";
    }
    try {
      captureTool.setTimeout(timeout);
    }
    catch(CaptureDeviceOpenException e) {
      return "could not set timeout to " + timeout + ". " + e.getMessage();
    }

    return "timeout set to " + timeout + "ms";
  }

  /**
   * Toggle the ui.
   */
  public String command_ui(String [] args) {
    if(captureTool.activateUi())
      return "packet view activated";
    else 
      return "packet view deactivated";
  }

  /**
   * Generate help for commands specific to this console.
   * Invoke parent to give help on commands inherited from other consoles.
   * @param suppressed a collection of commands handled by child consoles.
   */
  protected String command_help(Collection suppressed) {
    StringBuffer buffer = new StringBuffer();
    
    // suppress help display of commands reimplemented here
    HashSet suppress = new HashSet();
    if(suppressed == null) 
      suppressed = new HashSet();

    suppress.addAll(suppressed);
    buffer.append(super.command_help(suppress));

    buffer.append("  capture:\n");
    if(! suppressed.contains("capture"))
      buffer.append("\tcapture [count]         - " + 
                    "capture packets\n");
    if(! suppressed.contains("clear"))
      buffer.append("\tclear                   - " + 
                    "clear the packet view frame\n");
    if(! suppressed.contains("device"))
      buffer.append("\tdevice [device]         - " + 
                    "set name of the device to capture packets on\n");
    if(! suppressed.contains("filter"))
      buffer.append("\tfilter [expression]     - " + 
                    "set the filter expression\n");
    if(! suppressed.contains("hclear"))
      buffer.append("\thclear                  - " + 
                    "clear the capture history buffer\n");
    if(! suppressed.contains("history"))
      buffer.append("\thistory                 - " + 
                    "show the the capture history buffer contents\n");
    if(! suppressed.contains("hsize"))
      buffer.append("\thsize [packet_count]    - " + 
                    "set the maximum history buffer FIFO size\n");
    if(! suppressed.contains("mode"))
      buffer.append("\tmode [mode_name]        - " + 
                    "toggle packet data display mode\n");
    if(! suppressed.contains("snaplen"))
      buffer.append("\tsnaplen [length]        - " + 
                    "set the capture data length in bytes\n");
    if(! suppressed.contains("status"))
      buffer.append("\tstatus                  - " + 
                    "show current capture status\n");
    if(! suppressed.contains("timeout"))
      buffer.append("\ttimeout [ms]            - " + 
                    "set the capture timeout in ms\n");
    if(! suppressed.contains("ui"))
      buffer.append("\tui                      - " + 
                    "toggle the packet view frame on and off\n");

    return buffer.toString();
  }


  /**
   * Tester.
   */
  public static void main(String [] args) {
    // main() is overridden because running CaptureConsole without it
    // would invoke the inherited TestConsole's main. 
    System.err.println("Use CaptureTool to invoke the console.");
  }


  /**
   * The packet capture tool associated with this console.
   */
  private CaptureTool captureTool;

  private String _rcsid = 
    "$Id: CaptureConsole.java,v 1.17 2004/05/06 20:08:29 pcharles Exp $";
}
