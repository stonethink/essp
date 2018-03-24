package client.essp.tc.pmmanage.onWeek;

import java.math.BigDecimal;

import javax.swing.table.AbstractTableModel;

import c2s.essp.tc.weeklyreport.DtoWeekAllocateHour;

public class VMTableTcSumOnWeekByDm extends AbstractTableModel {
    public final static int SATURDAY_COLUMN_INDEX = 1;
    public final static int FRIDAY_COLUMN_INDEX = 7;
    public final static int SUMMARY_COLUMN_INDEX = 8;
    public final static int OVERTIME_COLUMN_INDEX = 9;
    public final static int ALLOCATE_COLUMN_INDEX = 10;

    //当前行的时间数据,由表格VMTableTcOnWeekByDm维护
    private DtoWeekAllocateHour totalOnWeek = new DtoWeekAllocateHour();

    public Class getColumnClass(int column) {
        return BigDecimal.class;
    }

    public String getColumnName(int column) {
        return "" + column;
    }

    public int getColumnCount() {
        return ALLOCATE_COLUMN_INDEX + 1;
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
        if (this.totalOnWeek == null) {
            return null;
        }

        if (col == 0) {
            return "Total ";
        } else if (col >= SATURDAY_COLUMN_INDEX && col <= SUMMARY_COLUMN_INDEX) {
            return totalOnWeek.getHour(col - SATURDAY_COLUMN_INDEX);
        } else if (col == OVERTIME_COLUMN_INDEX) {
            return new BigDecimal[] {
                    totalOnWeek.getOvertimeSumConfirmed()
                    , totalOnWeek.getOvertimeSum()};
        } else if (col == ALLOCATE_COLUMN_INDEX) {
            return new BigDecimal[] {
                    totalOnWeek.getAllocateHourConfirmed()
                    , totalOnWeek.getAllocateHour()};
        }

        return null;
    }

    public void setTotalOnWeek(DtoWeekAllocateHour totalOnWeek) {
        this.totalOnWeek = totalOnWeek;
    }


}
