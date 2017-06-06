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

import adbenitez.notify.Notification.IconType;
import adbenitez.notify.Notification.SoundType;
import adbenitez.notify.core.font.FontController;

public class NotifyConfig {
    //	================= ATTRIBUTES ==============================

    private static NotifyConfig instance;
    private static boolean debug;
    private final String CLASS_NAME;

    private final String CLOSE_ICON;
    private String icons_pack;
    private String icons_path;
    private String sounds_path;

    private final FontController font;
    private final Font titleFontDesk;
    private final Font messageFontDesk;
    private final String libName;
    private final String libVersion;
    private final String libInfo;
    private HashMap<SoundType, AudioClip> sounds;
    private final JPanel component;

    //	================= END ATTRIBUTES ==========================

    //	================= CONSTRUCTORS ===========================

    private NotifyConfig() {
        font = FontController.getInstance();
        titleFontDesk = font.getBoldFont(20.0F); // new Font(null, 1, 20);
        messageFontDesk = font.getFont(15.0F); // new Font(null, 0, 15);

        CLASS_NAME = NotifyConfig.class.getSimpleName();
        libName = "jNotifyOSD";
        libVersion = "1.3.0";
        libInfo = "By adbenitez (asieldbenitez@gmail.com)";

        CLOSE_ICON = "CLOSE_ICON.png";
        sounds_path = "/adbenitez/notify/resources/sounds/";
        icons_path = "/adbenitez/notify/resources/icons/";
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

    public void setIconsPath(String path) {
        icons_path = path;
    }

    public String getIconsPath() {
        return icons_path;
    }

    public void setIconsPack(String pack) {
        icons_pack = pack;
    }

    public String getIconsPack() {
        return icons_pack;
    }

    public void setSoundsPath(String path) {
        sounds_path = path;
        sounds = null;
    }

    public String getSoundsPath() {
        return sounds_path;
    }

    public String getLibName() {
        return libName;
    }

    public String getLibVersion() {
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
                urlSound = sounds_path + st.name() + ".wav";
                url = getClass().getResource(urlSound);
                if (url == null) {
                    switch (st) {
                    case ERROR:
                        urlSound = sounds_path + SoundType.WARNING.name() + ".wav";
                        url = getClass().getResource(urlSound);
                        break;
                    case WARNING:
                        urlSound = sounds_path + SoundType.ERROR.name() + ".wav";
                        url = getClass().getResource(urlSound);
                        break;
                    default: // do nothing
                    }
                    if (url == null) {
                        urlSound = sounds_path + SoundType.MESSAGE.name() + ".wav";
                        url = getClass().getResource(urlSound);
                    }
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
        if (icon != IconType.NONE) {
            String path = icons_path + icons_pack + icon.name() + ".png";
            URL url = getClass().getResource(path);
            if (url == null) {
                path = icons_pack + icon.name() + ".png";
                url = getClass().getResource(path);
            }
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
            icon = colorizeNoWhite(icon, color);
        }
        return icon;
    }

    public ImageIcon getCloseIcon() {
        ImageIcon icon = null;
        String closePath = icons_path + CLOSE_ICON;
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
                int max = 255;
                pixels[i] = (((acm * r)/max) << 16) + (((acm * g)/max) << 8) + ((acm * b)/max) + (alpha << 24);
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

    private ImageIcon colorizeNoWhite(ImageIcon icon, Color color) {
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
                int max = 255;
                if (acm < 200) {
                    pixels[i] = (((acm * r)/max) << 16) + (((acm * g)/max) << 8) + ((acm * b)/max) + (alpha << 24);
                } else {
                    pixels[i] = (acm << 16) + (acm << 8) + acm + (alpha << 24);
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
