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
    private int lenFile, lenData;

    private Font font;

    DataInputStream dis;

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
        if (dis.read(b, 0, 9) < 9) throw new IOException("Unexpected EOF");

        if (b[0] != 0x01) throw new BadFormat("Invalid Header Begin");
        lenFile = BitTurmix.byteToUInt16(b, 1);
        if (lenFile < 0) throw new BadFormat("Invalid File Length data");

        if (b[3] != 0x10 || b[4] != 0x00) throw new BadFormat("Invalid Font ID Block");
        fontID = BitTurmix.byteToUInt8(b, 5);

        if (b[6] != 0x11 || b[7] != 0x00) throw new BadFormat("Invalid Height Block");
        int Lheight = BitTurmix.byteToUInt8(b, 8);
        if (Lheight < 0 || Lheight > 2) throw new BadFormat("Invalid Height Length data");

        b = new byte[8];
        if (dis.read(b, 0, Lheight+3) < Lheight+3) throw new IOException("Unexpected EOF");
        height = Lheight == 1 ? BitTurmix.byteToUInt8(b) : BitTurmix.byteToUInt16(b);

        if (b[Lheight] != 0x20) throw new BadFormat("Invalid Data Len Block");
        lenData = BitTurmix.byteToUInt16(b, Lheight+1);

        if (lenFile - lenData - Lheight != 9) throw new BadFormat("Invalid Data Length");
    }

    public Symbol readSymbol() {

    }

    @Override
    public Font importFont(String filename) throws IOException, BadFormat {
         dis = new DataInputStream(new FileInputStream(filename));

         readHeader();
         font = new Font(height);



         return font;
    }
}
