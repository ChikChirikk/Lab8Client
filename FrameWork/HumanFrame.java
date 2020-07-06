package FrameWork;

import Human.HumanBeing;
import Utilites.LocaleController;
import Utilites.Localizeable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HumanFrame extends JFrame implements Localizeable {
    private JFrame frame = this;
    private JTextField nameFieldf;
    private JTextField coordXf;
    private JTextField coordYf;
    private JCheckBox realHerof;
    private JCheckBox hasToothPeekf;
    private JTextField impactSpeedf;
    private JTextField soundtrackNamef;
    private JTextField minutesOfWaitingf;
    private JComboBox weaponeTypef;
    private JTextField carNamef;
    private JCheckBox carCoolf;
    private final String[] typesOfWeapon = new String[]{"KNIFE", "HAMMER", "BAT", "RIFLE"};

    private JLabel nameFieldl;
    private JLabel coordXl;
    private JLabel coordYl;
    private JLabel realHerol;
    private JLabel hasToothPeekl;
    private JLabel impactSpeedl;
    private JLabel soundtrackNamel;
    private JLabel minutesOfWaitingl;
    private JLabel weaponeTypel;
    private JLabel carNamel;
    private JLabel carCooll;
    private JLabel idl = new JLabel();
    private JLabel ownerl = new JLabel();

    private JButton removeButton;
    private JButton updateButton;
    private HumanBeing human;
    public static int count = 0;

    public HumanFrame(HumanBeing humanBeing) {
        if (count == 0) {
            count = 1;
            LocaleController.regist(this);
            human = humanBeing;
            setAlwaysOnTop(true);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setBounds(screenSize.width / 2 - 300, screenSize.height / 2 - 170, 600, 340);
            setBackground(Color.WHITE);
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setTitle("Челик " + human.getId());
            setResizable(false);
            initComponents();
            initHuman();
            createLocale();
            design();
            setVisible(true);
        }
    }

    private void addIdAndOwner() {
        idl = new JLabel("id " + human.getId());
        ownerl = new JLabel("owner " + human.getOwner());
        idl.setBounds(100, 1, 30, 10);
        //ownerl.setBounds(1, 1, 30,10);
        add(idl);
        // add(ownerl);
    }
    private void getIoException() {
        JOptionPane.showMessageDialog(frame, "Сервер недоступен=( Попробуйте через время отправить команду", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
        if (AuthorizeFrame.login.equals(human.getOwner())) {
            JPanel panel = getBottomsPanel();
            grid.setConstraints(panel, c);
            right.add(panel);
        }
        add(right);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                count = 0;
            }

            @Override
            public void windowClosed(WindowEvent e) {
                count = 0;
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

    }

    private JPanel getBottomsPanel() {
        updateButton = new JButton("update");
        removeButton = new JButton("remove");
        addFuncToButtoms();
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setOpaque(false);
        panel.add(updateButton);
        panel.add(removeButton);
        return panel;

    }

    private void initHuman() {
        nameFieldf.setText(human.getName());
        coordXf.setText(String.valueOf(human.getCoordinates().getX()));
        coordYf.setText(String.valueOf(human.getCoordinates().getY()));
        if (human.isRealHero())
            realHerof.setSelected(true);
        if (human.isHasToothpick())
            realHerof.setSelected(true);
        impactSpeedf.setText(String.valueOf(human.getImpactSpeed()));
        soundtrackNamef.setText(human.getSoundtrackName());
        minutesOfWaitingf.setText(String.valueOf(human.getMinutesOfWaiting()));
        weaponeTypef.setSelectedItem(String.valueOf(human.getWeaponType()));
        carNamef.setText(human.getCar().getCarName());
        if (human.getCar().isCarCool())
            carCoolf.setSelected(true);
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
    private static HumanBeing humanBeing = null;

    public static HumanBeing getHumanBeing() {
        while (humanBeing == null) {
        }
        return humanBeing;
    }

    public void addFuncToButtoms() {
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
                    try {
                        System.out.println("update " + human.getId());
                        MainFrame.workCommand("update " + human.getId(), humanBeing);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        getIoException();
                    }
                    frame.setVisible(false);
                    frame.dispose();
                    count = 0;
                }
            }
        };
        updateButton.addActionListener(workButton);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainFrame.workCommand("remove_by_id " + human.getId());
                    frame.setVisible(false);
                    frame.dispose();
                } catch (IOException ex) {
                    getIoException();
                    ex.printStackTrace();
                }
            }
        });
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
        idl.setText("id");
        ownerl.setText(resourceBundle.getString("Owner"));
        try {
            removeButton.setText(resourceBundle.getString("remove"));
            updateButton.setText(resourceBundle.getString("update"));
        } catch (Exception e) {
        }
        coordXf.setToolTipText(resourceBundle.getString("odzx"));
        coordYf.setToolTipText(resourceBundle.getString("odzy"));
        minutesOfWaitingf.setToolTipText(resourceBundle.getString("double"));
        impactSpeedf.setToolTipText(resourceBundle.getString("double"));
    }
    public void design(){
        setFont(F.getMedium());
        setForeground(F.getColor());
    }
}


