# FNT File format

The FNT file format is a binary pixel font file format.


<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Example data](#example-data)
- [Understanding the format](#understanding-the-format)
  - [Markers](#markers)
  - [File header](#file-header)
  - [Symbol data](#symbol-data)
  - [Binary data](#binary-data)

<!-- /code_chunk_output -->

## Example data

To help explain the data structure, the following valid fnt file will be used
as an example:

```
01 00 49 10 00 FF 11 00 01 07 20 0A 6D
21 00 12 22 00 01 30 23 00 01 05 24 00 07 60 90 90 90 90 90 60
21 00 12 22 00 01 31 23 00 01 04 24 00 07 40 C0 40 40 40 40 E0
21 00 12 22 00 01 32 23 00 01 05 24 00 07 60 90 10 20 40 80 F0
```

This font contains three characters: `0x30` (0), `0x31` (1), and `0x32` (2)

## Understanding the format

### Markers

Markers are used throughout the file. They are always followed by `0x00` (except
the `0x01` begin marker) and one or two blocks. When two blocks are used, the
first block is always the length of the second block.

Data in block 1 is exactly 1 byte long unless noted otherwise in `(byte)`

| HEX Marker | Data block 1       | Data block 2   |       Description     |
|------------|--------------------|----------------|-----------------------|
|   `0x01`   | Length of file (2) | -              | Global header begin   |
|   `0x10`   | Font id            | -              | Font identifier       |
|   `0x11`   | L of block         | Height of font | Height data           |
|   `0x20`   | L of data (2)      | Character data | Data length           |
|   `0x21`   | L of char block    | -              | Character data        |
|   `0x22`   | L of block         | Char encoded   | Character identifier  |
|   `0x23`   | L of width block   | Char width     | Character width       |
|   `0x24`   | L of symb block    | Symbol data    | Character data        |

> `0x21` is a sub marker of the `0x20` marker

> `0x22` - `0x24` are sub markers of `0x21` 

### File header

The file always begins with the file header containing metadata about the file:

```
01 00 49 10 00 FF 11 00 01 07 20 0A 6D
-- ----- ----- -- ----- -- -- -- -----

01 <L-FILE> 10 00 <ID> 11 00 <L-HEIGTH> <HEIGHT> 20 <L-DATA>
```

- `<L-FILE>` records the length of the fnt file with the rest of the header
- `<ID>` is an ID available to use by the font's creator
- `<HEIGHT>` is the height of the font in pixels - the height is fix for all
characters in a single file
- `<L-DATA>` records the length of the character data block 

### Symbol data

```
21 00 12 22 00 01 30 23 00 01 05 24 00 07 60 90 90 90 90 90 60
----- -- ----- -- -- ----- -- -- ----- -- --------------------
21 00 <L-SYM> 22 00 <L-CHAR> <CHAR> 23 00 <L-WIDTH> <WIDTH> 24 00 <L-DATA> <DATA>
```
- `<L-SYM>` records the length of the block
- `<L-CHAR>` records the length of the `<CHAR>` data block
- `<CHAR>` contains the encoded character's ASCII/Unicode representation
- `<WIDTH>` is the width of this particular Symbol
  - The Height is defined globally for the file, but width may be different for
every symbol in the file.
- `<DATA>` Encodes the pixel font in a binary format

### Binary data

The pixel font binary data uses the bits to represent each pixel of the font.
Taking 0 from the previous font as an example:

```
60 90 90 90 90 90 60
```

When converted to binary (left) 
and using `#`=`1` and `0`=`.` to visualize it (right):

```
01100000  .##.....
10010000  #..#....
10010000  #..#....
10010000  #..#....
10010000  #..#....
10010000  #..#....
01100000  .##.....
```

If the font width is less than 8, the extra (LSB) bits are discarded. 

If the width is greater than 8, multiple bytes are used in a similar manner: 
Two (or more) consecutive LSB discarded bytes are used for one line.
