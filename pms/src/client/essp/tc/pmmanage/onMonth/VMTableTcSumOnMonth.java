package client.essp.tc.pmmanage.onMonth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumOnMonthByPm;
import client.essp.tc.common.TableListener;

public class VMTableTcSumOnMonth extends AbstractTableModel {
    public final static int SUMMARY_COLUMN_INDEX = 1;
    public final static int OVERTIME_COLUMN_INDEX = 2;
    public final static int ALLOCATE_COLUMN_INDEX = 3;

    private DtoWeeklyReportSumOnMonthByPm totalOnMonth = new DtoWeeklyReportSumOnMonthByPm();

    public Class getColumnClass(int column) {
        return Object.class;
    }

    public String getColumnName(int column) {
        return null;
    }

    public int getColumnCount() {
        return ALLOCATE_COLUMN_INDEX+1;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
    }

    public int getRowCount() {
        return 1;
    }

    public Object getValueAt(int row, int col) {
        if (totalOnMonth == null) {
            return null;
        }

        if (col == 0) {
            return "Total ";
        } else if (col == SUMMARY_COLUMN_INDEX) {
            return new BigDecimal[]{totalOnMonth.getActualHourConfirmedInTheAcnt(), totalOnMonth.getActualHourInTheAcnt() };
        } else if (col == OVERTIME_COLUMN_INDEX) {
            return new BigDecimal[] {
                    totalOnMonth.getOvertimeSumConfirmedInTheAcnt(), totalOnMonth.getOvertimeSumInTheAcnt()};
        } else if (col == ALLOCATE_COLUMN_INDEX) {
            return new BigDecimal[] {
                    totalOnMonth.getAllocateHourConfirmed(), totalOnMonth.getAllocateHour()};
        }

        return null;
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

    public void setTotalOnMonth(DtoWeeklyReportSumOnMonthByPm totalOnMonth) {
        this.totalOnMonth = totalOnMonth;
    }

    public DtoWeeklyReportSumOnMonthByPm getTotalOnMonth() {
        return this.totalOnMonth;
    }

}
