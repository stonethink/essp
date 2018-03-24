package client.essp.tc.hrmanager;

import client.framework.model.VMTable2;
import client.essp.tc.common.TableListener;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import java.math.BigDecimal;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.DtoAllocateHour;
import c2s.dto.IDto;

public class VMTableTc extends VMTable2 {
    public static final String LOCK_TITLE = "Locked";
    public static final String STANDARD_TITLE = "Standard";
    public static final String ACTUAL_TITLE = "Actual";
    public static final String NORMAL_TITLE = "Normal";
    public static final String VARIENT_TITLE = "Varient";
    public static final String PAY_TIME_TITLE = "Pay Time";
    public static final String OVERTIME_TITLE = "Overtime";
    public static final String LEAVE_ALL_TITLE = "Leave(All)";
    public static final String LEAVE_HALF_TITLE = "Leave(Half)";
    public static final String LEAVE_NONE_TITLE = "Leave(None)";
    public static final String ABSENT_TITLE = "Absent";
    public static final String VIALOT_TITLE = "Vialot";

//    static final int LOCK_INDEX = 0;
//    static final int STANDARD_INDEX =  4;
//    static final int ACTUAL_INDEX =    5;
//    static final int NORMAL_INDEX =    6;
//    static final int VARIENT_INDEX =   7;
//    static final int PAY_TIME_INDEX =  8;
//    static final int OVERTIME_INDEX =  9;
//    static final int LEAVE_ALL_INDEX =  10;
//    static final int LEAVE_HALF_INDEX = 11;
//    static final int LEAVE_NONE_INDEX = 12;

    private DtoAllocateHour allocateHourOnWeek = null;

    public VMTableTc(Object[][] configs) {
        super(configs);
    }

    public Object getValueAt(int row , int col){
        DtoWeeklyReportSumByHr dto = (DtoWeeklyReportSumByHr)getRow(row);
        if( dto == null ){
            return null;
        }
        String title = getColumnName(col);

        if( title.equals( STANDARD_TITLE ) ){
            return dto.getStandardHour();
        }else if(title.equals( ACTUAL_TITLE )){
            return new BigDecimal[] {dto.getActualHourConfirmed(), dto.getActualHour()};
        }else if(title.equals( NORMAL_TITLE )){
            return dto.getNormalHours();
        }else if(title.equals( VARIENT_TITLE )){
            return dto.getVarientHours();
        }else if(title.equals( PAY_TIME_TITLE )){
            return dto.getPayTimes();
        }else if(title.equals( OVERTIME_TITLE )){
            return new BigDecimal[]{dto.getOvertimeSumConfirmed(), dto.getOvertimeSum()};
        }else if(title.equals( LEAVE_ALL_TITLE )){
            return new BigDecimal[]{dto.getLeaveOfPayAllConfirmed(), dto.getLeaveOfPayAll()};
        }else if(title.equals( LEAVE_HALF_TITLE )){
            return new BigDecimal[]{dto.getLeaveOfPayHalfConfirmed(), dto.getLeaveOfPayHalf()};
        }else if(title.equals( LEAVE_NONE_TITLE )){
            return new BigDecimal[]{dto.getLeaveOfPayNoneConfirmed(), dto.getLeaveOfPayNone()};
        }

        return super.getValueAt(row, col);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        DtoWeeklyReportSumByHr dto = (DtoWeeklyReportSumByHr)getRow(rowIndex);
        if( dto == null ){
            return ;
        }
        String title = getColumnName(columnIndex);
        if( title.equals( STANDARD_TITLE ) ){
            dto.setStandardHour((BigDecimal)aValue);
            dto.setOp(IDto.OP_MODIFY);
        }else{
            super.setValueAt(aValue,rowIndex,columnIndex);
        }
    }
    public boolean isCellEditable(int r, int c){

//        //lock column
//        if( LOCK_TITLE.equals( getColumnName(c) ) ){
//            String value = getValueAt(r, c).toString();
//            boolean isLocked = Boolean.valueOf(value).booleanValue();
//            if( isLocked == false ){
//                return false;
//            }else{
//                return true;
//            }
//        }

        return super.isCellEditable(r, c);
    }

    public void setAllocateHourOnWeek(DtoAllocateHour allocateHourOnWeek){
        this.allocateHourOnWeek = allocateHourOnWeek;
    }

    public void refreshHoursOnWeek(int row){
        DtoWeeklyReportSumByHr dto =(DtoWeeklyReportSumByHr)getRow(row);
        if( dto != null ){
            if( this.allocateHourOnWeek != null ){
                dto.setActualHour(this.allocateHourOnWeek.getActualHour());
                dto.setActualHourConfirmed(this.allocateHourOnWeek.getActualHourConfirmed());
                this.fireTableRowsUpdated(row, row);
            }
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

}
