// $Id: HostRenderer.java,v 1.5 2001/05/28 18:24:28 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.client;

import java.util.Iterator;
import java.util.HashMap;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.Canvas;
import java.awt.Graphics;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.IPPacket;
import net.sourceforge.jpcap.net.TCPPacket;


/**
 * A rendering of a host on a network.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.5 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/05/28 18:24:28 $
 */
public class HostRenderer
{
  /**
   * Create a new host renderer.
   * @param canvas the canvas where this host is being drawn.
   * @param ipAddress the ip address of the host being drawn.
   */
  public HostRenderer(Canvas canvas, String ipAddress) {
    this.canvas = canvas;
    this.ipAddress = ipAddress;

    hostName = ipAddress;

    if(Settings.ENABLE_NAMES) 
      new HostNameLookupThread(ipAddress, this);
    /*
    if(Settings.ENABLE_NAMES)
      try {
        InetAddress ia = InetAddress.getByName(ipAddress);
        hostName = ia.getHostName();
      }
      catch(UnknownHostException e) {
        // if the reverse lookup fails, just store the ip address in the name
        hostName = ipAddress;
      }
    else 
      hostName = ipAddress;
    */


    x = Math.random();
    y = Math.random();
    
    // force paint of self when first created
    paint();
  }

  /**
   * Fetch the canvas x-coordinate where this host is rendered.
   * For better performance, this is only recalculated in paint().
   */
  int getRenderedX() {
    return xpos;
  }

  /**
   * Fetch the canvas y-coordinate where this host is rendered.
   * For better performance, this is only recalculated in paint().
   */
  int getRenderedY() {
    return ypos;
  }

  public void setPos(int xn, int yn) {
    xpos = xn;
    x = (double)xpos / (double)canvas.getWidth();

    ypos = yn;
    y = (double)ypos / (double)canvas.getHeight();
  }

  /**
   * Determine if the given coordinate is 'inside' the host being rendered.
   */
  boolean isInside(int x, int y) {
    // calculation should use icon width or height divided by two, but 
    // the larger values used here make it more forgiving when trying to 
    // click on the small host icons.
    return(x > xpos - Settings.ICON_WIDTH && 
           x < xpos + Settings.ICON_WIDTH &&
           y > ypos - Settings.ICON_HEIGHT && 
           y < ypos + Settings.ICON_HEIGHT);
  }

  void paint() {
    draw(false);
  }

  void erase() {
    draw (true);
  }

  /**
   * Paint or erase this host on the canvas.
   */
  void draw(boolean erase) {
      if(erase) 
	  return;

    if(gc == null)
      gc = canvas.getGraphics();

    int width = canvas.getWidth(); 
    int height = canvas.getHeight();

    xpos = (int)(x * canvas.getWidth());
    ypos = (int)(y * canvas.getHeight());

    gc.setColor(erase ? Settings.COLOR_BG : Settings.COLOR_HOST_ICON);
    if(selected) 
      gc.fillRect(xpos - Settings.ICON_WIDTH / 2, 
                  ypos - Settings.ICON_HEIGHT / 2, 
                Settings.ICON_WIDTH, Settings.ICON_HEIGHT);

    else 
      gc.drawRect(xpos - Settings.ICON_WIDTH / 2, 
                  ypos - Settings.ICON_HEIGHT / 2, 
                  Settings.ICON_WIDTH, Settings.ICON_HEIGHT);
    gc.setColor(erase ? Settings.COLOR_BG : Settings.COLOR_HOST_TEXT);
    gc.drawString(hostName, xpos + Settings.ICON_WIDTH, ypos);
  }

  void setSelected(boolean selected) {
    this.selected = selected;
  }

  /**
   * Convert this host renderer to a readable string.
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[HostRenderer: ");
    sb.append(hostName);
    sb.append(']');

    return sb.toString();
  }

  /**
   * Fetch the IP address of the host associated with this renderer.
   */
  public String getIpAddress() {
    return ipAddress;
  }

  /**
   * Fetch the name of the host associated with this renderer.
   */
  public String getHostName() {
    return hostName;
  }

  /**
   * Fetch the name of the host associated with this renderer.
   */
  protected void setHostName(String hostName) {
    this.hostName = hostName;
  }

  /**
   * Relative x-location (between 0.0 and 1.0) where this host is rendered.
   */
  double x;

  /**
   * Absolute x-location where this host is currently being rendered.
   */
  int xpos;

  /**
   * Relative y-location (between 0.0 and 1.0) where this host is rendered.
   */
  double y;

  /**
   * Absolute x-location where this host is currently being rendered.
   */
  int ypos;

  /**
   * The IP address of the host associated with this renderer.
   */
  public String ipAddress;

  /**
   * The host name of the host associated with this renderer.
   */
  public String hostName;

  Graphics gc;
  private Canvas canvas;
  private boolean selected = false;

  private String _rcsid = 
    "$Id: HostRenderer.java,v 1.5 2001/05/28 18:24:28 pcharles Exp $";
}

