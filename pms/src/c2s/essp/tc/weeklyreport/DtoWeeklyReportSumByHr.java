package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;
import java.util.Date;

public class DtoWeeklyReportSumByHr extends DtoAllocateHour implements IDtoAllocateHourIncLeaveDetail{
    public final static String ON_WEEK = "week";
    public final static String ON_MONTH = "month";
    private String chineseName;
    private String locked;
    private Date realBeginPeriod;
    private Date realEndPeriod;

    private BigDecimal standardHour;
    private BigDecimal absent; //����
    private Long violat; //Υ��

    private BigDecimal leaveOfPayAll; //ȫн��
    private BigDecimal leaveOfPayAllConfirmed; //ȫн�٣�����׼�˵ģ�
    private BigDecimal leaveOfPayHalf; //��н��
    private BigDecimal leaveOfPayHalfConfirmed; //��н�٣�����׼�˵ģ�
    private BigDecimal leaveOfPayNone; //����н��
    private BigDecimal leaveOfPayNoneConfirmed; //����н�٣�����׼�˵ģ�
    public String getLocked() {
        return locked;
    }

    public Date getRealBeginPeriod() {

        return realBeginPeriod;
    }

    public Date getRealEndPeriod() {

        return realEndPeriod;
    }

    public BigDecimal getAbsent() {
        return absent;
    }

    public Long getViolat() {
        return violat;
    }

    public BigDecimal getStandardHour() {
        return standardHour;
    }

    public BigDecimal getLeaveOfPayAll() {
        return leaveOfPayAll;
    }

    public BigDecimal getLeaveOfPayHalf() {
        return leaveOfPayHalf;
    }

    public BigDecimal getLeaveOfPayNone() {
        return leaveOfPayNone;
    }

    public BigDecimal getLeaveOfPayAllConfirmed() {
        return leaveOfPayAllConfirmed;
    }

    public BigDecimal getLeaveOfPayHalfConfirmed() {
        return leaveOfPayHalfConfirmed;
    }

    public BigDecimal getLeaveOfPayNoneConfirmed() {
        return leaveOfPayNoneConfirmed;
    }

    public String getChineseName() {
        return chineseName;
    }

    public BigDecimal getNormalHours(){

        BigDecimal actualHourConfirmed = this.getActualHourConfirmed();
        BigDecimal overtimeConfirmed = this.getOvertimeSumConfirmed();

        return subtract(actualHourConfirmed, overtimeConfirmed);
    }

    public BigDecimal getVarientHours(){
                BigDecimal standardHours = this.getStandardHour();
        BigDecimal normalHours = getNormalHours();
        BigDecimal leaveHours = this.getLeaveSumConfirmed();
        BigDecimal absentHours = this.getAbsent();

        //normalHours + leaveHours + absentHours - standardHours
        return add( normalHours,
                    add( leaveHours,
                         subtract( absentHours, standardHours ) ) );
    }

    public BigDecimal getPayTimes(){
        BigDecimal normalHours = getNormalHours();
        BigDecimal leaveHoursOfPayAll = this.getLeaveOfPayAllConfirmed();
        BigDecimal leaveHoursOfPayHalf = this.getLeaveOfPayHalfConfirmed();
        BigDecimal absentHours = this.getAbsent();

        if( leaveHoursOfPayHalf == null ){
            leaveHoursOfPayHalf = new BigDecimal(0);
        }else{
            leaveHoursOfPayHalf = leaveHoursOfPayHalf.divide(new BigDecimal(2), 1, BigDecimal.ROUND_HALF_UP);
        }

        //normalHours + leaveHoursOfPayAll + leaveHoursOfPayHalf/2 - absentHours
        //modify by xr,��н��ʱ���ڱ�׼��ʱʱ�Ա�׼��ʱΪ׼
        BigDecimal payHours = add( normalHours,
                                   add(leaveHoursOfPayAll,
                                       subtract(leaveHoursOfPayHalf, absentHours)));
        if(standardHour != null && payHours.compareTo(standardHour) >= 1)
            return standardHour;
        return payHours;
    }

    private BigDecimal add(BigDecimal left , BigDecimal right ){
        if( left == null ){
            left = new BigDecimal(0);
        }
        if( right == null ){
            right = new BigDecimal(0);
        }
        return left.add(right);
    }

    private BigDecimal subtract(BigDecimal left , BigDecimal right ){
        if( left == null ){
            left = new BigDecimal(0);
        }
        if( right == null ){
            right = new BigDecimal(0);
        }
        return left.subtract(right);
    }


    public void setLocked(String locked) {
        this.locked = locked;
    }

    public void setRealBeginPeriod(Date realBeginPeriod) {

        this.realBeginPeriod = realBeginPeriod;
    }

    public void setRealEndPeriod(Date realEndPeriod) {

        this.realEndPeriod = realEndPeriod;
    }

    public void setAbsent(BigDecimal absent) {
        this.absent = absent;
    }

    public void setViolat(Long violat) {
        this.violat = violat;
    }

    public void setStandardHour(BigDecimal standardHour) {
        this.standardHour = standardHour;
    }

    public void setLeaveOfPayAll(BigDecimal leaveOfPayAll) {
        this.leaveOfPayAll = leaveOfPayAll;
    }

    public void setLeaveOfPayHalf(BigDecimal leaveOfPayHalf) {
        this.leaveOfPayHalf = leaveOfPayHalf;
    }

    public void setLeaveOfPayNone(BigDecimal leaveOfPayNone) {
        this.leaveOfPayNone = leaveOfPayNone;
    }

    public void setLeaveOfPayNoneConfirmed(BigDecimal leaveOfPayNoneConfirmed) {
        this.leaveOfPayNoneConfirmed = leaveOfPayNoneConfirmed;
    }

    public void setLeaveOfPayHalfConfirmed(BigDecimal leaveOfPayHalfConfirmed) {
        this.leaveOfPayHalfConfirmed = leaveOfPayHalfConfirmed;
    }

    public void setLeaveOfPayAllConfirmed(BigDecimal leaveOfPayAllConfirmed) {
        this.leaveOfPayAllConfirmed = leaveOfPayAllConfirmed;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

}
