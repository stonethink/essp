package org.jdesktop.swing.binding;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import org.jdesktop.swing.data.DataModel;
import org.jdesktop.swing.data.DataModelToTableModelAdapter;
import org.jdesktop.swing.data.MetaData;
import org.jdesktop.swing.data.SelectionModel;
import org.jdesktop.swing.data.TabularDataModel;
import org.jdesktop.swing.data.ValueChangeEvent;
import org.jdesktop.swing.data.ValueChangeListener;

/**
 * This "Binding" happens to the given DataModel as a whole (as
 * opposed to a single field of the model).
 * 
 * @author Richard Bair
 */
public class DirectTableBinding extends AbstractBinding {
	private JTable table;
	
	/**
	 * @param component
	 * @param dataModel
	 */
	public DirectTableBinding(JTable component, TabularDataModel dataModel) {
        this(component, dataModel, null);
//		super(component, dataModel, "", DirectTableBinding.AUTO_VALIDATE_NONE);
//		//construct a TableModel for the table based on the given dataModel.
//		TableModel tm = createAdapter(dataModel, null);
//		table.setModel(tm);
	}
	
	public DirectTableBinding(JTable component, TabularDataModel dataModel, String[] fieldNames) {
//		super(component, dataModel, "", DirectTableBinding.AUTO_VALIDATE_NONE);
//		//construct a TableModel for the table based on the given dataModel.
//		TableModel tm = createAdapter(dataModel, fieldNames);
//		table.setModel(tm);
        this(component, dataModel, fieldNames, null);
	}

    public DirectTableBinding(JTable component, TabularDataModel dataModel, 
            String[] fieldNames, SelectionModel selectionModel) {
        super(component, dataModel, "", DirectTableBinding.AUTO_VALIDATE_NONE);
        //construct a TableModel for the table based on the given dataModel.
        TableModel tm = createAdapter(dataModel, fieldNames);
        table.setModel(tm);
        if (selectionModel != null) {
           new ListSelectionBinding(selectionModel, table.getSelectionModel());
        }
    }

    public boolean push() {
        return true;
    }
    
    public boolean pull() {
        return true;
    }
    
    public boolean isValid() {
        return true;
    }
    
    protected TableModel createAdapter(TabularDataModel tabularDataModel, String[] fieldNames) {
        return new DataModelToTableModelAdapter(tabularDataModel, fieldNames);
    }
	/* (non-Javadoc)
	 * @see org.jdesktop.swing.binding.AbstractUIBinding#getBoundComponent()
	 */
	protected JComponent getBoundComponent() {
		return table;
	}

	/* (non-Javadoc)
	 * @see org.jdesktop.swing.binding.AbstractUIBinding#setBoundComponent(javax.swing.JComponent)
	 */
	protected void setBoundComponent(JComponent component) {
		if (!(component instanceof JTable)) {
			throw new IllegalArgumentException("TableBindings only accept a JTable or one of its child classes");
		}
		this.table = (JTable)component;
	}

	/* (non-Javadoc)
	 * @see org.jdesktop.swing.binding.AbstractUIBinding#getComponentValue()
	 */
	protected Object getComponentValue() {
		//a table component never updates its parent data model in this way
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jdesktop.swing.binding.AbstractUIBinding#setComponentValue(java.lang.Object)
	 */
	protected void setComponentValue(Object value) {
		//means nothing to this binding
	}

    // JW: this is extracted as DataModelToTableModelAdapter
    // what an ugly name but couldn't come up with anything nicer
    
//	private static final class DataModelTableModel extends AbstractTableModel {
//		private TabularDataModel dm;
//		private String[] fieldNames;
//		
//		public DataModelTableModel(final TabularDataModel dm) {
//			this.dm = dm;
//			fieldNames = dm.getFieldNames();
//			//register with the data model
//			dm.addValueChangeListener(new ValueChangeListener() {
//				public void valueChanged(ValueChangeEvent e) {
//					fireTableStructureChanged();
////					fireTableDataChanged();
//				}
//			});
//		}
//		
//		public DataModelTableModel(final TabularDataModel dm, final String[] visibleFieldNames) {
//			this.dm = dm;
//			fieldNames = visibleFieldNames/*dm.getFieldNames()*/;
//			//register with the data model
//			dm.addValueChangeListener(new ValueChangeListener() {
//				public void valueChanged(ValueChangeEvent e) {
//					fireTableStructureChanged();
////					fireTableDataChanged();
//				}
//			});
//		}
//		
//		public Class getColumnClass(int columnIndex) {
//			return dm.getMetaData(fieldNames[columnIndex]).getElementClass();
//		}
//		
//		public String getColumnName(int column) {
//			//its possible that the meta data hasn't shown up yet. In this
//			//case, use the field name until the meta data arrives
//			MetaData md = dm.getMetaData(fieldNames[column]);
//			return md == null ? fieldNames[column] : md.getLabel();
//		}
//		
//		public boolean isCellEditable(int rowIndex, int columnIndex) {
//			return !dm.getMetaData(fieldNames[columnIndex]).isReadOnly();
//		}
//		
//		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
////			dm.setValue(fieldNames[columnIndex], aValue, rowIndex);
//		}
//		
//		/* (non-Javadoc)
//		 * @see javax.swing.table.TableModel#getRowCount()
//		 */
//		public int getRowCount() {
//			return dm.getRecordCount();
//		}
//
//		/* (non-Javadoc)
//		 * @see javax.swing.table.TableModel#getColumnCount()
//		 */
//		public int getColumnCount() {
//			return fieldNames == null ? dm.getFieldCount() : fieldNames.length;
//		}
//
//		/* (non-Javadoc)
//		 * @see javax.swing.table.TableModel#getValueAt(int, int)
//		 */
//		public Object getValueAt(int rowIndex, int columnIndex) {
//			return dm.getValueAt(fieldNames[columnIndex], rowIndex);
//		}
//		
//	}

	/* (non-Javadoc)
	 * @see org.jdesktop.jdnc.incubator.rbair.swing.binding.AbstractBinding#getComponent()
	 */
	public JComponent getComponent() {
		return getBoundComponent();
	}

	/* (non-Javadoc)
	 * @see org.jdesktop.jdnc.incubator.rbair.swing.binding.AbstractBinding#setComponent(javax.swing.JComponent)
	 */
	public void setComponent(JComponent component) {
		setBoundComponent(component);
	}

}
