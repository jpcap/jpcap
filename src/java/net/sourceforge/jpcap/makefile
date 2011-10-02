# $Id: makefile,v 1.21 2004/10/02 00:52:16 pcharles Exp $
#
#   package net.sourceforge.jpcap
#
PKG_VERSION = 0.01.18
PKG_TOP = net.sourceforge.jpcap
REL = ../../..

TARGETS = \
	util \
	net \
	capture \
	client \
	simulator \
	tutorial

JAVADOC_TARGETS = \
	util \
	net \
	capture \
	client \
	simulator


# default target and tree traversal for standard targets
include ${MAKE_HOME}/chain_default.makefile
#include ${MAKE_HOME}/chain_release.makefile
include ${MAKE_HOME}/chain_install.makefile
include ${MAKE_HOME}/chain_depend.makefile
include ${MAKE_HOME}/chain_clean.makefile


# override release traversal..
release: version jar javadoc

version:
	@echo version stamp


## release
RH = $(subst \,/, ${RELEASE_HOME})

ADD_TARGETS = $(JAVADOC_TARGETS)
PKG_NAMES = $(addprefix $(PKG_TOP)., $(ADD_TARGETS))
JAVADOC_TITLE = "jpcap System Class Documentation"
JDOCJAR_NAME=$(RH)/jars/javadoc_$(PKG_TOP)-$(PKG_VERSION).jar
javadoc: 
	mkdir -p $(RH)/jdoc
	mkdir -p $(RH)/jdoc/jpcap
	javadoc -windowtitle $(JAVADOC_TITLE) \
	-d $(RH)/jdoc/jpcap $(PKG_NAMES)
	jar cvf $(JDOCJAR_NAME) -C $(RH)/jdoc/jpcap .

JAR_NAME=$(RH)/jars/$(PKG_TOP)-$(PKG_VERSION).jar
PKG_PATHS = $(subst .,/, $(PKG_TOP))
PKG_JAR_COUPLES = $(addprefix -C $(REL) , $(PKG_PATHS))
jar: 
	mkdir -p $(RH)/jars
	jar cvf $(JAR_NAME) README
	jar uvf $(JAR_NAME) $(PKG_JAR_COUPLES)

CJAR_NAME=$(RH)/jars/client_$(PKG_TOP)-$(PKG_VERSION).jar
CPKGS = $(addprefix $(PKG_TOP)., $(CLIENT_TARGETS))
CPKG_PATHS = $(subst .,/, $(CPKGS))
CPKG_JAR_COUPLES = $(addprefix -C $(REL) , $(CPKG_PATHS))
cjar: 
	mkdir -p $(RH)/jars
	jar cvf $(CJAR_NAME) README
	jar uvf $(CJAR_NAME) $(CPKG_JAR_COUPLES)
