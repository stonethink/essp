package client.essp.tc.pmmanage.onWeek;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByPm;
import client.essp.tc.common.TableListener;
import client.framework.model.VMTable2;
import c2s.dto.DtoUtil;
import client.essp.tc.common.IPeriodModel;

public class VMTableTcOnWeek extends VMTable2 implements IPeriodModel{
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
    public static final int CONFIRM_COLUMN_INDEX = 12;
    public static final int STATUS_COLUMN_INDEX = 13;

    //当前行的数据,由表格TableWeeklyReport的model维护
    private DtoHoursOnWeek hoursOnWeek = null; //一周内每天的总工作时间，当周报数据修改时会维护这个值，只有最后的sum hour有用
    private DtoHoursOnWeek hoursOnWeekInTheAcnt = null; //在本项目中的，一周内每天的总工作时间，当周报数据修改时会维护这个值。

    private DtoHoursOnWeek totalOnWeek = new DtoHoursOnWeek();

    String aboutConfirmStatus = null;
    String oldConfirmStatus = null;

    public VMTableTcOnWeek() {
        this(null);
    }

    public VMTableTcOnWeek(Object[][] configs) {
        super(configs);

        for (int i = 0; i < PERIOD_BITMAP.length; i++) {
            PERIOD_BITMAP[i] = false;
        }
        for (int i = DtoHoursOnWeek.SATURDAY; i <= DtoHoursOnWeek.SUMMARY; i++) {
            totalOnWeek.setHour(i, new BigDecimal(0));
        }
    }

    public boolean isCellEditable(int row, int col) {
        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) getRow(row);
        if ( (col == STATUS_COLUMN_INDEX || col == CONFIRM_COLUMN_INDEX)
            && dto != null
            && DtoWeeklyReport.STATUS_UNLOCK.equals(dto.getConfirmStatus()) == false
                ) {
            //当周报lock（包括confirm/reject）后，可以修改confirm / comment栏位
            return super.isCellEditable(row, col);
        } else {
            return false;
        }
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
                    return hour.setScale(2,BigDecimal.ROUND_HALF_UP);
                }
            }
        }

        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm)getRow(row);
        if( dto != null ){
            if (col == SUMMARY_COLUMN_INDEX) {
                return dto.getSumHour();
            }else if (col == OVERTIME_COLUMN_INDEX) {
                return new BigDecimal[]{dto.getOvertimeSumConfirmedInTheAcnt(), dto.getOvertimeSumInTheAcnt() };
            }else if (col == ALLOCATE_COLUMN_INDEX) {
                return new BigDecimal[]{dto.getAllocateHourConfirmed(), dto.getAllocateHour()};
            }
        }
        return super.getValueAt(row, col);
    }

    public void setValueAt(Object value, int row, int col) {
        Object oldValue = getValueAt(row, col);

        if (row < getRowCount() && col == CONFIRM_COLUMN_INDEX) {
            DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm)getRow(row);
            oldConfirmStatus = dto.getConfirmStatus();
            dto.setConfirmStatus((String)value);

            String oldConfirmStatus = (String) oldValue;
            if( oldConfirmStatus == null ){
                //throw new RuntimeException("Confirm Status is null.");
                return;
            }
            if (oldConfirmStatus.equals(value) == false){

                notifyTableChanged(TableListener.EVENT_TOTAL_CONFIRM_DATA_CHANGED);

                guessConfirmStatus();
            }
        }else{
            super.setValueAt(value, row, col);
        }
    }

    public void setRows(List dtoList) {
        super.setRows(dtoList);

        reCaculateSumAll();
        guessConfirmStatus();
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

        for (int i = 0; i < getRowCount(); i++) {
            DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) getRow(i);
            if (dto != null) {

                for (int j = DtoHoursOnWeek.SATURDAY; j <= DtoHoursOnWeek.SUMMARY; j++) {
                    BigDecimal hour = dto.getHour(j);
                    if (hour != null) {
                        totalOnWeek.add(j, hour);
                    }
                }
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

//    public void statusChanged() {
//        notifyTableChanged(TableListener.EVENT_TOTAL_CONFIRM_DATA_CHANGED);
//        guessConfirmStatus();
//    }

    /**guess the confirm status*/
    private void guessConfirmStatus() {
        String newStatus;

        if( getRowCount() > 0 ){
            DtoWeeklyReportSumByPm firstDto = (DtoWeeklyReportSumByPm) getRow(0);
            newStatus = firstDto.getConfirmStatus();

            for (Iterator iter = getRows().iterator(); iter.hasNext(); ) {
                DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) iter.next();
                newStatus = DtoWeeklyReport.generalConfirmStatus(newStatus, dto.getConfirmStatus());
            }

            if( DtoWeeklyReport.STATUS_UNLOCK.equals( newStatus ) ){
                newStatus =DtoWeeklyReport.STATUS_NONE;
            }
        }else{
            newStatus = DtoWeeklyReport.STATUS_NONE;
        }

        if (
                (newStatus == null && getAboutConfirmStatus() != null)
                || (newStatus != null && newStatus.equals(getAboutConfirmStatus()) == false)
                ) {
            setAbountConfirmStatus(newStatus);
            notifyTableChanged(TableListener.EVENT_DETAIL_CONFIRM_DATA_CHANGED);
        }
    }

    public void refreshSumHours(int selectedRow) {
        //数据已经被TableWeeklyReport更新，这里更新本表的显示
        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) getRow(selectedRow);
        if (dto == null
            || this.hoursOnWeek == null
            || this.hoursOnWeekInTheAcnt == null ){
            return;
        }

        //更新每天的汇总时间
        for (int i = DtoWeeklyReport.SATURDAY; i <= DtoWeeklyReport.SUMMARY; i++) {

            BigDecimal oldHour = dto.getHour(i);
            BigDecimal newHour = hoursOnWeekInTheAcnt.getHour(i);

            dto.setHour(i, newHour);
            totalOnWeek.subtract(i, oldHour).add(i, newHour); //更新汇总行
        }

        //更新allocate栏位：
        BigDecimal actualHour = hoursOnWeek.getSumHour();
        dto.setActualHour(actualHour);

        fireTableRowsUpdated(selectedRow, selectedRow);

        notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
    }

    void confirm(int row, String confirmStatus){
        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) getRow(row);
        if( dto != null ){
            if( DtoWeeklyReport.STATUS_CONFIRMED.equals(oldConfirmStatus) ){
                if( DtoWeeklyReport.STATUS_CONFIRMED.equals(confirmStatus) == false ){
                    //从confirm状态转到其他状态
                    BigDecimal actualHourConfirmed = dto.getActualHourConfirmed();
                    actualHourConfirmed = actualHourConfirmed.subtract(dto.getSumHour());
                    dto.setActualHourConfirmed(actualHourConfirmed);
                    fireTableRowsUpdated(row, row);

                    notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
                }
            }else{
                if( DtoWeeklyReport.STATUS_CONFIRMED.equals(confirmStatus) == true ){
                    //从其他状态转到confirm状态
                    BigDecimal actualHourConfirmed = dto.getActualHourConfirmed();
                    actualHourConfirmed = actualHourConfirmed.add(dto.getSumHour());
                    dto.setActualHourConfirmed(actualHourConfirmed);
                    fireTableRowsUpdated(row, row);

                    notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
                }
            }
        }
    }

    void confirmAll(String confirmStatus) {
        for (int i = 0; i < getRowCount(); i++) {
            //不能修改unlock weeklyreport
            DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) getRow(i);
            if( DtoWeeklyReport.STATUS_UNLOCK.equals(dto.getConfirmStatus()) == false ){
                oldConfirmStatus = dto.getConfirmStatus();
                dto.setConfirmStatus(confirmStatus);
                confirm(i, confirmStatus);
            }
        }

        if (getRowCount() > 0) {
            fireTableRowsUpdated(0, getRowCount() - 1);
        }

        setAbountConfirmStatus(confirmStatus);
    }

    public String getAboutConfirmStatus() {
        return aboutConfirmStatus;
    }

    public DtoHoursOnWeek getTotalOnWeek() {
        return totalOnWeek;
    }

    private void setAbountConfirmStatus(String status) {
        this.aboutConfirmStatus = status;
    }

    public void setHoursOnWeek(DtoHoursOnWeek hoursOnWeek) {
        this.hoursOnWeek = hoursOnWeek;
    }

    public void setHoursOnWeekInTheAcnt(DtoHoursOnWeek hoursOnWeekInTheAcnt) {
        this.hoursOnWeekInTheAcnt = hoursOnWeekInTheAcnt;
    }

    public String getOldConfirmStatus(){
        return this.oldConfirmStatus;
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
