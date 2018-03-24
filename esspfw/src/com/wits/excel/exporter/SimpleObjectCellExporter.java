package com.wits.excel.exporter;

import com.wits.excel.exporter.writer.DataWriterFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFRow;
import com.wits.excel.ICellStyleSwitch;

public class SimpleObjectCellExporter extends AbstractCellExporter {
    /**
     * export
     * 判断是否配置了输出Bean的属性,若有配置,则输出属性值,否则直接输出对象值
     * @param sheet HSSFSheet
     * @param value Object
     * @todo Implement this com.wits.excel.ICellExporter method
     */
    public void export(HSSFWorkbook wb,HSSFSheet sheet, Object bean) {
        HSSFCell cell = getRelativeCell(sheet,0);
        HSSFRow row = getRelativeRow(sheet,0);

        Object value = getRealValue(bean);
        if(value == null)
            return;
        //若Writer为Null,根据Value的类型创建合适的Writer
        if(dataWriter == null){
            dataWriter = DataWriterFactory.getInstance().getDataWriterByValue(value);
        }

        dataWriter.writeCell(wb,sheet,row,cell,value);

        //add by lipengxu 2007-10-08
        //ICellStyleSwitch用于解决格式太多，打开时报错的问题。
        if (bean instanceof ICellStyleSwitch) {
            HSSFCellStyle cellStyle = this.getCellStyle(wb, cell.getCellStyle(),
                                                        (ICellStyleSwitch) bean,
                                                        propertyName);
            cell.setCellStyle(cellStyle);
        } else if (bean instanceof ICellStyleAware) {
            HSSFCellStyle cellStyle = wb.createCellStyle();
            ((ICellStyleAware) bean).setCellStyle(
                    propertyName,
                    bean,
                    wb,
                    cellStyle);
            cell.setCellStyle(cellStyle);
        }

    }
}
