package server.essp.tc.weeklyreport.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import c2s.essp.common.account.IDtoAccount;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import db.essp.pms.Activity;
import db.essp.pms.ActivityPK;
import db.essp.tc.TcWeeklyReport;
import essp.tables.PwWkitem;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import server.essp.tc.common.LgWeeklyReport;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import server.framework.hibernate.HBComAccess;
import server.essp.tc.common.LgTcCommon;
import com.wits.util.comDate;
import com.wits.util.StringUtil;

public class LgWeeklyReportByWorker extends LgWeeklyReport {
    private String loginId = null;

    public LgWeeklyReportByWorker() {
        DtoUser user = getUser();
        if (user != null) {
            loginId = user.getUserLoginId();
            log.info("loginId in session: " + loginId);
        } else {
//            loginId = "stone.shi";
//            log.info( "loginId for test: " + loginId );
            //throw new BusinessException("E000", "Please login first.");
        }
    }

    public List initFromDailyreport(Date beginPeriod, Date endPeriod, List weeklyReportList) throws BusinessException {
        List datList = new ArrayList();

        Map dataMap = new HashMap();
        List exclusiveData = new ArrayList();

        Map acntMap = new HashMap();
        Map activityMap = new HashMap();
        IAccountUtil acntUtil = AccountFactory.create();

        //参数weeklyReportList中已有的account的数据将不再从dailyreport中取
        for (Iterator iter = weeklyReportList.iterator(); iter.hasNext(); ) {
            DtoWeeklyReport item = (DtoWeeklyReport) iter.next();
            Long acntRid = item.getAcntRid();
            if (acntRid != null) {
                exclusiveData.add(acntRid);
            }
        }

        try {
            Date resetBeginPeriod = LgTcCommon.resetBeginDate(beginPeriod);
            Calendar cal = Calendar.getInstance();
            cal.setTime(endPeriod);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 1000);
//            Date resetEndPeriod = LgTcCommon.resetEndDate(endPeriod);
            Date resetEndPeriod = cal.getTime();

            String sql = "from PwWkitem t where t.wkitemDate >= :beginPeriod and t.wkitemDate <= :endPeriod "
                         + " and t.wkitemOwner =:wkitemOwner and t.wkitemIsdlrpt = '1' "
                         + " order by t.wkitemDate"
                         ;
            List list = getDbAccessor().getSession().createQuery(sql)
                            .setDate("beginPeriod", resetBeginPeriod)
                            .setDate("endPeriod", resetEndPeriod)
                            .setString("wkitemOwner", this.loginId)
                            .list();
            Iterator iter = list.iterator();
            for (; iter.hasNext(); ) {
                PwWkitem db = (PwWkitem) iter.next();

                if (exclusiveData.contains(db.getProjectId()) == true) {
                    continue;
                }

                String key = getTempKey(db.getProjectId(), db.getActivityId());
                DtoWeeklyReport dto = (DtoWeeklyReport) dataMap.get(key);
                if (dto == null) {
                    dto = new DtoWeeklyReport();
                    dto.setAcntRid(db.getProjectId());
                    dto.setActivityRid(db.getActivityId());
                    dto.setBeginPeriod(beginPeriod);
                    dto.setEndPeriod(endPeriod);
                    dto.setUserId(loginId);

                    //account name
                    if (db.getProjectId() != null) {
                        IDtoAccount acnt = (IDtoAccount) acntMap.get(db.getProjectId());
                        if (acnt == null) {
                            acnt = acntUtil.getAccountByRID(db.getProjectId());
                            if (acnt != null) {
                                acntMap.put(db.getProjectId(), acnt);
                                dto.setAcntName(acnt.getId() + " -- " + acnt.getName());
                            }
                        } else {
                            dto.setAcntName(acnt.getId() + " -- " + acnt.getName());
                        }
                    }

                    //activity name
                    if (db.getActivityId() != null) {
                        ActivityPK pk = new ActivityPK(db.getProjectId(), db.getActivityId());
                        Activity activity = (Activity) activityMap.get(pk);
                        if (activity == null) {
                            activity = (Activity) getDbAccessor().getSession().get(Activity.class, pk);
                            if (activity != null) {
                                activityMap.put(activity.getPk(), activity);
                                dto.setActivityName(activity.getCode() + " -- " + activity.getName());
                            }
                        } else {
                            dto.setActivityName(activity.getCode() + " -- " + activity.getName());
                        }
                    }

                    dataMap.put(key, dto);
                    datList.add(dto);
                }
                Calendar c = Calendar.getInstance();
                c.setTime(db.getWkitemDate());
                int day = c.get(Calendar.DAY_OF_WEEK) % 7;
                BigDecimal hour = (BigDecimal) dto.getHour(day);
                if (hour == null) {
                    hour = new BigDecimal(0);
                }
                if (db.getWkitemWkhours() != null) {
                    hour = hour.add(db.getWkitemWkhours());
                }
                dto.setHour(day, hour);
                //把日报信息放入周报JobDescritpion中
                String wpStr = (db.getWpId() == null) ? "" : ", " + db.getWkitemBelongto();
                String desc = StringUtil.nvl(dto.getJobDescription()) + "\n"
                              + db.getWkitemName()
                              + " [" + comDate.dateToString(db.getWkitemDate()) + wpStr + "]";
                dto.setJobDescription(desc);
            }

            return datList;
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when init weekly report.", ex);
        }
    }

    public List copyLastPeriod(Date beginDate, Date endDate, List weeklyReportList) {
        List dataList = new ArrayList();
        Map dataMap = new HashMap();
        List exclusiveData = new ArrayList();

        Calendar beginC = Calendar.getInstance();
        Calendar endC = Calendar.getInstance();
        beginC.setTime(beginDate);
        endC.setTime(endDate);

        //求区间所含的日期
        boolean periodBitmap[] = getPeriodBitmap(beginC, endC);

        //将[beginDate , endDate]区间展开为一个星期的区间
        getWeekPeriod(beginC, endC);
        Date extendBeginDate = beginC.getTime();
        Date extendEndDate = endC.getTime();
        Date[] ds = LgTcCommon.resetBeginDate(extendBeginDate,extendEndDate);
        extendBeginDate = ds[0];
        extendEndDate = ds[1];

        //参数weeklyReportList中已有的account的数据将不再从上个星期copy
        for (Iterator iter = weeklyReportList.iterator(); iter.hasNext(); ) {
            DtoWeeklyReport item = (DtoWeeklyReport) iter.next();
            Long acntRid = item.getAcntRid();
            if (acntRid != null) {
                exclusiveData.add(acntRid);
            }
        }

        try {
            String sql = "from TcWeeklyReport t where t.userId =:userId"
                         + " and t.beginPeriod >= :beginPeriod "
                         + " and t.endPeriod <= :endPeriod"
                         + " order by NVL(t.acntRid,0) asc, NVL(t.activityRid,0) asc, NVL(t.satHour,0) desc, NVL(t.sunHour,0) desc, NVL(t.monHour,0) desc,NVL(t.tueHour,0) desc, NVL(t.wedHour,0) desc, NVL(t.thuHour,0) desc, NVL(t.friHour,0) desc "
                         ;
            Iterator it = getDbAccessor().getSession().createQuery(sql)
                          .setString("userId", loginId)
                          .setDate("beginPeriod", extendBeginDate)
                          .setDate("endPeriod", extendEndDate)
                          .iterate();
            while (it.hasNext()) {
                TcWeeklyReport db = (TcWeeklyReport) it.next();

                if (exclusiveData.contains(db.getAcntRid()) == true) {
                    continue;
                }

                DtoWeeklyReport dto = null;
                String key = getTempKey(db);
                if (key != null) {
                    dto = (DtoWeeklyReport) dataMap.get(key);
                }
                if (dto == null) {
                    dto = new DtoWeeklyReport();
                    dto.setAcntRid(db.getAcntRid());
                    dto.setActivityRid(db.getActivityRid());
                    dto.setCodeValueRid(db.getCodeValueRid());
                    dto.setBeginPeriod(beginDate);
                    dto.setEndPeriod(endDate);
                    dto.setIsActivity(db.getIsActivity());
                    dto.setUserId(db.getUserId());
                    dto.setComments(db.getComments());
                    dto.setJobDescription(db.getJobDescription());

                    if (db.getAccount() != null) {
                        dto.setAcntName(db.getAccount().getId() + "--" + db.getAccount().getName());
                    }
                    if (dto.doesActivity()) {
                        if (db.getActivity() != null) {
                            dto.setActivityName(db.getAccount().getId() + "--" + db.getActivity().getName());
                        }
                    } else {
                        if (db.getCodeValue() != null) {
                            dto.setCodeValueName(db.getCodeValue().getValue());
                        }
                    }

                    dataMap.put(key, dto);
                    dataList.add(dto);
                }

                //设置时间.只有在period内的日期才有工作时间
                for (int i = 0; i < periodBitmap.length; i++) {
                    if (periodBitmap[i] == true) {
                        BigDecimal hour = getHourFromDb(db, i);
                        if (hour != null) {
                            if (dto.getHour(i) != null) {
                                hour = hour.add(dto.getHour(i));
                            }
                        }
                        dto.setHour(i, hour);
                    } else {
                        dto.setHour(i, null);
                    }
                }
            }

            return dataList;
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when copy weekly report.", ex);
        }
    }

    //求出区间beginPeriod——endPeriod之间的工作日
    public boolean[] getWorkDayBitmap(Date beginPeriod, Date endPeriod) {
        boolean[] bitmap = new boolean[7];
        for (int i = 0; i < bitmap.length; i++) {
            bitmap[i] = false;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(endPeriod);
        c.setFirstDayOfWeek(Calendar.SATURDAY);
        int endDay = c.get(Calendar.DAY_OF_WEEK)%7;
        c.setTime(beginPeriod);
        int day = c.get(Calendar.DAY_OF_WEEK)%7;

        //求区间所含的天数
        WorkCalendar workCalendar = WrokCalendarFactory.serverCreate();
        while (day <= endDay) {
            if (workCalendar.isWorkDay(c)) {
                bitmap[(day) % 7] = true;
            }

            day++;
            c.set(Calendar.DAY_OF_WEEK, c.get(Calendar.DAY_OF_WEEK)+1);
        }

        return bitmap;
    }

    private BigDecimal getHourFromDb(TcWeeklyReport db, int day) {
        switch (day) {
        case DtoWeeklyReport.SATURDAY:
            return db.getSatHour();
        case DtoWeeklyReport.SUNDAY:
            return db.getSunHour();
        case DtoWeeklyReport.MONDAY:
            return db.getMonHour();
        case DtoWeeklyReport.TUESDAY:
            return db.getTueHour();
        case DtoWeeklyReport.WEDNESDAY:
            return db.getWedHour();
        case DtoWeeklyReport.THURSDAY:
            return db.getThuHour();
        case DtoWeeklyReport.FRIDAY:
            return db.getFriHour();
        default:
            return null;
        }
    }

    private void getWeekPeriod(Calendar beginC, Calendar endC) {
        //将[beginDate , endDate]区间展开为一个星期的区间[上星期星期六，这星期星期五]
        //一般情况下，它们就是一个星期区间
        //当周跨月末时，此区间不是一个星期区间，此时就有必要
        int beginDay = beginC.get(Calendar.DAY_OF_WEEK);
        if (beginDay != Calendar.SATURDAY) {
            int a = -beginDay; //距离上星期六的天数
            int day = beginC.get(Calendar.DAY_OF_YEAR) + a;
            beginC.set(Calendar.DAY_OF_YEAR, day);
        }

        int endDay = endC.get(Calendar.DAY_OF_WEEK);
        if (endDay != Calendar.FRIDAY) {
            int a = Calendar.FRIDAY - (endDay % 7); //距离这星期五的天数
            int day = endC.get(Calendar.DAY_OF_YEAR) + a;
            endC.set(Calendar.DAY_OF_YEAR, day);
        }

        //求上一个星期区间
        beginC.set(Calendar.WEEK_OF_YEAR, beginC.get(Calendar.WEEK_OF_YEAR) - 1);
        endC.set(Calendar.WEEK_OF_YEAR, endC.get(Calendar.WEEK_OF_YEAR) - 1);
    }

    private boolean[] getPeriodBitmap(Calendar beginC, Calendar endC) {
        int beginDay = beginC.get(Calendar.DAY_OF_WEEK);
        int endDay = endC.get(Calendar.DAY_OF_WEEK);

        //求区间所含的天数
        boolean periodBitmap[] = new boolean[7];
        for (int i = 0; i < periodBitmap.length; i++) {
            if (i >= beginDay % 7 && i <= endDay % 7) {
                periodBitmap[i] = true;
            } else {
                periodBitmap[i] = false;
            }
        }
        return periodBitmap;
    }

    private String getTempKey(Long acntRid, Long activityRid) {
        String key = null;
        if (acntRid != null) {
            key = acntRid.toString();
        }
        if (activityRid != null) {
            key += "@" + activityRid.toString();
        }
        return key;
    }

    private String getTempKey(TcWeeklyReport db) {
        String key = null;
        if (db.getAcntRid() != null) {
            key = db.getAcntRid().toString();
        }
        if ("0".equals(db.getIsActivity())) {
            if (db.getCodeValueRid() != null) {
                key += "@c" + db.getCodeValueRid().toString();
            }
        } else {
            if (db.getActivityRid() != null) {
                key += "@a" + db.getActivityRid().toString();
            }
        }

        return key;
    }

    public static void main(String args[]){
        try {
            HBComAccess dba = new HBComAccess();
            LgWeeklyReportByWorker logic = new LgWeeklyReportByWorker();
            logic.setDbAccessor(dba);
            dba.newTx();

            boolean []b = logic.getWorkDayBitmap(new Date(),new Date());
            for (int i = 0; i < b.length; i++) {
                System.out.println(b[i]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
