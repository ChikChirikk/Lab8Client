package FrameWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FailedComponents extends Thread {
    private JTextField textField = null;
    private JSpinner spinner = null;
    private int time;
    private Color color;
    public static final int UNLESS_FOCUSED = 0;

    public FailedComponents(JTextField textField, Color color, int time) {
        setDaemon(true);
        this.textField = textField;
        this.color = color;
        this.time = time;
    }

    public FailedComponents(JSpinner spinner, Color color, int time) {
        setDaemon(true);
        this.spinner = spinner;
        this.time = time;
        this.color = color;
    }

    public void run() {
        try {
            if (textField != null) {
                if (time != 0) {
                    textField.setBorder(BorderFactory.createLineBorder(color));
                    this.sleep(time);
                    textField.setBorder(BorderFactory.createLineBorder(Color.white));
                } else {
                    textField.setBorder(BorderFactory.createLineBorder(color));
                    textField.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                        }
                        @Override
                        public void focusLost(FocusEvent e) {

                        }
                    });
                }
            } else if (spinner != null) {
                spinner.setBorder(BorderFactory.createLineBorder(color));
                this.sleep(time);
                spinner.setBorder(BorderFactory.createLineBorder(Color.white));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            this.interrupt();
        }
    }
}

