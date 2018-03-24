/*
 * $Id: LinkHandler.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing;

import java.awt.Cursor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.table.TableModel;

import org.jdesktop.swing.JXTable;

import org.jdesktop.swing.data.Link;

import org.jdesktop.swing.table.TableColumnExt;

/**
 * Handles mouse actions on components with embedded Link objects. This
 * includes with table cells, labels and other controls.
 * When clicked, the Link contents will be displayed
 * in a browser if the applciation is run in an Applet or WebStart
 * application context.
 * <p>
 * TODO: Should make this general for Trees, TreeTables and lists.
 *
 * @see org.jdesktop.swing.data.Link
 * @author Mark Davidson
 */
public class LinkHandler extends MouseAdapter implements MouseMotionListener {

    private Cursor oldCursor;

    public void mouseClicked(MouseEvent evt) {
    Link link = null;
    JComponent component = (JComponent)evt.getSource();

    if (component instanceof JXTable) {
        JXTable table = (JXTable)evt.getSource();
        int col = table.columnAtPoint(evt.getPoint());

        if (isLinkColumn(table,col)) {
        int row = table.rowAtPoint(evt.getPoint());
        if (row != -1) {
            link = (Link)table.getValueAt(row, col);
        }
        }
    }
    else {
        // Value of the link is stored as a client property
        link = (Link)component.getClientProperty("jdnc.link.value");
    }

    if (link != null) {
        Application app = Application.getApp(component);
        app.showDocument(link.getURL(), link.getTarget());
        link.setVisited(true);
    }
    }

    public void mouseMoved(MouseEvent evt) {
    setCursor(evt);
    }

    public void mouseDragged(MouseEvent evt) { }

    private boolean isLinkColumn(JXTable table, int column) {
        // JW: Quickfix - the index might be -1 if 
        // hitting outside of the columns
        if (column < 0) return false;
        TableModel model = table.getModel();
        return (model.getColumnClass(table.convertColumnIndexToModel(column)) == Link.class);
    }

    private void setCursor(MouseEvent evt) {
    if (evt.getSource() instanceof JXTable) {
        JXTable table = (JXTable)evt.getSource();
        int col = table.columnAtPoint(evt.getPoint());

        if (isLinkColumn(table,col)) {
        if (oldCursor == null) {
            oldCursor = table.getCursor();
            table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        } else {
        if (oldCursor != null) {
            table.setCursor(oldCursor);
            oldCursor = null;
        }
        }
    }
    }
}
