package server.essp.pms.qa.monitoring.action;

import c2s.dto.ReturnInfo;
import server.essp.pms.wbs.logic.LgCheckPoint;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckAction;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckPoint;
import c2s.essp.pms.wbs.DtoCheckPoint;
import c2s.essp.pms.qa.DtoQaCheckAction;
import c2s.essp.pms.qa.DtoMonitoringTreeNode;
import java.util.ArrayList;
import c2s.dto.ITreeNode;
import c2s.dto.IDto;
import c2s.dto.DtoUtil;
import server.essp.pms.qa.monitoring.logic.LgMonitoring;

public class AcQaMonitoringSave extends AbstractESSPAction  {
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
                           HttpServletResponse response, TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        DtoMonitoringTreeNode node = (DtoMonitoringTreeNode) inputInfo.getInputObj("RootNode");
        LgMonitoring logic = new LgMonitoring();
        logic.saveDate(node.listAllChildrenByPostOrder());
        returnInfo.setReturnObj("RootNode",node);
    }
}
