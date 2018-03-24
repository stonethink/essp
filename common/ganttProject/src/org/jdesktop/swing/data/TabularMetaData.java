/*
 * $Id: TabularMetaData.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swing.data;

/**
 * MetaData for declaring a field of type TabularDataModel.
 * 
 * @author Jeanette Winzenburg
 */
public class TabularMetaData extends MetaData {
    private String[] fieldNames;

    public TabularMetaData() {
        this("tabularvalue");
    }

    public TabularMetaData(String name) {
        super(name, TabularDataModel.class, null);
     
    }
    
    public TabularMetaData(String name, String label, String[] fieldNames) {
        this(name);
        setLabel(label);
        setFieldNames(fieldNames);
    }

    /**
     * sets subset of fields to show. null means all.
     * @param fieldNames
     */
    public void setFieldNames(String[] fieldNames) {
        String[] oldNames = getFieldNames();
        this.fieldNames = fieldNames;
        firePropertyChange("fieldNames", oldNames, getFieldNames());
        
    }

    /** 
     * returns subset of fields to show in the asociated tabular
     * structure. may be null to indicate all or empty array to
     * indicate none (hmmm...)
     * @return
     */
    public String[] getFieldNames() {
        // todo: return unmodifiable array
        return fieldNames;
    }
    
    
    public String getLabel() {
        return label;
    }
}
