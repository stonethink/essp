package server.essp.pms.qa.monitoring.action;

import c2s.essp.pms.wbs.DtoWbsActivity;
import server.essp.pms.wbs.logic.LgWbs;
import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.dto.InputInfo;
import c2s.dto.ITreeNode;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import java.util.List;
import server.framework.common.BusinessException;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import server.essp.pms.qa.monitoring.logic.LgMonitoring;
import c2s.dto.ReturnInfo;
import client.essp.pms.qa.monitoring.VwMonitoringList;
import java.util.Date;
import c2s.essp.pms.qa.DtoQaCheckAction;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckAction;

public class AcQaMonitoringList extends AbstractESSPAction {
    public AcQaMonitoringList() {
        super();
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        //UserTest.test();
        IDtoAccount accountDto = (IDtoAccount)this.getSession().getAttribute(
            IDtoAccount.SESSION_KEY);
        if (accountDto == null) {
            returnInfo.setError(true);
            returnInfo.setReturnMessage("Please select a account at first!!!");
            return;
        }
        if (accountDto.getRid() == null) {
            returnInfo.setError(true);
            returnInfo.setReturnMessage("Account'Rid is null!!!");
            return;
        }
        String fitlerType = (String) inputInfo.getInputObj(VwMonitoringList.
            KEY_FILTER_TYPE);
        String filterStatus = (String) inputInfo.getInputObj(VwMonitoringList.
            KEY_FILTER_STATUS);
        String planPerformer = (String) inputInfo.getInputObj(VwMonitoringList.
            KEY_PLAN_PERFORMER);
        Date planStartDate = (Date) inputInfo.getInputObj(VwMonitoringList.
            KEY_PLAN_DATE);
        Date planFinishDate = (Date) inputInfo.getInputObj(VwMonitoringList.
            KEY_PLAN_FINISH_DATE);
        String actualPerformer = (String) inputInfo.getInputObj(
            VwMonitoringList.KEY_ACTUAL_PERFORMER);
        Date actualStartDate = (Date) inputInfo.getInputObj(VwMonitoringList.
            KEY_ACTUAL_DATE);
        Date actualFinishDate = (Date) inputInfo.getInputObj(VwMonitoringList.
            KEY_ACTUAL_FINISH_DATE);
        LgMonitoring monitoringLogic = new LgMonitoring(fitlerType,
            filterStatus, planPerformer, planStartDate, planFinishDate,
            actualPerformer, actualStartDate, actualFinishDate);
        ITreeNode treeNode = monitoringLogic.getCheckTree(accountDto.getRid(), false);
        LgQaCheckAction lgAction = new LgQaCheckAction();
        List qaLaborResList = lgAction.getQaLaborRes();

        //增加界面菜单的参数,SEPG可任意修改,其他菜单保持不变
        String entryFunType = (String) inputInfo.getInputObj("entryFunType");
        if (DtoAcntKEY.SEPG_ACCOUNT_FUN.equals(entryFunType)) {

            if (treeNode != null) {
                DtoQaMonitoring dto = (DtoQaMonitoring) treeNode.getDataBean();
                DtoWbsActivity dtoWbsActivity = dto.getDtoWbsActivity();
                if (dtoWbsActivity != null) {
                    dtoWbsActivity.setReadonly(false);
                }
                List allChild = treeNode.listAllChildrenByPostOrder();
                for (int i = 0; i < allChild.size(); i++) {
                    ITreeNode node = (ITreeNode) allChild.get(i);
                    dto = (DtoQaMonitoring) node.getDataBean();
                    dtoWbsActivity = dto.getDtoWbsActivity();
                    if (dtoWbsActivity != null) {
                        dtoWbsActivity.setReadonly(false);
                    }
                }
            }
        }

        returnInfo.setReturnObj(DtoQaCheckAction.PMS_QA_LABORRES_LIST,
                                qaLaborResList);
        returnInfo.setReturnObj("MONITORINGTREE", treeNode);
    }
}
