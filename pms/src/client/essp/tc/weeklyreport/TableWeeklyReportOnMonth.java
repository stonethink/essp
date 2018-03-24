package client.essp.tc.weeklyreport;

import javax.swing.table.TableModel;
import client.framework.view.vwcomp.NodeViewManager;

/**
 * ������TableWeeklyreport, TableSum, TableOvertime���еĿ�����й�����
 * Ŀ�ģ����Ƕ�����ͬ��8���У�һ������7�� + sum�� ������8��Ҫ����
 *      ����TableWeeklyreportʱ����������Ŀ��Ҫ���ű�
 */
public class TableWeeklyReportOnMonth extends TableWeeklyReport {

    public TableWeeklyReportOnMonth(TableModel m) {
        super(m);
    }

    public TableWeeklyReportOnMonth(TableModel m, NodeViewManager manager) {
        super(m, manager);
    }

    /**
     * 1.����ÿ�еĿ��
     * 2.�õ���TableSum��ÿ�п�ȣ������øñ��doLayout�����п�
     * 2.�õ���TableOvertime��ÿ�п�ȣ������øñ��doLayout�����п�
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
