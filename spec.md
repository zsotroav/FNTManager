# FNTManager - FNT Font file manager

FNTManager is a fixed-height pixel font manager that works with `.fnt` files
and font strip images. The app is able to read from and write to both file
formats.

## Application
The application is a simple Java swing GUI application (as per the
requirements) containing two views: A primary work window and a font tester

### Primary work window
The primary work window contains most of the required tools:
- On the left is a clickable and scrollable list of available symbols in the loaded font
  - Above the list is an "Available symbols" label
  - Below the list is a "Preview font" button that opens up the tester window
- On the right is a preview of the currently selected font
  - Above the preview is a label showing the selected symbol's character and 
  width
  - Below the preview is a button to edit/save the symbol

#### Symbol editing
- By default, the edit button is in the edit state and reads "Edit Symbol"
- Clicking edit will make the preview clickable (and by extension the
Symbol editable) and change the text of the button to "Save Symbol"
- Clicking save will lock the preview, return the button's text to edit,
and save the symbol's new data.
- While in edit mode it is not possible to select a new symbol from the list.

### Menu strip

Above the two columns is a standard menu strip with the following structure:
```
    menu strip
D   ├─ New font
f   ├─ Import
    ├─ Export
f   │  ├─ FNT File
f   │  └─ Font Strip Image
    ├─ Edit
D   │  ├─ Create new Symbol
d   │  ├─ Change Symbol Character
d   │  ├─ Change Symbol Width
q   │  └─ Delete Symbol from font
    ├─ View
c   │  ├─ Change brush color
c   │  ├─ Change background color
d   │  └─ Change preview scale
    └─ About
```

Actions for marked items:
| Marker       | Action                                           |
|--------------|--------------------------------------------------|
| `f`          | Show Load/Save file dialog                       |
| `d`          | Show a basic input dialog for the required value |
| `D` New Font | `d` with font height and id                      |
| `D` Create   | `d` with Symbol character and width              |
| `c`          | Show a color picker                              |
| `q`          | Show a confirmation dialog                       |

The About menu strip item shows a basic "About us" dialog with basic 
information about the application.

## Font tester window
The font tester window consists of a large horizontal font preview and a text
input box below it. The preview is a simple image that shows the typed-out
text in the input box.

The tester window has a similar menu strip:
```
    menu strip
    ├─ View
c   │  ├─ Change brush color
c   │  ├─ Change background color
d   │  └─ Change preview scale
f   └─ Export as image
```

## File formats
- FNT File format: *See Appendix 1: FNT file format*
- Image strip images: *See Appendix 2: Image Strips*