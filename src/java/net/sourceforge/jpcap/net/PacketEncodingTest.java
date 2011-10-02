// $Id: PacketEncodingTest.java,v 1.1 2002/07/10 23:12:09 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package net.sourceforge.jpcap.net;

import junit.framework.*;
import net.sourceforge.jpcap.net.PacketEncoding;

/**
 *
 * @author jguthrie
 */
public class PacketEncodingTest extends TestCase {
    byte [] testBytes = new byte [] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int headerLen = 3;
    byte [] goodHeader0 = new byte [] {1, 2, 3};
    byte [] goodData0 = new byte [] {4, 5, 6, 7, 8, 9, 10};
    byte [] goodSizedData0 = new byte [] {4, 5, 6, 7};
    byte [] goodHeader2 = new byte [] {3, 4, 5};
    byte [] goodData2 = new byte [] {6, 7, 8, 9, 10};
    byte [] goodSizedData2 = new byte [] {6, 7, 8, 9};

    public PacketEncodingTest(java.lang.String testName) {
        super(testName);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(PacketEncodingTest.class);
        
        return suite;
    }
    
    private boolean sameBytes(byte[] b1,byte[] b2) {
        if ( ( b1 == null ) || ( b2 == null ) ) return false; // nulls are bad
        if ( b1.length != b2.length ) return false;  // different lengths are bad
        for ( int i = 0; i < b1.length; i++ ) {
            if ( b1[i] != b2[i] ) return false;  // different values are bad
        }
        return true;  // nothing bad, so that's good
    }
    
    private String bytesAsString(byte[] bytes) {
        if ( bytes == null ) return "null";
        StringBuffer buf = new StringBuffer("[");
        String sep = "";
        for ( int i = 0; i < bytes.length; i++ ) {
            buf.append(sep);
            sep = ",";
            buf.append(bytes[i]);
        }
        buf.append("]");
        return buf.toString();
    }
    
    /** Test of extractHeader method with header start at position 0 */
    public void testExtractHeaderAtPosition0() {
        byte [] header = PacketEncoding.extractHeader(0, headerLen, testBytes);
        assertTrue("byte array mismatch, "+bytesAsString(header)+", should be "+
                   bytesAsString(goodHeader0),sameBytes(header,goodHeader0));
    }
    
    /** Test of extractHeader method with header start at position 2 */
    public void testExtractHeaderAtPosition2() {
        byte [] header = PacketEncoding.extractHeader(2, headerLen, testBytes);
        assertTrue("byte array mismatch, "+bytesAsString(header)+", should be "+
                   bytesAsString(goodHeader2),sameBytes(header,goodHeader2));
    }
    
    /** Test of extractHeader method with offset out of bounds.
        Should return 0 length byte array */
    public void testExtractHeaderTooLargeOffset() {
        byte [] header = PacketEncoding.extractHeader(20, headerLen, testBytes);
        assertTrue("bad parameter did not return 0-length array", (header.length==0));
    }
    
    /** Test of extractHeader method with offset out of bounds.
        Should return whole byte array */
    public void testExtractHeaderTooLargeLength() {
        byte [] header = PacketEncoding.extractHeader(0, 20, testBytes);
        assertTrue("byte array mismatch, "+bytesAsString(header)+", should be "+
                   bytesAsString(testBytes), sameBytes(header,testBytes));
    }
    
    /** Test of extractHeader method with negative offset. */
    public void testExtractHeaderNegativeOffset() {
        byte [] header = PacketEncoding.extractHeader(-20, headerLen, testBytes);
        assertTrue("negative parameter did not return 0-length array", (header.length==0));
    }
    
    /** Test of extractHeader method with negative header length.
        Should return 0 length byte array */
    public void testExtractHeaderNegativeLength() {
        byte [] header = PacketEncoding.extractHeader(0, -20, testBytes);
        assertTrue("negative parameter did not return 0-length array", (header.length==0));
    }
    
    /** Test of extractHeader method with null input array.
        Should return null byte array */
    public void testExtractHeaderNullArray() {
        byte [] header = PacketEncoding.extractHeader(0, 20, null);
        assertNull("null in did not return null",header);
    }
    
    /** Test of extractData method with header start at position 0 */
    public void testExtractDataAtPosition0() {
        byte [] data = PacketEncoding.extractData(0, headerLen, testBytes);
        assertTrue("byte array mismatch, "+bytesAsString(data)+", should be "+
                   bytesAsString(goodData0), sameBytes(data,goodData0));
    }
    
    /** Test of extractData method with header start at position 2 */
    public void testExtractDataAtPosition2() {
        byte [] data = PacketEncoding.extractData(2, headerLen, testBytes);
        assertTrue("byte array mismatch, "+bytesAsString(data)+", should be "+
                   bytesAsString(goodData2), sameBytes(data,goodData2));
    }
    
    /** Test of extractData method with offset out of bounds.
        Should return 0 length byte array */
    public void testExtractDataTooLargeOffset() {
        byte [] data = PacketEncoding.extractData(20, headerLen, testBytes);
        assertTrue("bad parameter did not return 0-length array", (data.length==0));
    }
    
    /** Test of extractData method with header length out of bounds.
        Should return whole byte array */
    public void testExtractDataTooLargeLength() {
        byte [] data = PacketEncoding.extractData(0, 20, testBytes);
        assertTrue("bad parameter did not return 0-length array", (data.length==0));
    }
    
    /** Test of extractData method with negative offset.
        Should return 0 length byte array */
    public void testExtractDataNegativeOffset() {
        byte [] data = PacketEncoding.extractData(-20, headerLen, testBytes);
        assertTrue("negative parameter did not return 0-length array", (data.length==0));
    }
        
    /** Test of extractData method with negative header length.
        Should return 0 length byte array */
    public void testExtractDataNegativeLength() {
        byte [] data = PacketEncoding.extractData(0, -20, testBytes);
        assertTrue("negative parameter did not return 0-length array", (data.length==0));
    }
    
    /** Test of extractData method with null input array.
        Should return null byte array */
    public void testExtractDataNullArray() {
        byte [] data = PacketEncoding.extractData(0, 20, null);
        assertNull("null in did not return null",data);
    }
    
    /** Test of extractData method with header start at position 0 */
    public void testExtractSizedDataAtPosition0() {
        byte [] data = PacketEncoding.extractData(0, headerLen, testBytes, 4);
        assertTrue("byte array mismatch, "+bytesAsString(data)+", should be "+
                   bytesAsString(goodSizedData0), sameBytes(data,goodSizedData0));
    }
    
    /** Test of extractData method with header start at position 0 */
    public void testExtractSizedDataAtPosition2() {
        byte [] data = PacketEncoding.extractData(2, headerLen, testBytes, 4);
        assertTrue("byte array mismatch, "+bytesAsString(data)+", should be "+
                   bytesAsString(goodSizedData2), sameBytes(data,goodSizedData2));
    }
    
    /** Test of extractData method with offset out of bounds.
        Should return 0 length byte array */
    public void testExtractSizedDataTooLargeOffset() {
        byte [] data = PacketEncoding.extractData(20, headerLen, testBytes, 4);
        assertTrue("bad parameter did not return 0-length array", (data.length==0));
    }
    
    /** Test of extractData method with header length out of bounds.
        Should return whole byte array */
    public void testExtractSizedDataTooLargeLength() {
        byte [] data = PacketEncoding.extractData(0, 20, testBytes, 4);
        assertTrue("bad parameter did not return 0-length array", (data.length==0));
    }
    
    /** Test of extractData method with negative size.
        Should return 0 length byte array */
    public void testExtractSizedDataNegativeSize() {
        byte [] data = PacketEncoding.extractData(0, headerLen, testBytes, -2);
        assertTrue("bad parameter did not return 0-length array", (data.length==0));
    }
    
    /** Test of extractData method with header length out of bounds.
        Should return whole byte array */
    public void testExtractSizedDataTooLargeSize() {
        byte [] data = PacketEncoding.extractData(0, headerLen, testBytes, 20);
        assertTrue("byte array mismatch, "+bytesAsString(data)+", should be "+
                   bytesAsString(goodData0), sameBytes(data,goodData0));
    }
    
    /** Test of extractData method with null input array.
        Should return null byte array */
    public void testExtractSizedDataNullArray() {
        byte [] data = PacketEncoding.extractData(0, 20, null, 4);
        assertNull("null in did not return null",data);
    }
    
}
