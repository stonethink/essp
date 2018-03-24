/*
 * Created on 2008-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.step.monitoring.action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.timesheet.step.monitoring.export.MonitoringExport;
import server.framework.common.BusinessException;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.step.monitoring.DtoMonitoringQuery;

import com.wits.util.Parameter;

public class AcMonitoringExport extends AbstractExcelAction {
	public void execute(HttpServletRequest request,
			HttpServletResponse response, OutputStream os, Parameter param)
			throws Exception {
		ITreeNode treeNode = (ITreeNode) request.getSession().getAttribute(
				DtoMonitoringQuery.DTO_TREE_NODE);
		String projName = (String) request.getSession().getAttribute(
				DtoMonitoringQuery.DTO_PROJECT_NAME);
		request.getSession().removeAttribute(DtoMonitoringQuery.DTO_TREE_NODE);
		request.getSession().removeAttribute(
				DtoMonitoringQuery.DTO_PROJECT_NAME);
		Parameter inputData = new Parameter();
		inputData.putAll(param);
		inputData.put(DtoMonitoringQuery.DTO_TREE_NODE, treeNode);
		inputData.put(DtoMonitoringQuery.DTO_PROJECT_NAME, projName);
		try {
			MonitoringExport utilExporter = new MonitoringExport();
			utilExporter.webExport(response, os, inputData);
		} catch (Exception ex) {
			throw new BusinessException("Error",
					"Error when Call export() 0f SampleExcelExporter ", ex);
		}
	}
}
