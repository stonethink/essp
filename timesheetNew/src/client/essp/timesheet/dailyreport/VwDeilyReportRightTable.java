package client.essp.timesheet.dailyreport;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableColumn;

import client.essp.timesheet.weeklyreport.common.ResetRenderListener;
import client.framework.view.vwcomp.*;

public class VwDeilyReportRightTable extends VWJTable {
	
	private VMTableDailyReport myModel;
	private List<ResetRenderListener> resetRenderListeners = new ArrayList();
	
	 public VwDeilyReportRightTable(VMTableDailyReport model) {
	        super(model);
	        myModel = model;
	    }
	    /**
	     * 重设Render
	     */
	    public void resetRender() {
	        int count = this.getColumnModel().getColumnCount();
	        //循环所有列
	        for(int i = 0; i < count; i++) {
	        	TableColumn column = this.getColumnModel().getColumn(i);
	            if(i == 0) {
	            	column.setCellEditor(new VWJDrTableEditor(new VWJText(), myModel));
	            } else if(i == 1) {
	            	column.setCellEditor(new VWJDrTableEditor(new VWJReal(), myModel));
	            } else if(i == 3) {
	            	column.setCellEditor(new VWJDrTableEditor(new VWJText(), myModel));
	            }
	            
	        }
	    }
	
}
