package com.zsotroav.Util;

import java.nio.charset.StandardCharsets;

public class BitTurmix {
    public static int byteToUInt16(byte[] bytes, int index) {
        if (index < 0 || index + 1 >= bytes.length) return -1;
        return ((bytes[index] & 0xFF) << 8) | (bytes[index + 1] & 0xFF);
    }

    public static int byteToUInt16(byte[] bytes) {
        return byteToUInt16(bytes, 0);
    }

    public static int byteToUInt8(byte[] bytes, int index) {
        if (index < 0 || index >= bytes.length) return -1;
        return ((bytes[index] & 0xFF));
    }

    public static int byteToUInt8(byte[] bytes) {
        return byteToUInt8(bytes, 0);
    }

    public static char byteToUTF8(byte[] bytes, int index, int len) {
        if (index < 0 || index >= bytes.length) throw new IndexOutOfBoundsException();
        return new String(bytes, index, len, StandardCharsets.UTF_8).toCharArray()[0];
    }

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

    public static char byteIntToUTF8(int data) {
        byte[] b = intToByteArray(data);
        return byteToUTF8(b, 0, b.length);
    }
}
