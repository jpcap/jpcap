/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.net;

/**
 * IGMP protocol field encoding information.
 *
 */
public interface IGMPFields
{
  // field lengths

  /**
   * Length of the IGMP message type code in bytes.
   */
  int IGMP_CODE_LEN = 1;

  /**
   * Length of the IGMP max response code in bytes.
   */
  int IGMP_MRSP_LEN = 1;

  /**
   * Length of the IGMP header checksum in bytes.
   */
  int IGMP_CSUM_LEN = 2;

  /**
   * Length of group address in bytes.
   */
  int IGMP_GADDR_LEN = IPAddress.WIDTH;

  // field positions

  /**
   * Position of the IGMP message type.
   */
  int IGMP_CODE_POS = 0;

  /**
   * Position of the IGMP max response code.
   */
  int IGMP_MRSP_POS = IGMP_CODE_POS + IGMP_CODE_LEN;

  /**
   * Position of the IGMP header checksum.
   */
  int IGMP_CSUM_POS = IGMP_MRSP_POS + IGMP_MRSP_LEN;

  /**
   * Position of the IGMP group address.
   */
  int IGMP_GADDR_POS = IGMP_CSUM_POS + IGMP_CSUM_LEN;

  // complete header length 

  /**
   * Length in bytes of an IGMP header.
   */
    int IGMP_HEADER_LEN = IGMP_GADDR_POS + IGMP_GADDR_LEN; // 8
}
