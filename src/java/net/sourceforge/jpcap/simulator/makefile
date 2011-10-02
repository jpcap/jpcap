# $Id: makefile,v 1.2 2001/06/26 22:54:01 pcharles Exp $
#
#   package net.sourceforge.jpcap.simulator
#
PKG = net.sourceforge.jpcap.simulator
PKG_DIR = $(subst .,/, $(PKG))
REL = ../../../..
include ${MAKE_HOME}/os.makefile
include ${MAKE_HOME}/rules.makefile


JAVA = \
	Settings \
	HeaderGenerator \
	PacketGenerator \
	PacketCaptureSimulator


JAVA_SOURCE = $(addsuffix .java, $(JAVA))
JAVA_CLASSES = $(addsuffix .class, $(JAVA))


all: $(JAVA_CLASSES)


include ${MAKE_HOME}/targets.makefile
include ${MAKE_HOME}/depend.makefile
