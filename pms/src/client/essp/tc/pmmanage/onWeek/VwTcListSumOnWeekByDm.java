package client.essp.tc.pmmanage.onWeek;

import java.awt.Dimension;

import javax.swing.UIManager;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.RowRenderer;
import client.essp.tc.common.TcLineBorder;
import com.wits.util.TestPanel;
import client.essp.tc.common.RowRendererTwoNumber;

public class VwTcListSumOnWeekByDm extends VwTcListSumOnWeek {

    //设置表的外观
    protected void initUI() {
        TableModel model = new VMTableTcSumOnWeekByDm();
        table = new TableTcSumOnWeekByDm(model);
        this.add(table, null);

        //设置表格的边框
        TcLineBorder tableBorder = new TcLineBorder(UIManager.getColor(
                "Table.gridColor"));
        tableBorder.setShowBottom(false);
        tableBorder.setShowLeft(false);
        tableBorder.setShowRight(false);
        table.setBorder(tableBorder);

        //第一列的描绘器
        TableColumn rowHeader = table.getColumnModel().getColumn(0);
        rowHeader.setCellRenderer(this.rowHeaderRenderer);

        //时间列的描绘器
        rowRendererReal.setSelectedBackgroundColor(RowRenderer.noColor);
        for (int i = VMTableTcSumOnWeek.SATURDAY_COLUMN_INDEX;
                     i <= VMTableTcSumOnWeek.SUMMARY_COLUMN_INDEX; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setCellRenderer(this.rowRendererReal);
        }

        RowRendererTwoNumber rowRenderer = new RowRendererTwoNumber();
        //overtime column
        TableColumn overtimeColumn = table.getColumnModel().getColumn(VMTableTcSumOnWeekByDm.OVERTIME_COLUMN_INDEX);
        overtimeColumn.setPreferredWidth(60);
        overtimeColumn.setMaxWidth(65);
        overtimeColumn.setCellRenderer(rowRenderer);

        //allocate column
        TableColumn allocateColumn = table.getColumnModel().getColumn(VMTableTcSumOnWeekByDm.ALLOCATE_COLUMN_INDEX);
        allocateColumn.setPreferredWidth(60);
        allocateColumn.setMaxWidth(65);
        allocateColumn.setCellRenderer(rowRenderer);

        this.setMinimumSize(new Dimension( -1, this.getMinmizeTableHeight()));
        this.setPreferredSize(new Dimension( -1, this.getPreferredTableHeight()));
        this.setMaximumSize(new Dimension( -1, this.getMaxmizeTableHeight()));
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwTcListSumOnWeek();

        w1.addTab("Sum", workArea);
        TestPanel.show(w1);
    }
}
