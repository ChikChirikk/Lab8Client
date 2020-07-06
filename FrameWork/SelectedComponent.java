package FrameWork;

import javax.swing.*;
import java.awt.*;

public class SelectedComponent extends Thread {
    JTextField component;
    Color color;

    public SelectedComponent(JTextField component, Color color) {
        this.component = component;
        this.color = color;
        this.start();
    }

    public void run() {
        try {
            component.setBorder(BorderFactory.createLineBorder(color));
            this.sleep(1000);
            component.setBorder(BorderFactory.createLineBorder(Color.white));
            this.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
