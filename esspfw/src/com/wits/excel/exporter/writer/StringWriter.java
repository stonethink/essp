package com.wits.excel.exporter.writer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class StringWriter extends AbstractDataWriter {
    /**
     * writeCell
     *
     * @param cell HSSFCell
     * @param value Object
     * @todo Implement this com.wits.excel.IDataWriter method
     */
    public void writeCell(HSSFWorkbook workbook,HSSFSheet sheet,HSSFRow row,HSSFCell cell, Object value) {
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);

        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue(value.toString());
    }
}
