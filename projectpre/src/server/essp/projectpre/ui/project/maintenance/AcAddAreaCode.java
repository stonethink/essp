package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * ���area code��Action
 * 
 * @author Robin
 * 
 */
public class AcAddAreaCode extends AbstractESSPAction {

	/**
	 * ���area code
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {

		// �����ActionForm����Ļ����ô˷������ActionFrom
		AfSite af = (AfSite) this.getForm();
		Site site = new Site();
		if (af.getSiteName() != null && !af.getSiteName().trim().equals("")) {
			site.setSiteName(af.getSiteName().trim());
		}
		if (af.getSiteCode() != null && !af.getSiteCode().trim().equals("")) {
			site.setSiteCode(af.getSiteCode().trim());
		}
        if (af.getManager() != null && !af.getManager().trim().equals("")) {
            site.setManager(af.getManager().trim());
        }
        site.setDescription(af.getDescription());
		site.setStatus(true);
		site.setSiteLoading(af.getSiteLoading());
		// ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
		ISiteService logic = (ISiteService) this.getBean("AreaCodeLogic");

		logic.save(site);

		// Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
		// �����Ҫ�Զ���ForWardId������������
		// data.getReturnInfo().setForwardID("ForwardId");
	}

}
