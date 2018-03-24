/*
 * $Id: TableModelExtAdapter.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.data;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Adapts a DefaultTableModelExt object to the DataModel interface so that
 * user-interface components other than a table can easily be bound to
 * columns in the model.
 *
 * @author Amy Fowler
 * @version 1.0
 */
public class TableModelExtAdapter extends AbstractDataModel {

    private DefaultTableModelExt model;
    private int rowIndex = -1;
    private boolean updatingModel = false;

    public TableModelExtAdapter(DefaultTableModelExt model) {
        this.model = model;
        model.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (updatingModel) {
                    return;
                }
                if (e.getType() == TableModelEvent.UPDATE) {
                    if (rowIndex >= e.getFirstRow() && rowIndex <= e.getLastRow()) {
                        int columnIndex = e.getColumn();
                        final DefaultTableModelExt model = TableModelExtAdapter.this.model;
// cvs version (1.3) does only fire for one column
                        fireColumnsChanged(model, columnIndex, columnIndex);
                    }
                }
            }
            private void fireColumnsChanged(DefaultTableModelExt model, int firstColumn, int lastColumn) {
              if (firstColumn < 0) {
                firstColumn = 0;
                lastColumn = model.getColumnCount()-1;
              }
              for (int col = firstColumn; col <= lastColumn; col++) {
                fireValueChanged(model.getColumnMetaData(col).getName());
              }
              
            }
        });
    }

    public String[] getFieldNames() {
        int columnCount = model.getColumnCount();
        String columnNames[] = new String[columnCount];
        MetaData metaData[] = model.getMetaData();
        for(int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData[i].getName();
        }
        return columnNames;
    }

    public MetaData getMetaData(String fieldName) {
        return model.getColumnMetaData(model.getColumnIndex(fieldName));
    }

    public int getFieldCount() {
        return model.getColumnCount();
    }

    public Object getValue(String fieldName) {
        return getRecordIndex() >= 0 ? 
            model.getValueAt(getRecordIndex(), model.getColumnIndex(fieldName)) : null;
    }

    protected void setValueImpl(String fieldName, Object value) {
      	if (getRecordIndex() < 0) return;
         updatingModel = true;
         model.setValueAt(value, getRecordIndex(), model.getColumnIndex(fieldName));
         updatingModel = false;
     }

    /**
     * Sets the current record index such that the data field values
     * in this value map represent the values contained at the specified
     * row in the tabular data model.
     * @param rowIndex integer representing the current row index
     * @throws IndexOutOfBoundsException if index >= recordCount
     */
    public void setRecordIndex(int rowIndex) {
        if (rowIndex >= model.getRowCount()) {
            throw new IndexOutOfBoundsException("row index " + rowIndex +
                                                "exceeds row count " +
                                                model.getRowCount());
        }
        if (this.rowIndex != rowIndex) {
            this.rowIndex = rowIndex;
            String fieldNames[] = getFieldNames();
            for(int i = 0; i < fieldNames.length; i++) {
                fireValueChanged(fieldNames[i]);
            }
        }
    }

    /**
     *
     * @return integer representing the current row index
     */
    public int getRecordIndex() {
        return rowIndex;
    }

    /**
     *
     * @return integer representing the number of rows currently in
     *         the tabular data model
     */
    public int getRecordCount() {
        return model.getRowCount();
    }
}
