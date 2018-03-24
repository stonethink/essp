package com.wits.excel.exporter.writer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import java.util.Calendar;
import java.util.Date;
import com.wits.util.comDate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class DateWriter extends AbstractDataWriter {
    /**
     * writeCell
     *
     * @param cell HSSFCell
     * @param value Object
     * @todo Implement this com.wits.excel.IDataWriter method
     */
    public void writeCell(HSSFWorkbook workbook,HSSFSheet sheet,HSSFRow row,HSSFCell cell, Object value) {
        Date date = getDate(value);
        String dateStr = null;
        if(getFormat() != null){
            dateStr = comDate.dateToString(date,getFormat());
        }else{
            dateStr = comDate.dateToString(date);
        }
        cell.setCellValue(dateStr);
    }

    protected Date getDate(Object value){
        if(value instanceof Date)
            return (Date) value;
        else if(value instanceof Calendar)
            return ((Calendar)value).getTime();
        else
            throw new IllegalArgumentException("value:["+value+"] is not date object in DateWriter");
    }
}
