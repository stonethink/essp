/*
 * $Id: DefaultDataModel.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.data;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


/**
 * Default data model implementation designed to hold a single record of
 * field values.  This class provides storage of the model's values and
 * may be used when there is no underlying data model.
 *
 * @see TableModelExtAdapter
 * @see JavaBeanDataModel
 *
 * @author Amy Fowler
 * @version 1.0
 */

public class DefaultDataModel extends AbstractDataModel {
    private ArrayList fieldNames = new ArrayList();
    private HashMap values = new HashMap();
    private HashMap metaData = new HashMap();
    private HashMap fieldAdapters = new HashMap();

    public DefaultDataModel() {
    }

    public DefaultDataModel(MetaData fieldMetaData[]) {
        for(int i = 0; i < fieldMetaData.length; i++) {
            addField(fieldMetaData[i], null);
        }
    }

    public void addField(MetaData fieldMetaData,
                           Object defaultValue) {
        String name = fieldMetaData.getName();
        addField(fieldMetaData);
        values.put(name, defaultValue);
    }

    public void addField(MetaData fieldMetaData) {
        String name = fieldMetaData.getName();
        fieldNames.add(name); // track order fields were added
        metaData.put(name, fieldMetaData);
    }

    public void removeField(MetaData fieldMetaData) {
        String name = fieldMetaData.getName();
        fieldNames.remove(name);
        metaData.remove(name);
    }

    public String[] getFieldNames() {
        return (String[])fieldNames.toArray(new String[fieldNames.size()]);
    }

    public MetaData getMetaData(String fieldName) {
        return (MetaData)metaData.get(fieldName);
    }

    public int getFieldCount() {
        return metaData.size();
    }

    public Object getValue(String fieldName) {
        return values.get(fieldName);
    }

    protected void setValueImpl(String fieldName, Object value) {
        values.put(fieldName, value);
    }

    public int getRecordCount() {
        return 1;
    }

    public int getRecordIndex() {
        return 0;
    }

    public void setRecordIndex(int index) {
        if (index != 0) {
            throw new IndexOutOfBoundsException("DefaultDataModel contains only 1 record");
        }
    }


}
