package client.essp.pms.activity;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.dto.DtoUtil;
import javax.swing.JButton;
import client.framework.view.vwcomp.VWUtil;
import c2s.dto.ITreeNode;
import com.wits.util.Parameter;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import client.framework.view.vwcomp.VWJDate;
import java.util.List;
import c2s.essp.pms.wbs.DateCheck;
import c2s.dto.IDto;
import client.framework.view.event.RowSelectedLostListener;
import client.framework.model.VMComboBox;
import c2s.dto.DtoComboItem;
import java.util.Vector;
import java.util.Date;
import client.framework.view.common.comMSG;
import com.wits.util.ProcessVariant;
import com.wits.util.IVariantListener;
import com.wits.util.TestPanel;

import c2s.essp.common.calendar.WorkCalendar;
import client.framework.common.Global;

public class VwActivityStatus extends VwActivityStatusBase implements
    RowSelectedLostListener {
    private static final String actionIdAdd = "FWbsActivityAddAction";
    private static final String actionIdUpdate = "FWbsActivityUpdateAction";
    private static final String actionIdGetCompleteMethods =
        "FWbsGetCompleteMethods";

    private ITreeNode treeNode = null;
    private DtoWbsActivity dataBean = null;
    private DtoWbsActivity wbs = null;
    private JButton saveBtn = null;

    private boolean hasGetCompleteMethods;
    private VMComboBox completeMethods;
    private Object listPanel;

    public VwActivityStatus() {
        addUICEvent();
        hasGetCompleteMethods = false;
        completeMethods = null;
    }

    public void addUICEvent() {
        saveBtn = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });

        isActualStart.addItemListener(new
                                      ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                actualStartChange(e);
            }

        });

        isActualFinish.addItemListener(new
                                       ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                actualFinishChange(e);
            }

        });

        completeMethod.addItemListener(new ItemListener() {
            private Object preItem = null;
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    preItem = e.getItem();
                } else if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (preItem != null && preItem.equals(e.getItem())) {

                    } else {
                        completeRate.setText("");
                    }

                    DtoComboItem item = (DtoComboItem) e.getItem();
                    if (item != null && item.getItemValue() != null &&
                        item.getItemValue().equals(DtoWbsActivity.
                        ACTIVITY_COMPLETE_BY_MANUAL)) {
                        completeRate.setEnabled(true);
                    } else {
                        completeRate.setEnabled(false);
                    }

                    if (item != null && item.getItemValue() != null && dataBean != null &&
                        dataBean.getCompleteMethod() != null &&
                        item.getItemValue().equals(dataBean.getCompleteMethod())) {
                        completeRate.setUICValue(dataBean.getCompleteRate());
                    }
                }
            }
        });

    }

    private void actualStartChange(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            actualStart.setEnabled(true);
            if (dataBean.getActualStart() != null) {
                actualStart.setUICValue(dataBean.getActualStart());
            } else {
                actualStart.setUICValue(Global.todayDate);
            }
        } else {
            actualStart.setEnabled(false);
            actualStart.setUICValue(null);
        }
    }

    private void actualFinishChange(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            actualFinish.setEnabled(true);
            if (dataBean.getActualFinish() != null) {
                actualFinish.setUICValue(dataBean.getActualFinish());
            } else {
                actualFinish.setUICValue(Global.todayDate);
            }
        } else {
            actualFinish.setEnabled(false);
            actualFinish.setUICValue(null);
        }
    }

    public boolean processRowSelectedLost(int oldSelectedRow,
                                          Object oldSelectedNode) {
        if (dataBean != null) {
            if (!dataBean.isNeedSave()) {
                return true;
            }

            DtoWbsActivity dto = (DtoWbsActivity) DtoUtil.deepClone(
                dataBean);
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

    private boolean saveProcess() {
        DtoWbsActivity dto = (DtoWbsActivity) dataBean;
        InputInfo inputInfo = new InputInfo();

        if (dto.getActivityRid() == null) {
            inputInfo.setActionId(actionIdAdd);
        } else {
            inputInfo.setActionId(actionIdUpdate);
        }
        inputInfo.setInputObj(DtoKEY.DTO, dataBean);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
            DtoWbsActivity returnDto = (DtoWbsActivity) returnInfo.
                                       getReturnObj(DtoKEY.DTO);
            try {
                DtoUtil.copyProperties(dataBean, returnDto);
                dataBean.setOp(IDto.OP_NOCHANGE);
            } catch (Exception ex) {
            }

            DateCheck.upModifyDate(treeNode, plannedStart.getName(),
                                   DateCheck.TYPE_START_DATE);
            DateCheck.upModifyDate(treeNode, plannedFinish.getName(),
                                   DateCheck.TYPE_FINISH_DATE);
            VWUtil.clearErrorField(this);
            fireDataChanged();
            fireProcessDataChange();
            return true;
        } else {
            return false;
        }
    }

    private void actionPerformedSave(ActionEvent e) {
        //先判断日期是否符合要求

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

    private Boolean getFinish() {
        return new Boolean((String) isActualFinish.getUICValue());
    }

    private Boolean getStart() {
        return new Boolean((String) isActualStart.getUICValue());
    }

    private boolean checkModified() {
        /**
         * 标准check.
         * VWUtil.convertUI2Dto的第三个参数用于过滤掉标准check会出错的属性。
         * 而这些属性将放到补充check部分再作检查
         */
        VWUtil.convertUI2Dto(dataBean, this, new String[] {"start",
                             "finish"});

        /**
         * 补充check.
         *
         */
        if (dataBean.isFinish() == null &&
            getFinish().equals(Boolean.TRUE)) {
            dataBean.setFinish(getFinish());
            dataBean.setOp(IDto.OP_MODIFY);
        } else if (dataBean.isStart() == null &&
                   getStart().equals(Boolean.TRUE)) {
            dataBean.setStart(getStart());
            dataBean.setOp(IDto.OP_MODIFY);
        } else if (dataBean.isStart() != null &&
                   !dataBean.isStart().equals(getStart())) {
            dataBean.setStart(getStart());
            dataBean.setOp(IDto.OP_MODIFY);
        } else if (dataBean.isFinish() != null &&
                   !dataBean.isFinish().equals(getFinish())) {
            dataBean.setFinish(getFinish());
            dataBean.setOp(IDto.OP_MODIFY);
        }

        if (dataBean.isDelete() == true) {

            //如果一条数据已经从tree中删除了，就不保存了。
            return false;
        } else {
            return dataBean.isChanged();
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
                                       " error!!! finish date must after start date.");
                return false;
            }
//            else if (startDate.after(wbs.getPlannedStart())) {
//                startDateComp.setErrorField(true);
//                comMSG.dispErrorDialog(info +
//                                       " error!!! start date must after WBS's start date.");
//                return false;
//            } else if (finishDate.before(wbs.getPlannedFinish())) {
//                finishDateComp.setErrorField(true);
//                comMSG.dispErrorDialog(info +
//                                       " error!!! finish date must late WBS's finish date.");
//                return false;
//            }
            else if ("Planned date".equals(info)) {
                //更新工期
                durationPlan.setUICValue(WorkCalendar.
                                         calculateTimeLimit(startDate,
                    finishDate));
            }
        }
        return true;
    }

    public boolean validateData() {
        if (!checkDate(plannedStart, plannedFinish, "Planned date")) {
            return false;
        }

        if (isActualStart.isSelected()) {
            if (actualStart.getUICValue() == null) {
                comMSG.dispErrorDialog(" Actual Start date required!");
                return false;
            }
        }

        if (isActualFinish.isSelected()) {
            if (actualFinish.getUICValue() == null) {
                comMSG.dispErrorDialog(" Actual Finish date required!");
                return false;
            }
        }

        if (isActualStart.isSelected() && isActualFinish.isSelected()) {
            if (!checkDate(actualStart, actualFinish, "Actual date")) {
                return false;
            }
        }
        return true;
    }

    public void setParameter(Parameter parameter) {
        VWUtil.clearErrorField(this);
        listPanel = parameter.get("listPanel");
        treeNode = (ITreeNode) parameter.get(DtoKEY.WBSTREE);
        if (parameter.get("Wbs") != null) {
            this.wbs = (DtoWbsActivity) parameter.get("Wbs");
        }
        dataBean = (DtoWbsActivity) treeNode.getDataBean();
        getCompleteMethods();
        updateData();
    }

    private void getCompleteMethods() {
        if (!hasGetCompleteMethods) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionIdGetCompleteMethods);
            ReturnInfo returnInfo = this.accessData(inputInfo);
            if (!returnInfo.isError()) {
                List methodList = (List) returnInfo.getReturnObj(
                    DtoKEY.COMPLETE_METHODS);

                if (methodList != null) {
                    completeMethods = new VMComboBox(new Vector(methodList));
                    VMComboBox completeMethodsCopy = (VMComboBox) DtoUtil.
                        deepClone(
                            completeMethods);
                    completeMethod.setModel(completeMethodsCopy);
                    hasGetCompleteMethods = true;
                }

            }
        }
    }

    public void updateData() {
        VWUtil.bindDto2UI(dataBean, this);

        if (dataBean.getCompleteMethod() == null) {
            dataBean.setCompleteMethod("");
        }
        VWUtil.initComboBox(completeMethod, completeMethods,
                            dataBean.getCompleteMethod(),
                            dataBean.getCompleteMethod());
        DtoComboItem item = (DtoComboItem) completeMethod.
                            getSelectedItem();
        if (item != null &&
            item.getItemValue().equals(DtoWbsActivity.
                                       ACTIVITY_COMPLETE_BY_MANUAL)) {
            completeRate.setEnabled(true);
        } else {
            completeRate.setEnabled(false);
        }

        plannedStart.setEnabled(true);
        plannedFinish.setEnabled(true);
        isActualStart.setEnabled(true);
        isActualFinish.setEnabled(true);
        actualStart.setEnabled(true);
        actualFinish.setEnabled(true);
        completeMethod.setEnabled(true);
        durationPlan.setEnabled(false);
//        timeLimitType.setEnabled(false);
        durationActual.setEnabled(false);
        durationRemain.setEnabled(false);
        durationCompleteRate.setEnabled(false);
        saveBtn.setEnabled(true);

        if (dataBean.isFinish() != null &&
            dataBean.isFinish().booleanValue()) {
            actualFinish.setEnabled(true);
        } else {
            actualFinish.setEnabled(false);
        }

        if (dataBean.isStart() != null &&
            dataBean.isStart().booleanValue()) {
            actualStart.setEnabled(true);
        } else {
            actualStart.setEnabled(false);
        }

        if (dataBean.isReadonly()) {
            plannedStart.setEnabled(false);
            plannedFinish.setEnabled(false);
            isActualStart.setEnabled(false);
            isActualFinish.setEnabled(false);
            actualStart.setEnabled(false);
            actualFinish.setEnabled(false);
            completeMethod.setEnabled(false);
            completeRate.setEnabled(false);
            timeLimitType.setEnabled(false);

        }

    }

    protected void fireProcessDataChange() {
        ITreeNode root = treeNode;
        while (root != null && root.getParent() != null) {
            root = root.getParent();
        }
        ProcessVariant.set("activitytree", root);
        ProcessVariant.fireDataChange("activitytree", (IVariantListener) listPanel);
    }


    public static void main(String[] args) {
        VwActivityStatus vwactivitystatus = new VwActivityStatus();
        TestPanel.show(vwactivitystatus);
    }
}
