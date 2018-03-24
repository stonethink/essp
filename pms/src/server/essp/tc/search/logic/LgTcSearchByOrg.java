package server.essp.tc.search.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.RowSet;

import c2s.essp.common.account.IDtoAccount;
import c2s.essp.tc.weeklyreport.DtoTcSearchCondition;
import c2s.essp.tc.weeklyreport.DtoTcSearchResult;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import db.essp.tc.TcByWorkerAccount;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import itf.hr.IHrUtil;
import itf.hr.HrFactory;
import itf.orgnization.IOrgnizationUtil;
import itf.orgnization.OrgnizationFactory;
import server.essp.tc.common.LgTcCommon;
import itf.hr.LgHrUtilImpl;

public class LgTcSearchByOrg extends AbstractBusinessLogic {
    /**人力资源表*/
    public static final String HR_TABLE = "ESSP_HR_EMPLOYEE_MAIN_T";
    /** 用户登录表*/
    public static final String LOGIN_TABLE = LgHrUtilImpl.LOGIN_TABLE;
    /**组织结构表*/
    public static final String ORG_TABLE = "ESSP_UPMS_UNIT";
    /**组织结构人员分配表*/
    public static final String ORG_USER_TABLE = "ESSP_UPMS_UNIT_USER_T";

    public static final String ACCOUNT_TABLE = "PMS_ACNT";

    IAccountUtil acntUtil = AccountFactory.create();
    IOrgnizationUtil orgUtil = OrgnizationFactory.create();

    public List search(DtoTcSearchCondition dtoCondition) {
        List result = new ArrayList();
        if (dtoCondition.getOrgId() == null
            || dtoCondition.getBeginPeriod() == null
            || dtoCondition.getEndPeriod() == null
                ) {
            return result;
        }
//        Date[] ds = LgTcCommon.resetBeginDate(dtoCondition.getBeginPeriod(),dtoCondition.getEndPeriod());
//        dtoCondition.setBeginPeriod( ds[0] );
//        dtoCondition.setEndPeriod( ds[1] );

        boolean searchNone = Boolean.valueOf( dtoCondition.getNoneStatus() ).booleanValue();
        String[] status = LgTcSearchCommon.getStatus(dtoCondition);
        String orgId = dtoCondition.getOrgId();
        String orgStr;
        if( dtoCondition.isIncSub() ){
            orgStr = orgUtil.getSubOrgs(orgId);
        }else{
            orgStr = "'" + orgId + "'";
        }

        List weekList = LgTcSearchCommon.getWeekList(dtoCondition.getBeginPeriod()
                                                     , dtoCondition.getEndPeriod());
        for (Iterator iterWeek = weekList.iterator(); iterWeek.hasNext(); ) {
            Date[] weekPeriod = (Date[]) iterWeek.next();
            Date beginOfWeek = weekPeriod[0];
            Date endOfWeek = weekPeriod[1];

            List userList = null;
            if (searchNone) {
                userList = orgUtil.getUserListInOrgs(orgStr);
                List noneList = searchUserWithoutWeeklyReportOnWeek(userList,
                                                                    beginOfWeek, endOfWeek);
                result.addAll(noneList);
            }

            if (status.length > 0) {
                if( userList == null ){
                    userList = orgUtil.getUserListInOrgs(orgStr);
                }
                String userStr = orgUtil.getUserStrInOrgs(userList);
                List hasList = searchOnWeek(userStr, beginOfWeek,
                                             endOfWeek, status);
                result.addAll(hasList);
            }
        }

        result=LgTcSearchCommon.merge(result);
        LgTcSearchCommon.sort(result);
        return result;
    }

    /**查询userStr中在时间段begin ~ end内填写了周报的人，周报的状态为status*/
    private List searchOnWeek(String userStr, Date beginOfWeek, Date endOfWeek, String[] status) {
        List weeklyReport = new ArrayList();

        String sql = "select t from TcByWorkerAccount t where t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod"
                     + " and t.userId in(" + userStr + ")";

        StringBuffer statusStr = new StringBuffer("''");
        for (int i = 0; i < status.length; i++) {
            statusStr.append(",'");
            statusStr.append(status[i]);
            statusStr.append("'");
        }

        if (statusStr.length() > 2) {
            sql += " and t.confirmStatus in (" + statusStr + ")";
        }

        sql += " order by t.userId, t.beginPeriod";

        try {
            Iterator iter = getDbAccessor().getSession().createQuery(sql)
                            .setDate("beginPeriod", beginOfWeek)
                            .setDate("endPeriod", endOfWeek)
                            .iterate();
            for (; iter.hasNext(); ) {
                TcByWorkerAccount db = (TcByWorkerAccount) iter.next();

                DtoTcSearchResult dto = createDto(db.getUserId()
                                                  , db.getAcntRid()
                                                  , db.getBeginPeriod()
                                                  , db.getEndPeriod()
                                                  , db.getConfirmStatus());

                weeklyReport.add(dto);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when select weekly report.", ex);
        }

        return weeklyReport;
    }

    /**查询userList中没有填写周报的人*/
    private List searchUserWithoutWeeklyReportOnWeek(List userList, Date beginOfWeek, Date endOfWeek) {
        List result = new ArrayList();

        try {
            for (Iterator iterUser = userList.iterator(); iterUser.hasNext(); ) {
                String userId = (String) iterUser.next();
                if(userId == null || "".equals(userId.trim()))
                    continue;
                String sql = " from TcByWorkerAccount t where t.userId =:userId"
                             + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod";
                int num = getDbAccessor().getSession().createQuery(sql)
                          .setString("userId", userId)
                          .setDate("beginPeriod", beginOfWeek)
                          .setDate("endPeriod", endOfWeek)
                          .list().size();

                if (num == 0) {
                    DtoTcSearchResult dto = createDto(
                            userId, null, beginOfWeek, endOfWeek
                            , DtoWeeklyReport.STATUS_NONE);

                    result.add(dto);
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when select user who does not have weekly report.", ex);
        }

        String userStr = orgUtil.getUserStrInOrgs(userList);
        List rejectList = searchOnWeek(userStr, beginOfWeek, endOfWeek
                                       , new String[] {DtoWeeklyReport.STATUS_REJECTED});
        result.addAll(rejectList);
        return result;
    }

    private DtoTcSearchResult createDto(String userId, Long acntRid, Date beginOfWeek, Date endOfWeek, String status) {
        DtoTcSearchResult dto = new DtoTcSearchResult();
        dto.setUser(userId);
        dto.setBeginPeriod(beginOfWeek);
        dto.setEndPeriod(endOfWeek);
        dto.setStatus(status);

        if(acntRid != null){
            IDtoAccount acnt = acntUtil.getAccountByRID(acntRid);
            if (acnt != null) {
                dto.setAcntName(acnt.getName());
                dto.setManager(acnt.getManager());
            }
        }

        return dto;
    }


//    private String getSubOrgs(String orgId) throws BusinessException {
//        try {
//            List orgList = new ArrayList();
//            //get orgnazition and its children
//            StringBuffer orgSb = new StringBuffer("''");
//            String sqlSelOrgIncSub =
//                    "select UNIT_ID "
//                    + " from " + ORG_TABLE
//                    + " start with UNIT_ID='" + orgId + "'"
//                    + " connect by prior UNIT_ID=PARENT_ID";
//            RowSet orgRowSet = this.getDbAccessor().executeQuery(sqlSelOrgIncSub);
//            while (orgRowSet.next()) {
//                String unitId = orgRowSet.getString("UNIT_ID");
//                if (orgList.contains(unitId) == false) {
//                    orgSb.append(",");
//                    orgSb.append("'");
//                    orgSb.append(orgRowSet.getString("UNIT_ID"));
//                    orgSb.append("'");
//
//                    orgList.add(unitId);
//                }
//            }
//            String orgStr = orgSb.toString();
//            return orgStr;
//        } catch (BusinessException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            throw new BusinessException("E00", "Error when get the user in orgnazition - " + orgId + " including sub one.", ex);
//        }
//    }
//
//    private List getUserListInOrgs(String orgStr) throws BusinessException {
//        try {
//            String sql = "select login.loginname as loginid " +
//                         "from " + LOGIN_TABLE + " login " +
//                         "left join " + ORG_USER_TABLE + " org_user on login.user_id = org_user.user_id " +
//                         "where org_user.unit_id in (" + orgStr + ")";
//            log.info(sql);
//            log.info(orgStr);
//            RowSet rset = this.getDbAccessor().executeQuery(sql);
//            List userList = new ArrayList();
//            while (rset.next()) {
//                String userId = rset.getString("loginid");
//                if (userList.contains(userId) == false) {
//
//                    userList.add(userId);
//                }
//            }
//
//            return userList;
//        } catch (Exception ex) {
//            throw new BusinessException("E000", "Error when get the user of orgnazition - " + orgStr, ex);
//        }
//    }
//
//    private String getUserStrInOrgs(List userList) throws BusinessException {
//        StringBuffer userStr = new StringBuffer("''");
//
//        for (Iterator iter = userList.iterator(); iter.hasNext(); ) {
//            String userId = (String) iter.next();
//            userStr.append(",");
//            userStr.append("'");
//            userStr.append(userId);
//            userStr.append("'");
//        }
//
//        return userStr.toString();
//    }

}
