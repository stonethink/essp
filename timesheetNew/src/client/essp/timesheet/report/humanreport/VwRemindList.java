package client.essp.timesheet.report.humanreport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoHumanTimes;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;

public class VwRemindList extends VWTableWorkArea implements IVWPopupEditorEvent{
	
	private List<DtoHumanTimes> resultList;
	JButton selectAllBtn = new JButton();
	JButton selectNonBtn = new JButton();
	JButton selectDiffBtn = new JButton();
	JButton sendBtn = new JButton();
	private final static String acntionId_Send = "FTSSendMails";
	
	public VwRemindList() {
		try {
			jbInit();
		} catch (Exception e) {
			log.error(e);
		}
		addUICEvent();
	}
	private void jbInit() throws Exception {
		VWJ2RealNumForReport twoReal = new VWJ2RealNumForReport();
		twoReal.setMaxInputDecimalDigit(2);
		twoReal.setMaxInputIntegerDigit(6);
		twoReal.setCanNegative(true);
		twoReal.setCanNegative2(true);
		twoReal.setCanInputSecondNum(true);
		VWJReal real = new VWJReal();
		real.setCanNegative(true);
		real.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] { {"rsid.timesheet.checked", "checked",
            				VMColumnConfig.EDITABLE, new VWJCheckBox(),
            				Boolean.FALSE}, {"rsid.timesheet.employeeId", "empId",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.employeeName", "empName",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.workStart", "beginDate",
                            VMColumnConfig.UNEDITABLE, new VWJDate(),
                            Boolean.FALSE}, {"rsid.timesheet.workEnd", "endDate",
                            VMColumnConfig.UNEDITABLE, new VWJDate(),
                            Boolean.FALSE}, {"rsid.timesheet.standardHours", "standarHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.actualHours", "actualHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.normalHours", "normalHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.overtimeHours", "overtimeHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.leaveHours", "leaveHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE},{"rsid.timesheet.balance", "sumBalance",
                            VMColumnConfig.UNEDITABLE, twoReal,
                            Boolean.FALSE}
       };
       super.jbInit(configs, DtoHumanTimes.class);
       //可排序
       this.getTable().setSortable(true);
       
	}
	
	private void addUICEvent() {
		this.getButtonPanel().addButton(selectAllBtn);
		selectAllBtn.setText("rsid.timesheet.selectAll");
		selectAllBtn.setToolTipText("rsid.timesheet.selectAll");
		selectAllBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processSelectAll();
			}
		});
		
		this.getButtonPanel().addButton(selectNonBtn);
		selectNonBtn.setText("rsid.timesheet.selectNon");
		selectNonBtn.setToolTipText("rsid.timesheet.selectNon");
		selectNonBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processSelectNon();
			}
		});
		
		this.getButtonPanel().addButton(selectDiffBtn);
		selectDiffBtn.setText("rsid.timesheet.selectDiff");
		selectDiffBtn.setToolTipText("rsid.timesheet.selectDiff");
		selectDiffBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processSelectDiff();
			}
		});
		
		this.getButtonPanel().addButton(sendBtn);
		sendBtn.setText("rsid.timesheet.send");
		sendBtn.setToolTipText("rsid.timesheet.send");
		sendBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processSend();
			}
		});
		
//		// Display
//		TableColumnChooseDisplay chooseDisplayTimes = new TableColumnChooseDisplay(
//				this.getTable(), this);
//		JButton buttonTimes = chooseDisplayTimes.getDisplayButton();
//		this.getButtonPanel().addButton(buttonTimes);
	}
	private void processSelectAll() {
		for(DtoHumanTimes dto : resultList) {
			dto.setChecked(true);
		}
		resetUI();
	}
	private void processSelectNon() {
		for(DtoHumanTimes dto : resultList) {
			dto.setChecked(false);
		}
		resetUI();
	}
	private void processSelectDiff() {
		for(DtoHumanTimes dto : resultList) {
			if(dto.isBalanced()){
				dto.setChecked(true);
			} else {
				dto.setChecked(false);
			}
		}
		resetUI();
	}
	private void processSend() {
		List sendList = new ArrayList();
		for(DtoHumanTimes dto : resultList) {
			if(dto.isChecked()) {
				sendList.add(dto);
			}
		}
		if(sendList.size() == 0) {
			return;
		}
		InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(acntionId_Send);
        inputInfo.setInputObj(DtoHumanTimes.DTO_SEND_LIST, sendList);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(!returnInfo.isError()){
           comMSG.dispMessageDialog("rsid.timesheet.sendOver");
        }
	}
	/**
     * 参数设置
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        resultList = (List)param.get(DtoHumanTimes.DTO_QUERY_LIST);
    }
    /**
     * 刷新界面
     */
    protected void resetUI() {
        this.getModel().setRows(resultList);
    }
	public boolean onClickCancel(ActionEvent e) {
		return true;
	}
	public boolean onClickOK(ActionEvent e) {
		return true;
	}
}
