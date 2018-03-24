package server.essp.timesheet.approval.service;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * <p>Title: Approval Service interface</p>
 *
 * <p>Description: ����Approval Service����Ҫʵ�ֵĹ���</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IApprovalService {

    /**
     * �г�ָ����Ŀ��ʱ����ڵĹ�ʱ��
     * @param acntRid Long
     * @param begin Date
     * @param end Date
     * @param queryWay String
     * @param managerId String
     * @return List
     */
    public List listTsByProject(Long acntRid, Date begin, Date end, 
    		String queryWay, String managerId);

    /**
     * �г���ǰ�û�ΪPM������״̬������Ŀ
     * @param loginId
     * @return Vector
     */
    public Vector listProjectByManager(String loginId);

    /**
     * �г���ǰ�û�ΪRM������Ա����ָ��ʱ����ڵĹ�ʱ��
     * @param managerId String
     * @param begin Date
     * @param end Date
     * @param queryWay String
     * @return List
     */
    public List listTsByRm(String managerId, Date begin, Date end, 
    		String queryWay);

    /**
     * ��Ŀ����ȷ�Ϲ�ʱ��
     * @param tsList List
     * @param employeeId String
     */
    public void approvalByPM(List tsList, String employeeId);

    /**
     * ��Դ����ȷ�Ϲ�ʱ��
     * @param tsList List
     * @param employeeId String
     */
    public void approvalByRM(List tsList, String employeeId);

    /**
     * ��Ŀ�����ع�ʱ��
     * @param tsList List
     * @param loginId String
     * @param employeeId String
     * @param reason String
     */
    public void rejectByPM(List tsList, String loginId, String employeeId, String reason);

    /**
     * ��Դ�����ع�ʱ��
     * @param tsList List
     * @param loginId String
     * @param employeeId String
     */
    public void rejectByRM(List tsList, String loginId, String employeeId, String reason);
}
