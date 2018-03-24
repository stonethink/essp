package client.essp.timesheet.calendar;

import java.util.*;

import javax.swing.JTable;

import client.essp.common.calendar.CalendarCellRenderer;
import client.essp.common.calendar.EditableCalendarPanel;
/**
 *
 * <p>Title: TimesheetCalendarPanel</p>
 *
 * <p>Description: ����������</p>
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
     * ��ӦDataChanged�¼�
     * ���ѡ��ĵ����ڷ����仯�򼤻�SelectDateChange�¼�
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
     * ��ʼ���̳к��model�����õ����������
     */
    protected void initModel(){
         TimesheetCalendarModel timesheetCalendarModel
                = new TimesheetCalendarModel();
        this.setTblModel(timesheetCalendarModel);
        JTable table = new JTable(this.getTblModel());
        this.setTable(table);
    }
    /**
     * ʹ���޸Ĺ����Renderer
     */
	protected CalendarCellRenderer initCellRenderer() {
		return new TimesheetCalendarCellRenderer();
	}
	/**
     * ���������
     * @param l CalendarSelectDateListener
     */
    public void addCalendarSelectDateListener(CalendarSelectDateListener l) {
        calendarSelectDateListeners.add(l);
    }
    /**
     * ��ӦSelectDateChange�¼����ü�����ʵ�ֵ�selectDateChange����
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
