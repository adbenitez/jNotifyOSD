/*
 * Copyright (c) 2017 Asiel Díaz Benítez <asieldbenitez@gmail.com>.
 *
 * Based on NiconNotifyOSD 2.0 from:
 * Frederick Adolfo Salazar Sanchez <fredefass01@gmail.com>
 *
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * You should have received a copy of the GNU General Public License
 * along with this file.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package adbenitez.notify.gui.desktopNotify;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;

import adbenitez.notify.Notification.IconType;
import adbenitez.notify.Notification.MessageType;
import adbenitez.notify.Notification.OrientationType;
import adbenitez.notify.Notification.ThemeType;
import adbenitez.notify.core.util.NotifyConfig;
import adbenitez.notify.event.DecisionEvent;
import adbenitez.notify.event.DecisionEvent.Decision;
import adbenitez.notify.event.DecisionListener;
import adbenitez.notify.event.NotificationEvent;

/**
 * A confirm notification with an "Accept" and
 * "Cancel" buttons.
 *
 */
public class DesktopConfirm extends DesktopNotify
    implements ActionListener {
    //	================= ATTRIBUTES ==============================

    private static final long serialVersionUID = 1L;
    private final String CLASS_NAME = getClass().getSimpleName();

    private JButton jbAccept;
    private JButton jbCancel;

    private static int BUTTON_IPAD = 7;
    private static String ACCEPT_TEXT = "Accept";
    private static String CANCEL_TEXT = "Cancel";

    private LinkedList<DecisionListener> decisionListeners;

    //	================= END ATTRIBUTES ==========================

    //	================= CONSTRUCTORS ===========================

    public DesktopConfirm(NotificationEvent ev) {
        super(ev);
        init();
    }

    public DesktopConfirm(NotificationEvent ev, IconType icon) {
        super(ev, icon);
        init();
    }

    public DesktopConfirm(NotificationEvent ev, IconType icon, Color iconColor) {
        super(ev, icon, iconColor);
        init();
    }

    //	================= END CONSTRUCTORS =======================

    //	===================== METHODS ============================

    private void init() {
        decisionListeners = new LinkedList<DecisionListener>();
        addDecisionListener(new DecisionListener() {
                public void decisionPerformed(DecisionEvent ev) {
                    Decision d = ev.getDecision();
                    if (NotifyConfig.getDebug()) {
                        System.out.println(CLASS_NAME+": Selected option: "+d.name());
                    }
                }
            });
        jbAccept = new FlatButton(ACCEPT_TEXT);
        jbAccept.setForeground(Color.white);
        jbAccept.addActionListener(this);

        jbCancel = new FlatButton(CANCEL_TEXT);
        jbCancel.setForeground(new Color(Integer.parseInt("404040", 16)));
        jbCancel.setBackground(new Color(Integer.parseInt("E9E9E9", 16)));
        jbCancel.addActionListener(this);

        JPanel buttBox = new JPanel(new GridBagLayout());
        buttBox.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1; c.weighty = 1;
        c.ipady = c.ipadx = BUTTON_IPAD;

        if (ev.getOrientation() == OrientationType.LEFT) {
            c.gridx = 0;
            buttBox.add(jbCancel, c);
            c.gridx = 1;
            buttBox.add(jbAccept, c);
        } else {
            c.gridx = 0;
            buttBox.add(jbAccept, c);
            c.gridx = 1;
            buttBox.add(jbCancel, c);
        }

        setAcceptButtonColor();
        addComponent(buttBox);
        adjustSize();
    }

    private void setAcceptButtonColor() {
        MessageType messageT = getEvent().getType();
        ThemeType themeT = getTheme().getThemeType();
        if (themeT == ThemeType.DARK &&
            (messageT == MessageType.PLAIN
             || messageT == MessageType.CONFIRM)) {
            Color color = new Color(Integer.parseInt("707070", 16));
            jbAccept.setBackground(color);
        } else {
            jbAccept.setBackground(getForegroundTitle());
        }
    }

    public JButton getAcceptButton() {
        return jbAccept;
    }

    public JButton getCancelButton() {
        return jbCancel;
    }

    public void actionPerformed(ActionEvent evt) {
        Decision d;
        if (evt.getSource().equals(jbAccept)) {
            d = Decision.ACCEPT;
            close();
        } else if (evt.getSource().equals(jbCancel)) {
            d = Decision.CANCEL;
            close();
        } else { // Close button
            d = Decision.NONE;
            super.actionPerformed(evt);
        }
        notifyListeners(new DecisionEvent(d));
    }

    public void addDecisionListener(DecisionListener l) {
        decisionListeners.add(l);
    }

    private void notifyListeners(DecisionEvent ev) {
        for (DecisionListener l : decisionListeners) {
            l.decisionPerformed(ev);
        }
    }

    //	====================== END METHODS =======================

    @SuppressWarnings("serial")
	private class FlatButton extends JButton {

        public FlatButton (String text) {
            super(text);
            setBorder(null);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setOpaque(true);
        }
    }

}
