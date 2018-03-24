/*
 * $Id: DirectListBinding.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.binding;

import javax.swing.JComponent;
import javax.swing.JList;

import org.jdesktop.swing.data.DataModelToListModelAdapter;
import org.jdesktop.swing.data.SelectionModel;
import org.jdesktop.swing.data.TabularDataModel;

/**
 * This "Binding" happens to the given DataModel as a whole (as
 * opposed to a single field of the model).
 * 
 * @author Richard Bair
 */
public class DirectListBinding extends AbstractBinding {
    
    private JList list;


    public DirectListBinding(JList list, TabularDataModel model, String displayFieldName, SelectionModel sm) {
        super(list, model, displayFieldName, AbstractBinding.AUTO_VALIDATE_NONE);
        //create a selection binding
        new ListSelectionBinding(sm, list.getSelectionModel());
        //create a custom ListModel bound to the entire TabularDataModel
        list.setModel(new DataModelToListModelAdapter(model, displayFieldName));
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
    }

    protected Object getComponentValue(){
        return null;
    }

    protected void setComponentValue(Object value) {
    }
}

