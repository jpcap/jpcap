# $Id: chain_clean.makefile,v 1.1 2001/05/17 19:53:05 pcharles Exp $
#
# NOTE: depends on externally defined TARGETS
# chain 'clean' target invocations into target subdirectories
#
clean: $(addprefix clean_, $(TARGETS))

$(addprefix clean_, $(TARGETS)): 
	$(MAKE) --directory $(subst clean_,,$@) -f makefile clean
