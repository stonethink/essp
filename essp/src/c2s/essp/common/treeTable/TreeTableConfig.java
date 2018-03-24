package c2s.essp.common.treeTable;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TreeTableConfig {
    public TreeTableConfig() {
    }

    public static final String STYLE_TEXT = "OraCellText";
    public static final String STYLE_NUMBER = "OraCellnumber";
    public static final String STYLE_DATE = "OraCelldate";
    public static final String STYLE_CENTER = "OraCellCenter";

    private String name;
    private String style = STYLE_TEXT;
    private boolean herf = false;
    private boolean treeColumn = false;


//    String dataType ="String";
    /**
     * 参数为一个数组,按顺序,每个元素的意思为:
     * 1. name 列名,用于显示
     * 2. dataName 列对应的属性名,用于取本列的数据
     * 3. editable 列是否可以编辑
     * 4. component 用于编辑该列的控件,比如JText
     * 5. isHidden 控制列是否显示
     * 6. preferWidth 列的宽度
     * @param columnConfig Object[]
     */
    public TreeTableConfig(Object[] columnConfig) {

        if (columnConfig == null) {
            return;
        }

        if (columnConfig.length > 0) {
            this.setName((String) columnConfig[0]);
        }
        if (columnConfig.length > 1) {
            this.setHerf(((Boolean) columnConfig[1]).booleanValue());
        }
        if (columnConfig.length > 2) {
            this.setStyle((String) columnConfig[2]);
        }
        if (columnConfig.length > 3) {
            this.setTreeColumn(((Boolean) columnConfig[3]).booleanValue());
        }
        return;
    }

    public boolean isHerf() {
        return herf;
    }

    public String getName() {
        return name;
    }

    public String getStyle() {
        return style;
    }

    public boolean isTreeColumn() {
        return treeColumn;
    }

    public void setHerf(boolean herf) {
        this.herf = herf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setTreeColumn(boolean treeColumn) {
        this.treeColumn = treeColumn;
    }

}
