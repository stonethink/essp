package com.wits.excel.exporter;

import com.wits.excel.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import c2s.dto.DtoUtil;
import server.framework.common.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.HashMap;


public abstract class AbstractCellExporter implements ICellExporter {
    protected static HashMap<HSSFWorkbook, HashMap> wbMap = new HashMap();
    protected String beanName;
    protected String propertyName;
    protected IDataWriter dataWriter;
    protected CellPosition position;

    /**
     * getBeanName
     *
     * @return String
     * @todo Implement this com.wits.excel.ICellExporter method
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * getDataWriter
     *
     * @return IDataWriter
     * @todo Implement this com.wits.excel.ICellExporter method
     */
    public IDataWriter getDataWriter() {
        return dataWriter;
    }

    /**
     * getPosition
     *
     * @return CellPosition
     * @todo Implement this com.wits.excel.ICellExporter method
     */
    public CellPosition getPosition() {
        return position;
    }

    /**
     * getPropertyName
     *
     * @return String
     * @todo Implement this com.wits.excel.ICellExporter method
     */
    public String getPropertyName() {
        return propertyName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setDataWriter(IDataWriter dataWriter) {
        this.dataWriter = dataWriter;
    }

    public void setPosition(CellPosition position) {
        this.position = position;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }


    ///-----------------------------------------------------
    //获得Cell配置相对rowOffset行对应的Cell
    protected HSSFCell getRelativeCell(HSSFSheet sheet,int rowOffset){
        short row = (short)(position.getRow() - 1 + rowOffset);
        short column = (short)(position.getColumn() - 1);
        HSSFRow hssfRow = sheet.getRow(row);
        if(hssfRow == null)
            hssfRow = sheet.createRow(row);
        HSSFCell cell = hssfRow.getCell(column);
        if(cell == null)
            cell = hssfRow.createCell(column);
        return cell;
    }
    //获得Cell配置相对row行
    protected HSSFRow getRelativeRow(HSSFSheet sheet,int rowOffset){
        short row = (short)(position.getRow() - 1 + rowOffset);
        HSSFRow hssfRow = sheet.getRow(row);
        if(hssfRow == null)
            hssfRow = sheet.createRow(row);
        return hssfRow;
    }
    //判断是否有配置Property
    protected boolean hasConfigProperty(){
        if( propertyName == null ||  ExcelConfig.NULL_PROPERTY.equalsIgnoreCase(propertyName))
            return false;
        return true;
    }
    //根据是否配置属性取得真正要输出的Value对象
    protected Object getRealValue(Object bean) throws BusinessException {
        Object value;
        if (hasConfigProperty()) {
            try {
                value = DtoUtil.getProperty(bean, propertyName);
            } catch (Exception tx) {
                throw new BusinessException(tx);
            }
        } else {
            value = bean;
        }
        return value;
    }
    /**
     * 引用Cell对应的Style对象
     * @param wb HSSFWorkbook
     * @param baseCellStyle HSSFCellStyle
     * @return HSSFCellStyle
     */
    protected HSSFCellStyle referenceCellStyle(HSSFWorkbook wb,
                                               HSSFCellStyle baseCellStyle) {
        HSSFCellStyle cellStyle = null;
        cellStyle = wb.getCellStyleAt(baseCellStyle.getIndex());
        return cellStyle;
    }
    /**
     * 复制一个Cell对应的Style对象
     * @param wb HSSFWorkbook
     * @param baseCellStyle HSSFCellStyle
     * @return HSSFCellStyle
     */
    protected HSSFCellStyle cloneStyle(HSSFWorkbook wb,
                                       HSSFCellStyle baseCellStyle) {
        return ExcelExporter.cloneStyle(wb, baseCellStyle);
    }

    /**
     * 获取HSSFCellStyle
     *     以HSSFWorkbook和名称缓存。
     * @param wb HSSFWorkbook
     * @param baseCellStyle HSSFCellStyle
     * @param styleSwitch ICellStyleSwitch
     * @param propertyName String
     * @return HSSFCellStyle
     */
    protected static HSSFCellStyle getCellStyle(HSSFWorkbook wb,
                                         HSSFCellStyle baseCellStyle,
                                         ICellStyleSwitch styleSwitch,
                                         String propertyName) {

        String styleName = styleSwitch.getStyleName(propertyName);
        //styleName为空，对格式不做特殊要求，返回源格式。
        if(styleName == null || "".equals(styleName)) {
            return baseCellStyle;
        }

        //根据HSSFWorkbook获取格式缓存
        HashMap<String, HSSFCellStyle> styleMap = wbMap.get(wb);
        if(styleMap == null) {
            styleMap = new HashMap<String, HSSFCellStyle>();
            wbMap.put(wb, styleMap);
        }

        //按名称在缓存中寻找style
        HSSFCellStyle style = styleMap.get(styleName);
        //如果缓存中不存在，获取一个新的。
        if(style == null) {
            //复制一个新格式
            style = ExcelExporter.cloneStyle(wb, baseCellStyle);
            style = styleSwitch.getStyle(styleName, style);
            //无法获取，则使用源格式。
            if(style == null) {
                style = baseCellStyle;
            }
            //缓存新格式，待下次使用.
            styleMap.put(styleName, style);
        }
        return style;
    }

    /**
     * 清除HSSFWorkbook下的格式缓存
     * @param wb HSSFWorkbook
     */
    public static void removeCellStyleCache(HSSFWorkbook wb) {
        wbMap.remove(wb);
    }
}
