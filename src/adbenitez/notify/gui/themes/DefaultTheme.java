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

import javax.swing.JPanel;
import javax.swing.JLabel;

public class DefaultTheme extends NotificationTheme {

    //	================= ATTRIBUTES ==============================

    private static DefaultTheme instance;
    private final String themeName;
    private final ThemeType themeType;

    //	================= END ATTRIBUTES ==========================

    //	================= CONSTRUCTORS ===========================

    private DefaultTheme() {
        themeName = "Default";
        themeType = ThemeType.DEFAULT;
    }

    //	================= END CONSTRUCTORS =======================

    //	===================== METHODS ============================

    public Color getBackground() {
        return new Color(new JPanel().getBackground().getRGB());
    }

    public Color getTitleForeground() {
        return new Color(new JLabel().getForeground().getRGB());
    }

    public Color getMessageForeground() {
        return new Color(new JLabel().getForeground().getRGB());
    }

    public String getThemeName() {
        return themeName;
    }

    public ThemeType getThemeType() {
        return themeType;
    }

    public static DefaultTheme getInstance() {
        if (instance == null) {
            instance = new DefaultTheme();
        }
        return instance;
    }

    //	====================== END METHODS =======================

}
