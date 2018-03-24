package client.framework.view.vwcomp;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import java.awt.event.InputEvent;
import javax.swing.table.TableColumn;
import client.framework.model.IColumnConfig;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.SwingUtilities;

/**
 * 	触发行选择的时机:
 *       1.setRoot() / setRows()   触发 RowSelected事件,选择第一行 / 一行都没有时选择-1行
 *       2.addRow()   触发 RowSelectedLost
 *                    触发 RowSelected事件,选择新增的行
 *       3.deleteRow()  触发 RowSelected事件,选择删除行所在的行(也可能是其附近的行) / 一行都没有时选择-1行
 *       4.mouseClick    触发 RowSelectedLost
 *                       触发 RowSelected事件,选择点击的行
 *       5.取消选中行(比如ctrl+鼠标点击)  触发 RowSelectedLost
 *                                     触发 RowSelected事件,选中-1行
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

/**
 * @url element://model:project::esspfw/jdt:e_class:src:client.framework.view.vwcomp.VWJTableRender
 * @url element://model:project::esspfw/jdt:e_class:src:client.framework.model.VMTable2
 */
public class VWJTable extends JTable implements IVWJTableViewManager {
    public static final int TABLE_ROW_HEIGHT = 18;

    /**
     * 决定是否排序
     */
    private boolean sortable = false;
    private List rowSelectedListenerList = new ArrayList();
    private List rowSelectedLostListenerList = new ArrayList();
    int selectedRow;
    Object selectedData;
    JScrollPane scrollPane;
    NodeViewManager nodeViewManager;

    public VWJTable() {
        this(null);
    }

    public VWJTable(TableModel dm) {
        this(dm, new NodeViewManager());
    }

    public VWJTable(TableModel dm, NodeViewManager nodeViewManager) {
        super(dm);
        this.nodeViewManager = nodeViewManager;

        this.setRowHeight(TABLE_ROW_HEIGHT);
        ListSelectionModel listSelectionModel = this.getSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setRenderAndEditor();

        addUICEvent();
    }

    public void setRenderAndEditor(){
        VWUtil.setTableRender(this);
        VWUtil.setTableEditor(this);

        setHeaderRender();
    }

    public void setHeaderRender() {
        if (this.getModel() instanceof VMTable2) {
            IColumnConfig dm = (IColumnConfig)this.getModel();
            for (int i = 0; i < this.getColumnCount(); i++) {

                String columnName = this.getColumnName(i);
                TableColumn column = this.getColumn(columnName);
                column.setHeaderRenderer(new VWJTableHeaderRender((VMTable2)
                    dm));
            }
        }
    }

    private void addUICEvent() {
        getTableHeader().addMouseListener(new TableHeaderGesture());

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mouseClickedThis(e);
            }
        });
    }

    public void mouseClickedThis(MouseEvent e) {
        if ( (e.getModifiers() & InputEvent.CTRL_MASK ) != 0) {

            //不容许按ctrl键取消行选择
            if (getSelectedRow() == -1) {
                setSelectRow(selectedRow);
                return;
            }
        }

        fireSelected();
    }

    public void setSortable(boolean flag) {
        sortable = flag;
        if(sortable) {
        	this.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
        	this.getTableHeader().setCursor(Cursor.getDefaultCursor());
        }
    }

    public Object addRow() {
        int currRowIndex = getSelectedRow();
        if (currRowIndex < 0) {
            currRowIndex = 0;
        }

        Object dto = null;
        TableModel dm = getModel();
        if (dm instanceof VMTable2) {
            dto = ((VMTable2) dm).addRow(currRowIndex);
            int addRowIndex = 0;
            if (currRowIndex >= getRowCount()) {
                addRowIndex = getRowCount();
            } else {
                addRowIndex = currRowIndex + 1;
            }
//            ( (VMTable2) dm).fireTableRowsInserted(addRowIndex, addRowIndex);
            setSelectRowAndFire(addRowIndex, true);
        } else {
            return null;
        }
        return dto;
    }

    public Object addRow(Object dto2) {
        Object dto = dto2;
        int currRowIndex = getSelectedRow();
//        if (currRowIndex < 0) {
//            currRowIndex = 0;
//        }

//         Object dto = null;
        TableModel dm = getModel();
        if (dm instanceof VMTable2) {
            dto = ((VMTable2) dm).addRow(currRowIndex, dto);
            int addRowIndex = 0;
            if (currRowIndex >= getRowCount()) {
                addRowIndex = getRowCount();
            } else {
                addRowIndex = currRowIndex + 1;
            }
//             ( (VMTable2) dm).fireTableRowsInserted(addRowIndex, addRowIndex);
            setSelectRowAndFire(addRowIndex, true);
        } else {
            return null;
        }
        return dto;
    }


    public Object deleteRow() {
        int currRowIndex = getSelectedRow();
        if (currRowIndex < 0) {
            return null;
        }
        Object dto = null;
        TableModel dm = getModel();
        if (dm instanceof VMTable2) {
            dto = ((VMTable2) dm).deleteRow(currRowIndex);
            int iNextSelectedRowIndex = -1;
            if( currRowIndex < this.getRowCount() ){
                iNextSelectedRowIndex = currRowIndex;
            }else if( currRowIndex -1 >= 0 ){
                iNextSelectedRowIndex = currRowIndex-1;
            }

            if (iNextSelectedRowIndex >= 0) {
                setSelectRowAndFire(iNextSelectedRowIndex, false);
            }else{
            }

            this.fireRowSelected();
        } else {
            return null;
        }
        return dto;
    }

    public void setRows(List dtoList) {
        TableModel model = this.getModel();
        if (model instanceof VMTable2) {
            ((VMTable2) model).setRows(dtoList);

            if (dtoList == null || dtoList.size() == 0) {
            } else {
                this.setSelectRowAndFire(0, false);
            }

            fireRowSelected();
        }
    }

    public void setSelectRow(int row) {
        setSelectRowAndFire(row, true );
    }

    protected void setSelectRowAndFire(int row, boolean toFire) {
        int currRowIndex = row;
        if (currRowIndex < 0) {
            currRowIndex = 0;
        }
        if (currRowIndex >= getRowCount()) {
            currRowIndex = getRowCount();
        }
        if (currRowIndex >= 0 && currRowIndex < getRowCount()) {
        	this.getSelectionModel().clearSelection();
            this.setRowSelectionInterval(currRowIndex, currRowIndex);

            if (toFire == true) {
                fireSelected();
            }
        }
    }

    public Object getSelectedData() {
        int i = this.getSelectedRow();
        if (i >= 0 && i < getRowCount()) {
            TableModel model = this.getModel();
            if (model instanceof VMTable2) {
                return ((VMTable2) model).getRow(i);
            }
        }

        return null;
    }

    public void setNextSelectRow() {
        int nextRowIndex = getSelectedRow() + 1;
        setSelectRow(nextRowIndex);
    }

    public void addRowSelectedListener(RowSelectedListener listener) {
        this.rowSelectedListenerList.add(listener);
    }

    public void addRowSelectedLostListener(RowSelectedLostListener listener) {
        this.rowSelectedLostListenerList.add(listener);
    }

    public void fireSelected( ) {
        if (fireRowSelectedLost() == false) {

            //有listener不愿丢失select
            this.setSelectRowAndFire(selectedRow, false);
        } else {
            fireDiffRowSelected();
        }
    }

    //fire the listeners when a different row is selected.
    protected void fireDiffRowSelected() {
        Object currentSelectedData = this.getSelectedData();

        if (this.selectedData != currentSelectedData) {
            fireRowSelected();
        }
    }

    protected void fireRowSelected() {
        this.selectedRow = this.getSelectedRow();
        this.selectedData = this.getSelectedData();

        for (int i = 0; i < rowSelectedListenerList.size(); i++) {
            RowSelectedListener listener = (RowSelectedListener)
                                           rowSelectedListenerList.get(i);
            listener.processRowSelected();
        }
    }

    //fire the listeners when a different row is selected.
    protected boolean fireRowSelectedLost() {
        boolean bSelectedLost = true;

        Object nextSelectedData = this.getSelectedData();
        if( this.selectedData != nextSelectedData ){

            for (int i = 0; i < rowSelectedLostListenerList.size(); i++) {
                RowSelectedLostListener listener = (RowSelectedLostListener)
                    rowSelectedLostListenerList.get(i);
                if (listener.processRowSelectedLost(selectedRow, selectedData) == false) {

                    //记录下是否有listener不愿lost select
                    bSelectedLost = false;
                }
            }
        }
        return bSelectedLost;

    }


    public void refreshTable(){
        int i = this.getSelectedRow();
        if( i >= 0 && i < this.getRowCount() ){
            if( this.getModel() instanceof AbstractTableModel ){
                AbstractTableModel model = (AbstractTableModel)this.getModel();
                model.fireTableRowsUpdated(i,i);
            }
        }
    }

    public NodeViewManager getNodeViewManager() {
        return this.nodeViewManager;
    }

    public JScrollPane getScrollPane(){
        if( this.scrollPane == null ){
            this.scrollPane = new JScrollPane(this);
        }
        return this.scrollPane;
    }

    public void setCellComponent(int column, Component comp) {
        TableModel model = this.getModel();
        if (model instanceof VMTable2) {
            VMTable2 vmModel = (VMTable2) model;
            VMColumnConfig cfg = (VMColumnConfig) vmModel.getColumnConfigs().get(column);
            cfg.setComponent((Component) comp);
        }
    }

    public Component getCellComponent(int column) {
        TableModel model = this.getModel();
        if (model instanceof VMTable2) {
            VMTable2 vmModel = (VMTable2) model;
            VMColumnConfig cfg = (VMColumnConfig) vmModel.getColumnConfigs().
                                 get(column);
            return cfg.getComponent();
        } else {
            return null;
        }
    }

    public String getToolTipText(MouseEvent e) {
        int row = rowAtPoint(e.getPoint());
        int col = columnAtPoint(e.getPoint());
        if( col < 0 ){
            return null;
        }

        Component comp = getCellComponent(col);
        if (comp != null && (comp instanceof VWJText
                             || comp instanceof VWJTextArea
                             || comp instanceof VWJDisp)) {
            Object value = getValueAt(row, col);
            if (value != null) {
                return value.toString();
            }
        }

        return null;
    }

    private class TableHeaderGesture extends MouseAdapter {
        public void mouseClicked(MouseEvent me) {
            if (sortable) {
                int columnIndex = VWJTable.this.getTableHeader().columnAtPoint(me.getPoint());
                if (columnIndex != -1) {
                    TableModel model = VWJTable.this.getModel();
                    int modelColumnIndex = VWJTable.this.getTableHeader().getTable().convertColumnIndexToModel(columnIndex);
                    if (model instanceof IVWJTableSort) {
                        IVWJTableSort sorter = (IVWJTableSort) model;
                        sorter.sortByColumnIndex(modelColumnIndex);
                    }
                }
            }
        }
    }
}
