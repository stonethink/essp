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
 * <p>Description: �����ı��ģ�� </p>
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

    //    VDView_Data currVD = new VDView_Data(); //��ǰ��view_Data
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
    //���ڼ���ĳ��Ԫ���з������ڵĲ���
    private int dateNum = 1;
    //����ԭ���Ĳ���dateNum
    private int origDateNum = 1;
    //���ڼ��㵱ǰ���ڵĲ���
    private int dateToday = -1;
    //���ڴ��ԭ���Ĳ���dateToday
    private int orgiDateToday = -1;
    /**
     * Ĭ�ϵĹ��췽�����õ�ǰ���ڳ�ʼ��
     */
    public EditableCalendarTableModel() {
        Calendar calendar = todayCalendar;
        this.year               = calendar.get(Calendar.YEAR);
        this.month              = calendar.get(Calendar.MONTH) + 1;
        initColumn();
        getSettings();
    }
    /**
     * ��ʼ���е����Ƽ�˳�򣨴˷������ᱻ���أ�
     */
    protected void initColumn(){
         super.columnIdentifiers = super.convertToVector(weekNames);
    }
    /**
     * ����actionId
     */
    public void setActionId(String actionId){
       this.actionId = actionId;
    }
    /**
     * ���췽������ָ�������³�ʼ��
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
     * ȡĳ��Ԫ������ֵ
     * @param row ��
     * @param col ��
     * @return ����
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
     * ȡĳ��Ԫ������
     * @param row ��
     * @param col ��
     * @return ����
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
     * ĳ����Ԫ���Ƿ�Ϊ����
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
     * ȡ��ӽ�ĳ�����ڵ������У�����Ƕ���29�գ�����ָ��2��28����һ��
     * @param date ����ֵ
     * @return ��Ԫ���
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
        dateToday = orgiDateToday;//��ԭΪ�޸�֮ǰ�Ĳ���
        return new Point(col, row);
    }

    /**
     * ȡĳ�����ڵ���
     * @param date ����ֵ
     * @return ��Ԫ��
     */
    public int getDateRow(int date) {
        Point p = getDateCell(date);

        if (p == null) {
            return -1;
        }

        return p.y;
    }

    /**
     * ȡĳ�����ڵ���
     * @param date ����ֵ
     * @return ��Ԫ����
     */
    public int getDateCol(int date) {
        Point p = getDateCell(date);

        if (p == null) {
            return -1;
        }

        return p.x;
    }

    /**
     * ���ɱ༭
     * @param row
     * @param column
     * @return
     */
    public boolean isCellEditable(int row,
                                  int column) {
        return false;
    }

    /**
     * ����һ���µ�����
     */
    public void calcData() {
        nf.setMinimumIntegerDigits(2);

        Calendar calendar = firstDayCalendar;
        calendar.set(this.year, this.month - 1, 1);

        //�����������£�
        this.year  = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;

        int maxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        origDateNum = dateNum;//����ԭ�ȵĲ���
        orgiDateToday = dateToday;
        for (int i = 0; i < dataString.length; i++) {
            for (int j = 0; j < dataString[i].length; j++) {
                int       date = ((i * 7) + j) - dayOfWeek + dateNum;
                //�����һ��Ԫ��Ҫ��������ھ��뵱ǰ�·�һ�ų���һ��
                //��ֱ�ӽ���һ������������һ����ʾ
                if(date<-5){
                    dateNum = dateNum + 7;//�޸ļ������ڵĲ���
                    dateToday = - dateNum;//�޸����ڼ���ѡ�е������ڵĲ���
                    i=-1;//����һ��
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
        dateNum = origDateNum;//��ԭΪ�޸�֮ǰ�Ĳ���

        //super.setDataVector(dataString, weekNames);
        super.dataVector = super.convertToVector(dataString);
        this.fireTableDataChanged();
    }

    /**
     * ��ȡ����
     */
    public void getSettings() {
        log.debug("Get Settings");

        String          funId     = "get";
        TransactionData transData = new TransactionData();

        //�ύ��Ϣ��������
        InputInfo inputInfo = transData.getInputInfo();

        //        inputInfo.setControllerUrl(controllerUrl);
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        //�����������£�
        Calendar calendar = firstDayCalendar;
        calendar.set(this.year, this.month - 1, 1);
        this.year  = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;

        //����ѯ��������
        inputInfo.setInputObj("WorkYear", new Integer(this.year));

        //����servlet��ִ�в���
        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        //������Ϣ�������
        ReturnInfo returnInfo = transData.getReturnInfo();

        if (returnInfo.isError() == false) {
            String workDayString = (String) returnInfo.getReturnObj("WorkDayString");
            workRange    = WorkRange.getInstance(workDayString);
            this.workDay = workRange.getWorkDays();
            calcData();
            log.debug("Getting Settings Successfully");
        } else {
            //���� ��ʼ��workRange�� add by lipengxu 2007-09-30
            comMSG.dispErrorDialog(returnInfo.getReturnCode());
            workRange = WorkRange.getInstance(this.year + "-01-01~" + this.year + "-12-31+");

            this.workDay = workRange.getWorkDays();
            calcData();
            log.error(returnInfo.getReturnMessage());
        }
    }

    /**
     * �����һ�����ݵ��޸�
     * @return int
     */
    public void saveSettings() {
        log.debug("Save Settings");

        String          funId     = "save";
        TransactionData transData = new TransactionData();

        //�ύ��Ϣ��������
        InputInfo inputInfo = transData.getInputInfo();

        //        inputInfo.setControllerUrl(controllerUrl);
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        DtoTcWt dtoTcWt = new DtoTcWt();
        dtoTcWt.setWtsYear(new Integer(this.year));
        dtoTcWt.setWtsDays(workRange.toString());
        inputInfo.setInputObj("DtoTcWt", dtoTcWt);

        //����servlet��ִ�в���
        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        //������Ϣ�������
        ReturnInfo returnInfo = transData.getReturnInfo();

        if (returnInfo.isError() == false) {
            log.debug("Save Settings Successfully");
        } else {
            log.error(returnInfo.getReturnMessage());
        }
    }

    /**
     * ȡ��
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     * ������ݣ������¼�������
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * ȡ�·�
     * @return
     */
    public int getMonth() {
        return month;
    }

    /**
     * �����·ݣ������¼�������
     * @param month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * ȡ����,�п��ܲ����ڱ��صĽ���
     * @return
     */
    public Calendar getTodayCalendar() {
        return todayCalendar;
    }

    /**
     * ���ý��죬ϵͳĬ�Ͻ����ǿͻ��˱��صĽ���
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
