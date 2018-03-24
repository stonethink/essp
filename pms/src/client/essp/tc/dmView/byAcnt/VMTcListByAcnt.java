package client.essp.tc.dmView.byAcnt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByDmAcnt;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByDmAcnt;
import client.essp.tc.common.TableListener;
import client.framework.model.VMTable2;

public class VMTcListByAcnt extends VMTable2 {
    public static final int ACTUAL_HOUR_COLUMN_INDEX = 2;
    public static final int OVERTIME_COLUMN_INDEX = 3;
    public static final int ALLOCATE_COLUMN_INDEX = 4;

    //由于没有汇总行，这个属性暂没用
    private DtoWeeklyReportSumByDmAcnt totalOnMonth = new DtoWeeklyReportSumByDmAcnt();

    public VMTcListByAcnt(Object[][] configs) {
        super(configs);

        totalOnMonth.setActualHour(new BigDecimal(0));
        totalOnMonth.setActualHourConfirmed(new BigDecimal(0));
        totalOnMonth.setLeaveSum(new BigDecimal(0));
        totalOnMonth.setLeaveSumConfirmed(new BigDecimal(0));
        totalOnMonth.setOvertimeSum(new BigDecimal(0));
        totalOnMonth.setOvertimeSumConfirmed(new BigDecimal(0));
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Object getValueAt(int row, int col) {

        DtoWeeklyReportSumByDmAcnt dto = (DtoWeeklyReportSumByDmAcnt)getRow(row);
        if( dto != null ){
            if (col == ACTUAL_HOUR_COLUMN_INDEX) {
                return new BigDecimal[]{dto.getActualHourConfirmed(), dto.getActualHour() };
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
        totalOnMonth.setActualHour(new BigDecimal(0));
        totalOnMonth.setActualHourConfirmed(new BigDecimal(0));
        totalOnMonth.setLeaveSum(new BigDecimal(0));
        totalOnMonth.setLeaveSumConfirmed(new BigDecimal(0));
        totalOnMonth.setOvertimeSum(new BigDecimal(0));
        totalOnMonth.setOvertimeSumConfirmed(new BigDecimal(0));

        for (int i = 0; i < getRowCount(); i++) {
            DtoWeeklyReportSumByDmAcnt dto = (DtoWeeklyReportSumByDmAcnt) getRow(i);
            if (dto != null) {
                totalOnMonth.setActualHour(totalOnMonth.getActualHour().add(dto.getActualHour()));
                totalOnMonth.setActualHourConfirmed(totalOnMonth.getActualHourConfirmed().add(dto.getActualHourConfirmed()));
                totalOnMonth.setLeaveSum(totalOnMonth.getLeaveSum().add(dto.getLeaveSum()));
                totalOnMonth.setOvertimeSum(totalOnMonth.getOvertimeSum().add(dto.getOvertimeSum()));
                totalOnMonth.setLeaveSumConfirmed(totalOnMonth.getLeaveSumConfirmed().add(dto.getLeaveSumConfirmed()));
                totalOnMonth.setOvertimeSumConfirmed(totalOnMonth.getOvertimeSumConfirmed().add(dto.getOvertimeSumConfirmed()));
            }
        }

        notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
    }

    public void updateRow(int row , DtoWeeklyReportSumByDmAcnt newDto){
        DtoWeeklyReportSumByDmAcnt dto = (DtoWeeklyReportSumByDmAcnt)getRow(row);
        if( dto != null ){
            totalOnMonth.setActualHour(totalOnMonth.getActualHour().subtract(dto.getActualHour()));
            totalOnMonth.setActualHourConfirmed(totalOnMonth.getActualHourConfirmed().subtract(dto.getActualHourConfirmed()));
            totalOnMonth.setLeaveSum(totalOnMonth.getLeaveSum().subtract(dto.getLeaveSum()));
            totalOnMonth.setOvertimeSum(totalOnMonth.getOvertimeSum().subtract(dto.getOvertimeSum()));
            totalOnMonth.setLeaveSumConfirmed(totalOnMonth.getLeaveSumConfirmed().subtract(dto.getLeaveSumConfirmed()));
            totalOnMonth.setOvertimeSumConfirmed(totalOnMonth.getOvertimeSumConfirmed().subtract(dto.getOvertimeSumConfirmed()));

            totalOnMonth.setActualHour(totalOnMonth.getActualHour().add(newDto.getActualHour()));
            totalOnMonth.setActualHourConfirmed(totalOnMonth.getActualHourConfirmed().add(newDto.getActualHourConfirmed()));
            totalOnMonth.setLeaveSum(totalOnMonth.getLeaveSum().add(newDto.getLeaveSum()));
            totalOnMonth.setOvertimeSum(totalOnMonth.getOvertimeSum().add(newDto.getOvertimeSum()));
            totalOnMonth.setLeaveSumConfirmed(totalOnMonth.getLeaveSumConfirmed().add(newDto.getLeaveSumConfirmed()));
            totalOnMonth.setOvertimeSumConfirmed(totalOnMonth.getOvertimeSumConfirmed().add(newDto.getOvertimeSumConfirmed()));

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

    public DtoWeeklyReportSumByDmAcnt getTotalOnMonth() {
        return totalOnMonth;
    }
}
