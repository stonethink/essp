package client.essp.tc.pmmanage.onWeek;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class TableTcSumOnWeek extends JTable {
    private boolean doLayoutLock = true;
    private int[] columnWidths = new int[11];
    public final static int ROW_HEIGHT = 22;

    public TableTcSumOnWeek(TableModel dm) {
        super(dm);
        this.setRowHeight(ROW_HEIGHT);

        if (this.getModel().getColumnCount() != columnWidths.length) {
            throw new RuntimeException("Error model of sum table.");
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
