// $Id: RawPacketListener.java,v 1.1 2001/05/23 02:55:02 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.capture;

import net.sourceforge.jpcap.net.RawPacket;


/**
 * Raw packet data listener.
 * <p>
 * Applications interested in listening for raw packet data must register
 * with PacketCapture and implement RawPacketDataListener.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.1 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/05/23 02:55:02 $
 */
public interface RawPacketListener
{
  void rawPacketArrived(RawPacket rawPacket);
}
