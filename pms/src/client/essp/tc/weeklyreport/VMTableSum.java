package client.essp.tc.weeklyreport;

import java.math.BigDecimal;

import javax.swing.table.AbstractTableModel;

import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;

public class VMTableSum extends AbstractTableModel implements IAllocateHourSupply{

    //当前行的时间数据,由表格TableWeeklyReport的model维护
    DtoHoursOnWeek hoursOnWeek = null;

    //当前行的加班时间数据,由表格TableOvertime维护
    DtoHoursOnWeek overtimeOnWeek = null;
    DtoHoursOnWeek leaveOnWeek = null;
    DtoHoursOnWeek overtimeOnWeekConfirmed = null;
    DtoHoursOnWeek leaveOnWeekConfirmed = null;

    public final static int SATURDAY_COLUMN_INDEX = 1;
    public final static int FRIDAY_COLUMN_INDEX = 7;
    public final static int SUMMARY_COLUMN_INDEX = 8;

    public Class getColumnClass(int column) {
        return BigDecimal.class;
    }

    public String getColumnName(int column) {
        return "" + column;
    }

    public int getColumnCount() {
        return 10;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
    }

    public int getRowCount() {
        return 3;
    }

    public Object getValueAt(int row, int col) {
        if (overtimeOnWeek == null ||
            leaveOnWeek == null ||
            overtimeOnWeekConfirmed == null ||
            leaveOnWeekConfirmed == null) {
            return null;
        }

        int day = col - SATURDAY_COLUMN_INDEX;
         if (row == 0) {
             if( col == 0 ){
                 return "Actual Hours ";
             }else if (col >= SATURDAY_COLUMN_INDEX && col <= SUMMARY_COLUMN_INDEX) {
                 return hoursOnWeek.getHour(day);
             }
        } else if (row == 1) {
            if( col == 0 ){
                return "Allocated Hours ";
            } else if (col >= SATURDAY_COLUMN_INDEX && col <= SUMMARY_COLUMN_INDEX) {
                return getAllocateHour(day);
            }
        } else if (row == 2) {
            if( col == 0 ){
                return "Overtime Hours ";
            } else if (col >= SATURDAY_COLUMN_INDEX && col <= SUMMARY_COLUMN_INDEX) {
                    return new BigDecimal[]{overtimeOnWeekConfirmed.getHour(day) ,overtimeOnWeek.getHour(day)};
            }
        }

        return null;
    }

    public BigDecimal[] getAllocateHour( int day ){
        //allocate = sumHourOfDay - overtime + leave
        BigDecimal allocConfirmed = new BigDecimal(0);
        BigDecimal alloc = new BigDecimal(0);
        if( hoursOnWeek.getHour(day) != null ){
            allocConfirmed = hoursOnWeek.getHour(day);
            alloc = hoursOnWeek.getHour(day);
        }
        if( overtimeOnWeekConfirmed.getHour(day) != null ){
            allocConfirmed = allocConfirmed.subtract(overtimeOnWeekConfirmed.getHour(day));
        }
        if( overtimeOnWeek.getHour(day) != null ){
            alloc = alloc.subtract(overtimeOnWeek.getHour(day));
        }

        if( leaveOnWeekConfirmed.getHour(day) != null ){
            allocConfirmed = allocConfirmed.add(leaveOnWeekConfirmed.getHour(day));
        }
        if( leaveOnWeek.getHour(day) != null ){
            alloc = alloc.add(leaveOnWeek.getHour(day));
        }

        return new BigDecimal[]{allocConfirmed, alloc};
    }

    public void setHoursOnWeek(DtoHoursOnWeek hoursOnWeek) {
        this.hoursOnWeek = hoursOnWeek;
    }

    public void setOvertimeOnWeekConfirmed(DtoHoursOnWeek overtimeOnWeekConfirmed) {
        this.overtimeOnWeekConfirmed = overtimeOnWeekConfirmed;
    }

    public void setOvertimeOnWeek(DtoHoursOnWeek overtimeOnWeek) {
        this.overtimeOnWeek = overtimeOnWeek;
    }

    public void setLeaveOnWeekConfirmed(DtoHoursOnWeek leaveOnWeekConfirmed) {
        this.leaveOnWeekConfirmed = leaveOnWeekConfirmed;
    }

    public void setLeaveOnWeek(DtoHoursOnWeek leaveOnWeek) {
        this.leaveOnWeek = leaveOnWeek;
    }

}
