package com.wits.excel.exporter.writer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import server.framework.taglib.util.FormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class NumberWriter extends AbstractDataWriter {
    /**
     * writeCell
     *
     * @param cell HSSFCell
     * @param value Object
     * @todo Implement this com.wits.excel.IDataWriter method
     */
    public void writeCell(HSSFWorkbook workbook,HSSFSheet sheet,HSSFRow row,HSSFCell cell, Object value) {
        if(value instanceof Number){
            Number num = (Number) value;
            if(getFormat() != null){
                String result = FormatUtils.formatNumber(num.toString(), getFormat());
                cell.setCellValue(Double.parseDouble(result));
            }else{
                cell.setCellValue(num.doubleValue());
            }
        }
    }
}
