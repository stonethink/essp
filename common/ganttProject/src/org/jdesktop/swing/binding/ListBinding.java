/*
 * $Id: ListBinding.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListModel;

import org.jdesktop.swing.data.DataConstants;
import org.jdesktop.swing.data.DataModel;
import org.jdesktop.swing.data.MetaData;
import org.jdesktop.swing.data.SelectionModel;

public class ListBinding extends AbstractBinding {
    
    private JList list;

    public ListBinding(JList list, DataModel model, String fieldName) {
        super(list, model, fieldName, AbstractBinding.AUTO_VALIDATE_NONE);
    }


    public boolean isModified() {
        return false;
    }

    public boolean isValid() {
        return true;
    }

    public JComponent getComponent() {
        return list;
    }

    protected void setComponent(JComponent component) {
        list = (JList)component;
        configureSelection();
    }

    private void configureSelection() {
        Object selectionMeta = metaData.getCustomProperty(DataConstants.SELECTION_MODEL);
        if (selectionMeta instanceof SelectionModel) {
            new ListSelectionBinding((SelectionModel) selectionMeta, list.getSelectionModel());
        }
        
    }

    
    protected void installMetaDataListener() {
        PropertyChangeListener l = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (DataConstants.SELECTION_MODEL.equals(evt.getPropertyName())) {
                    configureSelection();
                }
                
            }
            
        };
        metaData.addPropertyChangeListener(l);
    }

    
    protected Object getComponentValue(){
	ListModel model = list.getModel();
	Class klazz = metaData.getElementClass();

	if (klazz.equals(List.class)) {
	    List lvalue = new ArrayList();
	    for (int i = 0, size = model.getSize(); i < size; i++) {
		lvalue.add(model.getElementAt(i));
	    }
	    return lvalue;
	} 
	else if (klazz.isArray()) {

	    // XXX we lose the array type, use a generic Object[]
	    int size = model.getSize();
	    Object[] values = new Object[size];
	    for (int i = 0; i < size; i++) {
		values[i] = model.getElementAt(i);
	    }
	    return values;
	}
	return null;
    }

    protected void setComponentValue(Object value) {
	Class klazz = metaData.getElementClass();
	if (klazz.equals(List.class)) {
	    List lvalue = (List)value;
	    if (lvalue != null) {
		list.setListData(lvalue.toArray());
	    }
	}
	else if (klazz.isArray()) {
	    Object[] arrayValue = (Object[])value;

	    if (arrayValue != null) {
		list.setListData(arrayValue);
	    } else {
		// Empty the list.
		list.setModel(new DefaultListModel());
	    }
	}
    }
}

