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

package adbenitez.notify.gui.themes;

import adbenitez.notify.Notification.ThemeType;

import java.awt.Color;

public class DarkTheme extends NotificationTheme {
    //	================= ATTRIBUTES ==============================

    private static DarkTheme instance;
    private final String themeName;
    private final ThemeType themeType;

    //	================= END ATTRIBUTES ==========================

    //	================= CONSTRUCTORS ===========================

    private DarkTheme() {
        themeName = "Dark";
        themeType = ThemeType.DARK;
    }

    //	================= END CONSTRUCTORS =======================

    //	===================== METHODS ============================

    public Color getBackground() {
        return new Color(Integer.parseInt("303030", 16));
    }

    public Color getTitleForeground() {
        return new Color(Integer.parseInt("FFFFFF", 16));
    }

    public Color getMessageForeground() {
        return new Color(Integer.parseInt("EEEEEE", 16));
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
