/*
 * Created on 2007-10-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.aprm.dao;

import java.util.Date;
import java.util.List;
import db.essp.timesheet.TsImportTemp;

public interface IAPRMDao {
    /**
     * ����
     * @param temp
     */
    public void save(TsImportTemp temp);
 
    
    /**
     * ���˺�ʱ��õ��ݴ浵�еļ�¼
     * @return
     */
    public List getImportInfoList();
    
    /**
     * ����loginId��beginDate��endDate��ѯ�ݴ浵�з��������ļ�¼
     * @param loginId
     * @param beginDate
     * @param endDate
     * @return List
     */
    public List getImportTemp(String loginId,Date beginDate,
            Date endDate);
    
  
    /**
     * ɾ���ݴ浵�е����м�¼
     *
     */
    public void deleteAllTemp();
}
