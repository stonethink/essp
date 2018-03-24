package client.framework.model;

import java.util.List;

public interface IColumnConfig {
    /**
     * 返回所有要显示的列
     * @return List
     */
    public List getColumnConfigs();

    /**
     * 返回所有的列,包括隐含的列
     * 原来为一个Map,后根据需要改为一个List，因为Map会把顺序弄乱.modify by:Robin.Zhang
     * @return List
     */
    public List getColumnMap();

    /**
     *
     * @param columnConfigs List
     * @param bReSetColumnMap boolean -- 设置columnConfigs时，是否重置columnMap
     *     重值columnMap指使columnMap的元素和columnConfigs的相同.
     */
    public  void setColumnConfigs(List columnConfigs, boolean bReSetColumnMap);

    /**
     * return the type of object in the treetable.
     * Generally, the return type is an implement of IDto.
     */
    public Class getDtoType();

}
