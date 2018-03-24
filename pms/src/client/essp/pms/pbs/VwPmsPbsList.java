package client.essp.pms.pbs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.pbs.DtoPmsPbs;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWAccountLabel;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import c2s.essp.pms.account.DtoAcntKEY;
import client.essp.common.loginId.VWJLoginId;

public class VwPmsPbsList extends VWTreeTableWorkArea {
    static final String actionIdList = "FPbsListAction";
    static final String actionIdDelete = "FPbsDeleteAction";
    static final String actionIdUpdate = "FPbsUpdateAction";

    static final String actionIdLeftMove = "FPbsLeftMoveAction";
    static final String actionIdRightMove = "FPbsRightMoveAction";
    static final String actionIdUpMove = "FPbsUpMoveAction";
    static final String actionIdDownMove = "FPbsDownMoveAction";

    static final String treeColumnName = "productName";
    JButton btnAdd;
    JButton btnDel;
    JButton btnLoad;
    JButton btnDown;
    JButton btnUp;
    JButton btnLeft;
    JButton btnRight;
    JButton btnExpand;
    JButton btnDisplay;
    VWAccountLabel lblAcnt;

    boolean isPm = false;
    private String entryFunType;

    public VwPmsPbsList() {
        VWJComboBox txtEbsStatus = new VWJComboBox();

        Object status[] = new Object[] {"Initial", "Apporved", "Cancel",
                          "Pending", "Closed"};
        VMComboBox vmComboBoxEbsStatus = new VMComboBox(status);
        vmComboBoxEbsStatus.addNullElement();
        txtEbsStatus.setModel(vmComboBoxEbsStatus);

        final Object[][] configs = new Object[][] { {"Product Name", "productName", VMColumnConfig.EDITABLE, null}, {"Product Code", "productCode",
                                   VMColumnConfig.UNEDITABLE,
                                   new VWJText()}, {"Status", "pbsStatus", VMColumnConfig.UNEDITABLE, txtEbsStatus}, {"Reference", "pbsReferrence", //the tree column's render component must be null.
                                   VMColumnConfig.UNEDITABLE,
                                   new VWJText()}, {"Manager", "pbsManager", VMColumnConfig.UNEDITABLE, new VWJLoginId()},

        };

        try {
            super.jbInit(configs, treeColumnName,
                         DtoPmsPbs.class, new PmsPbsNodeViewManager());
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

        lblAcnt = new VWAccountLabel();
        this.getButtonPanel().add(lblAcnt);

        //Add pbs
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });
        btnAdd.setToolTipText("add pbs");

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

        //Left
        btnLeft = this.getButtonPanel().addButton("left.png");
        btnLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLeft(e);
            }
        });
        btnLeft.setToolTipText("move left");

        //Right
        btnRight = this.getButtonPanel().addButton("right.png");
        btnRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedRight(e);
            }
        });
        btnRight.setToolTipText("move right");

        //expand
        btnExpand = this.getButtonPanel().addButton("expand.png");
        btnExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand(e);
            }
        });
        btnExpand.setToolTipText("expand the tree");

        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(this.getTreeTable(), this);
        btnDisplay = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(btnDisplay);

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
            DtoPmsPbs currentDataBean = (DtoPmsPbs) currentNode.getDataBean();
            if (currentDataBean.isInsert()) {
                comMSG.dispErrorDialog("This pbs has not been saved.Cannot add a new pbs to this pbs.");
            } else {
                DtoPmsPbs dtoPmsPbs = new DtoPmsPbs();
                //dtoPmsPbs.setAcntRid(this.inAcntRid);
                dtoPmsPbs.setPbsParentRid(currentDataBean.getPbsRid());
                dtoPmsPbs.setReadonly(false);

                ITreeNode newNode = new DtoTreeNode(dtoPmsPbs);
                newNode = this.getTreeTable().addRow(newNode);
            }
        }else{
            if( this.getTreeTable().getRowCount() == 0 ){

                //add a root
                DtoPmsPbs dtoPmsPbs = new DtoPmsPbs();
                //dtoPmsPbs.setAcntRid(this.inAcntRid);
                //dtoPmsPbs.setProductName(" ");
                dtoPmsPbs.setPbsParentRid(null);
                dtoPmsPbs.setReadonly(false);

                ITreeNode newNode = new DtoTreeNode(dtoPmsPbs);
                newNode = this.getTreeTable().addRow(newNode);
            }
        }
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
            DtoPmsPbs dataBean = (DtoPmsPbs) deleteNode.getDataBean();

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
        ITreeNode node = this.getSelectedNode();
        if( node == null ){
            return;
        }

        DtoPmsPbs dataBean = (DtoPmsPbs) node.getDataBean();

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
        ITreeNode node = this.getSelectedNode();
        if( node == null ){
            return;
        }

        DtoPmsPbs dataBean = (DtoPmsPbs) node.getDataBean();

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

    public void actionPerformedLeft(ActionEvent e) {
        if (this.getTreeTable().isLeftMovable() == false) {
            return;
        }

        ITreeNode node = this.getSelectedNode();
        DtoPmsPbs dataBean = (DtoPmsPbs) node.getDataBean();
        ITreeNode oldParentNode = node.getParent();
        ITreeNode oldParentParentNode = oldParentNode.getParent();
        ITreeNode newParentNode = oldParentParentNode;
        DtoPmsPbs newParentDataBean = (DtoPmsPbs) newParentNode.getDataBean();

        if (dataBean.isInsert()) {
            comMSG.dispErrorDialog("Can't move the data.Please save it first. ");
            return;
        }

        if (newParentDataBean.isInsert()) {
            comMSG.dispErrorDialog("Can't move the data to an unsaved data.Please save the data to move to first.");
            return;
        }

        //save this node to db
        Long parentRidBak = dataBean.getPbsParentRid();
        dataBean.setPbsParentRid(newParentDataBean.getPbsRid());
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdLeftMove);
        inputInfo.setInputObj("dto", dataBean);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            this.getTreeTable().leftMove();
        }else{
            dataBean.setPbsParentRid(parentRidBak);
        }
    }

    public void actionPerformedRight(ActionEvent e) {
        if (this.getTreeTable().isRightMovable() == false) {
            return;
        }

        ITreeNode node = this.getSelectedNode();
        DtoPmsPbs dataBean = (DtoPmsPbs) node.getDataBean();
        ITreeNode oldParentNode = node.getParent();
        int nIndex = oldParentNode.getIndex(node);
        int nNewParentIndex = nIndex - 1;
        ITreeNode newParentNode = oldParentNode.getChildAt(nNewParentIndex);
        DtoPmsPbs newParentDataBean = (DtoPmsPbs) newParentNode.getDataBean();

        if (dataBean.isInsert()) {
            comMSG.dispErrorDialog("Can't move the data.Please save it first. ");
            return;
        }

        if (newParentDataBean.isInsert()) {
            comMSG.dispErrorDialog("Can't move the data to an unsaved data.Please save the data to move to first.");
            return;
        }

        //save this node to db
        Long parentRidBak = dataBean.getPbsParentRid();
        (dataBean).setPbsParentRid(newParentDataBean.getPbsRid());
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdRightMove);
        inputInfo.setInputObj("dto", dataBean);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            this.getTreeTable().rightMove();
        }else{
            dataBean.setPbsParentRid(parentRidBak);
        }
    }

    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        isPm = false;
        //mod by xr 2006/06/26
        //增加界面菜单的参数,SEPG可任意修改,其他菜单保持不变
        entryFunType = (String) param.get("entryFunType");
        log.debug("entryFunType=" + entryFunType);
        if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN;
        }
    }

    //页面刷新－－－－－
    protected void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("entryFunType",entryFunType);
        inputInfo.setActionId(this.actionIdList);
//        inputInfo.setInputObj("inAcntRid", inAcntRid);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            ITreeNode root = (ITreeNode) returnInfo.getReturnObj("root");
            String acntName = (String)returnInfo.getReturnObj("acntName");
            String acntCode = (String)returnInfo.getReturnObj("acntCode");
            Boolean bIsPm = (Boolean)returnInfo.getReturnObj("isPm");
            if( bIsPm != null ){
                isPm = bIsPm.booleanValue();
            }else{
                isPm = false;
            }
            System.out.println("isPm: " +bIsPm);
            this.lblAcnt.setModel( lblAcnt.createtDefaultModel(acntCode, acntName));

            this.getTreeTable().setRoot(root);

            setButtonVisible();
        }
    }

    private void setButtonVisible(){

		if ( isPm == true ) {
                btnAdd.setVisible(true);
                btnDel.setVisible(true);
                btnDown.setVisible(true);
                btnUp.setVisible(true);
                btnLeft.setVisible(true);
                btnRight.setVisible(true);
                return;
        }

        ITreeNode node = this.getSelectedNode();
        if (node != null) {
            DtoPmsPbs dataBean = (DtoPmsPbs) node.getDataBean();
            if ( isPm == true || dataBean.isReadonly() == false) {
                btnAdd.setVisible(true);
                btnDel.setVisible(true);
                btnDown.setVisible(true);
                btnUp.setVisible(true);
                btnLeft.setVisible(true);
                btnRight.setVisible(true);
            } else {
                btnAdd.setVisible(false);
                btnDel.setVisible(false);
                btnDown.setVisible(false);
                btnUp.setVisible(false);
                btnLeft.setVisible(false);
                btnRight.setVisible(false);
            }
        } else {
            btnAdd.setVisible(false);
            btnDel.setVisible(false);
            btnDown.setVisible(false);
            btnUp.setVisible(false);
            btnLeft.setVisible(false);
            btnRight.setVisible(false);
       }

        btnLoad.setVisible(true);
        btnDisplay.setVisible(true);
        btnExpand.setVisible(true);
     }

    public static void main(String args[]) {
        VWWorkArea workArea0 = new VWWorkArea();
        VWWorkArea workArea = new VwPmsPbsList();

        workArea0.addTab("Pbs", workArea);
        TestPanel.show(workArea0);

        Parameter param = new Parameter();
        param.put("inAcntRid", new Long(7));
        workArea.setParameter(param);

        workArea.refreshWorkArea();
    }


}
