package com.wits.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;

public interface IDataWriter {
    public String getFormat();

    public void setFormat(String format);

    public void writeCell(HSSFWorkbook workbook, HSSFSheet sheet,HSSFRow row, HSSFCell cell,Object value);
}
