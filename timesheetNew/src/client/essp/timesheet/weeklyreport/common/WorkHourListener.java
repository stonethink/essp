package client.essp.timesheet.weeklyreport.common;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDay;

/**
 * <p>Title: WorkHourListener</p>
 *
 * <p>Description: ��ʱ�仯��������
 *            ��DetailTable�еĹ�ʱ�����仯ʱ������仯���кͱ仯���ֵ����������</p>
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
     * ������ʱ�仯
     * @param columnIndex int �仯��������
     * @param sumHour Double  �仯���ֵ
     */
    public void workHourChanged(int columnIndex, DtoTimeSheetDay dtoDay);
}
