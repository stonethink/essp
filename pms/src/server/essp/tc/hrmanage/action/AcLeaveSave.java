package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.common.code.DtoKey;
import c2s.essp.tc.leave.DtoLeave;
import db.essp.attendance.TcLeaveMain;
import c2s.dto.DtoUtil;

import java.util.Date;
import com.wits.util.comDate;
import server.essp.attendance.leave.logic.LgLeave;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import db.essp.attendance.TcLeaveDetail;
import c2s.essp.attendance.Constant;
import java.util.List;
import c2s.essp.tc.leave.DtoLeaveDetail;
import server.workflow.wfengine.WfProcess;
import server.workflow.wfengine.WfEngine;
import c2s.workflow.DtoWorkingProcess;
import db.essp.attendance.TcLeaveLog;

public class AcLeaveSave extends AbstractESSPAction {
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
        String decision = "";
        TcLeaveMain leave = null;
        if(dto.getRid() == null){
            leave = dto2DB(dto);
            save(leave);
            decision = "Added by HR";
        }else{
            leave = update(dto);
            decision = "Modified by HR";
        }
        LgLeave lg = new LgLeave();
        lg.addReviewLog(leave,decision,null);
    }
    private void save(TcLeaveMain leave){
        LgLeave lg = new LgLeave();
        try {
            leave.setDatetimeStart(leave.getActualDatetimeStart());
            leave.setDatetimeFinish(leave.getActualDatetimeFinish());
            leave.setTotalHours(leave.getActualTotalHours());

            this.getDbAccessor().save(leave);
            Set detailSet = leave.getTcLeaveDetails();
            for(Iterator it = detailSet.iterator();it.hasNext();){
                TcLeaveDetail detail = (TcLeaveDetail) it.next();
                this.getDbAccessor().save(detail);
            }
            //扣除用户可用时间
            lg.addUseLeaveHours(leave.getLoginId(),leave.getLeaveName(),leave.getActualTotalHours().doubleValue());
        } catch (Exception ex) {
            throw new BusinessException("HR_LEAVE_0004","error saving leave",ex);
        }
    }
    private TcLeaveMain update(DtoLeave dto){
        LgLeave lg = new LgLeave();
        try {
            TcLeaveMain primary = (TcLeaveMain) this.getDbAccessor().load(TcLeaveMain.class,dto.getRid());
            String dateFrom = comDate.dateToString(dto.getActualDateFrom(),Constant.DATE_FORMAT);
            String dateTo = comDate.dateToString(dto.getActualDateTo(),Constant.DATE_FORMAT);
            String timeFrom = dto.getActualTimeFrom();
            String timeTo = dto.getActualTimeTo();
            double actualHours = dto.getActualTotalHours().doubleValue();
            double oldActualHours = primary.getActualTotalHours().doubleValue();
            if (actualHours > oldActualHours) {
                lg.addUseLeaveHours(primary.getLoginId(), primary.getLeaveName(),
                                 actualHours - oldActualHours);
            } else if (actualHours < oldActualHours) {
                lg.returnUseLeaveHours(primary.getLoginId(), primary.getLeaveName(),
                                    oldActualHours - actualHours);
            }
            primary.setActualDatetimeFinish(Constant.parseDate(dateTo,timeTo));
            primary.setActualDatetimeStart(Constant.parseDate(dateFrom,timeFrom));
            primary.setActualTotalHours(new Double(actualHours));
            this.getDbAccessor().delete(primary.getTcLeaveDetails());
            List detailList = dto.getDetailList();
            if(detailList != null && detailList.size() != 0){
                for(int i = 0;i < detailList.size() ;i ++){
                    DtoLeaveDetail detailDto = (DtoLeaveDetail) detailList.get(i);
                    TcLeaveDetail detail = new TcLeaveDetail();
                    DtoUtil.copyBeanToBean(detail,detailDto);
                    detail.setTcLeave(primary);
                    this.getDbAccessor().save(detail);
                }
            }
            //未走完流程的Terminate流程
            if(Constant.STATUS_APPLYING.equals(primary.getStatus())
               || Constant.STATUS_REVIEWING.equals(primary.getStatus())){
                primary.setStatus(Constant.STATUS_FINISHED);
                Long wkRid = primary.getWkId();
                if(wkRid != null){
                    WfEngine engine = new WfEngine();
                    WfProcess process = engine.getProcessInstance(wkRid);
                    if (process != null) {
                        DtoWorkingProcess dtoWorkingProcess = process.getDtoWorkingProcess();
                        dtoWorkingProcess.setDescription("Teminated by HR!");
                        process.terminate();
                    }
                }
            }
            return primary;
        } catch (Exception ex) {
            throw new BusinessException("HR_LEAVE_0005","error updating leave",ex);
        }
    }
    private TcLeaveMain dto2DB(DtoLeave dto){
        TcLeaveMain leave  = new TcLeaveMain();
        DtoUtil.copyBeanToBean(leave,dto);
        Date datetimeStart = dto.getActualDateFrom();
        Date timeFrom = comDate.toDate(dto.getActualTimeFrom(),Constant.TIME_FORMAT);
        datetimeStart.setHours(timeFrom.getHours());
        datetimeStart.setMinutes(timeFrom.getMinutes());
        Date datatimeFinish = dto.getActualDateTo();
        Date timeTo = comDate.toDate(dto.getActualTimeTo(),Constant.TIME_FORMAT);
        datatimeFinish.setHours(timeTo.getHours());
        datatimeFinish.setMinutes(timeTo.getMinutes());

        leave.setActualDatetimeStart(datetimeStart);
        leave.setActualDatetimeFinish(datatimeFinish);
        leave.setStatus(Constant.STATUS_FINISHED);
        leave.setOrgId(dto.getOrgId());
        Set detailSet = new HashSet();

        List detailList = dto.getDetailList();
        if(detailList != null && detailList.size() != 0){
            for(int i  = 0; i < detailList.size() ;i ++){
                DtoLeaveDetail detailDto = (DtoLeaveDetail) detailList.get(i);
                TcLeaveDetail detail  = new TcLeaveDetail();
                DtoUtil.copyBeanToBean(detail,detailDto);
                detail.setTcLeave(leave);
                detailSet.add(detail);
            }
        }
        leave.setTcLeaveDetails(detailSet);
        return leave;
    }
}
