package client.framework.model;

import java.util.List;

public interface IColumnConfig {
    /**
     * ��������Ҫ��ʾ����
     * @return List
     */
    public List getColumnConfigs();

    /**
     * �������е���,������������
     * ԭ��Ϊһ��Map,�������Ҫ��Ϊһ��List����ΪMap���˳��Ū��.modify by:Robin.Zhang
     * @return List
     */
    public List getColumnMap();

    /**
     *
     * @param columnConfigs List
     * @param bReSetColumnMap boolean -- ����columnConfigsʱ���Ƿ�����columnMap
     *     ��ֵcolumnMapָʹcolumnMap��Ԫ�غ�columnConfigs����ͬ.
     */
    public  void setColumnConfigs(List columnConfigs, boolean bReSetColumnMap);

    /**
     * return the type of object in the treetable.
     * Generally, the return type is an implement of IDto.
     */
    public Class getDtoType();

}
