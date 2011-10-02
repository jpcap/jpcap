// $Id: pcaptests.c,v 1.4 2004/02/26 06:44:26 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

// This executable is a test stub for debugging jpcap/pcap native layer,
// without the Java component.
//
// @author Patrick Charles
// @version $Revision: 1.4 $
// @lastModifiedBy $Author: pcharles $
// @lastModifiedAt $Date: 2004/02/26 06:44:26 $
//
#include <stdio.h>
#include <stdlib.h>
#include <pcap.h>

static pcap_t *PD = NULL;

int lookupTest() {
  char ebuf[PCAP_ERRBUF_SIZE];
  int netmask, network;
  char *device;

  device = pcap_lookupdev(ebuf);
  if(device == NULL) {
    fprintf(stderr, "no device found");
    return 0;
  }
  fprintf(stderr, "device = %s\n", device);

  if(pcap_lookupnet((char*)device, &network, &netmask, ebuf) < 0) {
    fprintf(stderr, "error: %s\n", ebuf);
    return 0;
  }

  fprintf(stderr, "network: %x\nnetmask: %x\n", network, netmask);
  return 1;
}

int filterTest() {
  char ebuf[PCAP_ERRBUF_SIZE];
  int snaplen = 96;
  int promiscuous = 1;
  int timeout = 1000;
  int optimize = 1;
  char *device;
  struct bpf_program bpp;
  char *filter = "host www.yahoo.com";

  device = pcap_lookupdev(ebuf);
  if(device == NULL) {
    fprintf(stderr, "no device found");
    return 0;
  }
  fprintf(stderr, "device = %s\n", device);

  PD = pcap_open_live((char*)device, snaplen, promiscuous, timeout, ebuf);
  if(PD == NULL) {
    fprintf(stderr, "open error: %s\n", ebuf);
    return 0;
  }
  /*
  // set bad filter..
  filter = "port x";
  if(pcap_compile(PD, &bpp, (char*)filter, optimize, 0) == -1) {
    fprintf(stderr, "compile error\n");
    return 0;
  }
  if(pcap_setfilter(PD, &bpp) == -1) {
    fprintf("filter set error: %s\n", pcap_geterr(PD));
    return 0;
  }
  */
  fprintf(stderr, "set filter ok\n");
}

void printPacket(u_char *user, u_char *cp, u_int caplen, u_int length, 
                 u_int seconds, u_int useconds)
{
  register u_int i, s;
  register int nshorts;
  
  nshorts = (u_int) caplen / sizeof(u_short);

  fprintf(stderr, "Packet: u = %s, l = %d of %d, t = %u.%06u, d = ", 
          user, caplen, length, seconds, useconds);

  i = 0;
  while (--nshorts >= 0) {
    if ((i++ % 8) == 0)
      fprintf(stderr, "\n\t\t\t");
    s = *cp++;
    fprintf(stderr, " %02x%02x", s, *cp++);
  }

  if (caplen & 1) {
    if ((i % 8) == 0)
      (void)fprintf(stderr, "\n\t\t\t");
    (void)fprintf(stderr, " %02x", *cp);
  }

  fprintf(stderr, "\t\n");
}

void processData(u_char *user, struct pcap_pkthdr *h, u_char *sp) {
  int seconds;
  int useconds;
  register const struct timeval *tvp;
  int i;

  /* decompose the timestamp contained in the packet header */
  tvp = &h->ts;
  seconds = tvp->tv_sec;
  useconds = tvp->tv_usec;

  (void)printPacket(user, sp, h->caplen, h->len, seconds, useconds);
}

int main(int argc, char **argv) {
  lookupTest();
  filterTest();
  fprintf(stderr, "capturing 1 packet.. \n");
  pcap_loop(PD, 1, (pcap_handler)processData, 0);
}


const char *rcsid = 
  "$Id: pcaptests.c,v 1.4 2004/02/26 06:44:26 pcharles Exp $";
