# $Id: chain_install.makefile,v 1.1 2001/05/17 19:53:05 pcharles Exp $
#
# NOTE: depends on externally defined TARGETS
# chain 'install' target invocations into target subdirectories
#
install: $(addprefix install_, $(TARGETS))

$(addprefix install_, $(TARGETS)): 
	$(MAKE) --directory $(subst install_,,$@) -f makefile install
