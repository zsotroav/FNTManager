package com.zsotroav.Util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.zsotroav.Util.BitTurmix.*;
import static org.junit.Assert.*;

public class BitTurmixTest {

    byte[] b;

    @Before public void initTest() {
        b = new byte[] {
                0x01,
                0x0A,
                (byte) 0xFF,
                (byte) 0xFF,
                0x20,
                0x30,
                0x41,
                (byte) 0xC3, (byte) 0x81,
        };
    }

    @Test public void Uint8A() {
        int val = byteToUInt8(b, 0);
        assertEquals(1, val);
    }

    @Test public void Uint8B() {
        int val = byteToUInt8(b);
        assertEquals(1, val);
    }

    @Test public void Uint8C() {
        int val = byteToUInt8(b,1);
        assertEquals(10, val);
    }

    @Test public void Uint8D() {
        int val = byteToUInt8(b,2);
        assertEquals(255, val);
    }

    // 0x010A == 266
    @Test public void Uint16A() {
        int val = byteToUInt16(b, 0);
        assertEquals(266, val);
    }

    // Start at the beginning
    @Test public void Uint16B() {
        int val = byteToUInt16(b);
        assertEquals(266, val);
    }

    // Invalid input
    @Test public void Uint16C() {
        int val = byteToUInt16(b, b.length);
        assertEquals(-1, val);
    }

    @Test public void Uint16D() {
        int val = byteToUInt16(b, 1);
        assertEquals(2815, val);
    }

    @Test public void IntToByteArrayA() {
        var val = intToByteArray(1);
        assertArrayEquals(new byte[] {0x01}, val);
    }

    @Test public void IntToByteArrayB() {
        var val = intToByteArray(255);
        assertArrayEquals(new byte[] {(byte)0xFF}, val);
    }

    @Test public void IntToByteArrayC() {
        var val = intToByteArray(256);
        assertArrayEquals(new byte[] {0x01, 0x00}, val);
    }

    @Test public void IntToByteArrayD() {
        var val = intToByteArray(65535);
        assertArrayEquals(new byte[] {(byte) 0xFF, (byte) 0xFF}, val);
    }

    @Test public void IntToByteArrayE() {
        var val = intToByteArray(16777215);
        assertArrayEquals(new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, val);
    }

    @Test public void IntToByteArrayF() {
        var val = intToByteArray(-1);
        assertArrayEquals(new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, val);
    }

    @Test public void IntToByteArray2A() {
        var val = intToByteArray2(0);
        assertArrayEquals(new byte[] {0x00, 0x00}, val);
    }

    @Test public void IntToByteArray2B() {
        var val = intToByteArray2(1);
        assertArrayEquals(new byte[] {0x00, 0x01}, val);
    }

    @Test public void IntToByteArray2C() {
        var val = intToByteArray2(255);
        assertArrayEquals(new byte[] {0x00, (byte) 0xFF}, val);
    }

    @Test public void IntToByteArray2D() {
        var val = intToByteArray2(256);
        assertArrayEquals(new byte[] {0x01, 0x00}, val);
    }

    @Test public void IntToByteArray2E() {
        var val = intToByteArray2(65535);
        assertArrayEquals(new byte[] {(byte) 0xFF, (byte) 0xFF}, val);
    }

    @Test public void IntToByteArray2F() {
        var val = intToByteArray2(16777215);
        assertArrayEquals(new byte[] {(byte) 0xFF, (byte) 0xFF}, val);
    }

    @Test public void ByteToUTF8A() {
        var val = byteToUTF8(b, 4, 1);
        assertEquals(' ', val);
    }

    @Test public void ByteToUTF8B() {
        var val = byteToUTF8(b, 5, 1);
        assertEquals('0', val);
    }

    @Test public void ByteToUTF8C() {
        var val = byteToUTF8(b, 6, 1);
        assertEquals('A', val);
    }

    @Test public void ByteToUTF8D() {
        var val = byteToUTF8(b, 7, 2);
        assertEquals('Á', val);
    }

    @Test public void ByteIntToUTF8B() {
        var val = byteIntToUTF8(32);
        assertEquals(' ', val);
    }

    @Test public void ByteIntToUTF8A() {
        var val = byteIntToUTF8(50049);
        assertEquals('Á', val);
    }

    @Test public void UTF8ToByteA() {
        var val = UTF8ToByteArray(' ');
        assertArrayEquals(new byte[] { (byte)0x20}, val);
    }

    @Test public void UTF8ToByteB() {
        var val = UTF8ToByteArray('Á');
        assertArrayEquals(new byte[]{(byte) 0xC3, (byte) 0x81}, val);
    }

    @Test public void BoolByteArrA() {
        boolean[] bb = {
            true, false, true, false, true, false, true, false,
            false, true, false, true, false, true, false, true
        };

        var val = boolArrayToByteArray(bb);
        assertArrayEquals(new byte[] {(byte) 0xAA, (byte) 0x55}, val);
    }

    @Test public void BoolByteArrB() {
        boolean[] bb = {
                false, false, false, false, true, true, true, true
        };

        var val = boolArrayToByteArray(bb);
        assertArrayEquals(new byte[] {(byte) 0x0F}, val);
    }

    @Test public void BoolByteArrC() {
        boolean[] bb = {};
        try {
            boolArrayToByteArray(bb);
            fail("Didn't throw exception");
        } catch (IndexOutOfBoundsException ignored) {}
    }
}
