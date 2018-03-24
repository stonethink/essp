package server.essp.common.humanAllocate.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.common.humanAllocate.logic.LgUserAlloc;
import server.essp.common.humanAllocate.viewbean.VbAllocByCon;

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
public class AcUserAllocByConPre extends AbstractBusinessAction {
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
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
        LgUserAlloc logic = new LgUserAlloc();
        logic.setDbAccessor(this.getDbAccessor());

        VbAllocByCon vb =  (VbAllocByCon) request.getAttribute(Constant.VIEW_BEAN_KEY);
        if(vb == null)
            vb = new VbAllocByCon();
        //查询页面显示所需内容
        vb.setPostRanks(logic.searchAllPostRank());
        vb.setSkillLevel1(logic.searchAllSkillLeve11());
        vb.setSkillLevel2(logic.searchAllSkillLeve12());
        vb.setOpera1(logic.searchAllIndustry());
        vb.setLanguages(logic.searchAllLanguage());
        vb.setManagements(logic.searchAllManagement());
        vb.setGeneralRanks(logic.searchGeneralRanks());

        request.setAttribute(Constant.VIEW_BEAN_KEY,vb);
        request.setAttribute("userAllocConditionForm",request.getAttribute("userAllocConditionForm"));
        data.getReturnInfo().setForwardID("success");
    }
}
