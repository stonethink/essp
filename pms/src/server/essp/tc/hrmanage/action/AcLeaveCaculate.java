package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.common.code.DtoKey;
import com.wits.util.comDate;
import server.essp.attendance.leave.logic.LgLeave;
import java.util.Map;
import java.util.Iterator;
import c2s.essp.tc.leave.DtoLeave;
import java.util.List;
import java.util.ArrayList;
import c2s.essp.tc.leave.DtoLeaveDetail;
import java.util.Calendar;

public class AcLeaveCaculate extends AbstractESSPAction {
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
        DtoLeave dto = (DtoLeave) data.getInputInfo().getInputObj(DtoKey.DTO);
        String dateFrom = comDate.dateToString(dto.getActualDateFrom());
        String dateTo = comDate.dateToString(dto.getActualDateTo());
        String timeFrom = dto.getActualTimeFrom();
        String timeTo = dto.getActualTimeTo();

        LgLeave lg = new LgLeave();
        Map dayHoursMap = lg.caculateWorkHours(dateFrom,dateTo,timeFrom,timeTo);
        double totalHours = 0.0D;
        List oldDetailList = dto.getDetailList();
        List newDetailList = new ArrayList();
        if(dayHoursMap != null && dayHoursMap.size() != 0){
            for(Iterator it = dayHoursMap.keySet().iterator();it.hasNext();){
                Calendar day = (Calendar) it.next();
                Float f = (Float) dayHoursMap.get(day);
                totalHours += (f == null ? 0D : f.floatValue());
                DtoLeaveDetail newDetail = new DtoLeaveDetail();
                newDetail.setLeaveDay(day.getTime());
                newDetail.setHours(new Double(f.floatValue()));
                //判断新的当前明细在旧的记录中是否存在,目的是为了保留Remark值
                if(oldDetailList != null){
                    for (int j = 0; j < oldDetailList.size(); j++) {
                        DtoLeaveDetail detail = (DtoLeaveDetail) oldDetailList.get(j);
                        if (comDate.dateToString(day.getTime())
                            .equals(comDate.dateToString(detail.getLeaveDay()))) {
                            newDetail.setRemark(detail.getRemark());
                            oldDetailList.remove(detail);
                            break;
                        }
                    }
                }
                newDetailList.add(newDetail);
            }
        }
        dto.setActualTotalHours(new Double(totalHours));
        dto.setDetailList(newDetailList);
        data.getReturnInfo().setReturnObj(DtoKey.DTO,dto);
    }
}
