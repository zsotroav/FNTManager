package com.zsotroav.FNTManager.File.Exporter;

import com.zsotroav.FNTManager.File.Importer.BadFormat;
import com.zsotroav.FNTManager.Font.Font;

import java.io.IOException;

public interface FontExporter {
    String getFileNameExtensionFormat();
    String getUserFriendlyName();

    Font exportFont(String filename) throws BadFormat, IOException;
}