/*
 * $Id: ResetAction.java,v 1.1 2006/10/10 03:50:57 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.jdnc.actions;

import org.jdesktop.swing.actions.BoundAction;

/**
 * Resets all components in form with current values in model.
 * @author Amy Fowler
 * @version 1.0
 */

public class ResetAction extends BoundAction {

    public ResetAction() {
        super("Reset", "reset");
    }
}
