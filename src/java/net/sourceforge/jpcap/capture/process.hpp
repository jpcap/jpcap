// $Id: process.hpp,v 1.2 2004/02/24 21:25:08 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2003, 2004 Patrick Charles and Jonas Lehmann		   *
 * Distributed under the Mozilla Public License				   *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt				   *
 ***************************************************************************/

extern "C" {
#include <pcap.h>
}
#include <JavaVM/jni.h>


/**
 * Encapsulation of packet processing, with managed of separate instances,
 *
 * Submitted by Anatol Novchenko, along with numerous changes to jpcap.c.
 */
class processing {

 public:
   void processData(u_char *user, struct pcap_pkthdr *h, jbyte *sp);
   jobject javaObject;// reference to java object hooked into this wrapper lib.
   JNIEnv *javaEnvironment; // java vm containing running java object.

};

