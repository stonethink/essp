/*
 * $Id: DataValue.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2005 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.jdesktop.dataset.NameGenerator;

/**
 * TODO implement a PropertyChangeListener on the synthetic "value" field.
 * When some value that the DataValue expression depends on changes, it
 * becomes necessary to recompute the "value", and then notify that the
 * "value" has changed.
 *
 * @author rbair
 */
public class DataValue {
    //protected for testing
    protected static final String DEFAULT_NAME_PREFIX = "DataValue";
    private static final NameGenerator NAMEGEN = new NameGenerator(DEFAULT_NAME_PREFIX);

    //used for communicating changes to this JavaBean, especially necessary for
    //IDE tools, but also handy for any other component listening to this data value
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private DataSet dataSet;
    private String name;
    
    private String expression;
    
    /** Creates a new instance of DataValue */
    public DataValue(DataSet ds) {
        assert ds != null;
        this.dataSet = ds;
        name = NAMEGEN.generateName(this);
    }
    
	/**
	 * @param name
	 */
	public void setName(String name) {
        if (this.name != name) {
            assert DataSetUtils.isValidName(name);
            String oldName = this.name;
            this.name = name;
            pcs.firePropertyChange("name", oldName, name);
        }
	}

	public String getName() {
		return name;
	}

    public DataSet getDataSet() {
        return dataSet;
    }
    
    public String getExpression() {
        return expression;
    }
    
    public void setExpression(String expression) {
        this.expression = expression;
    }
    
    public Object getValue() {
        if (expression != null) {
            //TODO Hook functor stuff in here for expressions
            return null;
        } else {
            return null;
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