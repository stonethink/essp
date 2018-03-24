/*
 * $Id: ComboBoxBinding.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.binding;

import org.jdesktop.swing.data.DataModel;
import org.jdesktop.swing.data.EnumeratedMetaData;
import org.jdesktop.swing.data.MetaData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.MutableComboBoxModel;

/**
 * Class which binds a component that supports setting a one-of-many
 * value (JComboBox) to a data model field which may be an arbitrary type.
 * @author Amy Fowler
 * @version 1.0
 */
public class ComboBoxBinding extends AbstractBinding {
    private JComboBox comboBox;
    /* Note: we cannot support binding to any component with a ComboBoxModel
     * because ComboBoxModel fires no event when the value changes!
     * JW: ?? don't understand
     */
    public ComboBoxBinding(JComboBox combobox,
                           DataModel dataModel, String fieldName) {
        super(combobox, dataModel, fieldName, Binding.AUTO_VALIDATE_NONE);
    }

    public JComponent getComponent() {
        return comboBox;
    }

    protected void setComponent(JComponent component) {
        comboBox = (JComboBox) component;
        configureDropDown();
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!pulling) {
                    setModified(true);
                }
            }
        });
    }

    
    private void configureDropDown() {
        if (metaData instanceof EnumeratedMetaData) {
            updateComboBoxModel(((EnumeratedMetaData) metaData).getEnumeration());
        }
    }

    protected void updateComboBoxModel(Object[] items) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        model.setSelectedItem(getComponentValue());
        // JW: brute force
       comboBox.setModel(model);
       setValidState(UNVALIDATED);
    }

    protected void installMetaDataListener() {
        PropertyChangeListener l = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ("enumeration".equals(evt.getPropertyName())) {
                    updateComboBoxModel((Object[]) evt.getNewValue());
                }
                
            }
            
        };
        metaData.addPropertyChangeListener(l);
    }

    protected Object getComponentValue(){
        return comboBox.getSelectedItem();
    }

    protected void setComponentValue(Object value) {
        comboBox.getModel().setSelectedItem(value);
    }

}
