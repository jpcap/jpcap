// $Id: PropertyHelper.java,v 1.3 2004/02/24 17:59:20 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;
import java.awt.Color;


/**
 * Property helper utility methods.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.3 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2004/02/24 17:59:20 $
 */
public class PropertyHelper
{
  /**
   * Read the specified float property.
   * <p>
   * Throws an exception if the property value isn't a floating-point number.
   *
   * @param key the name of the property
   * @return the float value of the property
   */
  public static float getFloatProperty(Properties properties, Object key) {
    String string = (String)properties.get(key);

    if(string == null)
      System.err.println("WARN: couldn't find float value under '" + 
                         key + "'");

    return Float.parseFloat((String)properties.get(key));
  }

  /**
   * Read the specified integer property.
   * <p>
   * Throws an exception if the property value isn't an integer.
   *
   * @param key the name of the property
   * @return the integer value of the property
   */
  public static int getIntProperty(Properties properties, Object key) {
    String string = (String)properties.get(key);

    if(string == null)
      System.err.println("WARN: couldn't find integer value under '" + 
                         key + "'");

    return Integer.parseInt((String)properties.get(key));
  }

  /**
   * Convert a space delimited color tuple string to a color.
   * <p>
   * Converts a string value like "255 255 0" to a color constant,
   * in this case, yellow.
   *
   * @param key the name of the property
   * @return a Color object equivalent to the provided string contents. 
   *         Returns white if the string is null or can't be converted.
   */
  public static Color getColorProperty(Properties properties, Object key) {
    String string = (String)properties.get(key);

    if(string == null) {
      System.err.println("WARN: couldn't find color tuplet under '" + 
                         key + "'");
      return Color.white;
    }

    StringTokenizer st = new StringTokenizer(string, " ");
    Color c;
    try {
      c = new Color(Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()));
    }
    catch(NoSuchElementException e) {
      c = Color.white;
      System.err.println("WARN: invalid color spec '" + string + 
                         "' in property file");
    }

    return c;
  }

  /**
   * Convert a dot-delimited IP address to an integer.
   * <p>
   * Converts a string value like "10.0.0.5" to an integer.
   *
   * @param key the name of the property
   * @return the integer value of the specified IP number.
   * returns zero if the IP number is not valid.
   */
  public static int getIpProperty(Properties properties, Object key) {
    String string = (String)properties.get(key);

    if(string == null) {
      System.err.println("WARN: couldn't find IP value under '" + 
                         key + "'");
      return 0;
    }

    StringTokenizer st = new StringTokenizer(string, ".");
    int address;
    try {
      address = 
        Integer.parseInt(st.nextToken()) << 24 | 
        Integer.parseInt(st.nextToken()) << 16 | 
        Integer.parseInt(st.nextToken()) <<  8 | 
        Integer.parseInt(st.nextToken());
    }
    catch(NoSuchElementException e) {
      address = 0;
      System.err.println("WARN: invalid color spec '" + string + 
                         "' in property file");
    }

    return address;
  }

  /**
   * Read the specified boolean property.
   * Converts a property value like "true" or "1" to its boolean value. 
   * <p>
   * Returns false if the property doesn't exist or can't be converted to a 
   * boolean.
   *
   * @param key the name of the property
   * @return the property value
   */
  public static boolean getBooleanProperty(Properties properties, Object key) {
    String string = (String)properties.get(key);

    if(string == null) {
      System.err.println("WARN: couldn't find boolean value under '" + 
                         key + "'");
      return false;
    }

    if(string.toLowerCase().equals("true") ||
       string.toLowerCase().equals("on") ||
       string.toLowerCase().equals("yes") ||
       string.toLowerCase().equals("1")) 
      return true;
    else 
      return false;
  }

  /**
   * Refresh property settings from disk.
   */
  public static Properties refresh(String name, FileInputStream fis) 
   throws IOException {
    System.err.println("INFO: loading properties from " + name);
    Properties properties = new Properties();
    properties.load(fis);

    return properties;
  }

  /**
   * Load the specified properties file from one of the specified set of 
   * paths.
   *
   * @param paths an array of strings containing target paths.
   * @param fileName the name of the property file.
   * @return a populated set of properties loaded from the first file 
   * found in the set of supplied paths. If no property file is found, 
   * returns null.
   */
  public static Properties load(String [] paths, String fileName) {
    Properties properties = null;
    File propertiesFile = null;
    try {
      String path = null;
      for(int i=0; i<paths.length; i++) {
        path = paths[i] + File.separator + fileName;
        propertiesFile = new File(path);
        if(propertiesFile.exists()) break;
      }
      if(!propertiesFile.exists()) {
        System.err.println("FATAL: could not find file '" + fileName +
                           "' in default search paths: ");
        for(int i=0; i<paths.length; i++) {
          System.err.print("'" + paths[i] + "'");
          if(i < paths.length - 1) 
            System.err.print(", ");
        }
        System.err.println();
        System.exit(1);
      }

      properties = refresh(propertiesFile.getAbsolutePath(), 
                           new FileInputStream(propertiesFile));
    }
    catch(Exception e) {
      System.err.println("ERROR: couldn't load properties from '" + 
                         propertiesFile + "'");
    }

    return properties;
  }


  private static String _rcsId = 
    "$Id: PropertyHelper.java,v 1.3 2004/02/24 17:59:20 pcharles Exp $";
}
