package server.essp.timesheet.weeklyreport.dao;

import java.util.*;
import c2s.dto.DtoUtil;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import com.primavera.common.value.BeginDate;
import com.primavera.common.value.EndDate;
import com.primavera.common.value.ObjectId;
import com.primavera.common.value.UnitsPerTime;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.client.bo.object.Calendar;
import com.primavera.integration.client.bo.object.ResourceAssignment;
import com.primavera.integration.client.bo.object.ResourceRate;
import com.primavera.integration.client.bo.object.TimesheetPeriod;
import com.primavera.integration.util.WhereClauseHelper;
import server.essp.common.primavera.PrimaveraApiBase;
import c2s.essp.common.calendar.WorkCalendar;

/**
 *
 * <p>Title: TimeSheetP3ApiDaoImp</p>
 *
 * <p>Description: 与工时单相关访问P3 API的实现</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class TimeSheetP3ApiDaoImp extends PrimaveraApiBase implements ITimeSheetP3ApiDao {

    private final static Map propertyMap = new HashMap();

    /**
     * 获取传入日期所在的TimeSheetPeriod
     * @param day Date
     * @return DtoTimeSheetPeriod
     */
    public DtoTimeSheetPeriod getTimeSheetPeriod(Date day) throws Exception {
        if(day == null) return null;
        DtoTimeSheetPeriod dtoPeriod = null;
        Date begin = WorkCalendar.resetBeginDate(day);
        Date end = WorkCalendar.resetEndDate(day);
        String strBeginDate = WhereClauseHelper.formatDate(new BeginDate(begin));
        String strEndDate = WhereClauseHelper.formatDate(new BeginDate(end));
        String strWhere = strEndDate + " >= StartDate and " + strBeginDate + " <= EndDate";
        BOIterator iter = this.getGOM().loadTimesheetPeriods(TimesheetPeriod.getAllFields(), strWhere, null);
        if(iter.hasNext()) {
            TimesheetPeriod period = (TimesheetPeriod) iter.next();
            dtoPeriod = new DtoTimeSheetPeriod();
            DtoUtil.copyBeanToBeanWithPropertyMap(dtoPeriod, period, propertyMap);
        }
        return dtoPeriod;
    }

    public int getPeriodNum(Date begin, Date end) throws Exception {
        String strBegin = WhereClauseHelper.formatDate(new BeginDate(begin));
        String strEnd = WhereClauseHelper.formatDate(new EndDate(end));
        String strWhere = strEnd + " >= StartDate and " + strBegin + " <= EndDate";
        BOIterator iter = this.getGOM().loadTimesheetPeriods(TimesheetPeriod.getAllFields(), strWhere, null);
        return iter.getCount();
    }

    /**
     * 获取当前用户的Resource Id
     * @return Long
     */
    public Long getCurrentResourceId() throws Exception {
        return Long.valueOf(getCurrentResource().getObjectId().toInteger());
    }

    /**
     * 列出当前用户指定日期列表的标准工时，和它们的和
     * @param dateList List
     * @return List<Double>  size = dateList.size + 1 (sumHour)
     */
    public List listStandarHours(List<Date> dateList) {
        Date[] dates = getInOutDate(this.getLoginId());
        return listStandarHours(this.getCurrentCalendar(), dateList, dates[0], dates[1]);
    }
    public static long getCTime() {
		return new Date().getTime();
	}
    
  
    public Double getSumStandarHours(Calendar c, List<Date> dateList, Date inDate, Date outDate){
            Double sumHour = new Double(0);
            for(Date date : dateList) {
                if(date == null) {
                    log.warn("listStandarHours dateList exsit null data!");
                    break;
                }
                Double hour = new Double(0);
                if(inDate != null && date.before(inDate)) {
                    continue;
                }
                if(outDate != null && date.after(outDate)) {
                    continue;
                }
                try {
                    hour = c.getTotalWorkHours(date);
                } catch (BusinessObjectException ex) {
                    log.error(ex);
                }
                sumHour += hour;
            }
            return sumHour;
    }
    /**
     * 列出指定日期列表的标准工时，和它们的和
     * @param c
     * @param dateList
     * @return
     */
    private List listStandarHours(Calendar c, List<Date> dateList, Date inDate, Date outDate) {
        List hourList = new ArrayList();
        if(dateList == null || c == null) {
            return hourList;
        }
        Double sumHour = new Double(0);
        Date realOutDate = WorkCalendar.resetBeginDate(outDate);
        for(Date date : dateList) {
            if(date == null) {
                hourList.add(sumHour);
                log.warn("listStandarHours dateList exsit null data!");
                break;
            }
            Double hour = new Double(0);
            if(inDate != null && date.before(inDate)) {
                hourList.add(hour);
                continue;
            }
            if(outDate != null && !date.before(realOutDate)) {
                hourList.add(hour);
                continue;
            }
            try {
                hour = c.getTotalWorkHours(date);
            } catch (BusinessObjectException ex) {
                log.error(ex);
            }
            hourList.add(hour);
            sumHour += hour;
        }
        hourList.add(sumHour);
        return hourList;
    }
    
    /**
     * 获取指定用户指定指定时间的标准工时
     *  size = dateList.size + 1 (SumHour)
     * @param loginId
     * @param dateList
     * @return
     */
    public List listStandarHours(String loginId, List<Date> dateList) {
        Date[] dates = getInOutDate(loginId);
        return listStandarHours(this.getCalendar(loginId), dateList, dates[0], dates[1]);
    }
    
    /**
     * 获取资源的入职，离职日期
     *      入职日期：资源的数量和单价中最早的一个“单位时间最大量”不为0的“生效日期”
     *      离职日期：最晚一个“单位时间最大量”为0的“生效日期”，且需在入职日期之后。
     * @param loginId String
     * @return Date[2]
     * @throws Exception
     */
    public Date[] getInOutDate(String loginId) {
        Date[] dates = new Date[2];
        try {
            
            BOIterator iter = this.getResource(loginId).loadResourceRates(
                    new String[] {"EffectiveDate", "MaxUnitsPerTime"},
                    null, "EffectiveDate");
            ResourceRate rate = null;
            while(iter.hasNext()) {
                rate = (ResourceRate) iter.next();
                Date effDate = rate.getEffectiveDate();
                if(effDate == null) continue;
                UnitsPerTime unit = rate.getMaxUnitsPerTime();
                if(unit == null) continue;
                if(dates[0] == null && unit.doubleValue() > 0) {
                    
                    dates[0] = WorkCalendar.resetBeginDate(effDate);
                    continue;
                }
            }
            //最后一个为0
            if(rate != null) {
            	UnitsPerTime unit = rate.getMaxUnitsPerTime();
            	Date effDate = rate.getEffectiveDate();
            	if(unit != null && dates[0] != null && unit.doubleValue() == 0) {
                    dates[1] = WorkCalendar.resetEndDate(effDate);
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return dates;
    }

    /**
     * 根据DtoTimeSheetDetail中的activityId获取该作业的开始和结束时间
     *  根据DtoTimeSheetDetail中的rsrcAssignmentId获取该资源分配的计划开始和结束时间
     * @param dtoDetail DtoTimeSheetDetail
     */
    public void getActivityPlanDate(DtoTimeSheetDetail dtoDetail) throws Exception {
        if(dtoDetail == null) {
            return;
        }
        if(dtoDetail.getActivityId() != null) {
            Activity activity = Activity.load(getSession(),
                          new String[]{"StartDate","FinishDate"},
                          new ObjectId(dtoDetail.getActivityId()));
            if(activity == null) return;
            Date start = new Date(activity.getStartDate().getTime());
            Date finish = new Date(activity.getFinishDate().getTime());
            dtoDetail.setActivityStart(start);
            dtoDetail.setActivityFinish(finish);
            
        }
        if(dtoDetail.getRsrcAssignmentId() != null) {
            ResourceAssignment assignment = ResourceAssignment.load(getSession(), 
                    new String[]{"PlannedStartDate", "PlannedFinishDate"}, 
                    new ObjectId(dtoDetail.getRsrcAssignmentId()));
            Date start = new Date(assignment.getPlannedStartDate().getTime());
            Date finish = new Date(assignment.getPlannedFinishDate().getTime());
            dtoDetail.setActPlanStart(start);
            dtoDetail.setActPlanFinish(finish);
        }
    }


    static {
        propertyMap.put("objectId", "tsId");
        propertyMap.put("startDate", "beginDate");
        propertyMap.put("endDate", "endDate");
    }



    public static void main(String[] args) {
        TimeSheetP3ApiDaoImp api = new TimeSheetP3ApiDaoImp();
        try {
            DtoTimeSheetPeriod period = api.getTimeSheetPeriod(new Date());
            System.out.println(period.getBeginDate() + ":" + period.getEndDate());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
