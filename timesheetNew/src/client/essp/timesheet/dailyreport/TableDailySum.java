package client.essp.timesheet.dailyreport;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import client.essp.timesheet.weeklyreport.common.ColumnWithListener;
import client.essp.timesheet.weeklyreport.summary.VMTableSum;

public class TableDailySum extends JTable implements ColumnWithListener {
	private boolean doLayoutLock = true;
    private int[] columnWidths;
    public final static int ROW_HEIGHT = 22;

    public TableDailySum(TableModel dm) {
        super(dm);
        this.setRowHeight(ROW_HEIGHT);
    }

    /**
     * 根据this.columnWidths设置列的宽度
     * TableWeeklyReport.doLayout会设置columnWidths的值
     * 目的：本表的列宽与TableWeeklyReport的列宽保持一致
     */
    public void doLayout() {
        if (doLayoutLock == false && columnWidths != null) {
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                TableColumn column = columnModel.getColumn(i);
                column.setWidth(columnWidths[i]);
            }
        }
    }

    public void columnWidthChanged(int[] widths) {
        int length = widths.length;
        if(length < 1) return;
        columnWidths = new int[2];
        columnWidths[0] = 0;
        for (int i = 0; i < length; i++) {
            if(i < 3) {
                columnWidths[0] += widths[i];
            } else {
                columnWidths[1] = widths[i];
            }
        }
        doLayoutLock = false;
        doLayout();
        doLayoutLock = true;
    }

}
