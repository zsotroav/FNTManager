package com.zsotroav.FNTManager.File.Exporter;

import com.zsotroav.FNTManager.Font.Font;
import com.zsotroav.FNTManager.Font.Symbol;
import com.zsotroav.Util.BitTurmix;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FNTExporter implements FontExporter {

    private RandomAccessFile out;
    private Font font;
    private int lenFile = 0;

    @Override public boolean canExportToFile(String filename) { return filename.endsWith(".fnt"); }

    @Override public String getFileNameExtensionFormat() { return "FNT Font File;fnt"; }

    @Override public String getUserFriendlyName() { return "FNT Font"; }

    @Override public void exportFont(Font font, String filename) throws IOException {
        this.font = font;

        if (!filename.endsWith(".fnt")) filename = filename + ".fnt";

        var fi = new File(filename);
        if (fi.exists() && !fi.delete()) throw new IOException("Couldn't delete file: " + filename);

        out = new RandomAccessFile(filename, "rw");

        writeHeader();

        List<Symbol> symbols = new ArrayList<>(font.getSymbols());
        Collections.sort(symbols);

        for (Symbol s : symbols)
            writeSymbol(s);

        finishHeader();
        out.close();
    }

    private int headerLen;

    private void writeHeader() throws IOException {
        byte[] header = new byte[] {
                0x01,       // File begin
                0x00, 0x00, // lenFile (will be filled later)
                0x10, 0x00, font.getID(),
                0x11, 0x00, // Height data
        };
        out.write(header);
        out.write(Math.ceilDiv(font.getHeight(),255));
        out.write(BitTurmix.intToByteArray(font.getHeight()));
        out.write(new byte[] {0x20, 0x00, 0x00}); // lenData (later)

        headerLen = 9 + Math.ceilDiv(font.getHeight(),255);
    }

    private void finishHeader() throws IOException {
        var curr = out.getFilePointer();
        out.seek(1);
        out.write(BitTurmix.intToByteArray2(lenFile + headerLen));

        out.seek(3 + headerLen - 2); // offset because of non-counted header, go back to write area
        out.write(BitTurmix.intToByteArray2(lenFile));

        out.seek(curr);
    }

    private void writeSymbolHeader(Symbol symbol) throws IOException {
        // 21 00 <L-SYM> 22 00 <L-CHAR> <CHAR> 23 00 <L-WIDTH> <WIDTH> 24 00 <L-DATA> <DATA>

        byte[] character = BitTurmix.UTF8ToByteArray(symbol.getCharacter());
        byte[] width = BitTurmix.intToByteArray(symbol.getWidth());

        int dataLen = Math.ceilDiv(symbol.getWidth(),8) * symbol.getHeight();

        int length =
                6 + // Fixed bytes (without 0x21 0x00)
                3 + // Length data bytes
                character.length +
                width.length +
                dataLen;

        lenFile += length + 3;

        byte[] head = new byte[] {
                0x21, 0x00, // Character data
                (byte) length,
                0x22, 0x00, // Char identifier
                (byte) character.length,
        };
        out.write(head);
        out.write(character);

        // Character width
        out.write(new byte[] { 0x23, 0x00 });
        out.write(width.length);
        out.write(width);

        // Data
        out.write(new byte[] { 0x24, 0x00 });
        out.write(dataLen);
    }

    private void writeSymbol(Symbol symbol) throws IOException {
        writeSymbolHeader(symbol);
        for (int i = 0; i < symbol.getHeight(); i++) {
            out.write(BitTurmix.boolArrayToByteArray(symbol.getPixels()[i]));
        }
    }

}
