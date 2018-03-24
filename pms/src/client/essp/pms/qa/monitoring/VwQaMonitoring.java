package client.essp.pms.qa.monitoring;

import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.pms.activity.code.VwActivityCodeList;
import client.framework.view.event.RowSelectedLostListener;
import c2s.essp.pms.wbs.DtoKEY;
import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.IVWAppletParameter;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.essp.pms.activity.VwActivityStatus;
import client.essp.pms.activity.worker.VwActivityWorkerList;
import client.essp.pms.activity.wp.VwWpListActivity;
import c2s.essp.pms.wbs.DtoWbsActivity;
import com.wits.util.IVariantListener;
import java.awt.event.ActionEvent;
import client.essp.pms.activity.VwActivityGeneral;
import com.wits.util.ProcessVariant;
import com.wits.util.Parameter;
import client.framework.view.vwcomp.VWJLabel;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import client.essp.common.view.VWTDWorkArea;
import java.awt.Dimension;
import client.essp.pms.activity.cost.VwActivityCost;
import client.essp.pms.wbs.pbsAndFiles.VwPbsAndFile;
import c2s.dto.ITreeNode;
import client.framework.view.event.RowSelectedListener;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import net.sourceforge.ganttproject.GanttProject;
import c2s.dto.IDto;
import java.util.List;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import com.wits.util.TestPanel;
import client.essp.pms.wbs.VwWbsGeneral;
import javax.swing.JFrame;
import client.essp.pms.wbs.activity.VwWbsActivityList;
import client.essp.pms.wbs.code.VwWbsCodeList;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.pms.activity.relation.VwRelationship;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import c2s.dto.InputInfo;
import client.essp.common.excelUtil.VWJButtonExcel;
import c2s.essp.common.excelUtil.IExcelParameter;
import client.framework.common.Global;
import java.awt.image.RenderedImage;
import net.sourceforge.ganttproject.GanttExportSettings;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import net.sourceforge.ganttproject.chart.Chart;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.qa.DtoQaCheckAction;
import c2s.essp.pms.qa.DtoMonitoringTreeNode;
import com.wits.util.comDate;
import client.framework.view.common.comMSG;
import com.wits.util.StringUtil;
import java.util.ArrayList;
import client.essp.common.view.VWWorkArea;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeModelEvent;
import client.essp.pms.wbs.process.VwWbsProcess;
import client.essp.pms.activity.process.VwActivityProcess;
import c2s.essp.pms.account.DtoAcntKEY;
import client.framework.common.Constant;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import client.framework.model.VMTreeTableModel;
import client.essp.pms.wbs.checkpoint.VwCheckPointList;

public class VwQaMonitoring extends VWTDWorkArea implements
    IVWAppletParameter,
    IVariantListener {
    /**
     * define control variable
     */
    private boolean refreshFlag = false;

    /**
     * define common data (globe)
     */
    Long inAcntRid;

/////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
//    public static String ACTIONID_EXPORTPLAN_PRE = "FBLPlanModifyExportPlanPre";
//    public static String ACTIONID_EXPORTPLAN = "FBLPlanModifyExportPlan";
    public static final String actionIdExcel = "FQaMonitoringExcelAction";

    VwMonitoringList mList;
    JButton detailBtn = null;
//    JButton addPointBtn = null;
    JButton addActionBtn = null;
    JButton saveBtn = null;
    JButton deleteBtn = null;
    JButton refreshBtn = null;
    JButton expandBtn = null;
    JButton filterBtn = null;
    //  JButton ajustTimingBtn = null;
    JButton exportBtn = null;
    private String entryFunType;

    int imageHeight;
    int imageWidth;
    VWTDWorkArea tdArea = new VWTDWorkArea(400);
    VWJLabel filterLb0;
    VWJLabel filterLbl;
    public VwQaMonitoring() {
        super(1000);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        addUICEvent();
    }

    JSplitPane planArea = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JScrollPane planScroll = new JScrollPane();
    GanttProject project;
    //Component initialization
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700,470));
        filterLbl = new VWJLabel();
        mList = new VwMonitoringList(filterLbl);
        this.getTopArea().addTab("QA Monitoring", mList);
    }

    public byte[] getGanttImage() throws Exception {
        Chart chart = project.getActiveChart();
        if (chart == null) {
            chart = project.getGanttChart();
        }
        RenderedImage renderedImage = chart.getRenderedImage(new
            GanttExportSettings());
        imageHeight = renderedImage.getHeight();
        imageWidth = renderedImage.getWidth();

        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        ImageIO.write(renderedImage, "png", byteArrayOut);

        return byteArrayOut.toByteArray();
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.mList.getTreeTable().addRowSelectedLostListener(new
            RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedNode) {
                return processRowSelectedLostHandle(oldSelectedRow,
                    oldSelectedNode);
            }
        });
        this.mList.getTreeTable().addRowSelectedListener(new
            RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedQaMonitoringList();
            }
        });

        this.mList.getTreeTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = mList.getTreeTable().getSelectedColumn();
                    VMTreeTableModel model = mList.getModel();
                    String clmName = model.getColumnName(index);
                    if("Name".endsWith(clmName)) {
                        popDetailWindow();
                    }
                }
            }
        });
        //filter Label
//        filterLbl.setPreferredSize(new Dimension(160, 20));
//        filterTitle.setPreferredSize(new Dimension(80, 20));
//        filterTitle.setHorizontalAlignment(SwingConstants.RIGHT);
//        filterTitle.setText("Filter By:");
//        filterLbl.setSize(180, 20);
//        filterLbl.setText("Filter By:All CheckAction");
        filterLb0 = new VWJLabel();
        filterLb0.setText("Filter By: ");
        mList.getButtonPanel().add(filterLb0);
        mList.getButtonPanel().add(filterLbl);

        //detail button
        detailBtn = mList.getButtonPanel().addButton("detail.png");
        detailBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //执行弹出窗口，弹出详细的WBS或Activity的卡片框
                popDetailWindow();
            }
        });
        detailBtn.setToolTipText("show/modify detail");

        //Add Action
        addActionBtn = mList.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAddAction(e);
            }
        });
        addActionBtn.setToolTipText("add Action");

        //Delete
        deleteBtn = mList.getButtonPanel().addDelButton(new
            ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });
        deleteBtn.setToolTipText("delete data");
        //Save
        saveBtn = mList.getButtonPanel().addSaveButton(new
            ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //执行保存动作
                if (validateData() == true) {
                actionPerformedSave();

           }
            }
        });
        saveBtn.setEnabled(false);
        saveBtn.setToolTipText("save data");

        //expand
        expandBtn = mList.getButtonPanel().addButton("expand.png");
        expandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand(e);
            }
        });
        expandBtn.setToolTipText("expand");
        //filter
        filterBtn = mList.getButtonPanel().addButton("filter.gif");
        filterBtn.setToolTipText("Filter");
        filterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //弹出过滤条件选择窗口
                VwMonitoringFilter filter = new VwMonitoringFilter(mList.
                    getTreeTable(), mList, filterLbl);

                VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                    "Filter",
                    filter, filter);
                pop.setSize(300, 200);
                int confirm = pop.showConfirm();
                if(confirm == Constant.OK) {
                    mList.reloadData();
                    mList.fireDataChanged();
                }
            }
        });

        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(mList.getTreeTable(),
                                         mList);
        JButton button = chooseDisplay.getDisplayButton();
        mList.getButtonPanel().addButton(button);
        mList.getTreeTable().getTreeTableModel().addTreeModelListener(new TreeModelListener(){
            public void treeNodesChanged(TreeModelEvent e) {
                saveBtn.setEnabled(true);
            }

            public void treeNodesInserted(TreeModelEvent e) {
                saveBtn.setEnabled(true);
            }

            public void treeNodesRemoved(TreeModelEvent e) {
            }

            public void treeStructureChanged(TreeModelEvent e) {
            }
        });

        //Refresh
        refreshBtn = mList.getButtonPanel().addLoadButton(new
            ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        refreshBtn.setToolTipText("refresh data");
        //export
        exportBtn = new VWJButtonExcel(new IExcelParameter(){
             public String getUrlAddress(){
                 StringBuffer sb = new StringBuffer(Global.appRoot);
                 sb.append(IExcelParameter.DEFAULT_EXCEL_JSP_ADDRESS);
                 sb.append("?");
                 sb.append(IExcelParameter.ACTION_ID);
                 sb.append("=");
                 sb.append(actionIdExcel);
                 sb.append("&");
                 sb.append("acntRid=");
                 sb.append(inAcntRid.longValue());
                 sb.append("&");
                 sb.append(VwMonitoringList.KEY_FILTER_TYPE+"=");
                 sb.append(mList.filterType);
                 sb.append("&");
                 sb.append(VwMonitoringList.KEY_FILTER_STATUS+"=");
                 sb.append(mList.filterStatus);
                 sb.append("&");
                 sb.append(VwMonitoringList.KEY_PLAN_PERFORMER+"=");
                 sb.append(mList.planPerformer);
                 sb.append("&");
                 sb.append(VwMonitoringList.KEY_PLAN_DATE+"=");
                 sb.append(comDate.dateToString(mList.planStartDate));
                 sb.append("&");
                 sb.append(VwMonitoringList.KEY_PLAN_FINISH_DATE+"=");
                 sb.append(comDate.dateToString(mList.planFinishDate));
                 sb.append("&");
                 sb.append(VwMonitoringList.KEY_ACTUAL_PERFORMER+"=");
                 sb.append(mList.actualPerformer);
                 sb.append("&");
                 sb.append(VwMonitoringList.KEY_ACTUAL_DATE+"=");
                 sb.append(comDate.dateToString(mList.actualStartDate));
                 sb.append("&");
                 sb.append(VwMonitoringList.KEY_ACTUAL_FINISH_DATE+"=");
                 sb.append(comDate.dateToString(mList.actualFinishDate));
                 sb.append("&");
                 sb.append("filter=");
                 sb.append(filterLbl.getText());


                 return sb.toString();
             }
         });
         exportBtn.setToolTipText("Export");
        mList.getButtonPanel().addButton(exportBtn);

        ProcessVariant.addDataListener("account", this);
        ProcessVariant.addDataListener("wbstree", this);
    }

    public void popDetailWindow() {
      DtoMonitoringTreeNode currentNode = (DtoMonitoringTreeNode) mList.getTreeTable().getSelectedNode();
      DtoWbsTreeNode node = new DtoWbsTreeNode(null);
        if (currentNode != null) {
   //         DtoWbsActivity nodeData = (DtoWbsActivity) node.getDataBean();
            DtoQaMonitoring currentNodeData = (DtoQaMonitoring)currentNode.getDataBean();
            node.setDataBean(currentNodeData.getDtoWbsActivity());
            if (currentNodeData.getType().equals(DtoKey.TYPE_WBS)) {
                //是WBS,弹出WBS的详细情况
                VwWbsGeneral wbsGeneral;
//                VwMilestone milestone;
                 VwCheckPointList checkPoint;
                 VwPbsAndFile pbs;
                 VwWbsActivityList activityList;
                 VwWbsCodeList wbsCode;
                 VwWbsProcess wbsProcess;

                 VWGeneralWorkArea popWorkArea = new VWGeneralWorkArea();
                 wbsGeneral = new VwWbsGeneral();
                 popWorkArea.addTab("General", wbsGeneral);
                 wbsGeneral.setParentWorkArea(popWorkArea);
                 checkPoint = new VwCheckPointList();
                 popWorkArea.addTab("CheckPoint", checkPoint);
                 pbs = new VwPbsAndFile();
                 popWorkArea.addTab("Pbs", pbs);
                 activityList = new VwWbsActivityList();
                 popWorkArea.addTab("Activity", activityList);
                 wbsCode = new VwWbsCodeList();
                 popWorkArea.addTab("Code", wbsCode);
                 wbsProcess = new VwWbsProcess();
                popWorkArea.addTab("Process", wbsProcess);

                 Parameter param = new Parameter();
                 param.put(DtoKEY.WBSTREE, node);
                 param.put("wbs", node.getDataBean());
                 param.put("listPanel", this.mList);
                 wbsGeneral.setParameter(param);
                 checkPoint.setParameter(param);
                 activityList.setParameter(param);
                 pbs.setParameter(param);
                 wbsCode.setParameter(param);
                 wbsProcess.setParameter(param);

                 popWorkArea.setSize(600, 400);
                 popWorkArea.setVisible(true);
                 VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                     "WBS Detail", popWorkArea);
                 pop.setSize(700, 330);
                 pop.show();
            } else if (currentNodeData.getType().equals(DtoKey.TYPE_ACTIVITY)) {
                //是Activity,弹出Activity的详细情况
                VwActivityGeneral activityGeneral;
                VwActivityStatus activityStatus;
                VwPbsAndFile pbs;
                VwActivityWorkerList activityWorkers;
                VwRelationship relation;
                VwWpListActivity activityWp;
                VwActivityCodeList activityCode;
                VwActivityCost activityCost;
                VwActivityProcess activityProcess;

                VWGeneralWorkArea popWorkArea = new VWGeneralWorkArea();
                activityGeneral = new VwActivityGeneral();
                popWorkArea.addTab("General", activityGeneral);
                activityGeneral.setParentWorkArea(popWorkArea);
                activityStatus = new VwActivityStatus();
                popWorkArea.addTab("Status", activityStatus);
                activityWorkers = new VwActivityWorkerList();
                popWorkArea.addTab("Workers", activityWorkers);
                relation = new VwRelationship();
                popWorkArea.addTab("Relationship", relation);
                pbs = new VwPbsAndFile();
                popWorkArea.addTab("Pbs", pbs);
                activityWp = new VwWpListActivity();
                popWorkArea.addTab("Wp", activityWp);
                activityCode = new VwActivityCodeList();
                popWorkArea.addTab("Code", activityCode);
                activityCost = new VwActivityCost();
                popWorkArea.addTab("Cost", activityCost);
                activityProcess = new VwActivityProcess();
                popWorkArea.addTab("Process", activityProcess);

                Parameter param = new Parameter();
                param.put(DtoKEY.WBSTREE, node);
                param.put("listPanel", this.mList);
                activityGeneral.setParameter(param);
                activityStatus.setParameter(param);
                activityWorkers.setParameter(param);
                relation.setParameter(param);
                activityWp.setParameter(param);
                activityCode.setParameter(param);
                pbs.setParameter(param);
                activityCost.setParameter(param);
                activityProcess.setParameter(param);

                popWorkArea.setSize(600, 400);
                popWorkArea.setVisible(true);
                VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                    "Activity Detail", popWorkArea);
                pop.setSize(700, 330);
                pop.show();
            } else if(currentNodeData.getType().equals(DtoQaCheckAction.
                                             DTO_PMS_CHECK_ACTION_TYPE)) {
                DtoQaMonitoring pData = (DtoQaMonitoring)
                                         currentNode.getParent().getDataBean();
                DtoWbsActivity dtoWbsActivity = pData.getDtoWbsActivity();
                VwCheckActionPop checkActionPop = new VwCheckActionPop();
                Parameter param = new Parameter();
                param.put(DtoKey.DTO, currentNodeData);
                param.put("dtoWbsActivity", dtoWbsActivity);
                param.put("performerModel",mList.cmbPerformer.getModel());
                checkActionPop.setParameter(param);
                checkActionPop.refreshWorkArea();

                VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                    "Edit Check Action", checkActionPop, checkActionPop);
                pop.setSize(620, 600);
                pop.show();
                if(currentNodeData.isChanged()) {
                    saveBtn.setEnabled(true);
                }
            }
        }

    }

    public void setNodeNomrmal(ITreeNode root) {
        DtoQaMonitoring dataBean = (DtoQaMonitoring) root.getDataBean();
        dataBean.setOp(IDto.OP_NOCHANGE);
        root.setDataBean(dataBean);
        if (!root.isLeaf()) {
            List list = root.children();
            for (int i = 0; i < list.size(); i++) {
                setNodeNomrmal((ITreeNode) list.get(i));
            }
        }
    }


    public void actionPerformedSave() {
        ReturnInfo ri = mList.actionPerformedSave();
        if (!ri.isError()) {
            saveBtn.setEnabled(false);
            detailBtn.setEnabled(true);
//            mList.resetUI();
            //保存以后设置所有的节点的属性均为正常
            setNodeNomrmal((ITreeNode) mList.getModel().getRoot());
            mList.fireProcessDataChange();
        } else {
            //报错了
        }
    }


    private void setButtonStatus(ITreeNode node) {
        detailBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        if (node == null) {
            return;
        }
        DtoQaMonitoring dataBean = (DtoQaMonitoring) node.getDataBean();
        if (dataBean == null) {
            return;
        }
        detailBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
        if (dataBean.getType().equals(DtoKey.TYPE_WBS) ||
            dataBean.getType().equals(DtoKey.TYPE_ACTIVITY)) {
            DtoWbsActivity dto=new DtoWbsActivity();
            dto=dataBean.getDtoWbsActivity();
            if(dto.isReadonly()){
               addActionBtn.setEnabled(false);
           }else{
              addActionBtn.setEnabled(true);
           }
            deleteBtn.setEnabled(false);
        } else if (dataBean.getType().equals(DtoQaCheckAction.
                                             DTO_PMS_CHECK_ACTION_TYPE)) {
            ITreeNode  pNode = node.getParent();
            DtoQaMonitoring parentBean = (DtoQaMonitoring)pNode.getDataBean();
            DtoWbsActivity newBean=new DtoWbsActivity();
            newBean=parentBean.getDtoWbsActivity();
            if(newBean.isReadonly()){
               deleteBtn.setEnabled(false);
            }else{
                deleteBtn.setEnabled(true);
            }
            addActionBtn.setEnabled(false);
        }
    }

    private boolean processRowSelectedLostHandle(int oldSelectedRow,
                                                 Object oldSelectedNode) {
        boolean result = true;
        VWWorkArea selectedArea = this.getDownArea().getSelectedWorkArea();
        if (selectedArea instanceof RowSelectedLostListener) {
            result = ((RowSelectedLostListener) selectedArea).
                     processRowSelectedLost(oldSelectedRow, oldSelectedNode);
        }
        return result;
    }

    ////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        String accountId = (String) param.get("accountId");
        if (accountId != null) {
            this.inAcntRid = new Long(accountId);
        } else {
            this.inAcntRid = (Long) param.get("inAcntRid");
        }
        //增加界面菜单的参数,SEPG可任意修改,其他菜单保持不变
        String newEntryFunType = (String) param.get("entryFunType");
        if(newEntryFunType != null) {
            entryFunType = newEntryFunType;
        } else if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN;
        }


        this.refreshFlag = true;

    }

    /////// 段3，获取数据并刷新画面
    //最外层的workArea不需要刷新自己
    public void refreshWorkArea() {
        if (this.refreshFlag == true) {
            Parameter param = new Parameter();
            param.put("inAcntRid", this.inAcntRid);
            param.put("entryFunType", entryFunType);
            mList.setParameter(param);
            mList.refreshWorkArea();
            this.refreshFlag = false;
        }
    }

    /////// 段4，事件处理
    public void actionPerformedAddPoint(ActionEvent e) {
        mList.actionPerformedAddPoint(e);
    }

    public void actionPerformedAddAction(ActionEvent e) {
        ITreeNode currentNode = mList.getSelectedNode();
        DtoQaMonitoring currentDataBean = (DtoQaMonitoring) currentNode.
                                              getDataBean();
        List pointList = currentDataBean.getPointList();
        if(pointList.size() > 0) {
            VwChoosePoint vwPoint = new VwChoosePoint(mList);
            VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                    "Choose Check Point", vwPoint, vwPoint);
                pop.setSize(300, 200);
                pop.show();

        } else {
            comMSG.dispErrorDialog("There is no check point in this wbs/activiy!");
        }

    }

    public void actionPerformedLoad(ActionEvent e) {
        Parameter param = new Parameter();
        param.put("inAcntRid", this.inAcntRid);
        mList.setParameter(param);
        mList.reloadData();
    }

    public void actionPerformedDel(ActionEvent e) {
        mList.actionPerformedDelete(e);
    }

    public void actionPerformedExpand(ActionEvent e) {
        mList.actionPerformedExpand(e);
    }

    public void processRowSelectedQaMonitoringList() {
        ITreeNode node = mList.getSelectedNode();
        setButtonStatus(node);
        if (node != null) {
            Parameter param = new Parameter();
            param.put(DtoKEY.WBSTREE, node);
            param.put("listPanel", mList);
            try {
                this.getDownArea().getSelectedWorkArea().refreshWorkArea();
            } catch (Exception e) {

            }
        }
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        if (this.refreshFlag == true) {
            mList.saveWorkArea();
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public String[][] getParameterInfo() {
        String[][] parameterInfo = { {"accountId", "", "the rid of account"}
                                   , {"entryFunType", "", "entryFunType"}
        };
        return parameterInfo;
    }


    public void dataChanged(String name, Object value) {
        if (name.equals("account")) {
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
                    this.refreshFlag = true;
                    ProcessVariant.set("accountChanged", Boolean.TRUE);
                }
            }
        }

    }


    private boolean validateData() {
        StringBuffer msg = new StringBuffer("");
        List repeatRow = new ArrayList();
        List list = ((ITreeNode)mList.getModel().getRoot()).listAllChildrenByPostOrder();
        for (int i = 0; i < list.size(); i++) {
            ITreeNode node = (ITreeNode)list.get(i);
            DtoQaMonitoring dto = (DtoQaMonitoring)node.getDataBean();
            DtoWbsActivity dtoWA = new DtoWbsActivity();
            ITreeNode parentNode=null;
            if(dto.getType().equals(DtoQaCheckPoint.DTO_PMS_CHECK_POINT_TYPE)){
                parentNode=node.getParent();
            }else if(dto.getType().equals(DtoQaCheckAction.DTO_PMS_CHECK_ACTION_TYPE)){
                parentNode=node.getParent().getParent();
            }
            if(parentNode!=null){
                DtoQaMonitoring dtoBean = (DtoQaMonitoring)parentNode.
                                          getDataBean();
                dtoWA = dtoBean.getDtoWbsActivity();
            }
            if(!dtoWA.isReadonly()){
                if (!(dto.getType().equals(DtoKey.TYPE_WBS)) &&
                    !(dto.getType().equals(DtoKey.TYPE_ACTIVITY))) {
                   String  code=dto.getCode();
                    if (dto.getType().equals(DtoQaCheckAction.
                                             DTO_PMS_CHECK_ACTION_TYPE)) {
                        if (StringUtil.nvl(dto.getOccasion()).equals("") == true) {
                            msg.append("The " + code +
                                       " ： Must input occasion.\r\n");
                        }
                    }
                }
            }
        }

        if (msg.toString().equals("") == false) {
            comMSG.dispErrorDialog(msg.toString());
            return false;
        } else {
            return true;
        }
    }
    public static void main(String args[]) {
        VwQaMonitoring VwMon = new VwQaMonitoring();
        TestPanel.show(VwMon);
    }

}
