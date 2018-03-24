package client.essp.cbs.cost.activity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.DtoSubject;
import c2s.essp.cbs.cost.DtoActivityCost;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwActivityCost extends VWGeneralWorkArea  implements IVWPopupEditorEvent{
    static final String actionIdActivityCostGet = "FCbsAccountActivytCostGetAction";
    DtoActivityCost activityCost;
    ITreeNode node;
    VwActivityCostDetail vwActivityCostDetail;
    VwActivityLaborCostItemList vwActivityLaborCostItemList;
    VwActivityOtherCostItemList vwActivityOtherCostItemList;

    VWWorkArea topWorkArea;
    VWWorkArea laborCostWorkArea;
    VWWorkArea otherCostWorkArea;
    private JSplitPane splitPane;
    private boolean isSaveOk = true;
    public VwActivityCost() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUICEvent() {
        vwActivityCostDetail.getTable().addRowSelectedListener(new RowSelectedListener(){
            public void processRowSelected() {
                processRowSelectedCost();
            }
        });

        vwActivityCostDetail.getTable().addRowSelectedLostListener(new RowSelectedLostListener(){
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedData) {
                return processRowSelectedCostLost();
            }
        });
    }
    private boolean processRowSelectedCostLost(){
        vwActivityOtherCostItemList.saveWorkArea();
        return vwActivityOtherCostItemList.isSaveOk();
    }
    private void processRowSelectedCost(){
        if(activityCost != null){
            Parameter param = new Parameter();
            String attribute = (String) vwActivityCostDetail.getTable().getSelectedData();
            param.put("attribute",attribute);
            param.put("activityCost",activityCost);
            param.put("vwActivityCostDetail",vwActivityCostDetail);
            if(DtoSubject.ATTRI_LABOR_SUM.equals(attribute)){
                vwActivityLaborCostItemList.setParameter(param);
                vwActivityLaborCostItemList.resetUI();
                splitPane.setDividerLocation(120);
                splitPane.setBottomComponent(laborCostWorkArea);
            }else if(DtoSubject.ATTRI_NONLABOR_SUM.equals(attribute) ||
                 DtoSubject.ATTRI_EXPENSE_SUM.equals(attribute)){
                vwActivityOtherCostItemList.setParameter(param);
                vwActivityOtherCostItemList.resetUI();
                splitPane.setDividerLocation(120);
                splitPane.setBottomComponent(otherCostWorkArea);
            }
        }
    }


    protected void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(800,400));
        this.setLayout(new BorderLayout());

        vwActivityCostDetail = new VwActivityCostDetail();
        topWorkArea = new VWWorkArea();
        topWorkArea.addTab("Activity Cost",vwActivityCostDetail);

        vwActivityLaborCostItemList = new VwActivityLaborCostItemList();
        vwActivityOtherCostItemList = new VwActivityOtherCostItemList();

        otherCostWorkArea = new VWWorkArea();
        laborCostWorkArea = new VWWorkArea();
        laborCostWorkArea.addTab("Labor Cost",vwActivityLaborCostItemList);
        otherCostWorkArea.addTab("Cost Item",vwActivityOtherCostItemList);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setDividerLocation(120);
        this.splitPane.setTopComponent(topWorkArea);

        this.add(splitPane, BorderLayout.CENTER);
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        activityCost = (DtoActivityCost) param.get("activityCost");
        node = (ITreeNode) param.get(DtoKEY.WBSTREE);
    }

    public void saveWorkArea(){
        if(vwActivityOtherCostItemList.isModified() || vwActivityCostDetail.isModified()){
            isSaveOk = false;
            if(confirmSaveWorkArea()){
                if(vwActivityOtherCostItemList.isModified() && vwActivityOtherCostItemList.validateData()){
                    isSaveOk = vwActivityOtherCostItemList.saveData();
                }else if(vwActivityCostDetail.isModified()){
                    isSaveOk = vwActivityCostDetail.saveData();
                }
            }else{
                isSaveOk = true;
            }
        }else{
            isSaveOk = true;
        }
    }
    public boolean isSaveOk(){
        return isSaveOk;
    }
    public void resetUI() {
        if(node != null){
            DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
            Long acntRid = dataBean.getAcntRid();
            Long activityId = dataBean.getActivityRid();
            if (acntRid != null && activityId != null && dataBean.isActivity()) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(this.actionIdActivityCostGet);
                inputInfo.setInputObj("acntRid", acntRid);
                inputInfo.setInputObj("activityId", activityId);
                ReturnInfo returnInfo = accessData(inputInfo);
                if (!returnInfo.isError()) {
                    activityCost = (DtoActivityCost) returnInfo.getReturnObj("activityCost");
                }
            }
        }
        if(activityCost != null){
            Parameter param = new Parameter();
            param.put("activityCost",activityCost);
            vwActivityCostDetail.setParameter(param);
            vwActivityCostDetail.resetUI();
            vwActivityCostDetail.getTable().setSelectRow(0);
            vwActivityCostDetail.getTable().fireSelected();
        }else{
            vwActivityCostDetail.getButtonPanel().setEnabled(false);
            vwActivityCostDetail.getTable().setEnabled(false);
            vwActivityOtherCostItemList.getButtonPanel().setEnabled(false);
            vwActivityOtherCostItemList.getTable().setEnabled(false);
        }
    }


    public boolean onClickOK(ActionEvent e) {
        if(vwActivityOtherCostItemList.isModified()){
            if(vwActivityOtherCostItemList.validateData()){
                if(!vwActivityOtherCostItemList.saveData())
                    return false;
            }else{
                return false;
            }
        }
        if(!vwActivityCostDetail.isModified())
            return true;
        return vwActivityCostDetail.saveData();
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }
    public static void main(String[] args){
        VWWorkArea w1 = new VWWorkArea();
        VwActivityCost tt = new VwActivityCost();
        w1.addTab("Labor Resource", tt);
        TestPanel.show(w1);
    }
}
