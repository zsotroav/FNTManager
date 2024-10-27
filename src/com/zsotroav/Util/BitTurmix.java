package com.zsotroav.Util;

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
}
