package client.essp.tc.hrmanager.attendance;

import java.util.Date;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import c2s.dto.ReturnInfo;
import client.framework.common.Constant;
import c2s.dto.InputInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.util.Parameter;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.common.comMSG;
import c2s.essp.common.code.DtoKey;
import c2s.essp.tc.attendance.DtoAttendance;

public class VwAttendanceList extends VwAttendanceListBase {
    static final String actionIdList = "FTCHrManageAttendanceListAction";
   static final String actionIdDelete = "FTCHrManageAttendanceDeleteAction";

   private String userId;
   private Date beginPeriod;
   private Date endPeriod;
   private List attendanceList;
   private boolean isParameterValidate = true;
   VwAttendanceGeneral vwAttendanceGeneral;

    public VwAttendanceList() {
        super();
        try {
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void addUICEvent() {
       this.getButtonPanel().addAddButton(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               actionPerformedAdd();
           }
       });

       this.getButtonPanel().addEditButton(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               actionPerformedEdit();
           }
       });

       this.getButtonPanel().addDelButton(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               actionPerformedDel();
           }
       });
       this.getButtonPanel().addLoadButton(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               actionPerformedLoad();
           }
       });
   }

   private void actionPerformedAdd(){
       if(vwAttendanceGeneral == null)
           vwAttendanceGeneral = new VwAttendanceGeneral();
       Parameter param = new Parameter();
       param.put(DtoTcKey.BEGIN_PERIOD,beginPeriod);
       param.put(DtoTcKey.END_PERIOD,endPeriod);
       DtoAttendance dto = new DtoAttendance();
       dto.setLoginId(userId);
       param.put(DtoKey.DTO,dto);
       vwAttendanceGeneral.setParameter(param);
       vwAttendanceGeneral.resetUI();
       VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Deregulation",
                                                       vwAttendanceGeneral, vwAttendanceGeneral);
       pop.show();
       if(vwAttendanceGeneral.needRefrehParent())
           resetUI();
   }

   private void actionPerformedEdit(){
       if(vwAttendanceGeneral == null)
           vwAttendanceGeneral = new VwAttendanceGeneral();
       Parameter param = new Parameter();
       param.put(DtoTcKey.BEGIN_PERIOD,beginPeriod);
       param.put(DtoTcKey.END_PERIOD,endPeriod);
       param.put(DtoKey.DTO,this.getTable().getSelectedData());
       vwAttendanceGeneral.setParameter(param);
       vwAttendanceGeneral.resetUI();
       VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Attendance",
                                                       vwAttendanceGeneral, vwAttendanceGeneral);
       pop.show();
       if(vwAttendanceGeneral.needRefrehParent())
           resetUI();
   }
   private void actionPerformedDel(){
       int f = comMSG.dispConfirmDialog("Confirm to delete selected record!");
       if( f == Constant.OK ){
           InputInfo inputInfo = new InputInfo();
           inputInfo.setActionId(this.actionIdDelete);
           inputInfo.setInputObj(DtoKey.DTO, this.getTable().getSelectedData());
           ReturnInfo returnInfo = accessData(inputInfo);
           if(!returnInfo.isError()){
               resetUI();
           }
       }
   }
   private void actionPerformedLoad(){
       resetUI();
   }

   public void setParameter(Parameter param) {
       super.setParameter(param);
       this.beginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
       this.endPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
       this.userId = (String) (param.get(DtoTcKey.USER_ID));
       if(beginPeriod == null || endPeriod == null || userId == null)
           isParameterValidate = false;
       else
           isParameterValidate = true;
   }

   public void resetUI(){
       this.getButtonPanel().setVisible(isParameterValidate);
       if (!isParameterValidate)
           return;

       InputInfo inputInfo = new InputInfo();
       inputInfo.setActionId(this.actionIdList);
       inputInfo.setInputObj(DtoTcKey.USER_ID, this.userId);
       inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
       inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);

       ReturnInfo returnInfo = accessData(inputInfo);
       if (returnInfo.isError() == false) {
           attendanceList = (List) returnInfo.getReturnObj("attendanceList");
           getTable().setRows(attendanceList);
       }
   }


}
