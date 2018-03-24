package c2s.essp.common.workflow;

import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

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
public abstract class DtoWkDefine implements IWkDefine {

    private String workflowName = "" ;
    private List activityDefineList = new ArrayList();
    private IActivityDefine activityDefineMap = null;
    private Hashtable parameter = new Hashtable();

    public DtoWkDefine() {
        createActivityDefineList();
        createMap();
    }

    public String getWorkflowName() {
        return this.workflowName;
    }

    public List getActivityDefineList() {
        return this.activityDefineList;
    }

    public IActivityDefine getActivityDefineMap() {
        return this.activityDefineMap;
    }

    public void setActivityDefineList(List activityDefineList) {
        this.activityDefineList = activityDefineList;
    }

    public void setActivityDefineMap(IActivityDefine activityDefineMap) {
        this.activityDefineMap = activityDefineMap;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public Hashtable getParameter() {
        return this.parameter;
    }

    public void setParameter(Hashtable parameter) {
        this.parameter = parameter;
    }

    protected abstract void createActivityDefineList();
    protected abstract void createMap();


}
