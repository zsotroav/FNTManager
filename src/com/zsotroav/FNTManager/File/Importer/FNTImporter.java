package com.zsotroav.FNTManager.File.Importer;

import com.zsotroav.FNTManager.Font.Font;
import com.zsotroav.FNTManager.Font.Symbol;
import com.zsotroav.Util.BitTurmix;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class FNTImporter implements FontImporter {

    private int height;
    private int fontID;
    private int lenData;

    private Font font;

    private DataInputStream dis;

    @Override
    public boolean canHandleExtension(String extension) {
        return extension.equals("fnt");
    }

    public void readHeader() throws IOException, BadFormat {
        /*
         * 01 00 49 10 00 FF 11 00 01 07 20 00 3F
         * -- ----- ----- -- ----- -- -- -- -----
         * 01 <L-FILE> 10 00 <ID> 11 00 <L-HEIGHT> <HEIGHT> 20 <L-DATA>
         */
        byte[] b = new byte[16];
        if (dis.read(b, 0, 9) < 9) throw new IOException("Unexpected EOF :: Header");

        if (b[0] != 0x01) throw new BadFormat("Invalid Header Begin");
        int lenFile = BitTurmix.byteToUInt16(b, 1);
        if (lenFile < 0) throw new BadFormat("Invalid File Length data");

        if (b[3] != 0x10 || b[4] != 0x00) throw new BadFormat("Invalid Font ID Block");
        fontID = BitTurmix.byteToUInt8(b, 5);

        if (b[6] != 0x11 || b[7] != 0x00) throw new BadFormat("Invalid Height Block");
        int Lheight = BitTurmix.byteToUInt8(b, 8);
        if (Lheight < 0 || Lheight > 2) throw new BadFormat("Invalid Height Length data");

        b = new byte[8];
        if (dis.read(b, 0, Lheight+3) < Lheight+3) throw new IOException("Unexpected EOF :: DataLen");
        height = Lheight == 1 ? BitTurmix.byteToUInt8(b) : BitTurmix.byteToUInt16(b);

        if (b[Lheight] != 0x20) throw new BadFormat("Invalid Data Len Block");
        lenData = BitTurmix.byteToUInt16(b, Lheight+1);

        if (lenFile - lenData - Lheight != 9) throw new BadFormat("Invalid Data Length");
    }

    public Symbol readSymbol() throws IOException, BadFormat {
        byte[] b = new byte[3];
        if (dis.read(b, 0, 3) < 3) throw new IOException("Unexpected EOF :: Symb");

        if (b[0] != 0x21 || b[1] != 0x00) throw new BadFormat("Invalid Symb Begin");

        int len = BitTurmix.byteToUInt8(b, 2);
        b = new byte[len];
        if (dis.read(b, 0, len) < len) throw new IOException("Unexpected EOF :: SymbData");

        lenData -= 3 + len;

        if (b[0] != 0x22 || b[1] != 0x00) throw new BadFormat("Invalid Symb Char");

        int charLen = BitTurmix.byteToUInt8(b, 2);

        if (charLen > 2 || charLen < 1) throw new BadFormat("Invalid Char Length");
        int symbol = charLen == 1 ? BitTurmix.byteToUInt8(b, 3) : BitTurmix.byteToUInt16(b, 3);

        int off = 3 + charLen;

        if (b[off++] != 0x23 || b[off++] != 0x00) throw new BadFormat("Invalid Symb Width");
        int widthLen = BitTurmix.byteToUInt8(b, off++);

        if (widthLen > 2 || widthLen < 1) throw new BadFormat("Invalid Char Length");
        int width;
        if (widthLen == 1){
            width = BitTurmix.byteToUInt8(b, off);
            off++;
        } else {
            width = BitTurmix.byteToUInt16(b, off);
            off += 2;
        }

        if (b[off++] != 0x24 || b[off++] != 0x00)
            throw new BadFormat("Invalid Symb Data Width");
        int dataLen = BitTurmix.byteToUInt8(b, off++);

        Symbol s = new Symbol(symbol, width, height);


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int k = dataLen / height;
                int head = off + k * i + (j/8);
                int bitNumber =  8 - (j % 8);
                var bit = (b[head] & (1 << bitNumber - 1)) != 0;
                s.setPixel(j, i, bit);
            }
        }

        return s;
    }

    @Override
    public Font importFont(String filename) throws IOException, BadFormat {
         dis = new DataInputStream(new FileInputStream(filename));

         readHeader();
         font = new Font(height);

         while (lenData > 0) font.addSymbol(readSymbol());

         return font;
    }
}
