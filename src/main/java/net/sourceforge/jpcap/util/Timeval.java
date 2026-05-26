/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.util;

import java.util.Date;
import java.io.Serializable;

/**
 * POSIX.4 timeval for Java.
 * <p>
 * Container for java equivalent of c's struct timeval.
 *
 */
public class Timeval implements Serializable
{
  public Timeval(long seconds, int microseconds) {
    this.seconds = seconds;
    this.microseconds = microseconds;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(seconds);
    sb.append('.');
    sb.append(microseconds);
    sb.append('s');

    return sb.toString();
  }

  /**
   * Convert this timeval to a java Date.
   */
  public Date getDate() {
    return new Date(seconds * 1000 + microseconds / 1000);
  }

  public long getSeconds() {
    return seconds;
  }

  public int getMicroSeconds() {
    return microseconds;
  }

  long seconds;
  int microseconds;
}
