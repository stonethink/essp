/*
 * Created on 2007-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.humanbase.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.humanbase.form.AfHumanBaseQuery;
import server.essp.hrbase.humanbase.form.AfSearchCondition;
import server.essp.hrbase.humanbase.service.IHumanBaseSevice;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcListHumanBase extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData transData) throws BusinessException {
    	AfHumanBaseQuery af = (AfHumanBaseQuery)this.getForm();
    	IHumanBaseSevice service = (IHumanBaseSevice)this.getBean("humanBaseSevice");
        List resultList = service.getHrBaseByCondition(af);
        List vbList = service.beanList2VbList(resultList);
        request.setAttribute("webVo",vbList);
        Object bean = request.getSession().getAttribute("HrSearchCondition");
		if(bean==null){
			AfSearchCondition searchCondition = new AfSearchCondition();
			searchCondition.createDefaultSelect();
			request.getSession().setAttribute("HrSearchCondition",searchCondition);
		}
    }
}
