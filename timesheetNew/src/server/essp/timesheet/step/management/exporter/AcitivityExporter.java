package server.essp.timesheet.step.management.exporter;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import c2s.dto.ITreeNode;
import c2s.essp.timesheet.step.management.DtoActivityAndStep;

import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;

public class AcitivityExporter extends ExcelExporter{
	public static final String TEMPLATE_FILE = "Template_Activity_And_Step.xls";//模板文件名
    public static final String OUT_FILE = "Activity_And_Step.xls";//导出文件名
    
	public AcitivityExporter() {
		super(TEMPLATE_FILE, OUT_FILE);
	}
	
	public AcitivityExporter(String outFile) {
    	super(TEMPLATE_FILE, outFile);
    }
	
    public static final String SHEEET_RESULT = "ActivityStep";
    
    public void doExport(Parameter inputData) throws Exception {
    	
    	ITreeNode node = (ITreeNode) inputData.get(DtoActivityAndStep.ACTIVITY_LIST);
       	Parameter reportParam = new Parameter();
    	reportParam.put("beanList", node);    	 
        HSSFSheet tsDetailSheet = targetWorkbook.getSheet(SHEEET_RESULT);
        String tcSheetConfigFile = getSheetCfgFileName(SHEEET_RESULT);
        SheetExporter se = new SheetExporter(targetWorkbook, tsDetailSheet, tcSheetConfigFile);
        se.export(reportParam);      	
    }

}
