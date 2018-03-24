package server.essp.timesheet.approval.service;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import db.essp.timesheet.TsAccount;
import server.essp.timesheet.account.dao.IAccountDao;

/**
 * <p>Title: ApprovalServiceImp</p>
 *
 * <p>Description: ��ʱ��������ʵ��</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class ApprovalServiceImp implements IApprovalService {
    private IApproval approval;
    private IAccountDao accountDao;
    private final static String SELECT_FIRST_OPTION = "Select All";//��������ѡ��
    /**
     * ��Ŀ����ȷ�Ϲ�ʱ��
     *
     * @param tsList List
     * @param employeeId String
     */
    public void approvalByPM(List tsList, String employeeId) {
    	ApprovalProxy.setEmployeeId(employeeId);
        approval.pmApproval(tsList);
    }

    /**
     * ��Դ����ȷ�Ϲ�ʱ��
     * @param tsList List
     * @param employeeId String
     */
    public void approvalByRM(List tsList, String employeeId) {
    	ApprovalProxy.setEmployeeId(employeeId);
        approval.rmApproval(tsList);
    }


    /**
     * �г���ǰ�û�ΪPM������״̬������Ŀ
     *
     * @return Vector
     */
    public Vector listProjectByManager(String loginId) {
        Vector resultItem = new Vector();
        List<TsAccount> list = accountDao.listAccounts(loginId);
        if(list != null && list.size() > 0){
            DtoComboItem dtoItem = null;
            dtoItem = new DtoComboItem(SELECT_FIRST_OPTION, Long.valueOf(-1));
            resultItem.add(dtoItem);
            for (TsAccount tsAccount : list) {
                dtoItem = new DtoComboItem(tsAccount.getAccountId()+"--"
                                           +tsAccount.getAccountName(),
                                           tsAccount.getRid());
                resultItem.add(dtoItem);
            }
        }
        return resultItem;
    }

    /**
     * �г���ǰ�û�ΪRM������Ա����ָ��ʱ����ڵĹ�ʱ��
     * @param managerId String
     * @param begin Date
     * @param end Date
     * @param queryWay String
     * @return List
     */
    public List listTsByRm(String managerId, Date begin, Date end, 
    		               String queryWay) {
    	ApprovalProxy.setEmployeeId(managerId);
        return approval.rmList(managerId, begin, end, queryWay);
    }

    /**
     * �г�ָ����Ŀ��ʱ����ڵĹ�ʱ��
     *
     * @return List
     * @param acntRid Long
     * @param begin Date
     * @param end Date
     * @param queryWay String
     * @param managerId String
     */
    public List listTsByProject(Long acntRid, Date begin, Date end, 
    		                    String queryWay, String managerId) {
    	ApprovalProxy.setEmployeeId(managerId);
        return approval.pmList(acntRid, begin, end, queryWay, managerId);
    }

    /**
     * ��Ŀ�����ع�ʱ��
     * @param tsList List
     * @param loginId String
     * @param employeeId String
     */
    public void rejectByPM(List tsList, String loginId, String employeeId, String reason) {
    	ApprovalProxy.setEmployeeId(employeeId);
        approval.pmReject(tsList, loginId, reason);
    }

    /**
     * ��Դ�����ع�ʱ��
     * @param tsList List
     * @param loginId String
     * @param employeeId String
     */
    public void rejectByRM(List tsList, String loginId, String employeeId, String reason) {
    	ApprovalProxy.setEmployeeId(employeeId);
        approval.rmReject(tsList, loginId, reason);
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setApproval(IApproval approval) {
        this.approval = approval;
    }
}
