package com.wits.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * <p>Title: TreeLevelCellStyleSwitch</p>
 *
 * <p>Description: 树型结构报表，层次颜色选择类</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class TreeLevelCellStyleSwitch implements ICellStyleSwitch {

    private static final String TREE_LEVEL_STYLE_NAME = "treeLevelStyleName_";

    /**
     * 根据getStyleName返回的名称获取HSSFCellStyle
     * 只在第一次调用，然后缓存，再需要时直接引用。
     *
     * @param styleName String
     * @param cellStyle HSSFCellStyle
     * @return HSSFCellStyle
     */
    public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
        short index = -1;
        try {
            styleName = styleName.replaceAll(TREE_LEVEL_STYLE_NAME, "");
            index = Short.valueOf(styleName);
        } catch (Exception e) {
            return null;
        }
        if(index < 0 || index > 10) {
            return null;
        }
        cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(LEVEL_COLORS[index - 1]);
        return cellStyle;
    }

    /**
     * 根据当前请求的属性propertyName和bean的状态返回一个CellStyle名称
     *
     * @param propertyName String
     * @return String
     */
    public String getStyleName(String levelName) {
        return TREE_LEVEL_STYLE_NAME + levelName;
    }

    static final short[] LEVEL_COLORS = new short[10];
    static{
        LEVEL_COLORS[0] = HSSFColor.LIGHT_TURQUOISE.index;
        LEVEL_COLORS[1] = HSSFColor.GREY_25_PERCENT.index;
        LEVEL_COLORS[2] = HSSFColor.LIGHT_YELLOW.index;
        LEVEL_COLORS[3] = HSSFColor.PINK.index;
        LEVEL_COLORS[4] = HSSFColor.LIGHT_BLUE.index;
        LEVEL_COLORS[5] = HSSFColor.GREEN.index;
        LEVEL_COLORS[6] = HSSFColor.LIGHT_ORANGE.index;
        LEVEL_COLORS[7] = HSSFColor.BROWN.index;
        LEVEL_COLORS[8] = HSSFColor.LIGHT_CORNFLOWER_BLUE.index;
        LEVEL_COLORS[9] = HSSFColor.DARK_YELLOW.index;
    }

}
