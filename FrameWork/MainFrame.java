package FrameWork;

import Commands.Execute_script;
import Connection.App;
import Connection.ClientReceiver;
import Connection.ClientSender;
import Connection.CommandsToSend;
import Controller.Commands;
import Controller.HumanCollection;
import Human.HumanBeing;
import Utilites.LocaleController;
import Utilites.Localizeable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainFrame extends JFrame implements Localizeable {
    private static ClientSender sender;
    private static ClientReceiver receiver;
    public static App app;
    public static final Color color = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    private JFrame frame = this;
    private static CustomButton addButton;
    private static CustomButton clearButton;
    private static CustomButton updateButton;
    private static CustomButton selectButton;

    private static JButton back;

    private static JComboBox<String> filterBox;
    private static SearchTextField searchField;

    private static VerticalButton scriptButton;
    private static VerticalButton infoButton;
    private static JTabbedPane tabbedPane;

    private static MenuBar menuBar;
    private static HumansTable humanTable;
    private static ResourceBundle rb = LocaleController.getResourceBundle();
    private static String[] filterVar = new String[]{
            "id",
            rb.getString("Name"),
            rb.getString("Owner"),
            rb.getString("Coordinates"),
            rb.getString("Creation_date"),
            rb.getString("Is_the_character_fictional?"),
            rb.getString("Has_a_toothpick?"),
            rb.getString("Impact_speed"),
            rb.getString("Soundtrack_name"),
            rb.getString("Minutes_of_waiting"),
            rb.getString("Type_of_weapon"),
            rb.getString("Name_of_the_car"),
            rb.getString("Is_car_cool?"),
    };
    private static String[] columnNames = new String[]{"id",
            "Имя", "Владелец", "Координаты", "Дата создания",
            "Вымышленный персонаж", "Есть зубочистка", "Скорость удара", "Название трека", "Время ожидания", "Тип оружия", "Имя тачки", "Пантовая ли тачка?"
    };
    private static Canvas canvas;
    private Scanner in = new Scanner(System.in);
    private static Commands command = new Commands();

    public MainFrame(ClientSender sender, ClientReceiver receiver) {
        LocaleController.regist(this);
        this.sender = sender;
        this.receiver = receiver;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width / 2 - 550, screenSize.height / 2 - 300, 1100, 600);
        setBackground(Color.WHITE);
        //setMinimumSize(new Dimension(1100, 600));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Управление коллекцией");
        setAlwaysOnTop(false);
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
        initComponents();
        //createLocal(Localization.getRus());
        createLocale();
        setVisible(true);
    }

    private static CommandsToSend commandsToSend = new CommandsToSend();

    public static void workCommand(String commandName) throws IOException {
        try {
            command.setCommand(commandName);
            if (!(commandsToSend.getCommandsToSend().equals("")))
                app.begin();
            if (command.getCommand().getName().equals("execute_script"))
                command.getCommand().execute(command.getArg());
            else {
                sender.sendCommand(command);
//                if (sender.isCommandWithObject()) {
//                    if (receiver.receive().equals("newHuman")) {
//                        HashMap packedHuman = new HashMap();
//                        packedHuman.put("human", NewHumanFrame.getHumanBeing());
//                        packedHuman.put("commandName", command.getCommand().getName());
//                        packedHuman.put("portLoginAndPassword", app.loginAndPassword);
//                        sender.send(packedHuman);
//                    }
//                }
                HumanCollection.setArray(receiver.receiveCollection());
                String received = receiver.receive();
                System.out.println(received);
                if (!(commandsToSend.getCommandsToSend().equals(""))) {
                    System.out.println("\nКоманды неотправленные на сервер:");
                    commandsToSend.addCommandsToSend(commandName);
                    commandsToSend.removeCommandsToSend();
                }
            }
            humanTable.fillCollection();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();

        } catch (SocketTimeoutException e1) {
            System.out.println("Сервер недоступен =(");
            commandsToSend.addCommandsToSend(commandName + "\n");
            throw e1;
        }

    }

    public static void workCommand(String commandName, HumanBeing humanBeing) throws IOException {
        try {
            command.setCommand(commandName);
            if (!(commandsToSend.getCommandsToSend().equals("")))
                app.begin();
            if (command.getCommand().getName().equals("execute_script"))
                command.getCommand().execute(command.getArg());
            else {
                sender.sendCommand(command);
                if (sender.isCommandWithObject()) {
                    if (receiver.receive().equals("newHuman")) {
                        HashMap packedHuman = new HashMap();
                        packedHuman.put("human", humanBeing);
                        packedHuman.put("commandName", command.getCommand().getName());
                        packedHuman.put("portLoginAndPassword", app.loginAndPassword);
                        System.out.println(packedHuman.toString());
                        sender.send(packedHuman);
                    }
                }
                HumanCollection.setArray(receiver.receiveCollection());
                String received = receiver.receive();
                System.out.println(received);
                if (!(commandsToSend.getCommandsToSend().equals(""))) {
                    System.out.println("\nКоманды неотправленные на сервер:");
                    commandsToSend.addCommandsToSend(commandName);
                    commandsToSend.removeCommandsToSend();
                }
            }
            humanTable.fillCollection();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();

        } catch (SocketTimeoutException e1) {
            System.out.println("Сервер недоступен =(");
            commandsToSend.addCommandsToSend(commandName + "\n");
            throw e1;
        }

    }

    private static HumanCollection collection = new HumanCollection();


    private void getIoException() {
        JOptionPane.showMessageDialog(frame, "Сервер недоступен=( Попробуйте через время отправить команду", "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    private void initComponents() {
        back = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Runnable startWindow = () -> {
                    new AuthorizeFrame();
                    try {
                        App.startProgramm();
                        frame.dispose();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                };
                new Thread(startWindow).start();
                Thread.currentThread().interrupt();
            }
        });
        back.setText(LocaleController.getResourceBundle().getString("back"));
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        back.setFont(F.getOrdinary());
        menuBar.add(back);
        addButton = new CustomButton();
        clearButton = new CustomButton();
        updateButton = new CustomButton();
        selectButton = new CustomButton();
        filterBox = new JComboBox<>(filterVar);
        filterBox.setFont(F.getSmall());
        filterBox.setSize(new Dimension(20, 10));
        searchField = new SearchTextField();
        searchField.setBackground(Color.white);
        searchField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        searchField.setPreferredSize(new Dimension(180,10));
        scriptButton = new VerticalButton("Script", false);
        scriptButton.setBackground(F.getColor());
        infoButton = new VerticalButton("Info", false);
        infoButton.setBackground(F.getColor());
        searchField.setMinimumSize(new Dimension(20, 10));
        clearButton.addActionListener(e -> {
            try {
                workCommand("clear");
            } catch (IOException ex) {
                getIoException();
            }
        });
        addButton.addActionListener(e -> {
            new NewHumanFrame();
        });
        updateButton.addActionListener(e -> {
            try {
                MainFrame.workCommand("show");
            } catch (IOException ex) {
                getIoException();
            }
        });
        scriptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainFrame.workCommand("execute_script");
                    if (!Execute_script.result.equals(""))
                        new Result(Execute_script.result);
                } catch (IOException ex) {
                    getIoException();
                }
            }
        });
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InfoResult(collection.getInfo());
            }
        });
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String field = searchField.getText();
                    if (!field.equals("")) {
                        humanTable.fillCollection(filterBox.getSelectedIndex(), field);
                    } else humanTable.fillCollection();
                } catch (Exception e1) {
                    humanTable.fillCollection();
                }
            }
        });
        JPanel gridPanel = new JPanel();
        gridPanel.setOpaque(false);
        GridBagLayout grid = new GridBagLayout();
        gridPanel.setLayout(grid);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = GridBagConstraints.REMAINDER;
        c.gridwidth = 1;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 20, 0, 20);
        grid.setConstraints(addButton, c);
        grid.setConstraints(updateButton, c);
        grid.setConstraints(clearButton, c);
        grid.setConstraints(filterBox, c);
        grid.setConstraints(searchField, c);
        grid.setConstraints(selectButton, c);
        gridPanel.add(addButton);
        gridPanel.add(clearButton);
        gridPanel.add(filterBox);
        gridPanel.add(searchField);
        gridPanel.add(selectButton);
        gridPanel.add(updateButton);


        tabbedPane = new JTabbedPane();
        setTabbedPane(tabbedPane);
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(10);
        setLayout(borderLayout);
        //tabbedPane.setSize(new Dimension(1000, 200));
        add(gridPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }


    private void setTabbedPane(JTabbedPane tabbedPane) {
        GridBagLayout gridBagLayout = new GridBagLayout();
        JPanel twoButtonPanel = new JPanel(new GridBagLayout());
        twoButtonPanel.setLayout(gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(20, 0, 20, 20);
        gridBagLayout.setConstraints(scriptButton, c);
        gridBagLayout.setConstraints(infoButton, c);
        twoButtonPanel.add(scriptButton);
        twoButtonPanel.add(infoButton);
        twoButtonPanel.setOpaque(false);


        JPanel twoButtonAndTable = new JPanel(new BorderLayout());
        twoButtonAndTable.setOpaque(false);
        twoButtonAndTable.add(twoButtonPanel, BorderLayout.WEST);

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        canvas = new Canvas(frame);
        humanTable = new HumansTable(tableModel, canvas);

        for (int i=0; i<columnNames.length; i++) {
            TableColumn col = humanTable.getColumnModel().getColumn(i);
            col.setMinWidth(40);
            //col.setHeaderRenderer(new MyTableHeaderRenderer());
        }
        JScrollPane pane = new JScrollPane(humanTable);
        pane.setOpaque(false);
        twoButtonAndTable.add(pane);
        tabbedPane.add(LocaleController.getResourceBundle().getString("Table"), twoButtonAndTable);
        tabbedPane.add(LocaleController.getResourceBundle().getString("Canvas"), canvas);
        tabbedPane.setForeground(F.getColor());
        tabbedPane.setFont(F.getSmall());


    }

    @Override
    public void createLocale() {
        ResourceBundle resourceBundle = LocaleController.getResourceBundle();
        addButton.setText(resourceBundle.getString("add"));
        clearButton.setText(resourceBundle.getString("clear"));
        updateButton.setText(resourceBundle.getString("update"));
        selectButton.setText(resourceBundle.getString("select"));
        String[] newfilterVar = new String[]{
                "id",
                resourceBundle.getString("Name"),
                resourceBundle.getString("Owner"),
                resourceBundle.getString("Coordinates"),
                resourceBundle.getString("Creation_date"),
                resourceBundle.getString("Is_the_character_fictional?"),
                resourceBundle.getString("Has_a_toothpick?"),
                resourceBundle.getString("Impact_speed"),
                resourceBundle.getString("Soundtrack_name"),
                resourceBundle.getString("Minutes_of_waiting"),
                resourceBundle.getString("Type_of_weapon"),
                resourceBundle.getString("Name_of_the_car"),
                resourceBundle.getString("Is_car_cool?"),
        };
        DefaultComboBoxModel model = (DefaultComboBoxModel) filterBox.getModel();
        model.removeAllElements();
        for (String item : newfilterVar)
            model.addElement(item);
        filterBox.setModel(model);
        back.setText(resourceBundle.getString("back"));
    }


    class VerticalButton extends JButton {
        XButton template;
        boolean clockwise;

        VerticalButton(String text, boolean clockwise) {
            template = new XButton(text, getFont());
            this.clockwise = clockwise;
            Dimension d = template.getPreferredSize();
            setPreferredSize(new Dimension(d.height, d.width));

        }

        public void setFont(Font font) {
            super.setFont(font);
            if (template != null) {
                template.setFont(font);
            }
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            Dimension d = getSize();
            template.setSize(d.height, d.width);

            if (clockwise) {
                g2.rotate(Math.PI / 2.0);
                g2.translate(0, -getSize().width);
            } else {
                g2.translate(0, getSize().height);
                g2.rotate(-Math.PI / 2.0);
            }
            template.setSelected(this.getModel().isPressed());
            template.paintComponent(g2);
            g2.dispose();
        }

        private class XButton extends JToggleButton {
            XButton(String text, Font font) {
                super(text);
                setFont(font);
            }

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        }

    }

}
