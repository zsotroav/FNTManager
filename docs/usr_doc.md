# FNTManager - FNT Font file manager - User documentation

## Table of Contents

<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=3 orderedList=false} -->

<!-- code_chunk_output -->

- [Table of Contents](#table-of-contents)
- [Starting the app](#starting-the-app)
- [CLI Options](#cli-options)
- [GUI Options](#gui-options)
  - [MenuStrip](#menustrip)
  - [Main View](#main-view)
  - [Preview window](#preview-window)

<!-- /code_chunk_output -->

## Starting the app

To use the app, either launch it:
- directly
- with java `java -jar FNTManager.jar`
- with CLI options `java -jar FNTManager.jar <options>`

## CLI Options
```
  -c <in> <out>   --convert
                    Convert font without GUI
  -f <file>       --file <file>
                    Load file into GUI
  -h              --help
                    Show this help
```

## GUI Options
### MenuStrip
Most items are self-explanatory

```
menu strip
├─ Font
│  ├─ New Font
│  └─ Close Font
├─ Import
├─ Export
├─ Edit
│  ├─ Create new Symbol
│  ├─ Change Symbol Character
│  ├─ Change Symbol Width
│  └─ Delete Symbol from font
├─ View
│  ├─ Change brush color
│  ├─ Change background color
│  └─ Change preview scale
└─ About
   └─ FNTManager
```

The Import and Export menus are populated automatically with whatever importers/exporters are available in the program's distribution.

> **Important:** You must save the file by Exporting, as the program only interfaces with the font files when explicitly asked to by the user.

### Main View
When the program starts, the main view is shown. If no font is loaded, an information message is displayed. Load a font through the UI (or via the CLI options before opening) to access the full main view.

The main view consists of two halves:
- On the left is a list of the available symbols
  - The preview font button on the bottom allows you to load a preview window where you can test out the font by writing text with it.
- On the right is a preview of the selected symbol
  - Below the preview is the edit/save button. Click it to change bewteen preview and editing mode. Saving the edit will only affect the loaded font; to save to a file, you must export the loaded font.

### Preview window
In the preview window, you can type free text in the bottom text input, and the rendered text will appear on the top as you type.


There are a few self-explanatory options available in the menu strip on the top:
```
menu strip
├─ View
│  ├─ Change Brush Color
│  ├─ Change Background Color
│  ├─ Change Scale
│  └─ Change Spacing
└─ Export
   └─ PNG
```