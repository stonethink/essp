package server.essp.pms.templatePop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.logic.LgOsp;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class AcGuideLineOspList extends AbstractESSPAction{
    public AcGuideLineOspList() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {

        LgOsp lg=new LgOsp();
        //得到osp一般只有一个
        Long ospRid=lg.getOsp();
        data.getReturnInfo().setReturnObj("ospRid",ospRid);
    }
}
