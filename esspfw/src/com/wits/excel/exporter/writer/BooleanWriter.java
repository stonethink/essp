package com.wits.excel.exporter.writer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class BooleanWriter extends AbstractDataWriter {
    /**
     * writeCell
     *
     * @param cell HSSFCell
     * @param value Object
     * @todo Implement this com.wits.excel.IDataWriter method
     */
    public void writeCell(HSSFWorkbook workbook,HSSFSheet sheet,HSSFRow row,HSSFCell cell, Object value) {
        if(value instanceof Boolean ){
            Boolean b = (Boolean) value;
            cell.setCellValue(b.booleanValue());
        }else{
            throw new IllegalArgumentException("value:["+value+"] is not boolean object in BooleanWriter");
        }
    }
    public static void main(String[] args){

    }
}
