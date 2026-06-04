/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.net;

/**
 * IP protocol field encoding information.
 *
 */
public interface UDPFields
{
  // field lengths

  /**
   * Length of a UDP port in bytes.
   */
  int UDP_PORT_LEN = 2;

  /**
   * Length of the header length field in bytes.
   */
  int UDP_LEN_LEN = 2;

  /**
   * Length of the checksum field in bytes.
   */
  int UDP_CSUM_LEN = 2;

  // field positions

  /**
   * Position of the source port.
   */
  int UDP_SP_POS = 0;

  /**
   * Position of the destination port.
   */
  int UDP_DP_POS = UDP_PORT_LEN;

  /**
   * Position of the header length.
   */
  int UDP_LEN_POS = UDP_DP_POS + UDP_PORT_LEN;

  /**
   * Position of the header checksum length.
   */
  int UDP_CSUM_POS = UDP_LEN_POS + UDP_LEN_LEN;

  // complete header length 

  /**
   * Length of a UDP header in bytes.
   */
  int UDP_HEADER_LEN = UDP_CSUM_POS + UDP_CSUM_LEN; // == 8
}
