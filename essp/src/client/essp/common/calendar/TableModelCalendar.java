package client.essp.common.calendar;

import java.awt.Point;

import java.util.Calendar;

import javax.swing.table.DefaultTableModel;
import client.framework.common.Global;


/**
 * <p>Title: test</p>
 * <p>Description: 日历的表格模型 </p>
 * <p>Copyright: Enovation Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Xuxi.Chen
 * @version 1.0
 */
public class TableModelCalendar extends DefaultTableModel {
    protected static String[] weekNames = new String[] {
                                              "Sun", "Mon", "Tue", "Wed", "Thu",
                                              "Fri", "Sat"
                                          };
    protected int             year;
    protected int             month;
    private java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
    private Calendar          firstDayCalendar = java.util.Calendar.getInstance();
    private Calendar          todayCalendar    = java.util.Calendar.getInstance();
    private String[][]        dataString       = new String[6][7];

    /**
     * 默认的构造方法，用当前日期初始化
     */
    public TableModelCalendar() {
        firstDayCalendar.setTime(Global.todayDate);
        todayCalendar.setTime(Global.todayDate);
        Calendar calendar = firstDayCalendar;
        this.year               = calendar.get(Calendar.YEAR);
        this.month              = calendar.get(Calendar.MONTH) + 1;
        super.columnIdentifiers = super.convertToVector(weekNames);
        calcData();
    }

    /**
     * 构造方法，用指定的年月初始化
     * @param year
     * @param month
     */
    public TableModelCalendar(int year,
                              int month) {
        this.year               = year;
        this.month              = month;
        super.columnIdentifiers = super.convertToVector(weekNames);
        calcData();
    }

    /**
     * 取某单元的日期值
     * @param row 行
     * @param col 列
     * @return 日期
     */
    public int getCellDate(int row,
                           int col) {
        if (dataString[row][col].trim().length() == 0) {
            return -1;
        } else {
            return Integer.parseInt(dataString[row][col]);
        }
    }

    /**
     * 取某单元的日历
     * @param row 行
     * @param col 列
     * @return 日历
     */
    public Calendar getCellCalendar(int row,
                                    int col) {
        int date = getCellDate(row, col);

        if (date < 0) {
            return null;
        }

        Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(this.year, this.month - 1, date);

        return calendar;
    }

    /**
     * 某个单元格是否为今天
     * @param row
     * @param col
     * @return
     */
    public boolean isToday(int row,
                           int col) {
        //Calendar todayCalendar = java.util.Calendar.getInstance();
        return (this.year == todayCalendar.get(Calendar.YEAR))
               && ((this.month - 1) == todayCalendar.get(Calendar.MONTH))
               && (this.getCellDate(row, col) == todayCalendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 取最接近某天所在的行与列，如果是二月29日，可能指向2月28日那一格
     * @param date 日期值
     * @return 单元格点
     */
    public Point getDateCell(int date) {
        int iDate = date;
        int min = firstDayCalendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int max = firstDayCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (iDate < min) {
            iDate = min;
        } else if (iDate > max) {
            iDate = max;
        }

        int dayOfWeek = firstDayCalendar.get(Calendar.DAY_OF_WEEK);
        int seq = ((iDate + dayOfWeek) - 2);
        int row = seq / this.getColumnCount();
        int col = seq % this.getColumnCount();

        return new Point(col, row);
    }

    /**
     * 取某天所在的行
     * @param date 日期值
     * @return 单元行
     */
    public int getDateRow(int date) {
        Point p = getDateCell(date);

        if (p == null) {
            return -1;
        }

        return p.y;
    }

    /**
     * 取某天所在的列
     * @param date 日期值
     * @return 单元格列
     */
    public int getDateCol(int date) {
        Point p = getDateCell(date);

        if (p == null) {
            return -1;
        }

        return p.x;
    }

    /**
     * 不可编辑
     * @param row
     * @param column
     * @return
     */
    public boolean isCellEditable(int row,
                                  int column) {
        return false;
    }

    /**
     * 计算一个月的日历
     */
    protected void calcData() {
        nf.setMinimumIntegerDigits(2);

        Calendar calendar = firstDayCalendar;
        calendar.set(this.year, this.month - 1, 1);

        //规整化年与月；
        this.year  = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;

        int maxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        for (int i = 0; i < dataString.length; i++) {
            for (int j = 0; j < dataString[i].length; j++) {
                int date = ((i * 7) + j) - dayOfWeek + 2;

                if ((date > 0) && (date <= maxDate)) {
                    dataString[i][j] = nf.format(date);
                } else {
                    dataString[i][j] = "  ";
                }
            }
        }

        //super.setDataVector(dataString, weekNames);
        super.dataVector = super.convertToVector(dataString);
        this.fireTableDataChanged();
    }

    /**
     * 取年
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     * 设置年份，并重新计算日历
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
        calcData();
    }

    /**
     * 取月份
     * @return
     */
    public int getMonth() {
        return month;
    }

    /**
     * 设置月份，并重新计算日历
     * @param month
     */
    public void setMonth(int month) {
        this.month = month;
        calcData();
    }

    //for test
    public static void main(String[] args) {
        TableModelCalendar calendarTableModel1 = new TableModelCalendar();

        for (int i = 0; i < calendarTableModel1.dataString.length; i++) {
            for (int j = 0; j < calendarTableModel1.dataString[i].length;
                     j++) {
                System.out.print(calendarTableModel1.dataString[i][j] + '\t');
            }

            System.out.println();
        }
    }

    /**
     * 取今天,有可能不等于本地的今日
     * @return
     */
    public Calendar getTodayCalendar() {
        return todayCalendar;
    }

    /**
     * 设置今天，系统默认今天是客户端本地的今日
     * @param todayCalendar
     */
    public void setTodayCalendar(Calendar todayCalendar) {
        this.todayCalendar = todayCalendar;
    }
}
