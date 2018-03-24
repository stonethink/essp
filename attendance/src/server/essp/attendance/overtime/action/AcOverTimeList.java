package server.essp.attendance.overtime.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import c2s.essp.common.user.DtoUser;
import server.essp.attendance.overtime.logic.LgOverTime;
import java.util.List;
import server.essp.attendance.overtime.viewbean.VbOverTimeList;

public class AcOverTimeList extends AbstractESSPAction {
    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        DtoUser user = this.getUser();
        if(user == null)
            throw new BusinessException("unknown","The user does not login in!");
        String yearStr = request.getParameter("year");
        VbOverTimeList webVo = new VbOverTimeList();
        int selectedYear = 0;
        if(yearStr != null){
            selectedYear = Integer.parseInt(yearStr);
            webVo.setSelectedYear(selectedYear);
        }
        String loginId = user.getUserLoginId();

        LgOverTime lg = new LgOverTime();
        List overTimeList = lg.listOverTime(loginId,webVo.getSelectedYear());
        System.out.println("list overtime record of :" + overTimeList.size());
        webVo.setOverTimeList(overTimeList);
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
    public static int LIST_YEAR_NUM = 10;
}
