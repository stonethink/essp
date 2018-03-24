package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.attendance.overtime.logic.LgOverTime;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import db.essp.attendance.TcOvertime;
import c2s.essp.tc.overtime.DtoOverTime;
import c2s.dto.DtoUtil;
import java.util.Set;
import db.essp.attendance.TcOvertimeDetail;
import java.util.Iterator;
import c2s.essp.tc.overtime.DtoOverTimeDetail;
import itf.account.AccountFactory;
import com.wits.util.comDate;
import c2s.essp.attendance.Constant;
import java.util.Calendar;
import server.essp.tc.common.LgTcCommon;

public class AcOverTimeList extends AbstractESSPAction {
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

        LgOverTime lg = new LgOverTime();
        //列出该区间该人员所有的加班记录
        List list = lg.listPeriodAllOverTime(userId,beginPeriod,endPeriod);
        List overTimeList = new ArrayList();
        //查询结果转换为Dto
        if(list != null && list.size() != 0)
            for(int i = 0;i < list.size() ; i ++){
                TcOvertime overTime = (TcOvertime) list.get(i);
                DtoOverTime dto = new DtoOverTime();
                DtoUtil.copyBeanToBean(dto,overTime);
                String accountName = AccountFactory.create().getAccountByRID(overTime.getAcntRid()).getName();
                dto.setAccountName(accountName);
                Date start = overTime.getActualDatetimeStart();
                Date finish = overTime.getActualDatetimeFinish();
                dto.setActualDateFrom(start);
                dto.setActualDateTo(finish);
                dto.setActualTimeFrom(comDate.dateToString(start,Constant.TIME_FORMAT));
                dto.setActualTimeTo(comDate.dateToString(finish,Constant.TIME_FORMAT));
                Set detailSet = overTime.getTcOvertimeDetails();
                List detailList = new ArrayList(detailSet.size());
                for(Iterator it = detailSet.iterator();it.hasNext() ;){
                    TcOvertimeDetail detail = (TcOvertimeDetail) it.next();
                    DtoOverTimeDetail dtoDetail = new DtoOverTimeDetail();
                    DtoUtil.copyBeanToBean(dtoDetail,detail);
                    detailList.add(dtoDetail);
                }
                dto.setDetailList(detailList);
                overTimeList.add(dto);
            }
        data.getReturnInfo().setReturnObj("overTimeList",overTimeList);
    }
}
