/*
 * Created on 2008-3-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attribute.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.attribute.form.AfAttribute;
import server.essp.hrbase.attribute.service.IAttributeService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcSaveAttribute extends AbstractESSPAction {

    /**
     * 保存人员属性
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData data) throws BusinessException {
        IAttributeService attService = (IAttributeService)this.
        getBean("attributeService");
        AfAttribute af = (AfAttribute)this.getForm();
        attService.saveHrbAttribute(af);
    }

}
