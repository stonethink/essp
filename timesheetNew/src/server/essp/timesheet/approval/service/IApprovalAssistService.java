package server.essp.timesheet.approval.service;

import db.essp.timesheet.TsTimesheetMaster;
import java.util.Date;
import c2s.essp.timesheet.approval.DtoTsApproval;
import java.util.List;

/**
 * <p>Title: IApprovalAssistService</p>
 *
 * <p>Description: ������˹������ڽ�����˹����е�һЩ����ҵ�����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface IApprovalAssistService {
	
	/**
     * ��д���ݵ�P3���ݿ���
     * @param tsRid Long
     */
	public void writeBackToP3(Long tsRid);
	
    /**
     * ��д���ݵ�P3���ݿ���
     * @param tsMaster TsTimesheetMaster
     */
    public void writeBackToP3(TsTimesheetMaster tsMaster);
    
    /**
     * ����P3�й�ʱ����״̬
     * @param status String
     * @param tsMaster TsTimesheetMaster
     */
    public void setTimesheetStatusInP3(TsTimesheetMaster tsMaster, String status);
    /**
     * �����ʼ��������Ա
     * @param loginId String
     * @param ccId String[]
     * @param vmFile String
     * @param title String
     * @param obj Object
     */
    public void sendMail(final String loginId, final  String[] ccId,
                         final String vmFile, final  String title,
                         final Object obj);
    /**
     * ��ȡĳһʱ����ڵı�׼����ʱ��
     * ѭ��ʱ��ε�ÿһ���P3�в�������ڵĹ���ʱ��
     * �ۼ����еĹ���ʱ��
     * @param begin Date
     * @param end Date
     * @param loginId String
     * @return Double
     */
    public Double getStandarHours(Date begin, Date end, String loginId);

    /**
     * PM���ʱ
     * �����һ���˵�ʵ�ʹ���ʱ�䣬�Ӱ�ʱ�估���ʱ��
     * �����õ�dto��
     * 1.���ݵ�ǰ����Ŀ�Լ�Timesheet Master��rid
     *   ��ѯTimesheet Detail�������û���򷵻ش���
     * 2.��Timesheet Master��rid��ѯ�����е�detail����
     *   (��Ϊ��Ҫ�������Ա�����ʱ��,���ʱ��ֻ���������)
     * 3.����detail��¼��ѯ��д����صĹ���ʱ��
     * 4.����Ǳ���Ŀ�µ�ʱ�����ۼ�ʵ�ʹ���ʱ��,�Ӱ�ʱ��
     * 5.������Ǳ���Ŀ��ѯcode_value�Ƿ��Ǽٱ�code����
     *   �ۼƵ����ʱ����
     * @param dtoTsApproval DtoTsApproval
     * @param acntRid Long
     * @param tsRid Long
     * @param approvalLevel String
     * @return boolean
     */
    public boolean setHoursOk(DtoTsApproval dtoTsApproval, Long acntRid,
                              Long tsRid, String approvalLevel);

    /**
     * RM���ʱ
     * �����һ���˵�ʵ�ʹ���ʱ�䣬�Ӱ�ʱ�估���ʱ��
     * �����õ�dto��
     * 1.���ݵ�ǰ��Timesheet Master��rid
     *   ��ѯTimesheet Detail�������û���򷵻ش���
     * 2.��Timesheet Master��rid��ѯ�����е�detail����
     * 3.����detail��¼��ѯ��д����صĹ���ʱ��
     * 4.��ѯcode_value�Ƿ��Ǽٱ�code
     *   �����ۼƵ����ʱ���ϣ������ۼ�ʵ�ʹ���ʱ��ͼӰ�ʱ��
     * @param dtoTsApproval DtoTsApproval
     * @param tsRid Long
	 * @param approvalLevel String
     * @return boolean
     */
    public boolean setHoursOk(DtoTsApproval dtoTsApproval, Long tsRid, 
    		                  String approvalLevel);
    /**
     * �ҵ�Ҫ�����ʼ��������Ա����������PM��ͬ�����е���ˣ�
     * @param tsMaster TsTimesheetMaster
     * @param vmFile String
     * @param title String
     * @param object Object
     * @param type String
     * @param loginId String
     * @param acntRidList List
     * @param rejectedAcntRid Long
     * @param mailToRM boolean
     */
    public void searchPersonAndMailForPM(TsTimesheetMaster tsMaster,
                                    String vmFile, String type,
                                    String loginId, List<Long> acntRidList,
                                    Long rejectedAcntRid, boolean mailToRM, String reason);
    /**
     * �ҵ�Ҫ�����ʼ��������Ա����������RM�ڲ�ͬ�����е���ˣ�
     * @param tsMaster TsTimesheetMaster
     * @param vmFile String
     * @param title String
     * @param object Object
     * @param type String
     * @param loginId String
     * @param acntRidList List
     */
    public void searchPersonAndMailForRM(TsTimesheetMaster tsMaster,
                                    String vmFile, String type,
                                    String loginId, List<Long> acntRidList, String reason);

    /**
     * ����������̱��ʱ�Ĳ���
     * @param processType String
     */
    public void processLevelChanged(String processType, String site);
}
