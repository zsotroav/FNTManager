package com.zsotroav.FNTManager.File.Importer;

import com.zsotroav.FNTManager.Font.Font;

import java.io.IOException;

public interface FontImporter {

    /**
     * Check if a given file can be loaded by the importer
     * Note: This method does not validate the file's format and only does basic checks
     * @param filename path to the file
     * @return true/false can/can't load file with this implementation
     */
    boolean canLoadFile(String filename);

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

    /**
     * Import the specified file into a font
     * @param filename path to file to load
     * @return Loaded font
     * @throws BadFormat Unexpected error with the loaded file's data
     * @throws IOException Unexpected error while loading file
     */
    Font importFont(String filename) throws BadFormat, IOException;
}