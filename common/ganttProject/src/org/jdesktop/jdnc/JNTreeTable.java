/*
 * $Id: JNTreeTable.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.jdnc;

import javax.swing.Icon;
import javax.swing.JScrollPane;

import org.jdesktop.swing.decorator.FilterPipeline;
import org.jdesktop.swing.JXTreeTable;
import org.jdesktop.swing.treetable.DefaultTreeTableModel;
import org.jdesktop.swing.treetable.TreeTableModel;

/**
 * Encapsulates JXTreeTable functionality inside a JNComponent.
 *
 * @author Ramesh Gupta
 *
 * @javabean.class
 *    displayName="Tree Table Component"
 *    name="JNTreeTable"
 *    shortDesctiption="A tree table control"
 */
public class JNTreeTable extends JNTable {
    /**
     * Default constructor
     */
    public JNTreeTable() {
        this(new DefaultTreeTableModel());
    }

    /**
     * Creates a new JNTreeTable component that presents a JXTreeTable view for
     * the specified model.
     *
     * @param model data model for the tree table
     * @exception throws IllegalArgumentException if model is null
     */
    public JNTreeTable(TreeTableModel model) {
        super(new JXTreeTable(model));
    }

    public JNTreeTable(JXTreeTable jxTable)
    {
        super(jxTable);
    }

    /**
     * Returns the tree table for this component.
     *
     * @return the tree table for this component
     */
    public JXTreeTable getTreeTable() {
        return (JXTreeTable) jxtable;
    }

    /**
     * Returns the data model for the tree table.
     *
     * @return the data model for the tree table
     */
    public TreeTableModel getTreeTableModel() {
        return ((JXTreeTable) jxtable).getTreeTableModel();
    }

    /**
     * Sets the data model for the tree table.
     *
     * @param model data model for the tree table
     * @exception throws IllegalArgumentException if model is null
     *
     * @javabean.property shorDescription="Set the tree table model"
     */
    public void setTreeTableModel(TreeTableModel model) {
        ((JXTreeTable) jxtable).setTreeTableModel(model);
    }

    public void setFilters(FilterPipeline pipeline) {
        // can't filter hierarchical structures
    }

    public void setCollapsedIcon(Icon icon) {
        ((JXTreeTable) jxtable).setCollapsedIcon(icon);
    }

    public void setExpandedIcon(Icon icon) {
        ((JXTreeTable) jxtable).setExpandedIcon(icon);
    }

    public void setOpenIcon(Icon icon) {
        ((JXTreeTable) jxtable).setOpenIcon(icon);
    }

    public void setClosedIcon(Icon icon) {
        ((JXTreeTable) jxtable).setClosedIcon(icon);
    }

    public void setLeafIcon(Icon icon) {
        ((JXTreeTable) jxtable).setLeafIcon(icon);
    }
    /**
     * Collapses specified row in the tree table.
     */
    public void collapseRow(int row) {
        ((JXTreeTable) jxtable).collapseRow(row);
    }

    /**
     * Expands all nodes in the tree table.
     */
    public void expandRow(int row) {
        ((JXTreeTable) jxtable).expandRow(row);
    }

    /**
     * Collapses all nodes in the tree table.
     */
    public void collapseAll() {
        ((JXTreeTable) jxtable).collapseAll();
    }

    /**
     * Expands all nodes in the tree table.
     */
    public void expandAll() {
        ((JXTreeTable) jxtable).expandAll();
    }
}
