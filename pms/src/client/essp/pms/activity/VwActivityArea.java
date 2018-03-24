package client.essp.pms.activity;

import java.awt.Dimension;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.activity.code.VwActivityCodeList;
import client.essp.pms.activity.relation.VwRelationPredecessors;
import client.essp.pms.activity.relation.VwRelationSuccessors;
import client.essp.pms.activity.worker.VwActivityWorkerList;
import client.essp.pms.wbs.pbsAndFiles.VwPbsAndFile;
import com.wits.util.Parameter;
import client.essp.cbs.cost.activity.VwActivityCost;
import client.essp.pms.activity.wp.VwWpListActivity;
import client.essp.pms.activity.relation.VwRelationship;


public class VwActivityArea extends VWTDWorkArea {
    /**
     * define control variable
     */
    private boolean refreshFlag = false;

    /**
     * define common data (globe)
     */
    //Long inAcntRid;

/////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    VwWbsDisp wbsDisp;
    VwActivityGeneral activityGeneral;
    VwActivityStatus activityStatus;
    VwPbsAndFile pbs;
    VwActivityWorkerList activityWorkers;
    VwRelationship relation;
    VwWpListActivity activityWp;
    VwActivityCodeList activityCode;
    VwActivityCost activityCost;

    VWWorkArea activityDownArea;
    VWWorkArea wbsDownArea;
    ITreeNode node = null;
    private String entryFunType;

    public VwActivityArea() {
        super(350);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(710, 350));

        activityDownArea = new VWWorkArea();
        activityGeneral = new VwActivityGeneral();
        activityDownArea.addTab("General", activityGeneral);
        activityGeneral.setParentWorkArea(activityDownArea);

        wbsDisp = new VwWbsDisp();

        activityStatus = new VwActivityStatus();
        activityDownArea.addTab("Status", activityStatus);

        activityWorkers = new VwActivityWorkerList();
        activityDownArea.addTab("Workers", activityWorkers);

        relation=new VwRelationship();
        activityDownArea.addTab("Relationship", relation);

        pbs = new VwPbsAndFile();
        activityDownArea.addTab("Pbs", pbs);

        activityWp = new VwWpListActivity();
        activityDownArea.addTab("Wp", activityWp);

        activityCode = new VwActivityCodeList();
        activityDownArea.addTab("Code", activityCode);

        activityCost = new VwActivityCost();
        activityDownArea.addTab("Cost", activityCost);
        this.setTopArea(activityDownArea);

        wbsDownArea = new VWWorkArea();
        wbsDownArea.addTab("General", wbsDisp);

    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        node = (ITreeNode) param.get("ActivityTree");
        this.refreshFlag = true;
        resetUI();
    }

    /////// 段3，获取数据并刷新画面
    //最外层的workArea不需要刷新自己
    public void refreshWorkArea() {
        if (this.refreshFlag == true) {
            Parameter param = new Parameter();
            //param.put("inAcntRid", this.inAcntRid);
            param.put("entryFunType",entryFunType);

            this.refreshFlag = false;
        }
    }

    public void resetUI() {

        DtoWbsActivity dataBean;
        if (node != null) {
            dataBean = (DtoWbsActivity) node.getDataBean();
        } else {
            dataBean = new DtoWbsActivity();
        }
        if (dataBean.isActivity()) {
            this.setTopArea(activityDownArea);
            this.getTopArea().updateUI();
        } else {
            this.setTopArea(wbsDownArea);
            this.getTopArea().updateUI();
        }
        if (node != null) {
            Parameter param = new Parameter();
            param.put(DtoKEY.WBSTREE, node);
            if (this.getTopArea().equals(activityDownArea)) {
                activityGeneral.setParameter(param);
                activityStatus.setParameter(param);
                activityWorkers.setParameter(param);
                relation.setParameter(param);
                activityWp.setParameter(param);
                activityCode.setParameter(param);
                pbs.setParameter(param);
                activityCost.setParameter(param);
            } else {
                wbsDisp.setParameter(param);
            }

            try {
                this.getTopArea().getSelectedWorkArea().refreshWorkArea();
            } catch (Exception e) {

            }
        }
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        if (this.refreshFlag == true) {
            this.getTopArea().getSelectedWorkArea().saveWorkArea();
        }
    }
}
