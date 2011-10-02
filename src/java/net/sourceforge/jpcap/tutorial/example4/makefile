# $Id: makefile,v 1.1 2001/05/30 11:51:28 freecoder Exp $
#
#   package net.sourceforge.jpcap.tutorial.example1
#
PKG = net.sourceforge.jpcap.tutorial.example4
PKG_DIR = $(subst .,/, $(PKG))
REL = ../../../../..
include ${MAKE_HOME}/os.makefile
include ${MAKE_HOME}/rules.makefile

JAVA = \
	Example4

JAVA_SOURCE = $(addsuffix .java, $(JAVA))
JAVA_CLASSES = $(addsuffix .class, $(JAVA))

all: $(JAVA_CLASSES)

include ${MAKE_HOME}/targets.makefile
include ${MAKE_HOME}/depend.makefile
