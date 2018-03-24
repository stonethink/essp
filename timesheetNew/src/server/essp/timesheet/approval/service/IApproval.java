package server.essp.timesheet.approval.service;

import java.util.List;
import java.util.Date;
import c2s.essp.timesheet.approval.DtoTsApproval;

/**
 * <p>Title: Approval Interface</p>
 *
 * <p>Description: ���幤ʱ��������Ҫʵ�ֵĹ���
 *   1.PM��RM�г��������Ĺ�ʱ��
 *   2.PM��RM��׼��ʱ��
 *   3.PM��RM���ع�ʱ��</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IApproval {

    /**
     * �г�ָ����Ŀ��ʱ������Ĺ�ʱ��
     * @param acntRid Long
     * @param begin Date
     * @param end Date
     * @param queryWay String 
     * @param managerId String
     * @return List<DtoTsApproval>
     */
    public List<DtoTsApproval> pmList(Long acntRid, Date begin, Date end, 
    		                          String queryWay, String managerId);

    /**
     * �г�ָ��ʱ�������ڣ���ǰ�û�ΪRM������Ա���Ĺ�ʱ��
     * @param managerId String
     * @param begin Date
     * @param end Date
     * @param queryWay String
     * @return List<DtoTsApproval>
     */
    public List<DtoTsApproval> rmList(String managerId, Date begin, Date end, String queryWay);

    /**
     * PM��׼��ʱ��
     * @param tsList List<DtoTsApproval>
     */
    public void pmApproval(List<DtoTsApproval> tsList);

    /**
     * RM��׼��ʱ��
     * @param tsList List<DtoTsApproval>
     */
    public void rmApproval(List<DtoTsApproval> tsList);

    /**
     * PM���ع�ʱ������ҪeMail֪ͨ�����Ա
     * @param tsList List<DtoTsApproval>
     * @param loginId String
     */
    public void pmReject(List<DtoTsApproval> tsList, String loginId, String reason);

    /**
     * RM���ع�ʱ������ҪeMail֪ͨ�����Ա
     * @param tsList List<DtoTsApproval>
     * @param loginId String
     */
    public void rmReject(List<DtoTsApproval> tsList, String loginId, String reason);
}
