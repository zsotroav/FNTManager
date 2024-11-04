package com.zsotroav.FNTManager.File.Exporter;

import com.zsotroav.FNTManager.Font.Font;

import java.io.IOException;

public interface FontExporter {
    String getFileNameExtensionFormat();
    String getUserFriendlyName();

    void exportFont(Font font, String filename) throws IOException;
}