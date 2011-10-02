# $Id: makefile,v 1.4 2003/07/02 18:52:34 pcharles Exp $
#
#   package net.sourceforge.jpcap.tutorial.misc
#
PKG = net.sourceforge.jpcap.tutorial.misc
PKG_DIR = $(subst .,/, $(PKG))
REL = ../../../../..
include ${MAKE_HOME}/os.makefile
include ${MAKE_HOME}/rules.makefile

JAVA = \
	MemoryTest \
	OfflineTest \
	SerializationTest \
	ICMPTest 

JAVA_SOURCE = $(addsuffix .java, $(JAVA))
JAVA_CLASSES = $(addsuffix .class, $(JAVA))

all: $(JAVA_CLASSES)

include ${MAKE_HOME}/targets.makefile
include ${MAKE_HOME}/depend.makefile
