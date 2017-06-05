/*
 * Copyright (c) 2017 Asiel Díaz Benítez <asieldbenitez@gmail.com>.
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
import java.awt.*;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

import javax.swing.*;

import adbenitez.notify.Notification.IconType;
import adbenitez.notify.Notification.SoundType;
import adbenitez.notify.event.NotificationEvent;
import adbenitez.notify.core.server.ServerOSD;
import adbenitez.notify.core.util.NLabel;
import adbenitez.notify.core.util.NotifyConfig;
import adbenitez.notify.gui.themes.*;

/**
 * A Notification dialog.
 *
 */
public class DesktopNotify extends JDialog
    implements ActionListener {
    //	================= ATTRIBUTES ==============================

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
    protected  NLabel message;
    private JLabel jlIcon;
    private IconButton jbClose;
    private JPanel panel;
    private Box top_hBox;
    private Box info_vBox;
    private Box close_vBox;
    private Box icon_vBox;
    private static int ICON_SIZE = 67;
    private static boolean use_url = false;

    protected static int NOTIFICATION_WIDTH = 380;
    protected static int MINIMUM_HEIGHT = 98;
    protected static int MARGIN_RIGHT = 5;
    protected static int MARGIN_LEFT = 5;
    protected static int MARGIN_TOP = 5;
    protected static int MARGIN_BOTTOM = 5;
    protected static int PADDING_RIGHT = 5;
    protected static int PADDING_LEFT = 5;
    protected static int PADDING_TOP = 5;
    protected static int PADDING_BOTTOM = 5;

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
        setUndecorated(true);
        setAlwaysOnTop(true);
        setResizable(false);
        init();
    }

    //	================= END CONSTRUCTORS =======================

    //	===================== METHODS ============================

    private void init() {
        setSize(NOTIFICATION_WIDTH, MINIMUM_HEIGHT);
        GraphicsEnvironment ge =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        gDevice = ge.getDefaultScreenDevice();

        jlIcon = new JLabel();

        jlTitle = new JLabel(ev.getTitle());
        jlTitle.setFont(config.getTitleFontDesk());

        jbClose = new IconButton();
        Color fore = theme.getMessageForeground();
        ImageIcon closeIcon = config.getCloseIcon(fore);
        if (closeIcon == null) {
            jbClose.setText("X");
            jbClose.setForeground(fore);
        } else {
            jbClose.setIcon(closeIcon);
        }
        jbClose.addActionListener(this);

        message = new NLabel(ev.getText());
        message.setFont(config.getMessageFontDesk());
        message.setForeground(theme.getMessageForeground());

        // layout

        icon_vBox = Box.createVerticalBox();
        icon_vBox.add(jlIcon);
        icon_vBox.add(Box.createRigidArea(new Dimension(0,PADDING_BOTTOM)));
        icon_vBox.add(Box.createVerticalGlue());

        Box title_hBox = Box.createHorizontalBox();
        title_hBox.add(jlTitle);
        title_hBox.add(Box.createHorizontalGlue());

        info_vBox = Box.createVerticalBox();
        info_vBox.add(Box.createRigidArea(new Dimension(0,PADDING_TOP)));
        info_vBox.add(title_hBox);
        info_vBox.add(message);
        info_vBox.add(Box.createRigidArea(new Dimension(0,PADDING_BOTTOM)));
        close_vBox = Box.createVerticalBox();
        close_vBox.add(Box.createRigidArea(new Dimension(0,PADDING_TOP)));
        close_vBox.add(jbClose);
        close_vBox.add(Box.createRigidArea(new Dimension(0,PADDING_BOTTOM)));
        close_vBox.add(Box.createVerticalGlue());

        top_hBox = Box.createHorizontalBox();

        panel = new JPanel(new BorderLayout());
        panel.setBackground(theme.getBackground());
        panel.add(top_hBox, BorderLayout.PAGE_START);

        setOrientation();
        setDesktopInterface();
        setIconOption();
        setOpacityProp();
        setBorderType();

        int w = getSize().width;
        w -= jlIcon.getPreferredSize().width;
        w -= jbClose.getPreferredSize().width;
        w -= (PADDING_LEFT + PADDING_RIGHT + 5);
        Dimension d = jlTitle.getPreferredSize();
        d.setSize(w, d.height);
        jlTitle.setPreferredSize(d);

        setContentPane(panel);
        adjustSize();
    }

    protected void adjustSize() {
        pack();
        Dimension d = getSize();
        if (d.height < MINIMUM_HEIGHT) {
            d.height = MINIMUM_HEIGHT;
            setSize(d);
        }//  else if (d.height > SCREEN_HEIGHT) {
        //     d.height = SCREEN_HEIGHT;
        //     setSize(d);
        // }
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
        case CONFIRM:
            icon = config.getIcon(IconType.CONFIRM);
            color = theme.getTitleForeground();
            break;
        case INFORMATION:
            icon = config.getIcon(IconType.INFO);
            color = theme.getTitleInfoForeground();
            break;
        case SUCCESS:
            icon = config.getIcon(IconType.SUCCESS);
            color = theme.getTitleSuccessForeground();
            break;
        case WARNING:
            icon = config.getIcon(IconType.WARNING);
            color = theme.getTitleWarningForeground();
            break;
        case ERROR:
            icon = config.getIcon(IconType.ERROR);
            color = theme.getTitleErrorForeground();
            break;
        default: // PLAIN:
            icon = config.getIcon(IconType.NONE);
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
        case DARK:
            theme = DarkTheme.getInstance();
            break;
        case LIGHT:
            theme = LightTheme.getInstance();
            break;
        case GRAY:
            theme = GrayTheme.getInstance();
            break;
        default: // DEFAULT
            theme = DefaultTheme.getInstance();
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

    public void playSound() {
        if (ev.isSoundEnabled()) {
            try {
                switch (ev.getType()) {
                case SUCCESS:
                    playSound(SoundType.SUCCESS);
                    break;
                case WARNING:
                    playSound(SoundType.WARNING);
                    break;
                case ERROR:
                    playSound(SoundType.ERROR);
                    break;
                default: // PLAIN or CONFIRM
                    playSound(SoundType.MESSAGE);
                }
            } catch (Throwable ex) {
                if (NotifyConfig.getDebug()) {
                    System.out.println("ERROR! " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
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

    protected void close() {
        ServerOSD.getInstance().remove(getNid());
    }

    /**
     * The action to do when the close
     * button of this notification is
     * pressed.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jbClose)) {
            close();
        }
    }

    //	====================== END METHODS =======================

    @SuppressWarnings("serial")
	private class IconButton extends JButton {

        private Icon icon;

        public IconButton () {
            setBorderPainted(false);
            setContentAreaFilled(false);
        }

        public void setIcon(Icon newIcon) {
            super.setIcon(newIcon);
            icon = newIcon;
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();
            setPreferredSize(new Dimension(width, height));
        }

        public void paintComponent(Graphics g) {
            if (icon != null) {
                icon.paintIcon(this, g, 0, 0);
            } else {
                super.paintComponent(g);
            }
        }
    }

}
