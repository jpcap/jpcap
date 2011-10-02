// $Id: CaptureTest.java,v 1.2 2001/05/23 02:55:02 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.capture;

import net.sourceforge.jpcap.net.RawPacket;


/**
 * Class to exercise PacketCapture.
 * <p>
 * Testing of PacketCapture could easily be implemented in the same class's
 * main method, but since the tester is a client and must implement the 
 * listener interface, seems clearer to decouple the two.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.2 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/05/23 02:55:02 $
 */
public class CaptureTest implements RawPacketListener
{
  protected static String NAME = "CaptureTest";
  protected static int TEST_COUNT = 2;

  /**
   * Create a new tester.
   */
  public CaptureTest() {
  }


  // implementation of RawPacketListener interface

  public void rawPacketArrived(RawPacket rawPacket) {
    System.err.println(NAME + ": " + rawPacket + " arrived");
  }


  // implementation of test logic

  /**
   * Execute the test.
   */
  public void runTest() {
    //-- create PacketCapture system
    System.err.println(NAME + ": instantiating PacketCapture object.. ");
    PacketCapture pc = new PacketCapture();
    System.err.println(NAME + ": PacketCapture instantiated ok");

    //-- set the capture device
    String device = null;
    try {
      System.err.print(NAME + ": detecting capture device.. ");
      device = pc.findDevice();
      System.err.println(device);
    }
    catch(CaptureDeviceNotFoundException e) {
      // usually occurs if no network devices are available on the system
      System.err.println(e);
      System.exit(1);
    }

    //-- open the capture device
    try {
      System.err.print(NAME + ": opening capture device.. ");
      pc.open(device, true);
      System.err.println(device);
    }
    catch(CaptureDeviceOpenException e) {
      // usually occurs if the user doesn't have sufficient privileges
      System.err.println(e);
      System.exit(1);
    }

    //-- set filters
    try {
      String filter = ""; // nothing filtered by default
      System.err.println(NAME + ": setting filter to '" + filter + "'.. ");
      pc.setFilter(filter, true);
      System.err.println(NAME + ": filter compiled and activated ok");
    }
    catch(InvalidFilterException e) {
      System.err.println(e);
      System.exit(1);
    }

    //-- register the tester as a packet listener
    System.err.print(NAME + ": registering as a packet listener.. ");
    pc.addRawPacketListener(this);
    System.err.println("ok");

    //-- capture packets
    try {
      int count = TEST_COUNT;
      System.err.println(NAME + ": waiting for " + count + " packet(s).. ");
      pc.capture(count);
      System.err.println(NAME + ": done capturing.");
    }
    catch(CapturePacketException e) {
      System.err.println(e);
      System.exit(1);
    }

    //-- dump statistics
    System.err.println(pc.getStatistics());
  }


  /**
   * Simple test to exercise PacketCapture.
   */
  public static void main(String [] args) {
    CaptureTest ct = new CaptureTest();
    ct.runTest();
  }


  private String _rcsid = 
    "$Id: CaptureTest.java,v 1.2 2001/05/23 02:55:02 pcharles Exp $";
}
