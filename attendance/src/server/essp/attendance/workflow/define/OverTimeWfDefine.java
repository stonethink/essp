package server.essp.attendance.workflow.define;

import server.workflow.wfdefine.*;
import itf.orgnization.OrgnizationFactory;
import c2s.workflow.IUser;
import c2s.essp.attendance.WfUser;
import com.wits.util.Parameter;
import c2s.workflow.DtoWorkingProcess;
import c2s.essp.common.user.DtoUser;
import itf.hr.HrFactory;
import server.essp.attendance.overtime.logic.LgOverTime;
import server.essp.attendance.overtime.viewbean.VbOverTime;
import itf.account.AccountFactory;

import server.workflow.wfengine.WfException;
import server.framework.common.BusinessException;
import db.essp.attendance.TcOvertime;
import c2s.essp.attendance.Constant;
import db.essp.attendance.TcOvertimeLog;
import java.util.Date;

public class OverTimeWfDefine extends WfDefineBase {
    static String packagId = Constant.WF_PACKAGE_ID;
    static String processId = Constant.WF_OVERTIME_PROCESS_ID;
    static String processName = Constant.WF_OVERTIME_PROCESS_NAME;
    /**
     * Participant:
     * id		name		type		description
     */
    static String[][] participants
            = { {"app_user", "application user", "user", "申请者"}
              , {"pm_role", "pm role", "role", "项目经理"}
              , {"dm_role", "dm role", "role", "部门经理"}
              , {"ceo_role", "ceo role", "role", "总经理"}
    };
    /**
     * Application:
     * id		name		type		description
     */
    static String[][] applications
            = { {"overtime_app", "", "class", "申请"}
              , {"overtime_review", "server.essp.attendance.workflow.define.OverTimeReviewApplication", "class", "审核"}
    };
    /**
     * Activity:
     * id		name		performer		implementation
     */
    static String[][] activities
        = { {"BEGIN", "开始节点", }
          , {"user_app", "加班申请", "app_user", "overtime_app"}
          , {"pm_review", "项目经理审核", "pm_role", "overtime_review"}
          , {"dm_review", "部门经理审核", "dm_role", "overtime_review"}
          , {"END", "结束节点"}
    };
    /**
     * Transition:
     * id		from		to		condition
     */
    static String[][] transitions
        = { {"t1", "BEGIN", "user_app"}
          , {"t2", "user_app", "pm_review"}
          , {"t3-1", "pm_review", "END", "pm_disagree"}
          , {"t3-2", "pm_review", "dm_review","pm_agree"}
          , {"t3-2-1", "dm_review", "END"}
    };
    public OverTimeWfDefine() throws WfException {
        super(packagId, processId);
        init();
    }
    private void init() throws WfException {
        setProcessName(processName);
        setParticipants(participants);
        setApplications(applications);
        setActivities(activities);
        setTransitions(transitions);
    }
    public IUser getUser(String performer,DtoWorkingProcess dtoWorkingProcess,Parameter para ){
        WfUser user = null;
        if("app_user".equals(performer)){//查找申请人
            String appLoginid = dtoWorkingProcess.getSubEmpLoginid();
            user = new WfUser(getUser(appLoginid));
        }else if("pm_role".equals(performer)){//查找项目经理
            Long wkRid = dtoWorkingProcess.getRid();
            LgOverTime lg = new LgOverTime();
            VbOverTime overTime = lg.getOverTimeVBByWkRid(wkRid);
            Long acntRid = overTime.getAcntRid();
            String pmLoginId = AccountFactory.create().getAccountByRID(acntRid).getManager();
            user = new WfUser(getUser(pmLoginId));
        }else if("dm_role".equals(performer)){//查找部门经理
            Long wkRid = dtoWorkingProcess.getRid();
            LgOverTime lg = new LgOverTime();
            VbOverTime overTime = lg.getOverTimeVBByWkRid(wkRid);
            Long acntRid = overTime.getAcntRid();
            String orgId = AccountFactory.create().getAccountByRID(acntRid).getOrganization();
            String orgManger = OrgnizationFactory.create()
                               .getOrgManagerLoginId(orgId);
            user = new WfUser(getUser(orgManger));
        }
        if(user == null)
            throw new BusinessException("","can not find participant ["+performer+"] in work flow ["+processName+"]");
        return user;
    }
    private DtoUser getUser(String loginId){
        if(loginId == null)
            return null;
        return HrFactory.create().findResouce(loginId);
    }

    public boolean checkCondition(String condition,DtoWorkingProcess dtoWorkingProcess,Parameter para ){
        Boolean agree = (Boolean) para.get("agree");
        if (agree == null)
                return false;
        if("pm_agree".equals(condition) ){//判断项目经理是否同意
            return agree.booleanValue() ;
        }else if("pm_disagree".equals(condition)){
            return !agree.booleanValue() ;
        }
        return true;
   }
   //撤销加班流程时,设置加班记录状态为Aborted,并增加一笔Log
   public void abortProcess(DtoWorkingProcess dtoWorkingProcess,Parameter para){
       Long wkRid = dtoWorkingProcess.getRid();
       LgOverTime lg = new LgOverTime();
       TcOvertime overTime = lg.getOverTimeByWkRid(wkRid);
       try {
           overTime.setStatus(Constant.STATUS_ABORTED);
           TcOvertimeLog log = new TcOvertimeLog();
           log.setTcOvertime(overTime);
           log.setLogDate(new Date());
           log.setLoginId(overTime.getLoginId());
           log.setDecision(Constant.STATUS_ABORTED);
           log.setIsEachDay(overTime.getActualIsEachDay());
           log.setDatetimeStart(overTime.getActualDatetimeStart());
           log.setDatetimeFinish(overTime.getActualDatetimeFinish());
           log.setTotalHours(overTime.getActualTotalHours());
           lg.addOverTimeReviewLog(log);
       } catch (Exception ex) {
           throw new BusinessException("TC_OVERTIME_99999","error abort overtime!",ex);
       }
   }

}
