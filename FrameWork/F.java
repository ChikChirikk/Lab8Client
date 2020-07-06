package FrameWork;

import java.awt.*;

public class F {
    private static final Font ordinary = new Font("Century Gothic", Font.PLAIN, 14);
    private static final Font small = new Font("Century Gothic", Font.PLAIN, 10);
    private static final Font medium = new Font("Century Gothic", Font.PLAIN, 12);

    private static final Color color = new Color(23, 143, 108);
    private static final Color tableColor = new Color(color.getRed()+150, color.getGreen()+100, color.getBlue()+100);

    public static Font getOrdinary() {
        return ordinary;
    }

    public static Font getSmall() {
        return small;
    }

    public static Font getMedium() {
        return medium;
    }

    public static Color getTableColor() {
        return tableColor;
    }

    public static Color getColor() {
        return color;
    }
}
