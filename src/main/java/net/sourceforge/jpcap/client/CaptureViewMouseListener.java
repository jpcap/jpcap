/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
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
        victim = hr;
        victim.setSelected(true);
        pvc.repaint();
      }
    }
  }

  /**
   * A mouse release event places a host at a new location.
   * <p>
   * When the mouse is released, if a host is currently selected, drop
   * it at the new location.
   */
  public void mouseReleased(MouseEvent me) {
    if(victim != null) {
      int mx = Math.max(0, Math.min(me.getX(), pvc.getWidth()));
      int my = Math.max(0, Math.min(me.getY(), pvc.getHeight()));
      victim.setPos(mx, my);
      victim.setSelected(false);
      pvc.repaint();
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
      victim.setPos(me.getX(), me.getY());
      pvc.repaint();
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
}
