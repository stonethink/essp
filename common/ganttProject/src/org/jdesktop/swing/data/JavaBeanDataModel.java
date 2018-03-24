/*
 * $Id: JavaBeanDataModel.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.data;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

/**
 * A class that creates a collection of MetaData based BeanInfo
 * PropertyDescriptors. To use this class:
 * <ol>
 *   <li>Construct the model using the Bean class you wish to model
 *   <li>use <code>setJavaBean</code> to set the current object of this class.
 *   <li>Updates made to the form will update the property values of the bean.
 * </ol>
 * <p>
 * TODO: Using JavaBeans as a data source should be generalized and not
 * constrained to FormModels.
 *
 * @author Mark Davidson
 */
public class JavaBeanDataModel extends DefaultDataModel {

    private BeanInfo info;
    private Class beanClass;
    private Object bean; // current bean instance

    public JavaBeanDataModel(Class beanClass) throws IntrospectionException {
        this(beanClass, null);
    }

    /**
     * Constructs a JavaBeanDataModel by introspecting on the class and using the data from
     * the object as the current bean
     *
     * @param beanClass the class to use to introspect properties
     * @param bean the object where the current values will be retrieved and stored.
     */
    public JavaBeanDataModel(Class beanClass, Object bean) throws IntrospectionException {
        this.beanClass = beanClass;
	this.bean = bean;
        info = Introspector.getBeanInfo(beanClass);

	// 
        PropertyDescriptor[] props = info.getPropertyDescriptors();
        for (int i = 0; i < props.length; i++) {
            addField(new MetaData(props[i].getName(),
                                    props[i].getPropertyType(),
                                    props[i].getDisplayName()));
        }
    }

    /**
     * Set the JavaBean instance that this model will use.
     */
    public void setJavaBean(Object bean) {
        if (bean != null && bean.getClass() != beanClass) {
            throw new RuntimeException("ERROR: argument is not a " +
                                       beanClass.toString());
        }

        Object oldBean = this.bean;
        this.bean = bean;
        if (bean != oldBean) {
            // Should update all the values
            String[] fieldNames = getFieldNames();
            for (int i = 0; i < fieldNames.length; i++) {
                fireValueChanged(fieldNames[i]);
            }
        }
    }

    /**
     * Get the JavaBean instance that this model uses.
     */
    public Object getJavaBean() {
        return bean;
    }

    public Object getValue(String fieldName) {
        if (getJavaBean() == null) {
            return null;
        }
        PropertyDescriptor prop = getPropertyDescriptor(fieldName);
        Method readMethod = prop.getReadMethod();
        if (readMethod != null) {
            try {
                return readMethod.invoke(getJavaBean(), new Object[0]);
            }
            catch (Exception ex) {
                // XXX excecption for illegal access, etc..
                ex.printStackTrace();
            }
        }
        return null;
    }

    // XXX should be protected.
    protected void setValueImpl(String fieldName, Object value) {
        if (getJavaBean() == null) {
            return;
        }
        PropertyDescriptor prop = getPropertyDescriptor(fieldName);
        Method writeMethod = prop.getWriteMethod();
        if (writeMethod != null) {
            try {
                writeMethod.invoke(getJavaBean(), new Object[] {value});
            }
            catch (Exception ex) {
                // XXX excecption for illegal access, etc..
                ex.printStackTrace();
            }
        }
    }

    private PropertyDescriptor getPropertyDescriptor(String name) {
        PropertyDescriptor pd = null;
        PropertyDescriptor[] desc = info.getPropertyDescriptors();
        for (int i = 0; i < desc.length; i++) {
            if (name.equals(desc[i].getName())) {
                pd = desc[i];
                break;
            }
        }
        return pd;
    }
}
