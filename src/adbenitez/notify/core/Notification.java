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

package adbenitez.notify.core;
 
import adbenitez.notify.core.server.ServerOSD;
import adbenitez.notify.core.util.NotifyConfig;
import adbenitez.notify.gui.desktopNotify.DesktopConfirm;
import adbenitez.notify.gui.desktopNotify.DesktopNotify;

/**
 * Class to show desktop notifications.
 * 
 */
public class Notification {
    //	================= ATTRIBUTES =============================
    
    public enum MessageType {
        PLAIN_MESSAGE, INFORMATION_MESSAGE, SUCCESS_MESSAGE,
        WARNING_MESSAGE, ERROR_MESSAGE, CONFIRM_MESSAGE
    }

    public enum SoundType {
        SUCCESS_SOUND, ERROR_SOUND, WARNING_SOUND, MESSAGE_SOUND
    }

    public enum IconType {
        NONE, FACEBOOK_ICON, TWITTER_ON_ICON, TWITTER_OFF_ICON,
        UPDATE_GREEN_ICON, SECURE_ICON, GOOGLE_ICON, DISK_ICON,
        GPLUS_ICON, WEATHER_ICON, WIFI_ICON, DOWNLOAD_ICON, RSS_ICON,
        UPDATE_BLUE_ICON, EVERNOTE_ICON, MESSAGE_ORANGE_ICON,
        MESSAGE_BLUE_ICON, MUSIC_ICON, SHIELD_ICON, PLUGIN_ICON,
        MAIL_RED_ICON, MAIL_BLUE_ICON, IMAGE_ICON, NOTES_ICON,
        CALENDAR_ICON, API_ICON, BAT_FULL_ICON, BAT_MED_ICON,
        BAT_DOWN_ICON, CONTACT_ICON, DISK_ORANGE_ICON, ALARM_ICON,
        INFO_ICON, SUCCESS_ICON, CONFIRM_ICON, QUESTION_ICON,
        WARNING_ICON, ERROR_ICON, MESSAGE_ICON, YAHOO_ICON
    }

    public enum ThemeType {
        DARK_THEME, LIGHT_THEME, GRAY_THEME
    }

    public enum OrientationType {
        LEFT, RIGHT, CENTER
    }

    public enum BorderType {
        NONE, ROUNDED, RAISED, ETCHED
    }

    // Message type
    public static final MessageType PLAIN_MESSAGE = MessageType.PLAIN_MESSAGE;
    public static final MessageType INFORMATION_MESSAGE = MessageType.INFORMATION_MESSAGE;
    public static final MessageType SUCCESS_MESSAGE = MessageType.SUCCESS_MESSAGE;
    public static final MessageType WARNING_MESSAGE = MessageType.WARNING_MESSAGE;
    public static final MessageType ERROR_MESSAGE = MessageType.ERROR_MESSAGE;
    public static final MessageType CONFIRM_MESSAGE = MessageType.CONFIRM_MESSAGE;
    // Sound type
    public static final SoundType SUCCESS_SOUND = SoundType.SUCCESS_SOUND;
    public static final SoundType ERROR_SOUND = SoundType.ERROR_SOUND;
    public static final SoundType WARNING_SOUND = SoundType.WARNING_SOUND;
    public static final SoundType MESSAGE_SOUND = SoundType.MESSAGE_SOUND;
    // Icon type    
    public static final IconType NO_ICON = IconType.NONE;
    public static final IconType FACEBOOK_ICON = IconType.FACEBOOK_ICON;
    public static final IconType TWITTER_ON_ICON = IconType.TWITTER_ON_ICON;
    public static final IconType TWITTER_OFF_ICON = IconType.TWITTER_OFF_ICON;
    public static final IconType UPDATE_GREEN_ICON = IconType.UPDATE_GREEN_ICON;
    public static final IconType SECURE_ICON = IconType.SECURE_ICON;
    public static final IconType GOOGLE_ICON = IconType.GOOGLE_ICON;
    public static final IconType DISK_ICON = IconType.DISK_ICON;
    public static final IconType GPLUS_ICON = IconType.GPLUS_ICON;
    public static final IconType WEATHER_ICON = IconType.WEATHER_ICON;
    public static final IconType WIFI_ICON = IconType.WIFI_ICON;
    public static final IconType DOWNLOAD_ICON = IconType.DOWNLOAD_ICON;
    public static final IconType RSS_ICON = IconType.RSS_ICON;
    public static final IconType UPDATE_BLUE_ICON = IconType.UPDATE_BLUE_ICON;
    public static final IconType EVERNOTE_ICON = IconType.EVERNOTE_ICON;
    public static final IconType MESSAGE_ORANGE_ICON = IconType.MESSAGE_ORANGE_ICON;
    public static final IconType MESSAGE_BLUE_ICON = IconType.MESSAGE_BLUE_ICON;
    public static final IconType MUSIC_ICON = IconType.MUSIC_ICON;
    public static final IconType SHIELD_ICON = IconType.SHIELD_ICON;
    public static final IconType PLUGIN_ICON = IconType.PLUGIN_ICON;
    public static final IconType MAIL_RED_ICON = IconType.MAIL_RED_ICON;
    public static final IconType MAIL_BLUE_ICON = IconType.MAIL_BLUE_ICON;
    public static final IconType IMAGE_ICON = IconType.IMAGE_ICON;
    public static final IconType NOTES_ICON = IconType.NOTES_ICON;
    public static final IconType CALENDAR_ICON = IconType.CALENDAR_ICON;
    public static final IconType API_ICON = IconType.API_ICON;
    public static final IconType BAT_FULL_ICON = IconType.BAT_FULL_ICON;
    public static final IconType BAT_MED_ICON = IconType.BAT_MED_ICON;
    public static final IconType BAT_DOWN_ICON = IconType.BAT_DOWN_ICON;
    public static final IconType CONTACT_ICON = IconType.CONTACT_ICON;
    public static final IconType DISK_ORANGE_ICON = IconType.DISK_ORANGE_ICON;
    public static final IconType ALARM_ICON = IconType.ALARM_ICON;
    public static final IconType INFO_ICON = IconType.INFO_ICON;
    public static final IconType SUCCESS_ICON = IconType.SUCCESS_ICON;
    public static final IconType CONFIRM_ICON = IconType.CONFIRM_ICON;
    public static final IconType QUESTION_ICON = IconType.QUESTION_ICON;
    public static final IconType WARNING_ICON = IconType.WARNING_ICON;
    public static final IconType ERROR_ICON = IconType.ERROR_ICON;
    public static final IconType MESSAGE_ICON = IconType.MESSAGE_ICON;
    public static final IconType YAHOO_ICON = IconType.YAHOO_ICON;
    // Theme type
    public static final ThemeType DARK_THEME = ThemeType.DARK_THEME;
    public static final ThemeType LIGHT_THEME = ThemeType.LIGHT_THEME;
    public static final ThemeType GRAY_THEME = ThemeType.GRAY_THEME;
    // Orientation type
    public static final OrientationType LEFT = OrientationType.LEFT;
    public static final OrientationType RIGHT = OrientationType.RIGHT;
    public static final OrientationType CENTER = OrientationType.CENTER;
    // Border type
    public static final BorderType NO_BORDER = BorderType.NONE;
    public static final BorderType ROUNDED = BorderType.ROUNDED;
    public static final BorderType RAISED = BorderType.RAISED;
    public static final BorderType ETCHED = BorderType.ETCHED;
    
        
    private static ServerOSD serverOSD;
    
    private static DesktopNotify notify;
    private static NotificationEvent event;
    private static DesktopConfirm confirm;

    private static ThemeType default_theme = DARK_THEME;
    private static BorderType default_border = ROUNDED;
    private static OrientationType default_orientation = RIGHT;
    private static MessageType default_type = INFORMATION_MESSAGE;
    private static boolean default_sounds = false;
    private static int default_timeout = 10000;
    private static float default_opacity = 0.95f;
    
    //	================= END ATTRIBUTES =========================
    
    //	================= CONSTRUCTORS ===========================
    
       
    //	================= END CONSTRUCTORS =======================
    
    //	===================== METHODS ============================

    public static void show(String message, MessageType type) {
        String title;
        switch (type) {
        case INFORMATION_MESSAGE:
            title = "Information";
            break;      
        case SUCCESS_MESSAGE:
            title = "Success";
            break;
        case WARNING_MESSAGE:
            title = "Warning";
            break;
        case ERROR_MESSAGE:
            title = "Error";
            break;
        case CONFIRM_MESSAGE:
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
        event = new NotificationEvent(title, message, theme,
                                      default_orientation, default_border, default_opacity,
                                      sound, type, timeout);
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
                            IconType icon, boolean sound, MessageType type, int timeout) {
        init_Server();
        event = new NotificationEvent(title, message, theme,
                                      default_orientation, default_border,
                                      default_opacity, sound, type, timeout);
        notify = new DesktopNotify(event, icon);
        serverOSD.send(notify);
    }
    
    public static void showConfirm(String message, MessageType type) {
        String title;
        switch (type) {
        case INFORMATION_MESSAGE:
            title = "Information";
            break;      
        case SUCCESS_MESSAGE:
            title = "Success";
            break;
        case WARNING_MESSAGE:
            title = "Warning";
            break;
        case ERROR_MESSAGE:
            title = "Error";
            break;
        case CONFIRM_MESSAGE:
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
        showConfirm(title, message, 
                           CONFIRM_MESSAGE, timeout);
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
        showConfirm(title, message, 
                           type, default_timeout);
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
        showConfirm(title, message,
                           default_sounds, type, timeout);
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
        showConfirm(title, message,
                           sound, default_timeout);
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
        showConfirm(title, message,
                           sound, CONFIRM_MESSAGE, timeout);
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
        showConfirm(title, message, 
                           sound, type, default_timeout);
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
        showConfirm(title, message, default_theme, 
                           sound, type, timeout);
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
        showConfirm(title, message,
                           icon, default_timeout);
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
        showConfirm(title, message,
                           icon, default_sounds, timeout);
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
        showConfirm(title, message,
                           icon, sound, default_timeout);
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
        showConfirm(title, message, default_theme,
                           icon, sound, timeout);
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
        event = new NotificationEvent(title, message, theme,
                                      default_orientation, default_border,
                                      default_opacity, sound, CONFIRM_MESSAGE, timeout);
        confirm = new DesktopConfirm(event, icon);
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

    public static void useNewIcons(boolean b) {
        NotifyConfig config = NotifyConfig.getInstance();
        config.useNewIcons(b);
    }

    public static void setDebug(boolean status) {
        NotifyConfig.setDebug(status);
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
        double version = config.getLibVersion();
        String info = config.getLibInfo();        
        event = new NotificationEvent(title,
                                      "Version: " + version
                                      + "\n" + info,
                                      default_theme,
                                      default_orientation,
                                      default_border,
                                      default_opacity,
                                      default_sounds,
                                      INFORMATION_MESSAGE, 
                                      default_timeout);
        notify = new DesktopNotify(event, Notification.API_ICON);
        serverOSD.send(notify);
    }

    
    //	====================== END METHODS =======================
}


