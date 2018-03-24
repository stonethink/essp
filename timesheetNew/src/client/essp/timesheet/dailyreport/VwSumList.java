package client.essp.timesheet.dailyreport;

import java.awt.Dimension;

import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import client.essp.common.view.VWGeneralWorkArea;
import client.essp.timesheet.weeklyreport.common.*;
import client.essp.timesheet.weeklyreport.summary.*;

public class VwSumList extends VWGeneralWorkArea implements ResetRenderListener {
	
	private TableDailySum table = null;
	private TableCellRenderer rowRender = RowRenderer.defaultRowRenderer;
	private final static RowHeaderRenderer rowHeaderRenderer = new RowHeaderRenderer();
	VMTableDeilySum myModel = new VMTableDeilySum();
	
	 public VwSumList() {
	        try {
	            table = new TableDailySum(myModel);
	            this.add(table, null);
	            initUI();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        myModel.addResetRenderListener(this);
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

	        resetRowRender(rowHeaderRenderer, rowRender);

	        this.setMinimumSize(new Dimension( -1, this.getMinmizeTableHeight()));
	        this.setPreferredSize(new Dimension( -1, this.getPreferredTableHeight()));
	        this.setMaximumSize(new Dimension( -1, this.getMaxmizeTableHeight()));
	    }
	  //表的高度
	    public int getPreferredTableHeight() {
	        return TableDailySum.ROW_HEIGHT * VMTableDeilySum.ALL_ROW_CCOUNT + 5;
	    }

	    //表的最小高度
	    public int getMinmizeTableHeight() {
	        return getPreferredTableHeight();
	    }

	    //表的最da高度
	    public int getMaxmizeTableHeight() {
	        return getPreferredTableHeight();
	    }
	 
	public void resetRender(VWJTwoNumReal comp) {
		if(comp != null) {
            comp.setCanNegative(true);
            comp.setCanSecondNumNegative(true);
            rowRender = new RowRenderer(comp);
        }
        resetRowRender(rowHeaderRenderer, rowRender);

	}
	 public WorkTimeListener getWorkTimeListener() {
	        return this.myModel;
	    }
	public ColumnWithListener getColumnWithListener() {
        return this.table;
    }
	private void resetRowRender(TableCellRenderer headerRender, TableCellRenderer rowRender) {
        //第一列的描绘器
        TableColumn rowHeader = table.getColumnModel().getColumn(0);
        rowHeader.setCellRenderer(headerRender);

        //其它列的描绘器
        for (int i = VMTableSum.BEGIN_HOUR_COLUMN_INDEX;
                     i < table.getModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setCellRenderer(rowRender);
        }

    }
	
}
