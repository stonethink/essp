package client.essp.pms.account.template;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.Dimension;
import client.framework.view.vwcomp.IVWWizardEditorEvent;
import java.awt.event.ActionEvent;
import com.wits.util.Parameter;
import client.framework.view.event.DataChangedListener;
import client.essp.pms.modifyplan.VwBaseLinePlanModify;

public class VwTemplatePreviewBase extends VWGeneralWorkArea implements IVWWizardEditorEvent{

    protected VwTemplatePbsList vwTemplatePbsList;
    protected VwTemplateWbsList vwTemplateWbsList;
//    protected VwBaseLinePlanModify vwBlPanModify;

    protected Parameter param=new Parameter();

    public VwTemplatePreviewBase(){
        try{
            jbInit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {

        this.setPreferredSize(new Dimension(600, 400));
        vwTemplatePbsList = new VwTemplatePbsList();
        vwTemplateWbsList = new VwTemplateWbsList();
//        vwBlPanModify=new VwBaseLinePlanModify();
        this.addTab("WBS/Activity",vwTemplateWbsList);
        this.addTab("PBS",vwTemplatePbsList);
//        this.addTab("BL Plan Modify",vwBlPanModify);

    }



    public boolean onClickBack(ActionEvent e) {
        return true;
    }

    public boolean onClickNext(ActionEvent e) {

        return true;

    }

    public boolean onClickFinish(ActionEvent e) {
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public void refresh(){

         vwTemplatePbsList.refresh();
         vwTemplateWbsList.refresh();

    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.param=param;
        vwTemplatePbsList.setParameter(this.param);
        vwTemplateWbsList.setParameter(this.param);
//
//        Parameter param2=new Parameter();
//        param2=param;
////        param2.put("acntrid","true");
//        param2.put("isTemplateOpration","true");
//        vwBlPanModify.setParameter(param2);
    }



}
