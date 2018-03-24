package client.essp.cbs.cost.activity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import com.wits.util.Parameter;
import client.framework.view.vwcomp.VWJPopupEditor;
import c2s.essp.cbs.cost.DtoActivityCost;
import c2s.essp.cbs.cost.DtoResCostSum;


public class VwActivityCostList extends VwActivityCostListBase  {
    static final String actionIdCostList = "FCbsAccountActivytCostListAction";
//    static final String actionIdCostCaculate = "FCbsAccountActivytCostCaculateAction";
    private Long acntRid;
    private Boolean isReadOnly;
    private List activityCostList;

    VwActivityCost vwActivityCost;
    public VwActivityCostList(){
        addUCIEvent();
    }

    private void addUCIEvent() {
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(this.getTable(), this);
        JButton btnDisplay = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(btnDisplay);

        this.getButtonPanel().addLoadButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });

        this.getButtonPanel().addEditButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedEdit();
            }
        });
    }
    private void actionPerformedEdit(){
        if(activityCostList == null || activityCostList.size() <= 1)
            return;
        DtoActivityCost dto = (DtoActivityCost) this.getTable().getSelectedData();
        if(DtoResCostSum.SUM.equals(dto.getActivityCode())){//第一行不能修改
            return;
        }
        if(vwActivityCost == null)
            vwActivityCost = new VwActivityCost();
        Parameter param = new Parameter();
        param.put("activityCost",dto);
        vwActivityCost.setParameter(param);
        vwActivityCost.resetUI();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Activity Cost",
                                                vwActivityCost,vwActivityCost);
        pop.show();

    }
    public Long getAcntRid() {
        return acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        acntRid = (Long) param.get("acntRid");
        isReadOnly = (Boolean) param.get("isReadOnly");
    }

    public void resetUI(){
        if(acntRid == null || isReadOnly == null){
            this.getButtonPanel().setVisible(false);
            this.getTable().setEnabled(false);
            return;
        }
        if(isReadOnly.booleanValue()){
            this.getButtonPanel().setVisible(false);
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdCostList);
        inputInfo.setInputObj("acntRid", acntRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            activityCostList = (List) returnInfo.getReturnObj("activityCostList");
            if(activityCostList != null)
                this.getTable().setRows(activityCostList);
        }
    }
}
