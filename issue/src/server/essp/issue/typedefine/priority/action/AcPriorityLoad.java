package server.essp.issue.typedefine.priority.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.issue.typedefine.priority.logic.LgPriority;
import server.essp.issue.typedefine.priority.viewbean.VbPriority;
import com.wits.util.StringUtil;
import server.essp.issue.typedefine.priority.form.AfPriority;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author QianLiu
 * @version 1.0*/
public class AcPriorityLoad extends AbstractBusinessAction {
    /**
     * 依据传入的typeName和priority获取该AfType对象
     * Call: LgType.loadAfType(typeName,priority)
     * ForwardID: update
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws server.framework.common.BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction method*/
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        AfPriority form=(AfPriority)this.getForm();
        String typeName=form.getTypeName();
        String priority=form.getPriority();
        if (StringUtil.nvl(typeName).equals("")==true){
              typeName="";
        }
        if (StringUtil.nvl(priority).equals("")==true){
             priority="";
        }
        LgPriority logic = new LgPriority();

        VbPriority oneViewBean=logic.getOneViewBean(typeName,priority);
        oneViewBean.setTypeName(typeName);

        request.setAttribute("oneVB",oneViewBean);
    }
}
