package client.essp.timesheet.calendar;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import client.essp.common.calendar.EditableCalendarTableModel;
import client.net.ConnectFactory;
import client.net.NetConnector;
/**
 *
 * <p>Title: TimesheetCalendarModel</p>
 *
 * <p>Description: 日历显示控件的Model</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class TimesheetCalendarModel extends EditableCalendarTableModel {
    /**
     * 用于查询开始星期数的Action的ID
     */
    private final static String actionId_GetWeekNames = "FTSWeekStart";
    /**
     *  默认的日历列名及顺序
     */
    private final static String[] stardarWeekNames = new String[] {
    	    "rsid.timesheet.sat", 
    	    "rsid.timesheet.sun",
            "rsid.timesheet.mon", "rsid.timesheet.tue",
            "rsid.timesheet.wed", "rsid.timesheet.thu", 
            "rsid.timesheet.fri"};
    
    private static Integer weekStart;
    /**
     *用于计算日历中某单元格日期数的参数
     */
    private int dateNum = 1;
    public TimesheetCalendarModel() {
        super();
    }

    /**
     * 初始化列的顺序和读取工作日设置的Action的ID
     */
    protected void initColumn() {
        String[] weekNames = getWeekNamesByP3();
        super.columnIdentifiers = super.convertToVector(weekNames);
        this.setActionId("FTSCalendar");
    }
    /**
     * 根据P3设置获取日历列名及顺序
     * @return String[]
     */
    private String[] getWeekNamesByP3() {
        String[] weekNames = new String[] {};
        if(weekStart == null) {
        	InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionId_GetWeekNames);
            ReturnInfo returnInfo = accessData(inputInfo);
            //返回信息（结果）
            if (returnInfo.isError() == false) {
                weekStart = (Integer) returnInfo.getReturnObj("weekStart");
            } else {
            	weekStart = 7;
            }
        }
        weekNames = getNames(weekStart);
        this.setDateNum(selectDateNum(weekStart));
        this.setDateToday( -dateNum); //设置用于计算当前日期的参数
        return weekNames;
    }

    /**
     * 根据开始星期设置用于计算日历某单元格日期数值的参数
     * @param weekStart int
     * @return int
     */
    private int selectDateNum(int weekStart) {
        if (weekStart == 1) {
            dateNum = 2;
        } else {
            dateNum = weekStart - 6;
        }
        return dateNum;
    }

    /**
     * 通过输入的星期数字返回排列后的星期的名称
     * @param startIndex int
     * @return String[]
     */
    private String[] getNames(int startIndex) {
        String[] bakNames = stardarWeekNames;
        int lengh = bakNames.length;
        String[] names = new String[lengh];
        for (int i = 0; i < lengh; i++) {
            names[i] = bakNames[(i + startIndex) % lengh];
        }
        return names;
    }

    /**
     * 访问服务端
     */
    protected ReturnInfo accessData(InputInfo inputInfo) {
        TransactionData transData = new TransactionData();
        transData.setInputInfo(inputInfo);

        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        ReturnInfo returnInfo = transData.getReturnInfo();

        return returnInfo;
    }

}
