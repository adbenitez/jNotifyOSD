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

package adbenitez.notify.core.font;

import adbenitez.notify.core.util.NotifyConfig;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class that load the notifications font.
 *
 */
public class FontController {
    //	================= ATTRIBUTES ==============================
    private final String CLASS_NAME = getClass().getSimpleName();
    
    private static FontController font;
    private Font ubuntuFont;
    private String urlFont = "/adbenitez/notify/core/font/font.otf";
    private InputStream in;

    //	================= END ATTRIBUTES ==========================

    //	================= CONSTRUCTORS ===========================

    private FontController() {
        try {
            in = getClass().getResourceAsStream(urlFont);
            ubuntuFont = Font.createFont(0, in);
            in.close();
        } catch (FontFormatException|IOException e) {
            if (NotifyConfig.getDebug()) {
                System.out.println(CLASS_NAME+": ERROR! " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    //	================= END CONSTRUCTORS =======================

    //	===================== METHODS ============================
    
    public Font getUbuntuFont(float size) {
        return ubuntuFont.deriveFont(size);
    }
  
    public Font getUbuntuBold(float size) {
        return ubuntuFont.deriveFont(1, size);
    }
  
    public static FontController getInstance() {
        if (font == null) {
            font = new FontController();
        }
        return font;
    }

    //	====================== END METHODS =======================    
}
