package client.essp.pms.wbs.pbsAndFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.pbs.pbsAndFiles.DtoPbsAssign;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJPopupEditor;
import c2s.essp.pms.pbs.DtoPmsPbs;
import javax.swing.JButton;
import client.framework.view.common.comMSG;
import client.framework.common.Constant;
import c2s.dto.IDto;

public class VwPbsList extends VWTableWorkArea {
    static final String actionIdList = "FPbsAssignListAction";
    static final String actionIdUpdate = "FPbsAssignUpdateAction";
    static final String actionIdAdd = "FPbsAssignAddAction";
    static final String actionIdDelete = "FPbsAssignDeleteAction";

    /**
     * parameter data
     */
    private Long acntRid;
    private Long joinType;
    private Long joinRid;
    private boolean isReadonly=true;
    private boolean isParameterValid = false;

    /**
     * define common data
     */
    private List pbsList=new ArrayList();
    private VwAssignPbsList vwAssignPbsList;
    boolean isSaveOk = true;
    JButton btnSave = null;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnAdd = null;

    public VwPbsList() {
        VWJText text = new VWJText();

        Object[][] configs = new Object[][] {
                             {"Name", "name", VMColumnConfig.UNEDITABLE, text}
                             , {"Code", "code", VMColumnConfig.UNEDITABLE, text}
                             , {"Status", "status", VMColumnConfig.UNEDITABLE, text}
        };

        try {
            super.jbInit(configs, DtoPbsAssign.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        setButtonVisible();
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
        if (vwAssignPbsList == null) {
            vwAssignPbsList = new VwAssignPbsList();
            Parameter param = new Parameter();
            param.put("acntRid", acntRid);
            vwAssignPbsList.setParameter(param);
            vwAssignPbsList.refreshWorkArea();
        }

        VWWorkArea workArea2 = new VWWorkArea();
        workArea2.addTab("Pbs", vwAssignPbsList);

        IVWPopupEditorEvent popupEditorEvent = new IVWPopupEditorEvent() {
            public boolean onClickOK(ActionEvent e) {
                 DtoPmsPbs dtoPmsPbs = (DtoPmsPbs)vwAssignPbsList.getSelectedData();
                 if( dtoPmsPbs != null ){

                     //检查是否重复
                     for (int i = 0; i < pbsList.size(); i++) {
                         DtoPbsAssign dto = (DtoPbsAssign)pbsList.get(i);
                         if( dto.getPbsRid().equals( dtoPmsPbs.getPbsRid() ) == true ){
                             comMSG.dispErrorDialog("The pbs already exist.");
                             return false;
                         }
                     }
                 }

                 return true;
            }

            public boolean onClickCancel(ActionEvent e) {
                return true;
            }
        };

        VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(), "Add Pbs", workArea2, popupEditorEvent);
        int iRet = popupEditor.showConfirm();
        if( iRet == Constant.OK ){
            DtoPmsPbs dtoPmsPbs = (DtoPmsPbs) vwAssignPbsList.getSelectedData();
            if (dtoPmsPbs != null) {
                DtoPbsAssign dtoPbsAssign = new DtoPbsAssign();

                dtoPbsAssign.setAcntRid(acntRid);
                dtoPbsAssign.setJoinRid(joinRid);
                dtoPbsAssign.setJoinType(joinType);
                dtoPbsAssign.setPbsRid(dtoPmsPbs.getPbsRid());

                dtoPbsAssign.setCode(dtoPmsPbs.getProductCode());
                dtoPbsAssign.setName(dtoPmsPbs.getProductName());
                dtoPbsAssign.setStatus(dtoPmsPbs.getPbsStatus());

                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(actionIdAdd);
                inputInfo.setInputObj("dto", dtoPbsAssign);
                ReturnInfo returnInfo = accessData(inputInfo);
                if( returnInfo.isError() == false ){
                    dtoPbsAssign = (DtoPbsAssign)returnInfo.getReturnObj("dto");
                    getTable().addRow(dtoPbsAssign);
                    dtoPbsAssign.setOp(IDto.OP_NOCHANGE);
                }
            }
        }
    }

    public void actionPerformedDel(ActionEvent e) {
        DtoPbsAssign dto = (DtoPbsAssign)this.getSelectedData();
        if (dto == null) {
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {

            if (dto.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj("dto", dto);
                inputInfo.setActionId(this.actionIdDelete);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    getTable().deleteRow();
                    this.pbsList.remove(dto);
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

    private boolean checkModified() {
        for (Iterator it = this.pbsList.iterator();
                           it.hasNext(); ) {
            DtoPbsAssign dtoPbsAssign = (DtoPbsAssign) it.next();

            if (dtoPbsAssign.isChanged() == true) {
                return true;
            }
        }

        return false;
    }


    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    private boolean validateData() {
        return true;
    }

    private void saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj("pbsList", pbsList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            pbsList = (List) returnInfo.getReturnObj("pbsList");

            this.getTable().setRows(pbsList);
        }
    }

    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.acntRid = (Long) (param.get("acntRid"));
        this.joinRid = (Long) (param.get("joinRid"));
        this.joinType = (Long) (param.get("joinType"));
        Boolean bIsReadonly = (Boolean)param.get("isReadonly");

        if (this.acntRid == null || this.joinRid == null || this.joinType == null) {
            isParameterValid = false;
        } else {
            isParameterValid = true;
        }

        if( bIsReadonly == null ){
            isReadonly = true;
        }else{
            isReadonly = bIsReadonly.booleanValue();
        }
    }
    //页面刷新－－－－－
    protected void resetUI() {
        if( this.acntRid == null || this.joinRid == null || this.joinType == null){
            setButtonVisible();
            pbsList = new ArrayList();
            getTable().setRows(pbsList);
            return;
        }

        setButtonVisible();
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj("acntRid", this.acntRid);
        inputInfo.setInputObj("joinRid", this.joinRid);
        inputInfo.setInputObj("joinType", this.joinType);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            pbsList = (List) returnInfo.getReturnObj("pbsList");

            getTable().setRows(pbsList);
        }
    }

    private void setButtonVisible() {
        if( isParameterValid == true && isReadonly == false ){
            this.btnAdd.setVisible(true);
            this.btnDel.setVisible(true);
            this.btnSave.setVisible(true);
        }else{
            this.btnAdd.setVisible(false);
            this.btnDel.setVisible(false);
            this.btnSave.setVisible(false);
        }
    }


    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save pbs list?") == true) {
                if (validateData() == true) {
                    saveData();
                    isSaveOk = true;
                }
            } else {
                isSaveOk = true;
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean isSaveOk(){
        return this.isSaveOk;
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwPbsList();

        w1.addTab("Pbs", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put("acntRid", new Long(7));
        param.put("joinType", new Long(1));
        param.put("joinRid", new Long(1));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
