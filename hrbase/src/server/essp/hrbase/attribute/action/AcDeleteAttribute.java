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
import server.essp.hrbase.attribute.service.IAttributeService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcDeleteAttribute extends AbstractESSPAction {

    /**
     * „h³ýÈË†TŒÙÐÔ
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
            TransactionData data) throws BusinessException {
        String rid = request.getParameter("rid");
        if(rid == null || "".equals(rid)) {
            return;
        }
        IAttributeService hrbAttService = (IAttributeService)this.getBean("attributeService");
        hrbAttService.deleteHrbAttribute(new Long(rid));
    }

}