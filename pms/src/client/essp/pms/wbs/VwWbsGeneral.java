package client.essp.pms.wbs;

import com.wits.util.Parameter;
import client.framework.view.vwcomp.VWUtil;
import c2s.dto.IDto;
import c2s.essp.pms.wbs.DtoKEY;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import c2s.dto.InputInfo;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.framework.model.VMComboBox;
import c2s.dto.ReturnInfo;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.common.comMSG;
import c2s.dto.ITreeNode;
import java.util.List;
import c2s.dto.DtoUtil;
import client.framework.view.event.RowSelectedLostListener;
import java.util.Vector;
import client.essp.common.view.VWWorkArea;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import java.util.Date;
import com.wits.util.IVariantListener;
import com.wits.util.ProcessVariant;
import c2s.essp.pms.wbs.DateCheck;

public class VwWbsGeneral extends VwWbsGeneralBase implements
    RowSelectedLostListener, IVariantListener {
    private static final String actionIdAdd = "FWbsAddAction";
    private static final String actionIdUpdate = "FWbsUpdateAction";
    private static final String actionIdUpdateList = "FWbsUpdateListAction";
    private static final String actionIdGetManagers = "FWbsGetManagers";
    public static final String ACTIONID_WBSACTIVITY_LIST =
        "FWbsWbsActivityListAction";
    private DtoWbsTreeNode treeNode;
    private DtoWbsActivity dataBean;
    protected boolean hasGetManagers;
    private VMComboBox managers;
    private VWWorkArea parentWorkArea;
    private Object listPanel;

    public VwWbsGeneral() {
        super();
        hasGetManagers = false;
        managers = null;
        addUICEvent();
    }

    public void addUICEvent() {
        this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
    }

    public void dataChanged(String varName, Object data) {
        if (varName != null && varName.equals("laborresource")) {
            this.hasGetManagers = false;
            getManagers();
        }
    }

    private boolean checkModified() {
        //如果此条数据是只读的，表示不能修改
        //Modified by:Robin
        if (dataBean.isReadonly()) {
            return false;
        }
        VWUtil.convertUI2Dto(dataBean, this);
        if (dataBean.isDelete() == true) {

            //如果一条数据已经从tree中删除了，就不保存了。
            return false;
        } else {
            return dataBean.isChanged();
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
                            } catch (Exception ex) {
                            }
                            return false;
                        } else {
                            return true;
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
                    return true;
                }

            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void saveWorkArea() {

        if (dataBean != null) {
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
                }

            }
        }
    }

    public void setParentWorkArea(VWWorkArea parent) {
        parentWorkArea = parent;
    }

    private boolean saveProcess() {
        boolean isEnableOtherPanels = false;
        if (dataBean.getOp() != null && dataBean.getOp().equals(IDto.OP_INSERT)) {
            isEnableOtherPanels = true;
        }
        InputInfo inputInfo = new InputInfo();
        DtoWbsActivity dto = dataBean;
        if (dto.getOp() != null &&
            dto.getOp().equals(IDto.OP_INSERT)) {
            inputInfo.setActionId(actionIdAdd);
        } else {
            inputInfo.setActionId(actionIdUpdate);
        }
        inputInfo.setInputObj(DtoKEY.DTO, dataBean);
        ReturnInfo returnInf = this.accessData(inputInfo);
        if (!returnInf.isError()) {
            if (isEnableOtherPanels) {
                if (this.parentWorkArea != null) {
                    parentWorkArea.enableAllTabs();
                }
            }
            dataBean.setOp(DtoWbsActivity.OP_NOCHANGE);
            if (this.parentWorkArea != null) {
                parentWorkArea.enableAllTabs();
            }
            DateCheck.upModifyDate(treeNode,plannedStart.getName(),DateCheck.TYPE_START_DATE);
            DateCheck.upModifyDate(treeNode,plannedFinish.getName(),DateCheck.TYPE_FINISH_DATE);
            VWUtil.clearErrorField(this);
            fireDataChanged();
            fireProcessDataChange();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断输入的预计开始时间和预计结束时间是否与它子的预计开始时间和预计结束时间发生冲突
     * @param date Date 传入的时间（预计开始时间或预计结束时间）
     * @param treeNode ITreeNode 当前选中的结点
     * @param type String 时间检查的类型（预计开始或预计结束）
     * @return boolean 返回检查的结果，正确返回true，错误则返回false
     */
    private boolean checkDateScopeT(Date date, ITreeNode treeNode, String type) {
        List children = treeNode.listAllChildrenByPreOrder();
        Date sDate = null;
        Date fDate = null;
        //循环找出子结点中最早的预计开始时间
        for (int i = 0; i < children.size(); i++) {
            ITreeNode child = (ITreeNode) children.get(i);
            DtoWbsActivity dtoWbs = (DtoWbsActivity) child.getDataBean();
            if (sDate == null) {
                sDate = dtoWbs.getPlannedStart();
            } else if (sDate != null && dtoWbs.getPlannedStart() != null &&
                       dtoWbs.getPlannedStart().before(sDate)) {
                sDate = dtoWbs.getPlannedStart();
            }
        }
        //循环找出子结点中最晚的预计结束时间
        for (int i = 0; i < children.size(); i++) {
            ITreeNode child = (ITreeNode) children.get(i);
            DtoWbsActivity dtoWbs = (DtoWbsActivity) child.getDataBean();
            if (fDate == null) {
                fDate = dtoWbs.getPlannedFinish();
            } else if (fDate != null && dtoWbs.getPlannedFinish() != null &&
                       dtoWbs.getPlannedFinish().after(fDate)) {
                fDate = dtoWbs.getPlannedFinish();
            }
        }
        //如果当前输入的预计开始时间在子结点的预计开始时间之后或在子结点的预计结束时间之后则报错
        if (type.equals("plannedStart")) {
            if ((sDate != null && date.after(sDate)) ||
                (fDate != null && date.after(fDate))) {
                comMSG.dispErrorDialog(
                    "This planned start date collides with the children's.");
                plannedStart.setUICValue(sDate);
                return false;
            }
        }
        //如果当前输入的预计结束时间在子结点的预计结束时间之前或在子结点的预计开始之间之前则报错
        if (type.equals("plannedFinish")) {
            if ((fDate != null && date.before(fDate)) ||
                (sDate != null && date.before(sDate))) {
                comMSG.dispErrorDialog(
                    "This planned finish date collides with the children's.");
                plannedFinish.setUICValue(fDate);
                return false;
            }
        }
        return true;
    }

    private void actionPerformedSave(ActionEvent e) {
        //判断输入的时间是否前后倒置
        if (!checkDate(plannedStart, plannedFinish, "")) {
            return;
        }
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
                    } catch (Exception ex) {
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

    private boolean checkDate(VWJDate startDateComp, VWJDate finishDateComp,
                              String info) {
        Date startDate = (Date) startDateComp.getUICValue();
        Date finishDate = (Date) finishDateComp.getUICValue();

        /**
         * 检查 startDate <= finishDate
         */
        if (startDate != null && finishDate != null) {
            if (finishDate.before(startDate)) {
                startDateComp.setErrorField(true);
                finishDateComp.setErrorField(true);
                comMSG.dispErrorDialog(info +
                                       " error!!! finish date must be after start date.");
                return false;
            }
        }
        return true;
    }

    public void setDataEditable(boolean canModify) {
        name.setEnabled(canModify);
        code.setEnabled(canModify);
        manager.setEnabled(canModify);
        weight.setEnabled(canModify);
        brief.setEnabled(canModify);
        this.completeMethod.setEnabled(canModify);

        //以下部分原为期望时间当有子的时候就不能编辑，现在改为计划时间
        //当WBS下面有Activity的时候也不能编辑，而由Activity的时间来更新WBS的时间
        //去掉了期望时间
        //Modify:Robin
        if (treeNode.getChildCount() == 0 && canModify) {
            if (dataBean.isInsert()) {
                this.plannedStart.setEnabled(true);
                this.plannedFinish.setEnabled(true);
            } else {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(this.ACTIONID_WBSACTIVITY_LIST);
                inputInfo.setInputObj("acntRid", dataBean.getAcntRid());
                inputInfo.setInputObj("wbsRid", dataBean.getWbsRid());
                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    List list = (List) returnInfo.getReturnObj(
                        "wbsActivityList");
                    if (list != null && list.size() > 0) {
                        this.plannedStart.setEnabled(false);
                        this.plannedFinish.setEnabled(false);
                    } else {
                        this.plannedStart.setEnabled(true);
                        this.plannedFinish.setEnabled(true);
                    }
                }
            }
        } else {
            this.plannedStart.setEnabled(false);
            this.plannedFinish.setEnabled(false);
        }

        if (completeRateCompMethod[2].equals(dataBean.getCompleteMethod()) && canModify) {
            completeManualInput.setVisible(true);
            completeLabel_percentSingle.setVisible(true);
        } else {
            completeManualInput.setVisible(false);
            completeLabel_percentSingle.setVisible(false);
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
            comMSG.dispErrorDialog("Please input code");
            return false;
        } else {
            code.setErrorField(false);
        }

//        if (!checkDate(anticipatedStart, anticipatedFinish, "Anticipated date")) {
//            return false;
//        }

        /**
         * 同一个节点下的WBS Code不能重复
         */
        ITreeNode parentNode = treeNode.getParent();
        if (parentNode != null) {
            DtoWbsActivity dto = dataBean;
            for (int i = 0; i < parentNode.getChildCount(); i++) {
                ITreeNode childNode = parentNode.getChildAt(i);
                if (!childNode.equals(treeNode)) {
                    DtoWbsActivity childDto = (DtoWbsActivity) childNode.
                                              getDataBean();
                    if (childDto.getCode() != null &&
                        childDto.getCode().equals(dto.getCode())) {
                        code.setErrorField(true);
                        comMSG.dispErrorDialog(
                            "code repeated in a same wbs node!!! \n" +
                            "Please input a new code,or you can use the default '" +
                            dto.getAutoCode() + "'");
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void setParameter(Parameter parameter) {
        VWUtil.clearErrorField(this);
        listPanel = parameter.get("listPanel");
        treeNode = (DtoWbsTreeNode) parameter.get(DtoKEY.WBSTREE);
        dataBean = (DtoWbsActivity) treeNode.getDataBean();
        updateData();
        if (dataBean.getOp() != null && dataBean.isInsert()) {
            this.parentWorkArea.enableTabOnly("General");
            this.parentWorkArea.setSelected("General");
        } else {
            this.parentWorkArea.enableAllTabs();
        }
        dataBean.setNeedSave(false);
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
        getManagers();
        VWUtil.bindDto2UI(dataBean, this);

//        VWUtil.initComboBox(manager, managers, dataBean.getManager(),
//                            dataBean.getManager());
        this.setDataEditable(!dataBean.isReadonly());
    }

    protected void fireProcessDataChange() {
        ITreeNode root = treeNode;
        while (root != null && root.getParent() != null) {
            root = root.getParent();
        }
        ProcessVariant.set("wbstree", root);
        ProcessVariant.fireDataChange("wbstree", (IVariantListener) listPanel);
    }

}
