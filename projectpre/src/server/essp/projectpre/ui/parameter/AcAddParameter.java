package server.essp.projectpre.ui.parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.ui.parameter.AfParameter;
import server.framework.common.BusinessException;

/**
 * 新增参数设定的Action
 * @author hongweihou
 *
 */
public class AcAddParameter extends AbstractESSPAction {
	/**
	 * 新增参数设定
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {

		// 如果有ActionForm传入的话，用此方法获得ActionFrom
		AfParameter af = (AfParameter) this.getForm();
		Parameter parameter = new Parameter();
		if (af.getKind() != null && !af.getKind().trim().equals("")) {
			parameter.getCompId().setKind(af.getKind().trim());
		}
		if (af.getCode() != null && !af.getCode().trim().equals("")) {
			parameter.getCompId().setCode(af.getCode().trim());
		}
		if (af.getName() != null && !af.getName().trim().equals("")) {
			parameter.setName(af.getName().trim());
		}
        if (af.getSequence() != null && !af.getSequence().trim().equals("")) {
            parameter.setSequence(new Long(af.getSequence().trim()));
        }
       
        parameter.setDescription(af.getDescription().trim());
   
		parameter.setStatus(new Boolean(true));
		// 通过此方法以接口的形式得到一个服务的实例
		IParameterService logic = (IParameterService) this.getBean("ParameterLogic");

		logic.save(parameter);

		// 默认会转向ForwardId为sucess的页面
		// 如果需要自定义ForWardId，用下面的语句
		// data.getReturnInfo().setForwardID("ForwardId");
	}

}
