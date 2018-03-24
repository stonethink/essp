package server.essp.attendance.overtime.action;

import java.util.*;

import javax.servlet.http.*;

import c2s.dto.*;
import c2s.essp.common.calendar.*;
import com.wits.util.*;
import itf.orgnization.*;
import server.essp.attendance.overtime.viewbean.*;
import server.essp.framework.action.*;
import server.framework.common.*;

public class AcOverTimeSearchPre extends AbstractESSPAction{
    public String WITS_WH = "UNIT00000195";
    public String HR_FI_GS = "UNIT00000249" +",UNIT00000259"; //HR+FI+CS+GS

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
        //获取Site 最高部门
        Config viewAllDeptConf = new Config("viewAllDeptConfig");
        String wits_site_code = viewAllDeptConf.getValue("site_Code");
        if(wits_site_code!=null && wits_site_code.length() > 0){
            this.WITS_WH=wits_site_code;
        }

        IOrgnizationUtil orgUtil = OrgnizationFactory.create();
        String orgIds = orgUtil.getSubOrgs(WITS_WH);

        orgIds = orgIds.replaceAll("'", "");
        if (orgIds.startsWith(",")) {
            orgIds = orgIds.substring(1);
        }
        request.setAttribute("orgIds", orgIds);
        Calendar ca = Calendar.getInstance();
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        Calendar[] month = wc.getBEMonthDay(ca);
        VbOverTimeSearch webVo = new VbOverTimeSearch();
        webVo.setBeginDate(month[0].getTime());
        webVo.setEndDate(month[1].getTime());
        request.setAttribute("webVo", webVo);
    }
}
