package client.essp.timesheet.step.management;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import c2s.essp.timesheet.step.management.DtoStepBase;
import client.framework.view.vwcomp.IVWComponent;

public class StatusCellRenderer implements TableCellRenderer {
	private static final DefaultTableCellRenderer defaultRender = new DefaultTableCellRenderer();
	private Component comp = null;

	public StatusCellRenderer(Component comp) {
		this.comp = comp;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int columnIndex) {

		if (comp == null || !(comp instanceof IVWComponent)) {
			comp = defaultRender.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, columnIndex);
		} else {
			IVWComponent vwComp = (IVWComponent) comp;
			vwComp.setUICValue(value);
		}
		if (DtoStepBase.AHEAD.equals(value.toString())) {
			comp.setBackground(Color.BLUE);
		} else if (DtoStepBase.NORMAL.equals(value.toString())) {
			comp.setBackground(Color.GREEN);
		} else if (DtoStepBase.DELAY.equals(value.toString())) {
			comp.setBackground(Color.RED);
		}
		if (comp instanceof JComponent) {
			((JComponent) comp).setBorder(BorderFactory.createEmptyBorder());
		}

		return comp;
	}

}
