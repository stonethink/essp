/*
 * Created on 2008-5-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.outwork.privilege.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.outwork.DtoUser;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.outwork.privilege.service.IOutWorkerPriService;
import server.framework.common.BusinessException;

public class AcAddUser extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        String loginIds = (String) data.getInputInfo().getInputObj(DtoUser.DTO_USER_LOGINS);
        List uList = (List) data.getInputInfo().getInputObj(DtoUser.DTO_USER_LIST);
        IOutWorkerPriService service = (IOutWorkerPriService)
                                         this.getBean("outWorkerPriService");
        List userList = service.addUserInfo(loginIds,uList);
        data.getReturnInfo().setReturnObj(DtoUser.DTO_USER_LIST, userList);
    }
}
