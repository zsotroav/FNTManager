package com.zsotroav.FNTManager.Font;

import java.util.Collection;
import java.util.HashMap;

public class Font {
    /// Fixed height of the font and all symbols in it
    private final int height;
    /// Available symbols
    private final HashMap<Character, Symbol> symbols;
    /// ID of the font
    private final byte id;

    public int getHeight() { return height; }
    public byte getId() { return id; }

    public boolean newSymbol(char character, boolean[][] data) {
        if (data.length != height ||
            symbols.containsKey(character)) return false;

        symbols.put(character, new Symbol(character, data));
        return true;
    }

    public boolean newSymbol(char character, int width) {
        if (symbols.containsKey(character)) return false;
        symbols.put(character, new Symbol(character, width, height));
        return true;
    }

    public boolean addSymbol(Symbol s) {
        if (symbols.containsKey(s.getCharacter())) return false;
        symbols.put(s.getCharacter(), s);
        return true;
    }

    public Symbol getSymbol(char character) { return symbols.get(character); }
    public boolean containsSymbol(char character) { return symbols.containsKey(character); }

    public void removeSymbol(char character) { symbols.remove(character); }
    public void removeSymbol(Symbol s) { symbols.remove(s.getCharacter()); }

    public Collection<Symbol> getSymbols() { return symbols.values(); }

    public int size() { return symbols.size(); }
    public boolean isEmpty() { return symbols.isEmpty(); }

    public Font(int height) { this(height, (byte)0); }

    public Font(int height, byte id) {
        this.height = height;
        this.id = id;
        symbols = new HashMap<>();
    }
}
