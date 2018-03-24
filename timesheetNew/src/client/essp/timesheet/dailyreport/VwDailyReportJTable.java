package client.essp.timesheet.dailyreport;

import java.util.ArrayList;
import java.util.List;

import client.essp.timesheet.weeklyreport.common.ColumnWithListener;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJTable;

public class VwDailyReportJTable extends VWJTable {
	
	private List<ColumnWithListener> columnWithListeners = new ArrayList();
	
	public VwDailyReportJTable(VMTable2 model) {
        super(model);
    }
	
	  public void doLayout() {
	        super.doLayout();
	        fireColumnWithChanged();
	  }
	  /**
	     * 增加列宽监听器
	     * @param l ColumnWithListener
	     */
	    public void addColumnWithListener(ColumnWithListener l) {
	        columnWithListeners.add(l);
	    }

	  /**
	     * 触发列宽改变事件
	     */
	    private void fireColumnWithChanged() {
	        if(getModel().getColumnCount() < 1) {
	            return;
	        }
	        //Status 不监听
	        int[] widths = new int[columnModel.getColumnCount()];
	        for (int i = 0; i < widths.length; i++) {
	            widths[i] = columnModel.getColumn(i).getWidth();
	        }

	        for(int i = 0; i < columnWithListeners.size(); i++) {
	            columnWithListeners.get(i).columnWidthChanged(widths);
	        }
	    }
}
