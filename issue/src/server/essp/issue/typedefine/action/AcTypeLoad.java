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
import server.essp.issue.typedefine.viewbean.VbType;

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
public class AcTypeLoad extends AbstractBusinessAction {
    /**
     * 依据传入的typeName获取该AfType对象
     * Call: LgType.loadAfType(typeName)
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
        AfType  form=(AfType)this.getForm();
        String typeName=form.getTypeName();
        LgType logic = new LgType();
        logic.setDbAccessor(this.getDbAccessor());

        VbType oneViewBean=logic.getOneViewBean(typeName);
        request.setAttribute("oneVB",oneViewBean);
    }

    /** @link dependency */
    /*# server.essp.issue.typedefine.logic.LgType lnkLgType; */

    /** @link dependency */
    /*# AfType lnkAfType; */
}
