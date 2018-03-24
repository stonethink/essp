package client.essp.pwm.wp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pwm.wp.DtoPwWpchk;
import client.essp.common.util.TestPanelParam;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.common.Global;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJTable;
import com.wits.util.Parameter;
import org.xml.sax.SAXException;
import validator.Validator;
import validator.ValidatorResult;

public class FPW01022Checkpoint extends VWTableWorkArea implements
    IVWPopupEditorEvent {
    public static final String WAITING_ACTION = "Waiting check";
//    public static final String DELIVER_ACTION = "Delivered";
//    public static final String REJECT_ACTION = "Reject";
//    public static final String CLOSE_ACTION = "Closed";
    public static final String DFAULT_CHECKPOINT_NAME1 = "Planning the WP";
    public static final String DFAULT_CHECKPOINT_NAME2 = "Delivery the WP";

    private String actionId = "FPW01022CheckpointAction";

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
    private List checkpointList;
    private VMTblCheckpoint vmTblCheckpoint;
    private FPW01022CheckpointLog _VWPwWpCheckpointLog = null;
    private Validator validator = null;
    JButton btnSave = null;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnAdd = null;

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    public FPW01022Checkpoint() {
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
    }

    public FPW01022Checkpoint(Validator validator) {
        this.validator = validator;

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() throws Exception {

        vmTblCheckpoint = new VMTblCheckpoint(DtoPwWpchk.class, this.validator, getActionListener());

        this.model = vmTblCheckpoint;
        this.table = new VWJTable(model);
        getTable().setSortable(true);

//        getTable().getColumnModel().getColumn(3).setCellRenderer(
//            cellRenderButton);

//        getTable().addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                jTableMouseClicked(e);
//            }
//        });

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

        this.add(getTable().getScrollPane());
    }

    //本workArea不需要外界的parameter
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
    public void resetUI() {
        setButtonVisible();
        setEnabledMode();

        if (this.inWpId == null) {
            this.getTable().setRows(new ArrayList());
            return;
        }

        String funId = "select";
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);
        inputInfo.setInputObj("inWpId", inWpId);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            checkpointList = (List) returnInfo.getReturnObj("checkpointList");
            boolean isAssignby = ((Boolean) returnInfo.getReturnObj(
                "isAssignby")).booleanValue();
            vmTblCheckpoint.setAssignBy(isAssignby);
            getTable().setRows(checkpointList);
        }

        //set default checkpoint
        this.vmTblCheckpoint.setDefaultCheckpoint1(null);
        this.vmTblCheckpoint.setDefaultCheckpoint2(null);
        for (int i = 0; i < vmTblCheckpoint.getRowCount(); i++) {
            DtoPwWpchk dto = (DtoPwWpchk) vmTblCheckpoint.getRow(i);
            if (FPW01022Checkpoint.DFAULT_CHECKPOINT_NAME1.equals(dto.
                getWpchkName()) == true ||
                FPW01022Checkpoint.DFAULT_CHECKPOINT_NAME2.equals(dto.
                getWpchkName()) == true) {
                if (this.vmTblCheckpoint.getDefaultCheckpoint1() == null) {
                    this.vmTblCheckpoint.setDefaultCheckpoint1(dto.getRid());
                    continue;
                }

                if (this.vmTblCheckpoint.getDefaultCheckpoint2() == null) {
                    this.vmTblCheckpoint.setDefaultCheckpoint2(dto.getRid());
                    break;
                }
            }
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
            if (confirmSaveWorkArea("Do you save the check points of work package?") == true) {
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

    public boolean checkModified() {
        for (Iterator it = this.vmTblCheckpoint.getRows().iterator();
                           it.hasNext(); ) {
            DtoPwWpchk dtoPwWpchk = (DtoPwWpchk) it.next();

            if (dtoPwWpchk.isChanged() == true) {
                return true;
            }
        }

        return false;
    }

    //处理 button 事件---------
    public void actionPerformedAdd(ActionEvent e) {
        DtoPwWpchk dto = new DtoPwWpchk();
        dto.setWpchkName(FPW01022Checkpoint.DFAULT_CHECKPOINT_NAME1);
        dto.setWpchkDate(Global.todayDate);
        dto.setWpchkStatus(FPW01022Checkpoint.WAITING_ACTION);
        this.getTable().addRow(dto);
    }

    public void actionPerformedDel(ActionEvent e) {
        DtoPwWpchk dto = (DtoPwWpchk)this.getTable().getSelectedData();
        if( dto == null ){
            return;
        }
        if (this.vmTblCheckpoint.isDefaultCheckpoint(dto) == true) {
            comMSG.dispErrorDialog(
                "This checkpoint is a default checkpoint, can't delete it.");
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            if (dto.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj("dtoPwWpchk", dto);
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

    private ActionListener getActionListener(){
        return new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (_VWPwWpCheckpointLog == null) {
                    _VWPwWpCheckpointLog = new FPW01022CheckpointLog();
                }
                Parameter param = new Parameter();

                DtoPwWpchk dtoPwWpchk = (DtoPwWpchk)getSelectedData();
                Long chkId = dtoPwWpchk.getRid();
                if (chkId != null) {
                    param.put("inCheckpointId", chkId.toString());
                    _VWPwWpCheckpointLog.setParameter(param);
                    _VWPwWpCheckpointLog.refreshWorkArea();
                    VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(), "Checkpoint"
                        , _VWPwWpCheckpointLog, FPW01022Checkpoint.this);
                    popupEditor.show();
                }
            }
        };
    }

    public boolean validateData() {
        boolean bValid = true;
        ValidatorResult result = this.vmTblCheckpoint.validateData();
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

    public boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId);
        inputInfo.setFunId("update");

        inputInfo.setInputObj("inWpId", inWpId);
        inputInfo.setInputObj("checkpointList", checkpointList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            checkpointList = (List) returnInfo.getReturnObj("checkpointList");

            getTable().setRows(checkpointList);
            return true;
        }else{
            return false;
        }
    }

    public boolean onClickOK(ActionEvent e) {
        if (_VWPwWpCheckpointLog != null) {
            _VWPwWpCheckpointLog.saveWorkArea();
            return _VWPwWpCheckpointLog.isSaveOk();
        }

        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public static void main(String[] args) {
        FPW01022Checkpoint workArea = new FPW01022Checkpoint();

        Parameter param = new Parameter();
//        param.put("inUserId", "000038");
        Global.userId = "stone.shi";
        param.put("inWpId", new Long(10007));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
        TestPanelParam.show(workArea);
    }

}
