package server.essp.projectpre.ui.project.query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * ��ѡ��ļ�����λ�ŵ�session��
 * @author junzheng
 *
 */
public class AcSearchCondition extends AbstractESSPAction {
	/**
	 * ��ѡ��ļ�����λ�ŵ�session��
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		AfSearchCondition  searchForm = (AfSearchCondition)this.getForm();
		//���û�й�ѡ�κ���ʾ��λѡ�����ʾĬ�ϵ���ʾ��λ
		if(!searchForm.isSelectSomething()){
			searchForm.createDefaultSelect();
		}
		request.getSession().setAttribute("SearchCondition",searchForm);

	}

}
