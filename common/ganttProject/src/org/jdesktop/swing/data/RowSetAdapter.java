/*
 * $Id: RowSetAdapter.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.data;

import java.sql.ResultSetMetaData;

import java.util.ArrayList;

import javax.sql.RowSet;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;

/**
 * Placeholder for future RowSet TableModel adapter to enable
 * easy connectivity to JDBC RowSet functionality. For more details see the
 * <a href="http://www.jcp.org/en/jsr/detail?id=114">JSR 114</a>.
 * Note: This class is not yet fully functional.
 *
 * @author Amy Fowler
 * @version 1.0
 */
public class RowSetAdapter extends AbstractTableModel {
    private RowSet rowset;
    private ResultSetMetaData metaData;

    /**
     * Note: This class is not yet functional, but will be completed
     * in JDNC Milestone5.
     *
     * Creates a table model adapter which binds to the specified tabular
     * data model.
     *
     * @param rowset RowSet object containing the tabular data
     * @throws NullPointerException if rowset is null
     * @throws SQLException
     */
    private RowSetAdapter(RowSet rowset) throws java.sql.SQLException{
        if (rowset == null) {
            throw new NullPointerException("rowset cannot be null");
        }
        this.rowset = rowset;
        this.metaData = rowset.getMetaData();
    }

    public Class getColumnClass(int columnIndex) {
        Class klass = null;
        try {
            klass = Class.forName(metaData.getColumnClassName(
                translateAdapterColumn(columnIndex)));
        } catch (Exception e) {

        }
        return klass;
    }

    public int getRowCount() {
        //REMIND(aim): awk! size() doesn't exist in 1.4.2....
        //return rowset.size();
        return 0;
    }

    public int getColumnCount() {
        int columnCount = 0;
        try {
            columnCount = metaData.getColumnCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnCount;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        try {
            synchronized(rowset) {
                rowset.absolute(translateAdapterRow(rowIndex));
                value = rowset.getObject(translateAdapterColumn(columnIndex));
            }
        } catch (Exception e) {

        }
        return value;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //REMIND(aim): investigate isReadOnly vs isWritable vs isDefinitelyWritable
        boolean editable = false;
        try {
            editable = metaData.isWritable(translateAdapterColumn(columnIndex));
        } catch (Exception e) {

        }
        return editable;
    }

    protected int translateAdapterColumn(int columnIndex) {
        return columnIndex + 1;
    }

    protected int translateDataColumn(int dataColumnIndex) {
        return dataColumnIndex - 1;
    }

    protected int translateAdapterRow(int rowIndex) {
        return rowIndex + 1;
    }

    protected int translateDataRow(int dataRowIndex) {
        return dataRowIndex - 1;
    }

}
