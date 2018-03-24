package client.essp.tc.pmmanage.onMonth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumOnMonthByPm;
import client.essp.tc.common.TableListener;
import client.framework.model.VMTable2;
import c2s.dto.DtoUtil;

public class VMTableTcOnMonth extends VMTable2 {
    public static final String SUMMARY_TITLE = "Actual hour";

    public static final int ACTUAL_HOUR_COLUMN_INDEX = 2;
    public static final int OVERTIME_COLUMN_INDEX = 3;
    public static final int ALLOCATE_COLUMN_INDEX = 4;

    private DtoWeeklyReportSumOnMonthByPm totalOnMonth = new DtoWeeklyReportSumOnMonthByPm();

    public VMTableTcOnMonth(Object[][] configs) {
        super(configs);

        totalOnMonth.setActualHourInTheAcnt(new BigDecimal(0));
        totalOnMonth.setActualHourConfirmedInTheAcnt(new BigDecimal(0));
        totalOnMonth.setActualHour(new BigDecimal(0));
        totalOnMonth.setActualHourConfirmed(new BigDecimal(0));
        totalOnMonth.setLeaveSum(new BigDecimal(0));
        totalOnMonth.setLeaveSumConfirmed(new BigDecimal(0));
        totalOnMonth.setOvertimeSum(new BigDecimal(0));
        totalOnMonth.setOvertimeSumConfirmed(new BigDecimal(0));
        totalOnMonth.setOvertimeSumConfirmedInTheAcnt(new BigDecimal(0));
        totalOnMonth.setOvertimeSumInTheAcnt(new BigDecimal(0));
        //totalOnMonth.setSumHour(new BigDecimal(0));
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Object getValueAt(int row, int col) {

        DtoWeeklyReportSumOnMonthByPm dto = (DtoWeeklyReportSumOnMonthByPm)getRow(row);
        if( dto != null ){
            if (col == ACTUAL_HOUR_COLUMN_INDEX) {
                return new BigDecimal[]{dto.getActualHourConfirmedInTheAcnt(), dto.getActualHourInTheAcnt() };
            }else if (col == OVERTIME_COLUMN_INDEX) {
                return new BigDecimal[]{dto.getOvertimeSumConfirmedInTheAcnt(), dto.getOvertimeSumInTheAcnt() };
            }else if (col == ALLOCATE_COLUMN_INDEX) {
                return new BigDecimal[]{dto.getAllocateHourConfirmed(), dto.getAllocateHour()};
            }
        }
        return super.getValueAt(row, col);
    }

    public void setRows(List dtoList) {
        super.setRows(dtoList);

        reCaculateSumAll();
        notifyTableChanged(TableListener.EVENT_ROW_COUNT_CHANGED);
    }

    //1.重新汇总所有的行
    private void reCaculateSumAll() {
        totalOnMonth.setActualHourInTheAcnt(new BigDecimal(0));
        totalOnMonth.setActualHourConfirmedInTheAcnt(new BigDecimal(0));
        totalOnMonth.setActualHour(new BigDecimal(0));
        totalOnMonth.setActualHourConfirmed(new BigDecimal(0));
        totalOnMonth.setLeaveSum(new BigDecimal(0));
        totalOnMonth.setLeaveSumConfirmed(new BigDecimal(0));
        totalOnMonth.setOvertimeSum(new BigDecimal(0));
        totalOnMonth.setOvertimeSumConfirmed(new BigDecimal(0));
        totalOnMonth.setOvertimeSumConfirmedInTheAcnt(new BigDecimal(0));
        totalOnMonth.setOvertimeSumInTheAcnt(new BigDecimal(0));
//        totalOnMonth.setSumHour(new BigDecimal(0));

        for (int i = 0; i < getRowCount(); i++) {
            DtoWeeklyReportSumOnMonthByPm dto = (DtoWeeklyReportSumOnMonthByPm) getRow(i);
            if (dto != null) {
                totalOnMonth.setActualHourInTheAcnt(totalOnMonth.getActualHourInTheAcnt().add(dto.getActualHourInTheAcnt()));
                totalOnMonth.setActualHourConfirmedInTheAcnt(totalOnMonth.getActualHourConfirmedInTheAcnt().add(dto.getActualHourConfirmedInTheAcnt()));
                totalOnMonth.setActualHour(totalOnMonth.getActualHour().add(dto.getActualHour()));
                totalOnMonth.setActualHourConfirmed(totalOnMonth.getActualHourConfirmed().add(dto.getActualHourConfirmed()));
                totalOnMonth.setLeaveSum(totalOnMonth.getLeaveSum().add(dto.getLeaveSum()));
                totalOnMonth.setOvertimeSum(totalOnMonth.getOvertimeSum().add(dto.getOvertimeSum()));
                totalOnMonth.setLeaveSumConfirmed(totalOnMonth.getLeaveSumConfirmed().add(dto.getLeaveSumConfirmed()));
                totalOnMonth.setOvertimeSumConfirmed(totalOnMonth.getOvertimeSumConfirmed().add(dto.getOvertimeSumConfirmed()));
                totalOnMonth.setOvertimeSumConfirmedInTheAcnt(totalOnMonth.getOvertimeSumConfirmedInTheAcnt().add(dto.getOvertimeSumConfirmedInTheAcnt()));
                totalOnMonth.setOvertimeSumInTheAcnt(totalOnMonth.getOvertimeSumInTheAcnt().add(dto.getOvertimeSumInTheAcnt()));
//                totalOnMonth.setSumHour(totalOnMonth.getSumHour().add(dto.getSumHour()));
            }
        }

        notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
    }

    public void updateRow(int row , DtoWeeklyReportSumOnMonthByPm newDto){
        DtoWeeklyReportSumOnMonthByPm dto = (DtoWeeklyReportSumOnMonthByPm)getRow(row);
        if( dto != null ){
            totalOnMonth.setActualHour(totalOnMonth.getActualHour().subtract(dto.getActualHour()));
            totalOnMonth.setActualHourConfirmed(totalOnMonth.getActualHourConfirmed().subtract(dto.getActualHourConfirmed()));
            totalOnMonth.setLeaveSum(totalOnMonth.getLeaveSum().subtract(dto.getLeaveSum()));
            totalOnMonth.setOvertimeSum(totalOnMonth.getOvertimeSum().subtract(dto.getOvertimeSum()));
            totalOnMonth.setLeaveSumConfirmed(totalOnMonth.getLeaveSumConfirmed().subtract(dto.getLeaveSumConfirmed()));
            totalOnMonth.setOvertimeSumConfirmed(totalOnMonth.getOvertimeSumConfirmed().subtract(dto.getOvertimeSumConfirmed()));
            totalOnMonth.setOvertimeSumConfirmedInTheAcnt(totalOnMonth.getOvertimeSumConfirmedInTheAcnt().subtract(dto.getOvertimeSumConfirmedInTheAcnt()));
            totalOnMonth.setOvertimeSumInTheAcnt(totalOnMonth.getOvertimeSumInTheAcnt().subtract(dto.getOvertimeSumInTheAcnt()));
//            totalOnMonth.setSumHour(totalOnMonth.getSumHour().subtract(dto.getSumHour()));

            totalOnMonth.setActualHour(totalOnMonth.getActualHour().add(newDto.getActualHour()));
            totalOnMonth.setActualHourConfirmed(totalOnMonth.getActualHourConfirmed().add(newDto.getActualHourConfirmed()));
            totalOnMonth.setLeaveSum(totalOnMonth.getLeaveSum().add(newDto.getLeaveSum()));
            totalOnMonth.setOvertimeSum(totalOnMonth.getOvertimeSum().add(newDto.getOvertimeSum()));
            totalOnMonth.setLeaveSumConfirmed(totalOnMonth.getLeaveSumConfirmed().add(newDto.getLeaveSumConfirmed()));
            totalOnMonth.setOvertimeSumConfirmed(totalOnMonth.getOvertimeSumConfirmed().add(newDto.getOvertimeSumConfirmed()));
            totalOnMonth.setOvertimeSumConfirmedInTheAcnt(totalOnMonth.getOvertimeSumConfirmedInTheAcnt().add(newDto.getOvertimeSumConfirmedInTheAcnt()));
            totalOnMonth.setOvertimeSumInTheAcnt(totalOnMonth.getOvertimeSumInTheAcnt().add(newDto.getOvertimeSumInTheAcnt()));
//            totalOnMonth.setSumHour(totalOnMonth.getSumHour().add(newDto.getSumHour()));

            DtoUtil.copyBeanToBean(dto, newDto);

            fireTableRowsUpdated(row, row);
            notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
        }
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

    public DtoWeeklyReportSumOnMonthByPm getTotalOnMonth() {
        return totalOnMonth;
    }
}
