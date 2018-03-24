package server.essp.tc.outwork.action;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.common.user.DtoUser;
import itf.orgnization.OrgnizationFactory;
import itf.orgnization.IOrgnizationUtil;
import com.wits.util.comDate;
import server.essp.tc.outwork.form.AfSearchForm;
import java.util.Date;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.Calendar;
import server.essp.tc.outwork.logic.LgOutWork;
import java.util.List;
import java.util.ArrayList;
import com.wits.util.Config;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;

public class AcOutWorkerSearch extends AbstractESSPAction {
    //默认是武汉site的部门，如果配置文件中有相应的配置的话，则会根据配置文件中的内容作相应的调整。
    //应大连site上线需求而改变
    //modify:Robin 2006.09.14

    public String WITS_WH = "UNIT00000195";
    public String HR_FI_GS = "UNIT00000249" +",UNIT00000259"; //HR+FI+CS+GS
    public AcOutWorkerSearch() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        Config viewAllDeptConf=new Config("viewAllDeptConfig");
        String wits_site_code=viewAllDeptConf.getValue("group_Code");
        String common_dept_code=viewAllDeptConf.getValue("common_dept_code");
        if(wits_site_code!=null&&wits_site_code.length()>0){
            this.WITS_WH=wits_site_code;
        }
        if(common_dept_code!=null&&common_dept_code.length()>0){
            this.HR_FI_GS=common_dept_code;
        }

        //根据ActionForm中的条件来搜索
        DtoUser user = (DtoUser)this.getSession().getAttribute(DtoUser.SESSION_USER);
        IOrgnizationUtil orgUtil = OrgnizationFactory.create();
        //HR，财务，行政则可以看到全公司的情况
        String orgIds = orgUtil.getSubOrgs(WITS_WH);

        IHrUtil hrUtil=HrFactory.create();
        DtoUser userDetail=hrUtil.findResouce(user.getUserLoginId());
        if (HR_FI_GS.indexOf(userDetail.getOrgId()) >= 0) {
            orgIds = orgUtil.getSubOrgs(WITS_WH);
            request.setAttribute("bigRang", "true");
            this.getSession().setAttribute("ATTENDENCE_MANAGER",wits_site_code);
        } else {
            orgIds = orgUtil.getSubOrgs(user.getOrgId());
            request.setAttribute("bigRang", "false");
        }
        orgIds = orgIds.replaceAll("'", "");
        if (orgIds.startsWith(",")) {
            orgIds = orgIds.substring(1);
        }
        this.getSession().setAttribute("orgIds", orgIds);
        //首次进入时自动帮忙填时间
        Calendar calendar = Calendar.getInstance();
        Calendar period[] = WorkCalendar.getBEWeekDay(calendar);
        Date begin = new Date(period[0].getTimeInMillis());
        Date end = new Date(period[1].getTimeInMillis());
        AfSearchForm form = null;

        //如果不带参数，就去Session中取查询条件
        if (request.getParameter("isDel") != null && !request.getParameter("isDel").equals("")
            && request.getParameter("isDel").equals("Yes")) {
            //是为删除而进入的
            form = (AfSearchForm) request.getSession().getAttribute("searchCondition");
        } else if (request.getParameter("isDel") != null && !request.getParameter("isDel").equals("")
                   && request.getParameter("isDel").equals("No")) {
            form = (AfSearchForm)this.getForm();
        }

        //首次进入在Session中取不到
        if (form == null) {
            form = new AfSearchForm();
        }

        //如果不是HR，财务，行政人员，自动把ORGID限定为本部门
        if (HR_FI_GS.indexOf(user.getOrgId()) < 0) {
            form.setDepartRid(user.getOrgId());
        }

        if (form.getBeginDate() == null || form.getBeginDate().equals("")) {
            form.setBeginDate(comDate.dateToString(begin, "yyyyMMdd"));
        } else {
            form.setBeginDate(comDate.dateToString(comDate.toDate(form.getBeginDate()), "yyyyMMdd"));
        }
        if (form.getEndDate() == null || form.getEndDate().equals("")) {
            form.setEndDate(comDate.dateToString(end, "yyyyMMdd"));
        } else {
            form.setEndDate(comDate.dateToString(comDate.toDate(form.getEndDate()), "yyyyMMdd"));
        }

//        request.setAttribute("outWorkerSearchForm", form);

        if (orgUtil.getOrgManagerLoginId(user.getOrgId()).equals(user.getUserLoginId())) {
            request.setAttribute("ISORGMANAGER", "true");
        }else{
            request.setAttribute("ISORGMANAGER", "false");
        }

        LgOutWork logic = new LgOutWork();
        List result = new ArrayList();
        if (form.getAcntRid() != null && form.getAcntRid().equals("-1")) {
            form.setAcntRid("");
        }
        if (form.getDepartRid() != null && form.getDepartRid().equals("-1")) {
            form.setDepartRid("");
        }
        String departRid = (String)request.getParameter("departRid");
        if(departRid != null && "".equals(departRid) == false) {
            form.setDepartRid(departRid);
        }
        result = logic.listAll(form);

        request.getSession().setAttribute("searchCondition", form);
        request.setAttribute("webVo", result);

    }

}
