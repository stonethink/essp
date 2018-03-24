package com.wits.excel.exporter;

import com.wits.excel.ExcelConfig;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import c2s.dto.ITreeNode;
import server.framework.common.BusinessException;
import java.util.List;
import java.util.ArrayList;
import com.wits.excel.exporter.writer.DataWriterFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFRow;
import com.wits.excel.ICellStyleSwitch;
import com.wits.excel.TreeLevelCellStyleSwitch;

public class TreeCellExporter extends AbstractDynamicCellExporter {

    private static ICellStyleSwitch TreeLevelstyleSwitch
            = new TreeLevelCellStyleSwitch();
    //树遍历方式,默认先根序
    private String travelOrder = ExcelConfig.TREE_TRAVEL_PREORDER;
    //要显示的树层,默认显示所有层
    private String level = ExcelConfig.LEVEL_ALL;
    //是否分组,默认不分组
    private boolean group = false;
    /**
     * export
     *
     * @param sheet HSSFSheet
     * @param value Object
     * @todo Implement this com.wits.excel.ICellExporter method
     */
    public void export(HSSFWorkbook wb,HSSFSheet sheet, Object bean) {
        if(! (bean instanceof ITreeNode) )
            throw new BusinessException("TreeCellExporter can only export ITreeNode");
        ITreeNode root = (ITreeNode) bean;
        //遍历树所有节点,放入List中
        List allNode = traveTree(root);
        if(allNode == null || allNode.size() == 0)
            return;
        Object dataBean = root.getDataBean();
        //判断根节点的DataBean是否为空,若为空则下面将不显示改行数据,且不对根节点分组
        boolean isRootDataNull = dataBean == null;
        Object value = null;

        if(!isFix()){
            int startRowNun = position.getRow() ;
            int lastRowNum = sheet.getLastRowNum();
            sheet.shiftRows(startRowNun,lastRowNum,allNode.size());
            fireTemplateChangeListner(allNode);
        }

        //Cell配置对应的HSSFCell对象
        HSSFCell baseCell = getRelativeCell(sheet,0);
        //第一Cell的样式,以后每一个Cell写入的时候都设置成该样式
        HSSFCellStyle baseCellStyle = baseCell.getCellStyle();
        HSSFCell cell = null;
        HSSFRow row = null;
        //按Level选择输出的数据
        int nullDataNum = 0;
        for(int index = 0;index < allNode.size(); index ++){
            ITreeNode node = (ITreeNode) allNode.get(index);
            dataBean = node.getDataBean();
            //除了根节点其他任何节点的DataBean都不能为空
            if(dataBean == null && node.getLevel() != ITreeNode.ROOT_LEVEL){
                throw new BusinessException("","the databean of tree node can not be null except the root!");
            }else if(dataBean == null && node.getLevel() == ITreeNode.ROOT_LEVEL){
                nullDataNum ++;
                continue;
            }
            cell = getRelativeCell(sheet,index - nullDataNum);
            row = getRelativeRow(sheet,index - nullDataNum);
            int levelNum = node.getLevel();

            HSSFCellStyle style = null;
            //add by lipengxu 2007-10-08
            //ICellStyleSwitch用于解决格式太多，打开时报错的问题。
            if (dataBean instanceof ICellStyleSwitch) {
                style = this.getCellStyle(wb, baseCellStyle,
                                          (ICellStyleSwitch) dataBean,
                                          propertyName);
            } else if(dataBean instanceof ICellStyleAware){
                style = this.cloneStyle(wb,baseCellStyle);
                ((ICellStyleAware)dataBean).setCellStyle(
                    propertyName,
                    dataBean,
                    wb,
                    style);
            } else if(style == null || style.equals(baseCellStyle)){
                //若客户端没有设置Cell样式(实现ICellStyleAware接口),则树的不同层级设置不同填充色
                if(levelNum <= 10 && !node.isLeaf()){
                    style = this.getCellStyle(wb, baseCellStyle,
                                          TreeLevelstyleSwitch,
                                          levelNum + "");
                }else{
                    style = this.referenceCellStyle(wb,baseCellStyle);
                }
            }
            cell.setCellStyle(style);

            value = getRealValue(dataBean);
            if(!isOutPutNode(node) || value == null)
                continue;
            //根据是否配置属性判断输出的Value,若Writer为空,根据Value创建合适Writer
            if(dataWriter == null ){
                dataWriter = DataWriterFactory.getInstance().getDataWriterByValue(value);
            }
            dataWriter.writeCell(wb,sheet,row,cell,value);
        }
        //对树进行分组
        if(group){
            for(int index = 0;index < allNode.size(); index ++){
                ITreeNode node = (ITreeNode) allNode.get(index);
                if(isRootDataNull)
                    groupChild(sheet,node,index - 1);
                else
                    groupChild(sheet,node,index);
            }
        }
    }


    public boolean isGroup(){
        return this.group;
    }
    public void setGroup(boolean group){
        this.group = group;
    }
    public String getLevel() {
        return level;
    }

    public String getTravelOrder() {
        return travelOrder;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setTravelOrder(String travelOrder) {
        this.travelOrder = travelOrder;
    }
    //按travelOrder指定的方式遍历树
    private List traveTree(ITreeNode root ){
        List result  = new ArrayList();
        if(travelOrder.equals(ExcelConfig.TREE_TRAVEL_PREORDER)){
            result.add(root);
            result.addAll(root.listAllChildrenByPreOrder());
        }else if(travelOrder.equals(ExcelConfig.TREE_TRAVEL_POSTORDER)){
            result.addAll(root.listAllChildrenByPostOrder());
            result.add(root);
        }else
            throw new BusinessException("","illegal trave:["+travelOrder+"]");
        return result;
    }
    //判断在该列是否输出该Node
    private boolean isOutPutNode(ITreeNode node){
        if(ExcelConfig.LEVEL_ALL.equals(level))
            return true;
        if(ExcelConfig.LEVEL_LEAF.equals(level) && node.isLeaf()){
            return true;
        }
        if(ExcelConfig.LEVEL_NO_LEAF.equals(level) && !node.isLeaf()){
            return true;
        }
        try{
            int levelNum = Integer.parseInt(level);
            if (node.getLevel() == levelNum)
                return true;
        }catch(Exception ex){
//            System.out.println("warn:node level["+level+"] is not digit!");
        }
        return false;
    }
    //将当前节点的所有子节点分组
    private void groupChild(HSSFSheet sheet,ITreeNode node,int offset){
        if(node.getDataBean() == null)
            return;
        List allChild = node.listAllChildrenByPostOrder();
        if(allChild == null || allChild.size() <= 0)
            return;
        int groupStart = 0,groupEnd = 0;
        if(travelOrder.equals(ExcelConfig.TREE_TRAVEL_PREORDER)){
            groupStart = position.getRow() + offset;
            groupEnd = groupStart + allChild.size() - 1;
        }else{
            groupEnd = position.getRow() + offset - 2;
            groupStart = groupEnd - allChild.size() + 1;
        }
//        System.out.println("group:" + groupStart + "," +groupEnd);
        sheet.groupRow(groupStart,groupEnd);
    }


    static final short[] LEVEL_COLORS = new short[4];
    static{
        LEVEL_COLORS[0] = HSSFColor.LIGHT_TURQUOISE.index;
        LEVEL_COLORS[1] = HSSFColor.GREY_25_PERCENT.index;
        LEVEL_COLORS[2] = HSSFColor.LIGHT_YELLOW.index;
        LEVEL_COLORS[3] = 78;

    }
}
