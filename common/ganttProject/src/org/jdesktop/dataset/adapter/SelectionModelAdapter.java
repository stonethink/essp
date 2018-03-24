/*
 * SelectionModelAdapter.java
 *
 * Created on February 24, 2005, 6:56 AM
 */

package org.jdesktop.dataset.adapter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.dataset.DataSelector;
import org.jdesktop.swing.data.SelectionModel;
import org.jdesktop.swing.data.SelectionModelEvent;
import org.jdesktop.swing.data.SelectionModelListener;

/**
 *
 * @author rbair
 */
public class SelectionModelAdapter implements SelectionModel {
    private DataSelector selector;
    private List<SelectionModelListener> listeners = new ArrayList<SelectionModelListener>();
    private boolean changing = false;
    
    /** Creates a new instance of SelectionModelAdapter */
    public SelectionModelAdapter(DataSelector ds) {
        assert ds != null;
        this.selector = ds;
        this.selector.addPropertyChangeListener("rowIndices",  new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (!changing) {
                    changing = true;
                    SelectionModelEvent e = new SelectionModelEvent(
                            SelectionModelAdapter.this, 0, 
                            selector.getDataTable().getRows().size() - 1);
                    for (SelectionModelListener listener : listeners) {
                        listener.selectionChanged(e);
                    }
                    changing = false;
                }
            }
        });
        
    }

    public void addSelectionModelListener(SelectionModelListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeSelectionModelListener(SelectionModelListener listener) {
        listeners.remove(listener);
    }

    public void setSelectionIndices(int[] indices) {
        changing = true;
        selector.setRowIndices(indices);
        changing = false;
    }

    public int[] getSelectionIndices() {
        List<Integer> indices = selector.getRowIndices();
        int[] results = new int[indices.size()];
        for (int i=0; i<results.length; i++) {
            results[i] = indices.get(i);
        }
        return results;
    }
}