package client.essp.pms.quality.goal;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.quality.goal.DtoQualityGoal;
import c2s.essp.pms.quality.goal.DtoReleaseRecord;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import java.awt.event.*;
import java.util.*;
import client.framework.view.vwcomp.VWJTextArea;
import client.framework.view.vwcomp.VWJReal;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwQualityGoalGeneral extends VWGeneralWorkArea {

    static final String actionIdUpdate = "FQuQualityGoalUpdateAction";


    private Long acntRid;
    private DtoQualityGoal dataBean;
    private List qualityGoalReleaseList;
    VwQualityGoalReleaseList vwQualityGoalReleaseList;
    private VWWorkArea parentWorkArea;
    private boolean refreshFlag = false;

    long actualSize = 0;
    long actualDefects = 0;
    double actualRate = 0;


    VWJLabel lblProduction = new VWJLabel();
    VWJLabel lblUnit = new VWJLabel();
    VWJLabel lblPlanRate = new VWJLabel();
    VWJLabel lblActualRate = new VWJLabel();
    VWJLabel lblActualSize = new VWJLabel();
    VWJLabel lblActualDefects = new VWJLabel();
    VWJLabel lblSumSize = new VWJLabel();
    VWJLabel lblSumDefects = new VWJLabel();
    VWJLabel lblSeq = new VWJLabel();
    VWJLabel lblDescription = new VWJLabel();

    VWJText textProduction = new VWJText();
    VWJText textUnit = new VWJText();
    VWJReal textPlanRate = new VWJReal();
    VWJReal textActualRate = new VWJReal();
    VWJReal textActualSize = new VWJReal();
    VWJReal textActualDefects = new VWJReal();
    VWJReal textSeq = new VWJReal();
    VWJTextArea textDescription = new VWJTextArea();

    VWJCheckBox jCheckBoxIsSumSize = new VWJCheckBox();
    VWJCheckBox jCheckBoxIsSumdefects = new VWJCheckBox();

    JButton btnSave = null;


    public VwQualityGoalGeneral() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        addUICEvent();
    }

    /**
     * addUICEvent
     */
    public void addUICEvent() {
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
        btnSave.setToolTipText("Save Data");

    }

    private void actionPerformedSave(ActionEvent e) {

        dataBean.setNeedSave(true);
        DtoQualityGoal dto = (DtoQualityGoal) DtoUtil.deepClone(dataBean);
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
                this.refreshWorkArea();
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

    private boolean checkModified() {
        //如果此条数据是只读的，表示不能修改
        if (dataBean.isReadonly()) {
            return false;
        }
        VWUtil.convertUI2Dto(dataBean, this);

        //should set isSumSize manually by isSumSizeBool
//        dataBean.setIsSumSize();
        //should set isSumDefect manually by isSumDefectBool
//        dataBean.setIsSumDefect();

        if (dataBean.isDelete() == true) {
            return false;
        } else {
            return dataBean.isChanged();
        }
    }

    public boolean validateData() {
        if (textProduction.getText().trim().equals("")) {
            textProduction.setErrorField(true);
            comMSG.dispErrorDialog("Please input production");
            return false;
        } else {
            textProduction.setErrorField(false);
        }
        return true;
    }

    private boolean saveProcess() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdUpdate);

        inputInfo.setInputObj(DtoKey.DTO, dataBean);
        ReturnInfo returnInf = this.accessData(inputInfo);
        if (!returnInf.isError()) {
            this.fireDataChanged();
            dataBean.setOp(DtoQualityGoal.OP_NOCHANGE);
            if (this.parentWorkArea != null) {
                parentWorkArea.enableAllTabs();
                vwQualityGoalReleaseList.fireNeedRefresh();
            }
            VWUtil.clearErrorField(this);
            return true;
        } else {
            return false;
        }
    }


    //建议在画好页面后再另添代码
    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(700, 280));

        lblProduction.setText("Production");
        lblProduction.setBounds(new Rectangle(35, 24, 108, 20));
        textProduction.setBounds(new Rectangle(149, 24, 212, 20));

        lblUnit.setText("Unit");
        lblUnit.setBounds(new Rectangle(402, 24, 113, 20));
        textUnit.setBounds(new Rectangle(524, 24, 212, 20));

        lblPlanRate.setText("Plan  Rate");
        lblPlanRate.setBounds(new Rectangle(35, 49, 108, 20));
        textPlanRate.setBounds(new Rectangle(149, 49, 212, 20));

        this.lblActualRate.setText("Actual Rate");
        lblActualRate.setBounds(new Rectangle(402, 49, 113, 20));
        textActualRate.setBounds(new Rectangle(524, 49, 212, 20));

        lblActualSize.setText("Actual Size");
        lblActualSize.setBounds(new Rectangle(35, 74, 108, 20));
        textActualSize.setBounds(new Rectangle(149, 74, 212, 20));

        lblActualDefects.setText("Actual Defects");
        lblActualDefects.setBounds(new Rectangle(402, 74, 113, 20));
        textActualDefects.setBounds(new Rectangle(524, 74, 212, 20));

        lblSumSize.setText("Sum Size");
        lblSumSize.setBounds(new Rectangle(35, 99, 108, 20));
        jCheckBoxIsSumSize.setBounds(new Rectangle(149, 99, 212, 20));

        lblSumDefects.setText("Sum Defects");
        lblSumDefects.setBounds(new Rectangle(402, 99, 113, 20));
        jCheckBoxIsSumdefects.setBounds(new Rectangle(524, 99, 212, 20));

        lblSeq.setText("Seq");
        lblSeq.setBounds(new Rectangle(35, 124, 108, 20));
        textSeq.setBounds(new Rectangle(149, 124, 212, 20));
        textSeq.setMaxInputDecimalDigit(0);

        lblDescription.setText("Description");
        lblDescription.setBounds(new Rectangle(35, 149, 108, 20));
        textDescription.setBounds(new Rectangle(149, 149, 585, 60));

        textProduction.setName("production");
        this.textUnit.setName("unit");
        this.textPlanRate.setName("planRate");
        this.textActualRate.setName("actualRate");
        this.textActualSize.setName("actualSize");
        this.textActualDefects.setName("actualDefect");
        this.jCheckBoxIsSumSize.setName("isSumSize");
        this.jCheckBoxIsSumdefects.setName("isSumDefect");
        this.textSeq.setName("seq");
        this.textDescription.setName("description");

        this.textUnit.setEnabled(false);
        this.textPlanRate.setEnabled(false);
        this.textActualSize.setEnabled(false);
        this.textActualRate.setEnabled(false);
        this.textActualDefects.setEnabled(false);

        this.textPlanRate.setMaxInputDecimalDigit(2);
        this.textActualRate.setMaxInputDecimalDigit(2);
        this.textActualSize.setMaxInputDecimalDigit(0);
        this.textActualDefects.setMaxInputDecimalDigit(0);

        this.add(this.jCheckBoxIsSumdefects);
        this.add(this.jCheckBoxIsSumSize);
        this.add(this.lblActualDefects);
        this.add(this.lblActualRate);
        this.add(this.lblActualSize);
        this.add(this.lblDescription);
        this.add(this.lblPlanRate);
        this.add(this.lblProduction);
        this.add(this.lblSeq);

        this.add(this.lblSumDefects);
        this.add(this.lblSumSize);
        this.add(this.lblUnit);
        this.add(this.textActualDefects);
        this.add(this.textActualRate);
        this.add(this.textActualSize);
        this.add(this.textDescription);
        this.add(this.textPlanRate);
        this.add(this.textProduction);
        this.add(this.textSeq);
        this.add(this.textUnit);

        jCheckBoxIsSumSize.getModel().addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                processIsSumsize();
            }
        });
        jCheckBoxIsSumdefects.getModel().addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                processIsSumDefects();
            }
        });
    }

    /**
     * process mouse click event
     * set textActuralSize as sumSize if  jCheckBoxIsSumSize is selected
     * or set textActuralSize as the last releaseRecord if  jCheckBoxIsSumSize is not selected
     */
    public void processIsSumsize() {
        //should set actualSize = 0,
        actualSize = 0;

        //if there are  ReleaseRecords with the current dtoQualityGoal in QualityGoalList
        if (this.qualityGoalReleaseList.size() > 0) {
            if (this.jCheckBoxIsSumSize.isSelected()) {
                for (int i = 0; i < qualityGoalReleaseList.size(); i++) {
                    actualSize +=
                        ((DtoReleaseRecord) qualityGoalReleaseList.get(i)).
                        getSize().longValue();
                }
                this.textActualSize.setUICValue(new Long(actualSize));

                System.out.println("isSumSize");
            } else {
                //get the last releaseRecord
                DtoReleaseRecord dto = (DtoReleaseRecord)
                                       qualityGoalReleaseList.get(
                                           qualityGoalReleaseList.size() -
                                           1);
                actualSize = dto.getSize().longValue();

                this.textActualSize.setUICValue(new Long(actualSize));
                System.out.println("not isSumSize");
            }
            actualRate = actualDefects / ((double) actualSize);
            this.textActualRate.setUICValue(new Double(actualRate));
        } else {
            this.textActualSize.setUICValue(new Long(0));
            this.textActualRate.setUICValue(new Double(0));
        }
    }

    /**
     * process mouse click event
     * set textActuralDefects as sumDefects if  jCheckBoxIsSumDefects is selected
     * or set textActuralDefects as the last releaseRecord if  jCheckBoxIsSumDefects is not selected
     */

    private void processIsSumDefects() {
        this.actualDefects = 0;
        if (this.qualityGoalReleaseList.size() > 0) {
            if (this.jCheckBoxIsSumdefects.isSelected()) {
                for (int i = 0; i < qualityGoalReleaseList.size(); i++) {
                    actualDefects +=
                        ((DtoReleaseRecord) qualityGoalReleaseList.get(i)).
                        getDefects().longValue();
                }
                this.textActualDefects.setUICValue(new Long(actualDefects));

                System.out.println("IsSumDefects");
            } else {
                DtoReleaseRecord dto = (DtoReleaseRecord)
                                       qualityGoalReleaseList.get(
                                           qualityGoalReleaseList.size() -
                                           1);
                actualDefects = dto.getDefects().longValue();

                this.textActualDefects.setUICValue(new Long(actualDefects));

                System.out.println("not IsSumDefects");
            }

            actualRate = actualDefects / ((double) actualSize);
            this.textActualRate.setUICValue(new Double(actualRate));

        } else {
            this.textActualDefects.setUICValue(new Long(0));
            this.textActualRate.setUICValue(new Double(0));
        }
    }

    public void setParentWorkArea(VWWorkArea parent) {
        parentWorkArea = parent;
    }

    public void reCaculate() {
        long actualSize = 0;
        long actualDefects = 0;
        double actualRate = 0;
        if (dataBean == null || qualityGoalReleaseList == null
            || qualityGoalReleaseList.size() < 1) {
            return;
        }
        if (dataBean.getIsSumSize().booleanValue()) {

            for (int i = 0; i < qualityGoalReleaseList.size(); i++) {
                actualSize +=
                    ((DtoReleaseRecord) qualityGoalReleaseList.get(i)).
                    getSize().longValue();
            }
            dataBean.setActualSize(new Long(actualSize));
        } else
        //set acturalSize as the last element'size
        {
            //get the last element in List qualityGoalReleaseList
            DtoReleaseRecord dto = (DtoReleaseRecord)
                                   qualityGoalReleaseList.get(
                                       qualityGoalReleaseList.size() -
                                       1);
            actualSize = dto.getSize().longValue();
            dataBean.setActualSize(new Long(actualSize));
        }
        //
        if (dataBean.getIsSumDefect().booleanValue()) {
            for (int i = 0; i < qualityGoalReleaseList.size(); i++) {
                actualDefects +=
                    ((DtoReleaseRecord) qualityGoalReleaseList.get(i)).
                    getDefects().longValue();
            }
            dataBean.setActualDefect(new Long(actualDefects));
        } else {
            DtoReleaseRecord dto = (DtoReleaseRecord)
                                   qualityGoalReleaseList.get(
                                       qualityGoalReleaseList.size() -
                                       1);
            actualDefects = dto.getDefects().longValue();
            dataBean.setActualDefect(new Long(actualDefects));
        }
        actualRate = actualDefects / ((double) actualSize);
        dataBean.setActualRate(new Double(actualRate));
    }

    public void setParameter(Parameter parameter) {
        super.setParameter(parameter);
        //should clear the value first
        actualSize = 0;
        actualDefects = 0;
        actualRate = 0;
        dataBean = (DtoQualityGoal) parameter.get(DtoQualityGoal.
                                                  DTO_QUALITY_GOAL);
        //get the releaseRecordList
        vwQualityGoalReleaseList = (VwQualityGoalReleaseList) parameter.get("qualityGoalReleaseList");
        if(vwQualityGoalReleaseList != null) {
            qualityGoalReleaseList = vwQualityGoalReleaseList.
                                     qualityGoalReleaseList;
        } else {
            qualityGoalReleaseList = new ArrayList();
        }
        refreshFlag = true;

    }

    public void refreshWorkArea() {
        if (refreshFlag) {
            VWUtil.clearUI(this);
            resetUI();
            refreshFlag = false;
        } else {
            super.refreshWorkArea();
        }
    }

    public void fireNeedRefresh() {
        this.refreshFlag = true;
    }

    protected void resetUI() {
        if (dataBean != null) {

            VWUtil.bindDto2UI(dataBean, this);

            if (dataBean.getOp().equals(IDto.OP_NOCHANGE)) {
                this.parentWorkArea.enableAllTabs();
                this.jCheckBoxIsSumdefects.setEnabled(true);
                this.jCheckBoxIsSumSize.setEnabled(true);

            } else {
                this.parentWorkArea.enableTabOnly("General");
                this.parentWorkArea.setSelected("General");
                this.jCheckBoxIsSumdefects.setEnabled(false);
                this.jCheckBoxIsSumSize.setEnabled(false);

            }
            this.textProduction.setEditable(true);
            this.textSeq.setEditable(true);
            this.textDescription.setEnabled(true);
            this.btnSave.setVisible(true);
            dataBean.setNeedSave(false);
        } else {
            this.parentWorkArea.enableTabOnly("General");
            this.parentWorkArea.setSelected("General");
            this.jCheckBoxIsSumdefects.setEnabled(false);
            this.jCheckBoxIsSumSize.setEnabled(false);
            this.textProduction.setEditable(false);
            this.textSeq.setEditable(false);
            this.textDescription.setEnabled(false);
            this.btnSave.setVisible(false);
        }
        this.reCaculate();
    }


    public static void main(String args[]) {
//        VwQualityGoalGeneral vwQualityGoalGeneral = new VwQualityGoalGeneral();
////        vwAccountGeneralBase.txtAcntId.setEnabled(false);
//        TestPanel.show(vwQualityGoalGeneral);
    }

}
