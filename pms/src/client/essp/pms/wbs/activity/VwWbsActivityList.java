package client.essp.pms.wbs.activity;

import java.util.List;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoWbs;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.VWJDate;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Date;
import c2s.essp.pms.wbs.DtoKEY;
import javax.swing.JButton;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import c2s.essp.common.user.DtoUser;
import client.framework.view.vwcomp.VWUtil;
import c2s.dto.IDto;
import client.essp.pms.wbs.pbsAndFiles.VwPbsAndFile;
import client.essp.pms.activity.relation.VwRelationSuccessors;
import client.essp.pms.activity.VwActivityGeneral;
import client.essp.pms.activity.code.VwActivityCodeList;
import client.essp.pms.activity.relation.VwRelationPredecessors;
import client.essp.pms.activity.wp.VwWpListActivity;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.essp.pms.activity.VwActivityStatus;
import client.essp.pms.activity.worker.VwActivityWorkerList;
import javax.swing.JFrame;
import client.essp.cbs.cost.activity.VwActivityCost;
import c2s.dto.ITreeNode;
import c2s.dto.DtoTreeNode;
import client.framework.view.common.comMSG;
import client.framework.common.Constant;
import client.framework.view.event.RowSelectedListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import client.framework.view.vwcomp.VWJTable;
import client.essp.pms.modifyplan.VwBLPlanList;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import c2s.dto.DtoUtil;
import client.essp.pms.modifyplan.VwBaseLinePlanModify;
import client.essp.pms.modifyplan.VwBLMonitoring;
import client.essp.pms.modifyplan.base.VWTreeTableWorkAreaForBL;
import c2s.essp.pms.wbs.DateCheck;
import com.wits.util.IVariantListener;
import com.wits.util.ProcessVariant;
import client.essp.common.view.VWTreeTableWorkArea;
import c2s.essp.common.calendar.WorkCalendar;
import client.essp.pwm.wp.CalculateWPDefaultDate;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwWbsActivityList extends VWTableWorkArea {
    public static final String ACTIONID_WBSACTIVITY_LIST =
        "FWbsWbsActivityListAction";
    private static final String ACTIONID_GET_ACTIVITY_CODE =
        "FWbsActivityGetCodeAction";
    private static final String ACTIONID_GETACNT_LABOR =
        "FBLPlanModifyGetAcntLabor";
    private static final String ACTION_ID_DELETE = "FWbsActivityDeleteAction";
    private static final String ACTION_ID_SAVE_ALL =
        "FWbsActivityListAllSaveAction";

    private DtoWbsActivity wbs;
    private VWJComboBox manager = new VWJComboBox();
    private VWJComboBox timeLimitType = new VWJComboBox();
    private VWTreeTableWorkArea upTreeTable;
    private VwBaseLinePlanModify projectPlan;
    private VwBLMonitoring projectMonitor;
    private JButton addBtn;
    private JButton editBtn;
    private JButton saveBtn;
    private JButton delBtn;
    private ITreeNode treeNode;
    Object wbsListPanel = null;

    //判断其调用此卡片的类型
    private String callerType = "WBS";

    public VwWbsActivityList() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        //设置标题栏位
        VWJDate date = new VWJDate();
        date.setCanSelect(true);
        VWJReal real = new VWJReal();
        real.setMaxInputDecimalDigit(1);
        timeLimitType.setModel(VMComboBox.toVMComboBox(DtoWbsActivity.
            ACTIVITY_TIME_LIMIT_TYPE));

        Object[][] configs = new Object[][] { {"Activity ID", "code",
                             VMColumnConfig.EDITABLE, new VWJText()},
                             {"Activity Name", "name",
                             VMColumnConfig.EDITABLE, new VWJText()},
                             {"Duration", "timeLimit",
                             VMColumnConfig.EDITABLE, real}, {"Duration Type",
                             "timeLimitType", VMColumnConfig.EDITABLE,
                             timeLimitType}, {"Planned Start", "plannedStart",
                             VMColumnConfig.EDITABLE, date}, {"Planned Finish",
                             "plannedFinish", VMColumnConfig.EDITABLE, date},
                             {"Weight", "weight", VMColumnConfig.EDITABLE,
                             new VWJReal()}, {"Manager", "manager",
                             VMColumnConfig.EDITABLE, manager},
                             {"%Calculate Method", "completeMethod",
                             VMColumnConfig.EDITABLE, new VWJText(),
                             Boolean.TRUE}, {"%Complete", "completeRate",
                             VMColumnConfig.EDITABLE, new VWJReal(),
                             Boolean.TRUE}, {"Actual Start", "plannedStart",
                             VMColumnConfig.UNEDITABLE, date, Boolean.TRUE},
                             {"Actual Finish", "plannedFinish",
                             VMColumnConfig.UNEDITABLE, date,
                             Boolean.TRUE},
        };
        try {
            model = new VmWbsActivityListModel(configs);
            model.setDtoType(DtoWbsActivity.class);
            table = new VWJTable(model);
            this.add(table.getScrollPane(), null);
//            super.jbInit(configs, DtoWbsActivity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置初始列宽
        JTableHeader header = this.getTable().getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(50);
        tcModel.getColumn(1).setPreferredWidth(80);
        tcModel.getColumn(2).setPreferredWidth(30);
        tcModel.getColumn(3).setPreferredWidth(70);
        tcModel.getColumn(4).setPreferredWidth(70);
        tcModel.getColumn(5).setPreferredWidth(60);
        tcModel.getColumn(6).setPreferredWidth(40);

        //add
        addBtn = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionperformedAdd();
                saveBtn.setEnabled(true);
            }
        });
        addBtn.setToolTipText("add activity");
        //edit
        editBtn = this.getButtonPanel().addEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionperformedEdit();
            }
        });
        editBtn.setToolTipText("Edit/Show detail data");
        //delete
        delBtn = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionperformedDelete();
            }
        });
        delBtn.setToolTipText("delete data");
        //save
        saveBtn = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionperformedSave();
            }
        });
        saveBtn.setToolTipText("save data");
        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(this.getTable(),
                                         this);
        JButton button = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(button);

        this.getTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                DtoWbsActivity dto = (DtoWbsActivity) getTable().
                                     getSelectedData();
                if (dto == null) {
                    return;
                }
                if (wbs.isReadonly()) {
                    addBtn.setEnabled(false);
//                    editBtn.setEnabled(false);
                    saveBtn.setEnabled(false);
                    delBtn.setEnabled(false);
                } else {
                    addBtn.setEnabled(true);
                    saveBtn.setEnabled(true);
                    delBtn.setEnabled(true);
                }
                if (dto.getOp().equals(IDto.OP_INSERT)) {
                    editBtn.setEnabled(false);
                } else {
                    editBtn.setEnabled(true);
                }

            }
        });

    }

    private ITreeNode parentTreeNode;
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.wbs = ((DtoWbsActivity) param.get("wbs"));
        treeNode = (ITreeNode) param.get(DtoKEY.WBSTREE);
        this.upTreeTable = (VWTreeTableWorkArea) param.get("upTreeTable");
        if (param.get("callerType") != null) {
            parentTreeNode = (ITreeNode) param.get(DtoKEY.WBSTREE);
            this.callerType = (String) param.get("callerType");
            if (callerType.equals("PP")) {
                this.projectPlan = (VwBaseLinePlanModify) param.get(
                    "ProjectPlan");
            } else {
                this.projectMonitor = (VwBLMonitoring) param.get("ProjectPlan");
            }
        }
    }

    public void resetUI() {

        if (wbs == null) {
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_WBSACTIVITY_LIST);
        inputInfo.setInputObj("acntRid", wbs.getAcntRid());
        inputInfo.setInputObj("wbsRid", wbs.getWbsRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            List wbsActivityList = (List) returnInfo.getReturnObj(
                "wbsActivityList");
            this.getTable().setRows(wbsActivityList);
        }
        ((VmWbsActivityListModel)this.getModel()).setWbsNode(treeNode);
        //更新按钮的状态
        setButtonEnable(wbs.isReadonly());
        //更新编辑器
        VMComboBox managerModel = new VMComboBox();
        List labor = null;
        InputInfo inputInfoForLabor = new InputInfo();
        inputInfoForLabor.setActionId(this.ACTIONID_GETACNT_LABOR);
        inputInfoForLabor.setInputObj("AcntRid", this.wbs.getAcntRid().toString());
        ReturnInfo returnInfoForLabor = accessData(inputInfoForLabor);

        if (!returnInfoForLabor.isError()) {
            labor = (List) returnInfoForLabor.getReturnObj("AcntLaborList");
        }

        if (labor != null) {
            String[] loginIds = new String[labor.size()];
            String[] names = new String[labor.size()];
            for (int i = 0; i < labor.size(); i++) {
                loginIds[i] = ((DtoUser) labor.get(i)).getUserLoginId();
                names[i] = ((DtoUser) labor.get(i)).getUserName();
            }
            managerModel = VMComboBox.toVMComboBox(names, loginIds);
        }
        VWUtil.initComboBox(manager, managerModel, null, null);

        //设置表是否可以被编辑
        this.getModel().setCellEditable(!wbs.isReadonly());
    }

    private void actionperformedAdd() {
        DtoWbsActivity dto = new DtoWbsActivity();
        dto.setActivity(true);
        dto.setAcntRid(this.wbs.getAcntRid());
        dto.setWbsRid(this.wbs.getWbsRid());

        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj(DtoKEY.DTO, dto);
        inputInfo.setActionId(this.ACTIONID_GET_ACTIVITY_CODE);

        ReturnInfo returnInfo = accessData(inputInfo);
        dto = (DtoWbsActivity) returnInfo.getReturnObj(DtoKEY.DTO);
        dto.setAutoCode(dto.getCode());
        dto.setName(dto.getCode());
        dto.setWeight(new Double(1.00));
        dto.setOp(DtoWbsActivity.OP_INSERT);

        Date wbsStart = this.wbs.getPlannedStart();
        Date wbsFinish = this.wbs.getPlannedFinish();
        ITreeNode nodeForStart = treeNode;
        ITreeNode nodeForFinish = treeNode;
        while (wbsStart == null && nodeForStart.getParent() != null) {
            nodeForStart = nodeForStart.getParent();
            wbsStart = ((DtoWbsActivity) nodeForStart.getDataBean()).
                       getPlannedStart();
        } while (wbsFinish == null && nodeForFinish.getParent() != null) {
            nodeForFinish = nodeForFinish.getParent();
            wbsFinish = ((DtoWbsActivity) nodeForFinish.getDataBean()).
                        getPlannedFinish();
        }

        Date[] tmp = CalculateWPDefaultDate.calculateDefaultPlanDate(wbsStart,
            wbsFinish);
        dto.setPlannedStart(tmp[0]);
        dto.setPlannedFinish(tmp[1]);
        dto.setTimeLimit(WorkCalendar.calculateTimeLimit(tmp[0], tmp[1]));
        dto.setTimeLimitType(DtoWbsActivity.ACTIVITY_TIME_LIMIT_TYPE[0]);
        dto.setOp(IDto.OP_INSERT);
        this.getModel().addRow(model.getRowCount() - 1, dto);
    }

    public void actionperformedEdit() {
        VwActivityGeneral activityGeneral;
        VwActivityStatus activityStatus;
        VwPbsAndFile pbs;
        VwActivityWorkerList activityWorkers;
        VwRelationPredecessors activityPredecessors;
        VwRelationSuccessors activitySuccessors;
        VwWpListActivity activityWp;
        VwActivityCodeList activityCode;
        VwActivityCost activityCost;

        VWGeneralWorkArea popWorkArea = new VWGeneralWorkArea();
        activityGeneral = new VwActivityGeneral();
        popWorkArea.addTab("General", activityGeneral);
        activityGeneral.setParentWorkArea(popWorkArea);
        activityStatus = new VwActivityStatus();
        popWorkArea.addTab("Status", activityStatus);
        activityWorkers = new VwActivityWorkerList();
        popWorkArea.addTab("Workers", activityWorkers);
        activityPredecessors = new VwRelationPredecessors();
        popWorkArea.addTab("Predecessors", activityPredecessors);
        activitySuccessors = new VwRelationSuccessors();
        popWorkArea.addTab("Successors", activitySuccessors);
        pbs = new VwPbsAndFile();
        popWorkArea.addTab("Pbs", pbs);
        activityWp = new VwWpListActivity();
        popWorkArea.addTab("Wp", activityWp);
        activityCode = new VwActivityCodeList();
        popWorkArea.addTab("Code", activityCode);
        activityCost = new VwActivityCost();
        popWorkArea.addTab("Cost", activityCost);

        Parameter param = new Parameter();

        ITreeNode node = new DtoTreeNode(this.getTable().getSelectedData());

        param.put(DtoKEY.WBSTREE, node);
        param.put("Wbs", this.wbs);
        activityGeneral.setParameter(param);
        activityStatus.setParameter(param);
        activityWorkers.setParameter(param);
        activityPredecessors.setParameter(param);
        activitySuccessors.setParameter(param);
        activityWp.setParameter(param);
        activityCode.setParameter(param);
        pbs.setParameter(param);
        activityCost.setParameter(param);

        popWorkArea.setSize(600, 400);
        popWorkArea.setVisible(true);
        VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                                                "Activity Detail", popWorkArea);
        pop.setSize(700, 330);
        pop.show();

    }

    public void actionperformedSave() {
        List dtoList = this.getModel().getRows();
        if (dtoList.size() < 1) {
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj(DtoKEY.DTO, dtoList);
        inputInfo.setActionId(this.ACTION_ID_SAVE_ALL);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            for (int i = 0; i < dtoList.size(); i++) {
                DtoWbsActivity dtoActivity = (DtoWbsActivity) dtoList.get(i);
                if ("PP".equals(callerType) || "Monitoring".equals(callerType)) {
                    if (dtoActivity.getOp().equals(IDto.OP_INSERT)) {
                        ITreeNode node = this.upTreeTable.getTreeTable().addRow(
                            parentTreeNode, new DtoWbsTreeNode(dtoActivity), false);
                        boolean reqireUpdate = updateUpTreeTable(parentTreeNode,
                            dtoActivity);
                        if (upTreeTable != null && reqireUpdate) {
                            upTreeTable.getTreeTable().repaint();
                        }
                        ((IDto) node.getDataBean()).setOp(IDto.OP_NOCHANGE);
                    } else if (dtoActivity.getOp().equals(IDto.OP_MODIFY)) {
                        ITreeNode child = findNodeByRid(parentTreeNode,
                            dtoActivity.getActivityRid());
                        DtoWbsActivity childWbs = (DtoWbsActivity) child.
                                                  getDataBean();
                        try {
                            DtoUtil.copyProperties(childWbs, dtoActivity);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        boolean reqireUpdate = updateUpTreeTable(parentTreeNode,
                            dtoActivity);
                        if (upTreeTable != null && reqireUpdate) {
                            upTreeTable.getTreeTable().repaint();
                        }
                        dtoActivity.setOp(IDto.OP_NOCHANGE);
                    }
                    if (this.callerType.equals("PP")) {
                        projectPlan.refreshGraphic();
                    } else {
                        projectMonitor.refreshGraphic();
                    }
                } else {
                    boolean reqireUpdate = updateUpTreeTable(treeNode,
                        dtoActivity);
                    if (upTreeTable != null && reqireUpdate) {
                        upTreeTable.getTreeTable().repaint();
                    }
                    dtoActivity.setOp(IDto.OP_NOCHANGE);

                }

            } //For end

        }
    }

    public static boolean updateUpTreeTable(ITreeNode treeNode,
                                      DtoWbsActivity dtoActivity) {
        DtoWbsActivity dtoWbs = (DtoWbsActivity) treeNode.
                                getDataBean();
        boolean reqireUpdate = false;
        if (dtoWbs.getPlannedStart() == null &&
            dtoActivity.getPlannedStart() != null) {
            dtoWbs.setPlannedStart(dtoActivity.getPlannedStart());
            DateCheck.upModifyDate(treeNode, "plannedStart",
                                   DateCheck.TYPE_START_DATE);
            reqireUpdate = true;
        } else if (dtoActivity.getPlannedStart() != null &&
                   dtoActivity.getPlannedStart().before(dtoWbs.
            getPlannedStart())) {
            dtoWbs.setPlannedStart(dtoActivity.getPlannedStart());
            DateCheck.upModifyDate(treeNode, "plannedStart",
                                   DateCheck.TYPE_START_DATE);
            reqireUpdate = true;
        }
        if (dtoWbs.getPlannedFinish() == null &&
            dtoActivity.getPlannedFinish() != null) {
            dtoWbs.setPlannedFinish(dtoActivity.getPlannedFinish());
            DateCheck.upModifyDate(treeNode, "plannedFinish",
                                   DateCheck.TYPE_FINISH_DATE);
            reqireUpdate = true;
        } else if (
            dtoActivity.getPlannedFinish() != null &&
            dtoActivity.getPlannedFinish().after(dtoWbs.
                                                 getPlannedFinish())) {
            dtoWbs.setPlannedFinish(dtoActivity.getPlannedFinish());
            DateCheck.upModifyDate(treeNode, "plannedFinish",
                                   DateCheck.TYPE_FINISH_DATE);
            reqireUpdate = true;
        }
        return reqireUpdate;
    }

    public ITreeNode findNodeByRid(ITreeNode parent, Long rid) {
        List childs = parent.children();
        for (int i = 0; i < childs.size(); i++) {
            ITreeNode child = (ITreeNode) childs.get(i);
            DtoWbsActivity dto = (DtoWbsActivity) child.getDataBean();
            if (dto.isActivity() && dto.getActivityRid() != null && dto.getActivityRid().equals(rid)) {
                return child;
            }
        }
        return null;
    }

    public void actionperformedDelete() {
        int tmp = comMSG.dispConfirmDialog(
            "Do you delete this Activity?");
        if (tmp != Constant.OK) {
            return;
        }
        DtoWbsActivity dto = (DtoWbsActivity)this.getSelectedData();
        if (!dto.isInsert()) {
            InputInfo inputInfo = new InputInfo();
            dto.setOp(IDto.OP_DELETE);
            inputInfo.setInputObj(DtoKEY.DTO, dto);
            inputInfo.setActionId(this.ACTION_ID_DELETE);

            ReturnInfo returnInfo = accessData(inputInfo);
            if (!returnInfo.isError()) {
                this.getTable().deleteRow();
                if ("PP".equals(callerType) || "Monitoring".equals(callerType)) { //如果上面的卡片是PP的话，就删除上面的树
                    List childs = parentTreeNode.children();
                    ITreeNode tempNode = null;
                    for (int i = 0; i < childs.size(); i++) {
                        DtoWbsActivity tempDto = (DtoWbsActivity) ((ITreeNode)
                            childs.get(i)).getDataBean();
                        if (tempDto.getActivityRid().equals(dto.getActivityRid())) {
                            tempNode = (ITreeNode) childs.get(i);
                            break;
                        }
                    }
                    if (tempNode != null) {
                        upTreeTable.getTreeTable().deleteRow(tempNode, false);
                    }
                    if ("PP".equals(callerType)) {
                        projectPlan.refreshGraphic();
                    } else {
                        projectMonitor.refreshGraphic();
                    }
                }
            }
        } else {
            this.getTable().deleteRow();
        }
    }

    public void setButtonEnable(boolean readOnly) {
        if (readOnly) {
            addBtn.setEnabled(false);
            saveBtn.setEnabled(false);
            delBtn.setEnabled(false);
        } else {
            addBtn.setEnabled(true);
            saveBtn.setEnabled(true);
            delBtn.setEnabled(true);
        }
        if (this.getSelectedData() == null) {
            this.editBtn.setEnabled(false);
        } else {
            this.editBtn.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        VWWorkArea w1 = new VWWorkArea();

        VwWbsActivityList activity = new VwWbsActivityList();

        Parameter param = new Parameter();
        DtoWbs wbs = new DtoWbs();
        wbs.setAcntRid(new Long(1));
        wbs.setWbsRid(new Long(34));
        param.put("wbs", wbs);

        activity.setParameter(param);
        w1.addTab("activity", activity);
        TestPanel.show(w1);
        activity.resetUI();
    }
}
