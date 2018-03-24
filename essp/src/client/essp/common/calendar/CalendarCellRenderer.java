package client.essp.common.calendar;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * <p>Title: </p>
 * <p>Description: �����������</p>
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
     * �ж�������ĳ����Ԫ���ǲ��ǽ���
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
     * ������ӿ������Ҫ����
     * @param table �������
     * @param value ��Ԫ����
     * @param isSelected �Ƿ�ѡ��
     * @param hasFocus �Ƿ��н���
     * @param row �к�
     * @param column �к�
     * @return ������
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
     * ȡ���յ�ǰ��ɫ
     * @return
     */
    public Color getHolidayBackground() {
        return holidayBackground;
    }

    /**
     * ȡ���յı���ɫ
     * @return
     */
    public Color getHolidayForeground() {
        return holidayForeground;
    }

    /**
     * ���ü��ձ���ɫ
     * @param HolidayBackground
     */
    public void setHolidayBackground(Color holidayBackground) {
        this.holidayBackground = holidayBackground;
    }

    /**
     * ���ü���ǰ��ɫ
     * @param HolidayForeground
     */
    public void setHolidayForeground(Color holidayForeground) {
        this.holidayForeground = holidayForeground;
    }
}
