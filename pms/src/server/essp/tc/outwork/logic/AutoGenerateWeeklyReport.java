package server.essp.tc.outwork.logic;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WorkCalendarConstant;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import db.essp.tc.TcOutWorker;
import net.sf.hibernate.Session;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.tc.common.LgTcCommon;
import server.essp.tc.common.LgWeeklyReport;
import server.framework.common.BusinessException;
import c2s.dto.IDto;
import java.math.BigDecimal;
import com.wits.util.comDate;
import c2s.essp.attendance.Constant;
import java.sql.ResultSet;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

/**
 *
 * <p>Title: ������Ա�ܱ�����</p>
 * <p>Description: ��ÿ�ܴߴ�δ��д�ܱ���Ա��ǰ,��Ϊ������Ա�Զ�����������ܱ�ʱ��
 * 1.��������ʱ��Ϊÿ��ĩ(ÿ�����25��)��ŵ�,�������ظ�����
 * 2.���㵱ǰ���ڲ��񻮷��ܵ�����,�����ݸ�������ҳ�����Ա,����Ŀ����,���������Ա��ÿ����Ŀ�ڳ������List
 * 3.�������г�����Ա,Ϊÿ��������Ա���ڵ���Ŀ�Զ���д�ܱ�
 * </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class AutoGenerateWeeklyReport extends AbstractESSPLogic {
    ////��ǰ����
    private Calendar today = Calendar.getInstance();
    private WorkCalendar wc = WrokCalendarFactory.serverCreate();
    private Calendar[] period = null;
    private LgWeeklyReport lgWeeklyReport = new LgWeeklyReport();
    ////�ͻ�����-�ֳ�֧�ֶ�Ӧ��rid,�����ݿ���������޸���,����ĳ���Ҫ�޸�
    public static final long OUT_WORKING_CODE = 101;

    public AutoGenerateWeeklyReport() {
        this(0);
    }

    public AutoGenerateWeeklyReport(int weekOffset) {
        period = wc.getNextBEWeekDay(today, weekOffset);
//        period = wc.getBEWeekDay(today);
        period[0].set(Calendar.HOUR_OF_DAY, 0);
        period[0].set(Calendar.MINUTE, 0);
        period[0].set(Calendar.SECOND, 0);
        period[0].set(Calendar.MILLISECOND, 0);
        period[1].set(Calendar.HOUR_OF_DAY, 23);
        period[1].set(Calendar.MINUTE, 59);
        period[1].set(Calendar.SECOND, 59);
        period[1].set(Calendar.MILLISECOND, 999);

    }

    public Calendar[] getPeriod() {
        return period;
    }

    public void run() {
//        checkDate();

        ////���г��������г����¼,�ٶԳ����¼����Ա����Ŀ����
        List outWorkerRecords = getOutWorkersRecordsInPeriod(period[0].getTime(),
                period[1].getTime());
        Map outWorkers = groupOutWorkerRecords(outWorkerRecords);

        ////��ÿ�������ڵ�ÿ����Ŀ����ʱ�������ܱ�
        if (outWorkers == null || outWorkers.size() == 0) {
            return;
        }
        Iterator it = outWorkers.keySet().iterator();
        while (it.hasNext()) {
            String loginId = (String) it.next();
            Map acountDaysMap = (Map) outWorkers.get(loginId);
            Iterator it2 = acountDaysMap.keySet().iterator();
            while (it2.hasNext()) {
                Long acntRid = (Long) it2.next();
                Set days = (Set) acountDaysMap.get(acntRid);
                if (days == null || days.size() == 0) {
                    continue;
                }
                List report = lgWeeklyReport.listByUserId(loginId,
                        period[0].getTime(),
                        period[1].getTime());
                DtoWeeklyReport dto = generateWeeklyReport(report, loginId, acntRid, days);
                saveWeeklyReport(dto);
            }
        }
    }

    /**
     * �����������ɵ��ܱ�
     * 1.���������������޸��ܱ�,ѡ�񱣴����²���,��ͬ������TcByWorkerAccount���ܱ�
     * 2.���õ�ǰ��Ŀ�ܱ��Ļ���ֵ��״̬ΪConfirmed
     * 3.�������ܱ�����ΪUnLocked,ʹ���ܱ��������޸�
     * @param dto DtoWeeklyReport
     */
    private void saveWeeklyReport(DtoWeeklyReport dto) {
//        if(dto.getSumHour() == null || dto.getSumHour().compareTo(DtoWeeklyReport.BIG_DECIMAL_0_0) == 0)
//            return;
        if (IDto.OP_INSERT.equals(dto.getOp())) {
            lgWeeklyReport.insert(dto);
        } else if (IDto.OP_MODIFY.equals(dto.getOp())) {
            lgWeeklyReport.update(dto);
        }
        String confirm = DtoWeeklyReport.STATUS_CONFIRMED;
        String loginId = dto.getUserId();
        Long acntRid = dto.getAcntRid();
        String dateStyle = comDate.pattenDate;
        String beginPeriod = comDate.dateToString(dto.getBeginPeriod(), dateStyle);
        String endPeriod = comDate.dateToString(dto.getEndPeriod(), dateStyle);
        String sql = "update tc_weekly_report_sum set confirm_status='" + confirm + "' " +
                     "where user_id='" + loginId + "' and acnt_rid = '" + acntRid + "' " +
                     "and to_char(begin_period,'" + dateStyle + "')='" + beginPeriod + "' " +
                     "and to_char(end_period,'" + dateStyle + "')='" + endPeriod + "'";
        try {
            log.info("update weekly sum status:" + sql);
            this.getDbAccessor().executeUpdate(sql);

            sql = "update tc_weekly_report_lock set is_locked='0' " +
                  "where user_id='" + loginId + "' " +
                  "and to_char(begin_period,'" + dateStyle + "')='" + beginPeriod + "' " +
                  "and to_char(end_period,'" + dateStyle + "')='" + endPeriod + "'"; ;

            log.info("unlock weekly report:" + sql);
            this.getDbAccessor().executeUpdate(sql);
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    /**
     * ����Ա�������Ŀ�����ܱ�
     * 1.���Ҹ���Ա�ڸ���Ŀ���Ƿ�����һ���ͻ�����-�ֳ�֧�ֵ��ܱ���¼,��û������һ���ü�¼
     * 2.������Ŀ�ڳ����������,��������ܱ������ӵ�ʱ��=��׼��ʱ-��ٹ�ʱ+�Ӱ๤ʱ-�ܱ�����ʱ��
     * ��ٺͼӰ��Ϊ��ȷ�ϵ�
     * 3.���ݸ����ܱ��Ƿ�Ϊ������,����ÿ��ʱ��
     */
    private DtoWeeklyReport generateWeeklyReport(List report, String loginId, Long acntRid, Set days) {
        boolean isFound = false;
        DtoWeeklyReport dto = null;
        if (report != null && report.size() > 0) {
            for (int i = 0; i < report.size(); i++) {
                dto = (DtoWeeklyReport) report.get(i);
                Long weeklyReportAcntRid = dto.getAcntRid();
                Long codeValueRid = dto.getCodeValueRid();
                if (codeValueRid != null && codeValueRid.longValue() == OUT_WORKING_CODE
                    && weeklyReportAcntRid.longValue() == acntRid.longValue()) {
                    isFound = true;
                    break;
                }
            }
        }
        if (isFound) {
            dto.setOp(IDto.OP_MODIFY);
        } else {
            dto = new DtoWeeklyReport();
            dto.setUserId(loginId);
            dto.setAcntRid(acntRid);
            dto.setCodeValueRid(new Long(OUT_WORKING_CODE));
            dto.setBeginPeriod(period[0].getTime());
            dto.setEndPeriod(period[1].getTime());
            dto.setIsActivity("0");
            dto.setOp(IDto.OP_INSERT);
        }
        Iterator it = days.iterator();
        while (it.hasNext()) {
            Calendar day = (Calendar) it.next();
            int dayOfWeek = getDayOfWeek(day);
            BigDecimal standardHours = new BigDecimal(DtoWeeklyReport.STANDARD_HOUR_ONE_DAY);
            BigDecimal leaveHours = getConfirmedLeaveHoursInDay(loginId, day);
            BigDecimal overTimeHours = getConfirmedOverTimeHoursInDay(loginId, day);
            BigDecimal weeklyReportSumInDay = getWeeklyReportSumInDay(report, dayOfWeek);
            BigDecimal increHoursInDay = standardHours
                                         .subtract(leaveHours)
                                         .add(overTimeHours)
                                         .subtract(weeklyReportSumInDay);
            if (IDto.OP_INSERT.equals(dto.getOp())) {
                dto.setHour(dayOfWeek, increHoursInDay);
            } else if (IDto.OP_MODIFY.equals(dto.getOp())) {
                dto.add(dayOfWeek, increHoursInDay);
            }
        }
        return dto;
    }

    //����Calendar��Ӧ�ĵ������ڼ�,��DtoWeeklyReprot�ж�������ڶ�Ӧ
    protected int getDayOfWeek(Calendar day) {
        int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
        case Calendar.SATURDAY:
            dayOfWeek = DtoWeeklyReport.SATURDAY;
            break;
        case Calendar.SUNDAY:
            dayOfWeek = DtoWeeklyReport.SUNDAY;
            break;
        case Calendar.MONDAY:
            dayOfWeek = DtoWeeklyReport.MONDAY;
            break;
        case Calendar.TUESDAY:
            dayOfWeek = DtoWeeklyReport.TUESDAY;
            break;
        case Calendar.WEDNESDAY:
            dayOfWeek = DtoWeeklyReport.WEDNESDAY;
            break;
        case Calendar.THURSDAY:
            dayOfWeek = DtoWeeklyReport.THURSDAY;
            break;
        case Calendar.FRIDAY:
            dayOfWeek = DtoWeeklyReport.FRIDAY;
            break;
        }
        return dayOfWeek;
    }

    //�����ܱ���¼����ÿ��ĺϼ�ֵ
    protected BigDecimal getWeeklyReportSumInDay(List weeklyReport, int dayOfWeek) {
        BigDecimal sum = DtoWeeklyReport.BIG_DECIMAL_0;
        for (int i = 0; i < weeklyReport.size(); i++) {
            DtoWeeklyReport dto = (DtoWeeklyReport) weeklyReport.get(i);
            BigDecimal hours = dto.getHour(dayOfWeek);
            if (hours != null) {
                sum = sum.add(hours);
            }
        }
        return sum;
    }

    //����ĳ����ĳ������ȷ�ϵ���ٹ�ʱ
    private BigDecimal getConfirmedLeaveHoursInDay(String loginId, Calendar day) {
        String sql = "select sum(t2.hours) as hours from tc_leave_main t1 left join tc_leave_detail t2 on t1.rid = t2.leave_id " +
                     "where t1.login_id='" + loginId + "' " +
                     "and t1.status != '"+Constant.STATUS_ABORTED+"' " +
                     "and to_char(t2.leave_day,'yyyy/MM/dd') = '" + comDate.dateToString(day.getTime(), "yyyy/MM/dd") + "'";
        log.info("sum leave hours:" + sql);
        try {
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            while (rt.next()) {
                double hours = rt.getDouble("hours");
                return new BigDecimal(hours);
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return DtoWeeklyReport.BIG_DECIMAL_0;
    }

    // ����ĳ����ĳ������ȷ�ϵļӰ๤ʱ
    private BigDecimal getConfirmedOverTimeHoursInDay(String loginId, Calendar day) {
        String sql = "select sum(t2.hours) as hours from tc_overtime t1 left join tc_overtime_detail t2 on t1.rid = t2.overtime_id " +
                     "where t1.login_id='" + loginId + "' " +
                     "and t1.status != '"+Constant.STATUS_ABORTED+"' " +
                     "and to_char(t2.overtime_day,'yyyy/MM/dd') = '" + comDate.dateToString(day.getTime(), "yyyy/MM/dd") + "'";
        log.info("sum overtime hours:" + sql);
        try {
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            while (rt.next()) {
                double hours = rt.getDouble("hours");
                return new BigDecimal(hours);
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return DtoWeeklyReport.BIG_DECIMAL_0;
    }

    /**
     * �жϵ�ǰ�����Ƿ�Ϊ��ĩ��25��
     */
    private void checkDate() {
        int dayOfMon = today.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);

        if (dayOfMon != WorkCalendarConstant.MONTH_BEGIN_DAY - 1 &&
            dayOfWeek != Calendar.FRIDAY) {
            throw new BusinessException("", "today [" + today.toString() + "] is not Friday or 25th of month!");
        }
    }

    /**
     * ����Աͳ�Ƴ����¼
     * @return Map Key--��Ա��LoginId,Value--��Ա�ڸ�����Ŀ�ڳ������List,ҲΪһ��Map,Key--��ĿRid,Value--�������List
     */
    protected Map groupOutWorkerRecords(List outWorkerRecords) {
        Map result = new HashMap();

        if (outWorkerRecords != null) {
            for (int i = 0; i < outWorkerRecords.size(); i++) {
                TcOutWorker outWorker = (TcOutWorker) outWorkerRecords.get(i);
                String loginId = outWorker.getLoginId();
                Long acntRid = outWorker.getAcntRid();
                Date begin = outWorker.getBeginDate();
                Date end = outWorker.getEndDate();
                Set workDays = getWorkDays(begin, end);
                Map acountDaysMap = (Map) result.get(loginId);
                if (acountDaysMap == null) {
                    acountDaysMap = new HashMap();
                    acountDaysMap.put(acntRid, workDays);
                    result.put(loginId, acountDaysMap);
                } else {
                    Set outDaysInAccount = (Set) acountDaysMap.get(acntRid);
                    if (outDaysInAccount == null) {
                        outDaysInAccount = workDays;
                    } else {
                        outDaysInAccount.addAll(workDays);
                    }
                    acountDaysMap.put(acntRid, outDaysInAccount);
                }
            }
        }
        return result;
    }

    //������ʼ�����ڵĹ����ռ���
    private Set getWorkDays(Date begin, Date end) {
        Set result = new HashSet();
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(begin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        calEnd.set(Calendar.HOUR_OF_DAY, 10);
        List workDays = wc.getWorkDays(calBegin, calEnd);
        if (workDays == null) {
            return result;
        }
        for (int i = 0; i < workDays.size(); i++) {
            Calendar day = (Calendar) workDays.get(i);
            day.set(Calendar.HOUR_OF_DAY, 0);
            day.set(Calendar.MINUTE, 0);
            day.set(Calendar.SECOND, 0);
            day.set(Calendar.MILLISECOND, 0);
            if (day.getTimeInMillis() >= period[0].getTimeInMillis() &&
                day.getTimeInMillis() <= period[1].getTimeInMillis()) {
                result.add(day);
            }
        }
        return result;
    }

    /**
     * �����������н��������Զ���д�ܱ��ĳ����¼
     * @param begin Date
     * @param end Date
     * @return List
     */
    private List getOutWorkersRecordsInPeriod(Date beginPeriod, Date endPeriod) {
        String hql = "from TcOutWorker worker " +
                     "where worker.beginDate <= :endPeriod " +
                     "and worker.endDate >= :beginPeriod " +
                     "and worker.isAutoWeeklyReport = :isAutoWeeklyReport";
        Date resetBeginPeriod = LgTcCommon.resetBeginDate(beginPeriod);
        Date resetEndPeriod = LgTcCommon.resetEndDate(endPeriod);
        try {
            Session s = this.getDbAccessor().getSession();
            return s.createQuery(hql)
                    .setDate("beginPeriod", resetBeginPeriod)
                    .setDate("endPeriod", resetEndPeriod)
                    .setBoolean("isAutoWeeklyReport", true)
                    .list();
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    public void generateForMonth(int monthOffSet) {
        Calendar[] monthPeriod = WorkCalendar.getNextBEMonthDay(today, monthOffSet);
        ArrayList weekList = (ArrayList) WorkCalendar.getBEWeekListMax(monthPeriod[0], monthPeriod[1]);
        for (int i = 0; i < weekList.size(); i++) {
            this.period[0] = ((Calendar[]) weekList.get(i))[0];
            this.period[1] = ((Calendar[]) weekList.get(i))[1];
            //����Ǳ��£��򵽱��ܽ�ֹ��
            if (monthOffSet == 0 && today.getTime().compareTo(this.period[0].getTime()) < 0) {
                return;
            }
            this.period[0].set(Calendar.HOUR_OF_DAY, 0);
            this.period[0].set(Calendar.MINUTE, 0);
            this.period[0].set(Calendar.SECOND, 0);
            this.period[0].set(Calendar.MILLISECOND, 0);
            this.period[1].set(Calendar.HOUR_OF_DAY, 23);
            this.period[1].set(Calendar.MINUTE, 59);
            this.period[1].set(Calendar.SECOND, 59);
            this.period[1].set(Calendar.MILLISECOND, 999);
            this.run();
        }
    }

    public static void main(String[] args) {
        AutoGenerateWeeklyReport autogenerateweeklyreport = null;
        boolean isWeek = true;
        int timeOffset = 0;
        if (args.length >= 2) {
            try {
                isWeek = args[0].equals("week");
                timeOffset = Integer.parseInt(args[1]);
                autogenerateweeklyreport = new AutoGenerateWeeklyReport(timeOffset);
            } catch (NumberFormatException tx) {
                log.error(tx);
                autogenerateweeklyreport = new AutoGenerateWeeklyReport();
            }
        } else {
            autogenerateweeklyreport = new AutoGenerateWeeklyReport();
        }

        try {
            autogenerateweeklyreport.getDbAccessor().followTx();
            if (isWeek) {
                System.out.println("generate weekly report of week:" + timeOffset);
                autogenerateweeklyreport.run();
            } else {
                System.out.println("generate weekly report of month:" + timeOffset);
                autogenerateweeklyreport.generateForMonth(timeOffset);
            }
            autogenerateweeklyreport.getDbAccessor().endTxCommit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                autogenerateweeklyreport.getDbAccessor().endTxRollback();
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }
}
