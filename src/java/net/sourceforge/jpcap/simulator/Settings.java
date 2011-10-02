// $Id: Settings.java,v 1.1 2001/06/26 22:53:52 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.simulator;

import java.util.Properties;
import net.sourceforge.jpcap.util.PropertyHelper;


/**
 * Simulator settings.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.1 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/06/26 22:53:52 $
 */
public class Settings
{
  public static String PROPERTY_PKG = "net.sourceforge.jpcap.simulator";
  public static String PROPERTY_FILE = "simulator.properties";

  public static int SIM_NETWORK;
  public static int SIM_NETMASK;
  public static float PROB_ETH_IP;
  public static float PROB_ETH_ARP;
  public static float PROB_ETH_RARP;
  public static float PROB_ETH_OTHER;
  public static float PROB_IP_TCP;
  public static float PROB_IP_UDP;
  public static float PROB_IP_ICMP;
  public static float PROB_IP_OTHER;
  public static float PROB_ARP_REQUEST;
  public static float PROB_ARP_REPLY;


  // default search paths for property file location
  public static String [] PATH_DEFAULTS = {
    System.getProperties().getProperty
    ("net.sourceforge.jpcap.properties.path"),
    System.getProperties().getProperty("user.home") + "/properties"
  };

  static {
    Properties properties = PropertyHelper.load(PATH_DEFAULTS, PROPERTY_FILE);
    if(properties == null || properties.size() == 0) {
      System.err.println("FATAL: simulator cannot start without properties!");
      System.exit(1);
    }

    SIM_NETWORK = PropertyHelper.getIpProperty
      (properties, PROPERTY_PKG + ".network");
    SIM_NETMASK = PropertyHelper.getIpProperty
      (properties, PROPERTY_PKG + ".mask");
    PROB_ETH_IP = PropertyHelper.getFloatProperty
      (properties, PROPERTY_PKG + ".prob.eth.ip");
    PROB_ETH_ARP = PropertyHelper.getFloatProperty
      (properties, PROPERTY_PKG + ".prob.eth.arp");
    PROB_ETH_RARP = PropertyHelper.getFloatProperty
      (properties, PROPERTY_PKG + ".prob.eth.rarp");
    PROB_ETH_OTHER = PropertyHelper.getFloatProperty
      (properties, PROPERTY_PKG + ".prob.eth.other");
    PROB_ARP_REQUEST = PropertyHelper.getFloatProperty
      (properties, PROPERTY_PKG + ".prob.arp.request");
    PROB_ARP_REPLY = PropertyHelper.getFloatProperty
      (properties, PROPERTY_PKG + ".prob.arp.reply");
    PROB_IP_TCP = PropertyHelper.getFloatProperty
      (properties, PROPERTY_PKG + ".prob.ip.tcp");
    PROB_IP_UDP = PropertyHelper.getFloatProperty
      (properties, PROPERTY_PKG + ".prob.ip.udp");
    PROB_IP_ICMP = PropertyHelper.getFloatProperty
      (properties, PROPERTY_PKG + ".prob.ip.icmp");
    PROB_IP_OTHER = PropertyHelper.getFloatProperty
      (properties, PROPERTY_PKG + ".prob.ip.other");
  }


  private static String _rcsId = 
    "$Id: Settings.java,v 1.1 2001/06/26 22:53:52 pcharles Exp $";
}
