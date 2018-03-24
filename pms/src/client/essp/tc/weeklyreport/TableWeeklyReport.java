package client.essp.tc.weeklyreport;

import javax.swing.table.TableModel;

import client.framework.view.vwcomp.VWJTable;
import java.awt.Component;
import client.framework.view.vwcomp.NodeViewManager;

/**
 * 三个表TableWeeklyreport, TableSum, TableOvertime的列的宽度是有关联的
 * 目的：它们都含相同的8个列（一个星期7天 + sum列 ），这8列要对齐
 *      调整TableWeeklyreport时，另外两表的宽度要跟着变
 */
public class TableWeeklyReport extends VWJTable {

    protected TableSum sumTable = null;
    protected TableOvertime overtimeTable = null;
    boolean isSumTableVisible = true;
    boolean isOvertimeTableVisible = true;

    protected VMTableWeeklyReport weekrptModel = null;

    public final static int HEADER_HEIGHT = 25;

    public TableWeeklyReport(TableModel m) {
        this(m, null);
    }

    public TableWeeklyReport(TableModel m, NodeViewManager nodeViewManager) {
        super(m, nodeViewManager);
        weekrptModel = (VMTableWeeklyReport) m;
    }

    public void setTableSum(TableSum table) {
        this.sumTable = table;
        Component comp = table;
        this.isSumTableVisible = true;
        do{
            if( comp.isVisible() == false ){
                this.isSumTableVisible = false;
                break;
            }
        }while( (comp = comp.getParent()) != null );
    }

    public void setTableOvertime(TableOvertime table) {
        this.overtimeTable = table;
        Component comp = table;
        this.isOvertimeTableVisible = true;
        do{
            if( comp.isVisible() == false ){
                this.isOvertimeTableVisible = false;
                break;
            }
        }while( (comp = comp.getParent()) != null );

    }

    protected void superDoLayout(){
        super.doLayout();
    }

    /**
     * 1.设置每列的宽度
     * 2.得到表TableSum的每列宽度，并调用该表的doLayout调整列宽
     * 2.得到表TableOvertime的每列宽度，并调用该表的doLayout调整列宽
     */
    public void doLayout() {
        super.doLayout();

        if (this.sumTable == null || this.overtimeTable == null) {
            return;
        }

        int sumTableColumnWidths[] = sumTable.getColumnWidths();
        int overtimeTableColumnWidths[] = overtimeTable.getColumnWidths();
        int sumIndex = 0;
        int overtimeIndex = 0;

        if (getModel().getColumnCount() > 3) {
            int cw1 = columnModel.getColumn(0).getWidth();
            int cw2 = columnModel.getColumn(1).getWidth();
            int cw3 = columnModel.getColumn(2).getWidth();
            sumTableColumnWidths[sumIndex++] = cw1 + cw2 + cw3 + 2;

            overtimeTableColumnWidths[overtimeIndex++] = cw1 + 1;
            overtimeTableColumnWidths[overtimeIndex++] = cw2;
            overtimeTableColumnWidths[overtimeIndex++] = cw3;
        }

        for (int i = weekrptModel.SATURDAY_COLUMN_INDEX; i <= weekrptModel.SUMMARY_COLUMN_INDEX; i++) {
            int cw = columnModel.getColumn(i).getWidth();
            sumTableColumnWidths[sumIndex++] = cw;
            overtimeTableColumnWidths[overtimeIndex++] = cw;
        }

        int commentsColumnWidth = columnModel.getColumn(weekrptModel.COMMENTS_COLUMN_INDEX).getWidth();
        sumTableColumnWidths[sumIndex++] = commentsColumnWidth;
        overtimeTableColumnWidths[overtimeIndex++] = commentsColumnWidth;

        if( isSumTableVisible ){
            sumTable.setLayoutLock(false);
            sumTable.doLayout();
            sumTable.setLayoutLock(true);
        }

        if( isOvertimeTableVisible ){
            overtimeTable.setLayoutLock(false);
            overtimeTable.doLayout();
            overtimeTable.setLayoutLock(true);
        }
    }
}
