/*
 * Created on 2008-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.step.monitoring.export;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;

import server.essp.timesheet.step.monitoring.service.MonitoringServiceImp.StyledDtoMonitoring;
import c2s.dto.DtoUtil;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.step.management.DtoStepBase;
import c2s.essp.timesheet.step.monitoring.DtoMonitoring;
import c2s.essp.timesheet.step.monitoring.DtoMonitoringQuery;
import com.wits.excel.ExcelExporter;
import com.wits.excel.ICellStyleSwitch;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;

public class MonitoringExport extends ExcelExporter{

    public static final String TEMPLATE_FILE = "Template_TsStepMonitoring.xls"; //模板文件名
    public static final String OUT_FILE_PREFIX ="WITS-WH-StepMonitoring";
    public static final String OUT_FILE_POSTFIX =".xls";
    public static final String OUT_FILE = OUT_FILE_PREFIX + OUT_FILE_POSTFIX; //导出文件名

   //Sheet名称
   public static final String SHEEET_TIME_SHEET = "Monitoring";

   public MonitoringExport() {
       super(TEMPLATE_FILE, OUT_FILE);
   }

   public MonitoringExport(String outFile) {
       super(TEMPLATE_FILE, outFile);
   }


    public void doExport(Parameter inputData) throws Exception {
        ITreeNode treenNode = (ITreeNode)inputData.get(DtoMonitoringQuery.
                DTO_TREE_NODE);
        String projName = (String)inputData.get(DtoMonitoringQuery.DTO_PROJECT_NAME);
        exchangeStyleBean(treenNode);
        Parameter reportParam = new Parameter();
        reportParam.put("projectName", projName);
        reportParam.put("beanList", treenNode);
       
        HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_TIME_SHEET);
        String reportSheetConfigFile = getSheetCfgFileName(SHEEET_TIME_SHEET);

        SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
            reportSheet, reportSheetConfigFile);
        reportSheetEx.export(reportParam);
    }
    
    private void exchangeStyleBean(ITreeNode root) {
        if(root == null) return;
    	List<ITreeNode> list = root.listAllChildrenByPreOrder();
    	for(ITreeNode node : list) {
    		DtoMonitoring bean = (DtoMonitoring)node.getDataBean();
    		StyledDtoMonitoring dto = new StyledDtoMonitoring();
    		DtoUtil.copyBeanToBean(dto, bean);
    		dto.setColorIndex(bean.getColorIndex());
    		node.setDataBean(dto);
    	}
    }
    
    
    private static short checkStatusIndicatorColor(StyledDtoMonitoring dto) {
    	if(DtoStepBase.NORMAL.equals(dto.getStatusIndicator())) {
			 return HSSFColor.BRIGHT_GREEN.index;
		 } else if(DtoStepBase.DELAY.equals(dto.getStatusIndicator())) {
			 return HSSFColor.RED.index;
		 } else if(DtoStepBase.AHEAD.equals(dto.getStatusIndicator())) {
			 return HSSFColor.BLUE.index;
		 } else {
			 return dto.getColorIndex();
		 }
  	 }

      public class StyledDtoMonitoring extends DtoMonitoring
           implements ICellStyleSwitch {
        public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
           if (styleName != null && !"".equals(styleName)) {
               cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
               cellStyle.setFillForegroundColor(Short.valueOf(styleName));
           }
            return cellStyle;
        }

        public String getStyleName(String propertyName) {
       	 if("statusIndicator".equals(propertyName)) {
  			  return checkStatusIndicatorColor(this) + "";
  		  }
  		  if(this.getColorIndex() > 0) {
  			  return this.getColorIndex() + "";
  		  } else {
  			  return null;
  		  }
       }
   }

}
