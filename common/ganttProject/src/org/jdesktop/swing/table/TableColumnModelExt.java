/*
 * TableColumnModelEx.java
 *
 * Created on January 14, 2005, 12:43 PM
 */

package org.jdesktop.swing.table;

import java.util.List;
import java.util.Set;
import javax.swing.table.TableColumnModel;

/**
 * An extension to the TableColumnModel that adds logic dealing with the
 * "visibilty" property of the TableColumnExt. If a column is made invisible,
 * then the ColumnModel must not return that column in subsequent calls to
 * <code>getColumnXXX</code>. In other words, it is "silently" removed from
 * the column model until it is again made visible. However, a change in the
 * visibility status of a column will trigger an event so that the underlying
 * JTable can repaint/relayout itself.
 *
 * The semantics behind removing a column and making it invisible are two
 * different things. When a column is removed, it is no longer associated
 * with the model in any way, whereas an invisible column is simply not
 * displayed. This is somewhat similar to the idea of column ordering being
 * different in the model from the view.
 *
 * @author Richard Bair
 */
public interface TableColumnModelExt extends TableColumnModel {
    /**
     * Returns a list containing all of the columns that are invisible. If
     * no columns in this model are invisible, then this will be an empty
     * list.
     */
    public Set getInvisibleColumns();
    /**
     * Returns all of the columns in the TableColumnModel, including invisible
     * ones.
     */
    public List getAllColumns();
}
