package server.essp.timesheet.period.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import server.essp.common.primavera.PrimaveraApiBase;
import c2s.dto.DtoUtil;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.object.TimesheetPeriod;
import com.primavera.integration.util.WhereClauseHelper;

/**
 * <p>Title: PeriodDaoImp</p>
 *
 * <p>Description: 具体实现对日期区间的操作</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class PeriodDaoImp extends PrimaveraApiBase implements IPeriodDao{
     private final static Map propertyMap = new HashMap();
     /**
      * 获取传入日期所在的TimeSheetPeriod
      * @param day Date
      * @return DtoTimeSheetPeriod
      */
    public DtoTimeSheetPeriod getTimeSheetPeriod(Date day) throws Exception {
        if(day == null) return null;
        DtoTimeSheetPeriod dtoPeriod = null;
        String strDate = WhereClauseHelper.formatDate(day);
        String strWhere = strDate + " >= StartDate and " + strDate + " <= EndDate";
        BOIterator iter = this.getGOM().loadTimesheetPeriods(TimesheetPeriod.getAllFields(), strWhere, null);
        if(iter.hasNext()) {
            TimesheetPeriod period = (TimesheetPeriod) iter.next();
            dtoPeriod = new DtoTimeSheetPeriod();
            DtoUtil.copyBeanToBeanWithPropertyMap(dtoPeriod, period, propertyMap);
        }
        return dtoPeriod;

    }

    /**
     * 向后移动一个日期区间
     * @param endDate Date
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getNextPeriod(Date endDate) throws Exception {
        if(endDate == null) return null;
        DtoTimeSheetPeriod dtoPeriod = null;
        String strDate = WhereClauseHelper.formatDate(endDate);
        String strWhere = "StartDate > " + strDate;

        BOIterator iter = this.getGOM().loadTimesheetPeriods(TimesheetPeriod.getAllFields(), strWhere, "StartDate asc");
        if(iter.hasNext()) {
           TimesheetPeriod period = (TimesheetPeriod) iter.next();
           dtoPeriod = new DtoTimeSheetPeriod();
           DtoUtil.copyBeanToBeanWithPropertyMap(dtoPeriod, period, propertyMap);
       }

        return dtoPeriod;
    }
    /**
     * 向后移动step个日期区间
     * @param endDate Date
     * @param step int
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getNextPeriod(Date endDate, int step) throws Exception {
        if(endDate == null) return null;
        DtoTimeSheetPeriod dtoPeriod = null;
        String strDate = WhereClauseHelper.formatDate(endDate);
        int iStep = step;
        String strWhere = "";
        String orderBy = "StartDate asc";
        if(step == 0) {
        	strWhere = "StartDate <= " + strDate 
        					+ " and " + "EndDate >= " + strDate;
        } else if(step > 0) {
        	strWhere = "StartDate > " + strDate;
        } else {
        	strWhere = "EndDate < " + strDate;
        	iStep = -step;
        	orderBy = "StartDate desc";
        }

        BOIterator iter = this.getGOM().loadTimesheetPeriods(TimesheetPeriod.getAllFields(), strWhere, orderBy);
        for(int i = 1; i < iStep && iter.hasNext(); i ++) {
        	iter.next();
        }
        if(iter.hasNext()) {
           TimesheetPeriod period = (TimesheetPeriod) iter.next();
           dtoPeriod = new DtoTimeSheetPeriod();
           DtoUtil.copyBeanToBeanWithPropertyMap(dtoPeriod, period, propertyMap);
       }

        return dtoPeriod;
    }
    
    /**
     * 向前移动一个日期区间
     * @param startDate Date
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getPrePeriod(Date startDate) throws Exception {
        if(startDate == null) return null;
         DtoTimeSheetPeriod dtoPeriod = null;
         String strDate = WhereClauseHelper.formatDate(startDate);
         String strWhere = "EndDate < " + strDate;

         BOIterator iter = this.getGOM().loadTimesheetPeriods(TimesheetPeriod.getAllFields(), strWhere, "EndDate desc");
         if(iter.hasNext()) {
            TimesheetPeriod period = (TimesheetPeriod) iter.next();
            dtoPeriod = new DtoTimeSheetPeriod();
            DtoUtil.copyBeanToBeanWithPropertyMap(dtoPeriod, period, propertyMap);
        }

         return dtoPeriod;

    }
    
    /**
     * 根据开始和结束日期获取工时单区间
     * @param begin
     * @param end
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getPeriod(Date begin, Date end) throws Exception {
    	if(begin == null || end == null) return null;
        DtoTimeSheetPeriod dtoPeriod = null;
        String strBeginDate = WhereClauseHelper.formatDate(begin);
        String strEndDate = WhereClauseHelper.formatDate(end);
        String strWhere = "StartDate = " + strBeginDate + " and EndDate = " + strEndDate;
        BOIterator iter = this.getGOM().loadTimesheetPeriods(TimesheetPeriod.getAllFields(),
                strWhere, "EndDate desc");
        if(iter.hasNext()) {
            TimesheetPeriod period = (TimesheetPeriod) iter.next();
            dtoPeriod = new DtoTimeSheetPeriod();
            DtoUtil.copyBeanToBeanWithPropertyMap(dtoPeriod, period, propertyMap);
        }
        return dtoPeriod;
    }
    /**
     * 获取全部区间列表
     */
	public List getAllTimeSheetPeriod() throws Exception {
		List resultList = new ArrayList();
		DtoTimeSheetPeriod dtoPeriod = null;
		BOIterator iter = this.getGOM().loadTimesheetPeriods(TimesheetPeriod.getAllFields(), null, "StartDate asc");
		while(iter.hasNext()){
			dtoPeriod = new DtoTimeSheetPeriod();
			TimesheetPeriod period = (TimesheetPeriod) iter.next();
			DtoUtil.copyBeanToBeanWithPropertyMap(dtoPeriod, period, propertyMap);
			resultList.add(dtoPeriod);
		}
		return resultList;
	}
	/**
	 * 获取最后一个工时单区间
	 */
	public DtoTimeSheetPeriod getLastEndPeriod() throws Exception {
		DtoTimeSheetPeriod dtoPeriod = null;
		BOIterator iter = this.getGOM().loadTimesheetPeriods(TimesheetPeriod.getAllFields(), null, "EndDate desc");
		if(iter.hasNext()) {
            TimesheetPeriod period = (TimesheetPeriod) iter.next();
            dtoPeriod = new DtoTimeSheetPeriod();
            DtoUtil.copyBeanToBeanWithPropertyMap(dtoPeriod, period, propertyMap);
        }
		return dtoPeriod;
	}
	/**
	 * 查询一个区间内是否与别的工时单区间重叠
	 */
	public boolean isDuplicate(Date startDate, Date endDate) throws Exception {
        String strStartDate = WhereClauseHelper.formatDate(startDate);
        String strEndDate = WhereClauseHelper.formatDate(endDate);
        String strWhere = "(StartDate >= " + strStartDate + " and StartDate <= " + strEndDate + ")"
                         + " or (EndDate >= " + strStartDate + " and EndDate <= " + strEndDate + ")"
                         + " or (StartDate <= " + strStartDate + " and EndDate >= " + strEndDate + ")";
        BOIterator iter = this.getGOM().loadTimesheetPeriods(TimesheetPeriod.getAllFields(), strWhere, null);
        if(iter.hasNext()) {
        	return true;
        }
		return false;
	}
    
     /**
     * 根据时间段查询在这时间段内的周期
     * @param begin
     * @param end
     * @return List
     * @throws Exception
     */
    public List getPeriodByDate(Date begin,Date end) throws Exception {
        List resultList = new ArrayList();
        DtoTimeSheetPeriod dtoPeriod = null;
        String beginStr = WhereClauseHelper.formatDate(begin);
        String endStr = WhereClauseHelper.formatDate(end);
        String strWhere = beginStr + " <= StartDate and " + endStr + " >= EndDate";
        BOIterator iter = this.getGOM().loadTimesheetPeriods(TimesheetPeriod.getAllFields(),
                strWhere, "StartDate asc");
        while(iter.hasNext()){
            dtoPeriod = new DtoTimeSheetPeriod();
            TimesheetPeriod period = (TimesheetPeriod) iter.next();
            DtoUtil.copyBeanToBeanWithPropertyMap(dtoPeriod, period, propertyMap);
            resultList.add(dtoPeriod);
        }
        return resultList;
    }
    
    static {
          propertyMap.put("objectId", "tsId");
          propertyMap.put("startDate", "beginDate");
          propertyMap.put("endDate", "endDate");
   }
    
    


}
