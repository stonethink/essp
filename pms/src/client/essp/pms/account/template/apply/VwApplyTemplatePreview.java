package client.essp.pms.account.template.apply;

import client.essp.pms.account.template.VwTemplatePreviewBase;
import java.awt.event.ActionEvent;
import com.wits.util.Parameter;
import client.framework.common.Constant;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import client.framework.model.VMTreeTableModelAdapter;
import client.framework.view.common.comMSG;
import c2s.dto.ITreeNode;
import javax.swing.table.TableModel;

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
public class VwApplyTemplatePreview extends VwTemplatePreviewBase {
    final String actionIdFin = "FAcntTemplateApplyFinAction";
    final String actionIdSave = "FAcntTemplateApplySaveAction";
//    private Parameter para=new Parameter();
    Long acntRid;//项目的rid
    Long apprAcntRid;//模板的rid
//    VwTemplatePbsList vwTemplatePbsList;
//    VwTemplateWbsList vwTemplateWbsList;
    public VwApplyTemplatePreview() {
        super();
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        acntRid = (Long) param.get("accountRid");
//        apprAcntRid = (Long) param.get("acntrid");
    }

    public boolean onClickBack(ActionEvent e) {
        //for test
//       client.framework.view.common.comMSG.dispMessageDialog("onClickBack in test ");
        return super.onClickBack(e);

    }

    public boolean onClickNext(ActionEvent e) {
        //for test
//       client.framework.view.common.comMSG.dispMessageDialog("onClickNext in test ");
        return super.onClickNext(e);

    }

    public boolean onClickFinish(ActionEvent e) {
        int retValue = client.framework.view.common.comMSG.dispConfirmDialog(
            "Are you sure to delete original PBS,WBS,Activity and WP?");
        if (retValue == Constant.CANCEL) {
            return false;
        } else {
            apprAcntRid = (Long) param.get("acntrid");
//            firstDelete();
            saveTree();
            param.put("isFinish","Finish");//成功的完成
        }
        return super.onClickFinish(e);
    }

    public boolean onClickCancel(ActionEvent e) {
        //for test
//       client.framework.view.common.comMSG.dispMessageDialog("onClickCancel in test ");
        return super.onClickCancel(e);
    }

//    public void firstDelete() {
//        InputInfo inputInfo = new InputInfo();
//        inputInfo.setActionId(actionIdFin);
//        inputInfo.setInputObj("acntRid", this.acntRid);
//        inputInfo.setInputObj("apprAcntRid", apprAcntRid);
//        ReturnInfo returnInfo = accessData(inputInfo);
//        if (returnInfo.isError() == true) {
//            return;
//        }
//    }

    public void saveTree() {
        ITreeNode rootpbs = null;
        ITreeNode rootwbs = null;
        TableModel tablepbs = vwTemplatePbsList.getTreeTable().getModel();
        TableModel tablewbs = vwTemplateWbsList.getTreeTable().getModel();
        VMTreeTableModelAdapter modelpbs = null;
        VMTreeTableModelAdapter modelwbs = null;
        if (tablepbs instanceof VMTreeTableModelAdapter) {
            modelpbs = (VMTreeTableModelAdapter) tablepbs;
        }
        if (tablewbs instanceof VMTreeTableModelAdapter) {
            modelwbs = (VMTreeTableModelAdapter) tablewbs;
        }
        if (modelpbs != null) {
            Object object = modelpbs.getTreeTableModel().getRoot();
            if (object instanceof ITreeNode) {
                rootpbs = (ITreeNode) object;
                if (rootpbs == null) {
                    comMSG.dispErrorDialog("This Pbs has not data!");
                }
            }
        }
        if (modelwbs != null) {
            Object object = modelwbs.getTreeTableModel().getRoot();
            if (object instanceof ITreeNode) {
                rootwbs = (ITreeNode) object;
                if (rootwbs == null) {
                    comMSG.dispErrorDialog("This Timing has not data!");
                }
            }
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdSave);
        inputInfo.setInputObj("RootPbs", rootpbs);
        inputInfo.setInputObj("RootWbs", rootwbs);
        inputInfo.setInputObj("acntRid", this.acntRid);
        inputInfo.setInputObj("Account", apprAcntRid);
        ReturnInfo returnInfo = accessData(inputInfo);
    }


}
