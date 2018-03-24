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
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.privilege.service.IPrivilegeSiteService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
/**
 * ≥ı ºªØUSER
 * @author TBH
 */
public class AcInitUserList  extends  AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
                    HttpServletResponse response, TransactionData data)
                    throws BusinessException {
    IPrivilegeSiteService logic = (IPrivilegeSiteService)this.
    getBean("PrivilegeService");
    List listRole = logic.getUserList();
    if(listRole == null){
        listRole = new ArrayList();
    }
    request.setAttribute("webVo", listRole);
}

}
