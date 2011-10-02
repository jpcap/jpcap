// $Id: PacketVisualizationCanvas.java,v 1.6 2001/07/02 00:03:32 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.client;

import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Canvas;


/**
 * A graphical view of captured packets.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.6 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/07/02 00:03:32 $
 */
public class PacketVisualizationCanvas extends Canvas
{
  /**
   * Create a new packet visualization canvas.
   */
  public PacketVisualizationCanvas(HashMap hostMap, HashMap commMap) {
    this.hostMap = hostMap;
    this.commMap = commMap;

    setBackground(Color.black);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public HashMap getHostMap() {
    return hostMap;
  }

  public HashMap getCommMap() {
    return commMap;
  }

public void repaint() {
    paintMe(this.getGraphics());
}

  /**
   * Repaint all the hosts and communications on this canvas.
   */
  public void paint(Graphics g) {
System.err.println("pvc paint");
    // only update Component's height and width on repaints.
    width = super.getWidth();
    height = super.getHeight();

    Iterator hIterator = hostMap.values().iterator();
    while(hIterator.hasNext()) {
      try {
        HostRenderer hs = (HostRenderer)hIterator.next();
        hs.paint();
      }
      catch(ConcurrentModificationException e) {
        //   It is possible for concurrency modification exceptions to occur 
        // here since packets are arriving asynchronously and potentially
        // being added to the canvas while a repaint is occurring.
        // If this occurs, it is much more efficient to simply break out 
        // of the paint operation. An alternate solution would be to 
        // synchronize the painting with the packet capture, but this would 
        // probably have bad performance implications.
        //   This concurrent modification exception occurs most commonly
        // on slow machines while the user is dragging hosts around the 
        // canvas. Since a paint cycle will likely be called just a few 
        // milliseconds later (as the user continues to drag the mouse 
        // and host), breaking out of the paint loop has no noticeable 
        // consequences because the next cycle quickly paints over hosts 
        // skipped during the concurrency conflict.
        break;
      }
    }

    Iterator cIterator = commMap.values().iterator();
    while(cIterator.hasNext()) {
      try {
	  /*
        CommRenderer cs = (CommRenderer)cIterator.next();
        cs.paint();
	  */
      }
      catch(ConcurrentModificationException e) {
        // see host iterator above for comments.
        break;
      }
    }
  }


  /**
   * host renderers drawing on this canvas.
   */
  HashMap hostMap;

  /**
   * communication renderers drawing on this canvas.
   */
  HashMap commMap;


  int width;
  int height;

  private String _rcsid = 
    "$Id: PacketVisualizationCanvas.java,v 1.6 2001/07/02 00:03:32 pcharles Exp $";
}
