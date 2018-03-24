/*
 * TabularDataModel.java
 *
 * Created on February 24, 2005, 6:26 AM
 */

package org.jdesktop.swing.data;

import java.util.List;

/**
 *
 * @author rbair
 */
public interface TabularDataModel extends DataModel {
    
    /**
     *
     * @return integer containing the number of records accessible
     *         from this data model
     */
    public int getRecordCount();

    public Object getValueAt(String fieldName, int index);
    
    public void setValueAt(String fieldName, int index, Object value);
    
    public void addTabularValueChangeListener(TabularValueChangeListener l);
    
    public void removeTabularValueChangeListener(TabularValueChangeListener l);
    
    public TabularValueChangeListener[] getTabularValueChangeListeners();
}
