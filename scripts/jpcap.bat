rem $Id: jpcap.bat,v 1.7 2004/10/02 00:52:16 pcharles Exp $
rem 
rem   This batch file is used to start the jpcap sample client 
rem   and visualization tool for users of the win32 distribution.
rem 
rem   NOTE: The directory containing the winpcap dll's must be in your 
rem   path before running this script. If you installed the WinPcap
rem   auto-installer, then this should already be the case.
rem 
rem   NOTE: The Java executable must be in your path before running 
rem   this script. e.g.
rem     set JAVA_HOME=c:\j2sdk1.4.2_04
rem     set PATH=%PATH%;%JAVA_HOME%\bin
rem
set CLASSPATH=jars\dev-classes_net.ultrametrics-0.03.jar
set CLASSPATH=%CLASSPATH%;jars\fooware_CommandLine-1.0.jar
set CLASSPATH=%CLASSPATH%;jars\net.sourceforge.jpcap-0.01.18.jar
set PATH=lib;%PATH%
java -Dnet.sourceforge.jpcap.properties.path=properties net.sourceforge.jpcap.client.CaptureTool

