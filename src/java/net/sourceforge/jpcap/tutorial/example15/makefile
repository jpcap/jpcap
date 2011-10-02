# $Id: makefile,v 1.1 2002/07/10 23:05:26 pcharles Exp $
#
#   package net.sourceforge.jpcap.tutorial.example15
#
PKG = net.sourceforge.jpcap.tutorial.example15
PKG_DIR = $(subst .,/, $(PKG))
REL = ../../../../..
include ${MAKE_HOME}/os.makefile
include ${MAKE_HOME}/rules.makefile

JAVA = \
	Example15

JAVA_SOURCE = $(addsuffix .java, $(JAVA))
JAVA_CLASSES = $(addsuffix .class, $(JAVA))

all: $(JAVA_CLASSES)

include ${MAKE_HOME}/targets.makefile
include ${MAKE_HOME}/depend.makefile
