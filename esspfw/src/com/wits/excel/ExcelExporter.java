package com.wits.excel;

import com.wits.util.Config;
import org.apache.log4j.Category;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.wits.util.Parameter;
import com.wits.util.PathGetter;
import java.net.URL;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.*;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.util.StringUtil;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

public class ExcelExporter {
    //template dir/ config dir从包根目录开始，以"/结尾"或为空
    final static String KEY_TPL_DIR = "TPL_DIR";
    final static String KEY_CFG_DIR = "CFG_DIR";
    //output dir从web目录开始，包含web文件夹，以"/结尾"或为空
    //比如"essp/hr/timecard/weeklyreport/"
    final static String KEY_OUT_DIR = "OUT_DIR";

    protected static Config cfg = new Config("excel"); //bad Constant!!!A parameter maybe better.
    protected static Category log = Category.getInstance("");

    protected static String tplDir = cfg.getValue(KEY_TPL_DIR);
    protected static String cfgDir = cfg.getValue(KEY_CFG_DIR);
    protected static String outDir = cfg.getValue(KEY_OUT_DIR);

    //模板文件
    protected HSSFWorkbook tplWorkbook;
    protected String tplFileName;
    //生成的目标文件
    protected HSSFWorkbook targetWorkbook ;
    protected String out_Dir="";//由调用程序指定的输出路径，此时不会用outDir
    protected boolean useOut_Dir=false;//是否以out_Dir作为输出文件的路径
    protected String targetFileName;

    private boolean newTarget = false;
    /**
     * newTarget表示输出是否直接将数据写入模板文件,还是新建一个模板拷贝然后将数据写入拷贝中
     */
    public ExcelExporter(String tplFileName, String outFileName,boolean newTarget) {
        this.tplFileName = tplFileName;
        this.targetFileName = outFileName;

        if (tplDir == null) {
            tplDir = "";
        }
        //读入模板文件,生成对应的WorkBook
        String absTplFileName = tplDir + tplFileName;
        // Read the template file
        log.info("absTplFileName:" + absTplFileName);
        URL tplFileUrl = ExcelExporter.class.getResource(absTplFileName);
        POIFSFileSystem fs = null;
        try {
            fs = new POIFSFileSystem(tplFileUrl.openStream());
            tplWorkbook = new HSSFWorkbook(fs);
        } catch (IOException ex) {
            throw new BusinessException("", "error reading template!", ex);
        }

        targetWorkbook = newTarget ? new HSSFWorkbook() : tplWorkbook;
        this.newTarget = newTarget;
    }

    public ExcelExporter(String tplFileName, String outFileName) {
        this(tplFileName,outFileName,false);
    }

    public ExcelExporter(String tplFileName,String out_Dir,String outFileName){
        this.tplFileName = tplFileName;
        this.out_Dir=out_Dir;
        this.targetFileName = outFileName;
        this.useOut_Dir=true;
        this.newTarget=false;
        if (tplDir == null) {
            tplDir = "";
        }
        //读入模板文件,生成对应的WorkBook
        String absTplFileName = tplDir + tplFileName;
        // Read the template file
        log.info("absTplFileName:" + absTplFileName);
        URL tplFileUrl = ExcelExporter.class.getResource(absTplFileName);
        POIFSFileSystem fs = null;
        try {
            fs = new POIFSFileSystem(tplFileUrl.openStream());
            tplWorkbook = new HSSFWorkbook(fs);

        } catch (IOException ex) {
            throw new BusinessException("", "error reading template!", ex);
        }

        targetWorkbook = newTarget ? new HSSFWorkbook() : tplWorkbook;
        this.newTarget = newTarget;

    }

    /**
     * 数据写入目标文件
     * @param inputData Parameter
     * @throws Exception
     */
    public void export(Parameter inputData) throws Exception {
        if (outDir == null) {
            outDir = "";
        }
        //---生成输入文件对应的路径----
        String targetFolderName;
        if(useOut_Dir==true){
         targetFolderName=PathGetter.getStandardDir()+out_Dir;
         useOut_Dir=false;
        }else{
            targetFolderName= PathGetter.getStandardDir() + outDir;
        }
        String abstargetFileName = targetFolderName + targetFileName;
        log.info("outFolderName" + targetFolderName);
        log.info("absOutFileName" + abstargetFileName);

        doExport(inputData);

        //输出targetWorkbook到目标文件
        File outFolder = new File(targetFolderName);
        if (outFolder.exists() == false) {
            outFolder.mkdirs();
            log.info("create folder -- " + targetFolderName);
        }

        FileOutputStream fileOut = new FileOutputStream(abstargetFileName);
        targetWorkbook.write(fileOut);
        fileOut.close();
    }

    /**
     * 用Web方式导出,写入Response输出流中
     * @param res HttpServletResponse
     * @param out OutputStream
     * @param inputData Parameter
     * @throws Exception
     */
    public void webExport(HttpServletResponse res, OutputStream out, Parameter inputData) throws Exception {
        if (outDir == null) {
            outDir = "";
        }

        doExport(inputData);

        try {
            res.setContentType("application/vnd.ms-excel");

            res.setHeader("Content-Disposition",
                          "attachment;filename=" + this.targetFileName);

            targetWorkbook.write(out);
            out.close();
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    //将数据写入TargetWorkBook
    protected void doExport(Parameter inputData) throws Exception {
        log.info("execute doExport(Parameter inputData)");
        for (int i = 0; i < tplWorkbook.getNumberOfSheets(); i++) {
            HSSFSheet sheet = null;
            if(newTarget){
                 sheet = createTargetSheetAt(i);
            }else{
                sheet = tplWorkbook.getSheetAt(i);
            }

            String tplSheetName = tplWorkbook.getSheetName(i);
            String sheetConfigFile = getSheetCfgFileName(tplSheetName);
            SheetExporter se = new SheetExporter(targetWorkbook,sheet,sheetConfigFile);

            se.export(inputData);
        }
    }
    //根据模板文件的Sheet在Target中创建对应的Sheet
    protected HSSFSheet createTargetSheetAt(int index){
        HSSFSheet tplSheet = tplWorkbook.getSheetAt(index);
        if(tplSheet == null)
            throw new BusinessException("","template sheet ["+index+"] does not exist in ["+tplFileName+"]");
        return cloneSheet(tplSheet,
                          targetWorkbook,tplWorkbook.getSheetName(index));
    }
    protected HSSFSheet createTargetSheetByName(String tplSheetName,String targetSheetName){
        HSSFSheet tplSheet = tplWorkbook.getSheet(tplSheetName);
        if(tplSheet == null)
            throw new BusinessException("","template sheet ["+tplSheetName+"] does not exist in ["+tplFileName+"]");
        return cloneSheet(tplSheet,targetWorkbook,targetSheetName);
    }
    protected HSSFSheet createTargetSheetByName(String tplSheetName){
        return createTargetSheetByName(tplSheetName,tplSheetName);
    }

    /**
     * 在targetWorkbook中复制指定Sheet
     * 1.在TargetWorkbook中创建一个新的Sheet
     * 2.将模板sheet从第0行到结束行复制到新的Sheet
     * @param tplSheet HSSFSheet
     * @param tplWorkBook HSSFWorkbook
     * @param targetWorkbook HSSFWorkbook
     * @param targetSheetName String
     * @return HSSFSheet
     */
    private HSSFSheet cloneSheet(HSSFSheet tplSheet,HSSFWorkbook targetWorkbook,String targetSheetName){
        //需复制的行号区间
        int startRowNum = 0;
        int endRowNum = tplSheet.getPhysicalNumberOfRows();

        HSSFSheet targetSheet = targetWorkbook.createSheet(targetSheetName);

//        targetSheet.createFreezePane(1,1);
        if ((startRowNum  ==  -1)  ||  (endRowNum  ==  -1)) {
            return targetSheet;
        }

        int  cType;
        int  i;
        short  j;
        //拷贝合并的单元格
        Region  region  =  null;
        int  targetRowFrom;
        int  targetRowTo;
        for  (i  =  0;  i  <  tplSheet.getNumMergedRegions();  i++) {
            region  =  tplSheet.getMergedRegionAt(i);
            if  ((region.getRowFrom()  >=  startRowNum)  &&  (region.getRowTo()  <=  endRowNum)) {
                targetRowFrom  =  region.getRowFrom()  -  startRowNum  ;
                targetRowTo  =  region.getRowTo()  -  startRowNum  ;
                region.setRowFrom(targetRowFrom);
                region.setRowTo(targetRowTo);
                targetSheet.addMergedRegion(region);
            }
        }
        //设置列宽
        HSSFRow  sourceRow  =  null;
        HSSFRow  targetRow  =  null;
        for  (i  =  startRowNum;  i  <=  endRowNum;  i++) {
            sourceRow  =  tplSheet.getRow(i);
            if  (sourceRow  !=  null) {
                for  (j  =  sourceRow.getFirstCellNum();  j  <  sourceRow.getLastCellNum();  j++)                 {
                    targetSheet.setColumnWidth(j,  tplSheet.getColumnWidth(j));
                }
                break;
            }
        }
        //拷贝行并填充数据
        HSSFCell  sourceCell  =  null;
        HSSFCell  targetCell  =  null;
        HSSFCellStyle sourceCellStyle = null;
        HSSFCellStyle targetCellStyle = null;
        for  (;i  <=  endRowNum;  i++) {
            sourceRow  =  tplSheet.getRow(i);
            if  (sourceRow  ==  null) {
                continue;
            }
            targetRow  =  targetSheet.createRow(i  -  startRowNum );
            targetRow.setHeight(sourceRow.getHeight());
            for  (j  =  sourceRow.getFirstCellNum();  j  <  sourceRow.getLastCellNum();  j++) {
                sourceCell  =  sourceRow.getCell(j);
                if  (sourceCell  ==  null) {
                    continue;
                }
                cType  =  sourceCell.getCellType();
                //cell是公式类型时Copy产生错误
                if(cType ==  HSSFCell.CELL_TYPE_FORMULA)
                    continue;
                targetCell  =  targetRow.createCell(j);
                targetCell.setEncoding(sourceCell.getEncoding());

                sourceCellStyle = sourceCell.getCellStyle();
                targetCellStyle = cloneStyle(targetWorkbook,sourceCellStyle);
//                System.out.println("set syle:" + i + "," +j);
                targetCell.setCellStyle(targetCellStyle);
                targetCell.setCellType(cType);
                switch  (cType) {
                case  HSSFCell.CELL_TYPE_BOOLEAN:
                    targetCell.setCellValue(sourceCell.getBooleanCellValue());
                    break;
                case  HSSFCell.CELL_TYPE_ERROR:
                    targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
                    break;
                case  HSSFCell.CELL_TYPE_FORMULA:
                    //parseFormula这个函数的用途在后面说明
//                    try{targetCell.setCellFormula(parseFormula(sourceCell.getCellFormula())); }
//                    catch(Exception ex){log.error(ex);}
                    break;
                case  HSSFCell.CELL_TYPE_NUMERIC:
                    targetCell.setCellValue(sourceCell.getNumericCellValue());
                    break;
                case  HSSFCell.CELL_TYPE_STRING:
                    targetCell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    targetCell.setCellValue(sourceCell.getStringCellValue());
                    break;
                }
            }
        }
        return targetSheet;
    }
    //clone一个样式
    public static HSSFCellStyle cloneStyle(HSSFWorkbook wb, HSSFCellStyle baseCellStyle) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(baseCellStyle.getAlignment());
        style.setBorderBottom(baseCellStyle.getBorderBottom());
        style.setBorderLeft(baseCellStyle.getBorderLeft());
        style.setBorderRight(baseCellStyle.getBorderRight());
        style.setBorderTop(baseCellStyle.getBorderTop());
        style.setBottomBorderColor(baseCellStyle.getBottomBorderColor());
        style.setDataFormat(baseCellStyle.getDataFormat());
        style.setLeftBorderColor(baseCellStyle.getLeftBorderColor());
        style.setRightBorderColor(baseCellStyle.getRightBorderColor());
        style.setTopBorderColor(baseCellStyle.getTopBorderColor());
        style.setVerticalAlignment(baseCellStyle.getVerticalAlignment());
        style.setWrapText(baseCellStyle.getWrapText());
        style.setFillBackgroundColor(baseCellStyle.getFillBackgroundColor());
        style.setFillForegroundColor(baseCellStyle.getFillForegroundColor());
        style.setFillPattern(baseCellStyle.getFillPattern());

//        HSSFFont font = wb.createFont();
//        font.setBoldweight(baseFont.getBoldweight());
//        font.setColor(baseFont.getColor());
//        font.setFontHeight(baseFont.getFontHeight());
//        font.setFontHeightInPoints(baseFont.getFontHeightInPoints());
//        font.setFontName(baseFont.getFontName());
//        font.setItalic(baseFont.getItalic());
//        font.setStrikeout(baseFont.getStrikeout());
//        font.setTypeOffset(baseFont.getTypeOffset());
//        font.setUnderline(baseFont.getUnderline());
//        style.setFont(font);
//        style.setFont()
//        baseCellStyle.getf
//        font.seti
        return style;
    }


    //查找模板文件的Sheet对应的配置文件
    protected String getSheetCfgFileName(String tplSheetName) {
        String absSCFN = "";
        String tmpSCFN = "";
        String tmpTplFileName = "";
        if (tplFileName == null || tplFileName.equals("")) {
            return "";
        }
        int iXLS = 0;
        iXLS = tplFileName.toLowerCase().lastIndexOf(".xls");
        if (iXLS > 0) {
            tmpTplFileName = tplFileName.substring(0, iXLS);
        }
        String[] sp = StringUtil.split(tmpTplFileName, "\\");
        if (sp.length > 0) {
            tmpTplFileName = sp[sp.length - 1];
        }
        sp = StringUtil.split(tmpTplFileName, "/");
        if (sp.length > 0) {
            tmpTplFileName = sp[sp.length - 1];
        }
//        try {
            String keySCFN = tmpTplFileName + "." + tplSheetName;
            tmpSCFN = cfg.getValue(keySCFN);
            if(tmpSCFN == null)
                throw new BusinessException("","Can not find config of sheet:["+keySCFN+"]");
            if (cfgDir == null) {
                cfgDir = "";
            }
            absSCFN = cfgDir + tmpSCFN + ".config";
//        } catch (Exception e) {
//            //
//        }
        return absSCFN;
    }

    public static void main(String[] args){
//        System.out.println(ExcelExporter.getSheetCfgFileName("Template_TC.xls","TimeCard"));
    }
}
