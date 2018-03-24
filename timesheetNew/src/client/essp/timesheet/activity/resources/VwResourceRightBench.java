package client.essp.timesheet.activity.resources;

import java.awt.Rectangle;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.essp.common.view.VWGeneralWorkArea;
import c2s.essp.timesheet.activity.DtoResourceAssignment;
import c2s.essp.timesheet.activity.DtoResourceUnits;
import client.framework.view.vwcomp.VWUtil;
import client.framework.view.vwcomp.VWJCheckBox;
import com.wits.util.Parameter;
import c2s.essp.timesheet.activity.DtoActivityKey;
import c2s.dto.DtoUtil;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.common.comMSG;
import client.framework.view.event.DataChangedListener;
/**
 * <p>Title: VwResourceRightBench</p>
 *
 * <p>Description:ResourceUnit��Ƭ </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwResourceRightBench extends VWGeneralWorkArea implements
    DataChangedListener{
    static final String actionIdInit = "FTSResourceUnitsDispaly";
    static final String actionIdUpdate = "FTSResourceUpdate";
    DtoResourceUnits dtoResUnits = new DtoResourceUnits();
    DtoResourceAssignment dataResAssign = new DtoResourceAssignment();
    private Long assigmentObjId = null;
    VWJLabel lbPlannedUnits = new VWJLabel();
    VWJReal inputPlannedUnits = new VWJReal();
    VWJLabel lbPriorActualUnits = new VWJLabel();
    VWJReal inputPriorActualUnits = new VWJReal();
    VWJLabel lbRemainingUnits = new VWJLabel();
    VWJReal inputRemainingUnits = new VWJReal();
    VWJLabel lbNewRemainingUnits = new VWJLabel();
    private VWJReal inputNewRemainingUnits = new VWJReal();
    VWJCheckBox primaryChek = new VWJCheckBox();
    JButton detailButton = new JButton();
    JButton btnSave = new JButton();
    VWJText ridText = new VWJText();

    public VwResourceRightBench() {
         try {
            jbInit();
            addUICEvent();
            setUIIsEnable();
         } catch (Exception ex) {
           ex.printStackTrace();
       }
    }

    //����
    protected void resetUI() {
        InputInfo inputInfo = new InputInfo();
        if (assigmentObjId != null) {
            inputInfo.setActionId(this.actionIdInit);
            inputInfo.setInputObj(DtoActivityKey.ASSIGMENT_RID, assigmentObjId);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == true) {
                return;
            }
            dtoResUnits = (DtoResourceUnits) returnInfo.getReturnObj(
                    DtoActivityKey.DTO_RESOURCE_UNIT);

            VWUtil.bindDto2UI(dtoResUnits, this);
            detailButton.setEnabled(true);
            if(dtoResUnits == null || dtoResUnits.getResourceRid() == null){
                detailButton.setEnabled(false);
            }else{
                detailButton.setEnabled(true);
            }
        }
    }

    //��ʼ��
    public void jbInit() throws Exception {
        this.setLayout(null);
        //��һ��1��
        lbPlannedUnits.setBounds(new Rectangle(10, 10, 190, 20));
        lbPlannedUnits.setText("rsid.timesheet.plannedUnits");
        //��һ��2��
        inputPlannedUnits.setBounds(new Rectangle(200, 10, 80, 20));
        inputPlannedUnits.setMaxInputDecimalDigit(2);
        //��2��1��
        lbPriorActualUnits.setBounds(new Rectangle(10, 40, 190, 20));
        lbPriorActualUnits.setText("rsid.timesheet.priorActualUnits");
        //��2��2��
        inputPriorActualUnits.setBounds(new Rectangle(200, 40, 80, 20));
        inputPriorActualUnits.setMaxInputDecimalDigit(2);
        //��3��1��
        lbRemainingUnits.setBounds(new Rectangle(10, 70, 190, 20));
        lbRemainingUnits.setText("rsid.timesheet.remainingUnits");
        //��3��2��
        inputRemainingUnits.setBounds(new Rectangle(200, 70, 80, 20));
        inputRemainingUnits.setMaxInputDecimalDigit(2);
        //��4��1��
        lbNewRemainingUnits.setBounds(new Rectangle(10, 100, 190, 20));
        lbNewRemainingUnits.setText("rsid.timesheet.newRemainingUnits");
        inputNewRemainingUnits.setBounds(new Rectangle(200, 100, 80, 20));
        inputNewRemainingUnits.setMaxInputDecimalDigit(2);

        detailButton.setBounds(new Rectangle(100, 130, 80, 20));
        detailButton.setText("rsid.timesheet.details");

        this.add(lbPlannedUnits);
        this.add(inputPlannedUnits);
        this.add(lbPriorActualUnits);
        this.add(inputPriorActualUnits);
        this.add(lbRemainingUnits);
        this.add(inputRemainingUnits);
        this.add(lbNewRemainingUnits);
        this.add(inputNewRemainingUnits);
        this.add(detailButton);
        setUIComponentName();
        detailButton.setEnabled(false);
    }

    //��������
    private void setUIComponentName() {
        inputPlannedUnits.setName("plannedUnits");
        inputPriorActualUnits.setName("priorActualUnits");
        inputRemainingUnits.setName("remainingUnits");
        inputNewRemainingUnits.setName("newRemainingUnits");
        primaryChek.setName("isPrimaryResource");
        ridText.setName("resourceRid");

    }

    //���ÿؼ��Ƿ�ɱ༭
    private void setUIIsEnable() {
        inputPlannedUnits.setEnabled(false);
        inputRemainingUnits.setEnabled(false);
        inputPriorActualUnits.setEnabled(false);
    }

    //�����¼�
    private void addUICEvent() {

        detailButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mouseClickedResDetail();
            }
        });
        //����
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
        btnSave.setToolTipText("rsid.common.save");
    }

      //��ϸ��Ϣ�Ի���
      private void mouseClickedResDetail(){
       VwResourceDetail resourceDetail = new  VwResourceDetail();
       VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(),
           "Resource Detail", resourceDetail, resourceDetail);
       resourceDetail.setParameter(dataResAssign);
       resourceDetail.refreshWorkArea();
       popupEditor.showConfirm();
   }

   //��ֵ
   public void setResDto(DtoResourceAssignment dtoResAssign) {
       super.setParameter(null);
       this.dataResAssign = dtoResAssign;
       if (this.dataResAssign == null) {
           this.dataResAssign = new DtoResourceAssignment();
       } else {
           this.assigmentObjId = this.dataResAssign.getObjectId();
       }
   }

  //����
   private void actionPerformedSave(ActionEvent e) {
       DtoResourceUnits dtoBak = (DtoResourceUnits) DtoUtil.deepClone(
               dtoResUnits);
       if (checkModified()) {
           if (saveData() == false) {
               DtoUtil.copyBeanToBean(dtoResUnits, dtoBak);
           }
       } else {
           DtoUtil.copyBeanToBean(dtoResUnits, dtoBak);
       }
   }

   //�ж������Ƿ��иı�
   private boolean checkModified() {
       VWUtil.convertUI2Dto(dtoResUnits, this);
       return dtoResUnits.isChanged();
   }

      //����
      private boolean saveData() {
          InputInfo inputInfo = new InputInfo();
          inputInfo.setActionId(this.actionIdUpdate);
          inputInfo.setInputObj(DtoActivityKey.DTO, this.dtoResUnits);
          ReturnInfo returnInfo = accessData(inputInfo);
          if (returnInfo.isError() == false) {
              DtoResourceUnits units = (DtoResourceUnits) returnInfo.getReturnObj(
                      DtoActivityKey.DTO);
              DtoUtil.copyBeanToBean(dtoResUnits, units);
              this.fireDataChanged();
              VWUtil.bindDto2UI(dtoResUnits, this);
              comMSG.dispMessageDialog("rsid.common.saveComplete");
              return true;
          } else {
              return false;
          }
      }

      //���ò���
      public void setParameter(Parameter param) {
          super.setParameter(param);
          Boolean isPR = (Boolean) param.get(DtoActivityKey.DTO_ISPRIMARY_RESOURCE);
          //���General��Ƭ����Ŀ�Ѿ���ʼ��δ��ɣ��������赥λΪ���޸ģ�����Ϊֻ��
          if (isPR) {
              inputNewRemainingUnits.setEnabled(true);
              btnSave.setEnabled(true);
          } else {
              inputNewRemainingUnits.setEnabled(false);
              btnSave.setEnabled(false);
          }
        
    }

    public void processDataChanged() {
         super.fireDataChanged();
    }
}
