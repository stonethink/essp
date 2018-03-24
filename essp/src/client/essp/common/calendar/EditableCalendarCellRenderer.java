package client.essp.common.calendar;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.Font;


public class EditableCalendarCellRenderer extends CalendarCellRenderer {
    private Color holidayForeground = Color.black;
    private Color holidayBackground = Color.lightGray;
    private Color todayForeground   = Color.black;
    private Color todayBackground   = Color.yellow;
    private Color monthEndForeground = Color.black;
    private Color monthEndBackground = Color.red;
    private static final int months = 25;
    public EditableCalendarCellRenderer() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 判断日历表某个单元格是不是今天
     * @param table
     * @param row
     * @param column
     * @return
     */
    private static boolean isToday(JTable table,
                                   int    row,
                                   int    column) {
        if (!(table.getModel() instanceof EditableCalendarTableModel)) {
            return false;
        }

        EditableCalendarTableModel tblMode = (EditableCalendarTableModel) table
                                             .getModel();

        return tblMode.isToday(row, column);
    }

    private static boolean isMonthEnd(JTable table,
                                   int    row,
                                   int    column){
        if (!(table.getModel() instanceof EditableCalendarTableModel)) {
           return false;
        }
        EditableCalendarTableModel tblMode = (EditableCalendarTableModel) table
                                             .getModel();
        int celldate = tblMode.getCellDate(row,column);
        if(celldate == months){
            return true;
        }
        return false;
    }

    /**
     * 描绘器接口需的主要方法
     * @param table 表格引用
     * @param value 单元内容
     * @param isSelected 是否选中
     * @param hasFocus 是否有焦点
     * @param row 行号
     * @param column 列号
     * @return 描绘组件
     */
    public Component getTableCellRendererComponent(JTable  table,
                                                   Object  value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int     row,
                                                   int     column) {
        CellValue cellValue = (CellValue) value;

        Font font = new Font("Arial",2,9);
        if (cellValue.isCurrentmonth()) {
            if (isSelected) {
                super.setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else if (isToday(table, row, column)) {
                this.setForeground(todayForeground);
                this.setBackground(todayBackground);
            } else if (isMonthEnd(table, row, column)) {
                this.setForeground(monthEndForeground);
                this.setBackground(monthEndBackground);
            } else if (cellValue.isWorkday()) {
                super.setForeground(table.getForeground());
                super.setBackground(table.getBackground());
            } else {
                this.setForeground(holidayForeground);
                this.setBackground(holidayBackground);
            }
            setFont(table.getFont());
        }else{
            if (isSelected) {
                super.setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else if (cellValue.isWorkday()) {
                super.setForeground(table.getForeground());
                super.setBackground(table.getBackground());
            } else {
                this.setForeground(holidayForeground);
                this.setBackground(holidayBackground);
            }
            setFont(font);
        }
        setHorizontalAlignment(SwingConstants.CENTER);

        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));

            if (table.isCellEditable(row, column)) {
                super.setForeground(UIManager.getColor("Table.focusCellForeground"));
                super.setBackground(UIManager.getColor("Table.focusCellBackground"));
            }
        } else {
            setBorder(noFocusBorder);
        }

        setValue(cellValue.getDayValue());

        return this;
    }

    private void jbInit() throws Exception {
    }
}
