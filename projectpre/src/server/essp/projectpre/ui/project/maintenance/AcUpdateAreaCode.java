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
 * 更新area code的Action
 * 
 * @author Robin
 * 
 */
public class AcUpdateAreaCode extends AbstractESSPAction {

	/**
	 * 更新area code
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
			
			// 如果有ActionForm传入的话，用此方法获得ActionFrom
			AfSite af=(AfSite) this.getForm();
			Site site=new Site();
			if(af.getSiteName()!=null) {
				site.setSiteName(af.getSiteName());
			}
			if(af.getSiteCode()!=null) {
				site.setSiteCode(af.getSiteCode());
			}
            if(af.getManager()!=null) {
                site.setManager(af.getManager());
            }
			if(af.getStatus()!=null&&af.getStatus().equals("true")) {
				site.setStatus(true);
			}else {
				site.setStatus(false);
			}
			site.setDescription(af.getDescription());
			site.setSiteLoading(af.getSiteLoading());
			// 通过此方法以接口的形式得到一个服务的实例
			ISiteService logic = (ISiteService) this
					.getBean("AreaCodeLogic");
			
			logic.update(site);
			request.setAttribute(Constant.VIEW_BEAN_KEY,site);
			//默认会转向ForwardId为sucess的页面
			//如果需要自定义ForWardId，用下面的语句
			//data.getReturnInfo().setForwardID("NULL");

	}

}
