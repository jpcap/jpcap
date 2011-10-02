# $Id: makefile,v 1.10 2004/03/16 23:26:26 pcharles Exp $
#
#   package net.sourceforge.jpcap.client
#
PKG = net.sourceforge.jpcap.client
PKG_DIR = $(subst .,/,$(PKG))
REL = ../../../..
include ${MAKE_HOME}/os.makefile
include ${MAKE_HOME}/rules.makefile


JAVA = \
	Settings \
	CaptureViewFrame \
	CaptureViewMouseListener \
	PacketVisualizationCanvas \
	HostNameLookupThread \
	HostRenderer \
	CommRenderer \
	\
	CaptureHistory \
	CaptureConsole \
	CaptureTool \
	\
	NetworkFinder


JAVA_SOURCE = $(addsuffix .java, $(JAVA))
JAVA_CLASSES = $(addsuffix .class, $(JAVA))


all: $(JAVA_CLASSES)


LIB_PATH = ${PROJECT_HOME}/src/java/$(PKG_DIR)/../capture

# NOTE: to run the class PacketCapture, the directory where libjpcap
# is located (net/ultrametrics/jpcap?) must be included in LD_LIBRARY_PATH.
test: all
ifeq ($(OSFLAVOR), UNIX)
	export LD_LIBRARY_PATH=$(LIB_PATH) ; java \
	-Dnet.sourceforge.jpcap.properties.path=${PROJECT_HOME}/properties \
	$(PKG).CaptureTool
else
ifeq ($(OSFLAVOR), DARWIN)
	export DYLD_LIBRARY_PATH=$(LIB_PATH) ; java \
	-Dnet.sourceforge.jpcap.properties.path=${PROJECT_HOME}/properties \
	$(PKG).CaptureTool
else 
	export PATH=${PATH}:$(LIB_PATH) ; java \
	-Dnet.sourceforge.jpcap.properties.path=${PROJECT_HOME}/properties \
	$(PKG).CaptureTool
endif
endif


include ${MAKE_HOME}/targets.makefile
include ${MAKE_HOME}/depend.makefile
