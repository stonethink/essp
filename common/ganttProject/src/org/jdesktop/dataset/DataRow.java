/*
 * $Id: DataRow.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2005 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author rbair
 */
public class DataRow {
    /**
     * Flag indicating the status of the DataRow
     */
	public enum DataRowStatus {INSERTED, DELETED, UPDATED, UNCHANGED};
    
    //used for communicating changes to this JavaBean, especially necessary for
    //IDE tools, but also handy for any other component listening to this row
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * The DataTable that created this DataRow. This is an immutable property
     */
    private DataTable table;
    /**
     * The status of this DataRow. By default, it is set to INSERTED. It is
     * possible to change the status manually, although during certain
     * lifecycle events it is automatically changed, such as after the data
     * is saved to disk, or a change is made.
     */
	private DataRowStatus status = DataRowStatus.INSERTED;
    /**
     * The data associated with this Row. This structure implies that when a
     * DataColumn is removed from the DataTable, then each row will have to be
     * traversed and the DataColumn removed from the Map, along with the cell
     */
    private Map<DataColumn,DataCell> cells = new HashMap<DataColumn,DataCell>();
    
    /**
     * Create a new DataRow. The table creating this row must be passed in
     */
    protected DataRow(DataTable table) {
        assert table != null;
        this.table = table;
        
        //construct the cells based on the columns in the table
        //add a cell for each column
        for (DataColumn c : this.table.getColumns()) {
            DataCell cell = new DataCell();
            cell.value = c.getDefaultValue();
            cells.put(c, cell);
        }
    }
    
    /**
     * Sets the given value to the column with the given name
     *
     * @param colName
     * @param value
     */
    public void setValue(String colName, Object value) {
    	DataColumn col = table.getColumn(colName);
    	assert col != null;
        DataCell cell = cells.get(col);
        cell.setValue(value);
        if (status == DataRowStatus.UNCHANGED && cell.changed) {
            setStatus(DataRowStatus.UPDATED);
        }
    }
    
    /**
     * @return the value at the given column name
     */
    public Object getValue(String colName) {
        return getValue(table.getColumn(colName));
    }
    
    public Object getValue(DataColumn col) {
        return cells.get(col).value;
    }
    
    public DataTable getTable() {
        return table;
    }

    public DataRowStatus getStatus() {
        return status;
    }

	/**
	 * @param status
	 */
	public void setStatus(DataRowStatus status) {
        if (this.status != status) {
            DataRowStatus oldValue = this.status;
            this.status = status;
            pcs.firePropertyChange("status", oldValue, status);
        }
	}	
	
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property,  listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName,  listener);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Row #");
        buffer.append(table.indexOfRow(this));
        buffer.append(" [ ");
        int i=0;
        for (DataCell c : cells.values()) {
            buffer.append(c.value);
            if (i < cells.size() -1) {
                buffer.append(", ");
            }
            i++;
        }
        return buffer.toString();
    }
    
    private static final class DataCell {
        Object originalValue;
        Object value;
        boolean changed;
        
        public void setValue(Object newValue) {
            if (newValue != value && !changed) {
                originalValue = value;
                value = newValue;
                changed = true;
            } else if (newValue != value) {
                value = newValue;
            }
        }
    }
}