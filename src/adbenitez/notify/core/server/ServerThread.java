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

package adbenitez.notify.core.server;

import adbenitez.notify.core.util.NotifyConfig;
 
public class ServerThread extends Thread {
    //	================= ATTRIBUTES ==============================
    private final String CLASS_NAME = getClass().getSimpleName();
    
    private final ServerOSD server;
    private final int nid;
    private final int time;

    //	================= END ATTRIBUTES ==========================
    
    //	================= CONSTRUCTORS ===========================
    
    public ServerThread(ServerOSD server, int nid, int time) {
        this.server = server;
        this.nid = nid;
        this.time = time;
    }

    //	================= END CONSTRUCTORS =======================
    
    //	===================== METHODS ============================

    /**
     * If time >= 0 sleeps 'time' milliseconds and then remove the
     * nid from the server, else just wait to be deleted.
     */
    public void run() {
        try {
            if (NotifyConfig.getDebug()) {
                System.out.println(CLASS_NAME+": Thread started, nid:" + nid);
            }
            if (time >= 0) {
                sleep(time);
                server.remove(nid);
            } 
        } catch (InterruptedException ex) {
            // just end the execution.
        }
    }
    
    protected void interruptThread() {
        if (NotifyConfig.getDebug()) {
            System.out.println(CLASS_NAME+": Ending Thread, nid: " + nid);
        }
        interrupt();
    }

    //	====================== END METHODS =======================
}


