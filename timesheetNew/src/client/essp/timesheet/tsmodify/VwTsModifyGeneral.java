package client.essp.timesheet.tsmodify;

import c2s.essp.timesheet.workscope.DtoAccount;
import client.essp.common.view.VWTDWorkArea;
import client.essp.timesheet.workscope.VwWorkScope;
import client.framework.view.vwcomp.VWUtil;

import com.wits.util.Parameter;

public class VwTsModifyGeneral extends VWTDWorkArea {
	
	private VwWorkScope vwWorkScope;
	private VwTsModifyWeeklyReport vwWeeklyReport;
	private String scopeLoginId;
	private String oldLoginId;
	
	public VwTsModifyGeneral() {
		super(200);
		jbInit();
	}
	
	private void jbInit() {
		this.setHorizontalSplit();
		vwWorkScope = new VwWorkScope();
		vwWeeklyReport = new VwTsModifyWeeklyReport();
		this.setTopArea(vwWorkScope);
		this.getDownArea().addTab("rsid.timesheet.timesheet", vwWeeklyReport);
		
	}
	public void setParameter(Parameter para) {
		super.setParameter(para);
		if(para != null) {
			para.put(DtoAccount.SCOPE_MODEL, DtoAccount.SCOPE_MODEL_PARAM);
			oldLoginId = scopeLoginId;
			scopeLoginId = (String) para.get(DtoAccount.SCOPE_LOGIN_ID);
		}
		vwWorkScope.setParameter(para);
		vwWeeklyReport.setParameter(para);
	}
	
	public void refreshWorkArea() {
		if(scopeLoginId != null && scopeLoginId.equals(oldLoginId) == false){
			vwWorkScope.refreshWorkArea();
		} else if(oldLoginId != null) {
			vwWorkScope.refreshWorkArea();
		}
		vwWeeklyReport.refreshWorkArea();
	}

}
