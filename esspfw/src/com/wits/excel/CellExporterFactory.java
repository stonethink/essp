package com.wits.excel;


import com.wits.excel.exporter.ListCellExporter;
import com.wits.excel.exporter.SimpleObjectCellExporter;
import com.wits.excel.exporter.TreeCellExporter;
import com.wits.excel.exporter.writer.DataWriterFactory;
import com.wits.util.StringUtil;



/**
 * ICellExporter的工厂类,根据Cell配置的不同返回ICellExporter的不同实现
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class CellExporterFactory {
    private static final CellExporterFactory instance = new CellExporterFactory();
    private CellExporterFactory(){  }

    public static CellExporterFactory getInstance(){
        return instance;
    }
    /**
     * 根据每个Cell的配置构造其对应的ICellExporter
     * @param cellCfg String
     * @return ICellExporter
     */
    public ICellExporter create(String cellCfg){
        if(cellCfg == null || "".equals(cellCfg.trim()))
            throw new IllegalArgumentException("illegal cell config: null!");
        ICellExporter exporter = null;
        CellPosition position = null;
        String beanName = null;
        String propertyName = null;
        IDataWriter dataWriter = null;

        int iSLineLen = cellCfg.length();
        int fIndex = cellCfg.indexOf("[");
        int lIndex = cellCfg.indexOf("]");
        if (fIndex < 0 || lIndex < 0) {
            throw new IllegalArgumentException("illegal cell config:" + cellCfg);
        }


        //获取Cell位置
        String positionStr = cellCfg.substring(fIndex + 1, lIndex);
        position =  new CellPosition(positionStr);
        if (iSLineLen < lIndex + 1) {
            throw new IllegalArgumentException("illegal cell config:[" + cellCfg + "]!Please define the bean in cell!");
        }

        //bean和property用;分隔
        String sLineRest = cellCfg.substring(lIndex + 1);
        String[] sp = StringUtil.split(sLineRest, ";");

        if (sp.length > 0) {
            //beanName,beanType用,分隔
            String[] spDetails = StringUtil.split(sp[0], ",");
            if (spDetails.length > 0) {
                beanName = spDetails[0];
            }
            if(spDetails.length <= 1){
                exporter = new SimpleObjectCellExporter();
            }else if(spDetails.length > 1){
                exporter = getExporterByType(spDetails[1]);
            }
            //tree 或 list可以定义是否动态插入的属性
            if(exporter instanceof IDynamicCellExporter && spDetails.length > 2){
                boolean isFix = spDetails[2].equals(ExcelConfig.FIX)
                                || spDetails[2] == null
                                || "".equals(spDetails[2].trim());
                ((IDynamicCellExporter)exporter).setFix(isFix);
            }
            if(exporter instanceof TreeCellExporter  ){
                TreeCellExporter treeCellExporter = (TreeCellExporter)exporter;
                if(spDetails.length > 3){//tree可以定义节点所在的层次
                    String level = spDetails[3] != null || "".equals(spDetails[3].trim()) ?
                                   spDetails[3] : ExcelConfig.LEVEL_ALL;
                    treeCellExporter.setLevel(level);
                }
                if(spDetails.length > 4){//tree遍历的方式
                    String order = spDetails[4] != null || "".equals(spDetails[4].trim()) ?
                                   spDetails[4] : ExcelConfig.TREE_TRAVEL_PREORDER;
                    treeCellExporter.setTravelOrder(order);
                }
                if(spDetails.length > 5){
                    boolean group = ExcelConfig.TREE_GROUP.equals(spDetails[5].trim()) ?
                                    true : false;
                    treeCellExporter.setGroup(group);
                }
            }
        }
        if(sp.length > 1){
            //propertyName,propertyType,propertyFormat用,分隔
            String[] spDetails = StringUtil.split(sp[1], ",");
            if(spDetails.length > 0){
                propertyName = spDetails[0];
            }
            //没指定propertyType时,用作字符串输出
//            if(spDetails.length <= 1){
//                dataWriter = new StringWriter();
//            }else
            if(spDetails.length > 1){
                dataWriter = DataWriterFactory.getInstance().getDataWriterByType(spDetails[1]);
            }
            if(spDetails.length > 2){
                dataWriter.setFormat(spDetails[2]);
            }
        }
        //若没指定输出Writer,在CellExporter中根据具体输出的Object对象选择Writer

        exporter.setBeanName(beanName);
        exporter.setDataWriter(dataWriter);
        exporter.setPosition(position);
        exporter.setPropertyName(propertyName);

        return exporter;
    }

    /**
     * 根据beanType构造不同的CellExporter
     * @param spDetails String[]
     * @return ICellExporter
     */
    protected ICellExporter getExporterByType(String beanType) {
        ICellExporter exporter = null;
        if( ExcelConfig.BEAN_TYPE_OBJECT.equals(beanType))
            exporter = new SimpleObjectCellExporter();
        else if(ExcelConfig.BEAN_TYPE_LIST.equals(beanType))
            exporter = new ListCellExporter();
        else if (ExcelConfig.BEAN_TYPE_TREE.equals(beanType))
            exporter = new TreeCellExporter();
        else
            exporter = new SimpleObjectCellExporter();
        return exporter;
    }

}
