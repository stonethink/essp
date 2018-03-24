package client.essp.cbs.budget;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.budget.DtoBudgetLog;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWUtil;
import java.awt.Dimension;

public class VwBudgetLog extends VwBudgetLogBase  implements  IVWPopupEditorEvent{
    static final String actionIdLog = "FCbsBudgetLogAction";
    private DtoBudgetLog budgetLog;
    private String budgetType;
    private boolean needRefreshParent = false;
    public VwBudgetLog() {
        super();
    }
    public void resetUI(){
        VWUtil.bindDto2UI(budgetLog,this);
        if(CbsConstant.ANTICIPATED_BUDGET.equals(budgetType)){
            setBaseVisible(false);
        }else{
            setBaseVisible(true);
        }
        needRefreshParent = false;
    }
    private void setBaseVisible(boolean b){

        lbBaseAmt.setVisible(b);
        lbBaseId.setVisible(b);
        lbBasePm.setVisible(b);

        inputBaseAmt.setVisible(b);
        inputBaseID.setVisible(b);
        inputBasePM.setVisible(b);

        if(!b){
            this.setPreferredSize(new Dimension(430,160));
            lbCurrentPm.setBounds(new Rectangle(20, 20, 88, 20));
            lbCurrentAmt.setBounds(new Rectangle(210, 20, 99, 20));

            inputCurrentPm.setBounds(new Rectangle(105, 20, 90, 20));
            inputCurrentAmt.setBounds(new Rectangle(315, 20, 90, 20));

            lbChangedPm.setBounds(new Rectangle(20, 50, 79, 20));
            lbChangedAmt.setBounds(new Rectangle(210, 50, 91, 20));

            inputChangedPm.setBounds(new Rectangle(105, 50, 90, 20));
            inputChangedAmt.setBounds(new Rectangle(315, 50, 90, 20));

            lbChangedBy.setBounds(new Rectangle(20, 80, 77, 20));
            lgChangedDate.setBounds(new Rectangle(210, 80, 85, 20));

            inputChangedBy.setBounds(new Rectangle(105, 80, 90, 20));
            inputChangedDate.setBounds(new Rectangle(315, 80, 90, 20));

            lbChangeReason.setBounds(new Rectangle(20, 110, 82, 20));

            inputChangedReason.setBounds(new Rectangle(105, 110, 300, 40));
        }
    }
    public boolean onClickOK(ActionEvent e) {
        return saveData();
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public DtoBudgetLog getBudgetLog() {
        return budgetLog;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetLog(DtoBudgetLog budgetLog) {
        this.budgetLog = budgetLog;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    private boolean saveData(){
        if(budgetLog == null)
            return true;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdLog);
        inputInfo.setInputObj("budgetLog",budgetLog);
        ReturnInfo returnInfo = accessData(inputInfo);
        if( !returnInfo.isError() ){
            needRefreshParent = true;
            return true;
        }
        return false;
    }
    public boolean needRefreshParent(){
        return needRefreshParent;
    }
}
