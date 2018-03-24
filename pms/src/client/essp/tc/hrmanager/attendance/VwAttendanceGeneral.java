package client.essp.tc.hrmanager.attendance;

import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.util.Date;
import java.util.Map;
import c2s.essp.tc.attendance.DtoAttendance;
import c2s.essp.common.code.DtoKey;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.util.Parameter;
import c2s.dto.ReturnInfo;
import client.framework.model.VMComboBox;
import c2s.dto.InputInfo;
import java.util.Vector;
import java.awt.event.ActionEvent;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import java.awt.event.ActionListener;
import com.wits.util.comDate;
import com.wits.util.StringUtil;
import client.framework.view.vwcomp.VWUtil;



public class VwAttendanceGeneral extends VwAttendanceGeneralBase implements
        IVWPopupEditorEvent{
    static final String actionIdInit = "FTCHrManageAttendanceInitAction";
    static final String actionIdSave = "FTCHrManageAttendanceSaveAction";
    private DtoAttendance dto;
    private Date beginPeriod;
    private Date endPeriod;
    private boolean needRefrehParent = false;//提交后是否刷新父窗口


    public VwAttendanceGeneral() {
        super();
        try {
           addUICEvent();
       } catch (Exception ex) {
           ex.printStackTrace();
       }

    }
    public void setParameter(Parameter param) {
       super.setParameter(param);
       this.beginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
       this.endPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
       //新增传入的Dto只有LoginId
       this.dto = (DtoAttendance) (param.get(DtoKey.DTO));
       needRefrehParent = false;
   }

   public void resetUI(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInit);
        inputInfo.setInputObj(DtoTcKey.USER_ID, this.dto);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            Vector attendanceTypeComList = (Vector) returnInfo.getReturnObj("attendanceTypeComList");
            VMComboBox vmAttendanceType  = new VMComboBox(attendanceTypeComList);
            inputAttendanceType.setVMComboBox(vmAttendanceType);
        }
         bindDto2UI();
        //新增可修改AttendanceType,更新不能修改AttendanceType
//        inputAttendanceType.setEnabled(dto.getRid() == null);
    }

    public boolean needRefrehParent(){
       return this.needRefrehParent;
   }
   public boolean onClickOK(ActionEvent e) {
       if(vildateData()){
           convertUI2Dto();
           if(dto.getRid() == null ){//新增
               if (saveData()) {
                   needRefrehParent = true;
                   return true;
               }
           }
           if(dto.getRid() != null){//更新
               if(!chechModified()){
                   needRefrehParent = false;
                   return true;
               }else{
                   if (saveData()) {
                       needRefrehParent = true;
                       return true;
                   }
               }
           }
       }
       needRefrehParent = false;
       return false;
   }

   public boolean onClickCancel(ActionEvent e) {
       return true;
   }

   private void addUICEvent(){
       inputAttendanceType.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               actionPerformedChooseAttendanceType();
           }
       });
   }

   //选择违规类型事件
   private void actionPerformedChooseAttendanceType(){
//       DtoComboItem attendanceItem = (DtoComboItem) inputAttendanceType.getModel().getSelectedItem();
//       if(attendanceItem != null){
//
//       }
   }

   private boolean validateDate() {
         Date attendanceDate = (Date) inputAttendanceDate.getUICValue();

         if(attendanceDate == null){
             comMSG.dispErrorDialog("Please fill in date!");
             comFORM.setFocus(inputAttendanceDate);
             return false;

         }else if(beginPeriod.getTime() > attendanceDate.getTime() ||
                 endPeriod.getTime() < attendanceDate.getTime()){
             comMSG.dispErrorDialog("The attendance period must be between "+
                                    comDate.dateToString(beginPeriod)+" and " +
                                    comDate.dateToString(endPeriod)+"!");
             comFORM.setFocus(inputAttendanceDate);
             return false;
         }
         return true;
     }
     private boolean vildateData(){
         Object attendanceType = inputAttendanceType.getUICValue();
         Date date=(Date)inputAttendanceDate.getUICValue();
         if(attendanceType == null){
             comMSG.dispErrorDialog("Please select a attendance type!");
             comFORM.setFocus(inputAttendanceType);
             return false;
         }else if(date==null){
             comMSG.dispErrorDialog("Please filled in attendance date!");
             comFORM.setFocus(inputAttendanceDate);
             return false;
         }
             return true;
    }
   private boolean saveData(){
           InputInfo inputInfo = new InputInfo();
           inputInfo.setActionId(this.actionIdSave);
           inputInfo.setInputObj(DtoKey.DTO, dto);
           ReturnInfo returnInfo = accessData(inputInfo);
           if (returnInfo.isError() == false) {
               return true;
           }
       return false;
   }
   private boolean chechModified(){
       return dto.isChanged() ;
   }

   private void convertUI2Dto(){
       VWUtil.convertUI2Dto(dto,this);

   }

    private void bindDto2UI(){
        dto.setRemark(StringUtil.nvl(dto.getRemark()));
//        System.out.println(dto.getLoginId());
        VWUtil.bindDto2UI(dto, this);

    }

}
