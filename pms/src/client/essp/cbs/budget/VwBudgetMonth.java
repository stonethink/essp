package client.essp.cbs.budget;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import c2s.essp.cbs.budget.DtoCbsBudget;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.treeTable.TreeTableModelAdapter;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJTableEditor;
import client.framework.view.vwcomp.VWJTableRender;
import client.framework.view.vwcomp.VWJTreeTable;
import client.essp.common.TableUitl.TableColumnWidthSetter;

public class VwBudgetMonth extends VWTableWorkArea {
    private DtoCbsBudget budget;
    private BudgetMonthTableModel model;
    private TreeTableModelAdapter treeModel ;

    VWJReal inputReal = new VWJReal();
    VWJTableRender render;
    VWJTableEditor editor;
    public VwBudgetMonth(VWJTreeTable subjectTree) {
        try {
            treeModel = (TreeTableModelAdapter)subjectTree.getModel();
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        try {
            inputReal.setMaxInputIntegerDigit(8);
            inputReal.setMaxInputDecimalDigit(2);

            model = new BudgetMonthTableModel(treeModel);
            table = new VWJTable(model);

            setTable();
            this.add(table.getScrollPane(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTable() {
        render = new VWJTableRender(inputReal);
        table.setDefaultRenderer(Double.class,render);
        editor = new VWJTableEditor(inputReal);
        table.setDefaultEditor(Double.class,editor);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setHeaderRender();
//        TableColumnWidthSetter.set(table);
    }
    public DtoCbsBudget getBudget() {
        return budget;
    }
    public void setBudget(DtoCbsBudget budget) {
        this.budget = budget;
    }
    public void resetUI(){
        if(budget != null){
            List columnConfigs = getColumnConfigs();
            model.setColumnConfigs(columnConfigs);
            setTable();
            model.setBudget(budget);
        }
    }
    private List getColumnConfigs(){
        List month = budget.getMonthList();
        List result = new ArrayList(month.size());
        VWJReal inputReal = new VWJReal();
        inputReal.setMaxInputIntegerDigit(8);
        inputReal.setMaxInputDecimalDigit(2);
        for(int i = 0; i < month.size(); i ++){
            Object[] columnConfig = new Object[]{month.get(i),month.get(i),VMColumnConfig.EDITABLE,inputReal,Boolean.TRUE,"50"};
            VMColumnConfig config = new VMColumnConfig(columnConfig);
            result.add(config);
        }
        return result;
    }

    public VMTable2 getModel(){
        return this.model;
    }

    public VWJTable getTable(){
        return this.table;
    }
    public void setReadOnly(boolean b){
        model.setReadOnly(b);
    }
}
