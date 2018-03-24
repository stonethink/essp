/*
 * $Id: DataTableListener.java,v 1.1 2006/10/10 03:50:55 mingxingzhang Exp $
 *
 * Copyright 2005 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset.event;

/**
 *
 * @author rbair
 */
public interface DataTableListener {
    /*
     * Called when the values of a single row changes (updates, etc)
     */
    public void rowChanged(RowChangeEvent evt);
    /**
     * Called when the table changes. For instance, when a DataColumn is
     * added or removed, or when a DataRow is added.
     */
    public void tableChanged(TableChangeEvent evt);
}
