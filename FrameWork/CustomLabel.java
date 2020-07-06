package FrameWork;

import java.awt.*;

public class CustomLabel extends Label {
    private static final Font font = new Font("TimesRoman", Font.BOLD, 16);
    private static final Color color = new Color(100,100,100);
    public CustomLabel(String text){
        super(text);
        setFont(font);
        setForeground(color);
    }
    public CustomLabel(){}

    public CustomLabel(String text, int center) {
        super(text, center);
        setFont(font);
        setForeground(color);
    }
}
