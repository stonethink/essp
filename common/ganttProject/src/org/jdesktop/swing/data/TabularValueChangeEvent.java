/*
 * Created on 25.02.2005
 *
 */
package org.jdesktop.swing.data;

import java.util.EventObject;

/**
 * Event fired by TabularDataModel on cell updates.
 * 
 * PENDING: how to handle adding/removing rows?
 * 
 * @author Jeanette Winzenburg
 * 
 */
public class TabularValueChangeEvent extends EventObject {
    

    private int rowIndex;
    private String fieldName;

    /**
     * Instantiates a change event for the cell given in 
     * rowIndex/fieldName coordinates.
     * 
     * @param source
     * @param rowIndex the row which is updated. -1 indicates all rows.
     * @param fieldName the field which is updated. null indicates all fields.
     */
    public TabularValueChangeEvent(TabularDataModel source, int rowIndex, String fieldName) {
        super(source);
        this.rowIndex = rowIndex;
        this.fieldName = fieldName;
    }

    /** the column coordinate.
     * 
     * @return the column which is updated. null indicates all columns.
     */
    public String getFieldName() {
        return fieldName;
    }
    
    /**
     * 
     * @return the rowIndex which is updated. -1 indicates all columns.
     */
    public int getRowIndex() {
        return rowIndex;
    }
}
