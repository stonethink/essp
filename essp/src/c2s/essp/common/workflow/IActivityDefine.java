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
public interface IActivityDefine {

    public static String MODE_AUTO = "A";
    public static String MODE_MENU = "M";

    public String getActivityID();
    public String getActivityName();
    public IParticipant getParticipant();
    public String getProcessClassName();

    public List getChildren();
    public List getParents();

    public boolean isStart();
    public boolean isEnd();

    public String getStartMode();
    public String getEndMode();

    public void setStart(boolean start);

    public void setEnd(boolean end);

    public void setActivityName(String activityName);

    public void setChildren(List children);

    public void setParents(List parents);

    public void setParticipant(IParticipant participant);

    public void setProcessClassName(String processClassName);

    public void setStartMode(String startMode);

    public void setEndMode(String endMode);

    public void setActivityID(String activityID);

}
