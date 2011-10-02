# $Id: jpcap.spec,v 1.22 2004/10/02 00:52:15 pcharles Exp $
#
#   RPM package specification for jpcap
#
%define jpcap_version 0.01.18
%define jpcap jpcap-%jpcap_version

Summary: A tool for capturing and visualizing network traffic
Name: jpcap
Version: %jpcap_version
Release: 1
Copyright: MPL-1.1
Group: Applications/Internet
Packager: Patrick Charles <http://www.sf.net/users/pcharles>
Source: http://prdownloads.sourceforge.net/jpcap/%jpcap.tar.gz
Vendor: <http://jpcap.sf.net>
# Distribution: jpcap <http://jpcap.sf.net>

# since a variety of vendors (Sun, IBM, Kaffe, etc.) offer VM's, 
# it would be better to rely on a generic 'Provides: JVM' type tag.
# Unfortunately, though, none of the VM vendors are currently tagging
# their vm's with a tag to flag a generic VM or JDK capability,
BuildRequires: j2sdk >= 1.2, libpcap >= 0.4
Requires: j2sdk >= 1.2, libpcap >= 0.4
BuildRoot: /tmp/%{name}-%jpcap_version-pkgroot

%description
Jpcap captures network packets and allows the visualization of traffic
patterns in real-time. Users can define filter expressions, examine packet 
data and manipulate graphical representations of hosts and network 
communications.

Included is a jar file containing the jpcap packet capture library
which can be used by Java developers who wish to create their own 
packet capture applications.

Internally, jpcap provides Java-native bindings to and relies on libpcap. 
Jpcap also requires JDK1.2. JDK1.3+ is recommended.

Install jpcap if you'd like to see what's happening on your network.

%prep

%setup

%build
PROJECT_HOME=/usr/src/redhat/BUILD/%jpcap
cd $PROJECT_HOME
gmake clean
gmake

%pre 

%install
cd $PROJECT_HOME
gmake setup_pkgroot

%post

%clean
rm -rf $RPM_BUILD_ROOT

%files
%defattr(-,root,root)
%doc README docs/INSTALL docs/RELEASE_NOTES docs/BUILD.unix docs/BUILD.osx docs/BUILD.windows docs/CREDITS docs/FAQ docs/MPL-1.1.txt

/usr/bin/jpcap
/usr/lib/libjpcap.so
/usr/lib/%jpcap/jars/net.sourceforge.%jpcap.jar
/usr/lib/%jpcap/jars/javadoc_net.sourceforge.%jpcap.jar
/usr/lib/%jpcap/thirdParty/jars/dev-classes_net.ultrametrics-0.03.jar
/usr/lib/%jpcap/thirdParty/jars/fooware_CommandLine-1.0.jar
/usr/lib/%jpcap/properties/tool.properties
/usr/lib/%jpcap/properties/simulator.properties


%changelog
* Tue Jun 26 2001 Patrick Charles <pcharles@users.sourceforge.net>
- added tool.properties to package.
- added Packager and Vendor tags to rpm specification.

* Wed Jun  6 2001 Patrick Charles <pcharles@users.sourceforge.net>
- added dependencies on JDK and libpcap

* Tue Jun  5 2001 Patrick Charles <pcharles@users.sourceforge.net>
- first pass at rpm packaging
