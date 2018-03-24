package server.essp.tc.weeklyreport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import itf.user.IUserUtil;
import itf.user.UserFactory;

public class AcWeeklyReportInit extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        String userId = (String)inputInfo.getInputObj(DtoTcKey.USER_ID);
        String dept = null;
        /*
        DtoUser user = (DtoUser) request.getSession().getAttribute(DtoUser.SESSION_NAME);
        if (user != null) {
            userId = user.getUseLoginName();
            dept = user.getOrganization();
        } else {
//            //for test
//            userId = "stone.shi";
//            dept = "Four Dept";

            throw new BusinessException("E0000","Please login first.");
        }
        */

       IUserUtil userUtil = UserFactory.create();
       DtoUser user = userUtil.getUserByLoginId(userId);
       if( user != null ){
           dept = user.getOrganization();
       }

        //returnInfo.setReturnObj(DtoTcKey.USER_ID, userId);
        returnInfo.setReturnObj(DtoTcKey.DEPT, dept);
    }
}
