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
	     * �����п������
	     * @param l ColumnWithListener
	     */
	    public void addColumnWithListener(ColumnWithListener l) {
	        columnWithListeners.add(l);
	    }

	  /**
	     * �����п�ı��¼�
	     */
	    private void fireColumnWithChanged() {
	        if(getModel().getColumnCount() < 1) {
	            return;
	        }
	        //Status ������
	        int[] widths = new int[columnModel.getColumnCount()];
	        for (int i = 0; i < widths.length; i++) {
	            widths[i] = columnModel.getColumn(i).getWidth();
	        }

	        for(int i = 0; i < columnWithListeners.size(); i++) {
	            columnWithListeners.get(i).columnWidthChanged(widths);
	        }
	    }
}
