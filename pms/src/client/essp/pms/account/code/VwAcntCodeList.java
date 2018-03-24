package client.essp.pms.account.code;

import c2s.dto.InputInfo;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.common.code.choose.VwCodeChooseList;
import client.essp.common.view.VWWorkArea;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwAcntCodeList extends VwCodeChooseList{
    static final String actionIdList =   "FAccountCodeListAction";
    static final String actionIdUpdate = "FAccountCodeUpdateAction";
    static final String actionIdDelete = "FAccountCodeDeleteAction";

    private Long acntRid;

    protected InputInfo createDeleteInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdDelete);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.acntRid);

        return inputInfo;
    }

    protected InputInfo createUpdateInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.acntRid);

        return inputInfo;
    }

    protected InputInfo createSelectInputInfo(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdList);

        inputInfo.setInputObj(DtoKey.ACNT_RID, this.acntRid);

        return inputInfo;
    }

    public void setParameter(Parameter param) {
        DtoPmsAcnt pmsAcc = (DtoPmsAcnt)param.get("dtoAccount");
        boolean isReadOnly = true;
        if( pmsAcc != null ){
            this.acntRid = pmsAcc.getRid();
            if( pmsAcc.isManagement() ){
                isReadOnly = false;
            }
        }else{
            acntRid = null;
        }

        if( this.acntRid != null ){
            isParameterValid = true;
        }else{
            isParameterValid = false;
        }

        Parameter p = new Parameter();
        p.put("isReadonly", new Boolean(isReadOnly));
        super.setParameter(p);
    }

    public String getCodeType(){
        return DtoKey.TYPE_ACNT;
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwAcntCodeList();

        w1.addTab("Code", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoKey.ACNT_RID, new Long(1));
        param.put(DtoKey.CATALOG, "project");
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
