package server.essp.pms.account.baseline.logic;

import server.framework.logic.AbstractBusinessLogic;
import java.util.List;
import db.essp.pms.Account;
import server.framework.common.BusinessException;
import db.essp.pms.AccountBaseline;
import db.essp.pms.AccountBaselineLog;
import c2s.essp.pms.account.DtoAcntBL;
import net.sf.hibernate.Query;
import c2s.dto.DtoUtil;
import java.util.ArrayList;
import c2s.essp.pms.account.DtoAcntBLApp;
import c2s.essp.pms.account.DtoAcntBLLog;
import db.essp.pms.AccountApprovalLog;
import server.framework.hibernate.HBSeq;
import server.essp.cbs.buget.logic.LgBuget;
import db.essp.pms.MilestoneHistory;
import java.sql.ResultSet;
import net.sf.hibernate.Session;
import server.essp.pms.account.logic.AccountBaseLineExport;
import com.wits.util.Parameter;
import c2s.essp.tc.weeklyreport.DtoTcKey;



/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: WITS-WH</p>
 * @version 1.0
 * @author stone*/
public class LgAccountBaseline extends AbstractBusinessLogic {
    private List baseLineApprovedListerners;

    /**
     * default constructor*/
    public LgAccountBaseline() {
        baseLineApprovedListerners = new ArrayList();
        addBaseLineAppLisnterner(new LgBuget());
        addBaseLineAppLisnterner(new LgSaveMilestoneBak());
    }
    //add by xr,添加BaseApproved监听器
    public void addBaseLineAppLisnterner(BaseLineApprovedListener l){
        baseLineApprovedListerners.add(l);
    }
    public void removeBaseLineAppLisnterner(BaseLineApprovedListener l){
        baseLineApprovedListerners.remove(l);
    }
    public void clearBaseLineAppLisnterner(BaseLineApprovedListener l){
        baseLineApprovedListerners.clear();
    }
    public void fireBaseLineApproved(AccountBaseline accountBaseline){
        for(int i = 0;i < baseLineApprovedListerners.size() ;i ++){
            Object obj = baseLineApprovedListerners.get(i);
            if(obj instanceof BaseLineApprovedListener){
                BaseLineApprovedListener l = (BaseLineApprovedListener) obj;
                l.approveBaseLine(accountBaseline);
            }
        }
    }
    /**
     *
     * @param account Account*/
    public LgAccountBaseline(Account account) {
        //this.account = account;
    }


    /**
     * 依据输入的account记录的rid构造
     *
     * @param accountRid Long
     * @throws BusinessException
     */
    public LgAccountBaseline(Long accountRid) throws BusinessException {
        //this.account = load(accountRid);
    }


    /**
     * 依据输入的项目代号accountId构造
     *
     * @param accountId String
     * @throws BusinessException
     */
    public LgAccountBaseline(String accountId) throws BusinessException {
        //this.account = load(accountId);
    }


    /**
     *
     * @param rid Long
     * @return db.essp.pms.AccountBaseline
     * @throws BusinessException
     */
    public AccountBaseline load(Long rid) {
        try {
            AccountBaseline accountBaseline = (AccountBaseline)this.
                                              getDbAccessor().
                                              load(AccountBaseline.class, rid);
            return accountBaseline;
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     *
     * @param accountId String
     * @return db.essp.pms.AccountBaseline
     * @throws BusinessException
     */
    public AccountBaseline load(String accountId) {
        try {
            Query q = getDbAccessor().getSession().createQuery("from AccountBaseline b, db.essp.pms.Account a where b.rid = a.rid and a.id = :accID ");
            q.setString("accID", accountId);
            AccountBaseline accountBaseline = (AccountBaseline) q.uniqueResult();
            return accountBaseline;

        } catch (BusinessException bex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * 获取当前Baseline的信息，包括：当前Baseline的内容以及ApprovalLog的列表 1.
     * 将this.accountBaseline -> DtoAccountBaseline 2. 获取ApprovalLog的list:
     * this.listApprovalLog()
     *
     * @return DtoAccountBaselineInfo
     * @throws BusinessException
     */
//    public DtoAccountBaselineInfo queryBaselineInfo() throws BusinessException {
//        return null;
//    }


    /**
     * 获取当前Baseline的信息，包括：当前Baseline的内容以及ApprovalLog的列表 1.
     * this.accountBaseline -> DtoAccountBaseline 2. 获取ApprovalLog的list:
     * this.listApprovalLog()
     *
     * @param accountRid Long
     * @return DtoAccountBaselineInfo
     * @throws BusinessException
     */
    public DtoAcntBL queryBaselineInfo(Long accountRid) throws
        BusinessException {
        this.accountBaseline = load(accountRid);

        if (this.accountBaseline == null) {
            return null;
        }

        try {
            dtoAcntBL = new DtoAcntBL();
            DtoUtil.copyProperties(dtoAcntBL, this.accountBaseline);
            dtoAcntBL.setAdd(false);
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

        return dtoAcntBL;
    }


    /**
     *
     * @param accountRid Long
     * @param baselineId String
     * @return DtoBaselineApprovalLog
     * @throws BusinessException
     */
    public List listApprovalLog(Long accountRid, String baselineId) {
        try {

            Query q = getDbAccessor().getSession().createQuery(
                "from AccountApprovalLog a where a.acntRid = :acntRid and a.baselineId = :blID ");
            q.setLong("acntRid", accountRid.longValue());
            q.setString("blID", baselineId);
            List dbarry = q.list();
            List csarry = new ArrayList();

            for (int i = 0; i < dbarry.size(); i++) {
                DtoAcntBLApp blApp = new DtoAcntBLApp();
                DtoUtil.copyProperties(blApp, dbarry.get(i));
                csarry.add(blApp);
            }

            return csarry;

        } catch (BusinessException bex) {
            return null;
        } catch (Exception ex) {
            return null;
        }

    }


    /**
     *
     * @param accountRid Long
     * @return DtoBaselineApprovalLog
     * @throws BusinessException
     */
    public List listBaseLineLog(Long accountRid) {
        try {

            Query q = getDbAccessor().getSession().createQuery(
                "from db.essp.pms.AccountBaselineLog a where a.acntRid = :acntRid ");
            q.setLong("acntRid", accountRid.longValue());
            List dbarry = q.list();
            List csarry = new ArrayList();

            for (int i = 0; i < dbarry.size(); i++) {
                DtoAcntBLLog blLog = new DtoAcntBLLog();
                DtoUtil.copyProperties(blLog, dbarry.get(i));
                csarry.add(blLog);
            }

            return csarry;

        } catch (BusinessException bex) {
            return null;
        } catch (Exception ex) {
            return null;
        }

    }

    public void saveOrUpdateBaseLine(DtoAcntBL dtoAccountBaseline, Boolean isPM,
                                     Boolean isManger, Boolean isApplication) throws
        BusinessException {

        if (isApplication.booleanValue()) {
            if (isRepeatOfBaselineID(dtoAccountBaseline)) {
                throw new BusinessException("Baseline [" +
                                            dtoAccountBaseline.getBaselineId() +
                                            "]is repeat!");
            }
        }

        saveOrUpdateBaseLine(dtoAccountBaseline);
        saveAppLog(dtoAccountBaseline, isPM, isManger);

    }


    /**
     * 修改Baseline
     * 1. 更新当前rid的记录
     * 2. 如果当前记录中的：appUser,appDate, appStauts ,comment有变化则写入PMS_ACNT_APPROVAL_LOG表；
     * 　　且当appStauts == "Approved"时，将当前记录写入PMS_ACNT_BASELINE_LOG表
     * 3. 做BaseLine Approved时的相关动作 @see fireBaseLineApproved(),saveMilestoneBak()
     * @param dtoAccountBaseline DtoAccountBaseline
     * @throws server.framework.common.BusinessException*/
    public void saveOrUpdateBaseLine(DtoAcntBL dtoAccountBaseline) throws
        BusinessException {
        accountBaseline = new AccountBaseline();
        try {
            DtoUtil.copyProperties(accountBaseline, dtoAccountBaseline);
            if (dtoAccountBaseline.isAdd()) {
                this.getDbAccessor().save(accountBaseline);
            } else {
                this.getDbAccessor().update(accountBaseline);
            }


            if (dtoAccountBaseline.getAppStatus().equals(DtoAcntBL.
                STATUS_APPROVED)) {

                //(add by zj)生成BaseLineLog的Excel文件，并返回FileInfo的id
                BaseLineLogExport baseLineLogExport=new BaseLineLogExport();
                String fileLink=baseLineLogExport.exportExcel(accountBaseline);

                //（modify by zj）保存被审核的BaseLine，fileLink用于下载BaseLineLog的Excel文件
                AccountBaselineLog accountBaselineLog=createBaseLineLog(dtoAccountBaseline);
                accountBaselineLog.setFileLink(fileLink);
                saveBaseLineLog(accountBaselineLog);
                fireBaseLineApproved(accountBaseline);

            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

    }

    public AccountBaselineLog createBaseLineLog(DtoAcntBL dtoAccountBaseline) throws
        BusinessException {
        AccountBaselineLog acntBLLog = new AccountBaselineLog();
        try {
            DtoUtil.copyProperties(acntBLLog, dtoAccountBaseline);
            acntBLLog.setRid(new Long(HBSeq.getSEQ("PMS_ACNT_BASELINE_LOG")));
            acntBLLog.setAcntRid(dtoAccountBaseline.getRid());
            acntBLLog.setApproveAttitude(dtoAccountBaseline.getRemark());

            return acntBLLog;
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

    }


    public void saveBaseLineLog(AccountBaselineLog accountBLLog) throws
        BusinessException {
        try {
            this.getDbAccessor().save(accountBLLog);
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

    }


    /**
     * 获取Baseline Log记录
     *
     * @param accountRid Long
     * @return DtoBaselineApprovalLog
     * @throws BusinessException
     */
//    public DtoAccountBaselineLog listBaselineLog(Long accountRid) throws
//        BusinessException {
//        return null;
//    }


    /**
     * 设置当前Baseline状态为: Modified*/
    public void setCurrBaselineModified() {

    }

    private boolean isRepeatOfBaselineID(DtoAcntBL dtoAccountBaseline) {

        try {

            Query q = getDbAccessor().getSession().createQuery(
                "from db.essp.pms.AccountBaselineLog a where a.acntRid = :acntRid and a.baselineId = :blID ");
            q.setLong("acntRid", dtoAccountBaseline.getRid().longValue());
            q.setString("blID", dtoAccountBaseline.getBaselineId());
            List dbarry = q.list();

            if (dbarry == null || dbarry.size() == 0) {
                return false;
            } else {
                return true;
            }

        } catch (BusinessException bex) {
            return false;
        } catch (Exception ex) {
            return false;
        }

    }

    private void saveAppLog(DtoAcntBL dtoAccountBaseline, Boolean bisPM,
                            Boolean bisManger) throws
        BusinessException {
        String sAppStatus = dtoAccountBaseline.getAppStatus();
        boolean isSaveApplcation = false;
        boolean isSave = true;
        boolean isPM = false;
        boolean isManager = false;
        if (bisPM != null && bisPM.booleanValue()) {
            isPM = true;
        }
        if (bisManger != null && bisManger.booleanValue()) {
            isManager = true;
        }
        AccountApprovalLog accountAppLog = new AccountApprovalLog();

        accountAppLog.setAcntRid(dtoAccountBaseline.getRid());

        accountAppLog.setBaselineId(dtoAccountBaseline.getBaselineId());
//        accountAppLog.setRid(new Long(HBSeq.getSEQ("PMS_ACNT_APPROVAL_LOG")));

        if (DtoAcntBL.STATUS_APPLICATION.equals(sAppStatus)) {
            isSaveApplcation = true;
            if(isManager && !isPM){
                isSave = false;
            }
        } else {
            if (isPM && !isManager) {
                isSaveApplcation = true;
            }
        }

        accountAppLog.setFilledBy(dtoAccountBaseline.getLoginUserId());
        if (isSaveApplcation) {
//            accountAppLog.setFilledBy(dtoAccountBaseline.getLoginUserId());
            accountAppLog.setAppStatus(DtoAcntBL.STATUS_APPLICATION);
            accountAppLog.setFilledDate(dtoAccountBaseline.getAppDate());
            accountAppLog.setRemark(dtoAccountBaseline.getAppReason());
        } else {
//            accountAppLog.setFilledBy(dtoAccountBaseline.getApproveUser());
            accountAppLog.setAppStatus(dtoAccountBaseline.getAppStatus());
            accountAppLog.setFilledDate(dtoAccountBaseline.getApproveDate());
            accountAppLog.setRemark(dtoAccountBaseline.getRemark());
        }
        if(isSave){
            try {
                System.out.println("##########################################");
                System.out.println("Session in add bs app log:" + this.getDbAccessor().getSession());
                System.out.println("##########################################");
                this.getDbAccessor().save(accountAppLog);
            } catch (Exception ex) {
                throw new BusinessException(ex);
            }
        }
    }

    AccountBaseline accountBaseline;
    AccountApprovalLog accountAppLog;
    List accountBaselineLog;
    DtoAcntBL dtoAcntBL;

    //AccountBaselineLog accountBaselineLog;


    List approvalLog;
}
