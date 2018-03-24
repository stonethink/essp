package client.essp.timesheet.activity.general;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import c2s.dto.DtoUtil;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.activity.DtoActivityGeneral;
import c2s.essp.timesheet.activity.DtoActivityKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.common.Constant;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.*;
import com.wits.util.Parameter;
/**
 * <p>Title:VwGeneral </p>
 *
 * <p>Description:VwGeneral��Ƭ </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class VwGeneral extends VWGeneralWorkArea {
    private Long activityRid = null;
    static final String actionIdInit = "FTSGeneralDisplay";
    static final String actionIdUpdate = "FTSGeneralUpdate";
    DtoActivityGeneral dtoGeneral = new DtoActivityGeneral();
    private Boolean isPrimaryResource = false;
    public VWJCheckBox inputIsStart = new VWJCheckBox();
    public VWJCheckBox inputIsFinish = new VWJCheckBox();
    VWJLabel nameLabel = new VWJLabel();
    VWJText inputName = new VWJText();
    VWJLabel activityNameLabel = new VWJLabel();
    VWJText inputActivityName = new VWJText();
    VWJLabel codeLabel = new VWJLabel();
    VWJText inputCode = new VWJText();
    VWJLabel lbDurationDay = new VWJLabel();
    VWJDatePanel inputAcutalStart = new VWJDatePanel();
    VWJReal inputDurationDay = new VWJReal();
    VWJDatePanel  inputActualFinish  = new VWJDatePanel();
    VWJLabel lbAnticipatedFinish = new VWJLabel();
    VWJDatePanel inputAnticipatedFinish = new VWJDatePanel();
    VWJLabel suspendLabel = new VWJLabel();
    VWJText inputSuspend = new VWJText();
    VWJLabel resumeLabel = new VWJLabel();
    VWJText inputResume = new VWJText();
    VWJText inputRid = new VWJText();
    JButton feedBackBtn = new JButton();
    JButton noteToBtn = new JButton();
    JButton btnFresh = new JButton();
    JButton btnSave =  null;
    VwFeedBackNote vwFeedBack;

    public VwGeneral() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * ����
     */
    public void resetUI() {
        VWUtil.clearUI(this);
        inputIsStart.setEnabled(false);
        inputIsFinish.setEnabled(false);
        btnSave.setEnabled(false);
        noteToBtn.setEnabled(false);
        feedBackBtn.setEnabled(false);
        inputActualFinish.setEnabled(false);
        inputAcutalStart.setEnabled(false);
        inputAnticipatedFinish.setEnabled(false);
        btnFresh.setEnabled(false);
        if(activityRid == null){
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInit);
        inputInfo.setInputObj(DtoActivityKey.DTO_RID, activityRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }
        DtoActivityGeneral dtoG = (DtoActivityGeneral)returnInfo.
                                        getReturnObj(DtoActivityKey.DTO_GENERAL);
        isPrimaryResource = (Boolean)returnInfo.
                             getReturnObj(DtoActivityKey.DTO_ISPRIMARY_RESOURCE);
        if(dtoG != null && dtoG.getRid()!=null){
            btnSave.setEnabled(true);
            noteToBtn.setEnabled(true);
            feedBackBtn.setEnabled(true);
            btnFresh.setEnabled(true);
            dtoGeneral = dtoG;
            VWUtil.bindDto2UI(dtoGeneral, this);
            this.actionPerformedSelectStart(isPrimaryResource);
        }
    }

   
   //�¼�
    public void addUICEvent() {
       //����
       btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              actionPerformedSave(e);
           }
       });
       btnSave.setToolTipText("rsid.common.save");

       //ˢ��
       btnFresh = this.getButtonPanel().addButton("refresh.png");
       btnFresh.setToolTipText("rsid.common.refresh");
       btnFresh.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               actionPerformedFefrsh(e);
           }
       });
      //ѡ��ʼѡ���
       boolean isStart = inputIsStart.isSelected();
       boolean isFinish = inputIsFinish.isSelected();
       inputIsStart.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  actionPerformedSelectStart(isPrimaryResource);

              }
          });

       //ѡ�����ѡ���
       inputIsFinish.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 actionPerformedSelectFinish();
             }
         });
       this.refreshCheckBox(isStart,isFinish);

      //FeedBack��ť
       feedBackBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               mouseClickedFeedBackBtn();
           }
       });

       //NoteTo��ť
       noteToBtn.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     mouseClickedNoteTo();
                 }
       });
  }
    
    private void actionPerformedFefrsh(ActionEvent e){
        resetUI();
    }
  //��FeedBack�¼�
  private void mouseClickedFeedBackBtn() {
      VwFeedBackNote vwFeedBack = new VwFeedBackNote();
      Parameter para = new Parameter();
      para.put(DtoActivityKey.DTO_GENERAL, this.dtoGeneral);
      vwFeedBack.setParameter(para);
      VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(),
              "Feedback", vwFeedBack, vwFeedBack);
      popupEditor.showConfirm();
  }

  //��NoteTo�¼�
  private void mouseClickedNoteTo() {
      VwNoteToPM vwNoteTo = new VwNoteToPM();
      Parameter para = new Parameter();
      para.put(DtoActivityKey.DTO_GENERAL, this.dtoGeneral);
      vwNoteTo.setParameter(para);
      VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(),
              "Note to PM", vwNoteTo, vwNoteTo);
      popupEditor.showConfirm();
  }

   //����
   private void actionPerformedSave(ActionEvent e) {
     if(validateDate()){
         DtoActivityGeneral dtoBak = (DtoActivityGeneral) DtoUtil.deepClone(
                 dtoGeneral);
       if (checkModified()) {
           if (saveData() == false) {
               DtoUtil.copyBeanToBean(dtoGeneral, dtoBak);
           }
       } else {
           DtoUtil.copyBeanToBean(dtoGeneral, dtoBak);
       }
       this.fireDataChanged();
       }
   }
   
   /**
    * У��_ʼ���ںͽY�������Ǟ�պ����_��
    * @return
    */
   private Boolean validateDate(){
       if (inputIsStart.isSelected() && inputAcutalStart.getUICValue() == null) {
           comMSG.dispErrorDialog("error.client.VwGeneral.inputStart");
           return false;
       }
       if (inputIsFinish.isSelected() && inputActualFinish.getUICValue() == null) {
           comMSG.dispErrorDialog("error.client.VwGeneral.inputFinish");
           return false;
       }    
       if(((Date)inputAcutalStart.getUICValue()).after((Date)inputActualFinish.getUICValue())){
           comMSG.dispErrorDialog("error.client.VwTsDates.endAfterStart");
           return false;
       }
       return true;
   }

    //�ж���Ϣ�Ƿ񱻸ı�
    private boolean checkModified() {
           VWUtil.convertUI2Dto(dtoGeneral, this);
           return dtoGeneral.isChanged();
    }

    //����
    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);
        inputInfo.setInputObj(DtoActivityKey.DTO_GENERAL, dtoGeneral);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            DtoActivityGeneral retGeneral = (DtoActivityGeneral) returnInfo.
                                            getReturnObj(
                    DtoActivityKey.DTO);
            DtoUtil.copyBeanToBean(dtoGeneral, retGeneral);
            this.fireDataChanged();
            VWUtil.bindDto2UI(dtoGeneral, this);
            comMSG.dispMessageDialog("rsid.common.saveComplete");
            return true;
        } else {
            return false;
        }
    }

    //��ʼѡ���
    private void actionPerformedSelectStart(Boolean isPrimaryResource) {
        //�����ǰ�Ñ����Ǯ�ǰACTIVITY�����YԴ���t�����M���޸Ĳ���
        if(!isPrimaryResource){
            inputIsStart.setEnabled(false);
            inputIsFinish.setEnabled(false);
            inputAcutalStart.setEnabled(false);
            inputActualFinish.setEnabled(false);
            inputAnticipatedFinish.setEnabled(false);
            feedBackBtn.setEnabled(false);
            noteToBtn.setEnabled(false);
            btnSave.setEnabled(false);
            btnFresh.setEnabled(false);
            return;
        }
        if (inputIsStart.isSelected() && inputIsFinish.isSelected() == false) {
            inputIsStart.setEnabled(true);
            inputIsFinish.setEnabled(true);
            inputAcutalStart.setEnabled(true);
            inputActualFinish.setEnabled(false);
            inputAnticipatedFinish.setEnabled(true);
        } else if (inputIsStart.isSelected() && inputIsFinish.isSelected()) {
            inputIsStart.setEnabled(false);
            inputIsFinish.setEnabled(true);
            inputActualFinish.setEnabled(true);
            inputAcutalStart.setEnabled(false);
            inputAnticipatedFinish.setEnabled(false);
        } else {
            if (inputAcutalStart.getUICValue() == null) {
                comMSG.dispErrorDialog("error.client.VwGeneral.inputStart"); 
                inputIsStart.setSelected(true);
            }else if(((Date)inputAcutalStart.getUICValue()).after((Date)inputActualFinish.getUICValue())){
                comMSG.dispErrorDialog("error.client.VwTsDates.endAfterStart");
                inputIsStart.setSelected(true);
            }else{
            inputIsFinish.setEnabled(false);
            inputIsStart.setEnabled(true);
            inputAcutalStart.setEnabled(false);
            inputActualFinish.setEnabled(false);
            inputAnticipatedFinish.setEnabled(false);
            }
        }
    }

    //����ѡ���
    private void actionPerformedSelectFinish() {
        String msg = "error.client.VwGeneral.changeStaus";
        int opt = 0;
        if (inputIsFinish.isSelected()) {
            if (inputAcutalStart.getUICValue() == null) {
                comMSG.dispConfirmDialog("error.client.VwGeneral.inputStart");
            } else {
                opt = comMSG.dispConfirmDialog(msg);
            }
            if (opt == Constant.OK) {
                inputIsStart.setEnabled(false);
                inputActualFinish.setEnabled(true);
                inputAcutalStart.setEnabled(false);
                inputAnticipatedFinish.setEnabled(false);
                if (!inputIsFinish.isSelected() &&
                    inputAnticipatedFinish.getUICValue() == null) {
                    inputAnticipatedFinish.setUICValue(new Date());
                }
            } else {
                inputIsFinish.setSelected(false);
            }
        }
        if (!inputIsFinish.isSelected()) {
            if (inputActualFinish.getUICValue() == null) {
                comMSG.dispErrorDialog("error.client.VwGeneral.inputFinish");
                inputIsFinish.setSelected(true);
            }else if(((Date)inputAcutalStart.getUICValue()).after((Date)inputActualFinish.getUICValue())){ 
                    comMSG.dispErrorDialog("error.client.VwTsDates.endAfterStart");
                    inputIsFinish.setSelected(true);
            }else{
              inputIsStart.setEnabled(true);
              inputIsStart.setSelected(true);
              inputActualFinish.setEnabled(false);
              inputAcutalStart.setEnabled(true);
              inputAnticipatedFinish.setEnabled(true);
            }
        }
    }

    //��ʼ��
    public void jbInit() throws Exception {
        this.setLayout(null);
        //��һ��1��
        nameLabel.setBounds(new Rectangle(10, 10, 120, 20));
        nameLabel.setText("rsid.timesheet.wBSName");

        feedBackBtn.setBounds(new Rectangle(343, 9, 157, 20));
        feedBackBtn.setText("rsid.timesheet.feedBack");
        noteToBtn.setBounds(new Rectangle(524, 9, 156, 20));
        noteToBtn.setText("rsid.timesheet.noteTo");

        //��һ��2��
        inputName.setBounds(new Rectangle(141, 10, 183, 20));

        //vWJText1.setBorder(BorderFactory.createLineBorder(color));
        //�ڶ���1��
        codeLabel.setBounds(new Rectangle(10, 40, 150, 20));
        codeLabel.setText("rsid.timesheet.activityID");
        //�ڶ���2��
        inputCode.setBounds(new Rectangle(141, 40, 183, 20));

        //�ڶ���3��
        activityNameLabel.setBounds(new Rectangle(343, 40, 150, 20));
        activityNameLabel.setText("rsid.timesheet.activityName");
        //�ڶ���4��
        inputActivityName.setBounds(new Rectangle(498, 40, 183, 20));
        //������1��
        inputIsStart.setBounds(10, 70, 120, 20);
        inputIsStart.setText("rsid.timesheet.actualStart");

        lbDurationDay.setText("rsid.timesheet.durationDay");
        lbDurationDay.setBounds(new Rectangle(343, 70, 150, 20));

        inputIsFinish.setBounds(10, 100, 120, 20);
        inputIsFinish.setText("rsid.timesheet.actualFinish");

        lbAnticipatedFinish.setText("rsid.timesheet.anticipatedFinish");
        lbAnticipatedFinish.setBounds(new Rectangle(343, 100, 150, 20));

        suspendLabel.setText("rsid.timesheet.suspend");
        suspendLabel.setBounds(new Rectangle(10, 130, 150, 20));

        inputSuspend.setBounds(new Rectangle(141, 130, 183, 20));

        resumeLabel.setText("rsid.timesheet.resume");
        resumeLabel.setBounds(new Rectangle(343, 130, 150, 20));

        inputResume.setBounds(new Rectangle(498, 130, 183, 20));

        inputAcutalStart.setBounds(new Rectangle(141, 70, 185, 20));
        inputAcutalStart.setCanSelect(true);

        inputDurationDay.setBounds(new Rectangle(497, 70, 185, 20));
        inputDurationDay.setMaxInputDecimalDigit(2);

        inputActualFinish.setBounds(new Rectangle(141, 100, 185, 20));
        inputActualFinish.setCanSelect(true);

        inputAnticipatedFinish.setBounds(new Rectangle(497, 100, 185, 20));
        inputAnticipatedFinish.setCanSelect(true);

        inputRid.setBounds(new Rectangle(10, 150, 30, 20));
        this.add(inputRid);
        this.add(nameLabel);
        this.add(inputName);
        this.add(activityNameLabel);
        this.add(inputActivityName);
        this.add(codeLabel);
        this.add(inputAnticipatedFinish);
        this.add(inputCode);
        this.add(suspendLabel);
        this.add(inputSuspend);
        this.add(resumeLabel);
        this.add(inputResume);
        this.add(inputAcutalStart);
        this.add(lbDurationDay);
        this.add(inputDurationDay);
        this.add(inputActualFinish);
        this.add(lbAnticipatedFinish);
        this.add(inputAnticipatedFinish);
        this.add(inputIsStart);
        this.add(inputIsFinish);
        this.add(feedBackBtn);
        this.add(noteToBtn);

        setUIComponentName();
        setTabOrder();
    }

    //��������
    private void setUIComponentName() {
        inputIsFinish.setName("finished");
        inputIsStart.setName("started");
        inputName.setName("wbsName");
        inputActivityName.setName("name");
        inputCode.setName("id");
        inputAcutalStart.setName("startDate");
        inputDurationDay.setName("plannedDuration");
        inputAnticipatedFinish.setName("expectedFinishDate");
        inputActualFinish.setName("finishDate");
        inputSuspend.setName("suspendDate");
        inputResume.setName("resumeDate");
        inputRid.setName("rid");
        inputRid.setVisible(false);

   }

    //����TAB��˳��
    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(inputName);
        compList.add(inputActivityName);
        compList.add(inputCode);
        compList.add(inputAcutalStart);
        compList.add(inputDurationDay);
        compList.add(inputAnticipatedFinish);
        compList.add(inputActualFinish);
        compList.add(inputAnticipatedFinish);
        compList.add(inputSuspend);
        comFORM.setTABOrder(this, compList);
    }

   //���ÿؼ��Ŀɱ༭��
    protected void refreshCheckBox(boolean startFlag,boolean finishFlag) {
      inputName.setEnabled(false);
      inputActivityName.setEnabled(false);
      inputCode.setEnabled(false);
      inputSuspend.setEnabled(false);
      inputResume.setEnabled(false);
      inputDurationDay.setEnabled(false);
      inputAcutalStart.setEnabled(startFlag);
      inputAnticipatedFinish.setEnabled(startFlag);
      inputActualFinish.setEnabled(finishFlag);
      }

      // ���ò���
      public void setParameter(Parameter param) {
          super.setParameter(param);
          activityRid = (Long)param.get(DtoActivityKey.DTO_RID);
      }

}


