/*
 * $Id: DatePickerBinding.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.binding;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JComponent;

import org.jdesktop.swing.data.DataModel;
import org.jdesktop.swing.data.MetaData;

import org.jdesktop.swing.JXDatePicker;

public class DatePickerBinding extends AbstractBinding {

    private JXDatePicker picker;

    public DatePickerBinding(JXDatePicker picker,
			     DataModel model, String fieldName) {
        super(picker, model, fieldName, AbstractBinding.AUTO_VALIDATE_NONE);
    }

    public JComponent getComponent() {
	return picker;
    }

    public void setComponent(JComponent component) {
	this.picker = (JXDatePicker)component;
    }

    protected Object getComponentValue() {
	Class klazz = metaData.getElementClass();
	if (klazz == Date.class) {
	    return picker.getDate();
	}
	else if (klazz == Calendar.class) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTimeInMillis(picker.getDateInMillis());
	    return cal;
	}
        else if (klazz == Long.class) {
            return new Long(picker.getDateInMillis());
        }
	// default?
	return picker.getDate();
    }

    protected void setComponentValue(Object value) {
	Class klazz = metaData.getElementClass();
	if (klazz == Date.class) {
	    picker.setDate((Date)value);
	}
	else if (klazz == Calendar.class) {
	    picker.setDateInMillis(((Calendar)value).getTimeInMillis());
	}
        else if (klazz == Long.class) {
            picker.setDateInMillis(((Long)value).longValue());
        }
    }
}
