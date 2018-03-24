package client.essp.timesheet.dailyreport;

import java.util.*;

import client.framework.model.VMTable2;

public class VMTableActivity extends VMTable2{
	
	private List<WorkTimeListener> workTimeListeners =
        new ArrayList<WorkTimeListener>();
	
	public VMTableActivity(Object[][] config) {
		super(config);
	}
	public void addWorkTimeListener(WorkTimeListener l) {
		workTimeListeners.add(l);
	}
	Map<Integer, Double> hours = new HashMap<Integer,Double>();
	
    public int getSumColumnIndex() {
        return this.getColumnCount() - 2;
    }


}
