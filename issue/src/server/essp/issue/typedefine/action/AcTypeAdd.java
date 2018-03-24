package server.essp.issue.typedefine.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import org.apache.struts.action.ActionForm;
import server.essp.issue.typedefine.form.AfType;
import db.essp.issue.IssueType;
import com.wits.util.Parameter;
import java.lang.Long;
import server.essp.issue.typedefine.logic.LgType;

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
public class AcTypeAdd extends AbstractBusinessAction {
    /**
     * 依据传入的AfType新增IssueType
     * Call: LgType.add(AfType)
     * ForwardID: list
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction method*/
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            AfType issueTypeForm = (AfType)this.getForm();
            LgType logic = new LgType();
            logic.setDbAccessor(this.getDbAccessor());
            logic.add(issueTypeForm);

            request.setAttribute("refresh", "opener.refreshSelf()");

    }
}
