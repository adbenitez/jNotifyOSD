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

import adbenitez.notify.core.Notification.ThemeType;

import java.awt.Color;

public abstract class NotificationTheme {
    //	===================== METHODS ============================
    
    public abstract Color getPanelBackground();
    
    public abstract Color getTitleForeground();
    public abstract Color getTitleInfoForeground();
    public abstract Color getTitleWarningForeground();
    public abstract Color getTitleErrorForeground();
    public abstract Color getTitleSuccessForeground();
    
    public abstract Color getMessageForeground();
    
    public abstract String getThemeName();
    public abstract ThemeType getThemeType();

    //	====================== END METHODS =======================
}