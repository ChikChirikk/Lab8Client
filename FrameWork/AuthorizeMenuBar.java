package FrameWork;

import Commands.Help;
import Utilites.LocaleController;
import Utilites.Localizeable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthorizeMenuBar extends JMenuBar implements Localizeable {
    private Font font = F.getOrdinary();
    private JMenu language;
    private JMenuItem rus;
    private JMenuItem english;
    private JMenuItem português;
    private JMenuItem lietuvis;

    public AuthorizeMenuBar() {
        LocaleController.regist(this);
        language = new JMenu();
        language.setFont(font);

        //exit.setBackground(new Color(127, 138, 144));
        language.setFont(font);

        rus = new JMenuItem("Русский");
        rus.setFont(font);
        rus.setIcon(new ImageIcon("resources/rus.png"));
        english = new JMenuItem("English");
        english.setFont(font);
        english.setIcon(new ImageIcon("resources/eng.png"));
        português = new JMenuItem("Português");
        português.setIcon(new ImageIcon("resources/port.png"));
        português.setFont(font);
        lietuvis = new JMenuItem("Lietuvis");
        lietuvis.setIcon(new ImageIcon("resources/lit.png"));
        lietuvis.setFont(font);

        language.add(rus);
        language.add(english);
        language.add(português);
        language.add(lietuvis);
        initLanguages();
        add(language);
        add(Box.createGlue());

        createLocale();
    }

    public void initLanguages() {
        rus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (LocaleController.getLocal("Russian") != Locale.getDefault())
                    LocaleController.changeLocale(LocaleController.getLocal("Russian"));
            }
        });
        english.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (LocaleController.getLocal("English") != Locale.getDefault())
                    LocaleController.changeLocale(LocaleController.getLocal("English"));
            }
        });
        português.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (LocaleController.getLocal("Portugal") != Locale.getDefault())
                    LocaleController.changeLocale(LocaleController.getLocal("Portugal"));
            }
        });
        lietuvis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (LocaleController.getLocal("Lithuanian") != Locale.getDefault())
                    LocaleController.changeLocale(LocaleController.getLocal("Lithuanian"));
            }
        });

    }

    @Override
    public void createLocale() {
        ResourceBundle resourceBundle = LocaleController.getResourceBundle();
        language.setText(resourceBundle.getString("Language"));
    }
}
