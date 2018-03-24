package server.essp.timesheet.period.service;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import server.essp.timesheet.period.dao.IPeriodDao;
import java.util.Date;
import java.util.List;

import server.framework.common.BusinessException;

/**
 * <p>Title: PeriodServiceImp</p>
 *
 * <p>Description: 日历操作逻辑具体实现</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class PeriodServiceImp implements IPeriodService{

    private IPeriodDao periodDao;
    /**
     * 查询某一日期所在的日期区间
     * @param day Date
     * @return DtoTimeSheetPeriod
     */
    public DtoTimeSheetPeriod queryPeriodByDay(Date day) {
        try {
            return periodDao.getTimeSheetPeriod(day);
        } catch (Exception ex) {
            throw new BusinessException("error.logic.common.getPeriodErr",
                                        "get period error", ex);
        }
    }

    public void setPeriodDao(IPeriodDao periodDao) {
        this.periodDao = periodDao;
    }
    /**
     * 向后或向前移动一个日期区间
     * @param date Date
     * @param moveWay String
     * @return DtoTimeSheetPeriod
     */
    public DtoTimeSheetPeriod moveToNextOrPrePeriod(Date date, String moveWay) {
        DtoTimeSheetPeriod period = null;
        try {
            if ("next".equals(moveWay)) {
                period = periodDao.getNextPeriod(date);
            } else if ("pre".equals(moveWay)) {
                period = periodDao.getPrePeriod(date);
            }
        } catch (Exception ex) {
            throw new BusinessException("error.logic.PeriodServiceImp.movePeriodErr",
                                        "move period error", ex);
        }
        return period;
    }
    
    /**
     * 向后移动step个日期区间
     * @param endDate Date
     * @param step int
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getNextPeriod(Date endDate, int step) {
    	try {
			return periodDao.getNextPeriod(endDate, step);
		} catch (Exception e) {
			throw new BusinessException("error.logic.PeriodServiceImp.getNext",
                    "move period error", e);
		}
    }
    /**
     * 根据日期区间查询工时单区间
     */
	public List getPeriodByDate(Date begin, Date end) {
		try {
			return periodDao.getPeriodByDate(begin, end);
		} catch (Exception e) {
			throw new BusinessException("error.logic.tsDates.getPeriodErr",
                    "get period list error", e);
		}
	}
}
