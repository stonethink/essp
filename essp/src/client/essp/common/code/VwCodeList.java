package client.essp.common.code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.code.DtoCode;
import client.essp.common.view.VWAccountLabel;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import c2s.essp.common.code.DtoKey;
import javax.swing.tree.TreePath;
import client.framework.view.vwcomp.VWJCheckBox;

public class VwCodeList extends VWTreeTableWorkArea {
    static final String actionIdList = "F00CodeListAction";
    static final String actionIdDelete = "F00CodeDeleteAction";
    static final String actionIdUpMove = "F00CodeUpMoveAction";
    static final String actionIdDownMove = "F00CodeDownMoveAction";

    String type = "";

    boolean isParameterValid = false;

    static final String treeColumnName = "name";
    JButton btnAdd;
    JButton btnDel;
    JButton btnLoad;
    JButton btnDown;
    JButton btnUp;

    public VwCodeList() {
        VWJText text = new VWJText();

        Object[][] configs = { {"Name", "name", VMColumnConfig.EDITABLE, null, Boolean.FALSE}
                                  , {"Description", "description", VMColumnConfig.UNEDITABLE, text, Boolean.FALSE}
                                  , {"Disabled", "status", VMColumnConfig.UNEDITABLE, new VWJCheckBox(), Boolean.FALSE}
                              };

        try {
            super.jbInit(configs, treeColumnName, DtoCode.class);

            getTreeTable().getTree().setShowsRootHandles(true);
            getTreeTable().getTree().setRootVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        setButtonVisible();
    }

    private void addUICEvent() {
        //捕获事件－－－－
        this.getTreeTable().addRowSelectedListener(new RowSelectedListener(){
            public void processRowSelected(){
                setButtonVisible();
            }
        });

        //Add
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
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
        ITreeNode parentNode = null;

        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode == null) {
            return;
        }

        DtoCode currentDataBean = (DtoCode) currentNode.getDataBean();
        if( currentDataBean.isCode() ){

            //新增current node的兄弟结点,紧挨其下
            parentNode = currentNode.getParent();
            if( parentNode == null ){
                return;
            }
        }else{

            //新增current node的儿子,为其最后一个儿子
            parentNode = currentNode;
        }

        DtoCode parentDataBean = (DtoCode) parentNode.getDataBean();

        DtoCode newCode = new DtoCode();
        newCode.setType(this.type);
        newCode.setCatalog(parentDataBean.getName());

        ITreeNode newNode = new DtoTreeNode(newCode);
        newNode = this.getTreeTable().addRow(parentNode,newNode);
    }

    public void actionPerformedDelete(ActionEvent e) {
        ITreeNode deleteNode = this.getSelectedNode();
        if( deleteNode == null ){
            return;
        }
        ITreeNode parentNode = deleteNode.getParent();
        if( parentNode == null ){

            //cannot delete the root
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?\r\nNote: This will delete data belong to it.");
        if (option == Constant.OK) {
            DtoCode dataBean = (DtoCode) deleteNode.getDataBean();

            if (dataBean.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj(DtoKey.DTO, dataBean);
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
        if( getTreeTable().isDownMovable() == false ){
            return;
        }

        ITreeNode node = this.getSelectedNode();
        if( node == null ){
            return;
        }

        DtoCode dataBean = (DtoCode) node.getDataBean();

        if (dataBean.isInsert()) {
            comMSG.dispErrorDialog("Can't move the data.Please save it first. ");
            return;
        }

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdDownMove);
        inputInfo.setInputObj(DtoKey.DTO, dataBean);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            this.getTreeTable().downMove();
        }
    }

    public void actionPerformedUp(ActionEvent e) {
        if( getTreeTable().isUpMovable() == false ){
            return;
        }

        ITreeNode node = this.getSelectedNode();
        if( node == null ){
            return;
        }

        DtoCode dataBean = (DtoCode) node.getDataBean();

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

    public void actionPerformedLoad(ActionEvent e){
        resetUI();
    }

    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        type = (String)param.get("type");
        if( type == null || type.equals("") == true ){
            this.isParameterValid = false;
        }else{
            this.isParameterValid = true;
        }
    }

    //页面刷新－－－－－
    protected void resetUI() {
        setButtonVisible();
        if( isParameterValid == false ){
            this.getTreeTable().setRoot(null);
            return;
        }

        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj(DtoKey.TYPE, this.type);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            ITreeNode root = (ITreeNode) returnInfo.getReturnObj(DtoKey.ROOT);
            String acntName = (String)returnInfo.getReturnObj("acntName");
            String acntCode = (String)returnInfo.getReturnObj("acntCode");

            this.getTreeTable().setRoot(root);
            if( root != null ){
                this.getTreeTable().expandPath(new TreePath(root));
                if( root.getChildCount() > 0 ){
                    this.getTreeTable().setSelectedRow(root.getChildAt(0));
                }
            }
        }
    }

    private void setButtonVisible() {
        if( isParameterValid == true ){
            btnAdd.setVisible(true);
            btnDel.setVisible(true);
            btnDown.setVisible(true);
            btnUp.setVisible(true);

            DtoCode dto = (DtoCode)this.getSelectedData();
            if (dto != null) {
                if (dto.isCode() == true) {
                    btnDel.setEnabled(true);
                    btnUp.setEnabled(true);
                    btnDown.setEnabled(true);
                } else {
                    btnDel.setEnabled(false);
                    btnUp.setEnabled(false);
                    btnDown.setEnabled(false);
                }
            }
        }else{
            btnAdd.setVisible(false);
            btnDel.setVisible(false);
            btnDown.setVisible(false);
            btnUp.setVisible(false);
        }
    }

    public static void main(String args[]) {
        VWWorkArea workArea0 = new VWWorkArea();
        VWWorkArea workArea = new VwCodeList();

        workArea0.addTab("Code", workArea);
        TestPanel.show(workArea0);

        Parameter param = new Parameter();
        param.put("type", "activity");
        workArea.setParameter(param);

        workArea.refreshWorkArea();
    }


}
