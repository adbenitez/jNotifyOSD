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

package adbenitez.notify;

import java.awt.Color;

import adbenitez.notify.core.server.ServerOSD;
import adbenitez.notify.core.util.NotifyConfig;
import adbenitez.notify.event.NotificationEvent;
import adbenitez.notify.gui.desktopNotify.DesktopConfirm;
import adbenitez.notify.gui.desktopNotify.DesktopNotify;

/**
 * Class to show desktop notifications.
 *
 */
public class Notification {
    //	================= ATTRIBUTES =============================

    public enum MessageType {
        PLAIN, INFORMATION, SUCCESS,
        WARNING, ERROR, CONFIRM
    }

    public enum SoundType {
        SUCCESS, ERROR, WARNING, MESSAGE
    }

    public enum IconType {
        NONE, CONFIRM, QUESTION, INFO,
        SUCCESS, WARNING, ERROR,

        FACEBOOK, TWITTER, GOOGLE,
        GPLUS, RSS, YAHOO, EVERNOTE,

        MESSAGE, IMAGE, NOTES,
        UPDATE, SECURE, DISK, USB,
        WEATHER, WIFI, DOWNLOAD,
        MUSIC, PLUGIN, MAIL,
        CALENDAR, BATTERY,
        CONTACT, ALARM,
    }

    public enum ThemeType {
        DEFAULT, DARK, LIGHT, GRAY
    }

    public enum OrientationType {
        LEFT, RIGHT, CENTER
    }

    public enum BorderType {
        NONE, ROUNDED, RAISED, ETCHED
    }

    private static ServerOSD serverOSD;

    private static DesktopNotify notify;
    private static NotificationEvent event;
    private static DesktopConfirm confirm;

    private static ThemeType default_theme = ThemeType.DEFAULT;
    private static BorderType default_border = BorderType.ROUNDED;
    private static OrientationType default_orientation = OrientationType.RIGHT;
    private static MessageType default_type = MessageType.INFORMATION;
    private static boolean default_sounds = true;
    private static int default_timeout = 10000;
    private static float default_opacity = 0.95f;

    //	================= END ATTRIBUTES =========================

    //	================= CONSTRUCTORS ===========================


    //	================= END CONSTRUCTORS =======================

    //	===================== METHODS ============================

    public static void show(String message, MessageType type) {
        String title;
        switch (type) {
        case INFORMATION:
            title = "Information";
            break;
        case SUCCESS:
            title = "Success";
            break;
        case WARNING:
            title = "Warning";
            break;
        case ERROR:
            title = "Error";
            break;
        case CONFIRM:
            title = "Confirm";
            break;
        default: // PLAIN_MESSAGE
            title = "Message";
        }
        show(title, message, type, default_timeout);
    }

    /**
     * Shows a desktop notification.
     * @param title the notification title.
     * @param message the notification message.
     */
    public static void show(String title, String message) {
        show(title, message, default_timeout);
    }

    /**
     * Shows a desktop notification.
     * @param title the notification title.
     * @param message the notification message.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void show(String title, String message,
                            int timeout) {
        show(title, message, default_type, timeout);
    }

    /**
     * Shows a desktop notification.
     * @param title the notification title.
     * @param message the notification message.
     * @param type the notification type.
     */
    public static void show(String title, String message,
                            MessageType type) {
        show(title, message, type, default_timeout);
    }

    /**
     * Shows a desktop notification.
     * @param title the notification title.
     * @param message the notification message.
     * @param type the notification type.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void show(String title, String message,
                            MessageType type, int timeout) {
        show(title, message, default_sounds, type, timeout);
    }

    public static void show(String title, String message, boolean sound) {
        show(title, message, default_theme, sound, default_type, default_timeout);
    }

    public static void show(String title, String message,
                            boolean sound, int timeout) {
        show(title, message, default_theme, sound, default_type, timeout);
    }

    public static void show(String title, String message,
                            boolean sound, MessageType type) {
        show(title, message, default_theme, sound, type, default_timeout);
    }

    /**
     * Shows a desktop notification.
     * @param title the notification title.
     * @param message the notification message.
     * @param sound if true the notification will play a sound.
     * @param type the notification type.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void show(String title, String message,
                            boolean sound, MessageType type, int timeout) {
        show(title, message, default_theme, sound, type, timeout);
    }

    /**
     * Shows a desktop notification.
     * @param title the notification title.
     * @param message the notification message.
     * @param theme the notification theme.
     * @param sound if true the notification will play a sound.
     * @param type the notification type.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void show(String title, String message, ThemeType theme,
                            boolean sound, MessageType type, int timeout) {
        init_Server();
        event = new NotificationEvent(title, message, theme, default_orientation, default_border, default_opacity, sound, type, timeout);
        notify = new DesktopNotify(event);

        serverOSD.send(notify);
    }

    /**
     * Shows a desktop notification.
     * @param title the notification title.
     * @param message the notification message.
     * @param icon the notification icon.
     */
    public static void show(String title, String message,
                            IconType icon) {
        show(title, message, icon, default_timeout);
    }

    public static void show(String title, String message,
                            IconType icon, Color iconColor) {
        show(title, message, icon, iconColor, default_timeout);
    }

    /**
     * Shows a desktop notification.
     * @param title the notification title.
     * @param message the notification message.
     * @param icon the notification icon.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void show(String title, String message,
                            IconType icon, int timeout) {
        show(title, message, icon, default_sounds, timeout);
    }

    public static void show(String title, String message,
                            IconType icon, Color iconColor,
                            int timeout) {
        show(title, message, icon, iconColor, default_sounds, timeout);
    }

    /**
     * Shows a desktop notification.
     * @param title the notification title.
     * @param message the notification message.
     * @param icon the notification icon.
     * @param sound if true the notification will play a sound.
     */
    public static void show(String title, String message,
                            IconType icon, boolean sound) {
        show(title, message, icon, sound, default_timeout);
    }

    /**
     * Shows a desktop notification.
     * @param title the notification title.
     * @param message the notification message.
     * @param icon the notification icon.
     * @param sound if true the notification will play a sound.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void show(String title, String message,
                            IconType icon, boolean sound, int timeout) {
        show(title, message, default_theme, icon, sound, timeout);
    }

    public static void show(String title, String message,
                            IconType icon, Color iconColor,
                            boolean sound, int timeout) {
        show(title, message, default_theme, icon, iconColor, sound, timeout);
    }

    /**
     * Shows a desktop notification.
     * @param title the notification title.
     * @param message the notification message.
     * @param theme the notification theme.
     * @param icon the notification icon.
     * @param sound if true the notification will play a sound.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void show(String title, String message, ThemeType theme,
                            IconType icon, boolean sound, int timeout) {
        show(title, message, theme, icon, sound, default_type, timeout);
    }

    public static void show(String title, String message, ThemeType theme,
                            IconType icon, Color iconColor,
                            boolean sound, int timeout) {
        show(title, message, theme, icon, iconColor, sound, default_type, timeout);
    }

    public static void show(String title, String message, ThemeType theme,
                            IconType icon, boolean sound, MessageType type, int timeout) {
        init_Server();
        event = new NotificationEvent(title, message, theme,
                                      default_orientation, default_border,
                                      default_opacity, sound, type, timeout);
        notify = new DesktopNotify(event, icon);
        serverOSD.send(notify);
    }

    public static void show(String title, String message,
                            ThemeType theme, IconType icon,
                            Color iconColor, boolean sound,
                            MessageType type, int timeout) {
        init_Server();
        event = new NotificationEvent(title, message, theme,
                                      default_orientation, default_border,
                                      default_opacity, sound, type, timeout);
        notify = new DesktopNotify(event, icon, iconColor);
        serverOSD.send(notify);
    }

    public static void showConfirm(String message, MessageType type) {
        String title;
        switch (type) {
        case INFORMATION:
            title = "Information";
            break;
        case SUCCESS:
            title = "Success";
            break;
        case WARNING:
            title = "Warning";
            break;
        case ERROR:
            title = "Error";
            break;
        case CONFIRM:
            title = "Confirm";
            break;
        default: // PLAIN_MESSAGE
            title = "Message";
        }
        showConfirm(title, message, type, default_timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     */
    public static void showConfirm(String title, String message) {
        showConfirm(title, message, default_timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void showConfirm(String title, String message,
                                   int timeout) {
        showConfirm(title, message, MessageType.CONFIRM, timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     * @param type the notification type.
     */
    public static void showConfirm(String title, String message,
                                   MessageType type) {
        showConfirm(title, message, type, default_timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     * @param type the notification type.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void showConfirm(String title, String message,
                                   MessageType type, int timeout) {
        showConfirm(title, message, default_sounds, type, timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     * @param sound if true the notification will play a sound.
     */
    public static void showConfirm(String title, String message,
                                   boolean sound) {
        showConfirm(title, message, sound, default_timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     * @param sound if true the notification will play a sound.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void showConfirm(String title, String message,
                                   boolean sound, int timeout) {
        showConfirm(title, message, sound, MessageType.CONFIRM, timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     * @param sound if true the notification will play a sound.
     * @param type the notification type.
     */
    public static void showConfirm(String title, String message,
                                   boolean sound, MessageType type) {
        showConfirm(title, message, sound, type, default_timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     * @param sound if true the notification will play a sound.
     * @param type the notification type.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void showConfirm(String title, String message,
                                   boolean sound, MessageType type,
                                   int timeout) {
        showConfirm(title, message, default_theme, sound, type, timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons.
     * @param title the notification title.
     * @param message the notification message.
     * @param theme the notification theme.
     * @param sound if true the notification will play a sound.
     * @param type the notification type.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void showConfirm(String title, String message,
                                   ThemeType theme, boolean sound,
                                   MessageType type, int timeout) {
        init_Server();
        event = new NotificationEvent(title, message, theme,
                                      default_orientation,default_border,
                                      default_opacity, sound, type, timeout);
        confirm = new DesktopConfirm(event);
        serverOSD.send(confirm);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     * @param icon the notification icon.
     */
    public static void showConfirm(String title, String message,
                                   IconType icon) {
        showConfirm(title, message, icon, default_timeout);
    }

    public static void showConfirm(String title, String message,
                                   IconType icon, Color iconColor) {
        showConfirm(title, message, icon, iconColor, default_timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     * @param icon the notification icon.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void showConfirm(String title, String message,
                                   IconType icon, int timeout) {
        showConfirm(title, message, icon, default_sounds, timeout);
    }

    public static void showConfirm(String title, String message,
                                   IconType icon, Color iconColor,
                                   int timeout) {
        showConfirm(title, message, icon, iconColor, default_sounds, timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     * @param icon the notification icon.
     * @param sound if true the notification will play a sound.
     */
    public static void showConfirm(String title, String message,
                                   IconType icon, boolean sound) {
        showConfirm(title, message, icon, sound, default_timeout);
    }

    public static void showConfirm(String title, String message,
                                   IconType icon, Color iconColor,
                                   boolean sound) {
        showConfirm(title, message, icon, iconColor, sound, default_timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons width the default theme.
     * @param title the notification title.
     * @param message the notification message.
     * @param icon the notification icon.
     * @param sound if true the notification will play a sound.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void showConfirm(String title, String message,
                                   IconType icon, boolean sound,
                                   int timeout) {
        showConfirm(title, message, default_theme, icon, sound, timeout);
    }

    public static void showConfirm(String title, String message,
                                   IconType icon, Color iconColor,
                                   boolean sound, int timeout) {
        showConfirm(title, message, default_theme,
                    icon, iconColor, sound, timeout);
    }

    /**
     * Shows a desktop notification with an "Accept"
     * and "Cancel" buttons.
     * @param title the notification title.
     * @param message the notification message.
     * @param theme the notification theme.
     * @param icon the notification icon.
     * @param sound if true the notification will play a sound.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void showConfirm(String title, String message,
                                   ThemeType theme, IconType icon,
                                   boolean sound, int timeout) {
        init_Server();
        event = new NotificationEvent(title, message, theme, default_orientation, default_border, default_opacity, sound, MessageType.CONFIRM, timeout);
        confirm = new DesktopConfirm(event, icon);
        serverOSD.send(confirm);
    }

    public static void showConfirm(String title, String message,
                                   ThemeType theme, IconType icon,
                                   Color iconColor, boolean sound,
                                   int timeout) {
        init_Server();
        event = new NotificationEvent(title, message, theme, default_orientation, default_border, default_opacity, sound, MessageType.CONFIRM, timeout);
        confirm = new DesktopConfirm(event, icon, iconColor);
        serverOSD.send(confirm);
    }

    /**
     * Sets the default sounds status for new notifications.
     * @param status the default sounds status.
     */
    public static void setSoundsStatus(boolean status) {
        default_sounds = status;
    }

    /**
     * Returns the default sounds status.
     *
     */
    public static boolean getSoundsStatus() {
        return default_sounds;
    }

    /**
     * Sets the default timeout for new notifications.
     * @param timeout the notification timeout in milliseconds,
     * if timeout < 0 the notification don't close itself.
     */
    public static void setDefaultTimeout(int timeout) {
        default_timeout = timeout;
    }

    /**
     * Returns the default timeout for new notifications.
     *
     */
    public static int getDefaultTimeout() {
        return default_timeout;
    }

    /**
     * Sets the default theme for new notifications.
     * @param theme the theme type.
     */
    public static void setDefaultTheme(ThemeType theme) {
        default_theme = theme;
    }

    /**
     * Returns the default theme for new notifications.
     *
     */
    public static ThemeType getDefaultTheme() {
        return default_theme;
    }


    public static void setDefaultOrientation(OrientationType orientation) {
        default_orientation = orientation;
    }

    public static OrientationType getDefaultOrientation() {
        return default_orientation;
    }

    public static void setDefaultBorder(BorderType border) {
        default_border = border;
    }

    public static BorderType getDefaultBorder() {
        return default_border;
    }

    /**
     * Sets the default opacity for new notifications.
     * @param opacity the opacity [0..1].
     */
    public static void setDefaultOpacity(float opacity) {
        default_opacity = opacity;
    }

    /**
     * Returns the default opacity for new notifications.
     *
     */
    public static float getDefaultOpacity() {
        return default_opacity;
    }

    /**
     * Sets the default type for new notifications.
     * @param type the notification type.
     */
    public static void setDefaultType(MessageType type) {
        default_type = type;
    }

    /**
     * Returns the default type for new notifications.
     *
     */
    public static MessageType getDefaultType() {
        return default_type;
    }

    public static void setDebug(boolean status) {
        NotifyConfig.setDebug(status);
    }

    public static boolean getDebug() {
        return NotifyConfig.getDebug();
    }

    public static void removeAllNotifications() {
        init_Server();
        serverOSD.removeAll();
    }

    /**
     * Initializes the ServerOSD.
     *
     */
    private static void init_Server() {
        if (serverOSD == null) {
            serverOSD = ServerOSD.getInstance();
        }
    }

    /**
     * Shows a desktop notification with version
     * info about the api.
     *
     */
    public static void showLibInfo() {
        init_Server();
        NotifyConfig config = NotifyConfig.getInstance();
        String title = config.getLibName();
        String version = config.getLibVersion();
        String info = config.getLibInfo();
        event = new NotificationEvent(title,
                                      "License: GPL3    Version: " + version
                                      + "\n" + info,
                                      default_theme,
                                      default_orientation,
                                      default_border,
                                      default_opacity,
                                      default_sounds,
                                      MessageType.INFORMATION,
                                      default_timeout);
        notify = new DesktopNotify(event);
        serverOSD.send(notify);
    }


    //	====================== END METHODS =======================
}
