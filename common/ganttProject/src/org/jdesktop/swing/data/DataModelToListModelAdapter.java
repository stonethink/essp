/*
 * Created on 28.02.2005
 *
 */
package org.jdesktop.swing.data;

import javax.swing.AbstractListModel;



public class DataModelToListModelAdapter extends AbstractListModel {
    TabularDataModel tabModel = null;
    String fieldName;

    public DataModelToListModelAdapter(TabularDataModel model, String fieldName) {
        this.tabModel = model;
        this.fieldName = fieldName;
        installDataModelListener();
    }
    
	protected void installDataModelListener() {
		TabularValueChangeListener l = new TabularValueChangeListener() {

            public void tabularValueChanged(TabularValueChangeEvent e) {
                fireContentsChanged(DataModelToListModelAdapter.this, 0, tabModel.getRecordCount() - 1);
                
            }
		};
        tabModel.addTabularValueChangeListener(l);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return tabModel.getRecordCount();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		return tabModel.getValueAt(fieldName, index);
	}
	
}