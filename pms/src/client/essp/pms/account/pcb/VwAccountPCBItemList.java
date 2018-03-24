package client.essp.pms.account.pcb;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.pcb.DtoPcbItem;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.framework.view.vwcomp.VWJReal;


public class VwAccountPCBItemList extends VWTableWorkArea {
    static final String actionIdList = "FAcntPCBItemListAction";
    static final String actionIdUpdate = "FAcntPCBItemUpdateAction";
    static final String actionIdDelete = "FAcntPCBItemDeleteAction";

    /**
     * define common data
     */
    private List pcbItemList;
    private Long acntRid;


    /*parameter*/

    private boolean isSaveOk = true;

    JButton btnSave = null;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnAdd = null;

    protected final static String[] pcbItemType = new String[] {
                                                  "Productivity", "Schedule",
                                                  "Quality", "Effort"};


    public VwAccountPCBItemList() {

        VWJComboBox type = new VWJComboBox();
        type.setModel(VMComboBox.toVMComboBox(pcbItemType));
        VWJReal seq = new VWJReal();
        seq.setMaxInputDecimalDigit(0);

        Object[][] configs = new Object[][] { {"Name", "name",
                             VMColumnConfig.EDITABLE, new VWJText()}
                             , {"Type", "type", VMColumnConfig.EDITABLE,
                             type}
                             , {"Remark", "remark", VMColumnConfig.EDITABLE,
                                 new VWJText()},
                             {"Seq", "seq", VMColumnConfig.EDITABLE,
                             seq}};

        try {
            super.jbInit(configs, DtoPcbItem.class);
            getTable().setSortable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        //调整列的宽度
        JTableHeader header = this.getTable().getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(100);
        tcModel.getColumn(1).setPreferredWidth(160);
        tcModel.getColumn(2).setPreferredWidth(140);

    }

    private void addUICEvent() {
        //捕获事件－－－－
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });
        btnAdd.setToolTipText("Add Item List");

        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });
        btnDel.setToolTipText("Del Item List");

        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
        btnSave.setToolTipText("Save Item List");

        TableColumnChooseDisplay chooseDisplay =
                    new TableColumnChooseDisplay(this.getTable(), this);
                JButton button = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(button);

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        btnLoad.setToolTipText("Refresh Item List");

    }

    protected void actionPerformedAdd(ActionEvent e) {
        DtoPcbItem dto = new DtoPcbItem();
        dto.setAcntRid(this.acntRid);
        dto.setType(pcbItemType[0]);
        getTable().addRow(dto);
    }

    protected void actionPerformedDel(ActionEvent e) {
        DtoPcbItem dtoPcbItem = (DtoPcbItem)this.getSelectedData();
        if (dtoPcbItem == null) {
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            if (dtoPcbItem.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj("dtoPcbItem", dtoPcbItem);
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


    private boolean checkModified() {
        if (pcbItemList == null) {
            return false;
        }

        for (Iterator it = this.pcbItemList.iterator();
                           it.hasNext(); ) {
            DtoPcbItem dtoPcbItem = (DtoPcbItem) it.next();

            if (dtoPcbItem.isChanged() == true) {
                return true;
            }
        }

        return false;
    }

    protected void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    protected void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData();
            }
        }

    }


    private boolean validateData() {
        StringBuffer msg = new StringBuffer("");

        List repeatRow = new ArrayList();
        for (int i = 0; i < this.getModel().getRows().size(); i++) {
            DtoPcbItem dto = (DtoPcbItem)this.getModel().getRows().get(i);
            if (StringUtil.nvl(dto.getType()).equals("") == true ||
                StringUtil.nvl(dto.getName()).equals("") == true) {
                msg.append("The " + (i + 1) +
                           " row： Must input type and name.\r\n");
            } else {
                repeatRow.add(dto);
                //是否有重复
                if (repeatRow.contains(new Integer(i)) == false) {
                    StringBuffer strRepeatRow = new StringBuffer();
                    boolean bRepeat = false;
                    for (int j = i + 1; j < this.getModel().getRows().size(); j++) {
                        DtoPcbItem tmpDto = (DtoPcbItem)this.getModel().getRows().
                                            get(j);

                        if (dto.getName().equals(tmpDto.getName()) == true) {
                            strRepeatRow.append(" ,");
                            strRepeatRow.append(j + 1);

                            repeatRow.add(new Integer(j));
                            bRepeat = true;
                        }
                    }
                    if (bRepeat == true) {
                        if (repeatRow.contains(new Integer(i)) == false) {
                            strRepeatRow.insert(0, i + 1);
                            repeatRow.add(new Integer(i));
                        }

                        msg.append("The name of rows: ");
                        msg.append(strRepeatRow);
                        msg.append(
                            "  are the same name can't be reduplicate.\r\n");
                    }
                }
            }
        }

        if (msg.toString().equals("") == false) {
            comMSG.dispErrorDialog(msg.toString());
            return false;
        } else {
            return true;
        }
    }


    private void saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj("pcbItemList", pcbItemList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            pcbItemList = (List) returnInfo.getReturnObj("pcbItemList");

            this.getTable().setRows(pcbItemList);
            isSaveOk = true;

        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.acntRid = (Long) (param.get(DtoAcntKEY.ACNT_RID));
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if (this.acntRid == null) {
            setButtonVisible(false);
            pcbItemList = new ArrayList();
            getTable().setRows(pcbItemList);
            return;
        }

        setButtonVisible(true);
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj(DtoAcntKEY.ACNT_RID, this.acntRid);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            pcbItemList = (List) returnInfo.getReturnObj("pcbItemList");

            getTable().setRows(pcbItemList);
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
            if (confirmSaveWorkArea("Do you save the PCB resource?") == true) {
                if (validateData() == true) {
                    saveData();
                }
            } else {
                isSaveOk = true;
            }
        }
    }


    public boolean isSaveOk() {
        return this.isSaveOk;
    }

    public static void main(String args[]) {
//        VWWorkArea w1 = new VWWorkArea();
//        VWWorkArea workArea = new VwAccountPCBItemList();
//
//        w1.addTab("Wp", workArea);
//        TestPanel.show(w1);
//
//        Parameter param = new Parameter();
//        param.put("isReadonly", Boolean.FALSE);
//        workArea.setParameter(param);
//        workArea.refreshWorkArea();

    }

    //为使本workarea能在两个位置（）用，所以这里做一个补丁
//    protected Long getParameterActivityRid() {
//        return this.activityRid;
//    }
//
//    protected boolean getIsReadOnly() {
//        return this.isReadonly;
//    }
}
