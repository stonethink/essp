package client.essp.tc.pmmanage.onMonth;

import javax.swing.table.TableModel;

import client.framework.view.vwcomp.VWJTable;

/**
 * 表TableTcOnMonth, TableTcSumOnMonth的列的宽度是有关联的
 * 目的：调整TableByWorker时，TableTcSumOnMonth表的宽度要跟着变
 */
public class TableTcOnMonth extends VWJTable {
    TableTcSumOnMonth sumTable = null;

    public final static int HEADER_HEIGHT = 25;

    public TableTcOnMonth(TableModel m) {
        super(m);
    }

    public void setTableSum(TableTcSumOnMonth table) {
        this.sumTable = table;
    }

    /**
     * 1.设置每列的宽度
     * 2.得到表TableSum的每列宽度，并调用该表的doLayout调整列宽
     */
    public void doLayout() {
        super.doLayout();

        if (this.sumTable == null) {
            return;
        }

        int sumTableColumnWidths[] = sumTable.getColumnWidths();
        int cw1 = columnModel.getColumn(0).getWidth();
        int cw2 = columnModel.getColumn(1).getWidth();
        sumTableColumnWidths[0] = cw1 + cw2 + 2;

        sumTableColumnWidths[1] = columnModel.getColumn(2).getWidth();

        int cw3 = columnModel.getColumn(3).getWidth();
        sumTableColumnWidths[2] = cw3;

        int cw4 = columnModel.getColumn(4).getWidth();
        sumTableColumnWidths[3] = cw4;

        sumTable.setLayoutLock(false);
        sumTable.doLayout();
        sumTable.setLayoutLock(true);
    }
}
