package client.essp.tc.pmmanage.onWeek;

import javax.swing.table.TableModel;

import client.framework.view.vwcomp.VWJTable;

/**
 * ��TableByWorker, TableSumByWorker���еĿ�����й�����
 * Ŀ�ģ����Ƕ�����ͬ��8���У�һ������7�� + sum�� ������8��Ҫ����
 *      ����TableByWorkerʱ�������Ŀ��Ҫ���ű�
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
     * 1.����ÿ�еĿ��
     * 2.�õ���TableSum��ÿ�п�ȣ������øñ��doLayout�����п�
     * 2.�õ���TableOvertime��ÿ�п�ȣ������øñ��doLayout�����п�
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
