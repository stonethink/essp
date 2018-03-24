/*
 * $Id: DataTable.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2005 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.jdesktop.dataset.NameGenerator;
import org.jdesktop.dataset.event.DataTableListener;
import org.jdesktop.dataset.event.TableChangeEvent;



/**
 *
 * @author rbair
 */
public class DataTable {
    /**
     * The Logger
     */
    private static final Logger LOG = Logger.getLogger(DataTable.class.getName());
    
    //protected for testing
    protected static final String DEFAULT_NAME_PREFIX = "DataTable";
    private static final NameGenerator NAMEGEN = new NameGenerator(DEFAULT_NAME_PREFIX);

    //used for communicating changes to this JavaBean, especially necessary for
    //IDE tools, but also handy for any other component listening to this table
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * A reference to the DataSet to which this DataTable belongs
     */
    private DataSet dataSet;
    /**
     * The DataProvider that is used to manage this DataTables data. It is
     * technically possible for a DataProvider to populate a DataTable to which
     * it is not associated, but the results are undefined
     */
    private DataProvider dataProvider;
    /**
     * The name of this DataTable
     */
    private String name;
    /**
     * Mapping of DataColumn.name to DataColumn. Extremely useful for
     * discovering the column object associated with a name. The resolver code
     * responsible for locating a particular data cell will first convert the
     * column name to a DataColumn, and then use the DataColumn in the hash for
     * locating the data. This is done so that:
     * <ol>
     * <li>The lookup is constant time (or close to it)</li>
     * <li>Avoid looping through a columns list looking for the column</li>
     * <li>More efficient handling if the column name changes in a large table,
     * which would not be possible if the column name was used in the hash</li>
     * </ol>
     */
	protected Map<String,DataColumn> columns = new LinkedHashMap<String,DataColumn>();
    /**
     * The list of DataRows in this DataTable. The DataRow actually contains the
     * data at the various cells.
     */
	protected List<DataRow> rows = new ArrayList<DataRow>();
    /**
     * Every DataTable contains a list of selectors, which manage tracking
     * "selected" state in a DataTable. This is necessary for proper handling
     * of master/detail relationships, also known as parent/child. This data
     * structure maps the data selectors name to the selector itself.
     */
    protected Map<String,DataSelector> selectors = new HashMap<String,DataSelector>();
    /**
     * Indicates whether deleting rows is supported. Attempts to
     * delete a row when row deletion is not supported will be ignored
     */
    private boolean deleteRowSupported = true;
    /**
     * Indicates whether appending rows is supported. Attempts to append a
     * row when row appending is not supported will be ignored.
     */
    private boolean appendRowSupported = true;
    /**
     * A list of DataTableListeners to notify of various events
     */
    private List<DataTableListener> listeners = new ArrayList<DataTableListener>();
    
    /**
     * A PropertyChangeListener for listening to name property change events
     * on DataSelectors and DataColumns. The listener makes sure that the name
     * used as the hash key in the selectors and columns maps is always
     * accurate.
     */
    private final PropertyChangeListener nameChangeListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getSource() instanceof DataSelector) {
                DataSelector sel = selectors.remove(evt.getOldValue());
                if (sel != null) {
                    //name changed
                    selectors.put((String)evt.getNewValue(), sel);
                }
            } else if (evt.getSource() instanceof DataColumn) {
                DataColumn c = columns.remove(evt.getOldValue());
                if (c != null) {
                    //name changed
                    columns.put((String)evt.getNewValue(), c);
                }
            }
        }
    };
	
    /**
     * Create a new DataTable
     */
    protected DataTable(DataSet ds) {
        assert ds != null;
        this.dataSet = ds;
        name = NAMEGEN.generateName(this);
    }
    
    /**
     * creates a new DataColumn, and adds it to this DataTable. A name will
     * be automatically generated for the DataColumn.
     *
     * @return the DataColumn that was created
     */
    public DataColumn createColumn() {
        return createColumn(null);
    }
    
    /**
     * Creates a new DataColumn with the given name, and adds it to this
     * DataTable.
     *
     * @param colName the name to give the DataColumn. If the name is invalid
     * or has already been used in the DataSet, an assertion will be raised
     *
     * @return the new DataColumn
     */
    public DataColumn createColumn(String colName) {
        DataColumn col = new DataColumn(this);
        if (colName != null) {
            col.setName(colName);
        }
        columns.put(col.getName(),  col);
        col.addPropertyChangeListener("name", nameChangeListener);
        
        fireColumnAdded(col);
        return col;
    }
    
    /**
     * Drops the column with the given name. If there is no column by the
     * specified name, then nothing is done.
     *
     * @param colName
     */
    public void dropColumn(String colName) {
        DataColumn col = columns.remove(colName);
        if (col != null) {
            col.removePropertyChangeListener("name",  nameChangeListener);
            fireColumnRemoved(col);
        }
    }
    
	/**
	 * @return a List of DataColumns representing all of the columns in this
     * DataTable.
	 */
	public List<DataColumn> getColumns() {
		return Collections.unmodifiableList(new ArrayList<DataColumn>(columns.values()));
	}
    
    /**
     * @param colName the name of the column that you want to retrieve
     * @return the DataColumn with the given name. If the given name does not
     * map to a DataColumn in this DataTable, then null is returned.
     */
    public DataColumn getColumn(String colName) {
        return columns.get(colName);
    }
    
    /**
     * @return an unmodifiable list of all of the rows in this DataTable. The
     * individual DataRow elements are modifiable, but the List is not.
     */
    public List<DataRow> getRows() {
        return Collections.unmodifiableList(rows);
    }
    
    /**
     * @param index the Index in this table associated with the DataRow to be
     * retrieved. This must be &gt;0, and &lt;rows.size()
     * @return the DataRow at the given 0 based index. The index must be valid
     */
    public DataRow getRow(int index) {
        assert index > 0 && index < rows.size();
        return rows.get(index);
    }
    
    /**
     * Creates and returns a selector. The DataSelector will have a generated
     * name by default.
     */
    public DataSelector createSelector() {
        return createSelector(null);
    }
    
    /**
     * Creates and returns a selector with the given name. If the name is
     * invalid, an assertion will be raised
     * @param name the name for the new DataSelector
     */
    public DataSelector createSelector(String name) {
        DataSelector sel = new DataSelector(this);
        if (name != null) {
            sel.setName(name);
        }
        selectors.put(sel.getName(),  sel);
        sel.addPropertyChangeListener("name", nameChangeListener);
        return sel;
    }
    
    /**
     * @return a List of DataSelectors associated with this DataTable.
     */
    public List<DataSelector> getSelectors() {
        return Collections.unmodifiableList(new ArrayList<DataSelector>(selectors.values()));
    }
    
    /**
     * @param name the name of the selector to create or return
     * @return the DataSelector with the given name. If no such DataSelector
     * exists, then a new DataSelector will be created and added to this
     * DataTable by the given name.
     */
    public DataSelector getSelector(String name) {
        //if the given selector doesn't exist, create it implicitly
        if (!selectors.containsKey(name)) {
            return createSelector(name);
        } else {
            return selectors.get(name);
        }
    }
    
    /**
     * Drop the given selector
     * @param the selector to drop
     */
    public void dropSelector(DataSelector selector) {
        dropSelector(selector.getName());
    }
    
    /**
     * drops the given named selector. If a selector by that name does not
     * exist, then do nothing
     * @param selectorName the name of the selector to drop
     */
    public void dropSelector(String selectorName) {
        DataSelector sel = selectors.remove(selectorName);
        if (sel != null) {
            sel.removePropertyChangeListener("name",  nameChangeListener);
            fireSelectorRemoved(sel);
        }
    }
    
	/**
     * Sets the given name to be the name of this DataTable
	 * @param name
	 */
	public void setName(String name) {
        if (this.name != name) {
            assert DataSetUtils.isValidName(name);
            assert !dataSet.hasElement(name);
            String oldName = this.name;
            this.name = name;
            pcs.firePropertyChange("name", oldName, name);
        }
	}

    /**
     * @return the name of this DataTable
     */
	public String getName() {
		return name;
	}

    /**
     * @return true if deletion of rows is supported
     */
    public boolean isDeleteRowSupported() {
        return deleteRowSupported;
    }

    /**
     * Sets the deleteRowSupported flag
     * @param deleteRowSupported the new value for deleteRowSupported
     */
    public void setDeleteRowSupported(boolean deleteRowSupported) {
        if (this.deleteRowSupported != deleteRowSupported) {
            boolean oldValue = this.deleteRowSupported;
            this.deleteRowSupported = deleteRowSupported;
            pcs.firePropertyChange("deleteRowSupported", oldValue, deleteRowSupported);
        }
    }

    /**
     * @return true if appending rows is supported
     */
    public boolean isAppendRowSupported() {
        return appendRowSupported;
    }

    /**
     * Sets whether appending rows is supported
     * @param appendRowSupported the new value for appendRowSupported
     */
    public void setAppendRowSupported(boolean appendRowSupported) {
        if (this.appendRowSupported != appendRowSupported) {
            boolean oldValue = this.appendRowSupported;
            this.appendRowSupported = appendRowSupported;
            pcs.firePropertyChange("appendRowSupported", oldValue, appendRowSupported);
        }
    }

    /**
     * @return the DataProvider for this DataTable. May be null
     */
    public DataProvider getDataProvider() {
        return dataProvider;
    }

    /** 
     * Sets the DataProvider for this DataTable.
     * @param dataProvider the DataProvider for this DataTable. This may be null.
     */
    public void setDataProvider(DataProvider dataProvider) {
        if (this.dataProvider != dataProvider) {
            DataProvider oldValue = this.dataProvider;
            this.dataProvider = dataProvider;
            pcs.firePropertyChange("dataProvider", oldValue, dataProvider);
        }
    }
    
    /** 
     * @return the DataSet that is associated with this DataTable
     */
    public DataSet getDataSet() {
        return dataSet;
    }

    /**
     * Append a new DataRow to this DataTable, and return the newly appended
     * Row. If appendRowSupported is false, then this method returns null
     * @return null if !appendRowSupported, else the newly created row
     */
    public DataRow appendRow() {
        DataRow row = appendRowNoEvent();
        if (row != null) {
            fireRowAdded(row);
        }
        return row;
    }
    
    /**
     * Appends a new DataRow to this DataTable, and returns the newly appended
     * row. This method differs from #appendRow in that it does not fire
     * any event. This is useful to the DataProvider, which will be adding many
     * rows and may not want many event notifications
     */
    public DataRow appendRowNoEvent() {
        if (appendRowSupported) {
            DataRow row = new DataRow(this);
            rows.add(row);
            return row;
        } else {
            return null;
        }
    }        

    /**
     * Doesn't actually remove the row, just marks it for deletion. If
     * deletion of rows is not supported, nothing happens.
     * @param rowIndex the index of the row to delete. If the index is invalid,
     * an assertion will be raised
     */
    public void deleteRow(int rowIndex) {
        assert rowIndex > 0 && rowIndex < rows.size();
        if (deleteRowSupported) {
            rows.get(rowIndex).setStatus(DataRow.DataRowStatus.DELETED);
        }
    }
    
    /**
     * Loads this DataTable using this tables DataProvider. If DataProvider is
     * null, then nothing is loaded. This method <b>does not</b> clear out the
     * DataTable prior to loading. Calling load() <i>n</i> times will cause the
     * DataTable to contain <i>rowCount * n</i> rows to be added.
     */
    public void load() {
        if (dataProvider != null) {
            dataProvider.load(this);
            fireDataTableChanged(new TableChangeEvent(this));
        }
    }
   
    /**
     * Saves this DataTable to the underlying DataStore. This calls the
     * DataProviders save method. If no DataProvider is specified, then nothing
     * is done.
     */
    public void save() {
        if (dataProvider != null) {
            dataProvider.save(this);
        }
    }
    
    /**
     * Clears all of the rows from this DataTable. <em>If any rows were altered,
     * these changes are lost!</em>. Call #save to save the changes before
     * clearing. An event is posted indicating that the rows in this DataTable
     * have changed.
     */
    public void clear() {
        rows.clear();
        fireDataTableChanged(new TableChangeEvent(this));
    }
    
    /**
     * Refreshes the DataSet. This is symantically the same as:
     * <code>
     * clear();
     * load();
     * </code>
     */
    public void refresh() {
        clear();
        load();
    }
    
    /**
     * @return the value at the given rowIndex, for the given columnName. If
     * either the index is invalid or the columnName, assertions will be raised.
     * @param index the row index of the row that you want to retrieve a value
     * for
     * @param columnName the name of the column who's value you want retrieved
     */
    public Object getValue(int index, String columnName) {
        assert index > 0 && index < rows.size();
        assert columns.containsKey(columnName);
        return rows.get(index).getValue(columnName);
    }

    /**
     * Sets the field value at the given row idnex and column to the given
     * value. If either the index is invalid or the columnName, assertions will
     * be raised.
     * @param index
     * @param columnName
     * @param value
     */
    public void setValue(int index, String columnName, Object value) {
        assert index > 0 && index < rows.size();
        assert columns.containsKey(columnName);
        rows.get(index).setValue(columnName, value);
    }

    /**
     * Retrieves the data value for the given row and column.
     *
     * @param row The DataRow to retrieve the value from. This row must be
     * a member of this table, or an assertion will be raised. If null, a
     * NullPointerException will be thrown.
     *
     * @param col The DataColumn to retrieve the value from. This col must
     * be a member of this table, or an assertion will be raised. If null, a
     * NullPointerException will be thrown.
     */
    public Object getValue(DataRow row, DataColumn col) {
        assert row.getTable() == this;
        assert col.getTable() == this;
        return row.getValue(col);
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
    
    public void addDataTableListener(DataTableListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    public void removeDataTableListener(DataTableListener listener) {
        listeners.remove(listener);
    }
    
    protected void fireColumnAdded(DataColumn col) {
        fireDataTableChanged(new TableChangeEvent(this));
    }
    
    protected void fireRowAdded(DataRow row) {
        fireDataTableChanged(new TableChangeEvent(this));
    }
    
    public void fireDataTableChanged(TableChangeEvent evt) {
        for (DataTableListener listener : listeners) {
            listener.tableChanged(evt);
        }
    }
    
    protected void fireSelectorRemoved(DataSelector sel) {
        fireDataTableChanged(new TableChangeEvent(this));
    }
    
    protected void fireColumnRemoved(DataColumn col) {
        fireDataTableChanged(new TableChangeEvent(this));
    }
    
    /**
     * Internal method that returns the int index of the given DataRow. This is
     * currently only used for constructing toString on DataRow, and testing.
     */
    protected int indexOfRow(DataRow row) {
        return rows.indexOf(row);
    }
}