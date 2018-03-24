package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

/**
 * Ԥ��area code��Action
 * 
 * @author Robin
 * 
 */
public class AcPreviewAreaCode extends AbstractESSPAction {
	private final static String CODE="code";

	/**
	 * Ԥ��area code
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
			String code=null;
			if (request.getParameter(CODE) != null) {
				code = (String) request.getParameter(CODE);
			}
			
			Site site=new Site();
			
			// ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
			ISiteService logic = (ISiteService) this
					.getBean("AreaCodeLogic");
			
			site=logic.loadByCode(code);
			
			request.setAttribute(Constant.VIEW_BEAN_KEY,site);
//			throw new BusinessException("error.system.db");
			//Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
			//�����Ҫ�Զ���ForWardId������������
			//data.getReturnInfo().setForwardID("ForwardId");
	
	}

}
