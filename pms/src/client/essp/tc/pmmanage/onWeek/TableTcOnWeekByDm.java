package client.essp.tc.pmmanage.onWeek;

import javax.swing.table.TableModel;

/**
 * ��TableByWorker, TableSumByWorker���еĿ�����й�����
 * Ŀ�ģ����Ƕ�����ͬ��8���У�һ������7�� + sum�� ������8��Ҫ����
 *      ����TableByWorkerʱ�������Ŀ��Ҫ���ű�
 */
public class TableTcOnWeekByDm extends TableTcOnWeek {
    public TableTcOnWeekByDm(TableModel m) {
        super(m);
    }

    /**
     * 1.����ÿ�еĿ��
     * 2.�õ���TableSum��ÿ�п�ȣ������øñ��doLayout�����п�
     * 2.�õ���TableOvertime��ÿ�п�ȣ������øñ��doLayout�����п�
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
