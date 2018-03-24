package client.essp.tc.pmmanage.onWeek;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.RowHeaderRenderer;
import client.essp.tc.common.RowRenderer;
import client.essp.tc.common.TcLineBorder;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJTableEditor;
import com.wits.util.TestPanel;
import client.framework.model.VMTable2;
import javax.swing.table.AbstractTableModel;

public class VwTcListSumOnWeek extends VWGeneralWorkArea {
    public JTable table = null;
    RowHeaderRenderer rowHeaderRenderer = new RowHeaderRenderer();
    RowRenderer rowRendererReal = new RowRenderer(RowRenderer.real);
    RowRenderer rowRendererText = new RowRenderer(RowRenderer.text);

    public VwTcListSumOnWeek() {
        try {
            initUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置表的外观
    protected void initUI() {
        TableModel model = new VMTableTcSumOnWeek();
        table = new TableTcSumOnWeek(model);
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

        //confirm label
        TableColumn rowHeader2 = table.getColumnModel().getColumn(VMTableTcSumOnWeek.LBL_CONFIRM_COLUMN_INDEX);
        rowHeader2.setCellRenderer(this.rowHeaderRenderer);

        //confirm
        rowRendererText.setSelectedBackgroundColor(RowRenderer.noColor);
        TableColumn columnConfirm = table.getColumnModel().getColumn(VMTableTcSumOnWeek.CONFIRM_COLUMN_INDEX);
        columnConfirm.setCellRenderer(this.rowRendererText);

        setEditor();

        this.setMinimumSize(new Dimension( -1, this.getMinmizeTableHeight()));
        this.setPreferredSize(new Dimension( -1, this.getPreferredTableHeight()));
        this.setMaximumSize(new Dimension( -1, this.getMaxmizeTableHeight()));
    }

    private void setEditor() {
        VWJComboBox cmbStatus = new VWJComboBox();
        VMComboBox vmStatus = VMComboBox.
                              toVMComboBox(new String[] {DtoWeeklyReport.STATUS_NONE
                                           , DtoWeeklyReport.STATUS_LOCK
                                           , DtoWeeklyReport.STATUS_CONFIRMED
                                           , DtoWeeklyReport.STATUS_REJECTED});
        cmbStatus.setModel(vmStatus);
        VWJTableEditor statusEditor = new VWJTableEditor(cmbStatus);
        TableColumn columnConfirm = table.getColumnModel().getColumn(VMTableTcSumOnWeek.CONFIRM_COLUMN_INDEX);
        columnConfirm.setCellEditor(statusEditor);
    }

    public JTable getTable() {
        return this.table;
    }

    //表的高度
    public int getPreferredTableHeight() {
        return TableTcSumOnWeek.ROW_HEIGHT * 1 + 0;
    }

    //表的最小高度
    public int getMinmizeTableHeight() {
        return getPreferredTableHeight();
    }

    //表的最da高度
    public int getMaxmizeTableHeight() {
        return getPreferredTableHeight();
    }

    public AbstractTableModel getModel(){
        return (AbstractTableModel)this.table.getModel();
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwTcListSumOnWeek();

        w1.addTab("Sum", workArea);
        TestPanel.show(w1);
    }
}
