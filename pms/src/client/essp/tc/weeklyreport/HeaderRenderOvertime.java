package client.essp.tc.weeklyreport;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import client.essp.tc.common.TcLineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class HeaderRenderOvertime extends DefaultTableCellRenderer {
    TcLineBorder tableBorder = null;

    public HeaderRenderOvertime() {
        setBackground(UIManager.getColor("TableHeader.background"));

        tableBorder = new TcLineBorder(UIManager.getColor(
                "Table.gridColor"));
        tableBorder.setShowTop(false);
        tableBorder.setShowLeft(false);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        JComponent comp = (JComponent)super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        comp.setBorder(tableBorder);

        String columnName = table.getColumnName(column);
        for (int i = 0; i < VMTableWeeklyReport.WEEK_TITLE.length; i++) {
            if( VMTableWeeklyReport.WEEK_TITLE[i].equals(columnName) ||
                   VMTableWeeklyReport.SUMMARY.equals(columnName) ){
                ((JLabel)comp).setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            }else{
                ((JLabel)comp).setHorizontalAlignment(SwingConstants.LEFT);
            }
        }
        return comp;
    }
}
