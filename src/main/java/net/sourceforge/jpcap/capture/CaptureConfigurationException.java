/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.capture;

/**
 * This exception occurs when the capture client tries to 
 * specify a capture device that does not exist or if the capture
 * device specified is illegal.
 *
 */
public class CaptureConfigurationException extends Exception
{
  /**
   * Create a new invalid capture device exception.
   */
  public CaptureConfigurationException(String message) { 
    super(message);
  }
}
