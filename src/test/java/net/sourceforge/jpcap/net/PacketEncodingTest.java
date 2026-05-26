/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.net;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class PacketEncodingTest {

    private static final byte[] TEST_BYTES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int HEADER_LEN = 3;

    @Test
    void extractHeaderAtPosition0() {
        assertArrayEquals(new byte[] {1, 2, 3},
            PacketEncoding.extractHeader(0, HEADER_LEN, TEST_BYTES));
    }

    @Test
    void extractHeaderAtPosition2() {
        assertArrayEquals(new byte[] {3, 4, 5},
            PacketEncoding.extractHeader(2, HEADER_LEN, TEST_BYTES));
    }

    @Test
    void extractHeaderTooLargeOffset() {
        assertEquals(0,
            PacketEncoding.extractHeader(20, HEADER_LEN, TEST_BYTES).length);
    }

    @Test
    void extractHeaderTooLargeLength() {
        assertArrayEquals(TEST_BYTES,
            PacketEncoding.extractHeader(0, 20, TEST_BYTES));
    }

    @Test
    void extractHeaderNegativeOffset() {
        assertEquals(0,
            PacketEncoding.extractHeader(-20, HEADER_LEN, TEST_BYTES).length);
    }

    @Test
    void extractHeaderNegativeLength() {
        assertEquals(0,
            PacketEncoding.extractHeader(0, -20, TEST_BYTES).length);
    }

    @Test
    void extractHeaderNullArray() {
        assertNull(PacketEncoding.extractHeader(0, 20, null));
    }

    @Test
    void extractDataAtPosition0() {
        assertArrayEquals(new byte[] {4, 5, 6, 7, 8, 9, 10},
            PacketEncoding.extractData(0, HEADER_LEN, TEST_BYTES));
    }

    @Test
    void extractDataAtPosition2() {
        assertArrayEquals(new byte[] {6, 7, 8, 9, 10},
            PacketEncoding.extractData(2, HEADER_LEN, TEST_BYTES));
    }

    @Test
    void extractDataTooLargeOffset() {
        assertEquals(0,
            PacketEncoding.extractData(20, HEADER_LEN, TEST_BYTES).length);
    }

    @Test
    void extractDataTooLargeLength() {
        assertEquals(0,
            PacketEncoding.extractData(0, 20, TEST_BYTES).length);
    }

    @Test
    void extractDataNegativeOffset() {
        assertEquals(0,
            PacketEncoding.extractData(-20, HEADER_LEN, TEST_BYTES).length);
    }

    @Test
    void extractDataNegativeLength() {
        assertEquals(0,
            PacketEncoding.extractData(0, -20, TEST_BYTES).length);
    }

    @Test
    void extractDataNullArray() {
        assertNull(PacketEncoding.extractData(0, 20, null));
    }

    @Test
    void extractSizedDataAtPosition0() {
        assertArrayEquals(new byte[] {4, 5, 6, 7},
            PacketEncoding.extractData(0, HEADER_LEN, TEST_BYTES, 4));
    }

    @Test
    void extractSizedDataAtPosition2() {
        assertArrayEquals(new byte[] {6, 7, 8, 9},
            PacketEncoding.extractData(2, HEADER_LEN, TEST_BYTES, 4));
    }

    @Test
    void extractSizedDataTooLargeOffset() {
        assertEquals(0,
            PacketEncoding.extractData(20, HEADER_LEN, TEST_BYTES, 4).length);
    }

    @Test
    void extractSizedDataTooLargeLength() {
        assertEquals(0,
            PacketEncoding.extractData(0, 20, TEST_BYTES, 4).length);
    }

    @Test
    void extractSizedDataNegativeSize() {
        assertEquals(0,
            PacketEncoding.extractData(0, HEADER_LEN, TEST_BYTES, -2).length);
    }

    @Test
    void extractSizedDataTooLargeSize() {
        assertArrayEquals(new byte[] {4, 5, 6, 7, 8, 9, 10},
            PacketEncoding.extractData(0, HEADER_LEN, TEST_BYTES, 20));
    }

    @Test
    void extractSizedDataNullArray() {
        assertNull(PacketEncoding.extractData(0, 20, null, 4));
    }
}
