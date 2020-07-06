package FrameWork;

import Human.HumanBeing;
import Utilites.LocaleController;
import Utilites.Localizeable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class NewHumanFrame extends JFrame implements Localizeable {
    private JFrame frame = this;
    private static JTextField nameFieldf;
    private static JTextField coordXf;
    private static JTextField coordYf;
    private static JCheckBox realHerof;
    private static JCheckBox hasToothPeekf;
    private static JTextField impactSpeedf;
    private static JTextField soundtrackNamef;
    private static JTextField minutesOfWaitingf;
    private static JComboBox weaponeTypef;
    private static JTextField carNamef;
    private static JCheckBox carCoolf;
    private final static String[] typesOfWeapon = new String[]{"KNIFE", "HAMMER", "BAT", "RIFLE"};

    private static JLabel nameFieldl;
    private static JLabel coordXl;
    private static JLabel coordYl;
    private static JLabel realHerol;
    private static JLabel hasToothPeekl;
    private static JLabel impactSpeedl;
    private static JLabel soundtrackNamel;
    private static JLabel minutesOfWaitingl;
    private static JLabel weaponeTypel;
    private static JLabel carNamel;
    private static JLabel carCooll;
    private JFrame mainFrame;
    private static JButton okbutton;

    public NewHumanFrame() {
        this.mainFrame = frame;
        LocaleController.regist(this);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width / 2 - 300, screenSize.height / 2 - 170, 600, 340);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Создание человека");
        setAlwaysOnTop(true);
        setResizable(false);
        //setContentPane(new Background("resources\\aut.png"));
        initComponents();
        createLocale();
        setVisible(true);
    }

    private void initComponents() {
        nameFieldf = new JTextField(12);
        coordXf = new JTextField(12);
        coordYf = new JTextField(12);
        realHerof = new JCheckBox();
        hasToothPeekf = new JCheckBox();
        impactSpeedf = new JTextField();
        soundtrackNamef = new JTextField();
        minutesOfWaitingf = new JTextField();
        weaponeTypef = new JComboBox(typesOfWeapon);
        carNamef = new JTextField();
        carCoolf = new JCheckBox();

        nameFieldl = new JLabel("Имя", SwingConstants.RIGHT);
        coordXl = new JLabel("x", SwingConstants.RIGHT);
        coordYl = new JLabel("y", SwingConstants.RIGHT);
        realHerol = new JLabel("<html>Вымышленный ли<br/>&emsp&emsp&emspперсонаж?</html>", SwingConstants.RIGHT);
        hasToothPeekl = new JLabel("Есть ли зубочистка?", SwingConstants.RIGHT);
        impactSpeedl = new JLabel("Скорость удара", SwingConstants.RIGHT);
        soundtrackNamel = new JLabel("Название трека", SwingConstants.RIGHT);
        minutesOfWaitingl = new JLabel("Минуты ожидания", SwingConstants.RIGHT);
        weaponeTypel = new JLabel("Тип оружия", SwingConstants.RIGHT);
        carNamel = new JLabel("Имя машины", SwingConstants.RIGHT);
        carCooll = new JLabel("Пантовая тачка?", SwingConstants.RIGHT);

        okbutton = new JButton("ОК");
        addFuncToOK();

        initActionsForCheckBox();
        GridLayout gridLayout = new GridLayout(1, 2);
        setLayout(gridLayout);

        GridBagLayout grid = new GridBagLayout();

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 20, 10, 20);

        JPanel left = new JPanel();
        left.setLayout(grid);
        left.setOpaque(false);
        left.add(packLabelAndField(nameFieldf, nameFieldl, c, grid));
        left.add(packLabelAndField(coordXf, coordXl, c, grid));
        left.add(packLabelAndField(coordYf, coordYl, c, grid));
        left.add(packLabelAndField(realHerof, realHerol, c, grid));
        left.add(packLabelAndField(hasToothPeekf, hasToothPeekl, c, grid));
        left.add(packLabelAndField(impactSpeedf, impactSpeedl, c, grid));
        add(left);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(grid);
        right.add(packLabelAndField(soundtrackNamef, soundtrackNamel, c, grid));
        right.add(packLabelAndField(minutesOfWaitingf, minutesOfWaitingl, c, grid));
        right.add(packLabelAndField(weaponeTypef, weaponeTypel, c, grid));
        right.add(packLabelAndField(carNamef, carNamel, c, grid));
        right.add(packLabelAndField(carCoolf, carCooll, c, grid));
        c.insets = new Insets(20, 20, 10, 20);
        c.fill = GridBagConstraints.NONE;
        grid.setConstraints(okbutton, c);
        right.add(okbutton);
        add(right);

    }

    private static String name;
    private static long coordX;
    private static long coordY;
    private static boolean realHero;
    private static boolean hasToothPeek;
    private static double impactSpeed;
    private static String soundtrackName;
    private static double minutesOfWaiting;
    private static String weaponeType;
    private static String carName;
    private static boolean carCool;

    private boolean isAllValid = true;
    private HumanBeing humanBeing = null;

    public HumanBeing getHumanBeing() {
        return humanBeing;
    }

    public void addFuncToOK() {
        AbstractAction workButton = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                humanBeing = null;
                isAllValid = true;
                name = nameFieldf.getText();
                if (name.equals("")) {
                    isAllValid = false;
                    new FailedComponents(nameFieldf, Color.RED, FailedComponents.UNLESS_FOCUSED).start();
                }
                try {
                    coordX = Long.parseLong(coordXf.getText());
                    if (coordY > 1350 || coordY < -1350) {
                        isAllValid = false;
                        new FailedComponents(coordYf, Color.RED, FailedComponents.UNLESS_FOCUSED).start();
                    }
                } catch (NumberFormatException n) {
                    isAllValid = false;
                    new FailedComponents(coordXf, Color.RED, FailedComponents.UNLESS_FOCUSED).start();
                }
                try {
                    coordY = Long.parseLong(coordYf.getText());
                    if (coordY > 483 || coordY < -483) {
                        isAllValid = false;
                        new FailedComponents(coordYf, Color.RED, FailedComponents.UNLESS_FOCUSED).start();
                    }
                } catch (NumberFormatException n) {
                    isAllValid = false;
                    new FailedComponents(coordYf, Color.RED, FailedComponents.UNLESS_FOCUSED).start();
                }
                realHero = realHerof.isSelected();
                hasToothPeek = hasToothPeekf.isSelected();
                try {
                    impactSpeed = Double.parseDouble(impactSpeedf.getText());
                } catch (NumberFormatException n) {
                    isAllValid = false;
                    new FailedComponents(impactSpeedf, Color.RED, FailedComponents.UNLESS_FOCUSED).start();
                }
                soundtrackName = soundtrackNamef.getText();
                if (soundtrackName.equals("")) {
                    isAllValid = false;
                    new FailedComponents(soundtrackNamef, Color.RED, FailedComponents.UNLESS_FOCUSED).start();
                }
                try {
                    minutesOfWaiting = Double.parseDouble(minutesOfWaitingf.getText());
                } catch (NumberFormatException n) {
                    isAllValid = false;
                    new FailedComponents(minutesOfWaitingf, Color.RED, FailedComponents.UNLESS_FOCUSED).start();
                }
                weaponeType = (String) weaponeTypef.getSelectedItem();
                carName = carNamef.getText();
                if (carName.equals("")) {
                    isAllValid = false;
                    new FailedComponents(carNamef, Color.RED, FailedComponents.UNLESS_FOCUSED).start();
                }
                carCool = carCoolf.isSelected();
                if (isAllValid) {
                    humanBeing = new HumanBeing();
                    humanBeing.setName(name);
                    HumanBeing.Coordinates coordinates = humanBeing.new Coordinates();
                    coordinates.setX(coordX);
                    coordinates.setY(coordY);
                    humanBeing.setCoordinates(coordinates);
                    humanBeing.setCreationDate(LocalDate.now());
                    humanBeing.setWeaponType(HumanBeing.WeaponType.valueOf(weaponeType));
                    humanBeing.setSoundtrackName(soundtrackName);
                    humanBeing.setMinutesOfWaiting(minutesOfWaiting);
                    humanBeing.setImpactSpeed(impactSpeed);
                    humanBeing.setRealHero(realHero);
                    humanBeing.setHasToothpick(hasToothPeek);
                    HumanBeing.Car car = humanBeing.new Car();
                    car.setCarCool(carCool);
                    car.setCarName(carName);
                    humanBeing.setCar(car);
                    frame.setVisible(false);
                    frame.dispose();
                    try {
                        MainFrame.workCommand("add", getHumanBeing());
                    } catch (IOException ex) {
                        getIoException();
                        ex.printStackTrace();
                    }
                }
            }
        };
        okbutton.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "workButton");
        okbutton.getActionMap().put("workButton", workButton);
        okbutton.addActionListener(workButton);
    }

    private void getIoException() {
        JOptionPane.showMessageDialog(frame, "Сервер недоступен=( Попробуйте через время отправить команду", "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
    public JPanel packLabelAndField(JTextField field, JLabel label, GridBagConstraints c, GridBagLayout grid) {
        JPanel labelAndButton = new JPanel(new GridLayout(1, 2, 10, 10));
        labelAndButton.add(label);
        labelAndButton.add(field);
        grid.setConstraints(labelAndButton, c);
        return labelAndButton;
    }

    public JPanel packLabelAndField(JComboBox field, JLabel label, GridBagConstraints c, GridBagLayout grid) {
        JPanel labelAndButton = new JPanel(new GridLayout(1, 2, 10, 10));
        labelAndButton.add(label);
        labelAndButton.add(field);
        grid.setConstraints(labelAndButton, c);
        return labelAndButton;
    }

    public JPanel packLabelAndField(JCheckBox field, JLabel label, GridBagConstraints c, GridBagLayout grid) {
        JPanel labelAndButton = new JPanel(new GridLayout(1, 2, 10, 10));
        labelAndButton.add(label);
        labelAndButton.add(field);
        grid.setConstraints(labelAndButton, c);
        return labelAndButton;
    }

    public void initActionsForCheckBox() {
        AbstractAction realHeroWork = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realHerof.setSelected(true);
            }
        };
        AbstractAction hasToothPeekWork = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hasToothPeekf.setSelected(true);
            }
        };
        AbstractAction carCoolWork = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carCoolf.setSelected(true);
            }
        };
        realHerof.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "realHeroWork");
        realHerof.getActionMap().put("realHeroWork", realHeroWork);

        hasToothPeekf.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "hasToothPeekWork");
        hasToothPeekf.getActionMap().put("hasToothPeekWork", hasToothPeekWork);

        carCoolf.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "carCoolWork");
        carCoolf.getActionMap().put("carCoolWork", carCoolWork);
    }

    @Override
    public void createLocale() {
        ResourceBundle resourceBundle = LocaleController.getResourceBundle();
        nameFieldl.setText(resourceBundle.getString("Name"));
        coordXl.setText("x");
        coordYl.setText("y");
        realHerol.setText(resourceBundle.getString("Is_the_character_fictional?"));
        hasToothPeekl.setText(resourceBundle.getString("Has_a_toothpick?"));
        impactSpeedl.setText(resourceBundle.getString("Impact_speed"));
        soundtrackNamel.setText(resourceBundle.getString("Soundtrack_name"));
        minutesOfWaitingl.setText(resourceBundle.getString("Minutes_of_waiting"));
        weaponeTypel.setText(resourceBundle.getString("Type_of_weapon"));
        carNamel.setText(resourceBundle.getString("Name_of_the_car"));
        carCooll.setText(resourceBundle.getString("Is_car_cool?"));
        okbutton.setText(resourceBundle.getString("add"));
        coordXf.setToolTipText(resourceBundle.getString("odzx"));
        coordYf.setToolTipText(resourceBundle.getString("odzy"));
        minutesOfWaitingf.setToolTipText(resourceBundle.getString("double"));
        impactSpeedf.setToolTipText(resourceBundle.getString("double"));

        coordXf.createToolTip();
    }
}

