/*
 * Copyright (c) 2017 Asiel Díaz Benítez.
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * You should have received a copy of the GNU General Public License
 * along with this file.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */

package adbenitez.test;

import adbenitez.notify.core.Notification;
import adbenitez.notify.core.Notification.*;
import adbenitez.notify.core.util.NLabel;
import adbenitez.notify.core.util.NotifyConfig;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main extends JFrame {
    
    //	================= ATTRIBUTES ==============================
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final NotifyConfig config = NotifyConfig.getInstance();
    private final String API_NAME = config.getLibName();
    private final String API_VERSION = config.getLibVersion();

    private JPanel panel;
    private JButton[] buttons;
    private JButton[] confirmButts;
    
    private JTextField titleTF;
    private JTextField messageTF;
    private JComboBox<IconType> iconComB;
    private JComboBox<String> typeComB;
    private JButton launchButt;
    private static final Color DARK_COLOR = new Color(Integer.parseInt("1f1f1f",16));
    private static final Color LIGHT_COLOR = new Color(Integer.parseInt("f0f0f0",16));
    
    //	================= END ATTRIBUTES ==========================
    
    //	================= CONSTRUCTORS ===========================
    
    public Main () {
        super();
        setup();
        setTitle(API_NAME + " v" + API_VERSION + " -- Test Drive");
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    //	================= END CONSTRUCTORS =======================
    
    //	===================== METHODS ============================
    
    private void setup() {
        buttons = getButtons();
        confirmButts = getConfirmButtons();
        JScrollPane js = new JScrollPane(getPanel());
        setContentPane(js);
    }
    
    private JPanel getPanel() {
        if (panel == null) {
            // ------------ BANNER --------------
            NLabel info = new NLabel("Welcome to " + API_NAME +" v" + API_VERSION
                                     +"\nBy Asiel Diaz Benitez <asieldbenitez@gmail.com>\n"
                                     +"Based on NiconNotifyOSD 2.0 from:\n"+
                                     "Frederick Adolfo Salazar Sanchez <fredefass01@gmail.com>\n\n"
                                     +API_NAME + " is licensed under the GPL v3.\n"
                                     +"Put the mouse over a component to see the code snippet associated.\n");
            info.setFocusable(true);
            info.setBackground(DARK_COLOR);
            info.setForeground(LIGHT_COLOR);
            info.setFont(new Font(null, 0, 16));            
            // ------------ BANNER --------------
            
            // ------------ OPT_PANEL --------------
            // sounds checkBox
            final JCheckBox sounds = new JCheckBox("Play Sounds.", true);
            sounds.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        boolean b = evt.getStateChange() == ItemEvent.SELECTED;
                        Notification.setSoundsStatus(b);
                        sounds.setToolTipText("Notification.setSoundsStatus("+b+")");
                    }
                });
            Notification.setSoundsStatus(sounds.isSelected());
            sounds.setToolTipText("Notification.setSoundsStatus("+sounds.isSelected()+")");
            sounds.setForeground(LIGHT_COLOR);

            // debug checkBox
            final JCheckBox debug = new JCheckBox("Print debug messages (standard output).", true);
            debug.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        boolean b = evt.getStateChange() == ItemEvent.SELECTED;
                        Notification.setDebug(b);
                        debug.setToolTipText("Notification.setDebug("+b+")");
                    }
                });
            Notification.setDebug(debug.isSelected());
            debug.setToolTipText("Notification.setDebug("+debug.isSelected()+")");
            debug.setForeground(LIGHT_COLOR);

            // iconsPack checkBox
            final JCheckBox iconsPack = new JCheckBox("Use New Icons", true);
            iconsPack.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        boolean b = evt.getStateChange() == ItemEvent.SELECTED;
                        Notification.useNewIcons(b);
                        iconsPack.setToolTipText("Notification.useNewIcons("+b+")");
                        
                    }
                });
            Notification.useNewIcons(iconsPack.isSelected());
            iconsPack.setToolTipText("Notification.useNewIcons("+iconsPack.isSelected()+")");
            iconsPack.setForeground(LIGHT_COLOR);
            
            // themes
            final JLabel themesLabel = new JLabel("Default theme: ");
            themesLabel.setForeground(LIGHT_COLOR);
            
            ThemeType[] themesArr = ThemeType.values();
            final JComboBox<ThemeType> themes = new JComboBox<ThemeType>(themesArr);
            themes.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        if (evt.getStateChange() == ItemEvent.SELECTED) {
                            ThemeType theme = (ThemeType)themes.getSelectedItem();
                            Notification.setDefaultTheme(theme);
                            themes.setToolTipText("Notification.setDefaultTheme(Notification."+theme.name()+")");
                            themesLabel.setToolTipText("Notification.setDefaultTheme(Notification."+theme.name()+")");
                        }
                    }
                });
            ThemeType theme = Notification.getDefaultTheme();
            themes.setSelectedItem(theme);
            themes.setToolTipText("Notification.setDefaultTheme(Notification."+theme.name()+")");
            themesLabel.setToolTipText("Notification.setDefaultTheme(Notification."+theme.name()+")");
            Notification.setDefaultTheme((ThemeType)themes.getSelectedItem());

            //types
            final JLabel typesLabel = new JLabel("Default type: ");
            typesLabel.setForeground(LIGHT_COLOR);
            MessageType[] typesArr = MessageType.values();
            final JComboBox<MessageType> types = new JComboBox<MessageType>(typesArr);
            types.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        if (evt.getStateChange() == ItemEvent.SELECTED) {
                            MessageType type = (MessageType)types.getSelectedItem();
                            Notification.setDefaultType(type);
                            types.setToolTipText("Notification.setDefaultType(Notification."+type.name()+")");
                            typesLabel.setToolTipText("Notification.setDefaultType(Notification."+type.name()+")");
                        }
                    }
                });
            MessageType mt = Notification.getDefaultType();
            types.setSelectedItem(mt);
            types.setToolTipText("Notification.setDefaultType(Notification."+mt.name()+")");
            typesLabel.setToolTipText("Notification.setDefaultType(Notification."+mt.name()+")");

            // border
            final JLabel borderLabel = new JLabel("Default border: ");
            borderLabel.setForeground(LIGHT_COLOR);
            BorderType[] borderArr = BorderType.values();
            final JComboBox<BorderType> border = new JComboBox<BorderType>(borderArr);
            border.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        if (evt.getStateChange() == ItemEvent.SELECTED) {
                            BorderType b = (BorderType)border.getSelectedItem();
                            Notification.setDefaultBorder(b);
                            border.setToolTipText("Notification.setDefaultBorder(Notication."+b.name()+")");
                            borderLabel.setToolTipText("Notification.setDefaultBorder(Notication."+b.name()+")");
                        }
                    }
                });
            BorderType b = Notification.getDefaultBorder();
            border.setSelectedItem(b);
            border.setToolTipText("Notification.setDefaultBorder(Notication."+b.name()+")");
            borderLabel.setToolTipText("Notification.setDefaultBorder(Notication."+b.name()+")");

            // Orientation
            final JLabel orientationLabel = new JLabel("Default orientation: ");
            orientationLabel.setForeground(LIGHT_COLOR);
            OrientationType[] orientationArr = OrientationType.values();
            final JComboBox<OrientationType> orientation = new JComboBox<OrientationType>(orientationArr);
            orientation.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        if (evt.getStateChange() == ItemEvent.SELECTED) {
                            OrientationType orient = (OrientationType)orientation.getSelectedItem();
                            Notification.setDefaultOrientation(orient);
                            orientation.setToolTipText("Notification.setDefaultOrientation(Notication."+orient.name()+")");
                            orientationLabel.setToolTipText("Notification.setDefaultOrientation(Notication."+orient.name()+")");
                            Notification.removeAllNotifications();
                        }
                    }
                });
            OrientationType orient = Notification.getDefaultOrientation();
            orientation.setSelectedItem(orient);
            orientation.setToolTipText("Notification.setDefaultOrientation(Notication."+orient.name()+")");
            orientationLabel.setToolTipText("Notification.setDefaultOrientation(Notication."+orient.name()+")");

            //opacity
            final JLabel opacityLabel = new JLabel("Default Opacity [0-1]:");
            opacityLabel.setForeground(LIGHT_COLOR);
            final JSpinner opacity = new JSpinner(new SpinnerNumberModel(Notification.getDefaultOpacity(), 0D, 1D, 0.01D));
            opacity.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent evt) {
                        float op = new Double((double)opacity.getValue()).floatValue();
                        Notification.setDefaultOpacity(op);
                        opacity.setToolTipText("Notification.setDefaultOpacity("+op+")");
                        opacityLabel.setToolTipText("Notification.setDefaultOpacity("+op+")");
                    }
                });
            float op = new Double((double)opacity.getValue()).floatValue();
            Notification.setDefaultOpacity(op);
            opacity.setToolTipText("Notification.setDefaultOpacity("+op+")");
            opacityLabel.setToolTipText("Notification.setDefaultOpacity("+op+")");
            
            // timeout
            final JLabel timeoutLabel = new JLabel("Timeout (in milliseconds, negative never closes):");
            timeoutLabel.setForeground(LIGHT_COLOR);
            final JSpinner timeout = new JSpinner(new SpinnerNumberModel(10000, null, 100000, 100));
            timeout.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent evt) {
                        int t = (int)timeout.getValue();
                        Notification.setDefaultTimeout(t);
                        timeoutLabel.setToolTipText("Notification.setDefaultTimeout("+t+")");
                        timeout.setToolTipText("Notification.setDefaultTimeout("+t+")");
                    }
                });
            int t = (int)timeout.getValue();
            Notification.setDefaultTimeout(t);
            timeoutLabel.setToolTipText("Notification.setDefaultTimeout("+t+")");
            timeout.setToolTipText("Notification.setDefaultTimeout("+t+")");
            // opt_panel
            JPanel opt_panel = new JPanel(new GridBagLayout());
            opt_panel.setBackground(null);
            TitledBorder titledB = new TitledBorder("Default options:");
            titledB.setTitleColor(LIGHT_COLOR);
            opt_panel.setBorder(titledB);
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1;
            //c.weighty = 1;
            c.insets = new Insets(2, 5, 2, 5);//top, left, bott, right
            
            c.gridy = 0; c.gridx = 0;
            opt_panel.add(sounds, c);
            c.gridy = 1;
            opt_panel.add(iconsPack, c);
            c.gridy = 2;
            c.insets = new Insets(2, 5, 5, 5);//top, left, bott, right
            opt_panel.add(debug, c);
            
            c.insets = new Insets(2, 5, 2, 5);//top, left, bott, right
            c.gridy = 3;
            c.gridx = 0; 
            opt_panel.add(themesLabel, c);
            c.gridx = 1;
            opt_panel.add(themes, c);

            c.gridy = 4;
            c.gridx = 0; 
            opt_panel.add(typesLabel, c);
            c.gridx = 1;
            opt_panel.add(types, c);
            
            c.gridy = 5;
            c.gridx = 0;            
            opt_panel.add(borderLabel, c);
            c.gridx = 1;            
            opt_panel.add(border, c);

            c.gridy = 6;
            c.gridx = 0;            
            opt_panel.add(orientationLabel, c);
            c.gridx = 1;            
            opt_panel.add(orientation, c);

            c.gridy = 7;
            c.gridx = 0;            
            opt_panel.add(opacityLabel, c);
            c.gridx = 1;            
            opt_panel.add(opacity, c);

            c.gridy = 8;
            c.gridx = 0;            
            opt_panel.add(timeoutLabel, c);
            c.gridx = 1;            
            opt_panel.add(timeout, c);
            // ------------ OPT_PANEL --------------
            
            // ------------ BUTT_PANEL --------------
            JPanel butt_panel = new JPanel(new GridBagLayout());
            butt_panel.setBackground(null);
            titledB = new TitledBorder("Quick Examples:");
            titledB.setTitleColor(LIGHT_COLOR);
            butt_panel.setBorder(titledB);
            
            c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            //c.weightx = 1;
            //c.weighty = 1;
            c.insets = new Insets(0, 1, 1, 1);//top, left, bott, right
            
            c.gridx = 0; c.gridy = 0;
            butt_panel.add(buttons[0], c);
            c.gridx = 1;
            butt_panel.add(buttons[5], c);
            c.gridx = 2;
            butt_panel.add(buttons[1], c);
            
            c.gridx = 0; c.gridy = 1; 
            butt_panel.add(buttons[2], c);
            c.gridx = 1;
            butt_panel.add(buttons[3], c);
            c.gridx = 2;
            butt_panel.add(buttons[4], c);

            c.gridy = 0; c.gridx=3;
            butt_panel.add(Box.createRigidArea(new Dimension(5, 0)), c);
            
            c.gridx = 4; c.gridy=0;
            butt_panel.add(confirmButts[0], c);
            c.gridx = 5;
            butt_panel.add(confirmButts[5], c);
            c.gridx = 6;
            butt_panel.add(confirmButts[1], c);
            
            c.gridx = 4; c.gridy=1;
            butt_panel.add(confirmButts[2], c);
            c.gridx = 5;
            butt_panel.add(confirmButts[3], c);
            c.gridx = 6;
            butt_panel.add(confirmButts[4], c);
            
            // ------------ BUTT_PANEL --------------

            // ------------ CUSTOM_PANEL ------------
            JPanel custom_panel = new JPanel(new GridBagLayout());
            custom_panel.setBackground(null);
            titledB = new TitledBorder("Custom Notification:");
            titledB.setTitleColor(LIGHT_COLOR);
            custom_panel.setBorder(titledB);

            JLabel label;
            
            c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1;
            
            c.gridy = 0; c.gridx = 0;
            c.insets = new Insets(40, 5, 2, 5);//top, left, bott, right
            label = new JLabel("Title:");
            label.setForeground(LIGHT_COLOR);
            label.setToolTipText("See de 'Launch' button to get the code snippet.");
            custom_panel.add(label, c);
            c.gridx = 1;
            custom_panel.add(getTitleTF(), c);

            c.insets = new Insets(2, 5, 2, 5);//top, left, bott, right
            
            c.gridy = 1; c.gridx = 0; 
            label = new JLabel("Message:");
            label.setForeground(LIGHT_COLOR);
            label.setToolTipText("See de 'Launch' button to get the code snippet.");
            custom_panel.add(label, c);
            c.gridx = 1;
            custom_panel.add(getMessageTF(), c);
            
            c.gridy = 2; c.gridx = 0;
            label = new JLabel("Icon:");
            label.setForeground(LIGHT_COLOR);
            label.setToolTipText("See de 'Launch' button to get the code snippet.");
            custom_panel.add(label, c);
            c.gridx = 1;
            custom_panel.add(getIconComB(), c);
            
            c.gridy = 3; c.gridx = 0;
            label = new JLabel("Notification method:");
            label.setForeground(LIGHT_COLOR);
            label.setToolTipText("See de 'Launch' button to get the code snippet.");
            custom_panel.add(label, c);
            c.gridx = 1;
            custom_panel.add(getTypeComB(), c);

            c.gridy = 4; c.gridx = 0;
            c.weighty = 1;
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.CENTER;
            c.insets = new Insets(20, 5, 2, 5);//top, left, bott, right
            custom_panel.add(getLaunchButt(), c);
            // ------------ CUSTOM_PANEL ------------
            
            Box hBox = Box.createHorizontalBox();
            hBox.add(opt_panel);
            hBox.add(Box.createRigidArea(new Dimension(5,0)));
            hBox.add(custom_panel);

            Box vBox = Box.createVerticalBox();
            vBox.add(info);
            vBox.add(butt_panel);
            vBox.add(Box.createRigidArea(new Dimension(0,5)));
            vBox.add(hBox);
                
            panel = new JPanel();
            panel.setBackground(DARK_COLOR);
            panel.add(vBox, BorderLayout.CENTER);
        }
        return panel;
    }

    private JButton[] getButtons() {
        final String[][] info = {
            {"No Icon :(", "This is a message without icon. Useful to save space in long notifications. Use it for a really long notification."},
            {"", "This is an information message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
            {"", "This is a success message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
            {"", "This is a warning message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
            {"", "This is an error message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
            {"A Really, Really Very Long Title For A Test", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec mollis. Quisque libero in sapien pharetra tincidunt. Aliquam elit ante, malesuada id, tempor eu, gravida id, odio."}
        };

        final String[] texts = {
            "No-icon Notification",
            "Info Notification",
            "Success Notification",
            "Warning Notification",
            "Error Notification",
            "Long Notification"
        };
        final MessageType[] mTypes = {
            Notification.PLAIN_MESSAGE,
            Notification.INFORMATION_MESSAGE,
            Notification.SUCCESS_MESSAGE,
            Notification.WARNING_MESSAGE,
            Notification.ERROR_MESSAGE,
            Notification.INFORMATION_MESSAGE
        };        
        final JButton[] buttons = new JButton[texts.length];
        for (int i = 0; i < texts.length; i++) {
            final JButton butt = new JButton(texts[i]);
            final String title = info[i][0];
            final String message = info[i][1];
            final MessageType type = mTypes[i];
            if (title.equals("")) {
                butt.setToolTipText("Notification.show(message"
                                    +", Notification."+type.name()+")");
            } else {
                butt.setToolTipText("Notification.show(title, message"
                                    +", Notification."+type.name()+")");
            }
            butt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (title.equals("")) {
                            Notification.show(message, type);
                        } else {
                            Notification.show(title, message, type);
                        }
                    }
                });            
            buttons[i] = butt;
        }
        
        return buttons;
    }

    private JButton[] getConfirmButtons() {
        final String[][] info = {
            {"No Icon :(", "This is a message without icon. Useful to save space in long notifications. Use it for a really long notification."},
            {"", "This is an information message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
            {"", "This is a success message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
            {"", "This is a warning message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
            {"", "This is an error message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
            {"A Really, Really Very Long Title For A Test", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec mollis. Quisque libero in sapien pharetra tincidunt. Aliquam elit ante, malesuada id, tempor eu, gravida id, odio."}
        };

        final String[] texts = {
            "No-icon Confirm Not.",
            "Info Confirm Not.",
            "Success Confirm Not.",
            "Warning Confirm Not.",
            "Error Confirm Not.",
            "Long Confirm Not."
        };
        final MessageType[] mTypes = {
            Notification.PLAIN_MESSAGE,
            Notification.INFORMATION_MESSAGE,
            Notification.SUCCESS_MESSAGE,
            Notification.WARNING_MESSAGE,
            Notification.ERROR_MESSAGE,
            Notification.CONFIRM_MESSAGE
        };
        final JButton[] buttons = new JButton[texts.length];
        for (int i = 0; i < texts.length; i++) {
            final JButton butt = new JButton(texts[i]);
            final String title = info[i][0];
            final String message = info[i][1];
            final MessageType type = mTypes[i];
            if (title.equals("")) {
                butt.setToolTipText("Notification.showConfirm(message"
                                +", Notification."+type.name()+")");
            } else {
                butt.setToolTipText("Notification.showConfirm(title, message"
                                    +", Notification."+type.name()+")");
            }
            butt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (title.equals("")) {
                            Notification.showConfirm(message, type);
                        } else {
                            Notification.showConfirm(title, message, type);
                        }
                    }
                });
            buttons[i] = butt;
        }
        
        return buttons;
    }

    private JTextField getTitleTF() {
        if (titleTF == null) {
            titleTF = new JTextField("Message");
            titleTF.setToolTipText("See the 'Launch' button to get the code snippet.");
        }
        return titleTF;
    }

    private JTextField getMessageTF() {
        if (messageTF == null) {
            messageTF = new JTextField("This is a test message.");
            messageTF.setToolTipText("See the 'Launch' button to get the code snippet.");
        }
        return messageTF;
    }

    private JComboBox<IconType> getIconComB() {
        if (iconComB == null) {
            iconComB = new JComboBox<IconType>(IconType.values());
            iconComB.setSelectedItem(Notification.INFO_ICON);
            iconComB.setToolTipText("See the 'Launch' button to get the code snippet.");
            iconComB.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        setLaunchToolTip();
                    }
                });
        }
        return iconComB;
    }

    private JComboBox<String> getTypeComB() {
        if (typeComB == null) {
            String[] types = {"show", "showConfirm"};
            typeComB = new JComboBox<String>(types);
            typeComB.setToolTipText("See the 'Launch' button to get the code snippet.");
            typeComB.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        setLaunchToolTip();
                    }
                });
        }
        return typeComB;
    }
    
    private JButton getLaunchButt() {
        if (launchButt == null) {
            launchButt = new JButton("Launch");
            launchButt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        String title = titleTF.getText();
                        String message = messageTF.getText();
                        IconType icon = (IconType)iconComB.getSelectedItem();
                        String method = (String)typeComB.getSelectedItem();
                        if (method.equals("show")) {
                            Notification.show(title, message, icon);
                        } else {
                            Notification.showConfirm(title, message, icon);
                        }
                    }
                });
            setLaunchToolTip();
        }
        return launchButt;
    }

    private void setLaunchToolTip() {
        String icon = ((IconType)getIconComB().getSelectedItem()).name();
        String method = (String)getTypeComB().getSelectedItem();
        getLaunchButt().setToolTipText("Notification."+method+"(title, message,"
                                       +" Notification."+icon+")");
    }
    
    //	====================== END METHODS =======================

    //	===================== MAIN ===============================
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        UIManager.setLookAndFeel(new javax.swing.plaf.nimbus.NimbusLookAndFeel());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                    new Main().setVisible(true);
                }
            });
    }
    
    //	====================== END MAIN ==========================
    
}
