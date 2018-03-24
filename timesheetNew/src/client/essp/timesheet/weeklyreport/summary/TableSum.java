package client.essp.timesheet.weeklyreport.summary;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import client.essp.timesheet.weeklyreport.common.ColumnWithListener;

/**
 *
 * <p>Title: TableSum</p>
 *
 * <p>Description: 工时单汇总Table</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class TableSum extends JTable implements ColumnWithListener{
    private boolean doLayoutLock = true;
    private int[] columnWidths;
    public final static int ROW_HEIGHT = 22;

    public TableSum(TableModel dm) {
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
        columnWidths = new int[length - 1];
        columnWidths[0] = 0;
        for (int i = 0; i < length; i++) {
            if(i < VMTableSum.COLUMN_OFFSET) {
                columnWidths[0] += widths[i];
            } else {
                columnWidths[i - (VMTableSum.COLUMN_OFFSET - 1)] = widths[i];
            }
        }
        doLayoutLock = false;
        doLayout();
        doLayoutLock = true;
    }

}
