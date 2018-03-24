package server.essp.timesheet.methodology.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.methodology.service.IMethodService;
import server.framework.common.BusinessException;
import server.essp.timesheet.methodology.form.VbMethod;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

public class AcLoadMethod extends AbstractESSPAction {
	
	/**
	 * 根据rid查询methodology
	 * 当穿入的rid为空的时候，将viewbean的属性值设置为空
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		VbMethod method =new VbMethod ();		 
		String ridStr = null;
		
		if(request.getAttribute("RefreshUpPage")!=null){
			request.setAttribute("RefreshUpPage","true");
		}else{
			request.setAttribute("RefreshUpPage","false");
		}
		if(request.getParameter("rid")!=null){
			ridStr=request.getParameter("rid");}
		if((ridStr == null||"".equals(ridStr))&&request.getAttribute("rid") != null){
			ridStr=request.getAttribute("rid").toString();}
		if(ridStr == null ||"".equals(ridStr)){
			method.setDescription("");
			method.setName("");
		}else{
			IMethodService service = (IMethodService) this.getBean("methodService");
			method=service.getMethod(Long.valueOf(ridStr));
		}
		request.setAttribute(Constant.VIEW_BEAN_KEY,method);
	}
}
