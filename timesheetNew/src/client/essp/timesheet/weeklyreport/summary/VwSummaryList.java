package client.essp.timesheet.weeklyreport.summary;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.timesheet.weeklyreport.common.*;
import com.wits.util.TestPanel;

/**
 * <p>Title: VwSummaryList</p>
 *
 * <p>Description: ��ʱ�����ܽ���,����Actual Hours, Standar Hours, Variant/p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwSummaryList extends VWGeneralWorkArea implements ResetRenderListener {
    private TableSum table = null;
    private final static RowHeaderRenderer rowHeaderRenderer = new RowHeaderRenderer();
    private TableCellRenderer rowRender = RowRenderer.defaultRowRenderer;
    VMTableSum myModel = new VMTableSum();
    public VwSummaryList() {
        try {
            table = new TableSum(myModel);
            this.add(table, null);
            initUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        myModel.addResetRenderListener(this);
    }

    //���ñ�����
    private void initUI() {
        //���ñ��ı߿�
        TcLineBorder tableBorder = new TcLineBorder(UIManager.getColor(
                "Table.gridColor"));
        tableBorder.setShowBottom(false);
        tableBorder.setShowLeft(false);
        tableBorder.setShowRight(false);
        table.setBorder(tableBorder);

        resetRowRender(rowHeaderRenderer, rowRender);

        this.setMinimumSize(new Dimension( -1, this.getMinmizeTableHeight()));
        this.setPreferredSize(new Dimension( -1, this.getPreferredTableHeight()));
        this.setMaximumSize(new Dimension( -1, this.getMaxmizeTableHeight()));
    }

    private void resetRowRender(TableCellRenderer headerRender, TableCellRenderer rowRender) {
        //��һ�е������
        TableColumn rowHeader = table.getColumnModel().getColumn(0);
        rowHeader.setCellRenderer(headerRender);

        //�����е������
        for (int i = VMTableSum.BEGIN_HOUR_COLUMN_INDEX;
                     i < table.getModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setCellRenderer(rowRender);
        }

    }

    public ColumnWithListener getColumnWithListener() {
        return this.table;
    }

    public WorkHourListener getWorkHourListener() {
        return this.myModel;
    }

    public ColumnNumListener getColumnNumListener() {
        return this.myModel;
    }

    public StandarHoursListener getStandarHoursListener() {
        return this.myModel;
    }

    public SubmitTimeSheetListener getSubmitTimeSheetListener() {
        return this.myModel;
    }

    public JTable getTable() {
        return this.table;
    }

    //��ĸ߶�
    public int getPreferredTableHeight() {
        return TableSum.ROW_HEIGHT * VMTableSum.ALL_ROW_CCOUNT + 5;
    }

    //�����С�߶�
    public int getMinmizeTableHeight() {
        return getPreferredTableHeight();
    }

    //�����da�߶�
    public int getMaxmizeTableHeight() {
        return getPreferredTableHeight();
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwSummaryList();

        w1.addTab("Sum", workArea);
        TestPanel.show(w1);
    }

    public void resetRender(VWJTwoNumReal comp) {
        if(comp != null) {
            comp.setCanNegative(true);
            comp.setCanSecondNumNegative(true);
            rowRender = new RowRenderer(comp);
        }
        resetRowRender(rowHeaderRenderer, rowRender);
    }
}
