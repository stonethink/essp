package server.essp.tc.search.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.RowSet;

import c2s.essp.common.account.IDtoAccount;
import c2s.essp.tc.weeklyreport.DtoTcSearchResult;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import itf.hr.LgHrUtilImpl;

public class LgTcSearchByHrAcnt extends AbstractBusinessLogic {
     /** 用户登录表*/
    public static final String LOGIN_TABLE = LgHrUtilImpl.LOGIN_TABLE;

    final static String HR_ACCOUNT_SCOPE_TABLE = "essp_hr_account_scope_t";

    IAccountUtil acntUtil = AccountFactory.create();

    public List search(Long acntRid, Calendar date) {
        System.out.println("search by: ["+acntRid+"], at [" +(date.getTime()).toString()+ "]");

        List result = new ArrayList();
        if (acntRid == null || date == null ) {
            return result;
        }

        List weekList = LgTcSearchCommon.getWeekList(date.getTime()
                                                     , date.getTime());
        System.out.println("weekList: " + weekList.size());
        Iterator iterWeek = weekList.iterator();
        if (iterWeek.hasNext()) {
            Date[] weekPeriod = (Date[]) iterWeek.next();
            Date beginOfWeek = weekPeriod[0];
            Date endOfWeek = weekPeriod[1];

            long t = endOfWeek.getTime() - date.getTimeInMillis();
            t = t>0?t:-t;
            if (t <= 0 || t >= 24 * 3600 * 1000) {

                //还没到周末
                System.out.println("Today is not the end of week[" +endOfWeek.toString()+ " -- " +beginOfWeek+ "].");
                return result;
            }

            List noneList = searchUserWithoutWeeklyReportOnWeek(acntRid,
                    beginOfWeek, endOfWeek);
            result.addAll(noneList);
        }

        result = LgTcSearchCommon.merge(result);
        LgTcSearchCommon.sort(result);
        return result;
    }


    /**查询acntRid中没有填写周报的人（包括周报被拒的人）*/
    private List searchUserWithoutWeeklyReportOnWeek(Long acntRid, Date beginOfWeek, Date endOfWeek) {
        List result = new ArrayList();

        List userList = getUserListInHrAcnt(acntRid);

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

        return result;
    }

    private DtoTcSearchResult createDto(String userId, Long acntRid, Date beginOfWeek, Date endOfWeek, String status) {
        DtoTcSearchResult dto = new DtoTcSearchResult();
        dto.setUser(userId);
        dto.setBeginPeriod(beginOfWeek);
        dto.setEndPeriod(endOfWeek);
        dto.setStatus(status);

        IDtoAccount acnt = acntUtil.getAccountByRID(acntRid);
        if (acnt != null) {
            dto.setAcntName(acnt.getName());
            dto.setManager(acnt.getManager());
        }

        return dto;
    }

    /**acntRid为hr性质的项目，这里列出其下所属的人*/
    private List getUserListInHrAcnt(Long acntRid) {
        List userList = new ArrayList();

        String sql = "select login.login_id as loginid " +
                     "from " + LOGIN_TABLE + " login " +
                     "left join " + HR_ACCOUNT_SCOPE_TABLE + " acntScope on login.user_id = acntScope.SCOPE_CODE " +
                     "where acntScope.ACCOUNT_ID = " + acntRid.longValue() + " order by lower(loginid)";

        try {
            RowSet rset = getDbAccessor().executeQuery(sql);
            while (rset.next()) {
                String loginId = rset.getString("loginid");
                userList.add(loginId);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when get user in account - " + acntRid, ex);
        }

        return userList;
    }


}
