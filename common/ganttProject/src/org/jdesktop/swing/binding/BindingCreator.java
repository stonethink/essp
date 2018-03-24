/*
 * $Id: BindingCreator.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swing.binding;

import javax.swing.JComponent;

import org.jdesktop.swing.data.DataModel;

/**
 * Responsible for creating a "fitting" Binding
 * for the component.
 *
 * 
 *
 * @author Jeanette Winzenburg
 */
public interface BindingCreator {

  Binding createBinding(JComponent component, DataModel dataModel,
      String fieldName);
}
