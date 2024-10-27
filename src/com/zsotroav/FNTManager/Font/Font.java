package com.zsotroav.FNTManager.Font;

import java.util.HashMap;

public class Font {
    private int height;

    private HashMap<Integer, Symbol> characters;

    public boolean newCharacter(int character, boolean[][] data) {
        if (data.length != height ||
            characters.containsKey(character)) return false;

        characters.put(character, new Symbol(character, data));
        return true;
    }

    public boolean newCharacter(int character, int width) {
        if (characters.containsKey(character)) return false;
        characters.put(character, new Symbol(character, width, height));
        return true;
    }

    public Symbol getCharacter(int character) { return characters.get(character); }

    public Font(int height) {
        this.height = height;
        characters = new HashMap<>();
    }
}
