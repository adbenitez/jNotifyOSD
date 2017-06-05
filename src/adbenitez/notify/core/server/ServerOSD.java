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
import adbenitez.notify.Notification.OrientationType;
import adbenitez.notify.gui.desktopNotify.DesktopNotify;

import java.awt.AWTError;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;

/**
 * Server that controls the
 * On Screen Display (OSD) notifications.
 */
public class ServerOSD {
    //	================= ATTRIBUTES ==============================

    private static final double serverVersion = 1.0D;
    private final String CLASS_NAME = getClass().getSimpleName();
    private static ServerOSD server;

    private HashMap<Integer, DesktopNotify> stackServer;
    private HashMap<Integer, ServerThread> stackThread;

    private int prev_vertical_position;

    //	================= END ATTRIBUTES ==========================

    //	================= CONSTRUCTORS ===========================

    /**
     * Creates the private instance of this
     * object.
     */
    protected ServerOSD() {
        if (NotifyConfig.getDebug()) {
            System.out.println(CLASS_NAME+": Starting server version "+serverVersion);
        }
        stackServer = new HashMap<Integer, DesktopNotify>();
        stackThread = new HashMap<Integer, ServerThread>();
    }

    //	================= END CONSTRUCTORS =======================

    //	===================== METHODS ============================

    /**
     * Creates a ID for a notification
     * based on the passsed notification object.
     * @param notification the notification object.
     */
    private int createNID(DesktopNotify notification) {
        boolean debug = NotifyConfig.getDebug();
        if (debug) {
            System.out.println(CLASS_NAME+": Creating nid key ...");
        }
        int nid = notification.hashCode();
        if (stackServer.containsKey(Integer.valueOf(nid))) {
            if (debug) {
                System.out.println(CLASS_NAME+": WARNING! The key already exist, removing ...");
            }
            remove(nid);
        }
        if (debug) {
            System.out.println(CLASS_NAME+": nid key is: " + nid);
        }
        return nid;
    }

    /**
     * Adds the notification to the server stack
     * and shows it.
     */
    public void send(DesktopNotify notify) {
        boolean debug = NotifyConfig.getDebug();
        int nid = createNID(notify);
        notify.setNid(nid);
        Integer nidObj = Integer.valueOf(nid);
        if (debug) {
            System.out.println(CLASS_NAME+": Stack size: " + stackServer.size());
        }
        int time = notify.getEvent().getTimeout();
        ServerThread serThread = new ServerThread(this, nid, time);
        stackServer.put(nidObj, notify);
        stackThread.put(nidObj, serThread);

        boolean showed = showNotification(notify);

        if(showed) {
            if (debug) {
                System.out.println(CLASS_NAME+": Notification Launched, nid:" + nid);
            }
            serThread.start();
        } else {
            remove(nid);
        }
    }

    public void removeAll() {
        int item_nid;
        for (Object item  : stackServer.values().toArray()) {
            item_nid = ((DesktopNotify)item).getNid();
            remove(item_nid);
        }
    }

    public void remove(int nid) {
        Integer nidObj = Integer.valueOf(nid);
        if (stackServer.containsKey(nidObj)) {
            DesktopNotify notify = stackServer.get(nidObj);
            ServerThread thrd = stackThread.get(nidObj);

            hideNotification(notify);

            thrd.interruptThread();
            stackServer.remove(nidObj);
            stackThread.remove(nidObj);

            if (NotifyConfig.getDebug()) {
                System.out.println(CLASS_NAME+": Notification with nid: " + nid + " removed successfully.");
            }
        }
    }

    private boolean showNotification(DesktopNotify notify) {
        int marginT = notify.getMarginTop();
        int marginB = notify.getMarginBottom();
        int marginR = notify.getMarginRight();
        int marginL = notify.getMarginLeft();
        int nWidth = notify.getWidth();
        int nHeight = notify.getHeight();
        Dimension screenD = getDeviceDimension();

        int h;
        OrientationType orientation = notify.getEvent().getOrientation();
        switch (orientation) {
        case LEFT:
            h = notify.getX() + marginL;
            break;
        case CENTER:
            h = screenD.width / 2 - notify.getWidth() / 2;
            int item_nid;
            for (Object item  : stackServer.values().toArray()) {
                item_nid = ((DesktopNotify)item).getNid();
                if (item_nid != notify.getNid()) {
                    remove(item_nid);
                }
            }
            break;
        default: //RIGHT
            h = screenD.width - nWidth - marginR;
        }

        int v = marginT;
        if (stackServer.size() == 1) {
                v += notify.getY(); // top inset
            } else if (stackServer.size() > 1) {
                v += prev_vertical_position;
        }

        prev_vertical_position = v + nHeight + marginB;

        notify.playSound();

        if (screenD.height >= prev_vertical_position) {
            notify.setLocation(h, v);
            notify.setVisible(true);

        } else {
            notify.setLocation(h, notify.getY() + marginT);
            prev_vertical_position = notify.getY() + nHeight + marginB;
            notify.setVisible(true);
        }
        return true;
    }


    private void hideNotification(DesktopNotify notify) {
        if (notify.isVisible()) {
            notify.dispose();
        }
    }

    private Dimension getDeviceDimension() {
        Dimension deviceDimension = null;
        try {
            Toolkit tool = Toolkit.getDefaultToolkit();
            deviceDimension = tool.getScreenSize();
        } catch (AWTError e) {
            System.out.println(CLASS_NAME+": ERROR! "+ e.getMessage());
            e.printStackTrace();
        }
        return deviceDimension;
    }

    public static ServerOSD getInstance() {
        if (server == null) {
            server = new ServerOSD();
        }
        return server;
    }

    //	====================== END METHODS =======================
}
