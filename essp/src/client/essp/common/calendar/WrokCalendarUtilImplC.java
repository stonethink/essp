package client.essp.common.calendar;

import c2s.essp.common.calendar.IWorkCalendarUtil;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.HashMap;
import java.util.List;
import c2s.essp.common.calendar.DtoTcWt;
import java.util.ArrayList;
import java.util.Calendar;
import c2s.dto.TransactionData;
import c2s.dto.InputInfo;
import client.net.ConnectFactory;
import client.net.NetConnector;
import c2s.dto.ReturnInfo;

public class WrokCalendarUtilImplC implements
    IWorkCalendarUtil {
  static final String CLIENT_WORK_CALENDAR = "C";
  static final String DEFAULT_WORK_CALENDAR_KEY = CLIENT_WORK_CALENDAR + "+++";

  static HashMap hashMap = new HashMap();

  public WrokCalendarUtilImplC() {
  }

  public void setWorkCalendar(String calendarId, WorkCalendar workCalendar) {
//    String sKey = SERVER_WORK_CALENDAR + "+++" + calendarId;
    String sKey = DEFAULT_WORK_CALENDAR_KEY;
    hashMap.put(sKey, workCalendar);
  }

  public WorkCalendar getWorkCalendar(String calendarId) {
    String sKey = DEFAULT_WORK_CALENDAR_KEY;
    if (hashMap.containsKey(sKey)) {
      return (WorkCalendar) hashMap.get(sKey);
    } else {
      return initWorkCalendar(calendarId);
    }
  }

  public WorkCalendar initWorkCalendar(String calendarId) {
    String sKey = DEFAULT_WORK_CALENDAR_KEY;
    List list = listYearWorkDayConfig(calendarId);
    WorkCalendar wc = new WorkCalendar(list);
    hashMap.put(sKey, wc);
    return wc;
  }

  public List listYearWorkDayConfig(String calendarId) {
    String actionId = "F00_CalendarGetWorkDayConfig";
    String funId = "";
    List list = null;

    TransactionData transData = new TransactionData();
    InputInfo inputInfo = transData.getInputInfo();
    inputInfo.setActionId(actionId);
    inputInfo.setFunId(funId);

    inputInfo.setInputObj("calendarId", calendarId);

    NetConnector connector = ConnectFactory.createConnector();
    connector.accessData(transData);

    ReturnInfo returnInfo = transData.getReturnInfo();
    if (returnInfo.isError() == false) {
      list = (List) returnInfo.getReturnObj("listYearWorkDayConfig");
    }
    return list;
  }

  public static void main(String[] args) {
    WrokCalendarUtilImplC impl = new WrokCalendarUtilImplC();
    List list = impl.listYearWorkDayConfig("");
  }
}
