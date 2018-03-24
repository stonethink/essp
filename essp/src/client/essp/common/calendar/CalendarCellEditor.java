package client.essp.common.calendar;

import java.awt.Component;

import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


/**
 * <p>Title: </p>
 * <p>Description: 日历的表格编辑器</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */
public class CalendarCellEditor extends AbstractCellEditor
    implements TableCellEditor {
    private CalendarSelectComponent textField = new CalendarSelectComponent();

    public CalendarCellEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public CalendarCellEditor(JComponent ownerComp) {
        textField.getJTextField1().setEditable(false);
    }

    /**
     * Calls <code>fireEditingStopped</code> and returns true.
     * @return true
     */
    public boolean stopCellEditing() {
        if (this.textField.getWin1() != null) {
            this.textField.getWin1().hide();
        }

        return super.stopCellEditing();
    }

    /**
     * Calls <code>fireEditingCanceled</code>.
     */
    public void cancelCellEditing() {
        if (this.textField.getWin1() != null) {
            this.textField.getWin1().hide();
        }

        super.cancelCellEditing();
    }

    public Object getCellEditorValue() {
        return textField.getValue();
    }

    public Component getTableCellEditorComponent(JTable  table,
                                                 Object  value,
                                                 boolean isSelected,
                                                 int     row,
                                                 int     column) {
        textField.setValue((Date) value);

        return textField;
    }

    private void jbInit() throws Exception {
    }
}
