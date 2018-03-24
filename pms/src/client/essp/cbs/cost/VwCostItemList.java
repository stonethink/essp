package client.essp.cbs.cost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
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

public class VwCostItemList extends VWTableWorkArea {
    static final String actionIdCostItemList = "FCbsCostItemListAction";
    static final String actionIdCostItemDelete = "FCbsCostItemDelAction";
    static final String actionIdCostItemUpdate = "FCbsCostItemListUpdateAction";
    private Long acntRid;
    private List costItemList;
    private Vector subjectList;
    private Vector currencyList;
    private String accountCurrency;
    VWJComboBox inputSubject = new VWJComboBox();
    VWJComboBox inputCurrency = new VWJComboBox();
    public boolean isRefreshParent;
    public VwCostItemList() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void addUICEvent() {
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
        dto.setAcntRid(acntRid);
        dto.setCurrency(accountCurrency);
        dto.setCostDate(Global.todayDate);
        dto.setScope(DtoCostItem.ACCOUNT_SCOPE);
    }
    private void actionPerformedSave(){
        if(checkModified() && validateData())
            saveData();
    }
    private void actionPerformedDel(){
        int f = comMSG.dispConfirmDialog("Confirm to delete selected record!");
        if( f == Constant.OK ){
            DtoCostItem dto = (DtoCostItem) this.getTable().getSelectedData();
            if(dto == null)
                return;
            this.getTable().deleteRow();
            if(dto.isInsert()){
                return;
            }
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdCostItemDelete);
            inputInfo.setInputObj("dto", dto);
            ReturnInfo returnInfo = accessData(inputInfo);
            isRefreshParent = true;
        }
    }
    protected void jbInit() throws Exception {
        VWJDate inputDate = new VWJDate();
        inputDate.setCanSelect(true);
        VWJReal inputReal = new VWJReal();
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
//        model = new VMTable2(configs);
//        model.setDtoType(DtoCostItem.class);
//        table = new VWJTable(model,new SumNodeViewManager());
//        this.add(table.getScrollPane(), null);
        super.jbInit(configs, DtoCostItem.class);
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        acntRid = (Long) param.get("acntRid");
        isRefreshParent = false;
    }

    public void resetUI(){
        if(acntRid == null){
            this.getButtonPanel().setEnabled(false);
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdCostItemList);
        inputInfo.setInputObj("acntRid", acntRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            costItemList = (List) returnInfo.getReturnObj("costItemList");
            if(costItemList != null)
                this.getTable().setRows(costItemList);
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

    public boolean isRefreshParent() {
        return isRefreshParent;
    }

    boolean checkModified(){
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
            if(dto.getName() == null){
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
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdCostItemUpdate);
        inputInfo.setInputObj("costItemList", costItemList);
        ReturnInfo returnInfo = accessData(inputInfo);
        isRefreshParent = true;
        return !returnInfo.isError();
    }
}
