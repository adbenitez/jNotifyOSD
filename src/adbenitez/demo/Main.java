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

package adbenitez.demo;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.UIManager.LookAndFeelInfo;

import adbenitez.notify.Notification;
import adbenitez.notify.Notification.*;
import adbenitez.notify.core.util.NLabel;
import adbenitez.notify.core.util.NotifyConfig;

public class Main extends JFrame {

    //	================= ATTRIBUTES ==============================

    private static final long serialVersionUID = 1L;

    private final NotifyConfig config = NotifyConfig.getInstance();
    private final String API_NAME = config.getLibName();
    private final String API_VERSION = config.getLibVersion();

    private JMenuBar mb;
    private JPanel panel;
    private JTabbedPane tabbedPane;
    private JButton[] buttons;
    private JButton[] confirmButts;

    private JTextField titleTF;
    private JTextField messageTF;
    private JComboBox<IconType> iconComB;
    private JComboBox<String> typeComB;
    private JButton launchButt;

    private JButton customColorButt;
    private JCheckBox customColorCheckB;
    private Color customColor;

    private final String THEME_TYPE = "Notification.ThemeType";
    private final String MESSAGE_TYPE = "Notification.MessageType";
    private final String BORDER_TYPE = "Notification.BorderType";
    private final String ORIENTATION_TYPE = "Notification.OrientationType";

    private final String[][] info = {
        {"No Icon :(", "This is a message without icon. Useful to save space in long notifications. Use it for a really long notification."},
        {"", "This is an information message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
        {"", "This is a success message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
        {"", "This is a warning message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
        {"", "This is an error message. Lorem ipsum dolor sit amet, consectetuer adipiscing elit."},
        {"A Really Very Long Title For A Test", "Lorem ipsum dolor sit amet, \nconsectetuer adipiscing elit. Donec mollis.\n Quisque libero in sapien pharetra tincidunt.\n Aliquam elit ante,\n malesuada id, tempor eu, gravida id, odio."}
    };

    //	================= END ATTRIBUTES ==========================

    //	================= CONSTRUCTORS ===========================

    public Main () {
        super();
        setup();
        setTitle(API_NAME + " v" + API_VERSION + " -- Test Drive");
        pack();// setSize(400, 400);//
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Notification.showLibInfo();
    }

    //	================= END CONSTRUCTORS =======================

    //	===================== METHODS ============================

    private void setup() {
        buttons = getButtons();
        confirmButts = getConfirmButtons();
        add(getTabbedPane());

        mb = new JMenuBar();
        JMenu menu = new JMenu("Look And Feel");
		ButtonGroup bg = new ButtonGroup();
		LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
		for (int i = 0; i < infos.length; i++) {
			addItem(new LookAndFeelAction(infos[i]), bg, menu);
		}
		mb.add(menu);
        setJMenuBar(mb);
    }

        private JTabbedPane getTabbedPane() {
            if (tabbedPane == null) {
                tabbedPane = new JTabbedPane();
                tabbedPane.addTab("About", null, new JScrollPane(getAboutPanel()));
                tabbedPane.addTab("Quick Start", null, new JScrollPane(getQuickPanel()));
                tabbedPane.addTab("Options", null, new JScrollPane(getOptionsPanel()));
                tabbedPane.addTab("Custom", null, new JScrollPane(getCustomPanel()));
            }
            return tabbedPane;
        }

    private JPanel getAboutPanel() {
        JPanel aboutP = new JPanel(new BorderLayout());
        NLabel info = new NLabel("Welcome to " + API_NAME +" v" + API_VERSION
                                 +"\nBy Asiel Diaz Benitez <asieldbenitez@gmail.com>\n"
                                 +"Based on NiconNotifyOSD 2.0 from:\n"+
                                 "Frederick Adolfo Salazar Sanchez <fredefass01@gmail.com>\n\n"
                                 +API_NAME + " is licensed under the GPL v3.\n"
                                 +"Put the mouse over a component to see the code snippet associated.\n");
        info.setFont(new Font(null, 0, 16));
        aboutP.add(info);

        return aboutP;
    }

    private JPanel getQuickPanel() {
        JPanel butt_panel = new JPanel(new GridBagLayout());
        butt_panel.setBackground(null);
        TitledBorder titledB = new TitledBorder("Quick Examples:");
        butt_panel.setBorder(titledB);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        //c.weightx = 1;
        //c.weighty = 1;
        c.insets = new Insets(0, 1, 5, 1);//top, left, bott, right

        c.gridx = 0; c.gridy = 0;
        butt_panel.add(buttons[0], c);
        c.gridy = 1;
        butt_panel.add(buttons[5], c);
        c.gridy = 2;
        butt_panel.add(buttons[1], c);
        c.gridy = 3;
        butt_panel.add(buttons[2], c);
        c.gridy = 4;
        butt_panel.add(buttons[3], c);
        c.gridy = 5;
        butt_panel.add(buttons[4], c);

        c.gridx = 1; c.gridy = 0;
        butt_panel.add(Box.createRigidArea(new Dimension(5, 0)), c);

        c.gridx = 2; c.gridy = 0;
        butt_panel.add(confirmButts[0], c);
        c.gridy = 1;
        butt_panel.add(confirmButts[5], c);
        c.gridy = 2;
        butt_panel.add(confirmButts[1], c);
        c.gridy = 3;
        butt_panel.add(confirmButts[2], c);
        c.gridy = 4;
        butt_panel.add(confirmButts[3], c);
        c.gridy = 5;
        butt_panel.add(confirmButts[4], c);

        return butt_panel;
    }

    private JPanel getOptionsPanel() {
        // sounds checkBox
        final JCheckBox sounds = new JCheckBox("Play Sounds.", Notification.getSoundsStatus());
        sounds.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    boolean b = evt.getStateChange() == ItemEvent.SELECTED;
                    Notification.setSoundsStatus(b);
                    sounds.setToolTipText("Notification.setSoundsStatus("+b+")");
                }
            });
        sounds.setToolTipText("Notification.setSoundsStatus("+sounds.isSelected()+")");

        // debug checkBox
        final JCheckBox debug = new JCheckBox("Print debug messages (standard output).", Notification.getDebug());
        debug.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    boolean b = evt.getStateChange() == ItemEvent.SELECTED;
                    Notification.setDebug(b);
                    debug.setToolTipText("Notification.setDebug("+b+")");
                }
            });
        debug.setToolTipText("Notification.setDebug("+debug.isSelected()+")");

        // themes
        final JLabel themesLabel = new JLabel("Default theme: ");

        ThemeType[] themesArr = ThemeType.values();
        final JComboBox<ThemeType> themes = new JComboBox<ThemeType>(themesArr);
        themes.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    if (evt.getStateChange() == ItemEvent.SELECTED) {
                        ThemeType theme = (ThemeType)themes.getSelectedItem();
                        Notification.setDefaultTheme(theme);
                        themes.setToolTipText("Notification.setDefaultTheme("+THEME_TYPE+"."+theme.name()+")");
                        themesLabel.setToolTipText("Notification.setDefaultTheme("+THEME_TYPE+"."+theme.name()+")");
                    }
                }
            });
        ThemeType theme = Notification.getDefaultTheme();
        themes.setSelectedItem(theme);
        themes.setToolTipText("Notification.setDefaultTheme("+THEME_TYPE+"."+theme.name()+")");
        themesLabel.setToolTipText("Notification.setDefaultTheme("+THEME_TYPE+"."+theme.name()+")");
        Notification.setDefaultTheme((ThemeType)themes.getSelectedItem());

        //types
        final JLabel typesLabel = new JLabel("Default type: ");
        MessageType[] typesArr = MessageType.values();
        final JComboBox<MessageType> types = new JComboBox<MessageType>(typesArr);
        types.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    if (evt.getStateChange() == ItemEvent.SELECTED) {
                        MessageType type = (MessageType)types.getSelectedItem();
                        Notification.setDefaultType(type);
                        types.setToolTipText("Notification.setDefaultType("+MESSAGE_TYPE+"."+type.name()+")");
                        typesLabel.setToolTipText("Notification.setDefaultType("+MESSAGE_TYPE+"."+type.name()+")");
                    }
                }
            });
        MessageType mt = Notification.getDefaultType();
        types.setSelectedItem(mt);
        types.setToolTipText("Notification.setDefaultType("+MESSAGE_TYPE+"."+mt.name()+")");
        typesLabel.setToolTipText("Notification.setDefaultType("+MESSAGE_TYPE+"."+mt.name()+")");

        // border
        final JLabel borderLabel = new JLabel("Default border: ");
        BorderType[] borderArr = BorderType.values();
        final JComboBox<BorderType> border = new JComboBox<BorderType>(borderArr);
        border.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    if (evt.getStateChange() == ItemEvent.SELECTED) {
                        BorderType b = (BorderType)border.getSelectedItem();
                        Notification.setDefaultBorder(b);
                        border.setToolTipText("Notification.setDefaultBorder("+BORDER_TYPE+"."+b.name()+")");
                        borderLabel.setToolTipText("Notification.setDefaultBorder("+BORDER_TYPE+"."+b.name()+")");
                    }
                }
            });
        BorderType b = Notification.getDefaultBorder();
        border.setSelectedItem(b);
        border.setToolTipText("Notification.setDefaultBorder("+BORDER_TYPE+"."+b.name()+")");
        borderLabel.setToolTipText("Notification.setDefaultBorder("+BORDER_TYPE+"."+b.name()+")");

        // Orientation
        final JLabel orientationLabel = new JLabel("Default orientation: ");
        OrientationType[] orientationArr = OrientationType.values();
        final JComboBox<OrientationType> orientation = new JComboBox<OrientationType>(orientationArr);
        orientation.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    if (evt.getStateChange() == ItemEvent.SELECTED) {
                        OrientationType orient = (OrientationType)orientation.getSelectedItem();
                        Notification.setDefaultOrientation(orient);
                        orientation.setToolTipText("Notification.setDefaultOrientation("+ORIENTATION_TYPE+"."+orient.name()+")");
                        orientationLabel.setToolTipText("Notification.setDefaultOrientation("+ORIENTATION_TYPE+"."+orient.name()+")");
                        Notification.removeAllNotifications();
                    }
                }
            });
        OrientationType orient = Notification.getDefaultOrientation();
        orientation.setSelectedItem(orient);
        orientation.setToolTipText("Notification.setDefaultOrientation("+ORIENTATION_TYPE+"."+orient.name()+")");
        orientationLabel.setToolTipText("Notification.setDefaultOrientation("+ORIENTATION_TYPE+"."+orient.name()+")");

        //opacity
        final JLabel opacityLabel = new JLabel("Default Opacity [0-1]:");
        final JSpinner opacity = new JSpinner(new SpinnerNumberModel(Notification.getDefaultOpacity(), 0D, 1D, 0.01D));
        opacity.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    float op = ((Double)opacity.getValue()).floatValue();
                    Notification.setDefaultOpacity(op);
                    opacity.setToolTipText("Notification.setDefaultOpacity("+op+")");
                    opacityLabel.setToolTipText("Notification.setDefaultOpacity("+op+")");
                }
            });
        float op = ((Double)opacity.getValue()).floatValue();
        Notification.setDefaultOpacity(op);
        opacity.setToolTipText("Notification.setDefaultOpacity("+op+")");
        opacityLabel.setToolTipText("Notification.setDefaultOpacity("+op+")");

        // timeout
        final JLabel timeoutLabel = new JLabel("Timeout (in milliseconds, negative never closes):");
        final JSpinner timeout = new JSpinner(new SpinnerNumberModel(10000, null, 100000, 100));
        timeout.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    int t = (Integer)timeout.getValue();
                    Notification.setDefaultTimeout(t);
                    timeoutLabel.setToolTipText("Notification.setDefaultTimeout("+t+")");
                    timeout.setToolTipText("Notification.setDefaultTimeout("+t+")");
                }
            });
        int t = (Integer)timeout.getValue();
        Notification.setDefaultTimeout(t);
        timeoutLabel.setToolTipText("Notification.setDefaultTimeout("+t+")");
        timeout.setToolTipText("Notification.setDefaultTimeout("+t+")");
        // opt_panel
        JPanel opt_panel = new JPanel(new GridBagLayout());
        opt_panel.setBackground(null);
        TitledBorder titledB = new TitledBorder("Default options:");
        opt_panel.setBorder(titledB);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        //c.weighty = 1;
        c.insets = new Insets(2, 5, 2, 5);//top, left, bott, right

        c.gridy = 0; c.gridx = 0;
        opt_panel.add(sounds, c);
        c.gridy = 1;
        c.insets = new Insets(2, 5, 5, 5);//top, left, bott, right
        opt_panel.add(debug, c);

        c.insets = new Insets(2, 5, 2, 5);//top, left, bott, right
        c.gridy = 2;
        c.gridx = 0;
        opt_panel.add(themesLabel, c);
        c.gridx = 1;
        opt_panel.add(themes, c);

        c.gridy = 3;
        c.gridx = 0;
        opt_panel.add(typesLabel, c);
        c.gridx = 1;
        opt_panel.add(types, c);

        c.gridy = 4;
        c.gridx = 0;
        opt_panel.add(borderLabel, c);
        c.gridx = 1;
        opt_panel.add(border, c);

        c.gridy = 5;
        c.gridx = 0;
        opt_panel.add(orientationLabel, c);
        c.gridx = 1;
        opt_panel.add(orientation, c);

        c.gridy = 6;
        c.gridx = 0;
        opt_panel.add(opacityLabel, c);
        c.gridx = 1;
        opt_panel.add(opacity, c);

        c.gridy = 7;
        c.gridx = 0;
        opt_panel.add(timeoutLabel, c);
        c.gridx = 1;
        opt_panel.add(timeout, c);
        // ------------ OPT_PANEL --------------
        return opt_panel;
    }

    private JPanel getCustomPanel() {
        JPanel custom_panel = new JPanel(new GridBagLayout());
        custom_panel.setBackground(null);
        TitledBorder titledB = new TitledBorder("Custom Notification:");
        custom_panel.setBorder(titledB);

        JLabel label;

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;

        c.gridy = 0; c.gridx = 0;
        c.insets = new Insets(5, 5, 2, 5);//top, left, bott, right
        label = new JLabel("Title:");
        label.setToolTipText("See de 'Launch' button to get the code snippet.");
        custom_panel.add(label, c);
        c.gridx = 1;
        custom_panel.add(getTitleTF(), c);

        c.insets = new Insets(2, 5, 2, 5);//top, left, bott, right

        c.gridy = 1; c.gridx = 0;
        label = new JLabel("Message:");
        label.setToolTipText("See de 'Launch' button to get the code snippet.");
        custom_panel.add(label, c);
        c.gridx = 1;
        custom_panel.add(getMessageTF(), c);

        c.gridy = 2; c.gridx = 0;
        label = new JLabel("Icon:");
        label.setToolTipText("See de 'Launch' button to get the code snippet.");
        custom_panel.add(label, c);
        c.gridx = 1;
        custom_panel.add(getIconComB(), c);

        c.gridy = 3; c.gridx = 0;
        custom_panel.add(getCustomColorCheckB(), c);
        c.gridx = 1;
        custom_panel.add(getCustomColorButt(), c);

        c.gridy = 4; c.gridx = 0;
        label = new JLabel("Notification method:");
        label.setToolTipText("See de 'Launch' button to get the code snippet.");
        custom_panel.add(label, c);
        c.gridx = 1;
        custom_panel.add(getTypeComB(), c);

        c.gridy = 5; c.gridx = 0;
        c.weighty = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20, 5, 2, 5);//top, left, bott, right
        custom_panel.add(getLaunchButt(), c);
        return custom_panel;
    }

    private JButton[] getButtons() {
        final String[] texts = {
            "No-icon Notification",
            "Info Notification",
            "Success Notification",
            "Warning Notification",
            "Error Notification",
            "Long Notification"
        };
        final MessageType[] mTypes = {
            MessageType.PLAIN,
            MessageType.INFORMATION,
            MessageType.SUCCESS,
            MessageType.WARNING,
            MessageType.ERROR,
            MessageType.INFORMATION
        };
        final JButton[] buttons = new JButton[texts.length];
        for (int i = 0; i < texts.length; i++) {
            final JButton butt = new JButton(texts[i]);
            final String title = info[i][0];
            final String message = info[i][1];
            final MessageType type = mTypes[i];
            if (title.equals("")) {
                butt.setToolTipText("Notification.show(message"
                                    +", "+MESSAGE_TYPE+"."+type.name()+")");
            } else {
                butt.setToolTipText("Notification.show(title, message"
                                    +", "+MESSAGE_TYPE+"."+type.name()+")");
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
        final String[] texts = {
            "No-icon Confirm",
            "Info Confirm",
            "Success Confirm",
            "Warning Confirm",
            "Error Confirm",
            "Long Confirm"
        };
        final MessageType[] mTypes = {
            MessageType.PLAIN,
            MessageType.INFORMATION,
            MessageType.SUCCESS,
            MessageType.WARNING,
            MessageType.ERROR,
            MessageType.CONFIRM
        };
        final JButton[] buttons = new JButton[texts.length];
        for (int i = 0; i < texts.length; i++) {
            final JButton butt = new JButton(texts[i]);
            final String title = info[i][0];
            final String message = info[i][1];
            final MessageType type = mTypes[i];
            if (title.equals("")) {
                butt.setToolTipText("Notification.showConfirm(message"
                                +", "+MESSAGE_TYPE+"."+type.name()+")");
            } else {
                butt.setToolTipText("Notification.showConfirm(title, message"
                                    +", "+MESSAGE_TYPE+"."+type.name()+")");
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
            ComboBoxRenderer renderer = new ComboBoxRenderer();
            iconComB = new JComboBox<IconType>(IconType.values());
            iconComB.setRenderer(renderer);
            iconComB.setMaximumRowCount(4);
            iconComB.setSelectedItem(IconType.INFO);
            iconComB.setToolTipText("See the 'Launch' button to get the code snippet.");
            iconComB.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        setLaunchToolTip();
                    }
                });
        }
        return iconComB;
    }

    private JButton getCustomColorButt() {
        if (customColorButt == null) {
            customColorButt = new JButton("Pick");
            customColorButt.setEnabled(false);
            customColor = Color.white;
            customColorButt.setBackground(customColor);
            customColorButt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        Color color = JColorChooser.showDialog(Main.this, "selecciona un color", customColor);
                        if (color != null) {
                            customColor = color;
                        }
                        customColorButt.setBackground(customColor);
                        setLaunchToolTip();
                    }
                });
        }
        return customColorButt;
    }

    private JCheckBox getCustomColorCheckB() {
        if (customColorCheckB == null) {
            customColorCheckB = new JCheckBox("Change Icon Color:");
            customColorCheckB.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        if (evt.getStateChange() == ItemEvent.SELECTED) {
                            getCustomColorButt().setEnabled(true);
                        } else {
                            getCustomColorButt().setEnabled(false);
                        }
                        setLaunchToolTip();
                    }
                });
        }
        return customColorCheckB;
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
                        String message = messageTF.getText().replace("\\n", "\n");
                        IconType icon = (IconType)iconComB.getSelectedItem();
                        String method = (String)typeComB.getSelectedItem();
                        if (method.equals("show")) {
                            if (customColorCheckB.isSelected()) {
                                Notification.show(title, message, icon, customColor);
                            } else {
                                Notification.show(title, message, icon);
                            }
                        } else {
                            if (customColorCheckB.isSelected()) {
                                Notification.showConfirm(title, message, icon, customColor);
                            } else {
                                Notification.showConfirm(title, message, icon);
                            }
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
        if (getCustomColorCheckB().isSelected()) {
            getLaunchButt().setToolTipText("Notification."+method
                                           +"(title, message,"
                                           +" IconType."+icon
                                           +", new Color("+customColor.getRGB()+"))");
        } else {
            getLaunchButt().setToolTipText("Notification."+method
                                           +"(title, message,"
                                           +" IconType."+icon+")");
        }
    }

    private void addItem(Action a, ButtonGroup bg, JMenu menu) {
		JRadioButtonMenuItem item = new JRadioButtonMenuItem(a);
		bg.add(item);
		menu.add(item);
        String name = UIManager.getLookAndFeel().getName();
        if (a.getValue(AbstractAction.NAME).equals(name)) {
            item.setSelected(true);
        }
	}

    //	====================== END METHODS =======================

    //	===================== MAIN ===============================

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Main().setVisible(true);
                }
            });
    }

    //	====================== END MAIN ==========================

    @SuppressWarnings("serial")
	private class LookAndFeelAction extends AbstractAction {

		private LookAndFeelInfo info;

		public LookAndFeelAction(LookAndFeelInfo info) {
			putValue(NAME, info.getName());
			this.info = info;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				UIManager.setLookAndFeel(info.getClassName());
				SwingUtilities.updateComponentTreeUI(Main.this);
				pack();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

    class ComboBoxRenderer extends JLabel implements ListCellRenderer {

        public ComboBoxRenderer() {
            setOpaque(true);
            setVerticalAlignment(CENTER);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            IconType iconT = ((IconType)value);
            ImageIcon icon = NotifyConfig.getInstance().getIcon(iconT);
            String name = iconT.name();
            setIcon(icon);
            setText(name);
            setFont(list.getFont());

            return this;
        }
    }

}
