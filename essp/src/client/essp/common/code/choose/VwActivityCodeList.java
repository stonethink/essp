package client.essp.common.code.choose;

import c2s.dto.InputInfo;
import c2s.essp.common.code.DtoKey;
import client.essp.common.view.VWWorkArea;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwActivityCodeList extends VwCodeChooseList{
    static final String actionIdList =   "F00ActivityCodeListAction";
    static final String actionIdUpdate = "F00ActivityCodeUpdateAction";
    static final String actionIdDelete = "F00ActivityCodeDeleteAction";

    private Long acntRid;
    private Long activityRid;

    protected InputInfo createDeleteInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdDelete);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.acntRid);
        inputInfo.setInputObj(DtoKey.ACTIVITY_RID, this.activityRid);

        return inputInfo;
    }

    protected InputInfo createUpdateInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.acntRid);
        inputInfo.setInputObj(DtoKey.ACTIVITY_RID, this.activityRid);

        return inputInfo;
    }

    protected InputInfo createSelectInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdList);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.acntRid);
        inputInfo.setInputObj(DtoKey.ACTIVITY_RID, this.activityRid);

        return inputInfo;
    }


    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.acntRid = (Long) (param.get(DtoKey.ACNT_RID));
        this.activityRid = (Long) (param.get(DtoKey.ACTIVITY_RID));

        if( this.acntRid != null && this.activityRid != null ){
            isParameterValid = true;
        }else{
            isParameterValid = false;
        }
    }

    public String getCodeType(){
        return DtoKey.TYPE_ACTIVITY;
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwActivityCodeList();

        w1.addTab("Code", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoKey.ACNT_RID, new Long(1));
        param.put(DtoKey.ACTIVITY_RID, new Long(1));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
