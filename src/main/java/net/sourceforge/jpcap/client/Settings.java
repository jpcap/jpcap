/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.client;

import java.awt.Color;
import java.util.Properties;

import net.sourceforge.jpcap.util.PropertyHelper;

/**
 * Client tool settings.
 * <p>
 * Defaults are baked in; {@code tool.properties} (filesystem path or
 * classpath resource) is only consulted to override them.
 */
public class Settings {

    public static final String PROPERTY_PKG = "net.sourceforge.jpcap";
    public static final String PROPERTY_FILE = "tool.properties";

    public static final boolean ENABLE_COLOR_CONSOLE;
    public static final boolean ENABLE_NAMES;
    public static final boolean ENABLE_UI_DEFAULT;
    public static final boolean FULL_REPAINT_ON_DRAG;
    public static final boolean SHOW_COUNTS;
    public static final boolean USE_SIMULATOR;

    public static final Color COLOR_BG;
    public static final Color COLOR_P_UNKNOWN;
    public static final Color COLOR_P_UNKNOWN_H;
    public static final Color COLOR_P_ICMP;
    public static final Color COLOR_P_ICMP_H;
    public static final Color COLOR_P_IGMP;
    public static final Color COLOR_P_IGMP_H;
    public static final Color COLOR_P_TCP;
    public static final Color COLOR_P_TCP_H;
    public static final Color COLOR_P_UDP;
    public static final Color COLOR_P_UDP_H;
    public static final Color COLOR_HOST_ICON;
    public static final Color COLOR_HOST_TEXT;

    public static final int ICON_WIDTH;
    public static final int ICON_HEIGHT;
    public static final int DEFAULT_CANVAS_X;
    public static final int DEFAULT_CANVAS_Y;

    public static final String[] PATH_DEFAULTS = {
        System.getProperty("net.sourceforge.jpcap.properties.path"),
        System.getProperty("user.home") + "/properties"
    };

    static {
        Properties p = PropertyHelper.load(PATH_DEFAULTS, PROPERTY_FILE);

        ENABLE_COLOR_CONSOLE = bool(p, ".console.color",     true);
        ENABLE_NAMES         = bool(p, ".dns",               true);
        ENABLE_UI_DEFAULT    = bool(p, ".ui.enable",         true);
        FULL_REPAINT_ON_DRAG = bool(p, ".ui.dragrepaint",    true);
        SHOW_COUNTS          = bool(p, ".ui.packetcounts",   true);
        USE_SIMULATOR        = bool(p, ".simulator",         false);

        DEFAULT_CANVAS_X = anInt(p, ".ui.width",  1280);
        DEFAULT_CANVAS_Y = anInt(p, ".ui.height", 800);

        ICON_WIDTH  = anInt(p, ".ui.width.host.icon",  10);
        ICON_HEIGHT = anInt(p, ".ui.height.host.icon", 10);

        COLOR_BG          = color(p, ".ui.color.background",         new Color(0, 0, 0));
        COLOR_P_UNKNOWN   = color(p, ".ui.color.proto.unknown.lo",   new Color(96, 96, 96));
        COLOR_P_UNKNOWN_H = color(p, ".ui.color.proto.unknown.hi",   new Color(128, 128, 128));
        COLOR_P_ICMP      = color(p, ".ui.color.proto.icmp.lo",      new Color(128, 128, 255));
        COLOR_P_ICMP_H    = color(p, ".ui.color.proto.icmp.hi",      new Color(192, 192, 255));
        COLOR_P_IGMP      = color(p, ".ui.color.proto.igmp.lo",      new Color(128, 96, 64));
        COLOR_P_IGMP_H    = color(p, ".ui.color.proto.igmp.hi",      new Color(255, 192, 128));
        COLOR_P_TCP       = color(p, ".ui.color.proto.tcp.lo",       new Color(192, 192, 64));
        COLOR_P_TCP_H     = color(p, ".ui.color.proto.tcp.hi",       new Color(255, 255, 128));
        COLOR_P_UDP       = color(p, ".ui.color.proto.udp.lo",       new Color(128, 192, 128));
        COLOR_P_UDP_H     = color(p, ".ui.color.proto.udp.hi",       new Color(160, 212, 160));
        COLOR_HOST_ICON   = color(p, ".ui.color.host.icon",          new Color(255, 0, 0));
        COLOR_HOST_TEXT   = color(p, ".ui.color.host.text",          new Color(255, 255, 255));
    }

    private static boolean bool(Properties p, String suffix, boolean defaultValue) {
        return PropertyHelper.getBooleanProperty(p, PROPERTY_PKG + suffix, defaultValue);
    }

    private static int anInt(Properties p, String suffix, int defaultValue) {
        return PropertyHelper.getIntProperty(p, PROPERTY_PKG + suffix, defaultValue);
    }

    private static Color color(Properties p, String suffix, Color defaultValue) {
        return PropertyHelper.getColorProperty(p, PROPERTY_PKG + suffix, defaultValue);
    }
}
