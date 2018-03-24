package client.essp.timesheet.weeklyreport.detail;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JTable;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;
import client.essp.timesheet.weeklyreport.common.VWJTwoNumReal;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.VWJTableEditor;
import client.framework.view.vwcomp.VWJText;

public class VWJDescTableEditor extends VWJTableEditor {
	private final static VWJText text = new VWJText();
	private Component comp;
    public VWJDescTableEditor() {
        super(text);
        comp = text;
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

        //System.out.println("向编辑器设值:"+value.toString());

        editorComponent.setUICValue(value);

        return comp;
    }
    
    public Object getCellEditorValue() {
        //System.out.println("invoke editorComponent.getUICValue()=" + ((IVWComponent)(config.getComponent())).getUICValue());
        //System.out.println("从编辑器取值:"+((IVWComponent)comp).getUICValue().toString());
        return ((IVWComponent) comp).getUICValue();
    }
    
    @Override
	public boolean isCellEditable(EventObject e) {
    	if (e instanceof MouseEvent) {
            return ((MouseEvent) e).getClickCount() >= VWJTsTableEditor.DEFAULT_START_ACCOUNT;
        }
		return true;
	}
}
