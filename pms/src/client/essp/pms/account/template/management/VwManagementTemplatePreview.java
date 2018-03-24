package client.essp.pms.account.template.management;

import java.awt.event.ActionEvent;
import c2s.dto.ReturnInfo;
import com.wits.util.Parameter;
import client.framework.model.VMTreeTableModelAdapter;
import java.awt.Dimension;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.dto.InputInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import javax.swing.table.TableModel;
import c2s.dto.ITreeNode;
import client.framework.view.vwcomp.IVWWizardEditorEvent;
import client.essp.pms.account.template.VwTemplateWbsList;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.pms.account.template.VwTemplatePbsList;
import client.framework.view.common.comMSG;
import client.essp.pms.account.template.VwTemplatePreviewBase;

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
public class VwManagementTemplatePreview extends VwTemplatePreviewBase{

    static final String methodAdd = "FAccountTemplateAdd";
    static final String generalAdd ="FAccountAddAction";
    private DtoPmsAcnt dataPmsAcc = new DtoPmsAcnt();

    public boolean onClickBack(ActionEvent e) {
        return true;
    }

    public boolean onClickNext(ActionEvent e) {

        return true;

    }


    public boolean onClickFinish(ActionEvent e) {
        Long acntRid = null;
        if(param !=null){
            this.dataPmsAcc = (DtoPmsAcnt)param.get("dataPmsAcc");
            acntRid = (Long)param.get("acntrid");
        }

//        if(saveGeneralDate()){
//            ITreeNode rootpbs = null;
//            ITreeNode rootwbs = null;
//            TableModel tablepbs = vwTemplatePbsList.getTreeTable().getModel();
//            TableModel tablewbs = vwTemplateWbsList.getTreeTable().getModel();
//            VMTreeTableModelAdapter modelpbs = null;
//            VMTreeTableModelAdapter  modelwbs = null;
//            if (tablepbs instanceof VMTreeTableModelAdapter){
//                modelpbs = (VMTreeTableModelAdapter) tablepbs;
//            }
//            if(tablewbs instanceof VMTreeTableModelAdapter){
//                modelwbs = (VMTreeTableModelAdapter)tablewbs;
//            }
//            if (modelpbs != null) {
//                Object object = modelpbs.getTreeTableModel().getRoot();
//                if (object instanceof ITreeNode) {
//                    rootpbs = (ITreeNode) object;
//                }
//            }
//            if(modelwbs !=null){
//                Object object = modelwbs.getTreeTableModel().getRoot();
//                if (object instanceof ITreeNode) {
//                    rootwbs = (ITreeNode) object;
//                }
//            }
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.methodAdd);
            inputInfo.setInputObj("Account",acntRid);
           // inputInfo.setInputObj("RootWbs",rootwbs);
            inputInfo.setInputObj(DtoAcntKEY.DTO, this.dataPmsAcc);
            ReturnInfo returnInfo = accessData(inputInfo);
//        }
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

    }

//    public boolean saveGeneralDate(){
//
//        InputInfo inputInfo = new InputInfo();
//        inputInfo.setActionId(this.generalAdd);
//        inputInfo.setInputObj(DtoAcntKEY.DTO, this.dataPmsAcc);
//        ReturnInfo returnInfo = accessData(inputInfo);
//        return true;
//    }



}
