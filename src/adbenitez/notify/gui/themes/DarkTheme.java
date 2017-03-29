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

package adbenitez.notify.gui.themes;

import adbenitez.notify.core.Notification;
import adbenitez.notify.core.Notification.ThemeType;

import java.awt.Color;

public class DarkTheme extends NotificationTheme {
    //	================= ATTRIBUTES ==============================
    
    private final String themeName;
    private final ThemeType themeType;
    
    private final Color panelBackground;
    private final Color titleForeground;
    private final Color titleInfoForeground;
    private final Color titleSuccessForeground;
    private final Color titleWarningForeground;
    private final Color titleErrorForeground;
    private final Color messageForeground;

    private static DarkTheme instance;
 
    //	================= END ATTRIBUTES ==========================
    
    //	================= CONSTRUCTORS ===========================
    
    private DarkTheme() {
        themeName = "Dark";
        themeType = Notification.DARK_THEME;
        
        panelBackground = new Color(Integer.parseInt("303030", 16));
        
        titleForeground = new Color(Integer.parseInt("FFFFFF", 16));
        titleInfoForeground = new Color(Integer.parseInt("0987CA", 16));
        titleWarningForeground = new Color(Integer.parseInt("f39c12", 16));
        titleErrorForeground = new Color(Integer.parseInt("e74c3c", 16));
        titleSuccessForeground = new Color(Integer.parseInt("4BAF4F", 16));

        messageForeground = new Color(Integer.parseInt("EEEEEE", 16));
    }
    
    //	================= END CONSTRUCTORS =======================
    
    //	===================== METHODS ============================
     
    public Color getPanelBackground() {
        return panelBackground;
    }
    
    public Color getTitleForeground() {
        return titleForeground;
    }

    public Color getTitleInfoForeground() {
        return titleInfoForeground;
    }
    
    public Color getMessageForeground() {
        return messageForeground;
    }
     
    public Color getTitleWarningForeground() {
        return titleWarningForeground;
    }
    
    public Color getTitleErrorForeground() {
        return titleErrorForeground;
    }
    
    public Color getTitleSuccessForeground() {
        return titleSuccessForeground;
    }
    
    public String getThemeName() {
        return themeName;
    }

    public ThemeType getThemeType() {
        return themeType;
    }
    
    public static DarkTheme getInstance() {
        if (instance == null) {
            instance = new DarkTheme();
        }
        return instance;
    }

    //	====================== END METHODS =======================
}


