package client.framework.model;

import client.framework.common.treeTable.TreeTableModelAdapter;
import java.util.List;
import client.framework.common.treeTable.TreeTableModel;
import javax.swing.JTree;

public class VMTreeTableModelAdapter extends TreeTableModelAdapter implements IColumnConfig{
    IColumnConfig columnConfig;

    public VMTreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree) {
        super(treeTableModel,tree);
        if( treeTableModel instanceof IColumnConfig ){
            columnConfig = ( (IColumnConfig)treeTableModel );
        }
    }

    public List getColumnConfigs(){
        return columnConfig.getColumnConfigs();
    }

     /**
      * return the type of object in the treetable.
      * Generally, the return type is an implement of IDto.
      */
    public Class getDtoType(){
        return columnConfig.getDtoType();
    }

    public List getColumnMap() {
        return columnConfig.getColumnMap();
    }

    public void  setColumnConfigs(List columnConfigs, boolean bFlag) {
        columnConfig.setColumnConfigs(columnConfigs,bFlag);
    }


}
