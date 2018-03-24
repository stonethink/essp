/*
 * Created on 2008-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attributegroup.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.attributegroup.form.AfAttributeGroup;
import server.essp.hrbase.attributegroup.service.IAttributeGroupService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcLoadAttributeGroup extends AbstractESSPAction {

    /**
     * 列出人员属性資料
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
            TransactionData data) throws BusinessException {
        String rid = request.getParameter("rid");
        AfAttributeGroup af;
        IAttributeGroupService attGroupService = (IAttributeGroupService)this.
            getBean("attributeGroupService");
        if(rid != null && !rid.equals("")){
          af = attGroupService.loadAttributeGroup(new Long(rid));
        }else{
          af = attGroupService.loadAttributeGroup(null);
        }
        request.setAttribute("webVo", af);
    }

}
