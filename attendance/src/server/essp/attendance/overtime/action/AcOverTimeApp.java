package server.essp.attendance.overtime.action;

import java.util.*;

import javax.servlet.http.*;

import c2s.dto.*;
import c2s.essp.common.calendar.*;
import com.wits.util.*;
import db.essp.attendance.*;
import server.essp.attendance.overtime.form.*;
import server.essp.attendance.overtime.logic.*;
import server.essp.framework.action.*;
import server.framework.common.*;

public class AcOverTimeApp extends AbstractESSPAction {
    /**
     * �����Ӱ������¼
     * 1.���ӼӰ�������ܼ�¼
     * 2.����ÿ��ļ�¼
     * 3.�½��������Ӱ������
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
        //�����Ӱ��¼
        AfOverTimeApp form = (AfOverTimeApp) this.getForm();
        LgOverTime lg = new LgOverTime();
        TcOvertime overTime = lg.addOverTimeApp(form);

        String dateFromStr = form.getDateFrom();
        String dateToStr = form.getDateTo();
        Date dateFrom = comDate.toDate(dateFromStr);
        Date dateTo = comDate.toDate(dateToStr);
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(dateFrom);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dateTo);
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        List days = wc.getDays(calFrom,calEnd); //���е���
        if(days == null || days.size() <=0)
            return;

        String detailInfo = form.getDetailInfo();
        parseDetailParam(detailInfo);
        /*
         *�����Ӱ������ÿ��,ÿ���ʱ���Remark��Request�л��,
         *ʱ���������:yyyyMMdd + hours
         *Remark��������:yyyyMMdd + remark
         */
        for(int i = 0;i < days.size();i ++){
            Calendar perDay = (Calendar) days.get(i);
            String dayStr = comDate.dateToString(perDay.getTime(),"yyyyMMdd");
            String hours  = getHours(dayStr);
            String remark = getRemark(dayStr);
            TcOvertimeDetail detail = new TcOvertimeDetail();
            detail.setTcOvertime(overTime);
            if("".equals(hours))
                detail.setHours(new Double(0));
            else
                detail.setHours(new Double(hours));
            detail.setOvertimeDay(perDay.getTime());
            detail.setRemark(remark);
            lg.addOverTimeDetail(detail);
        }
        data.getReturnInfo().setForwardID("success");
    }

    private String getHours(String dayStr){
        String hours = (String) detail.get("hours" + dayStr);
        return StringUtil.nvl(hours);
    }
    private String getRemark(String dayStr){
        String remark = (String) detail.get("remark" + dayStr);
        return StringUtil.nvl(remark);
    }
    //��������detailInfo,�������������"="�����Bug
    private void parseDetailParam(String detailInfo){
        String[] parameters = detailInfo.split("&");
        for(int i = 0;i < parameters.length;i ++){
            int splitIndex = parameters[i].indexOf("=");
            String name = parameters[i].substring(0,splitIndex );
            String value = parameters[i].substring(splitIndex + 1);
            detail.put(name,value);
        }
    }
    private Map detail = new HashMap();
}
