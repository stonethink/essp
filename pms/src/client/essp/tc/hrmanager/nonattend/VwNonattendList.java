package client.essp.tc.hrmanager.nonattend;

import c2s.dto.ReturnInfo;
import client.framework.common.Constant;
import java.awt.event.ActionEvent;
import com.wits.util.Parameter;
import java.util.Date;
import c2s.essp.common.code.DtoKey;
import java.awt.event.ActionListener;
import c2s.dto.InputInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.common.comMSG;
import java.util.List;
import c2s.essp.tc.nonattend.DtoNonattend;

public class VwNonattendList extends VwNonattendListBase {
    static final String actionIdList = "FTCHrManageNonattendListAction";
   static final String actionIdDelete = "FTCHrManageNonattendDeleteAction";

   private String userId;
   private Date beginPeriod;
   private Date endPeriod;
   private List nonattendList;
   private boolean isParameterValidate = true;
   VwNonattendGeneral vwNonattendGeneral;
   public VwNonattendList() {
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
       if(vwNonattendGeneral == null)
           vwNonattendGeneral = new VwNonattendGeneral();
       Parameter param = new Parameter();
       param.put(DtoTcKey.BEGIN_PERIOD,beginPeriod);
       param.put(DtoTcKey.END_PERIOD,endPeriod);
       DtoNonattend dto = new DtoNonattend();
       dto.setLoginId(userId);
       param.put(DtoKey.DTO,dto);
       vwNonattendGeneral.setParameter(param);
       vwNonattendGeneral.resetUI();
               VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Nonattend",
                                                       vwNonattendGeneral, vwNonattendGeneral);
       pop.show();
       if(vwNonattendGeneral.needRefrehParent())
           resetUI();
   }

   private void actionPerformedEdit(){
       if(vwNonattendGeneral == null)
           vwNonattendGeneral = new VwNonattendGeneral();
       Parameter param = new Parameter();
       param.put(DtoTcKey.BEGIN_PERIOD,beginPeriod);
       param.put(DtoTcKey.END_PERIOD,endPeriod);
       param.put(DtoKey.DTO,this.getTable().getSelectedData());
       vwNonattendGeneral.setParameter(param);
       vwNonattendGeneral.resetUI();
               VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Nonattend",
                                                       vwNonattendGeneral, vwNonattendGeneral);
       pop.show();
       if(vwNonattendGeneral.needRefrehParent())
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
           nonattendList = (List) returnInfo.getReturnObj("nonattendList");
           getTable().setRows(nonattendList);
       }
   }

}
