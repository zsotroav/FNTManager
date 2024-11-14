package com.zsotroav.FNTManager.File.Importer;

import com.zsotroav.FNTManager.Font.Font;
import com.zsotroav.FNTManager.Font.Symbol;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.*;
import java.nio.file.Files;

public class FontStripImporter implements FontImporter {

    private char[] lookup;

    @Override public boolean canLoadFile(String filename) {
        if (!filename.endsWith(".bmp") && !filename.endsWith(".png")) return false;

        String meta = filename.substring(0, filename.lastIndexOf(".")) + ".txt";
        File metaFile = new File(meta);

        return metaFile.exists();
    }

    @Override public String getFileNameExtensionFormat() {
        return "Font Strip Images;png!bmp";
    }

    @Override public String getUserFriendlyName() {
        return "Font Strip Image";
    }

    private void readMeta(String stripFilename) throws BadFormat, IOException {
        String meta = stripFilename.substring(0, stripFilename.lastIndexOf(".")) + ".txt";
        File metaFile = new File(meta);

        if (!metaFile.exists()) throw new BadFormat("Metadata file does not exist!");

        var in = new BufferedReader(new FileReader(metaFile));
        lookup = in.readLine().toCharArray();
        in.close();
    }

    @Override public Font importFont(String filename) throws BadFormat, IOException {
        if (!filename.endsWith(".bmp") && !filename.endsWith(".png"))
            throw new BadFormat("Invalid file extension!");

        readMeta(filename);

        BufferedImage img = loadImage(new File(filename));
        int fontHeight = img.getHeight()-2;
        Font font = new Font(fontHeight, (byte)0x00);

        int black = img.getRGB(0,0);

        int cnt = 0;
        int w = 1;
        Symbol curr = new Symbol(lookup[cnt], 1, fontHeight);

        for (int i = 0; i < img.getWidth(); i++) {
            if (img.getRGB(i, 0) != black) {
                if (++cnt >= lookup.length && i +1 < img.getWidth())
                    throw new BadFormat("Invalid strip>meta length!");
                font.addSymbol(curr);
                curr = new Symbol(lookup[cnt], 1, fontHeight);
                w = 1;
                continue;
            }

            curr.changeWidth(w++);

            for (int j = 0; j < fontHeight; j++)
                curr.setPixel(w-2, j, img.getRGB(i, j+2) == black);

        }
        font.addSymbol(curr);

        return font;
    }

    private static BufferedImage loadImage(File f) throws IOException {
        byte[] bytes = Files.readAllBytes(f.toPath());
        try (InputStream is = new ByteArrayInputStream(bytes))
        {
            return ImageIO.read(is);
        }
    }
}
