package client.essp.common.calendar;

import java.awt.*;
import java.util.*;

import c2s.dto.*;
import c2s.essp.common.calendar.*;
import client.net.*;
import com.essp.calendar.*;
import org.apache.log4j.*;
import client.framework.view.common.comMSG;


/**
 * <p>Title: test</p>
 * <p>Description: 日历的表格模型 </p>
 * <p>Copyright: Enovation Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Xuxi.Chen
 * @version 1.0
 */
public class EditableCalendarTableModel
    extends javax.swing.table.DefaultTableModel {
    protected static String[] weekNames = new String[] {
                                              "Sat","Sun", "Mon", "Tue", "Wed", "Thu",
                                              "Fri"
                                          };

    //    VDView_Data currVD = new VDView_Data(); //当前的view_Data
    static Category                log              = Category.getInstance("client");
    protected int                  year;
    protected int                  month;
    private WorkDay[]              workDay;
    private WorkRange              workRange;
    private java.text.NumberFormat nf               = java.text.NumberFormat
                                                      .getInstance();
    private Calendar               firstDayCalendar = java.util.Calendar
                                                      .getInstance();
    private Calendar               todayCalendar = java.util.Calendar
                                                   .getInstance();
    private CellValue[][]          dataString = new CellValue[6][7];

    //    String controllerUrl = HandleServlet.getServerURL();
    String actionId = "F00_Calendar";
    //用于计算某单元格中放入日期的参数
    private int dateNum = 1;
    //保存原来的参数dateNum
    private int origDateNum = 1;
    //用于计算当前日期的参数
    private int dateToday = -1;
    //用于存放原来的参数dateToday
    private int orgiDateToday = -1;
    /**
     * 默认的构造方法，用当前日期初始化
     */
    public EditableCalendarTableModel() {
        Calendar calendar = todayCalendar;
        this.year               = calendar.get(Calendar.YEAR);
        this.month              = calendar.get(Calendar.MONTH) + 1;
        initColumn();
        getSettings();
    }
    /**
     * 初始化列的名称及顺序（此方法将会被重载）
     */
    protected void initColumn(){
         super.columnIdentifiers = super.convertToVector(weekNames);
    }
    /**
     * 设置actionId
     */
    public void setActionId(String actionId){
       this.actionId = actionId;
    }
    /**
     * 构造方法，用指定的年月初始化
     * @param year
     * @param month
     */
    public EditableCalendarTableModel(int year,
                                      int month) {
        this.year               = year;
        this.month              = month;
        super.columnIdentifiers = super.convertToVector(weekNames);
        getSettings();
    }

    /**
     * 取某单元的日期值
     * @param row 行
     * @param col 列
     * @return 日期
     */
    public int getCellDate(int row,
                           int col) {
        if (dataString[row][col].getDayValue().trim().length() == 0) {
            return -1;
        } else {
            return Integer.parseInt(dataString[row][col].getDayValue());
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
        if(row == 0){
           if( col>= 0 || col<= 6){
              if( date > 7 ){
                  calendar.set(this.year, this.month - 2, date);
                  return calendar;
              }
           }
        }else if(row > 3){
            if(date < 15){
               calendar.set(this.year, this.month , date);
               return calendar;
            }
        }
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
        int seq = ((iDate + dayOfWeek) + dateToday);
        int row = seq / this.getColumnCount();
        int col = seq % this.getColumnCount();
        dateToday = orgiDateToday;//还原为修改之前的参数
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
    public void calcData() {
        nf.setMinimumIntegerDigits(2);

        Calendar calendar = firstDayCalendar;
        calendar.set(this.year, this.month - 1, 1);

        //规整化年与月；
        this.year  = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;

        int maxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        origDateNum = dateNum;//保存原先的参数
        orgiDateToday = dateToday;
        for (int i = 0; i < dataString.length; i++) {
            for (int j = 0; j < dataString[i].length; j++) {
                int       date = ((i * 7) + j) - dayOfWeek + dateNum;
                //如果第一单元格要放入的日期距离当前月份一号超过一周
                //则直接将下一行日期向上移一行显示
                if(date<-5){
                    dateNum = dateNum + 7;//修改计算日期的参数
                    dateToday = - dateNum;//修改用于计算选中当天日期的参数
                    i=-1;//上移一行
                    break;
                }
                CellValue cell = new CellValue();

                if ((date > 0) && (date <= maxDate)) {
                    cell.setDayValue(nf.format(date));

                    GregorianCalendar gc = new GregorianCalendar(this.year,
                                                                 this.month - 1,
                                                                 date);
                    int               day = gc.get(Calendar.DAY_OF_YEAR) - 1;

                    try {
                        cell.setWorkday(workDay[day].isWorkDay());
                        cell.setCurrentmonth(true);
                    } catch (Exception ex) {
                        if ((gc.get(Calendar.DAY_OF_WEEK) == 1)
                                || (gc.get(Calendar.DAY_OF_WEEK) == 7)) {
                            cell.setWorkday(true);
                        } else {
                            cell.setWorkday(false);
                        }
                    }
                }else if(date <= 0 && this.month != 1){
                    Calendar lastcalendar = java.util.Calendar
                                                      .getInstance();
                    lastcalendar.set(this.year, this.month - 2, 1);
                    int lastmaxDate = lastcalendar.getActualMaximum(
                        lastcalendar.DAY_OF_MONTH);
                    cell.setDayValue(nf.format(date + lastmaxDate));
                    GregorianCalendar gc = new GregorianCalendar(this.year,
                        this.month - 2,
                        date + lastmaxDate);
                    int day = gc.get(lastcalendar.DAY_OF_YEAR) - 1;
                    cell.setWorkday(workDay[day].isWorkDay());
                    cell.setCurrentmonth(false);

                }else if(date > maxDate && this.month != 12){
                    Calendar nextcalendar = java.util.Calendar
                                                      .getInstance();
                    nextcalendar.set(this.year, this.month, 1);
                    cell.setDayValue(nf.format(date - maxDate));
                    GregorianCalendar gc = new GregorianCalendar(this.year,
                                                                 this.month,
                                                                 date - maxDate);
                    int               day = gc.get(nextcalendar.DAY_OF_YEAR) - 1;
                    cell.setWorkday(workDay[day].isWorkDay());
                    cell.setCurrentmonth(false);

                }
                dataString[i][j] = cell;
            }
        }
        dateNum = origDateNum;//还原为修改之前的参数

        //super.setDataVector(dataString, weekNames);
        super.dataVector = super.convertToVector(dataString);
        this.fireTableDataChanged();
    }

    /**
     * 获取数据
     */
    public void getSettings() {
        log.debug("Get Settings");

        String          funId     = "get";
        TransactionData transData = new TransactionData();

        //提交信息（条件）
        InputInfo inputInfo = transData.getInputInfo();

        //        inputInfo.setControllerUrl(controllerUrl);
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        //规整化年与月；
        Calendar calendar = firstDayCalendar;
        calendar.set(this.year, this.month - 1, 1);
        this.year  = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;

        //将查询条件放入
        inputInfo.setInputObj("WorkYear", new Integer(this.year));

        //连接servlet，执行操作
        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        //返回信息（结果）
        ReturnInfo returnInfo = transData.getReturnInfo();

        if (returnInfo.isError() == false) {
            String workDayString = (String) returnInfo.getReturnObj("WorkDayString");
            workRange    = WorkRange.getInstance(workDayString);
            this.workDay = workRange.getWorkDays();
            calcData();
            log.debug("Getting Settings Successfully");
        } else {
            //报错， 初始化workRange， add by lipengxu 2007-09-30
            comMSG.dispErrorDialog(returnInfo.getReturnCode());
            workRange = WorkRange.getInstance(this.year + "-01-01~" + this.year + "-12-31+");

            this.workDay = workRange.getWorkDays();
            calcData();
            log.error(returnInfo.getReturnMessage());
        }
    }

    /**
     * 保存对一年数据的修改
     * @return int
     */
    public void saveSettings() {
        log.debug("Save Settings");

        String          funId     = "save";
        TransactionData transData = new TransactionData();

        //提交信息（条件）
        InputInfo inputInfo = transData.getInputInfo();

        //        inputInfo.setControllerUrl(controllerUrl);
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        DtoTcWt dtoTcWt = new DtoTcWt();
        dtoTcWt.setWtsYear(new Integer(this.year));
        dtoTcWt.setWtsDays(workRange.toString());
        inputInfo.setInputObj("DtoTcWt", dtoTcWt);

        //连接servlet，执行操作
        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        //返回信息（结果）
        ReturnInfo returnInfo = transData.getReturnInfo();

        if (returnInfo.isError() == false) {
            log.debug("Save Settings Successfully");
        } else {
            log.error(returnInfo.getReturnMessage());
        }
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

    public WorkDay[] getWorkDay() {
        return workDay;
    }

    public int getDateNum() {
        return dateNum;
    }

    public int getDateToday() {
        return dateToday;
    }

    public void setWorkDay(WorkDay[] workDay) {
        this.workDay = workDay;
    }

    public void setDateNum(int dateNum) {
        this.dateNum = dateNum;
    }

    public void setDateToday(int dateToday) {
        this.dateToday = dateToday;
    }
}
