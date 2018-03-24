package client.essp.pms.wbs.process.checklist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import c2s.essp.pms.account.DtoAcntKEY;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.model.VMTable2;

public class VwCheckPointList extends VWTableWorkArea {
    public static final String actionIdList = "FWbsQaCheckPointListAction";
    public static final String actionIdDelete = "FWbsQaCheckPointDeleteAction";
    public static final String actionIdUpdate = "FWbsQaCheckPointListSaveAction";
    public static final String actionIdGetCode = "FWbsGetQaCheckPointCodeAction";

    /**
     * define common data
     */
    private List dtoQaCheckPointList;
    private DtoWbsActivity dtoWbsActivity;
    private String entryFunType;
    private boolean isSaveOk = true;
    private Boolean isReadOnly = Boolean.TRUE;
    JButton btnSave = null;
    JButton btnAdd = null;
    JButton btnEdit = null;
    JButton btnDel = null;
    JButton btnLoad = null;



    public VwCheckPointList() {
        VWJReal seq = new VWJReal();
        seq.setMaxInputDecimalDigit(0);
        Object[][] configs = new Object[][] {
                              {"Seq", "seq", VMColumnConfig.EDITABLE, seq},
                              {"Name", "name", VMColumnConfig.EDITABLE,
                              new VWJText()},
                              {"Check Method", "method", VMColumnConfig.EDITABLE,
                              new VWJText()},
                              {"Remark", "remark", VMColumnConfig.EDITABLE,
                              new VWJText()}
        };
        try {
            super.jbInit(configs, DtoQaCheckPoint.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //可排序
        getTable().setSortable(true);
        //调整列的宽度
        JTableHeader header = this.getTable().getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setMinWidth(10);
        tcModel.getColumn(0).setPreferredWidth(10);//调整Seq初始宽度
        tcModel.getColumn(1).setPreferredWidth(150);
        tcModel.getColumn(2).setPreferredWidth(200);
        addUICEvent();
    }

    private void addUICEvent() {
        //捕获事件－－－－
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });

        btnEdit = this.getButtonPanel().addEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedEdit(e);
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
        //Display
                TableColumnChooseDisplay chooseDisplay =
                    new TableColumnChooseDisplay(this.getTable(), this);
                JButton button = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(button);
        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        this.getTable().addRowSelectedListener(new RowSelectedListener(){
            public void processRowSelected() {
                if(getTable().getSelectedData() == null) {
                    btnDel.setEnabled(false);
                } else {
                    btnDel.setEnabled(true);
                }
            }
        });
    }

    public void actionPerformedAdd(ActionEvent e) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdGetCode);
        inputInfo.setInputObj(DtoKey.ACNT_RID, dtoWbsActivity.getAcntRid());

        ReturnInfo returnInfo = accessData(inputInfo);

        Long newRid = null;
        if (returnInfo.isError() == false) {
            newRid = (Long) returnInfo.getReturnObj(DtoQaCheckPoint.
                DTO_PMS_CHECK_POINT_RID);
        }

        DtoQaCheckPoint dto = new DtoQaCheckPoint();
        dto.setRid(newRid);
        dto.setAcntRid(dtoWbsActivity.getAcntRid());
        if (dtoWbsActivity.isActivity()) {
            dto.setBelongTo(DtoKey.TYPE_ACTIVITY);
            dto.setBelongRid(dtoWbsActivity.getActivityRid());
        } else {
            dto.setBelongTo(DtoKey.TYPE_WBS);
            dto.setBelongRid(dtoWbsActivity.getWbsRid());
        }
        dto.setOp(IDto.OP_INSERT);
        getTable().addRow(dto);
    }

    public void actionPerformedEdit(ActionEvent e) {
        if(getTable().getSelectedData() == null) return;

        VwCheckPointPop checkPointPop = new VwCheckPointPop();
        Parameter param = new Parameter();
        param.put(DtoKey.DTO, getTable().getSelectedData());
        param.put("isReadOnly", isReadOnly);
        checkPointPop.setParameter(param);
        checkPointPop.refreshWorkArea();

        VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                    "Edit Check Point", checkPointPop, checkPointPop);
        pop.setSize(500, 400);
        int clickFlag = pop.showConfirm();
        if(Constant.OK == clickFlag) {
            this.fireDataChanged();
            this.updateUI();
        }
    }

    public void actionPerformedDel(ActionEvent e) {
        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            DtoQaCheckPoint dto = (DtoQaCheckPoint)this.getSelectedData();
            if (dto != null) {
                if (dto.isInsert() == false) {
                    InputInfo inputInfo = new InputInfo();
                    inputInfo.setInputObj(DtoQaCheckPoint.DTO_PMS_CHECK_POINT,
                                          dto);
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
        if(dtoQaCheckPointList == null) {
            return false;
        }
        for (Iterator it = this.dtoQaCheckPointList.iterator();
                           it.hasNext(); ) {
            DtoQaCheckPoint DtoQaCheckPoint = (DtoQaCheckPoint) it.next();

            if (DtoQaCheckPoint.isChanged() == true) {
                return true;
            }
        }

        return false;
    }


    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    private boolean validateData() {
        StringBuffer msg = new StringBuffer("");

        for (int i = 0; i < this.getModel().getRows().size(); i++) {
            DtoQaCheckPoint dto = (DtoQaCheckPoint)this.getModel().getRows().
                                  get(i);
            if (StringUtil.nvl(dto.getName()).equals("") == true) {
                msg.append("The " + (i + 1) +
                           " row： Must input name.\r\n");
            }
            if (StringUtil.nvl(dto.getMethod()).equals("") == true) {
                msg.append("The " + (i + 1) +
                           " row： Must input method.\r\n");
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

        inputInfo.setInputObj(DtoQaCheckPoint.PMS_CHECK_POINT_LIST,
                              dtoQaCheckPointList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            dtoQaCheckPointList = (List) returnInfo.getReturnObj(
                DtoQaCheckPoint.
                PMS_CHECK_POINT_LIST);

            this.getTable().setRows(dtoQaCheckPointList);
            isSaveOk = true;
        }
    }

    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.dtoWbsActivity = (DtoWbsActivity) param.get(DtoKey.DTO);
        entryFunType = (String) param.get("entryFunType");
        isReadOnly = Boolean.valueOf(dtoWbsActivity.isReadonly() ||
                         DtoAcntKEY.PMS_ACCOUNT_FUN.equals(entryFunType));
    }

    //页面刷新－－－－－
    protected void resetUI() {

        if (this.dtoWbsActivity == null) {
            setButtonVisible(false);
            this.btnLoad.setVisible(false);
            dtoQaCheckPointList = new ArrayList();
            getTable().setRows(dtoQaCheckPointList);
            return;
        }

        //无权限，或从PMS功能进来，只读
        setButtonVisible(!isReadOnly.booleanValue());
        this.btnLoad.setVisible(true);
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj(DtoKey.DTO, this.dtoWbsActivity);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            dtoQaCheckPointList = (List) returnInfo.getReturnObj(
                DtoQaCheckPoint.
                PMS_CHECK_POINT_LIST);

            getTable().setRows(dtoQaCheckPointList);
        }
    }

    private void setButtonVisible(boolean isVisible) {
        this.btnAdd.setVisible(isVisible);
        this.btnDel.setVisible(isVisible);
        this.btnSave.setVisible(isVisible);
        getTable().setEnabled(isVisible);
    }


    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the check point?") == true) {
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
    }

}
