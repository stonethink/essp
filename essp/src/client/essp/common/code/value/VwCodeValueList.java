package client.essp.common.code.value;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.code.DtoCodeValue;
import c2s.essp.common.code.DtoKey;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import c2s.essp.common.code.DtoCode;
import javax.swing.tree.TreePath;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import c2s.dto.IDto;
import client.framework.view.vwcomp.VWJCheckBox;

public class VwCodeValueList extends VWTreeTableWorkArea
    implements IVWPopupEditorEvent{
    static final String actionIdList = "F00CodeValueListAction";
    static final String actionIdDelete = "F00CodeValueDeleteAction";
    static final String actionIdUpdate = "F00CodeValueUpdateAction";

    static final String actionIdUpMove = "F00CodeValueUpMoveAction";
    static final String actionIdDownMove = "F00CodeValueDownMoveAction";

    static final String treeColumnName = "value";

    boolean isParameterValid = false;

    DtoCode dtoCode = null;

    JButton btnAdd;
    JButton btnUpdate = null;
    JButton btnDel;
    JButton btnLoad;
    JButton btnDown;
    JButton btnUp;
    VwCodeValueGeneral vwCodeValueGeneral = new VwCodeValueGeneral();

    public VwCodeValueList() {

        VWJText text = new VWJText();

        Object[][] configs = { {"Name", "value", VMColumnConfig.EDITABLE, null,
                             Boolean.FALSE}
                             , {"Description", "description",
                             VMColumnConfig.UNEDITABLE, text, Boolean.FALSE}
                             ,{"Disabled", "status", VMColumnConfig.UNEDITABLE, new VWJCheckBox(), Boolean.FALSE}
        };

        try {
            super.jbInit(configs, treeColumnName, DtoCodeValue.class);

            //getTreeTable().getTree().setShowsRootHandles(true);
            //getTreeTable().getTree().setRootVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        setButtonVisible();
    }

    private void addUICEvent() {
        //捕获事件－－－－
        this.getTreeTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                setButtonVisible();
            }
        });

        //Add
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });

       btnUpdate = this.getButtonPanel().addEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUpdate(e);
            }
        });

        //Delete
        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDelete(e);
            }
        });

        //Down
        btnDown = this.getButtonPanel().addButton("down.png");
        btnDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDown(e);
            }
        });
        btnDown.setToolTipText("move down");

        //Up
        btnUp = this.getButtonPanel().addButton("up.png");
        btnUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUp(e);
            }
        });
        btnUp.setToolTipText("move up");

        //Refresh
        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
    }

    public void actionPerformedAdd(ActionEvent e) {
        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode != null) {
            DtoCodeValue currentDataBean = (DtoCodeValue) currentNode.getDataBean();
            if (currentDataBean.isInsert()) {
                comMSG.dispErrorDialog(
                    "This code value has not been saved.Cannot add a new code value to this code value.");
            } else {
                DtoCodeValue dtoCodeValue = new DtoCodeValue();
                dtoCodeValue.setParentRid(currentDataBean.getRid());
                dtoCodeValue.setCodeRid(currentDataBean.getCodeRid());

                Parameter param = new Parameter();
                param.put(DtoKey.DTO, dtoCodeValue);
                vwCodeValueGeneral.setParameter(param);
                vwCodeValueGeneral.refreshWorkArea();

                VWJPopupEditor poputEditor = new VWJPopupEditor(getParentWindow(),
                    "Code Value", vwCodeValueGeneral, this);
                int i = poputEditor.showConfirm();
                if (i == Constant.OK) {
                    ITreeNode newNode = new DtoTreeNode(dtoCodeValue);
                    newNode = this.getTreeTable().addRow(newNode);
                    dtoCodeValue.setOp(IDto.OP_NOCHANGE);
                }
            }
        }
    }

    public void actionPerformedUpdate(ActionEvent e) {
        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode != null) {
            DtoCodeValue currentDataBean = (DtoCodeValue) currentNode.
                                           getDataBean();

            Parameter param = new Parameter();
            param.put(DtoKey.DTO, currentDataBean);
            vwCodeValueGeneral.setParameter(param);
            vwCodeValueGeneral.refreshWorkArea();

            VWJPopupEditor poputEditor = new VWJPopupEditor(getParentWindow(),
                "Code Value", vwCodeValueGeneral, this);
            int i = poputEditor.showConfirm();
            if (i == Constant.OK) {
                this.getTreeTable().refreshTree();
            }
        }
    }


    public void actionPerformedDelete(ActionEvent e) {
        ITreeNode deleteNode = this.getSelectedNode();
        if (deleteNode == null) {
            return;
        }
        ITreeNode parentNode = deleteNode.getParent();
        if (parentNode == null) {

            //cannot delete the root
            return;
        }

        int option = comMSG.dispConfirmDialog(
            "Do you delete the data?\r\nNote: This will delete data belong to it.");
        if (option == Constant.OK) {
            DtoCodeValue dataBean = (DtoCodeValue) deleteNode.getDataBean();

            if (dataBean.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj("dto", dataBean);
                inputInfo.setActionId(this.actionIdDelete);

                //delete from db
                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    this.getTreeTable().deleteRow();
                }
            } else {
                this.getTreeTable().deleteRow();
            }
        }
    }

    public void actionPerformedDown(ActionEvent e) {
        if (getTreeTable().isDownMovable() == false) {
            return;
        }

        ITreeNode node = this.getSelectedNode();
        if (node == null) {
            return;
        }

        DtoCodeValue dataBean = (DtoCodeValue) node.getDataBean();

        if (dataBean.isInsert()) {
            comMSG.dispErrorDialog("Can't move the data.Please save it first. ");
            return;
        }

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdDownMove);
        inputInfo.setInputObj("dto", node.getDataBean());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            this.getTreeTable().downMove();
        }
    }

    public void actionPerformedUp(ActionEvent e) {
        if (getTreeTable().isUpMovable() == false) {
            return;
        }


        ITreeNode node = this.getSelectedNode();
        if (node == null) {
            return;
        }

        DtoCodeValue dataBean = (DtoCodeValue) node.getDataBean();

        if (dataBean.isInsert()) {
            comMSG.dispErrorDialog("Can't move the data.Please save it first. ");
            return;
        }

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpMove);
        inputInfo.setInputObj("dto", node.getDataBean());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            this.getTreeTable().upMove();
        }
    }

    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        dtoCode = (DtoCode) param.get(DtoKey.CODE_OBJ);
        if (dtoCode == null || dtoCode.getRid() == null) {
            isParameterValid = false;
        } else {
            isParameterValid = true;
        }
    }

    //页面刷新－－－－－
    protected void resetUI() {
        setButtonVisible();
        if (isParameterValid == false) {
            this.getTreeTable().setRoot(null);
            return;
        }

        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj(DtoKey.CODE_RID, dtoCode.getRid());

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            ITreeNode root = (ITreeNode) returnInfo.getReturnObj(DtoKey.ROOT);

            //根接点显示codeValue所属的code的信息
            DtoCodeValue bean = (DtoCodeValue)root.getDataBean();
            bean.setValue(this.dtoCode.getName());
            bean.setDescription(dtoCode.getDescription());

            this.getTreeTable().setRoot(root);
            if( root != null ){
                this.getTreeTable().expandPath(new TreePath(root));
                this.getTreeTable().setSelectedRow(root);
            }

            setButtonVisible();
        }
    }

    private void setButtonVisible() {
        if (isParameterValid == true) {
            btnAdd.setVisible(true);
            btnUpdate.setVisible(true);
            btnDel.setVisible(true);
            btnDown.setVisible(true);
            btnUp.setVisible(true);

            btnUpdate.setEnabled(true);
            btnDel.setEnabled(true);
            ITreeNode currentNode = this.getSelectedNode();
            if (currentNode != null) {
                if (currentNode.getParent() == null) {

                    //root是一个code,而非一个code value，故不能update
                    btnUpdate.setEnabled(false);
                    btnDel.setEnabled(false);
                }
            }
        } else {
            btnAdd.setVisible(false);
            btnUpdate.setVisible(false);
            btnDel.setVisible(false);
            btnDown.setVisible(false);
            btnUp.setVisible(false);
        }
    }


    public boolean onClickOK(ActionEvent e) {
        vwCodeValueGeneral.saveWorkArea();
        boolean ok = vwCodeValueGeneral.isSaveOk();

        return ok;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public void refreshRoot(DtoCode code){
        ITreeNode root = (ITreeNode)this.getModel().getRoot();
        if( root != null ){
            DtoCodeValue codeValue = (DtoCodeValue)root.getDataBean();
            codeValue.setValue(code.getName());
            codeValue.setDescription(code.getDescription());
            getTreeTable().repaint();
        }
    }

    public static void main(String args[]) {
        VWWorkArea workArea0 = new VWWorkArea();
        VWWorkArea workArea = new VwCodeValueList();

        workArea0.addTab("Pbs", workArea);
        TestPanel.show(workArea0);

        Parameter param = new Parameter();
        param.put(DtoKey.CODE_RID, new Long(1));
        workArea.setParameter(param);

        workArea.refreshWorkArea();
    }


}
