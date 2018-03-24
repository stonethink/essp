package client.essp.common.calendar;

import java.util.*;

import c2s.dto.*;
import c2s.essp.common.calendar.*;
import client.net.*;

public class WrokTimeUtilImplC implements IWorkTimeUtil {
  static final String CLIENT_WORK_TIME = "C";
  static final String DEFAULT_WORK_TIME_KEY = CLIENT_WORK_TIME + "+++";

  static HashMap hashMap = new HashMap();

  public WrokTimeUtilImplC() {
  }

  public void setWorkTime(String workTimeId, WorkTime workTime) {
    String sKey = DEFAULT_WORK_TIME_KEY;
    hashMap.put(sKey, workTime);
  }

  public WorkTime getWorkTime(String workTimeId) {
    String sKey = DEFAULT_WORK_TIME_KEY;
    if (hashMap.containsKey(sKey)) {
      return (WorkTime) hashMap.get(sKey);
    } else {
      return initWorkTime(workTimeId);
    }
  }

  public WorkTime initWorkTime(String workTimeId) {
    String sKey = DEFAULT_WORK_TIME_KEY;
    List list = listWorkTimeConfig(workTimeId);
    WorkTime wc = new WorkTime(list);
    hashMap.put(sKey, wc);
    return wc;
  }

  public List listWorkTimeConfig(String workTimeId) {
    String actionId = "F00_CalendarGetWorkTimeConfig";
    String funId = "";
    List list = null;

    TransactionData transData = new TransactionData();
    InputInfo inputInfo = transData.getInputInfo();
    inputInfo.setActionId(actionId);
    inputInfo.setFunId(funId);

    inputInfo.setInputObj("workTimeId", workTimeId);

    NetConnector connector = ConnectFactory.createConnector();
    connector.accessData(transData);

    ReturnInfo returnInfo = transData.getReturnInfo();
    if (returnInfo.isError() == false) {
      list = (List) returnInfo.getReturnObj("listWorkTimeConfig");
    }
    return list;
  }

  public static void main(String[] args) {
    WrokTimeUtilImplC impl = new WrokTimeUtilImplC();
    List list = impl.listWorkTimeConfig("");
  }
}
