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

public class AcLoadAttribute extends AbstractESSPAction {

    /**
     * 列出人员属性資料
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
            TransactionData data) throws BusinessException {
        String rid = request.getParameter("rid");
        AfAttribute af;
        if(rid != null && "".equals(rid) == false) {
            IAttributeService attService = (IAttributeService)this.getBean("attributeService");
            af = attService.loadHrbAttribute(new Long(rid));
        } else {
            af = new AfAttribute();
        }
        request.setAttribute("webVo", af);
    }

}
