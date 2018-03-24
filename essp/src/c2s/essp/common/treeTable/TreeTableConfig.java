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
     * ����Ϊһ������,��˳��,ÿ��Ԫ�ص���˼Ϊ:
     * 1. name ����,������ʾ
     * 2. dataName �ж�Ӧ��������,����ȡ���е�����
     * 3. editable ���Ƿ���Ա༭
     * 4. component ���ڱ༭���еĿؼ�,����JText
     * 5. isHidden �������Ƿ���ʾ
     * 6. preferWidth �еĿ��
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
