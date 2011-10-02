# $Id: makefile,v 1.1 2002/02/18 21:49:49 pcharles Exp $
#
#   package net.sourceforge.jpcap.tutorial.sniffer
#
PKG = net.sourceforge.jpcap.tutorial.sniffer
PKG_DIR = $(subst .,/, $(PKG))
REL = ../../../../..
include ${MAKE_HOME}/os.makefile
include ${MAKE_HOME}/rules.makefile

JAVA = \
	Sniffer

JAVA_SOURCE = $(addsuffix .java, $(JAVA))
JAVA_CLASSES = $(addsuffix .class, $(JAVA))

all: $(JAVA_CLASSES)

include ${MAKE_HOME}/targets.makefile
include ${MAKE_HOME}/depend.makefile
