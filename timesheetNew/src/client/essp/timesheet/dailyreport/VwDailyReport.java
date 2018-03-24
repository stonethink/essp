package client.essp.timesheet.dailyreport;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;

import c2s.essp.timesheet.dailyreport.DtoDrActivity;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWTDWorkArea;
import client.essp.timesheet.ActivityChangedListener;
import client.essp.timesheet.calendar.CalendarSelectDateListener;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJPopupEditor;

import com.wits.util.Parameter;
import com.wits.util.comDate;

public class VwDailyReport extends VWTDWorkArea implements CalendarSelectDateListener{
	
	private VwDailyReportBase vwDailyReportBase;
	private VwDailyReportRight vwDailyReportRight;
	private JButton saveBtn;
	private JButton resetBtn;
	private JButton allBtn;
	private VWJLabel lblToday = new VWJLabel();
	private Date workDay = new Date();
	private boolean selectDateChangeLock = false;
	private List<ActivityChangedListener> activityChangedListeners
    								= new ArrayList<ActivityChangedListener>();
	private JButton delBtn;
	public VwDailyReport() {
		super(400);
		try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
	}

	private void jbInit() {
		this.setHorizontalSplit();
		vwDailyReportBase = new VwDailyReportBase();
		vwDailyReportRight = new VwDailyReportRight();
		
		
		this.getTopArea().addTab("rsid.timesheet.tab.activity", vwDailyReportBase);
		this.getDownArea().addTab("rsid.timesheet.step", vwDailyReportRight);
		
	}
	
	private void addUICEvent() {
		this.getButtonPanel().add(lblToday);
		
		allBtn = this.getButtonPanel().addButton("whd_toc2.gif");
		allBtn.setToolTipText("rsid.timesheet.displayAll");
		allBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processAll();
			}
		});
		
		saveBtn = this.getButtonPanel().addSaveButton(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processSave();
			}
		});
		saveBtn.setToolTipText("rsid.common.save");
		saveBtn.setEnabled(false);
		
		resetBtn = this.getButtonPanel().addLoadButton(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processReset();
			}
		});
		resetBtn.setToolTipText("rsid.common.refresh");
		vwDailyReportBase.getTable().getSelectionModel()
							.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processSelectActivity();
				} 
			}
		});
		delBtn = vwDailyReportBase.getButtonPanel().addDelButton(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processDel();
			}
		});
		delBtn.setToolTipText("rsid.common.delete");
		delBtn.setEnabled(false);
		vwDailyReportRight.addHourChangeListener(vwDailyReportBase.getHourChangeListener());
	}
	private void processAll() {
		VwAllDataList allDataList = new VwAllDataList();
		Parameter param = new Parameter();
		param.put(DtoDrActivity.DTO_DAY, workDay);
		allDataList.setParameter(param);
		VWGeneralWorkArea gwa = new VWGeneralWorkArea();
		gwa.addTab("rsid.timesheet.allData", allDataList);
		gwa.setPreferredSize(new Dimension(800, 400));
		//show
        VWJPopupEditor popup = new VWJPopupEditor(this.getParentWindow(),"",
                                                  gwa, allDataList);
        int result = popup.showConfirm();
	}
	private void processDel() {
		vwDailyReportBase.delDailyReport();
	}
	/**
     * 强制正在编辑的Cell保存
     *
     */
    public void stopCellEditing() {
    	int column = vwDailyReportRight.getTable().getSelectedColumn();
    	int row = vwDailyReportRight.getTable().getSelectedRow();
    	if(column < 0 || row < 0) {
    		return;
    	}
    	TableCellEditor editor = vwDailyReportRight.getTable().getCellEditor(row, column);
    	if(editor != null && vwDailyReportRight.getTable().isEditing()) {
    		try {
    			editor.stopCellEditing();
    		} catch (Exception e){
    			//此editor没有处于激活状态
    		}
    	}
    	vwDailyReportRight.getTable().setSelectRow(row);
    }
	private void processSelectActivity() {
		stopCellEditing();
		DtoDrActivity dto = (DtoDrActivity) vwDailyReportBase.getTable().getSelectedData();
		Parameter param = new Parameter();
		param.put(DtoDrActivity.DTO, dto);
		vwDailyReportRight.setParameter(param);
		vwDailyReportRight.refreshWorkArea();
		if(dto == null) {
			saveBtn.setEnabled(false);
			delBtn.setEnabled(false);
			return;
		}
		saveBtn.setEnabled(true);
		delBtn.setEnabled(true);
		fireactivityChanged(dto.getActivityId());
	}
	public Long getSelectActivityId() {
		DtoDrActivity dto = (DtoDrActivity) vwDailyReportBase.getTable().getSelectedData();
		if(dto == null) {
			return null;
		} else {
			return dto.getActivityId();
		}
	}
	private void processReset() {
		Parameter param = new Parameter();
		param.put(DtoDrActivity.DTO_DAY, workDay);
		vwDailyReportBase.setParameter(param);
		vwDailyReportBase.resetUI();
		processSelectActivity();
	}
	private void processSave() {
		stopCellEditing();
		Parameter param = new Parameter();
		param.put(DtoDrActivity.DTO_DAY, workDay);
		boolean isError = vwDailyReportBase.saveDailyReport();
		if(!isError) {
			processReset();
			comMSG.dispMessageDialog("rsid.common.saveComplete");
		}
	}
	
	public void setParameter(Parameter param) {
		super.setParameter(param);
		param.put(DtoDrActivity.DTO_DAY, workDay);
		vwDailyReportBase.setParameter(param);
		vwDailyReportRight.setParameter(param);
	}
	
	public void refreshWorkArea() {
		lblToday.setText(comDate.dateToString(workDay));
		vwDailyReportBase.refreshWorkArea();
		vwDailyReportRight.refreshWorkArea();
		vwDailyReportBase.fireWorkTimeChange();
	}

	public void selectDateChanged(Date date) {
		if(!selectDateChangeLock) {
            selectDateChangeLock = true;
            workDay = date;
            lblToday.setText(comDate.dateToString(workDay));
            processReset();
            vwDailyReportBase.fireWorkTimeChange();
            selectDateChangeLock = false;
        }
	}
	public void addActivityChangedListener(ActivityChangedListener l) {
		activityChangedListeners.add(l);
	}
	private void fireactivityChanged(Long activityRid) {
        for(ActivityChangedListener l : activityChangedListeners) {
            l.processActivityChanged(activityRid);
        }
    }
}
