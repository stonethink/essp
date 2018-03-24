package server.essp.tc.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import c2s.essp.tc.weeklyreport.DtoAllocateHour;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import com.wits.util.comDate;
import db.essp.tc.TcByWorkerAccount;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import net.sf.hibernate.Query;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.tc.common.LgOvertimeLeaveExtend;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import c2s.essp.tc.weeklyreport.IDtoAllocateHour;

public class LgWeeklyReportSum extends AbstractESSPLogic {

    IHrUtil hrUtil = HrFactory.create();
    protected LgWeeklyReportLock lgWeeklyReportLock  = new LgWeeklyReportLock();
    protected LgOvertimeLeaveExtend lgOvertimeLeave = new LgOvertimeLeaveExtend();
    protected LgAcntActivity lgAcntActivity = new LgAcntActivity();

    /**
     * 取得userId在period内的总工时
     * @param userId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return BigDecimal
     */
    public void getActualHourByUser(IDtoAllocateHour dto) {
        String userId = dto.getUserId();
        Date beginPeriod = dto.getBeginPeriod();
        Date endPeriod = dto.getEndPeriod();
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];
        try {
            List paramList = new ArrayList();
            List paramTypeList = new ArrayList();
            paramList.add( new java.sql.Timestamp( beginPeriod.getTime() ) );
            paramTypeList.add("date");
            paramList.add( new java.sql.Timestamp( endPeriod.getTime() ) );
            paramTypeList.add("date");

//            String sql = ("select sum(t.SAT_HOUR) as satHour, sum(t.SUN_HOUR) as sunHour, sum(t.MON_HOUR) as monHour, sum(t.TUE_HOUR) as tueHour, sum(t.WED_HOUR) as wedHour, sum(t.THU_HOUR) as thuHour, sum(t.FRI_HOUR) as friHour " +
//                          "from TC_WEEKLY_REPORT_SUM t where t.USER_ID ='" + userId + "'" +
//                          " and t.BEGIN_PERIOD >=? and t.END_PERIOD <=?");

            String sql = ("select (SAT_HOUR+SUN_HOUR+MON_HOUR+TUE_HOUR+WED_HOUR+THU_HOUR+FRI_HOUR) as sumHourOneWeek, CONFIRM_STATUS as confirmStatus " +
                                      "from TC_WEEKLY_REPORT_SUM t where t.USER_ID ='" + userId + "'" +
                          " and t.BEGIN_PERIOD >=? and t.END_PERIOD <=?");

            RowSet rset = getDbAccessor().executeQuery(sql, paramList, paramTypeList);
            double sumHour = 0;
            double sumHourConfirmed = 0;
            while (rset.next()) {
                double sumHourOneWeek = rset.getDouble("sumHourOneWeek");
                String confirmStatus = rset.getString("confirmStatus");

                sumHour += sumHourOneWeek;
                if( DtoWeeklyReport.STATUS_CONFIRMED.equals(confirmStatus) ){
                    sumHourConfirmed += sumHourOneWeek;
                }
            }

            dto.setActualHour(new BigDecimal(sumHour) );
            dto.setActualHourConfirmed(new BigDecimal(sumHourConfirmed) );
        }catch (Exception ex) {
            throw new BusinessException("E00000","Error when get the sum of timecard of user - " + userId ,ex);
        }
    }

    /**
     * 查找userId在项目acntRid & 在时间区间的weeklyReportSum
     * 如果时间区间为“周”，那么至多会查出一条数据
     * 如果时间区间为“月”，那么对月内的每个周都可能会查出一条数据
     * @param userId String
     * @param acntRid Long
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List getByUserAcnt(String userId, Long acntRid, Date beginPeriod, Date endPeriod) {
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];

        List weekrptList = new ArrayList();
        try {
            Query q = getDbAccessor().getSession().createQuery("from TcByWorkerAccount t where t.userId =:userId and t.acntRid =:acntRid"
                    + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod");
            q.setString("userId", userId);
            q.setLong("acntRid", acntRid.longValue());
            q.setDate("beginPeriod", beginPeriod);
            q.setDate("endPeriod", endPeriod);

            weekrptList = q.list();

            return weekrptList;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when select TcByWorkerAccount of user - " + userId, ex);
        }
    }

    /**
     * 查找userId在时间区间的weeklyReportSum
     * 如果时间区间为“周”，那么对user所在的每个项目都可能会查出一条数据
     * 如果时间区间为“月”，那么对月内的每个周 & 每个项目都可能会查出一条数据
     * @param userId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List getByUser(String userId, Date beginPeriod, Date endPeriod) {
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];
        List weekrptList = new ArrayList();
        try {
            Query q = getDbAccessor().getSession().createQuery("from TcByWorkerAccount t where t.userId =:userId"
                    + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod");
            q.setString("userId", userId);
            q.setDate("beginPeriod", beginPeriod);
            q.setDate("endPeriod", endPeriod);

            weekrptList = q.list();

            return weekrptList;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when select TcByWorkerAccount of user - " + userId, ex);
        }
    }

    public List getByUsers(String userStr, Date beginPeriod, Date endPeriod) {
        Date beginPeriod2 = LgTcCommon.resetBeginDate(beginPeriod);
        Date endPeriod2 = LgTcCommon.resetBeginDate(endPeriod);
//        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
//        beginPeriod = ds[0];
//        endPeriod = ds[1];
        List weekrptList = new ArrayList();
        try {
            Query q = getDbAccessor().getSession().createQuery("from TcByWorkerAccount t where t.userId in(" + userStr + ")"
                    + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod order by lower(t.userId)");
            q.setDate("beginPeriod", beginPeriod2);
            q.setDate("endPeriod", endPeriod2);

            weekrptList = q.list();

            return weekrptList;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when select TcByWorkerAccount of user - " + userStr, ex);
        }
    }


    /**
     * 查找项目acntRid在时间区间的weeklyReportSum
     * 如果时间区间为“周”，那么对项目下的每个user都可能会查出一条数据
     * 如果时间区间为“月”，那么对月内的每个周 & 每个user都可能会查出一条数据
     * @param acntRid Long
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List getByAcnt(Long acntRid, Date beginPeriod, Date endPeriod) {
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];
        try {
            Query q = getDbAccessor().getSession().createQuery("from TcByWorkerAccount t where t.acntRid =:acntRid"
                    + " and t.beginPeriod>=:beginPeriod and t.endPeriod<=:endPeriod order by lower(t.userId)"
                      );
            q.setLong("acntRid", acntRid.longValue());
            q.setDate("beginPeriod", beginPeriod);
            q.setDate("endPeriod", endPeriod);

            return q.list();
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when select TcByWorkerAccount of account - " + acntRid, ex);
        }
    }


    /**pm confirm a weeklyReport*/
    public String confirm(String userId, Long acntRid, Date beginPeriod, Date endPeriod, String confirmStatus) {
        String msg = null;

        List weeklyrptList = getByUserAcnt(userId, acntRid, beginPeriod, endPeriod);
        if (weeklyrptList.iterator().hasNext()) {
            TcByWorkerAccount db = (TcByWorkerAccount) weeklyrptList.iterator().next();

            if (DtoWeeklyReport.STATUS_REJECTED.equals(confirmStatus)) {
                //拒绝周报将会为该人解锁
                lgWeeklyReportLock.setLockOff(userId, beginPeriod, endPeriod);
            }

            if (DtoWeeklyReport.STATUS_CONFIRMED.equals(confirmStatus)) {
                //confirm周报将会为该人加锁
                lgWeeklyReportLock.setLockOn(userId, beginPeriod, endPeriod);
            }
            if (confirmStatus.equals(db.getConfirmStatus()) == false) {
                if( DtoWeeklyReport.STATUS_CONFIRMED.equals( db.getConfirmStatus() ) ){
                    //从"Confirmed"状态转为其他状态，要更新account/activity的时间
                    lgAcntActivity.updateByUserAcnt(userId, acntRid
                            ,beginPeriod , endPeriod, lgAcntActivity.SUBTRACT);

                }else{
                    if( DtoWeeklyReport.STATUS_CONFIRMED.equals( confirmStatus ) ){
                        //从其他状态转为"Confirmed"状态，要更新account/activity的时间
                        lgAcntActivity.updateByUserAcnt(userId, acntRid
                            ,beginPeriod , endPeriod, lgAcntActivity.ADD);
                    }
                }

                try {
                    db.setConfirmStatus(confirmStatus);
                    getDbAccessor().save(db);
                } catch (Exception ex) {
                    throw new BusinessException("E00000", "Error when " + confirmStatus + ".", ex);
                }
            }
        }

        if (DtoWeeklyReport.STATUS_CONFIRMED.equals(confirmStatus)) {
            msg = "Confirmed Ok.";
        } else if (DtoWeeklyReport.STATUS_REJECTED.equals(confirmStatus)) {
            msg = "Rejected Ok.";
        } else if (DtoWeeklyReport.STATUS_LOCK.equals(confirmStatus)) {
            msg = "Lock Ok.";
        }

        return msg;
    }


    /**
     * 返回结果为按“user”的weeklyReportSum
     * 参数dbList中有相同userId的数据将被合并，合并时：同一天的时间相加；
     * 适用：“周”内，求个人weeklyReportSum列表，含每天的时间信息
     * @param dbList List 其元素为weeklyReportSum
     * @return List
     */
    protected List dbList2DtoListOnWeek(List dbList, Date beginOfWeek, Date endOfWeek) {
        List dtoList = new ArrayList();

        Map dtoMap = new HashMap();

        for (Iterator iter = dbList.iterator(); iter.hasNext(); ) {
            TcByWorkerAccount tc = (TcByWorkerAccount) iter.next();

            String userId = tc.getUserId();
            DtoHoursOnWeek dto = (DtoHoursOnWeek) dtoMap.get(userId);
            if (dto == null) {
                dto = createDtoHoursOnWeek(tc, beginOfWeek, endOfWeek);

                dtoMap.put(userId, dto);
                dtoList.add(dto);
            }

            mergeDtoAndDbOnWeek(dto, tc, beginOfWeek, endOfWeek);
        }
        return dtoList;
    }

    /**
     * 返回结果为按“user”的weeklyReportSum
     * 参数dbList中有相同userId的数据将被合并，合并时：所有的时间相加；；status综合
     * 适用：“月”内，求个人weeklyReportSum列表，不含每天的时间信息，只有总时间
     * @param dbList List 其元素为weeklyReportSum
     * @return List
     */
    protected List dbList2DtoListOnMonth(List dbList, Date beginOfMonth, Date endOfMonth){
        List dtoList = new ArrayList();

        Map dtoMap = new HashMap();

        for (Iterator iter = dbList.iterator(); iter.hasNext(); ) {
            TcByWorkerAccount tc = (TcByWorkerAccount) iter.next();

            String userId = tc.getUserId();
            DtoAllocateHour dto = (DtoAllocateHour) dtoMap.get(userId);
            if (dto == null) {
                dto = createDtoAllocateHour(tc, beginOfMonth, endOfMonth);

                dtoMap.put(userId, dto);
                dtoList.add(dto);
            }

            mergeDtoAndDbOnMonth(dto, tc, beginOfMonth, endOfMonth);
        }

        return dtoList;
    }

    protected DtoHoursOnWeek createDtoHoursOnWeek(TcByWorkerAccount db,Date beginOfWeek,Date endOfWeek){
        DtoHoursOnWeek dto = new DtoHoursOnWeek();
        dto.setUserId(db.getUserId());
        dto.setBeginPeriod(db.getBeginPeriod());
        dto.setEndPeriod(db.getEndPeriod());
        return dto;
    }

    protected DtoAllocateHour createDtoAllocateHour(TcByWorkerAccount db, Date beginPeriod, Date endPeriod){
        DtoAllocateHour dto = new DtoAllocateHour();
        dto.setUserId(db.getUserId());
        dto.setBeginPeriod(db.getBeginPeriod());
        dto.setEndPeriod(db.getEndPeriod());
        dto.setActualHour(new BigDecimal(0));
        dto.setActualHourConfirmed(new BigDecimal(0));
        return dto;
    }

    protected void mergeDtoAndDbOnWeek(DtoHoursOnWeek dto, TcByWorkerAccount db, Date beginOfWeek, Date endOfWeek){
        //计算beginPeriod和endPeriod
        if (comDate.compareDate(dto.getBeginPeriod(), db.getBeginPeriod()) > 0) {
            dto.setBeginPeriod(db.getBeginPeriod());
        }

        if (comDate.compareDate(dto.getEndPeriod(), db.getEndPeriod()) < 0) {
            dto.setEndPeriod(db.getEndPeriod());
        }

        //将同一天的时间汇总
        BigDecimal hours[] = new BigDecimal[]{
                             db.getSatHour(), db.getSunHour(), db.getMonHour(),
                             db.getTueHour(), db.getWedHour(), db.getThuHour(), db.getFriHour()
        };

        for (int i = DtoHoursOnWeek.SATURDAY; i <= DtoHoursOnWeek.FRIDAY; i++) {
            dto.add(i, hours[i]);
            dto.add(DtoHoursOnWeek.SUMMARY, hours[i]);
        }
    }

    protected void mergeDtoAndDbOnMonth(DtoAllocateHour dto, TcByWorkerAccount db, Date beginOfMonth, Date endOfMonth){
        //计算beginPeriod和endPeriod
        if (comDate.compareDate(dto.getBeginPeriod(), db.getBeginPeriod()) > 0) {
            dto.setBeginPeriod(db.getBeginPeriod());
        }

        if (comDate.compareDate(dto.getEndPeriod(), db.getEndPeriod()) < 0) {
            dto.setEndPeriod(db.getEndPeriod());
        }

        //将所有的时间汇总
        BigDecimal sum = getSumHour(db);

        dto.setActualHour(dto.getActualHour().add(sum));
    }

    protected BigDecimal getSumHour(TcByWorkerAccount db) {
        BigDecimal sumHour = new BigDecimal(0);
        BigDecimal hours[] = new BigDecimal[] {
                             db.getSatHour(), db.getSunHour(), db.getMonHour(),
                             db.getTueHour(), db.getWedHour(), db.getThuHour(), db.getFriHour()
        };

        for (int i = DtoHoursOnWeek.SATURDAY; i <= DtoHoursOnWeek.FRIDAY; i++) {
            if( hours[i] != null ){
                sumHour = sumHour.add(hours[i]);
            }
        }
        return sumHour;
    }

    public static void main(String args[]) {
        try {
            LgWeeklyReportSum logic = new LgWeeklyReportSum();
            HBComAccess hbComAccess = new HBComAccess();
            logic.setDbAccessor(hbComAccess);

            Calendar c = Calendar.getInstance();
//            c.setTime(new Date());
            c.set(2006,1,26);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            Date b = c.getTime();
            //List list = logic.getByAcnt(new Long(1), c.getTime(), c.getTime());
            //System.out.println(list.size());
            Calendar c2 = Calendar.getInstance();
            c2.set(2006,2,25);
            c2.set(Calendar.HOUR_OF_DAY, 0);
            c2.set(Calendar.MINUTE, 0);
            c2.set(Calendar.SECOND, 0);
            c2.set(Calendar.MILLISECOND, 0);
            Date e = c2.getTime();

            logic.getByUsers("'RongXiao'",b,e);
//            System.out.print(dto.getActualHour());
//            System.out.print(dto.getActualHourConfirmed());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
