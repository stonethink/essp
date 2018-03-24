package client.essp.pms.qa.monitoring;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ComboBoxModel;
import javax.swing.SwingConstants;

import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.issue.VWJIssue;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.pms.wbs.process.checklist.VwCheckActionList;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJTextArea;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwCheckActionPop extends VWGeneralWorkArea
    implements IVWPopupEditorEvent {
    private DtoQaMonitoring checkPointAction;
    private DtoWbsActivity dtoWbsActivity;
    private ComboBoxModel performerModel;
    public VwCheckActionPop() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            log.error(ex);
        }

    }
    /**
     * jbInit
     */
    private void jbInit() {
        this.setLayout(null);

        titleLabel.setText("Edit Check Action");
        titleLabel.setBounds(new Rectangle(156, 18, 227, 31));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);


        nameLabel.setText("Name");
        nameLabel.setBounds(new Rectangle(36, 53, 40, 31));
        name.setName("name");
        name.setBounds(new Rectangle(99, 63, 478, 45));
        name.setEnabled(false);

        methodLabel.setText("Method");
        methodLabel.setBounds(new Rectangle(36, 112, 40, 31));
        method.setName("method");
        method.setBounds(new Rectangle(100, 120, 478, 80));
        method.setEnabled(false);

        remarkLabel.setText("Remark");
        remarkLabel.setBounds(new Rectangle(36, 200, 40, 31));
        remark.setName("remark");
        remark.setBounds(new Rectangle(100, 210, 478, 45));
        remark.setEnabled(false);

        occasionLabel.setText("Occasion");
        occasionLabel.setBounds(new Rectangle(36, 270, 58, 31));
        cmbOccasion.setName("occasion");
        cmbOccasion.setBounds(new Rectangle(99, 275, 120, 24));
        cmbOccasion.setModel(VMComboBox.toVMComboBox(VwCheckActionList.
                chkActionOccasion));

        planDateLabel.setText("Plan Date");
        planDateLabel.setBounds(new Rectangle(226, 271, 58, 31));
        planDate.setName("planDate");
        planDate.setBounds(new Rectangle(289, 275, 96, 24));
        planDate.setCanSelect(true);


        actDateLabel.setText("Actual Date");
        actDateLabel.setBounds(new Rectangle(406, 271, 76, 31));
        actDate.setName("actDate");
        actDate.setBounds(new Rectangle(479, 275, 99, 24));
        actDate.setCanSelect(true);


        perfomerLabel.setText("Performer");
        perfomerLabel.setBounds(new Rectangle(36, 315, 58, 31));
        cmbPerformer.setName("planPerformer");
        cmbPerformer.setBounds(new Rectangle(99, 314, 120, 24));


        contentLabel.setText("Content");
        contentLabel.setBounds(new Rectangle(36, 351, 58, 31));
        content.setName("content");
        content.setBounds(new Rectangle(100, 354, 478, 66));


        resultLabel.setText("Result");
        resultLabel.setBounds(new Rectangle(36, 427, 58, 31));
        result.setName("result");
        result.setBounds(new Rectangle(100, 433, 478, 45));


        statusLabel.setText("Status");
        statusLabel.setBounds(new Rectangle(233, 314, 58, 31));
        cmbStatus.setName("status");
        cmbStatus.setBounds(new Rectangle(288, 314, 97, 24));
        cmbStatus.setModel(VMComboBox.toVMComboBox(VwCheckActionList.
            chkActionStatus));

        ncrLabel.setText("NCR NO.");
        ncrLabel.setBounds(new Rectangle(418, 312, 58, 31));
        issue.setName("nrcNo");
        issue.setIssueType("NCR");
        issue.setBounds(new Rectangle(479, 310, 99, 24));



        this.add(titleLabel);
        this.add(nameLabel);
        this.add(methodLabel);
        this.add(remarkLabel);
        this.add(occasionLabel);
        this.add(perfomerLabel);
        this.add(method);
        this.add(cmbOccasion);
        this.add(cmbStatus);
        this.add(remark);
        this.add(name);
        this.add(planDateLabel);
        this.add(cmbPerformer);
        this.add(result);
        this.add(statusLabel);
        this.add(planDate);
        this.add(content);
        this.add(actDate);
        this.add(issue);
        this.add(actDateLabel);
        this.add(ncrLabel);
        this.add(resultLabel);
        this.add(contentLabel);
    }

    private void addUICEvent() {
        cmbOccasion.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                processOccasionChanged();
            }
        });
    }

    private void processOccasionChanged() {
        String occasionValue = (String) cmbOccasion.getUICValue();
        //动作时机自定义时，plan Date 可修改，其余情况自动计算
        if(VwCheckActionList.chkActionOccasion[4].equals(occasionValue)) {
            planDate.setEnabled(true);
            return;
        }
        planDate.setEnabled(false);
        Date pDate = VwCheckActionList.getQaCheckAtionPlanDate(occasionValue,
            dtoWbsActivity);
        planDate.setUICValue(pDate);
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.checkPointAction = (DtoQaMonitoring) param.get(DtoKey.DTO);
        dtoWbsActivity = (DtoWbsActivity) param.get("dtoWbsActivity");
        performerModel = (ComboBoxModel) param.get("performerModel");
    }

    protected void resetUI() {
        setEnable(!checkPointAction.isReadonly());
        cmbPerformer.setModel(performerModel);
        issue.setAcntRid(checkPointAction.getAcntRid());
        VWUtil.bindDto2UI(checkPointAction, this);
        processOccasionChanged();
    }

    private void setEnable(boolean enable) {
        cmbOccasion.setEnabled(enable);
        planDate.setEnabled(enable);
        actDate.setEnabled(enable);
        cmbPerformer.setEnabled(enable);
        content.setEnabled(enable);
        result.setEnabled(enable);
        cmbStatus.setEnabled(enable);
        issue.setEnabled(enable);
    }

    public boolean onClickOK(ActionEvent e) {
       VWUtil.convertUI2Dto(checkPointAction, this);
       return true;
   }

   public boolean onClickCancel(ActionEvent e) {
       return true;
   }

   VWJLabel titleLabel = new VWJLabel();
   VWJLabel nameLabel = new VWJLabel();
   VWJLabel methodLabel = new VWJLabel();
   VWJLabel remarkLabel = new VWJLabel();
   VWJLabel occasionLabel = new VWJLabel();
   VWJLabel planDateLabel = new VWJLabel();
   VWJLabel actDateLabel = new VWJLabel();
   VWJLabel perfomerLabel = new VWJLabel();
   VWJLabel contentLabel = new VWJLabel();
   VWJLabel resultLabel = new VWJLabel();
   VWJLabel statusLabel = new VWJLabel();
   VWJLabel ncrLabel = new VWJLabel();
   VWJTextArea name = new VWJTextArea();
   VWJTextArea method = new VWJTextArea();
   VWJTextArea remark = new VWJTextArea();
   VWJComboBox cmbOccasion = new VWJComboBox();
   VWJDate planDate = new VWJDate();
   VWJDate actDate = new VWJDate();
   VWJComboBox cmbPerformer = new VWJComboBox();
   VWJTextArea content = new VWJTextArea();
   VWJTextArea result = new VWJTextArea();
   VWJComboBox cmbStatus = new VWJComboBox();
   VWJIssue issue = new VWJIssue();


}
