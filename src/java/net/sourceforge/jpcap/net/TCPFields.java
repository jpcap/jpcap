// $Id: TCPFields.java,v 1.5 2003/10/29 02:38:27 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;


/**
 * IP protocol field encoding information.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.5 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2003/10/29 02:38:27 $
 */
public interface TCPFields
{
  // flag bitmasks

  int TCP_URG_MASK = 0x0020;
  int TCP_ACK_MASK = 0x0010;
  int TCP_PSH_MASK = 0x0008;
  int TCP_RST_MASK = 0x0004;
  int TCP_SYN_MASK = 0x0002;
  int TCP_FIN_MASK = 0x0001;


  // field lengths

  /**
   * Length of a TCP port in bytes.
   */
  int TCP_PORT_LEN = 2;

  /**
   * Length of the sequence number in bytes.
   */
  int TCP_SEQ_LEN = 4;

  /**
   * Length of the acknowledgment number in bytes.
   */
  int TCP_ACK_LEN = 4;

  /**
   * Length of the header length and flags field in bytes.
   */
  int TCP_FLAG_LEN = 2;

  /**
   * Length of the window size field in bytes.
   */
  int TCP_WIN_LEN = 2;

  /**
   * Length of the checksum field in bytes.
   */
  int TCP_CSUM_LEN = 2;

  /**
   * Length of the urgent field in bytes.
   */
  int TCP_URG_LEN = 2;


  // field positions

  /**
   * Position of the source port field.
   */
  int TCP_SP_POS = 0;

  /**
   * Position of the destination port field.
   */
  int TCP_DP_POS = TCP_PORT_LEN;

  /**
   * Position of the sequence number field.
   */
  int TCP_SEQ_POS = TCP_DP_POS + TCP_PORT_LEN;

  /**
   * Position of the acknowledgment number field.
   */
  int TCP_ACK_POS = TCP_SEQ_POS + TCP_SEQ_LEN;

  /**
   * Position of the header length and flags field.
   */
  int TCP_FLAG_POS = TCP_ACK_POS + TCP_ACK_LEN;

  /**
   * Position of the window size field.
   */
  int TCP_WIN_POS = TCP_FLAG_POS + TCP_FLAG_LEN;

  /**
   * Position of the checksum field.
   */
  int TCP_CSUM_POS = TCP_WIN_POS + TCP_WIN_LEN;

  /**
   * Position of the urgent pointer field.
   */
  int TCP_URG_POS = TCP_CSUM_POS + TCP_CSUM_LEN;


  // complete header length 

  /**
   * Length in bytes of a TCP header.
   */
  int TCP_HEADER_LEN = TCP_URG_POS + TCP_URG_LEN; // == 20
}
