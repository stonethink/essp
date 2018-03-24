package server.essp.attendance.workflow.define;

import server.workflow.wfdefine.*;
import c2s.workflow.IUser;
import com.wits.util.Parameter;
import c2s.workflow.DtoWorkingProcess;
import c2s.essp.attendance.WfUser;
import itf.hr.HrFactory;
import c2s.essp.common.user.DtoUser;
import server.essp.attendance.leave.logic.LgLeave;
import server.essp.attendance.leave.viewbean.VbLeave;
import itf.orgnization.OrgnizationFactory;
import c2s.essp.attendance.Constant;
import server.workflow.wfengine.WfException;
import db.essp.attendance.TcLeaveMain;
import server.framework.common.BusinessException;
import itf.account.AccountFactory;

public class LeaveWfDefine extends WfDefineBase {
    static String packagId = Constant.WF_PACKAGE_ID;
    static String processId = Constant.WF_LEAVE_PROCESS_ID;
    static String processName = Constant.WF_LEAVE_PROCESS_NAME;
    /**
     * Participant:
     * id		name		type		description
     */
    static String[][] participants
        = { {"app_user", "application user", "user", "������"},
            {"pm_role", "pm role", "role", "��Ŀ����"},
            {"dm_role", "dm role", "role", "���ž���"},
            {"ceo_role", "ceo role", "role", "�ܾ���"}
    };
    /**
     * Application:
     * id		name		type		description
     */
    static String[][] applications
            = { {"leave_app", "", "class", "����"}
              , {"leave_review", "server.essp.attendance.workflow.define.LeaveReviewApplication", "action", "���"}
    };


    /**
     * Activity:
     * id		name		performer		implementation
     */
    static String[][] activities
        = { {"BEGIN", "��ʼ�ڵ�", }
          , {"user_app", "�������", "app_user", "leave_app"}
          , {"pm_review", "��Ŀ�������", "pm_role", "leave_review"}
          , {"dm_review", "���ž������", "dm_role", "leave_review"}
          , {"ceo_review", "�ܾ������", "ceo_role", "leave_review"}
          , {"END", "�����ڵ�"}
    };


    /**
     * Transition:                                                         |---------------t3-2-1(pm_disagree||(dm_agree&day<3))---------------|
     * BEGIN --t1---> user_app --t2--> pm_review ---t3-2(pm_agree)---> dm_review -----t3-2-2(dm_agree&day>=3)---------> ceo_review ---t4----> END
     *                                    |                                                                                                    |
     *                                    |-----------------------------t3-1(pm_disagree)------------------------------------------------------|
     *id		from		to		condition
     */
    static String[][] transitions
        = { {"t1", "BEGIN", "user_app"}
          , {"t2", "user_app", "pm_review"}
          , {"t3-1", "pm_review", "END", "pm_disagree"}
          , {"t3-2", "pm_review", "dm_review","pm_agree"}
          , {"t3-2-1", "dm_review", "END","(dm_agree&day<3)||(dm_disagree)"}
          , {"t3-2-2", "dm_review", "ceo_review","dm_agree&day>=3"}
          , {"t4", "ceo_review", "END"}
    };
    public LeaveWfDefine() throws WfException {
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
         }else {
             Long wkRid = dtoWorkingProcess.getRid();
             LgLeave lg = new LgLeave();
             VbLeave leave = lg.getLeaveVBByWkRid(wkRid);
             if ("pm_role".equals(performer)) { //������Ŀ����
                 Long acntRid = leave.getAcntRid();
                 String manager = AccountFactory.create().getAccountByRID(
                         acntRid).getManager();
                 user = new WfUser(getUser(manager));
             } else if ("dm_role".equals(performer)) { //���Ҳ��Ÿ�����

                 String orgId = leave.getOrgId();
                 String orgManger = OrgnizationFactory.create()
                                    .getOrgManagerLoginId(orgId);
                 user = new WfUser(getUser(orgManger));
             } else if ("ceo_role".equals(performer)) { //���Ҳ��Ÿ�������һ��
                 String orgId = leave.getOrgId();
                 String parentOrgManger = OrgnizationFactory.create().
                                          getParentOrgManager(orgId);
                 user = new WfUser(getUser(parentOrgManger));
             }
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
       if("pm_agree".equals(condition) ){///�ж���Ŀ�����Ƿ�ͬ��
           return agree.booleanValue();
       }else if("pm_disagree".equals(condition)){
           return !agree.booleanValue();
       }else if("(dm_agree&day<3)||(dm_disagree)".equals(condition) ){////�жϲ��ž����Ƿ�ͬ�������С������
           Long day = (Long) para.get("day");
           if (agree == null)
               return false;
           if(day == null)
               day = new Long(0);
           return (agree.booleanValue() && day.longValue() < 3) ||
                   (!agree.booleanValue());
       }else if("dm_agree&day>=3".equals(condition)){
           Long day = (Long) para.get("day");
           if (agree == null || day == null)
               return false;
           return agree.booleanValue() && day.longValue() >= 3;
       }
       return true;
   }

   //�����������ʱ,������ټ�¼״̬ΪAborted,������ٵ�ʱ��,������һ��Log
   public void abortProcess(DtoWorkingProcess dtoWorkingProcess,Parameter para){
       Long wkRid = dtoWorkingProcess.getRid();
       LgLeave lg = new LgLeave();
       TcLeaveMain leave = lg.getLeaveByWkRid(wkRid);
       try {
           leave.setStatus(Constant.STATUS_ABORTED);
           lg.addReviewLog(leave,Constant.STATUS_ABORTED,null);
           lg.returnUseLeaveHours(leave.getLoginId(),
                                  leave.getLeaveName(),
                                  leave.getActualTotalHours().doubleValue());
       } catch (Exception ex) {
           throw new BusinessException("TC_LEAVE_99999","error abort leave!",ex);
       }
   }

   public static void main(String[] args){
//       WfUser user = (WfUser) HrFactory.create().findResouce("stone.shi");
    try {
        LeaveWfDefine l = new LeaveWfDefine();
    } catch (WfException ex) {
    }

   }
}
