package client.essp.pms.activity.relation;

import java.util.List;
import client.essp.common.view.VWWorkArea;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import c2s.essp.pms.wbs.DtoActivity;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoActivityRelation;
import client.framework.view.common.comMSG;

public class VwRelationPredecessors extends VwActivityRelation {
    public static final String ACTIONID_PRE_LIST =
        "FWbsActivityPredecessorListAction";

    /**
     * 获得Activity的所有前置节点
     * @param acntRid Long
     * @param activityRid Long
     * @return List
     * @todo Implement this
     *   client.essp.pms.activity.relation.VwActivityRelation method
     */
    public List loadRelation(Long acntRid, Long activityRid) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_PRE_LIST);
        inputInfo.setInputObj("acntRid", acntRid);
        inputInfo.setInputObj("postRid", activityRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            List relationList = (List) returnInfo.getReturnObj(
                "predecessorList");
            return relationList;
        }
        return null;
    }
    /**
     * 检查selectedNode是否能添加到该Activity的前置节点中：
     * 1.判断selectedNode是否已经是Activity的前置节点
     * 2.找到Activity所有后置节点，如果selectedNode包含在其中，则会形成环，报错
     * @param dtoActivity DtoWbsActivity
     * @param selectedNode DtoWbsActivity
     * @return boolean
     */
    public boolean checkAndAddRelation(DtoWbsActivity dtoActivity,
                                       DtoWbsActivity selectedNode) {
        DtoActivityRelation exist = findRelation(selectedNode.getActivityRid(),
                                    dtoActivity.getActivityRid());
        if(exist != null){
            comMSG.dispErrorDialog("selected node has been added as a predecessor!");
            return false;
        }
        List allPosts = this.findAllPosts(dtoActivity.getActivityRid());
        if(allPosts != null && allPosts.size() > 0 ){
            for (int i = 0; i < allPosts.size(); i++) {
                DtoActivityRelation relation = (DtoActivityRelation) allPosts.
                                               get(i);
                if (selectedNode.getActivityRid().longValue() ==
                    relation.getPostActivityId().longValue()) {
                    comMSG.dispErrorDialog("Can not add this predecessor!");
                    return false;
                }
            }
        }
        return addRelation(selectedNode,dtoActivity);
    }


    public static void main(String[] args) {
        VWWorkArea w1 = new VWWorkArea();

        VwRelationPredecessors pre = new VwRelationPredecessors();
        w1.addTab("Predecessors", pre);
        DtoActivity dtoActivity = new DtoActivity();
        dtoActivity.setAcntRid(new Long(1));
        dtoActivity.setActivityRid(new Long(10240));
        Parameter param = new Parameter();
        param.put("dtoActivity", dtoActivity);
        pre.setParameter(param);
        TestPanel.show(w1);
        pre.resetUI();
    }
}
