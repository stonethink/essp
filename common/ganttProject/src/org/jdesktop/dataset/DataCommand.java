/*
 * $Id: DataCommand.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a command that can be executed against a data store. Some commands
 * are for retrieving data for a DataProvider, while others are for persisting
 * data to the data store. For example, in the provider.sql package there are
 * SelectCommand, UpdateCommand, InsertCommand, and DeleteCommand instances.
 *
 * @author rbair
 */
public abstract class DataCommand {
    /**
     * Contains the short description
     */
    private String shortDescription;

    /**
     * A special marker indicating that a parameter has been undefined.
     */
    private static final Object UNDEFINED = new Object();
    
    /**
     * Contains all of the params
     */
    private Map<String,Object> params = new HashMap<String,Object>();

    /**
     * Set a short description for this Task. This description is used
     * within a GUI builder to describe what the Task does, or wherever a short
     * description might be useful, such as within some logging statements.
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription == null ? "" : shortDescription;
    }

    /**
     * Returns the short description of this DataCommand
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the given named parameter to the given value. Passing in a value of
     * "null" will *not* clear the parameter, but will set the parameter to the
     * null value.
     */
    public void setParameter(String name, Object value) {
        params.put(name, value);
    }

    /**
     * Clears the given named parameter of any associated value.
     */
    public void clearParameter(String name) {
        params.put(name, UNDEFINED);
    }
    
    /**
     * Clears all of the parameters
     */
    public void clearParameters() {
        for (String name : params.keySet()) {
            params.put(name, UNDEFINED);
        }
    }
    
    /**
     * Returns the value for the given named parameter.
     */
    public Object getParameter(String name) {
        return params.get(name);
    }

    /**
     * Returns an array containing all of the parameter names for this DataCommand
     */
    public abstract String[] getParameterNames();
    
    /**
     * Returns an object array containing all of the parameter values for this
     * DataCommand.
     */
    public Object[] getParameterValues() {
        return params.values().toArray();
    }
}