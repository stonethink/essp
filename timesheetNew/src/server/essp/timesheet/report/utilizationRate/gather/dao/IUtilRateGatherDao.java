package server.essp.timesheet.report.utilizationRate.gather.dao;

import java.util.List;
import java.util.Date;

import db.essp.timesheet.TsAccount;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public interface IUtilRateGatherDao {
    /**
     * ���ݲ���RID�õ������Ϣ
     * @param acntRid Long
     * @return List
     */
    public TsAccount getDeptInfoByMonths(Long acntRid);

    /**
     * ���ݲ�ѯ�����õ��в�ֵ��ʱ
     * @param beginDate Date
     * @param endDate Date
     * @param acntIdStr String
     * @return Double
     */
    public List getValidHours(final Date beginDate, final Date endDate,
                               final String acntIdStr);

    /**
     * ������ԃ�l���õ����H���r��
     * @param beginDate Date
     * @param endDate Date
     * @param acntIdStr String
     * @return Double
     */
    public List getActualHours(final Date beginDate, final Date endDate,
                                final String acntIdStr);


    /**
    * ��������Ȩ�޵õ�����Ͻ�Ĳ��ż���
    * @param loginId String
    * @return List
    */
    public List listDeptInfo(String acntId);
    
    /**
     * PMO��ѯʱ�г����в���
     * @return
     */
    public List listDept();
    
    /**
     * ������Ŀ���ŵõ���ǰ��Ŀ�µ�Ա��
     * @param acntId
     * @return List
     */
    public List getLoginIdsByCondition(final String acntId);

}
