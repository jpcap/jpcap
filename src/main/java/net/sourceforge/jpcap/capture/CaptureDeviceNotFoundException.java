/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.capture;

/**
 * This exception occurs when no capture devices are detected.
 *
 */
public class CaptureDeviceNotFoundException extends Exception
{
  /**
   * Create a new capture device not found exception.
   */
  public CaptureDeviceNotFoundException(String message) { 
    super(message);
  }
}
