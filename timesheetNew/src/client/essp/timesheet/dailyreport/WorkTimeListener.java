package client.essp.timesheet.dailyreport;

import c2s.essp.timesheet.dailyreport.DtoDrActivity;

public interface WorkTimeListener {
	
	/**
     * ������ʱ�仯
     * @param columnIndex int �仯��������
     * @param sumHour Double  �仯���ֵ
     */
    public void workTimeChanged(Double sumHour);

}
