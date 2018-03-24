package server.essp.timesheet.step.management.action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.timesheet.step.management.exporter.AcitivityExporter;
import server.essp.timesheet.step.management.service.IStepManagementService;
import server.framework.common.BusinessException;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.step.management.DtoActivityAndStep;

import com.wits.util.Parameter;

public class AcAcitivityExporter extends AbstractExcelAction {

	/**
	 * export all the step under the export
	 */
	public void execute(HttpServletRequest requset, HttpServletResponse response,
			OutputStream os, Parameter param) throws Exception {
		String actStr =(String)param.get("activityIds");
		IStepManagementService service=(IStepManagementService)this.getBean("stepManagementService");
		service.setExcelDto(true);
		ITreeNode node=service.getDateByActObjId(actStr);	
		Parameter inputData = new Parameter();
		inputData.put(DtoActivityAndStep.ACTIVITY_LIST, node);
		String fileName = AcitivityExporter.OUT_FILE;
		try {
			AcitivityExporter exporter = new AcitivityExporter(fileName);
            exporter.webExport(response, os, inputData);
          } catch (Exception ex) {
             throw new BusinessException("Error",
                "Error when Call export() 0f SampleExcelExporter ", ex);
           }
	}
	
	public void localExport(String activitys) {
		IStepManagementService service=(IStepManagementService)this.getBean("stepManagementService");
		service.setExcelDto(true);
		ITreeNode node=service.getDateByActObjId(activitys);	
		Parameter inputData = new Parameter();
		inputData.put(DtoActivityAndStep.ACTIVITY_LIST, node);
		String fileName = AcitivityExporter.OUT_FILE;
		try {
			AcitivityExporter exporter = new AcitivityExporter(fileName);
            exporter.export(inputData);
          } catch (Exception ex) {
             throw new BusinessException("Error",
                "Error when Call export() 0f SampleExcelExporter ", ex);
           }
	}
	
	public static void main(String[] args) {
		AcAcitivityExporter exporter = new AcAcitivityExporter();
		exporter.localExport("105573,105574,105575,105576,105577,105578,143382,143536");
	}

}
