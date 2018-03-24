package client.essp.tc.pmmanage.onWeek;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import client.essp.tc.common.TableListener;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;

public class VMTableTcSumOnWeek extends AbstractTableModel {
    public final static int SATURDAY_COLUMN_INDEX = 1;
    public final static int FRIDAY_COLUMN_INDEX = 7;
    public final static int SUMMARY_COLUMN_INDEX = 8;
    public final static int LBL_CONFIRM_COLUMN_INDEX = 9;
    public final static int CONFIRM_COLUMN_INDEX = 10;

    //当前行的时间数据,由表格TableTc的model维护
    private DtoHoursOnWeek totalOnWeek = null;
    private String confirmStatus = null;
    private String oldStatus = DtoWeeklyReport.STATUS_NONE;

    public Class getColumnClass(int column) {
        return BigDecimal.class;
    }

    public String getColumnName(int column) {
        return "" + column;
    }

    public int getColumnCount() {
        return CONFIRM_COLUMN_INDEX + 1;
    }

    public boolean isCellEditable(int row, int col) {
        if (col == CONFIRM_COLUMN_INDEX) {
            return true;
        } else {
            return false;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        if (col == CONFIRM_COLUMN_INDEX) {
            if ( value != null && value.equals(getConfirmStatus()) == false) {
                this.oldStatus = getConfirmStatus();
                setConfirmStatus((String) value);
                notifyTableChanged(TableListener.EVENT_TOTAL_CONFIRM_DATA_CHANGED);
            } else {
                setConfirmStatus((String) value);
            }
        }
    }

    public int getRowCount() {
        return 1;
    }

    public Object getValueAt(int row, int col) {

        if (col == 0) {
            return "Total ";
        } else if (col == LBL_CONFIRM_COLUMN_INDEX) {
            return "Confirm All ";
        } else if (col >= SATURDAY_COLUMN_INDEX && col <= SUMMARY_COLUMN_INDEX) {
            if (this.totalOnWeek != null) {
                return totalOnWeek.getHour(col - SATURDAY_COLUMN_INDEX);
            }
        } else if (col == CONFIRM_COLUMN_INDEX) {
            return getConfirmStatus();
        }

        return null;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public void rollbackStatus() {
        this.confirmStatus= this.oldStatus ;
    }

    public void setTotalOnWeek(DtoHoursOnWeek totalOnWeek) {
        this.totalOnWeek = totalOnWeek;
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

}
