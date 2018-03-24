/*
 * Created on 2008-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attributegroup.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.attributegroup.service.IAttributeGroupService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcAttributeGroupList extends AbstractESSPAction {

    /**
     * 列出所有人員屬性
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
            TransactionData data) throws BusinessException {
        IAttributeGroupService attGroupService = (IAttributeGroupService)this.
        getBean("attributeGroupService");
        List attList = attGroupService.listAllAttributeGroup();
        request.setAttribute("webVo", attList);
    }
}
