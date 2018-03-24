package server.essp.attendance.modify.logic;

import server.essp.framework.logic.*;
import server.essp.attendance.modify.viewbean.VbLeaveModify;
import server.essp.attendance.leave.logic.LgLeave;
import server.framework.common.BusinessException;
import db.essp.attendance.TcLeaveMain;
import c2s.essp.attendance.Constant;
import c2s.dto.DtoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import db.essp.attendance.TcLeaveDetail;
import server.essp.attendance.modify.viewbean.VbLeaveDetail;
import c2s.essp.common.account.IDtoAccount;
import itf.account.AccountFactory;
import itf.orgnization.OrgnizationFactory;
import java.util.Date;
import com.wits.util.comDate;
import java.util.Map;
import server.workflow.wfengine.WfEngine;
import server.workflow.wfengine.*;
import db.essp.attendance.TcLeaveLog;
import com.wits.util.Parameter;

public class LgLeaveModify extends AbstractESSPLogic {
    private LgLeave lgLeave = new LgLeave();
    /**
     * 根据Rid查找Leave,转成销假的ViewBean
     * @param rid Long
     * @return server.essp.attendance.modify.viewbean.VbLeaveModify
     */
    public VbLeaveModify getLeaveModifyByRid(Long rid){
        if(rid == null)
            throw new BusinessException("TC_LEAVE_MODIFY_00001","can not modify null leave!");
        TcLeaveMain leaveMain = lgLeave.getLeave(rid);
        VbLeaveModify result = leave2WebVo(leaveMain);
        return result;
    }
    /**
     * 根据work flow Rid查找Leave,转成销假的ViewBean
     * @param wkRid Long
     * @return VbLeaveModify
     */
    public VbLeaveModify getLeaveModifyByWkRid(Long wkRid){
        if(wkRid == null)
            throw new BusinessException("TC_LEAVE_MODIFY_00001","can not modify null leave!");
        TcLeaveMain leaveMain = lgLeave.getLeaveByWkRid(wkRid);
        VbLeaveModify result = leave2WebVo(leaveMain);
        return result;
    }
    /*
    * 销假申请动作:
    * 1.根据Request的数据更新每天请假明细的changeHours和Remark
    * 2.设置请假记录的状态为Applying
      3.增加一条Log
    * 4.启动销假流程,并将流程Rid保存到请假记录
    */
    public void appLeaveModify(TcLeaveMain leaveMain,Map leaveDetailMap){
        if(leaveMain == null)
            throw new BusinessException("TC_LEAVE_MODIFY_00003","can not modify null leave!");
        Set detailSet = leaveMain.getTcLeaveDetails();
        if(detailSet != null && detailSet.size() != 0){
            //更新明细
            for (Iterator it = detailSet.iterator(); it.hasNext(); ) {
                TcLeaveDetail detail = (TcLeaveDetail) it.next();
                TcLeaveDetail requestData = (TcLeaveDetail) leaveDetailMap.get(
                        detail.getRid());
                detail.setChangeHours(requestData.getChangeHours());
                detail.setRemark(requestData.getRemark());
            }
            //更新请假记录状态
            leaveMain.setStatus(Constant.STATUS_APPLYING);
            //增加Log
            lgLeave.addReviewLog(leaveMain,"Mofidy Leave",null);
            //启动流程
            WfEngine engine = new WfEngine();
            try {
                WfProcess prcoess = engine.createProcessInstance(Constant.WF_PACKAGE_ID,
                        Constant.WF_MODIFYLEAVE_PROCESS_ID,
                        leaveMain.getLoginId());
                System.out.println("\r\n\r\n@@@@@@@@@@@@@@@@@@@@@@@@@@@\r\n LoginId: "+leaveMain.getLoginId());
                String wfDesc = leaveMain.getWFDescription();
                prcoess.getDtoWorkingProcess().setDescription(wfDesc);
                prcoess.getDtoWorkingProcess().setCurrActivityDescription(wfDesc);
                prcoess.start();
                leaveMain.setWkId(prcoess.getDtoWorkingProcess().getRid());
                prcoess.finishActivity();
            } catch (WfException ex) {
                throw new BusinessException("TC_LEAVE_MODIFY_00004",
                                            "error create or start modify leave work flow!",ex);
            }
        }
    }
    /**
     * 审核销假动作
     * 1.审核意见为同意时:
     * 1.1 将LeaveDetail每天的ChangeHours写到Hours字段,作为最后确认的请假
     * 1.2 计算实际的总请假时间,更新请假记录的ActualHours,并更新请假记录状态为Finished
     * 1.3 将请假时间还回到该人员可用假
     * 2.审核意见为不同意时:
     * 2.1 更新请假记录状态为Finished
     * 3.审核意见为修改时:
     * 3.1根据Request传过来的数据,更新LeaveDetail每天的ChangeHours和Remark
     * 3.2同1.2
     * 4.增加一条Log
     * 5.结束流程
     * @param leaveMain TcLeaveMain
     * @param decision String
     * @param leaveDetailMap Map
     */
    public void reviewLeaveModify(TcLeaveMain leaveMain,
                                  String decision,Map leaveDetailMap,Long curActivityRid){
        if(leaveMain == null)
            throw new BusinessException("TC_LEAVE_MODIFY_00005","can not review null leave!");
        //审核同意
        if(Constant.DECISION_AGREE.equals(decision)){
            Set detailSet = leaveMain.getTcLeaveDetails();
            if(detailSet != null && detailSet.size() > 0){
                double totalHours = 0D;
                for(Iterator it = detailSet.iterator() ;it.hasNext(); ){
                    TcLeaveDetail detail = (TcLeaveDetail) it.next();
                    Double changeHours = detail.getChangeHours();
                    detail.setHours(changeHours);
                    totalHours += (changeHours == null ? 0D : changeHours.doubleValue());
                }
                Double oldActualTotalHours = leaveMain.getActualTotalHours();
                leaveMain.setActualTotalHours(new Double(totalHours));
                double returnHours = oldActualTotalHours == null ? 0D : oldActualTotalHours.doubleValue() - totalHours;
                lgLeave.returnUseLeaveHours(leaveMain.getLoginId(),
                                            leaveMain.getLeaveName(),
                                            returnHours);
            }
        }//审核不同意
        else if(Constant.DECISION_DISAGREE.equals(decision)){

        }//审核时修改
        else if(Constant.DECISION_MODIFY.equals(decision)){
            Set detailSet = leaveMain.getTcLeaveDetails();
            if(detailSet != null && detailSet.size() > 0){
                double totalHours = 0D;
                for(Iterator it = detailSet.iterator() ;it.hasNext(); ){
                    TcLeaveDetail detail = (TcLeaveDetail) it.next();
                    Long rid = detail.getRid();
                    TcLeaveDetail requestData = (TcLeaveDetail) leaveDetailMap.get(rid);
                    Double changeHours = requestData.getChangeHours();
                    String remark = requestData.getRemark();
                    detail.setHours(changeHours);
                    detail.setRemark(remark);
                    totalHours += (changeHours == null ? 0D : changeHours.doubleValue());
                }
                leaveMain.setActualTotalHours(new Double(totalHours));
            }
        }else{
            throw new BusinessException("TC_LEAVE_MODIFY_00006","illegal review decision ["+decision+"]!");
        }
        leaveMain.setStatus(Constant.STATUS_FINISHED);

        TcLeaveLog log = lgLeave.addReviewLog(leaveMain,decision + " Modifying",null);

        Long wkRid = leaveMain.getWkId();
        WfEngine engine = new WfEngine();
        try {
            WfProcess process = engine.getProcessInstance(wkRid,curActivityRid);
            process.getDtoWorkingProcess().setCurrActivityDescription(log.getDeal());
            Parameter param = new Parameter();
            boolean agree = Constant.DECISION_AGREE.equals(decision) ||
                            Constant.DECISION_MODIFY.equals(decision);
            param.put("agree", new Boolean(agree));
            process.setParameter(param);
            process.finishActivity();
        } catch (WfException ex) {
            throw new BusinessException("TC_LEAVE_MODIFY_00007","error finish modify leave workflow!",ex);
        }
    }
    private VbLeaveModify leave2WebVo(TcLeaveMain leaveMain)  {
        VbLeaveModify result = new VbLeaveModify();
        DtoUtil.copyBeanToBean(result,leaveMain);
        Date start = leaveMain.getDatetimeStart();
        Date finish = leaveMain.getDatetimeFinish();
        result.setActualDateFrom(comDate.dateToString(start));
        result.setActualDateTo(comDate.dateToString(finish));
        result.setActualTimeFrom(comDate.dateToString(start,comDate.pattenTimeHM));
        result.setActualTimeTo(comDate.dateToString(finish,comDate.pattenTimeHM));

        Long acntRid = result.getAcntRid();
        if(acntRid != null){
            IDtoAccount account = AccountFactory.create().getAccountByRID(
                    acntRid);
            result.setAccountName(account.getId() + " -- " + account.getName());
        }
        String orgId = result.getOrgId();
        if(orgId != null){
            String orgnization = OrgnizationFactory.create().getOrgName(orgId);
            result.setOrganization(orgnization);
        }
        List detailList = new ArrayList();
        Set detailSet = leaveMain.getTcLeaveDetails();
        if(detailSet != null && detailSet.size() > 0)
            for(Iterator it = detailSet.iterator();it.hasNext();){
                TcLeaveDetail detail = (TcLeaveDetail) it.next();
                VbLeaveDetail vb = new VbLeaveDetail();
                DtoUtil.copyBeanToBean(vb,detail);
                detailList.add(vb);
            }
        result.setDetailList(detailList);
        return result;
    }
}
