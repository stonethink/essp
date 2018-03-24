package com.wits.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * <p>Title: ICellStyleSwitch</p>
 *
 * <p>Description: 在特定情况下使用不同的CellStyle</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ICellStyleSwitch {

    /**
     * 根据getStyleName返回的名称获取HSSFCellStyle
     *   只在第一次调用，然后缓存，再需要时直接引用。
     * @param styleName String
     * @param cellStyle HSSFCellStyle
     * @return HSSFCellStyle
     */
    public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle);

    /**
     * 根据当前请求的属性propertyName和bean的状态返回一个CellStyle名称
     * @param propertyName String
     * @return String
     */
    public String getStyleName(String propertyName);
}
