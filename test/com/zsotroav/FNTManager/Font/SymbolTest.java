package com.zsotroav.FNTManager.Font;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SymbolTest {
    private Symbol s;

    @Before public void setUp() {
        boolean[][] data = {
                {true, true, true},
                {true, true, true},
                {true, true, true},
        };
        s = new Symbol('a', data);
    }

    @Test public void testCtor() {
        assertEquals(3, s.getHeight());
        assertEquals(3, s.getWidth());
        assertEquals('a', s.getCharacter());

        boolean[][] data = {
                {true, true, true},
                {true, true, true},
                {true, true, true},
        };

        assertArrayEquals(data, s.getPixels());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!s.getPixels()[i][j]) {fail("Coordinate (" + i + ", " + j + ") not valid");}
            }
        }
    }

    @Test public void testChangeWidthPlus() {
        s.changeWidth(4);
        assertEquals(4, s.getWidth());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!s.getPixels()[i][j]) {fail("Coordinate (" + i + ", " + j + ") not valid");}
            }
        }

        for (int i = 0; i < 3; i++) {
            if (s.getPixel(3, i)) fail("Coordinate (3, " + i + ") not valid");
        }
    }

    @Test public void testChangeWidthMinus() {
        s.changeWidth(2);
        assertEquals(2, s.getWidth());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (!s.getPixels()[i][j]) {fail("Coordinate (" + i + ", " + j + ") not valid");}
            }
        }
    }

    @Test public void testSymbolUTF8() {
        Symbol ss = new Symbol('á', 3, 3);
        assertEquals("0xC3A1: á", ss.toString());
    }

    @Test public void testSymbolUTF8FromString() {
        char c = Symbol.fromString("0xC3A1: á");
        assertEquals('á', c);
    }
}
