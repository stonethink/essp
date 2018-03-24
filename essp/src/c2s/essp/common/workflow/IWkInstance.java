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
public interface IWkInstance {
    public static String WK_INSTANCE_STATUS_START = "start";
    public static String WK_INSTANCE_STATUS_PROCESS = "process";
    public static String WK_INSTANCE_STATUS_END = "end";

    public Long getInstanceID();
    public String getInstanceName();
    public String getSubEmpLoginID();
    public Date getStartDate();
    public Date getFinishDate();
    public String getStatus();
    public Long getSteps();
    //public

    public void setFinishDate(Date finishDate);

    public void setInstanceID(Long instanceID);

    public void setInstanceName(String instanceName);

    public void setStartDate(Date startDate);

    public void setStatus(String status);

    public void setSubEmpLoginID(String subEmpLoginID);

    public void setSteps(Long steps);

}
