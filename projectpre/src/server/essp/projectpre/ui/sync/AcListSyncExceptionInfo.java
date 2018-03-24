/*
 * Created on 2007-10-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.sync;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.syncException.ISyncExceptionService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

public class AcListSyncExceptionInfo extends AbstractESSPAction {

    /**
     * ï@Ê¾½ØÞDÊ§”¡µÄÓ›ä›
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {      
        String status = server.essp.projectpre.service.constant.Constant.ACTIVE;       
        ISyncExceptionService   syncLogic = (ISyncExceptionService) this.getBean("SyncExceptionLogic");       
        List viewBean = syncLogic.listSyncException(status);
        request.setAttribute(Constant.VIEW_BEAN_KEY, viewBean);
    }
}
