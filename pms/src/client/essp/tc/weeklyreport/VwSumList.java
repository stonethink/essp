package client.essp.tc.weeklyreport;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.RowHeaderRenderer;
import client.essp.tc.common.RowRenderer;
import client.essp.tc.common.RowRendererTwoNumber;
import client.essp.tc.common.TcLineBorder;
import com.wits.util.TestPanel;

public class VwSumList extends VWGeneralWorkArea {
    TableSum table = null;
    RowHeaderRenderer rowHeaderRenderer = new RowHeaderRenderer();
    RowRendererTwoNumber rowRenderer = new RowRendererTwoNumber(RowRenderer.color);

    public VwSumList() {
        try {
            TableModel model = new VMTableSum();
            table = new TableSum(model);

            this.add(table, null);

            initUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置表的外观
    private void initUI() {
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

        //其它列的描绘器
        for (int i = VMTableSum.SATURDAY_COLUMN_INDEX; i < table.getModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setCellRenderer(this.rowRenderer);
        }

        this.setMinimumSize(new Dimension( -1, this.getMinmizeTableHeight()));
        this.setPreferredSize(new Dimension( -1, this.getPreferredTableHeight()));
        this.setMaximumSize(new Dimension( -1, this.getMaxmizeTableHeight()));
    }

    public JTable getTable() {
        return this.table;
    }

    //表的高度
    public int getPreferredTableHeight() {
        return TableSum.ROW_HEIGHT * 3 + 5;
    }

    //表的最小高度
    public int getMinmizeTableHeight() {
        return getPreferredTableHeight();
    }

    //表的最da高度
    public int getMaxmizeTableHeight() {
        return getPreferredTableHeight();
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwSumList();

        w1.addTab("Sum", workArea);
        TestPanel.show(w1);
    }
}
