package server.essp.workflow;

import c2s.essp.common.workflow.DtoWkDefine;
import java.util.List;
import c2s.essp.common.workflow.DtoActivityDef;
import c2s.essp.common.workflow.StartActivity;
import c2s.essp.common.workflow.EndActivity;
import c2s.essp.common.workflow.IParticipant;
import c2s.essp.workflow.DtoWkParticipant;
import c2s.essp.common.workflow.IActivityDefine;
import java.util.ArrayList;

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
public class SampleWkDefine extends DtoWkDefine {

    StartActivity start = new StartActivity();
    DtoActivityDef pmProce = new DtoActivityDef();
    DtoActivityDef dmProce = new DtoActivityDef();
    DtoActivityDef gmProce = new DtoActivityDef();
    EndActivity end = new EndActivity();

    double iParameter_LeaveDays = 24.0;
    public static String PARAMETER_LEVEAL_DAY = "LeaveDay";

    public SampleWkDefine() {

        this.setWorkflowName("Leave(Sample Workflow)");
    }


    protected void createActivityDefineList(){

        DtoWkParticipant sPa = new DtoWkParticipant();
        sPa.setType(sPa.PART_TYPE_ANYPERSONAL);
        start.setActivityName("提交请假");
        start.setEndMode(start.MODE_AUTO);
        start.setParticipant(sPa);

        DtoWkParticipant pPa = new DtoWkParticipant();
        pmProce.setActivityName("项目经理审核");
        pPa.setType(pPa.PART_TYPE_PM_OF_SUBMITUSER);
        pmProce.setParticipant(pPa);

        DtoWkParticipant dPa = new DtoWkParticipant();
        dmProce.setActivityName("部门主管审核");
        dPa.setType(dPa.PART_TYPE_DM_OF_SUBMITUSER);
        dmProce.setParticipant(dPa);

        DtoWkParticipant gPa = new DtoWkParticipant();
        gmProce.setActivityName("部门主管审核");
        gPa.setType(gPa.PART_TYPE_GM_OF_SUBMITUSER);
        gmProce.setParticipant(gPa);

        this.getActivityDefineList().add(start);
        this.getActivityDefineList().add(pmProce);
        this.getActivityDefineList().add(dmProce);
        this.getActivityDefineList().add(gmProce);
        this.getActivityDefineList().add(end);

    }

    protected void createMap(){
        start.addChildren(pmProce);
        pmProce.addChildren(dmProce);
        dmProce.addChildren(gmProce);
        gmProce.addChildren(end);
        this.setActivityDefineMap(start);
    }

    public List getNextActivityList(IActivityDefine currentActivityDefine) {
        double iParam = ( (Double) this.getParameter().get(PARAMETER_LEVEAL_DAY)).doubleValue();
        ArrayList arryRtn = new ArrayList();

        if ( currentActivityDefine.getActivityID().equals(start.getActivityID()) ){
            arryRtn.add(pmProce);
            return arryRtn;
        }

        if ( currentActivityDefine.getActivityID().equals(pmProce.getActivityID()) ){
            arryRtn.add(dmProce);
            return arryRtn;
        }

        if ( currentActivityDefine.getActivityID().equals(gmProce.getActivityID())){
            arryRtn.add(end);
            return arryRtn;
        }

        if (currentActivityDefine.getActivityID().equals(end.getActivityID())) {
            return null;
        }

        if (currentActivityDefine.getActivityID().equals(dmProce.getActivityID())) {
            if (iParam >= iParameter_LeaveDays) {
                arryRtn.add(gmProce);
            } else {
                arryRtn.add(end);
            }
            return arryRtn;
        }

        return null;
    }


}
