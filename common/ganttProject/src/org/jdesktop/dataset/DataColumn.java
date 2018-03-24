/*
 * $Id: DataColumn.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.dataset;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;
import org.jdesktop.dataset.NameGenerator;

/**
 * 
 * @author rbair
 */
public class DataColumn {
    /**
     * The Logger
     */
    private static final Logger LOG = Logger.getLogger(DataColumn.class.getName());
    
    //protected for testing
    protected static final String DEFAULT_NAME_PREFIX = "DataColumn";
    /**
     * Used to generate a name for the DataColumn, since each DataColumn must
     * have a name.
     */
    private static final NameGenerator NAMEGEN = new NameGenerator(DEFAULT_NAME_PREFIX);
    
    //used for communicating changes to this JavaBean, especially necessary for
    //IDE tools, but also handy for any other component listening to this column
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    /**
     * The DataTable that created this DataColumn. This is an immutable property
     * that is set in the constructor.
     */
	private DataTable table;
    /**
     * The name of this DataColumn. The name cannot contain any whitespace.
     * In general, it should conform to the same rules as an identifier
     * in Java.
     */
    private String name = NAMEGEN.generateName(this);
    /**
     * The Class of the data values for this DataColumn. This cannot be null. If
     * the type is unknown, then this should be Object.class.
     */
    private Class type = Object.class;
    /**
     * Flag indicating whether the fields within this column are readonly or
     * not.
     */
    private boolean readOnly = false;
    /**
     * Flag indicating whether the fields within this column are required.
     * If a column is required, then the field must be filled in prior to
     * a save, or an exception occurs.<br>
     * TODO constraint logic isn't specified yet. When it is, make sure to
     * include this check.
     */
    private boolean required = false;
    
    /**
     * The default value for the column. When a new row is added, the various
     * cells set their values to this default.
     */
    private Object defaultValue;

    /**
     * Indicates whether this DataColumn is a key column. Key Columns enforce
     * a unique constraint on the DataColumn (no two values in the column can
     * be the same, as determined by .equals()).
     */
    private boolean keyColumn;
    
    /**
     * Create a new DataColumn. To construct a DataColumn, do not call
     * <code>new DataColumn(table)</code> directly. Rather, call
     * <code>table.addColumn()</code>.<br>
     * @param table cannot be null. The DataTable that created this
     *        DataColumn.
     */
    protected DataColumn(DataTable table) {
        assert table != null;
        this.table = table;
    }
    
    /**
     * Returns the DataTable that this column belongs to
     */
    public DataTable getTable() {
        return table;
    }
    
    /**
     * Returns the name of the DataColumn
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the DataColumn. The name must be valid., or the change
     * will not be made. If the name is invalid, a warning will be posted.
     */
    public void setName(String name) {
        if (this.name != name) {
            assert DataSetUtils.isValidName(name);
            assert !table.columns.containsKey(name) && !table.selectors.containsKey(name);
            String oldName = this.name;
            this.name = name;
            pcs.firePropertyChange("name", oldName, name);
        }
    }

    /**
     * Returns the type of the values for this DataColumn
     */
    public Class getType() {
        return type;
    }

    /**
     * Sets the type for the values of this DataColumn.
     * @param type If null, then the type is set to Object.class
     */
    public void setType(Class type) {
        if (this.type != type) {
            Class oldType = this.type;
            this.type = type == null ? Object.class : type;
            pcs.firePropertyChange("type", oldType, type);
        }
    }

    /**
     * @return true if this DataColumn is read-only
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Sets whether this column is read-only or not.
     * @param readOnly
     */
    public void setReadOnly(boolean readOnly) {
        if (this.readOnly != readOnly) {
            boolean oldValue = this.readOnly;
            this.readOnly = readOnly;
            pcs.firePropertyChange("readOnly", oldValue, readOnly);
        }
    }

    /**
     * @return true if the fields in this column need to have a value
     * before they can be saved to the data store. The DataColumn is required
     * if the required flag is set by the setRequired method, or if the
     * DataColumn is a keyColumn. <br>
     * 
     * TODO need to decide if that is true, or if it is always required!
     */
    public boolean isRequired() {
        return required || keyColumn;
    }

    /**
     * Specifies whether the fields in this column must have a value (cannot
     * be null).
     * @param required
     */
    public void setRequired(boolean required) {
        if (this.required != required) {
            boolean oldValue = this.required;
            this.required = required;
            pcs.firePropertyChange("required", oldValue, required);
        }
    }

    /**
     * @return the value to use as a default value when a new field for
     * this column is created, such as when a new row is created.
     */
    public Object getDefaultValue() {
        return defaultValue;
    }
    
    /**
     * Set the defaultValue
     * @param defaultValue
     */
    public void setDefaultValue(Object defaultValue) {
        if (this.defaultValue != defaultValue) {
            Object oldVal = this.defaultValue;
            this.defaultValue = defaultValue;
            pcs.firePropertyChange("defaultValue", oldVal, defaultValue);
        }
    }
    
    /**
     * Returns whether the column is a key column or not
     */
    public boolean isKeyColumn() {
        return keyColumn;
    }
    
    /**
     * Sets this column to be a key column. This implicitly places a unique
     * constraint on the column. When this flag is set, no checks are made to
     * ensure correctness. However, the column will automatically be marked as
     * being required.
     *
     * @param value
     */
    public void setKeyColumn(boolean value) {
        if (value != keyColumn) {
            boolean oldVal = keyColumn;
            keyColumn = value;
            pcs.firePropertyChange("keyColumn", oldVal, value);
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
}