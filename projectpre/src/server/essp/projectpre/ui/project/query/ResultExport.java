/*
 * Created on 2007-4-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.project.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.poi.hssf.usermodel.HSSFSheet;

import server.essp.projectpre.service.account.AccountServiceImpl;
import server.essp.projectpre.service.account.IAccountService;

import com.wits.excel.ExcelExporter;
import com.wits.excel.ICellExporter;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;
import com.wits.util.comDate;
/**
 * 导出excel的类
 * @author wenhaizheng
 */
public class ResultExport extends ExcelExporter {
    public static final String TEMPLATE_FILE = "Template_PP_Acnt.xls"; //Excel模板文件名
    public static String OUT_FILE = "PP_Acnt_Info_"
                                          +comDate.dateToString(new Date(), "yyyyMMddHHmmss")
                                          +".xls"; //Excel导出文件名
    //Sheet名称
    public static final String SHEET_ACNTINFO = "Account_Info";

    public ResultExport() {
        super(TEMPLATE_FILE, OUT_FILE);
    }

    public ResultExport(String out_file) {
        super(TEMPLATE_FILE, out_file);
    }

    public ResultExport(String out_dir, String out_file) {
        super(TEMPLATE_FILE, out_dir, out_file);
    }
    /**
     * 导出excel的方法
     */
    public void doExport(Parameter inputData) throws Exception {
        Map hqlMap = (Map)inputData.get("HQLMAP");
        Map conditionMap = (Map)inputData.get("conditionMap");
        Parameter acntParam = new Parameter();
        acntParam.put("tblData", getExportData(hqlMap));
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
     * 获取要导出专案的数据
     * @param hqlMap
     * @return
     */
    private List getExportData(Map hqlMap){
        List dataList = new ArrayList();
        IAccountService logic = new AccountServiceImpl();
        dataList = logic.queryDataToExport(hqlMap);
        return dataList;
    }
    /**
     * 将不需要显示的列隐藏起来
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
    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args) {
        ResultExport result = new ResultExport();
        Parameter inputData = new Parameter();
        String hql = "from QueryAcntView as t where 1=1 and t.acntId like '%00354%'";
        inputData.put("HQL", hql);
        try {
            result.export(inputData);
            System.out.println("Export OK");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
