package com.wits.excel;

import java.util.Collection;
import java.util.List;

import com.wits.excel.exporter.ITemplateChangeListner;
import com.wits.util.Parameter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.ArrayList;
import java.io.InputStream;
import java.net.URL;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import server.framework.common.BusinessException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

/**
 * 导出数据到指定的Sheet里
 * Attention:HSSFWorkbook是要输出Excel文档对应的WorkBook,而不是模板文档的WorkBook
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class SheetExporter implements ITemplateChangeListner{
    private HSSFWorkbook targetWb;
    private HSSFSheet sheet;
    private List cellExporters;
    private Map treeLevelStyle ;//导出Tree数据时，保存各个层级Cell的样式
    public SheetExporter(HSSFWorkbook targetWb,HSSFSheet sheet,String sheetConfigFile){
        this.targetWb = targetWb;
        this.sheet = sheet;
        try {
            cellExporters = parse(sheetConfigFile);
        } catch (IOException ex) {
            throw new BusinessException("", "error reading sheet config!", ex);
        }
        treeLevelStyle = new HashMap();
    }
    /**
     * 解析Sheet配置文件生成CellExporter,将所有的CellExporter放入List中
     * 并按行从小到大,再按列从小到大进行排序
     * @param sheetConfigFile String
     * @return List
     * @throws IOException
     */
    protected List parse(String sheetConfigFile) throws IOException {
        List result = new ArrayList();
        URL configFileUrl = SheetExporter.class.getResource(sheetConfigFile);
        if(configFileUrl == null)
            throw new BusinessException("","Can not find sheet config:["+sheetConfigFile+"]");
        InputStream is = configFileUrl.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = br.readLine();
        while (line != null && !line.equals("")){
            if(!isComment(line)){
                ICellExporter exporter = CellExporterFactory.getInstance().
                                         create(line);
                result.add(exporter);
            }
            line = br.readLine();
        }
        Collections.sort(result,new CellExporterComparator());

        return result;
    }

    public SheetExporter(HSSFWorkbook targetWb,HSSFSheet sheet,List cellExporters){
        this.targetWb = targetWb;
        this.sheet = sheet;
        this.cellExporters = cellExporters;
    }
    //是否为注释行
    private boolean isComment(String line){
        return line.startsWith("#") ||
                line.startsWith("//");
    }

    public List getCellExporters(){
        return this.cellExporters;
    }

    public void export(Parameter inData){
        for(int i = 0 ;i < cellExporters.size() ;i ++){
            ICellExporter cellExporter = (ICellExporter) cellExporters.get(i);
            String beanName = cellExporter.getBeanName();
            Object bean = inData.get(beanName);
            if(bean == null)
                continue;
            if(cellExporter instanceof IDynamicCellExporter){
                ((IDynamicCellExporter)cellExporter).addTemplateChangeListner(this);
            }
            cellExporter.export(targetWb,sheet,bean);
        }
        treeLevelStyle.clear();
    }
    //监听器要做的事情
    //1.通知所有和src在一行的动态CellExporter设置成固定的,因为这些CellExporter在写出数据时都不用移动了
    //2.在给CellExporter下面的所有CellExporter位置都要往下移动dataSize的大小
    public void templateChange(IDynamicCellExporter src, Object bean) {
        for(int i = 0;i < cellExporters.size() ;i ++){
            ICellExporter exporter = (ICellExporter) cellExporters.get(i);
            if( exporter instanceof IDynamicCellExporter &&
                exporter.getPosition().getRow() == src.getPosition().getRow()){
                ((IDynamicCellExporter)exporter).setFix(true);
            }

            if(bean instanceof Collection){
                int srcRow = src.getPosition().getRow();
                int thisRow = exporter.getPosition().getRow();
                if(thisRow > srcRow){
                    Collection c = (Collection) bean;
                    exporter.getPosition().setRow(thisRow + c.size());
                }
            }
        }
    }

    class CellExporterComparator implements Comparator{
        public boolean equals(Object obj) {
            return false;
        }

        public int compare(Object o1, Object o2) {
            if(o1 instanceof ICellExporter && o2 instanceof ICellExporter){
                ICellExporter cellExporter1 = (ICellExporter) o1;
                ICellExporter cellExporter2 = (ICellExporter) o2;
                return
                cellExporter1.getPosition().getRow() * 50 + cellExporter1.getPosition().getColumn() -
                cellExporter2.getPosition().getRow() * 50 + cellExporter2.getPosition().getColumn();
            }
            return 0;
        }

    }
}
