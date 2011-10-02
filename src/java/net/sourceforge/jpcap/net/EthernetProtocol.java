// $Id: EthernetProtocol.java,v 1.3 2001/06/27 01:50:42 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;

import java.util.HashMap;


/**
 * Ethernet protocol utility class.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.3 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/06/27 01:50:42 $
 */
public class EthernetProtocol implements EthernetProtocols, EthernetFields
{
  /**
   * Extract the protocol type field from packet data.
   * <p>
   * The type field indicates what type of data is contained in the 
   * packet's data block.
   * @param packetBytes packet bytes.
   * @return the ethernet type code. i.e. 0x800 signifies IP datagram.
   */
  public static int extractProtocol(byte [] packetBytes) {
    // convert the bytes that contain the type code into a value..
    return packetBytes[ETH_CODE_POS] << 8 | packetBytes[ETH_CODE_POS + 1];
  }

  private String _rcsid = 
    "$Id: EthernetProtocol.java,v 1.3 2001/06/27 01:50:42 pcharles Exp $";
}
