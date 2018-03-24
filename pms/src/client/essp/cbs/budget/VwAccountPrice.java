package client.essp.cbs.budget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.DtoPrice;
import client.essp.cbs.config.PriceTableModel;
import client.essp.cbs.config.VwPrice;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import c2s.essp.cbs.budget.DtoBudgetParam;


public class VwAccountPrice extends VwPrice {
    static final String actionIdAccountInit = "FCbsAcountPriceInitAction";
    Boolean isCanModifyPrice;
    String currency;
    public VwAccountPrice(){
         super();
    }

    protected void actionPerformedAdd(){
        DtoPrice dto = (DtoPrice) this.getTable().addRow();
        dto.setCatalog(CbsConstant.LABOR_RESOURCE);
        dto.setAcntRid(acntRid);
        dto.setCurrency(currency);
        dto.setPrice(new Double(0));
        dto.setUnit(CbsConstant.LABOR_UNIT_PM);
        dto.setPriceScope(priceScope);
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        currency = (String) param.get("currency");
        isCanModifyPrice = (Boolean) param.get("isCanModifyPrice");
        if (currency == null || isCanModifyPrice == null) {
            isParamValidate = false;
        }else{
            isParamValidate = true;
        }
    }
    protected void initUI(){
        getInitData();
        inputCurrency.setVMComboBox(new VMComboBox(currencyList));
        inputSubject.setVMComboBox(new VMComboBox(subjectList));
        if(isCanModifyPrice.booleanValue() == false){
            this.getButtonPanel().setVisible(false);
            this.getModel().setCellEditable(false);
        }
        if(accountCurrency != null)
            currency = accountCurrency;
    }
    protected void addUICEvent(){
        JButton initBt = this.getButtonPanel().addButton("addExist.gif");
        initBt.setToolTipText("init acount price");
        initBt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedInitPrice();
            }
        });
        super.addUICEvent();
    }
    private void actionPerformedInitPrice(){
        int f = comMSG.dispConfirmDialog("The action will delete all the price in this account and then copy system price.\n" +
                                         "Do you want to continue?");
        if( f == Constant.OK ){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdAccountInit);
            inputInfo.setInputObj("acntRid", acntRid);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (!returnInfo.isError()) {
                resetUI();
            }
        }
    }
    protected void jbInit() throws Exception {
        Object[][] configs = new Object[][] {
                             {"Catalog", "catalog",VMColumnConfig.EDITABLE, inputCatalog},
                             {"ID","id", VMColumnConfig.EDITABLE, new VWJText()},
                             {"Name", "name", VMColumnConfig.EDITABLE,new VWJText()},
                             {"Subject Code", "subjectCode",VMColumnConfig.EDITABLE, inputSubject},
                             {"Unit","unit", VMColumnConfig.EDITABLE, inputUnit},
                             {"Currency", "currency", VMColumnConfig.EDITABLE,inputCurrency},
                             {"Price", "price", VMColumnConfig.EDITABLE, inputPrice},
        };
        try {
            model = new PriceTableModel(configs);
            model.setDtoType(DtoPrice.class);
            table = new VWJTable(model,new AccountPriceNodeViewManager());
            this.add(table.getScrollPane(), null);
//            super.jbInit(configs,DtoPrice.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
