package server.essp.issue.typedefine.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import java.lang.Long;
import db.essp.issue.IssueType;
import server.essp.issue.typedefine.form.AfType;
import com.wits.util.Parameter;
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
 * @version 1.0
 */
public class AcTypeUpdate extends AbstractBusinessAction {
    /**
     * 依据传入的AfType修改IssueType
     * Call: LgType.update(AfType)
     * ForwardID: update
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {

        AfType issueTypeForm = (AfType)this.getForm();
        LgType logic = new LgType();
        logic.setDbAccessor(this.getDbAccessor());
        logic.update(issueTypeForm);

        request.setAttribute("refresh", "parent.parent.refreshSelf()");
    }

    /** @link dependency */
    /*# LgType lnkLgType; */

    /** @link dependency */
    /*# AfType lnkAfType; */
}
