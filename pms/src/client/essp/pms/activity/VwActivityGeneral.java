package client.essp.pms.activity;

import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.framework.view.vwcomp.VWUtil;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.dto.InputInfo;
import com.wits.util.Parameter;
import c2s.dto.ReturnInfo;
import client.framework.model.VMComboBox;
import javax.swing.JButton;
import client.framework.view.common.comMSG;
import c2s.dto.DtoUtil;
import c2s.dto.ITreeNode;
import client.framework.view.event.RowSelectedLostListener;
import java.util.Vector;
import client.essp.common.view.VWWorkArea;
import c2s.dto.IDto;
import com.wits.util.IVariantListener;
import com.wits.util.ProcessVariant;
import com.wits.util.TestPanel;
import c2s.essp.pms.account.DtoPmsAcnt;

public class VwActivityGeneral extends VwActivityGeneralBase implements
    RowSelectedLostListener, IVariantListener {
    private static final String actionIdAdd = "FWbsActivityAddAction";
    private static final String actionIdUpdate = "FWbsActivityUpdateAction";
    private static final String actionIdGetManagers = "FWbsGetManagers";
    private ITreeNode treeNode = null;
    private DtoWbsActivity dataBean = null;
    private VMComboBox managers;
    protected boolean hasGetManagers = false;
    private JButton saveBtn = null;
    private VWWorkArea parentWorkArea;
    private Object listPanel;

    public VwActivityGeneral() {
        super();
        hasGetManagers = false;
        managers = null;
        addUICEvent();
    }

    public void addUICEvent() {
        saveBtn = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
        inputIsMilestone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedIsMilestone();
            }
        });
        boolean isMilestone = inputIsMilestone.isSelected();
        this.refreshMilestoneProperty(isMilestone);
    }

    public void actionPerformedIsMilestone() {
        //根据是否是MileStone控制界面
        boolean isMilestone = inputIsMilestone.isSelected();
        this.refreshMilestoneProperty(isMilestone);
        dataBean.setMilestone(Boolean.valueOf(isMilestone));
        if (isMilestone) {
            if (dataBean.getMilestoneType() == null ||
                dataBean.getMilestoneType().equals("")) {
                inputType.setSelectedIndex(0);
            }
        }
    }

    public void resetUI() {
        super.resetUI();
        if (dataBean.isMilestone() != null &&
            dataBean.isMilestone().booleanValue()) {
            refreshMilestoneProperty(true);
        } else {
            refreshMilestoneProperty(false);
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId("FAccountLoadAction");
        inputInfo.setInputObj("acntRid", dataBean.getAcntRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            DtoPmsAcnt acc=(DtoPmsAcnt)returnInfo.getReturnObj("DtoAccount");
            //只有项目经理可以设为Milestone, 也可用readOnly控制其可用与否
            if(!acc.isPm() || dataBean.isReadonly()){
                inputIsMilestone.setEnabled(false);
                refreshMilestoneProperty(false);
            }
        }
    }

    public void dataChanged(String varName, Object data) {
        if (varName != null && varName.equals("laborresource")) {
            this.hasGetManagers = false;
            getManagers();
        }
    }

    public boolean processRowSelectedLost(int oldSelectedRow,
                                          Object oldSelectedNode) {
        if (dataBean != null) {
            if (!dataBean.isNeedSave()) {
                return true;
            }

            DtoWbsActivity dto = (DtoWbsActivity) DtoUtil.deepClone(dataBean);
            if (checkModified()) {
                if (confirmSaveWorkArea() == true) {
                    if (validateData() == true) {
                        if (!saveProcess()) {
                            /**
                             * 如果保存失败，则恢复原属性
                             */
                            try {
                                DtoUtil.copyProperties(dataBean, dto);
                            } catch (Exception ex1) {
                            }
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    /**
                     * 如果用户选择不保存，则恢复原属性
                     */
                    try {
                        DtoUtil.copyProperties(dataBean, dto);
                    } catch (Exception ex) {
                    }
                    dataBean.setNeedSave(false);
                }
            }
        }
        return true;
    }

    public void saveWorkArea() {
        if (dataBean != null) {
            if (!dataBean.isNeedSave()) {
                return;
            }

            DtoWbsActivity dto = (DtoWbsActivity) DtoUtil.deepClone(dataBean);
            if (checkModified()) {
                if (confirmSaveWorkArea() == true) {
                    if (validateData() == true) {
                        if (!saveProcess()) {
                            /**
                             * 如果保存失败，则恢复原属性
                             */
                            try {
                                DtoUtil.copyProperties(dataBean, dto);
                            } catch (Exception ex) {
                            }
                        }
                    }
                } else {
                    /**
                     * 如果用户选择不保存，则恢复原属性
                     */
                    try {
                        DtoUtil.copyProperties(dataBean, dto);
                    } catch (Exception ex) {
                    }
                    dataBean.setNeedSave(false);
                }

            }
        }
    }


    public void setParentWorkArea(VWWorkArea parent) {
        parentWorkArea = parent;
    }

    private boolean saveProcess() {
        DtoWbsActivity dto = (DtoWbsActivity) dataBean;
        DtoWbsActivity parentDto = (DtoWbsActivity)this.treeNode.getParent().
                                   getDataBean();

        boolean isEnableOtherPanels = false;

        InputInfo inputInfo = new InputInfo();

        if (dto.getActivityRid() == null || dto.getOp().equals(IDto.OP_INSERT)) {
            inputInfo.setActionId(actionIdAdd);
        } else {
            inputInfo.setActionId(actionIdUpdate);
        }
        inputInfo.setInputObj(DtoKEY.DTO, dataBean);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
            parentDto.setHasActivity(true);

            if (dataBean.getOp() != null &&
                dataBean.getOp().equals(IDto.OP_INSERT)) {
                isEnableOtherPanels = true;
            }

            DtoWbsActivity returnDto = (DtoWbsActivity) returnInfo.
                                       getReturnObj(DtoKEY.DTO);
            try {
                DtoUtil.copyProperties(dataBean, returnDto);
                dataBean.setOp(DtoWbsActivity.OP_NOCHANGE);
            } catch (Exception ex) {
            }
            //comMSG.dispMessageDialog("All change saved");
            if (isEnableOtherPanels) {
                if (this.parentWorkArea != null) {
                    parentWorkArea.enableAllTabs();
                }
            }
            fireDataChanged();
            fireProcessDataChange();
            VWUtil.clearErrorField(this);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkModified() {
        VWUtil.convertUI2Dto(dataBean, this);

        if (dataBean.isDelete() == true) {

            //如果一条数据已经从tree中删除了，就不保存了。
            return false;
        } else {
            return dataBean.isChanged();
        }
    }

    private void actionPerformedSave(ActionEvent e) {
        dataBean.setNeedSave(true);
        DtoWbsActivity dto = (DtoWbsActivity) DtoUtil.deepClone(dataBean);
        if (checkModified()) {
            if (validateData() == true) {
                if (!saveProcess()) {
                    /**
                     * 如果保存失败，则恢复原属性
                     */
                    try {
                        DtoUtil.copyProperties(dataBean, dto);
                    } catch (Exception ex1) {
                    }
                }

            } else {
                /**
                 * 如果验证失败，则恢复原属性
                 */
                try {
                    DtoUtil.copyProperties(dataBean, dto);
                } catch (Exception ex) {
                }
            }

        } else {
            VWUtil.clearErrorField(this);
        }
    }

    /**
     * 检查整棵树中的Activity是否存在重复的code
     * @param node ITreeNode
     * @param dto DtoWbsActivity
     * @return boolean
     */
    private boolean checkCode(ITreeNode node, DtoWbsActivity dto) {
        if (node != null && dto != null) {
            DtoWbsActivity curData = (DtoWbsActivity) node.getDataBean();
            if (curData != null) {
                if (curData.isActivity()) {
                    if (curData.getActivityRid() != null &&
                        dto.getActivityRid() != null &&
                        !curData.getActivityRid().equals(dto.getActivityRid())) {
                        if (curData.getCode() != null &&
                            curData.getCode().equals(dto.getCode())) {
                            return false;
                        }
                    }
                    return true;
                }

                /**
                 * 检查每个子节点中的code
                 */
                List children = node.children();
                for (int i = 0; i < children.size(); i++) {
                    ITreeNode child = (ITreeNode) children.get(i);
                    DtoWbsActivity childDataBean = (DtoWbsActivity) child.
                        getDataBean();
                    if (childDataBean == null) {
                        throw new RuntimeException("childDataBean is null!!!");
                    }
                    if (!checkCode(child, dto)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkCode(DtoWbsActivity dto) {
        ITreeNode root = treeNode;
        while (root != null && root.getParent() != null) {
            root = root.getParent();
        }
        if (checkCode(root, dto)) {
            code.setErrorField(false);
            code.setToolTipText("");
            return true;
        } else {
            code.setErrorField(true);
            code.setToolTipText("Activity ID is error!!! \n" +
                                "It should not repeat with other activities in a same account.\n" +
                                "Or you can use the default: '" +
                                dto.getAutoCode() + "'.");
            comMSG.dispErrorDialog("Activity ID is error!!! \n" +
                                   "It should not repeat with other activities in a same account.\n" +
                                   "Or you can use the default: '" +
                                   dto.getAutoCode() + "'.");
            return false;
        }
    }


    public boolean validateData() {
        if (name.getText().trim().equals("")) {
            name.setErrorField(true);
            comMSG.dispErrorDialog("Please input name");
            return false;
        } else {
            name.setErrorField(false);
        }

        if (code.getText().trim().equals("")) {
            code.setErrorField(true);
            comMSG.dispErrorDialog("Please input id");
            return false;
        } else {
            code.setErrorField(false);
        }

        //Check code scope
        DtoWbsActivity dto = (DtoWbsActivity) dataBean;
        if (!checkCode(dto)) {
            return false;
        }
        return true;
    }

    public void setDataEditable(boolean flag) {
        name.setEnabled(flag);
        code.setEnabled(flag);
        manager.setEnabled(flag);
        weight.setEnabled(flag);
        brief.setEnabled(flag);
    }

    public void setParameter(Parameter parameter) {
        VWUtil.clearErrorField(this);
        if (parameter.get("listPanel") != null) {
            listPanel = parameter.get("listPanel");
        }
        treeNode = (ITreeNode) parameter.get(DtoKEY.WBSTREE);
        dataBean = (DtoWbsActivity) treeNode.getDataBean();
        updateData();
        if (dataBean.getOp() != null && dataBean.isInsert()) {
            this.parentWorkArea.enableTabOnly("General");
            this.parentWorkArea.setSelected("General");
        } else {
            this.parentWorkArea.enableAllTabs();
        }
        this.resetUI();
    }

    private void getManagers() {
        if (!hasGetManagers) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionIdGetManagers);
            inputInfo.setInputObj(DtoKEY.MANAGERS,
                                  ((DtoWbsActivity) dataBean).getAcntRid());
            ReturnInfo returnInfo = this.accessData(inputInfo);
            if (!returnInfo.isError()) {
                List managerList = (List) returnInfo.getReturnObj(
                    DtoKEY.MANAGERS);
                if (managerList != null) {
                    managers = new VMComboBox(new Vector(managerList));
                    VMComboBox managersCopy = (VMComboBox) DtoUtil.deepClone(
                        managers);
                    manager.setModel(managersCopy);
                    hasGetManagers = true;
                }
            }
        }
    }

    public void updateData() {
        if (dataBean.isActivity()) {
            name.setEnabled(true);
            code.setEnabled(true);
            manager.setEnabled(true);
            weight.setEnabled(true);
            brief.setEnabled(true);
            saveBtn.setEnabled(true);
//            if (dataBean.getManager() == null) {
//                VMComboBox model = (VMComboBox) manager.getModel();
//                Object item = model.addElement("", "");
//                model.setSelectedItem(item);
//                dataBean.setManager("");
//            }
            getManagers();
            VWUtil.bindDto2UI(dataBean, this);
//
//            VWUtil.initComboBox(manager, managers, dataBean.getManager(),
//                                dataBean.getManager());
            this.setDataEditable(!dataBean.isReadonly());
        } else {
            name.setText("");
            name.setEnabled(false);
            code.setText("");
            code.setEnabled(false);
            manager.setEnabled(false);
            weight.setText("");
            weight.setEnabled(false);
            brief.setText("");
            brief.setEnabled(false);
            saveBtn.setEnabled(false);

            //getManagers();
            //VWUtil.initComboBox(manager,managers,dataBean.getManager(),dataBean.getManager());
        }
    }

    protected void fireProcessDataChange() {
        ITreeNode root = treeNode;
        while (root != null && root.getParent() != null) {
            root = root.getParent();
        }
        ProcessVariant.set("activitytree", root);
        if (listPanel != null) {
            ProcessVariant.fireDataChange("activitytree",
                                          (IVariantListener) listPanel);
        }
    }

    public static void main(String[] args) {
        VwActivityGeneral vwactivitygeneral = new VwActivityGeneral();
        TestPanel.show(vwactivitygeneral);
    }
}
