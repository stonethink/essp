package server.essp.timesheet.step.management.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.template.step.form.VbStep;
import server.essp.timesheet.template.step.service.IDetailStepService;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.step.management.DtoTemplateStep;

public class AcLoadTemplateStepList extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		String id = data.getInputInfo().getInputObj("TemplateId").toString();
		IDetailStepService service = (IDetailStepService) this
				.getBean("stepService");
		List list = service.listStep(new Long(id));
		List result = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			VbStep orig = (VbStep) list.get(i);
			DtoTemplateStep dest = new DtoTemplateStep();
			DtoUtil.copyBeanToBean(dest, orig);
			if (orig.getIsCorp() == null || orig.getIsCorp().equals("No")) {
				dest.setIsCorp(false);
			} else {
				dest.setIsCorp(true);
			}
			result.add(dest);
		}

		data.getReturnInfo().setReturnObj(
				DtoTemplateStep.KEY_TEMPLATE_STEP_LIST, result);

	}
}
