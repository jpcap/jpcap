# $Id: depend.makefile,v 1.1 2001/05/17 19:53:05 pcharles Exp $
#
depend: generated_dependencies.u

# generated makefiles
generated_dependencies.u:
	jikes +M=$@ *.java
