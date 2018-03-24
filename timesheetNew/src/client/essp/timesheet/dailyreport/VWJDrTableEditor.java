package client.essp.timesheet.dailyreport;

import java.awt.Component;
import java.awt.event.*;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import client.framework.model.VMTable2;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.VWJTableEditor;

public class VWJDrTableEditor extends VWJTableEditor {

	public final static int DEFAULT_START_ACCOUNT = 1;
    VMTable2 model;
    Component comp;
    public VWJDrTableEditor(Component comp, VMTable2 model) {
        super(comp);
        this.comp = comp;
        this.model = model;
    }
    /** Implements the <code>TableCellEditor</code> interface. */
    public Component getTableCellEditorComponent(JTable table, Object value2,
                                                 boolean isSelected, int row,
                                                 int column) {
    	Object value = value2;
        IVWComponent editorComponent = null;
        if (value == null) {
            value = "";
        }
        //System.out.println("init editor value : ["+value.toString()+"]");
        editorComponent = ((IVWComponent) comp).duplicate();
        comp = (Component) editorComponent;

        editorComponent.setErrorField(false);
        editorComponent.setToolTipText(value.toString());
        if (editorComponent.getValidatorResult() != null) {
            editorComponent.getValidatorResult().clear();
        }

        //System.out.println("Ïò±à¼­Æ÷ÉèÖµ:"+value.toString());

        editorComponent.setUICValue(value);
        
        this.addCellEditorListener(new CellEditorListener(){
			public void editingCanceled(ChangeEvent e) {
				
			}

			public void editingStopped(ChangeEvent e) {
				
			}
        	
        });

        return comp;
    }
	public boolean isCellEditable(EventObject e) {
    	if (e instanceof MouseEvent) {
            return ((MouseEvent) e).getClickCount() >= 1;
        }
		return true;
	}
	public Object getCellEditorValue() {
        return ((IVWComponent) comp).getUICValue();
    }
}
