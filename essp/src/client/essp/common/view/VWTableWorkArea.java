package client.essp.common.view;

import client.framework.model.VMTable2;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJTable;
import com.wits.util.TestPanel;
import client.essp.common.TableUitl.TableColumnWidthSetter;

public class VWTableWorkArea extends VWGeneralWorkArea{

    /**
     * define common data (globe)
     */
    protected VMTable2 model;
    protected VWJTable table;

    public VWTableWorkArea() {
        super();
    }

    protected void jbInit(Object[][] configs, Class dtoClass) throws Exception {
        this.jbInit(configs, dtoClass, new NodeViewManager());
    }
    
    protected void jbInit(Object[][] configs, Class dtoClass, NodeViewManager nodeViewManager) throws Exception {
        //table
        model = new VMTable2(configs);
        model.setDtoType(dtoClass);
        table = new VWJTable(model, nodeViewManager);
        TableColumnWidthSetter.set(table);

        this.add(table.getScrollPane(), null);
    }

    public VMTable2 getModel() {
        return this.model;
    }

    public VWJTable getTable() {
        return this.table;
    }

    public void setSelectedRow(int rowIndex) {
        this.table.setSelectRow(rowIndex);
    }

    public Object getSelectedData() {
        int i = this.table.getSelectedRow();
        if( i >= 0 && i < this.table.getRowCount() ){
            return this.model.getRow(i);
        }else{
            return null;
        }
    }

    public static void main(String args[]){
        TestPanel.show( new VWTableWorkArea() );
    }


}
