/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.simulator;

import java.util.Properties;

import net.sourceforge.jpcap.util.PropertyHelper;

/**
 * Simulator settings.
 * <p>
 * Defaults are baked in; {@code simulator.properties} is only consulted to
 * override them. The simulator boots even when the file is absent.
 */
public class Settings {

    public static final String PROPERTY_PKG = "net.sourceforge.jpcap.simulator";
    public static final String PROPERTY_FILE = "simulator.properties";

    private static final int DEFAULT_NETWORK = ip(10, 0, 0, 128);
    private static final int DEFAULT_NETMASK = ip(255, 255, 255, 248);

    public static final int SIM_NETWORK;
    public static final int SIM_NETMASK;

    public static final float PROB_ETH_IP;
    public static final float PROB_ETH_ARP;
    public static final float PROB_ETH_RARP;
    public static final float PROB_ETH_OTHER;

    public static final float PROB_IP_TCP;
    public static final float PROB_IP_UDP;
    public static final float PROB_IP_ICMP;
    public static final float PROB_IP_OTHER;

    public static final float PROB_ARP_REQUEST;
    public static final float PROB_ARP_REPLY;

    public static final String[] PATH_DEFAULTS = {
        System.getProperty("net.sourceforge.jpcap.properties.path"),
        System.getProperty("user.home") + "/properties"
    };

    static {
        Properties p = PropertyHelper.load(PATH_DEFAULTS, PROPERTY_FILE);

        SIM_NETWORK = PropertyHelper.getIpProperty(p, PROPERTY_PKG + ".network", DEFAULT_NETWORK);
        SIM_NETMASK = PropertyHelper.getIpProperty(p, PROPERTY_PKG + ".mask",    DEFAULT_NETMASK);

        PROB_ETH_IP    = aFloat(p, ".prob.eth.ip",    0.90f);
        PROB_ETH_ARP   = aFloat(p, ".prob.eth.arp",   0.05f);
        PROB_ETH_RARP  = aFloat(p, ".prob.eth.rarp",  0.00f);
        PROB_ETH_OTHER = aFloat(p, ".prob.eth.other", 0.05f);

        PROB_IP_TCP    = aFloat(p, ".prob.ip.tcp",    0.60f);
        PROB_IP_UDP    = aFloat(p, ".prob.ip.udp",    0.20f);
        PROB_IP_ICMP   = aFloat(p, ".prob.ip.icmp",   0.05f);
        PROB_IP_OTHER  = aFloat(p, ".prob.ip.other",  0.05f);

        PROB_ARP_REQUEST = aFloat(p, ".prob.arp.request", 0.50f);
        PROB_ARP_REPLY   = aFloat(p, ".prob.arp.reply",   0.50f);
    }

    private static float aFloat(Properties p, String suffix, float defaultValue) {
        return PropertyHelper.getFloatProperty(p, PROPERTY_PKG + suffix, defaultValue);
    }

    private static int ip(int a, int b, int c, int d) {
        return (a << 24) | (b << 16) | (c << 8) | d;
    }
}
