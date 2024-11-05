package com.zsotroav.FNTManager.File.Importer;

import com.zsotroav.FNTManager.Font.Font;

import java.io.IOException;

public interface FontImporter {

    /**
     * File filter format
     * Format for string: &lt;description&gt;;&lt;ext1&gt;![extN...]
     * @return Input for the FileFilter
     */
    String getFileNameExtensionFormat();

    /**
     * User-friendly name of the file format
     * @return User-friendly name to appear in the menu bar
     */
    String getUserFriendlyName();

    Font importFont(String filename) throws BadFormat, IOException;
}