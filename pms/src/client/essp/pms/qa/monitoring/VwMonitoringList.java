package client.essp.pms.qa.monitoring;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WorkCalendarConstant;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.essp.pms.qa.DtoMonitoringTreeNode;
import c2s.essp.pms.qa.DtoQaCheckAction;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import client.essp.common.issue.VWJIssue;
import client.essp.common.view.VWAccountLabel;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.pms.wbs.process.checklist.VwCheckActionList;
import client.essp.pms.wbs.process.checklist.VwCheckPointList;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTreeTable;
import com.wits.util.IVariantListener;
import com.wits.util.Parameter;
import com.wits.util.comDate;
import client.framework.view.vwcomp.VWJTableRender;
import java.awt.Component;
import client.framework.view.vwcomp.NodeViewManager;
import javax.swing.JTable;
import client.essp.common.loginId.VWJLoginId;

public class VwMonitoringList extends VWTreeTableWorkArea implements
    IVariantListener {
    private static final String actionIdList = "FQaMonitoringListAction";
    private static final String ACTIONID_NODECOPY =
        "FBLPlanModifyCopyNodeAction";
    private static final String actionSave = "FQaMonitoringSaveAction";
    private static final String actionIdDelete = "FQaMonitoringDeleteAction";
    private static final String actionIdGetRid = "FWbsGetRidAction";
    public static final String KEY_FILTER_TYPE = "filterType";
    public static final String KEY_FILTER_ALL = "all";
    public static final String KEY_FILTER_STATUS = "status";
    public static final String KEY_PLAN_PERFORMER = "planPerformer";
    public static final String KEY_PLAN_DATE = "planStartDate";
    public static final String KEY_PLAN_FINISH_DATE = "planFinishDate";
    public static final String KEY_ACTUAL_PERFORMER = "actualPerformer";
    public static final String KEY_ACTUAL_DATE = "actualStartDate";
    public static final String KEY_ACTUAL_FINISH_DATE = "actualFinishDate";
    private String entryFunType;

    /**
     * define common data (globe)
     */
    Object[][] configs = null;
    static final String treeColumnName = "name";
    private Long inAcntRid;
    private VWJLabel filterLbl;
    String filterType;
    String filterStatus;
    String planPerformer;
    String actualPerformer;
    Date planStartDate = new Date();
    Date planFinishDate = new Date();
    Date actualStartDate = new Date();
    Date actualFinishDate = new Date();
    public VWJComboBox cmbPerformer;
    private VWJComboBox cmbStatus;
    private VWJComboBox cmbOccasion;
    private VWJIssue issue = new VWJIssue();
    VWAccountLabel accountLabel = null;
    public VwMonitoringList(VWJLabel filterLbl) {
        this.filterLbl = filterLbl;
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
            VWJDate date = new VWJDate();
            date.setCanSelect(true);
            cmbPerformer = new VWJComboBox();
            cmbStatus = new VWJComboBox();
            cmbStatus.setModel(VMComboBox.toVMComboBox(VwCheckActionList.
                chkActionStatus));
            cmbOccasion = new VWJComboBox();
            cmbOccasion.setModel(VMComboBox.toVMComboBox(VwCheckActionList.
                chkActionOccasion));
            issue.setIssueType("NCR");

            configs = new Object[][] { {"Name", "name", VMColumnConfig.EDITABLE, null},
                {"Code", "code", VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE},
                {"Remark", "remark", VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE},
                {"Method", "method", VMColumnConfig.EDITABLE, new VWJText()},
                {"Occasion", "occasion", VMColumnConfig.EDITABLE, cmbOccasion},
                {"Plan Date", "planDate", VMColumnConfig.EDITABLE, date},
                {"Actual Date", "actDate",  VMColumnConfig.EDITABLE, date},
                {"Performer", "planPerformer", VMColumnConfig.EDITABLE, cmbPerformer},
                {"Content", "content", VMColumnConfig.EDITABLE, new VWJText()},
                {"Result", "result", VMColumnConfig.EDITABLE, new VWJText()},
                {"Status", "status", VMColumnConfig.EDITABLE, cmbStatus},
                {"NCR NO", "nrcNo", VMColumnConfig.EDITABLE, issue},
            };
            model = new VMQaMonitoringTreeTableModel(null, configs);

            this.setPreferredSize(new Dimension(680, 240));

            //tree table
            model = new VMQaMonitoringTreeTableModel(null, configs,
                treeColumnName);
            model.setDtoType(DtoQaMonitoring.class);
            treeTable = new VWJTreeTable(model, new QaMonitoringManager());
            TableColumnWidthSetter.set(treeTable);

            this.add(treeTable.getScrollPane(), null);
            model.setTreeColumnEditable(false);

            //调整列的宽度
            JTableHeader header = this.getTreeTable().getTableHeader();
            TableColumnModel tcModel = header.getColumnModel();
            tcModel.getColumn(0).setPreferredWidth(250);
            tcModel.getColumn(1).setPreferredWidth(60);
            tcModel.getColumn(2).setPreferredWidth(60);
            tcModel.getColumn(3).setPreferredWidth(70);
            tcModel.getColumn(4).setPreferredWidth(70);
            tcModel.getColumn(5).setPreferredWidth(70);
            tcModel.getColumn(6).setPreferredWidth(60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addUICEvent();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUICEvent() {
//        this.getTreeTable().getColumn(6).setCellRenderer(new VWJTableRender(new VWJLoginId()));
        accountLabel = new VWAccountLabel();
        this.getButtonPanel().add(accountLabel);
    }

    public ReturnInfo actionPerformedSave() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(VwMonitoringList.actionSave);
        inputInfo.setInputObj("RootNode", this.getModel().getRoot());
        ReturnInfo returnInfo = accessData(inputInfo);
        return returnInfo;
    }

    //Qa add point
    public void actionPerformedAddPoint(ActionEvent e) {
        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode != null) {
            DtoQaMonitoring currentDataBean = (DtoQaMonitoring) currentNode.
                                              getDataBean();
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(VwCheckPointList.actionIdGetCode);
            inputInfo.setInputObj(DtoKey.ACNT_RID, currentDataBean.getAcntRid());
            ReturnInfo returnInfo = accessData(inputInfo);
            Long newRid = null;
            if (returnInfo.isError() == false) {
                newRid = (Long) returnInfo.getReturnObj(DtoQaCheckPoint.
                    DTO_PMS_CHECK_POINT_RID);
            } else {
                return;
            }
            DtoQaMonitoring dto = new DtoQaMonitoring();
            dto.setRid(newRid);
            dto.setAcntRid(currentDataBean.getAcntRid());
            if (currentDataBean.getType().equals(DtoKey.TYPE_ACTIVITY)) {
                dto.setBelongTo(DtoKey.TYPE_ACTIVITY);
                dto.setBelongRid(currentDataBean.getRid());
            } else {
                dto.setBelongTo(DtoKey.TYPE_WBS);
                dto.setBelongRid(currentDataBean.getRid());
            }
            dto.setOp(IDto.OP_INSERT);
            dto.setType(DtoQaCheckPoint.DTO_PMS_CHECK_POINT_TYPE);
            DtoMonitoringTreeNode newNode = new DtoMonitoringTreeNode(dto);
            getTreeTable().addRow(newNode);

        }
    }

    //Qa add action
    public void actionPerformedAddAction(DtoQaMonitoring dto) {
        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode != null) {
            DtoQaMonitoring currentDataBean = (DtoQaMonitoring) currentNode.
                                              getDataBean();
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(VwCheckActionList.actionIdGetCode);
            DtoQaCheckPoint cp = new DtoQaCheckPoint();
            cp.setAcntRid(currentDataBean.getAcntRid());
            inputInfo.setInputObj(DtoQaCheckPoint.DTO_PMS_CHECK_POINT, cp);
            ReturnInfo returnInfo = accessData(inputInfo);
            Long newRid = null;
            if (returnInfo.isError() == false) {
                newRid = (Long) returnInfo.getReturnObj(DtoQaCheckAction.
                    DTO_PMS_CHECK_ACTION_RID);
            } else {
                return;
            }

            dto.setRid(newRid);
            dto.setOp(IDto.OP_INSERT);
            dto.setOccasion(VwCheckActionList.chkActionOccasion[0]);
            dto.setStatus(VwCheckActionList.chkActionStatus[0]);
            //get parent wbs/activity's plan start date
            DtoQaMonitoring pDto = (DtoQaMonitoring) currentNode.getDataBean();
            if(pDto != null && pDto.getDtoWbsActivity() != null) {
               dto.setPlanDate(pDto.getDtoWbsActivity().getPlannedStart());
            }
            dto.setAcntRid(currentDataBean.getAcntRid());
            dto.setType(DtoQaCheckAction.DTO_PMS_CHECK_ACTION_TYPE);
            DtoMonitoringTreeNode newNode = new DtoMonitoringTreeNode(dto);
            getTreeTable().addRowFirst(currentNode, newNode, true);
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
            DtoQaMonitoring dataBean = (DtoQaMonitoring) deleteNode.getDataBean();
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

    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.inAcntRid = (Long) (param.get("inAcntRid"));
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
        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj("entryFunType", entryFunType);
        inputInfo.setInputObj(KEY_FILTER_TYPE, filterType);
        inputInfo.setInputObj(KEY_FILTER_STATUS, filterStatus);
        inputInfo.setInputObj(KEY_PLAN_PERFORMER, planPerformer);
        inputInfo.setInputObj(KEY_PLAN_DATE, planStartDate);
        inputInfo.setInputObj(KEY_PLAN_FINISH_DATE, planFinishDate);
        inputInfo.setInputObj(KEY_ACTUAL_PERFORMER, actualPerformer);
        inputInfo.setInputObj(KEY_ACTUAL_DATE, actualStartDate);
        inputInfo.setInputObj(KEY_ACTUAL_FINISH_DATE, actualFinishDate);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            ITreeNode root = (ITreeNode) returnInfo.
                             getReturnObj(
                                 "MONITORINGTREE");
            this.getTreeTable().setRoot(root);
            DtoQaMonitoring dto = (DtoQaMonitoring) root.getDataBean();
            accountLabel.setModel(dto.getDtoWbsActivity());
            this.inAcntRid = dto.getAcntRid();
            issue.setAcntRid(inAcntRid);
            List qaLaborResList = (List) returnInfo.getReturnObj(
                DtoQaCheckAction.PMS_QA_LABORRES_LIST);
            int laborCount = qaLaborResList.size();
            String[] qaLaborResNames = new String[laborCount];
            String[] qaLaborResIds = new String[laborCount];
            for (int i = 0; i < laborCount; i++) {
                DtoAcntLoaborRes dtoLabor = (DtoAcntLoaborRes) (qaLaborResList.
                    get(i));
                qaLaborResNames[i] = dtoLabor.getEmpName();
                qaLaborResIds[i] = dtoLabor.getLoginId();
            }
            cmbPerformer.setModel(VMComboBox.toVMComboBox(qaLaborResNames,
                qaLaborResIds));

            fireProcessDataChange();
        }
    }

    public DtoMonitoringTreeNode getBackRoot() {
        String curFilter = filterLbl.getText();
        if (curFilter != null &&
            !curFilter.equals(VwMonitoringFilter.FILTER_CONDITION[0])) {
            reloadData();
        }
        return (DtoMonitoringTreeNode)this.getModel().getRoot();
    }

    //页面刷新－－－－－
    protected void resetUI() {
        reloadData();
    }

    public void saveWorkArea() {
    }

    public void updateTreeTable() {
        getTreeTable().refreshTree();
    }

    public void dataChanged(String name, Object value) {
        if (value != null) {
            if (name.equals("wbstree")) {
                DtoMonitoringTreeNode root = (DtoMonitoringTreeNode) DtoUtil.
                                             deepClone(value);
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

    //把srcWbsTreeNode复制到destWbsTreeNode
    public void copyNode(DtoWbsTreeNode srcWbsTreeNode,
                         DtoWbsTreeNode destWbsTreeNode) {
        copyNode(srcWbsTreeNode, destWbsTreeNode, "false");
        this.reloadData();

    }

    //把源节点srcWbsTreeNode复制（isCut=="false"）或剪切（isCut=="true"）到目标节点destWbsTreeNode
    public void copyNode(DtoWbsTreeNode srcWbsTreeNode,
                         DtoWbsTreeNode destWbsTreeNode, String isCut) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("strItreeNode", srcWbsTreeNode);
        inputInfo.setInputObj("destItreeNode", destWbsTreeNode);
        inputInfo.setInputObj("isCut", isCut);
        inputInfo.setActionId(ACTIONID_NODECOPY);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            String hasCopy = (String) returnInfo.getReturnObj("hasCopy");
            if (hasCopy != null && "true".equals(hasCopy)) {
//               this.reloadData();
//               DtoWbsTreeNode wbsTreeNode = (DtoWbsTreeNode) returnInfo.getReturnObj(
//                     DtoKEY.WBSTREE);
//
//               destWbsTreeNode.addChild(wbsTreeNode);
//                getTreeTable().addRow(destWbsTreeNode, wbsTreeNode);
            }
        }

    }

    protected void fireProcessDataChange() {
        Object root = this.getTreeTable().getTreeTableModel().getRoot();
        DtoQaMonitoring rootDto = (DtoQaMonitoring) ((ITreeNode) root).
                                  getDataBean();
    }

    protected void jbInit() throws Exception {
        WorkCalendar workCalendar = WrokCalendarFactory.clientCreate();
        Calendar theCal = Calendar.getInstance();
        Calendar beginCal = workCalendar.beginDayOfWeek(theCal,
            WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);
        Calendar endCal = workCalendar.endDayOfWeek(theCal,
            WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);

//        planStartDate = beginCal.getTime();
//        planFinishDate = endCal.getTime();
//        filterType = this.KEY_PLAN_DATE;
//        filterLbl.setText("Filter By: " + filterType + " " +
//                          comDate.dateToString(planStartDate) + " ~ " +
//                          comDate.dateToString(planFinishDate));

        filterType = KEY_PLAN_DATE;
        planStartDate = beginCal.getTime();
        planFinishDate = endCal.getTime();
        filterLbl.setText(filterType + " " +
                          comDate.dateToString(planStartDate) + " ~ " +
                          comDate.dateToString(planFinishDate));

    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

//    class LoginIdRander extends VWJTableRender {
//         public LoginIdRander(Component comp, NodeViewManager nodeViewManager) {
//             super(comp, nodeViewManager);
//         }
//        public Component getTableCellRendererComponent(
//        JTable table,
//        Object value,
//        boolean isSelected,
//        boolean hasFocus,
//        int row,
//        int column) {
//            VWJLoginId comp = new VWJLoginId();
//            comp.setUICValue(value);
//            return comp;
//        }
//
//
//    }
}
