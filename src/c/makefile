# $Id: makefile,v 1.8 2004/03/16 22:49:56 pcharles Exp $
#
#   jpcap/src/c makefile
#
include ${MAKE_HOME}/os.makefile
include ${MAKE_HOME}/rules.makefile

FILES = \
	pcaptests

ifeq ($(OSFLAVOR), UNIX)
	INCLUDES = -I/usr/include/pcap
else
ifeq ($(OSFLAVOR), DARWIN)
	INCLUDES = -I/usr/include/pcap
else
	INCLUDES = -I${WINPCAP_HOME}/Include 
endif
endif

ifeq ($(OSFLAVOR), UNIX)
	LIBS = -lnsl /usr/lib/libpcap.a
else
ifeq ($(OSFLAVOR), DARWIN)
	LIBS = /usr/lib/libpcap.A.dylib
else
	LIBS = -L ${WINPCAP_HOME}/Lib -lwpcap
endif
endif

C_SOURCE = $(addsuffix .c, $(FILES))


all: pcaptests

pcaptests: pcaptests.c
	gcc  $(INCLUDES) pcaptests.c -o $@ $(LIBS)


$(SO_TARGET): $(PKG_UND)_$(WRAPPER).h jpcap.c
	gcc $(INCLUDES) jpcap.c -shared -o $@ $(LIBS)

$(PKG_UND)_$(WRAPPER).h: $(WRAPPER).java
	javah -jni $(PKG).$(WRAPPER)


test: all
	./pcaptests

# release a noop, nothing here to be included in release
release:

include ${MAKE_HOME}/targets.makefile
include ${MAKE_HOME}/depend.makefile
