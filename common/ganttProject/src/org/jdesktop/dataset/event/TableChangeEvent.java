/*
 * $Id: TableChangeEvent.java,v 1.1 2006/10/10 03:50:55 mingxingzhang Exp $
 *
 * Copyright 2005 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset.event;

import java.util.EventObject;
import org.jdesktop.dataset.DataTable;

/**
 *
 * @author rbair
 */
public class TableChangeEvent extends EventObject {
    
    /** Creates a new instance of TableChangeEvent */
    public TableChangeEvent(DataTable source) {
        super(source);
    }
    
}
