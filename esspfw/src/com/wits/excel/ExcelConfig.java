package com.wits.excel;

import com.wits.excel.exporter.TreeCellExporter;

/**
 * 需写入数据的个Cell的配置;配置包含:
 * 1. position 所处的位置
 * 2. beanName 对象名称，即定义于Parameter之KEY
 * 3. beanType 对象类型,"L":List,"0":简单对象,"T":树对象,DtoTreeNode(待实现)
 * 3. propertyName 对应的属性名，如果为NULL，即直接获取beanName对象之值
 * 4. propertyType 数据类型,包含string,number,date,boolean,formula
 * 5. propertyForamat 数据格式
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class ExcelConfig {
    //所有数据对象类型
    public static final String BEAN_TYPE_LIST = "L";
    public static final String BEAN_TYPE_OBJECT = "0";
    public static final String BEAN_TYPE_TREE = "T";
    //所有的属性类型
    public static final String DATA_TYPE_STRING = "string";
    public static final String DATA_TYPE_NUMBER = "number";
    public static final String DATA_TYPE_DATE = "date";
    public static final String DATE_TYPE_BOOLEAN = "boolean";
    public static final String DATE_TYPE_FORMULA = "formula";
     public static final String DATE_TYPE_GRAPHIC = "graphic";

    public static final String DYNAMIC = "D";
    public static final String FIX = "F";

    public static final String NULL_PROPERTY = "NULL";
    //TreeCellExporter在该Cell可导出的树的层
    public static final String LEVEL_LEAF = "leaf";//只导出叶子节点
    public static final String LEVEL_ALL = "all";//导出所有层的节点
    public static final String LEVEL_NO_LEAF = "no-leaf";//导出除叶子之外的所有节点
    //遍历树的方式
    public static final String TREE_TRAVEL_PREORDER = "pre";
    public static final String TREE_TRAVEL_POSTORDER = "post";

    public static final String TREE_GROUP = "group";
}
