// $Id: ARPFields.java,v 1.3 2001/06/27 01:46:59 pcharles Exp $

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
 * @version $Revision: 1.3 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/06/27 01:46:59 $
 */
public interface ARPFields
{
  // ARP codes

  /**
   * Type code for ethernet addresses.
   */
  int ARP_ETH_ADDR_CODE = 0x0001;

  /**
   * Type code for MAC addresses.
   */
  int ARP_IP_ADDR_CODE = 0x0800;

  /**
   * Code for ARP request.
   */
  int ARP_OP_REQ_CODE = 0x1;

  /**
   * Code for ARP reply.
   */
  int ARP_OP_REP_CODE = 0x2;


  // field lengths 

  /**
   * Operation type length in bytes.
   */
  int ARP_OP_LEN = 2;

  /**
   * Address type length in bytes.
   */
  int ARP_ADDR_TYPE_LEN = 2;

  /**
   * Address type length in bytes.
   */
  int ARP_ADDR_SIZE_LEN = 1;


  // field positions 

  /**
   * Position of the hardware address type.
   */
  int ARP_HW_TYPE_POS = 0;

  /**
   * Position of the protocol address type.
   */
  int ARP_PR_TYPE_POS = ARP_HW_TYPE_POS + ARP_ADDR_TYPE_LEN;

  /**
   * Position of the hardware address length.
   */
  int ARP_HW_LEN_POS = ARP_PR_TYPE_POS + ARP_ADDR_TYPE_LEN;

  /**
   * Position of the protocol address length.
   */
  int ARP_PR_LEN_POS = ARP_HW_LEN_POS + ARP_ADDR_SIZE_LEN;

  /**
   * Position of the operation type.
   */
  int ARP_OP_POS = ARP_PR_LEN_POS + ARP_ADDR_SIZE_LEN;

  /**
   * Position of the sender hardware address.
   */
  int ARP_S_HW_ADDR_POS = ARP_OP_POS + ARP_OP_LEN;

  /**
   * Position of the sender protocol address.
   */
  int ARP_S_PR_ADDR_POS = ARP_S_HW_ADDR_POS + MACAddress.WIDTH;

  /**
   * Position of the target hardware address.
   */
  int ARP_T_HW_ADDR_POS = ARP_S_PR_ADDR_POS + IPAddress.WIDTH;

  /**
   * Position of the target protocol address.
   */
  int ARP_T_PR_ADDR_POS = ARP_T_HW_ADDR_POS + MACAddress.WIDTH;


  // complete header length

  /**
   * Total length in bytes of an ARP header.
   */
  int ARP_HEADER_LEN = ARP_T_PR_ADDR_POS + IPAddress.WIDTH; // == 28
}
