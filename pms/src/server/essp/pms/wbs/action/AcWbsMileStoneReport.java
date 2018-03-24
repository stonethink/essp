package server.essp.pms.wbs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.account.DtoPmsAcnt;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import server.essp.pms.wbs.logic.LgMileStone;
import server.essp.pms.wbs.viewbean.VbMileStoneReport;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import com.wits.util.StringUtil;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;

public class AcWbsMileStoneReport extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        String acntRidStr = StringUtil.nvl(request.getParameter("acntRid"));
        Long acntRid = null;
        if("".equals(acntRidStr)){
            IDtoAccount currAccount = (IDtoAccount)getSession().getAttribute( DtoPmsAcnt.SESSION_KEY );
            if(currAccount != null)
                acntRid = currAccount.getRid();
        }else{
            acntRid = new Long(acntRidStr);
        }
        VbMileStoneReport webVo = new VbMileStoneReport();
        if(acntRid != null){
            LgMileStone lgMileStone = new LgMileStone();
            List mileStoneList = lgMileStone.listMileStone(acntRid);
            webVo.setMileStoneList(mileStoneList);
            webVo.setSelectedAcnt(acntRid.toString());
        }
        DtoUser user = this.getUser();
        LgAccountUtilImpl lgAcnt = new LgAccountUtilImpl();
        List acntOpts = lgAcnt.listOptAccountsDefaultStyle(user.getUserLoginId());
        log.info("mile stone user:" + user.getUserLoginId());
        SelectOptionImpl deflt = new SelectOptionImpl("---Please Select---","");
        if(acntOpts != null)
            acntOpts.add(0,deflt);
        webVo.setAcntList(acntOpts);
        request.setAttribute(Constant.VIEW_BEAN_KEY, webVo);
        ReturnInfo returnInfo = data.getReturnInfo();
        returnInfo.setForwardID("list");
    }
}
