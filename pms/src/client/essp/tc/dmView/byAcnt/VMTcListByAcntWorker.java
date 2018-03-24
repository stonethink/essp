package client.essp.tc.dmView.byAcnt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByDmAcntWorker;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByDmAcntWorker;
import client.essp.tc.common.TableListener;
import client.framework.model.VMTable2;

public class VMTcListByAcntWorker extends VMTable2 {
//    public static final int ACTUAL_HOUR_COLUMN_INDEX = 2;
//    public static final int OVERTIME_COLUMN_INDEX = 3;
//    public static final int ALLOCATE_COLUMN_INDEX = 4;

    //由于没有汇总行，这个属性暂没用
    private DtoWeeklyReportSumByDmAcntWorker totalOnMonth = new DtoWeeklyReportSumByDmAcntWorker();

    public VMTcListByAcntWorker(Object[][] configs) {
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

        DtoWeeklyReportSumByDmAcntWorker dto = (DtoWeeklyReportSumByDmAcntWorker)getRow(row);
        if( dto != null ){
            String columnTitle = getColumnName(col);
            if (columnTitle.equals("Actual hours")) {
                return dto.getSumHour();
            }else if (columnTitle.equals("Overtime")) {
                return new BigDecimal[]{dto.getOvertimeSumConfirmedInTheAcnt(), dto.getOvertimeSumInTheAcnt() };
            }else if (columnTitle.equals("Allocated Hours")) {
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
            DtoWeeklyReportSumByDmAcntWorker dto = (DtoWeeklyReportSumByDmAcntWorker) getRow(i);
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

    public DtoWeeklyReportSumByDmAcntWorker getTotalOnMonth() {
        return totalOnMonth;
    }
}
