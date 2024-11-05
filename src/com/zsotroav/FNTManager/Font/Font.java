package com.zsotroav.FNTManager.Font;

import java.util.Collection;
import java.util.HashMap;

public class Font {
    private int height;
    private HashMap<Character, Symbol> Symbols;
    private byte ID;

    public int getHeight() { return height; }
    public byte getID() { return ID; }

    public boolean newSymbol(char character, boolean[][] data) {
        if (data.length != height ||
            Symbols.containsKey(character)) return false;

        Symbols.put(character, new Symbol(character, data));
        return true;
    }

    public boolean newSymbol(char character, int width) {
        if (Symbols.containsKey(character)) return false;
        Symbols.put(character, new Symbol(character, width, height));
        return true;
    }

    public boolean addSymbol(Symbol s) {
        if (Symbols.containsKey(s.getCharacter())) return false;
        Symbols.put(s.getCharacter(), s);
        return true;
    }

    public Symbol getCharacter(char character) { return Symbols.get(character); }
    public boolean containsSymbol(char character) { return Symbols.containsKey(character); }

    public void removeCharacter(char character) { Symbols.remove(character); }
    public void removeCharacter(Symbol s) { Symbols.remove(s.getCharacter()); }

    public Collection<Symbol> getSymbols() { return Symbols.values(); }

    public int size() { return Symbols.size(); }
    public boolean isEmpty() { return Symbols.isEmpty(); }

    public Font(int height) { this(height, (byte)0); }

    public Font(int height, byte ID) {
        this.height = height;
        this.ID = ID;
        Symbols = new HashMap<>();
    }
}
