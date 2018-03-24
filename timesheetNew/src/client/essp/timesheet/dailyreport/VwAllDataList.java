package client.essp.timesheet.dailyreport;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.dailyreport.DtoAll;
import c2s.essp.timesheet.dailyreport.DtoDrActivity;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;

public class VwAllDataList extends VWTableWorkArea implements IVWPopupEditorEvent{
	
	private static final String actionId_AllData = "FTSAllData";
	private Date day;
	private List list;
	
	public VwAllDataList() {
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
				{ "rsid.timesheet.workTime", "activityWorkTime",
						VMColumnConfig.UNEDITABLE, new VWJReal(), Boolean.FALSE },
				{ "rsid.timesheet.workItem", "item", VMColumnConfig.UNEDITABLE,
						new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.workTime", "stepWorkTime", VMColumnConfig.UNEDITABLE,
						new VWJReal(), Boolean.FALSE },
				{ "rsid.timesheet.isFinish", "isFinish", VMColumnConfig.UNEDITABLE,
						new VWJCheckBox(), Boolean.FALSE }};
		super.jbInit(configs, DtoAll.class);
		
	}
	private void addUICEvent() {
		
	}
	public void setParameter(Parameter param) {
		super.setParameter(param);
		day = (Date) param.get(DtoDrActivity.DTO_DAY);
	}
	protected void resetUI() {
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_AllData);
		inputInfo.setInputObj(DtoDrActivity.DTO_DAY, day);
		ReturnInfo returnInfo = this.accessData(inputInfo);
		if(returnInfo.isError() == false) {
			list = (List) returnInfo.getReturnObj(DtoAll.DTO_RESULTS);
			this.getModel().setRows(list);
	        if(list != null && list.size() > 0) {
	            this.getTable().setSelectRow(0);
	        }
		}
		
	}
	public boolean onClickCancel(ActionEvent e) {
		return true;
	}
	public boolean onClickOK(ActionEvent e) {
		return true;
	}
	
	
	
}
