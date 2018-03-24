package client.essp.timesheet.dailyreport;

import java.util.*;

import javax.swing.table.AbstractTableModel;

import client.essp.common.view.ui.MessageUtil;
import client.essp.timesheet.weeklyreport.common.ResetRenderListener;

public class VMTableDeilySum extends AbstractTableModel implements WorkTimeListener{
	
	private int ColumnCount = 2;
	final static int ALL_ROW_CCOUNT = 1;
	final static int HEAD_COLUMN_INDEX = 0;
	final static int WORK_TIME_ROW_INDEX = 0;
    public final static int COLUMN_OFFSET = 4;
    public final static int BEGIN_HOUR_COLUMN_INDEX = 1;
    private List<ResetRenderListener> resetRenderListeners = new ArrayList<ResetRenderListener> ();
    Map<Integer, Double> hours = new HashMap<Integer,Double>();
	public int getColumnCount() {
		return ColumnCount;
	}

	public int getRowCount() {
		return ALL_ROW_CCOUNT;
	}
	public Double getWorkTime() {
		return hours.get(1);
	}
	
	public Object getValueAt(int row, int col) {
        if (row == WORK_TIME_ROW_INDEX) {
            if( col == HEAD_COLUMN_INDEX ){
                return MessageUtil.getMessage("rsid.timesheet.timetotal");
            }else {
                return getWorkTime();
            }
       }
		return null;
	}
	 public void addResetRenderListener(ResetRenderListener l) {
	        resetRenderListeners.add(l);
	    }

	public void workTimeChanged(Double totalHours) {
		hours.put(1, totalHours);
		this.fireTableDataChanged();
		
	}
}
