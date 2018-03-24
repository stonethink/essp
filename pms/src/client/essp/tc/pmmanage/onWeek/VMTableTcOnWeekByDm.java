package client.essp.tc.pmmanage.onWeek;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.DtoWeekAllocateHour;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByPm;
import client.essp.tc.common.TableListener;
import client.framework.model.VMTable2;
import client.essp.tc.common.IPeriodModel;

public class VMTableTcOnWeekByDm extends VMTable2  implements IPeriodModel{
    public static final String[] WEEK_TITLE = {"Sta.", "Sun.", "Mon.", "Tue.", "Wen.", "Thu.", "Fri."};
    public static final String SUMMARY_TITLE = "Sum";
    public static final String OVERTIME_TITLE = "Overtime";
    public static final String ALLOCATE_TITLE = "Allocated";

    private boolean[] PERIOD_BITMAP = new boolean[7];

    public static final int SATURDAY_COLUMN_INDEX = 2;
    public static final int FRIDAY_COLUMN_INDEX = 8;
    public static final int SUMMARY_COLUMN_INDEX = 9;
    public static final int OVERTIME_COLUMN_INDEX = 10;
    public static final int ALLOCATE_COLUMN_INDEX = 11;


    private DtoWeekAllocateHour totalOnWeek = new DtoWeekAllocateHour();

    public VMTableTcOnWeekByDm() {
        this(null);
    }

    public VMTableTcOnWeekByDm(Object[][] configs) {
        super(configs);

        for (int i = 0; i < PERIOD_BITMAP.length; i++) {
            PERIOD_BITMAP[i] = false;
        }
        for (int i = DtoHoursOnWeek.SATURDAY; i <= DtoHoursOnWeek.SUMMARY; i++) {
            totalOnWeek.setHour(i, new BigDecimal(0));
        }
        totalOnWeek.setActualHour(new BigDecimal(0));
        totalOnWeek.setActualHourConfirmed(new BigDecimal(0));
        totalOnWeek.setLeaveSum(new BigDecimal(0));
        totalOnWeek.setLeaveSumConfirmed(new BigDecimal(0));
        totalOnWeek.setOvertimeSum(new BigDecimal(0));
        totalOnWeek.setOvertimeSumConfirmed(new BigDecimal(0));
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Object getValueAt(int row, int col) {
        //7天中，在period之内的将有数据，否则不会有数据
        if (col >= SATURDAY_COLUMN_INDEX && col <= FRIDAY_COLUMN_INDEX) {
            int day = col - SATURDAY_COLUMN_INDEX;
            if (PERIOD_BITMAP[day] == false) {
                return null;
            } else {
                BigDecimal hour = (BigDecimal)super.getValueAt(row, col);
                if (DtoWeeklyReport.BIG_DECIMAL_0.equals(hour)) {
                    return null;
                } else {
                    return hour;
                }
            }
        }

        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) getRow(row);
        if (dto != null) {
            if (col == SUMMARY_COLUMN_INDEX) {
                return dto.getSumHour();
            } else if (col == OVERTIME_COLUMN_INDEX) {
                return new BigDecimal[] {
                        dto.getOvertimeSumConfirmedInTheAcnt(), dto.getOvertimeSumInTheAcnt()};
            } else if (col == ALLOCATE_COLUMN_INDEX) {
                return new BigDecimal[] {
                        dto.getAllocateHourConfirmed(), dto.getAllocateHour()};
            }
        }
        return super.getValueAt(row, col);
    }

    public void setValueAt(Object value, int row, int col) {

    }

    public void setRows(List dtoList) {
        super.setRows(dtoList);

        reCaculateSumAll();
        notifyTableChanged(TableListener.EVENT_ROW_COUNT_CHANGED);
    }

    public void setPeriod(Date beginPeriod, Date endPeriod) {
        if (beginPeriod != null && endPeriod != null) {
            for (int i = 0; i < PERIOD_BITMAP.length; i++) {
                PERIOD_BITMAP[i] = false;
            }

            Calendar day = Calendar.getInstance();
            day.setTime(beginPeriod);

            long maxTime = endPeriod.getTime();
            while (day.getTimeInMillis() <= maxTime) {
                int dayInWeek = day.get(Calendar.DAY_OF_WEEK);
                PERIOD_BITMAP[(dayInWeek) % 7] = true;

                day.set(Calendar.DAY_OF_MONTH, day.get(Calendar.DAY_OF_MONTH) + 1);
            }
        }
    }

    //2.重新汇总所有的行
    private void reCaculateSumAll() {
        for (int i = DtoHoursOnWeek.SATURDAY; i <= DtoHoursOnWeek.SUMMARY; i++) {
            totalOnWeek.setHour(i, new BigDecimal(0));
        }
        totalOnWeek.setActualHour(new BigDecimal(0));
        totalOnWeek.setActualHourConfirmed(new BigDecimal(0));
        totalOnWeek.setLeaveSum(new BigDecimal(0));
        totalOnWeek.setLeaveSumConfirmed(new BigDecimal(0));
        totalOnWeek.setOvertimeSum(new BigDecimal(0));
        totalOnWeek.setOvertimeSumConfirmed(new BigDecimal(0));

        for (int i = 0; i < getRowCount(); i++) {
            DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) getRow(i);
            if (dto != null) {

                for (int j = DtoHoursOnWeek.SATURDAY; j <= DtoHoursOnWeek.SUMMARY; j++) {
                    BigDecimal hour = dto.getHour(j);
                    if (hour != null) {
                        totalOnWeek.add(j, hour);
                    }
                }

                totalOnWeek.setActualHour(add(totalOnWeek.getActualHour(), dto.getActualHour()));
                totalOnWeek.setActualHourConfirmed(add(totalOnWeek.getActualHourConfirmed(), dto.getActualHourConfirmed()));
                totalOnWeek.setLeaveSum(add(totalOnWeek.getLeaveSum(), dto.getLeaveSum()));
                totalOnWeek.setOvertimeSum(add(totalOnWeek.getOvertimeSum(), dto.getOvertimeSum()));
                totalOnWeek.setLeaveSumConfirmed(add(totalOnWeek.getLeaveSumConfirmed(), dto.getLeaveSumConfirmed()));
                totalOnWeek.setOvertimeSumConfirmed(add(totalOnWeek.getOvertimeSumConfirmed(), dto.getOvertimeSumConfirmed()));
            }
        }

        if (getRowCount() > 0) {
            fireTableRowsUpdated(0, getRowCount() - 1);
        }
        notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
    }

    public void updateRow(int row , DtoWeeklyReportSumByPm newDto){
        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm)getRow(row);
        if( dto != null ){

            DtoUtil.copyBeanToBean(dto, newDto);

            fireTableRowsUpdated(row, row);
            notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
        }
    }

    public void refreshSumHours(int row){

    }

    public DtoWeekAllocateHour getTotalOnWeek() {
        return totalOnWeek;
    }


    /**
     * 表格的事件
     */
    List tableListenerList = new ArrayList();

    protected void notifyTableChanged(String eventType) {
        for (Iterator iter = tableListenerList.iterator(); iter.hasNext(); ) {
            TableListener tableListener = (TableListener) iter.next();

            tableListener.processTableChanged(eventType);
        }
    }

    public void addTableListeners(TableListener listener) {
        tableListenerList.add(listener);
    }

    private BigDecimal add(BigDecimal left, BigDecimal right){
        if( left == null ){
            left = new BigDecimal(0);
        }
        if( right == null ){
            right = new BigDecimal(0);
        }
        return left.add(right);
    }
}
