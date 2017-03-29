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

import adbenitez.notify.core.Notification.ThemeType;
import adbenitez.notify.core.Notification.BorderType;
import adbenitez.notify.core.Notification.OrientationType;
import adbenitez.notify.core.Notification.MessageType;

/**
 * A semantic event which indicates that the 
 * Server most to show a notification.
 */
public class NotificationEvent {
    //	================= ATTRIBUTES ==============================
    
    private final String title;
    private final String text;
    private final ThemeType theme;
    private final OrientationType orientation;
    private final BorderType border; 
    private final float opacity;
    private final boolean sound;
    private final MessageType type;
    private final int timeout;

    //	================= END ATTRIBUTES ==========================
    
    //	================= CONSTRUCTORS ===========================

    
    public NotificationEvent(String title, String text, ThemeType theme,
                             OrientationType orientation, BorderType border,
                             float opacity, boolean sound, MessageType type, int timeout) {
        this.title = title;
        this.text = text;
        this.theme = theme;
        this.orientation = orientation;
        this.border = border;
        this.opacity = opacity;
        this.type = type;
        this.sound = sound;
        this.timeout = timeout;
    }
   
    //	================= END CONSTRUCTORS =======================
    
    //	===================== METHODS ============================
    
    public String getTitle() {
        return title;
    }
    
    public String getText() {
        return text;
    }

    public ThemeType getTheme() {
        return theme;
    }

    public OrientationType getOrientation() {
        return orientation;
    }
    
    public BorderType getBorder() {
        return border;
    }
    
    public float getOpacity() {
        return opacity;
    }

    public boolean isSoundEnabled() {
        return sound;
    }
    
    public MessageType getType() {
        return type;
    }

    public int getTimeout() {
        return timeout;
    }        
   
    public String toString() {
        return "NotificationEvent: title=" + title
            + ", text=" + text
            + ", theme=" + theme
            + ", sound=" + sound            
            + ", type=" + type
            + ", timeout=" + timeout;
    }

    //	====================== END METHODS =======================
}


