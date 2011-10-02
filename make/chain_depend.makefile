# $Id: chain_depend.makefile,v 1.1 2001/05/17 19:53:05 pcharles Exp $
#
# NOTE: depends on externally defined TARGETS
# chain 'depend' target invocations into target subdirectories
#
depend: $(addprefix depend_, $(TARGETS))

$(addprefix depend_, $(TARGETS)): 
	$(MAKE) --directory $(subst depend_,,$@) -f makefile depend
