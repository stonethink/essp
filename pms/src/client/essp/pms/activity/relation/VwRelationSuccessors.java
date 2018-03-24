package client.essp.pms.activity.relation;

import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoActivity;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWWorkArea;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.common.comMSG;
import c2s.essp.pms.wbs.DtoActivityRelation;

public class VwRelationSuccessors extends VwActivityRelation {
    public static final String ACTIONID_POST_LIST = "FWbsActivitySuccessorListAction";


    /**
     *
     * @param acntRid Long
     * @param activityRid Long
     * @return List
     * @todo Implement this
     *   client.essp.pms.activity.relation.VwActivityRelation method
     */
    public List loadRelation(Long acntRid, Long activityRid) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_POST_LIST);
        inputInfo.setInputObj("acntRid",acntRid );
        inputInfo.setInputObj("preRid", activityRid );
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            List relationList = (List) returnInfo.getReturnObj(
                "successorList");
            return relationList;
        }
        return null;
    }
    /**
     * 检查selectedNode是否能添加到该Activity的后置节点中：
     * 1.判断selectedNode是否已经是Activity的后置节点
     * 2.找到selectedNode所有后置节点，如果Activity包含在其中，则会形成环，报错
     * @param dtoActivity DtoWbsActivity
     * @param selectedNode DtoWbsActivity
     * @return boolean
     */
    public boolean checkAndAddRelation(DtoWbsActivity dtoActivity,
                                       DtoWbsActivity selectedNode) {
           DtoActivityRelation exist = findRelation(dtoActivity.getActivityRid(),
                                       selectedNode.getActivityRid());
           if(exist != null){
               comMSG.dispErrorDialog("selected node has been added as a predecessor!");
               return false;
           }
           List allPosts = this.findAllPosts(selectedNode.getActivityRid());
           if(allPosts != null && allPosts.size() > 0 ){
               for (int i = 0; i < allPosts.size(); i++) {
                   DtoActivityRelation relation = (DtoActivityRelation) allPosts.
                                                  get(i);
                   if (dtoActivity.getActivityRid().longValue() ==
                       relation.getPostActivityId().longValue()) {
                       comMSG.dispErrorDialog("Can not add this successor!");
                       return false;
                   }
               }
           }
           return addRelation(dtoActivity,selectedNode);
    }

    public static void main(String[] args){
        VWWorkArea w1 = new VWWorkArea();

        VwRelationSuccessors post = new VwRelationSuccessors();
        w1.addTab("Succcessors", post);
        DtoActivity dtoActivity = new DtoActivity();
        dtoActivity.setAcntRid(new Long(1));
        dtoActivity.setActivityRid(new Long(10240));
        Parameter param = new Parameter();
        param.put("dtoActivity", dtoActivity);
        post.setParameter(param);
        TestPanel.show(w1);
        post.resetUI();
    }


}
