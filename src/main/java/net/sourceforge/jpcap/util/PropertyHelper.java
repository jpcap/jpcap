/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.util;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Property helper utility methods.
 * <p>
 * All getters accept a {@code null} {@link Properties} reference and fall
 * back to the supplied default in that case, so callers can treat the
 * property file as an optional override layered on top of code-level
 * defaults.
 */
public class PropertyHelper {

    private static String lookup(Properties properties, Object key) {
        return properties == null ? null : (String) properties.get(key);
    }

    public static float getFloatProperty(Properties properties, Object key,
                                         float defaultValue) {
        String s = lookup(properties, key);
        if (s == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            System.err.println("WARN: invalid float for '" + key + "': " + s);
            return defaultValue;
        }
    }

    public static int getIntProperty(Properties properties, Object key,
                                     int defaultValue) {
        String s = lookup(properties, key);
        if (s == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.err.println("WARN: invalid int for '" + key + "': " + s);
            return defaultValue;
        }
    }

    public static Color getColorProperty(Properties properties, Object key,
                                         Color defaultValue) {
        String s = lookup(properties, key);
        if (s == null) {
            return defaultValue;
        }
        StringTokenizer st = new StringTokenizer(s, " ");
        try {
            return new Color(
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()));
        } catch (NoSuchElementException | NumberFormatException e) {
            System.err.println("WARN: invalid color tuplet for '" + key
                + "': " + s);
            return defaultValue;
        }
    }

    public static int getIpProperty(Properties properties, Object key,
                                    int defaultValue) {
        String s = lookup(properties, key);
        if (s == null) {
            return defaultValue;
        }
        StringTokenizer st = new StringTokenizer(s, ".");
        try {
            return Integer.parseInt(st.nextToken()) << 24
                | Integer.parseInt(st.nextToken()) << 16
                | Integer.parseInt(st.nextToken()) << 8
                | Integer.parseInt(st.nextToken());
        } catch (NoSuchElementException | NumberFormatException e) {
            System.err.println("WARN: invalid IP for '" + key + "': " + s);
            return defaultValue;
        }
    }

    public static boolean getBooleanProperty(Properties properties, Object key,
                                             boolean defaultValue) {
        String s = lookup(properties, key);
        if (s == null) {
            return defaultValue;
        }
        String v = s.toLowerCase();
        return v.equals("true") || v.equals("on")
            || v.equals("yes") || v.equals("1");
    }

    /**
     * Load a properties file by name. Filesystem paths are tried first (for
     * user-supplied overrides); falls back to the classpath so packaged
     * defaults ship with the jar. Returns {@code null} if no file is found
     * -- callers should be ready to use hard-coded defaults in that case.
     */
    public static Properties load(String[] paths, String fileName) {
        if (paths != null) {
            for (String dir : paths) {
                if (dir == null) {
                    continue;
                }
                File file = new File(dir, fileName);
                if (file.exists()) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        return read(file.getAbsolutePath(), fis);
                    } catch (IOException e) {
                        System.err.println("WARN: couldn't load '" + file
                            + "': " + e.getMessage());
                    }
                }
            }
        }

        ClassLoader cl = PropertyHelper.class.getClassLoader();
        try (InputStream in = cl.getResourceAsStream(fileName)) {
            if (in != null) {
                return read("classpath:" + fileName, in);
            }
        } catch (IOException e) {
            System.err.println("WARN: couldn't load classpath:" + fileName
                + ": " + e.getMessage());
        }

        return null;
    }

    private static Properties read(String name, InputStream in)
        throws IOException {
        System.err.println("INFO: loading properties from " + name);
        Properties properties = new Properties();
        properties.load(in);
        return properties;
    }
}
