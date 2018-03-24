package client.essp.cbs.cost.activity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.DtoSubject;
import c2s.essp.cbs.cost.DtoActivityCost;
import c2s.essp.cbs.cost.DtoCostItem;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTableEditor;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import client.framework.common.Global;

public class VwActivityOtherCostItemList extends VWTableWorkArea {
    static final String actionIdCostItemList = "FCbsActivytCostItemListAction";
    static final String actionIdCostItemDelete = "FCbsCostItemDelAction";
    static final String actionIdCostItemUpdate = "FCbsCostItemListUpdateAction";
    private DtoActivityCost activityCost;
    private List costItemList;
    private String attribute;
    private Vector subjectList;
    private Vector currencyList;
    private String accountCurrency;

    VwActivityCostDetail vwActivityCostDetail;
    VWJComboBox inputSubject = new VWJComboBox();
    VWJComboBox inputCurrency = new VWJComboBox();
    VWJReal inputReal = new VWJReal();
    VWJDate inputDate = new VWJDate();
    private boolean isRefreshParent;
    private boolean isSaveOk = true;
    public VwActivityOtherCostItemList() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUICEvent() {
        this.getButtonPanel().setEnabled(true);
        this.getButtonPanel().addAddButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd();
            }
        });
        this.getButtonPanel().addDelButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel();
            }
        });
        this.getButtonPanel().addSaveButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave();
            }
        });
        this.getButtonPanel().addLoadButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
    }
    private void actionPerformedAdd(){
        DtoCostItem dto = (DtoCostItem) this.getTable().addRow();
        dto.setAcntRid( activityCost.getAcntRid());
        dto.setActivityId(activityCost.getActivityId());
        dto.setCurrency(accountCurrency);
        dto.setCostDate(Global.todayDate);
        dto.setScope(DtoCostItem.ACTIVITY_SCOPE);
        dto.setAttribute(attribute);
    }
    private void actionPerformedDel(){
        int f = comMSG.dispConfirmDialog("Confirm to delete selected record!");
        if( f == Constant.OK ){
            DtoCostItem dto = (DtoCostItem)this.getTable().
                              getSelectedData();
            if (dto == null)
                return;
            this.getTable().deleteRow();
            if (dto.isInsert()) {
                return;
            }
            caculateSum();
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdCostItemDelete);
            inputInfo.setInputObj("dto", dto);
            inputInfo.setInputObj("activityCost", activityCost);
            ReturnInfo returnInfo = accessData(inputInfo);
            if(!returnInfo.isError()){
                isRefreshParent = true;
                costItemList.remove(dto);
                if(vwActivityCostDetail != null)
                    vwActivityCostDetail.model.fireTableDataChanged();
            }
        }
    }
    private void actionPerformedSave(){
        if(isModified() && validateData())
            saveData();
    }
    protected void jbInit() throws Exception {
        inputDate.setCanSelect(true);
        inputReal.setMaxInputDecimalDigit(2);
        inputReal.setMaxInputIntegerDigit(8);
        inputReal.setCanNegative(true);
        Object[][] configs = new Object[][] {
                             {"Subject", "subjectCode", VMColumnConfig.EDITABLE, inputSubject},
                             {"Item", "name", VMColumnConfig.EDITABLE, new VWJText()},
                             {"Amount", "amt", VMColumnConfig.EDITABLE, inputReal},
                             {"Currency", "currency", VMColumnConfig.EDITABLE, inputCurrency},
                             {"Cost Date", "costDate", VMColumnConfig.EDITABLE,inputDate},
                             {"Reason", "remark", VMColumnConfig.EDITABLE,new VWJText()},
        };
        super.jbInit(configs, DtoCostItem.class);
    }
    public void setParameter(Parameter param){
        super.setParameter(param);
        activityCost = (DtoActivityCost) param.get("activityCost");
        attribute = (String)param.get("attribute");
        vwActivityCostDetail = (VwActivityCostDetail) param.get("vwActivityCostDetail");
        isRefreshParent = false;
    }
    public void resetUI(){
        if(activityCost == null || activityCost.getAcntRid() == null ||
           activityCost.getActivityId() == null){
            this.getButtonPanel().setEnabled(false);
            this.getTable().setEnabled(false);
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdCostItemList);
        inputInfo.setInputObj("attribute", attribute);
        inputInfo.setInputObj("acntRid", activityCost.getAcntRid());
        inputInfo.setInputObj("activityId", activityCost.getActivityId());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            costItemList = (List) returnInfo.getReturnObj("costItemList");
            table.setRows(costItemList);
            accountCurrency = (String) returnInfo.getReturnObj("accountCurrency");

            subjectList = (Vector) returnInfo.getReturnObj("subjectList");
            VWJTableEditor editorSubject =  (VWJTableEditor) table.getColumn("Subject").getCellEditor();
            inputSubject = (VWJComboBox) editorSubject.getComponent();
            inputSubject.setVMComboBox(new VMComboBox(subjectList));

            currencyList = (Vector) returnInfo.getReturnObj("currencyList");
            VWJTableEditor editorCurrency =  (VWJTableEditor) table.getColumn("Currency").getCellEditor();
            inputCurrency = (VWJComboBox) editorCurrency.getComponent();
            inputCurrency.setVMComboBox(new VMComboBox(currencyList));
        }
    }
    public boolean isRefreashParent(){
        return isRefreshParent;
    }
    boolean isModified(){
        if(costItemList == null || costItemList.size() <= 0)
            return false;
        else{
            for(int i = 0;i < costItemList.size();i ++){
                DtoCostItem dto = (DtoCostItem) costItemList.get(i);
                if(dto.isChanged())
                    return true;
            }
        }
        return false;
    }
    boolean validateData(){
        if(costItemList == null || costItemList.size() <= 0)
            return true;
        boolean isValide = true;
        StringBuffer msg = new StringBuffer();
        for(int i = 0;i < costItemList.size();i ++){
            DtoCostItem dto = (DtoCostItem) costItemList.get(i);
            if(dto.getSubjectCode() == null){
                isValide = false;
                msg.append("Line["+i+"]:Please choose the subject!\n");
            }
            if(dto.getName() == null || "".equals(dto.getName())){
                isValide = false;
                msg.append("Line["+i+"]:Please fill the name!\n");
            }
            if(dto.getCurrency() == null){
                isValide = false;
                msg.append("Line["+i+"]:Please choose the currency!\n");
            }
            if(dto.getCostDate() == null){
                isValide = false;
                msg.append("Line["+i+"]:Please choose the cost date!\n");
            }

        }
        if( !isValide ){
            comMSG.dispErrorDialog(msg.toString());
        }
        return isValide;
    }
    boolean saveData(){
        if(costItemList == null || costItemList.size() <= 0)
            return true;
        for(int i = 0;i < costItemList.size();i ++){  //CostItem对应的Subject可能更改,则其所属的统计科目也要修改
            DtoCostItem item = (DtoCostItem) costItemList.get(i);
            String subjectCode = item.getSubjectCode();
            item.setAttribute(getSubjectAttribute(subjectCode));
        }
        caculateSum();
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdCostItemUpdate);
        inputInfo.setInputObj("costItemList", costItemList);
        inputInfo.setInputObj("activityCost", activityCost);
        ReturnInfo returnInfo = accessData(inputInfo);
        if( !returnInfo.isError() ){
            isRefreshParent = true;
            resetUI();
            if(vwActivityCostDetail != null)
                    vwActivityCostDetail.model.fireTableDataChanged();
            return true;
        }
        return false;
    }
    private String getSubjectAttribute(String subjectCode){
        if(subjectList == null || subjectCode == null)
            return null;
        for(int i = 0;i < subjectList.size();i ++){
            DtoComboItem item = (DtoComboItem) subjectList.get(i);
            if(subjectCode.equals(item.getItemValue()))
                return item.getItemRelation().toString();
        }
        return null;
    }
    //对costItemList计算其NonLaobr和Expense的合计值,修改DtoActivityCost对象
    private void caculateSum(){
        if(costItemList == null || activityCost == null)
            return;
        double nonLaborAmt = 0.0d;
        double expenseAmt = 0.0d;
        for(int i = 0;i < costItemList.size();i ++){
            DtoCostItem item = (DtoCostItem) costItemList.get(i);
            String itemAttr = item.getAttribute();
            if(item.isDelete())
                continue;
            Double amt = item.getAmt();
            if(DtoSubject.ATTRI_NONLABOR_SUM.equals(itemAttr)){
                nonLaborAmt += (amt == null ? 0.0d : amt.doubleValue());
            }else if(DtoSubject.ATTRI_EXPENSE_SUM.equals(itemAttr)){
                expenseAmt += (amt == null ? 0.0d : amt.doubleValue());
            }
        }
        if(DtoSubject.ATTRI_NONLABOR_SUM.equals(attribute)){
            activityCost.setActualNonlaborAmt(new Double(nonLaborAmt));
            activityCost.addActualExpenseAmt(expenseAmt);
        }else if(DtoSubject.ATTRI_EXPENSE_SUM.equals(attribute)){
            activityCost.setActualExpAmt(new Double(expenseAmt));
            activityCost.addActualNonLaborAmt(nonLaborAmt);
        }

    }
    public void saveWorkArea() {
        if (isModified()){
            isSaveOk = false;
            if(confirmSaveWorkArea()){
               if(validateData()){
                   isSaveOk = saveData();
               }
            }else{
                isSaveOk = true;
            }
        }else{
            isSaveOk = true;
        }
    }
    public boolean isSaveOk(){
        return this.isSaveOk;
    }
}
