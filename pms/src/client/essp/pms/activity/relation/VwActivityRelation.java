package client.essp.pms.activity.relation;

import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoActivityRelation;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWTableWorkArea;
import client.essp.pms.activity.AbstractActivityEvent;
import client.essp.pms.activity.VwActivityPopup;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWTreeTablePopupEvent;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import c2s.essp.pms.wbs.DtoKEY;
import java.awt.Dimension;

public abstract class VwActivityRelation extends VWTableWorkArea implements
    IVWTreeTablePopupEvent {
    public static final String ACTIONID_RELATION_ADD =
        "FWbsActivityRelationAddAction";
    public static final String ACTIONID_RELATION_LOAD =
        "FWbsActivityRelationLoadAction";
    public static final String ACTIONID_RELATION_DELETE =
        "FWbsActivityRelationDeleteAction";
    public static final String ACTIONID_RELATION_SAVE =
        "FWbsActivityRelationSaveAction";
    protected List relationList; //当前Activity的所有前置或后置节点，
    private DtoWbsActivity dtoActivity; //当前Activity
    private DtoWbsActivity selectedNode; //根据Popup所选择的节点
    private List accountAllRelation; //当前项目所有的关联

    private VwActivityPopup activityPopup;
    private VWJComboBox inputType = new VWJComboBox();

    private boolean isParameterValid = true;
    private boolean isSaveOk = true;

    public VwActivityRelation() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        inputType.setVMComboBox(
            VMComboBox.toVMComboBox(DtoActivityRelation.RELATION_TYPE_NAME,
                                    DtoActivityRelation.RELATION_TYPE_VALUE));
        //标题栏位
        Object[][] configs = new Object[][] { {"Activity Code", "code",
                             VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Activity Name", "name",
                             VMColumnConfig.UNEDITABLE, new VWJText()}, {"WBS",
                             "wbsName", VMColumnConfig.UNEDITABLE,
                             new VWJText()}, {"%Complete", "completeRate",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.TRUE}, {"Manager", "manager",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.TRUE}, {"Planned Start", "plannedStart",
                             VMColumnConfig.UNEDITABLE, new VWJDate()},
                             {"Planned Finish", "plannedFinish",
                             VMColumnConfig.UNEDITABLE, new VWJDate()},
                             {"Actual Start", "actualStart",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.TRUE}, {"Actual Finish", "actualFinish",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.TRUE}, {"Type", "startFinishType",
                             VMColumnConfig.EDITABLE,
                             inputType}
        };
        try {
            super.jbInit(configs, DtoActivityRelation.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        addUICEvent();
    }

    /**
     * 添加事件
     */
    public void addUICEvent() {
        this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (dtoActivity != null && !dtoActivity.isReadonly()) {
                    actionPerformedAdd();
                }
            }
        });
        this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (dtoActivity != null && !dtoActivity.isReadonly()) {
                    actionPerformedDel();
                }
            }
        });
        this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (dtoActivity != null && !dtoActivity.isReadonly()) {
                    actionPerformedSave();
                }
            }
        });
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (dtoActivity != null && !dtoActivity.isReadonly()) {
                    resetUI();
                }
            }
        });

    }

    /**
     * 新增事件
     */
    public void actionPerformedAdd() {
        if (activityPopup == null) {
            activityPopup = new VwActivityPopup(this.getParentWindow(), this);
        }
        activityPopup.setPreferredSize(new Dimension(500,600));
        activityPopup.pack();
        activityPopup.setVisible(true);
    }

    /**
     * 删除事件
     */
    public void actionPerformedDel() {
        DtoActivityRelation relation = (DtoActivityRelation)this.getTable().
                                       getSelectedData();
        int f = comMSG.dispConfirmDialog("Confirm to delete selected record!");
        if (f == Constant.OK) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.ACTIONID_RELATION_DELETE);
            inputInfo.setInputObj("relation", relation);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (!returnInfo.isError()) {
                resetUI();
            }
        }
    }

    /**
     * 保存事件
     */
    public void actionPerformedSave() {
        if (checkModified()) {
            saveData();
        }
    }

    private boolean checkModified() {
        if (relationList == null || relationList.size() <= 0) {
            return false;
        }
        for (Iterator it = this.relationList.iterator();
                           it.hasNext(); ) {
            DtoActivityRelation dtoActivityRelation = (DtoActivityRelation) it.
                next();
            if (dtoActivityRelation.isChanged()) {
                return true;
            }
        }
        return false;
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_RELATION_SAVE);
        inputInfo.setInputObj("relationList", this.relationList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            resetUI();
            return true;
        } else {
            return false;
        }
    }

    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea()) {
                isSaveOk = saveData();
            } else {
                isSaveOk = true;
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean isSaveOk() {
        return this.isSaveOk;
    }

    /**
     * 在子类中分别查找该Activity的前置和后置
     * @param acntRid Long
     * @param activityRid Long
     * @return List
     */
    public abstract List loadRelation(Long acntRid, Long activityRid);

    /**
     * 在子类中分别检查所选择的Activity的前置或后置是否有效
     * 1.不重复
     * 2.不形成循环
     * 如果有效，分清楚前后置节点，调用addRelation()添加新的关系
     * @param acntRid Long
     * @param activityRid Long
     * @return boolean
     */
    public abstract boolean checkAndAddRelation(DtoWbsActivity dtoActivity,
                                                DtoWbsActivity selectedNode);

    /**
     * 增加preNode和postNode之间的关联
     * @param preNode DtoWbsActivity
     * @param postNode DtoWbsActivity
     */
    protected boolean addRelation(DtoWbsActivity preNode,
                                  DtoWbsActivity postNode) {
        if (dtoActivity == null) {
            return true;
        }
        DtoActivityRelation relation = new DtoActivityRelation();
        relation.setAcntRid(dtoActivity.getAcntRid());
        relation.setActivityId(preNode.getActivityRid());
        relation.setPostActivityId(postNode.getActivityRid());
        relation.setStartFinishType(DtoActivityRelation.RELATION_TYPE_VALUE[0]);
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_RELATION_ADD);
        inputInfo.setInputObj("relation", relation);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            resetUI();
            return true;
        }
        return false;
    }

    /**
     * 装载该项目的所有Activity Relation
     */
    protected void loadRelationsByAccount() {
        if (dtoActivity == null) {
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_RELATION_LOAD);
        inputInfo.setInputObj("acntRid", dtoActivity.getAcntRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            accountAllRelation = (List) returnInfo.getReturnObj(
                "accountAllRelation");
        }
    }

    /**
     * 找到某节点的所有的后置节点，包括后置节点的后置节点
     * @return List
     */
    protected List findAllPosts(Long preRid) {
        List result = new ArrayList();
        if (accountAllRelation == null) {
            return result;
        }
        List preRids = new ArrayList();
        preRids.add(preRid);
        List posts = findPostRelations(preRids, accountAllRelation);
        while (posts != null && posts.size() > 0) {
            result.addAll(posts);
            preRids.clear();
            for (int i = 0; i < posts.size(); i++) {
                DtoActivityRelation relation = (DtoActivityRelation) posts.get(
                    i);
                preRids.add(relation.getPostActivityId());
            }
            posts = findPostRelations(preRids, accountAllRelation);
            //检查posts中的关系在result中是否已经有了，若有了则表示数据库中的数据形成了一个环
            if (checkCircle(posts, result)) {
                comMSG.dispErrorDialog(
                    "There is a circle in activities relation of account:[" +
                    dtoActivity.getAcntRid() + "]");
                throw new RuntimeException(
                    "There is a circle in activities relation of account:[" +
                    dtoActivity.getAcntRid() + "]");
            }
        }
        return result;
    }

    /**
     * 找到某些节点的第一层的后置节点
     * @param preRid Long
     * @param allRelations List
     * @return List
     */
    private List findPostRelations(List preRids, List allRelations) {
        if (preRids == null || allRelations == null) {
            return null;
        }
        List result = new ArrayList();
        for (int i = 0; i < allRelations.size(); i++) {
            DtoActivityRelation relation = (DtoActivityRelation) allRelations.
                                           get(i);
            if (preRids.contains(relation.getActivityId())) {
                result.add(relation);
            }
        }
        return result;
    }

    /**
     * 检查是否存在环
     * @param posts List
     * @param result List
     */
    private boolean checkCircle(List posts, List result) {
        if (posts == null || posts.size() <= 0 || result == null ||
            result.size() <= 0) {
            return false;
        }
        for (int i = 0; i < posts.size(); i++) {
            DtoActivityRelation post = (DtoActivityRelation) posts.get(i);
            for (int j = 0; j < result.size(); j++) {
                DtoActivityRelation dto = (DtoActivityRelation) result.get(j);
                if (dto.getActivityId().longValue() ==
                    post.getActivityId().longValue() &&
                    dto.getPostActivityId().longValue() ==
                    post.getPostActivityId().longValue()) {
                    return true;
                }
                //comMSG.dispErrorDialog("There is a circle in activities relation of account:["+dto.getAcntRid()+"]");
                //throw new RuntimeException("There is a circle in activities relation of account:["+dto.getAcntRid()+"]");
            }
        }
        return false;
    }

    /**
     * 查找指定前置节点和后置节点的关系
     * @param preRid Long
     * @param postRid Long
     * @return DtoActivityRelation
     */
    protected DtoActivityRelation findRelation(Long preRid, Long postRid) {
        if (preRid == null || postRid == null) {
            return null;
        }
        for (int i = 0; i < relationList.size(); i++) {
            DtoActivityRelation relation = (DtoActivityRelation) relationList.
                                           get(i);
            if (preRid.longValue() == relation.getActivityId().longValue() &&
                postRid.longValue() == relation.getPostActivityId().longValue()) {
                return relation;
            }
        }
        return null;
    }

    public void resetUI() {
        setButtonVisible();
        if (dtoActivity == null) {
            return;
        }
        relationList = loadRelation(dtoActivity.getAcntRid(),
                                    dtoActivity.getActivityRid());
        if (relationList == null) {
            relationList = new ArrayList();
        }
        this.getTable().setRows(relationList);
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        ITreeNode treeNode = (ITreeNode) param.get(DtoKEY.WBSTREE);
        this.dtoActivity = (DtoWbsActivity) treeNode.getDataBean();
        //dtoActivity.setReadonly(false);
        if (dtoActivity == null || dtoActivity.isReadonly()) {
            isParameterValid = false;
        } else {
            isParameterValid = true;
        }

    }

    private void setButtonVisible() {
        this.getButtonPanel().setVisible(isParameterValid);
        this.getTable().setEnabled(isParameterValid);
    }

/////////////////Activity popup Event/////////////////////////////////
    public boolean onClickOK(ActionEvent e) {
        if (selectedNode == null) {
            return true;
        }
        if (!selectedNode.isActivity()) {
            comMSG.dispErrorDialog("The selected node is not a activity!");
            return false;
        }
        if (dtoActivity.getAcntRid().longValue() ==
            selectedNode.getAcntRid().longValue() &&
            dtoActivity.getActivityRid().longValue() ==
            selectedNode.getActivityRid().longValue()) {
            comMSG.dispErrorDialog("Can not add the current activity!!");
            return false;
        }
        loadRelationsByAccount();
        return checkAndAddRelation(dtoActivity, selectedNode);
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public Parameter getParameter() {
        Parameter param = new Parameter();
        param.put(AbstractActivityEvent.ACCOUNT_RID, dtoActivity.getAcntRid());
        return param;
    }

    public void onSelectedNode(ITreeNode selectedNode) {
        if (selectedNode == null) {
            return;
        }
        this.selectedNode = (DtoWbsActivity) selectedNode.getDataBean();
    }

    public PopupMenu getPopupMenu() {
        return null;
    }
}
