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

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import adbenitez.notify.core.Notification;
import adbenitez.notify.core.Notification.IconType;
import adbenitez.notify.core.Notification.SoundType;
import adbenitez.notify.core.NotificationEvent;
import adbenitez.notify.core.server.ServerOSD;
import adbenitez.notify.core.util.NLabel;
import adbenitez.notify.core.util.NotifyConfig;
import adbenitez.notify.gui.themes.DarkTheme;
import adbenitez.notify.gui.themes.GrayTheme;
import adbenitez.notify.gui.themes.LightTheme;
import adbenitez.notify.gui.themes.NotificationTheme;

/**
 * A Notification dialog.
 *
 */
public class DesktopNotify extends JDialog
    implements ActionListener {
    //	================= ATTRIBUTES ==============================
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final String CLASS_NAME = getClass().getSimpleName();
    private GraphicsDevice gDevice;
    
    protected final NotificationEvent ev;
    private int nid;
    private IconType icon;
    private Color iconColor;
    private NotificationTheme theme;
    private String urlIcon;
    private final NotifyConfig config;
    
    protected JLabel jlTitle;
    protected  NLabel jlMessage;    
    private JLabel jlIcon;
    private JButton jbClose;
    private JPanel panel;
    private Box top_hBox;
    private Box info_vBox;
    private Box close_vBox;
    private Box icon_vBox;
    private static int ICON_SIZE = 67;
    private static boolean use_url = false;
    
    protected static int NOTIFICATION_WIDTH = 380;
    protected static int NOTIFICATION_HEIGHT = 98;
    protected static int MARGIN_RIGHT = 5;
    protected static int MARGIN_LEFT = 5;
    protected static int MARGIN_TOP = 5;
    protected static int MARGIN_BOTTOM = 5;
    protected static int PADDING_RIGHT = 5;
    protected static int PADDING_LEFT = 5;
    protected static int PADDING_TOP = 5;
    protected static int PADDING_BOTTOM = 0;
    
    //	================= END ATTRIBUTES ==========================
    
    //	================= CONSTRUCTORS ===========================

    /**
     * Creates a desktop notification.
     * @param ev the notification event.
     */
    public DesktopNotify(NotificationEvent ev) {
        this(ev, (IconType)null);
    }

    /**
     * Creates a desktop notification.
     * @param ev the notification event.
     * @param icon the notification icon.
     */
    public DesktopNotify(NotificationEvent ev, IconType icon) {
        this.ev = ev;
        this.icon = icon;
        config = NotifyConfig.getInstance();
        use_url = false;
        
        setNotificationTheme();
        setSize(NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setResizable(false);
        init();
    }

    public DesktopNotify(NotificationEvent ev, IconType icon, Color iconColor) {
        this.ev = ev;
        this.icon = icon;
        this.iconColor = iconColor;
        config = NotifyConfig.getInstance();
        use_url = false;
        
        setNotificationTheme();
        setSize(NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setResizable(false);
        init();
    }
    
    /**
     * Creates a desktop notification.
     * @param ev the notification event.
     * @param url the icon url.
     */
    public DesktopNotify(NotificationEvent ev, String url) {
        this.ev = ev;
        use_url = true;
        urlIcon = url; 
        config = NotifyConfig.getInstance();
        
        setNotificationTheme();
        setSize(NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setResizable(false);
        init();        
    }

    //	================= END CONSTRUCTORS =======================
    
    //	===================== METHODS ============================
    
    private void init() {        
        GraphicsEnvironment ge =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        gDevice = ge.getDefaultScreenDevice();
        
        jlIcon = new JLabel();

        jlTitle = new JLabel(ev.getTitle());
        jlTitle.setFont(config.getTitleFontDesk());
        
        jbClose = new JButton();
        jbClose.setBorderPainted(false);
        jbClose.setContentAreaFilled(false);
        Color fore = theme.getMessageForeground();
        ImageIcon closeIcon = config.getCloseIcon(fore);
        if (closeIcon == null) {
            jbClose.setText("X");
            jbClose.setForeground(fore);
        } else {
            jbClose.setIcon(closeIcon); 
            int width = closeIcon.getIconWidth();
            int height = closeIcon.getIconHeight();
            jbClose.setPreferredSize(new Dimension(width, height));
        }
        jbClose.addActionListener(this);
        
        jlMessage = new NLabel(ev.getText());
        jlMessage.setFont(config.getMessageFontDesk());
        jlMessage.setForeground(theme.getMessageForeground());
        
        // layout

        icon_vBox = Box.createVerticalBox();
        icon_vBox.add(jlIcon);
        icon_vBox.add(Box.createVerticalGlue());
        
        Box title_hBox = Box.createHorizontalBox();
        title_hBox.add(jlTitle);
        title_hBox.add(Box.createHorizontalGlue());
        
        info_vBox = Box.createVerticalBox();
        info_vBox.add(Box.createRigidArea(new Dimension(0,PADDING_TOP)));
        info_vBox.add(title_hBox);
        info_vBox.add(jlMessage);
        
        close_vBox = Box.createVerticalBox();
        close_vBox.add(Box.createRigidArea(new Dimension(0,PADDING_TOP)));
        close_vBox.add(jbClose);
        close_vBox.add(Box.createVerticalGlue());

        top_hBox = Box.createHorizontalBox();

        panel = new JPanel(new BorderLayout());
        panel.setBackground(theme.getPanelBackground());
        panel.add(top_hBox, BorderLayout.PAGE_START);
        
        setOrientation();
        setDesktopInterface();
        setIconOption();
        setOpacityProp();
        setBorderType();
        
        int w = getSize().width;
        w -= jlIcon.getPreferredSize().width;
        w -= jbClose.getPreferredSize().width;
        w -= (PADDING_LEFT + PADDING_RIGHT+5);
        Dimension d = jlTitle.getPreferredSize();
        d.setSize(w, d.height);
        jlTitle.setPreferredSize(d);
        
        d = jlMessage.getPreferredSize();
        d.setSize(w, d.height);
        jlMessage.setPreferredSize(d);
        
        setContentPane(panel);
    }
    
    /**
     * Retuns the event associated this notification.
     *
     */
    public NotificationEvent getEvent() {
        return ev;
    }

    /**
     * Retuns the nid of this notification.
     *
     */
    public int getNid() {
        return nid;
    }

    /**
     * Sets the nid of this notification.
     *
     */
    public void setNid(int nid) {
        this.nid = nid;
    }
  
    /**
     * Adds a component to this notification.
     *
     */
    public void addComponent(Component component) {
        panel.add(component, BorderLayout.PAGE_END);
    }

    /**
     * Sets the icon of this notification.
     *
     */
    private void setNotifIcon(ImageIcon icon) {
        jlIcon.setIcon(icon);
    }

    private void setOrientation() {
        switch (ev.getOrientation()) {
        case LEFT:
            top_hBox.add(Box.createRigidArea(new Dimension(PADDING_LEFT,0)));
            top_hBox.add(close_vBox);
            top_hBox.add(Box.createHorizontalGlue());
            top_hBox.add(info_vBox);
            top_hBox.add(Box.createRigidArea(new Dimension(PADDING_RIGHT,0)));
            top_hBox.add(icon_vBox);
            panel.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            break;
        case CENTER:
            top_hBox.add(icon_vBox);
            top_hBox.add(Box.createRigidArea(new Dimension(PADDING_LEFT,0)));
            top_hBox.add(info_vBox);
            top_hBox.add(Box.createHorizontalGlue());
            top_hBox.add(close_vBox);
            top_hBox.add(Box.createRigidArea(new Dimension(PADDING_RIGHT,0)));
            panel.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            break;
        default: // RIGHT
            top_hBox.add(icon_vBox);
            top_hBox.add(Box.createRigidArea(new Dimension(PADDING_LEFT,0)));
            top_hBox.add(info_vBox);
            top_hBox.add(Box.createHorizontalGlue());
            top_hBox.add(close_vBox);
            top_hBox.add(Box.createRigidArea(new Dimension(PADDING_RIGHT,0)));
            panel.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        }
    }
    
    /**
     * Sets the icon and font for this notification
     * based on the message type.
     */
    private void setDesktopInterface() {
        ImageIcon icon;
        Color color;
        switch (ev.getType()) {        
        case CONFIRM_MESSAGE:
            icon = config.getIcon(Notification.CONFIRM_ICON);
            color = theme.getTitleForeground();
            break;
        case INFORMATION_MESSAGE:
            icon = config.getIcon(Notification.INFO_ICON);
            color = theme.getTitleInfoForeground();  
            break;
        case SUCCESS_MESSAGE:
            icon = config.getIcon(Notification.SUCCESS_ICON);
            color = theme.getTitleSuccessForeground();
            break;
        case WARNING_MESSAGE:
            icon = config.getIcon(Notification.WARNING_ICON);
            color = theme.getTitleWarningForeground();
            break;
        case ERROR_MESSAGE:
            icon = config.getIcon(Notification.ERROR_ICON);
            color = theme.getTitleErrorForeground();
            break;        
        default: // PLAIN_MESSAGE:
            icon = config.getIcon(Notification.NO_ICON);
            color = theme.getTitleForeground();
        }
        setNotifIcon(icon);
        jlTitle.setForeground(color);
    }

    /**
     * Sets the icon of the notification based
     * on the use_url flag (to load a custom icon)
     * or the icon attribute (to load a predefined icon).
     */
    private void setIconOption() {
        if (use_url) {
            File file = new File(urlIcon);
            if (file.exists()) {
                ImageIcon prevIcon = new ImageIcon(file.getPath());
                Image scaled = prevIcon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT);
                setNotifIcon(new ImageIcon(scaled));
            }
        } else if (icon != null) {
            setNotifIcon(config.getIcon(icon, iconColor));
        }
    }

        private void setOpacityProp() {        
        if (gDevice.isWindowTranslucencySupported(WindowTranslucency.TRANSLUCENT)) {
            setOpacity(ev.getOpacity());
        } else {
            if (NotifyConfig.getDebug()) {
                System.out.println(CLASS_NAME+": Your System don't support the Opacity feature.");
            }
        }
    }
    
    private void setBorderType() {
        switch (ev.getBorder()) {
        case ROUNDED:
            if (gDevice.isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSPARENT)) {
                addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                            setShape(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(), 15, 15));
                        }
                    });
            } else {
                if (NotifyConfig.getDebug()) {
                    System.out.println(CLASS_NAME+": Your System don't support this Border feature.");

                }
            }
            break;
        case RAISED:
            panel.setBorder(BorderFactory.createRaisedBevelBorder());
            break;
        case ETCHED:
            panel.setBorder(BorderFactory.createEtchedBorder());
            break;
        default: // NONE
            // do nothing
        }
    }

    /**
     * Sets the notification theme of
     * type defined in the event associated.
     */
    private void setNotificationTheme() {
        switch (ev.getTheme())  {
        case LIGHT_THEME:
            theme = LightTheme.getInstance();
            break;
        case GRAY_THEME:
            theme = GrayTheme.getInstance();
            break;
        default: // DARK_THEME
            theme = DarkTheme.getInstance();
        }
    }

    /**
     * Returns the theme of this notification.
     *
     */
    public NotificationTheme getTheme() {
        return theme;
    }

    /**
     * Returns the title foreground color
     * for this notification.
     */
    public Color getForegroundTitle() {
        return jlTitle.getForeground();
    }

    public int getMarginRight() {
        return MARGIN_RIGHT;
    }

    public int getMarginLeft() {
        return MARGIN_LEFT;
    }

    public int getMarginTop() {
        return MARGIN_TOP;
    }
    
    public int getMarginBottom() {
        return MARGIN_BOTTOM;
    }
    
    /**
     * Plays a sound based on
     * the type parameter.
     * @param type the sound type defined in
     * Notification class.
     */
    public void playSound(SoundType type) {
        final AudioClip aClip = config.getSound(type);
        if (aClip != null) {
            aClip.play();
        }
    }

    /**
     * The action to do when the close
     * button of this notification is
     * pressed.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jbClose)) {
            ServerOSD.getInstance().remove(getNid());
        }
    }

    //	====================== END METHODS =======================
}


