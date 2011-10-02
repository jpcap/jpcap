# $Id: releases.makefile,v 1.28 2004/10/02 00:52:15 pcharles Exp $
#
#   jpcap release targets
#

# system version
SVERSION=0.01.18


# the 'server' release consists of everything that is needed to run jpcap
SYS_DEST = $(RELEASE_HOME)/jpcap-$(SVERSION)
PKG_NAME = net.sourceforge.jpcap
LIB_PATH = src/java/net/sourceforge/jpcap/capture


distribution: release_system


server_release_prep: all
	rm -rf $(SYS_DEST)
	mkdir -p $(SYS_DEST)

release_system: server_release_prep
	mkdir -p $(SYS_DEST)/jars
	mkdir -p $(SYS_DEST)/lib
	cp -a $(PROJECT_HOME)/* $(SYS_DEST)
	cp $(RELEASE_HOME)/jars/$(PKG_NAME)-$(SVERSION).jar \
		$(SYS_DEST)/jars
	cp $(RELEASE_HOME)/jars/javadoc_$(PKG_NAME)-$(SVERSION).jar \
		$(SYS_DEST)/jars
	cp $(PROJECT_HOME)/$(LIB_PATH)/libjpcap.so $(SYS_DEST)/lib
	mkdir -p $(RELEASE_HOME)/distributions
	cd $(SYS_DEST) ; ln -s scripts/jpcap jpcap
	tar cf $(RELEASE_HOME)/distributions/jpcap-$(SVERSION).tar \
		-C $(RELEASE_HOME) jpcap-$(SVERSION)
	gzip $(RELEASE_HOME)/distributions/jpcap-$(SVERSION).tar
	rm -rf $(SYS_DEST)

# why doesn't this work?! it works ok with .cvsignore as a -name param
# and it works ok when executed directly, but not inside gmake?
#	cd $(SYS_DEST)/jpcap-$(SVERSION) && find -name CVS -exec rm -rf {} \;


# copy the source distribution into the rpm source tree to prepare for build
#
rpm_prep: release
	cp $(RELEASE_HOME)/distributions/jpcap-$(SVERSION).tar.gz \
		/usr/src/redhat/SOURCES

# deploy files to be included in the rpm into the pkg root staging area
#
PKG_ROOT=/tmp/jpcap-$(SVERSION)-pkgroot/usr
RHD = $(RELEASE_HOME)/distributions
setup_pkgroot:
	mkdir -p $(PKG_ROOT)/bin
	mkdir -p $(PKG_ROOT)/doc/jpcap-$(SVERSION)
	mkdir -p $(PKG_ROOT)/lib/jpcap-$(SVERSION)/thirdParty/jars
	mkdir -p $(PKG_ROOT)/lib/jpcap-$(SVERSION)/jars
	mkdir -p $(PKG_ROOT)/lib/jpcap-$(SVERSION)/properties
	cp scripts/jpcap $(PKG_ROOT)/bin/
	cp thirdParty/jars/dev-classes_net.ultrametrics-0.03.jar \
	  $(PKG_ROOT)/lib/jpcap-$(SVERSION)/thirdParty/jars/
	cp thirdParty/jars/fooware_CommandLine-1.0.jar \
	  $(PKG_ROOT)/lib/jpcap-$(SVERSION)/thirdParty/jars/
	cp $(RELEASE_HOME)/jars/$(PKG_NAME)-$(SVERSION).jar \
	  $(PKG_ROOT)/lib/jpcap-$(SVERSION)/jars/
	cp $(RELEASE_HOME)/jars/javadoc_$(PKG_NAME)-$(SVERSION).jar \
	  $(PKG_ROOT)/lib/jpcap-$(SVERSION)/jars/
	cp $(LIB_PATH)/libjpcap.so $(PKG_ROOT)/lib/
	cp properties/tool.properties \
	  $(PKG_ROOT)/lib/jpcap-$(SVERSION)/properties/
	cp properties/simulator.properties \
	  $(PKG_ROOT)/lib/jpcap-$(SVERSION)/properties/

HT=${HOSTTYPE}
# build the rpm package and copy the RPMS into the distribution tree
# In Red Hat 7, 'rpmbuild' below should be replaced with just 'rpm'
#
rpm: rpm_prep
	rpmbuild -ba rpm/jpcap.spec
	cp /usr/src/redhat/RPMS/$(HT)/jpcap-$(SVERSION)-1.$(HT).rpm $(RHD)/
	cp /usr/src/redhat/SRPMS/jpcap-$(SVERSION)-1.src.rpm $(RHD)/
