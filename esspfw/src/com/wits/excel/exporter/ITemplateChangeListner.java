package com.wits.excel.exporter;

import com.wits.excel.IDynamicCellExporter;

public interface ITemplateChangeListner {
    public static final int SHIFT_ROW = 1;

    public void templateChange(IDynamicCellExporter src,Object bean);
}
