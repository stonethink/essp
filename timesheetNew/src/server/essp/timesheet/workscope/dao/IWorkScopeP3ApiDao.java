package server.essp.timesheet.workscope.dao;

import java.util.List;
import c2s.essp.timesheet.workscope.DtoAccount;
import java.util.Date;

import com.primavera.integration.client.bo.object.Activity;

/**
 *
 * <p>Title: Work Scope数据类</p>
 *
 * <p>Description: 获取当前用户所工作的Activity</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IWorkScopeP3ApiDao {

    /**
     * 获取当前用户有存取权限的所有Account
     * @return List <DtoAccount>
     * @throws Exception
     */
    public DtoAccount getAccount(Integer AccountId) throws Exception;

    /**
     * 获取当前用户对指定Account下有存取权限的所有Activity
     * @param beginDate Date
     * @param endDate Date
     * @param noStart boolean
     * @param finished boolean
     * @return List <DtoActivity>
     * @throws Exception
     */
    public List getActivityList() throws Exception;
    
    /**
     * 根据ActivityId获取ActivityShowName,如果没找到则返回空字符串
     * @param activityId Long
     * @return String
     */
    public String getActivityShowName(Long activityId);
    
    /**
	 * 获取作业分类码
	 * 
	 * @param projObjId
	 * @return String
	 * @throws Exception
	 */
	public String getActivityCode(Activity activity)
			throws Exception;
}
