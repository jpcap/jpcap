# $Id: os.makefile,v 1.7 2004/10/01 23:39:37 pcharles Exp $
#
#   specific os dependencies and settings
#

# On NT and 95, gmake + shmsdos + uname
#   (ftp://ftp.mozilla.org/pub/mozilla/source/wintools.zip)
# work alright.
#
# uname is required by this makefile

# note:
#   the cygnus port of gnu make doesn't doesn't work very well in a dos 
#   shell. even with a shell wrapper, like shmsdos, nested makes
#   don't seem to work properly (cygwin-b20)

PLATFORM = $(shell "uname")

## windows 95
##
ifeq ($(PLATFORM), WIN95)
	PSEP = \$(empty)
# it would be better to stick with dos commands like 'del'
# as much as possible to reduce the 3rd party tool requirement, 
# but 'del' with wildcards doesn't work with cygnus b20 gnu make
	RM = rm -f
	OSFLAVOR=DOS

## windows NT
## 
else 
ifeq ($(PLATFORM), WINNT)
	PSEP = \$(empty)
	OSFLAVOR=DOS

## windows NT4 with cygnus
## 
else 
ifeq ($(PLATFORM), CYGWIN_NT-4.0)
	PSEP = \$(empty)
	OSFLAVOR=CYGWIN

## windows NT5 with cygnus
## 
else 
ifeq ($(PLATFORM), CYGWIN_NT-5.0)
	PSEP = \$(empty)
	OSFLAVOR=CYGWIN

## windows NT4 with minGW
## 
else 
ifeq ($(PLATFORM), MINGW32_NT-4.0)
	PSEP = \$(empty)
	OSFLAVOR=MINGW

## windows NT5 with minGW
## 
else 
ifeq ($(PLATFORM), MINGW32_NT-5.0)
	PSEP = \$(empty)
	OSFLAVOR=MINGW

## windows XP with minGW
## 
else 
ifeq ($(PLATFORM), MINGW32_NT-5.1)
	PSEP = \$(empty)
	OSFLAVOR=MINGW

## Mac OS X, Darwin
##
else
ifeq ($(PLATFORM), Darwin)
	PSEP = /
	OSFLAVOR=DARWIN

## everybody else.. unix, linux, etc.
else
	PSEP = /
	OSFLAVOR=UNIX
endif
endif
endif
endif
endif
endif
endif
endif
