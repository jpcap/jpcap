# $Id: chain_release.makefile,v 1.1 2001/05/17 19:53:05 pcharles Exp $
#
# NOTE: depends on externally defined TARGETS
# chain 'release' target invocations into target subdirectories
#
release: $(addprefix release_, $(TARGETS))

$(addprefix release_, $(TARGETS)): 
	$(MAKE) --directory $(subst release_,,$@) -f makefile release
