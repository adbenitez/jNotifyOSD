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

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

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
    private String icons_pack;
    private String ICONS_PATH;

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
    private final JPanel component;
    
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
        ICONS_PATH = "/adbenitez/notify/gui/Icons/";
        icons_pack = "Ardis/";
        debug = false;
        component = new JPanel();
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
        ICONS_PATH = path;
    }
    
    public String getIconsPath() {
        return ICONS_PATH;
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
                if (url == null && st == SoundType.ERROR_SOUND) {
                    urlSound = SOUNDS_PATH + SoundType.WARNING_SOUND.name() + ".wav";
                    url = getClass().getResource(urlSound);
                }
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
            String path = getIconsPath() + icons_pack + icon.name() + ".png";
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

    public ImageIcon getIcon(IconType iconType, Color color) {
        ImageIcon icon = getIcon(iconType);
        if (icon != null && color != null) {
            icon = colorize(icon, color);
        }
        return icon;
    }
    
    public ImageIcon getCloseIcon() {
        ImageIcon icon = null;
        String closePath = getIconsPath() + CLOSE_ICON;
        URL url = getClass().getResource(closePath);
        if (url != null) {
            icon = new ImageIcon(url);
        }
        return  icon;
    }

    public ImageIcon getCloseIcon(Color color) {
        ImageIcon icon = getCloseIcon();
        if (icon != null) {
            icon = colorize(icon, color);
        }
        return icon;
    }
    
    private ImageIcon colorize(ImageIcon icon, Color color) {
        int[] pixels = new int[icon.getIconHeight() * icon.getIconWidth()];
        try {
            PixelGrabber grabber = new PixelGrabber(icon.getImage(), 0, 0, icon.getIconWidth(), 
                                                    icon.getIconHeight(), pixels, 0,
                                                    icon.getIconWidth());
            grabber.grabPixels();
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();
            
            int pixel;
            for (int i = 0, acm; i < pixels.length; i++) {
            	pixel = pixels[i];
            	int alpha = (pixel >> 24) & 0xff;
                int red   = (pixel >> 16) & 0xff;
                int green = (pixel >>  8) & 0xff;
                int blue  = pixel & 0xff;
                acm = (blue + green + red) / 3; 

                if (acm < 250) {
                    pixels[i] = new Color((acm * r)/255, (acm * g)/255,
                                          (acm * b)/255, alpha).getRGB();
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        MemoryImageSource mis = new MemoryImageSource(icon.getIconWidth(),
                                                      icon.getIconHeight(),
                                                      pixels, 0,
                                                      icon.getIconWidth());
        return new ImageIcon(component.createImage(mis));
    }
    
    public static NotifyConfig getInstance() {
        if (instance == null) {
            instance = new NotifyConfig();
        }
        return instance;
    }
    
    //	====================== END METHODS =======================
}


