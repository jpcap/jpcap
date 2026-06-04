/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.capture;

/**
 * This exception occurs when no capture devices are detected.
 *
 * @author Rex Tsai &gt;chihchun@kalug.linux.org.tw&lt;
 */
public class CaptureDeviceLookupException extends Exception
{
  /**
   * Create a new capture device not found exception.
   */
  public CaptureDeviceLookupException(String message) { 
    super(message);
  }
}
