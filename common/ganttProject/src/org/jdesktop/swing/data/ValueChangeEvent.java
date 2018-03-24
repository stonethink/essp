/*
 * $Id: ValueChangeEvent.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.data;

import java.util.EventObject;

/**
 * Event indicating the value of a named data field within a
 * DataModel has changed.
 *
 * @see ValueChangeListener
 * @see DataModel
 *
 * @author Amy Fowler
 * @version 1.0
 */
public class ValueChangeEvent extends EventObject {

    private String fieldName = null;

    /**
     * Instantiates a new value change event for the specified named
     * field in the data model.
     * @param source DataModel containing the changed data field
     * @param fieldName String containing the name of the field that has changed
     */
    public ValueChangeEvent(DataModel source, String fieldName) {
        super(source);
        this.fieldName = fieldName;
    }

    /**
     *
     * @return String containing the name of the field that has changed
     */
    public String getFieldName() {
        return fieldName;
    }

}
