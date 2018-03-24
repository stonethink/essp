package server.essp.hrbase.humanbase.action;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.wits.excel.*;
import com.wits.util.Parameter;

public class HumanBaseExporter extends ExcelExporter {

	public static final String TEMPLATE_FILE = "Template_HumanBase.xls"; //Excelģ���ļ���
    public static String OUT_FILE = "HumanBase_Data.xls"; //Excel�����ļ���
    //Sheet����
    public static final String SHEET_ACNTINFO = "Human_Base_Data";

    public HumanBaseExporter() {
        super(TEMPLATE_FILE, OUT_FILE);
    }
    /**
     * ����excel�ķ���
     */
    public void doExport(Parameter inputData) throws Exception {
        List list = (List)inputData.get("list");
        Map conditionMap = (Map)inputData.get("conditionMap");
        Parameter acntParam = new Parameter();
        acntParam.put("tblData", list);
        HSSFSheet communicationsSheet = targetWorkbook.getSheet(
                SHEET_ACNTINFO);
        String communicationsSheetConfigFile = getSheetCfgFileName(
                SHEET_ACNTINFO);
        SheetExporter acntExporter = new SheetExporter(targetWorkbook,
            communicationsSheet, communicationsSheetConfigFile);
        acntExporter.export(acntParam);
        this.setHidden(acntExporter,communicationsSheet, conditionMap);
    }
    /**
     * ������Ҫ��ʾ������������
     * @param acntExporter
     * @param communicationsSheet
     * @param conditionMap
     */
    private void setHidden(SheetExporter acntExporter, HSSFSheet communicationsSheet, Map conditionMap){
        List cellsList = acntExporter.getCellExporters();
        int size = cellsList.size();
        for(int i = 0;i<size;i++){
            ICellExporter cellExporter = (ICellExporter)cellsList.get(i);
            if(!conditionMap.containsKey(cellExporter.getPropertyName())){
                communicationsSheet.setColumnWidth((short)(cellExporter
                                                          .getPosition().getColumn()-1),(short)0);
            }
        }
    }
}
