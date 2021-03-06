/*
 * $Id: TreeTableModel.java,v 1.1 2006/10/10 03:51:01 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.treetable;	// temporary package

import javax.swing.tree.TreeModel;

public interface TreeTableModel extends TreeModel {
    public Class getColumnClass(int column);
    public int getColumnCount();
    public String getColumnName(int column);
    public Object getValueAt(Object node, int column);
    public boolean isCellEditable(Object node, int column);
    public void setValueAt(Object value, Object node, int column);
}