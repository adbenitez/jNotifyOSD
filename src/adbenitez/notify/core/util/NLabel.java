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
 
import javax.swing.JTextArea;

/**
 * The class to show the notification messages.
 *
 */
public class NLabel extends JTextArea {
    //	================= ATTRIBUTES ==============================
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;	
    
    //	================= END ATTRIBUTES ==========================
    
    //	================= CONSTRUCTORS ===========================
    
    public NLabel(String text) {
        super(text, 3, 0);
        setEditable(false);
        setLineWrap(true);
        setBackground(null);
        setBorder(null);
        setWrapStyleWord(true);
        setFocusable(false);
    }

    //	================= END CONSTRUCTORS =======================
    
    //	===================== METHODS ============================
    
    
    //	====================== END METHODS =======================
}


