package client.essp.timesheet.weeklyreport.summary;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import c2s.essp.timesheet.weeklyreport.DtoAttLeave;
import c2s.essp.timesheet.weeklyreport.DtoAttOvertime;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDay;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJReal;
import client.essp.timesheet.weeklyreport.common.VWJTwoNumReal;


public class RowRenderer implements TableCellRenderer {

    private final static int DEFAULT_DECIMAL_DIGHT = 2;
    private final static Color color = new Color(200, 200, 200);
    private final static Color noColor = new Color(255, 255, 255);
    private final static Double noVariantValue = 0D;
    private NodeViewManager nodeViewManager = new NodeViewManager();
    private int decimalDigit = DEFAULT_DECIMAL_DIGHT;
    private Color selectedBackgroundColor = color;
    private VWJTwoNumReal comp;

    public static RowRenderer defaultRowRenderer = new RowRenderer();

    private RowRenderer() {
        comp = new VWJTwoNumReal();
        comp.setMaxInputDecimalDigit(decimalDigit);

    }

    public RowRenderer(VWJTwoNumReal comp) {
        this.comp = comp;
    }

    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        comp.setUICValue(value);
        comp.setBorder(null);
        comp.setFont(table.getFont());
        if (row % 2 == 0) {
            comp.setBackground(nodeViewManager.getOddBackground()); //设置奇数行底色
        } else if (row % 2 == 1) {
            comp.setBackground(nodeViewManager.getEvenBackground()); //设置偶数行底色
        }

        if (isSelected) {
            if (this.selectedBackgroundColor == null) {
                comp.setForeground(nodeViewManager.getSelectForeground());
                comp.setBackground(nodeViewManager.getSelectBackground());
            } else {
                comp.setForeground(Color.black);
                comp.setBackground(this.selectedBackgroundColor);
            }
        } else {
            comp.setForeground(nodeViewManager.getForeground());
        }

        if (row == VMTableSum.VARIANT_ROW_INDEX && value instanceof DtoTimeSheetDay) {
        	DtoTimeSheetDay day = (DtoTimeSheetDay) value;
        	if((day.getOvertimeHours() != null && !noVariantValue.equals(day.getWorkHours()))
        			|| (day.getOvertimeHours() != null && !noVariantValue.equals(day.getOvertimeHours()))) {
        		comp.setForeground(Color.RED);
        	}
        }
        if(value instanceof DtoAttLeave) {
        	comp.setToolTipText(((DtoAttLeave) value).getTipText());
        } else if(value instanceof DtoAttOvertime) {
        	comp.setToolTipText(((DtoAttOvertime) value).getTipText());
        }
        return ((JComponent) comp);
    }

    public void setSelectedBackgroundColor(Color color) {
        this.selectedBackgroundColor = color;
    }
}
