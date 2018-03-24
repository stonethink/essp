package server.essp.pms.account.logic;

import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import c2s.dto.DtoUtil;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;
import c2s.essp.pms.account.DtoPmsAcnt;
import essp.tables.SysCustomize;
import essp.tables.SysCustomizePk;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import db.essp.pms.Account;
import server.framework.common.Constant;


/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: WITS-WH</p>
 * @version 1.0
 * @author stone*/
public class LgAccountList extends AbstractESSPLogic {

    private String preHeaderSQL = "";
    private String preUserHeaderSQL = "";
    private String preEndSQL = "";
    private String preUserEndSQL = "";
    private String STATUS_UNDEL = "N";

    private final String GET_KIND_USER = "user";
    private final String GET_KIND_ALL = "all";

    private ArrayList lStatus;

    //当前选中的account
    private IDtoAccount currAccount = null;
    //当前选中的account所在的行
    private int chooseRowIndex = -1;
    private String loginId = null;
    private String queryCondition = null;
    private String entryFunType = null;

    final static String DEFAULT_FUN_TYPE = "PmsAccountFun";

    public void setCurrAccount(IDtoAccount currAccount) {
        this.currAccount = currAccount;
    }

    public IDtoAccount getCurrAccount() {
        return this.currAccount;
    }

    private void createPreSQL() {
        preHeaderSQL = " SELECT RID , RST , ACNT_ID , ACNT_NAME , "
                       + " ACNT_CURRENCY , ACNT_MANAGER,  "
                       +
                       " ACNT_TYPE,ACNT_ORGANIZATION,ACNT_ANTICIPATED_START, "
                       +
                       " ACNT_ANTICIPATED_FINISH,ACNT_PLANNED_START,ACNT_PLANNED_FINISH, "
                       + " ACNT_ACTUAL_START,ACNT_ACTUAL_FINISH,ACNT_STATUS,ACNT_BRIEF,ACNT_INNER,IS_TEMPLATE ";
        preUserHeaderSQL = preHeaderSQL
                           +
                           " , SUM(PM) AS PM , SUM(MANAGER)AS MANAGER , SUM(PR) AS PR ";

        preEndSQL = " Order by ACNT_ID DESC";
        preUserEndSQL = " GROUP BY RID , RST , ACNT_ID , ACNT_NAME ,"
                        + " ACNT_CURRENCY , ACNT_MANAGER, "
                        +
                        " ACNT_TYPE,ACNT_ORGANIZATION,ACNT_ANTICIPATED_START, "
                        +
                        " ACNT_ANTICIPATED_FINISH,ACNT_PLANNED_START,ACNT_PLANNED_FINISH, "
                        + " ACNT_ACTUAL_START,ACNT_ACTUAL_FINISH,ACNT_STATUS,ACNT_BRIEF,ACNT_INNER,IS_TEMPLATE";
        preUserEndSQL = preUserEndSQL + preEndSQL;
    }

    private String createSQLbyLoginID(String userLoginName) {
        String pmSQL = getPMAccountSQL(userLoginName);
        String managerSQL = getManagerAccountSQL(userLoginName);
        String participantSQL = getParticipantAccountSQL(userLoginName);

        String sql = pmSQL + " UNION " + managerSQL + " UNION " +
                     participantSQL;
        return sql;
    }

    private String getStautsSQL(List sAcntStatus) {
        String statusSQL = "";

        if (sAcntStatus == null) {
            return statusSQL;
        }

        for (int i = 0; i < sAcntStatus.size(); i++) {
            if (i != 0) {
                statusSQL = statusSQL + " OR ";
            }
            if (!sAcntStatus.get(i).toString().equals("null")) {
                statusSQL = statusSQL + " ACNT_STATUS = '" +
                            sAcntStatus.get(i).toString() + "' ";
            } else {
                statusSQL = statusSQL + " ACNT_STATUS is null ";
            }
        }

        return statusSQL;
    }

    private String getTypeSQL(List sAcntType) {
        String typeSQL = "";

        if (sAcntType == null) {
            return typeSQL;
        }

        for (int i = 0; i < sAcntType.size(); i++) {
            if (i != 0) {
                typeSQL = typeSQL + " OR ";
            }
            typeSQL = typeSQL + " ACNT_TYPE = '" +
                      sAcntType.get(i).toString() + "' ";
        }
        return typeSQL;
    }

    private ArrayList listbyLoginID(String userLoginName, List sAcntStatus) throws
        BusinessException {
        String sql = createSQLbyLoginID(userLoginName);

        sql = preUserHeaderSQL + " FROM ( " + sql + ") "
              + " WHERE  ACNT_INNER ='" + IDtoAccount.ACCOUNT_INNER_IN
              + "' AND RST = '" + STATUS_UNDEL + "'"
              + " AND (IS_TEMPLATE = '0' OR IS_TEMPLATE IS NULL) ";

        if (sAcntStatus != null) {
            sql = sql + " AND ( " + getStautsSQL(sAcntStatus) + " ) ";
        }

        sql = sql + preUserEndSQL;

        ArrayList arry = getAccountListBySQL(sql, GET_KIND_USER);

        return arry;

    }

    public List listbyAccountID(String accountid) throws
        BusinessException {
        List list = new ArrayList();
        String sql = "select * from PMS_ACNT where rst='" + Constant.RST_NORMAL +
                     "' and ACNT_ID='" + accountid + "'";
        try {
            list = this.getDbAccessor().executeQueryToList(sql, Account.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private ArrayList listAllUser(List sAcntStatus) throws
        BusinessException {

        String sql = preHeaderSQL + " FROM PMS_ACNT  WHERE  ACNT_INNER ='"
                     + IDtoAccount.ACCOUNT_INNER_IN
                     + "' AND RST = '" + STATUS_UNDEL + "'"
                     + " AND (IS_TEMPLATE = '0' OR IS_TEMPLATE IS NULL) ";
        if (sAcntStatus != null) {
            sql = sql + " AND ( " + getStautsSQL(sAcntStatus) + " ) ";
        }

        sql = sql + preUserEndSQL;

        ArrayList arry = getAccountListBySQL(sql, GET_KIND_ALL);

        return arry;

    }

    public ArrayList listAllUser(List sAcntStatus, List sAcntType) throws
        BusinessException {

        String sql = preHeaderSQL + " FROM PMS_ACNT  WHERE  ACNT_INNER ='"
                     + IDtoAccount.ACCOUNT_INNER_IN
                     + "' AND RST = '" + STATUS_UNDEL + "'"
                     + " AND (IS_TEMPLATE = '0' OR IS_TEMPLATE IS NULL) ";
        if (sAcntStatus != null) {
            sql = sql + " AND ( " + getStautsSQL(sAcntStatus) + " ) ";
        }
        if (sAcntType != null) {
            sql = sql + " AND ( " + getTypeSQL(sAcntType) + " ) ";
        }

        sql = sql + preUserEndSQL;

        ArrayList arry = getAccountListBySQL(sql, GET_KIND_ALL);

        return arry;

    }


    public ArrayList ListbyType(List sAcntType) throws
        BusinessException {

        String sql = preHeaderSQL + " FROM PMS_ACNT  WHERE  ACNT_INNER ='"
                     + IDtoAccount.ACCOUNT_INNER_IN
                     + "' AND RST = '" + STATUS_UNDEL + "'"
                     + " AND (IS_TEMPLATE = '0' OR IS_TEMPLATE IS NULL) ";
        if (sAcntType != null) {
            sql = sql + " AND ( " + getTypeSQL(sAcntType) + " ) ";
        }

        sql = sql + preUserEndSQL;

        ArrayList arry = getAccountListBySQL(sql, GET_KIND_ALL);

        return arry;

    }

    public ArrayList ListbyType(List sAcntType, Long Accountrid) throws
        BusinessException {

        String sql = preHeaderSQL + " FROM PMS_ACNT  WHERE  ACNT_INNER ='"
                     + IDtoAccount.ACCOUNT_INNER_IN
                     + "' AND RST = '" + STATUS_UNDEL + "'"
                     + " AND (IS_TEMPLATE = '0' OR IS_TEMPLATE IS NULL) ";
        if (sAcntType != null) {
            sql = sql + " AND ( " + getTypeSQL(sAcntType) + " ) ";
        }

        if (Accountrid != null) {
            sql = sql + " AND ( RID <>'" + Accountrid + "' ) ";
        }
        sql = sql + preUserEndSQL;

        ArrayList arry = getAccountListBySQL(sql, GET_KIND_ALL);

        return arry;

    }


    public List listAccountsByLoginID() throws BusinessException {
        DtoUser user = getUser();
        if (user == null) {
            throw new BusinessException("E_Acnt001", "User object is null!!!");
        }
        String loginId = user.getUserLoginId();

        return listAccountsByLoginID(loginId);
    }

    /**
     * 依据登录名获取登录用户的项目列表
     * 1.获取作为项目经理的项目e
     * 2.获取作为管理者的项目
     * 3.获取作为EBS所管理的项目
     * 4.获取作为参与者的项目
     * @param userLoginName String
     * @return ArrayList
     */
    public List listAccountsByLoginID(String userLoginName) throws
        BusinessException {

        ArrayList arry = listbyLoginID(userLoginName, lStatus);

        return arry;
    }

    private String getPMAccountSQL(String userLoginName) {
        String sql = " SELECT DISTINCT t.* , 1 AS PM , 0 AS MANAGER , 0 AS PR "
                     + " FROM PMS_ACNT t WHERE ACNT_MANAGER = '" +
                     userLoginName + "' ";
        return sql;
    }

    private String getManagerAccountSQL(String userLoginName) {
        String sql = "select DISTINCT a.*,0 AS PM , 1 AS MANAGER , 0 AS PR "
                     + " from ebs_ebs e , ebs_ebs9acnt ea , pms_acnt a "
                     +
                     " where e.rid = ea.ebs_rid and ea.acnt_rid = a.rid  and e.rid in ( "
                     + " select rid from ebs_ebs t "
                     + " start with t.ebs_manager = '" + userLoginName + "' "
                     + " connect by prior t.RID=t.EBS_parent_Rid  )";

        return sql;
    }

    private String getParticipantAccountSQL(String userLoginName) {
        String sql = "select distinct a.* , 0 AS PM , 0  AS MANAGER, 1 as PR "
                     + " from pms_labor_resources t  , pms_acnt a "
                     + " where t.login_id = '" + userLoginName +
                     "' and t.acnt_rid = a.rid ";

        return sql;
    }

    private ArrayList getAccountListBySQL(String sql, String sKind) throws
        BusinessException {
        ArrayList arry = new ArrayList();
        int i = 0;

        try {
            System.out.println("[LgAccountList]SQL: " + sql);
            RowSet rs = this.getDbAccessor().executeQuery(sql);

            chooseRowIndex = -1;

            Long customizeAccountRid = getCustomizeAccount();
            while (rs.next()) {
                DtoPmsAcnt acc = null;
                if (sKind.equals(GET_KIND_USER)) {
                    acc = copyAccountForUser(rs);
                } else {
                    acc = copyAccount(rs);
                }
                if (customizeAccountRid == null) {
                    if(i == 0) {
                        acc.setSelected(true);
                        chooseRowIndex = 0;
                        currAccount = acc;
                        setCustomizeAccount(acc.getRid());
                    }
                } else if (customizeAccountRid.equals(acc.getRid())) {
                    acc.setSelected(true);
                    chooseRowIndex = i;
                    currAccount = acc;
                }

                arry.add(acc);
                i++;
            }

            //如果currAccount不存在,则选择第一行
            if (chooseRowIndex == -1 && arry.size() > 1) {
                DtoPmsAcnt acc = (DtoPmsAcnt) arry.get(0);
                acc.setSelected(true);
                currAccount = acc;
                chooseRowIndex = 0;

                setCustomizeAccount(acc.getRid());
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

        return arry;
    }

    public int getChooseRowIndex() {
        return this.chooseRowIndex;
    }

    public IDtoAccount getChooseAccount() {
        return this.currAccount;
    }

    public Long getCustomizeAccount() {
        if (loginId == null) {
            return null;
        }

        try {
            SysCustomizePk pk = new SysCustomizePk();
            pk.setName(this.entryFunType);
            pk.setUserid(this.loginId);
            pk.setCondition(this.queryCondition);

            SysCustomize sysCustomize = (SysCustomize) getDbAccessor().
                                        getSession().get(SysCustomize.class,
                pk);
            if (sysCustomize != null) {
                return Long.valueOf(sysCustomize.getValue());
            }
        } catch (Exception ex) {
            return null;
        }

        return null;
    }

    public void setCustomizeAccount(Long accountRid) {
        if (loginId == null) {
            return;
        }

        try {
            SysCustomizePk pk = new SysCustomizePk();
            pk.setName(this.entryFunType);
            pk.setUserid(this.loginId);
            pk.setCondition(this.queryCondition);

            SysCustomize sysCustomize = (SysCustomize) getDbAccessor().
                                        getSession().get(SysCustomize.class,
                pk);

            if (sysCustomize != null) {
                sysCustomize.setValue(accountRid.toString());
                getDbAccessor().update(sysCustomize);
            } else {
                sysCustomize = new SysCustomize();
                sysCustomize.setPk(pk);
                sysCustomize.setValue(accountRid.toString());
                getDbAccessor().save(sysCustomize);
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    private DtoPmsAcnt copyAccount(RowSet rs) throws BusinessException {
        DtoPmsAcnt acc = new DtoPmsAcnt();
        try {
            acc.setRid(new Long(rs.getLong("RID")));
            acc.setName(rs.getString("ACNT_NAME"));
            acc.setId(rs.getString("ACNT_ID"));
            acc.setManager(rs.getString("ACNT_MANAGER"));
            acc.setStatus(rs.getString("ACNT_STATUS"));

            acc.setCurrency(rs.getString("ACNT_CURRENCY"));
            acc.setType(rs.getString("ACNT_TYPE"));
            acc.setOrganization(rs.getString("ACNT_ORGANIZATION"));

            acc.setAnticipatedStart(DtoUtil.getSqlDate(rs,
                "ACNT_ANTICIPATED_START"));
            acc.setAnticipatedFinish(DtoUtil.getSqlDate(rs,
                "ACNT_ANTICIPATED_FINISH"));
            acc.setPlannedStart(DtoUtil.getSqlDate(rs, "ACNT_PLANNED_START"));
            acc.setPlannedFinish(DtoUtil.getSqlDate(rs, "ACNT_PLANNED_FINISH"));
            acc.setActualStart(DtoUtil.getSqlDate(rs, "ACNT_ACTUAL_START"));
            acc.setActualFinish(DtoUtil.getSqlDate(rs, "ACNT_ACTUAL_FINISH"));
            acc.setBrief(rs.getString("ACNT_BRIEF"));
            acc.setInner(rs.getString("ACNT_INNER"));
            acc.setIsTemplate(Boolean.parseBoolean(rs.getString("IS_TEMPLATE")));
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return acc;

    }

    private DtoPmsAcnt copyAccountForUser(RowSet rs) throws BusinessException {
        DtoPmsAcnt acc = new DtoPmsAcnt();
        try {

            acc = copyAccount(rs);

            if (rs.getInt("PM") == 1) {
                acc.setPm(true);
            }

            if (rs.getInt("MANAGER") == 1) {
                acc.setManagement(true);
            }

            if (rs.getInt("PR") == 1) {
                acc.setParticipant(true);
            }

        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return acc;

    }

    public LgAccountList(String condition, String entryFunType) {
        this.queryCondition = condition;
        this.entryFunType = entryFunType;
        lStatus = new ArrayList();

        //1.初始默认状态
        lStatus.add(IDtoAccount.ACCOUNT_STATUS_INIT);
        lStatus.add(IDtoAccount.ACCOUNT_STATUS_APPROVED);

        //2.初始SQL
        createPreSQL();

        DtoUser user = getUser();
        if (user != null) {
            this.loginId = user.getUserLoginId();
        } else {
            loginId = null;
        }

    }

    public LgAccountList() {
        this("", DEFAULT_FUN_TYPE);
    }


    /**
     * 获取项目清单
     * list(PMS_ACNT), Where：
     * inner = "I" (内部项目)
     * && status = "[Initial/Apporved]" (正常状态)
     * && rst = "N" (记录状态为未删除)
     * OrderBy: id
     *
     * @return List*/
    public List listAccounts() {
        ArrayList arry = listAllUser(lStatus);
        return arry;
    }


    /**
     * 获取项目清单
     * list(PMS_ACNT), Where：
     * inner = "I" (内部项目)
     * && status = sAcntStatus
     * && rst = "N" (记录状态为未删除)
     * OrderBy: id
     *
     * @param sAcntStatus List 所需获取的状态清单
     * @return List*/
    public List listAccounts(List sAcntStatus) {
        ArrayList arry = listAllUser(sAcntStatus);
        return arry;
    }


    /**
     * list(PMS_ACNT), Where：
     * 获取全部的项目清单，获取条件是：
     * inner = "I" (内部项目)
     * && rst = "N" (记录状态为未删除)
     * OrderBy: id
     *
     * @return List*/
    public List listAllAccounts() {
        ArrayList arry = listAllUser(null);
        return arry;
    }


    /**
     * 获取项目清单
     * list(PMS_ACNT), Where：
     * inner = "I" (内部项目)
     * && status = sAcntStatus
     * && rst = "N" (记录状态为未删除)
     * OrderBy: id
     *
     * @param sAcntStatus List 所需获取的状态清单
     * @return List*/
    public List listAccountsByLoginID(String userLoginName, List sAcntStatus) {
        ArrayList arry = listbyLoginID(userLoginName, sAcntStatus);
        return arry;
    }

    /**
     * 根据登录Id,状态和类型查询项目列表
     * @param userLoginName String
     * @param sAcntStatus List
     * @param sAcntType List
     * @return List
     */
    public List listAccountByLoginID(String userLoginName, List sAcntStatus,
                                     List sAcntType) {
        String sql = createSQLbyLoginID(userLoginName);

        sql = preUserHeaderSQL + " FROM ( " + sql + ") "
              + " WHERE  ACNT_INNER ='" + IDtoAccount.ACCOUNT_INNER_IN
              + "' AND RST = '" + STATUS_UNDEL + "'"
              + " AND (IS_TEMPLATE = '0' OR IS_TEMPLATE IS NULL) ";

        if (sAcntStatus != null) {
            sql = sql + " AND ( " + getStautsSQL(sAcntStatus) + " ) ";
        }

        if (sAcntType != null) {
            sql = sql + " AND ( " + getTypeSQL(sAcntType) + " ) ";
        }
        sql = sql + preUserEndSQL;

        return getAccountListBySQL(sql, GET_KIND_USER);
    }

    /**
     * list(PMS_ACNT), Where：
     * 获取全部的项目清单，获取条件是：
     * inner = "I" (内部项目)
     * && rst = "N" (记录状态为未删除)
     * OrderBy: id
     *
     * @return List*/
    public List listAllAccountsByLoginID(String userLoginName) {
        ArrayList arry = listbyLoginID(userLoginName, null);
        return arry;
    }

    public ArrayList listEbsAcntByLoginID() {

        DtoUser user = getUser();
        if (user == null) {
            throw new BusinessException("E_Acnt001", "User object is null!!!");
        }
        String loginId = user.getUserLoginId();

        String sql = getManagerAccountSQL(loginId);

        sql = preUserHeaderSQL + " FROM ( " + sql + ") "
              + " WHERE  ACNT_INNER ='" + IDtoAccount.ACCOUNT_INNER_IN
              + "' AND RST = '" + STATUS_UNDEL + "'"
              + " AND (IS_TEMPLATE = '0' OR IS_TEMPLATE IS NULL)  ";
        sql = sql + preUserEndSQL;

        ArrayList arry = getAccountListBySQL(sql, GET_KIND_USER);

        return arry;

    }

    public List listEbsAcntByLoginID(String userLoginName, List sAcntStatus,
                                     List sAcntType) {
        String sql = getManagerAccountSQL(userLoginName);

        sql = preUserHeaderSQL + " FROM ( " + sql + ") "
              + " WHERE  ACNT_INNER ='" + IDtoAccount.ACCOUNT_INNER_IN
              + "' AND RST = '" + STATUS_UNDEL + "'"
              + " AND (IS_TEMPLATE = '0' OR IS_TEMPLATE IS NULL)  ";

        if (sAcntStatus != null) {
            sql = sql + " AND ( " + getStautsSQL(sAcntStatus) + " ) ";
        }

        if (sAcntType != null) {
            sql = sql + " AND ( " + getTypeSQL(sAcntType) + " ) ";
        }
        sql = sql + preUserEndSQL;
        return getAccountListBySQL(sql, GET_KIND_USER);
    }

    /**
     * 按状态和类型列出模板
     * @param statusList List
     * @param typeList List
     * @return List
     */
    public List listTemplate(List sAcntStatus, List sAcntType) {
        String sql = preHeaderSQL;

        sql = sql + " FROM PMS_ACNT "
              + " WHERE  (IS_TEMPLATE = '1' ) ";

        if (sAcntStatus != null) {
            sql = sql + " AND ( " + getStautsSQL(sAcntStatus) + " ) ";
        }

        if (sAcntType != null) {
            sql = sql + " AND ( " + getTypeSQL(sAcntType) + " ) ";
        }

        return getAccountListBySQL(sql, GET_KIND_ALL);
    }

    /**
     * 按状态列出osp
     * @param sAcntStatus List
     * @return List
     */
    public List listOSP(List sAcntStatus) {
       String sql = preHeaderSQL;

       sql = sql + " FROM PMS_ACNT "
             + " WHERE  (IS_TEMPLATE = '2' ) ";

       if (sAcntStatus != null) {
           sql = sql + " AND ( " + getStautsSQL(sAcntStatus) + " ) ";
       }
       List ospList = getAccountListBySQL(sql, GET_KIND_ALL);
       if(ospList != null){
           for(int oi = 0; oi < ospList.size(); oi++){
               DtoPmsAcnt dtoPmsAcnt =(DtoPmsAcnt)ospList.get(oi);
               dtoPmsAcnt.setIsOSP(true);
           }
       }
       return ospList;
   }
   /**
    * 查找状态为approved的OSP
    * @return DtoPmsAcnt
    */
   public DtoPmsAcnt getApprovedOSP(){
       List statusSearchList = new ArrayList();
       statusSearchList.add(IDtoAccount.ACCOUNT_STATUS_APPROVED);
       List resultList =  listOSP(statusSearchList);
       if(resultList == null || resultList.size() == 0 ){
           return null;
       }else{
           return (DtoPmsAcnt)resultList.get(0);
       }
   }

    public List listTemplate(List sAcntStatus, List sAcntType, Long Accountrid) {
        String sql = preHeaderSQL;

        sql = sql + " FROM PMS_ACNT "
              + " WHERE  (IS_TEMPLATE = '1' ) ";

        if (sAcntStatus != null) {
            sql = sql + " AND ( " + getStautsSQL(sAcntStatus) + " ) ";
        }

        if (sAcntType != null) {
            sql = sql + " AND ( " + getTypeSQL(sAcntType) + " ) ";
        }

        if (Accountrid != null) {
            sql = sql + " AND ( RID <>'" + Accountrid + "' ) ";
        }

        return getAccountListBySQL(sql, GET_KIND_ALL);
    }

    public String getLastTemplate(Long acntRid) {

        Long lastTemplateRid;
        LgAccountBase lg = new LgAccountBase();
        String projectName;

        try {
            Account account = lg.load(acntRid);
            lastTemplateRid = account.getImportTemplateRid();
            if (lastTemplateRid == null) {
                projectName = "";
            } else {
                Account accountLast = lg.load(lastTemplateRid);
                projectName = accountLast.getId() + "--" + accountLast.getName();
            }
            return projectName;

        } catch (Exception ex) {
            throw new BusinessException("PMS_ACC_001",
                                        "error while get last template!", ex);
        }
    }

    public static void main(String[] args) {
        LgAccountList lg = new LgAccountList();
        List result = lg.listTemplate(null, null);
    }
}
