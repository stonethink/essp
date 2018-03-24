package server.essp.timesheet.template.step.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.template.step.form.Afstep;
import server.essp.timesheet.template.step.service.IDetailStepService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcSaveStep extends AbstractESSPAction {

	/**
	 * 新增或者修改step
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {	
		Afstep form = (Afstep)this.getForm();
		Long rid=null;
		IDetailStepService service = (IDetailStepService) this.getBean("stepService");		
		rid=service.saveStep(form);
		request.setAttribute("tempId", form.getTempRid());
		request.setAttribute("RefreshUpPage","true");
		request.setAttribute("rid",rid);
		}
}
