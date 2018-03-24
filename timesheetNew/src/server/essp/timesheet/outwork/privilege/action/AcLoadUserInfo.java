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

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.outwork.privilege.service.IOutWorkerPriService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.outwork.DtoUser;

public class AcLoadUserInfo extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        IOutWorkerPriService service = (IOutWorkerPriService)
                                         this.getBean("outWorkerPriService");
        List userList = service.listUserInfo();
        data.getReturnInfo().setReturnObj(DtoUser.DTO_USER_LIST, userList);
    }
}
