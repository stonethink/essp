package client.essp.pms.activity;

import client.essp.common.view.VWTreeTableWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import client.framework.model.VMColumnConfig;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.pms.wbs.WbsNodeViewManager;
import c2s.dto.ReturnInfo;
import client.framework.common.Constant;
import java.awt.event.ActionEvent;
import com.wits.util.Parameter;
import c2s.essp.pms.wbs.DtoKEY;
import client.essp.common.view.VWWorkArea;
import c2s.dto.InputInfo;
import c2s.dto.ITreeNode;
import com.wits.util.TestPanel;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import client.framework.view.common.comMSG;
import c2s.dto.IDto;
import com.wits.util.IVariantListener;
import com.wits.util.ProcessVariant;
import client.framework.view.vwcomp.VWJReal;
import c2s.dto.DtoUtil;
import client.framework.view.vwcomp.VWJLabel;
import client.essp.common.view.VWAccountLabel;
import c2s.essp.pms.account.DtoAcntKEY;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import client.essp.common.loginId.VWJLoginId;

public class VwActivityList extends VWTreeTableWorkArea implements
    IVariantListener {
    private static final String actionIdList = "FWbsActivityListAction";
    private static final String actionIdAdd = "FWbsActivityAddAction";
    private static final String actionIdDelete = "FWbsActivityDeleteAction";
    private static final String actionIdUpdate = "FWbsActivityUpdateAction";
    private static final String actionIdUpMove = "FWbsActivityUpMoveAction";
    private static final String actionIdDownMove = "FWbsActivityDownMoveAction";
    private static final String actionIdLeftMove = "FWbsActivityLeftMoveAction";
    private static final String actionIdRightMove =
        "FWbsActivityRightMoveAction";
    private static final String actionIdGetCode = "FWbsActivityGetCodeAction";
    private static final String actionIdCompute = "FWbsComputeAction";

    /**
     * define common data (globe)
     */
    Object[][] configs = null;
    static final String treeColumnName = "name";
    private Long inAcntRid;
    VWAccountLabel accountLabel = null;
    private String entryFunType;

    private VWJLabel filterLbl;

    public VwActivityList(VWJLabel filterLbl) {
        this();
        this.filterLbl = filterLbl;
    }

    public VwActivityList() {
        try {

            VWJReal weightDisp = new VWJReal();
            weightDisp.setMaxInputDecimalDigit(2);
            weightDisp.setMaxInputIntegerDigit(3);

            VWJReal completeRateDisp = new VWJReal();
            completeRateDisp.setMaxInputDecimalDigit(2);
            completeRateDisp.setMaxInputIntegerDigit(3);

            VWJReal timeLimitDisp = new VWJReal();
            timeLimitDisp.setMaxInputDecimalDigit(0);
            timeLimitDisp.setMaxInputIntegerDigit(8);

            configs = new Object[][] { 
			{"Name", "name", VMColumnConfig.EDITABLE, null},
			{"Code", "code", VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE}, 
			{"Duration", "timeLimit", VMColumnConfig.UNEDITABLE, timeLimitDisp}, 
			{"Planned Start", "plannedStart", VMColumnConfig.UNEDITABLE, new VWJDate()},
			{"Planned Finish", "plannedFinish", VMColumnConfig.UNEDITABLE, new VWJDate()},
			{"Actual Start", "actualStart", VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE},
			{"Actual Finish", "actualFinish", VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE} , 
			{"Weight", "weight", VMColumnConfig.UNEDITABLE, weightDisp}, 
			{"%Complete", "completeRate", VMColumnConfig.UNEDITABLE, completeRateDisp}, 
			{"Manager", "manager", VMColumnConfig.UNEDITABLE, new VWJLoginId()}, 
			{"Complete Method", "completeMethod", VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE},
            };

            super.jbInit(configs, treeColumnName,
                         DtoWbsActivity.class, new WbsNodeViewManager());
            //设置初始列宽
            JTableHeader header = this.getTreeTable().getTableHeader();
//            header.setReorderingAllowed(false);
            TableColumnModel tcModel = header.getColumnModel();
            tcModel.getColumn(0).setPreferredWidth(250);
            tcModel.getColumn(1).setPreferredWidth(30);
            tcModel.getColumn(2).setPreferredWidth(70);
            tcModel.getColumn(3).setPreferredWidth(70);
            tcModel.getColumn(4).setPreferredWidth(40);
            tcModel.getColumn(5).setPreferredWidth(60);
            tcModel.getColumn(6).setPreferredWidth(100);

        } catch (Exception e) {
            e.printStackTrace();
        }
        addUICEvent();
    }

    private void addUICEvent() {
        //捕获事件－－－－
        ProcessVariant.addDataListener("activitytree", this);

        accountLabel = new VWAccountLabel();
        this.getButtonPanel().add(accountLabel);
    }

    private boolean checkUnsaved() {
        ITreeNode currentNode = (ITreeNode)this.getTreeTable().
                                getTreeTableModel().getRoot();
        ITreeNode checkNode = checkUnsaved(currentNode);
        if (checkNode != null) {
            this.getTreeTable().setSelectedRow(checkNode);
            comMSG.dispErrorDialog(
                "Before this activity saved, you can't add a new activity.");
            return false;
        }
        return true;
    }

    private ITreeNode checkUnsaved(ITreeNode node) {
        DtoWbsActivity currentDataBean = (DtoWbsActivity) node.
                                         getDataBean();
        if (currentDataBean.isActivity() && currentDataBean.isInsert()) {
            return node;
        }

        if (currentDataBean.isWbs()) {
            java.util.List children = node.children();
            for (int i = 0; i < children.size(); i++) {
                ITreeNode child = (ITreeNode) children.get(i);
                ITreeNode checkNode = checkUnsaved(child);
                if (checkNode != null) {
                    return checkNode;
                }
            }
        }

        return null;
    }

    public void actionPerformedAdd(ActionEvent e) {
        if (!checkUnsaved()) {
            return;
        }
        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode != null) {
            DtoWbsActivity currentDataBean = (DtoWbsActivity) currentNode.
                                             getDataBean();
            if (currentDataBean.isActivity()) {
                currentNode = currentNode.getParent();
                currentDataBean = (DtoWbsActivity) currentNode.
                                  getDataBean();
            }

            if (currentDataBean.isInsert()) {
                comMSG.dispErrorDialog(
                    "This wbs has not been saved.Cannot add a new activity to this wbs.");
            } else {

                DtoWbsActivity dataBean = new DtoWbsActivity();
                dataBean.setAcntRid(currentDataBean.getAcntRid());
                //dataBean.setParentRid(currentDataBean.getWbsRid());
                dataBean.setWbsRid(currentDataBean.getWbsRid());
                dataBean.setActivity(true);

                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj(DtoKEY.DTO, dataBean);
                inputInfo.setActionId(this.actionIdGetCode);

                ReturnInfo returnInfo = accessData(inputInfo);
                dataBean = (DtoWbsActivity) returnInfo.getReturnObj(DtoKEY.DTO);
                dataBean.setAutoCode(dataBean.getCode());
                dataBean.setName(dataBean.getCode());
                dataBean.setWeight(new Double(1.00));
                dataBean.setOp(DtoWbsActivity.OP_INSERT);

                DtoWbsTreeNode newNode = new DtoWbsTreeNode(dataBean);
                this.getTreeTable().addRow(currentNode, newNode);
                fireProcessDataChange();
            }
        }
    }

    public void actionPerformedDelete(ActionEvent e) {
        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            ITreeNode deleteNode = this.getSelectedNode();
            ITreeNode parentNode = deleteNode.getParent();
            IDto dataBean = (IDto) deleteNode.getDataBean();
            DtoWbsActivity parentBean = (DtoWbsActivity) parentNode.getDataBean();

            //delete from db
            if (dataBean.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj(DtoKEY.DTO, dataBean);
                inputInfo.setActionId(this.actionIdDelete);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (!returnInfo.isError()) {
                    getTreeTable().deleteRow();
                    if (parentNode.getChildCount() == 0) {
                        parentBean.setHasActivity(false);
                    }
                    fireProcessDataChange();
                }
            } else {
                getTreeTable().deleteRow();
            }
        }
    }

    public void actionPerformedDown(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();
        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdDownMove);
        inputInfo.setInputObj(DtoKEY.DTO, node);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            getTreeTable().downMove();
            fireProcessDataChange();
        }
    }

    public void actionPerformedUp(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();
        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpMove);
        inputInfo.setInputObj(DtoKEY.DTO, node);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            getTreeTable().upMove();
            fireProcessDataChange();
        }
    }

    public void actionPerformedLeft(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdLeftMove);
        inputInfo.setInputObj(DtoKEY.DTO, node);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            getTreeTable().leftMove();
            fireProcessDataChange();
        }
    }

    public void actionPerformedRight(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdRightMove);
        inputInfo.setInputObj(DtoKEY.DTO, node);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            getTreeTable().rightMove();
            fireProcessDataChange();
        }
    }

    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }

    public void actionPerformedCompute(ActionEvent e) {
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdCompute);
        inputInfo.setInputObj(DtoKEY.ACCOUNT_ID, inAcntRid.longValue() + "");

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            DtoWbsTreeNode root = (DtoWbsTreeNode) returnInfo.getReturnObj(
                DtoKEY.
                WBSTREE);
            root.setActivityTree(true);
            fireProcessDataChange();
            this.getTreeTable().setRoot(root);
            //计算完成之后会清除掉过滤
            filterLbl.setText(VwActivityFilter.FILTER_CONDITION[0]);
            filterLbl.setToolTipText("Fileter By:" +
                                     VwActivityFilter.FILTER_CONDITION[0]);
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.inAcntRid = (Long) (param.get("inAcntRid"));
        //mod by xr 2006/06/26
        //增加界面菜单的参数,SEPG可任意修改,其他菜单保持不变
        String newEntryFunType = (String) param.get("entryFunType");
        if(newEntryFunType != null) {
            entryFunType = newEntryFunType;
        } else if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN;
        }


    }

    public void reloadData() {
        if (inAcntRid == null) {
            inAcntRid = new Long(0);
        }
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.getActionIdList());
        inputInfo.setInputObj("entryFunType", entryFunType);
        inputInfo.setInputObj(DtoKEY.ACCOUNT_ID, inAcntRid.longValue() + "");

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            DtoWbsTreeNode root = (DtoWbsTreeNode) returnInfo.getReturnObj(
                DtoKEY.WBSTREE);
            root.setActivityTree(true);
            ProcessVariant.set("activitytree", root.clone());
            this.getTreeTable().setRoot(root);
//            if (this.filterLbl != null) {
//                this.filterLbl.setText(VwActivityFilter.FILTER_CONDITION[0]);
//            }
            DtoWbsActivity dto = (DtoWbsActivity) root.getDataBean();
            inAcntRid = dto.getAcntRid();

            ProcessVariant.set("accountChanged_activity", Boolean.FALSE);
            fireProcessDataChange();
            //this.getTreeTable().updateUI();
        }
    }

    public DtoWbsTreeNode getBackRoot() {
        String curFilter = filterLbl.getText();
        if (curFilter != null &&
            !curFilter.equals(VwActivityFilter.FILTER_CONDITION[0])) {
            reloadData();
        }
        return (DtoWbsTreeNode)this.getModel().getRoot();
    }

    //页面刷新－－－－－
    protected void resetUI() {
        Object wbstree = ProcessVariant.get("activitytree");
        if (wbstree != null) {
            DtoWbsTreeNode root = (DtoWbsTreeNode) wbstree;
            DtoWbsActivity dto = (DtoWbsActivity) root.getDataBean();

            Object acccountChanged = ProcessVariant.get("accountChanged_activity");
            if (acccountChanged != null && Boolean.TRUE.equals(acccountChanged)) {
                reloadData();
            } else {
                root = (DtoWbsTreeNode) root.clone();
                root.setActivityTree(true);
                this.getTreeTable().setRoot(root);
                DtoWbsActivity rootDto = (DtoWbsActivity) root.getDataBean();
                accountLabel.setModel(rootDto);
            }
        } else {
            reloadData();
        }
    }

    public void saveWorkArea() {
    }

    public void updateTreeTable() {
        getTreeTable().refreshTree();
    }

    public void dataChanged(String name, Object value) {
        if (value != null) {
            DtoWbsTreeNode root = (DtoWbsTreeNode) DtoUtil.deepClone(value);
            root.setActivityTree(true);
            this.getTreeTable().setRoot(root);
        }
    }

    protected void fireProcessDataChange() {
        Object root = this.getTreeTable().getTreeTableModel().getRoot();
        ProcessVariant.set("activitytree", root);
        ProcessVariant.fireDataChange("activitytree", this);

        DtoWbsActivity rootDto = (DtoWbsActivity) ((ITreeNode) root).
                                 getDataBean();
        accountLabel.setModel(rootDto);
    }

    protected String getActionIdList() {
        return actionIdList;
    }

    public static void main(String args[]) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            LookAndFeel lf=UIManager.getLookAndFeel();
//
//            UISetting.settingUIManager();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        VWWorkArea workArea0 = new VWWorkArea();
        VWWorkArea workArea = new VwActivityList(null);
        workArea0.addTab("Activity", workArea);
        TestPanel.show(workArea0);

        Parameter param = new Parameter();
        param.put("inAcntRid", new Long(1));
        workArea.setParameter(param);

        workArea.refreshWorkArea();
    }
}
