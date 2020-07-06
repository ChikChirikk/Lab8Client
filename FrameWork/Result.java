package FrameWork;

import javax.swing.*;
import java.awt.*;

public class Result extends JDialog {
    private final JScrollPane scrollPane = new JScrollPane();

    public Result(String result) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
        setBounds(screenSize.width / 2 - 250, screenSize.height / 2 - 200, 500, 400);
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        String message = "<html>" + result.replace("\n", "<br/>") + "</html>";
        scrollPane.setViewportView(new JLabel(message));
    }
}
