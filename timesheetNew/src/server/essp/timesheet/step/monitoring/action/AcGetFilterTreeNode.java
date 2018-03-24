/*
 * Created on 2008-6-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.step.monitoring.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.ITreeNode;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.step.monitoring.DtoMonitoringQuery;

public class AcGetFilterTreeNode extends AbstractESSPAction {

    @Override
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            ITreeNode treeNode = (ITreeNode)data.getInputInfo().
                    getInputObj(DtoMonitoringQuery.DTO_TREE_NODE);
            String prjName = (String)data.getInputInfo().getInputObj(
                     DtoMonitoringQuery.DTO_PROJECT_NAME);
            request.getSession().setAttribute(DtoMonitoringQuery.
                    DTO_TREE_NODE,treeNode);
            request.getSession().setAttribute(DtoMonitoringQuery.DTO_PROJECT_NAME
                    ,prjName);

    }

}