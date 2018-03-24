package server.essp.timesheet.template.step.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.template.step.form.Afstep;
import server.essp.timesheet.template.step.service.IDetailStepService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

public class AcPriviewStep  extends AbstractESSPAction {

	/**
	 * 浏览当前选中的step
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		Long rid = 0L;
		String tempId="";
		if(request.getAttribute("RefreshUpPage")!=null){
			request.setAttribute("RefreshUpPage","true");
		}else{
			request.setAttribute("RefreshUpPage","false");
		}
		if(("0")!=request.getParameter("rid")&&!("").equals(request.getParameter("rid")))
			rid = Long.valueOf(request.getParameter("rid"));	
		if(rid==0L&&request.getAttribute("rid") != null){
			rid=Long.valueOf(request.getAttribute("rid").toString());
		}
		if(null!=request.getParameter("tempId"))
		tempId=request.getParameter("tempId");
		if(("").equals(tempId)&&null!=request.getAttribute("tempId"))
			tempId=request.getAttribute("tempId").toString();
		IDetailStepService service = (IDetailStepService) this.getBean("stepService");
		Afstep vb=service.getStep(rid);		
		vb.setTempRid(tempId);
		request.setAttribute(Constant.VIEW_BEAN_KEY,vb);		
	}

}
