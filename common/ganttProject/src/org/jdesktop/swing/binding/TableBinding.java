/*
 * $Id: TableBinding.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swing.binding;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.jdesktop.swing.data.DataModel;
import org.jdesktop.swing.data.DataModelToTableModelAdapter;
import org.jdesktop.swing.data.TabularDataModel;
import org.jdesktop.swing.data.TabularMetaData;

/**
 * Binding a JTable to a field in a DataModel. The field value must be
 * of type TabularDataModel.
 * 
 * PENDING: no buffering, no validation.
 * 
 * @author Jeanette Winzenburg
 */
public class TableBinding extends AbstractBinding implements Binding {

    private JTable table;

    protected TableBinding(JComponent component, DataModel dataModel, String fieldName) {
        super(component, dataModel, fieldName, AUTO_VALIDATE_NONE);
        // TODO Auto-generated constructor stub
    }

    public boolean isModified() {
        return false;
    }

    public boolean isValid() {
        return true;
    }
    
    protected void setComponent(JComponent component) {
        table = (JTable) component;

    }

    protected Object getComponentValue() {
        // TODO Auto-generated method stub
        return null;
    }

    protected void setComponentValue(Object value) {
        if (value instanceof TabularDataModel) {
            TableModel model = createTabularAdapter((TabularDataModel) value);
            table.setModel(model);
        }

    }


    private TableModel createTabularAdapter(TabularDataModel model) {
        String[] fieldNames = null;
        if (metaData instanceof TabularMetaData) {
            fieldNames = ((TabularMetaData) metaData).getFieldNames();
        }
        return new DataModelToTableModelAdapter(model, fieldNames);
    }

    public JComponent getComponent() {
        return table;
    }

}
