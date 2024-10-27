package com.zsotroav.FNTManager.File.Importer;

public class BadFormat extends RuntimeException {
    public BadFormat(String message) { super(message); }
    public BadFormat() { super("Invalid File Format"); }
}
