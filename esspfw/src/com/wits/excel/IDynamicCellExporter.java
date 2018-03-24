package com.wits.excel;

import com.wits.excel.exporter.ITemplateChangeListner;

/**
 * 在对模板的Cell的数据写入过程中可能会改变模板本身的CellExporter要实现该接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public interface IDynamicCellExporter extends ICellExporter {
    /**
     * 判断该ICellExporter是否会改变模板本身
     * @param isFix boolean
     */
    public void setFix(boolean isFix);

    public boolean isFix();

    public void addTemplateChangeListner(ITemplateChangeListner l);

    public void removeTemplateChangeListner(ITemplateChangeListner l);

    public void fireTemplateChangeListner(Object bean);
}
