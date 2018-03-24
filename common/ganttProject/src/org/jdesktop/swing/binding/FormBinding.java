/*
 * $Id: FormBinding.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.binding;

import org.jdesktop.swing.data.Converters;
import org.jdesktop.swing.data.Converter;
import org.jdesktop.swing.data.DataModel;
import org.jdesktop.swing.data.MetaData;
import org.jdesktop.swing.data.Validator;

import org.jdesktop.swing.form.JForm;

import java.util.ArrayList;
import java.util.List;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

/**
 * Class which binds a JForm component to a data model field which is
 * type DataModel in order to support nested data models.
 *
 * @author Amy Fowler
 * @version 1.0
 */

public class FormBinding extends AbstractBinding {
    protected JForm form;

    public FormBinding(JForm form, DataModel dataModel, String fieldName) {
        super(form, dataModel, fieldName, Binding.AUTO_VALIDATE_NONE);
    }

    public boolean pull() {
        return form.pull();
    }

    public boolean isModified() {
        return form.isModified();
    }

    /**
     *
     */
    public boolean isValid() {
        return form.isFormValid();
    }

    public JComponent getComponent() {
        return form;
    }

    protected void setComponent(JComponent component) {
        form = (JForm)component;
    }

    protected Object getComponentValue() {
        // no-op
        return null;
    }

    protected void setComponentValue(Object value) {
        // no-op
    }

    public boolean push() {
        return form.push();
    }
}
