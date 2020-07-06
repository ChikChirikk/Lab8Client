package FrameWork;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MyTableHeaderRenderer extends JLabel implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
//        setBorder(BorderFactory.createLineBorder(Color.GRAY));
//        setBackground(F.getColor());
//        setText(value.toString());
        setFont(F.getSmall());
        setToolTipText((String) value);
        return this;
    }
}
