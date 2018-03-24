/*
 * $Id: JNTree.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.jdnc;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.jdesktop.swing.decorator.FilterPipeline;
import org.jdesktop.swing.decorator.HighlighterPipeline;
import org.jdesktop.swing.JXTree;

/**
 * Encapsulates JXTree functionality inside a JNComponent.
 *
 * @author Ramesh Gupta
 *
 * @javabean.class
 *    displayName="Tree Component"
 *    name="JNTree"
 *    shortDesctiption="A tree control"
 *
 * @javabean.icons
 *    color16="/javax/swing/beaninfo/images/JTreeColor16.gif"
 *    color32="/javax/swing/beaninfo/images/JTreeColor32.gif"
 */
public class JNTree extends JNComponent {
    /**
     * Default constructor
     */
    public JNTree() {
        this(new DefaultTreeModel(null));
    }

    /**
     * Creates a new JNTreeTable component that presents a JTree view for
     * the specified model.
     *
     * @param model data model for the tree
     * @exception throws IllegalArgumentException if model is null
     */
    public JNTree(TreeModel model) {
        tree = new JXTree(model);
        add(new JScrollPane(tree)); // defaults to BorderLayout.CENTER
    }

    /**
     * Returns the tree for this component.
     *
     * @return the tree for this component
     */
    public JXTree getTree() {
        return tree;
    }

    /**
     * Returns the data model for the tree.
     *
     * @return the data model for the tree
     */
    public TreeModel getTreeModel() {
        return getTree().getModel();
    }

    /**
     * Sets the data model for the tree.
     *
     * @param model data model for the tree
     * @exception throws IllegalArgumentException if model is null
     *
     * @javabean.property
     *    shortDescription="sets the tree model"
     */
    public void setTreeModel(TreeModel model) {
        getTree().setModel(model);
    }

    /*
        public Color getOddRowBackground() {
            return treeTable.getOddRowBackground();
        }
        public void setOddRowBackground(Color color) {
            treeTable.setOddRowBackground(color);
        }
        public Color getEvenRowBackground() {
            return treeTable.getEvenRowBackground();
        }
        public void setEvenRowBackground(Color color) {
         treeTable.setEvenRowBackground(color);
        }
     */
    public int getRowHeight() {
        return getTree().getRowHeight();
    }

    /**
     * @javabean.property shortDescription="sets the row height"
     */
    public void setRowHeight(int value) {
        getTree().setRowHeight(value);
    }

/*
    public int getRowMargin() {
        return tree.getRowMargin();
    }

    public void setRowMargin(int value) {
        tree.setRowMargin(value);
    }
*/
    public int getSelectionMode() {
        return getTree().getSelectionModel().getSelectionMode();
    }

    /**
     * @javabean.property shortDescription="Sets the selection mode"
     */
    public void setSelectionMode(int mode) {
        getTree().getSelectionModel().setSelectionMode(mode);
    }

/*
    public boolean getShowHorizontalLines() {
        return tree.getShowHorizontalLines();
    }

    public void setShowHorizontalLines(boolean value) {
        tree.setShowHorizontalLines(value);
    }
*/

    public FilterPipeline getFilters() {
        return getTree().getFilters();
    }

    public void setFilters(FilterPipeline pipeline) {
        getTree().setFilters(pipeline);
    }

    public HighlighterPipeline getHighlighters() {
        return getTree().getHighlighters();
    }

    /**
     * @javabean.property shortDescription="Sets the highlighter pipeline"
     */
    public void setHighlighters(HighlighterPipeline pipeline) {
        getTree().setHighlighters(pipeline);
    }

    /**
     * Collapses specified row in the tree table.
     */
    public void collapseRow(int row) {
        getTree().collapseRow(row);
    }

    /**
     * Expands all nodes in the tree table.
     */
    public void expandRow(int row) {
        getTree().expandRow(row);
    }

    /**
     * Collapses all nodes in the tree table.
     */
    public void collapseAll() {
        getTree().collapseAll();
    }

    /**
     * Expands all nodes in the tree table.
     */
    public void expandAll() {
        getTree().expandAll();
    }

    protected JXTree tree = null;
}
