package server.essp.common.calendar.logic;

import c2s.essp.common.calendar.IWorkCalendarUtil;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.HashMap;
import java.util.List;
import server.framework.logic.AbstractBusinessLogic;
import server.framework.common.BusinessException;
import c2s.essp.common.calendar.DtoTcWt;
import java.util.ArrayList;
import java.util.Calendar;
import javax.sql.RowSet;

public class WrokCalendarUtilImplS extends AbstractBusinessLogic implements
    IWorkCalendarUtil {
  static final String SERVER_WORK_CALENDAR = "S";
  static final String DEFAULT_WORK_CALENDAR_KEY = SERVER_WORK_CALENDAR + "+++";
  static final String WORK_DAY_CONFIG_TABLE = "TC_WTS";
  //modified by xr 2006/02/24
  //静态变量不合适,
  //static HashMap hashMap = new HashMap();
  private HashMap hashMap = new HashMap();

  public WrokCalendarUtilImplS() {
  }

  public void setWorkCalendar(String calendarId, WorkCalendar workCalendar) {
//    String sKey = SERVER_WORK_CALENDAR + "+++" + calendarId;
    String sKey = DEFAULT_WORK_CALENDAR_KEY;
    hashMap.put(sKey, workCalendar);
  }

  public WorkCalendar getWorkCalendar(String calendarId) {
    String sKey = DEFAULT_WORK_CALENDAR_KEY;
    if(hashMap.containsKey(sKey)){
      return (WorkCalendar) hashMap.get(sKey);
    }else{
      return  initWorkCalendar(calendarId);
    }
  }

  public WorkCalendar initWorkCalendar(String calendarId) {
    String sKey = DEFAULT_WORK_CALENDAR_KEY;
    List list = listYearWorkDayConfig(calendarId);
    WorkCalendar wc = new WorkCalendar(list);
    hashMap.put(sKey, wc);
    return wc;
  }

  public List listYearWorkDayConfig(String calendarId) throws BusinessException {
    Calendar calendar = Calendar.getInstance();
    int iYear = calendar.get(Calendar.YEAR);
    return listYearWorkDayConfig(calendarId, iYear - 1, iYear + 1);
  }

  public List listYearWorkDayConfig(String calendarId, int iBeginYear, int iEndYear) throws
      BusinessException {
    String sql = "select WTS_YEAR as wtsYear,trim(WTS_DAYS) as wtsDays from "
                 + WORK_DAY_CONFIG_TABLE
                 + " where WTS_YEAR >= '" + iBeginYear + "'"
                 + " and  WTS_YEAR <= '" + iEndYear + "'"
//                 + " and  RST = 'N' "
                 + " order by WTS_YEAR";
    log.info(sql);

    try {
//      List result = this.getDbAccessor().executeQueryToList(sql, DtoTcWt.class);
      List result = new ArrayList();
      RowSet rs = this.getDbAccessor().executeQuery(sql);
      while (rs.next()) {
        DtoTcWt dtoTcWt = result2DtoTcWt(rs);
        result.add(dtoTcWt);
      }

      return result;
    } catch (Exception ex) {
      log.error(ex);
      throw new BusinessException();
    }
  }

  private DtoTcWt result2DtoTcWt(RowSet rs) throws
      BusinessException {
    DtoTcWt dto = new DtoTcWt();
    try {
      dto.setWtsYear(new Integer(rs.getInt("wtsYear")));
      dto.setWtsDays(rs.getString("wtsDays"));
    } catch (Exception ex) {
      throw new BusinessException(ex);
    }
    return dto;
  }

  public static void main(String[] args) {
    WrokCalendarUtilImplS impl = new WrokCalendarUtilImplS();
    List list = impl.listYearWorkDayConfig("");
  }


}
