package client.essp.timesheet.period;

import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;
import java.util.Date;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.ui.MessageUtil;
import client.framework.common.Global;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJDatePanel;
import client.framework.view.vwcomp.VWJLabel;

public class VwTsDatesAdd extends VWGeneralWorkArea {
	
	private VWJLabel lblStartDate = new VWJLabel();
	private VWJLabel lblEndDate = new VWJLabel();
	private VWJDatePanel inputStartDate = new VWJDatePanel();
	private VWJDatePanel inputEndDate = new VWJDatePanel();
	private VWJLabel lblStartDay = new VWJLabel();
	private VWJLabel lblEndDay = new VWJLabel();
	private static final String actionId_GetLastTsDate = "FTSGetLastTsDate";
	private static final String actionId_AddTsDate = "FTSAddTsDate";
	public VwTsDatesAdd() {
		jbInit();
		addUICEvent();
	}
	private void jbInit() {
		this.setLayout(null);
		
		lblStartDate.setText("rsid.timesheet.begin");
		lblStartDate.setBounds(new Rectangle(10,10,60,20));
		inputStartDate.setBounds(new Rectangle(75,10,100,20));
		lblStartDay.setBounds(new Rectangle(110,35,70,20));
		inputStartDate.setCanSelect(true);
		
		lblEndDate.setText("rsid.timesheet.end");
		lblEndDate.setBounds(new Rectangle(180,10,60,20));
		inputEndDate.setBounds(new Rectangle(245,10,100,20));
		lblEndDay.setBounds(new Rectangle(280,35,70,20));
		inputEndDate.setCanSelect(true);
		
		this.add(lblStartDate);
		this.add(inputStartDate);
		this.add(lblEndDate);
		this.add(inputEndDate);
		this.add(lblStartDay);
		this.add(lblEndDay);
	}
	private void addUICEvent() {
		inputStartDate.getDateComp().addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
			}
			public void focusLost(FocusEvent e) {
				if(inputStartDate.getUICValue() == null) {
					return;
				}
				Date begin = (Date) inputStartDate.getUICValue();
				Calendar ca = Calendar.getInstance();
				ca.setTime(begin);
				int day = ca.get(Calendar.DAY_OF_WEEK);
				lblStartDay.setText(selectDays(day));
			}
		});
		inputEndDate.getDateComp().addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
			}
			public void focusLost(FocusEvent e) {
				if(inputEndDate.getUICValue() == null) {
					return;
				}
				Date begin = (Date) inputEndDate.getUICValue();
				Calendar ca = Calendar.getInstance();
				ca.setTime(begin);
				int day = ca.get(Calendar.DAY_OF_WEEK);
				lblEndDay.setText(selectDays(day));
			}
		});
	}
	protected void resetUI() {
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_GetLastTsDate);
		ReturnInfo returnInfo = this.accessData(inputInfo);
		DtoTimeSheetPeriod lastPeriod = null;
		if(!returnInfo.isError()) {
			lastPeriod = (DtoTimeSheetPeriod) 
			            returnInfo.getReturnObj(DtoTimeSheetPeriod.DTO_PERIOD);
		}
		if (lastPeriod != null && lastPeriod.getEndDate() != null) {
			Date lastDate = lastPeriod.getEndDate();
			if (lastDate.after(Global.todayDate)) {
				Calendar ca = Calendar.getInstance();
				ca.setTime(lastDate);
				ca.add(Calendar.DATE, 1);
				inputStartDate.setUICValue(ca.getTime());
				ca.add(Calendar.DATE, 6);
				inputEndDate.setUICValue(ca.getTime());
			} else {
				inputStartDate.setUICValue(Global.todayDate);
				Calendar ca = Calendar.getInstance();
				ca.setTime(Global.todayDate);
				ca.add(Calendar.DATE, 6);
				inputEndDate.setUICValue(ca.getTime());
			}
		} else {
			inputStartDate.setUICValue(Global.todayDate);
			Calendar ca = Calendar.getInstance();
			ca.setTime(Global.todayDate);
			ca.add(Calendar.DATE, 6);
			inputEndDate.setUICValue(ca.getTime());
		}
		setDay();
	}
	private void setDay(){
		Calendar ca = Calendar.getInstance();
		Date begin = (Date) inputStartDate.getUICValue();
		ca.setTime(begin);
		int day = ca.get(Calendar.DAY_OF_WEEK);
		lblStartDay.setText(selectDays(day));
		Date end = (Date) inputEndDate.getUICValue();
		ca.setTime(end);
		day = ca.get(Calendar.DAY_OF_WEEK);
		lblEndDay.setText(selectDays(day));
	}
	private String selectDays(int day) {
		switch (day) {
		case 1:
			return MessageUtil.getMessage("rsid.timesheet.periodsun");
		case 2:
			return MessageUtil.getMessage("rsid.timesheet.periodmon");
		case 3:
			return MessageUtil.getMessage("rsid.timesheet.periodtue");
		case 4:
			return MessageUtil.getMessage("rsid.timesheet.periodwed");
		case 5:
			return MessageUtil.getMessage("rsid.timesheet.periodthu");
		case 6:
			return MessageUtil.getMessage("rsid.timesheet.periodfri");
		case 7:
			return MessageUtil.getMessage("rsid.timesheet.periodsat");
		}
		return "";
	}
	public void processAdd(){
		if(checkError()){
			return;
		}
		DtoTimeSheetPeriod dtoPeriod = new DtoTimeSheetPeriod();
		dtoPeriod.setBeginDate((Date)inputStartDate.getUICValue());
		dtoPeriod.setEndDate((Date)inputEndDate.getUICValue());
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_AddTsDate);
		inputInfo.setInputObj(DtoTimeSheetPeriod.DTO_PERIOD, dtoPeriod);
		this.accessData(inputInfo);
	}
	private boolean checkError() {
		if(inputStartDate.getUICValue() == null) {
			comMSG.dispErrorDialog("error.client.VwTsDates.inputStart");
			return true;
		} 
		if(inputEndDate.getUICValue() == null) {
			comMSG.dispErrorDialog("error.client.VwTsDates.inputEnd");
			return true;
		}
		Date begin = (Date)inputStartDate.getUICValue();
		Date end = (Date)inputEndDate.getUICValue();
		if(begin.after(end)) {
			comMSG.dispErrorDialog("error.client.VwTsDates.endAfterStart");
			return true;
		}
		return false;
	}

}
