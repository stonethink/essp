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
     * 保存
     * @param temp
     */
    public void save(TsImportTemp temp);
 
    
    /**
     * 按人和时间得到暂存档中的记录
     * @return
     */
    public List getImportInfoList();
    
    /**
     * 根据loginId，beginDate，endDate查询暂存档中符合条件的记录
     * @param loginId
     * @param beginDate
     * @param endDate
     * @return List
     */
    public List getImportTemp(String loginId,Date beginDate,
            Date endDate);
    
  
    /**
     * 删除暂存档中的所有记录
     *
     */
    public void deleteAllTemp();
}
