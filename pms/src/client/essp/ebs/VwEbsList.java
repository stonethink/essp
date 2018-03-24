package client.essp.ebs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.ebs.DtoAssignAcnt;
import c2s.essp.ebs.DtoEbsEbs;
import c2s.essp.ebs.DtoPmsAcnt;
import c2s.essp.ebs.IDtoEbsAcnt;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.essp.common.loginId.VWJLoginId;

public class VwEbsList extends VWTreeTableWorkArea {
    private String actionIdList = "FEbsListAction";
    private String actionIdEbsDelete = "FEbsDeleteAction";
    private String actionIdEbsUpdate = "FEbsUpdateAction";

    private String actionIdAcntDelete = "FEbsAcntDeleteAction";
    private String actionIdAcntUpdate = "FEbsAcntUpdateAction";

    private String actionIdEbs9AcntUpdate = "FEbs9AcntUpdateAction";

    private String actionIdAssignAcnt = "FEbsAssignAcntAction";
    private String actionIdAssignAcntList = "FEbsAssignAcntListAction";

    /**
     * define common data (globe)
     */
    Map acntMap;
    String entryFunType = "EbsMaintain"; //EbsMaintain, EbsMaintainHQ, EbsView

    JButton btnAddEbs = null;
    JButton btnAddAcnt = null;
    JButton btnAssignAcnt = null;
    JButton btnDel = null;
    JButton btnRefresh = null;
    JButton btnDown = null;
    JButton btnUp = null;
    JButton btnLeft = null;
    JButton btnRight = null;
    JButton btnDisp = null;
    JButton btnExpand = null;

    public VwEbsList() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    //Component initialization
    private void jbInit() throws Exception {
        VWJDate vwjdate1 = new VWJDate();
        vwjdate1.setDataType(VWJDate._DATA_PRO_YMD);
        vwjdate1.setCanSelect(true);

        Object[][] configs = new Object[][] {
                {"Code", "code", VMColumnConfig.EDITABLE, null}
              , {"Name", "name", VMColumnConfig.UNEDITABLE,new VWJText()}
              , {"Manager", "manager", VMColumnConfig.UNEDITABLE,new VWJLoginId()}
              , {"Status", "status", VMColumnConfig.UNEDITABLE,new VWJText()}
        };

        super.jbInit(configs, "code", DtoEbsEbs.class, new EbsNodeViewManager());
    }

    private void addUICEvent() {
        //捕获事件－－－－

        //Add Ebs
        btnAddEbs = this.getButtonPanel().addButton("group.gif");
        btnAddEbs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAddEbs(e);
            }
        });
        btnAddEbs.setToolTipText("New EBS Group");

        //Add Account
        btnAddAcnt = this.getButtonPanel().addButton("account.gif");
        btnAddAcnt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAddAcnt(e);
            }
        });
        btnAddAcnt.setToolTipText("New Account");

        //Assign Account
        btnAssignAcnt = this.getButtonPanel().addButton(
            "addExist.gif");
        btnAssignAcnt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAddExistAcnt(e);
            }
        });
        btnAssignAcnt.setToolTipText("Assign Account to EBS Group.");

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

        //Expand
        btnExpand = this.getButtonPanel().addButton("expand.png");
        btnExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand(e);
            }
        });
        btnRight.setToolTipText("expand the node");

        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(this.getTreeTable(), this);
        btnDisp = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(btnDisp);

        //Refresh
        btnRefresh = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter para) {
        super.setParameter(para);
        entryFunType = (String) para.get("entryFunType");
       if (entryFunType == null || entryFunType.length() == 0) {
           entryFunType = "EbsMaintain";
       }
    }

    public void actionPerformedAddEbs(ActionEvent e) {
        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode != null) {
            IDtoEbsAcnt currentDataBean = (IDtoEbsAcnt) currentNode.getDataBean();
            if (currentDataBean.isEbs()) {
                if (currentDataBean.getRid() == null) {
                    comMSG.dispErrorDialog(
                        "This ebs group didn't be saved.Cannot add an ebs group to this ebs group.Please save it first.");
                } else {
                    DtoEbsEbs dtoEbsEbs = new DtoEbsEbs();
                    ITreeNode newNode = new DtoTreeNode(dtoEbsEbs);
                    newNode = this.getTreeTable().addRow(newNode);

                    dtoEbsEbs.setEbsParentRid(currentDataBean.getRid());
                }
            } else {
                comMSG.dispErrorDialog("Cannot add an ebs group to an account");
            }
        }else{
            if( this.getTreeTable().getRowCount() == 0 ){

                //add a root
                DtoEbsEbs dtoEbsEbs = new DtoEbsEbs();
                ITreeNode newNode = new DtoTreeNode(dtoEbsEbs);
                newNode = this.getTreeTable().addRow(newNode);

                dtoEbsEbs.setEbsParentRid(null);
            }
        }
    }

    public void actionPerformedAddAcnt(ActionEvent e) {
        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode != null) {
            IDtoEbsAcnt currentDataBean = (IDtoEbsAcnt) currentNode.getDataBean();
            if (currentDataBean.isEbs()) {
                if (currentDataBean.getRid() == null) {
                    comMSG.dispErrorDialog("This ebs group has not been saved.Cannot add an account to this ebs group.Please save it first.");
                } else {
                    DtoPmsAcnt dtoPmsAcnt = new DtoPmsAcnt();
                    ITreeNode newNode = new DtoTreeNode(dtoPmsAcnt);
                    newNode = this.getTreeTable().addRow(newNode);
                }
            } else {
                comMSG.dispErrorDialog("Cannot add an account to an account");
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

            //cannot delete root
            //return;
        }

        IDtoEbsAcnt dataBean = (IDtoEbsAcnt) deleteNode.getDataBean();

        int option = comMSG.dispConfirmDialog("Do you delete the data?\r\nNote: This will delete data belong to it.");
        if (option == Constant.OK) {

            if (dataBean.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                if (dataBean.isEbs()) {
                    inputInfo.setInputObj("dto", dataBean);
                    inputInfo.setActionId(this.actionIdEbsDelete);
                } else if (dataBean.isAcnt()) {
                    Long parentId = null;
                    if (parentNode != null) {
                        DtoEbsEbs parentDataBean = (DtoEbsEbs) parentNode.
                            getDataBean();
                        parentId = parentDataBean.getRid();
                    }

                    inputInfo.setInputObj("dto", dataBean);
                    inputInfo.setInputObj("parentId", parentId);
                    inputInfo.setActionId(this.actionIdAcntDelete);
                }

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
        this.getTreeTable().downMove();
    }

    public void actionPerformedUp(ActionEvent e) {
        this.getTreeTable().upMove();
    }

    public void actionPerformedLeft(ActionEvent e) {
        if (this.getTreeTable().isLeftMovable() == false) {
            return;
        }

        ITreeNode node = this.getSelectedNode();
        IDtoEbsAcnt dataBean = (IDtoEbsAcnt) node.getDataBean();
        ITreeNode oldParentNode = node.getParent();
        ITreeNode oldParentParentNode = oldParentNode.getParent();
        ITreeNode newParentNode = oldParentParentNode;
        IDtoEbsAcnt newParentDataBean = (IDtoEbsAcnt) newParentNode.getDataBean();

        if (dataBean.isInsert()) {
            comMSG.dispErrorDialog("Can't move the data.Please save it first. ");
            return;
        }

        if (newParentDataBean.isInsert()) {
            comMSG.dispErrorDialog("Can't move the data to an unsaved data.Please save the data to move to first.");
            return;
        }

        //如果新的parent节点已经含有要移动的节点，则不容许移动
        //新的parent节点是它的祖父节点
        if (isAlreadyHasTheChild(newParentNode, node) == true) {
            comMSG.dispErrorDialog("Can't move the data.The Ebs to move to already has the data.");
            return;
        }

        InputInfo inputInfo = new InputInfo();
        if (dataBean.isEbs()) {
            Long parentRidBak = ((DtoEbsEbs) dataBean).getEbsParentRid();
            ((DtoEbsEbs) dataBean).setEbsParentRid(newParentDataBean.getRid());

            //save this node to db
            inputInfo.setActionId(this.actionIdEbsUpdate);
            inputInfo.setInputObj("dto", dataBean);


            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                this.getTreeTable().leftMove();
            }else{
                ((DtoEbsEbs) dataBean).setEbsParentRid(parentRidBak);
            }

        } else if (dataBean.isAcnt()) {
            IDtoEbsAcnt oldParentDataBean = (IDtoEbsAcnt) oldParentNode.
                                            getDataBean();
            Long oldEbsRid = oldParentDataBean.getRid();
            Long newEbsRid = newParentDataBean.getRid();

            //save this node to db
            inputInfo.setActionId(this.actionIdEbs9AcntUpdate);
            inputInfo.setInputObj("acntRid", dataBean.getRid());
            inputInfo.setInputObj("oldEbsRid", oldEbsRid);
            inputInfo.setInputObj("newEbsRid", newEbsRid);

            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                this.getTreeTable().leftMove();
            }
        }
    }

    public void actionPerformedRight(ActionEvent e) {
        if (this.getTreeTable().isRightMovable() == false) {
            return;
        }

        ITreeNode node = this.getSelectedNode();
        IDtoEbsAcnt dataBean = (IDtoEbsAcnt) node.getDataBean();
        ITreeNode oldParentNode = node.getParent();
        int nIndex = oldParentNode.getIndex(node);
        int nNewParentIndex = nIndex - 1;
        ITreeNode newParentNode = oldParentNode.getChildAt(nNewParentIndex);
        IDtoEbsAcnt newParentDataBean = (IDtoEbsAcnt) newParentNode.getDataBean();

        if (dataBean.isInsert()) {
            comMSG.dispErrorDialog("Can't move the data.Please save it first. ");
            return;
        }

        if (newParentDataBean.isInsert()) {
            comMSG.dispErrorDialog("Can't move the data to an unsaved data.Please save the data to move to first.");
            return;
        }

        //如果新的parent节点是account,则不容许移动
        if (newParentDataBean.isAcnt()) {
            comMSG.dispErrorDialog("Can't move the data to an account。");
            return;
        }

        //如果新的parent节点已经含有要移动的节点，则不容许移动
        //新的parent节点是它的上一个兄弟节点
        if (isAlreadyHasTheChild(newParentNode, node) == true) {
            comMSG.dispErrorDialog("Can't move the data.The Ebs to move to already has the data.");
            return;
        }

        InputInfo inputInfo = new InputInfo();
        if (dataBean.isEbs()) {
            Long parentRidBak = ((DtoEbsEbs) dataBean).getEbsParentRid();
            ((DtoEbsEbs) dataBean).setEbsParentRid(newParentDataBean.getRid());

            //save this node to db
            inputInfo.setActionId(this.actionIdEbsUpdate);
            inputInfo.setInputObj("dto", dataBean);

            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                this.getTreeTable().rightMove();
            }else{
                ((DtoEbsEbs)dataBean).setEbsParentRid(parentRidBak);
            }
        } else if (dataBean.isAcnt()) {
            IDtoEbsAcnt oldParentDataBean = (IDtoEbsAcnt) oldParentNode.
                                            getDataBean();
            Long oldEbsRid = oldParentDataBean.getRid();
            Long newEbsRid = newParentDataBean.getRid();

            //save this node to db
            inputInfo.setActionId(this.actionIdEbs9AcntUpdate);
            inputInfo.setInputObj("acntRid", dataBean.getRid());
            inputInfo.setInputObj("oldEbsRid", oldEbsRid);
            inputInfo.setInputObj("newEbsRid", newEbsRid);

            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                this.getTreeTable().rightMove();
            }
        }

    }

    void actionPerformedAddExistAcnt(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();
        if( node == null ){
            return;
        }

        IDtoEbsAcnt dtoEbsEbs = (IDtoEbsAcnt) node.getDataBean();
        if (dtoEbsEbs.isAcnt()) {
            return;
        }

        Long ebsRid = dtoEbsEbs.getRid();

        //get account list
        List accountList;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdAssignAcntList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }
        accountList = (List) returnInfo.getReturnObj("accountList");

        //filter the child account
        List childAcnts = new ArrayList();
        Vector notChildAcnts = new Vector();
        for (int i = 0; i < node.getChildCount(); i++) {
            ITreeNode child = node.getChildAt(i);
            if (((IDtoEbsAcnt) child.getDataBean()).isAcnt()) {
                childAcnts.add(((IDtoEbsAcnt) child.getDataBean()).getRid());
            }
        }
        for (int i = 0; i < accountList.size(); i++) {
            DtoAssignAcnt dto = (DtoAssignAcnt) accountList.get(i);
            if (childAcnts.contains(dto.getRid()) == false) {
                notChildAcnts.add(dto);
            }
        }

        javax.swing.JList accCodeList = new JList(notChildAcnts);
        javax.swing.JScrollPane scrollPane = new JScrollPane(accCodeList);
        scrollPane.setPreferredSize(new Dimension(240, 400));
//        int iOpt = JOptionPane.showConfirmDialog(this,
//                                                 scrollPane, "select accounts",
//                                                 JOptionPane.OK_CANCEL_OPTION,
//                                                 JOptionPane.QUESTION_MESSAGE,
//                                                 null
//                   );

        VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(), "Assign Account to EBS Group", scrollPane);
        int iOpt = popupEditor.showConfirm();

        if (iOpt != Constant.OK) {
            return;
        }
        int[] selIndexes = accCodeList.getSelectedIndices();

        List newAccountList = new ArrayList();
        for (int i = 0; i < selIndexes.length; i++) {
            DtoAssignAcnt newAccount = (DtoAssignAcnt) notChildAcnts.get(
                selIndexes[i]);
            newAccountList.add(newAccount);
        }

        //save to database
        addExistAccount(ebsRid, newAccountList);
    }

    private void addExistAccount(Long ebsRid, List newAccountList) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("newAccountList", newAccountList);
        inputInfo.setInputObj("ebsRid", ebsRid);
        inputInfo.setActionId(this.actionIdAssignAcnt);

        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (returnInfo.isError() == false) {
            List accountList = (List) returnInfo.getReturnObj("accountList");
            for (int i = 0; i < accountList.size(); i++) {
                DtoPmsAcnt dtoPmsAcnt = (DtoPmsAcnt) accountList.get(i);
                DtoPmsAcnt oldAcnt = (DtoPmsAcnt) acntMap.get(dtoPmsAcnt.getRid());

                ITreeNode newNode;
                if (oldAcnt != null) {
                    //如果相同rid的account已经在tree中，那使用在tree中account对象
                    newNode = new DtoTreeNode(oldAcnt);
                } else {
                    newNode = new DtoTreeNode(dtoPmsAcnt);
                }
                this.getTreeTable().addRow(newNode);
            }
        }
    }

    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }

    public void actionPerformedLoad(ActionEvent e){
        resetUI();
    }

    //页面刷新－－－－－
    protected void resetUI() {
        setBtnVisible();
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            ITreeNode root =  (ITreeNode) returnInfo.getReturnObj("root");
            this.acntMap = (Map) returnInfo.getReturnObj("acntMap");

            this.getTreeTable().setRoot(root);
        }
    }

    private void setBtnVisible(){

        //EbsMaintain, EbsMaintainHQ
        if (entryFunType.equals("EbsMaintain") ||
            entryFunType.equals("EbsMaintainHQ")) {
            btnAddEbs.setVisible(true);
            btnAddAcnt.setVisible(true);
            btnAssignAcnt.setVisible(true);
            btnDel.setVisible(true);
            btnDown.setVisible(true);
            btnUp.setVisible(true);
            btnLeft.setVisible(true);
            btnRight.setVisible(true);
        }else{
            btnAddEbs.setVisible(false);
            btnAddAcnt.setVisible(false);
            btnAssignAcnt.setVisible(false);
            btnDel.setVisible(false);
            btnDown.setVisible(false);
            btnUp.setVisible(false);
            btnLeft.setVisible(false);
            btnRight.setVisible(false);
        }

        btnRefresh.setVisible(true);
        btnDisp.setVisible(true);
        btnExpand.setVisible(true);
    }

    public void saveWorkArea() {

    }

    private boolean isAlreadyHasTheChild(ITreeNode parentNode, ITreeNode node) {

        IDtoEbsAcnt dataBean = (IDtoEbsAcnt) node.getDataBean();
        Long rid = dataBean.getRid();
        if (rid == null) {
            return false;
        }
        for (int i = 0; i < parentNode.getChildCount(); i++) {
            ITreeNode tNode = parentNode.getChildAt(i);
            IDtoEbsAcnt tDataBean = (IDtoEbsAcnt) tNode.getDataBean();
            Long tRid = tDataBean.getRid();

            if ((dataBean.isAcnt() == true && tDataBean.isAcnt() &&
                 rid.equals(tRid) == true) ||
                (dataBean.isEbs() == true && tDataBean.isEbs() &&
                 rid.equals(tRid) == true)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String args[]) {

        VWWorkArea workArea = new VwEbsList();
        workArea.setParameter(new Parameter());
        workArea.refreshWorkArea();

        VWTDWorkArea td = new VWTDWorkArea(300);
        td.getTopArea().add(workArea);

        td.getDownArea().addTab("", new VWWorkArea());

        TestPanel.show(td);
    }


}
