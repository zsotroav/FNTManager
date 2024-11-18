package com.zsotroav.FNTManager.Font;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FontTest {

    private Font f;

    @Before public void setUp() {
        f = new Font(3, (byte) 0x0A);
    }

    @Test public void FontCtor() {
        assertEquals((byte) 0x0A, f.getId());
        assertEquals(3, f.getHeight());
    }

    @Test public void FontSymbolCtorTestSize() {
        boolean b = f.newSymbol('a', 5);
        assertTrue(b);
        assertTrue(f.containsSymbol('a'));
    }

    @Test public void FontSymbolCtorTestData() {
        boolean[][] data = {
                {true, true, true},
                {true, true, true},
                {true, true, true},
        };
        boolean b = f.newSymbol('a', data);
        assertTrue(b);
        assertTrue(f.containsSymbol('a'));
    }

    @Test public void FontSymbolCtorTestDataOver() {
        boolean[][] data = {
                {true, true, true},
                {true, true, true},
                {true, true, true},
                {true, true, true},
        };
        boolean b = f.newSymbol('a', data);
        assertFalse(b);
        assertFalse(f.containsSymbol('a'));
    }

    @Test public void FontSymbolCtorTestDataExisting() {
        boolean a = f.newSymbol('a', 5);
        assertTrue(a);

        boolean[][] data = {
                {true, true, true},
                {true, true, true},
                {true, true, true},
        };
        boolean b = f.newSymbol('a', data);
        assertFalse(b);

        assertTrue(f.containsSymbol('a'));
        assertEquals(1, f.size());
        assertEquals(5, f.getSymbol('a').getWidth());
    }

    @Test public void FontSymbolCtorTestExisting() {
        boolean a = f.newSymbol('a', 5);
        assertTrue(a);

        boolean b = f.newSymbol('a', 4);
        assertFalse(b);

        assertTrue(f.containsSymbol('a'));
        assertEquals(5, f.getSymbol('a').getWidth());
    }

    @Test public void FontSymbolAddTest() {
        Symbol s = new Symbol('a', 5, 3);
        boolean b = f.addSymbol(s);
        assertTrue(b);
        assertTrue(f.containsSymbol('a'));
        assertEquals(1, f.size());
    }

    @Test public void FontSymbolAddTest2() {
        Symbol s = new Symbol('a', 5, 5);
        boolean b = f.addSymbol(s);
        assertFalse(b);
        assertEquals(0, f.size());
        assertTrue(f.isEmpty());
    }

    @Test public void FontSymbolAddTestOver() {
        boolean a = f.newSymbol('a', 5);
        assertTrue(a);

        Symbol s = new Symbol('a', 5, 3);
        boolean b = f.addSymbol(s);
        assertFalse(b);
        assertEquals(1, f.size());
    }

    @Test public void FontRemoveTest() {
        boolean b = f.newSymbol('a', 5);
        assertTrue(b);

        assertTrue(f.containsSymbol('a'));
        assertEquals(1, f.size());

        f.removeSymbol('a');
        assertFalse(f.containsSymbol('a'));
        assertEquals(0, f.size());
        assertTrue(f.isEmpty());
    }

    @Test public void FontRemoveTest2() {
        Symbol s = new Symbol('a', 5, 3);
        boolean b = f.addSymbol(s);
        assertTrue(b);
        assertTrue(f.containsSymbol('a'));
        assertEquals(1, f.size());

        f.removeSymbol(s);
        assertFalse(f.containsSymbol('a'));
        assertEquals(0, f.size());
        assertTrue(f.isEmpty());
    }
}
