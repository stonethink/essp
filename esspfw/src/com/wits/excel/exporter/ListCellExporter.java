package com.wits.excel.exporter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.excel.*;
import java.util.Collection;
import server.framework.common.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import com.wits.excel.exporter.writer.DataWriterFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class ListCellExporter extends AbstractDynamicCellExporter{

        /**
     * export
     *
     * @param sheet HSSFSheet
     * @param value Object
     * @todo Implement this com.wits.excel.ICellExporter method
     */
    public void export(HSSFWorkbook wb,HSSFSheet sheet, Object bean) {
        if( !(bean instanceof Collection) )
            throw new BusinessException("ListCellExporter can only export collection");

        Collection c = (Collection) bean;
        if(c == null || c.size() == 0)
            return;
        Object[] objs = c.toArray();
        int length = objs.length;
        Object value = null;
        //根据是否配置属性判断输出的Value,若Writer为空,根据Value创建合适Writer
        value = getRealValue(objs[0]);

        if(dataWriter == null){
            dataWriter = DataWriterFactory.getInstance().getDataWriterByValue(value);
        }
        //若是动态插入,则需向下移动 从cell config所在的行+1 至  最后一行  dataSize行
        //通知监听器做相应的出来
        if(!isFix()){
            int startRowNun = position.getRow() ;
            int lastRowNum = sheet.getLastRowNum();
            sheet.shiftRows(startRowNun,lastRowNum,length);
            fireTemplateChangeListner(bean);
        }
        //Cell配置对应的HSSFCell对象
        HSSFCell baseCell = getRelativeCell(sheet,0);
        //第一Cell的样式,以后每一个Cell写入的时候都设置成该样式
        HSSFCellStyle baseCellStyle = baseCell.getCellStyle();
        HSSFCell cell = null;
        HSSFRow row = null;
        //遍历数组,输出每个对象对应的属性
        for(int index = 0;index < length; index ++){
            value = getRealValue(objs[index]);
            cell = getRelativeCell(sheet,index);
            row = getRelativeRow(sheet,index);
            HSSFCellStyle cellStyle = null;
            Object obj = objs[index];
            //add by lipengxu 2007-10-08
            //ICellStyleSwitch用于解决格式太多，打开时报错的问题。
            if(obj instanceof ICellStyleSwitch) {
                cellStyle = this.getCellStyle(wb, baseCellStyle,
                                              (ICellStyleSwitch) obj,
                                              propertyName);
            } else if(obj instanceof ICellStyleAware){
                cellStyle = cloneStyle(wb, baseCellStyle);
                ((ICellStyleAware)obj ).setCellStyle(
                    propertyName,
                    obj,
                    wb,
                    cellStyle);
            }else{
                cellStyle = referenceCellStyle(wb, baseCellStyle);
            }
            cell.setCellStyle(cellStyle);
            if(value == null)
                continue;
            dataWriter.writeCell(wb,sheet,row,cell,value);
        }
    }
}
