package server.essp.attendance.workflow.define;

import server.workflow.wfdefine.*;
import c2s.essp.attendance.Constant;
import server.workflow.wfengine.WfException;
import c2s.workflow.IUser;
import c2s.essp.attendance.WfUser;
import com.wits.util.Parameter;
import c2s.workflow.DtoWorkingProcess;
import c2s.essp.common.user.DtoUser;
import itf.hr.HrFactory;
import server.framework.common.BusinessException;
import db.essp.attendance.TcLeaveMain;
import server.essp.attendance.leave.logic.LgLeave;
import com.wits.util.Config;

public class ModifyLeaveWfDefine extends WfDefineBase {
    static String packagId = Constant.WF_PACKAGE_ID;
    static String processId = Constant.WF_MODIFYLEAVE_PROCESS_ID;
    static String processName = Constant.WF_MODIFYLEAVE_PROCESS_NAME;
    /**
     * Participant:
     * id		name		type		description
     */
    static String[][] participants  =
            { {"app_user", "application user", "user", "������"},
            {"manager_role", "manager role", "role", "���ڹ���Ա"}
    };
    /**
     * Application:
     * id		name		type		description
     */
    static String[][] applications
            = {
               {"modifyleave_review", "server.essp.attendance.workflow.define.ModifyLeaveReviewApplication", "action", "���"}
    };
    /**
     * Activity:
     * id		name		performer		implementation
     */
    static String[][] activities
            = { {"BEGIN", "��ʼ�ڵ�", }
              , {"user_app", "��������", "app_user","modifyleave_app"}
              , {"manager_review", "���ڹ���Ա���", "manager_role", "modifyleave_review"}
              , {"END", "�����ڵ�"}
    };
    static String[][] transitions
        = { {"t1", "BEGIN", "user_app"}
          , {"t2", "user_app", "manager_review"}
          , {"t3", "manager_review", "END"}

    };
    public ModifyLeaveWfDefine() throws WfException {
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
        if("app_user".equals(performer)){//����������
             String appLoginid = dtoWorkingProcess.getSubEmpLoginid();
             user = new WfUser(getUser(appLoginid));
         }else if("manager_role".equals(performer)){
             String manager = null;
             try{
                 Config cfg = new Config("manager");
                 manager = cfg.getValue("manager");
             }catch(Throwable t){
                 t.printStackTrace();
                 manager = "wenjuan.zhang";
             }
             user = new WfUser(getUser(manager));
         }else{
             throw new BusinessException("","can not find participant ["+performer+"] in work flow ["+processName+"]");
         }
         if(user == null)
             throw new BusinessException("","can not find participant ["+performer+"] in work flow ["+processName+"]");
         return user;
    }
    private DtoUser getUser(String loginId){
        return HrFactory.create().findResouce(loginId);
    }

    //�����������ʱ,������ټ�¼״̬ΪAborted,������ٵ�ʱ��,������һ��Log
    public void abortProcess(DtoWorkingProcess dtoWorkingProcess,Parameter para){
        Long wkRid = dtoWorkingProcess.getRid();
        LgLeave lg = new LgLeave();
        TcLeaveMain leave = lg.getLeaveByWkRid(wkRid);
        try {
            leave.setStatus(Constant.STATUS_FINISHED);
            lg.addReviewLog(leave,"Abort Modifying",null);
        } catch (Exception ex) {
            throw new BusinessException("TC_LEAVE_99999","error abort leave!",ex);
        }
    }

}
