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

public abstract class NotificationTheme {

    //	===================== METHODS ============================

    public abstract String getThemeName();
    public abstract ThemeType getThemeType();
    public abstract Color getMessageForeground();
    public abstract Color getBackground();
    public abstract Color getTitleForeground();

    public Color getTitleInfoForeground() {
        return new Color(Integer.parseInt("0987CA", 16));
    }

    public Color getTitleWarningForeground() {
        return new Color(Integer.parseInt("F39C12", 16));
    }

    public Color getTitleErrorForeground() {
        return new Color(Integer.parseInt("C51E31", 16));
    }

    public Color getTitleSuccessForeground() {
        return new Color(Integer.parseInt("63A849", 16));
    }

    //	====================== END METHODS =======================

}
