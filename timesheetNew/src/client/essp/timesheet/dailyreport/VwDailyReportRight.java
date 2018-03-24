package client.essp.timesheet.dailyreport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.dailyreport.DtoDrActivity;
import c2s.essp.timesheet.dailyreport.DtoDrStep;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;

public class VwDailyReportRight extends VWTableWorkArea {
	
	private static final String actionId_ShwoAll = "FTSShowAllStep";
	private static final String actionId_DeleteInDB = "FTSDeleteInDB";
	
	private JButton addBtn;
	private JButton delBtn;
	private JButton allBtn;
	private DtoDrActivity dto;
	private VMTableDailyReport myModel;
	private VwDeilyReportRightTable myTable;
	public VwDailyReportRight() {
		try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
	}
	
	private void jbInit() throws Exception {
		Object[][] configs = new Object[][] {
				{ "rsid.timesheet.workItem", "item", VMColumnConfig.UNEDITABLE,
					new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.workTime", "workTime", VMColumnConfig.EDITABLE,
							new VWJReal(), Boolean.FALSE },
				{ "rsid.timesheet.isFinish", "isFinish", VMColumnConfig.EDITABLE,
								new VWJCheckBox(), Boolean.FALSE }, 
				{ "rsid.timesheet.procDescr", "description", VMColumnConfig.EDITABLE,
									new VWJText(), Boolean.FALSE }};
		//table
		myModel = new VMTableDailyReport(configs);
        model = myModel;
        model.setDtoType(DtoDrStep.class);
        myTable = new VwDeilyReportRightTable(myModel);
        table = myTable;
        TableColumnWidthSetter.set(table);
        //不允许移动列
        table.getTableHeader().setReorderingAllowed(false);
        this.add(table.getScrollPane(), null);
        
        //设置初始列宽
        JTableHeader header = table.getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(150);
        tcModel.getColumn(1).setPreferredWidth(50);
        tcModel.getColumn(2).setPreferredWidth(50);
        tcModel.getColumn(3).setPreferredWidth(150);

	}
	/**
     * 强制正在编辑的Cell保存
     *
     */
    public void stopCellEditing() {
    	int column = this.getTable().getSelectedColumn();
    	int row = this.getTable().getSelectedRow();
    	if(column < 0 || row < 0) {
    		return;
    	}
    	TableCellEditor editor = this.getTable().getCellEditor(row, column);
    	if(editor != null && this.getTable().isEditing()) {
    		try {
    			editor.stopCellEditing();
    		} catch (Exception e){
    			//此editor没有处于激活状态
    		}
    	}
    	this.getTable().setSelectRow(row);
    }
	private void addUICEvent() {
		addBtn = this.getButtonPanel().addAddButton(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processAdd();
			}
		});
		addBtn.setToolTipText("rsid.common.add");
		addBtn.setEnabled(false);
		
		delBtn = this.getButtonPanel().addDelButton(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processDel();
			}
		});
		delBtn.setToolTipText("rsid.common.delete");
		delBtn.setEnabled(false);
		
		
		allBtn = this.getButtonPanel().addButton("expand_all.gif");
		allBtn.setToolTipText("rsid.common.showAll");
		allBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processShowAll();
			}
		});
		
		this.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					setBtnStatus();
				}
			}
		});
	}
	private void setBtnStatus() {
		DtoDrStep dtoDrStep = (DtoDrStep) this.getTable().getSelectedData();
		if(dtoDrStep != null) {
			delBtn.setEnabled(true);
		} else {
			delBtn.setEnabled(false);
		}
	}
    private void processAdd() {
    	if(dto == null){
    		return;
    	}
    	DtoDrStep dtoDrStep = new DtoDrStep();
		dtoDrStep.setCodeValueRid(dto.getCodeValueRid());
		dtoDrStep.setIsDBData(false);
		dtoDrStep.setIsFinish(false);
		dtoDrStep.setIsEditable(true);
		dtoDrStep.setIsAssigned(false);
		dtoDrStep.setProjName(dto.getAccountName());
		dtoDrStep.setAccountRid(dto.getAccountRid());
		dtoDrStep.setTaskId(dto.getActivityId());
		dtoDrStep.setTaskName(dto.getActivityName());
		dto.getStepList().add(dtoDrStep);
		this.getTable().setRows(dto.getStepList());
    }
    private void processDel() {
    	int option = comMSG.dispConfirmDialog("error.client.VwRmMaint.confirmDel");
		if (option != Constant.OK) {
			return;
		}
		stopCellEditing();
		DtoDrStep dtoDrStep = (DtoDrStep) this.getTable().getSelectedData();
    	InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_DeleteInDB);
		inputInfo.setInputObj(DtoDrStep.DTO, dtoDrStep);
		ReturnInfo returnInfo = this.accessData(inputInfo);
		if(returnInfo.isError() == false) {
			dto.getStepList().remove(dtoDrStep);
			this.getTable().deleteRow();
			dto.setWorkTime(dto.getWorkTime() - dtoDrStep.getWorkTime());
			myModel.fireHourChange();
		}
    }
	public void processShowAll() {
		if(dto == null || !dto.isActivity()) {
			return;
		}
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_ShwoAll);
		inputInfo.setInputObj(DtoDrActivity.DTO, dto);
		ReturnInfo returnInfo = this.accessData(inputInfo);
		if(returnInfo.isError() == false) {
			dto.setStepList((List) returnInfo.getReturnObj(DtoDrStep.DTO_RESULT));
			this.getTable().setRows(dto.getStepList());
			if(dto.getStepList() != null && dto.getStepList().size() > 0){
				this.getTable().setSelectRow(0);
			}
		}
	}
	public void setParameter(Parameter param) {
		super.setParameter(param);
		dto = (DtoDrActivity) param.get(DtoDrActivity.DTO);
		myModel.setDto(dto);
	}

	protected void resetUI() {
		if (dto == null) {
			addBtn.setEnabled(false);
			delBtn.setEnabled(false);
			allBtn.setEnabled(false);
			this.getTable().setRows(new ArrayList());
			return;
		} else {
			addBtn.setEnabled(true);
			delBtn.setEnabled(true);
			allBtn.setEnabled(true);
			this.getTable().setRows(dto.getStepList());
			if (dto.getStepList() != null && dto.getStepList().size() > 0) {
				this.getTable().setSelectRow(0);
			}
		}
		myTable.resetRender();
	}
	public void addHourChangeListener(HourChangeListener l) {
		myModel.addHourChangeListener(l);
	}
}
