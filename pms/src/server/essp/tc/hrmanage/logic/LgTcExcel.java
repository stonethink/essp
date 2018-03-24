package server.essp.tc.hrmanage.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import com.wits.util.comDate;
import db.essp.attendance.TcLeaveMain;
import db.essp.attendance.TcLeaveType;
import db.essp.attendance.TcOvertime;
import db.essp.tc.TcAttendance;
import db.essp.tc.TcNonAtten;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import net.sf.hibernate.Query;
import c2s.essp.attendance.Constant;
import server.essp.tc.hrmanage.viewbean.VbTcExcel;
import server.essp.tc.hrmanage.viewbean.VbTcPeriod;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import c2s.essp.common.calendar.WorkCalendar;
import server.essp.tc.hrmanage.viewbean.VbPartTcExcel;
import server.essp.attendance.overtime.logic.LgOverTime;
import server.essp.tc.common.LgTcCommon;
import java.math.BigDecimal;
import itf.hr.LgHrUtilImpl;
import itf.hr.HrFactory;

public class LgTcExcel extends AbstractBusinessLogic {
    final static String HR_ACCOUNT_SCOPE_TABLE = "essp_hr_account_scope_t";
    /** 用户登录表*/
    public final static String LOGIN_TABLE = LgHrUtilImpl.LOGIN_TABLE;
    /**违规持久化类*/
    public final static String ATTENDANCE_TABLE = "TcAttendance";
    /**旷工持久化类*/
    public final static String NONATTEN_TABLE = "TcNonAtten";
    /**调休持久化类*/
    public final static String LEAVEDETAIL_TABLE = "TcLeaveDetail";
    public final static String LEAVEMAIN_TABLE = "TcLeaveMain";
    public final static String LEAVETYPE_TABLE = "TcLeaveType";
    /**加班持久化类*/
    public final static String OVERTIME_TABLE = "TcOvertime";
    public final static String WEEKLYREPORT_TABLE = "tc_weekly_report_sum";

    private final static String VIOLAT = "违规";
    private final static String DELAY = "迟到";
    private final static String ABSENT = "旷工";
    private final static String SHIFT = "调休";
    private final static String OVERTIME = "加班";
    private final static String HOURS = "小时";
    public static final String STATUS_NONE = "None";
    public static final String STATUS_UNLOCK = "UnLocked";
    public static final String STATUS_LOCK = "Locked";
    public static final String STATUS_CONFIRMED = "Confirmed";
    public static final String STATUS_REJECTED = "Rejected";
    public static final String No_Filled_WeeklyReport = "周报未填";
    public static final String No_Locked_WeeklyReport = "周报未锁定";
    public static final String No_Confirmed_WeeklyReport= "周报未被确认";
    public static final String No_Confirmed_Leave="请假未批准";
    public static final String No_Confirmed_Overtime="加班未批准";

    private String wmflag = ""; //week or month
    private Date _beginPeriod;
    private Date _endPeriod;
//    private BigDecimal totalStandard=new BigDecimal(0);//合计标准工时
//    private BigDecimal totalActual=new BigDecimal(0);//合计实际工时
//    private BigDecimal totalNormal=new BigDecimal(0);//合计正常工时
//    private BigDecimal totalSub=new BigDecimal(0);//合计工时差
//    private BigDecimal totalSalaryWorkTime=new BigDecimal(0);//合计发薪工时
//    private BigDecimal totalOverTime=new BigDecimal(0);//合计加班工时
//    private BigDecimal totalFullSalaryLeave=new BigDecimal(0);//合计休假（全薪）
//    private BigDecimal totalHalfSalaryLeave=new BigDecimal(0);//合计休假（半薪）
//    private BigDecimal totalLeave=new BigDecimal(0);//合计休假
//    private BigDecimal totalAbsentFromWork=new BigDecimal(0);//合计旷工
//    private BigDecimal totalViolat=new BigDecimal(0);//合计违规

    public LgTcExcel() {

    }

    /**
     * 根据传入的参数获取Excel需要的所有信息:
     * 查找orgnazition下的每个人的weeklyReportSum
     * 如果时间区间为“周”，那么对user所在的每个项目都可能会查出一条数据
     * 如果时间区间为“月”，那么对月内的每个周 & 每个项目都可能会查出一条数据
     * @param acntRid Long
     * @param beginPeriod Date
     * @param endPeriod Date
     * @param flag String
     * @return List
     */
    public List AllDetailList(Long acntRid, Date beginPeriod, Date endPeriod, String flag) {
        List allDetailList = new ArrayList();
        List detailList = null;
        //得到_beginPeriod所属星期的第一天
        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(beginPeriod);
        this._beginPeriod = (WorkCalendar.getBEWeekDay(beginCal))[0].getTime();
        //得到_endPeriod所属星期的最后一天
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endPeriod);
        this._endPeriod = (WorkCalendar.getBEWeekDay(endCal))[1].getTime();

        LgWeeklyReportSumByHr lgWeeklyReportSumByHr = new LgWeeklyReportSumByHr();
        if (DtoWeeklyReportSumByHr.ON_WEEK.equalsIgnoreCase(flag)) {
            this.wmflag = DtoWeeklyReportSumByHr.ON_WEEK;

            detailList = lgWeeklyReportSumByHr.listByUserInHrOnWeek(acntRid,
                    _beginPeriod, _endPeriod);
        } else if (DtoWeeklyReportSumByHr.ON_MONTH.equalsIgnoreCase(flag)) {
            this.wmflag = DtoWeeklyReportSumByHr.ON_MONTH;

            detailList = lgWeeklyReportSumByHr.listByUserInHrOnMonth(acntRid,
                    _beginPeriod, _endPeriod);
        }
        //放入所需的第一个数据.形如（Week/Month）2006-01-01 - 2006-01-06
        //String period = "(" + wmflag + ")" + comDate.dateToString(_beginPeriod) + " - " + comDate.dateToString(_endPeriod);
        String end;
        String beging_year = comDate.dateToString(beginPeriod, "yyyy");
        String end_year = comDate.dateToString(endPeriod, "yyyy");

        if (beging_year.equalsIgnoreCase(end_year)) {
            end = comDate.dateToString(_endPeriod, "MM-dd");
        } else {
            end = comDate.dateToString(_endPeriod);
        }
        String period = comDate.dateToString(_beginPeriod) + " ~ " + end;
        VbTcPeriod vbTcPeriod = new VbTcPeriod();
        vbTcPeriod.setPeriod(period);
        allDetailList.add(0, vbTcPeriod);
        //放入所需的第二个数据.部分条目的合计
//        allDetailList.add(1,"");
        //放入所需的第三个数据.周报（月报）数据LIST
        allDetailList.add(1, getAllDetail(detailList));
        return allDetailList;
    }

    /**
     * 根据传入的参数获取Excel需要的信息（加班，休假（全/半），休假，旷工，违规，备注）
     * 查找orgnazition下的每个人的weeklyReportSum
     * 如果时间区间为“周”，那么对user所在的每个项目都可能会查出一条数据
     * 如果时间区间为“月”，那么对月内的每个周 & 每个项目都可能会查出一条数据
     * @param acntRid Long
     * @param beginPeriod Date
     * @param endPeriod Date
     * @param flag String
     * @return List
     */
    public List partDetailList(Long acntRid, Date beginPeriod, Date endPeriod) {
        List partDetailList = new ArrayList();
        List detailList = null;
        LgWeeklyReportSumByHr lgWeeklyReportSumByHr = new LgWeeklyReportSumByHr();
        this.wmflag = DtoWeeklyReportSumByHr.ON_MONTH;

        //得到_beginPeriod所属星期的第一天
        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(beginPeriod);
        this._beginPeriod = (WorkCalendar.getBEWeekDay(beginCal))[0].getTime();
        //得到_endPeriod所属星期的最后一天
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endPeriod);
        this._endPeriod = (WorkCalendar.getBEWeekDay(endCal))[1].getTime();

        detailList = lgWeeklyReportSumByHr.listByUserInHrOnMonth(acntRid,
                _beginPeriod, _endPeriod);
        //放入所需的第一个数据.形如 2006-01-01 - 2006-01-06
        String period = comDate.dateToString(_beginPeriod) + " ~ " + comDate.dateToString(_endPeriod);
        VbTcPeriod vbTcPeriod = new VbTcPeriod();
        vbTcPeriod.setPeriod(period);
        partDetailList.add(0, vbTcPeriod);
        //放入所需的第二个数据.部分条目的合计
//        allDetailList.add(1,"");
        //放入所需的第三个数据.周报（月报）数据LIST
        partDetailList.add(1, getPartDetail(detailList));
        return partDetailList;
    }


    /**
     * 生成报表需要的全部数据
     * @param detail_list List
     * @return List
     */
    public List getAllDetail(List detail_list) {
        List allDetailList = new ArrayList();
        if (detail_list == null) {
            return null;
        }
        for (int i = 0; i < detail_list.size(); i++) {
            VbTcExcel vbTcExcel = new VbTcExcel();
            DtoWeeklyReportSumByHr weeklyReportBean =
                    (DtoWeeklyReportSumByHr) detail_list.get(i);
            vbTcExcel.setName(weeklyReportBean.getUserId());
            vbTcExcel.setChineseName(weeklyReportBean.getChineseName());
            vbTcExcel.setBeginDateOfWork(weeklyReportBean.getRealBeginPeriod());
            vbTcExcel.setEndDateOfWork(weeklyReportBean.getRealEndPeriod());
            vbTcExcel.setStandardWorkTime(weeklyReportBean.getStandardHour().setScale(2, BigDecimal.ROUND_HALF_UP));
            //modify by xr ,报表显示人员的被确认的实际工时
//            vbTcExcel.setActualWorkTime(weeklyReportBean.getActualHour());
            vbTcExcel.setActualWorkTime(weeklyReportBean.getActualHourConfirmed().setScale(2, BigDecimal.ROUND_HALF_UP));
            vbTcExcel.setNormalWorkTime(weeklyReportBean.getNormalHours().setScale(2, BigDecimal.ROUND_HALF_UP));
            vbTcExcel.setSubWorkTime(weeklyReportBean.getVarientHours().setScale(2, BigDecimal.ROUND_HALF_UP));

            //modify by xr,发薪工时大于标准工时时以标准工时为准
            vbTcExcel.setSalaryWorkTime(weeklyReportBean.getPayTimes().setScale(2, BigDecimal.ROUND_HALF_UP));

            vbTcExcel.setOverTime(weeklyReportBean.getOvertimeSumConfirmed().setScale(2, BigDecimal.ROUND_HALF_UP));
            vbTcExcel.setFullSalaryLeave(weeklyReportBean.getLeaveOfPayAllConfirmed().setScale(2, BigDecimal.ROUND_HALF_UP));
            vbTcExcel.setHalfSalaryLeave(weeklyReportBean.getLeaveOfPayHalfConfirmed().setScale(2, BigDecimal.ROUND_HALF_UP));
            vbTcExcel.setLeave(weeklyReportBean.getLeaveOfPayNoneConfirmed().setScale(2, BigDecimal.ROUND_HALF_UP));
            vbTcExcel.setAbsentFromWork(weeklyReportBean.getAbsent());
            vbTcExcel.setViolat(weeklyReportBean.getViolat());
            vbTcExcel.setRemark(getRemark(vbTcExcel.getName()));
            vbTcExcel.setWmReportStatus(getWMReportStatus(weeklyReportBean.getUserId(), _beginPeriod, _endPeriod));
            vbTcExcel.setAttendanceReportStatus(getAttendanceReportStatus(weeklyReportBean.getUserId(), _beginPeriod, _endPeriod));
            allDetailList.add(vbTcExcel);
        }
        return allDetailList;
    }

    /**
     * 生成报表所需要的部分数据（加班，休假（全/半），休假，旷工，违规，备注）
     * @param vbTcExcel VbTcExcel
     * @return String
     */
    public List getPartDetail(List detail_list) {
        List partDetailList = new ArrayList();
        if (detail_list == null) {
            return null;
        }
        for (int i = 0; i < detail_list.size(); i++) {
            VbPartTcExcel vbPartTcExcel = new VbPartTcExcel();
            DtoWeeklyReportSumByHr weeklyReportBean =
                    (DtoWeeklyReportSumByHr) detail_list.get(i);
            vbPartTcExcel.setName(weeklyReportBean.getUserId());
            vbPartTcExcel.setChineseName(weeklyReportBean.getChineseName());
            vbPartTcExcel.setOverTime(weeklyReportBean.getOvertimeSumConfirmed().setScale(2, BigDecimal.ROUND_HALF_UP));
            vbPartTcExcel.setFullSalaryLeave(weeklyReportBean.getLeaveOfPayAllConfirmed().setScale(2, BigDecimal.ROUND_HALF_UP));
            vbPartTcExcel.setHalfSalaryLeave(weeklyReportBean.getLeaveOfPayHalfConfirmed().setScale(2, BigDecimal.ROUND_HALF_UP));
            vbPartTcExcel.setLeave(weeklyReportBean.getLeaveOfPayNoneConfirmed().setScale(2, BigDecimal.ROUND_HALF_UP));
            vbPartTcExcel.setAbsentFromWork(weeklyReportBean.getAbsent());
            vbPartTcExcel.setViolat(weeklyReportBean.getViolat());
            vbPartTcExcel.setRemark(getRemark(vbPartTcExcel.getName()));
            partDetailList.add(vbPartTcExcel);
        }
        return partDetailList;

    }

    public String getRemark(String userName) {
        String userId = userName;
        if (_beginPeriod == null || _endPeriod == null) {
            return "";
        }

        StringBuffer remark = new StringBuffer();
        List attendanceList; //存放所有的违规记录
        List nonAttenList; //存放所有的旷工记录
        List shiftLeaveList; //存放所有的调休记录
        List overTimeList; ////存放所有的加班记录
        //获取违规信息
        attendanceList = getAttendanceList(userId, _beginPeriod, _endPeriod);
        if (attendanceList != null && attendanceList.size() > 0) {
            for (int ali = 0; ali < attendanceList.size(); ali++) {
                TcAttendance tcAttendance = (TcAttendance) attendanceList.get(ali);
//               totalViolat=totalViolat.add(new BigDecimal(1));//统计每个人的违规次数
                if (tcAttendance.getType() == null || "".equals(tcAttendance.getType().trim())) {
                    remark.append(VIOLAT);
                } else {
                    remark.append(tcAttendance.getType());
                }
                remark.append("(");
                remark.append(comDate.dateToString(tcAttendance.getDate(), "yyyy-MM-dd"));
//               remark.append(tcAttendance.getDate());
                remark.append("); \n");
            }
        }
        //获取旷工信息
        nonAttenList = getNonAttenList(userId, _beginPeriod, _endPeriod);
        if (nonAttenList != null && nonAttenList.size() > 0) {
            for (int nli = 0; nli < nonAttenList.size(); nli++) {
                TcNonAtten tcNonAtten = (TcNonAtten) nonAttenList.get(nli);
//               totalAbsentFromWork=totalAbsentFromWork.add(tcNonAtten.getTotalHours());//统计每个人的旷工时间
                remark.append(ABSENT);
                remark.append(" " + tcNonAtten.getTotalHours());
                remark.append(HOURS);
                remark.append("(");
                remark.append(comDate.dateToString(tcNonAtten.getTimeFrom(), "yyyy-MM-dd HH:mm"));
//               remark.append(tcNonAtten.getTimeFrom().toString());
                remark.append(" ~ ");
                remark.append(handlerDateTime(tcNonAtten.getTimeFrom(),tcNonAtten.getTimeTo()));
//                String strTimeFrom = comDate.dateToString(tcNonAtten.getTimeFrom(), "yyyy-MM-dd");
//                String strTimeTo = comDate.dateToString(tcNonAtten.getTimeTo(), "yyyy-MM-dd");
//                String strFrom_year = comDate.dateToString(tcNonAtten.getTimeFrom(), "yyyy");
//                String strTo_year = comDate.dateToString(tcNonAtten.getTimeTo(), "yyyy");
//                String strFrom_month=comDate.dateToString(tcNonAtten.getTimeFrom(), "MM");
//                String strTo_month=comDate.dateToString(tcNonAtten.getTimeTo(), "MM");
//                String strFrom_yearAndMonth = comDate.dateToString(tcNonAtten.getTimeFrom(), "yyyy-MM");
//                String strTo_yearAndMonth = comDate.dateToString(tcNonAtten.getTimeTo(), "yyyy-MM");
//                if (strTimeFrom.equalsIgnoreCase(strTimeTo)) { //如果是同一天
//                    remark.append(comDate.dateToString(tcNonAtten.getTimeTo(), "HH:mm"));
//                } else if (strFrom_yearAndMonth.equalsIgnoreCase(strTo_yearAndMonth)) { //如果是同一月
//                    remark.append(comDate.dateToString(tcNonAtten.getTimeTo(), "MM-dd HH:mm"));
//                } else if (strFrom_year.equalsIgnoreCase(strTo_year)) { //如果是同一年
//                    remark.append(comDate.dateToString(tcNonAtten.getTimeTo(), "MM-dd HH:mm"));
//                } else {
//                    remark.append(comDate.dateToString(tcNonAtten.getTimeTo(), "yyyy-MM-dd HH:mm"));
//                }
//               remark.append(tcNonAtten.getTimeTo().toString());
                remark.append(");  \n");
            }
        }
        //获取调休信息
        shiftLeaveList = getShiftLeaveList(userId, _beginPeriod, _endPeriod);
        if (shiftLeaveList != null && shiftLeaveList.size() > 0) {
            for (int sli = 0; sli < shiftLeaveList.size(); sli++) {
                TcLeaveMain tcLeaveMain = (TcLeaveMain) shiftLeaveList.get(sli);
//              double actualTotalHours=tcLeaveMain.getActualTotalHours().doubleValue();
//              String leaveName=tcLeaveMain.getLeaveName();
//              String settlementWay=getPayWayOfLeave(leaveName);//根据leaveName获取leave的方式
//              if("Full Salary".equalsIgnoreCase(settlementWay))
//                  totalFullSalaryLeave=totalFullSalaryLeave.add(new BigDecimal(actualTotalHours));
//              else if("Half Salary".equalsIgnoreCase(settlementWay))
//                  totalHalfSalaryLeave=totalHalfSalaryLeave.add(new BigDecimal(actualTotalHours));
//              else if("No Salary".equalsIgnoreCase(settlementWay))
//                  totalLeave=totalLeave.add(new BigDecimal(actualTotalHours));
                remark.append(tcLeaveMain.getLeaveName());
                remark.append(" " + tcLeaveMain.getActualTotalHours());
                remark.append(HOURS);
                remark.append("(");
                remark.append(comDate.dateToString(tcLeaveMain.getActualDatetimeStart(), "yyyy-MM-dd HH:mm"));
//              remark.append(tcLeaveMain.getActualDatetimeStart().toString());
                remark.append(" ~ ");
                remark.append(handlerDateTime(tcLeaveMain.getActualDatetimeStart(),tcLeaveMain.getActualDatetimeFinish()));
//                String timeStart = comDate.dateToString(tcLeaveMain.getActualDatetimeStart(), "yyyy-MM-dd");
//                String timeFinish = comDate.dateToString(tcLeaveMain.getActualDatetimeFinish(), "yyyy-MM-dd");
//                String start_year = comDate.dateToString(tcLeaveMain.getActualDatetimeStart(), "yyyy");
//                String finish_year = comDate.dateToString(tcLeaveMain.getActualDatetimeFinish(), "yyyy");
//                String statr_yearAndMonth = comDate.dateToString(tcLeaveMain.getActualDatetimeStart(), "yyyy-MM");
//                String finish_yearAndMonth = comDate.dateToString(tcLeaveMain.getActualDatetimeFinish(), "yyyy-MM");
//                if (timeStart.equalsIgnoreCase(timeFinish)) { //如果是同一天
//                    remark.append(comDate.dateToString(tcLeaveMain.getActualDatetimeFinish(), "HH:mm"));
//                } else if (statr_yearAndMonth.equalsIgnoreCase(finish_yearAndMonth)) { //如果是同一月
//                    remark.append(comDate.dateToString(tcLeaveMain.getActualDatetimeFinish(), "MM-dd HH:mm"));
//                } else if (start_year.equalsIgnoreCase(finish_year)) { //如果是同一年
//                    remark.append(comDate.dateToString(tcLeaveMain.getActualDatetimeFinish(), "MM-dd HH:mm"));
//                } else {
//                    remark.append(comDate.dateToString(tcLeaveMain.getActualDatetimeFinish(), "yyyy-MM-dd HH"));
//                }

                //remark.append(comDate.dateToString(tcLeaveMain.getActualDatetimeFinish(), "yyyy-MM-dd HH:mm:ss"));
//              remark.append(tcLeaveMain.getActualDatetimeFinish());
                remark.append(");  \n");

            }
        }
        //获取加班信息
//        String[] overTimeArray=getOverTimePeriodDetail(userId);
//        String hours;
//        String overTime;
        overTimeList = getOverTimeList(userId, _beginPeriod, _endPeriod);

        if (overTimeList != null && overTimeList.size() > 0) {
            for (int i = 0; i < overTimeList.size(); i++) {
                TcOvertime tcOverTime = (TcOvertime) overTimeList.get(i);
//             totalOverTime=totalOverTime.add(new BigDecimal(tcOverTime.getActualTotalHours().doubleValue()));
                Long accountId = tcOverTime.getAcntRid();
                IDtoAccount iDtoAccount;
                try {
                    IAccountUtil iAccountUtil = AccountFactory.create();
                    iDtoAccount = iAccountUtil.getAccountByRID(accountId);
                } catch (Exception ex) {
                    log.error(ex);
                    throw new BusinessException("Error", "Error when Get accountName with accountRid");
                }
                if (iDtoAccount != null) {
                    String accountName = iDtoAccount.getName();
                    remark.append(accountName);
                    remark.append(OVERTIME);
                    remark.append(" " + tcOverTime.getActualTotalHours());
                    remark.append(HOURS);
                    remark.append("(");
                    remark.append(comDate.dateToString(tcOverTime.getActualDatetimeStart(), "yyyy-MM-dd HH:mm"));
//                   remark.append(tcOverTime.getActualDatetimeStart());
                    remark.append(" ~ ");
                    remark.append(handlerDateTime(tcOverTime.getActualDatetimeStart(),tcOverTime.getActualDatetimeFinish()));
//                    String timeStart = comDate.dateToString(tcOverTime.getActualDatetimeStart(), "yyyy-MM-dd");
//                    String timeFinish = comDate.dateToString(tcOverTime.getActualDatetimeFinish(), "yyyy-MM-dd");
//                    String start_year = comDate.dateToString(tcOverTime.getActualDatetimeStart(), "yyyy");
//                    String finish_year = comDate.dateToString(tcOverTime.getActualDatetimeFinish(), "yyyy");
//                    if (timeStart.equalsIgnoreCase(timeFinish)) { //如果是同一天
//                        remark.append(comDate.dateToString(tcOverTime.getActualDatetimeFinish(), "HH:mm"));
//                    } else if (start_year.equalsIgnoreCase(finish_year)) { //如果是同一年
//                        remark.append(comDate.dateToString(tcOverTime.getActualDatetimeFinish(), "MM-dd HH:mm"));
//                    } else {
//                        remark.append(comDate.dateToString(tcOverTime.getActualDatetimeFinish(), "yyyy-MM-dd HH:mm"));
//                    }

                    //remark.append(comDate.dateToString(tcOverTime.getActualDatetimeFinish(), "yyyy-MM-dd HH:mm:ss"));
//                   remark.append(tcOverTime.getActualDatetimeFinish());
                    remark.append(");  \n");
                }
            }
        }

        return remark.toString();
    }

    public String getPayWayOfLeave(String leaveName) {
        List payWayOfLeaveList = getSettlementWay(leaveName);
        String SettlementWay = "";
        if (payWayOfLeaveList != null && payWayOfLeaveList.size() > 0) {
            TcLeaveType tcLeaveType = (TcLeaveType) payWayOfLeaveList.get(0);
            SettlementWay = tcLeaveType.getSettlementWay();
        }
        return SettlementWay;
    }

//    public String[] getOverTimePeriodDetail(String userId){
//        List overTimeList=getOverTimeList(userId,_beginPeriod,_endPeriod);
//        double hours=0;//userId在期间(_beginPeriod-_endPeriod)加班的小时数
//        if(overTimeList==null || overTimeList.size()==0)
//            return null;
//        for(int i=0;i<overTimeList.size();i++){
//            TcOvertime tcOverTime=(TcOvertime)overTimeList.get(i);
//            Set tcOverTimeDetails=tcOverTime.getTcOvertimeDetails();
//            Iterator it=tcOverTimeDetails.iterator();
//            while(it.hasNext()){
//               TcOvertimeDetail tcOverTimeDetail=(TcOvertimeDetail)it.next();
//
//            }
//

//        }
//
//    }
    /**
     * 取Attendance表的所有ATTEN_DATE在beginPeriod和endPeriod之间的所有记录
     * @param beginPeriod Date
     * @param endPeriod Date
     * @param loginName String
     * @return List
     */
    public List getAttendanceList(String loginName, Date beginPeriod, Date endPeriod) {
        List attendanceList = new ArrayList();
        try {
            //将开始时间的时分秒都设为0
            Date resetBeginPeriod = LgTcCommon.resetBeginDate(beginPeriod);
            //将结束时间的设置为23:59:59 999
            Date resetEndPeriod = LgTcCommon.resetEndDate(endPeriod);

            String querySql = "from " + ATTENDANCE_TABLE
                              + " t where t.userId=:loginName and "
                              + " t.date>=:beginPeriod "
                              + " and t.date<=:endPeriod";
            Query q = getDbAccessor().getSession().createQuery(querySql);
            q.setString("loginName", loginName);
            q.setParameter("beginPeriod", resetBeginPeriod);
            q.setParameter("endPeriod", resetEndPeriod);
            //System.out.println(querySql);
            attendanceList = q.list();
            return attendanceList;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "Error when connect to " + ATTENDANCE_TABLE);
        }
    }

    /**
     * 取NonAtten表的所有NONDATE在beginPeriod和endPeriod之间的所有记录
     * @param loginName String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List getNonAttenList(String loginName, Date beginPeriod, Date endPeriod) {
        List nonAttenList = new ArrayList();
        try {
            Calendar begin = Calendar.getInstance();
            begin.setTime(beginPeriod);
            begin.set(Calendar.HOUR, 0);
            begin.set(Calendar.MINUTE, 0);
            begin.set(Calendar.SECOND, 0);
            begin.set(Calendar.MILLISECOND, 0);
            Calendar end = Calendar.getInstance();
            end.setTime(endPeriod);
            end.set(Calendar.HOUR, 23);
            end.set(Calendar.MINUTE, 59);
            end.set(Calendar.SECOND, 59);
            end.set(Calendar.MILLISECOND, 999);

            String querySql = "from " + NONATTEN_TABLE + " t where t.userId=:loginName ";
            String condition1 = "(t.timeFrom>=:beginPeriod and t.timeFrom<:endPeriod)";
            String condition2 = "(t.timeFrom<=:beginPeriod and t.timeTo>:beginPeriod)";
            String condition = " and ( " + condition1 + " or " + condition2 + " ) ";
            querySql = querySql + condition;

            Query q = this.getDbAccessor().getSession().createQuery(querySql);
            q.setString("loginName", loginName);
            q.setParameter("beginPeriod", begin.getTime());
            q.setParameter("endPeriod", end.getTime());
            nonAttenList = q.list();
            return nonAttenList;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "Error when connect to " + NONATTEN_TABLE);
        }
    }

    /**
     * 取leaveMain表的所有dateTimeStart,dateTimeFinish在beginPeriod和endPeriod之间的所有记录
     * @param loginName String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List getShiftLeaveList(String loginName, Date beginPeriod, Date endPeriod) {
        List shiftLeaveList = new ArrayList();
        try {
            Calendar begin = Calendar.getInstance();
            begin.setTime(beginPeriod);
            begin.set(Calendar.HOUR, 0);
            begin.set(Calendar.MINUTE, 0);
            begin.set(Calendar.SECOND, 0);
            begin.set(Calendar.MILLISECOND, 0);
            Calendar end = Calendar.getInstance();
            end.setTime(endPeriod);
            end.set(Calendar.HOUR, 23);
            end.set(Calendar.MINUTE, 59);
            end.set(Calendar.SECOND, 59);
            end.set(Calendar.MILLISECOND, 999);
            String querySql = "from " + LEAVEMAIN_TABLE + " t where t.loginId=:loginName"
                              + " and t.status=:status";
            String condition1 = "(t.actualDatetimeStart>=:beginPeriod and t.actualDatetimeStart<:endPeriod)";
            String condition2 = "(t.actualDatetimeStart<=:beginPeriod and t.actualDatetimeFinish>:beginPeriod)";
            String condition = " and ( " + condition1 + " or " + condition2 + " ) ";
            querySql = querySql + condition;
            Query q = this.getDbAccessor().getSession().createQuery(querySql);
            q.setString("loginName", loginName);
            q.setString("status", Constant.STATUS_FINISHED);
            q.setParameter("beginPeriod", begin.getTime());
            q.setParameter("endPeriod", end.getTime());
            shiftLeaveList = q.list();
            return shiftLeaveList;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "Error when connect to " + LEAVEMAIN_TABLE);
        }
    }

    /**
     * 获取未审核的请假记录(状态为Applying或Reviewing)
     * @param loginName String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List getNoConfirmedLeaveList(String loginName, Date beginPeriod, Date endPeriod) {
        List shiftLeaveList = new ArrayList();
        try {
            Calendar begin = Calendar.getInstance();
            begin.setTime(beginPeriod);
            begin.set(Calendar.HOUR, 0);
            begin.set(Calendar.MINUTE, 0);
            begin.set(Calendar.SECOND, 0);
            begin.set(Calendar.MILLISECOND, 0);
            Calendar end = Calendar.getInstance();
            end.setTime(endPeriod);
            end.set(Calendar.HOUR, 23);
            end.set(Calendar.MINUTE, 59);
            end.set(Calendar.SECOND, 59);
            end.set(Calendar.MILLISECOND, 999);
            String querySql = "from " + LEAVEMAIN_TABLE + " t where t.loginId=:loginName"
                              + " and (t.status=:status1 or t.status=:status2)";
            String condition1 = "(t.actualDatetimeStart>=:beginPeriod and t.actualDatetimeStart<:endPeriod)";
            String condition2 = "(t.actualDatetimeStart<=:beginPeriod and t.actualDatetimeFinish>:beginPeriod)";
            String condition = " and ( " + condition1 + " or " + condition2 + " ) ";
            querySql = querySql + condition;
            Query q = this.getDbAccessor().getSession().createQuery(querySql);
            q.setString("loginName", loginName);
            q.setString("status1", Constant.STATUS_APPLYING); //状态为Applying
            q.setString("status2", Constant.STATUS_REVIEWING); //状态为Reviewing
            q.setParameter("beginPeriod", begin.getTime());
            q.setParameter("endPeriod", end.getTime());
            shiftLeaveList = q.list();
            return shiftLeaveList;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "Error when connect to " + LEAVEMAIN_TABLE, ex);
        }
    }

    /**
     * 取overTime表的所有dateTimeStart,dateTimeFinish在beginPeriod和endPeriod之间的所有记录
     * @param loginName String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List getOverTimeList(String loginName, Date beginPeriod, Date endPeriod) {
        List overTimeList;
        //将开始时间的时分秒都设为0
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginPeriod);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        beginPeriod = cal.getTime();
        //将结束时间的设置为23:59:59:999
        cal = Calendar.getInstance();
        cal.setTime(endPeriod);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        endPeriod = cal.getTime();

        LgOverTime lg = new LgOverTime();
        overTimeList = lg.listPeriodFinishedOverTime(loginName, beginPeriod, endPeriod);
        return overTimeList;

//        try {
//            Calendar begin=Calendar.getInstance();
//            begin.setTime(beginPeriod);
//            begin.set(Calendar.HOUR,0);
//            begin.set(Calendar.MINUTE,0);
//            Calendar end=Calendar.getInstance();
//            end.setTime(endPeriod);
//            end.set(Calendar.HOUR,23);
//            end.set(Calendar.MINUTE,59);
//            end.set(Calendar.MILLISECOND,999);
//            String querySql = "from " + OVERTIME_TABLE + " t where t.loginId=:loginName"
//                              + " and status=:status";
//            String condition1 = "(t.actualDatetimeStart>=:beginPeriod and t.actualDatetimeStart<:endPeriod)";
//            String condition2 = "(t.actualDatetimeStart<=:beginPeriod and t.actualDatetimeFinish>:beginPeriod)";
//            String condition = " and ( " + condition1 + " or " + condition2 + " ) ";
//            querySql = querySql + condition;
//            Query q = this.getDbAccessor().getSession().createQuery(querySql);
//            q.setString("loginName", loginName);
//            q.setString("status", Constant.STATUS_FINISHED);
//            q.setParameter("beginPeriod", begin.getTime());
//            q.setParameter("endPeriod", end.getTime());
//            overTimeList = q.list();
//            return overTimeList;
//        } catch (Exception ex) {
//            log.error(ex);
//            throw new BusinessException("Error", "Error when connect to " + OVERTIME_TABLE);
//        }
    }

    /**
     * 获取未审核的加班记录(状态为Applying或Reviewing)
     * @param loginName String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List getNoConfirmedOverTimeList(String loginName, Date beginPeriod, Date endPeriod) {
        List overTimeList;
        //将开始时间的时分秒都设为0
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginPeriod);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        beginPeriod = cal.getTime();
        //将结束时间的设置为23:59:59:999
        cal = Calendar.getInstance();
        cal.setTime(endPeriod);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        endPeriod = cal.getTime();

        try {
            String querySql = "from " + OVERTIME_TABLE + " t where t.loginId=:loginName"
                              + " and (status=:status1 or status=:status2)";
            String condition1 = "(t.actualDatetimeStart>=:beginPeriod and t.actualDatetimeStart<:endPeriod)";
            String condition2 = "(t.actualDatetimeStart<=:beginPeriod and t.actualDatetimeFinish>:beginPeriod)";
            String condition = " and ( " + condition1 + " or " + condition2 + " ) ";
            querySql = querySql + condition;
            Query q = this.getDbAccessor().getSession().createQuery(querySql);
            q.setString("loginName", loginName);
            q.setString("status1", Constant.STATUS_APPLYING);
            q.setString("status2", Constant.STATUS_REVIEWING);
            q.setParameter("beginPeriod", beginPeriod);
            q.setParameter("endPeriod", endPeriod);
            overTimeList = q.list();
            return overTimeList;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "Error when connect to " + OVERTIME_TABLE);
        }

    }

    /**
     * 根据leaveName获取leave的方式（全薪/半薪/无薪）
     * @param leaveName String
     * @return List
     */
    public List getSettlementWay(String leaveName) {
        List settlementWayList = new ArrayList();
        try {
            String querySql = "from " + LEAVETYPE_TABLE + " t where t.leaveName=:leaveName";
            Query q = this.getDbAccessor().getSession().createQuery(querySql);
            q.setString("leaveName", leaveName);
            settlementWayList = q.list();
            return settlementWayList;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "Error when connect to " + LEAVETYPE_TABLE);
        }
    }

    /**
     * 获取未审核的周/月报信息
     * @param loginName String
     * @param beginPeriod Date
     * @param endPeriod Date
     */
    public String getWMReportStatus(String loginName, Date beginPeriod, Date endPeriod){
        StringBuffer reportStatus=new StringBuffer();
        if(this.wmflag==DtoWeeklyReportSumByHr.ON_WEEK){
            reportStatus.append(getWeeklyReportStatus(loginName, beginPeriod,endPeriod));
        }
        else{
             Calendar c_begin=Calendar.getInstance();
             c_begin.setTime(beginPeriod);
             Calendar c_end=Calendar.getInstance();
             c_end.setTime(endPeriod);
             ArrayList weeks = (ArrayList) WorkCalendar.getBEWeekListMax(c_begin, c_end);
             for(int i=0;i<weeks.size();i++){
                 Calendar aweekBegin = ((Calendar[]) weeks.get(i))[0];
                 Calendar aweekEnd=((Calendar[]) weeks.get(i))[1];
                 //System.out.println(comDate.dateToString(aweekBegin.getTime()));
                 reportStatus.append(getWeeklyReportStatus(loginName,aweekBegin.getTime(),aweekEnd.getTime()));
             }
        }
        return reportStatus.toString();
    }

    /**
     * 获取未审核的周报信息
     * @param loginName String
     * @param beginPeriod Date
     * @return String
     */
    public String getWeeklyReportStatus(String loginName,Date beginPeriod,Date endPeriod){
        Date begin=LgTcCommon.resetBeginDate(beginPeriod);
        String s_begin=comDate.dateToString(begin,"yyyyMMdd");
        //System.out.println(s_begin);
        ResultSet resultSet;
        StringBuffer result=new StringBuffer();
        String querySql="select wt.acnt_rid,wt.confirm_status from "
                        +WEEKLYREPORT_TABLE
                        +" wt where wt.user_id='"+loginName
                        +"' and to_char(wt.begin_period ,'yyyymmdd')='"+s_begin+"'";
        //System.out.println(querySql);
        try{
            resultSet = this.getDbAccessor().executeQuery(querySql);

            String beginStr=comDate.dateToString(beginPeriod,"yyyy-MM-dd");
            result.append(beginStr);
            result.append("~");
            //如果是同一年,显示格式为yyyy-MM-dd~MM-dd,如果不是同一年,显示格式为yyyy-MM-dd~yyyy-MM-dd
            result.append(handlerDate(beginPeriod,endPeriod));
            result.append(",");

            if(!resultSet.next()){//未填周报,则记录未填周报信息
                 result.append(No_Filled_WeeklyReport);
            }else{//填写了周报,则判断周报是否被锁定及是否被确认
                String status=resultSet.getString("confirm_status");
                if(status.equalsIgnoreCase(STATUS_UNLOCK)){//如果周报未锁定
                  result.append(No_Locked_WeeklyReport) ;
                 }else if(!status.equalsIgnoreCase(STATUS_CONFIRMED)){//如果周报未确认
                  Long accountRid=new Long(resultSet.getLong("acnt_rid"));
                  IAccountUtil accountUtil=AccountFactory.create();
                  IDtoAccount account=accountUtil.getAccountByRID(accountRid);
                  String accountName=account.getDisplayName();//获取项目名
                  result.append(accountName);
                  //获得项目经理
                  String manager = account.getManager();
                  if(manager != null)
                      manager = HrFactory.create().getChineseName(manager);
                  result.append("(PM:"+manager+") ");
                  result.append(No_Confirmed_WeeklyReport);
                 }else{//如果周报被确认就不记录
                     return "";
                 }
              }
            result.append(";  \n");
            return result.toString();
        }catch(Exception ex){
            throw new BusinessException(ex);
        }

    }

    /**
     * 获取未审核的差勤信息
     * @param loginName String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return String
     */
    public String getAttendanceReportStatus(String loginName, Date beginPeriod, Date endPeriod){
        StringBuffer ARSresult=new StringBuffer(' ');//存放处理结果
        List NCLeaveList;
        List NCOvertimeList;
        //获取未审核的请假记录
        NCLeaveList=getNoConfirmedLeaveList(loginName,beginPeriod,endPeriod);
        if(NCLeaveList!=null&&NCLeaveList.size()>0){
            for(int i=0;i<NCLeaveList.size();i++){
                TcLeaveMain tcLeaveMain = (TcLeaveMain)NCLeaveList.get(i);
                Date start=tcLeaveMain.getActualDatetimeStart();
                String sstart=comDate.dateToString(start,"yyyy-MM-dd HH:mm");
                Date end=tcLeaveMain.getActualDatetimeFinish();
                double totalHours=tcLeaveMain.getActualTotalHours().doubleValue();
                ARSresult.append(No_Confirmed_Leave);//写入"请假未批准"
                ARSresult.append(" ( ");
                ARSresult.append(sstart);
                ARSresult.append("~");
                ARSresult.append(handlerDateTime(start,end));
                ARSresult.append(", ");
                ARSresult.append(totalHours);
                ARSresult.append(HOURS);
                ARSresult.append(" ); \n");
            }
        }

        //获取未审核的加班记录
        NCOvertimeList=getNoConfirmedOverTimeList(loginName,beginPeriod,endPeriod);
        if(NCOvertimeList!=null&&NCOvertimeList.size()>0){
            for(int j=0;j<NCOvertimeList.size();j++){
                TcOvertime tcOverTime = (TcOvertime) NCOvertimeList.get(j);
                Date start=tcOverTime.getActualDatetimeStart();
                String sstart=comDate.dateToString(start,"yyyy-MM-dd HH:mm");
                Date end=tcOverTime.getActualDatetimeFinish();
                double totalHours=tcOverTime.getActualTotalHours().doubleValue();
                ARSresult.append(No_Confirmed_Overtime);//写入"加班未批准"
                ARSresult.append(" ( ");
                ARSresult.append(sstart);
                ARSresult.append("~");
                ARSresult.append(handlerDateTime(start,end));
                ARSresult.append(", ");
                ARSresult.append(totalHours);
                ARSresult.append(HOURS);
                ARSresult.append(" ); \n");
            }
        }
        return ARSresult.toString();
    }

    /**
     * 对显示的时间段进行处理:
     * 如果是同一天,则显示格式为:yyyy-MM-dd HH:mm~HH:mm;
     * 否则如果两个日期是同一年,则显示格式为:yyyy-MM-dd HH:mm~MM-dd HH:mm;
     * 否则,显示格式为:yyyy-MM-dd HH:mm~yyyy-MM-dd HH:mm
     * @param start Date
     * @param end Date
     * @return String
     */
    public String handlerDateTime(Date start,Date end){
        if(start==null||end==null) return "";
        String timeStart = comDate.dateToString(start, "yyyy-MM-dd");
        String timeFinish = comDate.dateToString(end, "yyyy-MM-dd");
        String start_year = comDate.dateToString(start, "yyyy");
        String finish_year = comDate.dateToString(end, "yyyy");
        if (timeStart.equalsIgnoreCase(timeFinish)) { //如果是同一天
             return comDate.dateToString(end, "HH:mm");
          }else if (start_year.equalsIgnoreCase(finish_year)) { //如果是同一年
             return comDate.dateToString(end, "MM-dd HH:mm");
           } else {
              return comDate.dateToString(end, "yyyy-MM-dd HH:mm");
         }


    }
    /**
     * 对显示的时间段进行处理:
     * 如果两个日期是同一年,则显示格式为:yyyy-MM-dd~MM-dd;
     * 否则,显示格式为:yyyy-MM-dd~yyyy-MM-dd
     * @param start Date
     * @param end Date
     * @return String
     */

    public String handlerDate(Date start,Date end){
        if(start==null||end==null) return "";
        String timeStart = comDate.dateToString(start, "yyyy-MM-dd");
        String timeFinish = comDate.dateToString(end, "yyyy-MM-dd");
        String start_year = comDate.dateToString(start, "yyyy");
        String finish_year = comDate.dateToString(end, "yyyy");
      if (start_year.equalsIgnoreCase(finish_year)) { //如果是同一年
             return comDate.dateToString(end, "MM-dd");
           } else {
              return comDate.dateToString(end, "yyyy-MM-dd");
         }


    }


    public static void main(String[] args) throws Exception {
//        Date begin = new Date(1036660252125L); //Thu Nov 07 17:10:52 CST 2002
        Calendar calendar = Calendar.getInstance();
        calendar.set(2007, 01, 26);
        Date begin = calendar.getTime();
        calendar.set(2007, 02, 02);
        Date end =calendar.getTime();
        System.out.println("begin:" + begin);

        System.out.println("begin:" + begin);
        System.out.println("end:" + end);
        LgTcExcel lgTcExcel = new LgTcExcel();
        List l = lgTcExcel.getShiftLeaveList("WH0607014",begin,end);
        //List result = lgTcExcel.AllDetailList(new Long(6024), begin, end, "week");
        lgTcExcel.AllDetailList(new Long(883),begin,end,"month");

    }

}
