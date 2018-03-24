package client.essp.timesheet.period;

import java.awt.Rectangle;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.tsdates.DtoTsDates;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.ui.MessageUtil;
import client.framework.common.Global;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWButtonGroup;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJDatePanel;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJRadioButton;

public class VwTsDatesCreator extends VWGeneralWorkArea {
	
	private VWJLabel lblLastEndDate = new VWJLabel();
	private VWJLabel lblBeginDate = new VWJLabel();
	private VWJLabel lblEndDate = new VWJLabel();
	private VWJLabel lblTitleDown = new VWJLabel();
	private VWJLabel lblEndsOn = new VWJLabel();
	private VWJDatePanel outputLastEndDate = new VWJDatePanel();
	private VWJDatePanel inputBeginDate = new VWJDatePanel();
	private VWJLabel lblBeginDay = new VWJLabel();
	private VWJDatePanel inputEndDate = new VWJDatePanel();
	private VWJLabel lblEndDay = new VWJLabel();
	private VWJRadioButton radEveryWeek = new VWJRadioButton();
	private VWJRadioButton radEveryTwoWeeks = new VWJRadioButton();
	private VWJRadioButton radEveryFourWeeks = new VWJRadioButton();
	private VWJRadioButton radEveryMonth = new VWJRadioButton();
	private VWButtonGroup radGroup = new VWButtonGroup();
	private VWJComboBox selectDay = new VWJComboBox();
	private static final String actionId_GetLastTsDate = "FTSGetLastTsDate";
	private static final String actionId_CreateTsDates = "FTSCreateTsDates";
	private DtoTimeSheetPeriod lastPeriod;
	public VwTsDatesCreator() {
		jbInit();
		addUICEvent();
	}
	private void jbInit() {
		this.setLayout(null);
		lblLastEndDate.setText("rsid.timesheet.lastEndDate");
		lblLastEndDate.setBounds(new Rectangle(30,30,190,20));
		outputLastEndDate.setBounds(new Rectangle(30,50,100,20));
		outputLastEndDate.setEditable(false);
		
		lblBeginDate.setText("rsid.timesheet.batchStart");
		lblBeginDate.setBounds(new Rectangle(30,80,180,20));
		inputBeginDate.setBounds(new Rectangle(30,100,100,20));
		lblBeginDay.setBounds(new Rectangle(135,100,70,20));
		inputBeginDate.setCanSelect(true);
		
		lblEndDate.setText("rsid.timesheet.batchEnd");
		lblEndDate.setBounds(new Rectangle(30,130,150,20));
		inputEndDate.setBounds(new Rectangle(30,150,100,20));
		lblEndDay.setBounds(new Rectangle(135,150,70,20));
		inputEndDate.setCanSelect(true);
		
		lblTitleDown.setText("rsid.timesheet.tsDatePeriod");
		lblTitleDown.setBounds(new Rectangle(30,190,150,20));
		
		radEveryWeek.setText("rsid.timesheet.tsDateEveryWeek");
		radEveryWeek.setBounds(new Rectangle(35,210,150,20));
		radEveryWeek.setSelected(true);
		
		radEveryTwoWeeks.setText("rsid.timesheet.tsDateEveryTwoWeeks");
		radEveryTwoWeeks.setBounds(new Rectangle(35,240,150,20));
		
		radEveryFourWeeks.setText("rsid.timesheet.tsDateEveryFourWeeks");
		radEveryFourWeeks.setBounds(new Rectangle(35,270,150,20));
		
		radEveryMonth.setText("rsid.timesheet.tsDateEveryMonth");
		radEveryMonth.setBounds(new Rectangle(35,300,150,20));
		
		radGroup.add(radEveryWeek);
		radGroup.add(radEveryTwoWeeks);
		radGroup.add(radEveryFourWeeks);
		radGroup.add(radEveryMonth);
		
		lblEndsOn.setText("rsid.timesheet.tsDateEndsOn");
		lblEndsOn.setBounds(new Rectangle(30,340,150,20));
		
		selectDay.setBounds(new Rectangle(30,370,100,20));
		
		this.add(lblLastEndDate);
		this.add(outputLastEndDate);
		this.add(lblBeginDate);
		this.add(inputBeginDate);
		this.add(lblBeginDay);
		this.add(lblEndDate);
		this.add(inputEndDate);
		this.add(lblEndDay);
		this.add(lblTitleDown);
		this.add(radEveryWeek);
		this.add(radEveryTwoWeeks);
		this.add(radEveryFourWeeks);
		this.add(radEveryMonth);
		this.add(lblEndsOn);
		this.add(selectDay);
	}
	private void addUICEvent() {
		radEveryWeek.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				selectDay.setEnabled(true);
			}
		});
		radEveryTwoWeeks.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				selectDay.setEnabled(true);
			}
		});
		radEveryFourWeeks.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				selectDay.setEnabled(true);
			}
		});
		radEveryMonth.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				selectDay.setEnabled(false);
			}
		});
		inputBeginDate.getDateComp().addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
			}
			public void focusLost(FocusEvent e) {
				if(inputBeginDate.getUICValue() == null) {
					return;
				}
				Date begin = (Date) inputBeginDate.getUICValue();
				Calendar ca = Calendar.getInstance();
				ca.setTime(begin);
				int day = ca.get(Calendar.DAY_OF_WEEK);
				lblBeginDay.setText(selectDays(day));
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
	private int getDaysBetweenTwoDates(Date startDate, Date endDate) {
		int n = 24 * 60 * 60 * 1000; 
		return (int)((endDate.getTime() - startDate.getTime()) / (n));
	}   
	private boolean isError(String createWay) {
		//开始日期不能为空
		if(inputBeginDate.getUICValue() == null) {
			comMSG.dispErrorDialog("error.client.VwTsDates.inputBatchStart");
			return true;
		}
		//结束日期不能为空
		if(inputEndDate.getUICValue() == null) {
			comMSG.dispErrorDialog("error.client.VwTsDates.inputBatchEnd");
			return true;
		}
		Date begin = (Date)inputBeginDate.getUICValue();
		Date end = (Date)inputEndDate.getUICValue();
//		Date lastDate = (Date)outputLastEndDate.getUICValue();
		//开始日期不能晚于结束日期
		if(begin.after(end)){
			comMSG.dispErrorDialog("error.client.VwTsDates.batchEndAfterBatchStart");
			return true;
		}
//		//选定开始和结束日期覆盖了已存在的工时单区间
//		if(begin.before(lastDate)){
//			comMSG.dispErrorDialog("error.client.VwTsDates.tsDatesDup");
//			return true;
//		}
		int days = getDaysBetweenTwoDates(begin, end);
		boolean showPeriodError = false;
		//按所选工时单区间产生方式检查输入开始和结束日期是否至少包含一个工时单区间
		if(DtoTimeSheetPeriod.CREATE_WAY_EVERYWEEK.equals(createWay)
		 && days < 6) {
			showPeriodError = true;
		} else if(DtoTimeSheetPeriod.CREATE_WAY_EVERYTWOWEEKS.equals(createWay)
				 && days < 13) {
			showPeriodError = true;
		} else if(DtoTimeSheetPeriod.CREATE_WAY_EVERYFOURWEEKS.equals(createWay)
				 && days < 27) {
			showPeriodError = true;
		} else if(DtoTimeSheetPeriod.CREATE_WAY_EVERYMONTH.equals(createWay)) {
			Calendar ca = Calendar.getInstance();
			ca.setTime(begin);
			int beginMonth = ca.get(Calendar.MONTH);
			int beginDay = ca.get(Calendar.DAY_OF_MONTH);
			ca.setTime(end);
			int endMonth = ca.get(Calendar.MONTH);
			int endDay = ca.get(Calendar.DAY_OF_MONTH);
			if(beginMonth == endMonth && 
					(beginDay != 1 || 
					 endDay != ca.getMaximum(Calendar.DAY_OF_MONTH))) {
				showPeriodError = true;
			}
		}
		if(showPeriodError) {
			comMSG.dispErrorDialog("error.client.VwTsDates.includeOnePeriod");
			return true;
		}
		return false;
	}
	public boolean processBatchCreate() {
		String createWay = getCreateWay();
		if(isError(createWay)){
			return false;
		}
		DtoTsDates dtoTsDates = new DtoTsDates();
		dtoTsDates.setStartDate((Date)inputBeginDate.getUICValue());
		dtoTsDates.setEndDate((Date)inputEndDate.getUICValue());
		dtoTsDates.setCreateWay(createWay);
		dtoTsDates.setEndsOn((Integer)selectDay.getUICValue());
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_CreateTsDates);
		inputInfo.setInputObj(DtoTimeSheetPeriod.CREATE_CONDITION, dtoTsDates);
		ReturnInfo returnInfo = this.accessData(inputInfo);
		if(!returnInfo.isError()){
			return true;
		} else {
			return false;
		}
		
	}

	protected void resetUI() {
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_GetLastTsDate);
		ReturnInfo returnInfo = this.accessData(inputInfo);
		if(!returnInfo.isError()) {
			lastPeriod = (DtoTimeSheetPeriod) 
			            returnInfo.getReturnObj(DtoTimeSheetPeriod.DTO_PERIOD);
		}
		//如果没有查询到最后结束日期则默认开始日期为今天，结束日期为今天后推一周的日期
		if (lastPeriod != null && lastPeriod.getEndDate() != null) {
			Date lastDate = lastPeriod.getEndDate();
			outputLastEndDate.setUICValue(lastDate);
			//最后工时单日期如果大于今天的日期则开始日期为最后工时单周期的下一天
			//否则开始日期为今天，结束日期为今天后推一周的日期
			if (lastDate.after(Global.todayDate)) {
				Calendar ca = Calendar.getInstance();
				ca.setTime(lastDate);
				ca.add(Calendar.DATE, 1);
				inputBeginDate.setUICValue(ca.getTime());
				ca.add(Calendar.DATE, 6);
				inputEndDate.setUICValue(ca.getTime());
			} else {
				inputBeginDate.setUICValue(Global.todayDate);
				Calendar ca = Calendar.getInstance();
				ca.setTime(Global.todayDate);
				ca.add(Calendar.DATE, 6);
				inputEndDate.setUICValue(ca.getTime());
			}
		} else {
			outputLastEndDate.setUICValue(null);
			inputBeginDate.setUICValue(Global.todayDate);
			Calendar ca = Calendar.getInstance();
			ca.setTime(Global.todayDate);
			ca.add(Calendar.DATE, 6);
			inputEndDate.setUICValue(ca.getTime());
		}
		selectDay.setModel(createDayItem());
		setDay();
	}
	private void setDay(){
		Calendar ca = Calendar.getInstance();
		Date begin = (Date) inputBeginDate.getUICValue();
		ca.setTime(begin);
		int day = ca.get(Calendar.DAY_OF_WEEK);
		lblBeginDay.setText(selectDays(day));
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
	private VMComboBox createDayItem() {
		Vector v = new Vector();
		DtoComboItem item = null;
		//周日
		item = new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.periodsun"), 1);
		v.add(item);
		//周一
		item = new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.periodmon"), 2);
		v.add(item);
		//周二
		item = new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.periodtue"), 3);
		v.add(item);
		//周三
		item = new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.periodwed"), 4);
		v.add(item);
		//周四
		item = new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.periodthu"), 5);
		v.add(item);
		//周五
		item = new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.periodfri"), 6);
		v.add(item);
		//周六
		item = new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.periodsat"), 7);
		v.add(item);
		
		VMComboBox dayItem = new VMComboBox(v);
		return dayItem;
	}
	private String getCreateWay(){
		String createWay = DtoTimeSheetPeriod.CREATE_WAY_EVERYWEEK;
		if(radEveryTwoWeeks.isSelected()){
			createWay = DtoTimeSheetPeriod.CREATE_WAY_EVERYTWOWEEKS;
		} else if(radEveryFourWeeks.isSelected()) {
			createWay = DtoTimeSheetPeriod.CREATE_WAY_EVERYFOURWEEKS;
		} else if(radEveryMonth.isSelected()) {
			createWay = DtoTimeSheetPeriod.CREATE_WAY_EVERYMONTH;
		}
		
		return createWay;
	}
	
}
