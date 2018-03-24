package server.essp.common.calendar.logic;

import java.util.*;

import javax.sql.*;

import c2s.essp.common.calendar.*;
import server.framework.common.*;
import server.framework.logic.*;

public class WrokTimeUtilImplS extends AbstractBusinessLogic implements
    IWorkTimeUtil {
  static final String SERVER_WORK_TIME = "S";
  static final String DEFAULT_WORK_TIME_KEY = SERVER_WORK_TIME + "+++";
  static final String WORK_TIME_CONFIG_TABLE = "tc_worktime";
  //modified by xr 2006/02/24
  //静态变量不合适,
  //static HashMap hashMap = new HashMap();
  private HashMap hashMap = new HashMap();
  public WrokTimeUtilImplS() {
  }

  public void setWorkTime(String workTimeId, WorkTime workTime) {
//    String sKey = SERVER_WORK_TIME + "+++" + workTimeId;
    String sKey = DEFAULT_WORK_TIME_KEY;
    hashMap.put(sKey, workTime);
  }

  public WorkTime getWorkTime(String workTimeId) {
    String sKey = DEFAULT_WORK_TIME_KEY;
    if(hashMap.containsKey(sKey)){
      return (WorkTime) hashMap.get(sKey);
    }else{
      return  initWorkTime(workTimeId);
    }
  }

  public WorkTime initWorkTime(String workTimeId) {
    String sKey = DEFAULT_WORK_TIME_KEY;
    List list = listWorkTimeConfig(workTimeId);
    WorkTime wc = new WorkTime(list);
    hashMap.put(sKey, wc);
    return wc;
  }

  public List listWorkTimeConfig(String workTimeId) throws
      BusinessException {
    String sql = "select WT_STARTTIME as wtStarttime,WT_ENDTIME as wtEndtime from "
                 + WORK_TIME_CONFIG_TABLE
//                 + " where  RST = 'N' "
                 + " order by WT_STARTTIME";
    log.info(sql);

    try {
      List result = new ArrayList();
      RowSet rs = this.getDbAccessor().executeQuery(sql);
      while (rs.next()) {
        DtoWorkTimeItem dto = result2DtoWorkTimeItem(rs);
        result.add(dto);
      }
      return result;
    } catch (Exception ex) {
      log.error(ex);
      throw new BusinessException();
    }
  }

  private DtoWorkTimeItem result2DtoWorkTimeItem(RowSet rs) throws
      BusinessException {

    try {
        DtoWorkTimeItem dto = new DtoWorkTimeItem(rs.getString("wtStarttime"),
                                                  rs.getString("wtEndtime"));
        return dto;
    } catch (Exception ex) {
      throw new BusinessException(ex);
    }
  }

  public static void main(String[] args) {
    WrokTimeUtilImplS impl = new WrokTimeUtilImplS();
    List list = impl.listWorkTimeConfig("");
  }


}
