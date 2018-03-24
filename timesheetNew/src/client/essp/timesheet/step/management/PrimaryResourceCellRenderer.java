package client.essp.timesheet.step.management;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import c2s.essp.timesheet.step.management.DtoActivityForStep;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.NodeViewManager;

public class PrimaryResourceCellRenderer implements TableCellRenderer {
	private static final DefaultTableCellRenderer defaultRender = new DefaultTableCellRenderer();
	private Component comp = null;
	private VMTable2 model;

	public PrimaryResourceCellRenderer(Component comp, VMTable2 model) {
		this.comp = comp;
		this.model = model;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int columnIndex) {
		DtoActivityForStep act = (DtoActivityForStep) this.model.getRow(row);
		if (comp == null || !(comp instanceof IVWComponent)) {
			comp = defaultRender.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, columnIndex);
		} else {
			IVWComponent vwComp = (IVWComponent) comp;
			vwComp.setUICValue(value);
		}

		if (!act.isReadonly()) {
			comp.setBackground(Color.PINK);
		} else {
			if (row % 2 == 0) {
				comp.setBackground(NodeViewManager.TableLineOddBack); // 设置偶数行底色
			} else if (row % 2 == 1) {
				comp.setBackground(NodeViewManager.TableLineEvenBack); // 设置奇数行底色
			}
		}
		if (isSelected) {
			comp.setBackground(NodeViewManager.TableSelectBack);
			comp.setForeground(NodeViewManager.TableSelectFore);
		} else {
			comp.setForeground(NodeViewManager.TableLineFore);
		}
		if (comp instanceof JComponent) {
            ((JComponent) comp).setBorder(BorderFactory.createEmptyBorder());
        }
		return comp;
	}

}
