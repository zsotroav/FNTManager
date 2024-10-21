package com.zsotroav.FNTManager.Font;

public class Symbol {
    // This is a potentially Unicode character
    private int character;

    private boolean[][] pixels;

    public Symbol(int character, boolean[][] pixels) {
        this.character = character;
        this.pixels = pixels;
    }

    public Symbol(int character, int width, int height) {
        this.character = character;
        this.pixels = new boolean[height][width];
    }

    public int getCharacter() { return character; }
    public boolean[][] getPixels() { return pixels; }
    public boolean getPixel(int x, int y) { return pixels[y][x]; }

    public void setPixels(boolean[][] pixels) { this.pixels = pixels; }
    public void setPixel(int x, int y, boolean value) { pixels[y][x] = value; }

    public String toString() {
        return character + ": ";
    }
}
