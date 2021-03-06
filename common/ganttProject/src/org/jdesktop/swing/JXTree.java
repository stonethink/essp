/*
 * $Id: JXTree.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import java.awt.event.ActionEvent;

import javax.swing.ActionMap;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.jdesktop.swing.decorator.ComponentAdapter;
import org.jdesktop.swing.decorator.FilterPipeline;
import org.jdesktop.swing.decorator.HighlighterPipeline;

/**
 * JXTree.
 *
 * @author Ramesh Gupta
 */
public class JXTree extends JTree {
    /** @todo Highlighters */
    private Method					conversionMethod = null;
    private final static Class[]	methodSignature = new Class[] {Object.class};
    private final static Object[]	methodArgs = new Object[] {null};

    protected FilterPipeline filters = null;
    protected HighlighterPipeline highlighters = null;
    /**
     * Constructs a <code>JXTree</code> with a sample model. The default model
     * used by this tree defines a leaf node as any node without children.
     */
    public JXTree() {
	initActions();
    }

    /**
     * Constructs a <code>JXTree</code> with each element of the specified array
     * as the child of a new root node which is not displayed. By default, this
     * tree defines a leaf node as any node without children.
     *
     * This version of the constructor simply invokes the super class version
     * with the same arguments.
     *
     * @param value an array of objects that are children of the root.
     */
    public JXTree(Object[] value) {
        super(value);
	initActions();
    }

    /**
     * Constructs a <code>JXTree</code> with each element of the specified
     * Vector as the child of a new root node which is not displayed.
     * By default, this tree defines a leaf node as any node without children.
     *
     * This version of the constructor simply invokes the super class version
     * with the same arguments.
     *
     * @param value an Vector of objects that are children of the root.
     */
    public JXTree(Vector value) {
        super(value);
	initActions();
    }

    /**
     * Constructs a <code>JXTree</code> created from a Hashtable which does not
     * display with root. Each value-half of the key/value pairs in the HashTable
     * becomes a child of the new root node. By default, the tree defines a leaf
     * node as any node without children.
     *
     * This version of the constructor simply invokes the super class version
     * with the same arguments.
     *
     * @param value a Hashtable containing objects that are children of the root.
     */
    public JXTree(Hashtable value) {
        super(value);
	initActions();
    }

    /**
     * Constructs a <code>JXTree</code> with the specified TreeNode as its root,
     * which displays the root node. By default, the tree defines a leaf node as
     * any node without children.
     *
     * This version of the constructor simply invokes the super class version
     * with the same arguments.
     *
     * @param root root node of this tree
     */
    public JXTree(TreeNode root) {
        super(root, false);
    }

    /**
     * Constructs a <code>JXTree</code> with the specified TreeNode as its root,
     * which displays the root node and which decides whether a node is a leaf
     * node in the specified manner.
     *
     * This version of the constructor simply invokes the super class version
     * with the same arguments.
     *
     * @param root root node of this tree
     * @param asksAllowsChildren if true, only nodes that do not allow children
     * are leaf nodes; otherwise, any node without children is a leaf node;
     * @see javax.swing.tree.DefaultTreeModel#asksAllowsChildren
     */
    public JXTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
		initActions();
    }

    /**
     * Constructs an instance of <code>JXTree</code> which displays the root
     * node -- the tree is created using the specified data model.
     *
     * This version of the constructor simply invokes the super class version
     * with the same arguments.
     *
     * @param newModel the <code>TreeModel</code> to use as the data model
     */
    public JXTree(TreeModel newModel) {
        super(newModel);
		initActions();
        // To support delegation of convertValueToText() to the model...
        conversionMethod = getValueConversionMethod(newModel);
	}

    public void setModel(TreeModel newModel) {
        super.setModel(newModel);
        // To support delegation of convertValueToText() to the model...
        conversionMethod = getValueConversionMethod(newModel);
    }

    private Method getValueConversionMethod(TreeModel model) {
        try {
            return model.getClass().getMethod("convertValueToText", methodSignature);
        }
        catch (NoSuchMethodException ex) {
            // not an error
        }
        return null;
    }

    public String convertValueToText(Object value, boolean selected,
                                     boolean expanded, boolean leaf,
                                     int row, boolean hasFocus) {
        // Delegate to model, if possible. Otherwise fall back to superclass...

        if(value != null) {
            if (conversionMethod == null) {
                return value.toString();
            }
            else {
                try {
                    methodArgs[0] = value;
                    return (String) conversionMethod.invoke(getModel(), methodArgs);
                }
                catch (Exception ex) {
                    // fall through
                }
            }
        }
        return "";
    }

    private void initActions() {
        // Register the actions that this class can handle.
        ActionMap map = getActionMap();
        map.put("expand-all", new Actions("expand-all"));
        map.put("collapse-all", new Actions("collapse-all"));
    }

    /**
     * A small class which dispatches actions.
     * TODO: Is there a way that we can make this static?
     */
    private class Actions extends UIAction {
        Actions(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent evt) {
            if ("expand-all".equals(getName())) {
		expandAll();
            }
            else if ("collapse-all".equals(getName())) {
                collapseAll();
            }
        }
    }


    /**
     * Collapses all nodes in the tree table.
     */
    public void collapseAll() {
        for (int i = getRowCount() - 1; i >= 0 ; i--) {
            collapseRow(i);
        }
    }

    /**
     * Expands all nodes in the tree table.
     */
    public void expandAll() {
        for (int i = 0; i < getRowCount(); i++) {
            expandRow(i);
        }
    }

    public FilterPipeline getFilters() {
        return filters;
    }

    public void setFilters(FilterPipeline pipeline) {
        /** @todo  */
        //TreeModel	model = getModel();
        //adjustListeners(pipeline, model, model);
        filters = pipeline;
    }

    public HighlighterPipeline getHighlighters() {
        return highlighters;
    }

    public void setHighlighters(HighlighterPipeline pipeline) {
        highlighters = pipeline;
    }

    protected ComponentAdapter getComponentAdapter() {
        return dataAdapter;
    }

    private final ComponentAdapter dataAdapter = new TreeAdapter(this);

    static class TreeAdapter extends ComponentAdapter {
        private final JTree tree;
        private TreePath path;
        // JTreeRenderContext requires cooperation from JTree, which must set path,
        // and call getRowForPath() to set the row in this context at appropriate times.

        /**
         * Constructs a <code>TableCellRenderContext</code> for the specified
         * target component.
         *
         * @param component the target component
         */
        public TreeAdapter(JTree component) {
            super(component);
            tree = component;
        }
        public JTree getTree() {
            return tree;
        }

        public boolean hasFocus() {
            return tree.isFocusOwner() && (tree.getLeadSelectionRow() == row);
        }

        public Object getValueAt(int row, int column) {
            return path.getLastPathComponent();
        }

        public Object getFilteredValueAt(int row, int column) {
            /** @todo Implement filtering */
            return path.getLastPathComponent();
        }

        public boolean isSelected() {
            return tree.isRowSelected(row);
        }

        public boolean isExpanded() {
            return tree.isExpanded(row);
        }

        public boolean isLeaf() {
            return tree.getModel().isLeaf(getValue());
        }

        public boolean isCellEditable(int row, int column) {
            return false;	/** @todo  */
        }

        public void setValueAt(Object aValue, int row, int column) {
            /** @todo  */
        }
    }

}
