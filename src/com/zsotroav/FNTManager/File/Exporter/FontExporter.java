package com.zsotroav.FNTManager.File.Exporter;

import com.zsotroav.FNTManager.Font.Font;

import java.io.IOException;

public interface FontExporter {

    /**
     * Check if a given file can be exported by the given exporter
     * Note: This method does not validate the file's format and only does basic checks
     * @param filename path to the file
     * @return true/false can/can't load file with this implementation
     */
    boolean canExportToFile(String filename);

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
     * Export the specified file into a font
     * @param font Font to be saved
     * @param filename path to file
     * @throws IOException Unexpected error while saving file
     */
    void exportFont(Font font, String filename) throws IOException;
}