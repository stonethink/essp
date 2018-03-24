package com.wits.excel.exporter.writer;

import com.wits.excel.ExcelConfig;
import com.wits.excel.IDataWriter;
import java.util.Calendar;
import java.util.Date;

public class DataWriterFactory {
    private static final DataWriterFactory instance = new DataWriterFactory();
    private DataWriterFactory(){}
    public static DataWriterFactory getInstance(){
        return instance;
    }
    //根据propertyTyp构造不同的DataWriter
    public IDataWriter getDataWriterByType(String propertyType){
        IDataWriter wirter = null;
        if(ExcelConfig.DATA_TYPE_DATE.equals(propertyType))
            wirter = new DateWriter();
        else if(ExcelConfig.DATA_TYPE_NUMBER.equals(propertyType))
            wirter = new NumberWriter();
        else if(ExcelConfig.DATA_TYPE_STRING.equals(propertyType))
            wirter = new StringWriter();
        else if (ExcelConfig.DATE_TYPE_BOOLEAN.equals(propertyType))
            wirter = new BooleanWriter();
        else if(ExcelConfig.DATE_TYPE_FORMULA.equals(propertyType))
            wirter = new FormulaWriter();
        else if(ExcelConfig.DATE_TYPE_GRAPHIC.equals(propertyType)){
            wirter = new GraphicWriter();
        }else{
            throw new IllegalArgumentException("illegal property type:[" +
                                               propertyType + "]");
        }
        return wirter;
    }

    public IDataWriter getDataWriterByValue(Object value){
        if(value instanceof Number)
            return new NumberWriter();
        else if(value instanceof String || value instanceof StringBuffer)
            return new StringWriter();
        else if(value instanceof Date || value instanceof Calendar)
            return new DateWriter();
        else if(value instanceof Boolean)
            return new BooleanWriter();
        return new StringWriter();
    }
}
