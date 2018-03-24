/*
 * $Id: DataSelector.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2005 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import org.jdesktop.dataset.NameGenerator;

/**
 *
 * @author rbair
 */
public class DataSelector {
    //protected for testing
    protected static final String DEFAULT_NAME_PREFIX = "DataSelector";
    private static final NameGenerator NAMEGEN = new NameGenerator(DEFAULT_NAME_PREFIX);

    private DataTable table;
    private String name;
	/**
	 * This variable tracks which records are selected. If the bit at position
	 * <i>n</i> is 1, then the <i>nth</i> record is selected.
	 */
    private BitSet indices = new BitSet(0);
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	    
    /** Creates a new instance of DataSelector */
    public DataSelector(DataTable table) {
        assert table != null;
        this.table = table;
        name = NAMEGEN.generateName(this);
    }
    
    public DataTable getDataTable() {
        return table;
    }
    
	/**
	 * @param name
	 */
	public void setName(String name) {
        if (this.name != name) {
            assert name != null && !name.trim().equals("");
            String oldName = this.name;
            this.name = name;
            pcs.firePropertyChange("name", oldName, name);
        }
	}

	public String getName() {
		return name;
	}

    public List<Integer> getRowIndices() {
        List<Integer> results = new ArrayList<Integer>(indices.cardinality());
        int n=-1;
        for(int i=0; i<indices.cardinality(); i++) {
            n = indices.nextSetBit(n+1);
            results.add(n);
        }
        return Collections.unmodifiableList(results);
    }
    
    public void setRowIndices(int[] rowIndices) {
        // JW: to honour the bean spec
        List oldIndices = getRowIndices();
    	indices.clear();
    	for (int index : rowIndices) {
    		indices.set(index);
    	}
//    	fireSelectionModelChanged(new SelectionModelEvent(this, 0, selectedRecords.length()-1));
        //TODO I'm abusing the property change listener here to get out the selection
        //change. I need a real event, I think.
        // JW: until then we can honour the bean spec with the following line:
    	pcs.firePropertyChange("rowIndices", oldIndices, getRowIndices());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property,  listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName,  listener);
    }
    
}
