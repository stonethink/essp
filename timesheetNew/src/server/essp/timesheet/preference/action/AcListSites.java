package server.essp.timesheet.preference.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.preference.DtoPreference;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.preference.service.IPreferenceService;
import server.framework.common.BusinessException;

public class AcListSites extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		IPreferenceService service = (IPreferenceService)
        										this.getBean("preferenceService");
		data.getReturnInfo().setReturnObj(DtoPreference.DTO_SITE, service.listSites());
	}

}
