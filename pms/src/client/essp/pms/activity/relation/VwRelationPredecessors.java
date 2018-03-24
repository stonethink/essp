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
     * ���Activity������ǰ�ýڵ�
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
     * ���selectedNode�Ƿ�����ӵ���Activity��ǰ�ýڵ��У�
     * 1.�ж�selectedNode�Ƿ��Ѿ���Activity��ǰ�ýڵ�
     * 2.�ҵ�Activity���к��ýڵ㣬���selectedNode���������У�����γɻ�������
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
