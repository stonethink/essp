/*
 * $Id: RadioBinding.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.binding;

import org.jdesktop.swing.data.DataModel;
import org.jdesktop.swing.data.MetaData;

import org.jdesktop.swing.JXRadioGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;


/**
 * Class which binds a component that supports setting a one-of-many
 * value (JXRadioGroup) to a data model field which is may be an arbitrary type.
 * @author Amy Fowler
 * @version 1.0
 */
public class RadioBinding extends AbstractBinding {
    private JXRadioGroup radioGroup;

    public RadioBinding(JXRadioGroup radioGroup,
                           DataModel dataModel, String fieldName) {
        super(radioGroup, dataModel, fieldName, Binding.AUTO_VALIDATE_NONE);
    }

    public JComponent getComponent() {
        return radioGroup;
    }

    protected void setComponent(JComponent component) {
        radioGroup = (JXRadioGroup) component;
        radioGroup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!pulling) {
                    setModified(true);
                }
            }
        });
    }

    protected Object getComponentValue(){
        return radioGroup.getSelectedValue();
    }

    protected void setComponentValue(Object value) {
        radioGroup.setSelectedValue(value);
    }

}
