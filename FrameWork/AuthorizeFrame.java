package FrameWork;

import Connection.App;
import Utilites.LocaleController;
import Utilites.Localizeable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthorizeFrame extends JFrame implements Localizeable {
    public static boolean validPortAndHost = false;
    public static boolean validLogAndPassword = false;
    private static boolean pressedOk = false;
    public static boolean pressedLog = false;
    public static boolean pressedSign = false;
    private static int port;
    public static String messageFail = "Сервер недоступен";
    private static String host;
    private static String password;
    public static String login;
    private JFrame frame = this;

    public static String getPassword() {
        return password;
    }

    public static String getLogin() {
        return login;
    }

    public static boolean isPressedLog() {
        return pressedLog;
    }

    public static boolean isPressedSign() {
        return pressedSign;
    }

    public static boolean isPressedOk() {
        return pressedOk;
    }

    public static String getHost() {
        return host;
    }

    public static int getPort() {
        return port;
    }

    private static final Font fieldFont = new Font("Calibri Light", Font.PLAIN, 14);
    private static final Font titleFont = new Font("TimesRoman", Font.BOLD, 18);
    private static final Font font = new Font("TimesRoman", Font.BOLD, 16);
    private static JPasswordField passwordField;
    private static JSpinner portField;
    private static JTextField hostField;
    private static JTextField loginField;

    private static CustomButton okbutton;
    private static CustomButton logButton = new CustomButton();
    private static CustomButton signButton = new CustomButton();
    private static  JButton back;
    private static JLabel portLabel = new JLabel();
    private static JLabel hostLabel = new JLabel();
    private static JLabel titleLable = new JLabel();
    private static JLabel loginLabel = new JLabel();
    private static JLabel passwordLabel = new JLabel();
    private static AuthorizeMenuBar authorizeMenuBar;

    public AuthorizeFrame() {
        LocaleController.regist(this);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width / 2 - 200, screenSize.height / 2 - 125, 400, 250);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Авторизация");
        setAlwaysOnTop(false);
        setFont(new java.awt.Font("Calibri Light", 0, 16));
        setResizable(false);
        authorizeMenuBar = new AuthorizeMenuBar();
        setJMenuBar(authorizeMenuBar);
        setContentPane(new Background("resources\\aut.png"));
        createPortAndHostWindow();
        createLocale();
    }

    private JPanel createLabelAndFields(JLabel label1, JTextField textField1, JLabel label2, JTextField textField2) {
        JPanel grid = new JPanel(new GridLayout(2, 2, -110, 15));
        grid.add(label1);
        grid.add(textField1);
        grid.add(label2);
        grid.add(textField2);
        grid.setOpaque(false);
        return grid;
    }

    private JPanel createLabelAndFields(JLabel label1, JSpinner spinner, JLabel label2, JTextField textField2) {
        JPanel grid = new JPanel(new GridLayout(2, 2, -110, 15));
        grid.add(label1);
        grid.add(spinner);
        grid.add(label2);
        grid.add(textField2);
        grid.setOpaque(false);
        return grid;
    }

    private void createPortAndHostWindow() {
        AbstractAction workButton = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    host = hostField.getText();
                    if (host.equals("")) host = "localhost";
                    port = (int) portField.getValue();
                    if (!host.equals("")) {

                        pressedOk = true;
                        Thread.currentThread().sleep(200);
                        pressedOk = false;
                        if (validPortAndHost) {
                            clearFrame();
                            createLogAndPasswordWindow();
                        } else {
                            JOptionPane.showMessageDialog(frame, messageFail + "=(", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException | InterruptedException e1) {
                    new FailedComponents(portField, Color.RED, 2000).start();
                }
            }
        };

        setLabel(titleLable, titleFont, Color.white);
        setLabel(hostLabel, font, Color.white);
        setLabel(portLabel, font, Color.white);
        hostField = new JTextField(16);
        hostField.setFont(fieldFont);
        hostField.setForeground(Color.BLACK);
        hostField.setUI(new JTextFieldHintUI("localhost", new Color(98, 98, 98)));

        portField = new JSpinner();
        JComponent editor = portField.getEditor();
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
        portField.setValue(156);
        portField.setFont(fieldFont);
        portField.setForeground(Color.BLACK);
        // portField.setUI(new JTextFieldHintUI("5643", new Color(98, 98, 98)));


        okbutton = new CustomButton();
        okbutton.setText("ok");
        okbutton.setForeground(Color.GRAY);
        okbutton.setBackground(Color.white);

        logButton.setForeground(Color.GRAY);
        logButton.setBackground(Color.white);
        logButton.setFont(F.getSmall());

        signButton.setForeground(Color.GRAY);
        signButton.setBackground(Color.white);
        signButton.setFont(F.getSmall());

        okbutton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "workButton");
        okbutton.getActionMap().put("workButton", workButton);
        okbutton.addActionListener(workButton);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBackground(Color.darkGray);
        panel.setSize(this.getSize());
        BorderLayout layout = new BorderLayout();
        layout.setVgap(20);
        panel.setLayout(layout);
        panel.add(titleLable, BorderLayout.NORTH);
        layout.setVgap(10);
        panel.add(createLabelAndFields(portLabel, portField, hostLabel, hostField));
        layout.setVgap(20);
        panel.add(okbutton, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);

//TODO убрать
        hostField.setText("localhost");
       okbutton.doClick();
    }

    public void createLogAndPasswordWindow() {
        back = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Runnable startWindow = () -> {
                    new AuthorizeFrame();
                    try {
                        App.startProgramm();
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
        authorizeMenuBar.add(back);
        AbstractAction workButtonLog = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AuthorizeFrame.login = loginField.getText();
                    AuthorizeFrame.password = passwordField.getText();
                    if (login.equals("")) new FailedComponents(loginField, Color.RED, 2000).start();
                    if (password.equals("")) new FailedComponents(passwordField, Color.RED, 2000).start();
                    if (!login.equals("") && !password.equals("")) {
                        pressedLog = true;
                        Thread.sleep(200);
                        //pressedLog = false;
                        if (validLogAndPassword) {

                            Runnable startWindow = () -> new MainFrame(App.getSender(), App.getReceiver());
                            new Thread(startWindow).start();
                            setVisible(false);
                        } else {
                            Thread.currentThread().sleep(200);
                            JOptionPane.showMessageDialog(frame, messageFail + "=(", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        };
        AbstractAction workButtonSign = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AuthorizeFrame.login = loginField.getText();
                    AuthorizeFrame.password = passwordField.getText();
                    if (login.equals("")) new FailedComponents(loginField, Color.RED, 2000).start();
                    if (password.equals("")) new FailedComponents(passwordField, Color.RED, 2000).start();
                    if (!login.equals("") && !password.equals("")) {
                        pressedSign = true;
                        Thread.currentThread().sleep(200);
                        //pressedSign = false;
                        if (validLogAndPassword) {
                            Runnable startWindow = () -> {
                                new MainFrame(App.getSender(), App.getReceiver());
                            };
                            new Thread(startWindow).start();
                            setVisible(false);
                        } else {
                            Thread.currentThread().sleep(200);
                            JOptionPane.showMessageDialog(frame, messageFail + "=(", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        };

        setLabel(loginLabel, font, Color.white);
        setLabel(passwordLabel, font, Color.white);

        loginField = new JTextField(16);
        loginField.setFont(fieldFont);
        passwordField = new JPasswordField(16);
        passwordField.setEchoChar('♡');


        logButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "workButton");
        logButton.getActionMap().put("workButton", workButtonLog);
        logButton.setText("Войти");
        logButton.addActionListener(workButtonLog);


        signButton.setText("Зарегистрироваться");
        signButton.addActionListener(workButtonSign);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBackground(Color.darkGray);
        panel.setSize(this.getSize());
        BorderLayout layout = new BorderLayout();
        layout.setVgap(20);
        panel.setLayout(layout);
        panel.add(titleLable, BorderLayout.NORTH);
        layout.setVgap(10);
        panel.add(createLabelAndFields(loginLabel, loginField, passwordLabel, passwordField));
        layout.setVgap(20);
        JPanel grid = new JPanel(new GridLayout(1, 2, 20, 2));
        grid.setOpaque(false);
        grid.add(logButton);
        grid.add(signButton);
        panel.add(grid, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);

        //TODO убрать
        loginField.setText("1");
        passwordField.setText("1");
        logButton.doClick();
    }

    private void clearFrame() {
        getContentPane().removeAll();
        repaint();
    }

    private void setLabel(JLabel label, Font font, Color color) {
        label.setFont(font);
        label.setForeground(color);
    }

    @Override
    public void createLocale() {
        ResourceBundle language = LocaleController.getResourceBundle();
        hostLabel.setText(language.getString("host") + ":");
        portLabel.setText(language.getString("port") + ":");
        titleLable.setText(language.getString("Managing_a_collection"));
        loginLabel.setText(language.getString("login") + ":");
        passwordLabel.setText(language.getString("password") + ":");
        logButton.setText(language.getString("Enter"));
        signButton.setText(language.getString("Register"));
        try {
            back.setText(language.getString("return"));
        }
        catch (Exception e){

        }
        this.repaint();
    }


    public static class JTextFieldHintUI extends BasicTextFieldUI implements FocusListener {
        private String hint;
        private Color hintColor;

        public JTextFieldHintUI(String hint, Color hintColor) {
            this.hint = hint;
            this.hintColor = hintColor;
        }

        private void repaint() {
            if (getComponent() != null) {
                getComponent().repaint();
            }
        }

        @Override
        protected void paintSafely(Graphics g) {
            // Render the default text field UI
            super.paintSafely(g);

            // Render the hint text
            JTextComponent component = getComponent();
            if (component.getText().length() == 0 && !component.hasFocus()) {
                g.setColor(hintColor);
                int padding = (component.getHeight() - component.getFont().getSize()) / 2;
                int inset = 3;
                g.drawString(hint, inset, component.getHeight() - padding - inset);
            }
        }

        @Override
        public void focusGained(FocusEvent e) {
            repaint();
        }

        @Override
        public void focusLost(FocusEvent e) {
            repaint();
        }

        @Override
        public void installListeners() {
            super.installListeners();
            getComponent().addFocusListener(this);
        }

        @Override
        public void uninstallListeners() {
            super.uninstallListeners();
            getComponent().removeFocusListener(this);
        }
    }

}