// $Id: Settings.java,v 1.9 2001/07/30 00:03:24 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.client;

import java.awt.Color;
import java.util.Properties;
import net.sourceforge.jpcap.util.PropertyHelper;


/**
 * Client tool settings.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.9 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/07/30 00:03:24 $
 */
public class Settings
{
  public static String PROPERTY_PKG = "net.sourceforge.jpcap";
  public static String PROPERTY_FILE = "tool.properties";

  public static boolean ENABLE_COLOR_CONSOLE;
  public static boolean ENABLE_NAMES;
  public static boolean ENABLE_UI_DEFAULT;
  public static boolean FULL_REPAINT_ON_DRAG;
  public static boolean SHOW_COUNTS;
  public static boolean USE_SIMULATOR;

  public static Color COLOR_BG;
  public static Color COLOR_P_UNKNOWN;
  public static Color COLOR_P_UNKNOWN_H;
  public static Color COLOR_P_ICMP;
  public static Color COLOR_P_ICMP_H;
  public static Color COLOR_P_IGMP;
  public static Color COLOR_P_IGMP_H;
  public static Color COLOR_P_TCP;
  public static Color COLOR_P_TCP_H;
  public static Color COLOR_P_UDP;
  public static Color COLOR_P_UDP_H;
  public static Color COLOR_HOST_ICON;
  public static Color COLOR_HOST_TEXT;
  public static int ICON_WIDTH;
  public static int ICON_HEIGHT;

  public static int DEFAULT_CANVAS_X = 512;
  public static int DEFAULT_CANVAS_Y = 512;



  // default search paths for property file location
  public static String [] PATH_DEFAULTS = {
    System.getProperties().getProperty
    ("net.sourceforge.jpcap.properties.path"),
    System.getProperties().getProperty("user.home") + "/properties"
  };

  static {
    Properties properties = PropertyHelper.load(PATH_DEFAULTS, PROPERTY_FILE);
    if(properties == null || properties.size() == 0) {
      System.err.println("FATAL: jpcap tool cannot start without properties!");
      System.exit(1);
    }


    ENABLE_COLOR_CONSOLE = PropertyHelper.getBooleanProperty
      (properties, PROPERTY_PKG + ".console.color");

    ENABLE_NAMES = PropertyHelper.getBooleanProperty
      (properties, PROPERTY_PKG + ".dns");

    ENABLE_UI_DEFAULT = PropertyHelper.getBooleanProperty
      (properties, PROPERTY_PKG + ".ui.enable");

    FULL_REPAINT_ON_DRAG = PropertyHelper.getBooleanProperty
      (properties, PROPERTY_PKG + ".ui.dragrepaint");

    SHOW_COUNTS = PropertyHelper.getBooleanProperty
      (properties, PROPERTY_PKG + ".ui.packetcounts");

    USE_SIMULATOR = PropertyHelper.getBooleanProperty
      (properties, PROPERTY_PKG + ".simulator");

    COLOR_BG = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.background");

    COLOR_P_UNKNOWN = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.proto.unknown.lo");

    COLOR_P_UNKNOWN_H = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.proto.unknown.hi");

    COLOR_P_ICMP = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.proto.icmp.lo");

    COLOR_P_ICMP_H = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.proto.icmp.hi");

    COLOR_P_IGMP = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.proto.igmp.lo");

    COLOR_P_IGMP_H = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.proto.igmp.hi");

    COLOR_P_TCP = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.proto.tcp.lo");

    COLOR_P_TCP_H = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.proto.tcp.hi");

    COLOR_P_UDP = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.proto.udp.lo");

    COLOR_P_UDP_H = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.proto.udp.hi");

    COLOR_HOST_ICON = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.host.icon");

    COLOR_HOST_TEXT = PropertyHelper.getColorProperty
      (properties, PROPERTY_PKG + ".ui.color.host.text");

    ICON_WIDTH = PropertyHelper.getIntProperty
      (properties, PROPERTY_PKG + ".ui.width.host.icon");

    ICON_HEIGHT = PropertyHelper.getIntProperty
      (properties, PROPERTY_PKG + ".ui.height.host.icon");

    DEFAULT_CANVAS_X = PropertyHelper.getIntProperty
      (properties, PROPERTY_PKG + ".ui.width");

    DEFAULT_CANVAS_Y = PropertyHelper.getIntProperty
      (properties, PROPERTY_PKG + ".ui.height");
  }


  private static String _rcsId = 
    "$Id: Settings.java,v 1.9 2001/07/30 00:03:24 pcharles Exp $";
}
