package client.essp.pms.wbs;

import client.essp.common.view.VWTreeTableWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import client.framework.model.VMColumnConfig;
import c2s.essp.pms.wbs.DtoWbsActivity;
import java.awt.event.ActionEvent;
import c2s.dto.ITreeNode;
import client.framework.view.common.comMSG;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import client.framework.common.Constant;
import c2s.dto.IDto;
import c2s.dto.InputInfo;
import com.wits.util.Parameter;
import c2s.dto.ReturnInfo;
import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;
import c2s.essp.pms.wbs.DtoKEY;
import com.wits.util.IVariantListener;
import com.wits.util.ProcessVariant;
import client.framework.view.vwcomp.VWJReal;
import c2s.dto.DtoUtil;
import client.essp.common.view.VWAccountLabel;
import c2s.essp.pms.wbs.DateCheck;
import c2s.essp.pms.account.DtoAcntKEY;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import client.framework.view.vwcomp.VWJLabel;
import client.essp.common.loginId.VWJLoginId;

public class VwWbsList extends VWTreeTableWorkArea implements IVariantListener {
    private static final String actionIdList = "FWbsListAction";
    private static final String actionIdAdd = "FWbsAddAction";
    private static final String actionIdDelete = "FWbsDeleteAction";
    private static final String actionIdUpdate = "FWbsUpdateAction";
    private static final String actionIdUpMove = "FWbsUpMoveAction";
    private static final String actionIdDownMove = "FWbsDownMoveAction";
    private static final String actionIdLeftMove = "FWbsLeftMoveAction";
    private static final String actionIdRightMove = "FWbsRightMoveAction";
    private static final String actionIdGetRid = "FWbsGetRidAction";
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

    public VwWbsList(VWJLabel filterLbl) {
        this();
        this.filterLbl = filterLbl;
    }

    public VwWbsList() {
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

            configs = new Object[][] { {"Name", "name",
                      VMColumnConfig.EDITABLE, null}, {"Code", "code",
                      VMColumnConfig.UNEDITABLE, new VWJText(),
                      Boolean.TRUE}, {"Duration",
                      "timeLimit", VMColumnConfig.UNEDITABLE, timeLimitDisp},
                      {"Planned Start", "plannedStart",
                      VMColumnConfig.UNEDITABLE, new VWJDate()},
                      {"Planned Finish", "plannedFinish",
                      VMColumnConfig.UNEDITABLE, new VWJDate()},
                      {"Actual Start", "actualStart",
                      VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE},
                      {"Actual Finish", "actualFinish",
                      VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE},
                      {"Weight", "weight", VMColumnConfig.UNEDITABLE,
                      weightDisp}, {"%Complete", "completeRate",
                      VMColumnConfig.UNEDITABLE,
                      completeRateDisp}, {"Manager", "manager",
                      VMColumnConfig.UNEDITABLE, new VWJLoginId()},
                      {"Complete Method", "completeMethod",
                      VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE}
            };

            super.jbInit(configs, treeColumnName,
                         DtoWbsActivity.class, new WbsNodeViewManager());

            //调整列的宽度
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
//            tcModel.getColumn(7).setPreferredWidth(60);//完工方法
//            tcModel.getColumn(8).setPreferredWidth(50);//code
//            tcModel.getColumn(9).setPreferredWidth(70);//实际开始
//            tcModel.getColumn(10).setPreferredWidth(70);//实际结束
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void addUICEvent() {
        //捕获事件－－－－
        ProcessVariant.addDataListener("wbstree", this);

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
                "Before this wbs saved, you can't add a new wbs.");
            return false;
        }
        return true;
    }

    private ITreeNode checkUnsaved(ITreeNode node) {
        DtoWbsActivity currentDataBean = (DtoWbsActivity) node.
                                         getDataBean();
        if (currentDataBean.isInsert()) {
            return node;
        }

        java.util.List children = node.children();
        for (int i = 0; i < children.size(); i++) {
            ITreeNode child = (ITreeNode) children.get(i);
            ITreeNode checkNode = checkUnsaved(child);
            if (checkNode != null) {
                return checkNode;
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
            if (currentDataBean.isInsert()) {
                comMSG.dispErrorDialog(
                    "This wbs has not been saved.Cannot add a new wbs to this wbs.");
//            } else if(currentDataBean.hasActivity()) {
//                comMSG.dispErrorDialog(
//                    "This wbs has activities.Cannot add a wbs child to this wbs.");
            } else {
                DtoWbsActivity dataBean = new DtoWbsActivity();
                dataBean.setAcntRid(currentDataBean.getAcntRid());
                dataBean.setParentRid(currentDataBean.getWbsRid());
                dataBean.setWbs(true);

                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj(DtoKEY.DTO, dataBean);
                inputInfo.setActionId(this.actionIdGetRid);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (!returnInfo.isError()) {
                    dataBean = (DtoWbsActivity) returnInfo.getReturnObj(DtoKEY.
                        DTO);
                    Long rid = dataBean.getWbsRid();
                    String code = "";
                    if (rid != null) {
                        java.text.DecimalFormat df = new java.text.
                            DecimalFormat("W0000");
                        code = df.format(rid);
                    }
                    dataBean.setName(code);
                    dataBean.setAutoCode(code);
                    dataBean.setCode(code);
                    dataBean.setWeight(new Double(1.00));
                    dataBean.setCompleteMethod(DtoWbsActivity.
                                               WBS_COMPLETE_BY_ACTIVITY);

                    dataBean.setOp(IDto.OP_INSERT);

                    DtoWbsTreeNode newNode = new DtoWbsTreeNode(dataBean);
                    this.getTreeTable().addRow(newNode);
                }
            }
        } else {
            ITreeNode root = (ITreeNode)this.getTreeTable().getTreeTableModel().
                             getRoot();
            if (root == null) {
                DtoWbsActivity dataBean = new DtoWbsActivity();
                dataBean.setAcntRid(inAcntRid);
                dataBean.setParentRid(null);
                dataBean.setWbs(true);

                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj(DtoKEY.DTO, dataBean);
                inputInfo.setActionId(this.actionIdGetRid);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (!returnInfo.isError()) {
                    dataBean = (DtoWbsActivity) returnInfo.getReturnObj(DtoKEY.
                        DTO);
                }

                dataBean.setOp(IDto.OP_INSERT);

                DtoWbsTreeNode newNode = new DtoWbsTreeNode(dataBean);
                this.getTreeTable().setRoot(newNode);

            }
        }
    }

    public void actionPerformedDelete(ActionEvent e) {
        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            ITreeNode deleteNode = this.getSelectedNode();
            if (deleteNode.getParent() == null) {
                comMSG.dispErrorDialog("Can't delete root!!!");
                return;
            }

//            ITreeNode parentNode = deleteNode.getParent();
            IDto dataBean = (IDto) deleteNode.getDataBean();
            //delete from db
            if (dataBean.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj(DtoKEY.DTO, dataBean);
                inputInfo.setActionId(this.actionIdDelete);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (!returnInfo.isError()) {
                    getTreeTable().deleteRow();
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
            this.getTreeTable().downMove();
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
            this.getTreeTable().upMove();
            fireProcessDataChange();
        }
    }

    public void actionPerformedLeft(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();
        DtoWbsActivity dto = (DtoWbsActivity) node.getDataBean();

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdLeftMove);
        inputInfo.setInputObj(DtoKEY.DTO, node);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            this.getTreeTable().leftMove();
//            dto.setAnticipatedStart(null);
//            dto.setAnticipatedFinish(null);
//            dto.setPlannedStart(null);
//            dto.setPlannedFinish(null);
//            dto.setActualStart(null);
//            dto.setActualFinish(null);
            fireProcessDataChange();
        }
        //this.getTreeTable().setSelectedRow(node);
    }

    public void actionPerformedRight(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdRightMove);
        inputInfo.setInputObj(DtoKEY.DTO, node);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            this.getTreeTable().rightMove();

            DateCheck.upModifyDate(node, "anticipatedStart",
                                   DateCheck.TYPE_START_DATE);
            DateCheck.upModifyDate(node, "anticipatedFinish",
                                   DateCheck.TYPE_FINISH_DATE);

            fireProcessDataChange();
        }
    }

    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }

    public void actionPerformedCompute(ActionEvent e) {
        //计算之前先清除过滤
        //modify:Robin
        this.reloadData();

        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdCompute);
        inputInfo.setInputObj(DtoKEY.ACCOUNT_ID, inAcntRid.longValue() + "");

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            DtoWbsTreeNode root = (DtoWbsTreeNode) returnInfo.getReturnObj(
                DtoKEY.
                WBSTREE);
            root.setWbsTree(true);
            this.getTreeTable().setRoot(root);
            fireProcessDataChange();
            //this.getTreeTable().updateUI();
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

    protected void reloadData() {

        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("entryFunType", entryFunType);
        inputInfo.setActionId(this.actionIdList);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            DtoWbsTreeNode root = (DtoWbsTreeNode) returnInfo.
                                  getReturnObj(
                                      DtoKEY.WBSTREE);
            root.setWbsTree(true);
            ProcessVariant.set("wbstree", root.clone());
            this.getTreeTable().setRoot(root);
            this.filterLbl.setText(VwWbsFilter.FILTER_CONDITION[0]);
            DtoWbsActivity dto = (DtoWbsActivity) root.getDataBean();
            this.inAcntRid = dto.getAcntRid();
            ProcessVariant.set("accountChanged_wbs", Boolean.FALSE);

            fireProcessDataChange();
        }
    }

    public DtoWbsTreeNode getBackRoot() {
        String curFilter = filterLbl.getText();
        if (curFilter != null &&
            !curFilter.equals(VwWbsFilter.FILTER_CONDITION[0])) {
            reloadData();
        }
        return (DtoWbsTreeNode)this.getModel().getRoot();
    }

    //页面刷新－－－－－
    protected void resetUI() {
        Object wbstree = ProcessVariant.get("wbstree");
        Boolean acccountChanged = (Boolean) ProcessVariant.get("accountChanged_wbs");
        if (wbstree != null) {
            DtoWbsTreeNode root = (DtoWbsTreeNode) wbstree;
            if (acccountChanged != null && Boolean.TRUE.equals(acccountChanged)) {
                reloadData();
            } else {
                root = (DtoWbsTreeNode) root.clone();
                root.setWbsTree(true);
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
            if (name.equals("wbstree")) {
                DtoWbsTreeNode root = (DtoWbsTreeNode) DtoUtil.deepClone(value);
                root.setWbsTree(true);
                this.getTreeTable().setRoot(root);
                this.getTreeTable().updateUI();
            } else if (name.equals("account")) {
                Long acntRid = null;
                if (value instanceof String) {
                    acntRid = new Long((String) value);
                } else if (value instanceof Long) {
                    acntRid = (Long) value;
                }
                if (acntRid != null) {
                    if (this.inAcntRid == null ||
                        acntRid.longValue() != inAcntRid.longValue()) {
                        this.inAcntRid = acntRid;
                        super.setParameter(new Parameter());
                    }
                }
            }
        }
    }

    protected void fireProcessDataChange() {
        Object root = this.getTreeTable().getTreeTableModel().getRoot();
        ProcessVariant.set("wbstree", root);
        ProcessVariant.fireDataChange("wbstree", this);

        DtoWbsActivity rootDto = (DtoWbsActivity) ((ITreeNode) root).
                                 getDataBean();
        accountLabel.setModel(rootDto);
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
        VWWorkArea workArea = new VwWbsList(null);

        workArea0.addTab("Wbs", workArea);
        TestPanel.show(workArea0);

        Parameter param = new Parameter();
        param.put("inAcntRid", new Long(1));
        workArea.setParameter(param);

        workArea.refreshWorkArea();
    }
}
