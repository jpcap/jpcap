# $Id: makefile,v 1.5 2001/06/07 18:24:26 pcharles Exp $
#
#   jpcap top-level makefile
#
include ${MAKE_HOME}/os.makefile

TARGETS = \
	src/java \
	src/c


include ${MAKE_HOME}/chain_default.makefile
# standard release chain is overridden to perform release and distro build
#include ${MAKE_HOME}/chain_release.makefile
include ${MAKE_HOME}/chain_install.makefile
include ${MAKE_HOME}/chain_depend.makefile
include ${MAKE_HOME}/chain_clean.makefile
include make/releases.makefile

recursive_release: $(addprefix release_, $(TARGETS))

$(addprefix release_, $(TARGETS)): 
	$(MAKE) --directory $(subst release_,,$@) -f makefile release

release: all recursive_release distribution

update:
	cvs update -d

help:
	@echo currently supported targets are: all, clean, and release
