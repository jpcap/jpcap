/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.net;

/**
 * Code constants for IGMP message types.
 *
 * From RFC #2236.
 *
 */
public interface IGMPMessages
{
  /**
   * membership query.
   */
  int QUERY = 0x11;

  /**
   * v1 membership report.
   */
  int V1_REPORT = 0x12;

  /**
   * v2 membership report.
   */
  int V2_REPORT = 0x16;

  /**
   * Leave group.
   */
  int LEAVE = 0x17;
}
