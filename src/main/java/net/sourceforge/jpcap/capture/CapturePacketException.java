/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.capture;

/**
 * This exception occurs when an error occurs while capturing data.
 *
 */
public class CapturePacketException extends Exception
{
  /**
   * Create a new invalid capture device exception.
   */
  public CapturePacketException(String message) { 
    super(message);
  }
}
