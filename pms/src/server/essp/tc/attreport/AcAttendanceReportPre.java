package server.essp.tc.attreport;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.common.calendar.WorkCalendar;
import com.wits.util.comDate;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;

public class AcAttendanceReportPre extends AbstractESSPAction {
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
        VbAttendanceReport webVo = getUIViewBean();

        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("report");
    }

    private VbAttendanceReport getUIViewBean() {
        //获得"HR"项目列表
        IAccountUtil acntUtil = AccountFactory.create();
        List accountList = acntUtil.getHrAcntSelectOptions();
        Calendar today = Calendar.getInstance();
        WorkCalendar workCal = new WorkCalendar();
        Calendar[] period = workCal.getNextBEWeekDay(today,-1);
        VbAttendanceReport webVo = new VbAttendanceReport();
        webVo.setAccountList(accountList);
        webVo.setReportBegin(comDate.dateToString(period[0].getTime()));
        webVo.setReportEnd(comDate.dateToString(period[1].getTime()));
        return webVo;
    }
}
