package client.essp.timesheet.dailyreport;

import java.util.*;

import c2s.essp.timesheet.dailyreport.DtoDrActivity;
import c2s.essp.timesheet.dailyreport.DtoDrStep;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;

public class VMTableDailyReport extends VMTable2 {
	
	private static final String COLUMNNAME_ITEM = "item";
	private static final String COLUMNNAME_ISFINISH = "isFinish";
	private DtoDrActivity dto;
	
	public DtoDrActivity getDto() {
		return dto;
	}

	public void setDto(DtoDrActivity dto) {
		this.dto = dto;
	}

	public VMTableDailyReport(Object[][] config) {
		super(config);
	}
	private List<HourChangeListener> hourChangeListener =
        new ArrayList<HourChangeListener>();
	
	
	/**
     * 根据是否为Activity判断是否可以编辑Item
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	DtoDrStep dtoDrStep = (DtoDrStep) this.getRow(rowIndex);
    	String columnName = ((VMColumnConfig) getColumnConfigs().get(columnIndex)).getDataName();
    	if(dtoDrStep.getIsEditable() 
    			&& COLUMNNAME_ITEM.equals(columnName)) {
    		return true;
    	}
    	if(!dtoDrStep.getIsAssigned() && COLUMNNAME_ISFINISH.equals(columnName)) {
    		return false;
    	}
    	return super.isCellEditable(rowIndex, columnIndex);
    }
    
    /**
     * 增加工时变化监听器
     * @param l WorkHourListener
     */
    public void addHourChangeListener(HourChangeListener l) {
    	hourChangeListener.add(l);
    }
    
    public void fireHourChange() {
    	Iterator<HourChangeListener> iter = hourChangeListener.iterator();
        while (iter.hasNext()) {
        	HourChangeListener l = iter.next();
            l.hoursChanged();
        
        }
    }
    /**
     * 获取某列工时之和
     * @param columnIndex int
     * @return Double
     */
    private Double getColumnSumHours() {
        Double sumHours = new Double(0);
        DtoDrStep step = null;
        for (int i = 0; i < this.getRowCount(); i++) {
        		Double hour = null;
        		step = (DtoDrStep)this.getRow(i);
                hour = step.getWorkTime();
                if(hour == null) continue;
                sumHours += hour;
        }
        return sumHours;
    }
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	super.setValueAt(aValue, rowIndex, columnIndex);
    	if (aValue != null && columnIndex == 1) {
    		dto.setWorkTime(getColumnSumHours());
    		fireHourChange();
    	}
    }

}
