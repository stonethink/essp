package client.essp.cbs.budget.labor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import c2s.essp.cbs.budget.DtoCbsBudget;
import c2s.essp.cbs.budget.DtoResBudgetItem;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJTableEditor;
import client.framework.view.vwcomp.VWJTableRender;
import client.framework.view.vwcomp.NodeViewManager;

public class VwLaborBudgetMonth extends VWTableWorkArea {
    LaborBgtMonthTableModel model ;
    List monthList;

    VWJReal inputReal = new VWJReal();
    VWJTableRender render;
    VWJTableEditor editor;
    NodeViewManager nodeViewManager;
    public VwLaborBudgetMonth(){
        this(null);
    }
    public VwLaborBudgetMonth(List monthList) {
        this.monthList = monthList;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        try{
            inputReal.setMaxInputDecimalDigit(2);
            inputReal.setMaxInputIntegerDigit(8);
            List columnConfigs = getColumnConfigs();
            model = new LaborBgtMonthTableModel(columnConfigs);
            nodeViewManager = new LaborBgtNodeViewManager();
            table = new VWJTable(model,nodeViewManager);
            setTable();
            model.setDtoType(DtoResBudgetItem.class);
            this.add(table.getScrollPane(), null);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void setTable() {
        render = new VWJTableRender(inputReal);
        render.setNodeViewManager(nodeViewManager);
       table.setDefaultRenderer(Double.class,render);
       editor = new VWJTableEditor(inputReal);
       table.setDefaultEditor(Double.class,editor);
       table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       table.setHeaderRender();
    }
    public VMTable2 getModel(){
        return model;
    }
    public void resetUI(){
        List columnConfigs = getColumnConfigs();
        model.setColumnConfigs(columnConfigs);
        setTable();
    }
    private List getColumnConfigs(){
        if(monthList == null)
            return new ArrayList();
        List result = new ArrayList(monthList.size());
        VWJReal inputReal = new VWJReal();
        inputReal.setMaxInputDecimalDigit(2);
        inputReal.setMaxInputIntegerDigit(4);
        for(int i = 0; i < monthList.size(); i ++){
            Object[] columnConfig = new Object[]{monthList.get(i),monthList.get(i),VMColumnConfig.EDITABLE,inputReal,Boolean.TRUE,"10"};
            VMColumnConfig config = new VMColumnConfig(columnConfig);
            result.add(config);
        }
        return result;
    }

    public void setMonthList(List monthList) {
        this.monthList = monthList;
    }

    public List getMonthList() {
        return monthList;
    }
}
