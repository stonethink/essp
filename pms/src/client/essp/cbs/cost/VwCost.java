package client.essp.cbs.cost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.cost.DtoCbsCost;
import client.essp.cbs.cost.labor.VwLaborCostPopWin;
import client.framework.view.vwcomp.VWJPopupEditor;
import com.wits.util.Parameter;
import client.essp.cbs.budget.VwCurrencyLabel;

public class VwCost extends VwCostBase {
    static final String actionIdCostGet = "FCbsCostGetAction";
    static final String actionIdCostSave = "FCbsCostSaveAction";

    VwLaborCostPopWin laborCost;
    VwCostItemPopWin costItem;

    DtoCbsCost costDto;
    private Long acntRid;
    private Boolean isReadOnly;
    private boolean isParameterValid = true;
    VwCurrencyLabel currencyLabel;
    public VwCost() {
        super();
        addUCIEvent();
    }
    private void addUCIEvent() {
        currencyLabel = new VwCurrencyLabel();
        this.getButtonPanel().add(currencyLabel);

        JButton laborBt = this.getButtonPanel().addButton("humanAllocate.gif");
        laborBt.setToolTipText("labor cost");
        laborBt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedLaborCost();
            }
        });

        this.getButtonPanel().addEditButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedEdit();
            }
        });
        this.getButtonPanel().addLoadButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });

        //expand
        JButton btnExpand = this.getButtonPanel().addButton("expand.png");
        btnExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand();
            }
        });
        btnExpand.setToolTipText("expand the tree");
    }

    private void actionPerformedEdit(){
        if(costItem == null)
            costItem = new VwCostItemPopWin();
        Parameter param = new Parameter();
        param.put("acntRid",acntRid);
        costItem.setParameter(param);
        costItem.resetUI();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Account Cost",
                                                costItem,costItem);
        pop.show();
        if(costItem.isRefreshParent())
            resetUI();
    }
    private void actionPerformedExpand(){
        this.getTreeTable().expandsRow();
    }
    private void actionPerformedLaborCost(){
        if(laborCost == null)
            laborCost = new VwLaborCostPopWin();
        laborCost.setAcntRid(acntRid);
        laborCost.resetUI();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Labor Cost",
                                                laborCost,laborCost);
        pop.show();
        if(laborCost.isRefreshParent())
            resetUI();
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        acntRid = (Long) param.get("acntRid");
        isReadOnly = (Boolean) param.get("isReadOnly");
        if(acntRid == null || isReadOnly == null)
            isParameterValid = false;
        else
            isParameterValid = true;
    }

    public void resetUI(){
        if(!isParameterValid){
            this.getButtonPanel().setVisible(false);
            this.getTreeTable().setEnabled(false);
            return;
        }
        if(isReadOnly.booleanValue()== true){
            this.getButtonPanel().setVisible(false);
        }else{
            this.getButtonPanel().setVisible(true);
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdCostGet);
        inputInfo.setInputObj("acntRid", acntRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            costDto = (DtoCbsCost) returnInfo.getReturnObj("costDto");
            if(costDto != null && costDto.getCostRoot() != null){
                this.getTreeTable().setRoot(costDto.getCostRoot());
                this.getTreeTable().expandRow(0);
                this.model.setCostDto(costDto);
                currencyLabel.setCurrency(costDto.getCurrency());
            }
        }
    }
}
