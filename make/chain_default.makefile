# $Id: chain_default.makefile,v 1.1 2001/05/17 19:53:05 pcharles Exp $
#
# NOTE: depends on externally defined TARGETS
# chain default target invocations into target subdirectories
#
.PHONY: $(TARGETS)

all: $(TARGETS) 

$(TARGETS):
	$(MAKE) --directory $@ -f makefile
