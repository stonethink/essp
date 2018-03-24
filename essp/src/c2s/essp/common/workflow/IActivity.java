package c2s.essp.common.workflow;

import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IActivity {

    public static String WK_ACTIVITY_STATUS_WAIT = "wait";
    public static String WK_ACTIVITY_STATUS_FINISH = "finish";
    public static String WK_ACTIVITY_STATUS_NONRICH = "unriched";

    public static String WK_ACTIVITY_TYPE_START = "start";
    public static String WK_ACTIVITY_TYPE_PROCESS = "process";
    public static String WK_ACTIVITY_TYPE_END = "end";

    public Long getInstanceID();
    public Long getActivityID();
    public String getProcessClassName();
    public String getCurrentEmpLoginID();
    public String getStatus();
    public Date getStartDate();
    public Date getFinishDate();
    public String getDefineID();

    public void setActivityID(Long activityID);

    public void setCurrentEmpLoginID(String currentEmpLoginID);

    public void setFinishDate(Date finishDate);

    public void setInstanceID(Long instanceID);

    public void setProcessClassName(String processClassName);

    public void setStartDate(Date startDate);

    public void setStatus(String status);

    public void setDefineID(String defineID);

}
