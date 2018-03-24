package server.essp.pms.psr.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import java.util.List;
import c2s.essp.common.user.DtoUser;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import server.framework.taglib.util.SelectOptionImpl;
import server.essp.pms.psr.bean.VbProjectStatusReport;
import server.framework.common.Constant;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcExportPSRPre  extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        VbProjectStatusReport webVo = new VbProjectStatusReport();
        DtoUser user = this.getUser();
        LgAccountUtilImpl lgAcnt = new LgAccountUtilImpl();
        List acntOpts = lgAcnt.listOptAccountsDefaultStyle(user.getUserLoginId());
        SelectOptionImpl deflt = new SelectOptionImpl("---Please Select---","");
        if(acntOpts != null) {
            acntOpts.add(0, deflt);
        }
        if(getAccount() != null && getAccount().getRid() != null) {
            webVo.setSelectedAcnt(getAccount().getRid().toString());
        }
        webVo.setAcntList(acntOpts);
        request.setAttribute(Constant.VIEW_BEAN_KEY, webVo);
    }
}
