/*
 * Created on 2007-12-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.privilege.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.privilege.service.IPrivilegeSiteService;
import server.framework.common.BusinessException;
/**
 * 區域權限列表
 * @author TBH
 */
public class AcPrivilegeSiteList  extends  AbstractESSPAction{

    public static final String Selected="selectd";
    /**
     * 显示可见用户的信息
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                    HttpServletResponse response, TransactionData data)
                    throws BusinessException {
    String loginId = (String)request.getParameter("LoginId");
    String loginName = (String)request.getParameter("loginName");
    String domain = (String)request.getParameter("domain");
    IPrivilegeSiteService logic = (IPrivilegeSiteService)this.getBean("PrivilegeService");
    List listSite = logic.listEnabled2VisibleSite(loginId);
    List list = new ArrayList();
   
    if(loginId ==null){
        request.setAttribute("webVo", list);
        return;
    }else{
        List selectSite = (List) logic.listAllChoiceSite(loginId);
        request.getSession().setAttribute(Selected, selectSite);
        request.getSession().setAttribute("loginId",loginId);
        request.getSession().setAttribute("loginName",loginName);
        request.getSession().setAttribute("domain",domain);
    }
    request.setAttribute("webVo", listSite);
  }
}
