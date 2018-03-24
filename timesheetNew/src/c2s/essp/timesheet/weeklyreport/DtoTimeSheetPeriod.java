package c2s.essp.timesheet.weeklyreport;

import java.util.Date;
import java.util.List;

import c2s.dto.DtoBase;
import java.util.Calendar;
import java.util.ArrayList;
import c2s.essp.common.calendar.WorkCalendar;

public class DtoTimeSheetPeriod extends DtoBase {

    public static String DTO_MOVE_FLAG = "Ts_DtoMoveFlag";
    public static String DTO_DAY = "Ts_DtoDay";
    public static String DTO_PERIOD = "Ts_DtoPeriod";
    public static String DTO_BEGIN = "Ts_DtoBegin";
    public static String DTO_END = "Ts_DtoEnd";
    public static String DTO_TsId = "Ts_DtoTsId";

    public static String MOVE_FLAG_NEXT = "next";
    public static String MOVE_FLAG_PRE = "pre";
    
    public static String CREATE_WAY_EVERYWEEK = "Ts_DtoCreateWayEveryWeek";
    public static String CREATE_WAY_EVERYTWOWEEKS = "Ts_DtoCreateWayEveryTwoWeeks";
    public static String CREATE_WAY_EVERYFOURWEEKS = "Ts_DtoCreateWayEveryFourWeeks";
    public static String CREATE_WAY_EVERYMONTH = "Ts_DtoCreateWayEveryMonth";
    
    public static String CREATE_CONDITION = "Ts_DtoCreateCondition";
    
    public static String DTO_TsDastesList = "Ts_DtoTsDatesList";

    private Long tsId;
    private Date beginDate;
    private Date endDate;

    public Long getTsId() {
        return tsId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List getDateList() {
        return period2DateList(getBeginDate(), getEndDate());
    }

    public static List<Date> period2DateList(Date beginDate, Date endDate) {
        List dateList = new ArrayList();
        if(beginDate == null || endDate == null) {
            return dateList;
        }

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        while(!begin.after(end)) {
            dateList.add(begin.getTime());
            begin = WorkCalendar.getNextDay(begin);
        }
        return dateList;
    }

    public void setTsId(Long tsId) {
        this.tsId = tsId;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
