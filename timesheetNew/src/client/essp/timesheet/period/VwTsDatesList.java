package client.essp.timesheet.period;

import java.util.List;

import com.wits.util.Parameter;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;


public class VwTsDatesList extends VWTableWorkArea {
	

	private List tsDatesList;
	private static final String actionId_GetTsDatesList = "FTSGetTsDatesList";
	private static final String actionId_DeleteTsDates = "FTSDeleteTsDates";
	private List tsDates;
	public VwTsDatesList() {
		try {
			jbInit();
		} catch (Exception e) {
			log.equals(e);
		}
	}
	private void jbInit() throws Exception {
		Object[][] configs = new Object[][] {
			{ "rsid.timesheet.tsDatesStart", "beginDate",
					VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.FALSE },
			{ "rsid.timesheet.tsDatesEnd", "endDate",
					VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.FALSE }};
		super.jbInit(configs,  DtoTimeSheetPeriod.class);
	}
	/**
	 * ¼¤»î½çÃæ
	 */
	protected void resetUI() {
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_GetTsDatesList);
		ReturnInfo returnInfo =  this.accessData(inputInfo);
		if(!returnInfo.isError()) {
			tsDatesList = (List) returnInfo.getReturnObj(
					                     DtoTimeSheetPeriod.DTO_TsDastesList);
		}
		this.getModel().setRows(tsDatesList);
		if(tsDatesList != null && tsDatesList.size() > 0) {
			this.getTable().setSelectRow(0);
		}
	}
	
	public void setParameter(Parameter para) {
		super.setParameter(para);
		tsDates = (List) para.get(DtoTimeSheetPeriod.DTO_TsDastesList);
	}
	public void processDel() {
		if(tsDates == null || tsDates.size() == 0) {
			return;
		}
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_DeleteTsDates);
		inputInfo.setInputObj(DtoTimeSheetPeriod.DTO_TsDastesList, tsDates);
		this.accessData(inputInfo);
	}
}
