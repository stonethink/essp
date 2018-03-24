package client.essp.timesheet.dailyreport;

import c2s.essp.timesheet.dailyreport.DtoDrActivity;

public interface WorkTimeListener {
	
	/**
     * 触发工时变化
     * @param columnIndex int 变化的列索引
     * @param sumHour Double  变化后的值
     */
    public void workTimeChanged(Double sumHour);

}
