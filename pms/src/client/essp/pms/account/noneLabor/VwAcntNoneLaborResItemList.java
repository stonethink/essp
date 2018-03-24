package client.essp.pms.account.noneLabor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoNoneLaborResItem;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.StringUtil;
import com.wits.util.comDate;
import client.framework.view.common.comMSG;
import client.framework.common.Constant;

public class VwAcntNoneLaborResItemList extends VWTableWorkArea {
    static final String actionIdList = "FAcntNoneLaborResItemListAction";
    static final String actionIdUpdate = "FAcntNoneLaborResItemUpdateAction";
    static final String actionIdDelete = "FAcntNoneLaborResItemDeleteAction";

    //cotrol data
    boolean isSaveOk = true;

    /**
     * define common data
     */
    private List noneLaborResItemList=new ArrayList();
    private Long acntRid;
    private Long envRid;

    private JButton btnSave;
    private JButton btnDel;
    private JButton btnLoad;
    private JButton btnAdd;

    public VwAcntNoneLaborResItemList() {
        VWJDate date = new VWJDate();
        date.setCanSelect(true);
        VWJNumber number = new VWJNumber();
        number.setMaxInputIntegerDigit(8);
        Object[][] configs = new Object[][] {
                             {"Resource Name", "resourceName", VMColumnConfig.EDITABLE, new VWJText()},
                             {"Resource Id", "resourceId", VMColumnConfig.EDITABLE, new VWJText()},
                             {"Requirement", "requirement", VMColumnConfig.EDITABLE, new VWJText()},
                             {"Start Date", "startDate", VMColumnConfig.EDITABLE, date},
                             {"Finish Date", "finishDate", VMColumnConfig.EDITABLE, date},
                             {"Resource Number", "resourceNumber", VMColumnConfig.EDITABLE, number},
                             {"Remark", "remark", VMColumnConfig.EDITABLE, new VWJText()} ,
        };

        try {
            super.jbInit(configs, DtoNoneLaborResItem.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void addUICEvent() {
        //捕获事件－－－－
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
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
    }

    public void actionPerformedAdd(ActionEvent e) {
        DtoNoneLaborResItem dto = new DtoNoneLaborResItem();
        dto.setAcntRid(this.acntRid);
        dto.setEnvRid(this.envRid);
        getTable().addRow(dto);
    }

    public void actionPerformedDel(ActionEvent e) {
        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            DtoNoneLaborResItem dto = (DtoNoneLaborResItem)this.getSelectedData();
            if (dto != null) {
                if (dto.isInsert() == false) {
                    InputInfo inputInfo = new InputInfo();
                    inputInfo.setInputObj(DtoAcntKEY.DTO, dto);
                    inputInfo.setActionId(this.actionIdDelete);

                    ReturnInfo returnInfo = accessData(inputInfo);
                    if (returnInfo.isError() == false) {
                        getTable().deleteRow();
                    }
                } else {
                    getTable().deleteRow();
                }
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

    private boolean checkModified() {
        for (Iterator it = this.noneLaborResItemList.iterator();
                           it.hasNext(); ) {
            DtoNoneLaborResItem dtoNoneLaborResItem = (DtoNoneLaborResItem) it.next();

            if (dtoNoneLaborResItem.isChanged() == true) {
                return true;
            }
        }

        return false;
    }


    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    private boolean validateData() {
        boolean bValid = true;
        StringBuffer msg = new StringBuffer("");

        for( int i = 0 ; i < this.getTable().getRowCount(); i++ ){
            DtoNoneLaborResItem dto = (DtoNoneLaborResItem)this.getModel().getRow(i);
            if( StringUtil.nvl( dto.getResourceName() ).equals("") == true ){
                msg.append("Row " + (i + 1) + " ： Must input resource name.\r\n");
                bValid = false;
            }

            if( StringUtil.nvl( dto.getResourceId() ).equals("") == true ){
                msg.append("Row " + (i + 1) + " ： Must input resource id.\r\n");
                bValid = false;
            }

            //关联检查
            if (dto.getStartDate() != null
                && dto.getFinishDate() != null
                && comDate.compareDate(dto.getStartDate(),
                                    dto.getFinishDate()) > 0) {
                msg.append("Row " + (i + 1) + " ：Start time cann't bigger than finish time.");
                bValid = false;
            }
        }

        if( bValid == false ){
            comMSG.dispErrorDialog(msg.toString());
        }
        return bValid;
    }

    private void saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj(DtoAcntKEY.NONE_LABOR_RES_ITEM_LIST, noneLaborResItemList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            noneLaborResItemList = (List) returnInfo.getReturnObj(DtoAcntKEY.NONE_LABOR_RES_ITEM_LIST);

            this.getTable().setRows(noneLaborResItemList);
        }
    }

    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.acntRid = (Long) (param.get(DtoAcntKEY.ACNT_RID));
        this.envRid = (Long) (param.get(DtoAcntKEY.ENV_RID));
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if( this.acntRid == null || this.envRid == null ){
            setButtonVisible(false);
            noneLaborResItemList = new ArrayList();
            getTable().setRows(noneLaborResItemList);
            return;
        }

        setButtonVisible(true);
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj(DtoAcntKEY.ACNT_RID, this.acntRid);
        inputInfo.setInputObj(DtoAcntKEY.ENV_RID, this.envRid);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            noneLaborResItemList = (List) returnInfo.getReturnObj(DtoAcntKEY.NONE_LABOR_RES_ITEM_LIST);

            getTable().setRows(noneLaborResItemList);
        }
    }

    private void setButtonVisible(boolean isVisible) {
        this.btnAdd.setVisible(isVisible);
        this.btnDel.setVisible(isVisible);
        this.btnLoad.setVisible(isVisible);
        this.btnSave.setVisible(isVisible);
    }

    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the noneLabor resource item?") == true) {
                if (validateData() == true) {
                    saveData();
                    this.isSaveOk = true;
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

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwAcntNoneLaborResItemList();

        w1.addTab("None labor resource", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoAcntKEY.ACNT_RID, new Long(7));
        param.put(DtoAcntKEY.ENV_RID, new Long(1));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
