// $Id: AnsiEscapeSequences.java,v 1.1 2001/06/01 06:24:44 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.util;

/**
 * String constants for color console output.
 * <p>
 * This file contains control sequences to print color text on a text 
 * console capable of interpreting and displaying control sequences.
 * <p>
 * A capable console would be 
 * unix bash, os/2 shell, or command.com w/ ansi.sys loaded
 *
 * @author Chris Cheetham
 */
public interface AnsiEscapeSequences
{
  String ESCAPE_BEGIN = "" + (char)033 + "[";
  String ESCAPE_END = "m";
  String RESET = ESCAPE_BEGIN + "0" + ESCAPE_END;

  String BOLD = ESCAPE_BEGIN + "0;1" + ESCAPE_END;
  String UNDERLINE = ESCAPE_BEGIN + "0;4" + ESCAPE_END;
  String INVERSE = ESCAPE_BEGIN + "0;7" + ESCAPE_END;

  String BLACK = ESCAPE_BEGIN + "0;30" + ESCAPE_END;
  String BLUE = ESCAPE_BEGIN + "0;34" + ESCAPE_END;
  String GREEN = ESCAPE_BEGIN + "0;32" + ESCAPE_END;
  String CYAN = ESCAPE_BEGIN + "0;36" + ESCAPE_END;
  String RED = ESCAPE_BEGIN + "0;31" + ESCAPE_END;
  String PURPLE = ESCAPE_BEGIN + "0;35" + ESCAPE_END;
  String BROWN = ESCAPE_BEGIN + "0;33" + ESCAPE_END;
  String LIGHT_GRAY = ESCAPE_BEGIN + "0;37" + ESCAPE_END;
  String DARK_GRAY = ESCAPE_BEGIN + "1;30" + ESCAPE_END;
  String LIGHT_BLUE = ESCAPE_BEGIN + "1;34" + ESCAPE_END;
  String LIGHT_GREEN = ESCAPE_BEGIN + "1;32" + ESCAPE_END;
  String LIGHT_CYAN = ESCAPE_BEGIN + "1;36" + ESCAPE_END;
  String LIGHT_RED = ESCAPE_BEGIN + "1;31" + ESCAPE_END;
  String LIGHT_PURPLE = ESCAPE_BEGIN + "1;35" + ESCAPE_END;
  String YELLOW = ESCAPE_BEGIN + "1;33" + ESCAPE_END;
  String WHITE = ESCAPE_BEGIN + "1;37" + ESCAPE_END;

  String RED_BACKGROUND = ESCAPE_BEGIN + "0;41" + ESCAPE_END;
  String GREEN_BACKGROUND = ESCAPE_BEGIN + "0;42" + ESCAPE_END;
  String YELLOW_BACKGROUND = ESCAPE_BEGIN + "0;43" + ESCAPE_END;
  String BLUE_BACKGROUND = ESCAPE_BEGIN + "0;44" + ESCAPE_END;
  String PURPLE_BACKGROUND = ESCAPE_BEGIN + "0;45" + ESCAPE_END;
  String CYAN_BACKGROUND = ESCAPE_BEGIN + "0;46" + ESCAPE_END;
  String LIGHT_GRAY_BACKGROUND = ESCAPE_BEGIN + "0;47" + ESCAPE_END;
}
