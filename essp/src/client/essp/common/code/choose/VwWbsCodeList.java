package client.essp.common.code.choose;

import c2s.dto.InputInfo;
import c2s.essp.common.code.DtoKey;
import client.essp.common.view.VWWorkArea;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwWbsCodeList extends VwCodeChooseList{
    static final String actionIdList =   "F00WbsCodeListAction";
    static final String actionIdUpdate = "F00WbsCodeUpdateAction";
    static final String actionIdDelete = "F00WbsCodeDeleteAction";

    private Long acntRid;
    private Long wbsRid;

    protected InputInfo createDeleteInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdDelete);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.acntRid);
        inputInfo.setInputObj(DtoKey.WBS_RID, this.wbsRid);

        return inputInfo;
    }

    protected InputInfo createUpdateInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.acntRid);
        inputInfo.setInputObj(DtoKey.WBS_RID, this.wbsRid);

        return inputInfo;
    }

    protected InputInfo createSelectInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdList);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.acntRid);
        inputInfo.setInputObj(DtoKey.WBS_RID, this.wbsRid);

        return inputInfo;
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.acntRid = (Long) (param.get(DtoKey.ACNT_RID));
        this.wbsRid = (Long) (param.get(DtoKey.WBS_RID));

        if( this.acntRid != null && this.wbsRid != null ){
            isParameterValid = true;
        }else{
            isParameterValid = false;
        }
    }

    public String getCodeType(){
        return DtoKey.TYPE_WBS;
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
