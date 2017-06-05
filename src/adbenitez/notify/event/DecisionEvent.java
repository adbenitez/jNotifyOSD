/*
 * Copyright (c) 2017 Asiel Díaz Benítez.
 *
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * You should have received a copy of the GNU General Public License
 * along with this file.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package adbenitez.notify.event;

public class DecisionEvent {

    public enum Decision {
        ACCEPT, CANCEL, NONE
    }

    //	================= ATTRIBUTES ==============================

    private Decision decision;

    //	================= END ATTRIBUTES ==========================

    //	================= CONSTRUCTORS ===========================

    public DecisionEvent (Decision decision) {
        this.decision = decision;
    }

    //	================= END CONSTRUCTORS =======================

    //	===================== METHODS ============================

    public Decision getDecision() {
        return decision;
    }

    //	====================== END METHODS =======================

}
