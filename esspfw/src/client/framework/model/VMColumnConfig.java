package client.framework.model;

public class VMColumnConfig {
    public static final String EDITABLE = "Editable";
    public static final String UNEDITABLE = "Uneditable";

    String name = "";
    String dataName = "";
    boolean editable = false;
    String dataFormat = "";
//    String dataType = TYPE_NORMAL;
    java.awt.Component component = null;

    boolean isHidden = false;

    int preferWidth = -1;

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
    public VMColumnConfig(Object[] columnConfig) {
        if (columnConfig == null) {
            return;
        }

        if (columnConfig.length > 0) {
            this.setName( (String) columnConfig[0]);
            this.setDataName( (String) columnConfig[0]);
        }
        if (columnConfig.length > 1) {
            this.setDataName( (String) columnConfig[1]);
        }
        if (columnConfig.length > 2) {
            setEditable( (String) columnConfig[2]);
        }
        if (columnConfig.length > 3) {
            this.setComponent( (java.awt.Component) columnConfig[3]);
        }
        if (columnConfig.length > 4) {
            this.setIsHidden( ( (Boolean) columnConfig[4] ).booleanValue() );
        }
        if (columnConfig.length > 5) {
            Integer w = new Integer( (String)columnConfig[5] );
            this.setPreferWidth( w.intValue() );
        }
    }

    public void setPreferWidth(int preferWidth){
        this.preferWidth = preferWidth;
    }

    public int getPreferWidth(){
        return this.preferWidth;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public boolean getEditable() {
        return editable;
        //return true;
    }

    public void setEditable(String editable) {
        if (EDITABLE.equals(editable)) {
            this.editable = true;
        } else {
            this.editable = false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.awt.Component getComponent() {
        return component;
    }

    public void setComponent(java.awt.Component component) {
        if( component != null ){
            this.component = component;
            this.component.setName(getDataName());
        }
    }

    public boolean getIsHidden(){
        return this.isHidden;
    }

}
