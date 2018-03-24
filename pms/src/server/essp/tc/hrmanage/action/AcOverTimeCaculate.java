package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.attendance.overtime.logic.LgOverTime;
import server.essp.attendance.overtime.viewbean.VbOverTimeDetail;
import c2s.essp.common.code.DtoKey;
import c2s.essp.tc.overtime.DtoOverTime;
import java.util.List;
import java.util.ArrayList;
import server.essp.attendance.overtime.viewbean.VbOverTimePerDay;
import c2s.essp.tc.overtime.DtoOverTimeDetail;
import com.wits.util.comDate;
import c2s.dto.DtoUtil;

public class AcOverTimeCaculate extends AbstractESSPAction {
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
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
        DtoOverTime dto = (DtoOverTime) data.getInputInfo().getInputObj(DtoKey.DTO);
        String dateFrom = comDate.dateToString(dto.getActualDateFrom());
        String dateTo = comDate.dateToString(dto.getActualDateTo());
        String timeFrom = dto.getActualTimeFrom();
        String timeTo = dto.getActualTimeTo();
        Boolean isEachDay = dto.getActualIsEachDay();

        LgOverTime lg = new LgOverTime();
        VbOverTimeDetail vb = lg.caculateOverTime(dateFrom,dateTo,
                                                  timeFrom,timeTo,
                                                  isEachDay.booleanValue());
        dto.setActualTotalHours(new Double(vb.getTotalHours()));

        List oldDetailList = dto.getDetailList();//重新计算加班每天明细
        List newDetailList = new ArrayList();//重新计算后加班每天明细
        List perDays = vb.getPerDay();
        if(perDays != null && perDays.size() != 0)
            for(int i = 0;i < perDays.size() ;i ++){
                VbOverTimePerDay perDay = (VbOverTimePerDay) perDays.get(i);
                DtoOverTimeDetail newDetail = new DtoOverTimeDetail();
                boolean found = false;//判断新的当前明细在旧的记录中是否存在
                if(oldDetailList != null){
                    for (int j = 0; j < oldDetailList.size(); j++) {
                        DtoOverTimeDetail detail = (DtoOverTimeDetail) oldDetailList.get(j);
                        if (comDate.dateToString(perDay.getOvertimeDay())
                            .equals(comDate.dateToString(detail.getOvertimeDay()))) {
                            found = true;
                            DtoUtil.copyBeanToBean(newDetail, detail);
                            oldDetailList.remove(detail);
                            break;
                        }
                    }
                }
                newDetail.setHours(new Double(perDay.getHours()));
                if(!found){
                    newDetail.setOvertimeDay(perDay.getOvertimeDay());
                }
                newDetailList.add(newDetail);
            }
        dto.setDetailList(newDetailList);
        data.getReturnInfo().setReturnObj(DtoKey.DTO,dto);
    }
}
