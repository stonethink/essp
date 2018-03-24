package server.essp.common.calendar.action;

import c2s.dto.*;

import com.essp.calendar.*;
import com.wits.util.*;

import server.essp.common.calendar.logic.*;
import server.framework.action.*;

import java.util.*;

import javax.servlet.http.*;

public class CalendarAction implements IBusinessAction {
    public CalendarAction() {
    }

    /**
     * execute
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data VDView_Data*/
    public void execute(HttpServletRequest  request,
                        HttpServletResponse response,
                        TransactionData     data) {
        String sAction = data.getInputInfo().getFunId();

        if (sAction.equals("get")) {
            get(data);
        } else if (sAction.equals("save")) {
            save(data);

            //        } else if (sAction.equals("setrange"))  {
            //            setRange(data);
        } else if (sAction.equals("setseq")) {
            setSeq(data);
        } else if (sAction.equals("setweekrange")) {
            setWeekRange(data);
        } else if (sAction.equals("setweeksn")) {
            setWeekSN(data);
        }
    }

    //��ȡ��ݣ�������ݲ��ҹ���������
    private void get(TransactionData data) {
        CalendarLogic cp = new CalendarLogic();
        cp.get(data);
    }

    //���湤������������
    private void save(TransactionData data) {
        CalendarLogic cp = new CalendarLogic();
        cp.save(data);

        //data.getData_Node().setFieldValue( "MonthID", new Integer(wk.getRange())) ;
    }

    //�������ڼ�����������ȡ������/�ǹ�����
    private void setSeq(TransactionData data) {
        String sStart = data.getInputInfo().getInputObj("Work_Start").toString();
        String sEnd  = data.getInputInfo().getInputObj("Work_Seq").toString();
        String sFlag = data.getInputInfo().getInputObj("Work_Flag").toString();

        if (!(sFlag.equals(WorkDay.HOLIDAY) || sFlag.equals(WorkDay.WORKDAY))) {
            sFlag = WorkDay.ALLWORKDAY;
        }

        Calendar     cStart  = StringUtil.String2Calendar(sStart);
        int          iEnd    = Integer.parseInt(sEnd);
        CalendarLogic cp      = new CalendarLogic();
        String[]     retStrs = null;

        try {
            retStrs = cp.getWorkDays(cStart, iEnd, sFlag);
            data.getReturnInfo().setReturnObj("Work_days", retStrs);
        } catch (Exception ex) {
            data.getReturnInfo().setError(ex);
        }
    }

    //
    //�����������飬ʱ�䷶Χ������/�ǹ����գ�
    private void setWeekRange(TransactionData data) {
        String sStart = data.getInputInfo().getInputObj("Work_Start").toString();
        String sEnd  = data.getInputInfo().getInputObj("Work_End").toString();
        String sFlag = data.getInputInfo().getInputObj("Work_Flag").toString();
        int[]  weeks = (int[]) data.getInputInfo().getInputObj("Work_Weeks");

        if (!(sFlag.equals(WorkDay.HOLIDAY) || sFlag.equals(WorkDay.WORKDAY))) {
            sFlag = WorkDay.ALLWORKDAY;
        }

        Calendar     cStart  = StringUtil.String2Calendar(sStart);
        Calendar     cEnd    = StringUtil.String2Calendar(sEnd);
        CalendarLogic cp      = new CalendarLogic();
        String[]     retStrs = null;

        try {
            retStrs = cp.getWorkDays(cStart, cEnd, sFlag, weeks);
            data.getReturnInfo().setReturnObj("Work_days", retStrs);
        } catch (Exception ex) {
            data.getReturnInfo().setError(ex);
        }
    }

    //�����������飬ѭ����������ȡ����
    private void setWeekSN(TransactionData data) {
        String       sStart = data.getInputInfo().getInputObj("Work_Start")
                                  .toString();
        String       sEnd  = data.getInputInfo().getInputObj("Work_Seq")
                                 .toString();
        int[]        weeks = (int[]) data.getInputInfo().getInputObj("Work_Weeks");

        Calendar     cStart  = StringUtil.String2Calendar(sStart);
        int          iEnd    = Integer.parseInt(sEnd);
        CalendarLogic cp      = new CalendarLogic();
        String[]     retStrs = null;

        try {
            retStrs = cp.getWorkDays(cStart, iEnd, weeks);
            data.getReturnInfo().setReturnObj("Work_days", retStrs);
        } catch (Exception ex) {
            data.getReturnInfo().setError(ex);
        }
    }
}
