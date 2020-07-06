package FrameWork;

import Controller.HumanCollection;
import Human.HumanBeing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;

public class Canvas extends JPanel {
    private BufferedImage myPicture;
    private JPanel panelForGraphic;
    private JFrame frame;
    private JLabel graph;

    public Canvas(JFrame frame) {
        try {
            this.frame = frame;

            panelForGraphic = new JPanel();
            panelForGraphic.setSize(1078, 464);
            setLayout(null);
            panelForGraphic.setLayout(null);
            panelForGraphic.setVisible(true);
            panelForGraphic.setOpaque(false);
            setLocation(0, 0);
            myPicture = ImageIO.read(new File("resources\\gr.png"));
            graph = new JLabel(new ImageIcon(myPicture));
            graph.setBounds(0, 0, 1078, 464);
            panelForGraphic.add(graph);
            add(panelForGraphic);
            //fillCanvas();
            setOpaque(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HashSet<HumanAnimation> humanAnimations = new HashSet<>();


    public static class Animation {
        public static HashSet<HumanAnimation> humans = new HashSet<>();

        public static void startAnime() {
            new Thread(() -> {
                while (true) {
                    if (humans.size() > 0) {
                        try {
                            humans.forEach(human -> human.setLocation(human.getX(), human.getY() + 3));
                            Thread.sleep(500);
                            humans.forEach(human -> human.setLocation(human.getX(), human.getY() - 3));
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    public void fillCanvas() {
        clearCanvas();
        for (HumanBeing humanBeing : HumanCollection.getArray()) {
            HumanAnimation humanAnimation = new HumanAnimation(humanBeing, 30, 30, MainFrame.color);
            Canvas.Animation.humans.add(humanAnimation);
            panelForGraphic.add(humanAnimation);
        }
        Animation.startAnime();
        frame.repaint();
    }


    public void fillCanvas(HumanBeing humanBeing) {
        HumanAnimation humanAnimation = new HumanAnimation(humanBeing, 30, 30, MainFrame.color);
        Canvas.Animation.humans.add(humanAnimation);
        panelForGraphic.add(humanAnimation);
        Animation.startAnime();
        frame.repaint();
    }
    public void clearCanvas(){
        Animation.humans.clear();
        panelForGraphic.removeAll();
        panelForGraphic.add(graph);
    }
}
