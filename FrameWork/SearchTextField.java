package FrameWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SearchTextField extends JIconTextField implements FocusListener, MouseListener {
    private boolean isFocused = false;
    private String textWhenNotFocused;

    public SearchTextField() {
        super();
        this.textWhenNotFocused = "Search...";
        this.addFocusListener(this);
        this.addMouseListener(this);

    }

    public String getTextWhenNotFocused() {
        return this.textWhenNotFocused;
    }

    public void setTextWhenNotFocused(String newText) {
        this.textWhenNotFocused = newText;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!this.hasFocus() && this.getText().equals("")) {
            int width = this.getWidth();
            int height = this.getHeight();
            Font prev = g.getFont();
            Font italic = prev.deriveFont(Font.ITALIC);
            Color prevColor = g.getColor();
            g.setFont(italic);
            g.setColor(UIManager.getColor("textInactiveText"));
            int h = g.getFontMetrics().getHeight();
            int textBottom = (height - h) / 2 + h - 4;
            int x = this.getInsets().left;
            Graphics2D g2d = (Graphics2D) g;
            RenderingHints hints = g2d.getRenderingHints();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.drawString(textWhenNotFocused, x, textBottom);
            g2d.setRenderingHints(hints);
            g.setFont(prev);
            g.setColor(prevColor);
        }

    }

    //FocusListener implementation:
    public void focusGained(FocusEvent e) {
        isFocused = true;
        setBorder(BorderFactory.createLineBorder(F.getColor(), 2));
        this.repaint();
    }

    public void focusLost(FocusEvent e) {
        isFocused = false;
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBorder(BorderFactory.createLineBorder(F.getColor(), 2));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!this.isFocused)
            setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }
}
