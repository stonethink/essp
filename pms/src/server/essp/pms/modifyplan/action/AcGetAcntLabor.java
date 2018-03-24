package server.essp.pms.modifyplan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import itf.account.AccountFactory;
import java.util.List;
import java.util.ArrayList;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.ebs.DtoPmsAcnt;

public class AcGetAcntLabor extends AbstractESSPAction {
    public AcGetAcntLabor() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        String acntRid=(String)data.getInputInfo().getInputObj("AcntRid");
        List labor=new ArrayList();
        if(acntRid==null&&acntRid.length()<1){
            DtoPmsAcnt dtoPmsAcnt=(DtoPmsAcnt)this.getSession().getAttribute(IDtoAccount.SESSION_KEY);
            acntRid=dtoPmsAcnt.getRid().toString();
        }
        if(acntRid!=null&&!acntRid.equals("")){
            labor = AccountFactory.create().listLaborResourceByAcntRid(
                new Long(acntRid));
        }
        ReturnInfo returnInfo=new ReturnInfo();
        returnInfo.setReturnObj("AcntLaborList",labor);
        data.setReturnInfo(returnInfo);

    }
}
