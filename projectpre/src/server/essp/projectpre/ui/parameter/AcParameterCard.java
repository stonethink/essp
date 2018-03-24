package server.essp.projectpre.ui.parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
/**
 * 设置跳转页面的参数到request中
 * @author hongweihou
 *
 */
public class AcParameterCard  extends AbstractESSPAction{
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		String kind=request.getParameter("kind");
		request.setAttribute("kind",kind);
	}
}
