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
 * 预览area code的Action
 * 
 * @author Robin
 * 
 */
public class AcPreviewAreaCode extends AbstractESSPAction {
	private final static String CODE="code";

	/**
	 * 预览area code
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
			String code=null;
			if (request.getParameter(CODE) != null) {
				code = (String) request.getParameter(CODE);
			}
			
			Site site=new Site();
			
			// 通过此方法以接口的形式得到一个服务的实例
			ISiteService logic = (ISiteService) this
					.getBean("AreaCodeLogic");
			
			site=logic.loadByCode(code);
			
			request.setAttribute(Constant.VIEW_BEAN_KEY,site);
//			throw new BusinessException("error.system.db");
			//默认会转向ForwardId为sucess的页面
			//如果需要自定义ForWardId，用下面的语句
			//data.getReturnInfo().setForwardID("ForwardId");
	
	}

}
