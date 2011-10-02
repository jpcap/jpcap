# $Id: rules.makefile,v 1.3 2001/06/04 04:06:57 pcharles Exp $
#

# java compiler
ifneq ($(JAVAC),)
# if the user has an externally defined java compiler preference, use it
else
# otherwise, use javac. everybody has that.
JAVAC = jikes
JAVAC = javac
endif

# java compiler flags
#
ifeq ($(JAVAC), jikes)
  JFLAGS = -depend -g -deprecation -verbose +P +Z
  JFLAGS = -depend -g +P +Z
  JFLAGS = -g +P +Z
else
  JFLAGS = -g
endif

# implicit rules
%.class: %.java
	$(JAVAC) $(JFLAGS) $*.java
