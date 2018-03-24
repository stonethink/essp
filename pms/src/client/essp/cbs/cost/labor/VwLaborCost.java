package client.essp.cbs.cost.labor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;



public class VwLaborCost extends VwLaborCostBase{
    static final String actionIdCaculate = "FCbsLaborCostCaculateAction";
    static final String actionIdList = "FCbsLaborCostListAction";

    private Long acntRid;
    private List laborCostList;
    private boolean refreshParent = true; //判断关闭窗口是否需刷新父窗口
    public VwLaborCost() {
        super();
        addUCIEvent();
    }

    private void addUCIEvent() {

        this.getButtonPanel().addLoadButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });

        JButton caculateBt  = this.getButtonPanel().addButton("calc.gif");
        caculateBt.setToolTipText("Recalculate labor cost");
        caculateBt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedCaculate();
            }
        });

    }

    private void actionPerformedCaculate(){
        int f = comMSG.dispConfirmDialog("Recaculating labor cost will cover the current data!" +
                                         "Do you want to continue?");
        if( f == Constant.OK ){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionIdCaculate);
            inputInfo.setInputObj("acntRid", acntRid);
            ReturnInfo returnInfo = accessData(inputInfo);
            if(!returnInfo.isError()){
                laborCostList = (List) returnInfo.getReturnObj("laborCostList");
                if(laborCostList != null)
                    this.getTable().setRows(laborCostList);
                refreshParent = true;
            }
        }
    }
    public void resetUI(){
        if(acntRid == null){
            this.getButtonPanel().setEnabled(false);
            this.getTable().setEnabled(false);
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdList);
        inputInfo.setInputObj("acntRid", acntRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            laborCostList = (List) returnInfo.getReturnObj("laborCostList");
            if(laborCostList != null)
                this.getTable().setRows(laborCostList);
        }
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public void setAcntRid(Long acntRid) {
        refreshParent = false;
        this.acntRid = acntRid;
    }

    public boolean isRefreshParent(){
        return this.refreshParent;
    }
}
