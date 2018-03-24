package client.essp.pwm.workbench.workitem;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.util.Calendar;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import com.uic.timeBar.PanelTimeBar;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class CellEditorTimeBar extends AbstractCellEditor implements java.awt.event.ActionListener,
    TableCellEditor {
	private PanelTimeBar panelTimeBar = new PanelTimeBar();
	private JTextField beginField;
	private JTextField endField;

    public CellEditorTimeBar() {
        panelTimeBar.getTimeBar().setShowScale(false);
        panelTimeBar.addActionListener(this);
        this.beginField = this.panelTimeBar.getJTextFieldBeginTime();
        this.endField = this.panelTimeBar.getJTextFieldEndTime();
    }

    public void actionPerformed(ActionEvent e) {
        this.stopCellEditing();
    }

    public boolean stopCellEditing() {
        if (this.beginField.isFocusOwner()) {
            this.panelTimeBar.jTextFieldBeginTime_focusLost(new FocusEvent(
                panelTimeBar, 100, false));
        }
        else if (this.endField.isFocusOwner()) {
            this.panelTimeBar.jTextFieldEndTime_focusLost(new FocusEvent(
                panelTimeBar, 100, false));
        }
        return super.stopCellEditing();
    }

    /**
     * ȡֵ
     * @return
     */
    public Object getCellEditorValue() {
        return panelTimeBar.getData();
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        panelTimeBar.setData( (Calendar[]) value);
        return panelTimeBar;
    }

    public PanelTimeBar getPanelTimeBar() {
        return panelTimeBar;
    }
}
