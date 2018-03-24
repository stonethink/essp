package client.essp.timesheet.dailyreport;

import java.awt.BorderLayout;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJTable;

import com.wits.util.Parameter;

public class VwDailyReportBase extends VWGeneralWorkArea {
	
	private VwDailyReportLeft vwDailyReportLeft = new VwDailyReportLeft();
	private VwSumList vwSumList = new VwSumList();
	
	public VwDailyReportBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
        addUICEvent();
    }
	
	private void jbInit() {
		this.add(vwDailyReportLeft, BorderLayout.CENTER);
        this.add(vwSumList, BorderLayout.SOUTH);
	}
	
	private void addUICEvent() {
		vwDailyReportLeft.addColumnWithListener(vwSumList.getColumnWithListener());
		vwDailyReportLeft.addWorkTimeListener(vwSumList.getWorkTimeListener());
	}
	public void setParameter(Parameter param) {
		super.setParameter(param);
		vwDailyReportLeft.setParameter(param);
	}
	
	 /**
     * Ë¢ÐÂUI
     */
    public void resetUI() {
        vwDailyReportLeft.refreshWorkArea();
        fireWorkTimeChange();
    }
    
    public VWJTable getTable() {
    	return vwDailyReportLeft.getTable();
    }
    public boolean saveDailyReport() {
    	return vwDailyReportLeft.saveDailyReport();
    }
    public HourChangeListener getHourChangeListener() {
    	return vwDailyReportLeft;
    }
    public void fireWorkTimeChange() {
    	vwDailyReportLeft.fireWorkTimeChange();
    }
    public void delDailyReport() {
    	vwDailyReportLeft.processDel();
    	fireWorkTimeChange();
    }

}
