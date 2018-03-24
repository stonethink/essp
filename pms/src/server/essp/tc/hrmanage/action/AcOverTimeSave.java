package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.common.code.DtoKey;
import c2s.essp.tc.overtime.DtoOverTime;
import db.essp.attendance.TcOvertime;
import c2s.dto.DtoUtil;
import com.wits.util.comDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import c2s.essp.tc.overtime.DtoOverTimeDetail;
import db.essp.attendance.TcOvertimeDetail;
import java.util.Iterator;
import c2s.essp.attendance.Constant;
import db.essp.attendance.TcOvertimeLog;
import server.essp.attendance.overtime.logic.LgOverTime;
import server.workflow.wfengine.WfEngine;
import server.workflow.wfengine.WfProcess;
import c2s.workflow.DtoWorkingProcess;

public class AcOverTimeSave extends AbstractESSPAction {
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
        TcOvertime overTime = dto2DB(dto);
        TcOvertimeLog log = new TcOvertimeLog();
        if(dto.getRid() == null){
            save(overTime);
            log.setDecision("Added by HR");
        }else{
            update(overTime);
            log.setDecision("Modified by HR");
        }
        //增加一条Log
        log.setLoginId(this.getUser().getUserLoginId());
        log.setLogDate(new Date());
        log.setDatetimeStart(overTime.getActualDatetimeStart());
        log.setDatetimeFinish(overTime.getActualDatetimeFinish());
        log.setIsEachDay(overTime.getIsEachDay());
        log.setTotalHours(overTime.getActualTotalHours());
        log.setTcOvertime(overTime);
        LgOverTime lg = new LgOverTime();
        lg.addOverTimeReviewLog(log);
    }
    private TcOvertime dto2DB(DtoOverTime dto){
        TcOvertime overTime = new TcOvertime();
        DtoUtil.copyBeanToBean(overTime,dto);
        Date datetimeStart = dto.getActualDateFrom();
        Date timeFrom = comDate.toDate(dto.getActualTimeFrom(),Constant.TIME_FORMAT);
        datetimeStart.setHours(timeFrom.getHours());
        datetimeStart.setMinutes(timeFrom.getMinutes());
        Date datatimeFinish = dto.getActualDateTo();
        Date timeTo = comDate.toDate(dto.getActualTimeTo(),Constant.TIME_FORMAT);
        datatimeFinish.setHours(timeTo.getHours());
        datatimeFinish.setMinutes(timeTo.getMinutes());

        overTime.setActualDatetimeStart(datetimeStart);
        overTime.setActualDatetimeFinish(datatimeFinish);

        overTime.setStatus(Constant.STATUS_FINISHED);
        List detailList = dto.getDetailList();
        Set detailSet = new HashSet(detailList.size());
        if(detailList != null && detailList.size() != 0){
            for(int i = 0;i  < detailList.size() ;i ++){
                DtoOverTimeDetail detailDto = (DtoOverTimeDetail) detailList.get(i);
                TcOvertimeDetail detail  = new TcOvertimeDetail();
                DtoUtil.copyBeanToBean(detail,detailDto);
                detail.setTcOvertime(overTime);
                detailSet.add(detail);
            }
        }
        overTime.setTcOvertimeDetails(detailSet);
        return overTime;
    }
    //新增加班记录,设置加班记录状态为Finished
    private void save(TcOvertime overTime){
        try {
            overTime.setDatetimeStart(overTime.getActualDatetimeStart());
            overTime.setDatetimeFinish(overTime.getActualDatetimeFinish());
            overTime.setIsEachDay(overTime.getActualIsEachDay());
            overTime.setTotalHours(overTime.getActualTotalHours());

            this.getDbAccessor().save(overTime);
            Set detailSet = overTime.getTcOvertimeDetails();
            if(detailSet != null)
                for(Iterator it = detailSet.iterator() ;it.hasNext();){
                    TcOvertimeDetail detail = (TcOvertimeDetail) it.next();
                    this.getDbAccessor().save(detail);
                }
        } catch (Exception ex) {
            throw new BusinessException("HR_OVERTIME_0001","error add overtime!",ex);
        }
    }
    //更新加班记录,若该条加班记录对应的流程还没走完则Terminate该流程,且设置加班记录的状态为Finished
    private void update(TcOvertime overTime){
        try {
            TcOvertime primary = (TcOvertime)this.getDbAccessor().load(TcOvertime.class, overTime.getRid());
            primary.setAcntRid(overTime.getAcntRid());
            primary.setActualDatetimeStart(overTime.getActualDatetimeStart());
            primary.setActualDatetimeFinish(overTime.getActualDatetimeFinish());
            primary.setActualIsEachDay(overTime.getActualIsEachDay());
            primary.setActualTotalHours(overTime.getActualTotalHours());
            primary.setCause(overTime.getCause());

            Set detailSet = overTime.getTcOvertimeDetails();
            Set primarayDetailSet = primary.getTcOvertimeDetails();
            this.getDbAccessor().delete(primarayDetailSet);
            for(Iterator it = detailSet.iterator();it.hasNext();){
                TcOvertimeDetail detail = (TcOvertimeDetail) it.next();
                this.getDbAccessor().save(detail);
            }
            //未走完流程的Terminate流程
            if(Constant.STATUS_APPLYING.equals(primary.getStatus())
               || Constant.STATUS_REVIEWING.equals(primary.getStatus())){
                primary.setStatus(Constant.STATUS_FINISHED);
                Long wkRid = primary.getWkId();
                if(wkRid == null)
                    return;
                WfEngine engine = new WfEngine();
                WfProcess process = engine.getProcessInstance(wkRid);
                if(process != null){
                    DtoWorkingProcess dto = process.getDtoWorkingProcess();
                    dto.setDescription("Teminated by HR!");
                    process.terminate();
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("HR_OVERTIME_0002","error update overtime!",ex);
        }
    }

}
