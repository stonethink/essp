package server.essp.tc.search.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;
import c2s.essp.tc.weeklyreport.DtoTcSearchCondition;
import c2s.essp.tc.weeklyreport.DtoTcSearchResult;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import db.essp.tc.TcByWorkerAccount;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import server.essp.tc.common.LgTcCommon;
import itf.hr.LgHrUtilImpl;

public class LgTcSearchByAcnt extends AbstractBusinessLogic {
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

    public List search(DtoTcSearchCondition dtoCondition) {
        List result = new ArrayList();
        if (dtoCondition.getAcntRid() == null
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
        Long acntRid = dtoCondition.getAcntRid();

        List weekList = LgTcSearchCommon.getWeekList(dtoCondition.getBeginPeriod()
                                                     , dtoCondition.getEndPeriod());
        for (Iterator iterWeek = weekList.iterator(); iterWeek.hasNext(); ) {
            Date[] weekPeriod = (Date[]) iterWeek.next();
            Date beginOfWeek = weekPeriod[0];
            Date endOfWeek = weekPeriod[1];

            if (searchNone) {
                List noneList = searchUserWithoutWeeklyReportOnWeek(acntRid,
                                                                    beginOfWeek, endOfWeek);
                result.addAll(noneList);
            }

            if (status.length > 0) {
                List userList = searchOnWeek(acntRid, beginOfWeek,
                                             endOfWeek, status);
                result.addAll(userList);
            }
        }

        result=LgTcSearchCommon.merge(result);
        LgTcSearchCommon.sort(result);
        return result;
    }


    /**查询项目中在时间段begin ~ end内的周报，周报的状态为status*/
    private List searchOnWeek(Long acntRid, Date beginOfWeek, Date endOfWeek, String[] status) {
        List weeklyReport = new ArrayList();

        String sql = "select t from TcByWorkerAccount t where t.acntRid =:acntRid"
                     + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod";

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
                            .setLong("acntRid", acntRid.longValue())
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

    /**查询acntRid中没有填写周报的人（包括周报被拒的人）*/
    private List searchUserWithoutWeeklyReportOnWeek(Long acntRid, Date beginOfWeek, Date endOfWeek) {
        List result = new ArrayList();

        List userList = getUserInAcnt(acntRid);

        try {
            for (Iterator iterUser = userList.iterator(); iterUser.hasNext(); ) {
                String userId = (String) iterUser.next();
                String sql = " from TcByWorkerAccount t where t.acntRid =:acntRid"
                             + " and t.userId =:userId"
                             + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod";
                int num = getDbAccessor().getSession().createQuery(sql)
                          .setLong("acntRid", acntRid.longValue())
                          .setString("userId", userId)
                          .setDate("beginPeriod", beginOfWeek)
                          .setDate("endPeriod", endOfWeek)
                          .list().size();

                if (num == 0) {
                    DtoTcSearchResult dto = createDto(
                            userId, acntRid, beginOfWeek, endOfWeek
                            , DtoWeeklyReport.STATUS_NONE);

                    result.add(dto);
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when select user who does not have weekly report.", ex);
        }

        //
        List rejectList = searchOnWeek(acntRid, beginOfWeek, endOfWeek
                                       , new String[]{DtoWeeklyReport.STATUS_REJECTED} );
        result.addAll(rejectList);
        return result;
    }

    private DtoTcSearchResult createDto(String userId, Long acntRid, Date beginOfWeek, Date endOfWeek, String status) {
        DtoTcSearchResult dto = new DtoTcSearchResult();
        dto.setUser(userId);
        dto.setBeginPeriod(beginOfWeek);
        dto.setEndPeriod(endOfWeek);
        dto.setStatus(status);

        IDtoAccount acnt = acntUtil.getAccountByRID(acntRid);
        if( acnt != null ){
            dto.setAcntName(acnt.getName());
            dto.setManager(acnt.getManager());
        }

        return dto;
    }

    private List getUserInAcnt(Long acntRid) {
        List users = new ArrayList();
        IAccountUtil acntUtil = AccountFactory.create();
        List laborResList = acntUtil.listLaborResourceByAcntRid(acntRid);
        for (Iterator itUser = laborResList.iterator(); itUser.hasNext(); ) {
            DtoUser laborRes = (DtoUser) itUser.next();
            users.add(laborRes.getUserLoginId());
        }

        return users;
    }

}
