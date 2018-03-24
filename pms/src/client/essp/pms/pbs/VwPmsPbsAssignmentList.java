package client.essp.pms.pbs;

import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.pbs.DtoPmsPbsAssignment;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.activity.AbstractActivityEvent;
import client.essp.pms.activity.VwActivityPopup;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWTreeTablePopupEvent;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwPmsPbsAssignmentList extends VWTableWorkArea implements IVWTreeTablePopupEvent{
    private String actionIdList = "FPbsAssignmentListAction";
    private String actionIdUpdate = "FPbsAssignmentUpdateAction";
    private String actionIdDelete = "FPbsAssignmentDeleteAction";
    private String actionIdAdd = "FPbsAssignmentAddAction";

    /**
     * define common data (globe)
     */
    private List assignmentList;
    private Long acntRid;
    private Long pbsRid;
    private boolean isSaveOk = true;
    private boolean isParameterValid = true;
    boolean isReadonly = true;
    JButton btnSave = null;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnAdd = null;

    VwAssignWbsList vwAssignWbsList;
    private VwActivityPopup activityPopup;
    private DtoWbsActivity selectedNode;//根据Popup所选择的节点

    public VwPmsPbsAssignmentList() {
        VWJCheckBox checkBox = new VWJCheckBox();

        VWJText text = new VWJText();

        Object[][] configs = new Object[][] {
                             {"Name", "name", VMColumnConfig.UNEDITABLE, text}
                             , {"Code", "code", VMColumnConfig.UNEDITABLE, text}
                             , {"Manager", "manager", VMColumnConfig.UNEDITABLE, text}
                             , {"Is Workproduct", "isWorkproduct", VMColumnConfig.EDITABLE, checkBox}
        };

        try {
            super.jbInit(configs, DtoPmsPbsAssignment.class);
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
        if(activityPopup == null){
            activityPopup = new VwActivityPopup(this.getParentWindow(),this);
        }
        activityPopup.pack();
        activityPopup.setLocation(150,120);
        activityPopup.setVisible(true);
    }

    public void actionPerformedDel(ActionEvent e) {
        DtoPmsPbsAssignment dto = (DtoPmsPbsAssignment)this.getSelectedData();
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
                    this.assignmentList.remove(dto);
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
        for (Iterator it = this.assignmentList.iterator();
                           it.hasNext(); ) {
            DtoPmsPbsAssignment doPmsPbsAssignment = (DtoPmsPbsAssignment) it.next();

            if (doPmsPbsAssignment.isChanged() == true) {
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

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj("assignmentList", assignmentList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            assignmentList = (List) returnInfo.getReturnObj("assignmentList");

            this.getTable().setRows(assignmentList);
            return true;
        }else{
            return false;
        }
    }

    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.acntRid = (Long) (param.get("acntRid"));
        this.pbsRid = (Long) (param.get("pbsRid"));

        if( this.acntRid != null && this.pbsRid != null ){
            isParameterValid = true;
        }else{
            isParameterValid = false;
        }

        Boolean bIsReadonly = (Boolean) param.get("isReadonly");
        if (bIsReadonly == null ) {
            isReadonly = true;
        }else{
            isReadonly = bIsReadonly.booleanValue();
        }
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if (this.acntRid == null || this.pbsRid == null) {
            setButtonVisible();
            setEnableMode();
            assignmentList = new ArrayList();
            getTable().setRows(assignmentList);
            return;
        }

        setButtonVisible();
        setEnableMode();
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj("acntRid", this.acntRid);
        inputInfo.setInputObj("pbsRid", this.pbsRid);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            assignmentList = (List) returnInfo.getReturnObj("assignmentList");

            getTable().setRows(assignmentList);
        }
    }

    private void setButtonVisible() {
        if (isParameterValid == true &&
            this.isReadonly == false) {
            this.btnAdd.setVisible(true);
            this.btnDel.setVisible(true);
            this.btnSave.setVisible(true);
        } else {
            this.btnAdd.setVisible(false);
            this.btnDel.setVisible(false);
            this.btnSave.setVisible(false);
        }
    }

    private void setEnableMode(){
        VMTable2 model = this.getModel();
       if (isParameterValid == true &&
                              this.isReadonly == false) {

           VMColumnConfig config = model.getColumnConfigByDataName("isWorkproduct");
           if( config != null ){
               config.setEditable(VMColumnConfig.EDITABLE);
           }
       } else {
           VMColumnConfig config = model.getColumnConfigByDataName("isWorkproduct");
           if( config != null ){
               config.setEditable(VMColumnConfig.UNEDITABLE);
           }
       }

    }

    public void saveWorkArea() {
        if (checkModified()) {
            this.isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the assignment wbs?") == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();
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

    /////////////////Activity popup Event/////////////////////////////////
    public boolean onClickOK(ActionEvent e) {
        if (activityPopup.getTreeTable().getSelectedNode() == null) {
            return true;
        }

        selectedNode = (DtoWbsActivity) activityPopup.getTreeTable().getSelectedNode().getDataBean();
        if (selectedNode == null) {
            return true;
        }

        //检查是否重复
        for (int i = 0; i < assignmentList.size(); i++) {
            DtoPmsPbsAssignment dto = (DtoPmsPbsAssignment) assignmentList.get(i);

            if (dto.getAcntRid().equals(selectedNode.getAcntRid()) == true) {

                if (selectedNode.isActivity() && dto.getJoinType().equals(DtoPmsPbsAssignment.JOINTYPEACT)) {
                    if (selectedNode.getActivityRid().equals(dto.getJoinRid()) == true) {
                        comMSG.dispErrorDialog("The data assigned already exist.");
                        return false;
                    }
                }

                if (selectedNode.isWbs() && dto.getJoinType().equals(DtoPmsPbsAssignment.JOINTYPEWBS)) {
                    if (selectedNode.getWbsRid().equals(dto.getJoinRid()) == true) {
                        comMSG.dispErrorDialog("The data assigned already exist.");
                        return false;
                    }
                }
            }
        }

        DtoPmsPbsAssignment dtoPmsPbsAssignment = new DtoPmsPbsAssignment();

        dtoPmsPbsAssignment.setAcntRid(selectedNode.getAcntRid());
        dtoPmsPbsAssignment.setCode(selectedNode.getCode());

        if (selectedNode.isActivity()) {
            dtoPmsPbsAssignment.setJoinRid(selectedNode.getActivityRid());
            dtoPmsPbsAssignment.setJoinType(DtoPmsPbsAssignment.JOINTYPEACT);
        } else {
            dtoPmsPbsAssignment.setJoinRid(selectedNode.getWbsRid());
            dtoPmsPbsAssignment.setJoinType(DtoPmsPbsAssignment.JOINTYPEWBS);
        }
        dtoPmsPbsAssignment.setManager(selectedNode.getManager());
        dtoPmsPbsAssignment.setName(selectedNode.getName());
        //dtoPmsPbsAssignment.setStatus(null);

        dtoPmsPbsAssignment.setPbsRid(this.pbsRid);

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdAdd);
        inputInfo.setInputObj("dto", dtoPmsPbsAssignment);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            dtoPmsPbsAssignment = (DtoPmsPbsAssignment) returnInfo.getReturnObj("dto");
            getTable().addRow(dtoPmsPbsAssignment);

            dtoPmsPbsAssignment.setOp(IDto.OP_NOCHANGE);
            return true;
        }else{
            return false;
        }
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public Parameter getParameter() {
        Parameter param = new Parameter();
        param.put(AbstractActivityEvent.ACCOUNT_RID,this.acntRid);
        return param;
    }

    public void onSelectedNode(ITreeNode selectedNode) {
        //这个方法好像没用
//        if(selectedNode == null)
//            return;
//        this.selectedNode = (DtoWbsActivity)selectedNode.getDataBean();
    }

    public PopupMenu getPopupMenu() {
        return null;
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwPmsPbsAssignmentList();

        w1.addTab("PmsPbsAssignment", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put("acntRid", new Long(7));
        param.put("pbsRid", new Long(15));
        workArea.setParameter(param);
        workArea.refreshWorkArea();

    }


}
