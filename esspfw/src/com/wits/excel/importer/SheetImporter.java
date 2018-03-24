package com.wits.excel.importer;

import java.util.*;

import c2s.dto.*;
import org.apache.poi.hssf.usermodel.*;
import server.framework.common.*;

public class SheetImporter {
    public static final String CELL_TYPE_BOOLEAN = "bool";
    public static final String CELL_TYPE_DATE = "date";
    public static final String CELL_TYPE_NUMBER = "number";
    public static final String CELL_TYPE_STRING = "string";

    private HSSFSheet sheet;
    private Class cls;
    private String[][] config;
    private int startRow = 1;

    /**
     * 构造方法，工作Sheet,存放数据的Class，相关配置（属性名，类型）。
     * @param sheet HSSFSheet
     * @param cls Class
     * @param config String[][]
     */
    public SheetImporter(HSSFSheet sheet, Class cls, String[][] config) {
        this.sheet = sheet;
        this.cls = cls;
        this.config = config;
    }

    /**
     * 构造方法，工作Sheet,存放数据的Class，相关配置（属性名，类型），读取开始行。
     * @param sheet HSSFSheet
     * @param cls Class
     * @param config String[][]
     * @param startRow int
     */
    public SheetImporter(HSSFSheet sheet, Class cls, String[][] config, int startRow) {
        this.sheet = sheet;
        this.cls = cls;
        this.config = config;
        this.startRow = startRow;
    }

    /**
     * 获取数据List
     * @return List
     */
    public List getDataList() {
        List lst = new ArrayList();
        int lastRow = sheet.getLastRowNum();
        for(int i = startRow; i <= lastRow; i++ ) {
            Object data = getRowData(sheet.getRow(i), i);
            lst.add(data);
        }
        return lst;
    }

    /**
     * 获取一行数据
     * @param row HSSFRow
     * @return Object
     */
    private Object getRowData(HSSFRow row, int rowIndex) {

        //实例化一个数据对象
        Object obj = null;
        try {
            obj = cls.newInstance();
        } catch (Exception ex) {
            throw new BusinessException("RS0001", "get object instance error", ex);
        }

        //按单元格读取
        int length = config.length;
        for (int i = 0; i < length; i++) {
            String[] oneConfig = config[i];
            if(oneConfig == null || oneConfig[0]== null || oneConfig[1] == null) {
                continue;
            }
            String mName = oneConfig[0];
            String type = oneConfig[1];
            HSSFCell cell = row.getCell((short) i);
            try {
                DtoUtil.setProperty(obj, mName, getDataOfType(cell, type));
            } catch (NullPointerException ex) {
            } catch (Exception ex1) {
                String show = "get cell data error at row:"
                              + (rowIndex + 1) + ", column:" + (i + 1);
                throw new BusinessException("RS0002", show, ex1);
            }
        }
        return obj;
    }

    /**
     * 根据配置使用相应读取方法获取数据
     * @param cell HSSFCell
     * @param type String
     * @return Object
     */
    private Object getDataOfType(HSSFCell cell, String type) {
        if(CELL_TYPE_BOOLEAN.equals(type)) {
            return cell.getBooleanCellValue();
        } else if(CELL_TYPE_DATE.equals(type)) {
            return cell.getDateCellValue();
        } else if(CELL_TYPE_NUMBER.equals(type)){
            return cell.getNumericCellValue();
        } else {
            if(cell.getRichStringCellValue() == null) {
                return null;
            }
            return cell.getRichStringCellValue().getString();
        }
    }
}
