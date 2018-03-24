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
 * 根据页面输入条件查询专案资料
 * @author wenhaizheng
 *
 */
public class AcProjectQuery extends AbstractESSPAction {
	
	/**
	 * 根据页面输入条件查询专案资料
	 * 并将结果设置到前台ViewBean
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
        //将查询后的SQL语句以及要设置的日期参数从MAP中取出放入session中，以备导出专案资料时使用
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
