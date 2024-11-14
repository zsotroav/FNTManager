package com.zsotroav.FNTManager.File.Exporter;

import com.zsotroav.FNTManager.Font.Font;
import com.zsotroav.FNTManager.Font.Symbol;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FontStripExporter implements FontExporter {

    @Override public boolean canExportToFile(String filename) {
        return filename.endsWith(".bmp") || filename.endsWith(".png");
    }

    @Override public String getFileNameExtensionFormat() {
        return "Font Strip Images;png!bmp";
    }

    @Override public String getUserFriendlyName() {
        return "Font Strip Image";
    }

    @Override public void exportFont(Font font, String filename) throws IOException {
        List<Symbol> symbols = new ArrayList<>(font.getSymbols());
        Collections.sort(symbols);

        int width = 0;
        ArrayList<Character> lookup = new ArrayList<>();

        for (Symbol s : symbols) {
            lookup.add(s.getCharacter());
            width += s.getWidth() + 1;
        }

        BufferedImage img = new BufferedImage(width-1, font.getHeight() + 2, BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = img.getGraphics();
        gfx.setColor(Color.WHITE);
        gfx.fillRect(0, 0, img.getWidth(), img.getHeight());

        width = 0;
        for (Symbol s : symbols) {
            gfx.setColor(Color.black);
            gfx.fillRect(width, 0, s.getWidth(), 1);

            for (int i = 0; i < s.getWidth(); i++) {
                for (int j = 0; j < s.getHeight(); j++) {
                    gfx.setColor(s.getPixel(i,j) ? Color.BLACK : Color.WHITE);
                    gfx.fillRect(i + width, j + 2, 1, 1);
                }
            }
            width += s.getWidth() + 1;
        }

        ImageIO.write(img, "png", new File(filename));
        String meta = filename.substring(0, filename.lastIndexOf(".")) + ".txt";
        PrintWriter out = new PrintWriter(meta);

        StringBuilder sb = new StringBuilder();
        for (char c : lookup) { sb.append(c); }
        out.println(sb);
        out.close();
    }
}
