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

public class LightTheme extends NotificationTheme {
    //	================= ATTRIBUTES ==============================

    private static LightTheme instance;
    private final String themeName;
    private final ThemeType themeType;

    //	================= END ATTRIBUTES ==========================

    //	================= CONSTRUCTORS ===========================

    private LightTheme() {
        themeName = "Light";
        themeType = ThemeType.LIGHT;
    }

    //	================= END CONSTRUCTORS =======================

    //	===================== METHODS ============================

    public Color getBackground() {
        return new Color(Integer.parseInt("F8F8F8", 16));
    }

    public Color getTitleForeground() {
        return new Color(Integer.parseInt("313030", 16));
    }

    public Color getMessageForeground() {
        return new Color(Integer.parseInt("555555", 16));
    }

    public String getThemeName() {
        return themeName;
    }

    public ThemeType getThemeType() {
        return themeType;
    }

    public static LightTheme getInstance() {
        if (instance == null) {
            instance = new LightTheme();
        }
        return instance;
    }

    //	====================== END METHODS =======================
}
