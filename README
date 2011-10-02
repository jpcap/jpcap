

                               jpcap README


  General Information

    jpcap is a set of Java classes which provide an interface and system 
    for network packet capture. A protocol library and tool for visualizing 
    network traffic is included. 

    jpcap hides the low-level details of network packet capture by 
    abstracting many network packet types and protocols into Java classes. 
    Internally, jpcap implements bindings to the libpcap system library 
    through JNI (the Java Native Interface). 

    jpcap utilizes libpcap, a widely deployed shared-library for capturing 
    user-level packets. libpcap must be installed on your system in order 
    to use jpcap.

    jpcap consists of a small shared-library which wraps libpcap plus a 
    collection of Java classes. The shared-library component provides event
    hooks, communication and data conversion between a running Java VM and 
    libpcap. The 'capture' package contains the core capture system.
    The 'net' package contains abstractions for many network packet types 
    and protocols. The 'simulator' package contains a network simulator.

    jpcap is licensed under the Mozilla Public License. See docs/MPL-1.1.txt.


  Running the Samples

    If you have installed from RPM and are looking to run the capture tool,
    try /usr/bin/jpcap.

    If you installed from .gz, try ./jpcap.

    jpcap can also be run in 'simulator' mode. In this mode, a pseudo
    network interface randomly generates network traffic for capture.
    The simulator can be enabled in properties/tool.properties.


  Unix vs. Mac OS X vs. Windows

    Active jpcap development is taking place on Unix platforms. However, 
    jpcap should run on any platform where libpcap is implemented.

    For building the jpcap library on Windows, the MinGW environment is 
    recommended. Also, the Windows port of libpcap, known as winpcap, is 
    required. For more information, have a look at docs/BUILD.windows.

    jpcap on Mac OS X is supported. Refer to the file docs/BUILD.osx.


  Application Developers (using the packet capture library in other apps)

    If you are a Java developer interested in using jpcap to capture 
    packets in an application you are developing, you need to do the 
    following: 

      o In your Java CLASSPATH, include jars/net.sourceforge.jpcap-x.yy.zz.jar
        plus all jars found in thirdParty/jars.

      o Native bindings to libpcap are provided precompiled for intel Linux.
        The Java wrapper shared object is 
          src/java/net/sourceforge/jpcap/capture/libjpcap.so

        This file needs to be included in your LD_LIBRARY_PATH.
        If you are working on another platform, you'll need to 
        build the jpcap library and shared object yourself, see below.

      o All capture operations are performed using methods from the class
          net.sourceforge.jpcap.capture.PacketCapture

      o Javadocs are located in jars/javadoc_net.sourceforge.jpcap-x.yy.zz.jar

      o Examples are provided in the package net.sourceforge.jpcap.tutorial.

      
    More specific documentation on the jpcap java packages can be found in 
    src/java/net/sourceforge/jpcap/README.

    If you checked out jpcap from the CVS repository and are looking 
    for information on building the jpcap library in this tree, 
    see docs/BUILD.[platform].

    Additional information can be found on sourceforge.net, where 
    the project was actively developed since 2001.

      http://www.sf.net/projects/jpcap

    More recent development, more active development is occurring on github:

      http://www.github.com/jpcap


  Contributing

    If you are using jpcap and have made enhancements or bug fixes to the 
    core capture library, please contribute them.

    You can contribute by forking the project and sending a pull request
    to http://www.github.com/jpcap.

    If you've developed an application using jpcap, we're interested in 
    hearing about it at jpcapadmin at ultrametrics dot net.

    Happy Hacking!

