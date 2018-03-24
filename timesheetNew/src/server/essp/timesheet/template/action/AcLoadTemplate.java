package server.essp.timesheet.template.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.template.form.AfTemplate;
import server.essp.timesheet.template.service.ITemplateService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

public class AcLoadTemplate extends AbstractESSPAction {
	
	/**
	 * 根据rid获取当前的模版数据
	 * 当rid为空的时候，初始rid为0
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		AfTemplate vb =new AfTemplate ();	
		Long rid = 0L;
		if(request.getAttribute("RefreshUpPage")!=null){
			request.setAttribute("RefreshUpPage","true");
		}else{
			request.setAttribute("RefreshUpPage","false");
		}
		ITemplateService service = (ITemplateService) this.getBean("templateService");
		if(null!=request.getParameter("rid"))		
		rid =Long.valueOf(request.getParameter("rid"));	
		if((rid == null||"".equals(rid))&&request.getAttribute("rid") != null){
			rid=Long.valueOf(request.getAttribute("rid").toString());
		}
		vb=service.getTemplate(rid);		
		request.setAttribute(Constant.VIEW_BEAN_KEY,vb);
	}
}
