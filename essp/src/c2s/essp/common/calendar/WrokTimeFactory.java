package c2s.essp.common.calendar;


public class WrokTimeFactory {
  public static final String CLIENT_WORK_TIME = "C";
  public static final String SERVER_WORK_TIME = "S";

  static final String clinetImplClassName =
      "client.essp.common.calendar.WrokTimeUtilImplC";
  static final String serverImplClassName =
      "server.essp.common.calendar.logic.WrokTimeUtilImplS";

  public WrokTimeFactory() {
  }

  public static WorkTime clientCreate() {
    return create(CLIENT_WORK_TIME);
  }

  public static WorkTime serverCreate() {
    return create(SERVER_WORK_TIME);
  }

  public static WorkTime create(String type) {
    return create(type, null);
  }

  public static WorkTime clientCreate(String workTimeId) {
    return create(CLIENT_WORK_TIME, workTimeId);
  }

  public static WorkTime serverCreate(String workTimeId) {
    return create(SERVER_WORK_TIME, workTimeId);
  }

  public static WorkTime create(String type, String workTimeId) {
    IWorkTimeUtil implObject = null;
    String implClassName = "";

    if (CLIENT_WORK_TIME.equals(type)) {
      implClassName = clinetImplClassName;
    } else {
      implClassName = serverImplClassName;
    }

    try {
        Class implClass = Class.forName(implClassName);
        implObject = (IWorkTimeUtil) implClass.
                     newInstance();
    } catch (Exception ex) {
        ex.printStackTrace();
        return null;
    }

    WorkTime wc = implObject.getWorkTime(workTimeId);
    return wc;
  }


  public static void main(String[] args) {
    String workTimeId = "";
//    WorkTime workTime = WrokTimeFactory.clientCreate(workTimeId);
    WorkTime workTime = WrokTimeFactory.serverCreate(workTimeId);

  }

}
