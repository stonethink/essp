package com.wits.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 根据每个Cell的配置生成一个对应的ICellExporter实现,cell数据的写入由该对象来控制
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public interface ICellExporter {

    public String getBeanName();

    public String getPropertyName();

    public IDataWriter getDataWriter();

    public CellPosition getPosition();

    public void setBeanName(String bean);

    public void setPropertyName(String property);

    public void setDataWriter(IDataWriter dataWriter);

    public void setPosition(CellPosition position);

    public void export(HSSFWorkbook wb,HSSFSheet sheet ,Object bean);
}
