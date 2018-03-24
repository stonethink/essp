package server.essp.common.humanAllocate.action;

import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import server.framework.common.Constant;
import server.essp.common.ldap.LDAPUtil;
import server.essp.common.humanAllocate.viewbean.VbAllocInAD;
import java.util.ArrayList;
import server.framework.taglib.util.SelectOptionImpl;
import server.essp.common.humanAllocate.form.AfUserAllocInAD;

public class AcUserAllocInADPre extends AbstractBusinessAction {
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
        List domainList = LDAPUtil.getDomainIdList();

        VbAllocInAD vb = (VbAllocInAD) request.getAttribute(Constant.VIEW_BEAN_KEY);
        if(vb == null)
               vb = new VbAllocInAD();
        List destList=new ArrayList();
        for(int i=0;i<domainList.size();i++){
            destList.add(new SelectOptionImpl((String)domainList.get(i),(String)domainList.get(i)));
        }
        vb.setDomainList(destList);
        request.setAttribute(Constant.VIEW_BEAN_KEY,vb);
        request.setAttribute("userAllocInADForm",(AfUserAllocInAD)request.getAttribute("userAllocInADForm"));

        data.getReturnInfo().setForwardID("success");
    }
}
