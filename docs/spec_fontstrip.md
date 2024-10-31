# Font strip file formats

A font strip is made up of a black and white `.png` or `.bmp` image file and a `.txt` text file

The image file is made up of three horizontal slices and only contains
black (`#000`) and white (`#FFF`) pixels:

-   Character width markers (Red in graphic)
    -   H=1px
    -   White marks where the information in the character slice is valid
    -   Black pixel(s) mark the empty columns between the characters
-   Buffer (G)
    -   H=1px, solid black stripe
    -   Reserved for future use and potential metadata storage.
-   Character graphics (B)
    -   H=Hfnt
    -   Contains the character information (B=inactive, W=active pixel)

The related text file contains the characters available in the font strip in
plain text (UTF-8) without any extra data.

## Example font strip:

This example shows a x5 upscaled font strip image with an extra column showing
RGB strips to highlight the three slices:

![spec_fontstrip_x5](spec_fontstrip_x5.png)

The related text file would contain the following:

```
1234567890
```