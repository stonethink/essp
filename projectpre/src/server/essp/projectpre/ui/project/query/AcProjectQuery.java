package server.essp.projectpre.ui.project.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.account.IAccountService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;
/**
 * ����ҳ������������ѯר������
 * @author wenhaizheng
 *
 */
public class AcProjectQuery extends AbstractESSPAction {
	
	/**
	 * ����ҳ������������ѯר������
	 * ����������õ�ǰ̨ViewBean
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		AfProjectQuery searchForm = (AfProjectQuery)this.getForm();
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        List roleList = (List)user.getRoles();
        String userLoginId = user.getUserLoginId();
        IAccountService logic = (IAccountService) this.getBean("AccountLogic");
        Map resultMap = logic.listAllMacthedConditionAcnt(searchForm, userLoginId, roleList);
        List acntList = (List)resultMap.get("acntList");
        String hql = (String)resultMap.get("HQL");
        Map hqlMap = new HashMap();
        //����ѯ���SQL����Լ�Ҫ���õ����ڲ�����MAP��ȡ������session�У��Ա�����ר������ʱʹ��
        hqlMap.put("HQL", hql);
        hqlMap.put("beginDate",resultMap.get("beginDate"));
        hqlMap.put("endDate",resultMap.get("endDate"));
        hqlMap.put("planedStartDate",resultMap.get("planedStartDate"));
        hqlMap.put("planedFinishDate",resultMap.get("planedFinishDate"));
        request.getSession().setAttribute("HQLMAP", hqlMap);
        request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY,acntList);
		Object bean = request.getSession().getAttribute("SearchCondition");
		if(bean==null){
			AfSearchCondition searchCondition = new AfSearchCondition();
			searchCondition.createDefaultSelect();
			request.getSession().setAttribute("SearchCondition",searchCondition);
		}
	}
}
