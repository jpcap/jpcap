/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.capture;

/**
 * This exception occurs when the capture device cannot be opened.
 *
 */
public class CaptureDeviceOpenException extends Exception
{
  /**
   * Create a new invalid capture device exception.
   */
  public CaptureDeviceOpenException(String message) { 
    super(message);
  }
}
