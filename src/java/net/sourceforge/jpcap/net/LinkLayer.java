// $Id: LinkLayer.java,v 1.6 2004/10/02 01:23:19 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;

import java.util.HashMap;


/**
 * Information about network link layers.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.6 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/10/02 01:23:19 $
 */
public class LinkLayer implements LinkLayers
{
  /**
   * Fetch the header length associated with various link-layer types.
   * @param layerType the link-layer code
   * @return the length of the header for the specified link-layer
   */
  public static int getLinkLayerLength(int layerType) {
    switch(layerType) {
    case ARCNET:
      return 6;
    case SLIP:
      return 16;
    case SLIP_BSDOS:
      return 24;
    case NULL:
    case LOOP:
      return 4;
    case PPP:
    case CHDLC:
    case PPP_SERIAL:
      return 4;
    case PPP_BSDOS:
      return 24;
    case FDDI:
      return 21;
    case IEEE802_11:
      return 22;
    case ATM_RFC1483:
      return 8;
    case RAW:
      return 0;
    case ATM_CLIP:
      return 8;
    case LINUX_SLL:
      return 16;
    case EN10MB:
    default:
      return 14;
    }
  }

  /**
   * Fetch the offset into the link-layer header where the protocol code
   * can be found. Returns -1 if there is no embedded protocol code.
   * @param layerType the link-layer code
   * @return the offset in bytes
   */
  public static int getProtoOffset(int layerType) {
    switch(layerType) {
    case ARCNET:
      return 2;
    case SLIP:
      return -1;
    case SLIP_BSDOS:
      return -1;
    case NULL:
    case LOOP:
      return 0;
    case PPP:
    case CHDLC:
    case PPP_SERIAL:
      return 2;
    case PPP_BSDOS:
      return 5;
    case FDDI:
      return 13;
    case IEEE802_11:
      return 14;
    case ATM_RFC1483:
      return 6;
    case RAW:
      return -1;
    case ATM_CLIP:
      return 6;
    case LINUX_SLL:
      return 14;
    case EN10MB:
    default:
      return 12;
    }
  }

  /**
   * Fetch a link-layer type description.
   * @param code the code associated with the description.
   * @return a description of the link-layer type.
   */
  public static String getDescription(int code) {
    Integer c = new Integer(code);
    if(descriptions.containsKey(c)) 
      return (String)descriptions.get(c);
    else 
      return "unknown";
  }

  /**
   * 'Human-readable' link-layer type descriptions.
   */
  //jdk1.5: private static HashMap <Integer, String> descriptions = new HashMap<Integer, String>();
  private static HashMap descriptions = new HashMap();
  static {
    descriptions.put(new Integer(NULL), "no link-layer encapsulation");
    descriptions.put(new Integer(EN10MB), "10/100Mb ethernet");
    descriptions.put(new Integer(EN3MB), "3Mb experimental ethernet");
    descriptions.put(new Integer(AX25), "AX.25 amateur radio");
    descriptions.put(new Integer(PRONET), "proteon pronet token ring");
    descriptions.put(new Integer(CHAOS), "chaos");
    descriptions.put(new Integer(IEEE802), "IEEE802 network");
    descriptions.put(new Integer(ARCNET), "ARCNET");
    descriptions.put(new Integer(SLIP), "serial line IP");
    descriptions.put(new Integer(PPP), "point-to-point protocol");
    descriptions.put(new Integer(FDDI), "FDDI");
    descriptions.put(new Integer(ATM_RFC1483), "LLC/SNAP encapsulated ATM");
    descriptions.put(new Integer(RAW), "raw IP");
    descriptions.put(new Integer(SLIP_BSDOS), "BSD SLIP");
    descriptions.put(new Integer(PPP_BSDOS), "BSD PPP");
    descriptions.put(new Integer(ATM_CLIP), "IP over ATM");
    descriptions.put(new Integer(PPP_SERIAL), "PPP over HDLC");
    descriptions.put(new Integer(CHDLC), "Cisco HDLC");
    descriptions.put(new Integer(IEEE802_11), "802.11 wireless");
    descriptions.put(new Integer(LOOP), "OpenBSD loopback");
    descriptions.put(new Integer(LINUX_SLL), "Linux cooked sockets");
    descriptions.put(new Integer(UNKNOWN), "unknown link-layer type");
  }


  private String _rcsid = 
    "$Id: LinkLayer.java,v 1.6 2004/10/02 01:23:19 pcharles Exp $";
}
