# $Id: standard_target_chain.makefile,v 1.1 2001/05/17 19:53:05 pcharles Exp $
#   standard build targets for traversing the source tree
# 
# NOTE: this makefile requires that TARGETS be an externally defined list of 
# directories on which the standard build targets will be applied
#
# this makefile provides rules which cause recursion into the specified
# (target) directory and reapplication of the same target
#
include ${MAKE_HOME}/chain_default.makefile
include ${MAKE_HOME}/chain_release.makefile
include ${MAKE_HOME}/chain_install.makefile
include ${MAKE_HOME}/chain_depend.makefile
include ${MAKE_HOME}/chain_clean.makefile
