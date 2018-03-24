package com.wits.excel.exporter.writer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class FormulaWriter extends AbstractDataWriter {
    /**
     * writeCell
     *
     * @param cell HSSFCell
     * @param value Object
     * @todo Implement this com.wits.excel.IDataWriter method
     */
    public void writeCell(HSSFWorkbook workbook,HSSFSheet sheet,HSSFRow row,HSSFCell cell, Object value) {

        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        try{
            String tempStr = (String) value;
            if(isRealForumla(tempStr)){
                cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                cell.setCellFormula(tempStr.substring(1));
                if(isHyperLinkFormula(tempStr)){
                    HSSFCellStyle style = cell.getCellStyle();
                    HSSFFont font = workbook.createFont();
                    font.setUnderline( HSSFFont.U_SINGLE);
                    font.setColor(HSSFColor.BLUE.index);
                    style.setFont(font);
                }
            }else{
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(tempStr);
            }
        }catch(Throwable tx){
//            System.out.println("export formula ["+value+"] error!");
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(value.toString());
        }
    }
    //判断一个字符串是否
    private boolean isRealForumla(String str){
        return str != null && str.startsWith(FORMULA_PREFIX);
    }
    private boolean isHyperLinkFormula(String str){
        return str != null && str.indexOf(FORMULA_HYPERLINK) != -1;
    }
    public static final String FORMULA_PREFIX = "=";
    public static final String FORMULA_HYPERLINK = "HYPERLINK";
}
