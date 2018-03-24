package server.essp.attendance.workflow.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.workflow.wfengine.WfEngine;
import db.workflow.WkInstance;
import java.util.Set;
import c2s.workflow.DtoRequestProcess;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import db.workflow.WkActivity;
import java.util.Comparator;
import java.util.Date;
import java.util.Collections;
import server.workflow.wfengine.WfConstant;

public class AcViewProcess extends AbstractESSPAction {
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
        String wkRid = request.getParameter("wkRid");
        try {
            WkInstance wk = (WkInstance) this.getDbAccessor()
                            .load(WkInstance.class, new Long(wkRid));
            if(wk.getStatus().equals(WfConstant.EVENT_FINISH))
                wk.setIsConfirmed(Boolean.TRUE);
            DtoRequestProcess webVo = new DtoRequestProcess();
            DtoUtil.copyBeanToBean(webVo,wk);
            List allActi = new ArrayList();
            Set activities = wk.getWkActivities();
            Iterator it = activities.iterator();
            while(it.hasNext()){
               WkActivity act =  (WkActivity)it.next();
               allActi.add(act);
            }
            Collections.sort(allActi,new ActivitySorter());
            webVo.setCurrActivityList(allActi);
            request.setAttribute("webVo",webVo);
            data.getReturnInfo().setForwardID("success");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("WF","error load work flow instance!",ex);
        }
    }
    static class ActivitySorter implements Comparator{
        public boolean equals(Object obj) {
            return false;
        }

        public int compare(Object o1, Object o2) {
            if((o1 instanceof WkActivity) &&  (o2 instanceof WkActivity)){
                WkActivity act1 = (WkActivity)o1;
                WkActivity act2 = (WkActivity)o2;
                Date start1 = act1.getStartDate();
                Date start2 = act2.getStartDate();
                if(start1 != null && start2 != null)
                    return (int)(start1.getTime() - start2.getTime());
            }
            return 0;
        }

    }
}
