package client.essp.common.calendar;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * <p>Title: </p>
 * <p>Description: 日历的描绘器</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */
public class CalendarCellRenderer extends DefaultTableCellRenderer {
    private Color holidayForeground = Color.black;
    private Color holidayBackground = Color.lightGray;
    private Color todayForeground   = Color.black;
    private Color todayBackground   = Color.yellow;

    public CalendarCellRenderer() {
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
        if (!(table.getModel() instanceof TableModelCalendar)) {
            return false;
        }

        TableModelCalendar tblMode = (TableModelCalendar) table.getModel();

        return tblMode.isToday(row, column);
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
        //        Component aComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        //        return aComp;
        if (isSelected) {
            super.setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else if (isToday(table, row, column)) {
            this.setForeground(todayForeground);
            this.setBackground(todayBackground);
        } else if ((column == 0) || (column == 6)) {
            this.setForeground(holidayForeground);
            this.setBackground(holidayBackground);
        } else {
            super.setForeground(table.getForeground());
            super.setBackground(table.getBackground());
        }

        setFont(table.getFont());
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

        setValue(value);

        return this;
    }

    /**
     * 取假日的前景色
     * @return
     */
    public Color getHolidayBackground() {
        return holidayBackground;
    }

    /**
     * 取假日的背景色
     * @return
     */
    public Color getHolidayForeground() {
        return holidayForeground;
    }

    /**
     * 设置假日背景色
     * @param HolidayBackground
     */
    public void setHolidayBackground(Color holidayBackground) {
        this.holidayBackground = holidayBackground;
    }

    /**
     * 设置假日前景色
     * @param HolidayForeground
     */
    public void setHolidayForeground(Color holidayForeground) {
        this.holidayForeground = holidayForeground;
    }
}
