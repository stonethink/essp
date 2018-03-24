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
 * ����area code��Action
 * 
 * @author Robin
 * 
 */
public class AcUpdateAreaCode extends AbstractESSPAction {

	/**
	 * ����area code
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
			
			// �����ActionForm����Ļ����ô˷������ActionFrom
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
			// ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
			ISiteService logic = (ISiteService) this
					.getBean("AreaCodeLogic");
			
			logic.update(site);
			request.setAttribute(Constant.VIEW_BEAN_KEY,site);
			//Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
			//�����Ҫ�Զ���ForWardId������������
			//data.getReturnInfo().setForwardID("NULL");

	}

}
