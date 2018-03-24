/*
 * $Id: ValueChangeListener.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.data;

import java.util.EventListener;

/**
 * Listener interface which receives events when field values change
 * within a DataModel object.
 *
 * @see DataModel#addValueChangeListener
 *
 * @author Amy Fowler
 * @version 1.0
 */
public interface ValueChangeListener extends EventListener {
    /**
     * Invoked when the value of a named data field changes
     * @param e ValueChangeEvent describing the event
     */
    void valueChanged(ValueChangeEvent e);
}
