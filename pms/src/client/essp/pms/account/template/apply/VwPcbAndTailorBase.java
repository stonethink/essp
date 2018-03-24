package client.essp.pms.account.template.apply;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.IVWWizardEditorEvent;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import com.wits.util.Parameter;
import client.framework.view.common.comMSG;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import client.framework.common.Constant;

public class VwPcbAndTailorBase extends VWGeneralWorkArea implements IVWWizardEditorEvent{
    private VwPcbBase vwPcb ;
    private VwTailor vwTailor;
    private Parameter para = new Parameter();
     final String actionIdSave = "FAcntTemplateApplySaveAction";
    public VwPcbAndTailorBase() {
        try{
            JbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void JbInit(){
        this.setPreferredSize(new Dimension(600, 400));
        vwPcb = new VwPcbBase();
        vwTailor = new VwTailor();
        this.addTab("PCB",vwPcb);
        this.addTab("Tailor",vwTailor);
    }

    public void setParameter(Parameter para){
        super.setParameter(para);
        this.para = para;
        vwPcb.setParameter(para);
        vwTailor.setParameter(para);
    }
    public void refresh(){
        vwPcb.refresh();
        vwTailor.refresh();
    }
    public boolean onClickBack(ActionEvent e) {
        return true;
    }

    public boolean onClickNext(ActionEvent e) {
        return false;//最后一张卡片，没有下一步
    }

    public boolean onClickFinish(ActionEvent e) {
        if(applyTemplate()){
            return true;
        }else{
            int returnValue = comMSG.dispDialogProcessYN("Failed to Save template! do you want to exit? ");
            if(returnValue == Constant.OK){
                return true;
            }else{
                return false;
            }
        }
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }
    public boolean applyTemplate(){
          Long templateRid = (Long)para.get("acntrid");//模板的rid
          Long acntRid = (Long)para.get("accountRid");//项目的rid

          if(acntRid == null ){
              return false;
          }
          String tailorDescription =(String)this.para.get("TailorDescription");
          InputInfo inputInfo = new InputInfo();
          inputInfo.setActionId(this.actionIdSave);
          inputInfo.setInputObj("Account",templateRid);
          inputInfo.setInputObj("TailorDescription",tailorDescription);
          inputInfo.setInputObj("acntRid",acntRid);
          ReturnInfo returnInfo = accessData(inputInfo);
          if(returnInfo.isError() == true){
              return false;
          }
          return true;
    }
}
