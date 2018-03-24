package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.Date;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.attendance.leave.logic.LgLeave;
import java.util.List;
import java.util.ArrayList;
import db.essp.attendance.TcLeaveMain;
import c2s.essp.tc.leave.DtoLeave;
import c2s.dto.DtoUtil;
import itf.orgnization.OrgnizationFactory;
import com.wits.util.comDate;
import c2s.essp.attendance.Constant;
import java.util.Calendar;
import java.util.Set;
import db.essp.attendance.TcLeaveDetail;
import java.util.Iterator;
import c2s.essp.tc.leave.DtoLeaveDetail;
import server.essp.tc.common.LgTcCommon;

public class AcLeaveList extends AbstractESSPAction {
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
        String userId = (String)data.getInputInfo().getInputObj(DtoTcKey.USER_ID);
        Date beginPeriod = (Date)data.getInputInfo().getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date)data.getInputInfo().getInputObj(DtoTcKey.END_PERIOD);

        //将开始时间的时分秒都设为0
        beginPeriod = LgTcCommon.resetBeginDate(beginPeriod);
        //将结束时间的设置为23:59:59 999
        endPeriod = LgTcCommon.resetEndDate(endPeriod);

        LgLeave lg = new LgLeave();
        List list = lg.listPeriodAllLeave(userId,beginPeriod,endPeriod);
        //查询结果转换为Dto
        List leaveList = new ArrayList();
        if(list != null && list.size() != 0){
            for(int i = 0;i < list.size() ;i ++){
                TcLeaveMain leave = (TcLeaveMain) list.get(i);
                DtoLeave dto = new DtoLeave();
                DtoUtil.copyBeanToBean(dto,leave);
                dto.setActualTotalHoursBak(leave.getActualTotalHours());
                String orgName = OrgnizationFactory.create().getOrgName(leave.getOrgId());
                dto.setOrgName(orgName);
                Date start = leave.getActualDatetimeStart();
                Date finish = leave.getActualDatetimeFinish();
                dto.setActualDateFrom(start);
                dto.setActualDateTo(finish);
                dto.setActualTimeFrom(comDate.dateToString(start,Constant.TIME_FORMAT));
                dto.setActualTimeTo(comDate.dateToString(finish,Constant.TIME_FORMAT));
                List detailList = new ArrayList();
                Set detailSet = leave.getTcLeaveDetails();
                if(detailSet != null && detailSet.size() != 0){
                    for(Iterator it = detailSet.iterator();it.hasNext();){
                        TcLeaveDetail detail = (TcLeaveDetail) it.next();
                        DtoLeaveDetail detailDto = new DtoLeaveDetail();
                        DtoUtil.copyBeanToBean(detailDto,detail);
                        detailList.add(detailDto);
                    }
                }
                dto.setDetailList(detailList);
                leaveList.add(dto);
            }
        }
        data.getReturnInfo().setReturnObj("leaveList",leaveList);
    }
}
