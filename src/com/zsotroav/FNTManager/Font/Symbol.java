package com.zsotroav.FNTManager.Font;

import com.zsotroav.Util.BitTurmix;

import java.nio.charset.StandardCharsets;

public class Symbol implements Comparable<Symbol> {
    /// Character encoded in the Symbol
    private char character;

    /// Pixels of the symbol
    private boolean[][] pixels;

    public int getWidth() { return pixels[0].length; }
    public int getHeight() { return pixels.length; }

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

    public String toString() { return toString(character); }

    public static String toString(char character) {
        byte[] b = Character.toString(character).getBytes(StandardCharsets.UTF_8);
        int i = b.length == 1 ? BitTurmix.byteToUInt8(b) : BitTurmix.byteToUInt16(b);
        return "0x" + Integer.toHexString(i).toUpperCase() + ": " + character;
    }

    public static char fromString(String s) {
        var split = s.split(":");
        return BitTurmix.byteIntToUTF8(Integer.decode(split[0]));
    }

    /**
     * Change the width of the symbol. <br>
     * Increasing the width adds empty pixels to the right. <br>
     * Decreasing the width removes pixels from the right.
     * @param newWidth New width of the symbol
     */
    public void changeWidth(int newWidth) {
        if (newWidth < 1 || newWidth > 100) throw new IndexOutOfBoundsException("Width must be between 1 and 100");
        boolean[][] newPixels = new boolean[pixels.length][newWidth];

        int w = Math.min(pixels[0].length, newWidth);

        for (int i = 0; i < pixels.length; i++) {
            System.arraycopy(pixels[i], 0, newPixels[i], 0, w);
        }
        pixels = newPixels;
    }

    @Override public int compareTo(Symbol o) {
        return Character.compare(character, o.character);
    }
}
