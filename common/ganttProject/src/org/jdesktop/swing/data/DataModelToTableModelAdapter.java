/*
 * $Id: DataModelToTableModelAdapter.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swing.data;

import javax.swing.table.AbstractTableModel;


/**
 * Extracted from TableBinding and changed to register
 * as TabularValueChangeListener.
 * 
 * @author Richard Bair
 * @author Jeanette Winzenburg
 * 
 */
public class DataModelToTableModelAdapter extends AbstractTableModel 
    implements MetaDataProvider {

    private TabularDataModel dm;
//    private String[] fieldNames;
    private DefaultMetaDataProvider metaDataProvider;
    
    public DataModelToTableModelAdapter(TabularDataModel dm) {
        this(dm, null);
    }

    public DataModelToTableModelAdapter(TabularDataModel dm, String[] visibleFieldNames) {
        this.dm = dm;
        initMetaDataProvider(visibleFieldNames);
//        fieldNames = visibleFieldNames == null ? dm.getFieldNames() : visibleFieldNames;
        installDataModelListener();
    }
    
    private void initMetaDataProvider(String[] visibleFieldNames) {
        if (visibleFieldNames == null) {
            metaDataProvider = new DefaultMetaDataProvider(dm);
        } else {
            MetaData[] metas = new MetaData[visibleFieldNames.length];
            for (int i = 0; i < metas.length; i++) {
                metas[i] = dm.getMetaData(visibleFieldNames[i]);
            }
            metaDataProvider = new DefaultMetaDataProvider(metas);
        }
        
    }

    public Class getColumnClass(int columnIndex) {
        MetaData metaData = getMetaData(columnIndex);
        return metaData.getElementClass();
    }
    

    public String getColumnName(int column) {
        //its possible that the meta data hasn't shown up yet. In this
        //case, use the field name until the meta data arrives
        // JW: when would that be the case?
//        MetaData md = dm.getMetaData(fieldNames[column]);
//        return md == null ? fieldNames[column] : md.getLabel();
        MetaData md = getMetaData(column);
        return md.getLabel();
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        MetaData md = getMetaData(columnIndex);
        return !md.isReadOnly();
    }
    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        dm.setValueAt(getFieldName(columnIndex), rowIndex, aValue);
    }
    
    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return dm.getRecordCount();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return getFieldCount();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return dm.getValueAt(getFieldName(columnIndex), rowIndex);
    }


    private String getFieldName(int columnIndex) {
        return getDefaultMetaDataProvider().getFieldName(columnIndex);
    }

    private MetaData getMetaData(int columnIndex) {
        String fieldName = getFieldName(columnIndex);
        return getMetaData(fieldName);
    }

//  ------------------------ MetaDataProvider

    public String[] getFieldNames() {
        // TODO Auto-generated method stub
        return getDefaultMetaDataProvider().getFieldNames();
    }
    
    public int getFieldCount() {
        // TODO Auto-generated method stub
        return getDefaultMetaDataProvider().getFieldCount();
    }


    public MetaData getMetaData(String dataID) {
        return getDefaultMetaDataProvider().getMetaData(dataID);
    }


    public MetaData[] getMetaData() {
        return getDefaultMetaDataProvider().getMetaData();
    }
    
    private DefaultMetaDataProvider getDefaultMetaDataProvider() {
        if (metaDataProvider == null) {
            metaDataProvider = new DefaultMetaDataProvider();
        }
        return metaDataProvider;
    }

    // --------------------------- init listener

    private void installDataModelListener() {
        // register with the data model

        // ValueChangeListener l = new ValueChangeListener() {
        // public void valueChanged(ValueChangeEvent e) {
        // fireTableStructureChanged();
        // }
        // };
        // dm.addValueChangeListener(l);

        TabularValueChangeListener l = new TabularValueChangeListener() {
            public void tabularValueChanged(TabularValueChangeEvent e) {
                int rowIndex = e.getRowIndex();
                int columnIndex = findColumnIndex(e.getFieldName());
                if (rowIndex < 0) {
                    if (columnIndex < 0) {
                        fireTableStructureChanged();
                    } else {
                        fireTableDataChanged();
                    }
                } else {
                    if (columnIndex < 0) {
                        fireTableRowsUpdated(rowIndex, rowIndex);
                    } else {
                        fireTableCellUpdated(rowIndex, columnIndex);
                    }
                }
                // PENDING: do better than this!
                //fireTableStructureChanged();
            }
        };
        dm.addTabularValueChangeListener(l);

    }


    protected int findColumnIndex(String fieldName) {
        if (fieldName == null) {
            return -1;
        }
        return getDefaultMetaDataProvider().getFieldIndex(fieldName);
//        for (int i = 0; i < fieldNames.length; i++) {
//            if (fieldName.equals(fieldNames[i])) {
//                return i;
//            }
//        }
//        return -1;
    }



}
