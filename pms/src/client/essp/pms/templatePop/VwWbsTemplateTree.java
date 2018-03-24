package client.essp.pms.templatePop;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import c2s.dto.DtoUtil;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import client.essp.cbs.cost.activity.VwActivityCost;
import client.essp.common.loginId.VWJLoginId;
import client.essp.common.view.VWAccountLabel;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.activity.VwActivityGeneral;
import client.essp.pms.activity.VwActivityStatus;
import client.essp.pms.activity.code.VwActivityCodeList;
import client.essp.pms.activity.process.VwActivityProcess;
import client.essp.pms.activity.relation.VwRelationship;
import client.essp.pms.activity.worker.VwActivityWorkerList;
import client.essp.pms.wbs.VwWbsGeneral;
import client.essp.pms.wbs.WbsNodeViewManager;
import client.essp.pms.wbs.checkpoint.VwCheckPointList;
import client.essp.pms.wbs.code.VwWbsCodeList;
import client.essp.pms.wbs.pbsAndFiles.VwPbsAndFile;
import client.essp.pms.wbs.process.VwWbsProcess;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.IVariantListener;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

/**
 * <p>Title: </p>
 *
 * <p>Description:显示被选择的template </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class VwWbsTemplateTree extends VWTreeTableWorkArea implements IVariantListener {
    private static final String actionNameList = "FWbsGLWbsTreeAction";

    private Object[][] configs = null;
    static final String treeColumnName = "name";
    private Long inAcntRid;

    VWAccountLabel accountLabel = null;

    public VwWbsTemplateTree() {
        try {
            jbInit();
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

//        JTableHeader header = this.getTreeTable().getTableHeader();
//        header.setVisible(false);
//        TableColumnModel tcModel = header.getColumnModel();
//        tcModel.getColumn(0).setPreferredWidth(100);

    }


    //页面刷新－－－－－
    protected void resetUI() {

        reloadData();
    }


    public void setParameter(Parameter param) {
        super.setParameter(param);
        inAcntRid = (Long) param.get("inAcntRid");
    }

//通过一个得到的accountId,从数据库得到其一个DtoWbsTreeNode对象,并将作为根节点放到TreeTable中
    public void reloadData() {
        if (inAcntRid == null) {
            return;
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
    public void dataChanged(String name, Object value) {
       if (value != null) {
           DtoWbsTreeNode root = (DtoWbsTreeNode) DtoUtil.deepClone(value);
           root.setActivityTree(true);
           this.getTreeTable().setRoot(root);
           this.getTreeTable().updateUI();
        }
   }




    public static void main(String args[]) {

        VWWorkArea workArea = new VWWorkArea();
        VwWbsTemplateTree tree = new VwWbsTemplateTree();
        workArea.addTab("Template", tree);
        TestPanel.show(workArea);

        workArea.refreshWorkArea();
    }
}
