package client.essp.pwm.workbench.workitem;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import c2s.essp.pwm.wp.DtoPwWkitem;
import com.uic.timeBar.PanelTimeBar;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class CellRenderTimeBar extends DefaultTableCellRenderer {

    private java.awt.Color timeBarForeColor = new Color(77,112,170);
    private java.awt.Color timeBarBackColor = Color.WHITE;

    protected com.uic.timeBar.PanelTimeBar panelTimeBar = new PanelTimeBar();

    public CellRenderTimeBar() {
        panelTimeBar.getTimeBar().setShowScale(false);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {

        if (value instanceof java.util.Calendar[]) { //时间拖动组件

            java.util.Calendar[] calendars = (java.util.Calendar[])value;
            panelTimeBar.setBeginTime(calendars[0]);
            panelTimeBar.setEndTime(calendars[1]);
//            if (isSelected) {
//                panelTimeBar.setForeground(table.getSelectionForeground());
//                panelTimeBar.setBackground(table.getSelectionBackground());
//                panelTimeBar.getTimeBar().setForeground(table.getSelectionForeground());
//                panelTimeBar.getTimeBar().setBackground(table.getSelectionBackground());
//            }
//            else {
                panelTimeBar.setForeground(table.getForeground());
                panelTimeBar.setBackground(table.getBackground());
                panelTimeBar.getTimeBar().setForeground(timeBarForeColor);
                panelTimeBar.getTimeBar().setBackground(timeBarBackColor);
//            }
            return panelTimeBar;
        }
        else {

            return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
        }
    }

    public com.uic.timeBar.PanelTimeBar getPanelTimeBar() {
        return panelTimeBar;
    }

}
