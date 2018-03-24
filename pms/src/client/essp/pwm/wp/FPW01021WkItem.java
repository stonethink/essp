package client.essp.pwm.wp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pwm.wp.DtoPwWkitem;
import client.essp.common.util.TestPanelParam;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.common.Global;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJTable;
import com.wits.util.Parameter;
import com.wits.util.comDate;
import org.xml.sax.SAXException;
import validator.Validator;
import validator.ValidatorResult;

public class FPW01021WkItem extends VWTableWorkArea {
    private String actionId = "FPW01021WkitemAction";

    /**
     * input parameters
     */
    private Long inWpId = null;

    /**
     * define control variable
     */
    private boolean isSaveOk = true;
    private boolean isParameterValid = false;

    /**
     * define common data (globe)
     */
    private Validator validator = null;
    VMTblWorkItem vmTblWorkItem = null;
    JButton btnSave = null;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnAdd = null;

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    public FPW01021WkItem() {
        try {
            validator = new Validator("/client/essp/pwm/wp/validator.xml",
                                      "client/essp/pwm/wp/validator");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setButtonVisible();
    }

    public FPW01021WkItem(Validator validator) {
        this.validator = validator;

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setButtonVisible();
    }


    //Component initialization
    private void jbInit() throws Exception {

        vmTblWorkItem = new VMTblWorkItem(DtoPwWkitem.class, this.validator);
        this.model = vmTblWorkItem;
        this.table = new VWJTable(model, new WkitemViewManager());
        getTable().setSortable(true);

        //捕获事件－－－－
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedADD(e);
            }
        });

        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });

        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });

        this.add(getTable().getScrollPane());
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        String sInWpId = (String) (param.get("inWpId"));
        try {
            this.inWpId = new Long(sInWpId);
            this.isParameterValid = true;
        } catch (NumberFormatException ex) {
            this.isParameterValid = false;
        }

    }

    //页面刷新－－－－－
    protected void resetUI() {
        setButtonVisible();
        setEnabledMode();

        if (this.inWpId == null) {
            this.getTable().setRows(new ArrayList());
            return;
        }

        String funId = "getWorkItems";
        InputInfo inputInfo = new InputInfo();

        inputInfo.setFunId(funId);
        inputInfo.setActionId(actionId);
        inputInfo.setInputObj("inWpId", inWpId);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            List workItems = (List) returnInfo.getReturnObj("workItems");

            getTable().setRows(workItems);
        }
    }

    private void setButtonVisible() {
        if (isParameterValid == true) {
            this.btnAdd.setVisible(true);
            this.btnDel.setVisible(true);
            this.btnLoad.setVisible(true);
            this.btnSave.setVisible(true);
        } else {
            this.btnAdd.setVisible(false);
            this.btnDel.setVisible(false);
            this.btnLoad.setVisible(false);
            this.btnSave.setVisible(false);
        }
    }

    private void setEnabledMode() {
        VMTable2 model = this.getModel();
        if (isParameterValid == true) {
            for (int i = 0; i < model.getColumnConfigs().size(); i++) {
                VMColumnConfig config = (VMColumnConfig) model.getColumnConfigs().get(i);
                config.setEditable(VMColumnConfig.EDITABLE);
            }
        } else {
            for (int i = 0; i < model.getColumnConfigs().size(); i++) {
                VMColumnConfig config = (VMColumnConfig) model.getColumnConfigs().get(i);
                config.setEditable(VMColumnConfig.UNEDITABLE);
            }
        }
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the work plan?") == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();
                }
            }else{
                isSaveOk = true;
            }
        }else{
            isSaveOk = true;
        }
    }

    public void saveWorkAreaDirect() {
        if (checkModified()) {
            isSaveOk = false;
            if (validateData() == true) {
                isSaveOk = saveData();
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean isSaveOk() {
        return this.isSaveOk;
    }

    /**
     *
     * @return boolean 被改动过时返回true，否则返回false
     */
    public boolean checkModified() {
        List workItems = this.vmTblWorkItem.getDtoList();
        for (Iterator it = workItems.iterator(); it.hasNext(); ) {
            DtoPwWkitem dtoPwWkitem = (DtoPwWkitem) it.next();

            if (dtoPwWkitem.isChanged() == true) {
                return true;
            }
        }

        return false;
    }

    public boolean validateData() {
        boolean bValid = true;
        ValidatorResult result = this.vmTblWorkItem.validateData();
        bValid = result.isValid();
        if (bValid == false) {
            StringBuffer msg = new StringBuffer();

            for (int i = 0; i < result.getAllMsg().length; i++) {
                msg.append(result.getAllMsg()[i]);
                msg.append("\r\n");
            }

            comMSG.dispErrorDialog(msg.toString());
            return false;
        }

        return bValid;
    }

    //处理 button 事件---------
    public void actionPerformedADD(ActionEvent e) {
        DtoPwWkitem dto = new DtoPwWkitem();
        dto.setWkitemOwner(Global.userId);
        //dto.setWkitemOwnerName(Global.userName);
        dto.setWkitemName("Planning");
        if( Global.todayDate != null && Global.todayDatePattern != null ){
            dto.setWkitemDate(Global.todayDate);
        }
        dto.setWkitemStarttime(new Date(2000,1,1,9,0,0));
        dto.setWkitemFinishtime(new Date(2000,1,1,18,0,0));
        dto.setWkitemWkhours(new BigDecimal(8));
        //DtoPwWkitem dtoPwWkitem = (DtoPwWkitem) tblWorkItem.addRow(dto);

        this.getTable().addRow(dto);
    }

    public void actionPerformedDel(ActionEvent e) {
        DtoPwWkitem dto = (DtoPwWkitem)this.getSelectedData();
        if( dto == null ){
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            if (dto.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj("dtoPwWkitem", dto);
                inputInfo.setActionId(this.actionId);
                inputInfo.setFunId("delete");

                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    getTable().deleteRow();

                    getModel().getDtoList().remove(dto);
                }
            } else {
                getTable().deleteRow();
            }
        }
    }

    public void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData();
            }
        }
    }

    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    public boolean saveData() {
        String funId = "saveWorkItemsByWpId";
        InputInfo inputInfo = new InputInfo();
        List workItems = this.vmTblWorkItem.getDtoList();

        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);
        inputInfo.setInputObj("inUserId", Global.userId);
        inputInfo.setInputObj("inWpId", inWpId);
        inputInfo.setInputObj("workItems", workItems);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            workItems = (List) returnInfo.getReturnObj("workItems");

            getTable().setRows(workItems);
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        FPW01021WkItem vwPwWpWorkItem = new FPW01021WkItem();

        Parameter param = new Parameter();
//        param.put("inUser", "000038");
//        param.put("inWpId", "95");
//        param.put("inProjectId", "909");
        param.put("inWpId", new Long(10007));
        vwPwWpWorkItem.setParameter(param);
        vwPwWpWorkItem.refreshWorkArea();
        TestPanelParam.show(vwPwWpWorkItem);
    }


}
