package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import java.util.Date;
import server.essp.tc.mail.logic.LgGetAllPersonalStaticsDate;
import java.util.Calendar;
import server.essp.common.mail.SendHastenMail;
import java.util.List;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;

public class AcWeeklyReportMail extends AbstractESSPAction {
    public static final String mailTitle = "Attendance Mail";
    public static final String mailTemplate = "mail/template/tc/personalAllInfoMailTemplate.htm";
    /**
     * executeAct
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, TransactionData data) throws
            BusinessException {
        Date beginPeriod = (Date) data.getInputInfo().getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) data.getInputInfo().getInputObj(DtoTcKey.END_PERIOD);
        Long acntRid = (Long) data.getInputInfo().getInputObj(DtoTcKey.ACNT_RID);
        String periodType = (String) data.getInputInfo().getInputObj(DtoTcKey.PERIOD_TYPE);
        List tcList = (List) data.getInputInfo().getInputObj(DtoTcKey.WEEKLY_REPORT_LIST);

//        for(int i = 0;i < tcList.size() ;i ++){
//            DtoWeeklyReportSumByHr dto = (DtoWeeklyReportSumByHr) tcList.get(i);
//            System.out.println("---------mail attendance:"+dto.getUserId()+"");
//        }
        SendHastenMail shm = new SendHastenMail();
        LgGetAllPersonalStaticsDate gapsd = new LgGetAllPersonalStaticsDate();

        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(beginPeriod);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endPeriod);

        shm.sendHastenMail(mailTemplate, mailTitle,gapsd.getWeeklyReportData(acntRid.toString(), beginCal, endCal, periodType,tcList));
//        AttendanceEmailSender sender = new AttendanceEmailSender();
//        sender.send(acntRid,beginPeriod,endPeriod,periodType);
    }
}
