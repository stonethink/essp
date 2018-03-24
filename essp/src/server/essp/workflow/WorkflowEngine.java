package server.essp.workflow;

import java.util.Date;
import java.util.List;
import itf.workflow.IWorkflowEngine;
import itf.workflow.IActivityParticipant;
import c2s.essp.common.workflow.IParticipant;
import c2s.essp.common.workflow.IWkInstance;
import c2s.essp.common.workflow.IActivity;
import c2s.essp.common.workflow.IWkDefine;
import c2s.essp.common.workflow.IActivityDefine;
import c2s.essp.workflow.DtoWkInstance;
import c2s.essp.workflow.DtoWkActivity;

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
public class WorkflowEngine implements IWorkflowEngine {
    public WorkflowEngine() {
    }

    public IWkInstance createWKInstance(String sInstanceName,
                                        String sSubEmpLoginID,
                                        IWkDefine iwkDefine) {

        IWkInstance iwInstance = new DtoWkInstance();
        iwInstance.setInstanceName(sInstanceName);
        iwInstance.setSubEmpLoginID(sSubEmpLoginID);
        iwInstance.setStartDate(new Date());

        if ( iwkDefine.getActivityDefineMap().getStartMode().equals(IActivityDefine.MODE_AUTO) ) {

        }


        return null;
    }

    public IWkInstance createWKInstance(IWkInstance iwInstance,
                                        IWkDefine iwkDefine) {
        return null;
    }

    public IWkInstance proceWKInstance(Long lInstanceID, String sWKStatus,
                                       IWkDefine iwkDefine) {
        return null;
    }

    public IWkInstance proceWKInstance(IWkInstance iwInstance,
                                       IWkDefine iwkDefine) {
        return null;
    }

    public void closeWKInstance(Long lInstanceID) {
    }

    public void closeWKInstance(Long lInstanceID, Date dWKFinishDate) {
    }

    public IActivity startActivity(String sCurrentEmpLoginID,
                                   IWkDefine iwkDefine) {
        return null;
    }

    public IActivity startActivity(IActivity iActivity, IWkDefine iwkDefine) {
        return null;
    }

    public IActivity proceAcitivity(Long lActivityID, String sCurrentEmpLoginID,
                                    String sStatus, IWkDefine iwkDefine) {
        return null;
    }

    public IActivity proceAcitivity(IActivity iActivity, IWkDefine iwkDefine) {
        return null;
    }

    public IWkInstance loadWKInstance(Long lInstanceID) {
        return null;
    }

    public List loadWKInstanceList(String sSubEmpLoginID) {
        return null;
    }

    public IActivity loadActivity(Long lInstanceID) {
        return null;
    }

    public List loadActivityList(Long lWKInstanceID) {
        return null;
    }

    private IActivity createActivityFromDefine( IWkDefine iwkDefine , IActivityDefine currentActivityDef ){
        IActivityParticipant iactivtiyPart = new Participant();
        IParticipant iPart = iactivtiyPart.loadParticipant(iwkDefine,currentActivityDef);
        IActivity iActivity = new DtoWkActivity();
        iActivity.setCurrentEmpLoginID(iPart.getLoginID());
        iActivity.setProcessClassName(currentActivityDef.getProcessClassName());
        return iActivity;
    }


}
