package client.essp.timesheet.weeklyreport.common;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDay;

/**
 * <p>Title: WorkHourListener</p>
 *
 * <p>Description: 工时变化监听器，
 *            当DetailTable中的工时发生变化时，将其变化的列和变化后的值传给监听者</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface WorkHourListener {

    /**
     * 触发工时变化
     * @param columnIndex int 变化的列索引
     * @param sumHour Double  变化后的值
     */
    public void workHourChanged(int columnIndex, DtoTimeSheetDay dtoDay);
}
