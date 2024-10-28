package com.zsotroav.FNTManager;

import com.zsotroav.FNTManager.File.Importer.FNTImporter;
import com.zsotroav.FNTManager.Font.Font;
import com.zsotroav.FNTManager.UI.Components.MenuBar;
import com.zsotroav.FNTManager.UI.Views.*;

import javax.swing.*;
import java.awt.*;


public class Main {
    private static MainView mainView;
    private static MenuBar menuBar;

    public static void main(String[] args) {
        JFrame frame = new JFrame("FNTManager");
        menuBar = new MenuBar();
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            mainView = new MainView(new FNTImporter().importFont("example.fnt"));
            frame.add(mainView);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        frame.pack();
        frame.setVisible(true);
    }
}
