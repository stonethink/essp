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
public interface IWkDefine {
    public String getWorkflowName();
    public List getActivityDefineList();
    public IActivityDefine getActivityDefineMap();
    public List getNextActivityList(IActivityDefine currentActivityDefine);
    public Hashtable getParameter();

    public void setActivityDefineList(List activityDefineList);

    public void setActivityDefineMap(IActivityDefine activityDefineMap);

    public void setWorkflowName(String workflowName);

    public void setParameter(Hashtable parameter);
}
