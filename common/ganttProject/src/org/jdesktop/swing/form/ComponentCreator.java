/*
 * $Id: ComponentCreator.java,v 1.1 2006/10/10 03:51:01 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swing.form;

import javax.swing.JComponent;

import org.jdesktop.swing.data.MetaData;

/**
 * Responsible for creating a "fitting" component
 * for the metaData.
 *
 * @author  Jeanette Winzenburg
 */
public interface ComponentCreator {

  /**
   * creates and returns a JComponent based on metaData. 
   * 
   * @param metaData
   * @return
   * @throws NullPointerException if metaData == null
   */  
  public JComponent createComponent(MetaData metaData);
}
