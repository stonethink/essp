package client.essp.cbs.cost.activity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.cost.DtoActivityCost;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import c2s.dto.IDto;

public class VwActivityCostDetail extends VWTableWorkArea {
    static final String actionIdActivityCostSave = "FCbsAccountActivytCostSaveAction";

    ActivityCostTableModel model;

    DtoActivityCost activityCost;
    public VwActivityCostDetail() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUICEvent() {
        this.getButtonPanel().addSaveButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave();
            }
        });
    }
    private void actionPerformedSave(){
        if(isModified()){
            saveData();
        }
    }
    private void jbInit() throws Exception {
        VWJReal inputReal = new VWJReal();
        inputReal.setMaxInputDecimalDigit(2);
        inputReal.setMaxInputIntegerDigit(8);
        inputReal.setCanNegative(true);
        Object[][] configs = new Object[][] {
                             {"Subject", "subject",VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Budget(AMT)", "budgetAmt", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Cost(AMT)", "actualAmt", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Remaining(AMT)", "remain", VMColumnConfig.UNEDITABLE,inputReal},

        };
        model = new ActivityCostTableModel(configs);
        table = new VWJTable(model);
        this.add(table.getScrollPane(),null);
    }
    public void setParameter(Parameter param){
        super.setParameter(param);
        activityCost = (DtoActivityCost) param.get("activityCost");
    }

    public void resetUI(){

        if(activityCost != null){
            model.setActivityCost(activityCost);
            model.fireTableDataChanged();
        }
    }

    boolean isModified(){
        if(activityCost == null)
            return false;
        return activityCost.isChanged();
    }
    boolean saveData(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdActivityCostSave);
        inputInfo.setInputObj("activityCost", activityCost);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            activityCost.setOp(IDto.OP_NOCHANGE);
            return true;
        }
        return false;
    }
    public static void main(String[] args){
        VWWorkArea w1 = new VWWorkArea();
        VwActivityCostDetail tt = new VwActivityCostDetail();
        w1.addTab("Labor Resource", tt);
        TestPanel.show(w1);
    }
}
