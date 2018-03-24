/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.busyRate.dao;

import java.util.Date;
import java.util.List;

import db.essp.timesheet.TsAccount;


public interface IBusyRateGatherDao {
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
        public List getValidHours(Date beginDate, Date endDate,String deptIds);
    
        /**
         * ���ݲ�ѯ�����õ��޲�ֵ��ʱ
         * @param beginDate Date
         * @param endDate Date
         * @param acntIdStr String
         * @return Double
         */
        public List getActualHours(Date beginDate,Date endDate,String deptIds);
    
        
        /**
         * �����T��̖�õ��������T�µ����ІT������
         * @param acntId
         * @return List
         */
        public List getLoginIdsByDeptIds(String deptIds);
    
}
