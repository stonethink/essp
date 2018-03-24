package client.essp.tc.weeklyreport;

import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJTable;

public class TableOvertime extends VWJTable {
    private boolean doLayoutLock = true;
    private int[] columnWidths = new int[12];

    public TableOvertime(TableModel dm, NodeViewManager nodeViewManager) {
        super(dm, nodeViewManager);

        if (this.getModel().getColumnCount() != columnWidths.length) {
            throw new RuntimeException("Error model of overtime table.");
        }
    }

    /**
     * ����this.columnWidths�����еĿ��
     * TableWeeklyReport.doLayout������columnWidths��ֵ
     * Ŀ�ģ�������п���TableWeeklyReport���п���һ��
     */
    public void doLayout() {
        if (doLayoutLock == false && columnWidths != null) {
            for (int i = 0; i < columnWidths.length; i++) {
                TableColumn column = columnModel.getColumn(i);
                column.setWidth(columnWidths[i]);
            }
        }
    }

    public void setLayoutLock(boolean lock) {
        this.doLayoutLock = lock;
    }

    public void setColumnWidths(int[] widths) {
        this.columnWidths = widths;
    }

    public int[] getColumnWidths() {
        return this.columnWidths;
    }
}
