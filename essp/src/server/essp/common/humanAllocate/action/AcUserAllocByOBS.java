package server.essp.common.humanAllocate.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.common.humanAllocate.logic.LgUserAlloc;
import java.util.List;
import server.essp.common.humanAllocate.viewbean.VbAllocByOBS;
import itf.orgnization.OrgnizationFactory;
import itf.orgnization.IOrgnizationUtil;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcUserAllocByOBS extends AbstractBusinessAction {
    /**
     * executeAct
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse reponse,
                           TransactionData data) throws
            BusinessException {
        String orgId = request.getParameter("selectOrg");
        String isIncludeSubOrg = request.getParameter("isIncludeSubOrg");
        log.info("search users of " + orgId + " isIncludeSubOrg: "+isIncludeSubOrg);

        LgUserAlloc logic = new LgUserAlloc();
        logic.setDbAccessor(this.getDbAccessor());
        String q_orgId = "'" + orgId + "'";
        if(Boolean.valueOf(isIncludeSubOrg).booleanValue()) {
            IOrgnizationUtil orgUtil = OrgnizationFactory.create();
            q_orgId = orgUtil.getSubOrgs(orgId);
            log.info("#### orgId ####: "+q_orgId);
        }
        List detail = logic.allocByOBS(q_orgId);

        VbAllocByOBS vb = new VbAllocByOBS();
        vb.setDetail(detail);
        vb.setSelectOrg(orgId);
        vb.setIsIncludeSubOrg(isIncludeSubOrg);
        request.setAttribute(Constant.VIEW_BEAN_KEY,vb);
        data.getReturnInfo().setForwardID("success");
    }
}
