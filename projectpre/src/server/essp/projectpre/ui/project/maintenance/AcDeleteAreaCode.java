package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * ɾ��area code��Action
 * 
 * @author Robin
 * 
 */
public class AcDeleteAreaCode extends AbstractESSPAction {
	private final static String CODE = "code";

	/**
	 * ɾ��area code
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		String code = null;
		if (request.getParameter(CODE) != null
				&& !request.getParameter(CODE).equals("")) {
			code = (String) request.getParameter(CODE);
		}

		// ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
		ISiteService logic = (ISiteService) this.getBean("AreaCodeLogic");

		logic.delete(code);

		// Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
		// �����Ҫ�Զ���ForWardId������������
		// data.getReturnInfo().setForwardID("ForwardId");

	}

}
