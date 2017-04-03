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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

import adbenitez.notify.core.Notification;
import adbenitez.notify.core.Notification.IconType;
import adbenitez.notify.core.Notification.MessageType;
import adbenitez.notify.core.Notification.ThemeType;
import adbenitez.notify.core.NotificationEvent;
import adbenitez.notify.core.server.ServerOSD;
import adbenitez.notify.core.util.NotifyConfig;

/**
 * A confirm notification with an "Accept" and
 * "Cancel" buttons.
 *
 */
public class DesktopConfirm extends DesktopNotify
    implements ActionListener {
    //	================= ATTRIBUTES ==============================
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final String CLASS_NAME = getClass().getSimpleName();
    
    private JButton jbAccept;
    private JButton jbCancel;
    private int option;

    private static int BUTTON_HEIGHT = 23;
    private static String ACCEPT_TEXT = "Accept";
    private static String CANCEL_TEXT = "Cancel";
    
    protected static int NOTIFICATION_HEIGHT = 121;
    
    //	================= END ATTRIBUTES ==========================
    
    //	================= CONSTRUCTORS ===========================    

    public DesktopConfirm(NotificationEvent ev) {
        super(ev);
        setSize(NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT);
        init();
    }
    
    public DesktopConfirm(NotificationEvent ev, IconType icon) {
        super(ev, icon);
        setSize(NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT);
        init();
    }

    public DesktopConfirm(NotificationEvent ev, IconType icon, Color iconColor) {
        super(ev, icon, iconColor);
        setSize(NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT);
        init();
    }
    
    //	================= END CONSTRUCTORS =======================
    
    //	===================== METHODS ============================
    
    private void init() {        
        jbAccept = new JButton(ACCEPT_TEXT);
        jbAccept.setMaximumSize(new Dimension(Short.MAX_VALUE, BUTTON_HEIGHT));
        jbAccept.setForeground(Color.white);
        jbAccept.setBorderPainted(false);
        jbAccept.setContentAreaFilled(false);
        jbAccept.setFocusPainted(false);
        jbAccept.setOpaque(true);
        
        jbAccept.addActionListener(this);
     
        jbCancel = new JButton(CANCEL_TEXT);
        jbCancel.setMaximumSize(new Dimension(Short.MAX_VALUE, BUTTON_HEIGHT));
        jbCancel.setForeground(new Color(Integer.parseInt("404040", 16)));
        jbCancel.setBackground(new Color(Integer.parseInt("E9E9E9", 16)));
        jbCancel.setBorderPainted(false);
        jbCancel.setContentAreaFilled(false);
        jbCancel.setFocusPainted(false);
        jbCancel.setOpaque(true);
        jbCancel.addActionListener(this);
     
        Box buttons_hBox = Box.createHorizontalBox();
        if (ev.getOrientation() == Notification.LEFT) {
            buttons_hBox.add(jbCancel);
            buttons_hBox.add(jbAccept);
        } else {
            buttons_hBox.add(jbAccept);
            buttons_hBox.add(jbCancel);
        }
        
        
        setAcceptButtonColor();
        addComponent(buttons_hBox);
    }
    
    private void setAcceptButtonColor() {
        MessageType messageT = getEvent().getType();
        ThemeType themeT = getTheme().getThemeType();
        if (themeT == Notification.DARK_THEME &&
            (messageT == Notification.PLAIN_MESSAGE
             || messageT == Notification.CONFIRM_MESSAGE)) {
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

    /**
     * Returns the selected option:
     * 1  if accept,
     * 0  if cancel,
     * -1 in other case.
     */
    public int getSelectedOption() {
        return option;
    }
    
    private void close() {
        ServerOSD.getInstance().remove(getNid());
    }
 
    public void actionPerformed(ActionEvent evt) {
        boolean debug = NotifyConfig.getDebug();
        if (evt.getSource().equals(jbAccept)) {
            if (debug) {
                System.out.println(CLASS_NAME+": Selected option: Accept");
            }
            option = 1;
            close();
        } else if (evt.getSource().equals(jbCancel)) {
            if (debug) {
                System.out.println(CLASS_NAME+": Selected option: Cancel");
            }
            option = 0;
            close();
        } else { // Close button
            System.out.println(CLASS_NAME+": Selected option: NONE");
            option = -1;
            super.actionPerformed(evt);
        }
    }

    //	====================== END METHODS =======================
}
