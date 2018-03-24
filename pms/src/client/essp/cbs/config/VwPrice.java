package client.essp.cbs.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.DtoPrice;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.VWJTableEditor;
import client.framework.view.vwcomp.IVWAppletParameter;
import client.framework.model.VMTable2;

public class VwPrice  extends VWTableWorkArea  implements IVWAppletParameter  {
    static final String actionIdInit = "FCbsPriceListAction";
    static final String actionIdSave = "FCbsPriceSaveAction";
    static final String actionIdDelete = "FCbsPriceDeleteAction";

    protected List priceList;
    protected Vector currencyList;
    protected Vector jobCodeList;
    protected Vector catalogList = new Vector();
    protected Vector subjectList;
    protected Vector unitList = new Vector();

    protected String priceScope;
    protected Long acntRid;
    protected String accountCurrency;
    protected boolean isParamValidate = true;;
    protected PriceTableModel model;

    protected VWJComboBox inputJobCodeName = new VWJComboBox();
    protected VWJComboBox inputJobCodeId = new VWJComboBox();
    protected VWJComboBox inputCurrency = new VWJComboBox();
    protected VWJComboBox inputCatalog = new VWJComboBox();
    protected VWJComboBox inputSubject = new VWJComboBox();
    protected VWJComboBox inputUnit = new VWJComboBox();
    protected VWJReal inputPrice = new VWJReal();
    protected boolean isSaveOk = true;
    public VwPrice() {
        super();
        //Catalog暂时只有Labor Resource一种
        catalogList.add(new DtoComboItem(CbsConstant.LABOR_RESOURCE));
        unitList.add(new DtoComboItem(CbsConstant.LABOR_UNIT_PM));
        unitList.add(new DtoComboItem(CbsConstant.LABOR_UNIT_PD));
        unitList.add(new DtoComboItem(CbsConstant.LABOR_UNIT_PH));
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    protected void  addUICEvent(){
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
    protected void actionPerformedDel(){
        int f = comMSG.dispConfirmDialog("Confirm to delete selected record!");
        if( f == Constant.OK ){
            DtoPrice dto = (DtoPrice)this.getTable().getSelectedData();
            if(dto.getOp().equals(IDto.OP_INSERT)){
                this.getTable().deleteRow();
                return;
            }
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdDelete);
            inputInfo.setInputObj("dto", dto);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (!returnInfo.isError()) {
                priceList.remove(this.getTable().getSelectedData());
                resetUI();
            }
        }
    }
    protected void actionPerformedAdd(){
        if(priceList != null && CbsConstant.SCOPE_GLOBAL.equals(priceScope)){
            int rows = 0;
            for(int i = 0; i < priceList.size();i ++){
                DtoPrice dto = (DtoPrice)priceList.get(i);
                if(!dto.getOp().equals(IDto.OP_DELETE))
                    rows ++;
            }
            if (jobCodeList != null && rows == jobCodeList.size()) {
                comMSG.dispErrorDialog(
                    "All Job Level has been in the table!");
                return;
            }
        }
        DtoPrice dto = (DtoPrice) this.getTable().addRow();
        dto.setCatalog(CbsConstant.LABOR_RESOURCE);
        dto.setAcntRid(acntRid);
        Object defualtCurrency = ((DtoComboItem) currencyList.get(0)).
                                 getItemValue();
        dto.setCurrency(defualtCurrency.toString());
        // dto.setCurrency(currency);
        dto.setPrice(new Double(0));
        dto.setUnit(CbsConstant.LABOR_UNIT_PM);
        dto.setPriceScope(priceScope);
    }
    protected void actionPerformedSave(){
        if(checkModified() && validateData())
            saveData();
    }
    protected boolean checkModified(){
        if(priceList == null || priceList.size()<= 0 )
            return false;
        for (Iterator it = this.priceList.iterator();
                           it.hasNext(); ) {
            DtoPrice dto = (DtoPrice) it.next();
            if (dto.isChanged()) {
                return true;
            }
        }
        return false;
    }
    protected boolean validateData(){
        if(priceList == null || priceList.size()<= 0 )
            return true;
        boolean isValidate = true;
        StringBuffer msg = new StringBuffer("");
        for (int i = 0 ;i < priceList.size() ;i ++) {
            DtoPrice dto = (DtoPrice) priceList.get(i);
            if(StringUtil.nvl(dto.getId()).equals("") ||
               StringUtil.nvl(dto.getName()).equals("") ){
                msg.append("Line["+i+"]:Please fill id and name!\n");
                isValidate = false;
            }
            if(StringUtil.nvl(dto.getSubjectCode()).equals("") ){
                msg.append("Line["+i+"]:Please choose a subject!\n");
                isValidate = false;
            }
        }
        if(!isValidate)
            comMSG.dispErrorDialog(msg.toString());
        return isValidate;
    }
    protected boolean saveData(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdSave);
        inputInfo.setInputObj("priceList", priceList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            resetUI();
            return true;
        }
        return false;
    }
    public boolean isSaveOk(){
        return this.isSaveOk;
    }
    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if(confirmSaveWorkArea()){
                if (validateData() == true) {
                    isSaveOk = saveData();
                }
            }else{
                isSaveOk = true;
            }
        } else {
            isSaveOk = true;
        }
    }
    public void setParameter(Parameter param){
        super.setParameter(param);
        priceScope = (String) param.get("priceScope");
        acntRid = (Long) param.get("acntRid");
        if(priceScope == null || acntRid == null){
            isParamValidate = false;
        }else{
            isParamValidate = true;
        }
        model.setPriceScope(priceScope);
    }
    public void resetUI(){
        if(!isParamValidate){
            setButtonVisiable();
            return;
        }
        initUI();
        inputUnit.setVMComboBox(new VMComboBox(unitList));
        inputCatalog.setVMComboBox(new VMComboBox(catalogList));
        VWJTableEditor editorSubject =  (VWJTableEditor) table.getColumn("Subject Code").getCellEditor();
        inputSubject = (VWJComboBox) editorSubject.getComponent();
        inputSubject.setVMComboBox(new VMComboBox(subjectList));
        VWJTableEditor editorCurrency =  (VWJTableEditor) table.getColumn("Currency").getCellEditor();
        inputCurrency = (VWJComboBox) editorCurrency.getComponent();
        inputCurrency.setVMComboBox(new VMComboBox(currencyList));
    }
    protected void initUI(){
        getInitData();
        if(jobCodeList != null){
            Vector jobcodeIdList = new Vector(jobCodeList.size());//Job Code Id的下拉框内容
            Vector jobcodeNameList = new Vector(jobCodeList.size());//Job Code Name的下拉框内容
            for(int i = 0; i < jobCodeList.size() ;i ++){
                DtoComboItem item = (DtoComboItem) jobCodeList.get(i);
                DtoComboItem idItem = new DtoComboItem(item.getItemValue());
                idItem.setItemRelation(item.getItemName());
                jobcodeIdList.add(idItem);
                DtoComboItem nameItem = new DtoComboItem(item.getItemName());
                nameItem.setItemRelation(item.getItemValue());
                jobcodeNameList.add(nameItem);
            }
            inputJobCodeId.setVMComboBox(new VMComboBox(jobcodeIdList));
            inputJobCodeName.setVMComboBox(new VMComboBox(jobcodeNameList));
        }
        model.setJobCodeList(jobCodeList);
    }

    protected void getInitData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInit);
        inputInfo.setInputObj("acntRid", acntRid);
        inputInfo.setInputObj("priceScope", priceScope);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            priceList = (List) returnInfo.getReturnObj("priceList");
            if(priceList == null)
                priceList = new ArrayList();
            this.getTable().setRows(priceList);
            currencyList = (Vector) returnInfo.getReturnObj("currencyList");
            jobCodeList = (Vector) returnInfo.getReturnObj("jobCodeList");
            subjectList = (Vector) returnInfo.getReturnObj("subjectList");
            accountCurrency = (String) returnInfo.getReturnObj("accountCurrency");
        }
    }

    private void setButtonVisiable(){
        this.getButtonPanel().setVisible(isParamValidate);
        this.getTable().setEnabled(isParamValidate);
    }
    public static void main(String[] args) {
        VWWorkArea w1 = new VWWorkArea();
        VwPrice resource = new VwPrice();
        w1.addTab("CBS",resource);
        TestPanel.show(w1);
        resource.refreshWorkArea();
    }

    protected void jbInit() throws Exception {
        inputPrice.setMaxInputIntegerDigit(8);
        inputPrice.setMaxInputDecimalDigit(2);
        //设置标题栏位
        Object[][] configs = new Object[][] {
                             {"Catalog", "catalog",VMColumnConfig.EDITABLE, inputCatalog},
                             {"ID", "id", VMColumnConfig.EDITABLE,inputJobCodeId},
                             {"Name", "name", VMColumnConfig.EDITABLE, inputJobCodeName},
                             {"Subject Code", "subjectCode", VMColumnConfig.EDITABLE,inputSubject},
                             {"Unit", "unit", VMColumnConfig.EDITABLE,inputUnit},
                             {"Currency", "currency", VMColumnConfig.EDITABLE,inputCurrency},
                             {"Price", "price", VMColumnConfig.EDITABLE,inputPrice},
        };
        try {
            model = new PriceTableModel(configs);
            model.setDtoType(DtoPrice.class);
            table = new VWJTable(model);
            this.add(table.getScrollPane(), null);
//            super.jbInit(configs,DtoPrice.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public VMTable2 getModel(){
        return model;
    }
    public String[][] getParameterInfo() {
        return new String[][]{};
    }
}
