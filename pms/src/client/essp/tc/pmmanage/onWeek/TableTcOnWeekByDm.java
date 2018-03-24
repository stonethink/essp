package client.essp.tc.pmmanage.onWeek;

import javax.swing.table.TableModel;

/**
 * 表TableByWorker, TableSumByWorker的列的宽度是有关联的
 * 目的：它们都含相同的8个列（一个星期7天 + sum列 ），这8列要对齐
 *      调整TableByWorker时，另外表的宽度要跟着变
 */
public class TableTcOnWeekByDm extends TableTcOnWeek {
    public TableTcOnWeekByDm(TableModel m) {
        super(m);
    }

    /**
     * 1.设置每列的宽度
     * 2.得到表TableSum的每列宽度，并调用该表的doLayout调整列宽
     * 2.得到表TableOvertime的每列宽度，并调用该表的doLayout调整列宽
     */
    public void doLayout() {
        super.superDoLayout();

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
        sumTableColumnWidths[9] = cw11;

        int cw12 = columnModel.getColumn(11).getWidth();
        sumTableColumnWidths[10] = cw12;

        sumTable.setLayoutLock(false);
        sumTable.doLayout();
        sumTable.setLayoutLock(true);
    }
}
