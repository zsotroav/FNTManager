package com.zsotroav.FNTManager.UI.Frames;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JFrame {

    public AboutFrame() {
        super("About FNTManager");
        this.setLayout(new GridLayout());

        JTextPane textArea = new JTextPane();
        textArea.setEditable(false);

        textArea.setContentType("text/html");

        textArea.setText(
                """
                <div style="text-align: center; margin: 10px">
                  <h1 style="margin-bottom: 0">FNT Manager</h1>
                  <h2>by zsotroav</h2>
                  <p>
                      FNTManager is a fixed-height pixel font manager primarily
                      for <code>.fnt</code> files and font strip images.
                  </p>
                  <p>
                      More info:
                      <a href="https://github.com/zsotroav/FNTManager"
                          >GitHub.com/zsotroav/FNTManager</a>
                  </p>
                  <p style="font-size: x-small;">
                      FNTManager is free software: you can redistribute it and/or
                      modify it under the terms of the GNU General Public License
                      as published by the Free Software Foundation, either version
                      3 of the License, or (at your option) any later version.
                  <br />
                  <br />
                      FNTManager is distributed in the hope that it will be useful,
                      but WITHOUT ANY WARRANTY; without even the implied warranty
                      of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
                      the GNU General Public License for more details.
                  <br />
                  <br />
                      You should have received a copy of the GNU General Public
                      License along with this program. If not, see
                      <a href="https://www.gnu.org/licenses"> gnu.org/licenses</a>.
                  </p>
                </div>\
                """);

        this.add(textArea);
        this.setSize(400, 350);
        this.setVisible(true);
    }

}
