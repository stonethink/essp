package client.essp.timesheet.weeklyreport.common;

import java.util.List;

/**
 * <p>Title: StandarHoursListener</p>
 *
 * <p>Description: 标准工时变化监听器
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
     * 触发标准工时有变化
     * @param dataList 按列顺序排列的
     *  standar hours <List>
	 * 	leave hours <List>
	 *  overtime hours <List>
	 *  leave and overtime hours is effective <Boolean>
     */
    public void standarHoursChanged(List dataList);
}
