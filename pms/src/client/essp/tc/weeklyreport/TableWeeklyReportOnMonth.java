package client.essp.tc.weeklyreport;

import javax.swing.table.TableModel;
import client.framework.view.vwcomp.NodeViewManager;

/**
 * 三个表TableWeeklyreport, TableSum, TableOvertime的列的宽度是有关联的
 * 目的：它们都含相同的8个列（一个星期7天 + sum列 ），这8列要对齐
 *      调整TableWeeklyreport时，另外两表的宽度要跟着变
 */
public class TableWeeklyReportOnMonth extends TableWeeklyReport {

    public TableWeeklyReportOnMonth(TableModel m) {
        super(m);
    }

    public TableWeeklyReportOnMonth(TableModel m, NodeViewManager manager) {
        super(m, manager);
    }

    /**
     * 1.设置每列的宽度
     * 2.得到表TableSum的每列宽度，并调用该表的doLayout调整列宽
     * 2.得到表TableOvertime的每列宽度，并调用该表的doLayout调整列宽
     */
    public void doLayout() {
        super.superDoLayout();

        if (this.sumTable == null || this.overtimeTable == null) {
            return;
        }

        int sumTableColumnWidths[] = sumTable.getColumnWidths();
        int overtimeTableColumnWidths[] = overtimeTable.getColumnWidths();
        int sumIndex = 0;
        int overtimeIndex = 0;

        if (getModel().getColumnCount() > 4) {
            int cw1 = columnModel.getColumn(0).getWidth();
            int cw2 = columnModel.getColumn(1).getWidth();
            int cw3 = columnModel.getColumn(2).getWidth();
            int cw4 = columnModel.getColumn(3).getWidth();
            sumTableColumnWidths[sumIndex++] = cw1 + cw2 + cw3 + cw4 + 2;

            overtimeTableColumnWidths[overtimeIndex++] = cw1 + 1;
            overtimeTableColumnWidths[overtimeIndex++] = cw2;
            overtimeTableColumnWidths[overtimeIndex++] = cw3 + cw4;
        }

        for (int i = weekrptModel.SATURDAY_COLUMN_INDEX; i <= weekrptModel.SUMMARY_COLUMN_INDEX; i++) {
            int cw = columnModel.getColumn(i).getWidth();
            sumTableColumnWidths[sumIndex++] = cw;
            overtimeTableColumnWidths[overtimeIndex++] = cw;
        }

        sumTableColumnWidths[sumIndex++] = columnModel.getColumn(weekrptModel.COMMENTS_COLUMN_INDEX).getWidth();
        overtimeTableColumnWidths[overtimeIndex++] = columnModel.getColumn(weekrptModel.COMMENTS_COLUMN_INDEX).getWidth();

        sumTable.setLayoutLock(false);
        sumTable.doLayout();
        sumTable.setLayoutLock(true);

        overtimeTable.setLayoutLock(false);
        overtimeTable.doLayout();
        overtimeTable.setLayoutLock(true);
    }
}
