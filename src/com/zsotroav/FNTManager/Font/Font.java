package com.zsotroav.FNTManager.Font;

import java.util.Collection;
import java.util.HashMap;

public class Font {
    private int height;
    private HashMap<Character, Symbol> Symbols;

    public int getHeight() { return height; }

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

    public Collection<Symbol> getSymbols() { return Symbols.values(); }

    public Font(int height) {
        this.height = height;
        Symbols = new HashMap<>();
    }
}
