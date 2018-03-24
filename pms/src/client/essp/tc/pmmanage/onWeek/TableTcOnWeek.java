package client.essp.tc.pmmanage.onWeek;

import javax.swing.table.TableModel;

import client.framework.view.vwcomp.VWJTable;

/**
 * 表TableByWorker, TableSumByWorker的列的宽度是有关联的
 * 目的：它们都含相同的8个列（一个星期7天 + sum列 ），这8列要对齐
 *      调整TableByWorker时，另外表的宽度要跟着变
 */
public class TableTcOnWeek extends VWJTable {
    TableTcSumOnWeek sumTable = null;

    public final static int HEADER_HEIGHT = 25;

    public TableTcOnWeek(TableModel m) {
        super(m);
    }

    public void setTableSum(TableTcSumOnWeek table) {
        this.sumTable = table;
    }

    /**
     * 1.设置每列的宽度
     * 2.得到表TableSum的每列宽度，并调用该表的doLayout调整列宽
     * 2.得到表TableOvertime的每列宽度，并调用该表的doLayout调整列宽
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

        for (int i = 2; i < 10; i++) {
            int cw = columnModel.getColumn(i).getWidth();
            sumTableColumnWidths[i - 1] = cw;
        }

        int cw11 = columnModel.getColumn(10).getWidth();
        int cw12 = columnModel.getColumn(11).getWidth();
        sumTableColumnWidths[9] = cw11 + cw12;

        sumTableColumnWidths[10] = columnModel.getColumn(12).getWidth();

        sumTable.setLayoutLock(false);
        sumTable.doLayout();
        sumTable.setLayoutLock(true);
    }

    public void superDoLayout() {
        super.doLayout();
    }
}
