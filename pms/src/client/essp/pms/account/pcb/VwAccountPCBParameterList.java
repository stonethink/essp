package client.essp.pms.account.pcb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.pcb.DtoPcbParameter;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import c2s.essp.pms.account.DtoAcntKEY;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJNumber;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwAccountPCBParameterList extends VWTableWorkArea {
    static final String actionIdList = "FAcntPCBParameterListAction";
    static final String actionIdUpdate = "FAcntPCBParameterUpdateAction";
    static final String actionIdDelete = "FAcntPCBParameterDeleteAction";

    //cotrol data
    boolean isSaveOk = true;

    private List parameterList = new ArrayList();

    private Long itemRid;
    private Long acntRid;

    private JButton btnSave;
    private JButton btnDel;
    private JButton btnLoad;
    private JButton btnAdd;

    public VwAccountPCBParameterList() {

        VWJReal seq = new VWJReal();
        VWJReal nReal = new VWJReal();
        nReal.setCanNegative(true);
        seq.setMaxInputDecimalDigit(0);
        Object[][] configs = new Object[][] { {"ID", "id",
                             VMColumnConfig.EDITABLE,
                             new VWJText()},

                             {"Name", "name",
                             VMColumnConfig.EDITABLE, new VWJText()}
                             , {"Unit", "unit", VMColumnConfig.EDITABLE,
                             new VWJText()}
                             , {"UCL", "ucl", VMColumnConfig.EDITABLE,
                             nReal}
                             , {"Mean", "mean", VMColumnConfig.EDITABLE,
                             nReal}
                             , {"LCL", "lcl", VMColumnConfig.EDITABLE,
                             nReal}
                             , {"Plan", "plan", VMColumnConfig.EDITABLE,
                             nReal}
                             , {"Actual", "actual", VMColumnConfig.EDITABLE,
                             nReal, Boolean.TRUE}
                             , {"Remark", "remark", VMColumnConfig.EDITABLE,
                             new VWJText()}, {"Seq", "seq",
                             VMColumnConfig.EDITABLE,
                             seq}

        };

        try {
            super.jbInit(configs, DtoPcbParameter.class);
            getTable().setSortable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        this.setButtonVisible(true);

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

        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(this.getTable(), this);
        JButton button = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(button);

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });

    }

    public void actionPerformedAdd(ActionEvent e) {
        DtoPcbParameter dto = new DtoPcbParameter();
        dto.setItemRid(this.itemRid);
        dto.setAcntRid(this.acntRid);
        getTable().addRow(dto);

    }


    public void actionPerformedDel(ActionEvent e) {
        DtoPcbParameter dtoPcbParameter = (DtoPcbParameter)this.getSelectedData();
        if (dtoPcbParameter == null) {
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            if (dtoPcbParameter.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj("dtoPcbParameter", dtoPcbParameter);
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

    private boolean checkModified() {
        for (Iterator it = this.parameterList.iterator();
                           it.hasNext(); ) {
            DtoPcbParameter dtoPcbParameter = (DtoPcbParameter) it.next();

            if (dtoPcbParameter.isChanged() == true) {
                return true;
            }
        }

        return false;
    }

    private boolean validateData() {
        boolean bValid = true;
        StringBuffer msg = new StringBuffer("");

        for (int i = 0; i < this.getModel().getRows().size(); i++) {
            DtoPcbParameter dto = (DtoPcbParameter)this.getModel().getRow(i);
            if (StringUtil.nvl(dto.getId()).equals("") == true) {
                msg.append("Row " + (i + 1) + " ： Must input  id.\r\n");
                bValid = false;
            }

            if (StringUtil.nvl(dto.getName()).equals("") == true) {
                msg.append("Row " + (i + 1) + " ： Must input name.\r\n");
                bValid = false;
            }

        }
        if (bValid == false) {
            comMSG.dispErrorDialog(msg.toString());
        }
        return bValid;
    }

    private void saveData() {

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj("parameterList", parameterList);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            String existedInfo = (String) returnInfo.getReturnObj("existedInfo");
            if(existedInfo == null) {
                parameterList = (List) returnInfo.getReturnObj("parameterList");
                this.getTable().setRows(parameterList);
            } else {
                comMSG.dispErrorDialog(existedInfo);
            }
        }

    }

    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.itemRid = (Long) (param.get("itemRid"));
        this.acntRid = (Long) (param.get(DtoAcntKEY.ACNT_RID));
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if (this.acntRid == null || this.itemRid == null) {
            setButtonVisible(false);
            parameterList = new ArrayList();
            getTable().setRows(parameterList);
            return;
        }

        setButtonVisible(true);
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);

        inputInfo.setInputObj("itemRid", this.itemRid);

        inputInfo.setInputObj(DtoAcntKEY.ACNT_RID, this.acntRid);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            parameterList = (List) returnInfo.getReturnObj("pcbParameterList");

            getTable().setRows(parameterList);
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
            if (confirmSaveWorkArea("Do you save the files?") == true) {
                if (validateData() == true) {
                    saveData();
                    this.isSaveOk = true;
                }
            } else {
                isSaveOk = true;
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean isSaveOk() {
        return this.isSaveOk;
    }


    public static void main(String args[]) {
    }

}
