package client.essp.timesheet.dailyreport;


import java.awt.Component;
import java.awt.Point;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.util.*;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import c2s.dto.*;
import c2s.essp.timesheet.dailyreport.DtoDrActivity;
import c2s.essp.timesheet.dailyreport.DtoDrStep;
import c2s.essp.timesheet.workscope.DtoWorkScopeDrag;
import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.weeklyreport.common.ColumnWithListener;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;

public class VwDailyReportLeft extends VWTableWorkArea implements HourChangeListener{
	
//	private JButton delBtn;
	private static final String actionId_saveDailyReport = "FTSSaveDailyReport";
	private static final String actionId_listActivityDB = "FTSListActivityDB";
	private static final String actionId_listByActivity = "FTSListByActivity";
	private static final String actionId_delByActivity = "FTSDelByActivity";
	private List list;
	private Date workDay;
	private VwDailyReportJTable myTable;
	VMTableActivity myModel;
	private List<WorkTimeListener> workTimeListeners = new ArrayList<WorkTimeListener>();
	
	public VwDailyReportLeft() {
		try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
	}
	
	private void jbInit() throws Exception {
		Object[][] configs = new Object[][] {
				{ "rsid.timesheet.projName", "accountName", VMColumnConfig.UNEDITABLE,
					new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.activityName", "activityName", VMColumnConfig.UNEDITABLE,
						new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.jobCode", "codeValueName", VMColumnConfig.UNEDITABLE,
						new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.workTime", "workTime",
						VMColumnConfig.UNEDITABLE, new VWJReal(), Boolean.FALSE } };
		//table
		myModel = new VMTableActivity(configs);
		model = myModel;
        model.setDtoType(DtoDrActivity.class);
        myTable = new VwDailyReportJTable(model);
		table = myTable;
        //不允许移动列
        table.getTableHeader().setReorderingAllowed(false);
        this.add(table.getScrollPane(), null);
        
        //设置初始列宽
        JTableHeader header = table.getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(150);
        tcModel.getColumn(1).setPreferredWidth(100);
        tcModel.getColumn(2).setPreferredWidth(100);
        tcModel.getColumn(3).setPreferredWidth(40);
		
	}
	private void addUICEvent() {
		new VwDailyReportDropTarget(table);
		new VwDailyReportDropTarget(this);
	}
	public void processDel() {
		int option = comMSG.dispConfirmDialog("error.client.VwDailyReport.confirmDel");
		if (option != Constant.OK) {
			return;
		}
		DtoDrActivity dto = (DtoDrActivity) this.getTable().getSelectedData();
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_delByActivity);
		inputInfo.setInputObj(DtoDrActivity.DTO_DAY, workDay);
		inputInfo.setInputObj(DtoDrActivity.DTO, dto);
		ReturnInfo returnInfo = this.accessData(inputInfo);
		if(returnInfo.isError() == false) {
			this.getTable().deleteRow();
		}
	}

	public void setParameter(Parameter param) {
		super.setParameter(param);
		workDay = (Date) param.get(DtoDrActivity.DTO_DAY);
	}

	protected void resetUI() {
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_listActivityDB);
		inputInfo.setInputObj(DtoDrActivity.DTO_DAY, workDay);
		ReturnInfo returnInfo = this.accessData(inputInfo);
		if(returnInfo.isError() == false) {
			list = (List) returnInfo.getReturnObj(DtoDrActivity.DTO_ACTIVITY_LIST);
			this.getTable().setRows(list);
			if(list != null && list.size() > 0) {
				this.getTable().setSelectRow(0);
			}
		}
	}
	public boolean saveDailyReport() {
		int rows = this.getTable().getRowCount();
		DtoDrActivity dto = null;
		List list = new ArrayList();
		List<DtoDrStep> stepList = null;
		for(int i = 0;i<rows;i++) {
			dto = (DtoDrActivity)this.getModel().getRow(i);
//			if(dto.getCodeValueRid() == null) {
//				comMSG.dispErrorDialog("rsid.timesheet.checkJobCode");
//				return true;
//			}
			stepList = dto.getStepList();
			if(stepList !=null && stepList.size() > 0) {
				for(DtoDrStep step : stepList) {
					if((step.getWorkTime() != null && step.getWorkTime() != 0) 
					&& (step.getItem() == null || "".equals(step.getItem().trim()))) {
						comMSG.dispErrorDialog("rsid.timesheet.checkItem");
						return true;
					}
					if((step.getWorkTime() != null && step.getWorkTime() != 0)
					&& dto.getCodeValueRid() == null) {
						comMSG.dispErrorDialog("rsid.timesheet.checkJobCode");
						return true;
					}
				}
			}
			list.add(dto);
		}
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_saveDailyReport);
		inputInfo.setInputObj(DtoDrActivity.DTO_DAY, workDay);
		inputInfo.setInputObj(DtoDrActivity.DTO_ACTIVITY_LIST, list);
		ReturnInfo returnInfo = this.accessData(inputInfo);
		if(returnInfo.isError() == false) {
			return false;
		} else {
			return true;
		}
	}

	private class VwDailyReportDropTarget extends VWDropTarget {
		public VwDailyReportDropTarget(Component c) {
			super(c);
            create();
		}
		/**
         * 获取要释放行的数据
         * @param event DropTargetDragEvent
         * @return DtoTimeSheetDetail
         */
        private DtoDrActivity getRowData(Point p) {
            int row = table.rowAtPoint(p);
            if(row < 0 || row >= myModel.getRowCount()) {
                return null;
            }
            return (DtoDrActivity)myModel.getRow(row);
        }
		protected void acceptData(DropTargetDropEvent event, Object data) {
			DtoWorkScopeDrag drop = (DtoWorkScopeDrag) data;
			if(drop.getIsLeaveType()) {
				return;
			}
			DtoDrActivity dtoDrActivity = getRowData(event.getLocation());
			int rowNum =  table.rowAtPoint(event.getLocation());
			if(drop.isActivity()) {
				int rows = table.getRowCount();
		        for (int row = 0; row<rows; row++) {
		        	dtoDrActivity = (DtoDrActivity) myModel.getRow(row);
		        	if(!dtoDrActivity.isActivity()) {
		        		continue;
		        	}
		        	if(dtoDrActivity.getActivityId().equals(drop.getActivityId())){
		        		table.setSelectRow(row);
		        		return;
		        	}
		        }
				rowNum = addRow(drop);
			} else if(dtoDrActivity == null && !drop.isActivity()) {
				int rows = table.getRowCount();
		        for (int row = 0; row<rows; row++) {
		        	dtoDrActivity = (DtoDrActivity) myModel.getRow(row);
		        	if(dtoDrActivity.isActivity()) {
		        		continue;
		        	}
		        	if(dtoDrActivity.getAccountName().equals(drop.getAccountName()) 
		        			&& dtoDrActivity.getCodeValueRid().equals(drop.getCodeValueRid())){
		        		table.setSelectRow(row);
		        		return;
		        	}
		        }
				rowNum = addRowCode(drop);
			} else if(dtoDrActivity != null && !drop.isActivity()) {
				
				if(dtoDrActivity.isActivity() && dtoDrActivity.getAccountName().equals(drop.getAccountName())) {
					dtoDrActivity.setCodeValueName(drop.getCodeValueName());
					dtoDrActivity.setCodeValueRid(drop.getCodeValueRid());
				} else {
					int rows = table.getRowCount();
			        for (int row = 0; row<rows; row++) {
			        	dtoDrActivity = (DtoDrActivity) myModel.getRow(row);
			        	if(dtoDrActivity.isActivity()) {
			        		continue;
			        	}
			        	if(dtoDrActivity.getAccountName().equals(drop.getAccountName()) 
			        			&& dtoDrActivity.getCodeValueRid().equals(drop.getCodeValueRid())){
			        		table.setSelectRow(row);
			        		return;
			        	}
			        }
					rowNum = addRowCode(drop);
				}
			}
			myModel.fireTableDataChanged();
            table.setSelectRow(rowNum);
		}
		private int addRowCode(DtoWorkScopeDrag drop) {
			DtoDrActivity dtoDrActivity = new DtoDrActivity();
			DtoUtil.copyBeanToBean(dtoDrActivity, drop);
			List stepList = new ArrayList();
			DtoDrStep dtoDrStep = new DtoDrStep();
			dtoDrStep.setCodeValueRid(dtoDrActivity.getCodeValueRid());
			dtoDrStep.setIsDBData(false);
			dtoDrStep.setIsFinish(false);
			dtoDrStep.setIsEditable(true);
			dtoDrStep.setIsAssigned(false);
			dtoDrStep.setProjName(dtoDrActivity.getAccountName());
			dtoDrStep.setAccountRid(drop.getAccountRid());
			stepList.add(dtoDrStep);
			dtoDrActivity.setStepList(stepList);
			int row = table.getRowCount();
			myModel.addRow(row, dtoDrActivity);
			return row;
		}
		private int addRow(DtoWorkScopeDrag drop) {
			DtoDrActivity dtoDrActivity = new DtoDrActivity();
			DtoUtil.copyBeanToBean(dtoDrActivity, drop);
			InputInfo inputInfo = new InputInfo();
			inputInfo.setActionId(actionId_listByActivity);
			inputInfo.setInputObj(DtoDrActivity.DTO_ACTIVITYID, dtoDrActivity.getActivityId());
			inputInfo.setInputObj(DtoDrActivity.DTO_ACCOUNTRID, drop.getAccountRid());
			ReturnInfo returnInfo = accessData(inputInfo);
			List<DtoDrStep> list = null;
			if(returnInfo.isError() == false) {
				list =(List) returnInfo.getReturnObj(DtoDrStep.DTO_RESULT);
				if(list == null || list.size() == 0) { 
					list = new ArrayList<DtoDrStep>();
					DtoDrStep dtoDrStep = new DtoDrStep();
					dtoDrStep.setCodeValueRid(dtoDrActivity.getCodeValueRid());
					dtoDrStep.setTaskId(dtoDrActivity.getActivityId());
					dtoDrStep.setIsDBData(false);
					dtoDrStep.setIsFinish(false);
					dtoDrStep.setIsEditable(true);
					dtoDrStep.setIsAssigned(false);
					dtoDrStep.setProjName(dtoDrActivity.getAccountName());
					dtoDrStep.setAccountRid(drop.getAccountRid());
					list.add(dtoDrStep);
				}
				dtoDrActivity.setStepList(list);
			}
			int row = table.getRowCount();
			myModel.addRow(row, dtoDrActivity);
			return row;
		}
		
		/**
	     * 可以在此确定控件的那些地方能放，那些不能放。
	     * @param event DropTargetDragEvent
	     */
	    public void dragOver(DropTargetDragEvent event) {
	        Object dragObj = this.getDropData(event.getTransferable());
	        if(dragObj instanceof DtoWorkScopeDrag) {
	        	if(((DtoWorkScopeDrag)dragObj).getIsLeaveType()) {
	        		event.rejectDrag();
	        	} else {
	        		super.dragOver(event);
	        	}
	        } else {
	        	event.rejectDrag();
	        }
	    }

		protected Class getAcceptClass() {
			return DtoWorkScopeDrag.class;
		}
	}
	public void addColumnWithListener(ColumnWithListener l) {
        myTable.addColumnWithListener(l);
    }
	public void addWorkTimeListener(WorkTimeListener l) {
		workTimeListeners.add(l);
    }
	public void fireWorkTimeChange() {
		DtoDrActivity dto = null;
		int rows = this.getTable().getRowCount();
		Double totalHours = new Double(0);
		for(int i = 0;i<rows;i++) {
			dto = (DtoDrActivity) this.getModel().getRow(i);
			totalHours += nvl(dto.getWorkTime());
		}
		Iterator<WorkTimeListener> iter = workTimeListeners.iterator();
		while(iter.hasNext()) {
			WorkTimeListener l = iter.next();
			l.workTimeChanged(totalHours);
		}
	}
	private Double nvl(Double d) {
		if(d == null) {
			return new Double(0);
		}
		return d;
	}
	public void hoursChanged() {
		this.repaint();
		fireWorkTimeChange();
	}
}
