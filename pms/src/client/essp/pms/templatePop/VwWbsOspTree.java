package client.essp.pms.templatePop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoWbsGuideLine;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import client.essp.cbs.cost.activity.VwActivityCost;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.loginId.VWJLoginId;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.pms.activity.VwActivityGeneral;
import client.essp.pms.activity.VwActivityStatus;
import client.essp.pms.activity.code.VwActivityCodeList;
import client.essp.pms.activity.process.VwActivityProcess;
import client.essp.pms.activity.relation.VwRelationship;
import client.essp.pms.activity.worker.VwActivityWorkerList;
import client.essp.pms.activity.wp.VwWpListActivity;
import client.essp.pms.wbs.VwWbsGeneral;
import client.essp.pms.wbs.WbsNodeViewManager;
import client.essp.pms.wbs.activity.VwWbsActivityList;
import client.essp.pms.wbs.checkpoint.VwCheckPointList;
import client.essp.pms.wbs.code.VwWbsCodeList;
import client.essp.pms.wbs.pbsAndFiles.VwPbsAndFile;
import client.essp.pms.wbs.process.VwWbsProcess;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright:在弹出的窗口里显示osp树 Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class VwWbsOspTree extends VWTreeTableWorkArea  {
    private static final String actionNameList = "FWbsGLOspTreeAction";

    private Object[][] configs = null;
    static final String treeColumnName = "name";
    private Long inAcntRid;
    JButton expandBtn = null;
    JButton detailBtn = null;
    JButton columnBtn = null;

    public VwWbsOspTree() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //拖放事件
        (new WbsTemplateDragSource(getTreeTable())).create();
    }


    protected void jbInit() throws Exception {
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
                  VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE}
                  , {"Duration", "timeLimit", VMColumnConfig.UNEDITABLE,
                  timeLimitDisp, Boolean.TRUE}, {"Planned Start", "plannedStart",
                  VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE}, {"Planned Finish",
                  "plannedFinish",
                  VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE}, {"Actual Start",
                  "actualStart",
                  VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE},
                  {"Actual Finish", "actualFinish",
                  VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE}
                  , {"Weight", "weight", VMColumnConfig.UNEDITABLE,
                  weightDisp, Boolean.TRUE}, {"%Complete", "completeRate",
                  VMColumnConfig.UNEDITABLE, completeRateDisp, Boolean.TRUE}, {"Manager",
                  "manager", VMColumnConfig.UNEDITABLE,
                  new VWJLoginId(), Boolean.TRUE}, {"Complete Method", "completeMethod",
                  VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE}

        };
        super.jbInit(configs, treeColumnName, DtoWbsActivity.class,
                     new WbsNodeViewManager());
    }
    protected void addUICEvent() {

        //detail button
        detailBtn = this.getButtonPanel().addButton("detail.png");
        detailBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //执行弹出窗口，弹出详细的WBS或Activity的卡片框
                popDetailWindow();
            }
        });
        detailBtn.setToolTipText("show detail");
        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(getTreeTable(),
                                         this);
        columnBtn = chooseDisplay.getDisplayButton();
        getButtonPanel().addButton(columnBtn);
        columnBtn.setToolTipText("display");

        expandBtn = this.getButtonPanel().addButton("expand.png");
        expandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getTreeTable().expandsRow();
            }
        });
        expandBtn.setToolTipText("expand");
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });

    }

    //页面刷新－－－－－
    protected void resetUI() {
        reloadData();
        getTreeTable().expandRow(0);
    }

//通过一个得到的accountId,从数据库得到其一个DtoWbsTreeNode对象,并将作为根节点放到TreeTable中
    public void reloadData() {
        getOsp();
        if (inAcntRid == null) {
            inAcntRid = new Long(0);
        }
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionNameList);

        inputInfo.setInputObj(DtoKEY.ACCOUNT_ID, inAcntRid.longValue() + "");

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            DtoWbsTreeNode root = (DtoWbsTreeNode) returnInfo.getReturnObj(
                DtoKEY.WBSTREE);
            root.setActivityTree(true);

            this.getTreeTable().setRoot(root);

        }
    }

    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }

    public void updateTreeTable() {
        getTreeTable().refreshTree();
    }

    /**
     * 查找OSP对应的Rid
     */
    public void getOsp() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId("FWbsGLOspListAction");
        ReturnInfo returnInfo = this.accessData(inputInfo);
        inAcntRid = (Long) returnInfo.getReturnObj("ospRid");
    }

    public void popDetailWindow() {
        ITreeNode node = getTreeTable().getSelectedNode();
        if (node != null) {
            DtoWbsActivity nodeData = (DtoWbsActivity) node.getDataBean();
            nodeData.setReadonly(true);
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
                param.put("listPanel", this);
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
                param.put("listPanel", this);
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


}
