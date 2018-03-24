/*
 * Created on 2007-10-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.service.syncException;

import java.util.List;

import server.essp.projectpre.db.PpSyncException;
import server.framework.common.BusinessException;

public interface ISyncExceptionService {
       /**
        * 保存
        * @param syncExcp
        * @throws BusinessException
        */
       public void save(PpSyncException syncExcp) throws BusinessException;
       
       /**
        * 列出状态为激活状态的同步异常信息记录
        * @return
        * @throws BusinessException
        */
       public List listSyncException(String status) throws BusinessException;
       
       /**
        * 修改状态
        * @param syncExcp
        * @throws BusinessException
        */       
       public void updateStaus(Long rid) throws BusinessException;
       
       /**
        * 根据ACNTRID新增或更新TIMESHEET或FINANCE记录
        * @param anctRid
        * @throws BusinessException
        */
       public void updateTimeSheetOrFinance(Long anctRid)throws BusinessException;
}
