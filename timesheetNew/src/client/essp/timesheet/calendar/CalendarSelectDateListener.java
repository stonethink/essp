package client.essp.timesheet.calendar;

import java.util.Date;
/**
 *
 * <p>Title: CalendarSelectDateListener</p>
 *
 * <p>Description: 监听选择日期发生变化的监听器</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface CalendarSelectDateListener {
    public void selectDateChanged(Date date);
}
