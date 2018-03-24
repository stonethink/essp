package server.essp.timecard.timecard.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import com.wits.util.Parameter;
import com.wits.util.comDate;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import server.essp.timecard.timecard.logic.ExcelLogic;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;

public class Excel {
    /**
     * 输出的文件名（或由系统自动产生）
     */
    private String OutPutFileName = "d:/test.xls";

    /**
     * 模板文件名
     */
    private String TempleteFileName = "d:/sample.xls";

    /**
     * 列表结构
     */
    private static Object[][] tblConfig;

    /**
     * 需要导出的数据
     */
    private List vddn = new ArrayList();

    public Excel() {
    }

    /**
     * 实例化，并声明需要从哪一行开始保留数据，保留多少行
     *
     * @param TempleteFile 模版文件名
     * @param OutPutFile 输出文件名
     * @throws Exception
     */
    public Excel(String TempleteFile, String OutPutFile) throws Exception {

        if (TempleteFile == null || TempleteFile.equals("")) {
            throw new Exception("Templete FileName is null");
        }
        if (OutPutFile == null || OutPutFile.equals("")) {
            throw new Exception("OutPut FileName is null");
        }

        OutPutFileName = OutPutFile;
        TempleteFileName = TempleteFile;

    }

    /*********************
     * 根据header来将data放入列表
     *
     * @param sheet
     * @param RowNO 写入excel的行号
     * @param header 配置文件
     * @param data 数据
     * @param DataNO 数据的行号
     */
    public void addRow(HSSFSheet sheet, int RowNO, Object[][] header, List data, int DataNO) {
        HSSFRow rowtmp = sheet.createRow(RowNO);
        for (int j = 0; j < header.length; j++) {
            HSSFCell cell = rowtmp.createCell( (short) j);
            String cellValue = "";
            Object tctmcd = (Object) data.get(DataNO);
            try {
                String viewType = "";
                if (header[j].length > 3) {
                    viewType = (String) header[j][3];
                }
                if (!viewType.equals("")) {
                    Date viewDate = (Date) DtoUtil.getProperty(tctmcd, (String) header[j][1]);
                    cellValue = comDate.dateToString(viewDate, viewType);
                } else {
                    cellValue = ( (Object) DtoUtil.getProperty(tctmcd, (String) header[j][1])).toString();
                }
            } catch (Exception ex) {
                //System.out.println(RowNO+1+" line "+(String) header[j][0]+" is null! ");
                //ex.printStackTrace();
            }
            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell.setCellValue(cellValue);
        }
    }

    /*****************
     * 产生excel文件
     *
     * @param data 数据
     */
    public boolean getForExcel(List data) {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(new FileInputStream(TempleteFileName));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        String titleName = wb.getSheetName(0);
        HSSFSheet sheet = wb.getSheet(titleName);
        //wb.setSheetName(0, "中文测试", HSSFCell.ENCODING_UTF_16);
        //create list of file
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        for (int i = 0; i < rowCount; i++) {
            HSSFRow row = sheet.getRow(i);
            for (short j = 0; j < colCount; j++) {
                int col = j;

                HSSFCell cell = row.getCell( (short) col);
                if (cell != null) {
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        System.out.println("row " + i + " column " + j +
                                           " value is " + cell.getNumericCellValue());

                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                        System.out.println("formula row " + i + " column " + j +
                                           " value is " + cell.getNumericCellValue());

                        if (j == 2) {
                            //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                            cell.setCellFormula("A2+B2");
                        }
                    } else {
                        if (cell.getStringCellValue().equals("") == false) {
                            System.out.println("row " + i + " column " + j +
                                               " value is " +
                                               cell.getStringCellValue());
                            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        }
                    }
                }
            }
        }
        for (int k = 0; k < data.size(); k++) {
            addRow(sheet, rowCount - 1, tblConfig, data, k);
            rowCount++;
        }

        try {
            //使用默认的构造方法创建workbook
            FileOutputStream fileOut = new FileOutputStream(OutPutFileName);
            //指定文件名
            wb.write(fileOut);
            //输出到文件
            fileOut.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public static void main(String[] args) {
        Excel ex = new Excel();
        //设定模版文件
        ex.setTempleteFileName("d:/sample.xls");
        //设定输出文件
        ex.setOutPutFileName("d:/test.xls");
        //设定配置excel列表的文件
        Object[][] tbSConfig = { {"EMPLOYEE", "tmcEmpName", "Editable", ""}
            , {"Position Type", "tmcEmpPositionType", "Editable", ""}
            , {"Start", "tmcWeeklyStart", "Editable", "yyyy-MM-dd"}
            , {"Finish", "tmcWeeklyFinish", "Editable", "yyyy-MM-dd"}
            , {"Actual", "tmcActualWorkHours", "Editable"}
            , {"Allocatted", "tmcAllocatedWorkHours", "Editable"}
            , {"Offset Work", "tmcAttenOffsetWork", "Editable"}
            , {"Overtime", "tmcAttenOvertime", "Editable"}
            , {"Vacation", "tmcAttenVacation", "Editable"}
            , {"Shift-Adjustment", "tmcAttenShiftAdjustment", "Editable"}
            , {"Private", "tmcAttenPrivateLeave", "Editable"}
            , {"Sick", "tmcAttenSickLeave", "Editable"}
            , {"Absence", "tmcAttenAbsence", "Editable"}
            , {"Breaking Rules", "tmcAttenBreakingRules", "Editable"}
        };
        ex.setTblConfig(tbSConfig);

        List data = new ArrayList();
        ExcelLogic logic = new ExcelLogic();
        logic.setDbAccessor(new HBComAccess());
        Parameter param = new Parameter();

        String funId = "getTmCdRpList";
        TransactionData transData = new TransactionData();
        InputInfo inputInfo = transData.getInputInfo();
        inputInfo.setFunId(funId);
        inputInfo.setInputObj("inStartTime", "2005-07-02");
        inputInfo.setInputObj("inFinishTime", "2005-07-08");

        param.put("TransactionData", transData);

        try {
            logic.executeLogic(param);
        } catch (BusinessException ex1) {
        }

        transData = (TransactionData) param.get("TransactionData");
        ReturnInfo returnInfo = transData.getReturnInfo();
        if (!returnInfo.isError()) {
            data = ( (List) returnInfo.getReturnObj("tcrList"));
        }
        //导出数据到excel文件
        boolean resultFlag = ex.getForExcel(data);
    }

    public String getOutPutFileName() {
        return OutPutFileName;
    }

    public void setOutPutFileName(String OutPutFileName) {
        this.OutPutFileName = OutPutFileName;
    }

    public List getVddn() {
        return vddn;
    }

    public String getTempleteFileName() {
        return TempleteFileName;
    }

    public void setTempleteFileName(String TempleteFileName) {
        this.TempleteFileName = TempleteFileName;
    }

    public Object[][] getTblConfig() {
        return tblConfig;
    }

    public void setTblConfig(Object[][] tblConfig) {
        this.tblConfig = tblConfig;
    }

}
