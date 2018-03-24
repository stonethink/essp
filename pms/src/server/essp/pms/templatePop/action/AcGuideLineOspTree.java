package server.essp.pms.templatePop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.TransactionData;
import c2s.essp.pms.wbs.DtoKEY;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.wbs.logic.LgWbs;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: »ñµÃospÊ÷</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class AcGuideLineOspTree extends AbstractESSPAction {

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

        LgWbs wbsLogic=new LgWbs();
        String s=(String) data.getInputInfo().getInputObj(DtoKEY.ACCOUNT_ID);

        ITreeNode treeNode=wbsLogic.getWbsTree(s);

        data.getReturnInfo().setReturnObj(DtoKEY.WBSTREE,treeNode);
    }
}
