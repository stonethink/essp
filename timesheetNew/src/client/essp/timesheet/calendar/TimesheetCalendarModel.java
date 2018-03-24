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
 * <p>Description: ������ʾ�ؼ���Model</p>
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
     * ���ڲ�ѯ��ʼ��������Action��ID
     */
    private final static String actionId_GetWeekNames = "FTSWeekStart";
    /**
     *  Ĭ�ϵ�����������˳��
     */
    private final static String[] stardarWeekNames = new String[] {
    	    "rsid.timesheet.sat", 
    	    "rsid.timesheet.sun",
            "rsid.timesheet.mon", "rsid.timesheet.tue",
            "rsid.timesheet.wed", "rsid.timesheet.thu", 
            "rsid.timesheet.fri"};
    
    private static Integer weekStart;
    /**
     *���ڼ���������ĳ��Ԫ���������Ĳ���
     */
    private int dateNum = 1;
    public TimesheetCalendarModel() {
        super();
    }

    /**
     * ��ʼ���е�˳��Ͷ�ȡ���������õ�Action��ID
     */
    protected void initColumn() {
        String[] weekNames = getWeekNamesByP3();
        super.columnIdentifiers = super.convertToVector(weekNames);
        this.setActionId("FTSCalendar");
    }
    /**
     * ����P3���û�ȡ����������˳��
     * @return String[]
     */
    private String[] getWeekNamesByP3() {
        String[] weekNames = new String[] {};
        if(weekStart == null) {
        	InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionId_GetWeekNames);
            ReturnInfo returnInfo = accessData(inputInfo);
            //������Ϣ�������
            if (returnInfo.isError() == false) {
                weekStart = (Integer) returnInfo.getReturnObj("weekStart");
            } else {
            	weekStart = 7;
            }
        }
        weekNames = getNames(weekStart);
        this.setDateNum(selectDateNum(weekStart));
        this.setDateToday( -dateNum); //�������ڼ��㵱ǰ���ڵĲ���
        return weekNames;
    }

    /**
     * ���ݿ�ʼ�����������ڼ�������ĳ��Ԫ��������ֵ�Ĳ���
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
     * ͨ��������������ַ������к�����ڵ�����
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
     * ���ʷ����
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
