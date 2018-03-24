package client.essp.timesheet.calendar;

import java.util.*;

import javax.swing.JTable;

import client.essp.common.calendar.CalendarCellRenderer;
import client.essp.common.calendar.EditableCalendarPanel;
/**
 *
 * <p>Title: TimesheetCalendarPanel</p>
 *
 * <p>Description: 日历主界面</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class TimesheetCalendarPanel extends EditableCalendarPanel {
    private List<CalendarSelectDateListener> calendarSelectDateListeners
            = new ArrayList();
    private Date lastSelectDate;

    public TimesheetCalendarPanel(){
        super(true);
    }
    /**
     * 响应DataChanged事件
     * 如果选择的的日期发生变化则激活SelectDateChange事件
     */
    protected void fireDataChanged() {
        List<Calendar> calendars = getSelectCalendars();
        if (calendars != null && calendars.size() > 0) {
            Date date = calendars.get(calendars.size() - 1).getTime();
            if (!date.equals(lastSelectDate)) {
                lastSelectDate = date;
                fireSelectDateChange(date);
            }
        }
        super.fireDataChanged();
    }
    /**
     * 初始化继承后的model并设置到日历表格中
     */
    protected void initModel(){
         TimesheetCalendarModel timesheetCalendarModel
                = new TimesheetCalendarModel();
        this.setTblModel(timesheetCalendarModel);
        JTable table = new JTable(this.getTblModel());
        this.setTable(table);
    }
    /**
     * 使用修改过后的Renderer
     */
	protected CalendarCellRenderer initCellRenderer() {
		return new TimesheetCalendarCellRenderer();
	}
	/**
     * 加入监听者
     * @param l CalendarSelectDateListener
     */
    public void addCalendarSelectDateListener(CalendarSelectDateListener l) {
        calendarSelectDateListeners.add(l);
    }
    /**
     * 响应SelectDateChange事件调用监听者实现的selectDateChange方法
     * @param date Date
     */
    private void fireSelectDateChange(Date date) {
        Iterator<CalendarSelectDateListener> iter
                = calendarSelectDateListeners.iterator();
        while(iter.hasNext()) {
            iter.next().selectDateChanged(date);
        }
    }


}
