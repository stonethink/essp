package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import c2s.dto.DtoBase;
import com.wits.util.comDate;
import server.essp.pms.account.code.ActivityCodeConfig;

public class DtoWeeklyReport extends DtoHoursOnWeek{
    public static final int STANDARD_HOUR_ONE_DAY = 8;

    public static final String STATUS_NONE = "None";
    public static final String STATUS_UNLOCK = "UnLocked";
    public static final String STATUS_LOCK = "Locked";
    public static final String STATUS_CONFIRMED = "Confirmed";
    public static final String STATUS_REJECTED = "Rejected";

    public static final BigDecimal BIG_DECIMAL_0 = new BigDecimal(0);
    public static final BigDecimal BIG_DECIMAL_0_0 = new BigDecimal(0.0);

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Long acntRid;
    private String acntName;

    /** nullable persistent field */
    private Long activityRid;
    private String activityName;

    /** nullable persistent field */
    private Long codeValueRid;
    private String codeValueName;

    /** nullable persistent field */
    private String isActivity;

    /** nullable persistent field */
    private String jobDescription;

    /** nullable persistent field */
    private String confirmStatus;

    /** nullable persistent field */
    private String comments;

    //为了维护表TcByWorkerAccount，赠加几个属性，记录修改前的值
    private Long oldAcntRid;
    private BigDecimal oldSatHour = null;
    private BigDecimal oldSunHour = null;
    private BigDecimal oldMonHour = null;
    private BigDecimal oldTueHour = null;
    private BigDecimal oldWedHour = null;
    private BigDecimal oldThuHour = null;
    private BigDecimal oldFriHour = null;

    private boolean weeklyReportSum = false;

    public Long getActivityRid() {
        return activityRid;
    }

    public Long getCodeValueRid() {
        return codeValueRid;
    }

    public String getComments() {
        return comments;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public Long getRid() {
        return rid;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getAcntName() {
        return acntName;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getCodeValueName() {
        return codeValueName;
    }

    public Long getOldAcntRid() {
        return oldAcntRid;
    }

    public BigDecimal getOldFriHour() {
        return oldFriHour;
    }

    public BigDecimal getOldMonHour() {
        return oldMonHour;
    }

    public BigDecimal getOldSatHour() {
        return oldSatHour;
    }

    public BigDecimal getOldSunHour() {
        return oldSunHour;
    }

    public BigDecimal getOldThuHour() {
        return oldThuHour;
    }

    public BigDecimal getOldTueHour() {
        return oldTueHour;
    }

    public BigDecimal getOldWedHour() {
        return oldWedHour;
    }

    public String getIsActivity() {
        return isActivity;
    }

    public boolean isWeeklyReportSum() {
        return weeklyReportSum;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setActivityRid(Long activityRid) {
        this.activityRid = activityRid;
    }

    public void setCodeValueRid(Long codeValueRid) {
        this.codeValueRid = codeValueRid;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setCodeValueName(String codeValueName) {
        this.codeValueName = codeValueName;
    }

    public void setOldAcntRid(Long oldAcntRid) {
        this.oldAcntRid = oldAcntRid;
    }

    public void setOldWedHour(BigDecimal oldWedHour) {
        this.oldWedHour = oldWedHour;
    }

    public void setOldTueHour(BigDecimal oldTueHour) {
        this.oldTueHour = oldTueHour;
    }

    public void setOldThuHour(BigDecimal oldThuHour) {
        this.oldThuHour = oldThuHour;
    }

    public void setOldSunHour(BigDecimal oldSunHour) {
        this.oldSunHour = oldSunHour;
    }

    public void setOldSatHour(BigDecimal oldSatHour) {
        this.oldSatHour = oldSatHour;
    }

    public void setOldMonHour(BigDecimal oldMonHour) {
        this.oldMonHour = oldMonHour;
    }

    public void setOldFriHour(BigDecimal oldFriHour) {
        this.oldFriHour = oldFriHour;
    }

    public void setIsActivity(String isActivity) {
        this.isActivity = isActivity;
    }

    public void setWeeklyReportSum(boolean weeklyReportSum) {
        this.weeklyReportSum = weeklyReportSum;
    }

    public String getActivityDisp(){
        if( doesActivity() ){
            return getActivityName();
        }else{
            return getCodeValueName();
        }
    }

    public String getPeriodInfo(){
        if( getBeginPeriod() != null && getEndPeriod() != null ){
            String beginStr = comDate.dateToString(getBeginPeriod());
            String endStr = comDate.dateToString(getEndPeriod());
            return beginStr + " ~ " + endStr;
        }else{
            return null;
        }
    }

    public boolean isRejected(){
        if( STATUS_REJECTED.equals(this.confirmStatus) ){
            return true;
        }else{
            return false;
        }
    }

    public boolean isNoneStatus(){
        if( confirmStatus == null || this.confirmStatus.equals(STATUS_NONE) ){
            return true;
        }else{
            return false;
        }
    }

    public boolean isConfirmed(){
        if( STATUS_CONFIRMED.equals(this.confirmStatus) ){
            return true;
        }else{
            return false;
        }
    }

    public boolean isUnLocked(){
        if( STATUS_UNLOCK.equals(this.confirmStatus) ){
            return true;
        }else{
            return false;
        }
    }

    public boolean isLocked(){
        if( STATUS_LOCK.equals(this.confirmStatus) ){
            return true;
        }else{
            return false;
        }
    }

    public boolean doesActivity(){
        if( "0".equals(isActivity) ){
            return false;
        }else{
            return true;
        }
    }

    public static String generalConfirmStatus(String one, String other) {

        //按none < unLock < lock < confirm,reject的顺序取小值
        List ary = new ArrayList(5);
        ary.add(DtoWeeklyReport.STATUS_NONE);
        ary.add(DtoWeeklyReport.STATUS_UNLOCK);
        ary.add(DtoWeeklyReport.STATUS_LOCK);
        ary.add(DtoWeeklyReport.STATUS_CONFIRMED);
        ary.add(DtoWeeklyReport.STATUS_REJECTED);
        int i = ary.indexOf(one);
        int j = ary.indexOf(other);
        if (i == -1 || j == -1) {
            return DtoWeeklyReport.STATUS_NONE;
        }
        if (i == j) {
            return one;
        }
        if (i + j == 7) {
            //i=3,j=4 或i=4,j=3
            return DtoWeeklyReport.STATUS_LOCK;
        }

        //取小的
        int min = Math.min(i, j);
        return (String) ary.get(min);
    }

}
