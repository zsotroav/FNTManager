package com.zsotroav.FNTManager.Font;

import com.zsotroav.Util.BitTurmix;

import java.nio.charset.StandardCharsets;

public class Symbol {
    // This is a potentially Unicode character
    private char character;

    private boolean[][] pixels;

    public Symbol(char character, boolean[][] pixels) {
        this.character = character;
        this.pixels = pixels;
    }

    public Symbol(char character, int width, int height) {
        this.character = character;
        this.pixels = new boolean[height][width];
    }

    public Symbol(int character, int width, int height) {
        this.character = (char)character;
        this.pixels = new boolean[height][width];
    }

    public char getCharacter() { return character; }
    public boolean[][] getPixels() { return pixels; }
    public boolean getPixel(int x, int y) { return pixels[y][x]; }

    public void setCharacter(char c) { character = c; }
    public void setPixels(boolean[][] pixels) { this.pixels = pixels; }
    public void setPixel(int x, int y, boolean value) { pixels[y][x] = value; }

    public String toString() {
        byte[] b = Character.toString(character).getBytes(StandardCharsets.UTF_8);
        int i = b.length == 1 ? BitTurmix.byteToUInt8(b) : BitTurmix.byteToUInt16(b);
        return "0x" + Integer.toHexString(i).toUpperCase() + ": " + (char)character;
    }
}
