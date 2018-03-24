package server.essp.attendance.overtime.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.attendance.overtime.form.AfOverTimeReport;
import java.util.ArrayList;
import itf.account.AccountFactory;
import java.util.List;
import itf.account.IAccountUtil;
import java.util.Calendar;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import com.wits.util.comDate;

public class AcOverTimeReportPre extends AbstractESSPAction{
    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData transactionData) throws
            BusinessException {
        AfOverTimeReport af = new AfOverTimeReport();
        //获得"HR"项目列表
        IAccountUtil acntUtil = AccountFactory.create();
        List accountList = acntUtil.getHrAcntSelectOptions();

        Calendar ca = Calendar.getInstance();
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        Calendar[] month = wc.getBEMonthDay(ca);


        af.setAcntList(accountList);
        af.setBeginDate(comDate.dateToString(month[0].getTime(), "yyyy/MM/dd"));
        af.setEndDate(comDate.dateToString(month[1].getTime(), "yyyy/MM/dd"));
        httpServletRequest.setAttribute("webVo", af);
        transactionData.getReturnInfo().setForwardID("success");

    }
}
