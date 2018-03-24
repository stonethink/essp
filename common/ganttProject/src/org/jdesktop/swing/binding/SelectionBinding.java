/*
 * SelectionBinding.java
 *
 * Created on February 24, 2005, 6:33 AM
 */

package org.jdesktop.swing.binding;

import org.jdesktop.swing.data.SelectionModel;

/**
 *
 * @author rb156199
 */
public abstract class SelectionBinding {
    protected SelectionModel selectionModel;
    
    public SelectionBinding(SelectionModel model) {
        assert model != null;
        selectionModel = model;
    }
}
