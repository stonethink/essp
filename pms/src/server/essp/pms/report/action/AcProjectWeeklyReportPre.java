package server.essp.pms.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import java.util.List;
import itf.account.IAccountUtil;
import server.essp.pms.report.viewbean.VbProjectWeeklyReportForm;
import java.util.Date;
import com.wits.util.comDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.ArrayList;
import server.framework.taglib.util.SelectOptionImpl;
import server.essp.pms.report.form.AfWeeklyReport;
import com.wits.util.StringUtil;

public class AcProjectWeeklyReportPre extends AbstractESSPAction {

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
        log.info("weely report user:" + user.getUserLoginId());
        VbProjectWeeklyReportForm webVo = getUIViewBean(user);
        String submitFlag = request.getParameter("submit");
        if(submitFlag != null){
            setParamInSession();
            webVo.setSubmitFlag(submitFlag);
        }

        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("report");
    }

    private VbProjectWeeklyReportForm getUIViewBean(DtoUser user) throws
        BusinessException {
        LgAccountUtilImpl logic = new LgAccountUtilImpl();
        List accounts = logic.listOptAccountsDefaultStyle(user.getUserLoginId());
        if(accounts == null){
            accounts = new ArrayList();
        }
        accounts.add(0,new SelectOptionImpl("-- Please Select --",""));

        Calendar today = new GregorianCalendar();
        WorkCalendar workCal = new WorkCalendar();
        Calendar reportBegin = workCal.getBEWeekDay(today)[0];
        Calendar reportEnd = workCal.getNextBEWeekDay(today,1)[1];
        VbProjectWeeklyReportForm webVo = new VbProjectWeeklyReportForm();

        webVo.setAccounts(accounts);
        webVo.setReportedBy(user.getUserLoginId());
        webVo.setReportDate(comDate.dateToString(today.getTime()));
        webVo.setReportBegin(comDate.dateToString(reportBegin.getTime()));
        webVo.setReportEnd(comDate.dateToString(reportEnd.getTime()));
        return webVo;
    }

    private void setParamInSession(){
        AfWeeklyReport form = (AfWeeklyReport) this.getForm();
        String acntRid = StringUtil.nvl(form.getAcntRid());
        String reportDate = StringUtil.nvl(form.getReportDate());
        String reportBegin = StringUtil.nvl(form.getReportBegin());
        String reportEnd = StringUtil.nvl(form.getReportEnd());
        String reportedBy = StringUtil.nvl(form.getReportedBy());
        String acntId = StringUtil.nvl(form.getAcntId());
        String acntName = StringUtil.nvl(form.getAcntName());
        if(!"".equals(acntRid) && !"".equals(acntId) && !"".equals(acntName) &&
           !"".equals(reportedBy) && !"".equals(reportDate) &&
           !"".equals(reportBegin) &&  !"".equals(reportEnd)){
            this.getRequest().getSession().setAttribute("ReportForm",form);
        }
    }
}
