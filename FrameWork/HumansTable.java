package FrameWork;

import Controller.HumanCollection;
import Human.HumanBeing;
import Utilites.LocaleController;
import Utilites.Localizeable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.ResourceBundle;

public class HumansTable extends JTable implements Localizeable {
    HumanCollection collection = new HumanCollection();
    private Canvas canvas;
    JTable table = this;

    public HumansTable(DefaultTableModel defaultTableModel, Canvas canvas) {
        super(defaultTableModel);
        LocaleController.regist(this);
        setRowHeight(getRowHeight() + 10);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        getTableHeader().setFont(F.getMedium());
        setFont(F.getOrdinary());
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(defaultTableModel);
        table.setRowSorter(sorter);
        setPreferredScrollableViewportSize(new Dimension(450, 63));
        setFillsViewportHeight(true);
        getTableHeader().setReorderingAllowed(false);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setCellsAlignment(SwingConstants.CENTER);
        this.canvas = canvas;
        this.fillCollection();
        createLocale();
        //clearCollection();
        getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                System.out.println(2);
                if (table.getSelectedRow() > -1) {
                    new HumanFrame(collection.getHuman(Math.toIntExact((Long) table.getValueAt(table.getSelectedRow(), 0))));
                }
            }
        });
    }

    public void fillCollection() {
        this.clearCollection();
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        int i = 0;
        canvas.fillCanvas();
        for (HumanBeing humanBeing : collection.getArray()) {
            Object[] data = humanBeing.getTableHuman();
            model.addRow(data);
        }
    }

    public void fillCollection(int row, String value) {
        this.clearCollection();
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        int i = 0;
        canvas.clearCanvas();
        for (HumanBeing humanBeing : collection.getArray()) {
            Object param = humanBeing.getTableHuman()[row];
            if (String.valueOf(param).indexOf(value) != -1) {
                Object[] data = humanBeing.getTableHuman();
                model.addRow(data);
                canvas.fillCanvas(humanBeing);
            }
        }
    }

    private String[] emptyRow = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private String[][] emptyRow1 = new String[][]{};

    public void clearCollection() {
        DefaultTableModel dm = (DefaultTableModel) getModel();
        int rowCount = dm.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }
    }


    public void setCellsAlignment(int alignment) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(alignment);
        TableModel tableModel = this.getModel();
        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            this.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
    }

    @Override
    public void createLocale() {
        ResourceBundle rb = LocaleController.getResourceBundle();
        getColumnModel().getColumn(1).setHeaderValue(rb.getString("Name"));
        getColumnModel().getColumn(2).setHeaderValue(rb.getString("Owner"));
        getColumnModel().getColumn(3).setHeaderValue(rb.getString("Coordinates"));
        getColumnModel().getColumn(4).setHeaderValue(rb.getString("Creation_date"));
        getColumnModel().getColumn(5).setHeaderValue(rb.getString("Is_the_character_fictional?"));
        getColumnModel().getColumn(6).setHeaderValue(rb.getString("Has_a_toothpick?"));
        getColumnModel().getColumn(7).setHeaderValue(rb.getString("Impact_speed"));
        getColumnModel().getColumn(8).setHeaderValue(rb.getString("Soundtrack_name"));
        getColumnModel().getColumn(9).setHeaderValue(rb.getString("Minutes_of_waiting"));
        getColumnModel().getColumn(10).setHeaderValue(rb.getString("Type_of_weapon"));
        getColumnModel().getColumn(11).setHeaderValue(rb.getString("Name_of_the_car"));
        getColumnModel().getColumn(12).setHeaderValue(rb.getString("Is_car_cool?"));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintEmptyRows(g);
    }


    public void paintEmptyRows(Graphics g) {
        Graphics newGraphics = g.create();
        newGraphics.setColor(UIManager.getColor("Table.gridColor"));

        Rectangle rectOfLastRow = getCellRect(getRowCount() - 1, 0, true);
        int firstNonExistentRowY = rectOfLastRow.y; //the top Y-coordinate of the first empty tablerow

        if (getVisibleRect().height > firstNonExistentRowY) //only paint the grid if empty space is visible
        {
            //fill the rows alternating and paint the row-lines:
            int rowYToDraw = (firstNonExistentRowY - 1) + getRowHeight(); //minus 1 otherwise the first empty row is one pixel to high
            int actualRow = getRowCount() - 1; //to continue the stripes from the area with table-data

            while (rowYToDraw < getHeight()) {
                if (actualRow % 2 == 0) {
                    newGraphics.setColor(F.getTableColor()); //change this to another color (Color.YELLOW, anyone?) to show that only the free space is painted
                    newGraphics.fillRect(0, rowYToDraw, getWidth(), getRowHeight());
                    newGraphics.setColor(UIManager.getColor("Table.gridColor"));
                }

                newGraphics.drawLine(0, rowYToDraw, getWidth(), rowYToDraw);

                rowYToDraw += getRowHeight();
                actualRow++;
            }


            //paint the column-lines:
            int x = 0;
            for (int i = 0; i < getColumnCount(); i++) {
                TableColumn column = getColumnModel().getColumn(i);
                x += column.getWidth(); //add the column width to the x-coordinate

                newGraphics.drawLine(x - 1, firstNonExistentRowY, x - 1, getHeight());
            }

            newGraphics.dispose();

        } //if empty space is visible

    } //paintEmptyRows


    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);

        if (!isRowSelected(row)) {
            c.setBackground(row % 2 == 0 ? getBackground() : F.getTableColor());
        }

        return c;
    }

}
