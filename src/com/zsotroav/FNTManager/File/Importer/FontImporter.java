package com.zsotroav.FNTManager.File.Importer;

import com.zsotroav.FNTManager.Font.Font;

import java.io.IOException;

public interface FontImporter {
    String getFileNameExtensionFormat();
    String getUserFriendlyName();

    Font importFont(String filename) throws BadFormat, IOException;
}