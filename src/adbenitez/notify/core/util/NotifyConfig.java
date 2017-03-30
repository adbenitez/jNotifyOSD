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

package adbenitez.notify.core.util;

import java.awt.Color;
import java.awt.Font;
import java.applet.AudioClip;
import java.applet.Applet;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

import adbenitez.notify.core.Notification;
import adbenitez.notify.core.Notification.IconType;
import adbenitez.notify.core.Notification.SoundType;
import adbenitez.notify.core.font.FontController;

public class NotifyConfig {
    //	================= ATTRIBUTES ==============================

    private static NotifyConfig instance;
    private static boolean debug;
    private final String CLASS_NAME;
    
    private final String SOUNDS_PATH; 
    private final String CLOSE_ICON;
    private String icons_clasic;
    private String icons_new;
    private String iconsPath;

    private final FontController font;
    private final Font titleFontDesk;
    private final Font messageFontDesk;
    private final Color fontErrorColor;
    private final Color fontWarningColor;
    private final Color fontOKColor;
    private final Color fontConfirmColor;
    private final Color fontDefaultColor;
    private final String libName;
    private final double libVersion;
    private final String libInfo;
    private HashMap<SoundType, AudioClip> sounds;
    
    //	================= END ATTRIBUTES ==========================

    //	================= CONSTRUCTORS ===========================
    
    private NotifyConfig() {
        font = FontController.getInstance();
        titleFontDesk = font.getUbuntuFont(20.0F);
        messageFontDesk = font.getUbuntuFont(15.0F);
        
        fontErrorColor = new Color(222, 60, 60);
        fontWarningColor = new Color(230, 89, 0);
        fontOKColor = new Color(116, 164, 0);
        fontConfirmColor = new Color(57, 191, 222);
        fontDefaultColor = new Color(214, 214, 214);

        CLASS_NAME = NotifyConfig.class.getSimpleName();
        libName = "jNotifyOSD";
        libVersion = 1.0D;
        libInfo = "By adbenitez based on NiconSystem CO | Icons desingned By Nitrux  MX";    
        CLOSE_ICON = "CLOSE_ICON.png";
        SOUNDS_PATH = "/adbenitez/notify/core/sound/";
        iconsPath = "/adbenitez/notify/gui/Icons/Nitrux/";
        icons_clasic = iconsPath + "Clasic/";
        icons_new = iconsPath + "Ardis/";
        debug = false;
        useNewIcons(true);
    }

    //	================= END CONSTRUCTORS =======================
    
    //	===================== METHODS ============================
    
    public Font getTitleFontDesk() {
        return titleFontDesk;
    }
 
    public Font getMessageFontDesk() {
        return messageFontDesk;
    }
 
    public Color getFontErrorColor() {
        return fontErrorColor;
    }
 
    public Color getFontWarningColor() {
        return fontWarningColor;
    }
    
    public Color getFontOKColor() {
        return fontOKColor;
    }
 
    public Color getFontConfirmColor() {
        return fontConfirmColor;
    }
 
    public Color getFontDefaultColor() {
        return fontDefaultColor;
    }

    public void setIconsPath(String path) {
        iconsPath = path;
    }
    
    public String getIconsPath() {
        return iconsPath;
    }

    public void useNewIcons(boolean b) {
        if (b) {
            iconsPath = icons_new;
        } else {
            iconsPath = icons_clasic;
        }
    }
    public String getLibName() {
        return libName;
    }
    
    public double getLibVersion() {
        return libVersion;
    }
    
    public String getLibInfo() {
        return libInfo;
    }
    
    /**
     * Sets the debug status.
     * @param status the new debug status.
     */
    public static void setDebug(boolean status) {
        debug = status;
    }

    /**
     * Returns the debug status.
     * 
     */
    public static boolean getDebug() {
        return debug;
    }
    
    public AudioClip getSound(SoundType sound) {
        if (sounds == null) {
            SoundType[] soundsT = SoundType.values();
            sounds = new HashMap<SoundType, AudioClip>();
            String urlSound;
            URL url;
            for (SoundType st : soundsT) {
                urlSound = SOUNDS_PATH + st.name() + ".wav";
                url = getClass().getResource(urlSound);
                if (url != null) {
                    sounds.put(st, Applet.newAudioClip(url));
                }
            }
        }
        return sounds.get(sound);
    }

    public ImageIcon getIcon(IconType icon) {
        ImageIcon imageIcon = null;
        if (icon != Notification.NO_ICON) {
            String path = getIconsPath() + icon.name() + ".png";
            URL url = getClass().getResource(path);
            if (url == null) {
                if (debug) {
                    System.out.println(CLASS_NAME+": WARNING! Icon not found.");
                }
            } else {
                imageIcon = new ImageIcon(url);
            }
        }
        return imageIcon;
    }

     public ImageIcon getCloseIcon() {
         String closePath = getIconsPath() + CLOSE_ICON;
         ImageIcon icon = new ImageIcon(getClass().getResource(closePath));
         return  icon;
    }

    public static NotifyConfig getInstance() {
        if (instance == null) {
            instance = new NotifyConfig();
        }
        return instance;
    }
    
    //	====================== END METHODS =======================
}


