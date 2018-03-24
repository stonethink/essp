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
        * ����
        * @param syncExcp
        * @throws BusinessException
        */
       public void save(PpSyncException syncExcp) throws BusinessException;
       
       /**
        * �г�״̬Ϊ����״̬��ͬ���쳣��Ϣ��¼
        * @return
        * @throws BusinessException
        */
       public List listSyncException(String status) throws BusinessException;
       
       /**
        * �޸�״̬
        * @param syncExcp
        * @throws BusinessException
        */       
       public void updateStaus(Long rid) throws BusinessException;
       
       /**
        * ����ACNTRID���������TIMESHEET��FINANCE��¼
        * @param anctRid
        * @throws BusinessException
        */
       public void updateTimeSheetOrFinance(Long anctRid)throws BusinessException;
}
