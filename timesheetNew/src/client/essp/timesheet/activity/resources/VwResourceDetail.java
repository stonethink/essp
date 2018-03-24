package client.essp.timesheet.activity.resources;

import java.awt.Dimension;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJCheckBox;
import java.awt.Rectangle;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import client.essp.common.view.VWGeneralWorkArea;
import c2s.essp.timesheet.activity.DtoResourceDetail;
import client.framework.view.vwcomp.VWUtil;
import c2s.essp.timesheet.activity.DtoResourceAssignment;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.awt.event.ActionEvent;
import c2s.essp.timesheet.activity.DtoActivityKey;

/**
 * <p>Title: VwResourceDetail</p>
 *
 * <p>Description:ResourceDetail卡片 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwResourceDetail extends VWGeneralWorkArea implements IVWPopupEditorEvent{
    static final String actionIdInit = "FTSResourceDetail";
    private Long assigmentObjId = null;
    DtoResourceAssignment dtoResAssign = new DtoResourceAssignment();
    DtoResourceDetail dtoResDetail = new DtoResourceDetail();
    private VWJLabel ResourceNameLabel = new VWJLabel();
    private VWJText inputResourceName = new VWJText();
    private VWJLabel officePhoneLabel = new VWJLabel();
    private VWJText inputOfficePhone = new VWJText();
    private VWJLabel mailLabel = new VWJLabel();
    private VWJText inputMail = new VWJText();
    private VWJLabel roleLabel = new VWJLabel();
    private VWJText inputRole = new VWJText();
    private VWJCheckBox inputPrimary = new VWJCheckBox();
    private VWJLabel primaryLabel = new VWJLabel();
    private VWJCheckBox inputResourceType = new VWJCheckBox();
    private VWJLabel resourceTypeLabel = new VWJLabel();


    public VwResourceDetail() {
        try {
            jbInit();
            setStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   //重置
    protected void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInit);
        inputInfo.setInputObj(DtoActivityKey.ASSIGMENT_RID, assigmentObjId);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
             return;
         }
         dtoResDetail = (DtoResourceDetail)returnInfo.getReturnObj(DtoActivityKey.DTO_RESOURCE_DETAIL);
         VWUtil.bindDto2UI(dtoResDetail, this);
    }

    //参数设置
    public void setParameter(DtoResourceAssignment dtoResAssign) {
          super.setParameter(null);
          this.dtoResAssign = dtoResAssign;
          if (this.dtoResAssign == null) {
              this.dtoResAssign = new DtoResourceAssignment();
          } else {
              this.assigmentObjId = this.dtoResAssign.getObjectId();
          }
          resetUI();
    }

    //初始化
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(300, 200));
        this.setLayout(null);
        ResourceNameLabel.setBounds(new Rectangle(10, 10, 120, 20));
        ResourceNameLabel.setText("rsid.timesheet.resourceName");
        inputResourceName.setBounds(new Rectangle(10, 30, 280, 20));

        officePhoneLabel.setBounds(new Rectangle(10, 50, 120, 20));
        officePhoneLabel.setText("rsid.timesheet.officePhone");

        mailLabel.setBounds(new Rectangle(130, 50, 120, 20));
        mailLabel.setText("rsid.timesheet.e-mail");

        inputOfficePhone.setBounds(new Rectangle(10, 70, 110, 20));
        inputMail.setBounds(new Rectangle(130, 70, 160, 20));

        roleLabel.setBounds(new Rectangle(10, 90, 120, 20));
        roleLabel.setText("rsid.common.role");

        inputRole.setBounds(new Rectangle(10, 110, 280, 20));

        inputPrimary.setBounds(new Rectangle(20, 140, 30, 20));
        primaryLabel.setBounds(new Rectangle(55, 140, 80, 20));
        primaryLabel.setText("rsid.timesheet.primary");

        inputResourceType.setBounds(new Rectangle(130, 140, 30, 20));
        resourceTypeLabel.setBounds(new Rectangle(165, 140, 120, 20));
        resourceTypeLabel.setText("rsid.timesheet.resourceType");

        this.add(ResourceNameLabel);
        this.add(inputResourceName);
        this.add(officePhoneLabel);
        this.add(mailLabel);
        this.add(inputOfficePhone);
        this.add(inputMail);
        this.add(inputRole);
        this.add(roleLabel);
        this.add(inputPrimary);
        this.add(primaryLabel);
        this.add(inputResourceType);
        this.add(resourceTypeLabel);
        setUIName();
    }
    /**
     * 设置状态
     */
    private void setStatus(){
        inputResourceName.setEnabled(false);
        inputOfficePhone.setEnabled(false);
        inputMail.setEnabled(false);
        inputRole.setEnabled(false);
        inputPrimary.setEnabled(false);
        inputResourceType.setEnabled(false);
    }
    //设置属性
    private void setUIName(){
       inputResourceName.setName("resourceName");
       inputOfficePhone.setName("officePhone");
       inputMail.setName("email");
       inputRole.setName("roleName");
       inputPrimary.setName("isPrimaryResource");
       inputResourceType.setName("isResourceType");
    }

    public void saveWorkArea() {
    }
    //OK事件
    public boolean onClickOK(ActionEvent actionEvent) {
        return true;
    }
    //Cancel事件
    public boolean onClickCancel(ActionEvent actionEvent) {
        return true;
    }

}
