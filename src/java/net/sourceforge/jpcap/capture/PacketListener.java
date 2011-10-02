// $Id: PacketListener.java,v 1.3 2001/05/25 13:46:00 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.capture;

import net.sourceforge.jpcap.net.Packet;


/**
 * Packet data listener.
 * <p>
 * Applications interested in listening for packets must register
 * with PacketCapture and implement PacketDataListener.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.3 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/05/25 13:46:00 $
 */
public interface PacketListener
{
  void packetArrived(Packet packet);
}
