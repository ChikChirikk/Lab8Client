package FrameWork;

import Human.HumanBeing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class HumanAnimation extends JPanel {
    int x, y, width, height;
    Color color;
    private HumanBeing humanBeing;

    public HumanAnimation(HumanBeing humanBeing, int width, int height, Color color) {
        this.humanBeing = humanBeing;
        this.x = getFormattedX(humanBeing.getCoordinates().getX());
        this.y = getFormattedY(humanBeing.getCoordinates().getY());
        this.height = height;
        this.width = width;
        this.color = color;
        setToolTipText(humanBeing.getName());
        setOpaque(false);
        setSize(width, height * 2);
        setLocation(x, y);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new HumanFrame(humanBeing);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    public int getFormattedX(long xl) {
        int x = width / 2;
        x += 1078 / 2 - 10;
        x += Math.toIntExact(xl) / 2.5;
        return x;
    }

    public int getFormattedY(long yl) {
        int y = Math.toIntExact(yl);
        y = 464 / 2 - 25;
        y -= 0.39 * Math.toIntExact(yl);
        return y;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Shape rect = new Rectangle(0, (int) (height * 0.7), width, (int) (height * 0.8));
        g2d.setColor(color);
        g2d.fill(rect);

        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, width, height);
        g2d.setColor(new Color(255, 199, 161));
        g2d.fill(circle);
        Ellipse2D.Double eye1 = new Ellipse2D.Double(width * 0.15, height * 0.4, width * 0.2, height * 0.15);
        Ellipse2D.Double eye2 = new Ellipse2D.Double(width * 0.66, height * 0.4, width * 0.2, height * 0.15);
        g2d.setColor(new Color(16, 133, 255));
        g2d.fill(eye1);
        g2d.fill(eye2);

        Polygon mustache = new Polygon();
        mustache.addPoint((int) (width * 0.3), (int) (height * 0.7));
        mustache.addPoint((int) (width * 0.4), (int) (height * 0.6));
        mustache.addPoint((int) (width * 0.6), (int) (height * 0.6));
        mustache.addPoint((int) (width * 0.7), (int) (height * 0.7));
        g2d.setColor(Color.BLACK);
        g2d.fill(mustache);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }


}


