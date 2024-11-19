package com.zsotroav.FNTManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.zsotroav.FNTManager.File.Exporter.FontExporter;
import com.zsotroav.FNTManager.File.Importer.FontImporter;
import com.zsotroav.FNTManager.Font.Font;
import com.zsotroav.FNTManager.UI.Frames.MainFrame;

import java.util.ServiceLoader;


public class Main {

    public static void main(String[] args) {
        FlatDarkLaf.setup();

        Font f = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-f": case "--file": case "-i":
                    if (args.length <= i+1) {
                        System.out.println("Usage: -f <filename>");
                        System.exit(-1);
                    }
                    f = loadFont(args[i+1]);
                    break;
                case "-c": case "--convert":
                    if (args.length <= i+2) {
                        System.out.println("Usage: -c <input> <output>");
                        System.exit(-1);
                    }
                    saveFont(args[i+2], loadFont(args[i+1]));
                    System.exit(0);
                case "-h": case "--help":
                    System.out.println("""
                            FNTManager, a fixed-height pixel font manager by zsotroav
                            
                            CLI Options
                              -c <in> <out>   --convert
                                                Convert font without GUI
                              -f <file>       --file <file>
                                                Load file into GUI
                              -h              --help
                                                Show this help
                            \s""");
                    System.exit(0);
            }
        }

        MainFrame frame = f != null ? new MainFrame(f) : new MainFrame();
        frame.setVisible(true);
    }

    /**
     * Automatically load font based on file type
     * @param filename File to load from
     * @return Loaded font or `null` if failed
     */
    private static Font loadFont(String filename) {
        for (FontImporter imp : ServiceLoader.load(FontImporter.class)) {
            if (!imp.canLoadFile(filename)) continue;
            try {
                return imp.importFont(filename);
            } catch (Exception e) {
                System.out.println("Failed to load font!\n" + e.getMessage());
                System.exit(-1);
            }
        }

        System.out.println("No valid importer found for the file specified.");
        return null;
    }

    /**
     * Automatically save font based on file type
     * @param filename File to save to
     * @param font Font to be saved
     */
    private static void saveFont(String filename, Font font) {
        for (FontExporter exp : ServiceLoader.load(FontExporter.class)) {
            if (!exp.canExportToFile(filename)) continue;
            try {
                exp.exportFont(font, filename);
            } catch (Exception e) {
                System.out.println("Failed to save font!\n" + e.getMessage());
                System.exit(-1);
            }
        }

        System.out.println("No valid exporter found for the file specified.");
        System.exit(-1);
    }
}