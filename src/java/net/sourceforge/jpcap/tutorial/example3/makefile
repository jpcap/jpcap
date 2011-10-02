# $Id: makefile,v 1.3 2004/02/22 20:38:34 pcharles Exp $
#
#   package net.sourceforge.jpcap.tutorial.example1
#
PKG = net.sourceforge.jpcap.tutorial.example3
PKG_DIR = $(subst .,/, $(PKG))
REL = ../../../../..
include ${MAKE_HOME}/os.makefile
include ${MAKE_HOME}/rules.makefile

JAVA = \
	Example3 \
	Example3b \
	Example3c \

JAVA_SOURCE = $(addsuffix .java, $(JAVA))
JAVA_CLASSES = $(addsuffix .class, $(JAVA))

all: $(JAVA_CLASSES)

include ${MAKE_HOME}/targets.makefile
include ${MAKE_HOME}/depend.makefile
