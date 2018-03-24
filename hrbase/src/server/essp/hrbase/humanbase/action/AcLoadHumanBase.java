package server.essp.hrbase.humanbase.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.humanbase.service.IHumanBaseSevice;
import server.essp.hrbase.humanbase.viewbean.VbHumanBase;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcLoadHumanBase extends AbstractESSPAction {

	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData transData)
			throws BusinessException {
		String ridStr = request.getParameter("rid");
		if (ridStr == null || "".equals(ridStr)) {
			return;
		}
		IHumanBaseSevice service = (IHumanBaseSevice) this
				.getBean("humanBaseSevice");
		VbHumanBase hb = service.loadHumanBase(Long.valueOf(ridStr));
		request.setAttribute("webVo", hb);
	}
}
