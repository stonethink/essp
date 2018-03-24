package client.essp.timesheet.weeklyreport.common;

import java.util.List;

/**
 * <p>Title: StandarHoursListener</p>
 *
 * <p>Description: ��׼��ʱ�仯������
 * 	standar hours <List>
 * 	leave hours <List>
 *  overtime hours <List>
 *  leave and overtime hours is effective <Boolean>
 * </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface StandarHoursListener {

    /**
     * ������׼��ʱ�б仯
     * @param dataList ����˳�����е�
     *  standar hours <List>
	 * 	leave hours <List>
	 *  overtime hours <List>
	 *  leave and overtime hours is effective <Boolean>
     */
    public void standarHoursChanged(List dataList);
}
