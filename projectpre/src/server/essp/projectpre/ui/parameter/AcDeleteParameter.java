package server.essp.projectpre.ui.parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.essp.projectpre.service.parameter.IParameterService;
import server.framework.common.BusinessException;
/**
 * 删除参数设定的Action
 * @author hongweihou
 *
 */
public class AcDeleteParameter extends AbstractESSPAction {
	/**
	 * 删除参数设定
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		AfParameter af=(AfParameter) this.getForm();
		Parameter parameter=new Parameter();
		if(af.getKind()!=null) {
			parameter.getCompId().setKind(af.getKind());
		}
		if(af.getCode()!=null) {
			parameter.getCompId().setCode(af.getCode());
		}
		// 通过此方法以接口的形式得到一个服务的实例
		IParameterService logic = (IParameterService) this.getBean("ParameterLogic");
        ParameterId parameterId = parameter.getCompId();
	    logic.delete(parameterId);
        request.setAttribute("compId.kind",parameter.getCompId().getKind());
		// 默认会转向ForwardId为sucess的页面
		// 如果需要自定义ForWardId，用下面的语句
		// data.getReturnInfo().setForwardID("ForwardId");

	}


}
