# $Id: makefile,v 1.16 2002/07/10 23:29:56 pcharles Exp $
#
#   package net.sourceforge.jpcap.capture
#
PKG = net.sourceforge.jpcap.net
PKG_DIR = $(subst .,/, $(PKG))
PKG_UND = $(subst .,_, $(PKG))
REL = ../../../..
include ${MAKE_HOME}/os.makefile
include ${MAKE_HOME}/rules.makefile

WRAPPER = PacketCapture

JAVA = \
	RawPacket \
	\
	EthernetProtocol \
	EthernetProtocols \
	IPProtocol \
	IPProtocols \
	IPVersions \
	TypesOfService \
	LinkLayer \
	LinkLayers \
	ICMPMessage \
	ICMPMessages \
	IGMPMessage \
	IGMPMessages \
	IPPort \
	IPPorts \
	IPAddress \
	MACAddress \
	\
	\
	PacketEncodingTest \
		EthernetPacketTest \
			IPPacketTest \
				TCPPacketTest \
	PacketEncoding \
		EthernetFields \
			ARPFields \
			IPFields \
				ICMPFields \
				IGMPFields \
				TCPFields \
				UDPFields \
	\
	Packet \
		EthernetPacket \
			ARPPacket \
			IPPacket \
				ICMPPacket \
				IGMPPacket \
				TCPPacket \
				UDPPacket \
	\
	PacketFactory


JAVA_SOURCE = $(addsuffix .java, $(JAVA))
JAVA_CLASSES = $(addsuffix .class, $(JAVA))


all: $(JAVA_CLASSES)


include ${MAKE_HOME}/targets.makefile
include ${MAKE_HOME}/depend.makefile
