package client.essp.timesheet.weeklyreport.detail;

import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.VWJTableEditor;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JTable;
import client.essp.timesheet.weeklyreport.common.VWJTwoNumReal;
import client.framework.model.VMTable2;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VWJTsTableEditor extends VWJTableEditor {
	public final static int DEFAULT_START_ACCOUNT = 1;
    VMTable2 model;
    Component comp;
    public VWJTsTableEditor(Component comp, VMTable2 model) {
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

        //System.out.println("向编辑器设值:"+value.toString());

        editorComponent.setUICValue(value);
        if(model != null) {
            DtoTimeSheetDetail dto = (DtoTimeSheetDetail) model.getRow(row);
            if(dto != null && dto.getIsLeaveType() && editorComponent instanceof VWJTwoNumReal) {
                ((VWJTwoNumReal) editorComponent).setCanInputSecondNum(false);
            }
        }
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
            return ((MouseEvent) e).getClickCount() >= DEFAULT_START_ACCOUNT;
        }
		return true;
	}
    


}
