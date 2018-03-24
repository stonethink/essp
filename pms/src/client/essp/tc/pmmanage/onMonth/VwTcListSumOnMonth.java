package client.essp.tc.pmmanage.onMonth;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;

import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.RowHeaderRenderer;
import client.essp.tc.common.RowRenderer;
import client.essp.tc.common.TcLineBorder;
import com.wits.util.TestPanel;
import client.essp.tc.common.RowRendererTwoNumber;

public class VwTcListSumOnMonth extends VWGeneralWorkArea {
    public TableTcSumOnMonth table = null;
    VMTableTcSumOnMonth model = null;

    RowHeaderRenderer rowHeaderRenderer = new RowHeaderRenderer();
//    RowRenderer rowRendererReal = new RowRenderer(RowRenderer.real, RowRenderer.noColor);
    RowRendererTwoNumber rowRendererTwoNum = new RowRendererTwoNumber(RowRenderer.noColor);

    public VwTcListSumOnMonth() {
        try {
            model = new VMTableTcSumOnMonth();
            table = new TableTcSumOnMonth(model);

            this.add(table, null);

            initUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置表的外观
    private void initUI() {
        //设置表格的边框
        TcLineBorder tableBorder = new TcLineBorder(UIManager.getColor(
                "Table.gridColor"));
        tableBorder.setShowBottom(false);
        tableBorder.setShowLeft(false);
        tableBorder.setShowRight(false);
        table.setBorder(tableBorder);

        //第一列的描绘器
        TableColumn rowHeader = table.getColumnModel().getColumn(0);
        rowHeader.setCellRenderer(this.rowHeaderRenderer);

        //时间列的描绘器
        //rowRendererReal.setSelectedBackgroundColor(RowRenderer.noColor);
        TableColumn column = table.getColumnModel().getColumn(VMTableTcSumOnMonth.SUMMARY_COLUMN_INDEX);
        column.setCellRenderer(this.rowRendererTwoNum);

        //overtime
        TableColumn columnOvertime = table.getColumnModel().getColumn(VMTableTcSumOnMonth.OVERTIME_COLUMN_INDEX);
        columnOvertime.setCellRenderer(this.rowRendererTwoNum);

        //allocate time
        TableColumn columnAllocate = table.getColumnModel().getColumn(VMTableTcSumOnMonth.ALLOCATE_COLUMN_INDEX);
        columnAllocate.setCellRenderer(this.rowRendererTwoNum);

        this.setMinimumSize(new Dimension( -1, this.getMinmizeTableHeight()));
        this.setPreferredSize(new Dimension( -1, this.getPreferredTableHeight()));
        this.setMaximumSize(new Dimension( -1, this.getMaxmizeTableHeight()));
    }

    public JTable getTable() {
        return this.table;
    }

    public void refreshTable(){
        int i = table.getSelectedRow();
        if( i >= 0 && i < table.getRowCount() ){
            model.fireTableRowsUpdated(i, i);
        }
    }

    //表的高度
    public int getPreferredTableHeight() {
        return TableTcSumOnMonth.ROW_HEIGHT * 1 + 0;
    }

    //表的最小高度
    public int getMinmizeTableHeight() {
        return getPreferredTableHeight();
    }

    //表的最da高度
    public int getMaxmizeTableHeight() {
        return getPreferredTableHeight();
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwTcListSumOnMonth();

        w1.addTab("Sum", workArea);
        TestPanel.show(w1);
    }
}
