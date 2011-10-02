// $Id: process.cpp,v 1.2 2004/02/24 21:25:08 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2003, 2004 Patrick Charles and Jonas Lehmann		   *
 * Distributed under the Mozilla Public License				   *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt				   *
 ***************************************************************************/

#include "process.hpp"

/*
 * Utilized by processData() callback, for handling packet data.
 */
void processing::processData(u_char *user, struct pcap_pkthdr *h, jbyte *sp) {
  jmethodID mid;
  jclass cls;
  jbyteArray jba;
  int seconds;
  int useconds;
  register const struct timeval *tvp;

  /* decompose the timestamp contained in the packet header */
  tvp = &h->ts;
  seconds = tvp->tv_sec;
  useconds = tvp->tv_usec;

  //  if(VERBOSE) {
  //	(void)printPacket(user, sp, h->caplen, h->len, seconds, useconds);
  //  }

  /* Check to see if any unhandled exceptions are pending in the VM before
     calling back the packet handler. If an exception is pending, return
     control immediately so that the VM can handle it.
  */
  if(javaEnvironment->ExceptionOccurred())
    return;

  cls = javaEnvironment->GetObjectClass(javaObject);


  mid = javaEnvironment-> GetMethodID(cls, "handlePacket", "(IIII[B)V");

  if(mid == 0) {
    fprintf(stderr, "jpcap: method lookup failure\n");
    fflush(stdout); fflush(stderr);
    return;
  }


  jba = javaEnvironment->NewByteArray(h->caplen);

  javaEnvironment->SetByteArrayRegion
    (jba, 0, h->caplen, sp);

  // dispatch captured data to the handle method in the java capture object..
  javaEnvironment->CallVoidMethod(javaObject, mid,
				     h->len, h->caplen, seconds, useconds,
				     jba);

  // free local storage for the byte array..
  javaEnvironment->DeleteLocalRef(jba);

}


