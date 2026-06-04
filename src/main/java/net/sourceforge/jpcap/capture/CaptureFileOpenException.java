/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */

package net.sourceforge.jpcap.capture;

/**
 * This exception occurs when the savefile cannot be opened.
 *
 * @author Christopher Balcerek
 */
public class CaptureFileOpenException extends Exception
{
  /**
   * Create a new invalid capture file exception.
   */
  public CaptureFileOpenException(String message) { 
    super(message);
  }
}
