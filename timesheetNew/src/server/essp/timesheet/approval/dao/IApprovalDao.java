package server.essp.timesheet.approval.dao;

import java.util.Date;
import java.util.List;

import c2s.essp.common.user.DtoUserBase;

/**
 * <p>Title: Approval Data Access Object</p>
 *
 * <p>Description: �����빤ʱ������ص����ݴ�ȡ����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IApprovalDao {
    /**
     * �г���ǩ�����µ�������Ա
     * @param managerId String
     * @return List
     */
    public List<DtoUserBase> listPersonByManager(String managerId);
    
    /**
     * ������ѡ��ר������ʼ���ڣ��������ڣ��Լ���ʱ��״̬��ѯ��ʱ��
     * @param accountRids
     * @param begin
     * @param end
     * @param status
     * @return
     */
    public List listPmApproval(String accountRids, Date begin, Date end, String status);

}
