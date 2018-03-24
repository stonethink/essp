/*
 * Created on 2008-5-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.step.monitoring.action;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.step.monitoring.service.IMonitoringService;
import server.framework.common.BusinessException;
import c2s.dto.ITreeNode;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.step.monitoring.DtoMonitoringQuery;

public class AcLoadMonitoringInfo extends AbstractESSPAction {

    @Override
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {

            IMonitoringService lg = (IMonitoringService) this
                    .getBean("monitoringService");
            String projId = (String)data.getInputInfo().
                       getInputObj(DtoMonitoringQuery.DTO_PROJECT_ID);
            Map map = lg.loadMonitoringInfo(this.getUser().getUserLoginId(), projId);
            ITreeNode treeNode = (ITreeNode)map.get(DtoMonitoringQuery.DTO_TREE_NODE);
            String projName = (String)map.get(DtoMonitoringQuery.DTO_PROJECT_NAME);
            data.getReturnInfo().setReturnObj(DtoMonitoringQuery.DTO_TREE_NODE, treeNode);
            data.getReturnInfo().setReturnObj(DtoMonitoringQuery.DTO_PROJECT_NAME, projName);

    }

}