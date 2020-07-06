package FrameWork;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class InfoResult {
    public InfoResult(String result) {
        String textField =("<html>" + result.replace("\n", "<br/>") + "</html>");

        JOptionPane.showMessageDialog(null, textField, "Info", JOptionPane.PLAIN_MESSAGE);
    }
}

