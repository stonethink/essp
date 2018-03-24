package server.essp.timesheet.workscope.dao;

import java.util.List;
import c2s.essp.timesheet.workscope.DtoAccount;
import java.util.Date;

import com.primavera.integration.client.bo.object.Activity;

/**
 *
 * <p>Title: Work Scope������</p>
 *
 * <p>Description: ��ȡ��ǰ�û���������Activity</p>
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
     * ��ȡ��ǰ�û��д�ȡȨ�޵�����Account
     * @return List <DtoAccount>
     * @throws Exception
     */
    public DtoAccount getAccount(Integer AccountId) throws Exception;

    /**
     * ��ȡ��ǰ�û���ָ��Account���д�ȡȨ�޵�����Activity
     * @param beginDate Date
     * @param endDate Date
     * @param noStart boolean
     * @param finished boolean
     * @return List <DtoActivity>
     * @throws Exception
     */
    public List getActivityList() throws Exception;
    
    /**
     * ����ActivityId��ȡActivityShowName,���û�ҵ��򷵻ؿ��ַ���
     * @param activityId Long
     * @return String
     */
    public String getActivityShowName(Long activityId);
    
    /**
	 * ��ȡ��ҵ������
	 * 
	 * @param projObjId
	 * @return String
	 * @throws Exception
	 */
	public String getActivityCode(Activity activity)
			throws Exception;
}
