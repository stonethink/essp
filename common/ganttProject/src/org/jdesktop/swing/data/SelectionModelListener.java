/*
 * $Id: SelectionModelListener.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swing.data;

import java.util.EventListener;

/**
 * Listener for tracking changes in what rows are selected in a
 * SelectionModel
 * @author Richard Bair
 */
public interface SelectionModelListener extends EventListener {
	/**
	 * Called whenever the value of the selection changes
	 * @param e an object containing a description of the event
	 */
	public void selectionChanged(SelectionModelEvent e);
}
