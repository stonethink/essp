package server.essp.timesheet.step.management.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.methodology.service.IMethodService;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.step.management.DtoTemplate;

public class AcTemplateList extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		String id = (String) data.getInputInfo().getInputObj("AccountId");
		IMethodService service = (IMethodService) this.getBean("methodService");
		List list = service.getTemplateListByAcntId(id);
		List result = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			DtoTemplate dest = new DtoTemplate();
			DtoUtil.copyBeanToBean(dest, list.get(i));
			result.add(dest);
		}

		data.getReturnInfo()
				.setReturnObj(DtoTemplate.KEY_TEMPLATE_LIST, result);

	}

}
