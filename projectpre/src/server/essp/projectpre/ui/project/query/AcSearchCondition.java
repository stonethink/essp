package server.essp.projectpre.ui.project.query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * 将选择的检视栏位放到session中
 * @author junzheng
 *
 */
public class AcSearchCondition extends AbstractESSPAction {
	/**
	 * 将选择的检视栏位放到session中
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		AfSearchCondition  searchForm = (AfSearchCondition)this.getForm();
		//如果没有勾选任何显示栏位选项，则显示默认的显示栏位
		if(!searchForm.isSelectSomething()){
			searchForm.createDefaultSelect();
		}
		request.getSession().setAttribute("SearchCondition",searchForm);

	}

}
