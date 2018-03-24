package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;
import java.util.Date;

import c2s.dto.DtoBase;

/**
 * 分别含有从星期六，星期天，....，星期五的工作时间数据,及这些数据的汇总数据
 */
public class DtoHoursOnWeek extends DtoBase{
    public static final int SATURDAY = 0;
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SUMMARY = 7;

    private String userId;
    private Date beginPeriod; //这个星期的开始日期
    private Date endPeriod;//这个星期的结束日期

    private BigDecimal satHour;

    private BigDecimal sunHour;

    private BigDecimal monHour;

    private BigDecimal tueHour;

    private BigDecimal wedHour;

    private BigDecimal thuHour;

    private BigDecimal friHour;

    private BigDecimal sumHour;

    public BigDecimal getFriHour() {
        return friHour;
    }

    public BigDecimal getMonHour() {
        return monHour;
    }

    public BigDecimal getSatHour() {
        return satHour;
    }

    public BigDecimal getSumHour() {
        return sumHour;
    }

    public BigDecimal getSunHour() {
        return sunHour;
    }

    public BigDecimal getThuHour() {
        return thuHour;
    }

    public BigDecimal getTueHour() {
        return tueHour;
    }

    public BigDecimal getWedHour() {
        return wedHour;
    }

    public Date getBeginPeriod() {
        return beginPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public String getUserId() {
        return userId;
    }

    public void setWedHour(BigDecimal wedHour) {
        this.wedHour = wedHour;
    }

    public void setTueHour(BigDecimal tueHour) {
        this.tueHour = tueHour;
    }

    public void setThuHour(BigDecimal thuHour) {
        this.thuHour = thuHour;
    }

    public void setSunHour(BigDecimal sunHour) {
        this.sunHour = sunHour;
    }

    public void setSumHour(BigDecimal sumHour) {
        this.sumHour = sumHour;
    }

    public void setSatHour(BigDecimal satHour) {
        this.satHour = satHour;
    }

    public void setMonHour(BigDecimal monHour) {
        this.monHour = monHour;
    }

    public void setFriHour(BigDecimal friHour) {
        this.friHour = friHour;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public BigDecimal[] getHourArray(){
        return new BigDecimal[]{this.satHour, sunHour, monHour, tueHour, wedHour, thuHour,friHour};
    }

    public BigDecimal getHour(int day){
        switch (day) {
        case SATURDAY:
            return getSatHour();
        case SUNDAY:
            return getSunHour();
        case MONDAY:
            return getMonHour();
        case TUESDAY:
            return getTueHour();
        case WEDNESDAY:
            return getWedHour();
        case THURSDAY:
            return getThuHour();
        case FRIDAY:
            return getFriHour();
        case SUMMARY:
            return getSumHour();
        default:
            return null;
        }
    }

    public void setHour(int day, BigDecimal hour) {
        switch (day) {
        case DtoWeeklyReport.SATURDAY:
            setSatHour(hour);
            break;
        case DtoWeeklyReport.SUNDAY:
            setSunHour(hour);
            break;
        case DtoWeeklyReport.MONDAY:
            setMonHour(hour);
            break;
        case DtoWeeklyReport.TUESDAY:
            setTueHour(hour);
            break;
        case DtoWeeklyReport.WEDNESDAY:
            setWedHour(hour);
            break;
        case DtoWeeklyReport.THURSDAY:
            setThuHour(hour);
            break;
        case DtoWeeklyReport.FRIDAY:
            setFriHour(hour);
            break;
        case DtoWeeklyReport.SUMMARY:
            setSumHour(hour);
            break;
        default:

        }
    }

    public DtoHoursOnWeek add(int day, BigDecimal added){
        if( added != null ){
            BigDecimal hour = getHour(day);
            if (hour == null) {
                this.setHour(day, added);
            } else {
                this.setHour(day, hour.add(added));
            }
        }
        return this;
    }

    public DtoHoursOnWeek subtract(int day, BigDecimal subtracted){
        if( subtracted != null ){
            BigDecimal hour = getHour(day);
            if (hour == null) {
                this.setHour(day, subtracted.negate());
            } else {
                this.setHour(day, hour.subtract(subtracted));
            }
        }
        return this;
    }

}
