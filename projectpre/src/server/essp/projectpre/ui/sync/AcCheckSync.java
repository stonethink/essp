/*
 * Created on 2007-10-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.sync;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.syncException.ISyncExceptionService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcCheckSync extends AbstractESSPAction {

        /**
         * 新增或修改截转失败的记录
         */
        public void executeAct(HttpServletRequest request,
                HttpServletResponse response, TransactionData data)
                throws BusinessException {       
                ISyncExceptionService   syncLogic = (ISyncExceptionService)
                this.getBean("SyncExceptionLogic");      
                Long rid=null;
                if (request.getParameter("CODE") != null) {
                   rid =Long.valueOf(request.getParameter("CODE")) ;
               }               
                syncLogic.updateTimeSheetOrFinance(rid);
             }
                 
}


