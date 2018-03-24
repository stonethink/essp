package client.essp.pms.activity;

import client.essp.pms.wbs.VwWbsGeneralBase;
import c2s.dto.ITreeNode;
import com.wits.util.Parameter;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.dto.ReturnInfo;
import client.framework.model.VMComboBox;
import c2s.dto.InputInfo;
import client.framework.view.vwcomp.VWUtil;
import java.util.List;
import java.util.Vector;
import c2s.dto.DtoUtil;

public class VwWbsDisp extends VwWbsGeneralBase {
    private static final String actionIdAdd = "FWbsAddAction";
    private static final String actionIdUpdate = "FWbsUpdateAction";
    private static final String actionIdGetManagers = "FWbsGetManagers";
    public static final String IS_WBS_DISP="isWbsDisp";

    private ITreeNode treeNode;
    private DtoWbsActivity dataBean;
    private boolean hasGetManagers;
    private VMComboBox managers;

    public VwWbsDisp() {
        super(VwWbsDisp.IS_WBS_DISP);
        name.setEnabled(false);
        code.setEnabled(false);
        manager.setEnabled(false);
        weight.setEnabled(false);
        plannedStart.setEnabled(false);
        plannedFinish.setEnabled(false);
//        anticipatedStart.setEnabled(false);
//        anticipatedFinish.setEnabled(false);
        //actualStart.setEnabled(false);
        //actualFinish.setEnabled(false);
        brief.setEnabled(false);
        completeMethod.setEnabled(false);
        completeManualInput.setEnabled(false);
        completeManualInput.setVisible(false);
        completeLabel_percentSingle.setVisible(false);
    }

    public void setParameter(Parameter parameter) {
        treeNode = (ITreeNode) parameter.get(DtoKEY.WBSTREE);
        dataBean = (DtoWbsActivity) treeNode.getDataBean();
        updateData();
    }

    public void updateData() {
        VWUtil.bindDto2UI(dataBean, this);
        if (!hasGetManagers) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionIdGetManagers);
            inputInfo.setInputObj(DtoKEY.MANAGERS,
                                  ((DtoWbsActivity) dataBean).getAcntRid());
            ReturnInfo returnInfo = this.accessData(inputInfo);
            if (!returnInfo.isError()) {
                List managerList=(List)returnInfo.getReturnObj(
                    DtoKEY.MANAGERS);
                if (managerList != null) {
                    managers = new VMComboBox(new Vector(managerList));
                    VMComboBox managersCopy = (VMComboBox) DtoUtil.deepClone(
                        managers);
                    manager.setModel(managersCopy);
                    hasGetManagers = true;
                }
            }
        }

        VWUtil.initComboBox(manager,managers,dataBean.getManager(),dataBean.getManager());
    }
}
