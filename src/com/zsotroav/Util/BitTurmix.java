package com.zsotroav.Util;

import java.nio.charset.StandardCharsets;

public class BitTurmix {
    /**
     * Convert a byte to a number usable as a UInt16
     * @param bytes Byte array to convert from
     * @param index Offset of array to convert from
     * @return Converted UInt16-usable number
     */
    public static int byteToUInt16(byte[] bytes, int index) {
        if (index < 0 || index + 1 >= bytes.length) return -1;
        return ((bytes[index] & 0xFF) << 8) | (bytes[index + 1] & 0xFF);
    }

    /**
     * Convert a byte to a number usable as a UInt16
     * @param bytes Byte array to convert from
     * @return Converted UInt16-usable number
     */
    public static int byteToUInt16(byte[] bytes) {
        return byteToUInt16(bytes, 0);
    }

    /**
     * Convert a byte to a number usable as a UInt16
     * @param bytes Byte array to convert from
     * @param index Offset of array to convert from
     * @return Converted UInt8-usable number
     */
    public static int byteToUInt8(byte[] bytes, int index) {
        if (index < 0 || index >= bytes.length) return -1;
        return (bytes[index] & 0xFF);
    }

    /**
     * Convert a byte to a number usable as a UInt16
     * @param bytes Byte array to convert from
     * @return Converted UInt8-usable number
     */
    public static int byteToUInt8(byte[] bytes) {
        return byteToUInt8(bytes, 0);
    }

    /**
     * Convert an (unsigned) integer to a byte array
     * @param value (u)int to convert
     * @return byte array corresponding to the (u)int (dynamic size)
     */
    public static byte[] intToByteArray(int value) {
        if (value < 256) return new byte[] {(byte)value};
        else if (value < 65536 ) return new byte[] {
                (byte)(value >>> 8),
                (byte)value};
        else if (value < 0xFFFFFF) return new byte[] {
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
        else return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }

    /**
     * Convert an (unsigned) integer to a 2 long byte array
     * @param value (u)int to convert
     * @return byte array corresponding to the (u)int
     */
    public static byte[] intToByteArray2(int value) {
        return new byte[] { (byte)(value >>> 8), (byte)value};
    }

    /**
     * Convert a byte array to a UTF8 character.
     * This function converts using Unicode code points.
     * @param bytes bytes to convert
     * @param index offset of the byte
     * @param len length of the converted data
     * @return UTF8 decoded character
     */
    public static char byteToUTF8(byte[] bytes, int index, int len) {
        if (index < 0 || index >= bytes.length) throw new IndexOutOfBoundsException();
        return new String(bytes, index, len, StandardCharsets.UTF_8).toCharArray()[0];
    }

    /**
     * Convert a byte array stored in an integer to a UTF8 character.
     * This function converts using Unicode code points.
     * @param data data to convert from
     * @return UTF8 decoded character
     */
    public static char byteIntToUTF8(int data) {
        byte[] b = intToByteArray(data);
        return byteToUTF8(b, 0, b.length);
    }

    /**
     * Convert a UTF8 character to its code point representation.
     * @param c character to convert
     * @return UTF8 Code Points in a byte array
     */
    public static byte[] UTF8ToByteArray(char c) {
        return String.valueOf(c).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Squash a boolean array into a byte array
     * @param data boolean array to convert
     * @return Squashed byte array
     */
    public static byte[] boolArrayToByteArray(boolean[] data) {
        if (data.length == 0) throw new IndexOutOfBoundsException();
        byte[] re = new byte[Math.ceilDiv(data.length, 8)];

        for (int i = 0; i < data.length/8 + 1; i++) {
            for (int j = 0; j < 8; j++) {
                if (i*8+j >= data.length) break;
                re[i] |= (byte) (data[i*8+j] ? 1 << 7-j : 0);
            }
        }

        return re;
    }
}
