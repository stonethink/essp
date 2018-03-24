/*
 * Created on 2008-1-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attvariant.exporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;

import c2s.dto.DtoUtil;
import c2s.essp.timesheet.report.DtoAttVariant;
import c2s.essp.timesheet.report.DtoAttVariantQuery;
import com.wits.excel.ExcelExporter;
import com.wits.excel.ICellStyleSwitch;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;

/**
 * AttVariantExporter
 * 
 * @author TuBaoHui
 */
public class AttVariantExporter extends ExcelExporter {

	public static final String TEMPLATE_FILE = "Template_AttVariant.xls"; // 模板文件名

	public static final String OUT_FILE_PREFIX = "WITS-WH-AttVariant";

	public static final String OUT_FILE_POSTFIX = ".xls";

	public static final String OUT_FILE = OUT_FILE_PREFIX + OUT_FILE_POSTFIX; // 导出文件名

	// Sheet名称
	public static final String SHEEET_LEAVE_HOURS = "LeaveHours";

	public static final String SHEEET_OVERTIME_HOURS = "OvertimeHours";

	public AttVariantExporter() {
		super(TEMPLATE_FILE, OUT_FILE);
	}

	public AttVariantExporter(String outFile) {
		super(TEMPLATE_FILE, outFile);
	}

	public void doExport(Parameter inputData) throws Exception {
		Map map = (Map) inputData.get(DtoAttVariantQuery.DTO_RESULT_LIST);
		List leaveHoursList = (List) map
				.get(DtoAttVariantQuery.DTO_LEAVE_HOURS);
		if (leaveHoursList != null && leaveHoursList.size() > 0) {
			Parameter reportParam = new Parameter();
			reportParam.put("beanList", dto2StyleBean(leaveHoursList));
			HSSFSheet reportSheet = targetWorkbook.getSheet(SHEEET_LEAVE_HOURS);
			String reportSheetConfigFile = getSheetCfgFileName(SHEEET_LEAVE_HOURS);
			SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
					reportSheet, reportSheetConfigFile);
			reportSheetEx.export(reportParam);
		}

		List otHoursList = (List) map
				.get(DtoAttVariantQuery.DTO_OVERTIME_HOURS);
		if (otHoursList != null && otHoursList.size() > 0) {
			Parameter reportParam = new Parameter();
			reportParam.put("beanList", dto2StyleBean(otHoursList));
			HSSFSheet reportSheet = targetWorkbook
					.getSheet(SHEEET_OVERTIME_HOURS);
			String reportSheetConfigFile = getSheetCfgFileName(SHEEET_OVERTIME_HOURS);
			SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
					reportSheet, reportSheetConfigFile);
			reportSheetEx.export(reportParam);
		}
	}
	
	private static List dto2StyleBean(List<DtoAttVariant> srcList) {
		List destList = new ArrayList();
		for(DtoAttVariant dto : srcList) {
			AttVariantStyleBean bean = new AttVariantStyleBean();
			DtoUtil.copyBeanToBean(bean, dto);
			destList.add(bean);
		}
		return destList;
	}
	
	public static class AttVariantStyleBean extends DtoAttVariant implements ICellStyleSwitch {
		private final static String VARIANT_STYLE_NAME_TRUE = "Att_Variant_Style_Name_True";
		private final static String VARIANT_STYLE_NAME_FALSE = "Att_Variant_Style_Name_False";
		
		public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
			if(styleName == null) {
				return null;
			}
			if(VARIANT_STYLE_NAME_TRUE.equals(styleName)) {
				cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
	            cellStyle.setFillForegroundColor(HSSFColor.TAN.index);
	            return cellStyle;
			} else if(VARIANT_STYLE_NAME_FALSE.equals(styleName)) {
				cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
	            cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
	            return cellStyle;
			}
			return null;
		}

		public String getStyleName(String propertyName) {
			if(this.isVariant()) {
				return VARIANT_STYLE_NAME_TRUE;
			} else {
				return VARIANT_STYLE_NAME_FALSE;
			}
		}
		
	}
}
