package net.sourceforge.ganttproject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;

import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.util.DateUtils;

import org.jdesktop.jdnc.JNTreeTable;
import org.jdesktop.swing.JXTreeTable;
import org.jdesktop.swing.table.TableColumnExt;
import org.jdesktop.swing.treetable.TreeTableModel;

class GPTreeTableBase extends JNTreeTable{
	protected GPTreeTableBase(TreeTableModel model) {
		super(new JXTreeTable(model) {
			protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
				if (e.isAltDown() || e.isControlDown()) {
					putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
				}
				boolean result = super.processKeyBinding(ks, e, condition, pressed);
				putClientProperty("JTable.autoStartsEdit", Boolean.TRUE);
				return result;
			}
			
		});
	}
	
    protected TableColumnExt newTableColumnExt(int modelIndex) {
    	TableColumnExt result = new TableColumnExt(modelIndex);
    	TableCellEditor defaultEditor = getTreeTable().getDefaultEditor(getTreeTableModel().getColumnClass(modelIndex));
    	if (defaultEditor!=null) {
    		result.setCellEditor(new TreeTableCellEditorImpl(defaultEditor));
    	}
    	return result;
    }
    
    protected TableCellEditor newDateCellEditor() {
    	return new DateCellEditor();
    }

    private static class DateCellEditor extends DefaultCellEditor {
        // normal textfield background color
        private final Color colorNormal = null;

        // error textfield background color (when the date isn't correct
        private final Color colorError = new Color(255, 125, 125);

        private Date date = null;

        public DateCellEditor() {
            super(new JTextField());
        }

        public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
			JTextField result = (JTextField) super.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
			result.selectAll();
			return result;
		}
        
        public Object getCellEditorValue() {
            return new GanttCalendar(date == null ? new Date() : date);
        }

        

        public boolean stopCellEditing() {
            boolean res = true;
            try {
                date = DateUtils.parseDate(((JTextComponent)getComponent()).getText(), GanttLanguage.getInstance().getLocale());
                getComponent().setBackground(colorNormal);
                super.fireEditingStopped();
            } catch (ParseException e) {
                Toolkit.getDefaultToolkit().beep();
                getComponent().setBackground(colorError);
                res = false;
            }

            return res;
        }
    }
}
