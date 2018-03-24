package client.essp.tc.weeklyreport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import client.essp.tc.common.TableListener;
import client.framework.model.VMTable2;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;

public class VMTableWeeklyReport extends VMTable2 {
    public static final String[] WEEK_TITLE = {"Sat.", "Sun.", "Mon.", "Tue.", "Wed.", "Thu.", "Fri."};
    public static final String SUMMARY = "Sum";

    private boolean[] periodBitmap = new boolean[7];

    private DtoHoursOnWeek hoursOnWeek = new DtoHoursOnWeek();
    private DtoHoursOnWeek hoursOnWeekInTheAcnt = new DtoHoursOnWeek();

    public int SATURDAY_COLUMN_INDEX = 3;
    public int FRIDAY_COLUMN_INDEX = 9;
    public int SUMMARY_COLUMN_INDEX = 10;
    public int COMMENTS_COLUMN_INDEX = 11;

    private boolean isDataEditable = false;

    public VMTableWeeklyReport(Object[][] configs) {
        super(configs);
    }

    public boolean isCellEditable(int row, int col) {
        if (isDataEditable() == false) {
            return false;
        }

        if (col >= SATURDAY_COLUMN_INDEX && col <= FRIDAY_COLUMN_INDEX) {
            int day = col - SATURDAY_COLUMN_INDEX;
            if (periodBitmap[day] == true) {
                return true;
            } else {
                return false;
            }
        }

        return super.isCellEditable(row, col);
    }

    public void setValueAt(Object value, int row, int col) {
        if (col >= SATURDAY_COLUMN_INDEX && col <= FRIDAY_COLUMN_INDEX) {
            reCaculateSumWhenUpdateCell(row, col, (BigDecimal) value);
        }

        super.setValueAt(value, row, col);
    }

    public Object getValueAt(int row, int col) {
        //7天中，在period之内的将有数据，否则不会有数据
        if (col >= SATURDAY_COLUMN_INDEX && col <= FRIDAY_COLUMN_INDEX) {
            int day = col - SATURDAY_COLUMN_INDEX;
            if (periodBitmap[day] == false) {
                return null;
            } else {
                BigDecimal hour = (BigDecimal)super.getValueAt(row, col);
                if (DtoWeeklyReport.BIG_DECIMAL_0.equals(hour) || hour == null) {
                    return null;
                } else {
                    return hour.setScale(2,BigDecimal.ROUND_HALF_UP);
                }
            }
        }

        return super.getValueAt(row, col);
    }

    public void setRows(List dtoList) {
        super.setRows(dtoList);

        reCaculateSumAll();
        notifyTableChanged(TableListener.EVENT_ROW_COUNT_CHANGED);
    }

    public Object addRow(int row, Object dtoBean) {
        Object obj = super.addRow(row, dtoBean);

        if (obj != null) {
            int addRowIndex = this.getRows().indexOf(obj);
            reCaculateSumWhenAdd(addRowIndex);
            notifyTableChanged(TableListener.EVENT_ROW_COUNT_CHANGED);
        }
        return obj;
    }

    public Object deleteRow(int rowIndex) {
        Object obj = super.deleteRow(rowIndex);

        if (obj != null) {
            reCaculateSumWhenDelete((DtoWeeklyReport) obj);
            notifyTableChanged(TableListener.EVENT_ROW_COUNT_CHANGED);
        }
        return obj;
    }

    public void setPeriod(Date beginPeriod, Date endPeriod) {
        if (beginPeriod != null && endPeriod != null) {
            for (int i = 0; i < periodBitmap.length; i++) {
                periodBitmap[i] = false;
                hoursOnWeek.setHour(i, null);
                hoursOnWeekInTheAcnt.setHour(i, null);
            }

            Calendar day = Calendar.getInstance();
            day.setTime(beginPeriod);

            long maxTime = endPeriod.getTime();
            while (day.getTimeInMillis() <= maxTime) {
                int dayInWeek = day.get(Calendar.DAY_OF_WEEK);
                int col = (dayInWeek) % 7;
                periodBitmap[col] = true;
                hoursOnWeek.setHour(col, new BigDecimal(0));
                hoursOnWeekInTheAcnt.setHour(col, new BigDecimal(0));

                day.set(Calendar.DAY_OF_MONTH, day.get(Calendar.DAY_OF_MONTH) + 1);
            }
        }
    }

    protected void reCaculateSumAll() {
        for (int i = DtoHoursOnWeek.SATURDAY; i <= DtoHoursOnWeek.SUMMARY; i++) {
            hoursOnWeek.setHour(i, new BigDecimal(0));
            hoursOnWeekInTheAcnt.setHour(i, new BigDecimal(0));
        }

        for (int i = 0; i < getRowCount(); i++) {
            DtoWeeklyReport dto = (DtoWeeklyReport) getRow(i);
            if (dto != null) {
                BigDecimal sum = new BigDecimal(0);

                for (int j = DtoHoursOnWeek.SATURDAY; j <= DtoHoursOnWeek.FRIDAY; j++) {
                    BigDecimal hour = dto.getHour(j);
                    if (periodBitmap[j] && hour != null) {
                        hoursOnWeek.add(j, hour);
                        hoursOnWeek.add(DtoHoursOnWeek.SUMMARY, hour);

                        if (dto.isWeeklyReportSum() == false) {
                            hoursOnWeekInTheAcnt.add(j, hour);
                            hoursOnWeekInTheAcnt.add(DtoHoursOnWeek.SUMMARY, hour);
                        }

                        sum = sum.add(hour);
                    }
                }

                dto.setSumHour(sum);
            }
        }

        //更新sum列的显示
        if (getRowCount() > 0) {
            fireTableRowsUpdated(0, getRowCount() - 1);
        }

        notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
    }

    private void reCaculateSumWhenDelete(DtoWeeklyReport deletedDto) {
        if (deletedDto != null) {

            for (int j = DtoHoursOnWeek.SATURDAY; j <= DtoHoursOnWeek.FRIDAY; j++) {
                BigDecimal hour = deletedDto.getHour(j);
                if (periodBitmap[j] && hour != null) {

                    hoursOnWeek.subtract(j, hour);
                    hoursOnWeek.subtract(DtoWeeklyReport.SUMMARY, hour);

                    if (deletedDto.isWeeklyReportSum() == false) {

                        hoursOnWeekInTheAcnt.subtract(j, hour);
                        hoursOnWeekInTheAcnt.subtract(DtoWeeklyReport.SUMMARY, hour);
                    }
                }
            }
            notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
        }
    }

    private void reCaculateSumWhenAdd(int row) {
        DtoWeeklyReport addedDto = (DtoWeeklyReport) getRow(row);
        if (addedDto != null) {
            BigDecimal sum = new BigDecimal(0);

            for (int j = DtoHoursOnWeek.SATURDAY; j <= DtoHoursOnWeek.FRIDAY; j++) {
                BigDecimal hour = addedDto.getHour(j);
                if (periodBitmap[j] && hour != null) {
                    hoursOnWeek.add(j, hour);
                    hoursOnWeek.add(DtoWeeklyReport.SUMMARY, hour);

                    if (addedDto.isWeeklyReportSum() == false) {
                        hoursOnWeekInTheAcnt.add(j, hour);
                        hoursOnWeekInTheAcnt.add(DtoWeeklyReport.SUMMARY, hour);
                    }

                    sum = sum.add(hour);
                }
            }

            addedDto.setSumHour(sum);

            //更新sum列的显示
            fireTableCellUpdated(row, SUMMARY_COLUMN_INDEX);
            notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
        }
    }

    private void reCaculateSumWhenUpdateCell(int row, int col, BigDecimal newValue) {
        DtoWeeklyReport dto = (DtoWeeklyReport) getRow(row);
        BigDecimal oldValue = dto.getHour(col - SATURDAY_COLUMN_INDEX);
        if (periodBitmap[col - SATURDAY_COLUMN_INDEX] && dto != null) {
            BigDecimal minus = null;
            if (oldValue == null) {
                minus = newValue;
            } else {
                if (newValue == null) {
                    minus = oldValue.negate();
                } else {
                    minus = newValue.subtract(oldValue);
                }
            }

            if ((new BigDecimal(0)).equals(minus) == false) {
                BigDecimal sum = dto.getSumHour();
                if (sum == null) {
                    sum = new BigDecimal(0);
                }
                sum = sum.add(minus);
                dto.setSumHour(sum);

                int day = col - SATURDAY_COLUMN_INDEX;
                hoursOnWeek.add(day, minus);
                hoursOnWeek.add(DtoWeeklyReport.SUMMARY, minus);

                if (dto.isWeeklyReportSum() == false) {
                    hoursOnWeekInTheAcnt.add(day, minus);
                    hoursOnWeekInTheAcnt.add(DtoWeeklyReport.SUMMARY, minus);
                }

                //更新sum列的显示
                fireTableCellUpdated(row, SUMMARY_COLUMN_INDEX);
                notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
            }
        }
    }

    public boolean[] getPeriodBitmap() {
        return this.periodBitmap;
    }

    public DtoHoursOnWeek getHoursOnWeek() {
        return hoursOnWeek;
    }

    public DtoHoursOnWeek getHoursOnWeekInTheAcnt() {
        return hoursOnWeekInTheAcnt;
    }

    public boolean isDataEditable() {
        return isDataEditable;
    }

    public void setDataEditable(boolean isDataEditable) {
        this.isDataEditable = isDataEditable;
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
