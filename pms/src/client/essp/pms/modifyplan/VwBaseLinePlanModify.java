package client.essp.pms.modifyplan;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.excelUtil.IExcelParameter;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.wbs.DtoActivityRelation;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import client.essp.cbs.cost.activity.VwActivityCost;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.excelUtil.VWJButtonExcel;
import client.essp.common.view.VWAccountLabel;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.activity.VwActivityGeneral;
import client.essp.pms.activity.VwActivityStatus;
import client.essp.pms.activity.code.VwActivityCodeList;
import client.essp.pms.activity.process.VwActivityProcess;
import client.essp.pms.activity.relation.VwRelationship;
import client.essp.pms.activity.worker.VwActivityWorkerList;
import client.essp.pms.activity.wp.VwWpListActivity;
import client.essp.pms.modifyplan.base.VMTreeTableModelForBL;
import client.essp.pms.modifyplan.popselect.WbsActivityDrogTarget;
import client.essp.pms.templatePop.VwWbsPopSelect;
import client.essp.pms.wbs.VwWbsGeneral;
import client.essp.pms.wbs.activity.VwWbsActivityList;
import client.essp.pms.wbs.checkpoint.VwCheckPointList;
import client.essp.pms.wbs.code.VwWbsCodeList;
import client.essp.pms.wbs.pbsAndFiles.VwPbsAndFile;
import client.essp.pms.wbs.process.VwWbsProcess;
import client.framework.common.Constant;
import client.framework.common.Global;
import client.framework.view.common.comMSG;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.IVWAppletParameter;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJPopupEditor;
import com.wits.util.IVariantListener;
import com.wits.util.Parameter;
import com.wits.util.ProcessVariant;
import com.wits.util.TestPanel;
import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttExportSettings;
import net.sourceforge.ganttproject.GanttProject;
import net.sourceforge.ganttproject.GanttTask;
import net.sourceforge.ganttproject.chart.Chart;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyCollection;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishStartConstraintImpl;


public class VwBaseLinePlanModify extends VWGeneralWorkArea implements
    IVWAppletParameter, IVariantListener {
    public static String ACTIONID_EXPORTPLAN_PRE = "FBLPlanModifyExportPlanPre";
    public static String ACTIONID_EXPORTPLAN = "FBLPlanModifyExportPlan";
    public static final String ACTIONID_NODECOPY ="FBLPlanModifyCopyNodeAction";
    /**
     * define control variable
     */
    private int taskIdCounter = 1;
    private boolean refreshFlag = false;

    private ITreeNode copyNode;
    private TreePath copyNodePath;
    private boolean cutFlag = false;
    private boolean copyOnlyActivity = false;
    private int pasteTimes = 1;
//    private boolean saveStatus = false;

    //判断是否是生成或应用模版时的标志位
    //此标志位将会导致detail和save按钮不可用
    private boolean templateFlag = false;
    /**
     * define common data (globe)
     */
    private Long inAcntRid;

/////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    VwBLPlanList blPlanList;
    private String entryFunType;

   JButton templateBtn = null;
    JButton detailBtn = null;
    JButton addWBSBtn = null;
    JButton addActivityBtn = null;
    JButton cutBtn = null;
    JButton copyBtn = null;
    JButton pasteBtn = null;
    JButton deleteBtn = null;
    JButton saveBtn = null;
    JButton upBtn = null;
    JButton downBtn = null;
    JButton leftBtn = null;
    JButton rightBtn = null;
    JButton expandBtn = null;
    JButton filterBtn = null;
    JButton computeBtn = null;
    JButton refreshBtn = null;
    JButton ajustTimingBtn = null;
    JButton columnBtn = null;
    JButton exportBtn = null;
    List chartBtnList;
    int imageHeight;
    int imageWidth;

    VWAccountLabel accountLabel = new VWAccountLabel();
    VWJLabel filterLbl = new VWJLabel();

    VWTDWorkArea tdArea = new VWTDWorkArea(400);
    JSplitPane planArea = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JScrollPane planScroll = new JScrollPane();
    GanttProject project;


    VWWorkArea activityArea = new VWWorkArea();
    VwWbsActivityList activityList;
    VWWorkArea wpArea = new VWWorkArea();
    VwWpListActivity activityWp;
    VwWbsPopSelect popSelect = null;
    private JTree leftTree;

    public VwBaseLinePlanModify() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        addUICEvent();
    }


    //Component initialization
    private void jbInit() throws Exception {
        blPlanList = new VwBLPlanList(this.filterLbl, accountLabel);
        project = new GanttProject(false, true);
        planArea.setDividerLocation(600);
        planArea.setBorder(BorderFactory.createEmptyBorder());
        planArea.setPreferredSize(new Dimension(Double.valueOf(planArea.getSize().
            getWidth()).intValue(), 2048));
        planArea.setOneTouchExpandable(true);
        planArea.setDividerSize(7);
        planArea.setTopComponent(blPlanList);
        planArea.setBottomComponent(project.getArea());
//        planArea.setAutoscrolls(false);
        planScroll.getViewport().add(planArea);
        tdArea.getTopArea().add(planScroll);
        this.addTab("Planning", tdArea);
        activityList = new VwWbsActivityList();
        activityArea.addTab("Activity", activityList);
        activityWp = new VwWpListActivity();
        wpArea.addTab("WP", activityWp);
        //创建Drog的目标
        new WbsActivityDrogTarget(blPlanList,this).createDrogTarget();

//        int upMaxHeight=this.getParentWindow().getHeight()-100;
//        tdArea.getSplitPane().setDividerLocation(upMaxHeight);

    }

    private void resetTreeHeight() {
        int rows = 1;
        if (leftTree != null) {
            ITreeNode root = (ITreeNode) blPlanList.getModel().getRoot();
            rows = root.listAllChildrenByPreOrder().size();
        }
        planArea.setPreferredSize(new Dimension(Double.valueOf(planArea.getSize().
            getWidth()).intValue(), 50 + rows * 20));
    }

    public void refreshGraphic() {
        taskIdCounter = 1;
        VMTreeTableModelForBL model = (VMTreeTableModelForBL) blPlanList.
                                      getModel();
        DefaultMutableTreeNode projectNode = project.getTree().getRoot();
        projectNode.removeAllChildren();
        project.newEmptyTaskManager();
        GanttTask rootTask = (GanttTask) projectNode.getUserObject();
        //修改根节点,并且增加项目根结点作为Gantt图的根节点
        ITreeNode root = (ITreeNode) model.getRoot();
        DtoWbsActivity rootData = (DtoWbsActivity) root.getDataBean();
        if (rootData.getPlannedStart() != null && rootData.getPlannedFinish() != null) {
            GanttCalendar calStart0 = new GanttCalendar(rootData.
                getPlannedStart());
            GanttCalendar calEnd0 = new GanttCalendar(rootData.getPlannedFinish());
            calEnd0.add(1);
            rootTask.setStart(calStart0);
            rootTask.setEnd(calEnd0);
        }
        rootTask.setTaskID(taskIdCounter++);
        rootTask.setNotes(rootData.getAccountCode());
        rootTask.setColor(project.getArea().getTaskColor());
        rootTask.setProjectTask(true);
        copyTree(root, projectNode); //复制整个树
        buildRlation(); //建立关系
        refreshTreeStructure(root); //重构树的结构
        project.getArea().repaint();
        project.repaint2();
        resetTreeHeight();
    }

    public void copyTree(ITreeNode leftNode, DefaultMutableTreeNode rightNode) {
        DtoWbsActivity dto = (DtoWbsActivity) leftNode.getDataBean();
        GanttTask task = project.getTaskManager().createTask();
        if (dto.getPlannedStart() != null && dto.getPlannedFinish() != null) {
            GanttCalendar calStart = new GanttCalendar(dto.getPlannedStart());
            GanttCalendar calEnd = new GanttCalendar(dto.getPlannedFinish());
            calEnd.add(1);
            task.setStart(calStart);
            task.setEnd(calEnd);
        } else {
            GanttCalendar cal = new GanttCalendar();
            task.setStart(cal);
            task.setEnd(cal.newAdd( -2));
        }
        if (dto.isMilestone() != null) {
            task.setMilestone(dto.isMilestone().booleanValue());
        }
        task.setTaskID(taskIdCounter++);
        task.setCompletionPercentage(dto.getCompleteRate().intValue());
        task.setName(dto.getName());
        String myCode;
        if (dto.isWbs()) {
            myCode = "WBS" + dto.getAcntRid() + dto.getWbsRid();
        } else {
            myCode = "ACTIVITY" + dto.getAcntRid() + dto.getActivityRid();
        }
        task.setNotes(myCode); //放CODE的地方
        task.setColor(project.getArea().getTaskColor());

        DefaultMutableTreeNode tempNode = project.getTree().addObject(task,
            rightNode, -1);
        project.getTaskManager().registerTask(task);

        List childs = leftNode.children();
        for (int i = 0; i < childs.size(); i++) {
            ITreeNode child = (ITreeNode) childs.get(i);
            copyTree(child, tempNode);
        }
    }

    //根据左边的树来展开右边的树
    public void refreshTreeStructure(ITreeNode leftNode) {
        List childs = leftNode.children();
        for (int i = 0; i < childs.size(); i++) {
            ITreeNode child = (ITreeNode) childs.get(i);
            refreshTreeStructure(child);
        }

        ITreeNode[] path = ((DtoWbsTreeNode) leftNode).getPath();
        int row = leftTree.getRowForPath(new TreePath(path));
        DtoWbsActivity dto = (DtoWbsActivity) leftNode.getDataBean();
        String myCode;
        if (dto.isWbs()) {
            myCode = "WBS" + dto.getAcntRid() + dto.getWbsRid();
        } else {
            myCode = "ACTIVITY" + dto.getAcntRid() + dto.getActivityRid();
        }
        DefaultMutableTreeNode node = getCurNodeByMyCode(project.getTree().
            getRoot(), myCode);
        if (row >= 0) {
            project.getTree().myExpandRefresh((DefaultMutableTreeNode) node.
                                              getParent());
        } else {
            project.getTree().myCollapseRefresh((DefaultMutableTreeNode) node.
                                                getParent());
        }
    }


    public void buildRlation() {
        List relationList = blPlanList.loadAllRelation();
        if (relationList != null) {
            TaskManager taskManager = project.getTaskManager();

            for (int i = 0; i < relationList.size(); i++) {
                DtoActivityRelation rel = (DtoActivityRelation) relationList.
                                          get(i);
                try {
                    Task dependee = getTaskByRid(rel.getPostActivityId());
                    Task dependant = getTaskByRid(rel.getActivityId());
                    if (dependee != null && dependant != null) {
                        TaskDependencyCollection depColl = taskManager.
                            getDependencyCollection();
                        depColl.createDependency(dependee, dependant,
                                                 getRlationTypeConstraint(rel.
                            getStartFinishType()));

                        TaskDependency dep = project.getTaskManager().
                                             getDependencyCollection()
                                             .createDependency(dependant,
                            dependee, new FinishStartConstraintImpl());

                        dep.setConstraint(getRlationTypeConstraint(rel.
                            getStartFinishType()));
                        dep.setDifference(0);
                        dep.setHardness(TaskDependency.Hardness.parse(
                            "Strong"));
                    }
                } catch (TaskDependencyException ex) {
                }
            }
        }

    }


    public TaskDependencyConstraint getRlationTypeConstraint(String type) {
        if (type.equals("FS")) {
            return project.getTaskManager().createConstraint(2);
        } else
        if (type.equals("SS")) {
            return project.getTaskManager().createConstraint(1);
        } else
        if (type.equals("FF")) {
            return project.getTaskManager().createConstraint(3);
        } else { //"SF"
            return project.getTaskManager().createConstraint(4);
        }
    }

    public int convertType(String type) {
        if (type.equals("FS")) {
            return 2;
        } else
        if (type.equals("SS")) {
            return 1;
        } else
        if (type.equals("FF")) {
            return 3;
        } else { //"SF"
            return 4;
        }
    }

    //通过Activity的Rid来找此Activity
    public DtoWbsActivity getActivityCodeByRid(ITreeNode root, Long rid) {
        DtoWbsActivity dto = (DtoWbsActivity) root.getDataBean();
        if (dto.isActivity() && dto.getActivityRid().compareTo(rid) == 0) {
            return dto;
        } else {
            if (!root.isLeaf()) {
                List childs = root.children();
                for (int i = 0; i < childs.size(); i++) {
                    DtoWbsActivity temp = getActivityCodeByRid((ITreeNode)
                        childs.get(i),
                        rid);
                    if (temp != null) {
                        return temp;
                    }
                }
            }
        }
        return null;
    }


    public DefaultMutableTreeNode getCurNodeByMyCode(DefaultMutableTreeNode
        root,
        String code) {
        Task task = (Task) root.getUserObject();
        if (task.getNotes().equals(code)) {
            return root;
        } else {
            Enumeration enumeration = root.children();
            while (enumeration.hasMoreElements()) {
                Object o = enumeration.nextElement();
                DefaultMutableTreeNode tmp = getCurNodeByMyCode((
                    DefaultMutableTreeNode) o, code);
                if (tmp != null) {
                    return tmp;
                }
            }
        }
        return null;
    }

    public Task getTaskByRid(Long rid) {
        DtoWbsActivity dto = getActivityCodeByRid((ITreeNode) blPlanList.
                                                  getModel().
                                                  getRoot(), rid);
        if (dto != null) {
            String code = "ACTIVITY" + dto.getAcntRid() + dto.getActivityRid();
            DefaultMutableTreeNode node = getCurNodeByMyCode(project.getTree().
                getRoot(), code);
            if (node != null) {
                return (Task) node.getUserObject();
            } else {
                return null;
            }
        } else {
            return null;
        }
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
    public void setLeftBtnVisible(boolean flag) {
        templateBtn.setVisible(flag);
        blPlanList.btnWorker.setVisible(flag);
        detailBtn.setVisible(flag);
        addWBSBtn.setVisible(flag);
        addActivityBtn.setVisible(flag);
        cutBtn.setVisible(flag);
        copyBtn.setVisible(flag);
        pasteBtn.setVisible(flag);
        deleteBtn.setVisible(flag);
        saveBtn.setVisible(flag);
        upBtn.setVisible(flag);
        downBtn.setVisible(flag);
        leftBtn.setVisible(flag);
        rightBtn.setVisible(flag);
        expandBtn.setVisible(flag);
        filterBtn.setVisible(flag);
//        computeBtn.setVisible(flag);
        refreshBtn.setVisible(flag);
        ajustTimingBtn.setVisible(flag);
        columnBtn.setVisible(flag);
    }

    private void addUICEvent() {
        blPlanList.getTreeTable().getTreeTableModel().addTreeModelListener(new
            TreeModelListener() {
            public void treeNodesChanged(TreeModelEvent e) {
//                System.out.println("数据改变，进入其监听");
                DtoWbsActivity dataBean = (DtoWbsActivity) blPlanList.
                                          getSelectedNode().getDataBean();

                if (!dataBean.isReadonly()) {
                    saveBtn.setEnabled(true);
                }
            }

            public void treeNodesInserted(TreeModelEvent e) {
//                System.out.println("插入节点");
            }

            public void treeNodesRemoved(TreeModelEvent e) {
//                System.out.println("节点移除");
            }

            public void treeStructureChanged(TreeModelEvent e) {
//                System.out.println("结构改变");
            }
        });

        blPlanList.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int w = blPlanList.getSize().width;
                if (w < 1) {
                    setLeftBtnVisible(false);
                } else {
                    setLeftBtnVisible(true);
                }
            }
        });

        project.getArea().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int w = project.getArea().getSize().width;
                if (w < 1) {
                    for (int i = 0; i < chartBtnList.size(); i++) {
                        ((JButton) chartBtnList.get(i)).setVisible(false);
                    }
                } else {
                    for (int i = 0; i < chartBtnList.size(); i++) {
                        ((JButton) chartBtnList.get(i)).setVisible(true);
                    }
                }

            }
        });

        blPlanList.getTreeTable().getTree().addTreeExpansionListener(new
            TreeExpansionListener() {
            public void treeExpanded(TreeExpansionEvent event) {
                ITreeNode node = (ITreeNode) event.getPath().
                                 getLastPathComponent();
                DtoWbsActivity dto = (DtoWbsActivity) node.getDataBean();
                String code;
                if (dto.isWbs()) {
                    code = "WBS" + dto.getAcntRid() + dto.getWbsRid();
                } else {
                    code = "ACTIVITY" + dto.getAcntRid() + dto.getActivityRid();
                }
                DefaultMutableTreeNode curNode = getCurNodeByMyCode(project.
                    getTree().getRoot(), code);
                leftTree.expandPath(event.getPath());
                project.getTree().myExpandRefresh(curNode);
            }

            public void treeCollapsed(TreeExpansionEvent event) {
                ITreeNode node = (ITreeNode) event.getPath().
                                 getLastPathComponent();
                DtoWbsActivity dto = (DtoWbsActivity) node.getDataBean();
                String code;
                if (dto.isWbs()) {
                    code = "WBS" + dto.getAcntRid() + dto.getWbsRid();
                } else {
                    code = "ACTIVITY" + dto.getAcntRid() + dto.getActivityRid();
                }
                DefaultMutableTreeNode curNode = getCurNodeByMyCode(project.
                    getTree().getRoot(), code);
                leftTree.collapsePath(event.getPath());
                project.getTree().myCollapseRefresh(curNode);
            }
        });

        this.blPlanList.getTreeTable().addRowSelectedListener(new
            RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedEvent();
            }
        });


        JScrollPane bScrollPane = blPlanList.getTreeTable().getScrollPane();
//        bScrollPane.setWheelScrollingEnabled(true);
        bScrollPane.addMouseWheelListener(new MouseWheelListener(){
            public void mouseWheelMoved(MouseWheelEvent e) {
                processMouseWheelMoved(e);
            }
        });
        project.getArea().addMouseWheelListener(new MouseWheelListener(){
            public void mouseWheelMoved(MouseWheelEvent e) {
                processMouseWheelMoved(e);
            }
        });


//        //Account Label
        tdArea.getButtonPanel().add(accountLabel);

        //filter label
        VWJLabel filterTitle = new VWJLabel();
        filterTitle.setSize(20, 20);
//        filterTitle.setPreferredSize(new Dimension(80, 20));
//        filterTitle.setHorizontalAlignment(SwingConstants.RIGHT);
//        filterTitle.setText("Filter By:");
        tdArea.getButtonPanel().add(filterTitle);
        filterLbl.setSize(60, 20);
        filterLbl.setPreferredSize(new Dimension(60, 20));
        filterLbl.setText("All Task");
        filterLbl.setToolTipText("Filter By:All Task");
        tdArea.getButtonPanel().add(filterLbl);
        //template button
        templateBtn = tdArea.getButtonPanel().addButton("applyTemplate.png");
        templateBtn.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (popSelect == null) {
                    Container c = getMyParentWindow();
                    popSelect = VwWbsPopSelect.createInstance( c);
                } else {
                    popSelect.showPopSelect();
                }

            }
        });
        templateBtn.setToolTipText("apply template");
        tdArea.getButtonPanel().addButton(blPlanList.btnWorker);
        //detail button
        detailBtn = tdArea.getButtonPanel().addButton("detail.png");
        detailBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //执行弹出窗口，弹出详细的WBS或Activity的卡片框
                popDetailWindow();
            }
        });

        detailBtn.setToolTipText("show/modify detail");

        //add wbs
        addWBSBtn = tdArea.getButtonPanel().addButton("addwbs.png");
        addWBSBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //执行添加WBS的动作
                blPlanList.actionPerformedAddWBS(e);
                refreshGraphic();
                actionPerformedSave();
            }
        });
        addWBSBtn.setToolTipText("add WBS");

        //Add activity
//        addActivityBtn = blPlanList.getButtonPanel().addAddButton(new ActionListener() {
        addActivityBtn = tdArea.getButtonPanel().addButton("addact.png");
        addActivityBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                blPlanList.actionPerformedAddActivity(e);
                refreshGraphic();
                actionPerformedSave();
            }
        });
        addActivityBtn.setToolTipText("add activity");

        //cut
        cutBtn = tdArea.getButtonPanel().addButton("cut.png");
        cutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //执行剪切动作
                //弄一个弹出窗口
//                popWindowForNextVesion();
                copyNode = blPlanList.getSelectedNode();
                pasteTimes = 1;
                if (copyNode != null) {
                    cutFlag = true;
                    copyNodePath = blPlanList.getTreeTable().getSelectionPath();
                    if (((DtoWbsActivity) copyNode.getDataBean()).isActivity()) {
                        copyOnlyActivity = true;
                    } else {
                        copyOnlyActivity = false;
                    }

                }
            }
        });
        cutBtn.setToolTipText("cut");

        //copy
        copyBtn = tdArea.getButtonPanel().addButton("copy.png");
        copyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copyNode = blPlanList.getSelectedNode();
                DtoWbsActivity dto = (DtoWbsActivity) copyNode.getDataBean();
                cutFlag = false;
                pasteTimes = 1;
                copyNodePath = blPlanList.getTreeTable().getSelectionPath();
                if (dto.isActivity()) {
                    copyOnlyActivity = true;
                } else {
                    copyOnlyActivity = false;
                }
                pasteBtn.setEnabled(!dto.isReadonly() && dto.isWbs());
            }
        });
        copyBtn.setToolTipText("copy");

        //paste
        pasteBtn = tdArea.getButtonPanel().addButton("paste.png");
        pasteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //执行粘贴操作
                ITreeNode selectedNode = blPlanList.getSelectedNode();
                String isCut = "false";
                if (cutFlag) {
                    //执行删除动作并清空标志位
                    //blPlanList.actionPerformedDelete(copyNode);
                    cutFlag = false;
                    isCut = "true";
                }

                if (copyNode != null && selectedNode != null) {
                    //提示是否保存数据，如果保存，则进入下步操作
                   if(Constant.OK==comMSG.dispDialogProcessYN("do you want to copy node ?")){

                       ITreeNode tempNode =blPlanList.copyNode((DtoWbsTreeNode)copyNode,(DtoWbsTreeNode)selectedNode,isCut);
                       //如果是cut,则删除copyNode
                       if("true".equals(isCut)){
                           blPlanList.getTreeTable().deleteRow(copyNode);
                       }
                       copyNode = null;
                       pasteUpdate(selectedNode,tempNode);
//                       refreshBtn.doClick();
                       pasteBtn.setEnabled(false);
                    }
//                    ITreeNode tempNode = setNodeInsertProp(copyNode,
//                        selectedNode);
//                    blPlanList.getTreeTable().addRow(selectedNode, tempNode);
//                    saveBtn.setEnabled(true);
                }
//                pasteTimes++;
//                blPlanList.fireProcessDataChange();
//                refreshGraphic();
            }
        });
        pasteBtn.setToolTipText("paste");
        pasteBtn.setEnabled(false);
        //Delete
        deleteBtn = tdArea.getButtonPanel().addDelButton(new
            ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
                refreshGraphic();
            }
        });

        //Save
        saveBtn = tdArea.getButtonPanel().addSaveButton(new
            ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //执行保存动作
                actionPerformedSave();
                refreshGraphic();
            }
        });
        saveBtn.setEnabled(false);

        //Down
        downBtn = tdArea.getButtonPanel().addButton("down.png");
        downBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDown(e);
                refreshGraphic();
            }
        });
        downBtn.setToolTipText("down");
        //Up
        upBtn = tdArea.getButtonPanel().addButton("up.png");
        upBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUp(e);
                refreshGraphic();
            }
        });
        upBtn.setToolTipText("up");
        //Left
        leftBtn = tdArea.getButtonPanel().addButton("left.png");
        leftBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLeft(e);
                refreshGraphic();
            }
        });
        leftBtn.setToolTipText("left");
        //Right
        rightBtn = tdArea.getButtonPanel().addButton("right.png");
        rightBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedRight(e);
                refreshGraphic();
            }
        });
        rightBtn.setToolTipText("right");
        //Adjust Timing
        ajustTimingBtn = tdArea.getButtonPanel().addButton("adjustTiming.gif");
        ajustTimingBtn.setToolTipText("Adjust Timing");
        ajustTimingBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (blPlanList.getTreeTable().getSelectedNode() == null) {
                    comMSG.dispErrorDialog("Please select an item!");
                    return;
                }
                VwAdjustTiming adjust = new VwAdjustTiming(blPlanList.
                    getTreeTable());
                VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                    "Adjust Timing", adjust, 2);
                pop.setSize(300, 200);
                int resultStatus = pop.showConfirm();
                if (resultStatus == Constant.OK) {
                    saveBtn.setEnabled(true);
                    refreshGraphic();
                }
            }
        });

        //expand
        expandBtn = tdArea.getButtonPanel().addButton("expand.png");
        expandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand(e);
            }
        });
        expandBtn.setToolTipText("expand");
        //filter
        filterBtn = tdArea.getButtonPanel().addButton("filter.gif");
        filterBtn.setToolTipText("Planning Filter");
        filterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //弹出过滤条件选择窗口
                VwBLPlanFilter filter = new VwBLPlanFilter(blPlanList.
                    getTreeTable(), blPlanList, filterLbl);

                VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                    "Filter", filter, 2);
                pop.setSize(300, 200);
                int resultStatus = pop.showConfirm();
                if (resultStatus == Constant.OK) {
                    refreshGraphic();
                    if (filterLbl.getText().equals(VwBLPlanFilter.
                        FILTER_CONDITION[0])) {
                        ITreeNode node = (ITreeNode) blPlanList.getModel().
                                         getRoot();
                        if (node != null) {
                            if(((DtoWbsActivity) node.getDataBean()).isReadonly()) {
                            ajustTimingBtn.setEnabled(false);
                            } else {
                                ajustTimingBtn.setEnabled(true);
                            }
                        }
                    } else {
                        ajustTimingBtn.setEnabled(false);
                    }
                }
            }
        });

        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(blPlanList.getTreeTable(),
                                         blPlanList);
        columnBtn = chooseDisplay.getDisplayButton();
        tdArea.getButtonPanel().addButton(columnBtn);

        //calculate complete rate
//        computeBtn = tdArea.getButtonPanel().addButton("calc.gif");
//        computeBtn.setToolTipText("Complete Rate");
//        computeBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                blPlanList.actionPerformedCompute(e);
//            }
//        });

        //Refresh
        refreshBtn = tdArea.getButtonPanel().addLoadButton(new
            ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        refreshBtn.setToolTipText("refresh data");
        JButton splitBtn = new JButton();
        splitBtn.setPreferredSize(new Dimension(2, 20));
        tdArea.getButtonPanel().addButton(splitBtn);
        //For GanttChart
        chartBtnList = project.getGanttChartBtnList();
        for (int i = 0; i < chartBtnList.size(); i++) {
            JButton btn = (JButton) chartBtnList.get(i);
            btn.setBackground(new Color(148, 170, 214));
            btn.setForeground(new Color(148, 170, 214));
            tdArea.getButtonPanel().addButton(btn);
        }
        //export
        exportBtn = new VWJButtonExcel(new IExcelParameter() {
            public String getUrlAddress() {
                byte[] imageTmp = new byte[0];
                try {
                    imageTmp = getGanttImage();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                Byte[] image = new Byte[imageTmp.length];
                for (int i = 0; i < imageTmp.length; i++) {
                    image[i] = Byte.valueOf(imageTmp[i]);
                }
                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(ACTIONID_EXPORTPLAN_PRE);
                inputInfo.setInputObj("image", image);
                inputInfo.setInputObj("filter", filterLbl.getToolTipText());
                inputInfo.setInputObj("imageHeight", String.valueOf(imageHeight));
                inputInfo.setInputObj("imageWidth", String.valueOf(imageWidth));
                DtoWbsTreeNode root = (DtoWbsTreeNode) blPlanList.getModel().
                                      getRoot();
                inputInfo.setInputObj("rootNode", root);
                inputInfo.setInputObj("acntRid", inAcntRid);
                accessData(inputInfo);

                StringBuffer sb = new StringBuffer(Global.appRoot);
                sb.append(IExcelParameter.DEFAULT_EXCEL_JSP_ADDRESS);
                sb.append("?");
                sb.append(IExcelParameter.ACTION_ID);
                sb.append("=");
                sb.append(ACTIONID_EXPORTPLAN);
                return sb.toString();
            }
        });

        tdArea.getButtonPanel().addButton(exportBtn);
          exportBtn.setToolTipText("export");

//        exportBtn = tdArea.getButtonPanel().addButton("export.png");
//        exportBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//                byte[] imageTmp = new byte[0];
//                try {
//                    imageTmp = getGanttImage();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                Byte[] image = new Byte[imageTmp.length];
//                for (int i = 0; i < imageTmp.length; i++) {
//                    image[i] = Byte.valueOf(imageTmp[i]);
//                }
//                InputInfo inputInfo = new InputInfo();
//                inputInfo.setActionId(ACTIONID_EXPORTPLAN_PRE);
//                inputInfo.setInputObj("image", image);
//                inputInfo.setInputObj("filter",filterLbl.getToolTipText());
//                inputInfo.setInputObj("imageHeight",String.valueOf(imageHeight));
//                inputInfo.setInputObj("imageWidth",String.valueOf(imageWidth));
//                DtoWbsTreeNode root = (DtoWbsTreeNode) blPlanList.getModel().
//                                      getRoot();
//                inputInfo.setInputObj("rootNode", root);
//                inputInfo.setInputObj("acntRid", inAcntRid);
////                accessData(inputInfo);
//                testExprot(inputInfo);
//            }
//        });
//        tdArea.getButtonPanel().addButton(exportBtn);

        ProcessVariant.addDataListener("account", this);
        ProcessVariant.addDataListener("planningtree", this);
    }

//    private void testExprot(InputInfo inputInfo) {
//        Parameter param = new Parameter();
//        param.put("image",inputInfo.getInputObj("image"));
//        param.put("imageHeight",inputInfo.getInputObj("imageHeight"));
//        param.put("imageWidth",inputInfo.getInputObj("imageWidth"));
//        param.put("rootNode",inputInfo.getInputObj("rootNode"));
//        param.put("acntRid",inputInfo.getInputObj("acntRid"));
//        param.put("filter",inputInfo.getInputObj("filter"));
//        String currentDate = comDate.dateToString(new Date());
//        String outputFile = "Project Plan" + currentDate + ".xls"; //导出文件名
//        ExcelExporter export = new PlanExporter(outputFile);
//        try {
//            export.export(param);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new BusinessException("", "error exporting data!", ex);
//        }
//
//    }

    private void processMouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown()) {//Ctrl+Wheel Gantt图缩放
            if (e.getUnitsToScroll() > 0 && project.getZoomManager().canZoomOut()) {
                //下滚缩小
                project.getZoomManager().zoomOut();
            } else if (e.getUnitsToScroll() < 0 && project.getZoomManager().canZoomIn()) {
                //上滚放大
                project.getZoomManager().zoomIn();
            }
            return;
        }
        if (e.isShiftDown()) {//Shift+Wheel Gantt图平行移动
            if (e.getUnitsToScroll() > 0) {
                //下滚右移
                project.getScrollingManager().scrollRight();
            } else {
                //上滚左移
                project.getScrollingManager().scrollLeft();
            }
            return;
        }

        //上下滚动
        Point p = planScroll.getViewport().getViewPosition();
        //滚动滑块可以在滚动条中最大高度
        int maxHeight = planScroll.getVerticalScrollBar().getMaximum() -
                        planScroll.getVerticalScrollBar().getModel().getExtent();
        int incre = p.y + e.getUnitsToScroll() * 8;
        if (incre < 0) {
            incre = 0;
        } else if (incre > maxHeight) {
            incre = maxHeight;
        }
        p.y = incre;
        planScroll.getViewport().setViewPosition(p);
    }

    private void setButtonStatus(ITreeNode node) {
        if (node == null) {
            return;
        }
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        if (dataBean == null) {
            return;
        }
        if (node.getParent() == null) {
            //说明Node是根节点，根节点指项目的节点，没有详情。
            if (dataBean.isReadonly()) {
                addWBSBtn.setEnabled(false);
                addActivityBtn.setEnabled(false);
            } else {
                addWBSBtn.setEnabled(true);
                addActivityBtn.setEnabled(true);
            }
            detailBtn.setEnabled(true);
            cutBtn.setEnabled(false);
            copyBtn.setEnabled(false);
            if (this.copyNode != null) {
                pasteBtn.setEnabled(true);
            } else {
                pasteBtn.setEnabled(false);
            }
            deleteBtn.setEnabled(false);
            upBtn.setEnabled(false);
            downBtn.setEnabled(false);
            leftBtn.setEnabled(false);
            rightBtn.setEnabled(false);
        } else if (dataBean.isActivity()) {
            //说明此节点是一个Activity
            if (dataBean.isReadonly()) {
                //只读的情况下
                detailBtn.setEnabled(true);
                addWBSBtn.setEnabled(false);
                addActivityBtn.setEnabled(false);
                copyBtn.setEnabled(true);
                cutBtn.setEnabled(false);
                pasteBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                upBtn.setEnabled(false);
                downBtn.setEnabled(false);
                leftBtn.setEnabled(false);
                rightBtn.setEnabled(false);
            } else if (dataBean.isInsert()) {
                detailBtn.setEnabled(false);
                addWBSBtn.setEnabled(false);
                addActivityBtn.setEnabled(false);
                copyBtn.setEnabled(false);
                cutBtn.setEnabled(false);
                pasteBtn.setEnabled(false);
                deleteBtn.setEnabled(true);
                upBtn.setEnabled(false);
                downBtn.setEnabled(false);
                leftBtn.setEnabled(false);
                rightBtn.setEnabled(false);
            } else {
                detailBtn.setEnabled(true);
                addWBSBtn.setEnabled(false);
                addActivityBtn.setEnabled(true);
                copyBtn.setEnabled(true);
                cutBtn.setEnabled(true);
                pasteBtn.setEnabled(false);

                deleteBtn.setEnabled(true);
                //如果是此节点下面的第一个的话就不能向上了
                if (node.getParent().getIndex(node) > 0) {
                    upBtn.setEnabled(true);
                } else {
                    upBtn.setEnabled(false);
                }
                if (node.getParent().getIndex(node) !=
                    node.getParent().getChildCount() - 1) {
                    downBtn.setEnabled(true);
                } else {
                    downBtn.setEnabled(false);
                }
                leftBtn.setEnabled(false); //原来的系统中如此设计，估计是因为怕引起ID冲突，具体原因待查。
                rightBtn.setEnabled(false);
            }
        } else if (dataBean.isWbs()) {
            //说明此节点是一个WBS
            if (dataBean.isReadonly()) {
                //只读的情况下
                detailBtn.setEnabled(true);
                addWBSBtn.setEnabled(false);
                addActivityBtn.setEnabled(false);
                copyBtn.setEnabled(true);
                cutBtn.setEnabled(false);
                pasteBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                upBtn.setEnabled(false);
                downBtn.setEnabled(false);
                leftBtn.setEnabled(false);
                rightBtn.setEnabled(false);
            } else if (dataBean.isInsert()) {
                detailBtn.setEnabled(false);
                addActivityBtn.setEnabled(true);
                copyBtn.setEnabled(false);
                cutBtn.setEnabled(false);
                pasteBtn.setEnabled(false);
                deleteBtn.setEnabled(true);
                upBtn.setEnabled(false);
                downBtn.setEnabled(false);
                leftBtn.setEnabled(false);
                rightBtn.setEnabled(false);
                expandBtn.setEnabled(true);
            } else {
                detailBtn.setEnabled(true);
                addWBSBtn.setEnabled(true);
                addActivityBtn.setEnabled(true);
                copyBtn.setEnabled(true);
                cutBtn.setEnabled(true);
                if (copyNode != null && copyNodePath != null) {
                    TreePath selectedPath = blPlanList.getTreeTable().
                                            getSelectionPath();
                    if (copyNodePath.isDescendant(selectedPath)) {
                        pasteBtn.setEnabled(false);
                    } else {
                        pasteBtn.setEnabled(true);
                    }
                } else {
                    pasteBtn.setEnabled(false);
                }

                deleteBtn.setEnabled(true);
                //如果是此节点下面的第一个的话就不能向上了
                if (node.getParent().getIndex(node) > 0) {
                    upBtn.setEnabled(true);
                } else {
                    upBtn.setEnabled(false);
                }
                if (node.getParent().getIndex(node) !=
                    node.getParent().getChildCount() - 1) {
                    downBtn.setEnabled(true);
                } else {
                    downBtn.setEnabled(false);
                }
                if (node.getParent().getParent() != null) {
                    leftBtn.setEnabled(true);
                } else {
                    leftBtn.setEnabled(false);
                }
                if (node.getParent().getIndex(node) > 0) {
                    int i = node.getParent().getIndex(node);
                    //如果上面是一个Activity也不能右移
                    if (!((DtoWbsActivity) node.getParent().getChildAt(i - 1).
                          getDataBean()).isActivity()) {
                        rightBtn.setEnabled(true);
                    }
                } else {
                    rightBtn.setEnabled(false);
                }
            }
        }
        if (templateFlag) {
            detailBtn.setVisible(false);
            saveBtn.setVisible(false);
        } else {
            detailBtn.setVisible(true);
            saveBtn.setVisible(true);
        }
        if (filterLbl.getText().equals(VwBLPlanFilter.FILTER_CONDITION[0])) {
            if (dataBean.isReadonly()) {
                ajustTimingBtn.setEnabled(false);
            } else {
                ajustTimingBtn.setEnabled(true);
            }
        } else {
            ajustTimingBtn.setEnabled(false);
        }
    }

//    private boolean isSEPG() {
//        return DtoAcntKEY.SEPG_ACCOUNT_FUN.equals(entryFunType);
//    }

/////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        String accountId = (String) param.get("accountId");
        if (accountId != null) {
            this.inAcntRid = new Long(accountId);
        } else {
            this.inAcntRid = (Long) param.get("inAcntRid");
        }
        String tempFlag = (String) param.get("isTemplateOpration");
        if (tempFlag != null) {
            templateFlag = true;
            blPlanList.setTemplateFlag(templateFlag);
            this.inAcntRid = (Long) param.get("acntrid");
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

        if (this.refreshFlag) {
            Parameter param = new Parameter();
            param.put("inAcntRid", this.inAcntRid);
            param.put("entryFunType", entryFunType);
            blPlanList.setParameter(param);
            blPlanList.refreshWorkArea();
            leftTree = blPlanList.getTreeTable().getTree();
            this.refreshFlag = false;
            refreshGraphic();
        }
    }

/////// 段4，事件处理

    public void actionPerformedLoad(ActionEvent e) {
        Parameter param = new Parameter();
        param.put("inAcntRid", this.inAcntRid);
        blPlanList.setParameter(param);
        blPlanList.reloadData();
        refreshGraphic();
//        pasteBtn.setEnabled(false);
    }

    public void actionPerformedSave() {
        ReturnInfo ri = blPlanList.actionPerformedSave();
        if (!ri.isError()) {
            saveBtn.setEnabled(false);
            detailBtn.setEnabled(true);
//                    blPlanList.resetUI();
            //保存以后设置所有的节点的属性均为正常
            setNodeNomrmal((ITreeNode) blPlanList.getModel().getRoot());
            blPlanList.getTreeTable().repaint();
        } else {
            //报错了
            System.out.println("保存时发生了错误!");
        }
    }

    public void actionPerformedDel(ActionEvent e) {
        blPlanList.actionPerformedDelete(e);
    }

    public void actionPerformedDown(ActionEvent e) {
        ITreeNode node = blPlanList.getSelectedNode();
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        if (dataBean.isWbs()) {
            blPlanList.actionPerformedDownForWBS(e);
        } else if (dataBean.isActivity()) {
            blPlanList.actionPerformedDownForActivity(e);
        } else {
            return;
        }
        this.setButtonStatus(node);
    }

    public void actionPerformedUp(ActionEvent e) {
        ITreeNode node = blPlanList.getSelectedNode();
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        if (dataBean.isWbs()) {
            blPlanList.actionPerformedUpForWBS(e);
        } else if (dataBean.isActivity()) {
            blPlanList.actionPerformedUpForActivity(e);
        } else {
            return;
        }

        this.setButtonStatus(node);
    }

    public void actionPerformedLeft(ActionEvent e) {
        ITreeNode node = blPlanList.getSelectedNode();
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        if (dataBean.isWbs()) {
            blPlanList.actionPerformedLeftForWBS(e);
        } else if (dataBean.isActivity()) {
            //目前情况下此句不会执行
            blPlanList.actionPerformedLeftForActivity(e);
        } else {
            return;
        }
        this.setButtonStatus(node);
    }

    public void actionPerformedRight(ActionEvent e) {
        ITreeNode node = blPlanList.getSelectedNode();
//        blPlanList.actionPerformedRight(e);
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        if (dataBean.isWbs()) {
            blPlanList.actionPerformedRightForWBS(e);
        } else if (dataBean.isActivity()) {
            blPlanList.actionPerformedRightForActivity(e);
        } else {
            return;
        }

        this.setButtonStatus(node);
    }

    public void actionPerformedExpand(ActionEvent e) {
        blPlanList.actionPerformedExpand(e);
    }


//选择一行，会进行下面的处理
    public void processRowSelectedEvent() {
        ITreeNode node = blPlanList.getSelectedNode();
        setButtonStatus(node);
        //替换下面的卡片
        DtoWbsActivity dataBean;
        if (node != null) {
            dataBean = (DtoWbsActivity) node.getDataBean();
        } else {
            dataBean = new DtoWbsActivity();
        }
        if (dataBean.isWbs()) {
            tdArea.setDownArea(activityArea);
            tdArea.getDownArea().updateUI();
        } else {
            tdArea.setDownArea(wpArea);
            tdArea.getDownArea().updateUI();
        }
        if (node != null) {
            Parameter param = new Parameter();
//        param.put(DtoKEY.DTO, dataBean);
            param.put(DtoKEY.WBSTREE, node);
            param.put("wbs", dataBean);
            if (dataBean.isWbs()) {
                param.put("callerType", "PP");
                param.put("upTreeTable", blPlanList);
                param.put("ProjectPlan", this);
                activityList.setParameter(param);
            } else {
                activityWp.setParameter(param);
            }
            try {
                tdArea.getDownArea().getSelectedWorkArea().refreshWorkArea();
            } catch (Exception e) {

            }
        }
    }

/////// 段5，保存数据
    public void saveWorkArea() {
        if (this.refreshFlag) {
            blPlanList.saveWorkArea();
//            this.getDownArea().getSelectedWorkArea().saveWorkArea();
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
//                    this.activityGeneral.hasGetManagers = false;
                    ProcessVariant.set("accountChanged", Boolean.TRUE);
                }
            }
        }

    }

    public void popDetailWindow() {
        ITreeNode node = blPlanList.getTreeTable().getSelectedNode();
        if (node != null) {
            DtoWbsActivity nodeData = (DtoWbsActivity) node.getDataBean();
            if (nodeData.isWbs()) {
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
                param.put("wbs", nodeData);
                param.put("listPanel", this.blPlanList);
                wbsGeneral.setParameter(param);
                checkPoint.setParameter(param);
                activityList.setParameter(param);
                pbs.setParameter(param);
                wbsCode.setParameter(param);
                wbsProcess.setParameter(param);

                popWorkArea.setSize(600, 400);
                popWorkArea.setVisible(true);
                VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(),
                    "WBS Detail", popWorkArea);
                pop.setSize(700, 330);
                pop.show();

            } else if (nodeData.isActivity()) {
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
                param.put("listPanel", this.blPlanList);
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
            }
        }

    }

    protected ITreeNode setNodeInsertProp(ITreeNode node, ITreeNode parentNode) {
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        DtoWbsActivity dotwbs = new DtoWbsActivity();
        ITreeNode treeNode = null;
        try {
            DtoUtil.copyProperties(dotwbs, dataBean);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (copyOnlyActivity) {
            dotwbs.setOp(IDto.OP_INSERT);
            Long parentRid = ((DtoWbsActivity) parentNode.getDataBean()).
                             getWbsRid();
            dotwbs.setWbsRid(parentRid);
            treeNode = new DtoWbsTreeNode(dotwbs);
            if (!node.equals(copyNode)) {
                parentNode.addChild(treeNode);
            }
            return treeNode;
        }
        dotwbs.setOp(IDto.OP_INSERT);
        //int temp = dataBean.getWbsRid().intValue() * pasteTimes * 13 + 37;
        Long wbsRid = blPlanList.getWbsRid(dataBean);
        dotwbs.setWbsRid(wbsRid); //new Long(Integer.toString(temp)));
        dotwbs.setParentRid(((DtoWbsActivity) parentNode.getDataBean()).
                            getWbsRid());
        treeNode = new DtoWbsTreeNode(dotwbs);
        if (!node.isLeaf()) {
            List list = node.children();
            for (int i = 0; i < list.size(); i++) {
                setNodeInsertProp((ITreeNode) list.get(i), treeNode);
            }
        }
        if (!node.equals(copyNode)) {
            parentNode.addChild(treeNode);
        }
        return treeNode;
    }
   public void setPasteNodeNormal(ITreeNode root){
        DtoWbsActivity dataBean = (DtoWbsActivity) root.getDataBean();
        dataBean.setOp(IDto.OP_NOCHANGE);
        root.setDataBean(dataBean);
        if (!root.isLeaf()) {
            List list = root.children();
            for (int i = 0; i < list.size(); i++) {
                setNodeNomrmal((ITreeNode) list.get(i));
            }
        }

   }
    public void setNodeNomrmal(ITreeNode root) {
        DtoWbsActivity dataBean = (DtoWbsActivity) root.getDataBean();
        if (root.getParent() != null) {
            //更新父节点
            VwWbsActivityList.updateUpTreeTable(root.getParent(), dataBean);
        }
        dataBean.setOp(IDto.OP_NOCHANGE);
        root.setDataBean(dataBean);
        if (!root.isLeaf()) {
            List list = root.children();
            for (int i = 0; i < list.size(); i++) {
                setNodeNomrmal((ITreeNode) list.get(i));
            }
        }
    }
    //完成paste动作后更新界面，让复制的节点可展开
    public void pasteUpdate(ITreeNode parentNode,ITreeNode newNode){
        blPlanList.getTreeTable().addRow(parentNode, newNode);
        setPasteNodeNormal(newNode);
        blPlanList.fireProcessDataChange();
        refreshGraphic();
//        this.cutBtn.setEnabled(true);
//        this.copyBtn.setEnabled(true);
//        this.pasteBtn.setEnabled(false);

    }
    public JButton getPasteBtn(){
       return this.pasteBtn;
   }
   public JButton getRefreshBtn(){
       return this.refreshBtn;
   }

//返回Save Button的状态，如果是可以保存的就会提示用户保存数据
    public boolean getSaveBtnStatus() {
        if (saveBtn != null) {
            return saveBtn.isEnabled();
        }
        return false;
    }

    public void setSaveBtnsetEnabled(boolean enable) {
        if (saveBtn != null) {
            saveBtn.setEnabled(enable);
        }
    }


    /**
   * 取得父窗口句柄
   * @return Frame
   */
  protected Container getMyParentWindow() {
      java.awt.Container c = this.getParent();

      while (c != null) {
          if ((c instanceof java.awt.Frame) || (c instanceof java.awt.Dialog)) {
              return c;
          }

          c = c.getParent();
      }

      return null;
    }

    public static void main(String[] args) {
        VwBaseLinePlanModify workArea = new VwBaseLinePlanModify();
        Parameter param = new Parameter();
        param.put("inAcntRid", new Long(1));
        workArea.setParameter(param);

        VWWorkArea workArea2 = new VWWorkArea();
        workArea2.addTab("activity", workArea);

        TestPanel.show(workArea);
        workArea.refreshWorkArea();

    }
}
