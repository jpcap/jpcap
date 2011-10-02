// $Id: CaptureViewMouseListener.java,v 1.2 2001/06/04 00:49:56 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.client;

import java.util.Iterator;
import java.util.HashMap;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/**
 * Mouse event handler for the capture view frame.
 * <p>
 * Allows the user to reorganize the visualization canvas by clicking and 
 * dragging rendered hosts.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.2 $n
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/06/04 00:49:56 $
 */
public class CaptureViewMouseListener 
  implements MouseListener, MouseMotionListener
{
  public CaptureViewMouseListener(PacketVisualizationCanvas pvc) {
    this.pvc = pvc;
    this.hostMap = pvc.getHostMap();
  }


  // implementation of MouseListener

  public void mouseClicked(MouseEvent me) { 
  }

  public void mouseEntered(MouseEvent me) { 
  }

  public void mouseExited(MouseEvent me) { 
  }

  /**
   * A mouse press event selects a host.
   * <p>
   * When the user clicks, detect if the location is on a plotted host.
   * If it is, select the host for movement to a new location.
   */
  public void mousePressed(MouseEvent me) {
    Iterator i = hostMap.values().iterator();
    while(i.hasNext()) {
      HostRenderer hr = (HostRenderer)i.next();
      if(hr.isInside(me.getX(), me.getY())) {
        //        System.err.println("drag " + hr + " to desired location");
        victim = hr;
        victim.setSelected(true);
        victim.paint();
      }
    }
    //    if(victim == null)
      //      System.err.println("click on a host!");
  }

  /**
   * A mouse release event places a host at a new location.
   * <p>
   * When the mouse is released, if a host is currently selected, drop
   * it at the new location.
   */
  public void mouseReleased(MouseEvent me) {
    if(victim != null) {
      int mx = me.getX();
      int my = me.getY();

      // don't let the user drop something outside the canvas bounds where 
      // it would no longer be accessible. snap to border on release.
      if(mx > pvc.getWidth()) mx = pvc.getWidth();
      if(my > pvc.getHeight()) my = pvc.getHeight();
      if(mx < 0) mx = 0;
      if(my < 0) my = 0;

      victim.setPos(mx, my);
      pvc.repaint();
      victim.setSelected(false);
      victim.paint();
      victim = null;
    }
  }


  // implementation of MouseMotionListener

  /**
   * Hosts are animated while the mouse is dragged.
   * <p>
   * When the mouse is dragged, if a host is currently selected, animate
   * the drag.
   */
  public void mouseDragged(MouseEvent me) {
    if(victim != null) {
      // full repaint on drag looks cool, but might be too expensive on a 
      // busy network where potentially hundreds of hosts are being drawn.
      if(Settings.FULL_REPAINT_ON_DRAG) {
        victim.setPos(me.getX(), me.getY());
        pvc.repaint();
      }
      else {
        victim.erase();
        victim.setPos(me.getX(), me.getY());
        victim.paint();
      }
    }
  }

  public void mouseMoved(MouseEvent me) {
  }


  /**
   * The canvas containing the host renderers being manipulated.
   */
  PacketVisualizationCanvas pvc;

  /**
   * A map of hosts stored by ip.
   */
  HashMap hostMap;

  /**
   * The host being manipulated by the user.
   */
  HostRenderer victim;

  private String _rcsid = 
    "$Id: CaptureViewMouseListener.java,v 1.2 2001/06/04 00:49:56 pcharles Exp $";
}
