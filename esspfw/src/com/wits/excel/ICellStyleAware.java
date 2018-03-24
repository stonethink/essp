package com.wits.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface ICellStyleAware {
    public void setCellStyle(String propertyName,Object bean,HSSFWorkbook wb,HSSFCellStyle cellStyle);
}
