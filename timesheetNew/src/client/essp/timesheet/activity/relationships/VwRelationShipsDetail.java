package client.essp.timesheet.activity.relationships;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJLabel;
import c2s.essp.timesheet.activity.DtoRelationShipsDetail;
import client.framework.view.vwcomp.VWJDate;
import java.awt.Rectangle;
import c2s.dto.ReturnInfo;
import client.framework.view.vwcomp.VWUtil;
import c2s.dto.InputInfo;
import c2s.essp.timesheet.activity.DtoRelationShips;
import c2s.essp.timesheet.activity.DtoActivityKey;
/**
 * <p>Title: VwRelationShipsDetail</p>
 *
 * <p>Description: RelationShip��ϸ��Ϣ��Ƭ</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwRelationShipsDetail  extends VWGeneralWorkArea{
    static final String actionIdInit = "FTSRelationshipsDetail";
    private Long activityRid = null;
    DtoRelationShipsDetail dtoRelDetail = new DtoRelationShipsDetail();
    DtoRelationShips dtoRelation = new DtoRelationShips();
    VWJLabel lbStatus = new VWJLabel();
    VWJText inputStatus = new VWJText();
    VWJLabel lbPrimaryResource = new VWJLabel();
    VWJText inputPrimaryResource = new VWJText();
    VWJLabel lbStartDate = new VWJLabel();
    VWJDate inputStartDate = new VWJDate();
    VWJLabel lbFinishDate = new VWJLabel();
    VWJDate inputFinishDate = new VWJDate();
    VWJLabel lbEmail = new VWJLabel();
    VWJText inputEmail = new VWJText();
    VWJLabel lbPhone = new VWJLabel();
    VWJText inputPhone = new VWJText();

    public VwRelationShipsDetail() {
        try {
            jbInit();
            initUIName();
            setEnableFalse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //��ʼ��
    private void jbInit() {
        this.setLayout(null);
        lbStatus.setBounds(new Rectangle(9, 12, 77, 20));
        lbStatus.setText("rsid.common.status");
        //��һ��2��
        inputStatus.setBounds(new Rectangle(84, 13, 100, 20));

        //��2��1��
        lbPrimaryResource.setBounds(new Rectangle(210, 12, 115, 20));
        lbPrimaryResource.setText("rsid.timesheet.primaryResource");
        //��2��2��
        inputPrimaryResource.setBounds(new Rectangle(325, 14, 100, 20));

        //��3��1��
        lbStartDate.setBounds(new Rectangle(9, 49, 68, 20));
        lbStartDate.setText("rsid.timesheet.start");
        //��3��2��
        inputStartDate.setBounds(new Rectangle(84, 49, 100, 20));

        //��4��1��
        lbEmail.setBounds(new Rectangle(210, 49, 115, 20));
        lbEmail.setText("rsid.timesheet.e-mail");
        inputEmail.setBounds(new Rectangle(325, 47, 100, 20));

        lbFinishDate.setBounds(new Rectangle(9, 82, 74, 20));
        lbFinishDate.setText("rsid.timesheet.finish");
        //��3��2��
        inputFinishDate.setBounds(new Rectangle(84, 84, 100, 20));

        //��4��1��
        lbPhone.setBounds(new Rectangle(210, 87, 115, 15));
        lbPhone.setText("rsid.timesheet.officePhone");
        inputPhone.setBounds(new Rectangle(325, 85, 100, 20));
        this.add(lbStatus);
        this.add(inputStatus);
        this.add(lbPrimaryResource);
        this.add(inputPrimaryResource);
        this.add(lbStartDate);
        this.add(inputStartDate);
        this.add(lbEmail);
        this.add(inputFinishDate);
        this.add(lbFinishDate);
        this.add(lbPhone);
        this.add(inputPhone);
        this.add(inputEmail);
    }
   //��������
    private void initUIName() {
        inputStatus.setName("status");
        inputPrimaryResource.setName("primaryResource");
        inputEmail.setName("email");
        inputPhone.setName("officePhone");
        inputStartDate.setName("startDate");
        inputFinishDate.setName("finishDate");
    }

   //���ÿؼ�Ϊ������
    private void setEnableFalse() {
        inputStatus.setEnabled(false);
        inputPrimaryResource.setEnabled(false);
        inputEmail.setEnabled(false);
        inputPhone.setEnabled(false);
        inputStartDate.setEnabled(false);
        inputFinishDate.setEnabled(false);
    }

    //����
    protected void resetUI() {
        if (this.dtoRelation.getActivityObjectId() != null) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdInit);
            inputInfo.setInputObj(DtoActivityKey.DTO_RID, activityRid);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == true) {
                return;
            }
            dtoRelDetail = (DtoRelationShipsDetail) returnInfo.getReturnObj(
                    DtoActivityKey.DTO_RELATION_DETAIL);
            VWUtil.bindDto2UI(dtoRelDetail, this);
        }
    }

   //��������
   public void setParameter(DtoRelationShips dtoRelation) {
       super.setParameter(null);
       this.dtoRelation = dtoRelation;
       if (this.dtoRelation == null) {
           this.dtoRelation = new DtoRelationShips();
       } else {
           this.activityRid = this.dtoRelation.getActivityObjectId();
       }
   }


}
