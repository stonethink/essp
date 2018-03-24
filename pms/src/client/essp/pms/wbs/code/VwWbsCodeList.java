package client.essp.pms.wbs.code;

import c2s.dto.InputInfo;
import c2s.essp.common.code.DtoKey;
import client.essp.common.view.VWWorkArea;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.essp.common.code.choose.VwCodeChooseList;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoKEY;

public class VwWbsCodeList extends VwCodeChooseList{
    static final String actionIdList =   "FWbsCodeListAction";
    static final String actionIdUpdate = "FWbsCodeUpdateAction";
    static final String actionIdDelete = "FWbsCodeDeleteAction";

    DtoWbsActivity dtoActivity;

    protected InputInfo createDeleteInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdDelete);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.getAcntRid());
        inputInfo.setInputObj(DtoKey.WBS_RID, this.getWbsRid());

        return inputInfo;
    }

    protected InputInfo createUpdateInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.getAcntRid());
        inputInfo.setInputObj(DtoKey.WBS_RID, this.getWbsRid());

        return inputInfo;
    }

    protected InputInfo createSelectInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdList);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.getAcntRid());
        inputInfo.setInputObj(DtoKey.WBS_RID, this.getWbsRid());

        return inputInfo;
    }

    public void setParameter(Parameter param) {
        ITreeNode treeNode = (ITreeNode) param.get(DtoKEY.WBSTREE);

        dtoActivity = (DtoWbsActivity) treeNode.getDataBean();
        if (dtoActivity != null) {
            Parameter p = new Parameter();
            p.put("isReadonly", new Boolean(dtoActivity.isReadonly()));
            super.setParameter(p);
            isParameterValid = true;
        } else {
            super.setParameter(new Parameter());
            isParameterValid = false;
        }
    }

    public String getCodeType(){
        return DtoKey.TYPE_WBS;
    }

    protected boolean getIsReadOnly(){
        if( this.dtoActivity != null ){
            return dtoActivity.isReadonly();
        }else{
            return true;
        }
    }

    private Long getAcntRid(){
        if( this.dtoActivity != null ){
            return dtoActivity.getAcntRid();
        }else{
            return null;
        }
    }

    private Long getWbsRid(){
        if( this.dtoActivity != null ){
            return dtoActivity.getWbsRid();
        }else{
            return null;
        }
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwWbsCodeList();

        w1.addTab("Code", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoKey.ACNT_RID, new Long(1));
        param.put(DtoKey.WBS_RID, new Long(12));
        param.put(DtoKey.CATALOG, "project");
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
