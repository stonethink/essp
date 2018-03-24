package client.essp.cbs.budget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;
import com.wits.util.Parameter;
import c2s.essp.cbs.budget.DtoBudgetParam;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import java.util.List;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import client.essp.common.view.VWTableWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;

public class VwBudgetLogList extends VWTableWorkArea {
    static final String actionIdList = "FCbsBugetLogListAction";
    private DtoBudgetParam budgetParam;

    private boolean isParamValidate = true;
    private List budgetLogList;
    public VwBudgetLogList() {
        super();
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        //设置标题栏位
        VWJReal inputReal = new VWJReal();
        inputReal.setMaxInputDecimalDigit(2);
        inputReal.setMaxInputIntegerDigit(8);
        inputReal.setCanNegative(true);
        Object[][] configs = new Object[][] {
                             {"Date", "logDate",VMColumnConfig.UNEDITABLE, new VWJDate()},
                             {"Total Budget(AMT)", "totalBugetAmt", VMColumnConfig.UNEDITABLE, inputReal},
                             {"Total Budget(PM)", "totalBugetPm", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Change Budget(AMT)", "changeBugetAmt", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Change Budget(PM)", "changeBugetPm", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Chaged By", "changedBy", VMColumnConfig.UNEDITABLE,new VWJText()},
                             {"Reason", "reson", VMColumnConfig.UNEDITABLE,new VWJText()},
        };
        try {
            super.jbInit(configs,DtoAcntLoaborRes.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void  addUICEvent(){
        this.getButtonPanel().addLoadButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        budgetParam = (DtoBudgetParam) param.get("budgetParam");
        if(budgetParam == null)
            isParamValidate = false;
        else
            isParamValidate = true;
    }

    public void resetUI(){
        if(!isParamValidate){
            this.getButtonPanel().setVisible(false);
            this.getTable().setEnabled(false);
            return;
        }
        if(budgetParam.getBudgetRid() == null)
            return;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj("budgetRid", budgetParam.getBudgetRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            budgetLogList = (List) returnInfo.getReturnObj("budgetLogList");
            this.getTable().setRows(budgetLogList);
            if(budgetParam.isReadOnly())
                this.getButtonPanel().setVisible(false);
            else
                this.getButtonPanel().setVisible(true);
        }
    }
    public static void main(String[] args) {
        VWWorkArea w1 = new VWWorkArea();
        VwBudgetLogList resource = new VwBudgetLogList();
        w1.addTab("CBS",resource);
        TestPanel.show(w1);
        resource.refreshWorkArea();
    }
}
