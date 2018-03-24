package client.essp.common.view;

import java.awt.Dimension;

import c2s.dto.ITreeNode;
import client.framework.model.VMTreeTableModel;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJTreeTable;
import com.wits.util.TestPanel;
import client.essp.common.TableUitl.TableColumnWidthSetter;

public class VWTreeTableWorkArea extends VWGeneralWorkArea {

    /**
     * define common data (globe)
     */
    protected VMTreeTableModel model;
    protected VWJTreeTable treeTable;

    public VWTreeTableWorkArea() {
        super();
    }

    //Component initialization
    /**
     * @note: 在子类的构造函数中调用
     *
     * @param configs Object[][]  ——table的config。具体参加VMColumnConfig类
     * @param treeColumnName String  ——表格中显示tree的那列的名字（dto中对应属性的名字）
     * @param dtoClass Class  ——表格中数据的dto的class
     * @throws Exception
     */
    protected void jbInit(Object[][] configs, String treeColumnName, Class dtoClass) throws Exception {
        jbInit(configs, treeColumnName, dtoClass, new NodeViewManager());
    }

    //Component initialization
    protected void jbInit(Object[][] configs, String treeColumnName, Class dtoClass, NodeViewManager nodeViewManager) throws Exception {
        this.setPreferredSize(new Dimension(680, 240));

        //tree table
        model = new VMTreeTableModel(null, configs, treeColumnName);
        model.setDtoType(dtoClass);
        treeTable = new VWJTreeTable(model, nodeViewManager);
        TableColumnWidthSetter.set(treeTable);

//        JScrollPane scrollPane = new JScrollPane(treeTable);

        this.add(treeTable.getScrollPane(), null);
    }


    public VMTreeTableModel getModel() {
        return this.model;
    }

    public VWJTreeTable getTreeTable() {
        return this.treeTable;
    }

    private void setSelectedRow(int rowIndex) {
        this.treeTable.setSelectedRow(rowIndex);
    }

    public ITreeNode getSelectedNode() {
        return this.treeTable.getSelectedNode();
    }

    public Object getSelectedData() {
        if( this.treeTable.getSelectedNode() != null ){
            return this.treeTable.getSelectedNode().getDataBean();
        }else{
            return null;
        }
    }

    public static void main(String args[]){
        TestPanel.show( new VWTreeTableWorkArea() );
    }

}
