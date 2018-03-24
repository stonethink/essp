package server.essp.pwm.wp.logic;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pwm.wp.DtoAccountAcitivity;
import c2s.essp.pwm.wp.DtoPwWp;
import c2s.essp.pwm.wp.DtoPwWpItem;
import com.wits.util.StringUtil;
import db.essp.pms.Activity;
import db.essp.pms.ActivityPK;
import essp.tables.EsspEbsParametersT;
import essp.tables.PwWp;
import essp.tables.PwWpsum;
import itf.account.AccountFactory;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import org.apache.log4j.Category;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.hibernate.HBAccess;
import java.sql.ResultSet;
import server.essp.pwm.wpList.logic.LgWpList;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.essp.pms.wbs.DtoActivityWorker;
import server.essp.pms.activity.logic.LgActivityWorker;
import java.util.Date;

public class FPW01000CommonLogic {
    private static Category log = Category.getInstance("server");

    public static Object load(Session hbSession, Class cls, Long lRid) throws HibernateException {
        Object rtn = null;

        Query q2 = hbSession.createQuery(
            "from " + cls.getName() + " t "
            + "where t.rid = :rid "
            );
        q2.setString("rid", lRid.toString());
        for (Iterator it = q2.iterate(); it.hasNext(); ) {
            rtn = it.next();
            break;
        }

        return rtn;
    }

    public static void copyProperties(Object dest, Object orig) {
        try {
            DtoUtil.copyProperties(dest, orig);
        } catch (Exception ex) {
        }
    }

    public static DtoPwWp createDToPwWp(HBComAccess dbAccessor, PwWp pwWp, String loginId) {
        DtoAccountAcitivity dtoAccountAcitivity = new DtoAccountAcitivity();
        DtoPwWp dtoPwWp = new DtoPwWp();
        DtoUtil.copyBeanToBean(dtoPwWp, pwWp);
        DtoUtil.copyBeanToBean(dtoPwWp, pwWp.getWpSum());

        dtoPwWp.setWpWorkerName(dtoPwWp.getWpWorker());
        String acntManager = pwWp.getAccount().getManager();

//        log.info("02: createDtoWp.dto is " + DtoUtil.dumpBean(pwWp)
//            + "\r\n loginid is " + loginId
//            + "\r\n acntManager is " + acntManager);
        if (loginId.equals(dtoPwWp.getWpAssignby()) == true
            || loginId.equals(acntManager) == true
            ) {
            dtoPwWp.setIsAssignBy(true);
        } else {
            dtoPwWp.setIsAssignBy(false);
        }
        dtoPwWp.setWpAssignbyName(pwWp.getWpAssignby());

        //getCurrAccountActivity
        Long inProjectId = dtoPwWp.getProjectId();
        Long inActivityId = dtoPwWp.getActivityId();
        if (inProjectId == null) {
            throw new BusinessException("E000000", "The account id of this wp is null.");
        }

        dtoAccountAcitivity = FPW01000CommonLogic.getCurrAccountActivity(
            dbAccessor, inProjectId, inActivityId);
        dtoPwWp.setAccountId(dtoAccountAcitivity.getAccountCode());
        dtoPwWp.setAccountName(dtoAccountAcitivity.getAccountName());
        dtoPwWp.setAccountManagerName(dtoAccountAcitivity.getManagerName());
        dtoPwWp.setAccountTypeName(dtoAccountAcitivity.getAccountType());
        dtoPwWp.setActivityCode(dtoAccountAcitivity.getTaskId());
        dtoPwWp.setActivityName(dtoAccountAcitivity.getClnitem());

        return dtoPwWp;
    }

    public static void setPwporwp(PwWp wp) throws BusinessException {
        String sWorkers = wp.getWpWorker();

        if (sWorkers == null || sWorkers.equals("")) {
            throw new BusinessException("E0000000", "The wp's workers is null!!");
        }
        // Pwp = "0" , Wp = "1"
        if (StringUtil.nvl(sWorkers).equals("") == false) {
            if (sWorkers.split(",").length > 1) {
                wp.setWpPwporwp("1");
            } else {
                wp.setWpPwporwp("0");
            }
        }
    }

    public static void createPwWpsum(HBComAccess hbComAccess, DtoPwWp dtoPwWp) throws BusinessException {
        try {
            Long lWpRid = dtoPwWp.getRid();
            if( lWpRid == null ){
                throw new BusinessException( "E000000" , "The wp's id is null." );
            }

            PwWpsum wpsum = new PwWpsum();
            FPW01000CommonLogic.copyProperties(wpsum, dtoPwWp);
//            wpsum.setRid(lWpRid);
            wpsum.setWpId(dtoPwWp.getRid());
            wpsum.setRid(dtoPwWp.getRid());

            //计算其他PW_WPSUM数据

            double wpPlanWkhr = dtoPwWp.getWpPlanWkhr() == null? 0: dtoPwWp.getWpPlanWkhr().doubleValue();
            double wpSizePlan = dtoPwWp.getWpSizePlan() == null? 0: dtoPwWp.getWpSizePlan().doubleValue();
            String wpSizeUnit = StringUtil.nvl(dtoPwWp.getWpSizeUnit());

            double wpDensityratePlan = dtoPwWp.getWpDensityratePlan() == null? 0: dtoPwWp.getWpDensityratePlan().doubleValue();
            double wpDefectratePlan = dtoPwWp.getWpDefectratePlan() == null? 0: dtoPwWp.getWpDefectratePlan().doubleValue();

            //wp_productivity
            double wpProductivityPlan = 0;
            if (wpPlanWkhr != 0) {
                wpProductivityPlan = wpSizePlan / wpPlanWkhr;
            }
            String wpProductivityUnit = wpSizeUnit + "/Hour";
            wpsum.setWpProductivityPlan(new BigDecimal(wpProductivityPlan));
            wpsum.setWpProductivityUnit(wpProductivityUnit);

            //density
            double wpDensityPlan = wpDensityratePlan * wpSizePlan;
            wpsum.setWpDensityPlan(new BigDecimal(wpDensityPlan));

            //defect
            double wpDefectPlan = wpDefectratePlan * wpSizePlan;
            wpsum.setWpDefectPlan(new Long( (long) wpDefectPlan));

            //HBComAccess.assignedRid(wpsum);
            hbComAccess.save(wpsum);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                log.error("", ex);
                throw new BusinessException("E000000", "Create PwWpsum error.");
            }
        }
    }

    public static String getAccountTypeName(HBComAccess hbComAccess, String typeId) throws BusinessException {
        String name = "";
        try {
            Session hbSession = hbComAccess.getSession();

            if (StringUtil.nvl(typeId).equals("") == false) {
                Query q = hbSession.createQuery("from essp.tables.EsspEbsParametersT t " +
                                                " where t.comp_id.kindCode = 'AccountType' and t.comp_id.code = :code");
                q.setString("code", typeId);
                for (Iterator it = q.iterate(); it.hasNext(); ) {
                    EsspEbsParametersT t = (EsspEbsParametersT) it.next();
                    name = StringUtil.nvl(t.getName());
                    break;
                }
            }
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "Select account type's name error.");
        }

        return name;
    }

    public static DtoAccountAcitivity getCurrAccountActivity(HBComAccess hbComAccess, Long lProjectId, Long lActivityId) throws BusinessException {
        DtoAccountAcitivity dtoAccountAcitivity = new DtoAccountAcitivity();
        try {
//            EsspSysAccountT currAccount = null;
//            EsspPwpTemcln currActivity = null;
            Session hbSession = hbComAccess.getSession();

            if (lProjectId != null) {

//                Query q2 = hbSession.createQuery(
//                    "from essp.tables.EsspSysAccountT t "
//                    + "where t.id = :id"
//                    );
//                q2.setString("id", lProjectId.toString());
//                for (Iterator it = q2.iterate(); it.hasNext(); ) {
//                    currAccount = (EsspSysAccountT)it.next();
//                    break;
//                }
                IDtoAccount currAccount = AccountFactory.create().getAccountByRID(lProjectId);

                if (currAccount == null) {
                    throw new BusinessException("E000000", "Not find the account - " + lProjectId + ".");
                }
                dtoAccountAcitivity.setProjectId(currAccount.getRid());
                dtoAccountAcitivity.setAccountCode(currAccount.getId());
                dtoAccountAcitivity.setAccountName(currAccount.getName());
                dtoAccountAcitivity.setAccountType(currAccount.getType());
//                dtoAccountAcitivity.setAccountTypeName(currAccount.getTypeName());

                dtoAccountAcitivity.setOrganization(currAccount.getOrganization());
                dtoAccountAcitivity.setManager(currAccount.getManager());

                //get manager name
//                LGHrAllocate lgHrAllocate = new LGHrAllocate();
//                String manageName = lgHrAllocate.getOneUserName(currAccount.getManager().toString());
//                dtoAccountAcitivity.setManagerName(manageName);
                dtoAccountAcitivity.setManagerName(currAccount.getManager()); //暂用manger的login id代替它的名字
            }

            if (lActivityId != null) {
//                currActivity = (EsspPwpTemcln) hbComAccess.load(EsspPwpTemcln.class, lActivityId);
//                Query q2 = hbSession.createQuery(
//                    "from essp.tables.EsspPwpTemcln t "
//                    + "where t.id = :id "
//                    );
//                q2.setString("id", lActivityId.toString());
//                for (Iterator it = q2.iterate(); it.hasNext(); ) {
//                    currActivity = (EsspPwpTemcln) it.next();
//                    break;
//                }

                ActivityPK activityPK = new ActivityPK( lProjectId, lActivityId );
                Activity currActivity = null;
                try {
                    currActivity = (Activity) hbComAccess.getSession().get(Activity.class, activityPK);
                    if (currActivity == null) {
                        //throw new BusinessException("E000000", "Not find the activity - " + lActivityId + ".");
                    }
                    dtoAccountAcitivity.setActivityId(currActivity.getPk().getActivityId());
                    dtoAccountAcitivity.setTaskId(currActivity.getCode());
                    dtoAccountAcitivity.setClnitem(currActivity.getName());
                } catch (Exception ex1) {
                    //throw new BusinessException("E000000", "Not find the activity - " + lActivityId + ".");
                }

            }
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                log.error("", ex);
                throw new BusinessException("E000000", "getCurrAccountActivity error.");
            }
        }

        return dtoAccountAcitivity;
    }

    public static List getWPList(HBComAccess hbComAccess, String inUserId, Long acntRid) throws Exception {
        String sql = "select * from PW_WP where ( " + /* " WP_PWPORWP=1 AND " + */ "  WP_WORKER LIKE '%" + inUserId + "%' "
                     + " and PROJECT_ID=" +acntRid.longValue()+ " "
                     + "AND WP_STATUS!='" + DtoPwWpItem.CLOSED_STATUS +
            "'  AND WP_STATUS!='" + DtoPwWpItem.CANCEL_STATUS + "'  AND WP_STATUS!='" + DtoPwWpItem.REJECT_STATUS + "'  )";

        List pwpList = hbComAccess.executeQueryToList(sql, DtoPwWp.class);

        return pwpList;
    }

    public static List getPWPInAccountList(HBComAccess hbComAccess, String inUserId, String projectId) throws Exception {
        String sql = "select * from PW_WP where ( WP_PWPORWP=0 AND WP_WORKER='" + inUserId + "' AND PROJECT_ID=" + projectId + " AND WP_STATUS!='" +
            DtoPwWpItem.CLOSED_STATUS + "'  AND WP_STATUS!='" + DtoPwWpItem.CANCEL_STATUS + "'  AND WP_STATUS!='" + DtoPwWpItem.REJECT_STATUS + "'  )";

        List pwpList = hbComAccess.executeQueryToList(sql, DtoPwWp.class);

        return pwpList;
    }

    public static boolean findElement( String element, String[] set ){
        int i ;
        for( i = 0; i < set.length; i++ ){
            if( element != null ){
                if( element.equals( set[i] ) == true ){
                    break;
                }else{
                    if( set[i] == null ){
                        break;
                    }
                }
            }
        }

        if( i == set.length ){
            return false ;
        }else{
            return true;
        }
    }

    public static Long generatorWpCode( HBComAccess hbComAccess, Long acntRid){
        return generatorWpCode(hbComAccess ,acntRid, null);
    }

    public static Long generatorWpCode( HBComAccess hbComAccess, Long acntRid, Long activityRid){
        long maxCode = 1;

        String sqlSel = "select MAX_CODE from PW_WP_MAX_CODE";
        String whereStr = " where 1=1 ";
        if( acntRid != null ){
            whereStr += " and ACNT_RID="+acntRid.longValue() ;
        }else{
            whereStr += " and ACNT_RID is null";
        }

        if( activityRid != null ){
            whereStr += " and ACTIVITY_RID="+activityRid.longValue() ;
        }else{
            whereStr += " and ACTIVITY_RID is null";
        }

        try {
            ResultSet rs = hbComAccess.executeQuery(sqlSel + whereStr);

            if (rs.next()) {
                maxCode = rs.getLong("MAX_CODE");
                maxCode++;

                if( isWpCodeExist(hbComAccess, acntRid, maxCode) == true ){
                    maxCode++;
                }

                String sqlUpdate = "update PW_WP_MAX_CODE set MAX_CODE=" + maxCode + whereStr;
                hbComAccess.executeUpdate(sqlUpdate);
            }else{
                maxCode = 1;

                if( isWpCodeExist(hbComAccess, acntRid, maxCode) == true ){
                    maxCode++;
                }

                String sqlIns = "insert into PW_WP_MAX_CODE (ACNT_RID, ACTIVITY_RID, MAX_CODE)values(" +
                                acntRid + ", " + activityRid+ ", " + maxCode + " )";
                hbComAccess.executeUpdate(sqlIns);
            }

        } catch (Exception ex) {
            return null;
        }

        return new Long(maxCode);
    }

    private static boolean isWpCodeExist(HBComAccess hbComAccess, Long acntRid, long code){
        String codeStr = code+"";
        int len = codeStr.length();
        for (int i = len; i < DtoPwWp.CODE_NUMBER_LENGTH; i++) {
            codeStr = "0" + codeStr;
        }
        String likeStr = "WP-%-"+codeStr;
        try {
            String sqlSel = "select count(*) as NUM from PW_WP where PROJECT_ID=" + acntRid.longValue()
                            + " and WP_CODE like '" + likeStr + "'";
            ResultSet rs = hbComAccess.executeQuery(sqlSel);
            if (rs.next()) {
                int i = rs.getInt("NUM");
                if (i > 0) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }

        return false;
    }

    public static void CheckData(DtoPwWp dtoPw,String funId){
        String[] workers = dtoPw.getWpWorker().split(",");
             for(int i=0;i<workers.length;i++){
                 String loginId = (String) workers[i];
                 LgActivityWorker lgActivityWorker = new LgActivityWorker();
                 //判断wp中的worker是否存在于activity worker里面
                 //1.如果存在,判断activity worker的计划时间区间是否包含wp的计划时间区间以及activity worker的
                 //计划工作时间是否大于wp worker的必要工作时间之和,如果计划时间区间不包含或计划工作时间小于的话,
                 //需要修改activity worker的计划时间区间或计划工作时间
                 //2.如果不存在,需要增加对应的activity worker
                 boolean isWorker = lgActivityWorker.isActivityWorkerDto(dtoPw.
                     getProjectId(), dtoPw.getActivityId(), loginId);
                 if (isWorker) {
                     LgWpList lgwp = new LgWpList();
                     DtoPwWp dtoPmWp = lgwp.getDtoPmWpSum(dtoPw.
                     getProjectId(), dtoPw.getActivityId(), loginId);
                     LgActivityWorker lgWorker = new LgActivityWorker();
                     List listworkers = (List)lgWorker.listActivityWorkerDto(dtoPw.
                     getProjectId(), dtoPw.getActivityId());
                     for(int j=0;j<listworkers.size();j++){
                         DtoActivityWorker worker = (DtoActivityWorker)listworkers.get(j);
                         if((worker.getLoginId()).equals(loginId)){
                             Date pmWpStart = dtoPmWp.getWpPlanStart();
                             Date pmWpFinish = dtoPmWp.getWpPlanFihish();
                             Date wpStart = dtoPw.getWpPlanStart();
                             Date wpFinish = dtoPw.getWpPlanFihish();
                             Date workerStart = worker.getPlanStart();
                             Date workerFinish = worker.getPlanFinish();
                             if (pmWpStart != null && workerStart != null
                                 && workerStart.after(pmWpStart)) {
                                 worker.setPlanStart(pmWpStart);
                             }
                             if (wpStart != null && workerStart != null
                                 && workerStart.after(wpStart)) {
                                 worker.setPlanStart(wpStart);
                             }
                             if (pmWpFinish != null && workerFinish != null
                                 && workerFinish.before(pmWpFinish)) {
                                 worker.setPlanFinish(pmWpFinish);
                             }
                             if (wpFinish != null && workerFinish != null
                                 && workerFinish.before(wpFinish)) {
                                 worker.setPlanFinish(wpFinish);
                             }
                             long PlanWorkTime = worker.getPlanWorkTime().longValue();
                             long ReqWkhr = 0;
                             if (funId.equals("creatWp")) {
                                 ReqWkhr = dtoPmWp.getWpReqWkhr().longValue() +
                                           dtoPw.getWpReqWkhr().longValue();
                             } else {
                                 DtoPwWp wp = (DtoPwWp)lgwp.getDtoPmWp(dtoPw.getRid());
                                 ReqWkhr = dtoPmWp.getWpReqWkhr().longValue() +
                                           dtoPw.getWpReqWkhr().longValue() - wp.getWpReqWkhr().longValue();
                             }
                             if (ReqWkhr > PlanWorkTime) {
                                 worker.setPlanWorkTime(new Long(ReqWkhr));
                             }
                             lgWorker.update(worker);
                             break;
                         }
                     }

                 } else {
                     LgAccountLaborRes lgLaborRes = new LgAccountLaborRes();
                     DtoAcntLoaborRes dto = lgLaborRes.findResourceDto(dtoPw.
                     getProjectId(), loginId);
                     DtoActivityWorker worker = new DtoActivityWorker();
                     //DtoAcntLoaborRes和DtoActivityWorker包含相同名字但意义不同的字段
                     worker.setAcntRid(dto.getAcntRid());
                     worker.setActivityRid(dtoPw.getActivityId());
                     worker.setEmpName(dto.getEmpName());
                     worker.setJobCode(dto.getJobCode());
                     worker.setJobcodeId(dto.getJobcodeId());
                     worker.setLoginId(dto.getLoginId());
                     worker.setRoleName(dto.getRoleName());
                     if(dtoPw.getWpReqWkhr()!=null){
                         worker.setPlanWorkTime(new Long(dtoPw.getWpReqWkhr().longValue()));
                     }
                     worker.setPlanStart(dtoPw.getWpPlanStart());
                     worker.setPlanFinish(dtoPw.getWpPlanFihish());
                     LgActivityWorker lgWorker = new LgActivityWorker();
                     lgWorker.add(worker);
                 }
             }

    }


    public static void main(String args[]){
        Date today = new Date();
        java.sql.Date sqlDate = new java.sql.Date(today.getTime());
        System.out.println(today.after(sqlDate));
        try {
            HBComAccess hb = new HBComAccess();
            hb.followTx();

            Long wpCode = FPW01000CommonLogic.generatorWpCode(hb, new Long(1), new Long(1));
            System.out.println(wpCode);
            hb.endTxCommit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
